package com.craterzone.service;

import java.lang.StackWalker.Option;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.craterzone.model.Person;
import com.craterzone.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public HashMap<String, Object> create(@RequestBody HashMap<String, Object> persons) {
				/* First way */
//		return personRepository.save(persons);
		
				/* Second way */
//		return mongoTemplate.save(persons);
		
				/* Third way */
		return mongoTemplate.save(persons,"person");	
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<HashMap> getAll() {
				/* First way */
//		return personRepository.findAll();

				/* Second way */
//		return mongoTemplate.findAll(Person.class);
		
				/* Third way */
//		Query query = new Query(Criteria.where("age").lte(20));
//	    query.fields().exclude("_id");
//		HashMap<String,Object> person = mongoTemplate.findOne(query,HashMap.class, "person");
		
				/* Fourth way */
		List<HashMap> person = mongoTemplate.findAll(HashMap.class, "person");		
		return person;
	}

	public Person getByFirstName(String firstName) {
		return personRepository.findByFirstName(firstName);
	}

	public Person update(String firstName, String lastName, int age) {
				/* First way */
//		Person person = personRepository.findByFirstName(firstName);         					 // Must use Unique name
//		person.setLastName(lastName);
//		person.setAge(age);
//		return personRepository.save(person);
		
				/* Second way */
		Query query = new Query(Criteria.where("firstName").is(firstName));
 		Update update = new Update().set("lastName",lastName ).set("age", age);
		return mongoTemplate.findAndModify(query,update,Person.class);
	}

	public void deleteAll() {
		personRepository.deleteAll();
	}

	public boolean delete(String firstName) {
		Optional<Person> person = Optional.of(personRepository.findByFirstName(firstName));
		if (person.isPresent()) {
					/* First way */
//			personRepository.delete(person.get());
					/* Second way */
			mongoTemplate.remove(person.get(),"person");
			return true;
		} else
			return false;

	}

	public List<Person> getAge(int age) {
				/* First way */
//		return personRepository.XYZ(age);

		
			/* Second way */
		Criteria criteria1 = new Criteria("age").is(age).and("firstName").is("Mohd Arsalan");
		Criteria criteria2 = new Criteria().where("age").is(age).and("firstName").is("Devanshu");       // Here "where" is Static member of Criteria class.
		Criteria criteria3 = new Criteria().orOperator(criteria1,criteria2);
		Criteria criteria4 = new Criteria("age").lte(18);
		Criteria criteria5 = new Criteria().andOperator(criteria3,criteria4);
		
	 	Query query = new Query();
	 	query.addCriteria(criteria5);
	 	return mongoTemplate.find(query,Person.class);

		
				/* third way */
//		Query query = new Query(Criteria.where("age").is(age));
// 	    query.fields().exclude("_id");
//		return mongoTemplate.find(query,Person.class);
		
				/*  fourth way */
//		Query query = new Query(Criteria.where("age").gte(age).and("firstName").is("Mohd Arsalan"));
// 		query.fields().exclude("_id");
//		return mongoTemplate.find(query,Person.class);

	}

	
}