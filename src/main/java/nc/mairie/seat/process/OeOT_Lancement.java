package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.OT;
import nc.mairie.seat.metier.Planning;
import nc.mairie.technique.*;
/**
 * Process OeOT_Lancement
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
*/
public class OeOT_Lancement extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6772777584078293756L;
	public static final int STATUT_MODIFIER_OT = 5;
	public static final int STATUT_DETAILS_OT = 4;
	public static final int STATUT_RETOUROT = 3;
	public static final int STATUT_DETAILS_PEPERSO = 2;
	public static final int STATUT_IMPRIMER = 1;
	private java.lang.String[] LB_OT;
	private String focus = null;
	private ArrayList<Planning> listOTProp;
	String firstNoOT;
	String lastNoOT;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	//on récupère le numéro OT pour lister les OT générés
	firstNoOT = (String)VariableGlobale.recuperer(request, "NUMOT");
	if(firstNoOT.equals("")){
		firstNoOT = "0";
	}
	lastNoOT = (String)VariableGlobale.recuperer(request, "NUMOTFIN");
	if(lastNoOT.equals("")){
		lastNoOT = "0";
	}
	// initialisation des la liste OT avec les OT temporaires
	listOTProp = Planning.listerPlanningEnCours(getTransaction(),firstNoOT);
	int [] tailles = {10,10,40};
	String [] padding = {"G","G","G"};
	FormateListe aFormat = new FormateListe(tailles,padding,false);
	String numeroot = "";
	String immat = "";
	if (getListOTProp().size()>0){
		for (int i =0;i<getListOTProp().size();i++){
			Planning unPlanning = (Planning)getListOTProp().get(i);
			numeroot = unPlanning.getCodeot();
			immat = unPlanning.getNumeroimmatriculation();
			if (i>0){
				Planning unPlanningAvant = (Planning)getListOTProp().get(i-1);
				if (unPlanning.getCodeot().trim().equals(unPlanningAvant.getCodeot().trim())){
					numeroot = "";
					immat = "";
				}
			}
			String ligne [] = { numeroot,immat,unPlanning.getLibelleentretien()};
			aFormat.ajouteLigne(ligne);
		}
		setLB_OT(aFormat.getListeFormatee());
	}else{
		setLB_OT(null);
	}
	VariableGlobale.enlever(request,"DETAILS");
	
}
/**
 * Constructeur du process OeOT_Lancement.
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
public OeOT_Lancement() {
	super();
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (19/07/05 14:46:20)
 * on supprime tous les OT créés (dans la liste) et on retourne à la fenêtre de lancement
 * @author : Générateur de process
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	// on supprime les ot créer
	OT unOT;
	// pour chaque OT créée on les supprime
	int premierOT = Integer.parseInt(firstNoOT);
	int dernierOT = Integer.parseInt(lastNoOT);
	if (premierOT>0){
		for (int indice= premierOT;indice<dernierOT+1;indice++){
			unOT = OT.chercherOT(getTransaction(),String.valueOf(indice));
			if(getTransaction().isErreur()){
				return false;
			}
			//
			for (int i=0;i<getListOTProp().size();i++){
				Planning unPlanning = (Planning)getListOTProp().get(i);
				if(unOT.getNumeroot()!=null){
					if(unOT.getNumeroot().trim().equals(unPlanning.getCodeot().trim())){
						unOT = OT.chercherOT(getTransaction(),unPlanning.getCodeot());
						if(getTransaction().isErreur()){
							return false;
						}
						unOT.suppressionOT(getTransaction());
						if(getTransaction().isErreur()){
							return false;
						}
					}
				}/*else{
					unOT = OT.chercherOT(getTransaction(),unPlanning.getCodeot());
					if(getTransaction().isErreur()){
						return false;
					}
					unOT.suppressionOT(getTransaction());
					if(getTransaction().isErreur()){
						return false;
					}*/
				//}
			}
		}
	}
	
	/*if(getListOTProp().size()>0){
		for (int i=0;i<getListOTProp().size();i++){
			Planning unPlanning = (Planning)getListOTProp().get(i);
			if(unOT.getNumeroot()!=null){
				if(!unOT.getNumeroot().trim().equals(unPlanning.getCodeot().trim())){
					unOT = OT.chercherOT(getTransaction(),unPlanning.getCodeot());
					if(getTransaction().isErreur()){
						return false;
					}
					unOT.suppressionOT(getTransaction());
					if(getTransaction().isErreur()){
						return false;
					}
				}
			}else{
				unOT = OT.chercherOT(getTransaction(),unPlanning.getCodeot());
				if(getTransaction().isErreur()){
					return false;
				}
				unOT.suppressionOT(getTransaction());
				if(getTransaction().isErreur()){
					return false;
				}
			}
		}
	}*/
	//tout s'est bien passé
	commitTransaction();
	// on retourne au planning
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_OT
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_OK_OT() {
	return "NOM_PB_OK_OT";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
public boolean performPB_OK_OT(javax.servlet.http.HttpServletRequest request) throws Exception {
	int numligne = (Services.estNumerique(getZone(getNOM_LB_OT_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_OT_SELECT())) : -1);
	if (numligne == -1 || getListOTProp().size() == 0 || numligne > getListOTProp().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","OT"));
		return false;
	}
	Planning unPlanning = (Planning)getListOTProp().get(numligne);
	EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfos(getTransaction(),unPlanning.getNumeroinventaire());
	if (getTransaction().isErreur()){
		return false;
	}
	if(null==unEquipementInfos){
		unEquipementInfos = new EquipementInfos();
	}
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",unEquipementInfos);
	setStatut(STATUT_DETAILS_PEPERSO,true);
	VariableGlobale.ajouter(request,"DETAILS","true");
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_VALIDER() {
	return "NOM_PB_VALIDER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	int numligne = (Services.estNumerique(getZone(getNOM_LB_OT_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_OT_SELECT())) : -1);
	if (numligne == -1 || getListOTProp().size() == 0 || numligne > getListOTProp().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","OT"));
		return false;
	}
	Planning unPlanning = (Planning)getListOTProp().get(numligne);
	if(getTransaction().isErreur()){
		return false;
	}
	OT unOT = OT.chercherOT(getTransaction(),unPlanning.getCodeot());
	if (getTransaction().isErreur()){
		return false;
	}
	if(null==unOT){
		unOT = new OT();
	}
	EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfos(getTransaction(),unPlanning.getNumeroinventaire());
	if(getTransaction().isErreur()){
		return false;
	}
	if(null==unEquipementInfos){
		unEquipementInfos = new EquipementInfos();
	}
	//on renseigne la variable globale
	VariableGlobale.ajouter(request,"OT",unOT);
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",unEquipementInfos);
	//Window.
	setStatut(STATUT_IMPRIMER,true);
	return true;
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_OT
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
private String [] getLB_OT() {
	if (LB_OT == null)
		LB_OT = initialiseLazyLB();
	return LB_OT;
}
/**
 * Setter de la liste:
 * LB_OT
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
private void setLB_OT(java.lang.String[] newLB_OT) {
	LB_OT = newLB_OT;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_OT
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_OT() {
	return "NOM_LB_OT";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_OT_SELECT
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_OT_SELECT() {
	return "NOM_LB_OT_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_OT
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_OT() {
	return getLB_OT();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_OT
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_OT_SELECT() {
	return getZone(getNOM_LB_OT_SELECT());
}

/**
 * @param focus focus à définir.
 */
public String getDefaultFocus() {
	return getNOM_PB_VALIDER();
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

	public ArrayList<Planning> getListOTProp() {
		return listOTProp;
	}
	public void setListOTProp(ArrayList<Planning> listOTProp) {
		this.listOTProp = listOTProp;
	}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (24/08/05 08:57:47)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_ANNULER() {
	return "NOM_PB_ANNULER";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_DETAILS_OT
 * Date de création : (24/08/05 08:59:45)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_DETAILS_OT() {
	return "NOM_PB_DETAILS_OT";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (24/08/05 08:59:45)
 * @author : Générateur de process
 */
public boolean performPB_DETAILS_OT(javax.servlet.http.HttpServletRequest request) throws Exception {
	int numligne = (Services.estNumerique(getZone(getNOM_LB_OT_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_OT_SELECT())) : -1);
	if (numligne == -1 || getListOTProp().size() == 0 || numligne > getListOTProp().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","OT"));
		return false;
	}
	Planning unPlanning = (Planning)getListOTProp().get(numligne);
	OT unOT = OT.chercherOT(getTransaction(),unPlanning.getCodeot());
	if(getTransaction().isErreur()){
		return false;
	}
	if(unOT==null){
		unOT = new OT();
	}
	EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfos(getTransaction(),unPlanning.getNumeroinventaire());
	if (getTransaction().isErreur()){
		return false;
	}
	if(null==unEquipementInfos){
		unEquipementInfos = new EquipementInfos();
	}
	// on débranche sur la visu de l'OT
	VariableGlobale.ajouter(request,"OT",unOT);
	VariableActivite.ajouter(this,"DEBRANCHE","TRUE");
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",unEquipementInfos);
	setStatut(STATUT_DETAILS_OT,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RETOUR
 * Date de création : (24/08/05 09:55:42)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_RETOUR() {
	return "NOM_PB_RETOUR";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (24/08/05 09:55:42)
 * @author : Générateur de process
 */
public boolean performPB_RETOUR(javax.servlet.http.HttpServletRequest request) throws Exception {
	VariableActivite.ajouter(this,"RETOUR","TRUE");
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_MODIFIER_OT
		if (testerParametre(request, getNOM_PB_MODIFIER_OT())) {
			return performPB_MODIFIER_OT(request);
		}

		//Si clic sur le bouton PB_RETOUR
		if (testerParametre(request, getNOM_PB_RETOUR())) {
			return performPB_RETOUR(request);
		}

		//Si clic sur le bouton PB_DETAILS_OT
		if (testerParametre(request, getNOM_PB_DETAILS_OT())) {
			return performPB_DETAILS_OT(request);
		}

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_OK_OT
		if (testerParametre(request, getNOM_PB_OK_OT())) {
			return performPB_OK_OT(request);
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
 * Date de création : (26/10/05 14:08:36)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeOT_Lancement.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER_OT
 * Date de création : (26/10/05 14:08:36)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_MODIFIER_OT() {
	return "NOM_PB_MODIFIER_OT";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (26/10/05 14:08:36)
 * @author : Générateur de process
 */
public boolean performPB_MODIFIER_OT(javax.servlet.http.HttpServletRequest request) throws Exception {
	int numligne = (Services.estNumerique(getZone(getNOM_LB_OT_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_OT_SELECT())) : -1);
	if (numligne == -1 || getListOTProp().size() == 0 || numligne > getListOTProp().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","OT"));
		return false;
	}
	Planning unPlanning = (Planning)getListOTProp().get(numligne);
	OT unOT = OT.chercherOT(getTransaction(),unPlanning.getCodeot());
	if(getTransaction().isErreur()){
		return false;
	}
	if(null==unOT){
		unOT = new OT();
	}
	EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfos(getTransaction(),unPlanning.getNumeroinventaire());
	if (getTransaction().isErreur()){
		return false;
	}
	if(null==unEquipementInfos){
		unEquipementInfos = new EquipementInfos();
	}
	// on débranche sur la visu de l'OT
	VariableGlobale.ajouter(request,"OT",unOT);
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",unEquipementInfos);
	setStatut(STATUT_MODIFIER_OT,true);
	return true;
}
}
