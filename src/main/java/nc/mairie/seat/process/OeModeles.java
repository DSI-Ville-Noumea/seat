package nc.mairie.seat.process;

import java.util.ArrayList;
import java.util.ListIterator;

import nc.mairie.seat.metier.Modeles;
import nc.mairie.seat.metier.Marques;
import nc.mairie.seat.metier.TYPEEQUIP;
import nc.mairie.seat.metier.Pneu;
import nc.mairie.seat.metier.Carburant;
import nc.mairie.seat.metier.Compteur;
import nc.mairie.technique.*;

/**
 * Process OeModeles
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
*/
public class OeModeles extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5901901224771705159L;
	public static final int STATUT_AJOUTER = 6;
	public static final int STATUT_MODIFIER = 7;
	private java.lang.String[] LB_MARQUE;
	private java.lang.String[] LB_MODELE;
	private String ACTION_SUPPRESSION = "Suppression d'un modèle.<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private ArrayList<Modeles> listeModele = null;
	private ArrayList<Marques> listeMarque = null;
	private Modeles modeleCourant;
	private Marques marquesCourant;
	boolean first = true;
	public int isVide = 0;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{

	if (first){
		//	Si liste des marques est vide
		if (getLB_MARQUE() == LBVide) {
			ArrayList<Marques> a = Marques.listerMarques(getTransaction());
			if (getTransaction().isErreur()){
				return;
			}
			setListeMarque(a);
			
			if (a.size()>0){
				//les élèments de la liste seront le codemarque pour pouvoir récupérer le dernier élément et les dimensions des marques.
				int [] tailles = {20};
				String [] champs = {"designationmarque"};
				boolean[] colonnes = {true};
				a = Services.trier(a,champs,colonnes);
				setListeMarque(a);
				//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
				String [] padding = {"G"};
				
				setLB_MARQUE(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
				// 
				addZone(getNOM_LB_MARQUE_SELECT(),"0");
				performPB_OKMARQUES(request);
			}else{
				setLB_MARQUE(null);
				getTransaction().declarerErreur("Aucune marque enregistrée.");
			}
		}
		setLB_MODELE(LBVide);
		
	}
	// si un élément est sélectionné dans la liste on alimente la liste des modèles
	int numligne = (Services.estNumerique(getZone(getNOM_LB_MARQUE_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_MARQUE_SELECT())) : -1);
	if (-1<numligne){
		performPB_OKMARQUES(request);
	}
	
	first = false;
}

/**
 * Initialisation de la liste des modeles appartenant à une marque
 * author : Coralie NICOLAS
 */
private void initialiseListeModelesMarque(javax.servlet.http.HttpServletRequest request, String cle) throws Exception{
	//Recherche des modèles
	ArrayList<Modeles> a = Modeles.listerModelesMarque(getTransaction(),cle);
	setListeModele(a);
	
	//Si au moins un modèle
	if (a.size() !=0 ) {
		int tailles [] = {20,7,20,30};
		String [] padding = {"G","G","G","G"};
		String[] champs = {"designationmodele"};
		boolean[] colonnes = {true};
		a = Services.trier(a,champs,colonnes);
		setListeModele(a);
		FormateListe aFormat = new FormateListe(tailles,padding, true);
				
		for (ListIterator<Modeles> list = a.listIterator(); list.hasNext(); ) {
			Modeles aModele = (Modeles)list.next();
			TYPEEQUIP aTEquip = TYPEEQUIP.chercherTYPEEQUIP(getTransaction(),aModele.getCodete());
			if (getTransaction().isErreur()){
				return ;
			}
			Carburant unCarbu = Carburant.chercherCarburant(getTransaction(),aModele.getCodecarburant());
			if(getTransaction().isErreur()){
				return;
			}
			String ligne [] = { aModele.getDesignationmodele(),aModele.getVersion(),aTEquip.getDesignationte(),unCarbu.getDesignationcarbu()};
			aFormat.ajouteLigne(ligne);
			
		}
		setLB_MODELE(aFormat.getListeFormatee());
	} else {
		setLB_MODELE(null);
	}
	setIsVide(a.size());
	//addZone(getNOM_ST_TITRE_ACTION(),"");
	//addZone(getNOM_LB_MODELE_SELECT(),"0");
}

/**
 * Constructeur du process OeModeles.
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
 */
public OeModeles() {
	super();
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {
		
//	je récupère la marque 
int indice = (Services.estNumerique(getVAL_LB_MARQUE_SELECT()) ? Integer.parseInt(getVAL_LB_MARQUE_SELECT()): -1); 
if (indice == -1) {
	getTransaction().declarerErreur("Vous devez sélectionner un élement");
	return false;
}
	Marques maMarque = (Marques)getListeMarque().get(indice);
	setMarquesCourant(maMarque);
	if (getMarquesCourant().getCodemarque()== null){
		getTransaction().declarerErreur("Vous n'avez rien sélectionner");
		return false;
	}
	//On met les variables activités
	VariableActivite.ajouter(this, "MARQUES", getMarquesCourant());
	// on envoie l'objet marque
	setStatut(STATUT_AJOUTER,true);	
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (11/05/05 15:26:22)
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
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {

	initialiseListeModelesMarque(request,marquesCourant.codemarque);
	addZone(getNOM_ST_TITRE_ACTION(),"");
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
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
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	je récupère la marque 
int indice = (Services.estNumerique(getVAL_LB_MARQUE_SELECT()) ? Integer.parseInt(getVAL_LB_MARQUE_SELECT()): -1); 
if (indice == -1) {
	getTransaction().declarerErreur("Vous devez sélectionner un élement");
	return false;
}
	Marques maMarque = (Marques)getListeMarque().get(indice);
	setMarquesCourant(maMarque);
/*	if (getMarquesCourant().getCodemarque()== null){
		getTransaction().declarerErreur("Vous n'avez rien sélectionner");
		return false;
	}*/

	//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_MODELE_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_MODELE_SELECT())) : -1);
	if (numligne == -1 || getListeModele().size() == 0 || numligne > getListeModele().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Modèles"));
		return false;
	}
//	Récup du modèle courant
	Modeles modele = (Modeles)getListeModele().get(numligne);
	setModeleCourant(modele);
	
	//On met les variables activités
	VariableActivite.ajouter(this, "MARQUES", getMarquesCourant());
	VariableActivite.ajouter(this,"MODELES", getModeleCourant());
	// on envoie l'objet marque
	setStatut(STATUT_MODIFIER,true);
		
	return true;
}

/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OKMARQUES
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_OKMARQUES() {
	return "NOM_PB_OKMARQUES";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_OKMARQUES(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_MARQUE_SELECT()) ? Integer.parseInt(getVAL_LB_MARQUE_SELECT()): -1); 
	
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	
	Marques maMarque = (Marques)getListeMarque().get(indice);
	setMarquesCourant(maMarque);
	initialiseListeModelesMarque(request, maMarque.getCodemarque());	
//	On nomme l'action
	addZone(getNOM_ST_ACTION_OK(),"Les modèles de la marque "+marquesCourant.getDesignationmarque());
	//addZone(getNOM_ST_TITRE_ACTION(),"");
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPRIMER
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
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
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_SUPPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {

	//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_MODELE_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_MODELE_SELECT())) : -1);
	if (numligne == -1 || getListeModele().size() == 0 || numligne > getListeModele().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Modèles"));
		return false;
	}
	
	//On nomme l'action
	
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);

	//Récup de l'entretien courant
	Modeles modele = (Modeles)getListeModele().get(numligne);
	setModeleCourant(modele);

	//Alim zones
	addZone(getNOM_ST_DESIGNATION(), modele.getDesignationmodele());
	addZone(getNOM_ST_NBESSIEUX(),modele.getNbessieux());
	addZone(getNOM_ST_NBPNEUAR(),modele.nbpneuarriere);
	addZone(getNOM_ST_NBPNEUAV(),modele.getNbpneuavant());
	addZone(getNOM_ST_PUISSANCE(),modele.getPuissance());
	addZone(getNOM_ST_RESERVOIR(),modele.getCapacitereservoir());
	addZone(getNOM_ST_VERSION(),modele.getVersion());
//	sélectionner le bon pneu
	Pneu monPneu = Pneu.chercherPneu(getTransaction(),modele.getCodepneu());
	if(getTransaction().isErreur()){
		return false;
	}
	addZone(getNOM_ST_PNEU(),monPneu.getDimension());
//	sélectionner le bon carburant
	Carburant monCarbu = Carburant.chercherCarburant(getTransaction(),modele.getCodecarburant());
	if (getTransaction().isErreur()){
		return false;
	}
	addZone(getNOM_ST_CARBU(),monCarbu.getDesignationcarbu());
//	sélectionner le bon compteur
	Compteur monCompteur = Compteur.chercherCompteur(getTransaction(),modele.getCodecompteur());
	if (getTransaction().isErreur()){
		return false;
	}
	addZone(getNOM_ST_COMPTEUR(),monCompteur.getDesignationcompteur());
//	sélectionner le bon type d'équipement
	TYPEEQUIP monTe = TYPEEQUIP.chercherTYPEEQUIP(getTransaction(),modele.getCodete());
	if (getTransaction().isErreur()){
		return false;
	}
	addZone(getNOM_ST_TYPEEQUIP(),monTe.getDesignationte());
	
	setStatut(STATUT_MEME_PROCESS);	
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (11/05/05 15:26:22)
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
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
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
		//Suppression
		//try{
			getModeleCourant().supprimerModeles(getTransaction());
		/*}catch(Exception e){
			getTransaction().declarerErreur(" La suppression n'est pas possible.");
		}
		if (getTransaction().isErreur())
			return false;*/
			
	//Tout s'est bien passé
	commitTransaction();
	initialiseListeModelesMarque(request, modeleCourant.getCodemarque());
	addZone(getNOM_ST_TITRE_ACTION(),"");
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_MARQUE
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
 */
private String [] getLB_MARQUE() {
	if (LB_MARQUE == null)
		LB_MARQUE = initialiseLazyLB();
	return LB_MARQUE;
}
/**
 * Setter de la liste:
 * LB_MARQUE
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
 */
private void setLB_MARQUE(java.lang.String[] newLB_MARQUE) {
	LB_MARQUE = newLB_MARQUE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_MARQUE
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_MARQUE() {
	return "NOM_LB_MARQUE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_MARQUE_SELECT
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_MARQUE_SELECT() {
	return "NOM_LB_MARQUE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_MARQUE
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_MARQUE() {
	return getLB_MARQUE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_MARQUE
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_MARQUE_SELECT() {
	return getZone(getNOM_LB_MARQUE_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_MODELE
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
 */
private String [] getLB_MODELE() {
	if (LB_MODELE == null)
		LB_MODELE = initialiseLazyLB();
	return LB_MODELE;
}
/**
 * Setter de la liste:
 * LB_MODELE
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
 */
private void setLB_MODELE(java.lang.String[] newLB_MODELE) {
	LB_MODELE = newLB_MODELE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_MODELE
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_MODELE() {
	return "NOM_LB_MODELE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_MODELE_SELECT
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_MODELE_SELECT() {
	return "NOM_LB_MODELE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_MODELE
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_MODELE() {
	return getLB_MODELE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_MODELE
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_MODELE_SELECT() {
	return getZone(getNOM_LB_MODELE_SELECT());
}

	/**
	 * @return Renvoie listeMarque.
	 */
	private ArrayList<Marques> getListeMarque() {
		return listeMarque;
	}
	/**
	 * @param listeMarque listeMarque à définir.
	 */
	private void setListeMarque(ArrayList<Marques> listeMarque) {
		this.listeMarque = listeMarque;
	}
	/**
	 * @return Renvoie listeModele.
	 */
	private ArrayList<Modeles> getListeModele() {
		return listeModele;
	}
	/**
	 * @param listeModele listeModele à définir.
	 */
	private void setListeModele(ArrayList<Modeles> listeModele) {
		this.listeModele = listeModele;
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
	 * @return Renvoie modeleCourant.
	 */
	private Modeles getModeleCourant() {
		return modeleCourant;
	}
	/**
	 * @param modeleCourant modeleCourant à définir.
	 */
	private void setModeleCourant(Modeles modeleCourant) {
		this.modeleCourant = modeleCourant;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_ACTION_OK
 * Date de création : (12/05/05 09:21:01)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_ACTION_OK() {
	return "NOM_ST_ACTION_OK";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_ACTION_OK
 * Date de création : (12/05/05 09:21:01)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_ACTION_OK() {
	return getZone(getNOM_ST_ACTION_OK());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (12/05/05 12:46:43)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_AJOUTER() {
	return "NOM_PB_AJOUTER";
}


/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_CARBU
 * Date de création : (06/06/05 16:03:48)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_CARBU() {
	return "NOM_ST_CARBU";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_CARBU
 * Date de création : (06/06/05 16:03:48)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_CARBU() {
	return getZone(getNOM_ST_CARBU());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMPTEUR
 * Date de création : (06/06/05 16:03:48)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_COMPTEUR() {
	return "NOM_ST_COMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMPTEUR
 * Date de création : (06/06/05 16:03:48)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_COMPTEUR() {
	return getZone(getNOM_ST_COMPTEUR());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DESIGNATION
 * Date de création : (06/06/05 16:03:48)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DESIGNATION() {
	return "NOM_ST_DESIGNATION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DESIGNATION
 * Date de création : (06/06/05 16:03:48)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DESIGNATION() {
	return getZone(getNOM_ST_DESIGNATION());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NBPNEUAR
 * Date de création : (06/06/05 16:03:48)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NBPNEUAR() {
	return "NOM_ST_NBPNEUAR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NBPNEUAR
 * Date de création : (06/06/05 16:03:48)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NBPNEUAR() {
	return getZone(getNOM_ST_NBPNEUAR());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NBPNEUAV
 * Date de création : (06/06/05 16:03:48)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NBPNEUAV() {
	return "NOM_ST_NBPNEUAV";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NBPNEUAV
 * Date de création : (06/06/05 16:03:48)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NBPNEUAV() {
	return getZone(getNOM_ST_NBPNEUAV());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NBESSIEUX
 * Date de création : (06/06/05 16:03:48)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NBESSIEUX() {
	return "NOM_ST_NBESSIEUX";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NBPNEUESSIEUX
 * Date de création : (06/06/05 16:03:48)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NBESSIEUX() {
	return getZone(getNOM_ST_NBESSIEUX());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PNEU
 * Date de création : (06/06/05 16:03:48)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_PNEU() {
	return "NOM_ST_PNEU";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PNEU
 * Date de création : (06/06/05 16:03:48)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_PNEU() {
	return getZone(getNOM_ST_PNEU());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PUISSANCE
 * Date de création : (06/06/05 16:03:48)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_PUISSANCE() {
	return "NOM_ST_PUISSANCE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PUISSANCE
 * Date de création : (06/06/05 16:03:48)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_PUISSANCE() {
	return getZone(getNOM_ST_PUISSANCE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_RESERVOIR
 * Date de création : (06/06/05 16:03:48)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_RESERVOIR() {
	return "NOM_ST_RESERVOIR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_RESERVOIR
 * Date de création : (06/06/05 16:03:48)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_RESERVOIR() {
	return getZone(getNOM_ST_RESERVOIR());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPEEQUIP
 * Date de création : (06/06/05 16:03:48)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TYPEEQUIP() {
	return "NOM_ST_TYPEEQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPEEQUIP
 * Date de création : (06/06/05 16:03:48)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TYPEEQUIP() {
	return getZone(getNOM_ST_TYPEEQUIP());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_VERSION
 * Date de création : (06/06/05 16:03:48)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_VERSION() {
	return "NOM_ST_VERSION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_VERSION
 * Date de création : (06/06/05 16:03:48)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_VERSION() {
	return getZone(getNOM_ST_VERSION());
}
	
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (11/05/05 15:26:22)
 * author : Générateur de process
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

		//Si clic sur le bouton PB_OKMARQUES
		if (testerParametre(request, getNOM_PB_OKMARQUES())) {
			return performPB_OKMARQUES(request);
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
 * Date de création : (07/06/05 08:54:50)
 * author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeModeles.jsp";
}
	public int getIsVide() {
		return isVide;
	}
	public void setIsVide(int isVide) {
		this.isVide = isVide;
	}
}
