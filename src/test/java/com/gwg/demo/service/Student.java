package com.gwg.demo.service;

import java.io.Serializable;

public class Student implements Serializable{
	
	private String name;
	
	public Student(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
