package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.AffectationServiceInfos;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.OT;
import nc.mairie.seat.metier.Pieces;
import nc.mairie.seat.metier.PiecesInfos;
import nc.mairie.seat.metier.PiecesOT;
import nc.mairie.technique.*;
/**
 * Process OePieces_OT
 * Date de création : (28/07/05 10:55:59)
* 
*/
public class OePieces_OT extends nc.mairie.technique.BasicProcess {
	public static final int STATUT_PIECES = 2;
	public static final int STATUT_RETOUROT = 1;
	private String ACTION_SUPPRESSION = "Suppression";
	private String ACTION_MODIFICATION = "Modification";
	private String ACTION_CREATION = "Création";
	private java.lang.String[] LB_PIECES;
	private java.lang.String[] LB_PIECES_OT;
	private ArrayList listePieces = new ArrayList();
	private ArrayList listePiecesOT = new ArrayList();
	private ArrayList listeQte = new ArrayList();
	private ArrayList listeDSortie = new ArrayList();
	private OT otCourant;
	private Pieces piecesCourant;
	private PiecesInfos pieceInfosCourant;
	private EquipementInfos equipementInfosCourant;
	private String focus = null;
	public boolean afficheQuantite;
	public boolean Modif;
	public boolean Suppresion;
	public boolean action;
	private String newQte;
	private String newDate;
	private boolean first = true;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (28/07/05 10:55:59)
* 
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	if(first){
		OT unOT = (OT)VariableGlobale.recuperer(request,"OT");
		EquipementInfos unEquipementInfos = (EquipementInfos)VariableGlobale.recuperer(request,"EQUIPEMENTINFOS");
		PiecesInfos unePieceInfos = (PiecesInfos)VariableGlobale.recuperer(request,"PIECEINFOS");
		String titreAction = (String)VariableGlobale.recuperer(request,"TITRE_ACTION");
		
		if (unOT!=null){
			setOtCourant(unOT);
			addZone(getNOM_ST_NOOT(),getOtCourant().getNumeroot());
		}
		
		if(unEquipementInfos!=null){
			setEquipementInfosCourant(unEquipementInfos);
			if(unEquipementInfos.getNumeroinventaire()!=null){
				setEquipementInfosCourant(unEquipementInfos);
				// recherche du service courant
				AffectationServiceInfos unAffectationServiceInfos = AffectationServiceInfos.chercherAffectationServiceInfosCourantEquip(getTransaction(),getEquipementInfosCourant().getNumeroinventaire(),getOtCourant().getDateentree());
				if(unAffectationServiceInfos.getNumeroinventaire()==null){
					//si pas trouvé, c'est fonctionellement normal, on traite l'erreur
					getTransaction().traiterErreur();
					addZone(getNOM_ST_SERVICE(),"pas affecté");
				}else{
					addZone(getNOM_ST_SERVICE(),unAffectationServiceInfos.getLiserv());
				}
				
				addZone(getNOM_ST_NOINVENT(),getEquipementInfosCourant().getNumeroinventaire());
				addZone(getNOM_ST_NOIMMAT(),getEquipementInfosCourant().getNumeroimmatriculation());
				addZone(getNOM_ST_TYPE(),getEquipementInfosCourant().getDesignationtypeequip());
				addZone(getNOM_ST_MODELE(),getEquipementInfosCourant().getDesignationmodele());
				addZone(getNOM_ST_MARQUE(),getEquipementInfosCourant().getDesignationmarque());
				
			}
		}
		
		
		if(unePieceInfos!=null){
			setPieceInfosCourant(unePieceInfos);
		}
		
		addZone(getNOM_ST_TITRE_ACTION(),titreAction);
		if(getVAL_ST_TITRE_ACTION().equals(ACTION_SUPPRESSION)){
			setSuppresion(true);
			setAction(true);
			addZone(getNOM_ST_QTE(),getPieceInfosCourant().getQuantite());
			//addZone(getNOM_ST_PU(),getPieceInfosCourant().getPu());
			addZone(getNOM_ST_PU(),getPieceInfosCourant().getPrix());
			addZone(getNOM_ST_PIECE(),getPieceInfosCourant().getDesignationpiece());
			addZone(getNOM_ST_DSORTIE(),getPieceInfosCourant().getDatesortie());
		}else{
			setSuppresion(false);
			if(getListePiecesOT().size()>0){
				setAction(true);
			}else{
				setAction(false);
			}
		}
		if(getVAL_ST_TITRE_ACTION().equals(ACTION_MODIFICATION)){
			setAfficheQuantite(true);
			addZone(getNOM_EF_DATESORTIE(),getPieceInfosCourant().getDatesortie());
			addZone(getNOM_EF_QUANTITE(),getPieceInfosCourant().getQuantite());
			addZone(getNOM_EF_LIBELLE(),getPieceInfosCourant().getDesignationpiece());
			addZone(getNOM_EF_PRIX(),getPieceInfosCourant().getPrix());
			Pieces unePiece = Pieces.chercherPieces(getTransaction(),getPieceInfosCourant().getCodepiece());
			if(getTransaction().isErreur()){
				return ;
			}
			setPiecesCourant(unePiece);
			setModif(true);
		}else{
			setAfficheQuantite(false);
//			 initialisation des zones
			addZone(getNOM_EF_QUANTITE(),"");
			addZone(getNOM_EF_DATESORTIE(),"");
			setModif(false);
		}
		//	si la zone de recherche est renseignée alors on exécute le bouton de recherche
		//	Récup de l'indice sélectionné
		int indice = (Services.estNumerique(getVAL_LB_PIECES_SELECT()) ? Integer.parseInt(getVAL_LB_PIECES_SELECT()): -1); 
		if (indice != -1) {
			performPB_AJOUTER(request);
		}else{
			performPB_OK_PIECES(request);
		}
	}
	if(etatStatut()==STATUT_PIECES){
		performPB_OK_PIECES(request);
	}
	initialiseListPieces(request);
	
