package jcsi.dao;

import fr.shortcircuit.db.DbManager;

public class DAOFactory {
	//protected static final DbManager DB = new DbManager();
	private static final DbManager DB = new DbManager();
	
    /**
     * Retourne un objet Classe interagissant avec la BDD
     * @return
     */
    public static DAO getDAOGame(String[] tab){
            return new DAOGame(DB, tab);
    }
    /**
     * Retourne un objet Professeur interagissant avec la BDD
     * @return
     */
    /*public static IDAO getProfesseurDAO(){
            return new DAOGame(DB);
    }*/
    /**
     * Retourne un objet Eleve interagissant avec la BDD
     * @return
     */
    /*public static IDAO getEleveDAO(){
            return new EleveDAO(conn);
    }*/
    /**
     * Retourne un objet Matiere interagissant avec la BDD
     * @return
     */
    /*public static IDAO getMatiereDAO(){
            return new MatiereDAO(conn);
    } */      

}
