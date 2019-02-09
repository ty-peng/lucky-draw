package com.pty.model;

import java.io.Serializable;

/**
 * 抽奖对象类
 * 
 * @author pty
 *
 */
@SuppressWarnings("serial")
public class Person implements Serializable, Cloneable {

	private String personNum;
	private String name;
	private String phone;

	public Person() {
		this.personNum="000000000000";
		this.name="默认";
		this.phone="13700000000";
	}
	
	public Person(String personNum, String name, String phone) {
		super();
		this.personNum = personNum;
		this.name = name;
		this.phone = phone;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {

		Person person = new Person(this.personNum, this.name, this.phone);
		return person;
	}

	@Override
	public String toString() {
		return personNum + name + phone;
	}

	public String getPersonNum() {
		return personNum;
	}

	public void setPersonNum(String personNum) {
		this.personNum = personNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
