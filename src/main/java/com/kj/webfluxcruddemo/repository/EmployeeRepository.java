package com.kj.webfluxcruddemo.repository;

import com.kj.webfluxcruddemo.document.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * The EmployeeRepository is a simple interface which extend JpaRepository to provide all default methods to your
 * entity/document repository.
 *
 * @author KJ
 * @version 1.0
 * @since 12 Sep, 2018
 */

@Repository
public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String> {

    Mono<Employee> findById(String employeeId);
}
