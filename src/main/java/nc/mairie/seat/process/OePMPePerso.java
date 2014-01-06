package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.PM_PePerso;
import nc.mairie.seat.metier.PM_Planning;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.technique.*;
/**
 * Process OePeBase
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
*/
public class OePMPePerso extends nc.mairie.technique.BasicProcess {
	public static final int STATUT_ACCES_FPM = 5;
	public static final int STATUT_RETOURFPM = 4;
	public static final int STATUT_RECHERCHEEQUIP = 3;
	public static final int STATUT_MODIFIER = 2;
	public static final int STATUT_AJOUTER = 1;
	private String ACTION_SUPPRESSION = "Suppression d'un entretien pour un petit matériel.<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private ArrayList listePePerso = null;
	private java.lang.String[] LB_PE;
	private PMatInfos pMatInfosCourant;
	private PM_Planning pmPlanningCourant;
	private PM_PePerso pmPePersoCourant;
	public int isVide=0;
	private String focus = null;
	private String tri = "libelleentretien";
	private String param ="";
	public boolean details;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request, "PMATINFOS");
	if (unPMatInfos!=null){
		if (unPMatInfos.existePMatInfosPm(getTransaction(),unPMatInfos.getPminv())){
			setPMatInfosCourant(unPMatInfos);
		}else{
			VariableGlobale.enlever(request,"PMATINFOS");
		}
	}
//	 quand appuie sur entrée
	if(!getZone(getNOM_EF_PMINV()).equals("")){
		performPB_EQUIP(request);
		addZone(getNOM_EF_PMINV(),"");
	}
	// Pour afficher le bouton de retour aux FPM à imprimer
	String estDetails = (String) VariableGlobale.recuperer(request,"DETAILS");
	if (estDetails!=null){
		setDetails(true);
	}else{
		setDetails(false);
	}
	
	if(getPMatInfosCourant()!=null){
		initialiseListePe(request);
		// on renseigne les zones
		addZone(getNOM_ST_NOIMMAT(),getPMatInfosCourant().getPmserie());
		addZone(getNOM_ST_NOINVENT(),getPMatInfosCourant().getPminv());
		addZone(getNOM_ST_NOMEQUIP(),getPMatInfosCourant().getDesignationmarque()+" "+getPMatInfosCourant().getDesignationmodele());
		addZone(getNOM_ST_TYPE(),getPMatInfosCourant().getDesignationtypeequip());
		addZone(getNOM_ST_VERSION(),getPMatInfosCourant().getVersion());
	}else{
		setLB_PE(LBVide);
//		 on vide les zones
		addZone(getNOM_ST_NOIMMAT(),"");
		addZone(getNOM_ST_NOINVENT(),"");
		addZone(getNOM_ST_NOMEQUIP(),"");
		addZone(getNOM_ST_TYPE(),"");
	}
	
}

/**
 * Initialisation de la liste des entretiens
 * @author : Coralie NICOLAS
 */
private void initialiseListePe(javax.servlet.http.HttpServletRequest request) throws Exception{
//	 Si param = "" on liste tous les entretiens
	java.util.ArrayList a = PM_Planning.listerPM_EntretiensPm(getTransaction(),getPMatInfosCourant().getPminv(),tri);
	// Si param = afaire on liste les entretiens à faire
	if ("afaire".equals(param)){
		a = PM_Planning.listerPePersoInfosAFaire(getTransaction(),getPMatInfosCourant().getPminv(),tri);
	// Si param = fait on liste les entretiens fait
	}else if("fait".equals(param)){
		a = PM_Planning.listerPM_EntretiensPmFait(getTransaction(),getPMatInfosCourant().getPminv(),tri);
	}
	
	trier(a, tri);
	cocher(param,tri);
}

