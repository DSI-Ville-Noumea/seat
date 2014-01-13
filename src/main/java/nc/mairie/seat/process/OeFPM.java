package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.FPMComplete;
import nc.mairie.seat.metier.FPM;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.PMateriel;
import nc.mairie.technique.*;
/**
 * Process OeFPM
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
*/
public class OeFPM extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3967951321774861495L;
	public static final int STATUT_MODIFIER = 2;
	public static final int STATUT_RECHERCHE_PMAT = 1;
	private java.lang.String[] LB_FPM;
	private String ACTION_MODIFICATION = "Modification";
	private String ACTION_CREATION = "Création";
	private String ACTION_SUPPRESSION = "Suppression d'une FPM.<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private ArrayList<FPMComplete> listeFPM;
	private FPM fpmCourant;
	private FPMComplete fpmCompleteCourant;
	private PMatInfos pMatInfosCourant;
	private PMateriel pMaterielCourant;
	public int isVide = 0;
	private String param ="encours";
	private String tri="numfiche";
	private boolean tOrdre = false;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	
	// on remplit la liste
	if (getLB_FPM()==LBVide || etatStatut()!=STATUT_MEME_PROCESS) {
		initialiseListeFPM(request);
	}
	cocher(param);
	if(getFpmCompleteCourant()!=null){
		addZone(getNOM_ST_NOOT(),getFpmCompleteCourant().getNumfiche());
		addZone(getNOM_ST_NOINVENT(),getFpmCompleteCourant().getPminv());
		if(getFpmCompleteCourant().getDentree().equals("01/01/0001")){
			addZone(getNOM_ST_DENTREE(),"");
		}else{
			addZone(getNOM_ST_DENTREE(),getFpmCompleteCourant().getDentree());
		}
		if(getFpmCompleteCourant().getDsortie().equals("01/01/0001")){
			addZone(getNOM_ST_DSORTIE(),"");
		}else{
			addZone(getNOM_ST_DSORTIE(),getFpmCompleteCourant().getDsortie());
		}
		addZone(getNOM_ST_NOSERIE(),getFpmCompleteCourant().getPmserie());
	}
}

public void initialiseListeFPM(javax.servlet.http.HttpServletRequest request) throws Exception{
	
	ArrayList<FPMComplete> listFPM;
	if(getTransaction().isErreur()){
		return ;
	}
	if(param.equals("")){
		listFPM = FPMComplete.listerFPMComplete(getTransaction());
	}else if (param.equals("encours")){
		listFPM = FPMComplete.listerFPMCompleteEnCours(getTransaction());
	}else if(param.equals("valide")){
		listFPM = FPMComplete.listerFPMCompleteValide(getTransaction());
	} else {
		listFPM = FPMComplete.listerFPMCompleteEnCours(getTransaction());
	}
	trier(listFPM);
}

/*
 *  (non-Javadoc)
 * @see nc.mairie.technique.BasicProcess#recupererStatut(javax.servlet.http.HttpServletRequest)
 */
