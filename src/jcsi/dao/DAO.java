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
         * Méthode de création
         * @param obj
         * @return
         */
        public abstract boolean create(T obj);
        /**
         * Méthode pour effacer
         * @param obj
         * @return
         */
        public abstract boolean delete(T obj);
        /**
         * Méthode de mise à jour
         * @param obj
         * @return
         */
        public abstract boolean update(T obj);
        /**
         * Méthode de recherche des informations
         * @param id
         * @return
         */
        public abstract T find(int id);
}