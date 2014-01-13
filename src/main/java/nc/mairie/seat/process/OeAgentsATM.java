package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.AActifs;
import nc.mairie.seat.metier.AgentAtmRenseignements;
import nc.mairie.seat.metier.AgentServiceInfos;
import nc.mairie.seat.metier.Agents;
import nc.mairie.seat.metier.AgentsATM;
import nc.mairie.seat.metier.Service;
import nc.mairie.seat.metier.Specialite;
import nc.mairie.technique.*;
/**
 * Process OeAgentsATM
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
*/
public class OeAgentsATM extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -955661390844447526L;
	private java.lang.String[] LB_AGENTS;
	private java.lang.String[] LB_MECANICIEN;
	private java.lang.String[] LB_SPE;
	private String ACTION_SUPPRESSION = "Suppression d'un mécanicien<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private String ACTION_MODIFICATION = "Modification d'un mécanicien.";
	private String ACTION_CREATION = "Création d'un mécanicien.";
	private ArrayList<AActifs> listeAgents;
	private ArrayList<AgentAtmRenseignements> listeMecaniciens;
	private ArrayList<Specialite> listeSpe;
	private AgentServiceInfos agentServiceInfosCourant;
	private AActifs AActifsCourant;
	private Specialite specialiteCourant;
	private AgentAtmRenseignements agentATMCourant;
	private boolean first = true;
	public boolean isVide = true;
	public boolean afficheSpe;
	public boolean isVideMeca = true;
	public boolean isSuppression = false;
	public boolean isModif = false;
	public boolean mustReload = true;
	
	
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	
	//optimisation LB 02/09/11
	if (mustReload) {
		mustReload = false;
		//LB le 14/02/12
		Service aService = Service.chercherServiceActifAvecAcronyme(getTransaction(), "ATM");
		
		// initialisation de la liste des agents du service de l'ATM
		ArrayList<AActifs> listeAgents = AActifs.chercherListAgentServiceInfosSce(getTransaction(),aService.getServi());
		//ArrayList listeAgents = AgentServiceInfos.chercherListAgentServiceInfosSce(getTransaction(),"2030");
		if(getTransaction().isErreur()){
			return ;
		}
		setListeAgents(listeAgents);
	
		//initialisations des listes
		initialiseListeMecaniciens(request);
		initialiseListeAgents(request);
		if (getListeSpe() ==null || getListeSpe().size() == 0) {
			initialiseListespe(request);
		}
		
		
		// pour affichage des éléments dans la page jsp
		if(getListeAgents().size()>0){
			setVide(false);
		}else{
			setVide(true);
		}
		if(getListeMecaniciens().size()>0){
			setVideMeca(false);
		}else{
			setVideMeca(true);
		}
		if(getVAL_ST_TITRE_ACTION().equals(ACTION_SUPPRESSION)){
			setSuppression(true);
		}else{
			setSuppression(false);
		}
		// on compare les listes des agents de l'ATM et des mécaniciens
		// si l'agent est enregistré comme mécanicien on l'enlève de la liste des agents
		if((getListeAgents().size()>0)&&(getListeMecaniciens().size()>0)){
			//parcours de la liste des agents
			for(int i = 0;i<getListeAgents().size();i++){
				AActifs unAActif = (AActifs)getListeAgents().get(i);
				//parcours de la liste des mécaniciens
				for(int j = 0;j<getListeMecaniciens().size();j++){
					AgentAtmRenseignements unAgentATMInfos = (AgentAtmRenseignements)getListeMecaniciens().get(j);
					if(unAActif.getNomatr().equals(unAgentATMInfos.getNomatr())){
						getListeAgents().remove(i);
						break;
					}
					
				}
			}
			initialiseListeAgents(request);
		}
	}
	
	//si spécialité courante pas null
	if(getSpecialiteCourant()!=null){
		addZone(getNOM_LB_SPE_SELECT(),String.valueOf(-1));
		for (int i = 0; i < getListeSpe().size(); i++) {
			Specialite uneSpecialite = (Specialite)getListeSpe().get(i);
			if (uneSpecialite.getCodespecialite().equals(getSpecialiteCourant().getCodespecialite())) {
				addZone(getNOM_LB_SPE_SELECT(),String.valueOf(i));
				break;
			}
		}
	}

}