public void trier(ArrayList<FPMComplete> a) throws Exception{
	String[] colonnes = {tri};
	//ordre decroissant
	boolean[] ordres = {tOrdre};//false,false};
	String dentree = "";
	String dsortie = "";
	
//	Si au moins un FPM pour PePerso
	if (a.size() !=0 ) {
		ArrayList<FPMComplete> aTrier = Services.trier(a,colonnes,ordres);
		setListeFPM(aTrier);
		int tailles [] = {10,5,20,10,10};
		String[] padding = {"D","G","G","C","C"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (int i = 0; i < a.size() ; i++) {
			FPMComplete unFPMComplete = (FPMComplete)aTrier.get(i);	
			// un FPM peut être une déclaration ou pas(f_pe_perso)
			//	recherche de l'équipement pour avoir son numéro d'immatriculation
						
			// affichage 
			if (unFPMComplete.getDentree().equals("01/01/0001")){
				dentree="";
			}else{
				dentree=unFPMComplete.getDentree();
			}
			if(unFPMComplete.getDsortie().equals("01/01/0001")){
				dsortie="";
			}else{
				dsortie=unFPMComplete.getDsortie();
			}
			String ligne [] = { unFPMComplete.getNumfiche(),unFPMComplete.getPminv(),unFPMComplete.getPmserie(),dentree,dsortie};
			aFormat.ajouteLigne(ligne);
		}
		setLB_FPM(aFormat.getListeFormatee());
	} else {
		setLB_FPM(null);
	}
	setIsVide(a.size());
	return ;
}



/**
 * Constructeur du process OeFPM.
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
public OeFPM() {
	super();
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {
	int indice  = (Services.estNumerique(getVAL_LB_FPM_SELECT()) ? Integer.parseInt(getVAL_LB_FPM_SELECT()): -1);
	if (indice == -1 || getListeFPM().size() == 0 || indice > getListeFPM().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Fiches d'entretiens"));
		return false;
	}
	FPMComplete unFPMComplete = (FPMComplete)getListeFPM().get(indice);
	setFpmCompleteCourant(unFPMComplete);
	//recherche de l'équipement correspondant à FPM
	setPMatInfosCourant(new PMatInfos());
	if(getFpmCompleteCourant().getPminv()!=null){
		PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),getFpmCompleteCourant().getPminv());
		if(getTransaction().isErreur()){
			return false;
		}
		if(null==unPMatInfos){
			unPMatInfos = new PMatInfos();
		}
		setPMatInfosCourant(unPMatInfos);
	}
	
	if(getFpmCompleteCourant()!=null){
		if(getFpmCompleteCourant().getPminv()!=null){
			FPM unFPM = FPM.chercherFPM(getTransaction(),getFpmCompleteCourant().getNumfiche());
			if(getTransaction().isErreur()){
				return false;
			}
			setFpmCourant(unFPM);
		}
	}
	// on renseigne les variable globales
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	VariableGlobale.ajouter(request,"FPM",getFpmCourant());
	VariableActivite.ajouter(this,"TITRE_ACTION",ACTION_MODIFICATION);
	setStatut(STATUT_MODIFIER,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE_EQUIP
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_RECHERCHE_PMAT() {
	return "NOM_PB_RECHERCHE_PMAT";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
public boolean performPB_RECHERCHE_PMAT(javax.servlet.http.HttpServletRequest request) throws Exception {
	VariableActivite.ajouter(this,"MODE","FPM");
	setStatut(STATUT_RECHERCHE_PMAT,true);
	return true;
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
public boolean performPB_SUPPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
	//on récupère FPM sélectionné
	int indice  = (Services.estNumerique(getVAL_LB_FPM_SELECT())) ? Integer.parseInt(getVAL_LB_FPM_SELECT()): -1;
	if (indice == -1 || getListeFPM().size() == 0 || indice > getListeFPM().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Fiches d'entretiens"));
		return false;
	}
	FPMComplete unFPMComplete= (FPMComplete)getListeFPM().get(indice);
	setFpmCompleteCourant(unFPMComplete);
	if (unFPMComplete.getNumfiche()!=null){
		FPM unFPM = FPM.chercherFPM(getTransaction(),getFpmCompleteCourant().getNumfiche());
		if (getTransaction().isErreur()){
			return false;
		}
		setFpmCourant(unFPM);
	}
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODELE
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_MODELE() {
	return "NOM_ST_MODELE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODELE
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_MODELE() {
	return getZone(getNOM_ST_MODELE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOSERIE() {
	return "NOM_ST_NOSERIE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOSERIE() {
	return getZone(getNOM_ST_NOSERIE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINVENT
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOINVENT() {
	return "NOM_ST_NOINVENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINVENT
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOINVENT() {
	return getZone(getNOM_ST_NOINVENT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_FPM
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
private String [] getLB_FPM() {
	if (LB_FPM == null)
		LB_FPM = initialiseLazyLB();
	return LB_FPM;
}
/**
 * Setter de la liste:
 * LB_FPM
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
private void setLB_FPM(java.lang.String[] newLB_FPM) {
	LB_FPM = newLB_FPM;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_FPM
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_FPM() {
	return "NOM_LB_FPM";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_FPM_SELECT
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_FPM_SELECT() {
	return "NOM_LB_FPM_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_FPM
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_FPM() {
	return getLB_FPM();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_FPM
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_FPM_SELECT() {
	return getZone(getNOM_LB_FPM_SELECT());
}
public PMateriel getPMaterielCourant() {
	return pMaterielCourant;
}
public void setPMaterielCourant(PMateriel pMaterielCourant) {
	this.pMaterielCourant = pMaterielCourant;
}
public PMatInfos getPMatInfosCourant() {
	return pMatInfosCourant;
}
public void setPMatInfosCourant(PMatInfos pMatInfosCourant) {
	this.pMatInfosCourant = pMatInfosCourant;
}
public int getIsVide() {
	return isVide;
}
public void setIsVide(int isVide) {
	this.isVide = isVide;
}
public ArrayList<FPMComplete> getListeFPM() {
	return listeFPM;
}
public void setListeFPM(ArrayList<FPMComplete> listeFPM) {
	this.listeFPM = listeFPM;
}
public FPM getFpmCourant() {
	return fpmCourant;
}
public void setFpmCourant(FPM fpmCourant) {
	this.fpmCourant = fpmCourant;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (27/07/05 15:32:43)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (27/07/05 15:32:43)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (27/07/05 15:40:13)
 * @author : Générateur de process
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	addZone(getNOM_ST_TITRE_ACTION(),"");
	return true;
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (27/07/05 15:40:13)
 * @author : Générateur de process
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	// si action suppression 
	if(getVAL_ST_TITRE_ACTION().equals(ACTION_SUPPRESSION)){
		getFpmCourant().suppressionFPM(getTransaction());
		if(getTransaction().isErreur()){
			return false;
		}
	}
	commitTransaction();
	VariableGlobale.enlever(request,"FPM");
	addZone(getNOM_ST_TITRE_ACTION(),"");
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (28/07/05 15:03:19)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_MODIFIER() {
	return "NOM_PB_MODIFIER";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPRIMER
 * Date de création : (28/07/05 15:03:19)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_SUPPRIMER() {
	return "NOM_PB_SUPPRIMER";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_TRI
 * Date de création : (01/08/05 10:09:16)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_OK_TRI() {
	return "NOM_PB_OK_TRI";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (01/08/05 10:09:16)
 * @author : Générateur de process
 */
public boolean performPB_OK_TRI(javax.servlet.http.HttpServletRequest request) throws Exception {
	
	String oldParam = param;
	
	// on renseigne les paramètres
	if (getNOM_RB_AFFICHAGE_ENCOURS().equals(getZone(getNOM_RG_AFFICHAGE()))){
		param = "encours";
	}else if (getNOM_RB_AFFICHAGE_VALIDE().equals(getZone(getNOM_RG_AFFICHAGE()))){
		param = "valide";
	}else{
		param = "";
	}
	
	if (oldParam != param) {
		setLB_FPM(LBVide);
		initialiseListeFPM(request);
	}
	
	return true;
}
public void cocher(String param){
	// Selon le param on coche le bon affichage
	addZone(getNOM_RG_AFFICHAGE(),getNOM_RB_AFFICHAGE_ENCOURS());
	if ("encours".equals(param)){
		addZone(getNOM_RG_AFFICHAGE(),getNOM_RB_AFFICHAGE_ENCOURS());
	}else if("valide".equals(param)){
		addZone(getNOM_RG_AFFICHAGE(),getNOM_RB_AFFICHAGE_VALIDE());
	}else if("".equals(param)){
		addZone(getNOM_RG_AFFICHAGE(),getNOM_RB_AFFICHAGE_TOUS());
	}
}
/**
 * Retourne le nom du groupe de radio boutons coché pour la JSP :
 * RG_AFFICHAGE
 * Date de création : (01/08/05 10:09:16)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RG_AFFICHAGE() {
	return "NOM_RG_AFFICHAGE";
}
/**
 * Retourne la valeur du radio bouton (RB_) coché dans la JSP :
 * RG_AFFICHAGE
 * Date de création : (01/08/05 10:09:16)
 * @author : Générateur de process
 */
public java.lang.String getVAL_RG_AFFICHAGE() {
	return getZone(getNOM_RG_AFFICHAGE());
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_AFFICHAGE_ENCOURS
 * Date de création : (01/08/05 10:09:16)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_AFFICHAGE_ENCOURS() {
	return "NOM_RB_AFFICHAGE_ENCOURS";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_AFFICHAGE_TOUS
 * Date de création : (01/08/05 10:09:16)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_AFFICHAGE_TOUS() {
	return "NOM_RB_AFFICHAGE_TOUS";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_AFFICHAGE_VALIDE
 * Date de création : (01/08/05 10:09:16)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_AFFICHAGE_VALIDE() {
	return "NOM_RB_AFFICHAGE_VALIDE";
}

/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (01/08/05 10:11:01)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_ANNULER() {
	return "NOM_PB_ANNULER";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (01/08/05 10:11:01)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_VALIDER() {
	return "NOM_PB_VALIDER";
}
	public FPMComplete getFpmCompleteCourant() {
		return fpmCompleteCourant;
	}
	public void setFpmCompleteCourant(FPMComplete fpmCompleteCourant) {
		this.fpmCompleteCourant = fpmCompleteCourant;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMPTEUR
 * Date de création : (03/08/05 13:46:11)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_COMPTEUR() {
	return "NOM_ST_COMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMPTEUR
 * Date de création : (03/08/05 13:46:11)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_COMPTEUR() {
	return getZone(getNOM_ST_COMPTEUR());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DENTREE
 * Date de création : (03/08/05 13:46:11)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_DENTREE() {
	return "NOM_ST_DENTREE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DENTREE
 * Date de création : (03/08/05 13:46:11)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_DENTREE() {
	return getZone(getNOM_ST_DENTREE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DSORTIE
 * Date de création : (03/08/05 13:46:11)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_DSORTIE() {
	return "NOM_ST_DSORTIE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DSORTIE
 * Date de création : (03/08/05 13:46:11)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_DSORTIE() {
	return getZone(getNOM_ST_DSORTIE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOOT
 * Date de création : (03/08/05 13:46:11)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOOT() {
	return "NOM_ST_NOOT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOOT
 * Date de création : (03/08/05 13:46:11)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOOT() {
	return getZone(getNOM_ST_NOOT());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (05/08/05 10:09:51)
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
 * Date de création : (05/08/05 10:09:51)
 * @author : Générateur de process
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {
	
	FPM unFPM = new FPM();
	PMatInfos unPMatInfos = new PMatInfos();	
	// on renseigne les variable globales
	VariableGlobale.ajouter(request,"PMATINFOS",unPMatInfos);
	VariableGlobale.ajouter(request,"FPM",unFPM);
	VariableActivite.ajouter(this,"TITRE_ACTION",ACTION_CREATION);
	setStatut(STATUT_MODIFIER,true);
	return true;
}
/**
 * Retourne le nom du groupe de radio boutons coché pour la JSP :
 * RG_TRI
 * Date de création : (03/04/07 10:56:34)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RG_TRI() {
	return "NOM_RG_TRI";
}
/**
 * Retourne la valeur du radio bouton (RB_) coché dans la JSP :
 * RG_TRI
 * Date de création : (03/04/07 10:56:34)
 * @author : Générateur de process
 */
public java.lang.String getVAL_RG_TRI() {
	return getZone(getNOM_RG_TRI());
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_DENTREE
 * Date de création : (03/04/07 10:56:34)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_DENTREE() {
	return "NOM_RB_TRI_DENTREE";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_DSORTIE
 * Date de création : (03/04/07 10:56:34)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_DSORTIE() {
	return "NOM_RB_TRI_DSORTIE";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_NUMIMMAT
 * Date de création : (03/04/07 10:56:34)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_NUMSERIE() {
	return "NOM_RB_TRI_NUMSERIE";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_NUMINV
 * Date de création : (03/04/07 10:56:34)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_NUMINV() {
	return "NOM_RB_TRI_NUMINV";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_NUFPM
 * Date de création : (03/04/07 10:56:34)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_NUMFICHE() {
	return "NOM_RB_TRI_NUMFICHE";
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_TRI
		if (testerParametre(request, getNOM_PB_TRI())) {
			return performPB_TRI(request);
		}

		//Si clic sur le bouton PB_AJOUTER
		if (testerParametre(request, getNOM_PB_AJOUTER())) {
			return performPB_AJOUTER(request);
		}

		//Si clic sur le bouton PB_OK_TRI
		if (testerParametre(request, getNOM_PB_OK_TRI())) {
			return performPB_OK_TRI(request);
		}

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_VALIDER
		if (testerParametre(request, getNOM_PB_VALIDER())) {
			return performPB_VALIDER(request);
		}

		//Si clic sur le bouton PB_MODIFIER
		if (testerParametre(request, getNOM_PB_MODIFIER())) {
			return performPB_MODIFIER(request);
		}

		//Si clic sur le bouton PB_RECHERCHE_EQUIP
		if (testerParametre(request, getNOM_PB_RECHERCHE_PMAT())) {
			return performPB_RECHERCHE_PMAT(request);
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
 * Date de création : (03/04/07 10:56:54)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeFPM.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_TRI
 * Date de création : (03/04/07 10:56:54)
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
 * Date de création : (03/04/07 10:56:54)
 * @author : Générateur de process
 */
public boolean performPB_TRI(javax.servlet.http.HttpServletRequest request) throws Exception {
//	 selon la sélection la liste sera triée
	if (getNOM_RB_TRI_DENTREE().equals(getZone(getNOM_RG_TRI()))){
		tri = "dentree";
		tOrdre = false;
	}
	if (getNOM_RB_TRI_DSORTIE().equals(getZone(getNOM_RG_TRI()))){
		tri = "dsortie";
		tOrdre = false;
	}
	if (getNOM_RB_TRI_NUMSERIE().equals(getZone(getNOM_RG_TRI()))){
		tri = "pmserie";
		tOrdre = true;
	}
	if (getNOM_RB_TRI_NUMINV().equals(getZone(getNOM_RG_TRI()))){
		tri = "pminv";
		tOrdre = true;
	}
	if (getNOM_RB_TRI_NUMFICHE().equals(getZone(getNOM_RG_TRI()))){
		tri = "numfiche";
		tOrdre = false;
	}
	trier(getListeFPM());
	return true;
}

}
