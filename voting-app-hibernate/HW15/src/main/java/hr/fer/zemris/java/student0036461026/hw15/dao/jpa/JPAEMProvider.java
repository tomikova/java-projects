package hr.fer.zemris.java.student0036461026.hw15.dao.jpa;

import hr.fer.zemris.java.student0036461026.hw15.dao.DAOException;

import javax.persistence.EntityManager;

/**
 * Storage of entity manager in ThreadLocal object that is map which 
 * contains thread indentifier as key of thread that will use map.
 * @author Tomislav
 *
 */
public class JPAEMProvider {

	/**
	 * Map with thread identifiers as key.
	 */
	private static ThreadLocal<LocalData> locals = new ThreadLocal<>();

	/**
	 * Method returns entity manager.
	 * @return Entity manager.
	 */
	public static EntityManager getEntityManager() {
		LocalData ldata = locals.get();
		if(ldata==null) {
			ldata = new LocalData();
			ldata.em = JPAEMFProvider.getEmf().createEntityManager();
			ldata.em.getTransaction().begin();
			locals.set(ldata);
		}
		return ldata.em;
	}

	/**
	 * Method commits last transaction and closes entity manager.
	 * @throws DAOException If error occurs while working with entity manager.
	 */
	public static void close() throws DAOException {
		LocalData ldata = locals.get();
		if(ldata==null) {
			return;
		}
		DAOException dex = null;
		try {
			ldata.em.getTransaction().commit();
		} catch(Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			ldata.em.close();
		} catch(Exception ex) {
			if(dex!=null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if(dex!=null) throw dex;
	}
	
	/**
	 * Class is holder of used entity manager.
	 * @author Tomislav
	 *
	 */
	private static class LocalData {
		/**
		 * Entity manager.
		 */
		EntityManager em;
	}
	
}
