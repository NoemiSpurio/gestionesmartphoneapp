package it.prova.gestionesmartphoneapp.dao.smartphone;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestionesmartphoneapp.model.App;
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

	@Override
	public Smartphone findByIdFetchingApps(Long id) {
		TypedQuery<Smartphone> query = entityManager
				.createQuery("select s FROM Smartphone s left join fetch s.apps a where s.id = ?1", Smartphone.class);
		query.setParameter(1, id);
		return query.getResultList().stream().findFirst().orElse(null);
	}

}
