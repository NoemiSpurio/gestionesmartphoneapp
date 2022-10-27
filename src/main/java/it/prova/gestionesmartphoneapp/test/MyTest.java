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

			testInstallazioneApp(appServiceInstance, smartphoneServiceInstance);

			testDisinstallazioneApp(appServiceInstance, smartphoneServiceInstance);
			
			testRimozioneSmartphone(appServiceInstance, smartphoneServiceInstance);

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

		smartphoneServiceInstance.aggiungiApp(nuovaApp, nuovoSmartphone);

		nuovoSmartphone = smartphoneServiceInstance.caricaSingoloElementoEagerApps(nuovoSmartphone.getId());

		if (nuovoSmartphone.getApps().size() != 1)
			throw new RuntimeException("testInstallazioneApp failed: installazione non andata a buon fine.");

		smartphoneServiceInstance.rimuovi(nuovoSmartphone);
		appServiceInstance.rimuovi(nuovaApp);

		System.out.println(".......testInstallazioneApp fine: PASSED.............");
	}

	private static void testDisinstallazioneApp(AppService appServiceInstance,
			SmartphoneService smartphoneServiceInstance) throws Exception {
		System.out.println(".......testDisinstallazioneApp inizio.............");
		Smartphone nuovoSmartphone = new Smartphone("Apple", "iPhone", 1000, "12.0.9");
		smartphoneServiceInstance.inserisciNuovo(nuovoSmartphone);
		App nuovaApp = new App("Whatsapp", new Date(), new Date(), "1.1.1");
		appServiceInstance.inserisciNuovo(nuovaApp);

		smartphoneServiceInstance.aggiungiApp(nuovaApp, nuovoSmartphone);

		nuovoSmartphone = smartphoneServiceInstance.caricaSingoloElementoEagerApps(nuovoSmartphone.getId());

		smartphoneServiceInstance.rimuoviApp(nuovaApp, nuovoSmartphone);

		nuovoSmartphone = smartphoneServiceInstance.caricaSingoloElementoEagerApps(nuovoSmartphone.getId());

		if (nuovoSmartphone.getApps().size() != 0)
			throw new RuntimeException("testDisinstallazioneApp failed: disinstallazione non avvenuta con successo.");

		smartphoneServiceInstance.rimuovi(nuovoSmartphone);
		appServiceInstance.rimuovi(nuovaApp);

		System.out.println(".......testDisinstallazioneApp fine: PASSED.............");

	}

	private static void testRimozioneSmartphone(AppService appServiceInstance,
			SmartphoneService smartphoneServiceInstance) throws Exception {

		System.out.println(".......testRimozioneSmartphone inizio.............");
		Smartphone nuovoSmartphone = new Smartphone("Apple", "iPhone", 1000, "12.0.9");
		smartphoneServiceInstance.inserisciNuovo(nuovoSmartphone);
		App nuovaApp1 = new App("Whatsapp", new Date(), new Date(), "1.1.1");
		appServiceInstance.inserisciNuovo(nuovaApp1);
		App nuovaApp2 = new App("Whatsapp", new Date(), new Date(), "1.1.1");
		appServiceInstance.inserisciNuovo(nuovaApp2);
		smartphoneServiceInstance.aggiungiApp(nuovaApp1, nuovoSmartphone);
		smartphoneServiceInstance.aggiungiApp(nuovaApp2, nuovoSmartphone);

		Smartphone smartphoneReloaded = smartphoneServiceInstance
				.caricaSingoloElementoEagerApps(nuovoSmartphone.getId());

		smartphoneServiceInstance.rimuovi(smartphoneReloaded);

		if (smartphoneServiceInstance.caricaSingoloElemento(nuovoSmartphone.getId()) != null)
			throw new RuntimeException("testRimozioneSmartphone failed: rimozione fallita.");

		appServiceInstance.rimuovi(nuovaApp1);
		appServiceInstance.rimuovi(nuovaApp2);

		System.out.println(".......testRimozioneSmartphone fine: PASSED.............");

	}
}