public void initialiseListeAgents(javax.servlet.http.HttpServletRequest request) throws Exception{
	ArrayList<AActifs> listAgentEpure = new ArrayList<AActifs>();
	//initialisation de la liste des agents de l'ATM
	if(getListeAgents().size()>0){
		//	les élèments de la liste 
		int [] tailles = {30,30};
		//	String [] champs = {"nom","prenom"};
			String [] padding = {"G","G"};
			FormateListe aFormat = new FormateListe(tailles,padding,false);
		// on ne retient que les agents qui ne sont pas encore déclarés comme mécanicien
		for (int i=0; i<getListeAgents().size();i++){
			AActifs unAActif = (AActifs)getListeAgents().get(i);
			AgentAtmRenseignements unAgentAtmR = new AgentAtmRenseignements();
			if(!unAgentAtmR.existeMecanicien(getTransaction(),unAActif.getNomatr())){
				listAgentEpure.add(unAActif);
				String ligne [] = { unAActif.getNom(),unAActif.getPrenom()};
				aFormat.ajouteLigne(ligne);
			}
		}
		setLB_AGENTS(aFormat.getListeFormatee());
	}else{
		setLB_AGENTS(LBVide);
	}
	setListeAgents(listAgentEpure);
	
}

/*
 *  Date de création : 04/08/05
 * @autheur : Coralie Nicolas
 * initialise la liste des mécaniciens
 */
