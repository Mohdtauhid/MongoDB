package com.craterzone.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.craterzone.model.Person;

@Repository
public interface PersonRepository extends MongoRepository<Person, String> {
	public Person findByFirstName(String firstName);

	@Query(value = "{age :?0}", fields = "{lastName : 0}")
	public List<Person> XYZ(int age);

	// public List<Person> findByAge(int age);
}