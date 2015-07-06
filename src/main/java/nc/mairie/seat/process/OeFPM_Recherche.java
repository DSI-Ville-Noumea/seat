package nc.mairie.seat.process;

import java.util.ArrayList;
import java.util.ListIterator;

import nc.mairie.seat.metier.FPM;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.technique.*;
/**
 * Process OeFPM_Recherche
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
*/
public class OeFPM_Recherche extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7269358086797437253L;
	private java.lang.String[] LB_FPM;
	private java.lang.String[] LB_PMATERIEL;
	private ArrayList<PMatInfos> listePMatInfos;
	private FPM fpmCourant;
	private ArrayList<FPM> listeFPM;
	private PMatInfos pMatInfosCourant;
	private String focus = null;
	private boolean first = true;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	
	if(first){
		PMatInfos unPMatInfos = (PMatInfos)VariableActivite.recuperer(this,"PMATINFOS");
		if(unPMatInfos!=null){
			setPMatInfosCourant(unPMatInfos);
			addZone(getNOM_EF_DESIGNATION(),unPMatInfos.getPminv());
		}		
	}
	String param = getZone(getNOM_EF_DESIGNATION());
	//initialisation des listes
	if (null!=(getPMatInfosCourant())){
		ArrayList<PMatInfos> listPM = PMatInfos.chercherListPMatInfosTous(getTransaction(),param);
		if (null == listPM){
			System.out.println("Aucun petit matériel enregistré dans la base.");
		}
		setListePMatInfos(listPM);
		ArrayList<FPM> listFPM = FPM.listerFpmPmat(getTransaction(),getPMatInfosCourant().getPminv());
		if (null == listFPM){
			System.out.println("Aucune fiche de petit matériel enregistrée dans la base.");
		}
		setListeFPM(listFPM);
	}
	performPB_OK(request);
	first = false;
}

public void initialiseFPM(javax.servlet.http.HttpServletRequest request,ArrayList<FPM> a) throws Exception{
	setListeFPM(a);
//	Si au moins un FPM
	if (a.size() !=0 ) {
		int tailles [] = {12};
		FormateListe aFormat = new FormateListe(tailles);
		for (ListIterator<FPM> list = a.listIterator(); list.hasNext(); ) {
			FPM unFPM = (FPM)list.next();
			String ligne [] = { unFPM.getNumfiche()};
			aFormat.ajouteLigne(ligne);
		}
		setLB_FPM(aFormat.getListeFormatee());
	} else {
		setLB_FPM(null);
	}
}

/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
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
 * Constructeur du process OeFPM_Recherche.
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
 */
