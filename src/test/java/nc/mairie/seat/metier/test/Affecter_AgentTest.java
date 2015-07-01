package nc.mairie.seat.metier.test;

import static org.junit.Assert.*;
import nc.mairie.seat.metier.Affecter_Agent;
import nc.mairie.seat.metier.Affecter_Service;
import nc.mairie.seat.metier.Agents;
import nc.mairie.seat.metier.Equipement;
import nc.mairie.seat.metier.Service;
import nc.mairie.technique.ServicesDivers;
import nc.mairie.technique.Transaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Affecter_AgentTest {

	Service unService = null;
	Agents agent = null;
	Equipement unEquipement = null;
	Transaction aTransaction=null;
	
	@Before
	public void init() throws Exception{
		aTransaction = ServicesDivers.getUneTransaction();
		assertFalse(aTransaction==null);
		assertFalse(aTransaction.getConnection() ==null);
		

		//Creation Equipement
		unEquipement = new Equipement();
		unEquipement.setNumeroinventaire("TEST");
		unEquipement.setNumeroimmatriculation("999999");
		unEquipement.setDatemiseencirculation("13/06/1990");
		unEquipement.setDatemiseencirculation("06/11/2008");
		unEquipement.setDatehorscircuit("01/01/0001");
		unEquipement.setPrixachat("5800000");
		unEquipement.setReserve("F");
		unEquipement.setCodemodele("1");
		unEquipement.setDureegarantie("3");

		unEquipement.creerEquipement(aTransaction, unEquipement);
		assertFalse(aTransaction.traiterErreur(),aTransaction.isErreur());

		//Recupération d'un service BIDON ZZZZ 
		unService=Service.chercherService(aTransaction, "ZZZZ");
		assertTrue("service ZZZZ introuvable",aTransaction.isErreur());
		aTransaction.traiterErreur();

		//Recupération d'un serviceDSI DCAA
		unService=Service.chercherService(aTransaction, "DCAA");
		assertFalse(aTransaction.isErreur());
		
	}
	
	@Test
	public void affecterAgentServiceSansDateDeFin() throws Exception {
		
		//Affectation de l'équipement sans date de fin
		Affecter_Service affecter_Service= new Affecter_Service();
		affecter_Service.setDdebut("01/01/2015");
		affecter_Service.setDfin("01/01/0001");
		affecter_Service.creerAffecter_Service(aTransaction, unEquipement, unService);
		assertFalse(aTransaction.isErreur());
				
		
		//recup d'un agent
		Agents unAgent = Agents.chercherAgents(aTransaction, "4117");
		assertFalse(aTransaction.isErreur());

		Affecter_Agent affecter_Agent = new Affecter_Agent();
		affecter_Agent.creerAffecter_Agent(aTransaction, unEquipement, unAgent, "01/01/2014");
		assertTrue("Création affectation agent AVANT l'affectation au service"+aTransaction.getMessageErreur(), aTransaction.isErreur());
		aTransaction.traiterErreur();
		
		affecter_Agent =  prepareAffecterAgent("01/01/2015","","DCAA");
		affecter_Agent.creerAffecter_Agent(aTransaction, unEquipement, unAgent, "01/01/2015");
		assertFalse("Création affectation agent APRES l'affectation au service :"+aTransaction.getMessageErreur(), aTransaction.isErreur());

	}
	
	public Affecter_Agent prepareAffecterAgent(String dateDebut, String dateFin, String service) {
		Affecter_Agent affecter_Agent = new Affecter_Agent();
		affecter_Agent.setDatedebut(dateDebut);
		affecter_Agent.setDatefin(dateFin);
		affecter_Agent.setHdeb("0");
		affecter_Agent.setHfin("0");
		affecter_Agent.setHdebmn("0");
		affecter_Agent.setHfinmn("0");
		affecter_Agent.setCodeservice(service);
		return affecter_Agent;
	}

	@Test
	public void affecterAgentServiceAVECDateDeFin() throws Exception {
		
		//Affectation de l'équipement avec une date de fin
		Affecter_Service affecter_Service= new Affecter_Service();
		affecter_Service.setDdebut("01/01/2015");
		affecter_Service.setDfin("31/12/2015");
		affecter_Service.creerAffecter_Service(aTransaction, unEquipement, unService);
		assertFalse(aTransaction.isErreur());
				
		
		//recup d'un agent
		Agents unAgent = Agents.chercherAgents(aTransaction, "4117");
		assertFalse(aTransaction.isErreur());

		//Test date début agent < date debut service et date de fin dans la période
		
		//affecter_Agent.creerAffecter_Agent(aTransaction, unEquipement, unAgent, affecter_Agent.getDatedebut());
		Affecter_Agent affecter_Agent = prepareAffecterAgent("01/01/2014", "01/07/2015", "DCAA");
		affecter_Agent.affecter_agent(aTransaction, unEquipement, unAgent, affecter_Agent, unService, affecter_Agent.getDatedebut(), affecter_Agent);
		assertTrue(aTransaction.getMessageErreur(),aTransaction.isErreur());
		aTransaction.traiterErreur();
		
		//Test date début agent < date debut service et date de fin illimité : erreur 
		affecter_Agent = prepareAffecterAgent("01/01/2014", "", "DCAA");
		affecter_Agent.affecter_agent(aTransaction, unEquipement, unAgent, affecter_Agent, unService, affecter_Agent.getDatedebut(), affecter_Agent);
		assertTrue(aTransaction.isErreur());
		aTransaction.traiterErreur();
		
		//test debut OK et fin dépassant
		affecter_Agent = prepareAffecterAgent("01/07/2015", "01/01/2016", "DCAA");
		affecter_Agent.affecter_agent(aTransaction, unEquipement, unAgent, affecter_Agent, unService, affecter_Agent.getDatedebut(), affecter_Agent);
		assertTrue(aTransaction.isErreur());
		aTransaction.traiterErreur();
				
		//test debut OK et fin illimitée
		affecter_Agent = prepareAffecterAgent("01/07/2015", "", "DCAA");
		affecter_Agent.affecter_agent(aTransaction, unEquipement, unAgent, affecter_Agent, unService, affecter_Agent.getDatedebut(), affecter_Agent);
		assertTrue( aTransaction.isErreur());
		aTransaction.traiterErreur();
		
		//test debut dépassant et fin illimitée
		affecter_Agent = prepareAffecterAgent("01/07/2016", "", "DCAA");
		affecter_Agent.affecter_agent(aTransaction, unEquipement, unAgent, affecter_Agent, unService, affecter_Agent.getDatedebut(), affecter_Agent);
		assertTrue(aTransaction.isErreur());
		aTransaction.traiterErreur();

		
		//test debut OK et fin OK
		affecter_Agent = prepareAffecterAgent("01/07/2015", "10/07/2015", "DCAA");
		affecter_Agent.affecter_agent(aTransaction, unEquipement, unAgent, affecter_Agent, unService, affecter_Agent.getDatedebut(), affecter_Agent);
		assertFalse(aTransaction.getMessageErreur(), aTransaction.isErreur());

	}
	
	@Test
	public void affecterAgentAvecAffectationExistante() throws Exception {
		
		//Affectation de l'équipement avec une date de fin
		Affecter_Service affecter_Service= new Affecter_Service();
		affecter_Service.setDdebut("01/01/2015");
		affecter_Service.setDfin("31/12/2015");
		affecter_Service.creerAffecter_Service(aTransaction, unEquipement, unService);
		assertFalse(aTransaction.isErreur());
				
		
		//recup d'un agent
		Agents unAgent = Agents.chercherAgents(aTransaction, "4117");
		assertFalse(aTransaction.isErreur());

		///test debut OK et fin OK
		Affecter_Agent affecter_Agent = prepareAffecterAgent("01/07/2015", "10/07/2015", "DCAA");
		affecter_Agent.affecter_agent(aTransaction, unEquipement, unAgent, affecter_Agent, unService, affecter_Agent.getDatedebut(), affecter_Agent);
		assertFalse(aTransaction.getMessageErreur(), aTransaction.isErreur());
		
		//test Avant la période
		Affecter_Agent affecter_Agent2 = prepareAffecterAgent("01/05/2015", "10/05/2015", "DCAA");
		affecter_Agent2.affecter_agent(aTransaction, unEquipement, unAgent, affecter_Agent2, unService, affecter_Agent.getDatedebut(), affecter_Agent);
		assertTrue(aTransaction.getMessageErreur(), aTransaction.isErreur());
		aTransaction.traiterErreur();
		
		//test Avant la période et fin > fin
		affecter_Agent2 = prepareAffecterAgent("01/05/2015", "", "DCAA");
		affecter_Agent2.affecter_agent(aTransaction, unEquipement, unAgent, affecter_Agent2, unService, affecter_Agent.getDatedebut(), affecter_Agent);
		assertTrue(aTransaction.getMessageErreur(), aTransaction.isErreur());
		aTransaction.traiterErreur();
		
		//test pendant la période
		affecter_Agent2 = prepareAffecterAgent("02/07/2015", "03/07/2015", "DCAA");
		affecter_Agent2.affecter_agent(aTransaction, unEquipement, unAgent, affecter_Agent2, unService, affecter_Agent.getDatedebut(), affecter_Agent);
		assertTrue(aTransaction.getMessageErreur(), aTransaction.isErreur());
		aTransaction.traiterErreur();
		
		//test debut pendant et fin après
		affecter_Agent2 = prepareAffecterAgent("02/07/2015", "11/07/2015", "DCAA");
		affecter_Agent2.affecter_agent(aTransaction, unEquipement, unAgent, affecter_Agent2, unService, affecter_Agent.getDatedebut(), affecter_Agent);
		assertTrue(aTransaction.getMessageErreur(), aTransaction.isErreur());
		aTransaction.traiterErreur();

		//test debut pendant et fin infini
		affecter_Agent2 = prepareAffecterAgent("02/07/2015", "", "DCAA");
		affecter_Agent2.affecter_agent(aTransaction, unEquipement, unAgent, affecter_Agent2, unService, affecter_Agent.getDatedebut(), affecter_Agent);
		assertTrue(aTransaction.getMessageErreur(), aTransaction.isErreur());
		aTransaction.traiterErreur();
		
		//test OK mais KO par rapport au service (pas de fin)
		affecter_Agent2 = prepareAffecterAgent("11/07/2015", "", "DCAA");
		affecter_Agent2.affecter_agent(aTransaction, unEquipement, unAgent, affecter_Agent2, unService, affecter_Agent.getDatedebut(), affecter_Agent);
		assertTrue(aTransaction.getMessageErreur(), aTransaction.isErreur());
		aTransaction.traiterErreur();

		//test debut OK et fin OK mais test KO car même agent
		affecter_Agent2 = prepareAffecterAgent("01/08/2015", "10/08/2015", "DCAA");
		affecter_Agent2.affecter_agent(aTransaction, unEquipement, unAgent, affecter_Agent2, unService, affecter_Agent.getDatedebut(), affecter_Agent);
		assertTrue(aTransaction.getMessageErreur(), aTransaction.isErreur());
		aTransaction.traiterErreur();

		//test debut OK et fin OK a	avec un autre agent
		unAgent = Agents.chercherAgents(aTransaction, "4118");
		assertFalse(aTransaction.isErreur());
		
		affecter_Agent2 = prepareAffecterAgent("01/08/2015", "10/08/2015", "DCAA");
		affecter_Agent2.affecter_agent(aTransaction, unEquipement, unAgent, affecter_Agent2, unService, affecter_Agent2.getDatedebut(), affecter_Agent);
		assertFalse(aTransaction.getMessageErreur(), aTransaction.isErreur());


	}
	
	@After
	public void end() throws Exception {
		aTransaction.rollbackTransaction();
		aTransaction.getConnection().close();
	}
	
	
}
