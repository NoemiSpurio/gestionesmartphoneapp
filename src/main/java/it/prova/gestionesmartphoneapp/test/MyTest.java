package it.prova.gestionesmartphoneapp.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;

import it.prova.gestionesmartphoneapp.dao.EntityManagerUtil;
import it.prova.gestionesmartphoneapp.model.App;
import it.prova.gestionesmartphoneapp.model.Smartphone;
import it.prova.gestionesmartphoneapp.service.AppService;
import it.prova.gestionesmartphoneapp.service.MyServiceFactory;
import it.prova.gestionesmartphoneapp.service.SmartphoneService;

public class MyTest {

	public static void main(String[] args) {

		SmartphoneService smartphoneServiceInstance = MyServiceFactory.getSmartphoneServiceInstance();
		AppService appServiceInstance = MyServiceFactory.getAppServiceInstance();

		try {

			System.out.println(
					"**************************** inizio batteria di test ********************************************");
			System.out.println(
					"*************************************************************************************************");

			testInserimentoSmartphone(smartphoneServiceInstance);

			testInserimentoApp(appServiceInstance);

			testAggiornamentoOS(smartphoneServiceInstance);

			testAggiornamentoApp(appServiceInstance);

			System.out.println(
					"****************************** fine batteria di test ********************************************");
			System.out.println(
					"*************************************************************************************************");

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			EntityManagerUtil.shutdown();
		}
	}

	private static void testInserimentoSmartphone(SmartphoneService smartphoneServiceInstance) throws Exception {
		System.out.println(".......testInserimentoSmartphone inizio.............");
		Smartphone nuovoSmartphone = new Smartphone("Apple", "iPhone", 1000, "12.0.9");
		smartphoneServiceInstance.inserisciNuovo(nuovoSmartphone);

		if (nuovoSmartphone.getId() == null)
			throw new RuntimeException("testInserimentoSmartphone failed.");

		smartphoneServiceInstance.rimuovi(nuovoSmartphone);

		System.out.println(".......testInserimentoSmartphone fine: PASSED.............");
	}

	private static void testInserimentoApp(AppService appServiceInstance) throws Exception {
		System.out.println(".......testInserimentoApp inizio.............");
		App nuovaApp = new App("Whatsapp", new Date(), new Date(), "1.1.1");
		appServiceInstance.inserisciNuovo(nuovaApp);

		if (nuovaApp.getId() == null)
			throw new RuntimeException("testInserimentoApp failed.");

		appServiceInstance.rimuovi(nuovaApp);

		System.out.println(".......testInserimentoApp fine: PASSED.............");
	}

	private static void testAggiornamentoOS(SmartphoneService smartphoneServiceInstance) throws Exception {
		System.out.println(".......testAggiornamentoOS inizio.............");
		Smartphone nuovoSmartphone = new Smartphone("Apple", "iPhone", 1000, "12.0.9");
		smartphoneServiceInstance.inserisciNuovo(nuovoSmartphone);
		nuovoSmartphone.setVersioneOS("12.0.10");
		smartphoneServiceInstance.update(nuovoSmartphone);

		if (!smartphoneServiceInstance.caricaSingoloElemento(nuovoSmartphone.getId()).getVersioneOS().equals("12.0.10"))
			throw new RuntimeException("testAggiornamentoOS failed.");

		smartphoneServiceInstance.rimuovi(nuovoSmartphone);
		System.out.println(".......testAggiornamentoOS fine: PASSED.............");
	}

	private static void testAggiornamentoApp(AppService appServiceInstance) throws Exception {
		System.out.println(".......testAggiornamentoApp inizio.............");
		App nuovaApp = new App("Whatsapp", new Date(), new SimpleDateFormat("dd/MM/yyyy").parse("24/09/2021"), "1.1.1");
		appServiceInstance.inserisciNuovo(nuovaApp);
		Date nuovaData = new SimpleDateFormat("dd/MM/yyyy").parse("24/10/2021");
		nuovaApp.setDataUltimoAggiornamento(nuovaData);
		nuovaApp.setVersione("1.1.2");
		appServiceInstance.aggiorna(nuovaApp);

		if (!appServiceInstance.caricaSingoloElemento(nuovaApp.getId()).getVersione().equals("1.1.2")
				|| !(appServiceInstance.caricaSingoloElemento(nuovaApp.getId()).getDataUltimoAggiornamento()
						.compareTo(nuovaData) == 0))
			throw new RuntimeException("testAggiornamentoApp failed.");

		appServiceInstance.rimuovi(nuovaApp);

		System.out.println(".......testAggiornamentoApp fine: PASSED.............");
	}

	private static void testInstallazioneApp(AppService appServiceInstance, SmartphoneService smartphoneServiceInstance)
			throws Exception {
		System.out.println(".......testInstallazioneApp inizio.............");
		Smartphone nuovoSmartphone = new Smartphone("Apple", "iPhone", 1000, "12.0.9");
		smartphoneServiceInstance.inserisciNuovo(nuovoSmartphone);
		App nuovaApp = new App("Whatsapp", new Date(), new Date(), "1.1.1");
		appServiceInstance.inserisciNuovo(nuovaApp);
		
		//TODO
	}
}
