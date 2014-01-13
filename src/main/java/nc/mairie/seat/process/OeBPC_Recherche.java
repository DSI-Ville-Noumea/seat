package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.BPC;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.technique.*;
/**
 * Process OeBPC_Recherche
 * Date de création : (09/06/05 08:25:03)
 * @author : Générateur de process
*/
public class OeBPC_Recherche extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1669983261656131263L;
	private java.lang.String[] LB_BPC;
	private java.lang.String[] LB_EQUIPEMENT;
	private ArrayList<EquipementInfos> listeEquipementInfos;
	private BPC bpcCourant;
	private ArrayList<BPC> listeBPC;
	private EquipementInfos equipementInfosCourant;
	private PMatInfos pMatInfosCourant;
	private String focus = null;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (09/06/05 08:25:03)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{

	String param = getZone(getNOM_EF_DESIGNATION()).toUpperCase();
	//initialisation des listes
	if (null!=(equipementInfosCourant)){
		ArrayList<EquipementInfos> equipement = EquipementInfos.chercherListEquipementInfosTous(getTransaction(),param);
		if(getTransaction().isErreur()){
			getTransaction().declarerErreur(getTransaction().traiterErreur());
			return ;
		}
		if (null == equipement){
			System.out.println("Aucun équipement enregistré dans la base.");
		}
		setListeEquipementInfos(equipement);
		ArrayList<BPC> bpc = BPC.listerBPCEquipement(getTransaction(),equipementInfosCourant.getNumeroinventaire());
		if (null == bpc){
			System.out.println("Aucun BPC enregistré dans la base.");
		}
		setListeBPC(bpc);
	}
	performPB_OK(request);
	//si aucun élément dans la base 
	if(getListeEquipementInfos().size()==0){
		getTransaction().declarerErreur("Aucun équipement enregistré dans la base.");
		return ;
	}
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (09/06/05 08:25:03)
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
 * Constructeur du process OeBPC_Recherche.
 * Date de création : (09/06/05 08:25:03)
 * @author : Générateur de process
 */
public OeBPC_Recherche() {
	super();
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (09/06/05 08:25:03)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeBPC_Recherche.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (09/06/05 08:25:03)
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
 * Date de création : (09/06/05 08:25:03)
 * @author : Générateur de process
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	equipementInfosCourant = null;
	bpcCourant = null;
	
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (09/06/05 08:25:03)
 * @author : Générateur de process
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
 * @author : Générateur de process
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
	String param = getZone(getNOM_EF_DESIGNATION()).toUpperCase() ;
	ArrayList<EquipementInfos> resultatEquipementInfos = EquipementInfos.chercherListEquipementInfosTous(getTransaction(),param);
	if(getTransaction().isErreur()){
		getTransaction().declarerErreur(getTransaction().traiterErreur()+"L'équipement n'a pas été trouvé.");
		return false;
	}
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
 * @author : Générateur de process
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
 * @author : Générateur de process
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
	ArrayList<BPC> resultat = BPC.listerBPCInventaire(getTransaction(),equipementInfosCourant.getNumeroinventaire());
	if(getTransaction().isErreur()){
		getTransaction().declarerErreur(getTransaction().traiterErreur());
		return false;
	}
	// on remplit la liste des BPC
	setListeBPC(resultat);
	if(resultat.size()>0){
		//les élèments de la liste 
		int [] tailles = {10,10,15,10};
		String [] champs = {"numerobpc","date","valeurcompteur","quantite"};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"D","C","D","D"};
		
		setLB_BPC(new FormateListe(tailles,resultat,champs,padding,true).getListeFormatee());
	}else{
		setLB_BPC(LBVide);
	}
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (09/06/05 08:25:03)
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
 * Date de création : (09/06/05 08:25:03)
 * @author : Générateur de process
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_BPC_SELECT()) ? Integer.parseInt(getVAL_LB_BPC_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	BPC monBPC = (BPC)getListeBPC().get(indice);
	if (getTransaction().isErreur()){
		return false;
	}
	setBpcCourant(monBPC);
	
//	On met la variable activité
	if(null==getEquipementInfosCourant()){
		setEquipementInfosCourant(new EquipementInfos());
	}
	VariableGlobale.ajouter(request, "EQUIPEMENTINFOS", getEquipementInfosCourant());
	VariableGlobale.ajouter(request, "BPC", getBpcCourant());
	setStatut(STATUT_PROCESS_APPELANT);
	indice = -1;
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DESIGNATION
 * Date de création : (09/06/05 08:25:03)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_DESIGNATION() {
	return "NOM_EF_DESIGNATION";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DESIGNATION
 * Date de création : (09/06/05 08:25:03)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_DESIGNATION() {
	return getZone(getNOM_EF_DESIGNATION());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_BPC
 * Date de création : (09/06/05 08:25:03)
 * @author : Générateur de process
 */
private String [] getLB_BPC() {
	if (LB_BPC == null)
		LB_BPC = initialiseLazyLB();
	return LB_BPC;
}
/**
 * Setter de la liste:
 * LB_BPC
 * Date de création : (09/06/05 08:25:03)
 * @author : Générateur de process
 */
private void setLB_BPC(java.lang.String[] newLB_BPC) {
	LB_BPC = newLB_BPC;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_BPC
 * Date de création : (09/06/05 08:25:03)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_BPC() {
	return "NOM_LB_BPC";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_BPC_SELECT
 * Date de création : (09/06/05 08:25:03)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_BPC_SELECT() {
	return "NOM_LB_BPC_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_BPC
 * Date de création : (09/06/05 08:25:03)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_BPC() {
	return getLB_BPC();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_BPC
 * Date de création : (09/06/05 08:25:03)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_BPC_SELECT() {
	return getZone(getNOM_LB_BPC_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_EQUIPEMENT
 * Date de création : (09/06/05 08:25:03)
 * @author : Générateur de process
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
 * @author : Générateur de process
 */
private void setLB_EQUIPEMENT(java.lang.String[] newLB_EQUIPEMENT) {
	LB_EQUIPEMENT = newLB_EQUIPEMENT;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_EQUIPEMENT
 * Date de création : (09/06/05 08:25:03)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_EQUIPEMENT() {
	return "NOM_LB_EQUIPEMENT";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_EQUIPEMENT_SELECT
 * Date de création : (09/06/05 08:25:03)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_EQUIPEMENT_SELECT() {
	return "NOM_LB_EQUIPEMENT_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_EQUIPEMENT
 * Date de création : (09/06/05 08:25:03)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_EQUIPEMENT() {
	return getLB_EQUIPEMENT();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_EQUIPEMENT
 * Date de création : (09/06/05 08:25:03)
 * @author : Générateur de process
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
 * @param focus focus à définir.
 */
public String getDefaultFocus() {
	return getNOM_EF_DESIGNATION();
}
	/**
	 * @return Renvoie bpcCourant.
	 */
	public BPC getBpcCourant() {
		return bpcCourant;
	}
	/**
	 * @param bpcInfosCourant bpcInfosCourant à définir.
	 */
	public void setBpcCourant(BPC bpcCourant) {
		this.bpcCourant = bpcCourant;
	}
	/**
	 * @return Renvoie equipementInfosCourant.
	 */
	public EquipementInfos getEquipementInfosCourant() {
		return equipementInfosCourant;
	}
	/**
	 * @param pMatInfosCourant pMatInfosCourant à définir.
	 */
	public void setPMatInfosCourant(PMatInfos pMatInfosCourant) {
		this.pMatInfosCourant = pMatInfosCourant;
	}
	/**
	 * @return Renvoie equipementInfosCourant.
	 */
	public PMatInfos getPMatInfosCourant() {
		return pMatInfosCourant;
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
	public ArrayList<EquipementInfos> getListeEquipementInfos() {
		return listeEquipementInfos;
	}
	/**
	 * @param listeEquipementInfos listeEquipementInfos à définir.
	 */
	public void setListeEquipementInfos(ArrayList<EquipementInfos> listeEquipementInfos) {
		this.listeEquipementInfos = listeEquipementInfos;
	}
	/**
	 * @return Renvoie listeBPC.
	 */
	public ArrayList<BPC> getListeBPC() {
		return listeBPC;
	}
	/**
	 * @param listeBPC listeBPC à définir.
	 */
	public void setListeBPC(ArrayList<BPC> listeBPC) {
		this.listeBPC = listeBPC;
	}
}