/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (28/06/05 15:46:22)
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
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	On met la variable activité
	if(null==getPMatInfosCourant()){
		setPMatInfosCourant(new PMatInfos());
	}
	VariableGlobale.ajouter(request, "PMATINFOS", getPMatInfosCourant());
	VariableGlobale.enlever(request, "PMPEPERSOINFOS");
	setStatut(STATUT_AJOUTER,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_ANNULER() {
	return "NOM_PB_ANNULER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	 on vide les zones
	addZone(getNOM_ST_TDUREE(),"");
	addZone(getNOM_ST_ENTRETIEN(),"");
	addZone(getNOM_ST_DUREE(),"");
	addZone(getNOM_ST_TITRE_ACTION(),"");
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (28/06/05 15:46:22)
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
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {
	int numligne = (Services.estNumerique(getZone(getNOM_LB_PE_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_PE_SELECT())) : -1);
	if (numligne == -1 || getListePePerso().size() == 0 || numligne > getListePePerso().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Plan d'entretien personnalisé du petit matériel"));
		return false;
	}
	
	PM_Planning monPM_Planning = (PM_Planning)getListePePerso().get(numligne);
	setPmPlanningCourant(monPM_Planning);
//	On met la variable activité
	if(null==getPMatInfosCourant()){
		setPMatInfosCourant(new PMatInfos());
	}
	VariableGlobale.ajouter(request, "PMATINFOS", getPMatInfosCourant());
	VariableGlobale.ajouter(request, "PMPEPERSOINFOS", getPmPlanningCourant());
	setStatut(STATUT_MODIFIER,true);
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHEMODELE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_RECHERCHEEQUIP() {
	return "NOM_PB_RECHERCHEEQUIP";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public boolean performPB_RECHERCHEEQUIP(javax.servlet.http.HttpServletRequest request) throws Exception {
	VariableActivite.ajouter(this,"MODE","PMPEPERSO");
	setStatut(STATUT_RECHERCHEEQUIP,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPRIMER
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_SUPPRIMER() {
	return "NOM_PB_SUPPRIMER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public boolean performPB_SUPPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
	int numligne = (Services.estNumerique(getZone(getNOM_LB_PE_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_PE_SELECT())) : -1);
	if (numligne == -1 || getListePePerso().size() == 0 || numligne > getListePePerso().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Plan d'entretien personnalisé du petit matériel"));
		return false;
	}
	
	PM_Planning monPM_Planning = (PM_Planning)getListePePerso().get(numligne);
	setPmPlanningCourant(monPM_Planning);
	// on recherche le pePerso
	PM_PePerso unPePerso = PM_PePerso.chercherPM_PePerso(getTransaction(),getPmPlanningCourant().getCodepmpep());
	if (getTransaction().isErreur()){
		return false;
	}
	setPmPePersoCourant(unPePerso);
	
	//on nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);
	// on initialise les zones
	addZone(getNOM_ST_COMMENTAIRE(),getPmPlanningCourant().getCommentaire());
	addZone(getNOM_ST_ENTRETIEN(),getPmPlanningCourant().getLibelleentretien());
	addZone(getNOM_ST_DUREE(),getPmPlanningCourant().getDuree());
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (28/06/05 15:46:22)
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
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Si aucune action en cours
	if (getZone(getNOM_ST_TITRE_ACTION()).length() == 0) {
		setStatut(STATUT_MEME_PROCESS, true, MairieMessages.getMessage("ERR996"));//"Vous ne pouvez pas valider, il n'y a pas d'action en cours."
		return false;
	}
//	Suppression
	getPmPePersoCourant().supprimerPM_PePerso(getTransaction());
	if (getTransaction().isErreur()){
		return false;
	}
//	Tout s'est bien passé
	commitTransaction();
	initialiseListePe(request);
	// on vide les zones
	addZone(getNOM_ST_TDUREE(),"");
	addZone(getNOM_ST_ENTRETIEN(),"");
	addZone(getNOM_ST_DUREE(),"");
	addZone(getNOM_ST_TITRE_ACTION(),"");
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DUREE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_DUREE() {
	return "NOM_ST_DUREE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DUREE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_DUREE() {
	return getZone(getNOM_ST_DUREE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_ENTRETIEN
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_ENTRETIEN() {
	return "NOM_ST_ENTRETIEN";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_ENTRETIEN
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_ENTRETIEN() {
	return getZone(getNOM_ST_ENTRETIEN());
}

/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODELE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_MODELE() {
	return "NOM_ST_MODELE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODELE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_MODELE() {
	return getZone(getNOM_ST_MODELE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TDUREE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TDUREE() {
	return "NOM_ST_TDUREE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TDUREE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TDUREE() {
	return getZone(getNOM_ST_TDUREE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TEQUIP
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TEQUIP() {
	return "NOM_ST_TEQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TEQUIP
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TEQUIP() {
	return getZone(getNOM_ST_TEQUIP());
}

/**
 * Getter de la liste avec un lazy initialize :
 * LB_PE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
private String [] getLB_PE() {
	if (LB_PE == null)
		LB_PE = initialiseLazyLB();
	return LB_PE;
}
/**
 * Setter de la liste:
 * LB_PE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
private void setLB_PE(java.lang.String[] newLB_PE) {
	LB_PE = newLB_PE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_PE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_PE() {
	return "NOM_LB_PE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_PE_SELECT
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_PE_SELECT() {
	return "NOM_LB_PE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_PE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_PE() {
	return getLB_PE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_PE
 * Date de création : (28/06/05 15:46:22)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_PE_SELECT() {
	return getZone(getNOM_LB_PE_SELECT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (28/06/05 15:47:42)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (28/06/05 15:47:42)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
	public int getIsVide() {
		return isVide;
	}
	public void setIsVide(int isVide) {
		this.isVide = isVide;
	}
	public PMatInfos getPMatInfosCourant() {
		return pMatInfosCourant;
	}
	public void setPMatInfosCourant(PMatInfos pMatInfosCourant) {
		this.pMatInfosCourant = pMatInfosCourant;
	}
	public ArrayList getListePePerso() {
		return listePePerso;
	}
	public void setListePePerso(ArrayList listePePerso) {
		this.listePePerso = listePePerso;
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
	 * @param focus focus à définir.
	 */
	public String getDefaultFocus() {
		return getNOM_EF_PMINV();
	}
	public PM_Planning getPmPlanningCourant() {
		return pmPlanningCourant;
	}
	public void setPmPlanningCourant(PM_Planning pmPlanningCourant) {
		this.pmPlanningCourant = pmPlanningCourant;
	}
	public PM_PePerso getPmPePersoCourant() {
		return pmPePersoCourant;
	}
	public void setPmPePersoCourant(PM_PePerso pmPePersoCourant) {
		this.pmPePersoCourant = pmPePersoCourant;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMMENTAIRE
 * Date de création : (01/07/05 13:14:20)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_COMMENTAIRE() {
	return "NOM_ST_COMMENTAIRE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMMENTAIRE
 * Date de création : (01/07/05 13:14:20)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_COMMENTAIRE() {
	return getZone(getNOM_ST_COMMENTAIRE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_INTERVALLE
 * Date de création : (05/07/05 11:45:20)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_INTERVALLE() {
	return "NOM_ST_INTERVALLE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_INTERVALLE
 * Date de création : (05/07/05 11:45:20)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_INTERVALLE() {
	return getZone(getNOM_ST_INTERVALLE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (05/07/05 11:45:20)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOIMMAT() {
	return "NOM_ST_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (05/07/05 11:45:20)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOIMMAT() {
	return getZone(getNOM_ST_NOIMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINVENT
 * Date de création : (05/07/05 11:45:20)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOINVENT() {
	return "NOM_ST_NOINVENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINVENT
 * Date de création : (05/07/05 11:45:20)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOINVENT() {
	return getZone(getNOM_ST_NOINVENT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOMEQUIP
 * Date de création : (05/07/05 11:45:20)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOMEQUIP() {
	return "NOM_ST_NOMEQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOMEQUIP
 * Date de création : (05/07/05 11:45:20)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOMEQUIP() {
	return getZone(getNOM_ST_NOMEQUIP());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TINT
 * Date de création : (05/07/05 11:45:20)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TINT() {
	return "NOM_ST_TINT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TINT
 * Date de création : (05/07/05 11:45:20)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TINT() {
	return getZone(getNOM_ST_TINT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (05/07/05 11:45:20)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (05/07/05 11:45:20)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_TRI
 * Date de création : (06/07/05 08:42:53)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_TRI() {
	return "NOM_PB_TRI";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (06/07/05 08:42:53)
 * @author : Générateur de process
 */
public boolean performPB_TRI(javax.servlet.http.HttpServletRequest request) throws Exception {
//	 selon la sélection la liste sera triée
	if (getNOM_RB_TRI_ENT().equals(getZone(getNOM_RG_TRI()))){
		tri = "libelleentretien";
	}
	if (getNOM_RB_TRI_OT().equals(getZone(getNOM_RG_TRI()))){
		tri = "numfiche";
	}
	if (getNOM_RB_TRI_REAL().equals(getZone(getNOM_RG_TRI()))){
		tri = "dreal";
	}
	if (getNOM_RB_TRI_PREVU().equals(getZone(getNOM_RG_TRI()))){
		tri = "dprev";
	}
	
	// selon les petits matériels voulus (tous / actifs / inactifs)
	if (getNOM_RB_AFFICHAGE_AFAIRE().equals(getZone(getNOM_RG_AFFICHAGE()))){
		param = "afaire";
	}
	if (getNOM_RB_AFFICHAGE_FAIT().equals(getZone(getNOM_RG_AFFICHAGE()))){
		param = "fait";
	}
	if (getNOM_RB_AFFICHAGE_TOUS().equals(getZone(getNOM_RG_AFFICHAGE()))){
		param = "Tous";
	}
	if(getPMatInfosCourant()!=null){
		java.util.ArrayList a = PM_Planning.listerPM_EntretiensPm(getTransaction(),getPMatInfosCourant().getPminv(),tri);
		// Si param = actifs on liste les petits matériels actifs
		if ("afaire".equals(param)){
			a = PM_Planning.listerPePersoInfosAFaire(getTransaction(),getPMatInfosCourant().getPminv(),tri);
		// Si param = inactifs on liste les petits matériels inactifs
		}else if("fait".equals(param)){
			a = PM_Planning.listerPM_EntretiensPmFait(getTransaction(),getPMatInfosCourant().getPminv(),tri);
		}
		trier(a, tri);
		cocher(param,tri);
	}else{
		getTransaction().declarerErreur("Veuillez sélectionné un petit matériel.");
		return false;
	}
	
	return true;
}
/**
 * Retourne le nom du groupe de radio boutons coché pour la JSP :
 * RG_AFFICHAGE
 * Date de création : (06/07/05 08:42:53)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RG_AFFICHAGE() {
	return "NOM_RG_AFFICHAGE";
}
/**
 * Retourne la valeur du radio bouton (RB_) coché dans la JSP :
 * RG_AFFICHAGE
 * Date de création : (06/07/05 08:42:53)
 * @author : Générateur de process
 */
public java.lang.String getVAL_RG_AFFICHAGE() {
	return getZone(getNOM_RG_AFFICHAGE());
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_AFFICHAGE_AFAIRE
 * Date de création : (06/07/05 08:42:53)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_AFFICHAGE_AFAIRE() {
	return "NOM_RB_AFFICHAGE_AFAIRE";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_AFFICHAGE_FAIT
 * Date de création : (06/07/05 08:42:53)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_AFFICHAGE_FAIT() {
	return "NOM_RB_AFFICHAGE_FAIT";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_AFFICHAGE_TOUS
 * Date de création : (06/07/05 08:42:53)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_AFFICHAGE_TOUS() {
	return "NOM_RB_AFFICHAGE_TOUS";
}
public void trier(ArrayList a,String colonne) throws Exception{
	String[] colonnes = {colonne};
	String intervalle;
	//ordre croissant
	boolean[] ordres = {true};
	String date = "";
	String dateprevu ="";
	if (tri.startsWith("d"))
		ordres=new boolean[]{false};
	a= Services.trier(a,colonnes,ordres);
	setListePePerso(a);
	int indice = a.size();
	
	if (a.size()>0){
		//les élèments de la liste seront le codemarque pour pouvoir récupérer le dernier élément et les dimensions des marques.
		int [] tailles = {30,10,10,10,6};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G","C","C","G","D"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (int i = 0; i < indice ; i++) {
			PM_Planning aPM_Planning = (PM_Planning)a.get(i);	
			date = aPM_Planning.getDreal();
			dateprevu = aPM_Planning.getDprev();
			if (date.equals("01/01/0001")){
				date="";
			}
			if (dateprevu.equals("01/01/0001")){
				dateprevu = "";
			}
			String ligne [] = { aPM_Planning.getLibelleentretien(),dateprevu,date,aPM_Planning.getDuree(),aPM_Planning.getNumfiche()};
			aFormat.ajouteLigne(ligne);
		}
		setLB_PE(aFormat.getListeFormatee());
	}else{
		setLB_PE(null);
	}
	setIsVide(a.size());
	return ;
}

public void cocher(String param, String tri){
	// Selon le param on coche le bon affichage
	addZone(getNOM_RG_AFFICHAGE(),getNOM_RB_AFFICHAGE_TOUS());
	if ("afaire".equals(param)){
		addZone(getNOM_RG_AFFICHAGE(),getNOM_RB_AFFICHAGE_AFAIRE());
	}else if("fait".equals(param)){
		addZone(getNOM_RG_AFFICHAGE(),getNOM_RB_AFFICHAGE_FAIT());
	}
	
	//Selon le tri coche la bonne colonne
	addZone(getNOM_RG_TRI(),getNOM_RB_TRI_ENT());
	if (tri.equals("libelleentretien")){
		addZone(getNOM_RG_TRI(),getNOM_RB_TRI_ENT());
	}else if (tri.equals("numfiche")){
		addZone(getNOM_RG_TRI(),getNOM_RB_TRI_OT());
	}else if (tri.equals("dreal")){
		addZone(getNOM_RG_TRI(),getNOM_RB_TRI_REAL());
	}else if (tri.equals("dprev")){
		addZone(getNOM_RG_TRI(),getNOM_RB_TRI_PREVU());
	}
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_ENT
 * Date de création : (06/07/05 08:46:54)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_ENT() {
	return "NOM_RB_TRI_ENT";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_OT
 * Date de création : (06/07/05 08:46:54)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_OT() {
	return "NOM_RB_TRI_OT";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_REAL
 * Date de création : (06/07/05 08:46:54)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_REAL() {
	return "NOM_RB_TRI_REAL";
}
/**
 * Retourne le nom du groupe de radio boutons coché pour la JSP :
 * RG_TRI
 * Date de création : (11/07/05 07:43:34)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RG_TRI() {
	return "NOM_RG_TRI";
}
/**
 * Retourne la valeur du radio bouton (RB_) coché dans la JSP :
 * RG_TRI
 * Date de création : (11/07/05 07:43:34)
 * @author : Générateur de process
 */
public java.lang.String getVAL_RG_TRI() {
	return getZone(getNOM_RG_TRI());
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_PREVU
 * Date de création : (11/07/05 07:43:34)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_PREVU() {
	return "NOM_RB_TRI_PREVU";
}
	public boolean isDetails() {
		return details;
	}
	public void setDetails(boolean details) {
		this.details = details;
	}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RETOUROT
 * Date de création : (28/07/05 09:48:06)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_RETOUROT() {
	return "NOM_PB_RETOUROT";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/07/05 09:48:06)
 * @author : Générateur de process
 */
public boolean performPB_RETOUROT(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_RETOURFPM,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ACCES_OT
 * Date de création : (28/11/05 11:33:15)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_ACCES_OT() {
	return "NOM_PB_ACCES_OT";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/11/05 11:33:15)
 * @author : Générateur de process
 */
public boolean performPB_ACCES_FPM(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_ACCES_FPM,true);
	return true;
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (28/06/05 15:54:20)
 * @author : Générateur de process
 */
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_EQUIP
		if (testerParametre(request, getNOM_PB_EQUIP())) {
			return performPB_EQUIP(request);
		}

		//Si clic sur le bouton PB_ACCES_OT
		if (testerParametre(request, getNOM_PB_ACCES_OT())) {
			return performPB_ACCES_FPM(request);
		}

		//Si clic sur le bouton PB_RETOUROT
		if (testerParametre(request, getNOM_PB_RETOUROT())) {
			return performPB_RETOUROT(request);
		}

		//Si clic sur le bouton PB_TRI
		if (testerParametre(request, getNOM_PB_TRI())) {
			return performPB_TRI(request);
		}
		//Si clic sur le bouton PB_AJOUTER
		if (testerParametre(request, getNOM_PB_AJOUTER())) {
			return performPB_AJOUTER(request);
		}

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_MODIFIER
		if (testerParametre(request, getNOM_PB_MODIFIER())) {
			return performPB_MODIFIER(request);
		}

		//Si clic sur le bouton PB_RECHERCHEMODELE
		if (testerParametre(request, getNOM_PB_RECHERCHEEQUIP())) {
			return performPB_RECHERCHEEQUIP(request);
		}

		//Si clic sur le bouton PB_SUPPRIMER
		if (testerParametre(request, getNOM_PB_SUPPRIMER())) {
			return performPB_SUPPRIMER(request);
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
 * Date de création : (02/04/07 09:09:00)
 * @author : Générateur de process
 */
public String getJSP() {
	return "OePMPePerso.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_EQUIP
 * Date de création : (02/04/07 09:09:00)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_EQUIP() {
	return "NOM_PB_EQUIP";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (02/04/07 09:09:00)
 * @author : Générateur de process
 */
public boolean performPB_EQUIP(javax.servlet.http.HttpServletRequest request) throws Exception {
	String recherche = getZone(getNOM_EF_PMINV()).toUpperCase();
	if(!recherche.equals("")){
		PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),recherche);
		if(getTransaction().isErreur()){
			getTransaction().declarerErreur("Le petit matériel recherché n'a pas été trouvé.");
			return false;
		}
		if(null==unPMatInfos){
			unPMatInfos = new PMatInfos();
		}
		setPMatInfosCourant(unPMatInfos);
	}else{
		getTransaction().declarerErreur("La zone de recherche doit être renseignée.");
		return false;
	}
	if(null==getPMatInfosCourant()){
		setPMatInfosCourant(new PMatInfos());
	}
	// on renseigne la liste des BPC
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_VERSION
 * Date de création : (02/04/07 09:09:00)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_VERSION() {
	return "NOM_ST_VERSION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_VERSION
 * Date de création : (02/04/07 09:09:00)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_VERSION() {
	return getZone(getNOM_ST_VERSION());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_EQUIP
 * Date de création : (02/04/07 09:09:00)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_PMINV() {
	return "NOM_EF_PMINV";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_EQUIP
 * Date de création : (02/04/07 09:09:00)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_PMINV() {
	return getZone(getNOM_EF_PMINV());
}
}
