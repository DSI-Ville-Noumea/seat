package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.TYPEEQUIP;
import nc.mairie.technique.*;
/**
 * Process OeTypeEquip
 * Date de création : (04/05/05 15:29:55)
 * @author : Générateur de process
*/
public class OeTypeEquip extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4066611968024847434L;
	private java.lang.String[] LB_LISTE_TE;
	private String ACTION_SUPPRESSION = "Suppression d'un type d'équipement.<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private String ACTION_MODIFICATION = "Modification d'un type d'équipement.";
	private String ACTION_CREATION = "Création d'un type d'équipement.";
	private ArrayList<TYPEEQUIP> listeTe = null;
	private TYPEEQUIP teCourant;
	private String focus = null;
	public int isVide = 0;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (04/05/05 15:29:55)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{

	//	Si liste des te est vide
	if (getLB_LISTE_TE() == LBVide) {
			ArrayList<TYPEEQUIP> a = TYPEEQUIP.listerTYPEEQUIP(getTransaction());
			setListeTe(a);
			
		if (a.size()>0){
			//les élèments de la liste seront le codete pour pouvoir récupérer le dernier élément et les dimensions des te.
			int [] tailles = {30,5};
			String [] champs = {"designationte","typete"};
			boolean[] colonnes = {true,true};
			a = Services.trier(a,champs,colonnes);
			setListeTe(a);
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"G","G"};
			
			setLB_LISTE_TE(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
		}else{
			setLB_LISTE_TE(null);
		}
		setIsVide(a.size());
	}

//	 on sélectionne l'élément en cours
	if(getTeCourant()!=null){
		if(getTeCourant().getCodete()!=null){
			addZone(getNOM_LB_LISTE_TE_SELECT(),String.valueOf(-1));
			for (int i = 0; i < getListeTe().size(); i++) {
				TYPEEQUIP unTe = (TYPEEQUIP)getListeTe().get(i);
				if (unTe.getCodete().equals(getTeCourant().getCodete())) {
					addZone(getNOM_LB_LISTE_TE_SELECT(),String.valueOf(i));
					break;
				}
			}	
		}
	}
	
	if (getVAL_ST_TITRE_ACTION().equals("")){
		addZone(getNOM_ST_MATERIEL(),"Petit matériel");
	}
}

/**
 * Constructeur du process OeTypeEquip.
 * Date de création : (04/05/05 15:29:55)
 * @author : Générateur de process
 */
