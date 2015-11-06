package nc.mairie.seat.metier.test;

import static org.junit.Assert.*;
import nc.mairie.seat.metier.PM_Affecter_Agent;
import nc.mairie.seat.metier.Agents;
import nc.mairie.seat.metier.PM_Affecter_Sce;
import nc.mairie.seat.metier.PMateriel;
import nc.mairie.seat.metier.Service;
import nc.mairie.technique.ServicesDivers;
import nc.mairie.technique.Transaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PM_Affecter_AgentTest {

	Service unService = null;
	Agents agent = null;
	PMateriel unPMateriel = null;
	Transaction aTransaction=null;
	
	@Before
	public void init() throws Exception{
		aTransaction = ServicesDivers.getUneTransaction();
		assertFalse(aTransaction==null);
		assertFalse(aTransaction.getConnection() ==null);
		

		//Creation PMateriel
		unPMateriel = new PMateriel();
		unPMateriel.setPminv("TEST");
		unPMateriel.setPmserie("999988887777");
		unPMateriel.setCodemodele("163");
		unPMateriel.setCodefre("1");
		unPMateriel.setDgarantie("365");
		unPMateriel.setReserve("F");
		unPMateriel.setDmes("25/10/2007");
		unPMateriel.setDmhs("01/10/0001");
		unPMateriel.setPrix("84600");
		unPMateriel.setDachat("25/10/2007");
		
		
		unPMateriel.creerPMateriel(aTransaction, unPMateriel);
		assertFalse(aTransaction.traiterErreur(),aTransaction.isErreur());

		//Recupération d'un service BIDON ZZZZ 
		unService=Service.chercherService(aTransaction, "ZZZZ");
		assertTrue("service ZZZZ introuvable",aTransaction.isErreur());
		aTransaction.traiterErreur();

		//Recupération d'un serviceDSI DCAA
		unService=Service.chercherService(aTransaction, "DCAAAAAAAAAAAAAA");
		assertFalse(aTransaction.isErreur());
		
	}
	
	@Test
	public void affecterAgentServiceSansDateDeFin() throws Exception {
		
		//Affectation de l'équipement sans date de fin
		PM_Affecter_Sce affecter_Service= new PM_Affecter_Sce();
		affecter_Service.setDdebut("01/01/2015");
		affecter_Service.setDfin("01/01/0001");
		affecter_Service.creerPM_Affecter_Sce(aTransaction, unPMateriel, unService);
		assertFalse(aTransaction.isErreur());
				
		
		//recup d'un agent
		Agents unAgent = Agents.chercherAgents(aTransaction, "4117");
		assertFalse(aTransaction.isErreur());

		PM_Affecter_Agent PM_affecter_Agent = new PM_Affecter_Agent();
		PM_affecter_Agent.creerPm_Affecter_Agent(aTransaction, unPMateriel, unAgent, "01/01/2014");
		assertTrue("Création affectation agent AVANT l'affectation au service"+aTransaction.getMessageErreur(), aTransaction.isErreur());
		aTransaction.traiterErreur();
		
		PM_affecter_Agent =  prepareAffecterAgent("01/01/2015","","DCAA");
		PM_affecter_Agent.creerPm_Affecter_Agent(aTransaction, unPMateriel, unAgent, "01/01/2015");
		assertFalse("Création affectation agent APRES l'affectation au service :"+aTransaction.getMessageErreur(), aTransaction.isErreur());

	}
	
	public PM_Affecter_Agent prepareAffecterAgent(String dateDebut, String dateFin, String service) {
		PM_Affecter_Agent PM_affecter_Agent = new PM_Affecter_Agent();
		PM_affecter_Agent.setDdeb(dateDebut);
		PM_affecter_Agent.setDfin(dateFin);
		PM_affecter_Agent.setHdeb("0");
		PM_affecter_Agent.setHfin("0");
		PM_affecter_Agent.setHdebmn("0");
		PM_affecter_Agent.setHfinmn("0");
		PM_affecter_Agent.setCodesce(service);
		return PM_affecter_Agent;
	}

	@Test
	public void affecterAgentServiceAVECDateDeFin() throws Exception {
		
		//Affectation de l'équipement avec une date de fin
		PM_Affecter_Sce affecter_Service= new PM_Affecter_Sce();
		affecter_Service.setDdebut("01/01/2015");
		affecter_Service.setDfin("31/12/2015");
		affecter_Service.creerPM_Affecter_Sce(aTransaction, unPMateriel, unService);
		assertFalse(aTransaction.isErreur());
				
		
		//recup d'un agent
		Agents unAgent = Agents.chercherAgents(aTransaction, "4117");
		assertFalse(aTransaction.isErreur());

		//Test date début agent < date debut service et date de fin dans la période
		
		//PM_affecter_Agent.creerPM_Affecter_Agent(aTransaction, unPMateriel, unAgent, PM_affecter_Agent.getDdeb());
		PM_Affecter_Agent PM_affecter_Agent = prepareAffecterAgent("01/01/2014", "01/07/2015", "DCAA");
		PM_affecter_Agent.affecter_agent(aTransaction, unPMateriel, unAgent, PM_affecter_Agent, unService, PM_affecter_Agent.getDdeb(), PM_affecter_Agent);
		assertTrue(aTransaction.getMessageErreur(),aTransaction.isErreur());
		aTransaction.traiterErreur();
		
		//Test date début agent < date debut service et date de fin illimité : erreur 
		PM_affecter_Agent = prepareAffecterAgent("01/01/2014", "", "DCAA");
		PM_affecter_Agent.affecter_agent(aTransaction, unPMateriel, unAgent, PM_affecter_Agent, unService, PM_affecter_Agent.getDdeb(), PM_affecter_Agent);
		assertTrue(aTransaction.isErreur());
		aTransaction.traiterErreur();
		
		//test debut OK et fin dépassant
		PM_affecter_Agent = prepareAffecterAgent("01/07/2015", "01/01/2016", "DCAA");
		PM_affecter_Agent.affecter_agent(aTransaction, unPMateriel, unAgent, PM_affecter_Agent, unService, PM_affecter_Agent.getDdeb(), PM_affecter_Agent);
		assertTrue(aTransaction.isErreur());
		aTransaction.traiterErreur();
				
		//test debut OK et fin illimitée
		PM_affecter_Agent = prepareAffecterAgent("01/07/2015", "", "DCAA");
		PM_affecter_Agent.affecter_agent(aTransaction, unPMateriel, unAgent, PM_affecter_Agent, unService, PM_affecter_Agent.getDdeb(), PM_affecter_Agent);
		assertTrue( aTransaction.isErreur());
		aTransaction.traiterErreur();
		
		//test debut dépassant et fin illimitée
		PM_affecter_Agent = prepareAffecterAgent("01/07/2016", "", "DCAA");
		PM_affecter_Agent.affecter_agent(aTransaction, unPMateriel, unAgent, PM_affecter_Agent, unService, PM_affecter_Agent.getDdeb(), PM_affecter_Agent);
		assertTrue(aTransaction.isErreur());
		aTransaction.traiterErreur();

		
		//test debut OK et fin OK
		PM_affecter_Agent = prepareAffecterAgent("01/07/2015", "10/07/2015", "DCAA");
		PM_affecter_Agent.affecter_agent(aTransaction, unPMateriel, unAgent, PM_affecter_Agent, unService, PM_affecter_Agent.getDdeb(), new PM_Affecter_Agent());
		assertFalse(aTransaction.getMessageErreur(), aTransaction.isErreur());

	}
	
	@Test
	public void affecterAgentAvecAffectationExistante() throws Exception {
		
		//Affectation de l'équipement avec une date de fin
		PM_Affecter_Sce affecter_Service= new PM_Affecter_Sce();
		affecter_Service.setDdebut("01/01/2015");
		affecter_Service.setDfin("31/12/2015");
		affecter_Service.creerPM_Affecter_Sce(aTransaction, unPMateriel, unService);
		assertFalse(aTransaction.isErreur());
				
		
		//recup d'un agent
		Agents unAgent = Agents.chercherAgents(aTransaction, "4117");
		assertFalse(aTransaction.isErreur());

		///test debut OK et fin OK
		PM_Affecter_Agent PM_affecter_Agent = prepareAffecterAgent("01/07/2015", "10/07/2015", "DCAA");
		PM_affecter_Agent.affecter_agent(aTransaction, unPMateriel, unAgent, PM_affecter_Agent, unService, PM_affecter_Agent.getDdeb(), new PM_Affecter_Agent());
		assertFalse(aTransaction.getMessageErreur(), aTransaction.isErreur());
		
		//test Avant la période
		PM_Affecter_Agent PM_affecter_Agent2 = prepareAffecterAgent("01/05/2015", "10/05/2015", "DCAA");
		PM_affecter_Agent2.affecter_agent(aTransaction, unPMateriel, unAgent, PM_affecter_Agent2, unService, PM_affecter_Agent.getDdeb(), PM_affecter_Agent);
		assertTrue(aTransaction.getMessageErreur(), aTransaction.isErreur());
		aTransaction.traiterErreur();
		
		//test Avant la période et fin > fin
		PM_affecter_Agent2 = prepareAffecterAgent("01/05/2015", "", "DCAA");
		PM_affecter_Agent2.affecter_agent(aTransaction, unPMateriel, unAgent, PM_affecter_Agent2, unService, PM_affecter_Agent.getDdeb(), PM_affecter_Agent);
		assertTrue(aTransaction.getMessageErreur(), aTransaction.isErreur());
		aTransaction.traiterErreur();
		
		//test pendant la période
		PM_affecter_Agent2 = prepareAffecterAgent("02/07/2015", "03/07/2015", "DCAA");
		PM_affecter_Agent2.affecter_agent(aTransaction, unPMateriel, unAgent, PM_affecter_Agent2, unService, PM_affecter_Agent.getDdeb(), PM_affecter_Agent);
		assertTrue(aTransaction.getMessageErreur(), aTransaction.isErreur());
		aTransaction.traiterErreur();
		
		//test debut pendant et fin après
		PM_affecter_Agent2 = prepareAffecterAgent("02/07/2015", "11/07/2015", "DCAA");
		PM_affecter_Agent2.affecter_agent(aTransaction, unPMateriel, unAgent, PM_affecter_Agent2, unService, PM_affecter_Agent.getDdeb(), PM_affecter_Agent);
		assertTrue(aTransaction.getMessageErreur(), aTransaction.isErreur());
		aTransaction.traiterErreur();

		//test debut pendant et fin infini
		PM_affecter_Agent2 = prepareAffecterAgent("02/07/2015", "", "DCAA");
		PM_affecter_Agent2.affecter_agent(aTransaction, unPMateriel, unAgent, PM_affecter_Agent2, unService, PM_affecter_Agent.getDdeb(), PM_affecter_Agent);
		assertTrue(aTransaction.getMessageErreur(), aTransaction.isErreur());
		aTransaction.traiterErreur();
		
		//test OK mais KO par rapport au service (pas de fin)
		PM_affecter_Agent2 = prepareAffecterAgent("11/07/2015", "", "DCAA");
		PM_affecter_Agent2.affecter_agent(aTransaction, unPMateriel, unAgent, PM_affecter_Agent2, unService, PM_affecter_Agent.getDdeb(), PM_affecter_Agent);
		assertTrue(aTransaction.getMessageErreur(), aTransaction.isErreur());
		aTransaction.traiterErreur();

		//test debut OK et fin OK mais test KO car même agent
		PM_affecter_Agent2 = prepareAffecterAgent("01/08/2015", "10/08/2015", "DCAA");
		PM_affecter_Agent2.affecter_agent(aTransaction, unPMateriel, unAgent, PM_affecter_Agent2, unService, PM_affecter_Agent.getDdeb(), PM_affecter_Agent);
		assertTrue(aTransaction.getMessageErreur(), aTransaction.isErreur());
		aTransaction.traiterErreur();

		//test debut OK et fin OK a	avec un autre agent
		unAgent = Agents.chercherAgents(aTransaction, "4118");
		assertFalse(aTransaction.isErreur());
		
		PM_affecter_Agent2 = prepareAffecterAgent("01/08/2015", "10/08/2015", "DCAA");
		PM_affecter_Agent2.affecter_agent(aTransaction, unPMateriel, unAgent, PM_affecter_Agent2, unService, PM_affecter_Agent2.getDdeb(), PM_affecter_Agent);
		assertFalse(aTransaction.getMessageErreur(), aTransaction.isErreur());


	}
	
	@After
	public void end() throws Exception {
		aTransaction.rollbackTransaction();
		aTransaction.getConnection().close();
	}
	
	
}
