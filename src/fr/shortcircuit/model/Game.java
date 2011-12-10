package fr.shortcircuit.model;



public class Game extends AbstractProduct
{	
	//furthermore members (fields, methods) to be added...
	
	/** Constructor overloading:  */
	public Game(String id, String designation, Category category, int price)
	{
		super(id, designation, category, price);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Business logic: "behaviour" defined by the IBonusProducer business interface 
	////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int getBonusPoint()
	{
		return 20;	
	}

	
}