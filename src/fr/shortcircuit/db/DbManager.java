package fr.shortcircuit.db;


import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.shortcircuit.gui.DesktopWindowView;
import fr.shortcircuit.model.AbstractProduct;
import fr.shortcircuit.model.Dvd;


public class DbManager
{
	//Objects spŽcifiques aux differents appels vers les bases (locales ou distantes).	
	public Connection			myConnect;	
	public Statement			myState;
	public ResultSet			myResultSet;

	//Objects de Meta-Information sur la Database connectŽe, et sur la requ�te effectuŽe.
	public DatabaseMetaData		myDbMetaData;
	public ResultSetMetaData	myResultSetMetaData;
	
	public PreparedStatement 	myPreparedStatement;

	public String 				arrayContent[][];
	public String 				arrayHeader[];
	public String 				strConnectURL;
	
	private Object currentObject;
	private static int id = 0;


	public DbManager()	{}

	//////////////////
	//SQL
	/////////////////
	
	public void dbConnect()
	{
		try
		{	
			//1ere etape: Chargement de la classe de driver, responsable - par contrat d'interfaces - de la connection vers le SGBD
			//Il existe 4 types de driver (I, II, III, IV): 2 locaux, et 2 remote. More infos: http://java.sun.com/jdbc/drivers.html
 			//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Class.forName("com.mysql.jdbc.Driver");
			
 			InitDb();
			myResultSet					= myState.executeQuery("Select * from dvd;");
			refreshGUI();
		}
		catch (ClassNotFoundException e) 	{System.out.println("dbConnect ClassNotFoundException: " + e.toString()); e.printStackTrace();}	
		catch (SQLException e) 				{System.out.println("dbConnect SQLException: " + e.toString()); e.printStackTrace();}	
		catch (Exception e) 					{System.out.println("dbConnect Exception: " + e.toString()); 	e.printStackTrace();}	
	}
	