public OeTypeEquip() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (04/05/05 15:29:55)
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
 * Date de création : (04/05/05 15:29:55)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {

	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
	
	//On vide la zone de saisie
	addZone(getNOM_EF_LIB_TYPEEQUIP(),"");
	addZone(getNOM_CK_MATERIEL(),getCHECKED_OFF());
	setStatut(STATUT_MEME_PROCESS);
	setFocus(getNOM_EF_LIB_TYPEEQUIP());
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (04/05/05 15:29:55)
 * @author : Générateur de process
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
 * Date de création : (04/05/05 15:29:55)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	if ("".equals(getVAL_ST_TITRE_ACTION())) {
		setStatut(STATUT_PROCESS_APPELANT);
	} else {
		//initialiseListeTe(request);
		setLB_LISTE_TE(LBVide);
	}
//	 on vide les zones 
	addZone(getNOM_EF_LIB_TYPEEQUIP(),"");
	addZone(getNOM_ST_DESIGNATION(),"");
	addZone(getNOM_ST_MATERIEL(),"");
	addZone(getNOM_CK_MATERIEL(),getCHECKED_OFF());
	addZone(getNOM_ST_TITRE_ACTION(),"");
	setFocus(null);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (04/05/05 15:29:55)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_MODIFIER() {
	return "NOM_PB_MODIFIER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (04/05/05 15:29:55)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {

//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_LISTE_TE_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_LISTE_TE_SELECT())) : -1);
	if (numligne == -1 || getListeTe().size() == 0 || numligne > getListeTe().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","types d'équipement"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);

	//Récup du type d'équipement courant
	TYPEEQUIP te = (TYPEEQUIP)getListeTe().get(numligne);
	setTeCourant(te);

	//Alim zones
	addZone(getNOM_EF_LIB_TYPEEQUIP(), te.getDesignationte());
	
//	 on coche la case si le type d'équipement est un petit matériel
	if ("MT".equals(te.getTypete().trim())){
		addZone(getNOM_CK_MATERIEL(),getCHECKED_ON());
	}else{
		addZone(getNOM_CK_MATERIEL(),getCHECKED_OFF());
	}
	setStatut(STATUT_MEME_PROCESS);
	setFocus(getNOM_EF_LIB_TYPEEQUIP());
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (04/05/05 15:29:55)
 * @author : Générateur de process
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
 * Date de création : (04/05/05 15:29:55)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_LISTE_TE_SELECT()) ? Integer.parseInt(getVAL_LB_LISTE_TE_SELECT()): -1); 
		
	//récup du type d'équipement
/*	if (indice == -1) {
		setStatut(STATUT_MEME_PROCESS,true,"Vous devez sélectionner un élement");
		return false;
	}
	*/
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	
	TYPEEQUIP monTe = (TYPEEQUIP)getListeTe().get(indice);
	setTeCourant(monTe);
	
	addZone(getNOM_EF_LIB_TYPEEQUIP(), monTe.getDesignationte());
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPRIMER
 * Date de création : (04/05/05 15:29:55)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_SUPPRIMER() {
	return "NOM_PB_SUPPRIMER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (04/05/05 15:29:55)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_SUPPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_LISTE_TE_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_LISTE_TE_SELECT())) : -1);
	if (numligne == -1 || getListeTe().size() == 0 || numligne > getListeTe().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","TYPEEQUIP"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);

	//Récup du te courant
	TYPEEQUIP Te = (TYPEEQUIP)getListeTe().get(numligne);
	setTeCourant(Te);

	//Alim zones
	addZone(getNOM_EF_LIB_TYPEEQUIP(), Te.getDesignationte());
	addZone(getNOM_ST_DESIGNATION(), Te.getDesignationte());
	if (Te.getTypete().equals("E")){
		addZone(getNOM_ST_MATERIEL(),"est un véhicule");
	}else{
		addZone(getNOM_ST_MATERIEL(),"est un petit matériel");
	}
	setStatut(STATUT_MEME_PROCESS);	
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (04/05/05 15:29:55)
 * @author : Générateur de process
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
 * Date de création : (04/05/05 15:29:55)
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
	String newTe = getZone(getNOM_EF_LIB_TYPEEQUIP()).toUpperCase();
	String newMat = "E";
	if ((getVAL_CK_MATERIEL().equals(getCHECKED_ON()))){
		newMat = "MT";
	}
	//Si Action Suppression
	if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_SUPPRESSION)) {

		//Test si un des champs a été modifié
		if (! newTe.equals(getTeCourant().getDesignationte().trim())) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR995"));//"ERR995","En suppression, aucune zone n'est modifiable."
			return false;
		}
		
		//Suppression
		try{
			getTeCourant().supprimerTYPEEQUIP(getTransaction());
		}catch(Exception e){
			getTransaction().declarerErreur(" La suppression n'est pas possible.");
		}
		if (getTransaction().isErreur())
			return false;
			
	//Si Action Modification
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_MODIFICATION)) {

		//Si lib te non saisit
		if (newTe.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","du type d'équipement"));//"ERR008","Le libellé @ est obligatoire"
			return false;
		}
		
		//Affectation des attributs
		getTeCourant().setDesignationte(newTe);
		getTeCourant().setTypete(newMat);
		//Modification
		getTeCourant().modifierTYPEEQUIP(getTransaction(),newTe,newMat);
		if (getTransaction().isErreur())
			return false;
			
	//Si Action Creation		
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_CREATION)) {

		//Si lib contact non saisit
		if (newTe.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","du type d'équipement"));//"ERR008","Le libellé @ est obligatoire"
			setFocus(getNOM_EF_LIB_TYPEEQUIP());
			return false;
		}
		
		setTeCourant(new TYPEEQUIP());
		
		//Affectation des attributs
		getTeCourant().setDesignationte(newTe);
		getTeCourant().setTypete(newMat);
		//Création
		getTeCourant().creerTYPEEQUIP(getTransaction(),newTe,newMat);
		if (getTransaction().isErreur())
			return false;		
	}

	//Tout s'est bien passé
	commitTransaction();
	//initialiseListeTe(request);
	// on vide les zones 
	addZone(getNOM_EF_LIB_TYPEEQUIP(),"");
	addZone(getNOM_ST_DESIGNATION(),"");
	addZone(getNOM_ST_MATERIEL(),"");
	addZone(getNOM_CK_MATERIEL(),getCHECKED_OFF());
	setLB_LISTE_TE(LBVide);
	addZone(getNOM_ST_TITRE_ACTION(),"");
	setFocus(null);
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (04/05/05 15:29:55)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (04/05/05 15:29:55)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_LIB_TYPEEQUIP
 * Date de création : (04/05/05 15:29:55)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_LIB_TYPEEQUIP() {
	return "NOM_EF_LIB_TYPEEQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_LIB_TYPEEQUIP
 * Date de création : (04/05/05 15:29:55)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_LIB_TYPEEQUIP() {
	return getZone(getNOM_EF_LIB_TYPEEQUIP());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_LISTE_TE
 * Date de création : (04/05/05 15:29:55)
 * @author : Générateur de process
 */
private String [] getLB_LISTE_TE() {
	if (LB_LISTE_TE == null)
		LB_LISTE_TE = initialiseLazyLB();
	return LB_LISTE_TE;
}
/**
 * Setter de la liste:
 * LB_LISTE_TE
 * Date de création : (04/05/05 15:29:55)
 * @author : Générateur de process
 */
private void setLB_LISTE_TE(java.lang.String[] newLB_LISTE_TE) {
	LB_LISTE_TE = newLB_LISTE_TE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_LISTE_TE
 * Date de création : (04/05/05 15:29:55)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_LISTE_TE() {
	return "NOM_LB_LISTE_TE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_LISTE_TE_SELECT
 * Date de création : (04/05/05 15:29:55)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_LISTE_TE_SELECT() {
	return "NOM_LB_LISTE_TE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_LISTE_TE
 * Date de création : (04/05/05 15:29:55)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_LISTE_TE() {
	return getLB_LISTE_TE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_LISTE_TE
 * Date de création : (04/05/05 15:29:55)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_LISTE_TE_SELECT() {
	return getZone(getNOM_LB_LISTE_TE_SELECT());
}
	/**
	 * @return Renvoie listeTe.
	 */
	private ArrayList<TYPEEQUIP> getListeTe() {
		return listeTe;
	}
	/**
	 * @param listeTe listeTe à définir.
	 */
	private void setListeTe(ArrayList<TYPEEQUIP> listeTe) {
		this.listeTe = listeTe;
	}
	/**
	 * @return Renvoie teCourant.
	 */
	private TYPEEQUIP getTeCourant() {
		return teCourant;
	}
	/**
	 * @param teCourant teCourant à définir.
	 */
	private void setTeCourant(TYPEEQUIP teCourant) {
		this.teCourant = teCourant;
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
		return getNOM_LB_LISTE_TE();
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DESIGNATION
 * Date de création : (14/06/05 13:10:26)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DESIGNATION() {
	return "NOM_ST_DESIGNATION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DESIGNATION
 * Date de création : (14/06/05 13:10:26)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DESIGNATION() {
	return getZone(getNOM_ST_DESIGNATION());
}
/**
 * Retourne le nom de la case à cocher sélectionnée pour la JSP :
 * CK_MATERIEL
 * Date de création : (23/06/05 07:59:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_CK_MATERIEL() {
	return "NOM_CK_MATERIEL";
}
/**
 * Retourne la valeur de la case à cocher à afficher par la JSP pour la case à cocher  :
 * CK_MATERIEL
 * Date de création : (23/06/05 07:59:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_CK_MATERIEL() {
	return getZone(getNOM_CK_MATERIEL());
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (04/05/05 15:29:55)
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
 * Date de création : (23/06/05 08:07:38)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeTypeEquip.jsp";
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MATERIEL
 * Date de création : (23/06/05 08:07:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_MATERIEL() {
	return "NOM_ST_MATERIEL";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MATERIEL
 * Date de création : (23/06/05 08:07:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_MATERIEL() {
	return getZone(getNOM_ST_MATERIEL());
}
	public int getIsVide() {
		return isVide;
	}
	public void setIsVide(int isVide) {
		this.isVide = isVide;
	}
}
