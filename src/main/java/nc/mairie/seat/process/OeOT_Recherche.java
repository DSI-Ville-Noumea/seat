package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.OT;
import nc.mairie.technique.*;
/**
 * Process OeOT_Recherche
 * Date de création : (09/06/05 08:25:03)
* 
*/
public class OeOT_Recherche extends nc.mairie.technique.BasicProcess {
	private java.lang.String[] LB_OT;
	private java.lang.String[] LB_EQUIPEMENT;
	private ArrayList listeEquipementInfos;
	private OT OtCourant;
	private ArrayList listeOT;
	private EquipementInfos equipementInfosCourant;
	private String focus = null;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (09/06/05 08:25:03)
* 
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	String param = getZone(getNOM_EF_DESIGNATION());
	//initialisation des listes
	if (null!=(equipementInfosCourant)){
		java.util.ArrayList equipement = EquipementInfos.chercherListEquipementInfosTous(getTransaction(),param);
		if(getTransaction().isErreur()){
			getTransaction().declarerErreur(getTransaction().traiterErreur());
			return ;
		}
		if (null == equipement){
			System.out.println("Aucun équipement enregistré dans la base.");
		}
		setListeEquipementInfos(equipement);
		//java.util.ArrayList ot = Planning.chercherPlanningEquip(getTransaction(),equipementInfosCourant.getNumeroinventaire());
		java.util.ArrayList listOt = OT.listerOTEquip(getTransaction(),getEquipementInfosCourant().getNumeroinventaire());
		if (null == listOt){
			System.out.println("Aucun OT enregistré dans la base.");
		}
		setListeOT(listOt);
	}
	performPB_OK(request);

}

public void initialiseOT(javax.servlet.http.HttpServletRequest request,ArrayList a) throws Exception{
	setListeOT(a);
//	Si au moins un OT
	if (a.size() !=0 ) {
		int tailles [] = {12};
		FormateListe aFormat = new FormateListe(tailles);
		for (java.util.ListIterator list = a.listIterator(); list.hasNext(); ) {
			OT unOT = (OT)list.next();
			String ligne [] = { unOT.getNumeroot()};
			aFormat.ajouteLigne(ligne);
		}
		setLB_OT(aFormat.getListeFormatee());
	} else {
		setLB_OT(null);
	}
}

