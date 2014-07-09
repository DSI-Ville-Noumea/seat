package nc.mairie.seat.process;

import java.util.ArrayList;
import java.util.Calendar;

import nc.mairie.seat.metier.Declarations;
import nc.mairie.seat.metier.Equipement;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.OT;
import nc.mairie.seat.metier.OTComplet;
import nc.mairie.seat.metier.OTInfos;
import nc.mairie.technique.*;
/**
 * Process OeOT
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
*/
public class OeOT extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5547170503609921847L;
	public static final int STATUT_MODIFIER = 2;
	public static final int STATUT_RECHERCHE_EQUIP = 1;
	private java.lang.String[] LB_OT;
	private String ACTION_MODIFICATION = "Modification";
	private String ACTION_CREATION = "Création";
	private String ACTION_SUPPRESSION = "Suppression d'un OT.<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private ArrayList<OTComplet> listeOT;
	private OT otCourant;
	private OTInfos otInfosCourant;
	private OTComplet otCompletCourant;
	private EquipementInfos equipementInfosCourant;
	private Equipement equipementCourant;
	public int isVide = 0;
	private String param ="encours";
	private String paramDate;
	private String tri="numeroot";
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
	
	//Si LBDATE est vide
	if (getLB_DATE()== LBVide) {
		int annee = Calendar.getInstance().get(Calendar.YEAR);
		String s [] = {"Tous", ">= "+(annee-1)};
		setLB_DATE(s);
		//on sélectionne l'année
		addZone(getNOM_LB_DATE_SELECT(), "1");
		paramDate = getVAL_LB_DATE_SELECT();
	}
	
	// on remplit la liste
	if (getListeOT() == null || getListeOT().size()==0 || etatStatut() != STATUT_MEME_PROCESS){
		initialiseListeOT(request);
	}
	trier(getListeOT());
	cocher(param);
	if(getOtCompletCourant()!=null){
		addZone(getNOM_ST_NOOT(),getOtCompletCourant().getNumeroot());
		addZone(getNOM_ST_NOINVENT(),getOtCompletCourant().getNuminv());
		if(getOtCompletCourant().getDateentree().equals("01/01/0001")){
			addZone(getNOM_ST_DENTREE(),"");
		}else{
			addZone(getNOM_ST_DENTREE(),getOtCompletCourant().getDateentree());
		}
		if(getOtCompletCourant().getDatesortie().equals("01/01/0001")){
			addZone(getNOM_ST_DSORTIE(),"");
		}else{
			addZone(getNOM_ST_DSORTIE(),getOtCompletCourant().getDatesortie());
		}
		addZone(getNOM_ST_COMPTEUR(),getOtCompletCourant().getCompteur());
		//if(getOtInfosCourant()!=null){
		addZone(getNOM_ST_NOIMMAT(),getOtCompletCourant().getNumeroimmatriculation());
			//addZone(getNOM_ST_NOINVENT(),getOtInfosCourant().getNumeroinventaire());
		//}
	}
}

public void initialiseListeOT(javax.servlet.http.HttpServletRequest request) throws Exception{

	ArrayList<OTComplet> listOT = new ArrayList<OTComplet>();
	
	String andLaDate = getVAL_LB_DATE_SELECT().equals("0") ? "" : 
		" (DATEENTREE " + (getLB_DATE()[Integer.parseInt(getVAL_LB_DATE_SELECT())]).replace(' ', '\'')+"-01-01\' " +
		"  OR DATEENTREE = '0001-01-01')";
	
	if(param.equals("")){
		listOT = OTComplet.listerOTComplet(getTransaction(), andLaDate );
	}else if (param.equals("encours")){
		listOT = OTComplet.listerOTCompletEncours(getTransaction(), andLaDate);
		//OT.listerOTDeclarationsEncours(getTransaction());
	}else if(param.equals("valide")){
		listOT = OTComplet.listerOTCompletValide(getTransaction(), andLaDate);
		//OT.listerOTDeclarationsValide(getTransaction());
	} else {
		listOT = OTComplet.listerOTCompletEncours(getTransaction(), andLaDate);
	}
		
	setListeOT(listOT);
	
}

