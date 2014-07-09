package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.TDuree;
import nc.mairie.technique.*;
/**
 * Process OeTDuree
 * Date de création : (04/05/05 13:41:28)
 * @author : Générateur de process
*/
public class OeTDuree extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7231005473917904517L;
	private java.lang.String[] LB_TDUREE;
	private String ACTION_SUPPRESSION = "Suppression d'un type de durée.<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private String ACTION_MODIFICATION = "Modification d'un type de durée.";
	private String ACTION_CREATION = "Création d'un type de durée.";
	private ArrayList<TDuree> listeDuree = null;
	private TDuree tdureeCourant;
	private String focus = null;
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
	if (getLB_TDUREE() == LBVide) {
		
		ArrayList<TDuree> a = TDuree.listerTDuree(getTransaction());
		setListeDuree(a);
		
		if (a.size()>0){
			//les élèments de la liste seront le codemarque pour pouvoir récupérer le dernier élément et les dimensions des marques.
			int [] tailles = {30};
			String [] champs = {"designationduree"};
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"G"};
			
			setLB_TDUREE(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
		}else{
			setLB_TDUREE(null);
		}
		isVide = a.size();
	}
//	 on sélectionne l'élément en cours
	if(getTdureeCourant()!=null){
		if(getTdureeCourant().getCodetd()!=null){
			addZone(getNOM_LB_TDUREE_SELECT(),String.valueOf(-1));
			for (int i = 0; i < getListeDuree().size(); i++) {
				TDuree unTduree = (TDuree)getListeDuree().get(i);
				if (unTduree.getCodetd().equals(getTdureeCourant().getCodetd())) {
					addZone(getNOM_LB_TDUREE_SELECT(),String.valueOf(i));
					break;
				}
			}	
		}
	}
	//Si on appuie sur entrée 
	//String test = getZone(getVAL_ST_TITRE_ACTION());

}
/**
 * Constructeur du process OeTDuree.
 * Date de création : (04/05/05 13:41:28)
 * @author : Générateur de process
 */
