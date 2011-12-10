package fr.shortcircuit.db;


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
			
 			//2eme etape: Definition de l'URL de connection
 			//strConnectURL				= "jdbc:odbc:java";
			strConnectURL				= "jdbc:mysql://localhost:3306/jcsi";
 			//ex d'URL permettant le connection distante: "jdbc:JDataConnect://www.domain.com/db-dsn"; 
 			
 			//3eme etape: Creation de l'object de connection
			myConnect 					= DriverManager.getConnection(strConnectURL, "root", "");//login & password
		
			//Option: Acces a un jeu de meta information sur la base avec laquelle on dialogue.
			myDbMetaData 				= myConnect.getMetaData();

			System.out.println("DbManager: dbConnect: show DataBase MetaData:");
			System.out.println("DbManager: dbConnect: productName=" 	+ myDbMetaData.getDatabaseProductName());
			System.out.println("DbManager: dbConnect: productVersion=" 	+ myDbMetaData.getDatabaseProductVersion());
			//etc... de nombreuses autres info sont disponibles
			
			//4eme etape: creation d'une instruction/formule, socle pour executer des requetes
			myState						= myConnect.createStatement();

			//5eme etape: invocation d'une requ�te (soit une selection stockee dans un ResultSet, soit un update/delete/insert renvoyant le nbr de ligne modifiee(s)).
			myResultSet					= myState.executeQuery("Select * from client;");
			//int nbrRow				= myState.executeUpdate("Delete from personne where id=2");

			//Stockage de l'historique des requetes dans un fichier, histoire d'avoir un backup
			//IoManager.writeFile("Select * from personne;", "queryPerformer.sql", false); 

			//Option: Acces a un jeu de meta information sur la base avec laquelle on dialogue.
			myResultSetMetaData			= myResultSet.getMetaData();

			System.out.println("\r\nDbManager: dbConnect: show Query MetaData:");
			
			int nbrColumn				= myResultSetMetaData.getColumnCount();
			List<String[]>	list		= new ArrayList<String[]>();
			arrayHeader					= new String[nbrColumn];
        
			//la premi�re colonne porte l'index 1, ET NON 0 !!!
			for (int i = 0; i != nbrColumn; i++) 
			{
				arrayHeader[i]	= myResultSetMetaData.getColumnName(i + 1);
				
				System.out.println("DbManager: dbConnect: MetaInfo: columnName=" + myResultSetMetaData.getColumnName(i + 1) + ", columnType=" + myResultSetMetaData.getColumnTypeName(i + 1));
				//etc... de nombreuses autres info sont disponibles
			}

			System.out.println("\r\nDbManager: dbConnect: show Query Data:");
			
			//6eme etape: parcours du resultSet et de ses donnŽes.
			//la premi�re colonne porte l'index 1, ET NON 0 !!!
			while (myResultSet.next()) //incremente aussi l'index pour la lecture des donnŽes
			{
				String[] content 		= new String[nbrColumn];
				content[0]				= myResultSet.getString(1);         
				content[1]				= myResultSet.getString(2);         

				list.add(content);
				
				System.out.println("DbManager: dbConnect: resultSet 1st column=" 	+ content[0]); 
				System.out.println("DbManager: dbConnect: resultSet 2nd column=" 	+ content[1] + "\r\n"); 
				
				//l'object ResultSet peut invoker bon nombre de getters: 
				//getShort, getDouble, getInt, getByte, getBoolean, getBigDecimal, getBinaryStream, getAsciiStream, 
				//getDate, getFloat, getBlob, getClob...						
			}
			
			//instanciation du String[][]
			arrayContent				= new String[list.size()][nbrColumn];
			int index					= 0;
		
			for (String[] content : list)
				arrayContent[index++] = content; 	
			
			//Exemple de prepared Statement
			myPreparedStatement 		= myConnect.prepareStatement("Insert into table values(?, ?"); 			
			
			//myPreparedStatement.setInt(1, 3);
			//myPreparedStatement.setString(2, 'myStrValue');
			
			//myPreparedStatement.executeUpdate();
			//myPreparedStatement.executeQuery();	
		}
		catch (ClassNotFoundException e) 	{System.out.println("dbConnect ClassNotFoundException: " + e.toString()); e.printStackTrace();}	
		catch (SQLException e) 				{System.out.println("dbConnect SQLException: " + e.toString()); e.printStackTrace();}	
		catch (Exception e) 					{System.out.println("dbConnect Exception: " + e.toString()); 	e.printStackTrace();}	
		finally
		{
			try {myState.close();}
			catch (java.sql.SQLException e)	{System.out.println("dbDisconnect: close statement: " + e.toString());}
			catch (Exception e)	{System.out.println("dbDisconnect: close statement: " + e.toString());}		
			
			try {myConnect.close();}
			catch (java.sql.SQLException e)	{System.out.println("dbDisconnect: close statement: " + e.toString());}
			catch (Exception e)	{System.out.println("dbDisconnect: close connection: " + e.toString());}		
		}
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

}