public OeFPM_Recherche() {
	super();
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeFPM_Recherche.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
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
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	setPMatInfosCourant(null);
	setFpmCourant(null);
	
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
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
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
	String param = getZone(getNOM_EF_DESIGNATION());
	ArrayList<PMatInfos> resultatPMatInfos = PMatInfos.chercherListPMatInfosTous(getTransaction(),param);
	// on remplit la liste des petits matériels
	setListePMatInfos(resultatPMatInfos);
	if(resultatPMatInfos.size()>0){
		//les élèments de la liste 
		int [] tailles = {10,15,15,15,5,10};
		String [] champs = {"pminv","pmserie","designationmarque","designationmodele","designationtypeequip","dmes"};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"D","D","G","G","G","C"};
		
		setLB_PMATERIEL(new FormateListe(tailles,resultatPMatInfos,champs,padding,true).getListeFormatee());
	}else{
		setLB_PMATERIEL(LBVide);
	}
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_EQUIPEMENT
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
 * @return String
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
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_OK_EQUIPEMENT(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_PMATERIEL_SELECT()) ? Integer.parseInt(getVAL_LB_PMATERIEL_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	PMatInfos monPMatInfos = (PMatInfos)getListePMatInfos().get(indice);
	if (getTransaction().isErreur()){
		return false;
	}
	setPMatInfosCourant(monPMatInfos);
	//ArrayList resultat = Planning.chercherPlanningEquip(getTransaction(),equipementInfosCourant.getNumeroinventaire());
	ArrayList<FPM> resultat = FPM.listerFpmPmat(getTransaction(),monPMatInfos.getPminv());
	if(getTransaction().isErreur()){
		return false;
	}
	// on remplit la liste des BPC
	initialiseFPM(request,resultat);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
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
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_FPM_SELECT()) ? Integer.parseInt(getVAL_LB_FPM_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	FPM monFpm = (FPM)getListeFPM().get(indice);
	setFpmCourant(monFpm);
	
	if (null==getPMatInfosCourant()){
		setPMatInfosCourant(new PMatInfos());
	}
//	On met la variable activité
	VariableGlobale.ajouter(request, "PMATINFOS", getPMatInfosCourant());
	VariableGlobale.ajouter(request, "FPM", monFpm);
	setStatut(STATUT_PROCESS_APPELANT);
	indice = -1;
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DESIGNATION
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_DESIGNATION() {
	return "NOM_EF_DESIGNATION";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DESIGNATION
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_DESIGNATION() {
	return getZone(getNOM_EF_DESIGNATION());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_BPC
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
 */
private String [] getLB_FPM() {
	if (LB_FPM == null)
		LB_FPM = initialiseLazyLB();
	return LB_FPM;
}
/**
 * Setter de la liste:
 * LB_BPC
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
 */
private void setLB_FPM(java.lang.String[] newLB_FPM) {
	LB_FPM = newLB_FPM;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_BPC
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_FPM() {
	return "NOM_LB_FPM";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_BPC_SELECT
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_FPM_SELECT() {
	return "NOM_LB_FPM_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_BPC
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_FPM() {
	return getLB_FPM();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_BPC
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_FPM_SELECT() {
	return getZone(getNOM_LB_FPM_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_EQUIPEMENT
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
 */
private String [] getLB_PMATERIEL() {
	if (LB_PMATERIEL == null)
		LB_PMATERIEL = initialiseLazyLB();
	return LB_PMATERIEL;
}
/**
 * Setter de la liste:
 * LB_EQUIPEMENT
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
 */
private void setLB_PMATERIEL(java.lang.String[] newLB_PMATERIEL) {
	LB_PMATERIEL = newLB_PMATERIEL;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_EQUIPEMENT
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_PMATERIEL() {
	return "NOM_LB_PMATERIEL";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_EQUIPEMENT_SELECT
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_PMATERIEL_SELECT() {
	return "NOM_LB_PMATERIEL_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_EQUIPEMENT
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_PMATERIEL() {
	return getLB_PMATERIEL();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_EQUIPEMENT
 * Date de création : (09/06/05 08:25:03)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_PMATERIEL_SELECT() {
	return getZone(getNOM_LB_PMATERIEL_SELECT());
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
	return getNOM_EF_DESIGNATION();
}
	/**
	 * @return Renvoie bpcCourant.
	 */
	public FPM getFpmCourant() {
		return fpmCourant;
	}
	/**
	 * @param fpmCourant fpmCourant à définir.
	 */
	public void setFpmCourant(FPM fpmCourant) {
		this.fpmCourant = fpmCourant;
	}
	/**
	 * @return Renvoie equipementInfosCourant.
	 */
	public PMatInfos getPMatInfosCourant() {
		return pMatInfosCourant;
	}
	/**
	 * @param pMatInfosCourant pMatInfosCourant à définir.
	 */
	public void setPMatInfosCourant(PMatInfos pMatInfosCourant) {
		this.pMatInfosCourant = pMatInfosCourant;
	}
	
	/**
	 * @return Renvoie listeEquipementInfos.
	 */
	public ArrayList<PMatInfos> getListePMatInfos() {
		return listePMatInfos;
	}
	/**
	 * @param listePMatInfos listePMatInfos à définir.
	 */
	public void setListePMatInfos(ArrayList<PMatInfos> listePMatInfos) {
		this.listePMatInfos = listePMatInfos;
	}
	/**
	 * @return Renvoie listeBPC.
	 */
	public ArrayList<FPM> getListeFPM() {
		return listeFPM;
	}
	/**
	 * @param listeFPM listeFPM à définir.
	 */
	public void setListeFPM(ArrayList<FPM> listeFPM) {
		this.listeFPM = listeFPM;
	}
}
