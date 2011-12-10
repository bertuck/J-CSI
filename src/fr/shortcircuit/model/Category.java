package fr.shortcircuit.model;

public class Category
{
	private String name;

	
	public Category()	{}

	public Category(String name)
	{
		setName(name);
	}
	
	//another alternative to construct the returned String
	public String toString()				{return "Category instance, name= " + name;}

	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//Getters & Setters
	//////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getName()					{return name;}
	
	public void setName(String name)		{this.name = name;}


}
