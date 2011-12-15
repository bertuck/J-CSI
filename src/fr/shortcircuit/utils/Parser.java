package fr.shortcircuit.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import fr.shortcircuit.db.DbManager;
import fr.shortcircuit.gui.DesktopWindowView;
import fr.shortcircuit.gui.IDatabaseViewConstants;
import fr.shortcircuit.model.UserInterface;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Parser implements IDatabaseViewConstants {
	private Method test;

	private UserInterface Ui;
	
	private String[] list;
	
	private DbManager DB;
	
	
	@SuppressWarnings("static-access")
	public Parser(UserInterface UI, DbManager db) {
		Ui = UI;
		DB = db;
		InitList();

	}
	
	public void InitList() {
		list = new String[20];
		for (int i = 0; i < 6; i++)
			list[i] = "";
	}
	
	public void DoParse(String buffer) {		
		int length = 0;
		InitList();
		ArrayList<String> result = new ArrayList<String>();
		Pattern pattern = Pattern.compile("((?<!\\\\)\".*?(?<!\\\\)\")|([^\"\\s]+)");
		Matcher matcher = pattern.matcher(buffer);
		while (matcher.find()) {
			result.add(matcher.group());
		}
		result.trimToSize();
		for (String word : result) {
			list[length++] = word;
		}
		CallMethod();
	}
	
	public void CallMethod() {
		switch (list[0]) {
        	case "insert":    if (CheckMethodInsert()) DB.insertObjDB(list);    break;
        	case "get":    if (CheckMethodGet())  DB.getObjDB(list);       		break;
        	case "update":   if (CheckMethodUpdate()) DB.updateObjDB(list);     break;
        	case "del":    if (CheckMethodDel()) DB.deleteObjDB(list);   		break;
        	case "load":    if (CheckMethodLoadXML()) LoadXML();   		break;
        	case "help":   if (CheckMethodHelp()) DoHelp();					    break;
        	default:	   System.out.println("Command not found!!!"); 			break;
		}
	}
	
	private boolean CheckMethodInsert() {
		if (list[1] != "" && list[2] != "" && list[3] != "" && list[4] != "" && list[5] == "")
			return true;
		System.out.println("Error method : 'insert <Obj> <designation> <price>'");
		return false;
	}
	
	private boolean CheckMethodGet() {
		if (list[1] != "" && list[2] != "" && list[3] == "")
			return true;
		System.out.println("Error method : 'get <Obj> <designation>'");
		return false;
		
	}
	
	private boolean CheckMethodUpdate() {
		if (list[1] != "" && list[2] != "" && list[3] != "" && list[4] != ""  && list[5] == "")
			return true;
		System.out.println("Error method : 'update <Obj> <propriété> <value> <new value>'");
		return false;
		
	}
	
	private boolean CheckMethodDel() {
		if (list[1] != "" && list[2] != "" && list[3] == "")
			return true;
		System.out.println("Error method : 'del <Obj> <designation>'");
		return false;
	}
	
	private boolean CheckMethodLoadXML() {
		if (list[1] != "" && list[2] == "")
			return true;
		System.out.println("Error method : 'load <file>'");
		return false;
	}
	
	private boolean CheckMethodHelp() {
		if (list[1] == "")
			return true;
		System.out.println("Error method : 'help'");
		return false;
	}
	
	private void DoHelp() {
		System.out.println(" ---------------------------------------------------------------------------------------");
		System.out.println("|                                    METHODES J-CSI                                     |");
		System.out.println(" ---------------------------------------------------------------------------------------");
		System.out.println("|'insert <Obj> <designation> <price>'  => Creer un objet                                |");
		System.out.println("|'get <Obj> <designation>'             => Afficher la description d'un objet            |");
		System.out.println("|'update  <Obj> <propriété> <value> <new value>' => changer la propriété d'un objet     |");
		System.out.println("|'del <Obj> <designation>'             => suprimer un objet                             |");
		System.out.println("|'load <file>'             			   => loader un ficher xml                          |");
		System.out.println(" ---------------------------------------------------------------------------------------");
	}
	
	private void LoadXML() {
		try { 
			  File file = new File(list[1]);
			  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			  DocumentBuilder db = dbf.newDocumentBuilder();
			  Document doc = db.parse(file);
			  doc.getDocumentElement().normalize();
			  System.out.println("Root element " + doc.getDocumentElement().getNodeName());
			  this.CreateDVDXML(doc);
			  this.CreateGAMEXML(doc);
			  } catch (Exception e) {
			    e.printStackTrace();
			  }
		}
	private void CreateDVDXML(Document doc) {
		  NodeList nodeLst = doc.getElementsByTagName("dvd");
		  list[1] = "dvd";
		  System.out.println("Information of all dvd");
		  for (int s = 0; s < nodeLst.getLength(); s++) {

		    Node fstNode = nodeLst.item(s);
		    
		    if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
		  
		      Element fstElmnt = (Element) fstNode;
		      NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("id");
		      Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
		      NodeList fstNm = fstNmElmnt.getChildNodes();
		      System.out.println("Id : "  + ((Node) fstNm.item(0)).getNodeValue());
		      NodeList scdNmElmntLst = fstElmnt.getElementsByTagName("designation");
		      Element scdNmElmnt = (Element) scdNmElmntLst.item(0);
		      NodeList scdNm = scdNmElmnt.getChildNodes();
		      System.out.println("Designation : "  + ((Node) scdNm.item(0)).getNodeValue());
		      list[2] = ((Node) scdNm.item(0)).getNodeValue();
		      NodeList thrNmElmntLst = fstElmnt.getElementsByTagName("category");
		      Element thrNmElmnt = (Element) thrNmElmntLst.item(0);
		      NodeList thrNm = thrNmElmnt.getChildNodes();
		      System.out.println("Category : "  + ((Node) thrNm.item(0)).getNodeValue());
		      list[3] = ((Node) thrNm.item(0)).getNodeValue();
		      NodeList lstNmElmntLst = fstElmnt.getElementsByTagName("price");
		      Element lstNmElmnt = (Element) lstNmElmntLst.item(0);
		      NodeList lstNm = lstNmElmnt.getChildNodes();
		      System.out.println("Price : " + ((Node) lstNm.item(0)).getNodeValue());
		      list[4] = ((Node) lstNm.item(0)).getNodeValue();
		      DB.insertObjDB(list);
		    }

		  }
		}
	private void CreateGAMEXML(Document doc) {
		  NodeList nodeLst = doc.getElementsByTagName("game");
		  list[1] = "game";
		  System.out.println("Information of all games");
		  for (int s = 0; s < nodeLst.getLength(); s++) {

		    Node fstNode = nodeLst.item(s);
		    
		    if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
		  
		      Element fstElmnt = (Element) fstNode;
		      NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("id");
		      Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
		      NodeList fstNm = fstNmElmnt.getChildNodes();
		      System.out.println("Id : "  + ((Node) fstNm.item(0)).getNodeValue());
		      NodeList scdNmElmntLst = fstElmnt.getElementsByTagName("designation");
		      Element scdNmElmnt = (Element) scdNmElmntLst.item(0);
		      NodeList scdNm = scdNmElmnt.getChildNodes();
		      System.out.println("Designation : "  + ((Node) scdNm.item(0)).getNodeValue());
		      list[2] = ((Node) scdNm.item(0)).getNodeValue();
		      NodeList thrNmElmntLst = fstElmnt.getElementsByTagName("category");
		      Element thrNmElmnt = (Element) thrNmElmntLst.item(0);
		      NodeList thrNm = thrNmElmnt.getChildNodes();
		      System.out.println("Category : "  + ((Node) thrNm.item(0)).getNodeValue());
		      list[3] = ((Node) thrNm.item(0)).getNodeValue();
		      NodeList lstNmElmntLst = fstElmnt.getElementsByTagName("price");
		      Element lstNmElmnt = (Element) lstNmElmntLst.item(0);
		      NodeList lstNm = lstNmElmnt.getChildNodes();
		      System.out.println("Price : " + ((Node) lstNm.item(0)).getNodeValue());
		      list[4] = ((Node) lstNm.item(0)).getNodeValue();
		      DB.insertObjDB(list);
		    }

		  }
		}
	}
