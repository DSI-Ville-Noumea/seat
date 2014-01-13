package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.Pieces;
import nc.mairie.technique.*;
/**
 * Process OePieces
 * Date de création : (13/05/05 13:35:49)
 * @author : Générateur de process
*/
public class OePieces extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 983552708203316339L;
	private java.lang.String[] LB_PIECES;
	private String ACTION_SUPPRESSION = "Suppression d'une pièce.<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private String ACTION_MODIFICATION = "Modification d'une pièce.";
	private String ACTION_CREATION = "Création d'une pièce.";
	private ArrayList<Pieces> listePieces = null;
	private Pieces piecesCourant;
	private String focus = null;
	private String isAction = "";
	public int isVide = 0;
	public boolean isDebranche = false;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (13/05/05 13:35:49)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	
	String debranche = (String)VariableActivite.recuperer(this,"DEBRANCHE");
	if(debranche==null){
		setDebranche(false);
	}else{
		if(debranche.equals("TRUE")){
			setDebranche(true);
		}else{
			setDebranche(false);
		}
	}
	
	//	Si liste des pièces est vide
	if (getLB_PIECES() == LBVide) {
		ArrayList<Pieces> a = Pieces.listerPieces(getTransaction());
		setListePieces(a);
		
		if (a.size()>0){
			//les élèments de la liste seront le codemarque pour pouvoir récupérer le dernier élément et les dimensions des marques.
			int [] tailles = {20,10};
			String [] champs = {"designationpiece","pu"};
			boolean[] colonnes = {true,true};
			a = Services.trier(a,champs,colonnes);
			setListePieces(a);
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"G","C"};
			
			setLB_PIECES(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
		}else{
			setLB_PIECES(null);
		}
		setIsVide(a.size());
	}
//	 on sélectionne l'élément en cours
	if(getPiecesCourant()!=null){
		if(getPiecesCourant().getCodepiece()!=null){
			addZone(getNOM_LB_PIECES_SELECT(),String.valueOf(-1));
			for (int i = 0; i < getListePieces().size(); i++) {
				Pieces unePiece = (Pieces)getListePieces().get(i);
				if (unePiece.getCodepiece().equals(getPiecesCourant().getCodepiece())) {
					addZone(getNOM_LB_PIECES_SELECT(),String.valueOf(i));
					break;
				}
			}	
		}
	}

}

/**
 * Constructeur du process OePieces.
 * Date de création : (13/05/05 13:35:49)
 * @author : Générateur de process
 */
