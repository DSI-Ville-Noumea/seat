/*
 * Créé le 28 avr. 2005
 *
 */
package nc.mairie.seat.robot;

import java.util.Hashtable;

import nc.mairie.robot.Robot;
import nc.mairie.robot.Testeur;
import nc.mairie.seat.process.OeAccueil;
import nc.mairie.seat.process.OeAffectation_Agent;
import nc.mairie.seat.process.OeAffectation_Service;
import nc.mairie.seat.process.OeAgent_Equipements;
import nc.mairie.seat.process.OeAgent_PMateriel;
import nc.mairie.seat.process.OeAgent_Recherche;
import nc.mairie.seat.process.OeAgentsATM;
import nc.mairie.seat.process.OeBE_OT;
import nc.mairie.seat.process.OeBPC;
import nc.mairie.seat.process.OeBPC_Recherche;
import nc.mairie.seat.process.OeBPC_Visualisation;
import nc.mairie.seat.process.OeBPC_VisualisationComplete;
import nc.mairie.seat.process.OeBPC_VisualisationEquip;
import nc.mairie.seat.process.OeBPC_ajout;
import nc.mairie.seat.process.OeBPC_modifier;
import nc.mairie.seat.process.OeCarburant;
import nc.mairie.seat.process.OeCompteur;
import nc.mairie.seat.process.OeDeclaration_Visualisation;
import nc.mairie.seat.process.OeDeclaration_VisualisationEquip;
import nc.mairie.seat.process.OeDeclarations;
import nc.mairie.seat.process.OeDeclarations_ajout;
import nc.mairie.seat.process.OeEntretien;
import nc.mairie.seat.process.OeEntretiens_FPM;
import nc.mairie.seat.process.OeEntretiens_OT;
import nc.mairie.seat.process.OeEquipement;
import nc.mairie.seat.process.OeEquipementService_Recherche;
import nc.mairie.seat.process.OeEquipement_Recherche;
import nc.mairie.seat.process.OeEquipement_Visualisation;
import nc.mairie.seat.process.OeEquipement_ajout;
import nc.mairie.seat.process.OeEquipement_modif;
import nc.mairie.seat.process.OeFPM;
import nc.mairie.seat.process.OeFPM_Impression;
import nc.mairie.seat.process.OeFPM_Lancement;
import nc.mairie.seat.process.OeFPM_Modification;
import nc.mairie.seat.process.OeFPM_Recherche;
import nc.mairie.seat.process.OeFPM_Validation;
import nc.mairie.seat.process.OeFPM_Visualisation;
import nc.mairie.seat.process.OeFre_PM;
import nc.mairie.seat.process.OeMarques;
import nc.mairie.seat.process.OeMecaniciens_FPM;
import nc.mairie.seat.process.OeMecaniciens_OT;
import nc.mairie.seat.process.OeModePrise;
import nc.mairie.seat.process.OeModeles;
import nc.mairie.seat.process.OeModele_Visualisation;
import nc.mairie.seat.process.OeModele_Recherche;
import nc.mairie.seat.process.OeModeles_ajout;
import nc.mairie.seat.process.OeModeles_modif;
import nc.mairie.seat.process.OeOT;
import nc.mairie.seat.process.OeOT_Impression;
import nc.mairie.seat.process.OeOT_Lancement;
import nc.mairie.seat.process.OeOT_Modification;
import nc.mairie.seat.process.OeOT_Recherche;
import nc.mairie.seat.process.OeOT_Validation;
import nc.mairie.seat.process.OeOT_Visualisation;
import nc.mairie.seat.process.OePMPePerso;
import nc.mairie.seat.process.OePMPePerso_ajout;
import nc.mairie.seat.process.OePM_Affectation_Agent;
import nc.mairie.seat.process.OePM_Affectation_Sce;
import nc.mairie.seat.process.OePM_BE;
import nc.mairie.seat.process.OePM_Planning;
import nc.mairie.seat.process.OePM_TDB;
import nc.mairie.seat.process.OePMateriel;
import nc.mairie.seat.process.OePMateriel_MAJ;
import nc.mairie.seat.process.OePMateriel_Recherche;
import nc.mairie.seat.process.OePMateriel_Visualisation;
import nc.mairie.seat.process.OePeBase;
import nc.mairie.seat.process.OePeBase_ajout;
import nc.mairie.seat.process.OePePerso;
import nc.mairie.seat.process.OePePerso_ajout;
import nc.mairie.seat.process.OePieces_FPM;
import nc.mairie.seat.process.OePieces_OT;
import nc.mairie.seat.process.OePlanning;
import nc.mairie.seat.process.OePompes;
import nc.mairie.seat.process.OeService_Equipements;
import nc.mairie.seat.process.OeService_PMateriel;
import nc.mairie.seat.process.OeService_Recherche;
import nc.mairie.seat.process.OeSpecialite;
import nc.mairie.seat.process.OeTIntervalle;
import nc.mairie.seat.process.OeTableauDeBord;
import nc.mairie.seat.process.OeTypeEnt;
import nc.mairie.seat.process.OeTypeEquip;
import nc.mairie.seat.process.OePneu;
import nc.mairie.seat.process.OePieces;
import nc.mairie.technique.BasicProcess;

