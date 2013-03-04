package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.technique.*;
import nc.mairie.seat.metier.Pneu;
/**
 * Process OePneu
 * Date de création : (28/04/05 08:37:07)
* 
*/
public class OePneu extends nc.mairie.technique.BasicProcess {
	private java.lang.String[] LB_LIST_DIMENSION;
	private String ACTION_SUPPRESSION = "Suppression d'un pneu.<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private String ACTION_MODIFICATION = "Modification d'un pneu.";
	private String ACTION_CREATION = "Création d'un pneu.";
	private ArrayList listePneu = null;
	private Pneu pneuCourant;
	boolean premierFois = true;	
	private String focus = null;
	public int isVide = 0;
	
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (28/04/05 08:37:07)
* 
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{

		//	Si liste des pneus est vide
	if (getLB_LIST_DIMENSION() == LBVide) {
		
		java.util.ArrayList a = Pneu.listerPneu(getTransaction());
		setListePneu(a);
		if (a.size()!=0){
			//les élèments de la liste seront le codePneu pour pouvoir récupérer le dernier élément et les dimensions des pneus.
			int [] tailles = {32};
			String [] champs = {"dimension"};
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"G"};
			
			FormateListe f = new FormateListe(tailles,a,champs,padding,false);
			String [] l = f.getListeFormatee();
			setLB_LIST_DIMENSION(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
			
		}else{
			setLB_LIST_DIMENSION(null);
		}
		setIsVide(a.size());
	}
//	 on sélectionne l'élément en cours
	if(getPneuCourant()!=null){
		if(getPneuCourant().getCodepneu()!=null){
			int position = -1;
			addZone(getNOM_LB_LIST_DIMENSION_SELECT(),String.valueOf(-1));
			for (int i = 0; i < getListePneu().size(); i++) {
				Pneu unPneu = (Pneu)getListePneu().get(i);
				if (unPneu.getCodepneu().equals(getPneuCourant().getCodepneu())) {
					addZone(getNOM_LB_LIST_DIMENSION_SELECT(),String.valueOf(i));
					break;
				}
			}	
		}
	}

}
/**
 * Constructeur du process OePneu.
 * Date de création : (28/04/05 08:37:07)
* 
 */
public OePneu() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (28/04/05 08:37:07)
* 
 */
public java.lang.String getNOM_PB_AJOUTER() {
	return "NOM_PB_AJOUTER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/04/05 08:37:07)
* 
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {

	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
	
	//On vide la zone de saisie
	addZone(getNOM_EF_LIBELLE_DIMENSION(),"");

	//init du pneu courant
	//setPneuCourant(null);

	setStatut(STATUT_MEME_PROCESS);
	setFocus(getNOM_EF_LIBELLE_DIMENSION());
	return true;
	
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (28/04/05 08:37:07)
* 
 */
public java.lang.String getNOM_PB_ANNULER() {
	return "NOM_PB_ANNULER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/04/05 08:37:07)
* 
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	if ("".equals(getVAL_ST_TITRE_ACTION())) {
		setStatut(STATUT_PROCESS_APPELANT);
	} else {
		//initialiseListePneus(request);
		setLB_LIST_DIMENSION(LBVide);
	}
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_ST_DESIGNATION(),"");
	setFocus(null);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (28/04/05 08:37:07)
* 
 */
public java.lang.String getNOM_PB_MODIFIER() {
	return "NOM_PB_MODIFIER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/04/05 08:37:07)
* 
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {

//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_LIST_DIMENSION_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_LIST_DIMENSION_SELECT())) : -1);
	if (numligne == -1 || getListePneu().size() == 0 || numligne > getListePneu().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Pneus"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);

	//Récup du pneu courant
	Pneu pneu = (Pneu)getListePneu().get(numligne);
	setPneuCourant(pneu);

	//Alim zones
	//int ligneType = getListeTypeContact().indexOf(getHashTypeContact().get(c.getCodTypeContact()));
	addZone(getNOM_EF_LIBELLE_DIMENSION(), pneu.getDimension());
	//addZone(getNOM_LB_TYPE_CONTACT_SELECT(), String.valueOf(ligneType));
	
	setStatut(STATUT_MEME_PROCESS);
	setFocus(getNOM_EF_LIBELLE_DIMENSION());
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RETOUR
 * Date de création : (28/04/05 08:37:07)
* 
 */
public java.lang.String getNOM_PB_RETOUR() {
	return "NOM_PB_RETOUR";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/04/05 08:37:07)
* 
 */
public boolean performPB_RETOUR(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_PROCESS_APPELANT);
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPRIMER
 * Date de création : (28/04/05 08:37:07)
* 
 */
public java.lang.String getNOM_PB_SUPPRIMER() {
	return "NOM_PB_SUPPRIMER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/04/05 08:37:07)
* 
 */
public boolean performPB_SUPPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {

	//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_LIST_DIMENSION_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_LIST_DIMENSION_SELECT())) : -1);
	if (numligne == -1 || getListePneu().size() == 0 || numligne > getListePneu().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Pneus"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);
	
	//Récup du pneu courant
	Pneu pneu = (Pneu)getListePneu().get(numligne);
	setPneuCourant(pneu);

	//Alim zones
	addZone(getNOM_ST_DESIGNATION(),pneuCourant.getDimension());
	addZone(getNOM_EF_LIBELLE_DIMENSION(),pneuCourant.getDimension());
	
	setStatut(STATUT_MEME_PROCESS);	
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (28/04/05 08:37:07)
* 
 */
public java.lang.String getNOM_PB_VALIDER() {
	return "NOM_PB_VALIDER";
}

/**
 * Initialisation de la liste des pneus
* 
 */
private void initialiseListePneus(javax.servlet.http.HttpServletRequest request) throws Exception{
	//Recherche des pneus
	/*java.util.ArrayList a = Pneu.listerPneu(getTransaction());
	if (null == a){
		System.out.println("Aucun élément enregistré dans la base.");
	}
	setListePneu(a);
	
	//Si au moins un pneu
	if (a.size() !=0 ) {
		int tailles [] = {5,20};
		FormateListe aFormat = new FormateListe(tailles);
		for (java.util.ListIterator list = a.listIterator(); list.hasNext(); ) {
			Pneu aPneu = (Pneu)list.next();
			//TypeContact aType = (TypeContact)getHashTypeContact().get(aContact.getCodTypeContact());
			String ligne [] = { aPneu.getCodepneu(),aPneu.getDimension()};
			aFormat.ajouteLigne(ligne);
			
		}
		setLB_LIST_DIMENSION(aFormat.getListeFormatee());
	} else {
		setLB_LIST_DIMENSION(null);
	}
	
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_LB_LIST_DIMENSION_SELECT(),"0");
	addZone(getNOM_EF_LIBELLE_DIMENSION(),"");*/
}


/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/04/05 08:37:07)
* 
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	
//	Si aucune action en cours
	if (getZone(getNOM_ST_TITRE_ACTION()).length() == 0) {
		setStatut(STATUT_MEME_PROCESS, true, MairieMessages.getMessage("ERR996"));//"Vous ne pouvez pas valider, il n'y a pas d'action en cours."
		return false;
	}

	//Récup des zones saisies
	//TypeContact newType = (TypeContact)getListeTypeContact().get(Integer.parseInt(getZone(getNOM_LB_TYPE_CONTACT_SELECT())));
	String newDimension = getZone(getNOM_EF_LIBELLE_DIMENSION()).toUpperCase();
	
	//Si Action Suppression
	if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_SUPPRESSION)) {

		//Test si un des champs a été modifié
		if (! newDimension.equals(getPneuCourant().getDimension().trim())) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR995"));//"ERR995","En suppression, aucune zone n'est modifiable."
			return false;
		}
		
		//Suppression
		try{
			getPneuCourant().supprimerPneu(getTransaction());
		}catch (Exception e){
			getTransaction().declarerErreur(" La suppression n'est pas possible.");
		}
		if (getTransaction().isErreur())
			return false;
			
	//Si Action Modification
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_MODIFICATION)) {

		//Si lib contact non saisit
		if (newDimension.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","du pneu"));//"ERR008","Le libellé @ est obligatoire"
			setFocus(getNOM_EF_LIBELLE_DIMENSION());
			return false;
		}
		
		//Affectation des attributs
		getPneuCourant().setDimension(newDimension);
		
		//Modification
		getPneuCourant().modifierPneu(getTransaction(),newDimension);
		if (getTransaction().isErreur())
			return false;
			
	//Si Action Creation		
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_CREATION)) {

		//Si lib contact non saisit
		if (newDimension.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","du pneu"));//"ERR008","Le libellé @ est obligatoire"
			setFocus(getNOM_EF_LIBELLE_DIMENSION());
			return false;
		}
		
		setPneuCourant(new Pneu());
		
		//Affectation des attributs
		getPneuCourant().setDimension(newDimension);

		//Création
		getPneuCourant().creerPneu(getTransaction(),newDimension);
		String info = getTransaction().getMessageErreur();
		if (getTransaction().isErreur())
			return false;		
	}

	//Tout s'est bien passé
	commitTransaction();
	//initialiseListePneus(request);
	setLB_LIST_DIMENSION(LBVide);
	addZone(getNOM_ST_TITRE_ACTION(),"");	
	addZone(getNOM_ST_DESIGNATION(),"");
	setFocus(null);
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (28/04/05 08:37:07)
* 
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (28/04/05 08:37:07)
* 
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_LIBELLE_DIMENSION
 * Date de création : (28/04/05 08:37:07)
* 
 */
public java.lang.String getNOM_EF_LIBELLE_DIMENSION() {
	return "NOM_EF_LIBELLE_DIMENSION";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_LIBELLE_DIMENSION
 * Date de création : (28/04/05 08:37:07)
* 
 */
public java.lang.String getVAL_EF_LIBELLE_DIMENSION() {
	return getZone(getNOM_EF_LIBELLE_DIMENSION());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_LIST_DIMENSION
 * Date de création : (28/04/05 08:37:07)
* 
 */
private String [] getLB_LIST_DIMENSION() {
	if (LB_LIST_DIMENSION == null)
		LB_LIST_DIMENSION = initialiseLazyLB();
	return LB_LIST_DIMENSION;
}
/**
 * Setter de la liste:
 * LB_LIST_DIMENSION
 * Date de création : (28/04/05 08:37:07)
* 
 */
private void setLB_LIST_DIMENSION(java.lang.String[] newLB_LIST_DIMENSION) {
	LB_LIST_DIMENSION = newLB_LIST_DIMENSION;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_LIST_DIMENSION
 * Date de création : (28/04/05 08:37:07)
* 
 */
public java.lang.String getNOM_LB_LIST_DIMENSION() {
	return "NOM_LB_LIST_DIMENSION";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_LIST_DIMENSION_SELECT
 * Date de création : (28/04/05 08:37:07)
* 
 */
public java.lang.String getNOM_LB_LIST_DIMENSION_SELECT() {
	return "NOM_LB_LIST_DIMENSION_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_LIST_DIMENSION
 * Date de création : (28/04/05 08:37:07)
* 
 */
public java.lang.String [] getVAL_LB_LIST_DIMENSION() {
	return getLB_LIST_DIMENSION();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_LIST_DIMENSION
 * Date de création : (28/04/05 08:37:07)
* 
 */
public java.lang.String getVAL_LB_LIST_DIMENSION_SELECT() {
	return getZone(getNOM_LB_LIST_DIMENSION_SELECT());
}
	/**
	 * @return Renvoie listePneu.
	 */
	private ArrayList getListePneu() {
		return listePneu;
	}
	/**
	 * @param listePneu listePneu à définir.
	 */
	private void setListePneu(ArrayList listePneu) {
		this.listePneu = listePneu;
	}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (29/04/05 15:19:04)
* 
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_LIST_DIMENSION_SELECT()) ? Integer.parseInt(getVAL_LB_LIST_DIMENSION_SELECT()): -1); 
		
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
	
	Pneu monPneu = (Pneu)getListePneu().get(indice);
	setPneuCourant(monPneu);
	
	addZone(getNOM_EF_LIBELLE_DIMENSION(), monPneu.getDimension());
	
	return true;
}
	/**
	 * @return Renvoie pneuCourant.
	 */
	private Pneu getPneuCourant() {
		return pneuCourant;
	}
	/**
	 * @param pneuCourant pneuCourant à définir.
	 */
	private void setPneuCourant(Pneu pneuCourant) {
		this.pneuCourant = pneuCourant;
	}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (02/05/05 08:39:17)
* 
 */
public java.lang.String getNOM_PB_OK() {
	return "NOM_PB_OK";
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
	 * @return focus focus à définir.
	 */
	public String getDefaultFocus() {
		return getNOM_LB_LIST_DIMENSION();
	}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (28/04/05 08:37:07)
* 
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {
		//Si clic sur le bouton PB_OK
		if (testerParametre(request, getNOM_PB_OK())) {
			return performPB_OK(request);
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

		//Si clic sur le bouton PB_RETOUR
		if (testerParametre(request, getNOM_PB_RETOUR())) {
			return performPB_RETOUR(request);
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
 * Date de création : (14/06/05 13:09:29)
* 
 */
@Override
public String getJSP() {
	return "OePneu.jsp";
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DESIGNATION
 * Date de création : (14/06/05 13:09:29)
* 
 */
public java.lang.String getNOM_ST_DESIGNATION() {
	return "NOM_ST_DESIGNATION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DESIGNATION
 * Date de création : (14/06/05 13:09:29)
* 
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
