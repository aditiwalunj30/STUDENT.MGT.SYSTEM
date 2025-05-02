package com.tca.entities;

public class Teacher {
	private int tsi;
	private String name;
	private String designation;
	
	public Teacher() {
		
	}
	
	public Teacher(int tsi, String name , String designation) {
		super();
		this.tsi = tsi;
		this.name = name;
		this.designation = designation;
	}
	public int gettsi() {
		return tsi;
	}
	public void settsi(int tsi) {
		this.tsi = tsi;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getdesignation() {
		return designation;
	}
	public void setdesignation(String designation) {
		this.designation = designation;
	}
}
	
