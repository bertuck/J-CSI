package fr.shortcircuit.model;

/**
 * A Client structure definition, with two addresses, one for billing, one for delivery (references can obviously be equals :)
 * 
 * 
 * @author Dim
 */
public class Client
{
	private Address		billingAddress;
	private Address		deliveryAddress;
	
	private String		firstName;
	private String		middleName;
	private String		lastName;

	
	
	public Client()
	{
		
	}

	//usual toString overRiding
	public String toString()
	{
		return "Client instance: firstName=" 	+ firstName + ", middleName=" + middleName + ", lastName=" + lastName 
												+ "\r\n\t\tbillingAddress: " 	+ ((billingAddress != null)? 	billingAddress.toString() 	: "null") 
		 										+ "\r\n\t\tdeliveryAddress: " 	+ ((deliveryAddress != null)? 	deliveryAddress.toString() : "null");
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//Getters & Setters
	//////////////////////////////////////////////////////////////////////////////////////////////////////

	public Address getBillingAddress()						{return billingAddress;}
	public Address getDeliveryAddress()						{return deliveryAddress;}
	public String getFirstName()							{return firstName;}
	public String getMiddleName()							{return middleName;}
	public String getLastName()								{return lastName;}

	public void setBillingAddress(Address billingAddress)	{this.billingAddress 	= billingAddress;}
	public void setDeliveryAddress(Address deliveryAddress)	{this.deliveryAddress 	= deliveryAddress;}
	public void setFirstName(String firstName)				{this.firstName 		= firstName;}
	public void setMiddleName(String middleName)			{this.middleName 		= middleName;}
	public void setLastName(String lastName)				{this.lastName 			= lastName;}

}
