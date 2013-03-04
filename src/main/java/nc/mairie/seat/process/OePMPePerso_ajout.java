package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.Entretien;
import nc.mairie.seat.metier.ModeleInfos;
import nc.mairie.seat.metier.Modeles;
import nc.mairie.seat.metier.PM_PePerso;
import nc.mairie.seat.metier.PM_Planning;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.PMateriel;
import nc.mairie.technique.*;
/**
 * Process OePePerso_ajout
 * Date de création : (29/06/05 07:38:35)
* 
*/
public class OePMPePerso_ajout extends nc.mairie.technique.BasicProcess {
	private java.lang.String[] LB_ENTRETIEN;
	private java.lang.String[] LB_TINTER;
	private String ACTION_MODIFICATION = "Modification";
	private String ACTION_CREATION = "Création";
	private String focus = null;
	private ArrayList listeEntretien;
	private ModeleInfos modeleInfosCourant;
	private PM_Planning pmPlanningCourant;
	private Entretien entretienCourant;
	private PMateriel pMaterielCourant;
	private Modeles modeleCourant;
	private PM_PePerso pmPePersoCourant;
	private PMatInfos pMatInfosCourant;
	private String isModif="";
	private String duree = "";
	private String commentaire = "";
	private String dprev = Services.dateDuJour();
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (29/06/05 07:38:35)
* 
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request, "PMATINFOS");
	setPMatInfosCourant(unPMatInfos);
	if (getPMatInfosCourant()!=null){
		PMateriel unPMateriel = PMateriel.chercherPMateriel(getTransaction(),getPMatInfosCourant().getPminv());
		if (getTransaction().isErreur()){
			return;
		}
		setPMaterielCourant(unPMateriel);
	}
	
	PM_Planning unPM_Planning = (PM_Planning)VariableGlobale.recuperer(request, "PMPEPERSOINFOS");
	if (unPM_Planning!=null){
		setPmPlanningCourant(unPM_Planning);
	}
	if((getEntretienCourant()==null)||(getEntretienCourant().getCodeentretien()==null)){
		if((unPM_Planning!=null)&&(unPM_Planning.getCodeentretien()!=null)){
			Entretien unEntretien = Entretien.chercherEntretien(getTransaction(),unPM_Planning.getCodeentretien());
			if(getTransaction().isErreur()){
				return;
			}
			setEntretienCourant(unEntretien);
		}
	}
	// zones concernant le petit matériel
	if (getPMatInfosCourant()!=null){
		if(getPMatInfosCourant().getPminv()!=null){
			Modeles unModele = Modeles.chercherModeles(getTransaction(),getPMatInfosCourant().getCodemodele());
			if(getTransaction().isErreur()){
				return;
			}
			setModeleCourant(unModele);
		}
		addZone(getNOM_ST_NOINVENT(),getPMatInfosCourant().getPminv());
		addZone(getNOM_ST_NOIMMAT(),getPMatInfosCourant().getPmserie());
		addZone(getNOM_ST_NOMEQUIP(),getPMatInfosCourant().getDesignationmarque()+" "+getPMatInfosCourant().getDesignationmodele());
		//	zones concernant le PePerso
		if (getPmPlanningCourant()==null){
			addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
			//addZone(getNOM_EF_DUREE(),"");
			//addZone(getNOM_EF_INTERVALLE(),"");
			addZone(getNOM_LB_ENTRETIEN_SELECT(),"0");
		}else{
			// si en modification
			PM_PePerso unPM_PePerso = PM_PePerso.chercherPM_PePerso(getTransaction(),pmPlanningCourant.getCodepmpep());
			if (getTransaction().isErreur()){
				return ;
			}
			setPmPePersoCourant(unPM_PePerso);
			addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);
			commentaire = getPmPlanningCourant().getCommentaire();
			duree = getPmPlanningCourant().getDuree();
			if ("01/01/0001".equals(getPmPlanningCourant().getDprev())){
				addZone(getNOM_EF_DPREV(), "");
			}else{
				addZone(getNOM_EF_DPREV(), getPmPlanningCourant().getDprev());
			}
			addZone(getNOM_ST_ENTRETIEN(),pmPlanningCourant.getLibelleentretien());
			setIsModif("Modif");
//			 on coche la case si c'est sinistre
			if ("O".equals(getPmPlanningCourant().getSinistre().trim())){
				addZone(getNOM_CK_SINISTRE(),getCHECKED_ON());
			}else{
				addZone(getNOM_CK_SINISTRE(),getCHECKED_OFF());
			}
		}
	}else{
		addZone(getNOM_ST_NOINVENT(),"");
		addZone(getNOM_ST_NOIMMAT(),"");
		addZone(getNOM_ST_TYPE(),"");
		addZone(getNOM_ST_NOMEQUIP(),"");
	}
