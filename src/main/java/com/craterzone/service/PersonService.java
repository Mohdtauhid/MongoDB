package com.craterzone.service;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craterzone.model.Person;
import com.craterzone.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	public Person create(String firstName, String lastName, int age) {
		return personRepository.save(new Person(firstName, lastName, age));
	}

	public List<Person> getAll() {
		return personRepository.findAll();
	}

	public Person getByFirstName(String firstName) {
		return personRepository.findByFirstName(firstName);
	}

	public Person update(String firstName, String lastName, int age) {
		Person person = personRepository.findByFirstName(firstName);          // Must use Unique name
		person.setLastName(lastName);
		person.setAge(age);
		return personRepository.save(person);
	}

	public void deleteAll() {
		personRepository.deleteAll();
	}

	public boolean delete(String firstName) {
		Optional<Person> person = Optional.of(personRepository.findByFirstName(firstName));
		if (person.isPresent()) {
			personRepository.delete(person.get());
			return true;
		} else
			return false;

	}

	public List<Person> getAge(int age) {
		return personRepository.XYZ(age);
	}

}