public OeTDuree() {
	super();
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (04/05/05 13:41:28)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {

	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
	//On vide la zone de saisie
	addZone(getNOM_EF_LIB_TDUREE(),"");

	setStatut(STATUT_MEME_PROCESS);
	setFocus(getNOM_EF_LIB_TDUREE());
	return true;
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (04/05/05 13:41:28)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	
	if ("".equals(getVAL_ST_TITRE_ACTION())) {
		setStatut(STATUT_PROCESS_APPELANT);
	} else {
		setLB_TDUREE(LBVide);
	}
	// on vide les zones
	addZone(getNOM_EF_LIB_TDUREE(),"");
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_ST_DESIGNATION(),"");
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
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {

//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_TDUREE_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_TDUREE_SELECT())) : -1);
	if (numligne == -1 || getListeDuree().size() == 0 || numligne > getListeDuree().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Marques"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);
	//Récup du la marque courante
	TDuree tDuree = (TDuree)getListeDuree().get(numligne);
	setTdureeCourant(tDuree);

	//Alim zones
	//int ligneType = getListeTypeContact().indexOf(getHashTypeContact().get(c.getCodTypeContact()));
	addZone(getNOM_EF_LIB_TDUREE(), tDuree.getDesignationduree());
	//addZone(getNOM_LB_TYPE_CONTACT_SELECT(), String.valueOf(ligneType));
	
	setStatut(STATUT_MEME_PROCESS);
	setFocus(getNOM_EF_LIB_TDUREE());
	return true;
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (04/05/05 13:41:28)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_TDUREE_SELECT()) ? Integer.parseInt(getVAL_LB_TDUREE_SELECT()): -1); 
		
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
	
	TDuree monTduree = (TDuree)getListeDuree().get(indice);
	setTdureeCourant(monTduree);
	
	addZone(getNOM_EF_LIB_TDUREE(), monTduree.getDesignationduree());
	
	return true;
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (04/05/05 13:41:28)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_SUPPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
	//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_TDUREE_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_TDUREE_SELECT())) : -1);
	if (numligne == -1 || getListeDuree().size() == 0 || numligne > getListeDuree().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","TDuree"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);
	//Récup du tduree courante
	TDuree monTduree = (TDuree)getListeDuree().get(numligne);
	setTdureeCourant(monTduree);

	//Alim zones
	addZone(getNOM_EF_LIB_TDUREE(), monTduree.getDesignationduree());
	addZone(getNOM_ST_DESIGNATION(), monTduree.getDesignationduree());
	
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
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	
//	Si aucune action en cours
	if (getZone(getNOM_ST_TITRE_ACTION()).length() == 0) {
		setStatut(STATUT_MEME_PROCESS, true, MairieMessages.getMessage("ERR996"));//"Vous ne pouvez pas valider, il n'y a pas d'action en cours."
		return false;
	}

	//Récup des zones saisies
	String newDesignation = getZone(getNOM_EF_LIB_TDUREE());
	
	//Si Action Suppression
	if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_SUPPRESSION)) {

		//Test si un des champs a été modifié
		if (! newDesignation.equals(getTdureeCourant().getDesignationduree().trim())) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR995"));//"ERR995","En suppression, aucune zone n'est modifiable."
			return false;
		}
		
		//Suppression
		try{
			getTdureeCourant().supprimerTDuree(getTransaction());
		}catch(Exception e){
			getTransaction().declarerErreur(" La suppression n'est pas possible.");
		}
		
		//if (getTransaction().isErreur())
			//return false;
			
	//Si Action Modification
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_MODIFICATION)) {

		//Si lib contact non saisit
		if (newDesignation.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","du type de durée"));//"ERR008","Le libellé @ est obligatoire"
			setFocus(getNOM_EF_LIB_TDUREE());
			return false;
		}
		
		//Affectation des attributs
		getTdureeCourant().setDesignationduree(newDesignation);
		
		//Modification
		getTdureeCourant().modifierTDuree(getTransaction(),newDesignation);
		if (getTransaction().isErreur())
			return false;
			
	//Si Action Creation		
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_CREATION)) {

		//Si lib contact non saisit
		if (newDesignation.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","du type de durée"));//"ERR008","Le libellé @ est obligatoire"
			setFocus(getNOM_EF_LIB_TDUREE());
			return false;
		}
		
		setTdureeCourant(new TDuree());
		
		//Affectation des attributs
		getTdureeCourant().setDesignationduree(newDesignation);

		//Création
		getTdureeCourant().creerTDuree(getTransaction(),newDesignation);
		if (getTransaction().isErreur())
			return false;
	}

	//Tout s'est bien passé
	commitTransaction();
	//initialiseListeMarques(request);
	// on vide les zones
	setLB_TDUREE(LBVide);
	addZone(getNOM_ST_DESIGNATION(),"");
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_EF_LIB_TDUREE(),"");
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (04/05/05 13:44:42)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_AJOUTER() {
	return "NOM_PB_AJOUTER";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (04/05/05 13:44:42)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_ANNULER() {
	return "NOM_PB_ANNULER";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (04/05/05 13:44:42)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_MODIFIER() {
	return "NOM_PB_MODIFIER";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (04/05/05 13:44:42)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_OK() {
	return "NOM_PB_OK";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPRIMER
 * Date de création : (04/05/05 13:44:42)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_SUPPRIMER() {
	return "NOM_PB_SUPPRIMER";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (04/05/05 13:44:42)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_VALIDER() {
	return "NOM_PB_VALIDER";
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (04/05/05 13:44:42)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (04/05/05 13:44:42)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_LIB_TDUREE
 * Date de création : (04/05/05 13:44:42)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_LIB_TDUREE() {
	return "NOM_EF_LIB_TDUREE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_LIB_TDUREE
 * Date de création : (04/05/05 13:44:43)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_LIB_TDUREE() {
	return getZone(getNOM_EF_LIB_TDUREE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_LISTE_MARQUES
 * Date de création : (04/05/05 13:44:43)
 * @author : Générateur de process
 */
private String [] getLB_TDUREE() {
	if (LB_TDUREE == null)
		LB_TDUREE = initialiseLazyLB();
	return LB_TDUREE;
}
/**
 * Setter de la liste:
 * LB_LISTE_MARQUES
 * Date de création : (04/05/05 13:44:43)
 * @author : Générateur de process
 */
private void setLB_TDUREE(java.lang.String[] newLB_TDUREE) {
	LB_TDUREE = newLB_TDUREE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_LISTE_MARQUES
 * Date de création : (04/05/05 13:44:43)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_TDUREE() {
	return "NOM_LB_TDUREE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_LISTE_MARQUES_SELECT
 * Date de création : (04/05/05 13:44:43)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_TDUREE_SELECT() {
	return "NOM_LB_TDUREE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_LISTE_MARQUES
 * Date de création : (04/05/05 13:44:43)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_TDUREE() {
	return getLB_TDUREE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_LISTE_MARQUES
 * Date de création : (04/05/05 13:44:43)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_TDUREE_SELECT() {
	return getZone(getNOM_LB_TDUREE_SELECT());
}
	/**
	 * @return Renvoie listeMarque.
	 */
	private ArrayList<TDuree> getListeDuree() {
		return listeDuree;
	}
	/**
	 * @param listeMarque listeMarque à définir.
	 */
	private void setListeDuree(ArrayList<TDuree> listeDuree) {
		this.listeDuree = listeDuree;
	}
	/**
	 * @return Renvoie marquesCourant.
	 */
	private TDuree getTdureeCourant() {
		return tdureeCourant;
	}
	/**
	 * @param marquesCourant marquesCourant à définir.
	 */
	private void setTdureeCourant(TDuree tdureeCourant) {
		this.tdureeCourant = tdureeCourant;
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
		return getNOM_LB_TDUREE();
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
	return "OeTDuree.jsp";
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DESIGNATION
 * Date de création : (14/06/05 13:09:56)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DESIGNATION() {
	return "NOM_ST_DESIGNATION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DESIGNATION
 * Date de création : (14/06/05 13:09:56)
 * @author : Générateur de process
 * @return String
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