//	 on remplit les listes déroulante
//	 Si liste des entretien est vide
	//if (getLB_ENTRETIEN() == LBVide) {
	java.util.ArrayList aEnt = Entretien.listerEntretien(getTransaction());
	setListeEntretien(aEnt);
	//les élèments de la liste 
	int [] taillesEnt = {20};
	String [] champsEnt = {"libelleentretien"};
	String [] paddingEnt = {"G"};
	setLB_ENTRETIEN(new FormateListe(taillesEnt,aEnt,champsEnt,paddingEnt,false).getListeFormatee());
	
	if(getEntretienCourant()!=null){
		//	recherche de l'entretien courant
		int position = -1;
		addZone(getNOM_LB_ENTRETIEN_SELECT(),String.valueOf(-1));
		for (int i = 0; i < getListeEntretien().size(); i++) {
			Entretien unEntretien = (Entretien)getListeEntretien().get(i);
			if (unEntretien.getCodeentretien().equals(getEntretienCourant().getCodeentretien())) {
				addZone(getNOM_LB_ENTRETIEN_SELECT(),String.valueOf(i));
				break;
			}
		}
	}
	//initialisation des zones
	addZone(getNOM_EF_COMMENTAIRE(),commentaire);
	addZone(getNOM_EF_DUREE(),duree);
	addZone(getNOM_EF_DPREV(),dprev);
}
/**
 * Constructeur du process OePePerso_ajout.
 * Date de création : (29/06/05 07:38:35)
* 
 */
public OePMPePerso_ajout() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (29/06/05 07:38:35)
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
 * Date de création : (29/06/05 07:38:35)
* 
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_PROCESS_APPELANT);
	setIsModif("");
	//on vides les zones
	setLB_ENTRETIEN(LBVide);
	addZone(getNOM_EF_DUREE(),"");
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_ST_NOINVENT(),"");
	addZone(getNOM_ST_NOIMMAT(),"");
	addZone(getNOM_ST_TYPE(),"");
	addZone(getNOM_ST_NOMEQUIP(),"");
	setFocus(null);
	setPmPlanningCourant(null);
	VariableGlobale.enlever(request,"PMPEPERSOINFOS");
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (29/06/05 07:38:35)
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
 * Date de création : (29/06/05 07:38:35)
* 
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	//on rècupère les zones
	String newDuree = getZone(getNOM_EF_DUREE());
	String newCommentaire = getZone(getNOM_EF_COMMENTAIRE()).toUpperCase();
	String sinistre = getZone(getNOM_CK_SINISTRE());
	String dprev = getZone(getNOM_EF_DPREV());
	
	if(!dprev.equals("")){
		if (!Services.estUneDate(dprev)){
			getTransaction().declarerErreur("La date n'est pas correct.");
			setFocus(getNOM_EF_DPREV());
			return false;
		}
	}
	
	// si sinistre coché on met oui dans sinistre
	String newSinistre = sinistre.equals(getCHECKED_ON()) ? "O" : "N";
	//sélection de l'entretien
	int indice = (Services.estNumerique(getVAL_LB_ENTRETIEN_SELECT()) ? Integer.parseInt(getVAL_LB_ENTRETIEN_SELECT()): -1);
	Entretien monEntretien = (Entretien)getListeEntretien().get(indice);
	if(getTransaction().isErreur()){
		return false;
	}
	setEntretienCourant(monEntretien);
	
	//on controle les champs obligatoires
	if (newDuree.length() == 0) {
		getTransaction().declarerErreur("Vous devez renseigner la durée de l'entretien.");
		setFocus(getNOM_EF_DUREE());
		return false;
	}
		
	if(newCommentaire.length()>200){
		getTransaction().declarerErreur("Le commentaire est trop long pour pouvoir être enregistré.");
		setFocus(getNOM_EF_COMMENTAIRE());
		return false;
	}
	
	// selon si création ou modification
	//	Si Action Modification
	if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_MODIFICATION)) {
	
		//Affectation des attributs
		getPmPePersoCourant().setDuree(newDuree);
		getPmPePersoCourant().setCommentaire(newCommentaire);
		getPmPePersoCourant().setSinistre(newSinistre);
		getPmPePersoCourant().setDprev(dprev);
		
		//Modification
		getPmPePersoCourant().modifierPM_PePerso(getTransaction(),getPMaterielCourant(),getEntretienCourant());
			
	//Si Action Creation		
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_CREATION)) {		
		setPmPePersoCourant(new PM_PePerso());
		
		//Affectation des attributs
		getPmPePersoCourant().setDuree(newDuree);
		getPmPePersoCourant().setCommentaire(newCommentaire);
		getPmPePersoCourant().setSinistre(newSinistre);
		getPmPePersoCourant().setDprev(dprev);
	
		//Création
		getPmPePersoCourant().creerPM_PePerso(getTransaction(),getPMaterielCourant(),getEntretienCourant());
		
	}
	if (getTransaction().isErreur()){
		return false;
	}else{
//		Tout s'est bien passé
		commitTransaction();
		//initialiseListeCompteur(request);
		//on vide les zones
		setLB_ENTRETIEN(LBVide);
		addZone(getNOM_EF_DUREE(),"");
		addZone(getNOM_ST_TITRE_ACTION(),"");
		addZone(getNOM_ST_NOINVENT(),"");
		addZone(getNOM_ST_NOIMMAT(),"");
		addZone(getNOM_ST_TYPE(),"");
		addZone(getNOM_ST_NOMEQUIP(),"");
		setFocus(null);
		setStatut(STATUT_PROCESS_APPELANT);
		setIsModif("");
		setPmPlanningCourant(null);
		VariableGlobale.enlever(request,"PMPEPERSOINFOS");
	}
	
	return true;
}

