package fr.shortcircuit.model;

public class Address
{
	private String 		streetNumber;
	private String 		streetName;
	private String 		city;
	private String 		zipCode;
	private String 		country;
	
	
	public Address()
	{
		
	}

	public String toString()
	{
		return "Address instance, streetNumber=" + streetNumber + ", streetName=" + streetName + ", city=" + city + ", zipCode=" + zipCode + ", country=" + country;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//Getters & Setters
	//////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getStreetNumber()						{return streetNumber;}
	public String getStreetName()						{return streetName;}
	public String getCity()								{return city;}
	public String getZipCode()							{return zipCode;}
	public String getCountry()							{return country;}

	public void setStreetNumber(String streetNumber)	{this.streetNumber 	= streetNumber;}
	public void setStreetName(String streetName)		{this.streetName 	= streetName;}
	public void setCity(String city)					{this.city 			= city;}
	public void setZipCode(String zipCode)				{this.zipCode 		= zipCode;}
	public void setCountry(String country)				{this.country		= country;}
		
}
