package jcsi.dao;
import fr.shortcircuit.db.DbManager;
import fr.shortcircuit.model.Game;

public class DAOGame extends DAO<Game> {
	
	DbManager DB;
	int id = 0;
	
 public DAOGame(DbManager db, String[] tab) {
	super(db, tab);
	DB = db;
	this.tab = tab;
 }
 
@Override
 public boolean create(Game obj) {
	try {
		DB.InitDb();
		int Row		= DB.myState.executeUpdate("INSERT INTO "+ tab[1] + " VALUES ("+ (id++) +", '"+ tab[2] + "', '" + tab[3] + "', '" + tab[4] +"')");
		System.out.println(tab[2] + " is created");
		//this.DB.refreshGUI();
	}
	catch (java.sql.SQLException e) {System.out.println("dbDisconnect: close statement: " + e.toString());}
	return false;
 }

@Override
public boolean delete(Game obj) {
	try {
		int nbrRow		= DB.myState.executeUpdate("delete from " + tab[1] + " where id='" + tab[2] + "'");
		System.out.println("ID " + tab[2] + " is deleted");
		this.DB.refreshGUI();
		return true;
	}
	catch (java.sql.SQLException e) {System.out.println("Designation" + tab[2] + " doesn't exist");}
	return false;
}

@Override
 public boolean update(Game obj) {
	 try {
		 int nbrRow		= DB.myState.executeUpdate("update " + tab[1] + " set " + tab[2] + "="+tab[4] + " where " +  tab[2] +"="+ tab[3]);
		 System.out.println(tab[2] + " is updated");
		 this.DB.refreshGUI();
	 }
	 catch (java.sql.SQLException e)	{System.out.println("Designation " + tab[2] + " doesn't exist");}
	return false;
 }

@Override
 public Game find(int id) {
	try {
		DB.InitDb();
		int nbrColumn				= DB.myResultSetMetaData.getColumnCount();
		this.DB.myResultSet		= DB.myState.executeQuery("select * from " + tab[1] + " where designation='" + tab[2] + "'");
		this.DB.reflectElement("Dvd", DB.myResultSet);
		DB.myResultSet.next();
		System.out.println(tab[2] + " exist");
		System.out.println("ID: " + DB.myResultSet.getString(1));
		System.out.println("Designation: " + DB.myResultSet.getString(2));
		System.out.println("Category: " + DB.myResultSet.getString(3));
		System.out.println("Price: " + DB.myResultSet.getString(4));
		return null;
	}
	catch (java.sql.SQLException e)	{System.out.println("Designation " + tab[2] + " doesn't exist");}
	return null;
 }

}
