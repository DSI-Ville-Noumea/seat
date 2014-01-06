package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.Carburant;
import nc.mairie.seat.metier.Pompes;
import nc.mairie.technique.*;
/**
 * Process OeCarburant
 * Date de création : (23/05/05 15:29:40)
 * @author : Générateur de process
*/
public class OeCarburant extends nc.mairie.technique.BasicProcess {
	private java.lang.String[] LB_CARBURANT;
	private String ACTION_SUPPRESSION = "Suppression d'un carburant<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private String ACTION_MODIFICATION = "Modification d'un carburant.";
	private String ACTION_CREATION = "Création d'un carburant.";
	private ArrayList listeCarburant = null;
	private Carburant carburantCourant;
	private String focus = null;
	public int isVide = 0;
	private ArrayList listePompes = null;
	private Pompes pompeCourante;
	
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (23/05/05 15:29:40)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{

	//	Si liste des carburants est vide
	if (getLB_CARBURANT() == LBVide) {
		java.util.ArrayList a = Carburant.listerCarburant(getTransaction());
		setListeCarburant(a);
		
//		if (a.size()>0){
//			//les élèments de la liste
//			int [] tailles = {30,15};
//			String [] champs = {"designationcarbu","num_pompe_atm"};
//			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
//			String [] padding = {"G"};
//			boolean[] colonnes = {true};
//			a = Services.trier(a,champs,colonnes);
//			setListeCarburant(a);
//			
//			FormateListe f = new FormateListe(tailles,a,champs,padding,false);
//			String [] l = f.getListeFormatee();
//			setLB_CARBURANT(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
//		}else{
//			setLB_CARBURANT(null);
//		}
		if(a.size()>0){
			int [] tailles = {30,15};
			String [] padding = {"G","G"};
			FormateListe aFormat = new FormateListe(tailles,padding,false);
			
			for (int i=0;i<a.size();i++){
				Carburant unCarburant= (Carburant)a.get(i);
				Pompes unePompe = Pompes.chercherPompes(getTransaction(),unCarburant.getNum_pompe_atm());
				if(getTransaction().isErreur()){
					return;
				}
				
				String ligne [] = { unCarburant.getDesignationcarbu(),unePompe.getLibelle_pompe()};
				aFormat.ajouteLigne(ligne);
				setLB_CARBURANT(aFormat.getListeFormatee());
			}
		}else{
			setLB_CARBURANT(null);
		}
		setIsVide(a.size());
	}
//	 on sélectionne l'élément en cours
	if(getCarburantCourant()!=null){
		if(getCarburantCourant().getCodecarbu()!=null){
			int position = -1;
			addZone(getNOM_LB_CARBURANT_SELECT(),String.valueOf(-1));
			for (int i = 0; i < getListeCarburant().size(); i++) {
				Carburant unCarburant = (Carburant)getListeCarburant().get(i);
				if (unCarburant.getCodecarbu().equals(getCarburantCourant().getCodecarbu())) {
					addZone(getNOM_LB_CARBURANT_SELECT(),String.valueOf(i));
					break;
				}
			}	
		}
	}
	// pour les pompes
	initialiseListePompes(request);
	
}
/*
 * initialisation de la liste des pompes
 */
public void initialiseListePompes(javax.servlet.http.HttpServletRequest request) throws Exception{
	ArrayList a = Pompes.listerPompes(getTransaction());
	if (getTransaction().isErreur()){
		return;
	}
	if (a.size()>0){
		//les élèments de la liste
		int [] tailles = {30};
		String [] champs = {"libelle_pompe"};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G"};
		boolean[] colonnes = {true};
		a = Services.trier(a,champs,colonnes);
		setListePompes(a);
		
		FormateListe f = new FormateListe(tailles,a,champs,padding,false);
		String [] l = f.getListeFormatee();
		setLB_POMPES(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
	}else{
		setLB_POMPES(null);
	}
//	 on sélectionne la pompe en cours
	if(getPompeCourante()!=null){
		if(getPompeCourante().getNum_pompe()!=null){
			int position = -1;
			addZone(getNOM_LB_POMPES_SELECT(),String.valueOf(-1));
			for (int i = 0; i < getListePompes().size(); i++) {
				Pompes unePompe = (Pompes)getListePompes().get(i);
				if (unePompe.getLibelle_pompe().equals(getPompeCourante().getLibelle_pompe())) {
					addZone(getNOM_LB_POMPES_SELECT(),String.valueOf(i));
					break;
				}
			}	
		}
	}
}

/**
 * Initialisation de la liste des carburant
 * @author : Coralie NICOLAS
 */
private void initialiseListeCarburant(javax.servlet.http.HttpServletRequest request) throws Exception{
	//Recherche des carburants
/*	java.util.ArrayList a = Carburant.listerCarburant(getTransaction());
	setListeCarburant(a);
	
	//Si au moins un carburant
	if (a.size() !=0 ) {
		int tailles [] = {20};
		FormateListe aFormat = new FormateListe(tailles);
		for (java.util.ListIterator list = a.listIterator(); list.hasNext(); ) {
			Carburant aCarburant = (Carburant)list.next();
			String ligne [] = { aCarburant.getDesignationcarbu()};
			aFormat.ajouteLigne(ligne);
			
		}
		setLB_CARBURANT(aFormat.getListeFormatee());
	} else {
		setLB_CARBURANT(null);
	}
	
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_LB_CARBURANT_SELECT(),"0");
	addZone(getNOM_EF_DESIGNATION(),"");*/
}


/**
 * Constructeur du process OeCarburant.
 * Date de création : (23/05/05 15:29:40)
 * @author : Générateur de process
 */
public OeCarburant() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (23/05/05 15:29:40)
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
 * Date de création : (23/05/05 15:29:40)
 * @author : Générateur de process
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {

	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
	
	//On vide la zone de saisie
	addZone(getNOM_EF_DESIGNATION(),"");

	setStatut(STATUT_MEME_PROCESS);
	setFocus(getNOM_EF_DESIGNATION());
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (23/05/05 15:29:40)
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
 * Date de création : (23/05/05 15:29:40)
 * @author : Générateur de process
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {

	if ("".equals(getVAL_ST_TITRE_ACTION())) {
		setStatut(STATUT_PROCESS_APPELANT);
	} else {
		//initialiseListeCarburant(request);
		
		setLB_CARBURANT(LBVide);
	}
//	 on vide les zones
	addZone(getNOM_EF_DESIGNATION(),"");
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_ST_DESIGNATION(),"");
	setLB_CARBURANT(LBVide);
	setFocus(null);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (23/05/05 15:29:40)
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
 * Date de création : (23/05/05 15:29:40)
 * @author : Générateur de process
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {

//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_CARBURANT_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_CARBURANT_SELECT())) : -1);
	if (numligne == -1 || getListeCarburant().size() == 0 || numligne > getListeCarburant().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Carburant"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);

	//Récup du type d'intervalle courant
	Carburant carburant = (Carburant)getListeCarburant().get(numligne);
	setCarburantCourant(carburant);

	//Alim zones
	addZone(getNOM_EF_DESIGNATION(), getCarburantCourant().getDesignationcarbu());
	//addZone(getNOM_EF_NUM_POMPE_ATM(),getCarburantCourant().getNum_pompe_atm());
	Pompes unePompe = Pompes.chercherPompes(getTransaction(),getCarburantCourant().getNum_pompe_atm());
	if(getTransaction().isErreur()){
		return false;
	}
	setPompeCourante(unePompe);
	
	setStatut(STATUT_MEME_PROCESS);
	setFocus(getNOM_EF_DESIGNATION());
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (23/05/05 15:29:40)
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
 * Date de création : (23/05/05 15:29:40)
 * @author : Générateur de process
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_CARBURANT_SELECT()) ? Integer.parseInt(getVAL_LB_CARBURANT_SELECT()): -1); 
		
	//récup du type d'intervalle

	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	
	Carburant monCarburant = (Carburant)getListeCarburant().get(indice);
	setCarburantCourant(monCarburant);
	
	addZone(getNOM_EF_DESIGNATION(), monCarburant.getDesignationcarbu());
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPRIMER
 * Date de création : (23/05/05 15:29:40)
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
 * Date de création : (23/05/05 15:29:40)
 * @author : Générateur de process
 */
public boolean performPB_SUPPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_CARBURANT_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_CARBURANT_SELECT())) : -1);
	if (numligne == -1 || getListeCarburant().size() == 0 || numligne > getListeCarburant().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Carburant"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);

	//Récup du type d'intervalle courante
	Carburant carburant = (Carburant)getListeCarburant().get(numligne);
	setCarburantCourant(carburant);

	//Alim zones
	addZone(getNOM_ST_DESIGNATION(), carburant.getDesignationcarbu());
	addZone(getNOM_EF_DESIGNATION(), carburant.getDesignationcarbu());
	
	setStatut(STATUT_MEME_PROCESS);	
	
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (23/05/05 15:29:40)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (23/05/05 15:29:40)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DESIGNATION
 * Date de création : (23/05/05 15:29:40)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_DESIGNATION() {
	return "NOM_EF_DESIGNATION";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DESIGNATION
 * Date de création : (23/05/05 15:29:40)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_DESIGNATION() {
	return getZone(getNOM_EF_DESIGNATION());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_CARBURANT
 * Date de création : (23/05/05 15:29:40)
 * @author : Générateur de process
 */
private String [] getLB_CARBURANT() {
	if (LB_CARBURANT == null)
		LB_CARBURANT = initialiseLazyLB();
	return LB_CARBURANT;
}
/**
 * Setter de la liste:
 * LB_CARBURANT
 * Date de création : (23/05/05 15:29:40)
 * @author : Générateur de process
 */
private void setLB_CARBURANT(java.lang.String[] newLB_CARBURANT) {
	LB_CARBURANT = newLB_CARBURANT;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_CARBURANT
 * Date de création : (23/05/05 15:29:40)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_CARBURANT() {
	return "NOM_LB_CARBURANT";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_CARBURANT_SELECT
 * Date de création : (23/05/05 15:29:40)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_CARBURANT_SELECT() {
	return "NOM_LB_CARBURANT_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_CARBURANT
 * Date de création : (23/05/05 15:29:40)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_CARBURANT() {
	return getLB_CARBURANT();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_CARBURANT
 * Date de création : (23/05/05 15:29:40)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_CARBURANT_SELECT() {
	return getZone(getNOM_LB_CARBURANT_SELECT());
}
	/**
	 * @return Renvoie carburantCourant.
	 */
	private Carburant getCarburantCourant() {
		return carburantCourant;
	}
	/**
	 * @param carburantCourant carburantCourant à définir.
	 */
	private void setCarburantCourant(Carburant carburantCourant) {
		this.carburantCourant = carburantCourant;
	}
	/**
	 * @return Renvoie listeCarburant.
	 */
	private ArrayList getListeCarburant() {
		return listeCarburant;
	}
	/**
	 * @param listeCarburant listeCarburant à définir.
	 */
	private void setListeCarburant(ArrayList listeCarburant) {
		this.listeCarburant = listeCarburant;
	}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (23/05/05 15:44:36)
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
 * Date de création : (23/05/05 15:44:36)
 * @author : Générateur de process
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Si aucune action en cours
	if (getZone(getNOM_ST_TITRE_ACTION()).length() == 0) {
		setStatut(STATUT_MEME_PROCESS, true, MairieMessages.getMessage("ERR996"));//"Vous ne pouvez pas valider, il n'y a pas d'action en cours."
		return false;
	}

	//Récup des zones saisies
	String newDesignation = getZone(getNOM_EF_DESIGNATION()).toUpperCase();
	String newNumPompeAtm ="";
	
	if (!getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_SUPPRESSION)) {
		int numligne = (Services.estNumerique(getZone(getNOM_LB_POMPES_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_POMPES_SELECT())) : -1);
		Pompes unePompe = (Pompes)getListePompes().get(numligne);
		setPompeCourante(unePompe);
		newNumPompeAtm = getPompeCourante().getNum_pompe();
	
	}
	/*if (newNumPompeAtm == -1 || getListeCarburant().size() == 0 || newNumPompeAtm > getListePompes().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Pompes"));
		return false;
	}*/
	
	//Si Action Suppression
	if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_SUPPRESSION)) {

		//Test si un des champs a été modifié
		if (! newDesignation.equals(getCarburantCourant().getDesignationcarbu().trim())) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR995"));//"ERR995","En suppression, aucune zone n'est modifiable."
			return false;
		}
		
		//Suppression
		try{
			getCarburantCourant().supprimerCarburant(getTransaction());
		}catch(Exception e){
			getTransaction().declarerErreur(" La suppression n'est pas possible.");
		}
		if (getTransaction().isErreur())
			return false;
			
	//Si Action Modification
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_MODIFICATION)) {

		//Si lib contact non saisit
		if (newDesignation.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","du carburant"));//"ERR008","Le libellé @ est obligatoire"
			setFocus(getNOM_EF_DESIGNATION());
			return false;
		}
		
		//Affectation des attributs
		getCarburantCourant().setDesignationcarbu(newDesignation);
		getCarburantCourant().setNum_pompe_atm(String.valueOf(newNumPompeAtm));
		
		//Modification
		getCarburantCourant().modifierCarburant(getTransaction(),newDesignation,String.valueOf(newNumPompeAtm));
		if (getTransaction().isErreur())
			return false;
			
	//Si Action Creation		
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_CREATION)) {

		//Si lib contact non saisit
		if (newDesignation.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","du carburant"));//"ERR008","Le libellé @ est obligatoire"
			setFocus(getNOM_EF_DESIGNATION());
			return false;
		}
		
		setCarburantCourant(new Carburant());
		
		//Affectation des attributs
		getCarburantCourant().setDesignationcarbu(newDesignation.toUpperCase());
		getCarburantCourant().setNum_pompe_atm(String.valueOf(newNumPompeAtm));
		
		//Création
		getCarburantCourant().creerCarburant(getTransaction(),newDesignation);
		if (getTransaction().isErreur())
			return false;
		
		
		
	}

	//Tout s'est bien passé
	commitTransaction();
	//initialiseListeCarburant(request);
	// on vide les zones
	addZone(getNOM_EF_DESIGNATION(),"");
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_ST_DESIGNATION(),"");
	setLB_CARBURANT(LBVide);
	setFocus(null);
	return true;
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
		return getNOM_LB_CARBURANT();
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DESIGNATION
 * Date de création : (14/06/05 13:09:48)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_DESIGNATION() {
	return "NOM_ST_DESIGNATION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DESIGNATION
 * Date de création : (14/06/05 13:09:48)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_DESIGNATION() {
	return getZone(getNOM_ST_DESIGNATION());
}
	public int getIsVide() {
		return isVide;
	}
	public void setIsVide(int isVide) {
		this.isVide = isVide;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NUM_POMPE_ATM
 * Date de création : (31/01/06 09:27:58)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NUM_POMPE_ATM() {
	return "NOM_ST_NUM_POMPE_ATM";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NUM_POMPE_ATM
 * Date de création : (31/01/06 09:27:58)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NUM_POMPE_ATM() {
	return getZone(getNOM_ST_NUM_POMPE_ATM());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_NUM_POMPE_ATM
 * Date de création : (31/01/06 09:27:58)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_NUM_POMPE_ATM() {
	return "NOM_EF_NUM_POMPE_ATM";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_NUM_POMPE_ATM
 * Date de création : (31/01/06 09:27:58)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_NUM_POMPE_ATM() {
	return getZone(getNOM_EF_NUM_POMPE_ATM());
}
	private java.lang.String[] LB_POMPES;
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (23/05/05 15:29:40)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_VALIDER
		if (testerParametre(request, getNOM_PB_VALIDER())) {
			return performPB_VALIDER(request);
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

		//Si clic sur le bouton PB_OK
		if (testerParametre(request, getNOM_PB_OK())) {
			return performPB_OK(request);
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
 * Date de création : (31/01/06 09:31:34)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeCarburant.jsp";
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_POMPES
 * Date de création : (31/01/06 09:31:34)
 * @author : Générateur de process
 */
private String [] getLB_POMPES() {
	if (LB_POMPES == null)
		LB_POMPES = initialiseLazyLB();
	return LB_POMPES;
}
/**
 * Setter de la liste:
 * LB_POMPES
 * Date de création : (31/01/06 09:31:34)
 * @author : Générateur de process
 */
private void setLB_POMPES(java.lang.String[] newLB_POMPES) {
	LB_POMPES = newLB_POMPES;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_POMPES
 * Date de création : (31/01/06 09:31:34)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_POMPES() {
	return "NOM_LB_POMPES";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_POMPES_SELECT
 * Date de création : (31/01/06 09:31:34)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_POMPES_SELECT() {
	return "NOM_LB_POMPES_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_POMPES
 * Date de création : (31/01/06 09:31:34)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_POMPES() {
	return getLB_POMPES();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_POMPES
 * Date de création : (31/01/06 09:31:34)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_POMPES_SELECT() {
	return getZone(getNOM_LB_POMPES_SELECT());
}
	public ArrayList getListePompes() {
		return listePompes;
	}
	public void setListePompes(ArrayList listePompes) {
		this.listePompes = listePompes;
	}
	public Pompes getPompeCourante() {
		return pompeCourante;
	}
	public void setPompeCourante(Pompes pompeCourante) {
		this.pompeCourante = pompeCourante;
	}
}