/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TEQUIP
 * Date de création : (29/06/05 07:38:35)
* 
 */
public java.lang.String getNOM_ST_TEQUIP() {
	return "NOM_ST_TEQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TEQUIP
 * Date de création : (29/06/05 07:38:35)
* 
 */
public java.lang.String getVAL_ST_TEQUIP() {
	return getZone(getNOM_ST_TEQUIP());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TIRE_ACTION
 * Date de création : (29/06/05 07:38:35)
* 
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TIRE_ACTION
 * Date de création : (29/06/05 07:38:35)
* 
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DUREE
 * Date de création : (29/06/05 07:38:35)
* 
 */
public java.lang.String getNOM_EF_DUREE() {
	return "NOM_EF_DUREE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DUREE
 * Date de création : (29/06/05 07:38:35)
* 
 */
public java.lang.String getVAL_EF_DUREE() {
	return getZone(getNOM_EF_DUREE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_INTERVALLE
 * Date de création : (29/06/05 07:38:35)
* 
 */
public java.lang.String getNOM_EF_DPREV() {
	return "NOM_EF_DPREV";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_INTERVALLE
 * Date de création : (29/06/05 07:38:35)
* 
 */
public java.lang.String getVAL_EF_DPREV() {
	return getZone(getNOM_EF_DPREV());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_ENTRETIEN
 * Date de création : (29/06/05 07:38:35)
* 
 */
private String [] getLB_ENTRETIEN() {
	if (LB_ENTRETIEN == null)
		LB_ENTRETIEN = initialiseLazyLB();
	return LB_ENTRETIEN;
}
/**
 * Setter de la liste:
 * LB_ENTRETIEN
 * Date de création : (29/06/05 07:38:35)
* 
 */
private void setLB_ENTRETIEN(java.lang.String[] newLB_ENTRETIEN) {
	LB_ENTRETIEN = newLB_ENTRETIEN;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_ENTRETIEN
 * Date de création : (29/06/05 07:38:35)
* 
 */
public java.lang.String getNOM_LB_ENTRETIEN() {
	return "NOM_LB_ENTRETIEN";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_ENTRETIEN_SELECT
 * Date de création : (29/06/05 07:38:35)
* 
 */
public java.lang.String getNOM_LB_ENTRETIEN_SELECT() {
	return "NOM_LB_ENTRETIEN_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_ENTRETIEN
 * Date de création : (29/06/05 07:38:35)
* 
 */
public java.lang.String [] getVAL_LB_ENTRETIEN() {
	return getLB_ENTRETIEN();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_ENTRETIEN
 * Date de création : (29/06/05 07:38:35)
* 
 */
public java.lang.String getVAL_LB_ENTRETIEN_SELECT() {
	return getZone(getNOM_LB_ENTRETIEN_SELECT());
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
	return getNOM_LB_ENTRETIEN();
}

	public PM_Planning getPmPlanningCourant() {
		return pmPlanningCourant;
	}
	public void setPmPlanningCourant(PM_Planning pmPlanningCourant) {
		this.pmPlanningCourant = pmPlanningCourant;
	}
	public ArrayList getListeEntretien() {
		return listeEntretien;
	}
	public void setListeEntretien(ArrayList listeEntretien) {
		this.listeEntretien = listeEntretien;
	}
	
	
	public Entretien getEntretienCourant() {
		return entretienCourant;
	}
	public void setEntretienCourant(Entretien entretienCourant) {
		this.entretienCourant = entretienCourant;
	}
	
	public PM_PePerso getPmPePersoCourant() {
		return pmPePersoCourant;
	}
	public void setPmPePersoCourant(PM_PePerso pmPePersoCourant) {
		this.pmPePersoCourant = pmPePersoCourant;
	}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_COMMENTAIRE
 * Date de création : (29/06/05 12:51:06)
* 
 */
public java.lang.String getNOM_EF_COMMENTAIRE() {
	return "NOM_EF_COMMENTAIRE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_COMMENTAIRE
 * Date de création : (29/06/05 12:51:06)
* 
 */
public java.lang.String getVAL_EF_COMMENTAIRE() {
	return getZone(getNOM_EF_COMMENTAIRE());
}
	
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_ENTRETIEN
 * Date de création : (29/06/05 15:35:12)
* 
 */
public java.lang.String getNOM_ST_ENTRETIEN() {
	return "NOM_ST_ENTRETIEN";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_ENTRETIEN
 * Date de création : (29/06/05 15:35:12)
* 
 */
public java.lang.String getVAL_ST_ENTRETIEN() {
	return getZone(getNOM_ST_ENTRETIEN());
}
	public String getIsModif() {
		return isModif;
	}
	public void setIsModif(String isModif) {
		this.isModif = isModif;
	}
	public PMatInfos getPMatInfosCourant() {
		return pMatInfosCourant;
	}
	public void setPMatInfosCourant(PMatInfos pMatInfosCourant) {
		this.pMatInfosCourant = pMatInfosCourant;
	}

/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (05/07/05 13:32:54)
* 
 */
public java.lang.String getNOM_ST_NOIMMAT() {
	return "NOM_ST_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (05/07/05 13:32:54)
* 
 */
public java.lang.String getVAL_ST_NOIMMAT() {
	return getZone(getNOM_ST_NOIMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINVENT
 * Date de création : (05/07/05 13:32:54)
* 
 */
public java.lang.String getNOM_ST_NOINVENT() {
	return "NOM_ST_NOINVENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINVENT
 * Date de création : (05/07/05 13:32:54)
* 
 */
public java.lang.String getVAL_ST_NOINVENT() {
	return getZone(getNOM_ST_NOINVENT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOMEQUIP
 * Date de création : (05/07/05 13:32:54)
* 
 */
public java.lang.String getNOM_ST_NOMEQUIP() {
	return "NOM_ST_NOMEQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOMEQUIP
 * Date de création : (05/07/05 13:32:54)
* 
 */
public java.lang.String getVAL_ST_NOMEQUIP() {
	return getZone(getNOM_ST_NOMEQUIP());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (05/07/05 13:32:54)
* 
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (05/07/05 13:32:54)
* 
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
	private java.lang.String[] LB_TENT;
/**
 * Getter de la liste avec un lazy initialize :
 * LB_TENT
 * Date de création : (05/07/05 13:45:10)
* 
 */
private String [] getLB_TENT() {
	if (LB_TENT == null)
		LB_TENT = initialiseLazyLB();
	return LB_TENT;
}
/**
 * Setter de la liste:
 * LB_TENT
 * Date de création : (05/07/05 13:45:10)
* 
 */
private void setLB_TENT(java.lang.String[] newLB_TENT) {
	LB_TENT = newLB_TENT;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_TENT
 * Date de création : (05/07/05 13:45:10)
* 
 */
public java.lang.String getNOM_LB_TENT() {
	return "NOM_LB_TENT";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_TENT_SELECT
 * Date de création : (05/07/05 13:45:10)
* 
 */
public java.lang.String getNOM_LB_TENT_SELECT() {
	return "NOM_LB_TENT_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_TENT
 * Date de création : (05/07/05 13:45:10)
* 
 */
public java.lang.String [] getVAL_LB_TENT() {
	return getLB_TENT();
}

	public PMateriel getPMaterielCourant() {
		return pMaterielCourant;
	}
	public void setPMaterielCourant(PMateriel pMaterielCourant) {
		
		this.pMaterielCourant = pMaterielCourant;
	}
/**
 * Retourne le nom de la case à cocher sélectionnée pour la JSP :
 * CK_SINISTRE
 * Date de création : (05/07/05 14:07:46)
* 
 */
public java.lang.String getNOM_CK_SINISTRE() {
	return "NOM_CK_SINISTRE";
}
/**
 * Retourne la valeur de la case à cocher à afficher par la JSP pour la case à cocher  :
 * CK_SINISTRE
 * Date de création : (05/07/05 14:07:46)
* 
 */
public java.lang.String getVAL_CK_SINISTRE() {
	return getZone(getNOM_CK_SINISTRE());
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (29/06/05 07:38:35)
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
 * Date de création : (12/07/05 15:02:31)
* 
 */
@Override
public String getJSP() {
	return "OePMPePerso_ajout.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_TINT
 * Date de création : (12/07/05 15:02:31)
* 
 */
public java.lang.String getNOM_PB_OK_TENT() {
	return "NOM_PB_OK_TENT";
}


	public Modeles getModeleCourant() {
		return modeleCourant;
	}
	public void setModeleCourant(Modeles modeleCourant) {
		this.modeleCourant = modeleCourant;
	}
}
