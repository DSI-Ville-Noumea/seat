package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.PMateriel;
import nc.mairie.technique.*;
/**
 * Process OePMateriel
 * Date de création : (25/04/07 14:16:44)
* 
*/
public class OePMateriel extends nc.mairie.technique.BasicProcess {
	public static final int STATUT_MODIFIER = 2;
	public static final int STATUT_AJOUTER = 1;
	private PMatInfos pMatInfosCourant;
	private java.lang.String[] LB_PMAT_Couleurs;
	private java.lang.String[] LB_PMAT_FCouleurs;
	private String ACTION_SUPPRESSION = "Suppression d'un petit matériel.<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private String tri = "pminv";
	private String param ="";
	private ArrayList listePMaterielInfos;
	public int isVide = 0;
	private String focus = null;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (25/04/07 14:16:44)
* 
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request,"PMATINFOS");
	if(unPMatInfos!=null){
		setPMatInfosCourant(unPMatInfos);
	}
	if (getLB_PMATERIEL() == LBVide || etatStatut() != STATUT_MEME_PROCESS) {
	
		// Si param = "" on liste tous les petits matériels
		java.util.ArrayList a;
		// Si param = actifs on liste les petits matériels actifs
		if ("actifs".equals(param)){
			a = PMatInfos.listerPMatInfosActifs(getTransaction(),tri);
		// Si param = inactifs on liste les petits matériels inactifs
		}else if("inactifs".equals(param)){
			a = PMatInfos.listerPMatInfosInactifs(getTransaction(),tri);
		} else {
			a = PMatInfos.listerPMatInfos(getTransaction(),tri);
		}
		setListePMaterielInfos(a);
	}
				
	trier(getListePMaterielInfos(), tri);
	cocher(param,tri);
}
public void trier(ArrayList a,String colonne) throws Exception{
	//String [] champs = {"pminv","pmserie"};
	boolean[] ordres = {true};
	String[] colonnes = {colonne};
	a = Services.trier(a,colonnes,ordres);
	setListePMaterielInfos(a);
	
//	Si au moins un bpc
	if (a.size() !=0 ) {
		int [] tailles = {5,20,10,20,15};
		String [] padding = {"G","G","G","G","G"};
		setLB_PMAT_Couleurs(new String[a.size()]);
		setLB_PMAT_FCouleurs(new String[a.size()]);
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		
		for (int i=0;i<a.size();i++){
			PMatInfos unPMatInfos = (PMatInfos)a.get(i);
			
			if (getPMatInfosCourant()!=null){
				if (unPMatInfos.getPminv().equals(getPMatInfosCourant().getPminv())){
					getLB_PMAT_Couleurs()[i] = "white";
					getLB_PMAT_FCouleurs()[i] = "teal";
				}
			}
			String ligne [] = { unPMatInfos.getPminv(),unPMatInfos.getPmserie(),unPMatInfos.getDesignationmarque(),unPMatInfos.getDesignationmodele(),unPMatInfos.getDesignationtypeequip()};
			aFormat.ajouteLigne(ligne);
			setLB_PMATERIEL(aFormat.getListeFormatee());
		}		
	} else {
		setLB_PMATERIEL(null);
	}
	setIsVide(a.size());
	return ;
}

public void cocher(String param, String tri){
	// Selon le param on coche le bon affichage
	addZone(getNOM_RG_AFFICHAGE(),getNOM_RB_AFFICHAGE_ACTIFS());
	if ("tous".equals(param)){
		addZone(getNOM_RG_AFFICHAGE(),getNOM_RB_AFFICHAGE_TOUS());
	}else if("inactifs".equals(param)){
		addZone(getNOM_RG_AFFICHAGE(),getNOM_RB_AFFICHAGE_INACTIFS());
	}
	
	//Selon le tri coche la bonne colonne
	addZone(getNOM_RG_TRI(),getNOM_RB_TRI_PMINV());
	if (tri.equals("pmserie")){
		addZone(getNOM_RG_TRI(),getNOM_RB_TRI_PMSERIE());
	}else if (tri.equals("designationmarque")){
		addZone(getNOM_RG_TRI(),getNOM_RB_TRI_MARQUE());
	}else if (tri.equals("designationmodele")){
		addZone(getNOM_RG_TRI(),getNOM_RB_TRI_MODELE());
	}else if (tri.equals("designationtypeequip")){
		addZone(getNOM_RG_TRI(),getNOM_RB_TRI_TYPE());
	}
}
/**
 * Constructeur du process OePMateriel.
 * Date de création : (25/04/07 14:16:44)
* 
 */
