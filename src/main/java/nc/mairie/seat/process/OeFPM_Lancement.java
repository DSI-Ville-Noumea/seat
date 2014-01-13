package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.PM_Planning;
import nc.mairie.seat.metier.FPM;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.technique.*;
/**
 * Process OeFPM_Lancement
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
*/
public class OeFPM_Lancement extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5719446004008137870L;
	public static final int STATUT_MODIFIER_FPM = 5;
	public static final int STATUT_DETAILS_FPM = 4;
	public static final int STATUT_RETOURFPM = 3;
	public static final int STATUT_DETAILS_PMPEPERSO = 2;
	public static final int STATUT_IMPRIMER = 1;
	private java.lang.String[] LB_ENTRETIENS;
	private java.lang.String[] LB_Fiche;
	private String focus = null;
	private ArrayList<PM_Planning> listFicheProp;
	String firstNoFiche;
	String lastNoFiche;
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
	//on récupère le numéro fpm pour lister les fpm générés
	firstNoFiche = (String)VariableGlobale.recuperer(request, "NUMFICHE");
	if(firstNoFiche.equals("")){
		firstNoFiche = "0";
	}
	lastNoFiche = (String)VariableGlobale.recuperer(request, "NUMFICHEFIN");
	if(lastNoFiche.equals("")){
		lastNoFiche = "0";
	}
	// initialisation des la liste avec les fpm temporaires
	listFicheProp = PM_Planning.listerPlanningEnCours(getTransaction(),firstNoFiche);
	int [] tailles = {10,10,40};
	String [] padding = {"G","G","G"};
	FormateListe aFormat = new FormateListe(tailles,padding,false);
	String numfiche = "";
	String serie = "";
	if (getListFicheProp().size()>0){
		for (int i =0;i<getListFicheProp().size();i++){
			PM_Planning unPlanning = (PM_Planning)getListFicheProp().get(i);
			numfiche = unPlanning.getNumfiche();
			serie = unPlanning.getPmserie();
			if (i>0){
				PM_Planning unPlanningAvant = (PM_Planning)getListFicheProp().get(i-1);
				if (unPlanning.getNumfiche().trim().equals(unPlanningAvant.getNumfiche().trim())){
					numfiche = "";
					serie = "";
				}
			}
			String ligne [] = { numfiche,serie,unPlanning.getLibelleentretien()};
			aFormat.ajouteLigne(ligne);
		}
		setLB_Fiche(aFormat.getListeFormatee());
	}else{
		setLB_Fiche(null);
	}
	VariableGlobale.enlever(request,"DETAILS");
}
/**
 * Constructeur du process OeOT_Lancement.
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
public OeFPM_Lancement() {
	super();
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (19/07/05 14:46:20)
 * on supprime tous les FPM créés (dans la liste) et on retourne à la fenêtre de lancement
 * @author : Générateur de process
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	// on supprime les fpm créer
	FPM unFPM;
	// pour chaque FPM créée on les supprime
	int premierFPM = Integer.parseInt(firstNoFiche);
	int dernierFPM = Integer.parseInt(lastNoFiche);
	if (premierFPM>0){
		for (int indice= premierFPM;indice<dernierFPM+1;indice++){
			unFPM = FPM.chercherFPM(getTransaction(),String.valueOf(indice));
			if(getTransaction().isErreur()){
				return false;
			}
			//
			for (int i=0;i<getListFicheProp().size();i++){
				PM_Planning unPlanning = (PM_Planning)getListFicheProp().get(i);
				if(unFPM.getNumfiche()!=null){
					if(unFPM.getNumfiche().trim().equals(unPlanning.getNumfiche().trim())){
						unFPM = FPM.chercherFPM(getTransaction(),unPlanning.getNumfiche());
						if(getTransaction().isErreur()){
							return false;
						}
						unFPM.suppressionFPM(getTransaction());
						if(getTransaction().isErreur()){
							return false;
						}
					}
				}
			}
		}
	}

	//tout s'est bien passé
	commitTransaction();
	// on retourne au planning
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_FPM
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_OK_FPM() {
	return "NOM_PB_OK_FPM";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
public boolean performPB_OK_FPM(javax.servlet.http.HttpServletRequest request) throws Exception {
	int numligne = (Services.estNumerique(getZone(getNOM_LB_ENTRETIENS_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_ENTRETIENS_SELECT())) : -1);
	if (numligne == -1 || getListFicheProp().size() == 0 || numligne > getListFicheProp().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","fiches d'entretiens du petit matériel"));
		return false;
	}
	PM_Planning unPlanning = (PM_Planning)getListFicheProp().get(numligne);
	PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),unPlanning.getPminv());
	if (getTransaction().isErreur()){
		return false;
	}
	if(null==unPMatInfos){
		unPMatInfos = new PMatInfos();
	}
	VariableGlobale.ajouter(request,"PMATINFOS",unPMatInfos);
	setStatut(STATUT_DETAILS_PMPEPERSO,true);
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
	int numligne = (Services.estNumerique(getZone(getNOM_LB_ENTRETIENS_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_ENTRETIENS_SELECT())) : -1);
	if (numligne == -1 || getListFicheProp().size() == 0 || numligne > getListFicheProp().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","fiches d'entretiens du petit matériel"));
		return false;
	}
	PM_Planning unPlanning = (PM_Planning)getListFicheProp().get(numligne);
	if(getTransaction().isErreur()){
		return false;
	}
	FPM unPMatFiche = FPM.chercherFPM(getTransaction(),unPlanning.getNumfiche());
	if (getTransaction().isErreur()){
		return false;
	}
	PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),unPlanning.getPminv());
	if(getTransaction().isErreur()){
		return false;
	}
	if(unPMatInfos==null){
		unPMatInfos = new PMatInfos();
	}
	//on renseigne la variable globale
	VariableGlobale.ajouter(request,"FPM",unPMatFiche);
	VariableGlobale.ajouter(request,"PMATINFOS",unPMatInfos);
	//Window.
	setStatut(STATUT_IMPRIMER,true);
	return true;
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_ENTRETIENS
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
private String [] getLB_ENTRETIENS() {
	if (LB_ENTRETIENS == null)
		LB_ENTRETIENS = initialiseLazyLB();
	return LB_ENTRETIENS;
}
/**
 * Setter de la liste:
 * LB_ENTRETIENS
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
@SuppressWarnings("unused")
private void setLB_ENTRETIENS(java.lang.String[] newLB_ENTRETIENS) {
	LB_ENTRETIENS = newLB_ENTRETIENS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_ENTRETIENS
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_ENTRETIENS() {
	return "NOM_LB_ENTRETIENS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_ENTRETIENS_SELECT
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_ENTRETIENS_SELECT() {
	return "NOM_LB_ENTRETIENS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_ENTRETIENS
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_ENTRETIENS() {
	return getLB_ENTRETIENS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_ENTRETIENS
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_ENTRETIENS_SELECT() {
	return getZone(getNOM_LB_ENTRETIENS_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_Fiche
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
private String [] getLB_Fiche() {
	if (LB_Fiche == null)
		LB_Fiche = initialiseLazyLB();
	return LB_Fiche;
}
/**
 * Setter de la liste:
 * LB_Fiche
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
private void setLB_Fiche(java.lang.String[] newLB_Fiche) {
	LB_Fiche = newLB_Fiche;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_Fiche
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_Fiche() {
	return "NOM_LB_Fiche";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_OT_SELECT
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_Fiche_SELECT() {
	return "NOM_LB_Fiche_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_Fiche
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_Fiche() {
	return getLB_Fiche();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_Fiche
 * Date de création : (19/07/05 14:46:20)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_Fiche_SELECT() {
	return getZone(getNOM_LB_Fiche_SELECT());
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

	public ArrayList<PM_Planning> getListFicheProp() {
		return listFicheProp;
	}
	public void setListFicheProp(ArrayList<PM_Planning> listOTProp) {
		this.listFicheProp = listOTProp;
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
 * PB_DETAILS_FPM
 * Date de création : (24/08/05 08:59:45)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_DETAILS_FPM() {
	return "NOM_PB_DETAILS_FPM";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (24/08/05 08:59:45)
 * @author : Générateur de process
 */