/**
 * @author ssi
 *
 */
public class RobotSeat extends Robot {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9153302647661192348L;

	/* (non-Javadoc)
	 * @see nc.mairie.robot.Robot#getDefaultProcess()
	 */
	@Override
	public BasicProcess getDefaultProcess() throws Exception {
		return new SeatMain();
	}

	/* (non-Javadoc)
	 * @see nc.mairie.robot.Robot#getFirstProcess(java.lang.String)
	 */
	@Override
	public BasicProcess getFirstProcess(String arg0) throws Exception {
		
		if ("GestionMarques".equals(arg0)) 
			return new OeMarques();
		else if ("GestionTypeEquip".equals(arg0)) 
			return new OeTypeEquip();
		else if ("GestionCarburant".equals(arg0)) 
			return new OeCarburant();
		else if ("GestionCompteur".equals(arg0)) 
			return new OeCompteur();
		else if ("GestionTypeInt".equals(arg0)) 
			return new OeTIntervalle();
		else if ("GestionModePrise".equals(arg0)) 
			return new OeModePrise();
		else if ("GestionPneu".equals(arg0)) 
			return new OePneu();
		else if ("GestionModeles".equals(arg0)) 
			return new OeModeles();
		else if ("VisualisationModele".equals(arg0)) 
			return new OeModele_Visualisation();
		else if ("GestionTypeEntretien".equals(arg0)) 
			return new OeTypeEnt();
		else if ("GestionEntretien".equals(arg0)) 
			return new OeEntretien();
		else if ("GestionEquipement".equals(arg0)) 
			return new OeEquipement();
		else if ("VisualisationEquipement".equals(arg0)) 
			return new OeEquipement_Visualisation();
		else if ("GestionBPC".equals(arg0)) 
			return new OeBPC();
		else if ("SaisieBPC".equals(arg0)) 
			return new OeBPC_ajout();
		else if ("VisualisationBPC".equals(arg0)) 
			return new OeBPC_Visualisation();
		else if ("VisualisationBPCs".equals(arg0)) 
			return new OeBPC_VisualisationEquip();
		else if ("AffectationService".equals(arg0)) 
			return new OeAffectation_Service();
		else if ("AffectationAgent".equals(arg0)) 
			return new OeAffectation_Agent();
		else if ("GestionPeBase".equals(arg0)) 
			return new OePeBase();
		else if ("GestionPePerso".equals(arg0)) 
			return new OePePerso();
		else if ("VisualisationPlanning".equals(arg0)) 
			return new OePlanning();
		else if ("ImprimerOT".equals(arg0)) 
			return new OeOT_Impression();
		else if ("GestionOT".equals(arg0)) 
			return new OeOT();
		else if ("GestionPieces".equals(arg0)) 
			return new OePieces();
		else if ("ValiderOT".equals(arg0)) 
			return new OeOT_Validation();
		else if ("GestionSpecialite".equals(arg0)) 
			return new OeSpecialite();
		else if ("VisualisationOT".equals(arg0)) 
			return new OeOT_Visualisation();
		else if ("GestionATM".equals(arg0)) 
			return new OeAgentsATM();
		else if ("TableauDeBord".equals(arg0)) 
			return new OeTableauDeBord();
		else if ("VisualisationDeclarationEquip".equals(arg0)) 
			return new OeDeclaration_VisualisationEquip();
		else if ("GestionDeclaration".equals(arg0)) 
			return new OeDeclarations();
		else if ("VisualisationBPCComplete".equals(arg0)) 
			return new OeBPC_VisualisationComplete();
		else if ("VisualisationPlanning".equals(arg0)) 
			return new OePlanning();
		else if ("VisualisationTableauDeBord".equals(arg0)) 
			return new OeTableauDeBord();
		else if ("GestionPompes".equals(arg0)) 
			return new OePompes();
		else if ("AffectationServiceEquipement".equals(arg0)) 
			return new OeService_Equipements();
		else if ("AffectationAgentEquipements".equals(arg0)) 
			return new OeAgent_Equipements();
		else if ("GestionFrePM".equals(arg0)) 
			return new OeFre_PM();
		else if ("GestionPMateriel".equals(arg0)) 
			return new OePMateriel();
		else if ("VisualisationPMateriel".equals(arg0)) 
			return new OePMateriel_Visualisation();
		else  if ("AffectationServicePm".equals(arg0)) 
			return new OePM_Affectation_Sce();
		else  if ("AffectationAgentPm".equals(arg0)) 
			return new OePM_Affectation_Agent();
		else  if ("VisuPmTDB".equals(arg0)) 
			return new OePM_TDB();
		else  if ("VisuAffAgentPm".equals(arg0)) 
			return new OeAgent_PMateriel();
		else  if ("VisuAffScePm".equals(arg0)) 
			return new OeService_PMateriel();
		else  if ("PMPePerso".equals(arg0)) 
			return new OePMPePerso();
		else  if ("VisuPlanningPm".equals(arg0)) 
			return new OePM_Planning();
		else  if ("VisualisationFPM".equals(arg0)) 
			return new OeFPM_Visualisation();
		else  if ("GestionFPM".equals(arg0)) 
			return new OeFPM();
		else  if ("ValiderFPM".equals(arg0)) 
			return new OeFPM_Validation();
		else  if ("ImprimerFPM".equals(arg0)) 
			return new OeFPM_Impression();
		else return null;
	} 
	
	
	/* (non-Javadoc)
	 * @see nc.mairie.robot.Robot#initialiseNavigation()
	 */
	@Override
	protected Hashtable<String, String> initialiseNavigation() {
		Hashtable<String, String> nav = new Hashtable<String, String>();
		
		//OEAccueil
		//nav.put(OeAccueil.class.getName()+OeAccueil.STATUT_PNEU, OePneu.class.getName());
		nav.put(OeAccueil.class.getName()+OeAccueil.STATUT_MARQUES, OeMarques.class.getName());
		nav.put(OeAccueil.class.getName()+OeAccueil.STATUT_TYPEEQUIP, OeTypeEquip.class.getName());
		nav.put(OeAccueil.class.getName()+OeAccueil.STATUT_MODELES, OeModeles.class.getName());
		nav.put(OeAccueil.class.getName()+OeAccueil.STATUT_ENTRETIEN, OeEntretien.class.getName());
		nav.put(OeAccueil.class.getName()+OeAccueil.STATUT_PIECES, OePieces.class.getName());
		nav.put(OeAccueil.class.getName()+OeAccueil.STATUT_TINTERVALLE, OeTIntervalle.class.getName());
		nav.put(OeAccueil.class.getName()+OeAccueil.STATUT_CARBURANT, OeCarburant.class.getName());
		nav.put(OeAccueil.class.getName()+OeAccueil.STATUT_COMPTEUR, OeCompteur.class.getName());
		nav.put(OeAccueil.class.getName()+OeAccueil.STATUT_EQUIPEMENT, OeEquipement.class.getName());
		nav.put(OeAccueil.class.getName()+OeAccueil.STATUT_BPC, OeBPC.class.getName());
		nav.put(OeAccueil.class.getName()+OeAccueil.STATUT_MODEPRISE, OeModePrise.class.getName());
		//nav.put(OeAccueil.class.getName()+OeAccueil.STATUT_POMPES, OePompes.class.getName());

		//	OeModeles
		nav.put(OeModeles.class.getName()+ OeModeles.STATUT_MODIFIER, OeModeles_modif.class.getName());
		nav.put(OeModeles.class.getName()+ OeModeles.STATUT_AJOUTER, OeModeles_ajout.class.getName());
		
		//	OeModeles_Visualisation
		nav.put(OeModele_Visualisation.class.getName()+ OeModele_Visualisation.STATUT_RECHERCHE, OeModele_Recherche.class.getName());
		
		//OeEquipement
		nav.put(OeEquipement.class.getName()+ OeEquipement.STATUT_AJOUTER, OeEquipement_ajout.class.getName());
		nav.put(OeEquipement.class.getName()+ OeEquipement.STATUT_MODIFIER, OeEquipement_modif.class.getName());
		
		//	OeEquipement_Visualisation
		nav.put(OeEquipement_Visualisation.class.getName()+ OeEquipement_Visualisation.STATUT_RECHERCHE, OeEquipement_Recherche.class.getName());
		
		//	OeBPC
		nav.put(OeBPC.class.getName()+ OeBPC.STATUT_AJOUTER, OeBPC_ajout.class.getName());
		nav.put(OeBPC.class.getName()+ OeBPC.STATUT_MODIFIER, OeBPC_modifier.class.getName());
		nav.put(OeBPC.class.getName()+ OeBPC.STATUT_RECHERCHE, OeEquipement_Recherche.class.getName());
		nav.put(OeBPC.class.getName()+ OeBPC.STATUT_PMATERIEL, OePMateriel_Recherche.class.getName());
		
//		OeBPC_ajout
		nav.put(OeBPC_ajout.class.getName()+ OeBPC_ajout.STATUT_RECH_EQUIP, OeEquipement_Recherche.class.getName());
		nav.put(OeBPC_ajout.class.getName()+ OeBPC_ajout.STATUT_PMATERIEL, OePMateriel_Recherche.class.getName());
		
		//	OeBPC_VisualisationEquip
		nav.put(OeBPC_VisualisationEquip.class.getName()+ OeBPC_VisualisationEquip.STATUT_RECHERCHER, OeEquipement_Recherche.class.getName());
		nav.put(OeBPC_VisualisationEquip.class.getName()+ OeBPC_VisualisationEquip.STATUT_DETAILS, OeBPC_Visualisation.class.getName());
		nav.put(OeBPC_VisualisationEquip.class.getName()+ OeBPC_VisualisationEquip.STATUT_PMATERIEL, OePMateriel_Recherche.class.getName());
		
		//	OeBPC_Visualisation
		nav.put(OeBPC_Visualisation.class.getName()+ OeBPC_Visualisation.STATUT_RECHERCHE, OeBPC_Recherche.class.getName());
		
		//	OeAffectation_Service
		nav.put(OeAffectation_Service.class.getName()+ OeAffectation_Service.STATUT_RECHERCHEREQUIPEMENT, OeEquipement_Recherche.class.getName());
		nav.put(OeAffectation_Service.class.getName()+ OeAffectation_Service.STATUT_RECHERCHESERVICE, OeService_Recherche.class.getName());
		
		//	OeAffectation_Agent
		nav.put(OeAffectation_Agent.class.getName()+ OeAffectation_Agent.STATUT_RECHERCHER, OeEquipementService_Recherche.class.getName());
		
		//	OePeBase
		nav.put(OePeBase.class.getName()+ OePeBase.STATUT_AJOUTER, OePeBase_ajout.class.getName());
		nav.put(OePeBase.class.getName()+ OePeBase.STATUT_MODIFIER, OePeBase_ajout.class.getName());
		nav.put(OePeBase.class.getName()+ OePeBase.STATUT_RECHERCHERMODELE, OeModele_Recherche.class.getName());
		
		//	OePePerso
		nav.put(OePePerso.class.getName()+ OePePerso.STATUT_AJOUTER, OePePerso_ajout.class.getName());
		nav.put(OePePerso.class.getName()+ OePePerso.STATUT_MODIFIER, OePePerso_ajout.class.getName());
		nav.put(OePePerso.class.getName()+ OePePerso.STATUT_RECHERCHEEQUIP, OeEquipement_Recherche.class.getName());
		nav.put(OePePerso.class.getName()+ OePePerso.STATUT_RETOUROT, OeOT_Lancement.class.getName());
		nav.put(OePePerso.class.getName()+ OePePerso.STATUT_ACCES_OT, OeOT.class.getName());
		
//		OePlanning
		nav.put(OePlanning.class.getName()+ OePlanning.STATUT_PEPERSO, OePePerso.class.getName());
		nav.put(OePlanning.class.getName()+ OePlanning.STATUT_VALIDATION, OeOT_Lancement.class.getName());
		
//		OeOT_Lancement
		nav.put(OeOT_Lancement.class.getName()+ OeOT_Lancement.STATUT_IMPRIMER, OeOT_Impression.class.getName());
		nav.put(OeOT_Lancement.class.getName()+ OeOT_Lancement.STATUT_DETAILS_PEPERSO, OePePerso.class.getName());
		nav.put(OeOT_Lancement.class.getName()+ OeOT_Lancement.STATUT_DETAILS_OT, OeOT_Visualisation.class.getName());
		nav.put(OeOT_Lancement.class.getName()+ OeOT_Lancement.STATUT_MODIFIER_OT, OeOT_Modification.class.getName());
				
		//	OeOT_Impression
		nav.put(OeOT_Impression.class.getName()+ OeOT_Impression.STATUT_RECHERCHE, OeOT_Recherche.class.getName());
		
		//	OeOT
		nav.put(OeOT.class.getName()+ OeOT.STATUT_RECHERCHE_EQUIP, OeEquipement_Recherche.class.getName());
		nav.put(OeOT.class.getName()+ OeOT.STATUT_MODIFIER, OeOT_Modification.class.getName());
		
		//	OeOT_Modification
		nav.put(OeOT_Modification.class.getName()+ OeOT_Modification.STATUT_ENTRETIENS, OeEntretiens_OT.class.getName());
		nav.put(OeOT_Modification.class.getName()+ OeOT_Modification.STATUT_PIECES, OePieces_OT.class.getName());
		nav.put(OeOT_Modification.class.getName()+ OeOT_Modification.STATUT_RECHERCHEREQUIP, OeEquipement_Recherche.class.getName());
		nav.put(OeOT_Modification.class.getName()+ OeOT_Modification.STATUT_MECANICIEN, OeMecaniciens_OT.class.getName());
		nav.put(OeOT_Modification.class.getName()+ OeOT_Modification.STATUT_FRE, OeBE_OT.class.getName());
		nav.put(OeOT_Modification.class.getName()+ OeOT_Modification.STATUT_CREATIONOT, OeEntretiens_OT.class.getName());
		nav.put(OeOT_Modification.class.getName()+ OeOT_Modification.STATUT_DECLARATIONS, OeDeclarations.class.getName());
		
		//	OeOT_Visualisation
		nav.put(OeOT_Visualisation.class.getName()+ OeOT_Visualisation.STATUT_RECHERCHER, OeOT_Recherche.class.getName());		

		//OeTableauDeBord
		nav.put(OeTableauDeBord.class.getName()+ OeTableauDeBord.STATUT_RECHERCHER, OeEquipement_Recherche.class.getName());
		nav.put(OeTableauDeBord.class.getName()+ OeTableauDeBord.STATUT_BPC, OeBPC_Visualisation.class.getName());
		nav.put(OeTableauDeBord.class.getName()+ OeTableauDeBord.STATUT_OT, OeOT_Visualisation.class.getName());
		nav.put(OeTableauDeBord.class.getName()+ OeTableauDeBord.STATUT_DECLARATIONS, OeDeclaration_Visualisation.class.getName());
		nav.put(OeTableauDeBord.class.getName()+ OeTableauDeBord.STATUT_AGENT, OeAgent_Recherche.class.getName());
		nav.put(OeTableauDeBord.class.getName()+ OeTableauDeBord.STATUT_SELECTION, OeAgent_Equipements.class.getName());
		
		//OePage1
		//nav.put(OePage1.class.getName()+ OePage1.STATUT_DETAIL, OePage2.class.getName());
		
		//OeDeclarations
		nav.put(OeDeclarations.class.getName()+ OeDeclarations.STATUT_AJOUTER, OeDeclarations_ajout.class.getName());
		nav.put(OeDeclarations.class.getName()+ OeDeclarations.STATUT_MODIFIER, OeDeclarations_ajout.class.getName());
		
//		OeDeclarations_ajout
		nav.put(OeDeclarations_ajout.class.getName()+ OeDeclarations_ajout.STATUT_RECHERCHER, OeEquipement_Recherche.class.getName());
		nav.put(OeDeclarations_ajout.class.getName()+ OeDeclarations_ajout.STATUT_OT, OeOT_Modification.class.getName());
		nav.put(OeDeclarations_ajout.class.getName()+ OeDeclarations_ajout.STATUT_RECH_PM, OePMateriel_Recherche.class.getName());
		nav.put(OeDeclarations_ajout.class.getName()+ OeDeclarations_ajout.STATUT_FPM, OeFPM_Modification.class.getName());
		
//		OeDeclarations_VisualisationEquip
		nav.put(OeDeclaration_VisualisationEquip.class.getName()+ OeDeclaration_VisualisationEquip.STATUT_RECHERCHE, OeEquipement_Recherche.class.getName());
		nav.put(OeDeclaration_VisualisationEquip.class.getName()+ OeDeclaration_VisualisationEquip.STATUT_RECH_PM, OePMateriel_Recherche.class.getName());
		
//		OeBPC_VisualisationComplete
		nav.put(OeBPC_VisualisationComplete.class.getName()+ OeBPC_VisualisationComplete.STATUT_EQUIP, OeEquipement_Recherche.class.getName());
		nav.put(OeBPC_VisualisationComplete.class.getName()+ OeBPC_VisualisationComplete.STATUT_SERVICE, OeService_Recherche.class.getName());
		
		// OePieces_ajout
		nav.put(OePieces_OT.class.getName()+ OePieces_OT.STATUT_PIECES, OePieces.class.getName());
		
		//OeOT_Validation
		nav.put(OeOT_Validation.class.getName()+ OeOT_Validation.STATUT_MODIFIER, OeOT_Modification.class.getName());
		nav.put(OeOT_Validation.class.getName()+ OeOT_Validation.STATUT_VISUALISER, OeOT_Visualisation.class.getName());
		
		//OeService_Equipements
		nav.put(OeService_Equipements.class.getName()+ OeService_Equipements.STATUT_SCE_RECHERCHE, OeService_Recherche.class.getName());
		nav.put(OeService_Equipements.class.getName()+ OeService_Equipements.STATUT_TDB, OeTableauDeBord.class.getName());
		nav.put(OeService_Equipements.class.getName()+ OeService_Equipements.STATUT_VISUALISER, OeAffectation_Agent.class.getName());
		nav.put(OeService_Equipements.class.getName()+ OeService_Equipements.STATUT_AFFECTATION, OeAffectation_Agent.class.getName());
		
//		OeAgent_Equipements
		nav.put(OeAgent_Equipements.class.getName()+ OeAgent_Equipements.STATUT_VISUALISER, OeEquipement_Visualisation.class.getName());
		nav.put(OeAgent_Equipements.class.getName()+ OeAgent_Equipements.STATUT_AGENTS, OeAgent_Recherche.class.getName());
		nav.put(OeAgent_Equipements.class.getName()+ OeAgent_Equipements.STATUT_TDB, OeTableauDeBord.class.getName());
		nav.put(OeAgent_Equipements.class.getName()+ OeAgent_Equipements.STATUT_BPC, OeBPC.class.getName());
		nav.put(OeAgent_Equipements.class.getName()+ OeAgent_Equipements.STATUT_BPCVISU, OeBPC_VisualisationEquip.class.getName());
		nav.put(OeAgent_Equipements.class.getName()+ OeAgent_Equipements.STATUT_BPCCOMP, OeBPC_VisualisationComplete.class.getName());
		nav.put(OeAgent_Equipements.class.getName()+ OeAgent_Equipements.STATUT_DECLVISU, OeDeclaration_VisualisationEquip.class.getName());
		nav.put(OeAgent_Equipements.class.getName()+ OeAgent_Equipements.STATUT_PEPERSO, OePePerso.class.getName());
		nav.put(OeAgent_Equipements.class.getName()+ OeAgent_Equipements.STATUT_AFF_SCE, OeAffectation_Service.class.getName());
		nav.put(OeAgent_Equipements.class.getName()+ OeAgent_Equipements.STATUT_AFF_AGENT, OeAffectation_Agent.class.getName());
		
//		OeAgent_Recherche
		nav.put(OeAgent_Recherche.class.getName()+ OeAgent_Recherche.STATUT_VISUALISER, OeEquipement_Visualisation.class.getName());
		nav.put(OeAgent_Recherche.class.getName()+ OeAgent_Recherche.STATUT_TDB, OeTableauDeBord.class.getName());
		nav.put(OeAgent_Recherche.class.getName()+ OeAgent_Recherche.STATUT_EQUIPEMENTSAGENT, OeAgent_Equipements.class.getName());
		nav.put(OeAgent_Recherche.class.getName()+ OeAgent_Recherche.STATUT_AFFECTATION_SCE, OeAffectation_Service.class.getName());
		nav.put(OeAgent_Recherche.class.getName()+ OeAgent_Recherche.STATUT_BPC, OeBPC.class.getName());
		nav.put(OeAgent_Recherche.class.getName()+ OeAgent_Recherche.STATUT_BPC_AJOUT, OeBPC_ajout.class.getName());
		nav.put(OeAgent_Recherche.class.getName()+ OeAgent_Recherche.STATUT_BPC_VISU_COMPLETE, OeBPC_VisualisationComplete.class.getName());
		nav.put(OeAgent_Recherche.class.getName()+ OeAgent_Recherche.STATUT_BPC_VISU_EQUIP, OeBPC_VisualisationEquip.class.getName());
		nav.put(OeAgent_Recherche.class.getName()+ OeAgent_Recherche.STATUT_DECL_AJOUT, OeDeclarations_ajout.class.getName());
		nav.put(OeAgent_Recherche.class.getName()+ OeAgent_Recherche.STATUT_DECL_VISU_EQUIP, OeDeclaration_VisualisationEquip.class.getName());
		nav.put(OeAgent_Recherche.class.getName()+ OeAgent_Recherche.STATUT_OT, OeOT.class.getName());
		nav.put(OeAgent_Recherche.class.getName()+ OeAgent_Recherche.STATUT_OT_MAJ, OeOT_Modification.class.getName());
		nav.put(OeAgent_Recherche.class.getName()+ OeAgent_Recherche.STATUT_PEPERSO, OePePerso.class.getName());
		nav.put(OeAgent_Recherche.class.getName()+ OeAgent_Recherche.STATUT_VISUPMAT, OePMateriel_Visualisation.class.getName());
		nav.put(OeAgent_Recherche.class.getName()+ OeAgent_Recherche.STATUT_TDBPMAT, OePM_TDB.class.getName());
		nav.put(OeAgent_Recherche.class.getName()+ OeAgent_Recherche.STATUT_PMATAGENT, OeAgent_PMateriel.class.getName());

		//		 OePieces_ajout
		nav.put(OeEquipement_Recherche.class.getName()+ OeEquipement_Recherche.STATUT_AGENT, OeAgent_Recherche.class.getName());

		//		OePMateriel
		nav.put(OePMateriel.class.getName()+ OePMateriel.STATUT_AJOUTER, OePMateriel_MAJ.class.getName());
		nav.put(OePMateriel.class.getName()+ OePMateriel.STATUT_MODIFIER, OePMateriel_MAJ.class.getName());
		
		//		OePMateriel_Visualisation
		nav.put(OePMateriel_Visualisation.class.getName() + OePMateriel_Visualisation.STATUT_RECHERCHE, OePMateriel_Recherche.class.getName());
		
		//		OePM_Affectation_Sce
		nav.put(OePM_Affectation_Sce.class.getName()+ OePM_Affectation_Sce.STATUT_RECHERCHEREQUIPEMENT, OePMateriel_Recherche.class.getName());
		nav.put(OePM_Affectation_Sce.class.getName()+ OePM_Affectation_Sce.STATUT_RECHERCHESERVICE, OeService_Recherche.class.getName());
		
//		OePM_Affectation_Agent
		nav.put(OePM_Affectation_Agent.class.getName()+ OePM_Affectation_Agent.STATUT_RECHERCHER, OePMateriel_Recherche.class.getName());
		
		//OePM_TDB
		nav.put(OePM_TDB.class.getName()+ OePM_TDB.STATUT_RECHERCHER, OePMateriel_Recherche.class.getName());
		nav.put(OePM_TDB.class.getName()+ OePM_TDB.STATUT_BPC, OeBPC_Visualisation.class.getName());
		nav.put(OePM_TDB.class.getName()+ OePM_TDB.STATUT_AGENT, OeAgent_Recherche.class.getName());
		nav.put(OePM_TDB.class.getName()+ OePM_TDB.STATUT_FPM, OeFPM_Visualisation.class.getName());
		nav.put(OePM_TDB.class.getName()+ OePM_TDB.STATUT_SELECTION, OeAgent_PMateriel.class.getName());
		nav.put(OePM_TDB.class.getName()+ OePM_TDB.STATUT_DECLARATIONS, OeDeclaration_Visualisation.class.getName());
		
		//		OeService_PMateriel
		nav.put(OeService_PMateriel.class.getName()+ OeService_PMateriel.STATUT_SCE_RECHERCHE, OeService_Recherche.class.getName());
		nav.put(OeService_PMateriel.class.getName()+ OeService_PMateriel.STATUT_TDB, OePM_TDB.class.getName());
		nav.put(OeService_PMateriel.class.getName()+ OeService_PMateriel.STATUT_VISUALISER, OePM_Affectation_Agent.class.getName());
		nav.put(OeService_PMateriel.class.getName()+ OeService_PMateriel.STATUT_AFFECTATION, OePM_Affectation_Agent.class.getName());
		
		//		OeAgent_PMateriel
		nav.put(OeAgent_PMateriel.class.getName()+ OeAgent_PMateriel.STATUT_VISUALISER, OePMateriel_Visualisation.class.getName());
		nav.put(OeAgent_PMateriel.class.getName()+ OeAgent_PMateriel.STATUT_AGENTS, OeAgent_Recherche.class.getName());
		nav.put(OeAgent_PMateriel.class.getName()+ OeAgent_PMateriel.STATUT_TDBPMAT, OePM_TDB.class.getName());
		nav.put(OeAgent_PMateriel.class.getName()+ OeAgent_PMateriel.STATUT_PMPEPERSO, OePMPePerso.class.getName());
		nav.put(OeAgent_PMateriel.class.getName()+ OeAgent_PMateriel.STATUT_AFF_SCE, OePM_Affectation_Sce.class.getName());
		nav.put(OeAgent_PMateriel.class.getName()+ OeAgent_PMateriel.STATUT_AFF_AGENT, OePM_Affectation_Agent.class.getName());
		
		//	OeEquipement_Recherche
		nav.put(OeEquipement_Recherche.class.getName()+ OeEquipement_Recherche.STATUT_AGENT, OeAgent_Recherche.class.getName());
		
		//	OePMPePerso
		nav.put(OePMPePerso.class.getName()+ OePMPePerso.STATUT_AJOUTER, OePMPePerso_ajout.class.getName());
		nav.put(OePMPePerso.class.getName()+ OePMPePerso.STATUT_MODIFIER, OePMPePerso_ajout.class.getName());
		nav.put(OePMPePerso.class.getName()+ OePMPePerso.STATUT_RECHERCHEEQUIP, OePMateriel_Recherche.class.getName());
		nav.put(OePMPePerso.class.getName()+ OePMPePerso.STATUT_RETOURFPM, OeFPM_Lancement.class.getName());
		nav.put(OePMPePerso.class.getName()+ OePMPePerso.STATUT_ACCES_FPM, OeFPM.class.getName());
		
		//	OePM_Planning
		nav.put(OePM_Planning.class.getName()+ OePM_Planning.STATUT_PEPERSO, OePMPePerso.class.getName());
		nav.put(OePM_Planning.class.getName()+ OePM_Planning.STATUT_VALIDATION, OeFPM_Lancement.class.getName());
		
		//	OeFPM
		nav.put(OeFPM.class.getName()+ OeFPM.STATUT_RECHERCHE_PMAT, OePMateriel_Recherche.class.getName());
		nav.put(OeFPM.class.getName()+ OeFPM.STATUT_MODIFIER, OeFPM_Modification.class.getName());
		
		//	OeFPM_Modification
		nav.put(OeFPM_Modification.class.getName()+ OeFPM_Modification.STATUT_ENTRETIENS, OeEntretiens_FPM.class.getName());
		nav.put(OeFPM_Modification.class.getName()+ OeFPM_Modification.STATUT_RECHERCHEREQUIP, OePMateriel_Recherche.class.getName());
		nav.put(OeFPM_Modification.class.getName()+ OeFPM_Modification.STATUT_MECANICIEN, OeMecaniciens_FPM.class.getName());
		nav.put(OeFPM_Modification.class.getName()+ OeFPM_Modification.STATUT_FRE, OePM_BE.class.getName());
		nav.put(OeFPM_Modification.class.getName()+ OeFPM_Modification.STATUT_CREATIONFPM, OeEntretiens_FPM.class.getName());
		nav.put(OeFPM_Modification.class.getName()+ OeFPM_Modification.STATUT_DECLARATIONS, OeDeclarations.class.getName());
		nav.put(OeFPM_Modification.class.getName()+ OeFPM_Modification.STATUT_PIECES, OePieces_FPM.class.getName());
		
//		OeFPM_Lancement
		nav.put(OeFPM_Lancement.class.getName()+ OeFPM_Lancement.STATUT_IMPRIMER, OeFPM_Impression.class.getName());
		nav.put(OeFPM_Lancement.class.getName()+ OeFPM_Lancement.STATUT_DETAILS_PMPEPERSO, OePMPePerso.class.getName());
		nav.put(OeFPM_Lancement.class.getName()+ OeFPM_Lancement.STATUT_DETAILS_FPM, OeFPM_Visualisation.class.getName());
		nav.put(OeFPM_Lancement.class.getName()+ OeFPM_Lancement.STATUT_MODIFIER_FPM, OeFPM_Modification.class.getName());
		
//		OeFPM_Impression
		nav.put(OeFPM_Impression.class.getName()+ OeFPM_Impression.STATUT_RECHERCHE, OeFPM_Recherche.class.getName());
		
//		OePMateriel_Recherche
		nav.put(OePMateriel_Recherche.class.getName()+ OePMateriel_Recherche.STATUT_AGENT, OeAgent_Recherche.class.getName());
		
//		OeFPM_Visualisation
		nav.put(OeFPM_Visualisation.class.getName()+ OeFPM_Visualisation.STATUT_RECHERCHER, OeFPM_Recherche.class.getName());
		
//		OeFPM_Validation
		nav.put(OeFPM_Validation.class.getName()+ OeFPM_Validation.STATUT_MODIFIER, OeFPM_Modification.class.getName());
		nav.put(OeFPM_Validation.class.getName()+ OeFPM_Validation.STATUT_VISUALISER, OeFPM_Visualisation.class.getName());
		
//		OePieces_FPM
		nav.put(OePieces_FPM.class.getName()+ OePieces_FPM.STATUT_PIECES, OePieces.class.getName());
		
		return nav;
	}

	/* (non-Javadoc)
	 * @see nc.mairie.robot.Robot#initialiseTesteur()
	 */
	@Override
	protected Testeur initialiseTesteur() {
		return null;
	}

}
