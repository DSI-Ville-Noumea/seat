package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.BE;
import nc.mairie.seat.metier.ENGJU;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.OT;
import nc.mairie.technique.*;
/**
 * Process OeBE_OT
 * Date de création : (10/08/05 13:13:58)
 * @author : Générateur de process
*/
public class OeBE_OT extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8929366816148749952L;
	private java.lang.String[] LB_BE;
	private String ACTION_SUPPRESSION = "Suppression";
	private String ACTION_CREATION = "Création";
	private String focus = null;
	private String codeBE;
	private String enscom ;
	private ENGJU enjuCourant;
	private EquipementInfos equipementInfosCourant;
	private BE beCourant;
	private ArrayList<ENGJU> listeBE;
	public boolean isListeVide;
	private OT otCourant;
	public boolean isAction;
	public boolean isSuppression;
	private boolean first = true;
	
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (10/08/05 13:13:58)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	// on récupère les variables
	if(first){
		OT unOT = (OT)VariableGlobale.recuperer(request,"OT");
		if (unOT!=null){
			setOtCourant(unOT);
			addZone(getNOM_ST_NOOT(),getOtCourant().getNumeroot());
			EquipementInfos unEquipementInfos = (EquipementInfos)VariableGlobale.recuperer(request,"EQUIPEMENTINFOS");
			if(unEquipementInfos!=null){
				setEquipementInfosCourant(unEquipementInfos);
				addZone(getNOM_ST_NOINVENT(),getEquipementInfosCourant().getNumeroinventaire());
				addZone(getNOM_ST_NOIMMAT(),getEquipementInfosCourant().getNumeroimmatriculation());
				addZone(getNOM_ST_TYPE(),getEquipementInfosCourant().getDesignationtypeequip());
				addZone(getNOM_ST_MODELE(),getEquipementInfosCourant().getDesignationmodele());
				addZone(getNOM_ST_MARQUE(),getEquipementInfosCourant().getDesignationmarque());
			}
		}
		BE unBE = (BE)VariableActivite.recuperer(this,"BE");
		setBeCourant(unBE);
		String titreAction = (String)VariableActivite.recuperer(this,"TITRE_ACTION");
		if(titreAction.equals(ACTION_CREATION)){
			addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
		}else{
			addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);
		}
		if(unBE!=null){
			setBeCourant(unBE);
		}
	}
	if(getVAL_ST_TITRE_ACTION().equals(ACTION_SUPPRESSION)){
		// on renseigne les zones 
		setSuppression(true);
		setAction(true);
		if(getBeCourant().getExerci()!=null){
			//String code = getBeCourant().getCodcol().trim()+getBeCourant().getExerci().trim()+getBeCourant().getCodbud().trim()+getBeCourant().getCodbud().trim()+getBeCourant().getNoengj().trim();
			addZone(getNOM_ST_CODEBE(),getBeCourant().getNoengj());
			// on récupère l'enju
			ENGJU unEnju = ENGJU.chercherpremierENGJU(getTransaction(),getBeCourant().getExerci().trim(),getBeCourant().getNoengj());
			if(getTransaction().isErreur()){
				return ;
			}
			setEnjuCourant(unEnju);
			//on récupère le fournisseur
			setFournisseurCourant(unEnju.getEnscom());
			addZone(getNOM_ST_ENSEIGNE(),getFournisseurCourant());
			// on récupère le montant total
			int total = ENGJU.montantBE(getTransaction(),getEnjuCourant());
			addZone(getNOM_ST_MONTANT(),String.valueOf(total));
		}
	}else{
		setSuppression(false);
	}
	if(getVAL_ST_TITRE_ACTION().equals(ACTION_CREATION)){
		if(getListeBE().size()>0){
			setListeVide(true);
			setAction(true);
		}else{
			setListeVide(false);
			setAction(false);
		}
	}
	setFirst(false);
}

