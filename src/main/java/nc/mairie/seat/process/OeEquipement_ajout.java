package nc.mairie.seat.process;

import java.util.ArrayList;
import nc.mairie.seat.metier.Carburant;
import nc.mairie.seat.metier.Compteur;
import nc.mairie.seat.metier.Equipement;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.Marques;
import nc.mairie.seat.metier.Modeles;
import nc.mairie.seat.metier.Pneu;
import nc.mairie.seat.metier.TYPEEQUIP;
import nc.mairie.technique.*;
/**
 * Process OeEquipement_ajout
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
*/
public class OeEquipement_ajout extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3700088663766212615L;
	private java.lang.String[] LB_MARQUE;
	private java.lang.String[] LB_MODELE;
	private String ACTION_MARQUE = "Sélection d'une marque";
	private ArrayList<Marques> listeMarque = null;
	private ArrayList<Modeles> listeModele = null;
	private Equipement equipementCourant;
	private TYPEEQUIP tequipCourant;
	private String newModele = "";
	private String focus = null;
	private boolean first=true;
	private boolean manqueParam = false;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{

	//on met la date du jour par défaut
	if(first){
		String datedujour = Services.dateDuJour(); 
		addZone(getNOM_EF_DATEMISEENCIRCULATION(),datedujour);
	}
	
//	 on met le focus sur le libellé du modèle
	setFocus(getNOM_EF_NOINVENTAIRE());
	
	//on initialise la liste des marques
	if(first){
	//	 Si liste des marques est vide
		if (getLB_MARQUE() == LBVide) {
			ArrayList<Marques> a = Marques.listerMarquesModele(getTransaction());
			if (getTransaction().isErreur()){
				return;
			}
			setListeMarque(a);
			//les élèments de la liste 
			int [] tailles = {15};
			String [] champs = {"designationmarque"};
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"G"};
		
			setLB_MARQUE(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
		}
		if (getLB_MARQUE()==LBVide){
			getTransaction().declarerErreur("Attention. Aucun modèle ou Aucune marque n'est enregistré");
			setManqueParam(true);
		}else{
			addZone(getNOM_LB_MARQUE_SELECT(),"0");
			performPB_OK_MARQUE(request);
		}
	}
	first = false;
	
}
/**
 * Constructeur du process OeEquipement_ajout.
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public OeEquipement_ajout() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (26/05/05 10:19:04)
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
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {

	setStatut(STATUT_PROCESS_APPELANT);
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_MARQUE
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_OK_MARQUE() {
	return "NOM_PB_OK_MARQUE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public boolean performPB_OK_MARQUE(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_MARQUE_SELECT()) ? Integer.parseInt(getVAL_LB_MARQUE_SELECT()): -1); 

	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	
	Marques maMarque = (Marques)getListeMarque().get(indice);
	
//	 on remplit la liste déroulante avec la table f_modeles
	//	 Si liste des modeles pour une marque est vide
	ArrayList<Modeles> a = Modeles.listerModelesMarque(getTransaction(),maMarque.getCodemarque());
	setListeModele(a);
	//les élèments de la liste 
	int [] tailles = {15};
	String [] champs = {"designationmodele"};
	//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
	String [] padding = {"G"};
	
	setLB_MODELE(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
	
//	On nomme l'action pour afficher la liste
	addZone(getNOM_ST_MARQUE(),ACTION_MARQUE);	
	addZone(getNOM_LB_MODELE_SELECT(),"0");
	performPB_OK_MODELE(request);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_MODELE
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_OK_MODELE() {
	return "NOM_PB_OK_MODELE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public boolean performPB_OK_MODELE(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_MODELE_SELECT()) ? Integer.parseInt(getVAL_LB_MODELE_SELECT()): -1); 
		
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	Modeles monModele = (Modeles)getListeModele().get(indice);
	if (getTransaction().isErreur()){
		return false;
	}
	//Pour l'ajout ou la modification
	newModele = monModele.getCodemodele();
	if(!first){
		addZone(getNOM_ST_VERSION(),monModele.getVersion());
		//Alimentation des zones statiques correspondant à l'équipement
		Carburant monCarbu = Carburant.chercherCarburant(getTransaction(), monModele.getCodecarburant());
		if (getTransaction().isErreur()){
			return false;
		}
		addZone(getNOM_ST_CARBU(), monCarbu.getDesignationcarbu());
		Compteur monCompteur = Compteur.chercherCompteur(getTransaction(), monModele.getCodecompteur());
		if (getTransaction().isErreur()){
			return false;
		}
		addZone(getNOM_ST_COMPTEUR(), monCompteur.getDesignationcompteur());
		Pneu monPneu = Pneu.chercherPneu(getTransaction(), monModele.getCodepneu());
		if(getTransaction().isErreur()){
			return false;
		}
		addZone(getNOM_ST_PNEU(), monPneu.getDimension());
		TYPEEQUIP monTe = TYPEEQUIP.chercherTYPEEQUIP(getTransaction(),monModele.getCodete());
		setTequipCourant(monTe);
		if(getTransaction().isErreur()){
			return false;
		}
		addZone(getNOM_ST_TYPE_EQUIP(), monTe.getDesignationte());
		if ("MT".equals(monTe.getTypete().trim())){
			addZone(getNOM_EF_NOIMMAT(),getVAL_EF_NOINVENTAIRE());
		}
		
		addZone(getNOM_ST_RESERVOIR(), monModele.getCapacitereservoir());
		addZone(getNOM_ST_PUISSANCE(), monModele.getPuissance());
		Marques maMarque = Marques.chercherMarques(getTransaction(),monModele.getCodemarque());
		if (getTransaction().isErreur()){
			return false;
		}
		addZone(getNOM_ST_PE(),maMarque.getDesignationmarque().trim()+" "+ monModele.getDesignationmodele().trim());
	}
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (26/05/05 10:19:04)
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
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	performPB_OK_MODELE(request);
	//Récup des zones saisies
	String newInventaire = getZone(getNOM_EF_NOINVENTAIRE()).toUpperCase();
	//si l'équipement est de type MT (matériel) alors le numéro immatriculation = numéro d'inventaire
	String newImmat = getZone(getNOM_EF_NOIMMAT()).toUpperCase();
	if(tequipCourant==null){
		getTransaction().declarerErreur("Vous devez sélectionné un modèle");
		setFocus(getNOM_LB_MODELE());
		return false;
	}
	if ("MT".equals(getTequipCourant().getTypete().trim())){
		newImmat = getZone(getNOM_EF_NOINVENTAIRE()).toUpperCase();
		addZone(getNOM_EF_NOIMMAT(),getVAL_EF_NOINVENTAIRE());
	}
	 	
	String newPrix = getZone(getNOM_EF_PRIX());
	String newGarantie = getZone(getNOM_EF_GARANTIE());
	String newDateMiseEnCirculation = "";
	if (Services.estUneDate(getZone(getNOM_EF_DATEMISEENCIRCULATION()))){
		newDateMiseEnCirculation = Services.formateDate(getZone(getNOM_EF_DATEMISEENCIRCULATION()).toUpperCase());
	}else if (!getZone(getNOM_EF_DATEMISEENCIRCULATION()).equals("")){
		getTransaction().declarerErreur("La date de mise en circulation n'est pas correcte.");
		setFocus(getNOM_EF_DATEMISEENCIRCULATION());
		return false;
	}
	String reserve = getZone(getNOM_CK_RESERVE());
//	 pour réservé
	String newReserve = reserve.equals(getCHECKED_ON()) ? "T" : "F";
		
	//	 Pour les champs obligatoires
//	Si lib inventaire non saisit
	if (newInventaire.length() == 0) {
		getTransaction().declarerErreur("Le n° d'inventaire est obligatoire");
		setFocus(getNOM_EF_NOINVENTAIRE());
		return false;
	}
	//	Si lib immatriculation non saisit
	if (newImmat.length() == 0) {
		getTransaction().declarerErreur("Le n° immatriculation est obligatoire");
		setFocus(getNOM_EF_NOIMMAT());
		return false;
	}
	//	Si lib prix non saisit
	if (newPrix.length() == 0) {
		getTransaction().declarerErreur("Le prix est obligatoire");
		setFocus(getNOM_EF_PRIX());
		return false;
	}
	//	Si lib datemiseencirculation non saisit
	if (newDateMiseEnCirculation.length() == 0) {
		getTransaction().declarerErreur("La date de mise en circulation est obligatoire");
		setFocus(getNOM_EF_DATEMISEENCIRCULATION());
		return false;
	}
//		Si lib codemodele non saisit
	if (newModele.length() == 0) {
		getTransaction().declarerErreur("Le modele est obligatoire");
		setFocus(getNOM_LB_MODELE());
		return false;
	}
	setEquipementCourant(new Equipement());
	
//		Affectation des attributs
	getEquipementCourant().setNumeroimmatriculation(newImmat);
	getEquipementCourant().setNumeroinventaire(newInventaire);
	getEquipementCourant().setDatemiseencirculation(newDateMiseEnCirculation);
	getEquipementCourant().setPrixachat(newPrix);
	getEquipementCourant().setDureegarantie(newGarantie);
	getEquipementCourant().setReserve(newReserve);
	getEquipementCourant().setCodemodele(newModele);

	//Création
	getEquipementCourant().creerEquipement(getTransaction(),getEquipementCourant());
	if (getTransaction().isErreur()){
		return false;
	}else{
		//Tout s'est bien passé
		commitTransaction();	
		
		//On vide les zones de saisies pour préaparer un autre ajout
		addZone(getNOM_EF_GARANTIE(),"");
		addZone(getNOM_EF_NOIMMAT(),"");
		addZone(getNOM_EF_NOINVENTAIRE(),"");
		addZone(getNOM_EF_PRIX(),"");
		addZone(getNOM_LB_MARQUE_SELECT(),"0");
		addZone(getNOM_LB_MODELE_SELECT(),"0");
		addZone(getNOM_CK_RESERVE(),getCHECKED_OFF());
	//	 on retourne à la liste des équipements
		getTransaction().declarerErreur("La création de l'équipement "+getEquipementCourant().getNumeroinventaire()+" a bien été faite");
		EquipementInfos unEquipInfos = EquipementInfos.chercherEquipementInfos(getTransaction(),getEquipementCourant().getNumeroinventaire());
		if(getTransaction().isErreur()){
			return false;
		}
		if(null==unEquipInfos){
			unEquipInfos = new EquipementInfos();
		}
		VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",unEquipInfos);
		setStatut(STATUT_PROCESS_APPELANT);
	}
	return true;
	
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DATEMISEENCIRCULATION
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_DATEMISEENCIRCULATION() {
	return "NOM_EF_DATEMISEENCIRCULATION";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DATEMISEENCIRCULATION
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_DATEMISEENCIRCULATION() {
	return getZone(getNOM_EF_DATEMISEENCIRCULATION());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_GARANTIE
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_GARANTIE() {
	return "NOM_EF_GARANTIE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_GARANTIE
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_GARANTIE() {
	return getZone(getNOM_EF_GARANTIE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_NOIMMAT
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_NOIMMAT() {
	return "NOM_EF_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_NOIMMAT
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_NOIMMAT() {
	return getZone(getNOM_EF_NOIMMAT());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_NOINVENTAIRE
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_NOINVENTAIRE() {
	return "NOM_EF_NOINVENTAIRE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_NOINVENTAIRE
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_NOINVENTAIRE() {
	return getZone(getNOM_EF_NOINVENTAIRE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_PRIX
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_PRIX() {
	return "NOM_EF_PRIX";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_PRIX
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_PRIX() {
	return getZone(getNOM_EF_PRIX());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_MARQUE
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
private String [] getLB_MARQUE() {
	if (LB_MARQUE == null)
		LB_MARQUE = initialiseLazyLB();
	return LB_MARQUE;
}
/**
 * Setter de la liste:
 * LB_MARQUE
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
private void setLB_MARQUE(java.lang.String[] newLB_MARQUE) {
	LB_MARQUE = newLB_MARQUE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_MARQUE
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_MARQUE() {
	return "NOM_LB_MARQUE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_MARQUE_SELECT
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_MARQUE_SELECT() {
	return "NOM_LB_MARQUE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_MARQUE
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_MARQUE() {
	return getLB_MARQUE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_MARQUE
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_MARQUE_SELECT() {
	return getZone(getNOM_LB_MARQUE_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_MODELE
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
private String [] getLB_MODELE() {
	if (LB_MODELE == null)
		LB_MODELE = initialiseLazyLB();
	return LB_MODELE;
}
/**
 * Setter de la liste:
 * LB_MODELE
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
private void setLB_MODELE(java.lang.String[] newLB_MODELE) {
	LB_MODELE = newLB_MODELE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_MODELE
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_MODELE() {
	return "NOM_LB_MODELE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_MODELE_SELECT
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_MODELE_SELECT() {
	return "NOM_LB_MODELE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_MODELE
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_MODELE() {
	return getLB_MODELE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_MODELE
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_MODELE_SELECT() {
	return getZone(getNOM_LB_MODELE_SELECT());
}
/**
 * Retourne le nom de la case à cocher sélectionnée pour la JSP :
 * CK_RESERVE
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public java.lang.String getNOM_CK_RESERVE() {
	return "NOM_CK_RESERVE";
}
/**
 * Retourne la valeur de la case à cocher à afficher par la JSP pour la case à cocher  :
 * CK_RESERVE
 * Date de création : (26/05/05 10:19:04)
 * @author : Générateur de process
 */