public OePieces() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (13/05/05 13:35:49)
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
 * Date de création : (13/05/05 13:35:49)
 * @author : Générateur de process
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {

	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
	isAction = "creation";
	//On vide la zone de saisie
	addZone(getNOM_EF_PIECES(),"");
	addZone(getNOM_EF_PU(),"");

	setStatut(STATUT_MEME_PROCESS);
	setFocus(getNOM_EF_PIECES());
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (13/05/05 13:35:49)
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
 * Date de création : (13/05/05 13:35:49)
 * @author : Générateur de process
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {

	if ("".equals(getVAL_ST_TITRE_ACTION())) {
		setStatut(STATUT_PROCESS_APPELANT);
	} else {
		setLB_PIECES(LBVide);
	}
	//on vide les zones
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_ST_DESIGNATION(),"");
	addZone(getNOM_EF_PIECES(),"");
	addZone(getNOM_ST_PU(),"");
	addZone(getNOM_EF_PU(),"");
	setFocus(null);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (13/05/05 13:35:49)
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
 * Date de création : (13/05/05 13:35:49)
 * @author : Générateur de process
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {

//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_PIECES_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_PIECES_SELECT())) : -1);
	if (numligne == -1 || getListePieces().size() == 0 || numligne > getListePieces().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Pièces"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);
	isAction = "modification";
	//Récup du la marque courante
	Pieces pieces = (Pieces)getListePieces().get(numligne);
	setPiecesCourant(pieces);

	//Alim zones
	addZone(getNOM_EF_PIECES(), pieces.getDesignationpiece());
	addZone(getNOM_EF_PU(), pieces.getPu());
	
	setStatut(STATUT_MEME_PROCESS);
	setFocus(getNOM_EF_PIECES());
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (13/05/05 13:35:49)
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
 * Date de création : (13/05/05 13:35:49)
 * @author : Générateur de process
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_PIECES_SELECT()) ? Integer.parseInt(getVAL_LB_PIECES_SELECT()): -1); 
		
	//récup de la piece

	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	
	Pieces maPiece = (Pieces)getListePieces().get(indice);
	setPiecesCourant(maPiece);
	
	addZone(getNOM_EF_PIECES(), maPiece.getDesignationpiece());
	addZone(getNOM_EF_PU(), maPiece.getPu());
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPRIMER
 * Date de création : (13/05/05 13:35:49)
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
 * Date de création : (13/05/05 13:35:49)
 * @author : Générateur de process
 */
public boolean performPB_SUPPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_PIECES_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_PIECES_SELECT())) : -1);
	if (numligne == -1 || getListePieces().size() == 0 || numligne > getListePieces().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Pièces"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);
	isAction = "suppression";
	//Récup de la marque courante
	Pieces pieces = (Pieces)getListePieces().get(numligne);
	setPiecesCourant(pieces);

	//Alim zones
	addZone(getNOM_EF_PIECES(), pieces.getDesignationpiece());
	addZone(getNOM_EF_PU(), pieces.getPu());
	addZone(getNOM_ST_DESIGNATION(),pieces.getDesignationpiece());
	addZone(getNOM_ST_PU(),pieces.getPu());
	
	setStatut(STATUT_MEME_PROCESS);	
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (13/05/05 13:35:49)
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
 * Date de création : (13/05/05 13:35:49)
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
	String newDesignation = getZone(getNOM_EF_PIECES()).toUpperCase();
	String newPu = getZone(getNOM_EF_PU()).toUpperCase();
	
	//Champs obligatoires
	if(newDesignation.equals("")){
		getTransaction().declarerErreur("Vous devez renseigner la désigantion de la pièce.");
		setFocus(getNOM_EF_PIECES());
		return false;
	}
	if(newPu.equals("")){
		getTransaction().declarerErreur("Vous devez renseigner le prix unitaire.");
		setFocus(getNOM_EF_PU());
		return false;
	}
	//Si Action Suppression
	if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_SUPPRESSION)) {

		//Test si un des champs a été modifié
		if (! newDesignation.equals(getPiecesCourant().getDesignationpiece().trim())) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR995"));//"ERR995","En suppression, aucune zone n'est modifiable."
			return false;
		}
		
		//Suppression
		getPiecesCourant().supprimerPieces(getTransaction());
		if (getTransaction().isErreur())
			return false;
			
	//Si Action Modification
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_MODIFICATION)) {

		//Si lib pièce non saisit
		if (newDesignation.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","de la pièce"));//"ERR008","Le libellé @ est obligatoire"
			return false;
		}
//		Si lib pu non saisit
		if (newPu.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","du prix unitaire"));//"ERR008","Le libellé @ est obligatoire"
			return false;
		}
		
		//Affectation des attributs
		getPiecesCourant().setDesignationpiece(newDesignation);
		getPiecesCourant().setPu(newPu);
		
		//Modification
		getPiecesCourant().modifierPieces(getTransaction());
		if (getTransaction().isErreur())
			return false;
			
	//Si Action Creation		
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_CREATION)) {

		//Si lib contact non saisit
		if (newDesignation.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","de la pièce"));//"ERR008","Le libellé @ est obligatoire"
			return false;
		}
		//	Si lib contact non saisit
		if (newPu.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","du prix unitaire"));//"ERR008","Le libellé @ est obligatoire"
			return false;
		}
		
		setPiecesCourant(new Pieces());
		
		//Affectation des attributs
		getPiecesCourant().setDesignationpiece(newDesignation);
		getPiecesCourant().setPu(newPu);

		//Création
		getPiecesCourant().creerPieces(getTransaction());
		if (getTransaction().isErreur())
			return false;
		
	}

	//Tout s'est bien passé
	commitTransaction();
