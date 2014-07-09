package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.Agents;
import nc.mairie.seat.metier.AgentsATM;
import nc.mairie.seat.metier.FPM;
import nc.mairie.seat.metier.PM_ATM;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.technique.*;
/**
 * Process OeMecaniciens_FPM
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
*/
public class OeMecaniciens_FPM extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -899960815343454709L;
	public static final int STATUT_TITRE_ACTION = 1;
	private String ACTION_SUPPRESSION = "Suppression";
//	private String ACTION_MODIFICATION = "Modification";
	private String ACTION_CREATION = "Création";
	private java.lang.String[] LB_MECANICIENS;
	private java.lang.String[] LB_MECA_FPM;
	private PM_ATM pmAtmCourant;
	private FPM fpmCourant;
	private PMatInfos pMatInfosCourant;
	private ArrayList<AgentsATM> listeMecaniciens;
	private ArrayList<AgentsATM> listeMecaFPM;
	private String focus = null;
	public boolean isSuppression = false;
	public boolean tailleListeMecaFPM;
	private boolean first = true;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	if(first){
		FPM unFPM = (FPM)VariableGlobale.recuperer(request,"FPM");
		if (unFPM!=null){
			setFpmCourant(unFPM);
			addZone(getNOM_ST_NUMFICHE(),getFpmCourant().getNumfiche());
			PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request,"PMATINFOS");
			if(unPMatInfos!=null){
				setPMatInfosCourant(unPMatInfos);
				addZone(getNOM_ST_NOINVENT(),getPMatInfosCourant().getPminv());
				addZone(getNOM_ST_NOSERIE(),getPMatInfosCourant().getPmserie());
				addZone(getNOM_ST_TYPE(),getPMatInfosCourant().getDesignationtypeequip());
				addZone(getNOM_ST_MODELE(),getPMatInfosCourant().getDesignationmodele());
				addZone(getNOM_ST_MARQUE(),getPMatInfosCourant().getDesignationmarque());
			}
		}
		String titreAction = (String)VariableActivite.recuperer(this,"TITRE_ACTION");
		addZone(getNOM_ST_TITRE_ACTION(),titreAction);
		if(getVAL_ST_TITRE_ACTION().equals(ACTION_CREATION)){
			setSuppression(false);
		}else{
			setSuppression(true);
		}
		PM_ATM unPM_ATM= (PM_ATM)VariableActivite.recuperer(this,"FPM_ATM");
		setPmAtmCourant(unPM_ATM);
		
		//la fiche d'entretiens n'a pas été enregistrée
		if(getVAL_ST_NUMFICHE().equals("")){
			getTransaction().declarerErreur("La fiche d'entretiens n'a pas encore été générée.Veuillez d'abord l'enregistrer.");
			return ;
		}
		
		initialiseListeMecaniciens(request);
		initialiseListeMecaFPM(request);

	}
	if(getPmAtmCourant()!=null){
		if(getPmAtmCourant().getNumfiche()!=null){
			Agents unAgent = Agents.chercherAgents(getTransaction(),getPmAtmCourant().getMatricule());
			if(getTransaction().isErreur()){
				return ;
			}
			addZone(getNOM_ST_MECANICIENS(),unAgent.getNom()+" "+unAgent.getPrenom());
		}
	}
	if(getListeMecaFPM().size()>0){
		setTailleListeMecaFPM(true);
	}else{
		setTailleListeMecaFPM(false);
	}
	setFirst(false);
}

