package com.kj.webfluxcruddemo.handler;

import com.kj.webfluxcruddemo.document.Employee;
import com.kj.webfluxcruddemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * EmployeeHandler is class to handle all request/response for Employee CRUD operations
 * Router will call corresponding handler to handle incoming requests.
 * for example /v1/employee POST will call createEmployee handler.
 *
 * @author KJ
 * @version 1.0
 * @since 12 Sep, 2018
 */

@Component
public class EmployeeHandler {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Get All employee method will be called by router to route incoming request at /v1/employee GET
     * and will return all employees available in DB.
     * @param request  //incoming server request
     * @return response  //outgoing server response.
     */

    public Mono<ServerResponse> getAllEmployees(ServerRequest request) {
        Flux<Employee> employees = employeeRepository.findAll();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(employees, Employee.class);
    }

    /**
     * Get an employee method will be called by router to route incoming request at /v1/employee/{id} GET
     * and will return an employee based on employee ID
     * @param request  //incoming server request
     * @return response  //outgoing server response.
     */

    public Mono<ServerResponse> getEmployee(ServerRequest request) {
        String employeeId = request.pathVariable("id");
        return employeeRepository.findById(employeeId)
                .flatMap(employee -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(employee), Employee.class)).switchIfEmpty(ServerResponse.notFound().build());
    }

    /**
     * Add a new employee method will be called by router to route incoming request at /v1/employee POST
     * and will return created employees.
     * @param request  //incoming server request
     * @return response  //outgoing server response.
     */

    public Mono<ServerResponse> addNewEmployee(ServerRequest request) {
        Mono<Employee> employeeMono = request.bodyToMono(Employee.class);
        Mono<Employee> newEmployee = employeeMono.flatMap(employeeRepository::save);
        return ServerResponse.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(newEmployee, Employee.class);
    }

    /**
     * Update an employee by employee ID and updated body coming in body.
     * will be called by router to route incoming request at /v1/employee/{id} PUT
     * and will return updated employee record.
     * @param request  //incoming server request
     * @return response  //outgoing server response.
     */

    public Mono<ServerResponse> updateEmployee(ServerRequest request) {
        String employeeId = request.pathVariable("id");
        Mono<Employee> employeeMono = request.bodyToMono(Employee.class);
        return employeeRepository.findById(employeeId)
                .flatMap(employee -> employeeMono.flatMap(employee1 -> {
                    employee.setFirstName(employee1.getFirstName());
                    employee.setLastName(employee1.getLastName());
                    Mono<Employee> updatedEmployee = employeeRepository.save(employee);
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(updatedEmployee, Employee.class);
                }));
    }

    /**
     * delete an employee based om employee ID.
     * will be called by router to route incoming request at /v1/employee/{id} DELETE
     * and will return all employees available in DB.
     * @param request  //incoming server request
     * @return response  //outgoing server response.
     */

    public Mono<ServerResponse> deleteAnEmployee(ServerRequest request) {
        String employeeId = request.pathVariable("id");
        return employeeRepository.findById(employeeId)
                .flatMap(employee -> employeeRepository
                        .delete(employee)
                        .then(ServerResponse.ok().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