public void initialiseListBe(javax.servlet.http.HttpServletRequest request) throws Exception{
	if(getListeBE().size()>0){
		int tailles [] = {10,10,30,10};
		String[] padding = {"G","G","G","C"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for(int i = 0;i<getListeBE().size();i++){
			ENGJU unEnju = (ENGJU)getListeBE().get(i);
			//on recherche les lignes concernant l'engagement juridique et on calcul le montant
			int montantTotal = ENGJU.montantBE_Equipement(getTransaction(), unEnju, getEquipementInfosCourant().getNumeroinventaire().trim());
			if(montantTotal==-1){
				getTransaction().declarerErreur("Une erreur s'est produite lors du calcul du montant du bon d'engagement.");
				return ;
			}
			//on renseigne la lb
			String ligne [] = { unEnju.getNoengj(),unEnju.getIdetbs(),unEnju.getEnscom(),String.valueOf(montantTotal)};
			aFormat.ajouteLigne(ligne);
		}
		setLB_BE(aFormat.getListeFormatee());
	} else {
		setLB_BE(null);
	}
		
}
/*
public int montantBE(javax.servlet.http.HttpServletRequest request,ENGJU unEnju) throws Exception{
	String codeDep = "";
	
	// controle si unEnju null
	if (unEnju.getCodcoll()==null){
		getTransaction().declarerErreur("Le Bon d'engagement passé en paramètre est null.");
		return -1;
	}
	int total = 0;
	// recherche des lignes du bon d'engagement
	ArrayList listLeju = LEJU.listerLEJUBE(getTransaction(),unEnju.getExerci(),unEnju.getNoengju());
	if(getTransaction().isErreur()){
		return -1;
	}
	String test = "";
	if(listLeju.size()>0){
		for(int i=0;i<listLeju.size();i++){
			LEJU unLeju = (LEJU)listLeju.get(i);
			// si la ligne concerne l'équipement
			codeDep = unLeju.getCddep().substring(1,5);
			if (codeDep.trim().equals(getEquipementInfosCourant().getNumeroinventaire().trim())||unLeju.getCddep().trim().equals(getEquipementInfosCourant().getNumeroinventaire().trim())){
				int montant = Integer.parseInt(unLeju.getMtlengju());
				total = total + montant;
			}
			
		}
	}
	
	return total;
}
*/
/**
 * Constructeur du process OeBE_OT.
 * Date de création : (10/08/05 13:13:58)
 * @author : Générateur de process
 */
public OeBE_OT() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (10/08/05 13:13:58)
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
 * Date de création : (10/08/05 13:13:58)
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
 * Date de création : (10/08/05 13:13:58)
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
 * Date de création : (10/08/05 13:13:58)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_ENLEVER(javax.servlet.http.HttpServletRequest request) throws Exception {
	// on enlève le BE sélectionné de la liste
	int indice = (Services.estNumerique(getVAL_LB_BE_SELECT()) ? Integer.parseInt(getVAL_LB_BE_SELECT()): -1);
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement dans la liste des bons d'engagement");
		return false;
	}
	getListeBE().remove(indice);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (10/08/05 13:13:58)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_PB_OK() {
	return "NOM_PB_OK";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (10/08/05 13:13:58)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
	//String exercice = getZone(getNOM_EF_EXERCICE());
	String enju = getZone(getNOM_EF_ENJU());
	//setCodeBE("100"+exercice+"9"+enju);
	// controle : pr faire la recherche les 2 paramètre doivent être renseignés
	/*if(exercice.equals("")){
		getTransaction().declarerErreur("L'exercice doit être renseigné.");
		return false;
	}*/
	if(enju.equals("")){
		getTransaction().declarerErreur("L'engagement juridique doit être renseigné.");
		return false;
	}
	//String exercice = enju.substring(0,4);
	// on recherche les renseignements concernant le fournisseur
	ENGJU unEnju = ENGJU.chercherdernierENGJU(getTransaction(),enju);
	if(getTransaction().isErreur()){
		return false;
	}
	if (null==unEnju||null==unEnju.getNoengj()){
		getTransaction().declarerErreur("L'engagement juridique ("+enju+") n'a pas été trouvé en base de donnée.");
		return false;
	}
	
	setEnjuCourant(unEnju);
	getListeBE().add(getEnjuCourant());
	initialiseListBe(request);
	/*Fournisseurs unFre = Fournisseurs.chercherFournisseurs(getTransaction(),getEnjuCourant().getIdetbs());
	if(getTransaction().isErreur()){
		return false;
	}*/
	setFournisseurCourant(unEnju.getEnscom());
	
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (10/08/05 13:13:58)
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
 * Date de création : (10/08/05 13:13:58)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	// on parcours la liste et on enregistre dans la tabe BE_OT
	if(getVAL_ST_TITRE_ACTION().equals(ACTION_CREATION)){
		BE unBe = new BE();
		if(getListeBE().size()>0){
			for(int i=0;i<getListeBE().size();i++){
				ENGJU unEnju = (ENGJU)getListeBE().get(i);
				unBe.creerBE(getTransaction(),getOtCourant(),unEnju);
			}
		}
	}else{
		getBeCourant().supprimerBE(getTransaction());
	}
	//si erreur
	if(getTransaction().isErreur()){
		return false;
	}
	
	//tout s'est bien passé
	commitTransaction();
	setSuppression(false);
	addZone(getNOM_ST_ENSEIGNE(),"");
	addZone(getNOM_ST_CODEBE(),"");
	// retour à la fenêtre d'appel
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_ENJU
 * Date de création : (10/08/05 13:13:58)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_EF_ENJU() {
	return "NOM_EF_ENJU";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_ENJU
 * Date de création : (10/08/05 13:13:58)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_EF_ENJU() {
	return getZone(getNOM_EF_ENJU());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_EXERCICE
 * Date de création : (10/08/05 13:13:58)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_EF_EXERCICE() {
	return "NOM_EF_EXERCICE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_EXERCICE
 * Date de création : (10/08/05 13:13:58)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_EF_EXERCICE() {
	return getZone(getNOM_EF_EXERCICE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_BE
 * Date de création : (10/08/05 13:13:58)
 * @author : Générateur de process
 */
private String [] getLB_BE() {
	if (LB_BE == null)
		LB_BE = initialiseLazyLB();
	return LB_BE;
}
/**
 * Setter de la liste:
 * LB_BE
 * Date de création : (10/08/05 13:13:58)
 * @author : Générateur de process
 */
private void setLB_BE(java.lang.String[] newLB_BE) {
	LB_BE = newLB_BE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_BE
 * Date de création : (10/08/05 13:13:58)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_LB_BE() {
	return "NOM_LB_BE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_BE_SELECT
 * Date de création : (10/08/05 13:13:58)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_LB_BE_SELECT() {
	return "NOM_LB_BE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_BE
 * Date de création : (10/08/05 13:13:58)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String [] getVAL_LB_BE() {
	return getLB_BE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_BE
 * Date de création : (10/08/05 13:13:58)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_LB_BE_SELECT() {
	return getZone(getNOM_LB_BE_SELECT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (10/08/05 13:15:12)
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
 * Date de création : (10/08/05 13:15:12)
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
 * Date de création : (10/08/05 13:15:12)
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
 * Date de création : (10/08/05 13:15:12)
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
 * Date de création : (10/08/05 13:15:12)
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
 * Date de création : (10/08/05 13:15:12)
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
 * Date de création : (10/08/05 13:15:12)
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
 * Date de création : (10/08/05 13:15:12)
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
 * Date de création : (10/08/05 13:15:12)
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
 * Date de création : (10/08/05 13:15:12)
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
 * ST_TYPE
 * Date de création : (10/08/05 13:15:12)
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
 * Date de création : (10/08/05 13:15:12)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
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
 */
/**
 * @return String
 */
public String getDefaultFocus() {
	return getNOM_EF_ENJU();
}
	public String getCodeBE() {
		return codeBE;
	}
	public void setCodeBE(String codeBE) {
		this.codeBE = codeBE;
	}
	public ENGJU getEnjuCourant() {
		return enjuCourant;
	}
	public void setEnjuCourant(ENGJU enjuCourant) {
		this.enjuCourant = enjuCourant;
	}
	public String getFournisseurCourant() {
		return enscom;
	}
	public void setFournisseurCourant(String fournisseurCourant) {
		this.enscom = fournisseurCourant;
	}
	public ArrayList<ENGJU> getListeBE() {
		if(listeBE==null){
			listeBE = new ArrayList<ENGJU>();
		}
		return listeBE;
	}
	public void setListeBE(ArrayList<ENGJU> listeBE) {
		this.listeBE = listeBE;
	}
	public boolean isListeVide() {
		return isListeVide;
	}
	public void setListeVide(boolean isListeVide) {
		this.isListeVide = isListeVide;
	}
	public OT getOtCourant() {
		return otCourant;
	}
	public void setOtCourant(OT otCourant) {
		this.otCourant = otCourant;
	}
	public boolean isAction() {
		return isAction;
	}
	public void setAction(boolean isAction) {
		this.isAction = isAction;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_CODEBE
 * Date de création : (11/08/05 09:24:48)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_CODEBE() {
	return "NOM_ST_CODEBE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_CODEBE
 * Date de création : (11/08/05 09:24:48)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_CODEBE() {
	return getZone(getNOM_ST_CODEBE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_ENSEIGNE
 * Date de création : (11/08/05 09:24:48)
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
 * Date de création : (11/08/05 09:24:48)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_ENSEIGNE() {
	return getZone(getNOM_ST_ENSEIGNE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MONTANT
 * Date de création : (11/08/05 09:24:48)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_MONTANT() {
	return "NOM_ST_MONTANT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MONTANT
 * Date de création : (11/08/05 09:24:48)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_MONTANT() {
	return getZone(getNOM_ST_MONTANT());
}
	public BE getBeCourant() {
		return beCourant;
	}
	public void setBeCourant(BE beCourant) {
		this.beCourant = beCourant;
	}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (10/08/05 13:13:58)
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

		//Si clic sur le bouton PB_ENLEVER
		if (testerParametre(request, getNOM_PB_ENLEVER())) {
			return performPB_ENLEVER(request);
		}

		//Si clic sur le bouton PB_OK
		if (testerParametre(request, getNOM_PB_OK())) {
			return performPB_OK(request);
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
 * Date de création : (11/08/05 09:34:11)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeBE_OT.jsp";
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (11/08/05 09:34:12)
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
 * Date de création : (11/08/05 09:34:12)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
	public boolean isSuppression() {
		return isSuppression;
	}
	public void setSuppression(boolean isSuppression) {
		this.isSuppression = isSuppression;
	}
	public EquipementInfos getEquipementInfosCourant() {
		return equipementInfosCourant;
	}
	public void setEquipementInfosCourant(EquipementInfos equipementInfosCourant) {
		this.equipementInfosCourant = equipementInfosCourant;
	}
}
