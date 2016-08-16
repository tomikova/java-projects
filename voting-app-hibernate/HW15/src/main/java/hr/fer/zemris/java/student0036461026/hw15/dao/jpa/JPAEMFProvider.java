package hr.fer.zemris.java.student0036461026.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Factory class of entity manager.
 * @author Tomislav
 *
 */
public class JPAEMFProvider {

	/**
	 * Entity manager factory object.
	 */
	public static EntityManagerFactory emf;
	
	/**
	 * Method return entity manager factory object.
	 * @return Entity manager factory object.
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * Method sets entity manager factory object.
	 * @param emf Entity manager factory object.
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}
