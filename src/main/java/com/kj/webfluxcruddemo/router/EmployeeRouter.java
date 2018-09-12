package com.kj.webfluxcruddemo.router;

import com.kj.webfluxcruddemo.handler.EmployeeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

/**
 * EmployeeRouter is routing class to configure all your employee routes and simple pass
 * handler to handle incoming request for corresponding end points.
 *
 * @author KJ
 * @version 1.0
 * @since 12 Sep, 2018
 */

@Configuration
public class EmployeeRouter {

    @Bean
    public RouterFunction<ServerResponse> route(EmployeeHandler employeeHandler) {
        return RouterFunctions.route(
                GET("/v1/employee").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), employeeHandler::getAllEmployees)
                .andRoute(GET("/v1/employee/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        employeeHandler::getEmployee)
                .andRoute(POST("/v1/employee").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        employeeHandler::addNewEmployee)
                .andRoute(PUT("/v1/employee/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), employeeHandler::updateEmployee)
                .andRoute(DELETE("/v1/employee/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), employeeHandler::deleteAnEmployee);
    }
}
