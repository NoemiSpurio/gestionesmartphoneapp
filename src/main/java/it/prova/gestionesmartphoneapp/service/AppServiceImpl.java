package it.prova.gestionesmartphoneapp.service;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestionesmartphoneapp.dao.EntityManagerUtil;
import it.prova.gestionesmartphoneapp.dao.app.AppDAO;
import it.prova.gestionesmartphoneapp.model.App;

public class AppServiceImpl implements AppService {
	
	private AppDAO appDAO;

	@Override
	public List<App> listAll() throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			appDAO.setEntityManager(entityManager);

			return appDAO.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public App caricaSingoloElemento(Long id) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			appDAO.setEntityManager(entityManager);

			return appDAO.get(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiorna(App appInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();

			appDAO.setEntityManager(entityManager);

			appDAO.update(appInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void rimuovi(App appInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();

			appDAO.setEntityManager(entityManager);

			appDAO.delete(appInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void setAppDAO(AppDAO appDAO) {
		this.appDAO = appDAO;
	}

	@Override
	public void inserisciNuovo(App appInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();

			appDAO.setEntityManager(entityManager);

			appDAO.insert(appInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

}
