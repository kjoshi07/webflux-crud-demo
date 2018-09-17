# Spring Boot 2.x Reactive Rest CRUD APIs with Spring WebFlux &amp; Mongo DB

## STEP 1: Add all required dependencies

       <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webflux</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-mongodb-reactive</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>io.projectreactor</groupId>
			<artifactId>reactor-test</artifactId>
			<scope>test</scope>
		</dependency>
        
 ## STEP 2: Add MongoDB proporties in \src\main\resources\application.properties
 
     spring.data.mongodb.host=localhost
     spring.data.mongodb.port=27017
     spring.data.mongodb.database=demo
     
 ## STEP 3: Create a Employee @document class
 
     please see webflux-crud-demo\src\main\java\com\kj\webfluxcruddemo\document\Employee.java
 
 ## STEP 4: Extend Employee Repository by ReactiveMongoRepository, 
 
     please see \webflux-crud-demo\src\main\java\com\kj\webfluxcruddemo\repository\EmployeeRepository.java
 
 ## STEP 5: Create Router class to route end points
 
      please see \webflux-crud-demo\src\main\java\com\kj\webfluxcruddemo\router\EmployeeRouter.java
 
 ## STEP 6: Create handlers to handle incoming requests at end points, 
 
     please see \webflux-crud-demo\src\main\java\com\kj\webfluxcruddemo\handler\EmployeeHandler.java
