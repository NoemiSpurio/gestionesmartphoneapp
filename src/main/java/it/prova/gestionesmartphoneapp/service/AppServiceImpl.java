package it.prova.gestionesmartphoneapp.service;

import java.util.List;

import it.prova.gestionesmartphoneapp.dao.app.AppDAO;
import it.prova.gestionesmartphoneapp.model.App;

public class AppServiceImpl implements AppService {
	
	private AppDAO appDAO;

	@Override
	public List<App> listAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public App caricaSingoloElemento(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void aggiorna(App appInstance) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void rimuovi(App appInstance) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAppDAO(AppDAO appDAO) {
		// TODO Auto-generated method stub

	}

}