public java.lang.String getVAL_CK_RESERVE() {
	return getZone(getNOM_CK_RESERVE());
}
	
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (26/05/05 10:26:23)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (26/05/05 10:26:23)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_CARBU
 * Date de création : (26/05/05 10:31:05)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_CARBU() {
	return "NOM_ST_CARBU";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_CARBU
 * Date de création : (26/05/05 10:31:05)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_CARBU() {
	return getZone(getNOM_ST_CARBU());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMPTEUR
 * Date de création : (26/05/05 10:31:05)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_COMPTEUR() {
	return "NOM_ST_COMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMPTEUR
 * Date de création : (26/05/05 10:31:05)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_COMPTEUR() {
	return getZone(getNOM_ST_COMPTEUR());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PE
 * Date de création : (26/05/05 10:31:05)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_PE() {
	return "NOM_ST_PE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PE
 * Date de création : (26/05/05 10:31:05)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_PE() {
	return getZone(getNOM_ST_PE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PNEU
 * Date de création : (26/05/05 10:31:05)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_PNEU() {
	return "NOM_ST_PNEU";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PNEU
 * Date de création : (26/05/05 10:31:05)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_PNEU() {
	return getZone(getNOM_ST_PNEU());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PUISSANCE
 * Date de création : (26/05/05 10:31:05)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_PUISSANCE() {
	return "NOM_ST_PUISSANCE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PUISSANCE
 * Date de création : (26/05/05 10:31:05)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_PUISSANCE() {
	return getZone(getNOM_ST_PUISSANCE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_RESERVOIR
 * Date de création : (26/05/05 10:31:05)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_RESERVOIR() {
	return "NOM_ST_RESERVOIR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_RESERVOIR
 * Date de création : (26/05/05 10:31:05)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_RESERVOIR() {
	return getZone(getNOM_ST_RESERVOIR());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE_EQUIP
 * Date de création : (26/05/05 10:31:05)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TYPE_EQUIP() {
	return "NOM_ST_TYPE_EQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE_EQUIP
 * Date de création : (26/05/05 10:31:05)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TYPE_EQUIP() {
	return getZone(getNOM_ST_TYPE_EQUIP());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_VERSION
 * Date de création : (26/05/05 10:31:05)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_VERSION() {
	return "NOM_ST_VERSION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_VERSION
 * Date de création : (26/05/05 10:31:05)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_VERSION() {
	return getZone(getNOM_ST_VERSION());
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
	 * @return Renvoie equipementCourant.
	 */
	private Equipement getEquipementCourant() {
		return equipementCourant;
	}
	/**
	 * @param equipementCourant equipementCourant à définir.
	 */
	private void setEquipementCourant(Equipement equipementCourant) {
		this.equipementCourant = equipementCourant;
	}
	/**
	 * @return Renvoie tequipCourant.
	 */
	private TYPEEQUIP getTequipCourant() {
		return tequipCourant;
	}
	/**
	 * @param tequipCourant tequipCourant à définir.
	 */
	private void setTequipCourant(TYPEEQUIP tequipCourant) {
		this.tequipCourant = tequipCourant;
	}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (26/05/05 10:19:04)
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

		//Si clic sur le bouton PB_OK_MARQUE
		if (testerParametre(request, getNOM_PB_OK_MARQUE())) {
			return performPB_OK_MARQUE(request);
		}

		//Si clic sur le bouton PB_OK_MODELE
		if (testerParametre(request, getNOM_PB_OK_MODELE())) {
			return performPB_OK_MODELE(request);
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
 * Date de création : (08/06/05 07:49:37)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeEquipement_ajout.jsp";
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DATERECEPTION
 * Date de création : (08/06/05 07:49:37)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_DATERECEPTION() {
	return "NOM_EF_DATERECEPTION";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DATERECEPTION
 * Date de création : (08/06/05 07:49:37)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_DATERECEPTION() {
	return getZone(getNOM_EF_DATERECEPTION());
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
	return getNOM_EF_NOINVENTAIRE();
}

	public boolean isManqueParam() {
		return manqueParam;
	}
	public void setManqueParam(boolean manqueParam) {
		this.manqueParam = manqueParam;
	}
}
