package com.kj.webfluxcruddemo;

import com.kj.webfluxcruddemo.document.Employee;
import com.kj.webfluxcruddemo.handler.EmployeeHandler;
import com.kj.webfluxcruddemo.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebfluxCrudDemoApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    EmployeeHandler employeeHandler;

    @Autowired
    private EmployeeRepository employeeRepository;


    /**
     * Create a new Employee test, just check Created successfully.
     */
    @Test
    public void testCreateEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Due");
        employee.setEmail("john@due.com");
        webTestClient.post().uri("/v1/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employee), Employee.class)
                .exchange()
                .expectStatus().isCreated();

    }

    /**
     * Get All Employees available in DB
     */

    @Test
    public void testGetAllEmployees() {
        webTestClient.get()
                .uri("/v1/employee")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    /**
     * Get an employee from DB, first create a employee directly through repository then
     * webClient to verify by GET
     */

    @Test
    public void testGetEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Due");
        employee.setEmail("john1@due.com");
        Mono<Employee> createdEmployee = employeeRepository.save(employee);
        createdEmployee.flatMap(employee1 -> {
            webTestClient.get().uri("/v1/employee/" + employee1.getId())
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody()
                    .jsonPath("$.firstName").isEqualTo(employee.getFirstName());
            return null;
        });

    }

    /**
     * Update an employee, by creating one then updating value by PUT.
     */

    @Test
    public void testUpdateAnEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Due");
        employee.setEmail("john2@due.com");
        Mono<Employee> createdEmployee = employeeRepository.save(employee);
        createdEmployee.flatMap(employee1 -> {
            employee1.setFirstName("KJ");
            webTestClient.put().uri("/v1/employee/" + employee1.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(Mono.just(employee1), Employee.class)
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody()
                    .jsonPath("$.firstName").isEqualTo(employee1.getFirstName());
            return null;
        });

    }

}
