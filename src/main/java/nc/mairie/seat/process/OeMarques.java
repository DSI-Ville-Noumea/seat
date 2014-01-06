package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.Marques;
import nc.mairie.technique.*;
/**
 * Process OeMarques
 * Date de création : (04/05/05 13:41:28)
 * @author : Générateur de process
*/
public class OeMarques extends nc.mairie.technique.BasicProcess {
	private java.lang.String[] LB_LISTE_MARQUES;
	private String ACTION_SUPPRESSION = "Suppression d'une marque.<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private String ACTION_MODIFICATION = "Modification d'une marque.";
	private String ACTION_CREATION = "Création d'une marque.";
	private ArrayList listeMarque = null;
	private Marques marquesCourant;
	private String focus = null;
	private String isAction = "";
	public int isVide = 0;
	
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (04/05/05 13:41:28)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{

	//	Si liste des marques est vide
	if (getLB_LISTE_MARQUES() == LBVide) {
		
		java.util.ArrayList a = Marques.listerMarques(getTransaction());
		setListeMarque(a);
		
		if (a.size()>0){
			String[] champs = {"designationmarque"};
			boolean[] colonnes = {true};
			a = Services.trier(a,champs,colonnes);
			setListeMarque(a);
			//les élèments de la liste seront le codemarque pour pouvoir récupérer le dernier élément et les dimensions des marques.
			int [] tailles = {50};
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"G"};
			
			FormateListe f = new FormateListe(tailles,a,champs,padding,false);
			setLB_LISTE_MARQUES(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
		}else{
			setLB_LISTE_MARQUES(null);
		}
		setIsVide(a.size());
	}
	// on sélectionne l'élément en cours
	if(getMarquesCourant()!=null){
		if(getMarquesCourant().getCodemarque()!=null){
			addZone(getNOM_LB_LISTE_MARQUES_SELECT(),String.valueOf(-1));
			for (int i = 0; i < getListeMarque().size(); i++) {
				Marques uneMarque = (Marques)getListeMarque().get(i);
				if (uneMarque.getCodemarque().equals(getMarquesCourant().getCodemarque())) {
					addZone(getNOM_LB_LISTE_MARQUES_SELECT(),String.valueOf(i));
					break;
				}
			}	
		}
	}
	
}

/**
 * Constructeur du process OeMarques.
 * Date de création : (04/05/05 13:41:28)
 * @author : Générateur de process
 */
public OeMarques() {
	super();
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (04/05/05 13:41:28)
 * @author : Générateur de process
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {

	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
	isAction = "creation";
	//On vide la zone de saisie
	addZone(getNOM_EF_LIB_MARQUES(),"");

	setStatut(STATUT_MEME_PROCESS);
	setFocus(getNOM_EF_LIB_MARQUES());
	return true;
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (04/05/05 13:41:28)
 * @author : Générateur de process
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	
	if ("".equals(getVAL_ST_TITRE_ACTION())) {
		setStatut(STATUT_PROCESS_APPELANT);
	} else {
		setLB_LISTE_MARQUES(LBVide);
	}
	//on vide les zones
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_ST_DESIGNATION(),"");
	addZone(getNOM_EF_LIB_MARQUES(),"");
	setFocus(null);
	return true;
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (04/05/05 13:41:28)
 * @author : Générateur de process
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {

//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_LISTE_MARQUES_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_LISTE_MARQUES_SELECT())) : -1);
	if (numligne == -1 || getListeMarque().size() == 0 || numligne > getListeMarque().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Marques"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);
	isAction = "modification";
	//Récup du la marque courante
	Marques marque = (Marques)getListeMarque().get(numligne);
	setMarquesCourant(marque);

	//Alim zones
	//int ligneType = getListeTypeContact().indexOf(getHashTypeContact().get(c.getCodTypeContact()));
	addZone(getNOM_EF_LIB_MARQUES(), marque.getDesignationmarque());
	//addZone(getNOM_LB_TYPE_CONTACT_SELECT(), String.valueOf(ligneType));
	
	setStatut(STATUT_MEME_PROCESS);
	setFocus(getNOM_EF_LIB_MARQUES());
	return true;
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (04/05/05 13:41:28)
 * @author : Générateur de process
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_LISTE_MARQUES_SELECT()) ? Integer.parseInt(getVAL_LB_LISTE_MARQUES_SELECT()): -1); 
		
	//récup de la marque
/*	if (indice == -1) {
		setStatut(STATUT_MEME_PROCESS,true,"Vous devez sélectionner un élement");
		return false;
	}
	*/
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	
	Marques maMarque = (Marques)getListeMarque().get(indice);
	setMarquesCourant(maMarque);
	
	addZone(getNOM_EF_LIB_MARQUES(), maMarque.getDesignationmarque());
	
	return true;
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (04/05/05 13:41:28)
 * @author : Générateur de process
 */
public boolean performPB_SUPPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
	//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_LISTE_MARQUES_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_LISTE_MARQUES_SELECT())) : -1);
	if (numligne == -1 || getListeMarque().size() == 0 || numligne > getListeMarque().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Marques"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);
	isAction = "suppression";
	//Récup de la marque courante
	Marques marque = (Marques)getListeMarque().get(numligne);
	setMarquesCourant(marque);

	//Alim zones
	addZone(getNOM_EF_LIB_MARQUES(), marque.getDesignationmarque());
	addZone(getNOM_ST_DESIGNATION(), marque.getDesignationmarque());
	
	setStatut(STATUT_MEME_PROCESS);	
	
	return true;
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (04/05/05 13:41:28)
 * @author : Générateur de process
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	
//	Si aucune action en cours
	if (getZone(getNOM_ST_TITRE_ACTION()).length() == 0) {
		setStatut(STATUT_MEME_PROCESS, true, MairieMessages.getMessage("ERR996"));//"Vous ne pouvez pas valider, il n'y a pas d'action en cours."
		return false;
	}

	//Récup des zones saisies
	//TypeContact newType = (TypeContact)getListeTypeContact().get(Integer.parseInt(getZone(getNOM_LB_TYPE_CONTACT_SELECT())));
	String newDesignation = getZone(getNOM_EF_LIB_MARQUES()).toUpperCase();
	
	//Si Action Suppression
	if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_SUPPRESSION)) {

		//Test si un des champs a été modifié
		if (! newDesignation.equals(getMarquesCourant().getDesignationmarque().trim())) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR995"));//"ERR995","En suppression, aucune zone n'est modifiable."
			return false;
		}
		