public boolean performPB_DETAILS_FPM(javax.servlet.http.HttpServletRequest request) throws Exception {
	int numligne = (Services.estNumerique(getZone(getNOM_LB_ENTRETIENS_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_ENTRETIENS_SELECT())) : -1);
	if (numligne == -1 || getListFicheProp().size() == 0 || numligne > getListFicheProp().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Fiches d'entretiens du petit matériel"));
		return false;
	}
	PM_Planning unPlanning = (PM_Planning)getListFicheProp().get(numligne);
	FPM unPMatFiche = FPM.chercherFPM(getTransaction(),unPlanning.getNumfiche());
	if(getTransaction().isErreur()){
		return false;
	}
	if(null==unPMatFiche){
		unPMatFiche = new FPM();
	}
	PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),unPlanning.getPminv());
	if (getTransaction().isErreur()){
		return false;
	}
	if(null==unPMatInfos){
		unPMatInfos = new PMatInfos();
	}
	// on débranche sur la visu de les FPM
	VariableGlobale.ajouter(request,"FPM",unPMatFiche);
	VariableActivite.ajouter(this,"DEBRANCHE","TRUE");
	VariableGlobale.ajouter(request,"PMATINFOS",unPMatInfos);
	setStatut(STATUT_DETAILS_FPM,true);
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

		//Si clic sur le bouton PB_MODIFIER_FPM
		if (testerParametre(request, getNOM_PB_MODIFIER_FPM())) {
			return performPB_MODIFIER_FPM(request);
		}

		//Si clic sur le bouton PB_RETOUR
		if (testerParametre(request, getNOM_PB_RETOUR())) {
			return performPB_RETOUR(request);
		}

		//Si clic sur le bouton PB_DETAILS_FPM
		if (testerParametre(request, getNOM_PB_DETAILS_FPM())) {
			return performPB_DETAILS_FPM(request);
		}

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_OK_FPM
		if (testerParametre(request, getNOM_PB_OK_FPM())) {
			return performPB_OK_FPM(request);
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
	return "OeFPM_Lancement.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER_FPM
 * Date de création : (26/10/05 14:08:36)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_MODIFIER_FPM() {
	return "NOM_PB_MODIFIER_FPM";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (26/10/05 14:08:36)
 * @author : Générateur de process
 */
public boolean performPB_MODIFIER_FPM(javax.servlet.http.HttpServletRequest request) throws Exception {
	int numligne = (Services.estNumerique(getZone(getNOM_LB_ENTRETIENS_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_ENTRETIENS_SELECT())) : -1);
	if (numligne == -1 || getListFicheProp().size() == 0 || numligne > getListFicheProp().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","fiches d'entretiens du petit matériel"));
		return false;
	}
	PM_Planning unPlanning = (PM_Planning)getListFicheProp().get(numligne);
	FPM unPMatFiche = FPM.chercherFPM(getTransaction(),unPlanning.getNumfiche());
	if(getTransaction().isErreur()){
		return false;
	}
	PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),unPlanning.getPminv());
	if (getTransaction().isErreur()){
		return false;
	}
	// on débranche sur la visu de la FPM
	VariableGlobale.ajouter(request,"FPM",unPMatFiche);
	VariableGlobale.ajouter(request,"PMATINFOS",unPMatInfos);
	setStatut(STATUT_MODIFIER_FPM,true);
	return true;
}
}