/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (09/06/05 08:25:03)
* 
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_OK
		if (testerParametre(request, getNOM_PB_OK())) {
			return performPB_OK(request);
		}

		//Si clic sur le bouton PB_OK_EQUIPEMENT
		if (testerParametre(request, getNOM_PB_OK_EQUIPEMENT())) {
			return performPB_OK_EQUIPEMENT(request);
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
 * Constructeur du process OeOT_Recherche.
 * Date de création : (09/06/05 08:25:03)
* 
 */
public OeOT_Recherche() {
	super();
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (09/06/05 08:25:03)
* 
 */
@Override
public String getJSP() {
	return "OeOT_Recherche.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (09/06/05 08:25:03)
* 
 */
public java.lang.String getNOM_PB_ANNULER() {
	return "NOM_PB_ANNULER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (09/06/05 08:25:03)
* 
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	equipementInfosCourant = null;
	OtCourant = null;
	
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (09/06/05 08:25:03)
* 
 */
public java.lang.String getNOM_PB_OK() {
	return "NOM_PB_OK";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (09/06/05 08:25:03)
* 
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
	String param = getZone(getNOM_EF_DESIGNATION());
	ArrayList resultatEquipementInfos = EquipementInfos.chercherListEquipementInfosTous(getTransaction(),param);
	// on remplit la liste des équipements
	setListeEquipementInfos(resultatEquipementInfos);
	if(resultatEquipementInfos.size()>0){
		//les élèments de la liste 
		int [] tailles = {10,15,15,15,5,10};
		String [] champs = {"numeroinventaire","numeroimmatriculation","designationmarque","designationmodele","designationtypeequip","datemiseencirculation"};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"D","D","G","G","G","C"};
		
		setLB_EQUIPEMENT(new FormateListe(tailles,resultatEquipementInfos,champs,padding,true).getListeFormatee());
	}else{
		setLB_EQUIPEMENT(LBVide);
	}
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_EQUIPEMENT
 * Date de création : (09/06/05 08:25:03)
* 
 */
public java.lang.String getNOM_PB_OK_EQUIPEMENT() {
	return "NOM_PB_OK_EQUIPEMENT";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (09/06/05 08:25:03)
* 
 */
public boolean performPB_OK_EQUIPEMENT(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_EQUIPEMENT_SELECT()) ? Integer.parseInt(getVAL_LB_EQUIPEMENT_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	EquipementInfos monEquipementInfos = (EquipementInfos)getListeEquipementInfos().get(indice);
//	if (getTransaction().isErreur()){
//		return false;
//	}
	setEquipementInfosCourant(monEquipementInfos);
	//ArrayList resultat = Planning.chercherPlanningEquip(getTransaction(),equipementInfosCourant.getNumeroinventaire());
	ArrayList resultat = OT.listerOTEquip(getTransaction(),monEquipementInfos.getNumeroinventaire());
	if(getTransaction().isErreur()){
		return false;
	}
	// on remplit la liste des BPC
	initialiseOT(request,resultat);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (09/06/05 08:25:03)
* 
 */
public java.lang.String getNOM_PB_VALIDER() {
	return "NOM_PB_VALIDER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (09/06/05 08:25:03)
* 
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_OT_SELECT()) ? Integer.parseInt(getVAL_LB_OT_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	OT monOT = (OT)getListeOT().get(indice);
	//Planning monPlanning = (Planning)getListeOT().get(indice);
	/*if (getTransaction().isErreur()){
		return false;
	}
	OT monOT = OT.chercherOT(getTransaction(),monPlanning.getCodeot());
	if (getTransaction().isErreur()){
		return false;
	}*/
	if(monOT==null){
		monOT = new OT();
	}
	setOtCourant(monOT);
	if(null==getEquipementInfosCourant()){
		setEquipementInfosCourant(new EquipementInfos());
	}
//	On met la variable activité
	VariableGlobale.ajouter(request, "EQUIPEMENTINFOS", getEquipementInfosCourant());
	VariableGlobale.ajouter(request, "OT", monOT);
	setStatut(STATUT_PROCESS_APPELANT);
	indice = -1;
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DESIGNATION
 * Date de création : (09/06/05 08:25:03)
* 
 */
public java.lang.String getNOM_EF_DESIGNATION() {
	return "NOM_EF_DESIGNATION";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DESIGNATION
 * Date de création : (09/06/05 08:25:03)
* 
 */
public java.lang.String getVAL_EF_DESIGNATION() {
	return getZone(getNOM_EF_DESIGNATION());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_BPC
 * Date de création : (09/06/05 08:25:03)
* 
 */
private String [] getLB_OT() {
	if (LB_OT == null)
		LB_OT = initialiseLazyLB();
	return LB_OT;
}
/**
 * Setter de la liste:
 * LB_BPC
 * Date de création : (09/06/05 08:25:03)
* 
 */
private void setLB_OT(java.lang.String[] newLB_OT) {
	LB_OT = newLB_OT;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_BPC
 * Date de création : (09/06/05 08:25:03)
* 
 */
public java.lang.String getNOM_LB_OT() {
	return "NOM_LB_OT";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_BPC_SELECT
 * Date de création : (09/06/05 08:25:03)
* 
 */
public java.lang.String getNOM_LB_OT_SELECT() {
	return "NOM_LB_OT_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_BPC
 * Date de création : (09/06/05 08:25:03)
* 
 */
public java.lang.String [] getVAL_LB_OT() {
	return getLB_OT();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_BPC
 * Date de création : (09/06/05 08:25:03)
* 
 */
public java.lang.String getVAL_LB_OT_SELECT() {
	return getZone(getNOM_LB_OT_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_EQUIPEMENT
 * Date de création : (09/06/05 08:25:03)
* 
 */
private String [] getLB_EQUIPEMENT() {
	if (LB_EQUIPEMENT == null)
		LB_EQUIPEMENT = initialiseLazyLB();
	return LB_EQUIPEMENT;
}
/**
 * Setter de la liste:
 * LB_EQUIPEMENT
 * Date de création : (09/06/05 08:25:03)
* 
 */
private void setLB_EQUIPEMENT(java.lang.String[] newLB_EQUIPEMENT) {
	LB_EQUIPEMENT = newLB_EQUIPEMENT;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_EQUIPEMENT
 * Date de création : (09/06/05 08:25:03)
* 
 */
public java.lang.String getNOM_LB_EQUIPEMENT() {
	return "NOM_LB_EQUIPEMENT";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_EQUIPEMENT_SELECT
 * Date de création : (09/06/05 08:25:03)
* 
 */
public java.lang.String getNOM_LB_EQUIPEMENT_SELECT() {
	return "NOM_LB_EQUIPEMENT_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_EQUIPEMENT
 * Date de création : (09/06/05 08:25:03)
* 
 */
public java.lang.String [] getVAL_LB_EQUIPEMENT() {
	return getLB_EQUIPEMENT();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_EQUIPEMENT
 * Date de création : (09/06/05 08:25:03)
* 
 */
public java.lang.String getVAL_LB_EQUIPEMENT_SELECT() {
	return getZone(getNOM_LB_EQUIPEMENT_SELECT());
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
 * @return focus focus à définir.
 */
public String getDefaultFocus() {
	return getNOM_EF_DESIGNATION();
}
	/**
	 * @return Renvoie bpcCourant.
	 */
	public OT getOtCourant() {
		return OtCourant;
	}
	/**
	 * @param bpcInfosCourant bpcInfosCourant à définir.
	 */
	public void setOtCourant(OT otCourant) {
		this.OtCourant = otCourant;
	}
	/**
	 * @return Renvoie equipementInfosCourant.
	 */
	public EquipementInfos getEquipementInfosCourant() {
		return equipementInfosCourant;
	}
	/**
	 * @param equipementInfosCourant equipementInfosCourant à définir.
	 */
	public void setEquipementInfosCourant(EquipementInfos equipementInfosCourant) {
		this.equipementInfosCourant = equipementInfosCourant;
	}
	
	/**
	 * @return Renvoie listeEquipementInfos.
	 */
	public ArrayList getListeEquipementInfos() {
		return listeEquipementInfos;
	}
	/**
	 * @param listeEquipementInfos listeEquipementInfos à définir.
	 */
	public void setListeEquipementInfos(ArrayList listeEquipementInfos) {
		this.listeEquipementInfos = listeEquipementInfos;
	}
	/**
	 * @return Renvoie listeBPC.
	 */
	public ArrayList getListeOT() {
		return listeOT;
	}
	/**
	 * @param listeBPC listeBPC à définir.
	 */
	public void setListeOT(ArrayList listeBPC) {
		this.listeOT = listeBPC;
	}
}
