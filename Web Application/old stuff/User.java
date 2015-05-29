package model;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	private String email,password,firstName,surName,address,country;
	private UserRole userRole;
	public User(UserRole userRole,String email, String password, String firstName,
			String surName, String address, String country) {
		this.userRole = userRole;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.surName = surName;
		this.address = address;
		this.country = country;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

    @Override
    public String toString() {
        return "User{" +
                "surName='" + surName + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