public void initialiseListeMecaniciens(javax.servlet.http.HttpServletRequest request) throws Exception{
	
	ArrayList<AgentAtmRenseignements> listMeca = AgentAtmRenseignements.listerAgentAtmRenseignements(getTransaction());
	if(getTransaction().isErreur()){
		return;
	}
	setListeMecaniciens(listMeca);
/*	if(listMeca.size()>0){
		for(int i=0;i<listMeca.size();i++){
			AgentsATM unAgentATM = (AgentsATM)listMeca.get(i);
			// on cherche l'agentserviceinfos correspondant
			AgentServiceInfos unASI = AgentServiceInfos.chercherAgentServiceInfos(getTransaction(),unAgentATM.getMatricule());
			if(getTransaction().isErreur()){
				return ;
			}
			getListeMecaniciens().add(unASI);
		}
	}*/
	
	if(getListeMecaniciens().size()>0){
		int [] tailles = {25,20,35};
		String [] padding = {"G","G","G"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (int i=0;i<getListeMecaniciens().size();i++){
			AgentAtmRenseignements monMeca = (AgentAtmRenseignements)getListeMecaniciens().get(i);
			String ligne [] = { monMeca.getNom(),monMeca.getPrenom(),monMeca.getLibellespe()};
			aFormat.ajouteLigne(ligne);
		}
		setLB_MECANICIEN(aFormat.getListeFormatee());
	}else{
		setLB_MECANICIEN(LBVide);
	}
}
/*
 *  Date de création : 04/08/05
 * @autheur : CN
 * initialise la liste des spécialités
 */
public void initialiseListespe(javax.servlet.http.HttpServletRequest request) throws Exception{
	ArrayList<Specialite> liste = Specialite.listerSpecialite(getTransaction());
	setListeSpe(liste);
	if (liste.size()!=0){
		int [] tailles = {20};
		String [] champs = {"libellespe"};
		String [] padding = {"G"};
		setLB_SPE(new FormateListe(tailles,liste,champs,padding,false).getListeFormatee());
	}else{
		setLB_SPE(null);
	}
}
/**
 * Constructeur du process OeAgentsATM.
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
public OeAgentsATM() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_AJOUTER() {
	return "NOM_PB_AJOUTER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {
	int indice = (Services.estNumerique(getVAL_LB_AGENTS_SELECT()) ? Integer.parseInt(getVAL_LB_AGENTS_SELECT()): -1);
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement dans la liste des agents");
		return false;
	}
	AActifs unAactifs = (AActifs)getListeAgents().get(indice);
	setAActifsCourant(unAactifs);
	// pour entrer la quantité de pièces utilisé
	setAfficheSpe(true);
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
	addZone(getNOM_ST_AGENT(),getAActifsCourant().getNom()+" "+getAActifsCourant().getPrenom());
	setModif(true);
	return true;
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	setAfficheSpe(false);
	//on vide les zones
	addZone(getNOM_ST_AGENT(),"");
	addZone(getNOM_ST_SPE(),"");
	addZone(getNOM_LB_SPE_SELECT(),"0");
	addZone(getNOM_ST_TITRE_ACTION(),"");
	setModif(false);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ENLEVER
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_ENLEVER() {
	return "NOM_PB_ENLEVER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
public boolean performPB_ENLEVER(javax.servlet.http.HttpServletRequest request) throws Exception {
	// on enlève l'agent sélectionné de la liste des mécaniciens
	int indice = (Services.estNumerique(getVAL_LB_MECANICIEN_SELECT()) ? Integer.parseInt(getVAL_LB_MECANICIEN_SELECT()): -1);
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement dans la liste des mécaniciens");
		return false;
	}
	AgentAtmRenseignements unAgentATMInfos = (AgentAtmRenseignements)getListeMecaniciens().get(indice);
	setAgentATMCourant(unAgentATMInfos);
	/*Specialite unSpecialite = Specialite.chercherSpecialite(getTransaction(),getAgentATMInfosCourant().getCodespe());
	if(getTransaction().isErreur()){
		return false;
	}
	setSpecialiteCourant(unSpecialite);*/
	// tout s'est bien passé
	commitTransaction();
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);
	// on recherche l'agent correspondant
	Agents unAgent = Agents.chercherAgents(getTransaction(),getAgentATMCourant().getNomatr());
	if(getTransaction().isErreur()){
		return false;
	}
	addZone(getNOM_ST_AGENT(),unAgent.getNom()+" "+unAgent.getPrenom());
	Specialite uneSpecialite = Specialite.chercherSpecialite(getTransaction(),getAgentATMCourant().getCodespe());
	if(getTransaction().isErreur()){
		return false;
	}
	setSpecialiteCourant(uneSpecialite);
	addZone(getNOM_ST_SPE(),getSpecialiteCourant().getLibellespe());
	setAfficheSpe(true);
	setSuppression(true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_OK() {
	return "NOM_PB_OK";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
	// on récupère l'indice de spécialité de l'agent
	int indice = (Services.estNumerique(getVAL_LB_SPE_SELECT()) ? Integer.parseInt(getVAL_LB_SPE_SELECT()): -1);
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement dans la liste de sélection");
		return false;
	}
	Specialite uneSpecialite = (Specialite)getListeSpe().get(indice);
	setSpecialiteCourant(uneSpecialite);
	//si suppression
	if(getVAL_ST_TITRE_ACTION().equals(ACTION_SUPPRESSION)){
		AgentsATM unAgentATM = AgentsATM.chercherAgentsATMMatr(getTransaction(),getAgentATMCourant().getNomatr());
		if(getTransaction().isErreur()){
			return false;
		}
		unAgentATM.supprimerAgentsATM(getTransaction());
		
	// si modification
	}else if (getVAL_ST_TITRE_ACTION().equals(ACTION_MODIFICATION)){
		// recherche de l'agent concerné
		Agents unAgent = Agents.chercherAgents(getTransaction(),getAgentATMCourant().getNomatr());
		if(getTransaction().isErreur()){
			return false;
		}
		//modification
		//recherche de l'agent ATM
		AgentsATM unAgentATM = AgentsATM.chercherAgentsATMMatr(getTransaction(),getAgentATMCourant().getNomatr());
		if(getTransaction().isErreur()){
			return false;
		}
		unAgentATM.modificationAgentsATM(getTransaction(),unAgent,getSpecialiteCourant());
		
	// si creation
	}else if(getVAL_ST_TITRE_ACTION().equals(ACTION_CREATION)){
		AgentsATM unAgentATM = new AgentsATM();
		//recherche de l'agent concerné
		Agents unAgent = Agents.chercherAgents(getTransaction(),getAActifsCourant().getNomatr());
		if(getTransaction().isErreur()){
			return false;
		}
		unAgentATM.creerAgentsATM(getTransaction(),unAgent,getSpecialiteCourant());
	}
	//si erreur 
	if(getTransaction().isErreur()){
		return false;
	}
	// tout s'est bien passé
	commitTransaction();
	
	//initialisation des zones
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_ST_SPE(),"");
	addZone(getNOM_ST_AGENT(),"");
	addZone(getNOM_LB_SPE_SELECT(),"0");
	setAfficheSpe(false);
	setSuppression(false);
	setModif(false);
	mustReload=true;
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_MODIFIER() {
	return "NOM_PB_MODIFIER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {
	//	on récupère l'agent sélectionné 
	int indice = (Services.estNumerique(getVAL_LB_MECANICIEN_SELECT()) ? Integer.parseInt(getVAL_LB_MECANICIEN_SELECT()): -1);
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement dans la liste des mécaniciens");
		return false;
	}
	AgentAtmRenseignements unAgentsATM = (AgentAtmRenseignements)getListeMecaniciens().get(indice);
	setAgentATMCourant(unAgentsATM);
	// on renseigne la spécialisté
	Specialite uneSpe = Specialite.chercherSpecialite(getTransaction(),getAgentATMCourant().getCodespe());
	if(getTransaction().isErreur()){
		return false;
	}
	setSpecialiteCourant(uneSpe);
	// on affiche la spécialité pour modification
	setAfficheSpe(true);
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);
	// on recherche l'agent correspondant
	Agents unAgent = Agents.chercherAgents(getTransaction(),getAgentATMCourant().getNomatr());
	if(getTransaction().isErreur()){
		return false;
	}
	addZone(getNOM_ST_AGENT(),unAgent.getNom()+" "+unAgent.getPrenom());
	setModif(true);
	return true;
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_AGENTS
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
private String [] getLB_AGENTS() {
	if (LB_AGENTS == null)
		LB_AGENTS = initialiseLazyLB();
	return LB_AGENTS;
}
/**
 * Setter de la liste:
 * LB_AGENTS
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
private void setLB_AGENTS(java.lang.String[] newLB_AGENTS) {
	LB_AGENTS = newLB_AGENTS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_AGENTS
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_AGENTS() {
	return "NOM_LB_AGENTS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_AGENTS_SELECT
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_AGENTS_SELECT() {
	return "NOM_LB_AGENTS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_AGENTS
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_AGENTS() {
	return getLB_AGENTS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_AGENTS
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_AGENTS_SELECT() {
	return getZone(getNOM_LB_AGENTS_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_MECANICIEN
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
private String [] getLB_MECANICIEN() {
	if (LB_MECANICIEN == null)
		LB_MECANICIEN = initialiseLazyLB();
	return LB_MECANICIEN;
}
/**
 * Setter de la liste:
 * LB_MECANICIEN
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
private void setLB_MECANICIEN(java.lang.String[] newLB_MECANICIEN) {
	LB_MECANICIEN = newLB_MECANICIEN;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_MECANICIEN
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_MECANICIEN() {
	return "NOM_LB_MECANICIEN";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_MECANICIEN_SELECT
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_MECANICIEN_SELECT() {
	return "NOM_LB_MECANICIEN_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_MECANICIEN
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_MECANICIEN() {
	return getLB_MECANICIEN();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_MECANICIEN
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_MECANICIEN_SELECT() {
	return getZone(getNOM_LB_MECANICIEN_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_SPE
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
private String [] getLB_SPE() {
	if (LB_SPE == null)
		LB_SPE = initialiseLazyLB();
	return LB_SPE;
}
/**
 * Setter de la liste:
 * LB_SPE
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
private void setLB_SPE(java.lang.String[] newLB_SPE) {
	LB_SPE = newLB_SPE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_SPE
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_SPE() {
	return "NOM_LB_SPE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_SPE_SELECT
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_SPE_SELECT() {
	return "NOM_LB_SPE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_SPE
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_SPE() {
	return getLB_SPE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_SPE
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_SPE_SELECT() {
	return getZone(getNOM_LB_SPE_SELECT());
}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
	public boolean isVide() {
		return isVide;
	}
	public void setVide(boolean isVide) {
		this.isVide = isVide;
	}
	public ArrayList<AActifs> getListeAgents() {
		return listeAgents;
	}
	public void setListeAgents(ArrayList<AActifs> listeAgents) {
		this.listeAgents = listeAgents;
	}
	public ArrayList<AgentAtmRenseignements> getListeMecaniciens() {
		if(listeMecaniciens==null){
			listeMecaniciens = new ArrayList<AgentAtmRenseignements>();
		}
		return listeMecaniciens;
	}
	public void setListeMecaniciens(ArrayList<AgentAtmRenseignements> listeMecaniciens) {
		this.listeMecaniciens = listeMecaniciens;
	}
	public ArrayList<Specialite> getListeSpe() {
		return listeSpe;
	}
	public void setListeSpe(ArrayList<Specialite> listeSpe) {
		this.listeSpe = listeSpe;
	}
	public AgentServiceInfos getAgentServiceInfosCourant() {
		return agentServiceInfosCourant;
	}
	public void setAgentServiceInfosCourant(
			AgentServiceInfos agentServiceInfosCourant) {
		this.agentServiceInfosCourant = agentServiceInfosCourant;
	}
	public boolean isAfficheSpe() {
		return afficheSpe;
	}
	public void setAfficheSpe(boolean afficheSpe) {
		this.afficheSpe = afficheSpe;
	}
	public Specialite getSpecialiteCourant() {
		return specialiteCourant;
	}
	public void setSpecialiteCourant(Specialite specialiteCourant) {
		this.specialiteCourant = specialiteCourant;
	}
	public boolean isVideMeca() {
		return isVideMeca;
	}
	public void setVideMeca(boolean isVideMeca) {
		this.isVideMeca = isVideMeca;
	}
	public AgentAtmRenseignements getAgentATMCourant() {
		return agentATMCourant;
	}
	public void setAgentATMCourant(AgentAtmRenseignements agentATMInfosCourant) {
		this.agentATMCourant = agentATMInfosCourant;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (08/08/05 08:13:58)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (08/08/05 08:13:58)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_AGENT
 * Date de création : (08/08/05 08:34:58)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_AGENT() {
	return "NOM_ST_AGENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_AGENT
 * Date de création : (08/08/05 08:34:58)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_AGENT() {
	return getZone(getNOM_ST_AGENT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SPE
 * Date de création : (08/08/05 08:34:58)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_SPE() {
	return "NOM_ST_SPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SPE
 * Date de création : (08/08/05 08:34:58)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_SPE() {
	return getZone(getNOM_ST_SPE());
}
	public boolean isSuppression() {
		return isSuppression;
	}
	public void setSuppression(boolean isSuppression) {
		this.isSuppression = isSuppression;
	}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (04/08/05 07:58:05)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_AJOUTER
		if (testerParametre(request, getNOM_PB_AJOUTER())) {
			return performPB_AJOUTER(request);
		}

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_ENLEVER
		if (testerParametre(request, getNOM_PB_ENLEVER())) {
			return performPB_ENLEVER(request);
		}

		//Si clic sur le bouton PB_OK
		if (testerParametre(request, getNOM_PB_OK())) {
			return performPB_OK(request);
		}

		//Si clic sur le bouton PB_VALIDER
		if (testerParametre(request, getNOM_PB_MODIFIER())) {
			return performPB_MODIFIER(request);
		}

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (08/08/05 10:32:16)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeAgentsATM.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (08/08/05 10:32:16)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_ANNULER() {
	return "NOM_PB_ANNULER";
}
	public AActifs getAActifsCourant() {
		return AActifsCourant;
	}
	public void setAActifsCourant(AActifs actifsCourant) {
		AActifsCourant = actifsCourant;
	}
	public boolean isModif() {
		return isModif;
	}
	public void setModif(boolean isModif) {
		this.isModif = isModif;
	}
}
