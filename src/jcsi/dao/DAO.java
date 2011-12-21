package jcsi.dao;

import fr.shortcircuit.db.DbManager;


public abstract class DAO<T> {
        
        protected DbManager DB = null;
        
        protected String[] tab;
        
        /**
         * Constructeur
         * @param conn
         */
        public DAO(DbManager db, String[] tab){
                this.DB = db;
                this.tab = tab;
        }
        
        /**
         * M�thode de cr�ation
         * @param obj
         * @return
         */
        public abstract boolean create(T obj);
        /**
         * M�thode pour effacer
         * @param obj
         * @return
         */
        public abstract boolean delete(T obj);
        /**
         * M�thode de mise � jour
         * @param obj
         * @return
         */
        public abstract boolean update(T obj);
        /**
         * M�thode de recherche des informations
         * @param id
         * @return
         */
        public abstract T find(int id);
}