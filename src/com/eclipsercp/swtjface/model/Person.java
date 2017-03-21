package com.eclipsercp.swtjface.model;

public class Person {

	private String name;
	private Integer group;
	private Boolean swtDone;

	public Person(String name) {
		this.name = name;
	}	

	public Person(String name, Integer group, Boolean swtDone) {
		this(name);
		this.group = group;
		this.swtDone = swtDone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public Boolean isSwtDone() {
		return swtDone;
	}

	public void setSwtDone(boolean swtDone) {
		this.swtDone = swtDone;
	}

}