	// à tester
	if(!getVAL_ST_TITRE_ACTION().equals(ACTION_SUPPRESSION)){
		if(getListePiecesOT().size()>0){
			setAction(true);
		}else{
			setAction(false);
		}
	}
	setFocus(getNOM_EF_LIBELLE());
	//setAfficheQuantite(false);
	// si pas de pièces enregistré
	if(getListePieces().size()==0){
		getTransaction().declarerErreur("Aucune pièce n'a été enregistrée.");
		return;
	}
	setFirst(false);
}

public void initialiseListPieces(javax.servlet.http.HttpServletRequest request) throws Exception{
	if(getListePieces().size()>0){
		//les élèments de la liste 
		int [] tailles = {20,7};
		String [] champs = {"designationpiece","pu"};
		String [] padding = {"G","C"};
		
		setLB_PIECES(new FormateListe(tailles,getListePieces(),champs,padding,true).getListeFormatee());
	}else{
		setLB_PIECES(LBVide);
	}
	// on sélectionne le bon élément
	if(getVAL_ST_TITRE_ACTION().equals(ACTION_MODIFICATION)){
		if(getPiecesCourant()!=null){
	//		recherche du type d'intervalle courant
			int position = -1;
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

public void initialiseListPiecesOT(javax.servlet.http.HttpServletRequest request) throws Exception{
	if(getListePiecesOT().size()>0){
		int [] tailles = {20,7,5,10};
		String [] padding = {"G","C","C","C"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (int i=0;i<getListePiecesOT().size();i++){
			Pieces maPiece = (Pieces)getListePiecesOT().get(i);
			String maQte = (String)getListeQte().get(i);
			String maDSortie = (String)getListeDSortie().get(i);
			String ligne [] = { maPiece.getDesignationpiece(),maPiece.getPu(),maQte,maDSortie};
			aFormat.ajouteLigne(ligne);
		}
		setLB_PIECES_OT(aFormat.getListeFormatee());
	}else{
		setLB_PIECES_OT(LBVide);
	}
	
}

/**
 * Constructeur du process OePieces_OT.
 * Date de création : (28/07/05 10:55:59)
* 
 */
public OePieces_OT() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (28/07/05 10:55:59)
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
 * Date de création : (28/07/05 10:55:59)
* 
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {
	int indice = (Services.estNumerique(getVAL_LB_PIECES_SELECT()) ? Integer.parseInt(getVAL_LB_PIECES_SELECT()): -1);
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement dans la liste des pièces");
		return false;
	}
	Pieces unePiece = (Pieces)getListePieces().get(indice);
	setPiecesCourant(unePiece);
	// pour entrer la quantité de pièces utilisé
	setAfficheQuantite(true);
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (28/07/05 10:55:59)
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
 * Date de création : (28/07/05 10:55:59)
* 
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_PROCESS_APPELANT,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ENLEVER
 * Date de création : (28/07/05 10:55:59)
* 
 */
public java.lang.String getNOM_PB_ENLEVER() {
	return "NOM_PB_ENLEVER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/07/05 10:55:59)
* 
 */
public boolean performPB_ENLEVER(javax.servlet.http.HttpServletRequest request) throws Exception {
	int indice = (Services.estNumerique(getVAL_LB_PIECES_OT_SELECT()) ? Integer.parseInt(getVAL_LB_PIECES_OT_SELECT()): -1);
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement dans la liste des pièces");
		return false;
	}
	Pieces unePiece = (Pieces)getListePiecesOT().get(indice);
	getListePieces().add(unePiece);
	getListePiecesOT().remove(indice);
	getListeDSortie().remove(indice);
	getListeQte().remove(indice);
	initialiseListPiecesOT(request);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (28/07/05 10:55:59)
* 
 */
public java.lang.String getNOM_PB_OK() {
	return "NOM_PB_OK";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/07/05 10:55:59)
* 
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
	newQte = getZone(getNOM_EF_QUANTITE());
	newDate = getZone(getNOM_EF_DATESORTIE());
	if(!Services.estUneDate(getZone(getNOM_EF_DATESORTIE()))){
		getTransaction().declarerErreur("La date n'est pas valide.");
		return false;
	}
	//RG : si date pas saisi la date devient celle de la date d'entree
	if(newDate.equals("")){
		newDate = getOtCourant().getDateentree();
	}else{
		newDate = Services.formateDate(newDate);
	}
	// controle des champs obligatoire
	if(newQte.equals("")){
		getTransaction().declarerErreur("Vous n'avez pas saisi la quantité utilisée.");
		return false;
	}
	/*if(newDate.equals("")){
		getTransaction().declarerErreur("Vous n'avez pas renseigné la date de sortie.");
		return false;
	}*/
	
	setAfficheQuantite(false);
	if(!getVAL_ST_TITRE_ACTION().equals(ACTION_MODIFICATION)){
		// on ajoute la pièce dans la liste pour l'Ot
		getListePiecesOT().add(getPiecesCourant());
		getListeDSortie().add(newDate);
		getListeQte().add(newQte);
		// on l'enlève de la liste des pièces
		getListePieces().remove(getPiecesCourant());
		initialiseListPiecesOT(request);
	}else{
		performPB_VALIDER(request);
	}
	// on vide les zones
	addZone(getNOM_EF_DATESORTIE(),"");
	addZone(getNOM_EF_QUANTITE(),"");
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RETOUROT
 * Date de création : (28/07/05 10:55:59)
* 
 */
public java.lang.String getNOM_PB_VALIDER() {
	return "NOM_PB_VALIDER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/07/05 10:55:59)
* 
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	// si en suppression
	if(!getVAL_ST_TITRE_ACTION().equals(ACTION_SUPPRESSION)){
//		 si en modification
		if(getVAL_ST_TITRE_ACTION().equals(ACTION_MODIFICATION)){
			String maQte= getZone(getNOM_EF_QUANTITE());
			String maDSortie = getZone(getNOM_EF_DATESORTIE());
			 
			// controle de saisie : zones obligatoires
			if(maQte.equals("")){
				getTransaction().declarerErreur("La quantité sortie doit être précisée.");
				setFocus(getNOM_EF_QUANTITE());
				return false;
			}
			if(maDSortie.equals("")){
				getTransaction().declarerErreur("La date de sortie doit être renseignée.");
				setFocus(getNOM_EF_DATESORTIE());
				return false;
			}
			PiecesOT unePieceOT = PiecesOT.chercherPiecesOT(getTransaction(),getPieceInfosCourant().getCodepiece(),getPieceInfosCourant().getNumot(),getPieceInfosCourant().getDatesortie());
			if(getTransaction().isErreur()){
				return false;
			}
			unePieceOT.setDatesortie(maDSortie);
			unePieceOT.setQuantite(maQte);
			unePieceOT.modifierPiecesOT(getTransaction());
		}else if(getVAL_ST_TITRE_ACTION().equals(ACTION_CREATION)){
			// on enregistre dans la table pieces_ot 
			PiecesOT unePieceOT = new PiecesOT();
			if (getListePiecesOT().size()>0){
				for (int i=0;i<getListePiecesOT().size();i++){
					Pieces unePiece = (Pieces)getListePiecesOT().get(i);
					String maQte = (String)getListeQte().get(i);
					String maDSortie = (String)getListeDSortie().get(i);
					unePieceOT.setQuantite(maQte);
					unePieceOT.setDatesortie(maDSortie);
						unePieceOT.creationPiecesOT(getTransaction(),getOtCourant(),unePiece);
						if(getTransaction().isErreur()){
							return false;
						}
					}
				}
		}
	}else{
		//suppression
		PiecesOT unePieceOT = PiecesOT.chercherPiecesOT(getTransaction(),getPieceInfosCourant().getCodepiece(),getPieceInfosCourant().getNumot(),getPieceInfosCourant().getDatesortie());
		if(getTransaction().isErreur()){
			return false;
		}
		unePieceOT.supprimerPiecesOT(getTransaction());
	}
	// si tout s'est bien passé on sauvegarde
	if(getTransaction().isErreur()){
		setFirst(true);
		return false;
	}else{
		VariableGlobale.ajouter(request,"OT",getOtCourant());
		commitTransaction();
		setStatut(STATUT_PROCESS_APPELANT);
	}
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_QUANTITE
 * Date de création : (28/07/05 10:55:59)
* 
 */
public java.lang.String getNOM_EF_QUANTITE() {
	return "NOM_EF_QUANTITE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_QUANTITE
 * Date de création : (28/07/05 10:55:59)
* 
 */
public java.lang.String getVAL_EF_QUANTITE() {
	return getZone(getNOM_EF_QUANTITE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_PIECES
 * Date de création : (28/07/05 10:55:59)
* 
 */
private String [] getLB_PIECES() {
	if (LB_PIECES == null)
		LB_PIECES = initialiseLazyLB();
	return LB_PIECES;
}
/**
 * Setter de la liste:
 * LB_PIECES
 * Date de création : (28/07/05 10:55:59)
* 
 */
private void setLB_PIECES(java.lang.String[] newLB_PIECES) {
	LB_PIECES = newLB_PIECES;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_PIECES
 * Date de création : (28/07/05 10:55:59)
* 
 */
public java.lang.String getNOM_LB_PIECES() {
	return "NOM_LB_PIECES";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_PIECES_SELECT
 * Date de création : (28/07/05 10:55:59)
* 
 */
public java.lang.String getNOM_LB_PIECES_SELECT() {
	return "NOM_LB_PIECES_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_PIECES
 * Date de création : (28/07/05 10:55:59)
* 
 */
public java.lang.String [] getVAL_LB_PIECES() {
	return getLB_PIECES();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_PIECES
 * Date de création : (28/07/05 10:55:59)
* 
 */
public java.lang.String getVAL_LB_PIECES_SELECT() {
	return getZone(getNOM_LB_PIECES_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_PIECES_OT
 * Date de création : (28/07/05 10:55:59)
* 
 */
private String [] getLB_PIECES_OT() {
	if (LB_PIECES_OT == null)
		LB_PIECES_OT = initialiseLazyLB();
	return LB_PIECES_OT;
}
/**
 * Setter de la liste:
 * LB_PIECES_OT
 * Date de création : (28/07/05 10:55:59)
* 
 */
private void setLB_PIECES_OT(java.lang.String[] newLB_PIECES_OT) {
	LB_PIECES_OT = newLB_PIECES_OT;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_PIECES_OT
 * Date de création : (28/07/05 10:55:59)
* 
 */
public java.lang.String getNOM_LB_PIECES_OT() {
	return "NOM_LB_PIECES_OT";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_PIECES_OT_SELECT
 * Date de création : (28/07/05 10:55:59)
* 
 */
public java.lang.String getNOM_LB_PIECES_OT_SELECT() {
	return "NOM_LB_PIECES_OT_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_PIECES_OT
 * Date de création : (28/07/05 10:55:59)
* 
 */
public java.lang.String [] getVAL_LB_PIECES_OT() {
	return getLB_PIECES_OT();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_PIECES_OT
 * Date de création : (28/07/05 10:55:59)
* 
 */
public java.lang.String getVAL_LB_PIECES_OT_SELECT() {
	return getZone(getNOM_LB_PIECES_OT_SELECT());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_LIBELLE
 * Date de création : (28/07/05 10:57:02)
* 
 */
public java.lang.String getNOM_EF_LIBELLE() {
	return "NOM_EF_LIBELLE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_LIBELLE
 * Date de création : (28/07/05 10:57:02)
* 
 */
public java.lang.String getVAL_EF_LIBELLE() {
	return getZone(getNOM_EF_LIBELLE());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_PIECES
 * Date de création : (28/07/05 10:59:27)
* 
 */
public java.lang.String getNOM_PB_OK_PIECES() {
	return "NOM_PB_OK_PIECES";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/07/05 10:59:27)
* 
 */
public boolean performPB_OK_PIECES(javax.servlet.http.HttpServletRequest request) throws Exception {
	String recherche = getZone(getNOM_EF_LIBELLE());
	ArrayList resultat = Pieces.chercherPiecesLib(getTransaction(),recherche);
	if(getTransaction().isErreur()){
		return false;
	}
	// on remplit la liste des équipements
	setListePieces(resultat);
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
 * @return focus focus à définir.
 */
public String getDefaultFocus() {
	return getNOM_EF_LIBELLE();
}

public ArrayList getListePieces() {
	return listePieces;
}
public void setListePieces(ArrayList listePieces) {
	this.listePieces = listePieces;
}
public ArrayList getListePiecesOT() {
	return listePiecesOT;
}
public void setListePiecesOT(ArrayList listePiecesOT) {
	this.listePiecesOT = listePiecesOT;
}
public Pieces getPiecesCourant() {
	return piecesCourant;
}
public void setPiecesCourant(Pieces piecesCourant) {
	this.piecesCourant = piecesCourant;
}
public boolean isAfficheQuantite() {
	return afficheQuantite;
}
public void setAfficheQuantite(boolean afficheQuantite) {
	this.afficheQuantite = afficheQuantite;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DATESORTIE
 * Date de création : (28/07/05 13:37:23)
* 
 */
public java.lang.String getNOM_EF_DATESORTIE() {
	return "NOM_EF_DATESORTIE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DATESORTIE
 * Date de création : (28/07/05 13:37:23)
* 
 */
public java.lang.String getVAL_EF_DATESORTIE() {
	return getZone(getNOM_EF_DATESORTIE());
}
	public OT getOtCourant() {
		return otCourant;
	}
	public void setOtCourant(OT otCourant) {
		this.otCourant = otCourant;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOOT
 * Date de création : (01/08/05 15:08:29)
* 
 */
public java.lang.String getNOM_ST_NOOT() {
	return "NOM_ST_NOOT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOOT
 * Date de création : (01/08/05 15:08:29)
* 
 */
public java.lang.String getVAL_ST_NOOT() {
	return getZone(getNOM_ST_NOOT());
}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
	public ArrayList getListeDSortie() {
		return listeDSortie;
	}
	public void setListeDSortie(ArrayList listeDSortie) {
		this.listeDSortie = listeDSortie;
	}
	public ArrayList getListeQte() {
		return listeQte;
	}
	public void setListeQte(ArrayList listeQte) {
		this.listeQte = listeQte;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (02/08/05 10:09:14)
* 
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (02/08/05 10:09:14)
* 
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
	public PiecesInfos getPieceInfosCourant() {
		return pieceInfosCourant;
	}
	public void setPieceInfosCourant(PiecesInfos pieceInfosCourant) {
		this.pieceInfosCourant = pieceInfosCourant;
	}
	public boolean isModif() {
		return Modif;
	}
	public void setModif(boolean modif) {
		Modif = modif;
	}
	public boolean isSuppresion() {
		return Suppresion;
	}
	public void setSuppresion(boolean suppresion) {
		Suppresion = suppresion;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DSORTIE
 * Date de création : (02/08/05 11:18:36)
* 
 */
public java.lang.String getNOM_ST_DSORTIE() {
	return "NOM_ST_DSORTIE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DSORTIE
 * Date de création : (02/08/05 11:18:36)
* 
 */
public java.lang.String getVAL_ST_DSORTIE() {
	return getZone(getNOM_ST_DSORTIE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PIECE
 * Date de création : (02/08/05 11:18:36)
* 
 */
public java.lang.String getNOM_ST_PIECE() {
	return "NOM_ST_PIECE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PIECE
 * Date de création : (02/08/05 11:18:36)
* 
 */
public java.lang.String getVAL_ST_PIECE() {
	return getZone(getNOM_ST_PIECE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PU
 * Date de création : (02/08/05 11:18:36)
* 
 */
public java.lang.String getNOM_ST_PU() {
	return "NOM_ST_PU";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PU
 * Date de création : (02/08/05 11:18:36)
* 
 */
public java.lang.String getVAL_ST_PU() {
	return getZone(getNOM_ST_PU());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_QTE
 * Date de création : (02/08/05 11:18:36)
* 
 */
public java.lang.String getNOM_ST_QTE() {
	return "NOM_ST_QTE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_QTE
 * Date de création : (02/08/05 11:18:36)
* 
 */
public java.lang.String getVAL_ST_QTE() {
	return getZone(getNOM_ST_QTE());
}
	
	public boolean isAction() {
		return action;
	}
	public void setAction(boolean action) {
		this.action = action;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (03/08/05 10:39:30)
* 
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (03/08/05 10:39:30)
* 
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODELE
 * Date de création : (03/08/05 10:39:30)
* 
 */
public java.lang.String getNOM_ST_MODELE() {
	return "NOM_ST_MODELE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODELE
 * Date de création : (03/08/05 10:39:30)
* 
 */
public java.lang.String getVAL_ST_MODELE() {
	return getZone(getNOM_ST_MODELE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (03/08/05 10:39:30)
* 
 */
public java.lang.String getNOM_ST_NOIMMAT() {
	return "NOM_ST_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (03/08/05 10:39:30)
* 
 */
public java.lang.String getVAL_ST_NOIMMAT() {
	return getZone(getNOM_ST_NOIMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINVENT
 * Date de création : (03/08/05 10:39:30)
* 
 */
public java.lang.String getNOM_ST_NOINVENT() {
	return "NOM_ST_NOINVENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINVENT
 * Date de création : (03/08/05 10:39:30)
* 
 */
public java.lang.String getVAL_ST_NOINVENT() {
	return getZone(getNOM_ST_NOINVENT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (03/08/05 10:39:30)
* 
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (03/08/05 10:39:30)
* 
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (03/08/05 10:39:30)
* 
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (03/08/05 10:39:30)
* 
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
public EquipementInfos getEquipementInfosCourant() {
	return equipementInfosCourant;
}
public void setEquipementInfosCourant(EquipementInfos equipementInfosCourant) {
	this.equipementInfosCourant = equipementInfosCourant;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_CREER_PIECES
 * Date de création : (24/10/05 08:26:29)
* 
 */
public java.lang.String getNOM_PB_CREER_PIECES() {
	return "NOM_PB_CREER_PIECES";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (24/10/05 08:26:29)
* 
 */
public boolean performPB_CREER_PIECES(javax.servlet.http.HttpServletRequest request) throws Exception {
	VariableActivite.ajouter(this,"DEBRANCHE","TRUE");
	setStatut(STATUT_PIECES,true);
	return true;
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (28/07/05 10:55:59)
* 
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_CREER_PIECES
		if (testerParametre(request, getNOM_PB_CREER_PIECES())) {
			return performPB_CREER_PIECES(request);
		}

		//Si clic sur le bouton PB_OK_PIECES
		if (testerParametre(request, getNOM_PB_OK_PIECES())) {
			return performPB_OK_PIECES(request);
		}

		//Si clic sur le bouton PB_AJOUTER
		if (testerParametre(request, getNOM_PB_AJOUTER())) {
			return performPB_AJOUTER(request);
		}

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

		//Si clic sur le bouton PB_RETOUROT
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
 * Date de création : (07/11/05 07:40:50)
* 
 */
@Override
public String getJSP() {
	return "OePieces_OT.jsp";
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_PRIX
 * Date de création : (07/11/05 07:40:50)
* 
 */
public java.lang.String getNOM_EF_PRIX() {
	return "NOM_EF_PRIX";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_PRIX
 * Date de création : (07/11/05 07:40:50)
* 
 */
public java.lang.String getVAL_EF_PRIX() {
	return getZone(getNOM_EF_PRIX());
}
}