/*
 *  (non-Javadoc)
 * @see nc.mairie.technique.BasicProcess#recupererStatut(javax.servlet.http.HttpServletRequest)
 */
public void trier(ArrayList<OTComplet> a) throws Exception{
	String[] colonnes = {tri};//"numeroot","dateentre"};
	//ordre decroissant
	boolean[] ordres = {tOrdre};//false,false};
	String dentree = "";
	String dsortie = "";
	
//	Si au moins un OT pour PePerso
	if (a.size() !=0 ) {
		ArrayList<OTComplet> aTrier = Services.trier(a,colonnes,ordres);
		setListeOT(aTrier);
		int tailles [] = {10,5,10,10,10,12};
		String[] padding = {"D","G","G","C","C","D"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (int i = 0; i < a.size() ; i++) {
			OTComplet unOTComplet = (OTComplet)aTrier.get(i);	
			// un OT peut être une déclaration ou pas(f_pe_perso)
			//	recherche de l'équipement pour avoir son numéro d'immatriculation
						
			// affichage 
			if (unOTComplet.getDateentree().equals("01/01/0001")){
				dentree="";
			}else{
				dentree=unOTComplet.getDateentree();
			}
			if(unOTComplet.getDatesortie().equals("01/01/0001")){
				dsortie="";
			}else{
				dsortie=unOTComplet.getDatesortie();
			}
			String ligne [] = { unOTComplet.getNumeroot(),unOTComplet.getNuminv(),unOTComplet.getNumeroimmatriculation(),dentree,dsortie,unOTComplet.getCompteur()};
			aFormat.ajouteLigne(ligne);
		}
		setLB_OT(aFormat.getListeFormatee());
	} else {
		setLB_OT(null);
	}
	setIsVide(a.size());
	//return ;
}



/**
 * Constructeur du process OeOT.
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
public OeOT() {
	super();
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {
	int indice  = (Services.estNumerique(getVAL_LB_OT_SELECT()) ? Integer.parseInt(getVAL_LB_OT_SELECT()): -1);
	if (indice == -1 || getListeOT().size() == 0 || indice > getListeOT().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","OT"));
		return false;
	}
	OTComplet unOTComplet = (OTComplet)getListeOT().get(indice);
	setOtCompletCourant(unOTComplet);
	//recherche de l'équipement correspondant à l'ot
	setEquipementInfosCourant(new EquipementInfos());
	if(getOtCompletCourant().getNuminv()!=null){
		EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfos(getTransaction(),getOtCompletCourant().getNuminv());
		if(getTransaction().isErreur()){
			return false;
		}
		setEquipementInfosCourant(unEquipementInfos);
	}else{
		OTInfos unOTInfos = OTInfos.chercherOTInfos(getTransaction(),unOTComplet.getNumeroot());
		if(unOTInfos!=null){
			getTransaction().traiterErreur();
			if(unOTInfos.getNumeroot()==null){
				Declarations uneDeclaration = Declarations.chercherDeclarationsOT(getTransaction(),getOtCourant().getNumeroot());
				if(getTransaction().isErreur()){
					getTransaction().traiterErreur();
				}else{
					EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfosInvOuImmat(getTransaction(),uneDeclaration.getNuminv());
					if(getTransaction().isErreur()){
						return false;
					}
					setEquipementInfosCourant(unEquipementInfos);
				}
			}else{
				EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfos(getTransaction(),unOTInfos.getNumeroinventaire());
				if(getTransaction().isErreur()){
					getTransaction().traiterErreur();
					unEquipementInfos = new EquipementInfos();
				}
				setEquipementInfosCourant(unEquipementInfos);
			}
		//}
		}
		
	}
	if(getOtCompletCourant()!=null){
		if(getOtCompletCourant().getNumeroot()!=null){
			OT unOT = OT.chercherOT(getTransaction(),getOtCompletCourant().getNumeroot());
			if(getTransaction().isErreur()){
				return false;
			}
			setOtCourant(unOT);
		}
	}
	if(null==getOtCourant()){
		setOtCourant(new OT());
	}
	// on renseigne les variable globales
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	//VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",new EquipementInfos());
	VariableGlobale.ajouter(request,"OT",getOtCourant());
	VariableActivite.ajouter(this,"TITRE_ACTION",ACTION_MODIFICATION);
	setStatut(STATUT_MODIFIER,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE_EQUIP
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_RECHERCHE_EQUIP() {
	return "NOM_PB_RECHERCHE_EQUIP";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_RECHERCHE_EQUIP(javax.servlet.http.HttpServletRequest request) throws Exception {
	VariableActivite.ajouter(this,"MODE","OT");
	setStatut(STATUT_RECHERCHE_EQUIP,true);
	return true;
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_SUPPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
	//on récupère l'OT sélectionné
	int indice  = (Services.estNumerique(getVAL_LB_OT_SELECT()) ? Integer.parseInt(getVAL_LB_OT_SELECT()): -1);
	if (indice == -1 || getListeOT().size() == 0 || indice > getListeOT().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","OT"));
		return false;
	}
	OTComplet unOTComplet = (OTComplet)getListeOT().get(indice);
	setOtCompletCourant(unOTComplet);
	if (unOTComplet.getNumeroot()!=null){
		OT unOT = OT.chercherOT(getTransaction(),getOtCompletCourant().getNumeroot());
		if (getTransaction().isErreur()){
			return false;
		}
		setOtCourant(unOT);
	}
	OTInfos unOTInfos = OTInfos.chercherOTInfos(getTransaction(),getOtCourant().getNumeroot());
	if(getTransaction().isErreur()){
		getTransaction().traiterErreur();
	}
	setOtInfosCourant(unOTInfos);
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODELE
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_MODELE() {
	return "NOM_ST_MODELE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODELE
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_MODELE() {
	return getZone(getNOM_ST_MODELE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOIMMAT() {
	return "NOM_ST_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOIMMAT() {
	return getZone(getNOM_ST_NOIMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINVENT
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOINVENT() {
	return "NOM_ST_NOINVENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINVENT
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOINVENT() {
	return getZone(getNOM_ST_NOINVENT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_OT
 * Date de création : (27/07/05 08:09:37)
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
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 */
private void setLB_OT(java.lang.String[] newLB_OT) {
	LB_OT = newLB_OT;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_OT
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_OT() {
	return "NOM_LB_OT";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_OT_SELECT
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_OT_SELECT() {
	return "NOM_LB_OT_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_OT
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_OT() {
	return getLB_OT();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_OT
 * Date de création : (27/07/05 08:09:37)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_OT_SELECT() {
	return getZone(getNOM_LB_OT_SELECT());
}
public Equipement getEquipementCourant() {
	return equipementCourant;
}
public void setEquipementCourant(Equipement equipementCourant) {
	this.equipementCourant = equipementCourant;
}
public EquipementInfos getEquipementInfosCourant() {
	return equipementInfosCourant;
}
public void setEquipementInfosCourant(EquipementInfos equipementInfosCourant) {
	this.equipementInfosCourant = equipementInfosCourant;
}
public int getIsVide() {
	return isVide;
}
public void setIsVide(int isVide) {
	this.isVide = isVide;
}
public ArrayList<OTComplet> getListeOT() {
	return listeOT;
}
public void setListeOT(ArrayList<OTComplet> listeOT) {
	this.listeOT = listeOT;
}
public OT getOtCourant() {
	return otCourant;
}
public void setOtCourant(OT otCourant) {
	this.otCourant = otCourant;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (27/07/05 15:32:43)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (27/07/05 15:32:43)
 * @author : Générateur de process
 * @return String
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
 * @param request request
 * @return boolean
 * @throws Exception Exception
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
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	// si action suppression 
	if(getVAL_ST_TITRE_ACTION().equals(ACTION_SUPPRESSION)){
		getOtCourant().suppressionOT(getTransaction());
		if(getTransaction().isErreur()){
			return false;
		}
		setOtCourant(null);
		setOtCompletCourant(null);
		setListeOT(null);
		VariableGlobale.enlever(request, "OT");
	}
	commitTransaction();
	addZone(getNOM_ST_TITRE_ACTION(),"");
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (28/07/05 15:03:19)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_MODIFIER() {
	return "NOM_PB_MODIFIER";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPRIMER
 * Date de création : (28/07/05 15:03:19)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_SUPPRIMER() {
	return "NOM_PB_SUPPRIMER";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_TRI
 * Date de création : (01/08/05 10:09:16)
 * @author : Générateur de process
 * @return String
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
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_OK_TRI(javax.servlet.http.HttpServletRequest request) throws Exception {
	
	String oldParam = param;
	String oldParamDate = paramDate;
	
	// on renseigne les paramètres
	if (getNOM_RB_AFFICHAGE_ENCOURS().equals(getZone(getNOM_RG_AFFICHAGE()))){
		param = "encours";
	}else if (getNOM_RB_AFFICHAGE_VALIDE().equals(getZone(getNOM_RG_AFFICHAGE()))){
		param = "valide";
	}else{
		param = "";
	}
	
	//on recherche la date sélectionnée
	paramDate = getVAL_LB_DATE_SELECT();
	
	if (!oldParam.equals(param) || !paramDate.equals(oldParamDate)) {
		setListeOT(null);
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
 * @return String
 */
public java.lang.String getNOM_RG_AFFICHAGE() {
	return "NOM_RG_AFFICHAGE";
}
/**
 * Retourne la valeur du radio bouton (RB_) coché dans la JSP :
 * RG_AFFICHAGE
 * Date de création : (01/08/05 10:09:16)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_RG_AFFICHAGE() {
	return getZone(getNOM_RG_AFFICHAGE());
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_AFFICHAGE_ENCOURS
 * Date de création : (01/08/05 10:09:16)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_RB_AFFICHAGE_ENCOURS() {
	return "NOM_RB_AFFICHAGE_ENCOURS";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_AFFICHAGE_TOUS
 * Date de création : (01/08/05 10:09:16)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_RB_AFFICHAGE_TOUS() {
	return "NOM_RB_AFFICHAGE_TOUS";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_AFFICHAGE_VALIDE
 * Date de création : (01/08/05 10:09:16)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_RB_AFFICHAGE_VALIDE() {
	return "NOM_RB_AFFICHAGE_VALIDE";
}

/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (01/08/05 10:11:01)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_ANNULER() {
	return "NOM_PB_ANNULER";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (01/08/05 10:11:01)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_VALIDER() {
	return "NOM_PB_VALIDER";
}
	public OTInfos getOtInfosCourant() {
		return otInfosCourant;
	}
	public void setOtInfosCourant(OTInfos otInfosCourant) {
		this.otInfosCourant = otInfosCourant;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMPTEUR
 * Date de création : (03/08/05 13:46:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_COMPTEUR() {
	return "NOM_ST_COMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMPTEUR
 * Date de création : (03/08/05 13:46:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_COMPTEUR() {
	return getZone(getNOM_ST_COMPTEUR());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DENTREE
 * Date de création : (03/08/05 13:46:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DENTREE() {
	return "NOM_ST_DENTREE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DENTREE
 * Date de création : (03/08/05 13:46:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DENTREE() {
	return getZone(getNOM_ST_DENTREE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DSORTIE
 * Date de création : (03/08/05 13:46:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DSORTIE() {
	return "NOM_ST_DSORTIE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DSORTIE
 * Date de création : (03/08/05 13:46:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DSORTIE() {
	return getZone(getNOM_ST_DSORTIE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOOT
 * Date de création : (03/08/05 13:46:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOOT() {
	return "NOM_ST_NOOT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOOT
 * Date de création : (03/08/05 13:46:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOOT() {
	return getZone(getNOM_ST_NOOT());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (05/08/05 10:09:51)
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
 * Date de création : (05/08/05 10:09:51)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {
	
	OT unOT = new OT();
	EquipementInfos unEquipementInfos = new EquipementInfos();	
	// on renseigne les variable globales
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",unEquipementInfos);
	VariableGlobale.ajouter(request,"OT",unOT);
	VariableActivite.ajouter(this,"TITRE_ACTION",ACTION_CREATION);
	setStatut(STATUT_MODIFIER,true);
	return true;
}
/**
 * Retourne le nom du groupe de radio boutons coché pour la JSP :
 * RG_TRI
 * Date de création : (03/04/07 10:56:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_RG_TRI() {
	return "NOM_RG_TRI";
}
/**
 * Retourne la valeur du radio bouton (RB_) coché dans la JSP :
 * RG_TRI
 * Date de création : (03/04/07 10:56:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_RG_TRI() {
	return getZone(getNOM_RG_TRI());
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_DENTREE
 * Date de création : (03/04/07 10:56:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_RB_TRI_DENTREE() {
	return "NOM_RB_TRI_DENTREE";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_DSORTIE
 * Date de création : (03/04/07 10:56:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_RB_TRI_DSORTIE() {
	return "NOM_RB_TRI_DSORTIE";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_NUMIMMAT
 * Date de création : (03/04/07 10:56:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_RB_TRI_NUMIMMAT() {
	return "NOM_RB_TRI_NUMIMMAT";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_NUMINV
 * Date de création : (03/04/07 10:56:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_RB_TRI_NUMINV() {
	return "NOM_RB_TRI_NUMINV";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_NUMOT
 * Date de création : (03/04/07 10:56:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_RB_TRI_NUMOT() {
	return "NOM_RB_TRI_NUMOT";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_TRI
 * Date de création : (03/04/07 10:56:54)
 * @author : Générateur de process
 * @return String
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
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_TRI(javax.servlet.http.HttpServletRequest request) throws Exception {
//	 selon la sélection la liste sera triée
	if (getNOM_RB_TRI_DENTREE().equals(getZone(getNOM_RG_TRI()))){
		tri = "dateentree";
		tOrdre = false;
	}
	if (getNOM_RB_TRI_DSORTIE().equals(getZone(getNOM_RG_TRI()))){
		tri = "datesortie";
		tOrdre = false;
	}
	if (getNOM_RB_TRI_NUMIMMAT().equals(getZone(getNOM_RG_TRI()))){
		tri = "numeroimmatriculation";//NUMEROIMMATRICULATION
		tOrdre = true;
	}
	if (getNOM_RB_TRI_NUMINV().equals(getZone(getNOM_RG_TRI()))){
		tri = "numinv";
		tOrdre = true;
	}
	if (getNOM_RB_TRI_NUMOT().equals(getZone(getNOM_RG_TRI()))){
		tri = "numeroot";
		tOrdre = false;
	}
	return true;
}
public OTComplet getOtCompletCourant() {
	return otCompletCourant;
}
public void setOtCompletCourant(OTComplet otCompletCourant) {
	this.otCompletCourant = otCompletCourant;
}
	private java.lang.String[] LB_DATE;
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
		if (testerParametre(request, getNOM_PB_RECHERCHE_EQUIP())) {
			return performPB_RECHERCHE_EQUIP(request);
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
 * Date de création : (07/09/11 10:05:12)
 * @author : Générateur de process
 */
public String getJSP() {
	return "OeOT.jsp";
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_DATE
 * Date de création : (07/09/11 10:05:12)
 * @author : Générateur de process
 */
private String [] getLB_DATE() {
	if (LB_DATE == null)
		LB_DATE = initialiseLazyLB();
	return LB_DATE;
}
/**
 * Setter de la liste:
 * LB_DATE
 * Date de création : (07/09/11 10:05:12)
 * @author : Générateur de process
 */
private void setLB_DATE(java.lang.String[] newLB_DATE) {
	LB_DATE = newLB_DATE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_DATE
 * Date de création : (07/09/11 10:05:12)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_DATE() {
	return "NOM_LB_DATE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_DATE_SELECT
 * Date de création : (07/09/11 10:05:12)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_DATE_SELECT() {
	return "NOM_LB_DATE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_DATE
 * Date de création : (07/09/11 10:05:12)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_DATE() {
	return getLB_DATE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_DATE
 * Date de création : (07/09/11 10:05:12)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_DATE_SELECT() {
	return getZone(getNOM_LB_DATE_SELECT());
}
}
