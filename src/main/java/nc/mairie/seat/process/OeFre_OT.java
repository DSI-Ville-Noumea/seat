package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.Fournisseurs;
import nc.mairie.seat.metier.Fre_OT;
import nc.mairie.seat.metier.OT;
import nc.mairie.technique.*;
/**
 * Process OeFre_OT
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
*/
public class OeFre_OT extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5739481172023778776L;
	private java.lang.String[] LB_FRE;
	private java.lang.String[] LB_FRE_OT;
	private String ACTION_SUPPRESSION = "Suppression";
//	private String ACTION_CREATION = "Création";
	public boolean suppresion;
	private String focus = null;
	public boolean action;
	private ArrayList<Fournisseurs> listFre;
	private ArrayList<Fournisseurs> listFreOT;
	private Fournisseurs freCourant;
	private Fre_OT freOtCourant;
	private EquipementInfos equipementInfos;
	private OT otCourant;
	private boolean first=true;
	public boolean videFreOT;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{

	if(first){
		//on récupère les variables globaless
		EquipementInfos unEquipementInfos = (EquipementInfos)VariableGlobale.recuperer(request,"EQUIPEMENTINFOS");
		Fre_OT unFreOt = (Fre_OT)VariableGlobale.recuperer(request,"FRE_OT");
		String titreAction =(String)VariableGlobale.recuperer(request,"TITRE_ACTION");
		OT unOT = (OT)VariableGlobale.recuperer(request,"OT");
		
		//controle si null 
		if(unOT!=null){
			setOtCourant(unOT);
		}
		if(unEquipementInfos!=null){
			setEquipementInfos(unEquipementInfos);
		}
		if(unFreOt!=null){
			setFreOtCourant(unFreOt);
		}
		if(!titreAction.equals("")){
			addZone(getNOM_ST_TITRE_ACTION(),titreAction);
			if(getVAL_ST_TITRE_ACTION().equals(ACTION_SUPPRESSION)){
				setSuppresion(true);
				setAction(true);
			}else{
				setSuppresion(false);
			}
		}
		// initialisation des zones
		if(getFreOtCourant()!=null){
			if(getFreOtCourant().getNumeroot()!=null){
				Fournisseurs unFre = Fournisseurs.chercherFournisseurs(getTransaction(),getFreOtCourant().getCodefournisseur());
				if(getTransaction().isErreur()){
					return ;
				}
				setFreCourant(unFre);
				addZone(getNOM_ST_ENSEIGNE(),getFreCourant().getEnscom());
			}
		}
		//on affiche les fournisseurs
		performPB_RECHERCHE(request);
	}
	//alimentation des zones
	if(getOtCourant()!=null){
		if(getOtCourant().getNumeroot()!=null){
			addZone(getNOM_ST_NOOT(),getOtCourant().getNumeroot());
		}
	}
	if(getEquipementInfos()!=null){
		if(getEquipementInfos().getNumeroinventaire()!=null){
			addZone(getNOM_ST_TYPE(),getEquipementInfos().getDesignationtypeequip());
			addZone(getNOM_ST_NOINVENT(),getEquipementInfos().getNumeroinventaire());
			addZone(getNOM_ST_NOIMMAT(),getEquipementInfos().getNumeroimmatriculation());
			addZone(getNOM_ST_MODELE(),getEquipementInfos().getDesignationmodele());
			addZone(getNOM_ST_MARQUE(),getEquipementInfos().getDesignationmarque());
		}
	}
	
	if(getListFreOT().size()>0){
		setVideFreOT(false);
	}else{
		setVideFreOT(true);
		setAction(true);
	}
	//initialisation des listes
	initialiseListeFre(request);
	initialiseListeFreOt(request);
	setFirst(false);
}

public void initialiseListeFre(javax.servlet.http.HttpServletRequest request) throws Exception{
	if(getListFre().size()>0){
		//les élèments de la liste 
		int [] tailles = {60};
		String [] champs = {"enscom"};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G"};
		setLB_FRE(new FormateListe(tailles,getListFre(),champs,padding,true).getListeFormatee());
	}else{
		setLB_FRE(LBVide);
	}
}