public void initialiseListeMecaniciens(javax.servlet.http.HttpServletRequest request) throws Exception{
	//initialisation avec les mécaniciens de l'ATM
	if(getListeMecaniciens()==null){
		ArrayList<AgentsATM> listATM = AgentsATM.listerAgentsATM(getTransaction());
		if(getTransaction().isErreur()){
			return ;
		}
		setListeMecaniciens(listATM);
	}

	if (getListeMecaniciens().size()>0){
		int [] tailles = {30};
		String [] padding = {"G"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (int i = 0; i < getListeMecaniciens().size() ; i++) {
			AgentsATM monAgentATM = (AgentsATM)getListeMecaniciens().get(i);
			// on recherche l'agent concerné
			Agents unAgent = Agents.chercherAgents(getTransaction(),monAgentATM.getMatricule());
			if(getTransaction().isErreur()){
				return ;
			}	
			String agent = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
			String ligne [] = {agent};
			aFormat.ajouteLigne(ligne);
		}
		setLB_MECANICIENS(aFormat.getListeFormatee());
	}else{
		setLB_MECANICIENS(null);
	}
}

public void initialiseListeMecaFPM(javax.servlet.http.HttpServletRequest request) throws Exception{
	//initialise la liste des mécaniciens pour la FPM
	if(getListeMecaFPM().size()>0){
		int [] tailles = {30};
		String [] padding = {"G"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (int i=0;i<getListeMecaFPM().size();i++){
			AgentsATM unAgentATM = (AgentsATM)getListeMecaFPM().get(i);
//			 on recherche l'agent concerné
			Agents unAgent = Agents.chercherAgents(getTransaction(),unAgentATM.getMatricule());
			if(getTransaction().isErreur()){
				return ;
			}	
			String agent = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
			String ligne [] = {agent };
			aFormat.ajouteLigne(ligne);
		}
		setLB_MECA_FPM(aFormat.getListeFormatee());
	}else{
		setLB_MECA_FPM(null);
	}
}

/**
 * Constructeur du process OeMecaniciens_FPM.
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 */
public OeMecaniciens_FPM() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_AJOUTER() {
	return "NOM_PB_AJOUTER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {
	// on ajoute le mécanicien à la liste des mécaniciens pour la FPM
	int indice = (Services.estNumerique(getVAL_LB_MECANICIENS_SELECT()) ? Integer.parseInt(getVAL_LB_MECANICIENS_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	AgentsATM unAgentATM = (AgentsATM)getListeMecaniciens().get(indice);
	// on ajoute à la liste
	getListeMecaFPM().add(unAgentATM);
	getListeMecaniciens().remove(indice);
	initialiseListeMecaniciens(request);
	initialiseListeMecaFPM(request);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ENLEVER
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_ENLEVER() {
	return "NOM_PB_ENLEVER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_ENLEVER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	 on ajoute le mécanicien à la liste des mécaniciens pour la FPM
	int indice = (Services.estNumerique(getVAL_LB_MECA_FPM_SELECT()) ? Integer.parseInt(getVAL_LB_MECA_FPM_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	AgentsATM unAgentATM = (AgentsATM)getListeMecaFPM().get(indice);
	// on ajoute à la liste
	getListeMecaniciens().add(unAgentATM);
	getListeMecaFPM().remove(indice);
	initialiseListeMecaniciens(request);
	initialiseListeMecaFPM(request);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_VALIDER() {
	return "NOM_PB_VALIDER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	// on enregistre tous les mécaniciens sélectionnés pour la FPM
	if(getVAL_ST_NUMFICHE().equals("")){
		getTransaction().declarerErreur("La fiche d'entretien n'a pas encore été générée.Veuillez d'abord l'enregistré.");
		return false;
	}
	//suppression
	if(getVAL_ST_TITRE_ACTION().equals(ACTION_SUPPRESSION)){
		getPmAtmCourant().supprimerPM_ATM(getTransaction());
		
	//creation
	}else if (getVAL_ST_TITRE_ACTION().equals(ACTION_CREATION)){
		//parcours de la liste des mécaniciens pour la FPM
		if(getListeMecaFPM().size()>0){
			for(int i=0;i<getListeMecaFPM().size();i++){
				AgentsATM unAgentATM = (AgentsATM)getListeMecaFPM().get(i);
				PM_ATM unPM_ATM = new PM_ATM();
				unPM_ATM.creerPM_ATM(getTransaction(),getFpmCourant(),unAgentATM);
			}
		}
		
	}
	//si erreur
	if(getTransaction().isErreur()){
		return false;
	}
	//tout s'est bien passé
	commitTransaction();
	//on vide les zones
	setLB_MECA_FPM(LBVide);
	setLB_MECANICIENS(LBVide);
	addZone(getNOM_ST_TITRE_ACTION(),"");
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_MECANICIENS
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 */
private String [] getLB_MECANICIENS() {
	if (LB_MECANICIENS == null)
		LB_MECANICIENS = initialiseLazyLB();
	return LB_MECANICIENS;
}
/**
 * Setter de la liste:
 * LB_MECANICIENS
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 */
private void setLB_MECANICIENS(java.lang.String[] newLB_MECANICIENS) {
	LB_MECANICIENS = newLB_MECANICIENS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_MECANICIENS
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_MECANICIENS() {
	return "NOM_LB_MECANICIENS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_MECANICIENS_SELECT
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_MECANICIENS_SELECT() {
	return "NOM_LB_MECANICIENS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_MECANICIENS
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_MECANICIENS() {
	return getLB_MECANICIENS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_MECANICIENS
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_MECANICIENS_SELECT() {
	return getZone(getNOM_LB_MECANICIENS_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_MECA_FPM
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 */
private String [] getLB_MECA_FPM() {
	if (LB_MECA_FPM == null)
		LB_MECA_FPM = initialiseLazyLB();
	return LB_MECA_FPM;
}
/**
 * Setter de la liste:
 * LB_MECA_FPM
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 */
private void setLB_MECA_FPM(java.lang.String[] newLB_MECA_FPM) {
	LB_MECA_FPM = newLB_MECA_FPM;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_MECA_FPM
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_MECA_FPM() {
	return "NOM_LB_MECA_FPM";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_MECA_FPM_SELECT
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_MECA_FPM_SELECT() {
	return "NOM_LB_MECA_FPM_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_MECA_FPM
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_MECA_FPM() {
	return getLB_MECA_FPM();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_MECA_FPM
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_MECA_FPM_SELECT() {
	return getZone(getNOM_LB_MECA_FPM_SELECT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MECANICIENS
 * Date de création : (08/08/05 12:06:29)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_MECANICIENS() {
	return "NOM_ST_MECANICIENS";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MECANICIENS
 * Date de création : (08/08/05 12:06:29)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_MECANICIENS() {
	return getZone(getNOM_ST_MECANICIENS());
}
/**
 * @return Renvoie focus.
 */
public String getFocus() {
	if (focus == null) {
		focus=getDefaultFocus();
	}
	return focus;
}
/**
 * @param focus focus à définir.
 */
public void setFocus(String focus) {
	this.focus = focus;
}
/**
 * focus focus à définir.
 * @return focus
 */
public String getDefaultFocus() {
	return getNOM_LB_MECANICIENS_SELECT();
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (08/08/05 12:12:16)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (08/08/05 12:12:16)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODELE
 * Date de création : (08/08/05 12:12:16)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_MODELE() {
	return "NOM_ST_MODELE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODELE
 * Date de création : (08/08/05 12:12:16)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_MODELE() {
	return getZone(getNOM_ST_MODELE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (08/08/05 12:12:16)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOSERIE() {
	return "NOM_ST_NOSERIE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (08/08/05 12:12:16)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOSERIE() {
	return getZone(getNOM_ST_NOSERIE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINVENT
 * Date de création : (08/08/05 12:12:16)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOINVENT() {
	return "NOM_ST_NOINVENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINVENT
 * Date de création : (08/08/05 12:12:16)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOINVENT() {
	return getZone(getNOM_ST_NOINVENT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NUMFICHE
 * Date de création : (08/08/05 12:12:16)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NUMFICHE() {
	return "NOM_ST_NUMFICHE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NUMFICHE
 * Date de création : (08/08/05 12:12:16)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NUMFICHE() {
	return getZone(getNOM_ST_NUMFICHE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (08/08/05 12:12:16)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (08/08/05 12:12:16)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (08/08/05 12:43:49)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (08/08/05 12:43:49)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (08/08/05 12:45:54)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_ANNULER() {
	return "NOM_PB_ANNULER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (08/08/05 12:45:54)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
public boolean isSuppression() {
	return isSuppression;
}
public void setSuppression(boolean isSuppression) {
	this.isSuppression = isSuppression;
}
	public ArrayList<AgentsATM> getListeMecaniciens() {
		return listeMecaniciens;
	}
	public void setListeMecaniciens(ArrayList<AgentsATM> listeMecaniciens) {
		this.listeMecaniciens = listeMecaniciens;
	}
	public ArrayList<AgentsATM> getListeMecaFPM() {
		if(listeMecaFPM==null){
			listeMecaFPM = new ArrayList<AgentsATM>();
		}
		return listeMecaFPM;
	}
	public void setListeMecaFPM(ArrayList<AgentsATM> listeMecaFPM) {
		this.listeMecaFPM = listeMecaFPM;
	}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_AJOUTER
		if (testerParametre(request, getNOM_PB_AJOUTER())) {
			return performPB_AJOUTER(request);
		}

		//Si clic sur le bouton PB_ENLEVER
		if (testerParametre(request, getNOM_PB_ENLEVER())) {
			return performPB_ENLEVER(request);
		}

		//Si clic sur le bouton PB_VALIDER
		if (testerParametre(request, getNOM_PB_VALIDER())) {
			return performPB_VALIDER(request);
		}

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (08/08/05 13:36:09)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeMecaniciens_FPM.jsp";
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (08/08/05 13:36:09)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (08/08/05 13:36:09)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
	public boolean isTailleListeMecaFPM() {
		return tailleListeMecaFPM;
	}
	public void setTailleListeMecaFPM(boolean tailleListeMecaFPM) {
		this.tailleListeMecaFPM = tailleListeMecaFPM;
	}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
	public PMatInfos getPMatInfosCourant() {
		return pMatInfosCourant;
	}
	public void setPMatInfosCourant(PMatInfos pMatInfosCourant) {
		this.pMatInfosCourant = pMatInfosCourant;
	}
	public FPM getFpmCourant() {
		return fpmCourant;
	}
	public void setFpmCourant(FPM fpmCourant) {
		this.fpmCourant = fpmCourant;
	}
	public PM_ATM getPmAtmCourant() {
		return pmAtmCourant;
	}
	public void setPmAtmCourant(PM_ATM pmAtmCourant) {
		this.pmAtmCourant = pmAtmCourant;
	}
}
