package com.howtodoinjava.demo.model;

public class Employee {

	public Employee() {
		
	}
	public Employee(String e) {
		super();
		this.id = e.substring(0,e.indexOf(","));
		this.firstName = e.substring(e.indexOf(",")+1, e.indexOf("1"));
		this.lastName = e.substring(e.indexOf("1")+1, e.indexOf("2"));
		this.email = e.substring(e.indexOf("2")+1);
		
	}

	private String id;
	private String firstName;
	private String lastName;
	private String email;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	/*@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + "]";
	}*/
}