		//Suppression
		try{
			getMarquesCourant().supprimerMarques(getTransaction());
		}catch(Exception e){
			getTransaction().declarerErreur(" La suppression n'est pas possible.");
		}
		
		//if (getTransaction().isErreur())
			//return false;
			
	//Si Action Modification
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_MODIFICATION)) {

		//Si lib entretien non saisit
		if (newDesignation.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","de la marque"));//"ERR008","Le libellé @ est obligatoire"
			return false;
		}
		
		//Affectation des attributs
		getMarquesCourant().setDesignationmarque(newDesignation);
		
		//Modification
		getMarquesCourant().modifierMarques(getTransaction(),newDesignation);
		if (getTransaction().isErreur())
			return false;
			
	//Si Action Creation		
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_CREATION)) {

		//Si lib entretien non saisit
		if (newDesignation.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","de la marque"));//"ERR008","Le libellé @ est obligatoire"
			return false;
		}
		
		setMarquesCourant(new Marques());
		
		//Affectation des attributs
		getMarquesCourant().setDesignationmarque(newDesignation);

		//Création
		getMarquesCourant().creerMarques(getTransaction(),newDesignation);
		if (getTransaction().isErreur())
			return false;		
	}

	//Tout s'est bien passé
	commitTransaction();
	//initialiseListeMarques(request);
	//on vide les zones
	setLB_LISTE_MARQUES(LBVide);
	addZone(getNOM_ST_DESIGNATION(),"");
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_EF_LIB_MARQUES(),"");
	setFocus(null);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (04/05/05 13:44:42)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_AJOUTER() {
	return "NOM_PB_AJOUTER";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (04/05/05 13:44:42)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_ANNULER() {
	return "NOM_PB_ANNULER";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (04/05/05 13:44:42)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_MODIFIER() {
	return "NOM_PB_MODIFIER";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (04/05/05 13:44:42)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_OK() {
	return "NOM_PB_OK";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPRIMER
 * Date de création : (04/05/05 13:44:42)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_SUPPRIMER() {
	return "NOM_PB_SUPPRIMER";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (04/05/05 13:44:42)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_VALIDER() {
	return "NOM_PB_VALIDER";
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (04/05/05 13:44:42)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (04/05/05 13:44:42)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_LIB_MARQUES
 * Date de création : (04/05/05 13:44:42)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_LIB_MARQUES() {
	return "NOM_EF_LIB_MARQUES";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_LIB_MARQUES
 * Date de création : (04/05/05 13:44:43)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_LIB_MARQUES() {
	return getZone(getNOM_EF_LIB_MARQUES());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_LISTE_MARQUES
 * Date de création : (04/05/05 13:44:43)
 * @author : Générateur de process
 */
private String [] getLB_LISTE_MARQUES() {
	if (LB_LISTE_MARQUES == null)
		LB_LISTE_MARQUES = initialiseLazyLB();
	return LB_LISTE_MARQUES;
}
/**
 * Setter de la liste:
 * LB_LISTE_MARQUES
 * Date de création : (04/05/05 13:44:43)
 * @author : Générateur de process
 */
private void setLB_LISTE_MARQUES(java.lang.String[] newLB_LISTE_MARQUES) {
	LB_LISTE_MARQUES = newLB_LISTE_MARQUES;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_LISTE_MARQUES
 * Date de création : (04/05/05 13:44:43)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_LISTE_MARQUES() {
	return "NOM_LB_LISTE_MARQUES";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_LISTE_MARQUES_SELECT
 * Date de création : (04/05/05 13:44:43)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_LISTE_MARQUES_SELECT() {
	return "NOM_LB_LISTE_MARQUES_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_LISTE_MARQUES
 * Date de création : (04/05/05 13:44:43)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_LISTE_MARQUES() {
	return getLB_LISTE_MARQUES();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_LISTE_MARQUES
 * Date de création : (04/05/05 13:44:43)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_LISTE_MARQUES_SELECT() {
	return getZone(getNOM_LB_LISTE_MARQUES_SELECT());
}
	/**
	 * @return Renvoie listeMarque.
	 */
	private ArrayList getListeMarque() {
		return listeMarque;
	}
	/**
	 * @param listeMarque listeMarque à définir.
	 */
	private void setListeMarque(ArrayList listeMarque) {
		this.listeMarque = listeMarque;
	}
	/**
	 * @return Renvoie marquesCourant.
	 */
	private Marques getMarquesCourant() {
		return marquesCourant;
	}
	/**
	 * @param marquesCourant marquesCourant à définir.
	 */
	private void setMarquesCourant(Marques marquesCourant) {
		this.marquesCourant = marquesCourant;
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
		return getNOM_LB_LISTE_MARQUES();
	}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (04/05/05 13:41:28)
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
 * Date de création : (14/06/05 13:09:56)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeMarques.jsp";
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DESIGNATION
 * Date de création : (14/06/05 13:09:56)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_DESIGNATION() {
	return "NOM_ST_DESIGNATION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DESIGNATION
 * Date de création : (14/06/05 13:09:56)
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
}
