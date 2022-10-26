package it.prova.gestionesmartphoneapp.service;

import java.util.List;

import it.prova.gestionesmartphoneapp.dao.smartphone.SmartphoneDAO;
import it.prova.gestionesmartphoneapp.model.Smartphone;

public interface SmartphoneService {

	public List<Smartphone> listAll() throws Exception;
	
	public Smartphone caricaSingoloElemento(Long id) throws Exception;
	
	public void update(Smartphone smartphoneInstance) throws Exception;
	
	public void inserisciNuovo(Smartphone smartphoneInstance) throws Exception;
	
	public void rimuovi(Smartphone smartphoneInstance) throws Exception;
	
	public void setSmartphoneDAO(SmartphoneDAO smartphoneDAO);
}
