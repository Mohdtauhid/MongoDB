package com.craterzone.service;

import java.lang.StackWalker.Option;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.craterzone.model.Person;
import com.craterzone.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public Person create(Person person) {
				/* First way */
	//	return personRepository.save(person);
		
				/* Second way */
		return mongoTemplate.save(person);
	}

	public HashMap<String, Object> getAll() {
				/* First way */
//		return personRepository.findAll();

				/* Second way */
//		return mongoTemplate.findAll(Person.class);
		
				/* Third way */
		Query query = new Query(Criteria.where("age").is(20));
//	    query.fields().exclude("_id");
		HashMap<String, Object> person = mongoTemplate.findOne(query,HashMap.class, "person");
		return person;
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
				/* First way */
//		return personRepository.XYZ(age);

		
			/* Second way */
//		Criteria criteria = new Criteria("age").gte(age);
//	 	Query query = new Query();
//	 	query.addCriteria(criteria);
//	 	return mongoTemplate.find(query,Person.class);

		
				/* third way */
		Query query = new Query(Criteria.where("age").is(age));
// 	    query.fields().exclude("_id");
		return mongoTemplate.find(query,Person.class);
		

	}

	
}