//	on vide les zones
	setLB_PIECES(LBVide);
	addZone(getNOM_ST_DESIGNATION(),"");
	addZone(getNOM_ST_PU(),"");
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_EF_PIECES(),"");
	addZone(getNOM_EF_PU(),"");
	setFocus(null);		
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (13/05/05 13:35:49)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (13/05/05 13:35:49)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_PU
 * Date de création : (13/05/05 13:35:49)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_PU() {
	return "NOM_EF_PU";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_PU
 * Date de création : (13/05/05 13:35:49)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_PU() {
	return getZone(getNOM_EF_PU());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_PIECES
 * Date de création : (13/05/05 13:35:49)
 * @author : Générateur de process
 */
private String [] getLB_PIECES() {
	if (LB_PIECES == null)
		LB_PIECES = initialiseLazyLB();
	return LB_PIECES;
}
/**
 * Setter de la liste:
 * LB_PIECES
 * Date de création : (13/05/05 13:35:49)
 * @author : Générateur de process
 */
private void setLB_PIECES(java.lang.String[] newLB_PIECES) {
	LB_PIECES = newLB_PIECES;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_PIECES
 * Date de création : (13/05/05 13:35:49)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_PIECES() {
	return "NOM_LB_PIECES";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_PIECES_SELECT
 * Date de création : (13/05/05 13:35:49)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_PIECES_SELECT() {
	return "NOM_LB_PIECES_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_PIECES
 * Date de création : (13/05/05 13:35:49)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_PIECES() {
	return getLB_PIECES();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_PIECES
 * Date de création : (13/05/05 13:35:49)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_PIECES_SELECT() {
	return getZone(getNOM_LB_PIECES_SELECT());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_PIECES
 * Date de création : (13/05/05 13:46:25)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_PIECES() {
	return "NOM_EF_PIECES";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_PIECES
 * Date de création : (13/05/05 13:46:25)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_PIECES() {
	return getZone(getNOM_EF_PIECES());
}
/**
 * @return Renvoie listePieces.
 */
private ArrayList<Pieces> getListePieces() {
	return listePieces;
}
/**
 * @param listePieces listePieces à définir.
 */
private void setListePieces(ArrayList<Pieces> listePieces) {
	this.listePieces = listePieces;
}
/**
 * @return Renvoie piecesCourant.
 */
private Pieces getPiecesCourant() {
	return piecesCourant;
}
/**
 * @param piecesCourant piecesCourant à définir.
 */
private void setPiecesCourant(Pieces piecesCourant) {
	this.piecesCourant = piecesCourant;
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
	return getNOM_LB_PIECES();
}
public String getIsAction() {
	return isAction;
}
public void setIsAction(String isAction) {
	this.isAction = isAction;
}
public int getIsVide() {
	return isVide;
}
public void setIsVide(int isVide) {
	this.isVide = isVide;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DESIGNATION
 * Date de création : (28/07/05 08:26:45)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_DESIGNATION() {
	return "NOM_ST_DESIGNATION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DESIGNATION
 * Date de création : (28/07/05 08:26:45)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_DESIGNATION() {
	return getZone(getNOM_ST_DESIGNATION());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PU
 * Date de création : (28/07/05 08:26:45)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_PU() {
	return "NOM_ST_PU";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PU
 * Date de création : (28/07/05 08:26:45)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_PU() {
	return getZone(getNOM_ST_PU());
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (13/05/05 13:35:49)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_RETOUR
		if (testerParametre(request, getNOM_PB_RETOUR())) {
			return performPB_RETOUR(request);
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
 * Date de création : (24/10/05 08:43:06)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OePieces.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RETOUR
 * Date de création : (24/10/05 08:43:06)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_RETOUR() {
	return "NOM_PB_RETOUR";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (24/10/05 08:43:06)
 * @author : Générateur de process
 */
public boolean performPB_RETOUR(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
	public boolean isDebranche() {
		return isDebranche;
	}
	public void setDebranche(boolean isDebranche) {
		this.isDebranche = isDebranche;
	}
}
