package main;

public abstract class Vehicle {

	protected String naam;
	protected String kenteken;
	protected Customer customer;

	public Vehicle(String naam, String kenteken, Customer customer){
	this.naam = naam;
	this.kenteken = kenteken;
	this.customer = customer;
	}

	public abstract String getNaam();
	public abstract void setNaam(String naam);
	public abstract String getKenteken();
	public abstract void setKenteken(String kenteken);
	public abstract Customer getCustomers();
	public abstract void setCustomer(Customer customers);
	@Override
	public abstract String toString();
	}