public OePMateriel() {
	super();
}
	private java.lang.String[] LB_PMATERIEL;
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (25/04/07 14:39:10)
* 
 */
public java.lang.String getNOM_PB_AJOUTER() {
	return "NOM_PB_AJOUTER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (25/04/07 14:39:10)
* 
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {
	//setStatut(STATUT_MODIFIER,false);
	//setPMatInfosCourant(new PMatInfos());
	VariableGlobale.enlever(request,"PMATINFOS");
	setStatut(STATUT_AJOUTER,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (25/04/07 14:39:10)
* 
 */
public java.lang.String getNOM_PB_MODIFIER() {
	return "NOM_PB_MODIFIER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (25/04/07 14:39:10)
* 
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	je récupère le code de la ligne sélectionner
	int indice = (Services.estNumerique(getVAL_LB_PMATERIEL_SELECT()) ? Integer.parseInt(getVAL_LB_PMATERIEL_SELECT()): -1); 
	if(indice<0){
		if(getPMatInfosCourant()!=null){
			if(getPMatInfosCourant().getPminv()!=null){
				for(int i=0;i<getListePMaterielInfos().size();i++){
					PMatInfos pmatInfos =  (PMatInfos)getListePMaterielInfos().get(i);
					if(pmatInfos.getPminv().equals(getPMatInfosCourant().getPminv())){
						indice = i;
						break;
					}
				}
			}
		}
	}
	if (indice == -1 || getListePMaterielInfos().size() == 0 || indice > getListePMaterielInfos().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Petit matériel"));
		return false;
	}

	PMatInfos monPMatInfos = (PMatInfos)getListePMaterielInfos().get(indice);
	setPMatInfosCourant(monPMatInfos);
	if (getPMatInfosCourant().getPmserie()== null){
		getTransaction().declarerErreur("Vous n'avez rien sélectionné");
		return false;
	}
	
	//On met la variable activité
	VariableGlobale.ajouter(request, "PMATINFOS", getPMatInfosCourant());

	setStatut(STATUT_MODIFIER,true);
	
	return true;
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (25/04/07 14:39:10)
* 
 */
public boolean performPB_SUPPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_PMATERIEL_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_PMATERIEL_SELECT())) : -1);
	if(numligne<0){
		if(getPMatInfosCourant()!=null){
			if(getPMatInfosCourant().getPminv()!=null){
				for(int i=0;i<getListePMaterielInfos().size();i++){
					PMatInfos pmatInfos = (PMatInfos)getListePMaterielInfos().get(i);
					if(pmatInfos.getPminv().equals(getPMatInfosCourant().getPminv())){
						numligne = i;
						break;
					}
				}
			}
		}
	}
	if (numligne == -1 || getListePMaterielInfos().size() == 0 || numligne > getListePMaterielInfos().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Petit matériel"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);

	//Récup du type d'intervalle courante
	PMatInfos unPMatInfos = (PMatInfos)getListePMaterielInfos().get(numligne);
	setPMatInfosCourant(unPMatInfos);

	//Alim zones
	addZone(getNOM_ST_PMINV(), unPMatInfos.getPminv());
	addZone(getNOM_ST_PMSERIE(), unPMatInfos.getPmserie());
	addZone(getNOM_ST_FRE(), unPMatInfos.getLibellefre());
	addZone(getNOM_ST_MARQUE(), unPMatInfos.getDesignationmarque());
	addZone(getNOM_ST_MODELE(), unPMatInfos.getDesignationmodele());
	addZone(getNOM_ST_TYPE(), unPMatInfos.getDesignationtypeequip());
	addZone(getNOM_ST_DMES(), unPMatInfos.getDmes());
	
	setStatut(STATUT_MEME_PROCESS);	
	return true;
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_PMATERIEL
 * Date de création : (25/04/07 14:39:10)
* 
 */
private String [] getLB_PMATERIEL() {
	if (LB_PMATERIEL == null)
		LB_PMATERIEL = initialiseLazyLB();
	return LB_PMATERIEL;
}
/**
 * Setter de la liste:
 * LB_PMATERIEL
 * Date de création : (25/04/07 14:39:10)
* 
 */
private void setLB_PMATERIEL(java.lang.String[] newLB_PMATERIEL) {
	LB_PMATERIEL = newLB_PMATERIEL;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_PMATERIEL
 * Date de création : (25/04/07 14:39:10)
* 
 */
public java.lang.String getNOM_LB_PMATERIEL() {
	return "NOM_LB_PMATERIEL";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_PMATERIEL_SELECT
 * Date de création : (25/04/07 14:39:10)
* 
 */
public java.lang.String getNOM_LB_PMATERIEL_SELECT() {
	return "NOM_LB_PMATERIEL_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_PMATERIEL
 * Date de création : (25/04/07 14:39:10)
* 
 */
public java.lang.String [] getVAL_LB_PMATERIEL() {
	return getLB_PMATERIEL();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_PMATERIEL
 * Date de création : (25/04/07 14:39:10)
* 
 */
public java.lang.String getVAL_LB_PMATERIEL_SELECT() {
	return getZone(getNOM_LB_PMATERIEL_SELECT());
}
/**
 * Retourne le nom du groupe de radio boutons coché pour la JSP :
 * RG_AFFICHAGE
 * Date de création : (25/04/07 14:39:10)
* 
 */
public java.lang.String getNOM_RG_AFFICHAGE() {
	return "NOM_RG_AFFICHAGE";
}
/**
 * Retourne la valeur du radio bouton (RB_) coché dans la JSP :
 * RG_AFFICHAGE
 * Date de création : (25/04/07 14:39:10)
* 
 */
public java.lang.String getVAL_RG_AFFICHAGE() {
	return getZone(getNOM_RG_AFFICHAGE());
}
/**
 * Retourne le nom du groupe de radio boutons coché pour la JSP :
 * RG_TRI
 * Date de création : (25/04/07 14:39:10)
* 
 */
public java.lang.String getNOM_RG_TRI() {
	return "NOM_RG_TRI";
}
/**
 * Retourne la valeur du radio bouton (RB_) coché dans la JSP :
 * RG_TRI
 * Date de création : (25/04/07 14:39:10)
* 
 */
public java.lang.String getVAL_RG_TRI() {
	return getZone(getNOM_RG_TRI());
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_ACTIFS
 * Date de création : (25/04/07 14:39:10)
* 
 */
public java.lang.String getNOM_RB_AFFICHAGE_ACTIFS() {
	return "NOM_RB_AFFICHAGE_ACTIFS";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_INACTIFS
 * Date de création : (25/04/07 14:39:10)
* 
 */
public java.lang.String getNOM_RB_AFFICHAGE_INACTIFS() {
	return "NOM_RB_AFFICHAGE_INACTIFS";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_MARQUE
 * Date de création : (25/04/07 14:39:10)
* 
 */
public java.lang.String getNOM_RB_TRI_MARQUE() {
	return "NOM_RB_TRI_MARQUE";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_MODELE
 * Date de création : (25/04/07 14:39:10)
* 
 */
public java.lang.String getNOM_RB_TRI_MODELE() {
	return "NOM_RB_TRI_MODELE";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_PMINV
 * Date de création : (25/04/07 14:39:10)
* 
 */
public java.lang.String getNOM_RB_TRI_PMINV() {
	return "NOM_RB_TRI_PMINV";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_PMSERIE
 * Date de création : (25/04/07 14:39:10)
* 
 */
public java.lang.String getNOM_RB_TRI_PMSERIE() {
	return "NOM_RB_TRI_PMSERIE";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TYPE
 * Date de création : (25/04/07 14:39:10)
* 
 */
public java.lang.String getNOM_RB_TRI_TYPE() {
	return "NOM_RB_TRI_TYPE";
}

/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_TRI
 * Date de création : (25/04/07 14:40:36)
* 
 */
public java.lang.String getNOM_PB_OK_TRI() {
	return "NOM_PB_OK_TRI";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (25/04/07 14:40:36)
* 
 */
public boolean performPB_OK_TRI(javax.servlet.http.HttpServletRequest request) throws Exception {
//	 selon la sélection la liste sera triée
	if (getNOM_RB_TRI_PMINV().equals(getZone(getNOM_RG_TRI()))){
		tri = "pminv";
	}
	if (getNOM_RB_TRI_PMSERIE().equals(getZone(getNOM_RG_TRI()))){
		tri = "pmserie";
	}
	if (getNOM_RB_TRI_MARQUE().equals(getZone(getNOM_RG_TRI()))){
		tri = "designationmarque";
	}
	if (getNOM_RB_TRI_MODELE().equals(getZone(getNOM_RG_TRI()))){
		tri = "designationmodele";
	}
	if (getNOM_RB_TRI_TYPE().equals(getZone(getNOM_RG_TRI()))){
		tri = "designationtypeequip";
	}

	//Optimisation LUC 01/09/11
	String oldParam = param;
	// selon les petits matériels voulus (tous / actifs / inactifs)
	if (getNOM_RB_AFFICHAGE_INACTIFS().equals(getZone(getNOM_RG_AFFICHAGE()))){
		param = "inactifs";
	}
	if (getNOM_RB_AFFICHAGE_ACTIFS().equals(getZone(getNOM_RG_AFFICHAGE()))){
		param = "actifs";
	}
	if (getNOM_RB_AFFICHAGE_TOUS().equals(getZone(getNOM_RG_AFFICHAGE()))){
		param = "tous";
	}

	//optimisation luc 01/09/11
	if (! oldParam.equals(param)) {
		setLB_PMATERIEL(LBVide);

		/*	java.util.ArrayList a = PMatInfos.listerPMatInfos(getTransaction(),tri);
			// Si param = actifs on liste les petits matériels actifs
			if ("actifs".equals(param)){
				a = PMatInfos.listerPMatInfosActifs(getTransaction(),tri);
			// Si param = inactifs on liste les petits matériels inactifs
			}else if("inactifs".equals(param)){
				a = PMatInfos.listerPMatInfosInactifs(getTransaction(),tri);
			}
		
			trier(a, tri);
			cocher(param,tri);*/
	}
	return true;
}
	
public java.lang.String[] getLB_PMAT_Couleurs() {
	return LB_PMAT_Couleurs;
}
public void setLB_PMAT_Couleurs(java.lang.String[] couleurs) {
	LB_PMAT_Couleurs = couleurs;
}
public java.lang.String[] getLB_PMAT_FCouleurs() {
	return LB_PMAT_FCouleurs;
}
public void setLB_PMAT_FCouleurs(java.lang.String[] fcouleurs) {
	LB_PMAT_FCouleurs = fcouleurs;
}
/**
 * @return Renvoie equipementCourant.
 */
private PMatInfos getPMatInfosCourant() {
	return pMatInfosCourant;
}
/**
 * @param equipementCourant equipementCourant à définir.
 */
private void setPMatInfosCourant(PMatInfos pMatInfosCourant) {
	this.pMatInfosCourant = pMatInfosCourant;
}
/**
 * @return Renvoie listeEquipement.
 */
private ArrayList getListePMaterielInfos() {
	return listePMaterielInfos;
}
/**
 * @param listeEquipement listeEquipement à définir.
 */
private void setListePMaterielInfos(ArrayList listePMateriel) {
	this.listePMaterielInfos = listePMateriel;
}
public int getIsVide() {
	return isVide;
}
public void setIsVide(int isVide) {
	this.isVide = isVide;
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_AFFICHAGE_TOUS
 * Date de création : (27/04/07 08:26:01)
* 
 */
public java.lang.String getNOM_RB_AFFICHAGE_TOUS() {
	return "NOM_RB_AFFICHAGE_TOUS";
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (27/04/07 08:31:23)
* 
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (27/04/07 08:31:23)
* 
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPRIMER
 * Date de création : (27/04/07 08:33:28)
* 
 */
public java.lang.String getNOM_PB_SUPPRIMER() {
	return "NOM_PB_SUPPRIMER";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (27/04/07 08:33:28)
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
 * Date de création : (27/04/07 08:33:28)
* 
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Si aucune action en cours
	if (getZone(getNOM_ST_TITRE_ACTION()).length() == 0) {
		setStatut(STATUT_MEME_PROCESS, true, MairieMessages.getMessage("ERR996"));//"Vous ne pouvez pas valider, il n'y a pas d'action en cours."
		return false;
	}
//	on récupère la zone DateHorsCircuit
	String newDateVouR = getZone(getNOM_EF_DMHS());
	if(!newDateVouR.equals("")){
		if(!Services.estUneDate(newDateVouR)){
			getTransaction().declarerErreur("La date de mise hors service n'est pas correcte.");
			setFocus(getNOM_EF_DMHS());
			return false;
		}
	}else{
		getTransaction().declarerErreur("La date de mise hors circulation doit être renseignée.");
		return false;
	}
	newDateVouR = Services.formateDate(newDateVouR);	
	PMateriel unPMateriel = PMateriel.chercherPMateriel(getTransaction(),getPMatInfosCourant().getPminv());
	if (getTransaction().isErreur()){
		return false;
	}
	int controle = Services.compareDates(newDateVouR,unPMateriel.getDmes());
	if (controle>-1){
		unPMateriel.setDmhs(newDateVouR);
		unPMateriel.modifierPMateriel(getTransaction(),unPMateriel.getPminv());
	}else if (controle==-1){
		getTransaction().declarerErreur("La date hors service doit être supérieure à la date de mise en circulation.");
		setFocus(getNOM_EF_DMHS());
		return false;
	}else{
		return false;
	}
	
	//unPMateriel.supprimerPMateriel(getTransaction());

//	Tout s'est bien passé
	commitTransaction();
	VariableGlobale.enlever(request,"PMATINFOS");
	//initialiseListeEquipement(request);
	setLB_PMATERIEL(LBVide);
//	On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),"");
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (27/04/07 08:33:50)
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
 * Date de création : (27/04/07 08:33:50)
* 
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	je récupère le code de la ligne sélectionner
	int indice = (Services.estNumerique(getVAL_LB_PMATERIEL_SELECT()) ? Integer.parseInt(getVAL_LB_PMATERIEL_SELECT()): -1);
	
	if ("".equals(getVAL_ST_TITRE_ACTION())) {
		setStatut(STATUT_PROCESS_APPELANT);
	} else {
		//initialiseListeEquipement(request);
		setLB_PMATERIEL(LBVide);
	}
//	On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),"");
	
 
	// pour avoir le petit matériel pour toute les actions
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	if (param.equals("actifs")){
		
	}
	PMatInfos monPMatInfos = (PMatInfos)getListePMaterielInfos().get(indice);
	setPMatInfosCourant(monPMatInfos);
	//On met la variable activité
	VariableActivite.ajouter(this, "PMATINFOS", getPMatInfosCourant());

	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_CONTACT
 * Date de création : (27/04/07 08:37:20)
* 
 */
public java.lang.String getNOM_ST_CONTACT() {
	return "NOM_ST_CONTACT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_CONTACT
 * Date de création : (27/04/07 08:37:20)
* 
 */
public java.lang.String getVAL_ST_CONTACT() {
	return getZone(getNOM_ST_CONTACT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DGARANTIE
 * Date de création : (27/04/07 08:37:20)
* 
 */
public java.lang.String getNOM_ST_DGARANTIE() {
	return "NOM_ST_DGARANTIE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DGARANTIE
 * Date de création : (27/04/07 08:37:20)
* 
 */
public java.lang.String getVAL_ST_DGARANTIE() {
	return getZone(getNOM_ST_DGARANTIE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DMES
 * Date de création : (27/04/07 08:37:20)
* 
 */
public java.lang.String getNOM_ST_DMES() {
	return "NOM_ST_DMES";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DMES
 * Date de création : (27/04/07 08:37:20)
* 
 */
public java.lang.String getVAL_ST_DMES() {
	return getZone(getNOM_ST_DMES());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DMHS
 * Date de création : (27/04/07 08:37:20)
* 
 */
public java.lang.String getNOM_ST_DMHS() {
	return "NOM_ST_DMHS";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DMHS
 * Date de création : (27/04/07 08:37:20)
* 
 */
public java.lang.String getVAL_ST_DMHS() {
	return getZone(getNOM_ST_DMHS());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_FRE
 * Date de création : (27/04/07 08:37:20)
* 
 */
public java.lang.String getNOM_ST_FRE() {
	return "NOM_ST_FRE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_FRE
 * Date de création : (27/04/07 08:37:20)
* 
 */
public java.lang.String getVAL_ST_FRE() {
	return getZone(getNOM_ST_FRE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (27/04/07 08:37:20)
* 
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (27/04/07 08:37:20)
* 
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODELE
 * Date de création : (27/04/07 08:37:20)
* 
 */
public java.lang.String getNOM_ST_MODELE() {
	return "NOM_ST_MODELE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODELE
 * Date de création : (27/04/07 08:37:20)
* 
 */
public java.lang.String getVAL_ST_MODELE() {
	return getZone(getNOM_ST_MODELE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PMINV
 * Date de création : (27/04/07 08:37:20)
* 
 */
public java.lang.String getNOM_ST_PMINV() {
	return "NOM_ST_PMINV";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PMINV
 * Date de création : (27/04/07 08:37:20)
* 
 */
public java.lang.String getVAL_ST_PMINV() {
	return getZone(getNOM_ST_PMINV());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PMSERIE
 * Date de création : (27/04/07 08:37:20)
* 
 */
public java.lang.String getNOM_ST_PMSERIE() {
	return "NOM_ST_PMSERIE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PMSERIE
 * Date de création : (27/04/07 08:37:20)
* 
 */
public java.lang.String getVAL_ST_PMSERIE() {
	return getZone(getNOM_ST_PMSERIE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PRIX
 * Date de création : (27/04/07 08:37:20)
* 
 */
public java.lang.String getNOM_ST_PRIX() {
	return "NOM_ST_PRIX";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PRIX
 * Date de création : (27/04/07 08:37:20)
* 
 */
public java.lang.String getVAL_ST_PRIX() {
	return getZone(getNOM_ST_PRIX());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_RESERVE
 * Date de création : (27/04/07 08:37:20)
* 
 */
public java.lang.String getNOM_ST_RESERVE() {
	return "NOM_ST_RESERVE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_RESERVE
 * Date de création : (27/04/07 08:37:20)
* 
 */
public java.lang.String getVAL_ST_RESERVE() {
	return getZone(getNOM_ST_RESERVE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (27/04/07 08:37:20)
* 
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (27/04/07 08:37:20)
* 
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (27/04/07 08:38:34)
* 
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (27/04/07 08:38:34)
* 
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (27/04/07 09:34:22)
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
 * Date de création : (27/04/07 09:34:22)
* 
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
	return true;
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (25/04/07 14:16:44)
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

		
		//Si clic sur le bouton PB_OK_TRI
		if (testerParametre(request, getNOM_PB_OK_TRI())) {
			return performPB_OK_TRI(request);
		}

		//Si clic sur le bouton PB_AJOUTER
		if (testerParametre(request, getNOM_PB_AJOUTER())) {
			return performPB_AJOUTER(request);
		}

		//Si clic sur le bouton PB_MODIFIER
		if (testerParametre(request, getNOM_PB_MODIFIER())) {
			return performPB_MODIFIER(request);
		}

		//Si clic sur le bouton PB_SUPPRIMER
		if (testerParametre(request, getNOM_PB_SUPPRIMER())) {
			return performPB_SUPPRIMER(request);
		}

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (06/07/07 13:44:52)
* 
 */
@Override
public String getJSP() {
	return "OePMateriel.jsp";
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DMHS
 * Date de création : (06/07/07 13:44:52)
* 
 */
public java.lang.String getNOM_EF_DMHS() {
	return "NOM_EF_DMHS";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DMHS
 * Date de création : (06/07/07 13:44:52)
* 
 */
public java.lang.String getVAL_EF_DMHS() {
	return getZone(getNOM_EF_DMHS());
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
	return getNOM_EF_DMHS();
}
}