	public void InitDb() {
		try {
		strConnectURL				= "jdbc:mysql://localhost:3306/jcsi";
			//ex d'URL permettant le connection distante: "jdbc:JDataConnect://www.domain.com/db-dsn"; 
			
			//3eme etape: Creation de l'object de connection
		
		myConnect 					= DriverManager.getConnection(strConnectURL, "root", "");
	//login & password
		myDbMetaData 				= myConnect.getMetaData();

		//System.out.println("DbManager: dbConnect: show DataBase MetaData:");
		//System.out.println("DbManager: dbConnect: productName=" 	+ myDbMetaData.getDatabaseProductName());
		//System.out.println("DbManager: dbConnect: productVersion=" 	+ myDbMetaData.getDatabaseProductVersion());
		//etc... de nombreuses autres info sont disponibles
		
		//4eme etape: creation d'une instruction/formule, socle pour executer des requetes
		myState						= myConnect.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public AbstractProduct productFactory(String id, String designation, String category, int price)
	{
		try
		{
		id = myResultSet.getString(1);
		designation	= myResultSet.getString(2);
		category = myResultSet.getString(3);
		price = myResultSet.getInt(4);
		}
		catch (java.sql.SQLException e)	{System.out.println("dbDisconnect: close statement: " + e.toString());}
		AbstractProduct myProduct = new Dvd(id, designation, category, price);
		return myProduct;
	}
	
	public void refreshGUI()
	{
		try 
		{
			myResultSetMetaData			= myResultSet.getMetaData();

			//System.out.println("\r\nDbManager: dbConnect: show Query MetaData:");
			
			int nbrColumn				= myResultSetMetaData.getColumnCount();
			List<String[]>	list		= new ArrayList<String[]>();
			arrayHeader					= new String[nbrColumn];
        
			//la premi�re colonne porte l'index 1, ET NON 0 !!!
			for (int i = 0; i != nbrColumn; i++) 
			{
				arrayHeader[i]	= myResultSetMetaData.getColumnName(i + 1);
				
				//System.out.println("DbManager: dbConnect: MetaInfo: columnName=" + myResultSetMetaData.getColumnName(i + 1) + ", columnType=" + myResultSetMetaData.getColumnTypeName(i + 1));
				//etc... de nombreuses autres info sont disponibles
			}

			//System.out.println("\r\nDbManager: dbConnect: show Query Data:");
			
			//6eme etape: parcours du resultSet et de ses donnŽes.
			//la premi�re colonne porte l'index 1, ET NON 0 !!!
			while (myResultSet.next()) //incremente aussi l'index pour la lecture des donnŽes
			{
				String[] content 		= new String[nbrColumn];
				content[0]				= myResultSet.getString(1);         
				content[1]				= myResultSet.getString(2);
				content[2]				= myResultSet.getString(3);         
				content[3]				= myResultSet.getString(4);      
				list.add(content);
				
				/*System.out.println("DbManager: dbConnect: resultSet 1st column=" 	+ content[0]); 
				System.out.println("DbManager: dbConnect: resultSet 2nd column=" 	+ content[1] );
				System.out.println("DbManager: dbConnect: resultSet 3st column=" 	+ content[2]); 
				System.out.println("DbManager: dbConnect: resultSet 4st column=" 	+ content[3]+ "\r\n"); */
			}
			
			//instanciation du String[][]
			arrayContent				= new String[list.size()][nbrColumn];
			int index					= 0;
		
			for (String[] content : list)
				arrayContent[index++] = content;
			
		}
		catch (java.sql.SQLException e)	{;}
	}
	
	public void getObjDB(String[] tab)
	{
		try 
		{
			InitDb();
			int nbrColumn				= myResultSetMetaData.getColumnCount();
			this.myResultSet		= myState.executeQuery("select * from " + tab[1] + " where designation='" + tab[2] + "'");
			this.reflectElement("Dvd", this.myResultSet);
			myResultSet.next();
			System.out.println(tab[2] + " exist");
			System.out.println("ID: " + myResultSet.getString(1));
			System.out.println("Designation: " + myResultSet.getString(2));
			System.out.println("Category: " + myResultSet.getString(3));
			System.out.println("Price: " + myResultSet.getString(4));
		}
		catch (java.sql.SQLException e)	{System.out.println("Designation " + tab[2] + " doesn't exist");}
		
	}
	
	public void updateObjDB(String[] tab)
	{
		try 
		{
			int nbrRow		= myState.executeUpdate("update " + tab[1] + " set " + tab[2] + "="+tab[4] + " where " +  tab[2] +"="+ tab[3]);
			System.out.println(tab[2] + " is updated");
			this.refreshGUI();
		}
		catch (java.sql.SQLException e)	{System.out.println("Designation " + tab[2] + " doesn't exist");}
	}
	
	public void deleteObjDB(String[] tab)
	{
		try
		{
			int nbrRow		= myState.executeUpdate("delete from " + tab[1] + " where id='" + tab[2] + "'");
			System.out.println("ID " + tab[2] + " is deleted");
			this.refreshGUI();
		}
		catch (java.sql.SQLException e) {System.out.println("Designation" + tab[2] + " doesn't exist");}
	}
	
	public void insertObjDB(String[] tab)
	{
		try
		{
			InitDb();
			int Row		= myState.executeUpdate("INSERT INTO "+ tab[1] + " VALUES ("+ (id++) +", '"+ tab[2] + "', '" + tab[3] + "', '" + tab[4] +"')");
			System.out.println(tab[2] + " is created");
			this.refreshGUI();
		}
		catch (java.sql.SQLException e) {System.out.println("dbDisconnect: close statement: " + e.toString());}
	}

	//////////////////
	//MISC
	/////////////////
	
	public static String escapeQuote(String strIn)
	{	
		if (strIn == null)
			return "";
		
		String strOut		= "";
		int strLength		= strIn.length();
				
		for (int i = 0; i != strLength; i++)
			strOut			+= (strIn.substring(i, i + 1).equalsIgnoreCase("'"))? "''" : strIn.substring(i, i + 1);
		
		return strOut;
	}
	@SuppressWarnings("unchecked")
	public void reflectElement(String className, ResultSet rs)
	{
		//System.out.println("reflectElement: " + className);
		
		try
		{	
			Class associatedClass								= Class.forName("fr.shortcircuit.model." + className);
			Class tabParameterTypes[]							= {String.class};
			
			//permet de recuperer un constructeur specifique
			//Constructors tabAssociatedConstructors			= associatedClass.getDeclaredConstructors(tabParameterTypes);
			
			//Object newInstance									= associatedClass.newInstance();
			//currentObject										= newInstance;
			ResultSetMetaData rslt = rs.getMetaData();
			for (int i = 1; i !=  rslt.getColumnCount(); i++)
			{
				int index = i+1;
				int typeSQL = rslt.getColumnType(index); 
				String nomTypeSQL = rslt.getColumnTypeName(index); 
				String typeJava = rslt.getColumnClassName(index);
				System.out.println("Type SQL dans java.sql.Types : "+typeSQL);
				System.out.println("Nom du type SQL : "+nomTypeSQL);
				System.out.println("Classe java correspondante : "+typeJava);
				String currentAttributeName		= rslt.getColumnName(i);
				//String currentAttributeValue	= rs.getString(currentAttributeName);
				System.out.println("Column Name = " + rslt.getColumnName(i));
				//System.out.println("Column Value = " + rs.getString(currentAttributeName));
				
				//String currentMethodName						= "set" + currentAttributeName.substring(0, 1).toUpperCase() + currentAttributeName.substring(1);
				//Object tabParameterValues[]						= {currentAttributeValue};
				
				//@SuppressWarnings("unchecked")
				//Method associatedSetMethod						= associatedClass.getMethod(currentMethodName, tabParameterTypes);
				
				//associatedSetMethod.invoke(newInstance, tabParameterValues);
			}
			
			//System.out.println("collectionStorer: " + collectionStorer.getClass().getName());
		}
		catch (Exception e) {System.out.println("reflectElement: " + e.toString()); e.printStackTrace();}
	}

}