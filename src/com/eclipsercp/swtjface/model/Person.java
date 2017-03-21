package com.eclipsercp.swtjface.model;

/**
 * A class to save the data of each person.
 */
public class Person {

	private String name;
	private Integer group;
	private Boolean swtDone;

	/**
	 * Constructs a new person using its name.
	 * 
	 * @param name
	 *            the name of the new person
	 */
	public Person(String name) {
		this.name = name;
	}

	/**
	 * Constructs a new person using its name, group and "SWT Done" parameter.
	 * 
	 * @param name
	 *            the name of the new person
	 * @param group
	 *            the group of the new person
	 * @param swtDone
	 *            the "SWT Done" parameter of the new person
	 */
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
