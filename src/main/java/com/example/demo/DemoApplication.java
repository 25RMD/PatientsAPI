
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.io.File;

@SpringBootApplication
@RestController
public class DemoApplication {

	private static List<Person> persons; // Class-level variable
	private static File file;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);


        file = new File("C:/Users/home/Documents/java projects/demo/src/main/resources/data.json");

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			persons = objectMapper.readValue(file, new TypeReference<List<Person>>() {});

		} catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
		}
	}

	@GetMapping("/api/patients")
	public Collection<Person> getAllPatients() {

		// Get the person by index

		return persons; // Return the name as a String
	}

	@GetMapping("/api/patients/{id}")
	public ResponseEntity<Person> getPatient(@PathVariable int id) {


		return new ResponseEntity<>(persons.get(id), HttpStatus.OK);
	}

	@PostMapping("/api/patients")
	public ResponseEntity<List<Person>> addPatient(@RequestBody Person person) throws IOException {
		persons.add(person);
		ObjectMapper objectMapper = new ObjectMapper();


		objectMapper.writeValue(file, persons);

		return new ResponseEntity<>(persons, HttpStatus.CREATED);

	}

	@DeleteMapping("/api/patients/{id}")
	public ResponseEntity<List<Person>> removePatient(@PathVariable int id) throws IOException {

		Person specificPerson = persons.get(id);
		persons.remove(specificPerson);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(file, persons);

		return new ResponseEntity<>(persons, HttpStatus.OK);

	}

	@PutMapping("/api/patients/{id}")
	public ResponseEntity<List<Person>> replacePatient(@PathVariable int id, @RequestBody Person person) throws IOException {

		persons.add(id, person);

		ObjectMapper objectMapper = new ObjectMapper();


		objectMapper.writeValue(file, persons);

		return new ResponseEntity<>(persons, HttpStatus.CREATED);

	}
}