public void initialiseListeFreOt(javax.servlet.http.HttpServletRequest request) throws Exception{
	if(getListFreOT().size()>0){
		int [] tailles = {60};
		String [] padding = {"G"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (int i=0;i<getListFreOT().size();i++){
			//Fre_OT monFre_OT = (Fre_OT)getListFreOT().get(i);
			//Fournisseurs unFre = Fournisseurs.chercherFournisseurs(getTransaction(),monFre_OT.getCodefournisseur());
			Fournisseurs unFre = (Fournisseurs)getListFreOT().get(i);
			if(getTransaction().isErreur()){
				return ;
			}
			String ligne [] = { unFre.getEnscom()};
			aFormat.ajouteLigne(ligne);
		}
		setLB_FRE_OT(aFormat.getListeFormatee());
	}else{
		setLB_FRE_OT(LBVide);
	}
}


/**
 * Constructeur du process OeFre_OT.
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 */
public OeFre_OT() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 */
/**
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
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {
	// on ajoute le fournisseur à la liste
	int indice = (Services.estNumerique(getVAL_LB_FRE_SELECT()) ? Integer.parseInt(getVAL_LB_FRE_SELECT()): -1);
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élément dans la liste des fournisseurs");
		return false;
	}
	Fournisseurs unFre = (Fournisseurs)getListFre().get(indice);
	setFreCourant(unFre);
//	 on ajoute le fournisseur dans la liste pour l'Ot
	getListFreOT().add(getFreCourant());
	
	// on l'enlève de la liste des pièces
	getListFre().remove(getFreCourant());
	initialiseListeFreOt(request);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 */
/**
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
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ENLEVER
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 */
/**
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
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_ENLEVER(javax.servlet.http.HttpServletRequest request) throws Exception {
	int indice = (Services.estNumerique(getVAL_LB_FRE_OT_SELECT()) ? Integer.parseInt(getVAL_LB_FRE_OT_SELECT()): -1);
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement dans la liste des fournisseurs de l'OT");
		return false;
	}
	Fournisseurs unFre = (Fournisseurs)getListFreOT().get(indice);
	getListFre().add(unFre);
	getListFreOT().remove(indice);
	initialiseListeFreOt(request);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_PB_RECHERCHE() {
	return "NOM_PB_RECHERCHE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_RECHERCHE(javax.servlet.http.HttpServletRequest request) throws Exception {
	String param = getZone(getNOM_EF_RECHERCHE());
	ArrayList<Fournisseurs> resultatFre = Fournisseurs.listerFournisseursNom(getTransaction(),param);
	if(getTransaction().isErreur()){
		return false;
	}
	// on remplit la liste des fournisseurs
	setListFre(resultatFre);
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 */
/**
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
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	//si l'OT pas encore enregistré
	if(getOtCourant().getNumeroot()==null){
		getTransaction().equals("L'OT n'a pas encore été enregistré.");
		return false;
	}
	
	// on enregistre tous les fournisseurs ayant participé à l'OT
//	 si en pas suppression
	if(!getVAL_ST_TITRE_ACTION().equals(ACTION_SUPPRESSION)){
		// on enregistre dans la table pieces_ot 
		Fre_OT unFreOT = new Fre_OT();
		if (getListFreOT().size()>0){
			for (int i=0;i<getListFreOT().size();i++){
				Fournisseurs unFre = (Fournisseurs)getListFreOT().get(i);
				unFreOT.creerFre_OT(getTransaction(),getOtCourant(),unFre);
				
			}
		}
	}else{
		//suppression
		Fre_OT unFreOT = Fre_OT.chercherFre_OT(getTransaction(),getOtCourant().getNumeroot(),getFreCourant().getIdetbs());
		if(getTransaction().isErreur()){
			return false;
		}
		unFreOT.supprimerFre_OT(getTransaction());
	}
	//si erreur
	if(getTransaction().isErreur()){
		return false;
	}
	// si tout s'est bien passé on sauvegarde
	commitTransaction();
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_RECHERCHE
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_EF_RECHERCHE() {
	return "NOM_EF_RECHERCHE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_RECHERCHE
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_EF_RECHERCHE() {
	return getZone(getNOM_EF_RECHERCHE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_FRE
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 */
private String [] getLB_FRE() {
	if (LB_FRE == null)
		LB_FRE = initialiseLazyLB();
	return LB_FRE;
}
/**
 * Setter de la liste:
 * LB_FRE
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 */
private void setLB_FRE(java.lang.String[] newLB_FRE) {
	LB_FRE = newLB_FRE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_FRE
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_LB_FRE() {
	return "NOM_LB_FRE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_FRE_SELECT
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_LB_FRE_SELECT() {
	return "NOM_LB_FRE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_FRE
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String [] getVAL_LB_FRE() {
	return getLB_FRE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_FRE
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_LB_FRE_SELECT() {
	return getZone(getNOM_LB_FRE_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_FRE_OT
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 */
private String [] getLB_FRE_OT() {
	if (LB_FRE_OT == null)
		LB_FRE_OT = initialiseLazyLB();
	return LB_FRE_OT;
}
/**
 * Setter de la liste:
 * LB_FRE_OT
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 */
private void setLB_FRE_OT(java.lang.String[] newLB_FRE_OT) {
	LB_FRE_OT = newLB_FRE_OT;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_FRE_OT
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_LB_FRE_OT() {
	return "NOM_LB_FRE_OT";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_FRE_OT_SELECT
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_LB_FRE_OT_SELECT() {
	return "NOM_LB_FRE_OT_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_FRE_OT
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String [] getVAL_LB_FRE_OT() {
	return getLB_FRE_OT();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_FRE_OT
 * Date de création : (09/08/05 09:13:31)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_LB_FRE_OT_SELECT() {
	return getZone(getNOM_LB_FRE_OT_SELECT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (09/08/05 09:14:35)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (09/08/05 09:14:35)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODELE
 * Date de création : (09/08/05 09:14:35)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_MODELE() {
	return "NOM_ST_MODELE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODELE
 * Date de création : (09/08/05 09:14:35)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_MODELE() {
	return getZone(getNOM_ST_MODELE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (09/08/05 09:14:35)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_NOIMMAT() {
	return "NOM_ST_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (09/08/05 09:14:35)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_NOIMMAT() {
	return getZone(getNOM_ST_NOIMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINVENT
 * Date de création : (09/08/05 09:14:35)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_NOINVENT() {
	return "NOM_ST_NOINVENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINVENT
 * Date de création : (09/08/05 09:14:35)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_NOINVENT() {
	return getZone(getNOM_ST_NOINVENT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOOT
 * Date de création : (09/08/05 09:14:35)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_NOOT() {
	return "NOM_ST_NOOT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOOT
 * Date de création : (09/08/05 09:14:35)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_NOOT() {
	return getZone(getNOM_ST_NOOT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (09/08/05 09:14:35)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (09/08/05 09:14:35)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (09/08/05 09:14:35)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (09/08/05 09:14:35)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_ENSEIGNE
 * Date de création : (09/08/05 09:15:32)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_ENSEIGNE() {
	return "NOM_ST_ENSEIGNE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_ENSEIGNE
 * Date de création : (09/08/05 09:15:32)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_ENSEIGNE() {
	return getZone(getNOM_ST_ENSEIGNE());
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (09/08/05 09:13:31)
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

		//Si clic sur le bouton PB_RECHERCHE
		if (testerParametre(request, getNOM_PB_RECHERCHE())) {
			return performPB_RECHERCHE(request);
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
 * Date de création : (09/08/05 09:17:03)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeFre_OT.jsp";
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (09/08/05 09:17:03)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (09/08/05 09:17:03)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
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
	return getNOM_EF_RECHERCHE();
}
	public boolean isAction() {
		return action;
	}
	public void setAction(boolean action) {
		this.action = action;
	}
	public boolean isSuppresion() {
		return suppresion;
	}
	public void setSuppresion(boolean suppresion) {
		this.suppresion = suppresion;
	}
	public Fournisseurs getFreCourant() {
		return freCourant;
	}
	public void setFreCourant(Fournisseurs freCourant) {
		this.freCourant = freCourant;
	}
	public Fre_OT getFreOtCourant() {
		return freOtCourant;
	}
	public void setFreOtCourant(Fre_OT freOtCourant) {
		this.freOtCourant = freOtCourant;
	}
	public ArrayList<Fournisseurs> getListFre() {
		if(listFre==null){
			listFre = new ArrayList<Fournisseurs>();
		}
		return listFre;
	}
	public void setListFre(ArrayList<Fournisseurs> listFre) {
		this.listFre = listFre;
	}
	public ArrayList<Fournisseurs> getListFreOT() {
		if(listFreOT==null){
			listFreOT = new ArrayList<Fournisseurs>();
		}
		return listFreOT;
	}
	public void setListFreOT(ArrayList<Fournisseurs> listFreOT) {
		this.listFreOT = listFreOT;
	}
	public EquipementInfos getEquipementInfos() {
		return equipementInfos;
	}
	public void setEquipementInfos(EquipementInfos equipementInfos) {
		this.equipementInfos = equipementInfos;
	}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
	public OT getOtCourant() {
		return otCourant;
	}
	public void setOtCourant(OT otCourant) {
		this.otCourant = otCourant;
	}
	public boolean isVideFreOT() {
		return videFreOT;
	}
	public void setVideFreOT(boolean videFreOT) {
		this.videFreOT = videFreOT;
	}
}
