package fr.shortcircuit.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import fr.shortcircuit.utils.DateHelper;

/**
 * A Simple order entity providing:<BR> 
 * 
 * - java.util.Date properties, for usual Order business tracking<BR> 
 * - One reference to a Client instance (multiplicity= 1 to 1)<BR>
 * - A list of OrderLine instances (multiplicity= 1 to n)<BR>
 * - A Reference to EOrderState which symbolizes the determined states the Order is moving through, during its LifeCycle.<BR>
 * (Check EOrderState enum for more informations).
 * 
 * <P>
 * 
 * Please not: if you have a look at the OrderLine class, 
 * you'll see the relation between those two entities is uni-directionnal,
 * the OrderLine structure doesn't reference its Order, 
 * because in our "business case", 
 * we basically consider that an OrderLine is never manipulated on its own, 
 * but always through its inherent Order referencing class.<BR>
 * 
 * ->Order is a root node, on an object tree, and 
 * OrderLine is hereby considered as its sibling. 
 * 
 * @author Dim
 */
public class Order
{
	/** The client related to the Order */
	private Client 					client;
	
	/** The object is created, in order to manipulate a not-null reference */
	private List<OrderLine> 		lines 		= new ArrayList<OrderLine>();
		
	/** Dates to track the order evolution */
	private Date					dateOrderStarted;
	private Date					dateOrderEnded;
	
	
	/** Class simple constructor (mandatory) */
	public Order()
	{
	
	}

	/** This method is a "derivated property", 
	 * meaning it is a member not related to a Field, 
	 * but to an internal use of one, or many, fields. */
	public float getTotal()
	{
		float total = 0;
		
		for (Iterator<OrderLine> i = lines.iterator(); i.hasNext(); )
		{
			OrderLine ol = i.next(); 
			
			total += ol.getQuantity() * ol.getProduct().getPrice();
		}
	
		return total;
	}

	/**
	 * This method OVERRIDES the java.lang.Object toString() method: 
	 * because the original one is only giving in return an instance className + its @ddress in the VM memory,
	 * which is not really "log-friendly" :) we re-defined its functionnal behaviour, with our own implementation.<P>
	 * 
	 * This method is using the DateHelper class to format the stored dates.<P>
	 * 
	 * Please Note: this method is chaining the appending of an Order String declaration, 
	 * by calling its references member (Client, List<OrderLines>) similar toString() overriding.
	 * This "chaining" is (very simply) illustrating the "Composite" design pattern, 
	 * which is structuring a Java classes Tree.
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		sb.append("Order: \r\n\t"
				+ ((dateOrderStarted != null)? 	"dateOrderStarted=" + DateHelper.getStringDate(dateOrderStarted)	: ", dateOrderStarted = null") 
				+ ((dateOrderEnded != null)? 	"dateOrderEnded=" 	+ DateHelper.getStringDate(dateOrderEnded)	: ", dateOrderEnded = null"));

		sb.append("\r\n");

		//Ternary operator use
		sb.append("\r\n\tClient: " + ((client != null)? client.toString() : "null"));

		sb.append("\r\n");

		//Please Note: the simplified grammatical definition of a "for"
		for (OrderLine i : lines)
			sb.append("\r\n\t" + i.toString());

		return sb.toString();
	}
		
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//List common handling
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void addLine(OrderLine l)						{lines.add(l);}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//Getters & Setters
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Client getClient()								{return client;}
	public List<OrderLine> getLines()						{return lines;}
	public Date getDateOrderStarted()						{return dateOrderStarted;}
	public Date getDateOrderEnded()							{return dateOrderEnded;}

	public void setClient(Client client)					{this.client 			= client;}
	public void setLines(List<OrderLine> lines)				{this.lines 			= lines;}
	public void setDateOrderStarted(Date dateOrderStarted)	{this.dateOrderStarted 	= dateOrderStarted;}
	public void setDateOrderEnded(Date dateOrderEnded)		{this.dateOrderEnded 	= dateOrderEnded;}
}
