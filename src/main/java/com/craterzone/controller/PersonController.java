package com.craterzone.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.craterzone.model.Person;
import com.craterzone.service.PersonService;

@RestController
@RequestMapping("/api/v1/person")
public class PersonController {

	@Autowired
	private PersonService personService;

	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody HashMap<String, Object> per) {
		Optional<HashMap<String, Object>> person = Optional.ofNullable(personService.create(per));

		if (Objects.nonNull(person))
			return ResponseEntity.status(HttpStatus.CREATED).body(person.get());
		else
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}

	@GetMapping
	public ResponseEntity<Optional<Person>> getPerson(@RequestParam String firstName) {
		Optional<Person> person = Optional.ofNullable(personService.getByFirstName(firstName));

		if (person.isPresent())
			return ResponseEntity.status(HttpStatus.OK).body(person);
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/getAll")
	public ResponseEntity<?> getAll() {
		List<HashMap> person = personService.getAll();
		if (person != null) {
			return new ResponseEntity<>(person, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@PutMapping
	public ResponseEntity<Person> update(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam int age) {
		Person person = personService.update(firstName, lastName, age);
		if (person == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		else
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(person);
	}

	@DeleteMapping
	public ResponseEntity<String> delete(@RequestParam String firstName) {
		Optional<Boolean> bool = Optional.of(personService.delete(firstName));
		if (bool.equals(false))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		else
			return ResponseEntity.status(HttpStatus.OK).build();
	}

	@DeleteMapping("/deleteAll")
	public ResponseEntity<String> deleteAll() {
		personService.deleteAll();
		return ResponseEntity.status(HttpStatus.OK).body("Deleted all records");
	}

	@GetMapping("/age")
	public ResponseEntity<List<Person>> getByAge(@RequestParam int age) {
		List<Person> list = personService.getAge(age);
		if (list.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		else
			return ResponseEntity.of(Optional.of(list));
	}
}