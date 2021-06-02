package com.craterzone.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Data
@Document(collection="person")
public class Person {
	@Id
	String _id;
	String firstName;
	String lastName;
	int age;

}