package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.Agents;
import nc.mairie.seat.metier.AgentsATM;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.OT;
import nc.mairie.seat.metier.OT_ATM;
import nc.mairie.technique.*;
/**
 * Process OeMecaniciens_OT
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
*/
public class OeMecaniciens_OT extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2888728219810321635L;
	public static final int STATUT_TITRE_ACTION = 1;
	private String ACTION_SUPPRESSION = "Suppression";
//	private String ACTION_MODIFICATION = "Modification";
	private String ACTION_CREATION = "Création";
	private java.lang.String[] LB_MECANICIENS;
	private java.lang.String[] LB_MECA_OT;
	private OT_ATM otAtmCourant;
	private OT otCourant;
	private EquipementInfos equipementInfosCourant;
	private ArrayList<AgentsATM> listeMecaniciens;
	private ArrayList<AgentsATM> listeMecaOT;
	private String focus = null;
	public boolean isSuppression = false;
	public boolean tailleListeMecaOT;
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
		OT unOT = (OT)VariableGlobale.recuperer(request,"OT");
		if (unOT!=null){
			setOtCourant(unOT);
			addZone(getNOM_ST_NOOT(),getOtCourant().getNumeroot());
			EquipementInfos unEquipementInfos = (EquipementInfos)VariableGlobale.recuperer(request,"EQUIPEMENTINFOS");
			if(unEquipementInfos!=null){
				setEquipementInfosCourant(unEquipementInfos);
				addZone(getNOM_ST_NOINVENT(),getEquipementInfosCourant().getNumeroinventaire());
				addZone(getNOM_ST_NOIMMAT(),getEquipementInfosCourant().getNumeroimmatriculation());
				addZone(getNOM_ST_TYPE(),getEquipementInfosCourant().getDesignationtypeequip());
				addZone(getNOM_ST_MODELE(),getEquipementInfosCourant().getDesignationmodele());
				addZone(getNOM_ST_MARQUE(),getEquipementInfosCourant().getDesignationmarque());
			}
		}
		String titreAction = (String)VariableActivite.recuperer(this,"TITRE_ACTION");
		addZone(getNOM_ST_TITRE_ACTION(),titreAction);
		if(getVAL_ST_TITRE_ACTION().equals(ACTION_CREATION)){
			setSuppression(false);
		}else{
			setSuppression(true);
		}
		OT_ATM unOTATM = (OT_ATM)VariableActivite.recuperer(this,"OT_ATM");
		setOtAtmCourant(unOTATM);
		
		//l'OT n'a pas été enregistré
		if(getVAL_ST_NOOT().equals("")){
			getTransaction().declarerErreur("L'OT n'a pas encore été généré.Veuillez d'abord l'enregistrer.");
			return ;
		}
		
		initialiseListeMecaniciens(request);
		initialiseListeMecaOT(request);

	}
	if(getOtAtmCourant()!=null){
		if(getOtAtmCourant().getNumot()!=null){
			Agents unAgent = Agents.chercherAgents(getTransaction(),getOtAtmCourant().getMatricule());
			if(getTransaction().isErreur()){
				return ;
			}
			addZone(getNOM_ST_MECANICIENS(),unAgent.getNom()+" "+unAgent.getPrenom());
		}
	}
	if(getListeMecaOT().size()>0){
		setTailleListeMecaOT(true);
	}else{
		setTailleListeMecaOT(false);
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

public void initialiseListeMecaOT(javax.servlet.http.HttpServletRequest request) throws Exception{
	//initialise la liste des mécaniciens pour l'OT
	if(getListeMecaOT().size()>0){
		int [] tailles = {30};
		String [] padding = {"G"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (int i=0;i<getListeMecaOT().size();i++){
			AgentsATM unAgentATM = (AgentsATM)getListeMecaOT().get(i);
//			 on recherche l'agent concerné
			Agents unAgent = Agents.chercherAgents(getTransaction(),unAgentATM.getMatricule());
			if(getTransaction().isErreur()){
				return ;
			}	
			String agent = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
			String ligne [] = {agent };
			aFormat.ajouteLigne(ligne);
		}
		setLB_MECA_OT(aFormat.getListeFormatee());
	}else{
		setLB_MECA_OT(null);
	}
}

/**
 * Constructeur du process OeMecaniciens_OT.
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 */
public OeMecaniciens_OT() {
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
	// on ajoute le mécanicien à la liste des mécaniciens pour l'ot
	int indice = (Services.estNumerique(getVAL_LB_MECANICIENS_SELECT()) ? Integer.parseInt(getVAL_LB_MECANICIENS_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	AgentsATM unAgentATM = (AgentsATM)getListeMecaniciens().get(indice);
	// on ajoute à la liste
	getListeMecaOT().add(unAgentATM);
	getListeMecaniciens().remove(indice);
	initialiseListeMecaniciens(request);
	initialiseListeMecaOT(request);
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
//	 on ajoute le mécanicien à la liste des mécaniciens pour l'ot
	int indice = (Services.estNumerique(getVAL_LB_MECA_OT_SELECT()) ? Integer.parseInt(getVAL_LB_MECA_OT_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	AgentsATM unAgentATM = (AgentsATM)getListeMecaOT().get(indice);
	// on ajoute à la liste
	getListeMecaniciens().add(unAgentATM);
	getListeMecaOT().remove(indice);
	initialiseListeMecaniciens(request);
	initialiseListeMecaOT(request);
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
	// on enregistre tous les mécaniciens sélectionnés pour l'OT
	if(getVAL_ST_NOOT().equals("")){
		getTransaction().declarerErreur("L'OT n'a pas encore été généré.Veuillez d'abord l'enregistré.");
		return false;
	}
	//suppression
	if(getVAL_ST_TITRE_ACTION().equals(ACTION_SUPPRESSION)){
		getOtAtmCourant().supprimerOT_ATM(getTransaction());
		
	//creation
	}else if (getVAL_ST_TITRE_ACTION().equals(ACTION_CREATION)){
		//parcours de la liste des mécaniciens pour l'OT
		if(getListeMecaOT().size()>0){
			for(int i=0;i<getListeMecaOT().size();i++){
				AgentsATM unAgentATM = (AgentsATM)getListeMecaOT().get(i);
				OT_ATM unOtAtm = new OT_ATM();
				unOtAtm.creerOT_ATM(getTransaction(),getOtCourant(),unAgentATM);
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
	setLB_MECA_OT(LBVide);
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
 * LB_MECA_OT
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 */
private String [] getLB_MECA_OT() {
	if (LB_MECA_OT == null)
		LB_MECA_OT = initialiseLazyLB();
	return LB_MECA_OT;
}
/**
 * Setter de la liste:
 * LB_MECA_OT
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 */
private void setLB_MECA_OT(java.lang.String[] newLB_MECA_OT) {
	LB_MECA_OT = newLB_MECA_OT;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_MECA_OT
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_MECA_OT() {
	return "NOM_LB_MECA_OT";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_MECA_OT_SELECT
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_MECA_OT_SELECT() {
	return "NOM_LB_MECA_OT_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_MECA_OT
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_MECA_OT() {
	return getLB_MECA_OT();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_MECA_OT
 * Date de création : (08/08/05 12:00:59)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_MECA_OT_SELECT() {
	return getZone(getNOM_LB_MECA_OT_SELECT());
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
public java.lang.String getNOM_ST_NOIMMAT() {
	return "NOM_ST_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (08/08/05 12:12:16)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOIMMAT() {
	return getZone(getNOM_ST_NOIMMAT());
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
 * ST_NOOT
 * Date de création : (08/08/05 12:12:16)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOOT() {
	return "NOM_ST_NOOT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOOT
 * Date de création : (08/08/05 12:12:16)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOOT() {
	return getZone(getNOM_ST_NOOT());
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
	public ArrayList<AgentsATM> getListeMecaOT() {
		if(listeMecaOT==null){
			listeMecaOT = new ArrayList<AgentsATM>();
		}
		return listeMecaOT;
	}
	public void setListeMecaOT(ArrayList<AgentsATM> listeMecaOT) {
		this.listeMecaOT = listeMecaOT;
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
	return "OeMecaniciens_OT.jsp";
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
	public boolean isTailleListeMecaOT() {
		return tailleListeMecaOT;
	}
	public void setTailleListeMecaOT(boolean tailleListeMecaOT) {
		this.tailleListeMecaOT = tailleListeMecaOT;
	}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
	public EquipementInfos getEquipementInfosCourant() {
		return equipementInfosCourant;
	}
	public void setEquipementInfosCourant(EquipementInfos equipementInfosCourant) {
		this.equipementInfosCourant = equipementInfosCourant;
	}
	public OT getOtCourant() {
		return otCourant;
	}
	public void setOtCourant(OT otCourant) {
		this.otCourant = otCourant;
	}
	public OT_ATM getOtAtmCourant() {
		return otAtmCourant;
	}
	public void setOtAtmCourant(OT_ATM otAtmCourant) {
		this.otAtmCourant = otAtmCourant;
	}
}
