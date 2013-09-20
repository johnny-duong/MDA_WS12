package org.dieschnittstelle.mobile.android.todolist.model;

public class User {

	private String name;
	private String surname;
	private String email;
	private String password;

	public User() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPersonalData() {
		StringBuilder sb = new StringBuilder();

		name = this.getName();
		surname = this.getSurname();
		email = this.getEmail();
		password = this.getPassword();

		sb.append("Userdata: \n" + "Name: " + name + "\n" + "Surname: "
				+ surname + "\n" + "Email: " + email + "\n" + "Password: "
				+ password);

		return sb.toString();
	}

}
