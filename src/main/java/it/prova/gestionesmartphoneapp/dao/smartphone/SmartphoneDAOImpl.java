package it.prova.gestionesmartphoneapp.dao.smartphone;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestionesmartphoneapp.model.Smartphone;

public class SmartphoneDAOImpl implements SmartphoneDAO {

	private EntityManager entityManager;

	@Override
	public List<Smartphone> list() throws Exception {
		return entityManager.createQuery("from Cd", Smartphone.class).getResultList();
	}

	@Override
	public Smartphone get(Long id) throws Exception {
		return entityManager.find(Smartphone.class, id);
	}

	@Override
	public void update(Smartphone smartphoneInstance) throws Exception {
		if (smartphoneInstance == null) {
			throw new Exception("Problema valore in input");
		}
		smartphoneInstance = entityManager.merge(smartphoneInstance);
	}

	@Override
	public void insert(Smartphone smartphoneInstance) throws Exception {
		if (smartphoneInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(smartphoneInstance);
	}

	@Override
	public void delete(Smartphone smartphoneInstance) throws Exception {
		if (smartphoneInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(smartphoneInstance));
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
