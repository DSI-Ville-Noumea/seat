package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.Carburant;
import nc.mairie.seat.metier.Compteur;
import nc.mairie.seat.metier.Marques;
import nc.mairie.seat.metier.Modeles;
import nc.mairie.seat.metier.Pneu;
import nc.mairie.seat.metier.TYPEEQUIP;
import nc.mairie.technique.*;
/**
 * Process OeModeles_modif
 * Date de création : (07/06/05 07:14:51)
* 
*/
public class OeModeles_modif extends nc.mairie.technique.BasicProcess {
	private java.lang.String[] LB_CARBU;
	private java.lang.String[] LB_COMPTEUR;
	private java.lang.String[] LB_PNEU;
	private java.lang.String[] LB_TYPEEQUIP;
	private ArrayList listeTEquip = null;
	private ArrayList listePneu = null;
	private ArrayList listeCarburant = null;
	private ArrayList listeCompteur = null;
	private Modeles modeleCourant;
	private Marques marquesCourant;
	private TYPEEQUIP tEquipCourant;
	private Pneu pneuCourant;
	private Carburant carburantCourant;
	private Compteur compteurCourant;
	private String codePneu;
	private String codeCompteur;
	private String codeCarburant;
	private String codeTe;
	private String codeMarque;
	boolean first = true;
	private boolean isMateriel = false;
	private String focus = null;
	
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (07/06/05 07:14:51)
* 
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	
	// on récupère l'objet marque
	marquesCourant = (Marques)VariableActivite.recuperer(this, "MARQUES");
	Modeles unModeles = (Modeles)VariableActivite.recuperer(this, "MODELES");
	setModeleCourant(unModeles);
	
//	 on met le focus sur le libellé du modèle
	setFocus(getNOM_EF_DESIGNATION());
	if (first){
		//on alimente les objets courants
		Pneu pneu = Pneu.chercherPneu(getTransaction(),modeleCourant.getCodepneu());
		if (getTransaction().isErreur()){
			return;
		}
		setPneuCourant(pneu);
		codePneu = pneuCourant.getCodepneu();
		Compteur compteur = Compteur.chercherCompteur(getTransaction(),modeleCourant.getCodecompteur());
		if (getTransaction().isErreur()){
			return;
		}
		setCompteurCourant(compteur);
		codeCompteur = compteurCourant.getCodecompteur();
		Carburant carbu = Carburant.chercherCarburant(getTransaction(),modeleCourant.getCodecarburant());
		if (getTransaction().isErreur()){
			return;
		}
		setCarburantCourant(carbu);
		codeCarburant = carburantCourant.getCodecarbu();
		TYPEEQUIP te = TYPEEQUIP.chercherTYPEEQUIP(getTransaction(),modeleCourant.getCodete());
		if (getTransaction().isErreur()){
			return;
		}
		setTEquipCourant(te);
		codeTe = tEquipCourant.getCodete();
		if ("MT".equals(tEquipCourant.getDesignationte().trim())){
			isMateriel = true;
		}else{
			isMateriel = false;
		}
	}
//	on renseigne le code marque
	codeMarque = marquesCourant.getCodemarque();
	
	// on renseigne toutes les listes si c'est la première fois qu'on rentre sur cette fenêtre
	if (first){
		//	 on remplit la liste déroulante avec la table f_pneu
		//	 Si liste des pneu est vide
		if (getLB_PNEU() == LBVide) {
			java.util.ArrayList a = Pneu.listerPneu(getTransaction());
			setListePneu(a);
			//les élèments de la liste 
			int [] tailles = {30};
			String [] champs = {"dimension"};
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"G"};
			
			setLB_PNEU(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
			
			//	recherche du pneu courant
			addZone(getNOM_LB_PNEU_SELECT(),String.valueOf(-1));
			for (int i = 0; i < getListePneu().size(); i++) {
				Pneu unPneu = (Pneu)getListePneu().get(i);
				if (unPneu.getCodepneu().equals(getPneuCourant().getCodepneu())) {
					addZone(getNOM_LB_PNEU_SELECT(),String.valueOf(i));
					break;
				}
			}	
		}
		//	 on remplit la liste déroulante avec la table f_compteur
		//	 Si liste des compteurs est vide
		if (getLB_COMPTEUR() == LBVide) {
			java.util.ArrayList a = Compteur.listerCompteur(getTransaction());
			setListeCompteur(a);
			//les élèments de la liste 
			int [] tailles = {15};
			String [] champs = {"designationcompteur"};
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"G"};
			
			setLB_COMPTEUR(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
			
			//recherche du compteur courant
			addZone(getNOM_LB_COMPTEUR_SELECT(),String.valueOf(-1));
			for (int i = 0; i < getListeCompteur().size(); i++) {
				Compteur cpt = (Compteur)getListeCompteur().get(i);
				if (cpt.getCodecompteur().equals(getCompteurCourant().getCodecompteur())) {
					addZone(getNOM_LB_COMPTEUR_SELECT(),String.valueOf(i));
					break;
				}
			}	
		}
		//	 on remplit la liste déroulante avec la table f_carburant
		//	 Si liste des carburants est vide
		if (getLB_CARBU() == LBVide) {
			java.util.ArrayList a = Carburant.listerCarburant(getTransaction());
			setListeCarburant(a);
			//les élèments de la liste 
			int [] tailles = {10};
			String [] champs = {"designationcarbu"};
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"G"};
			
			setLB_CARBU(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
			
//			recherche du carbu courant
			addZone(getNOM_LB_CARBU_SELECT(),String.valueOf(-1));
			for (int i = 0; i < getListeCarburant().size(); i++) {
				Carburant unCarbu = (Carburant)getListeCarburant().get(i);
				if (unCarbu.getCodecarbu().equals(getCarburantCourant().getCodecarbu())) {
					addZone(getNOM_LB_CARBU_SELECT(),String.valueOf(i));
					break;
				}
			}	
		}
		
		//	 on remplit la liste déroulante avec la table f_tequip
		//	 Si liste des types d'équipements est vide
		if (getLB_TYPEEQUIP() == LBVide) {
			java.util.ArrayList a = TYPEEQUIP.listerTYPEEQUIP(getTransaction());
			setListeTEquip(a);
			//les élèments de la liste 
			int [] tailles = {20};
			String [] champs = {"designationte"};
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"G"};
			
			setLB_TYPEEQUIP(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
			
			//recherche du type d'équipement courant
			addZone(getNOM_LB_TYPEEQUIP_SELECT(),String.valueOf(-1));
			for (int i = 0; i < getListeTEquip().size(); i++) {
				TYPEEQUIP unTe = (TYPEEQUIP)getListeTEquip().get(i);
				if (unTe.getCodete().equals(getTEquipCourant().getCodete())) {
					addZone(getNOM_LB_TYPEEQUIP_SELECT(),String.valueOf(i));
					break;
				}
			}	
		}
	}
	
	//on alimente les zones
	addZone(getNOM_EF_DESIGNATION(), modeleCourant.getDesignationmodele());
	addZone(getNOM_EF_NBESSIEUX(),modeleCourant.getNbessieux());
	addZone(getNOM_EF_NBPNEUAR(),modeleCourant.nbpneuarriere);
	addZone(getNOM_EF_NBPNEUAV(),modeleCourant.getNbpneuavant());
	addZone(getNOM_EF_PUISSANCE(),modeleCourant.getPuissance());
	addZone(getNOM_EF_RESERVOIR(),modeleCourant.getCapacitereservoir());
	addZone(getNOM_EF_VERSION(),modeleCourant.getVersion());
	//	Alim zones pour les listes
	//	sélectionner le bon pneu
	/*int indicePneu = Integer.parseInt(modeleCourant.getCodepneu())-1;
	addZone(getNOM_LB_PNEU_SELECT(),String.valueOf(indicePneu));
	//	sélectionner le bon carburant
	int indiceCarburant = Integer.parseInt(modeleCourant.getCodecarburant())-1;
	addZone(getNOM_LB_CARBU_SELECT(),String.valueOf(indiceCarburant));
	//	sélectionner le bon type d'équipement
	int indiceTe = Integer.parseInt(modeleCourant.getCodete())-1;
	addZone(getNOM_LB_TYPEEQUIP_SELECT(),String.valueOf(indiceTe));
	//	sélectionner le bon compteur
	int indiceCompteur = Integer.parseInt(modeleCourant.getCodecompteur())-1;
	addZone(getNOM_LB_COMPTEUR_SELECT(),String.valueOf(indiceCompteur));*/

	// si ce n'est pas un équipement on renseigne isMateriel
	TYPEEQUIP monTe = TYPEEQUIP.chercherTYPEEQUIP(getTransaction(),modeleCourant.getCodete());
	if ("MT".equals(monTe.getDesignationte())){
		isMateriel = true;
	}else{
		isMateriel = false;
	}
	
	//on met first à false car déjà initialisé
	first = false;

}
/**
 * Constructeur du process OeModeles_modif.
 * Date de création : (07/06/05 07:14:51)
* 
 */
public OeModeles_modif() {
	super();
}

/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (07/06/05 07:14:51)
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
 * Date de création : (07/06/05 07:14:51)
* 
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DESIGNATION
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getNOM_EF_DESIGNATION() {
	return "NOM_EF_DESIGNATION";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DESIGNATION
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getVAL_EF_DESIGNATION() {
	return getZone(getNOM_EF_DESIGNATION());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_NBESSIEUX
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getNOM_EF_NBESSIEUX() {
	return "NOM_EF_NBESSIEUX";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_NBESSIEUX
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getVAL_EF_NBESSIEUX() {
	return getZone(getNOM_EF_NBESSIEUX());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_NBPNEUAR
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getNOM_EF_NBPNEUAR() {
	return "NOM_EF_NBPNEUAR";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_NBPNEUAR
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getVAL_EF_NBPNEUAR() {
	return getZone(getNOM_EF_NBPNEUAR());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_NBPNEUAV
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getNOM_EF_NBPNEUAV() {
	return "NOM_EF_NBPNEUAV";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_NBPNEUAV
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getVAL_EF_NBPNEUAV() {
	return getZone(getNOM_EF_NBPNEUAV());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_PUISSANCE
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getNOM_EF_PUISSANCE() {
	return "NOM_EF_PUISSANCE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_PUISSANCE
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getVAL_EF_PUISSANCE() {
	return getZone(getNOM_EF_PUISSANCE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_RESERVOIR
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getNOM_EF_RESERVOIR() {
	return "NOM_EF_RESERVOIR";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_RESERVOIR
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getVAL_EF_RESERVOIR() {
	return getZone(getNOM_EF_RESERVOIR());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_VERSION
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getNOM_EF_VERSION() {
	return "NOM_EF_VERSION";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_VERSION
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getVAL_EF_VERSION() {
	return getZone(getNOM_EF_VERSION());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_CARBU
 * Date de création : (07/06/05 07:14:51)
* 
 */
private String [] getLB_CARBU() {
	if (LB_CARBU == null)
		LB_CARBU = initialiseLazyLB();
	return LB_CARBU;
}
/**
 * Setter de la liste:
 * LB_CARBU
 * Date de création : (07/06/05 07:14:51)
* 
 */
private void setLB_CARBU(java.lang.String[] newLB_CARBU) {
	LB_CARBU = newLB_CARBU;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_CARBU
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getNOM_LB_CARBU() {
	return "NOM_LB_CARBU";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_CARBU_SELECT
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getNOM_LB_CARBU_SELECT() {
	return "NOM_LB_CARBU_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_CARBU
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String [] getVAL_LB_CARBU() {
	return getLB_CARBU();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_CARBU
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getVAL_LB_CARBU_SELECT() {
	return getZone(getNOM_LB_CARBU_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_COMPTEUR
 * Date de création : (07/06/05 07:14:51)
* 
 */
private String [] getLB_COMPTEUR() {
	if (LB_COMPTEUR == null)
		LB_COMPTEUR = initialiseLazyLB();
	return LB_COMPTEUR;
}
/**
 * Setter de la liste:
 * LB_COMPTEUR
 * Date de création : (07/06/05 07:14:51)
* 
 */
private void setLB_COMPTEUR(java.lang.String[] newLB_COMPTEUR) {
	LB_COMPTEUR = newLB_COMPTEUR;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_COMPTEUR
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getNOM_LB_COMPTEUR() {
	return "NOM_LB_COMPTEUR";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_COMPTEUR_SELECT
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getNOM_LB_COMPTEUR_SELECT() {
	return "NOM_LB_COMPTEUR_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_COMPTEUR
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String [] getVAL_LB_COMPTEUR() {
	return getLB_COMPTEUR();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_COMPTEUR
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getVAL_LB_COMPTEUR_SELECT() {
	return getZone(getNOM_LB_COMPTEUR_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_PNEU
 * Date de création : (07/06/05 07:14:51)
* 
 */
private String [] getLB_PNEU() {
	if (LB_PNEU == null)
		LB_PNEU = initialiseLazyLB();
	return LB_PNEU;
}
/**
 * Setter de la liste:
 * LB_PNEU
 * Date de création : (07/06/05 07:14:51)
* 
 */
private void setLB_PNEU(java.lang.String[] newLB_PNEU) {
	LB_PNEU = newLB_PNEU;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_PNEU
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getNOM_LB_PNEU() {
	return "NOM_LB_PNEU";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_PNEU_SELECT
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getNOM_LB_PNEU_SELECT() {
	return "NOM_LB_PNEU_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_PNEU
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String [] getVAL_LB_PNEU() {
	return getLB_PNEU();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_PNEU
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getVAL_LB_PNEU_SELECT() {
	return getZone(getNOM_LB_PNEU_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_TYPEEQUIP
 * Date de création : (07/06/05 07:14:51)
* 
 */
private String [] getLB_TYPEEQUIP() {
	if (LB_TYPEEQUIP == null)
		LB_TYPEEQUIP = initialiseLazyLB();
	return LB_TYPEEQUIP;
}
/**
 * Setter de la liste:
 * LB_TYPEEQUIP
 * Date de création : (07/06/05 07:14:51)
* 
 */
private void setLB_TYPEEQUIP(java.lang.String[] newLB_TYPEEQUIP) {
	LB_TYPEEQUIP = newLB_TYPEEQUIP;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_TYPEEQUIP
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getNOM_LB_TYPEEQUIP() {
	return "NOM_LB_TYPEEQUIP";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_TYPEEQUIP_SELECT
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getNOM_LB_TYPEEQUIP_SELECT() {
	return "NOM_LB_TYPEEQUIP_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_TYPEEQUIP
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String [] getVAL_LB_TYPEEQUIP() {
	return getLB_TYPEEQUIP();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_TYPEEQUIP
 * Date de création : (07/06/05 07:14:51)
* 
 */
public java.lang.String getVAL_LB_TYPEEQUIP_SELECT() {
	return getZone(getNOM_LB_TYPEEQUIP_SELECT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (07/06/05 07:17:59)
* 
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (07/06/05 07:17:59)
* 
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_CARBU
 * Date de création : (07/06/05 07:21:47)
* 
 */
public java.lang.String getNOM_PB_OK_CARBU() {
	return "NOM_PB_OK_CARBU";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (07/06/05 07:21:47)
* 
 */
public boolean performPB_OK_CARBU(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_CARBU_SELECT()) ? Integer.parseInt(getVAL_LB_CARBU_SELECT()): -1); 
		
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	Carburant monCarbu = (Carburant)getListeCarburant().get(indice);
	if (getTransaction().isErreur()){
		return false;
	}
	setCarburantCourant(monCarbu);
	codeCarburant= monCarbu.getCodecarbu();
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_COMPTEUR
 * Date de création : (07/06/05 07:21:47)
* 
 */
public java.lang.String getNOM_PB_OK_COMPTEUR() {
	return "NOM_PB_OK_COMPTEUR";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (07/06/05 07:21:47)
* 
 */
public boolean performPB_OK_COMPTEUR(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_COMPTEUR_SELECT()) ? Integer.parseInt(getVAL_LB_COMPTEUR_SELECT()): -1); 
		
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	
	Compteur monCpt = (Compteur)getListeCompteur().get(indice);
	if (getTransaction().isErreur()){
		return false;
	}
	setCompteurCourant(monCpt);
	codeCompteur = monCpt.getCodecompteur();
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_PNEU
 * Date de création : (07/06/05 07:21:47)
* 
 */
public java.lang.String getNOM_PB_OK_PNEU() {
	return "NOM_PB_OK_PNEU";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (07/06/05 07:21:47)
* 
 */
public boolean performPB_OK_PNEU(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_PNEU_SELECT()) ? Integer.parseInt(getVAL_LB_PNEU_SELECT()): -1); 
		
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	Pneu monPneu = (Pneu)getListePneu().get(indice);
	if (getTransaction().isErreur()){
		return false;
	}
	setPneuCourant(monPneu);
	codePneu = monPneu.getCodepneu();

	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_TYPEEQUIP
 * Date de création : (07/06/05 07:21:47)
* 
 */
public java.lang.String getNOM_PB_OK_TYPEEQUIP() {
	return "NOM_PB_OK_TYPEEQUIP";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (07/06/05 07:21:47)
* 
 */
public boolean performPB_OK_TYPEEQUIP(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_TYPEEQUIP_SELECT()) ? Integer.parseInt(getVAL_LB_TYPEEQUIP_SELECT()): -1); 
		
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	
	TYPEEQUIP monTe = (TYPEEQUIP)getListeTEquip().get(indice);
	if (getTransaction().isErreur()){
		return false;
	}
	setTEquipCourant(monTe);
	codeTe = monTe.getCodete();

	return true;
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (07/06/05 07:14:51)
* 
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_VALDIER
		if (testerParametre(request, getNOM_PB_VALIDER())) {
			return performPB_VALIDER(request);
		}
		//Si clic sur le bouton PB_OK_CARBU
		if (testerParametre(request, getNOM_PB_OK_CARBU())) {
			return performPB_OK_CARBU(request);
		}

		//Si clic sur le bouton PB_OK_COMPTEUR
		if (testerParametre(request, getNOM_PB_OK_COMPTEUR())) {
			return performPB_OK_COMPTEUR(request);
		}

		//Si clic sur le bouton PB_OK_PNEU
		if (testerParametre(request, getNOM_PB_OK_PNEU())) {
			return performPB_OK_PNEU(request);
		}

		//Si clic sur le bouton PB_OK_TYPEEQUIP
		if (testerParametre(request, getNOM_PB_OK_TYPEEQUIP())) {
			return performPB_OK_TYPEEQUIP(request);
		}

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (07/06/05 07:25:31)
* 
 */
@Override
public String getJSP() {
	return "OeModeles_modif.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALDIER
 * Date de création : (07/06/05 07:25:31)
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
 * Date de création : (07/06/05 07:25:31)
* 
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	//Récup des zones saisies
	String newDesignation = getZone(getNOM_EF_DESIGNATION()).toUpperCase();
	String newVersion = getZone(getNOM_EF_VERSION().toUpperCase()).toUpperCase();
	String newNbpneuavant = getZone(getNOM_EF_NBPNEUAV());
	String newNbpneuarriere = getZone(getNOM_EF_NBPNEUAR());
	String newNbessieux = getZone(getNOM_EF_NBESSIEUX());
	String newPuissance = getZone(getNOM_EF_PUISSANCE());
	String newReservoir = getZone(getNOM_EF_RESERVOIR());
//	pour ajouter le bon identifiant du Pneu
	String newPneu = codePneu;
//	 pour ajouter le bon identifiant du Type d'équipement
	String newTe = codeTe;
//	 pour ajouter le bon identifiant de Marque
	String newMarque = codeMarque;
//	 pour ajouter le bon identifiant de Carburant
	String newCarburant = codeCarburant;
//	 pour ajouter le bon identifiant de Compteur
	String newTcompteur = codeCompteur;
	

//	controle de saisie
	//Si lib designation non saisit
	if (newDesignation.length() == 0) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","du modèle"));//"ERR008","Le libellé @ est obligatoire"
		setFocus(getNOM_EF_DESIGNATION());
		return false;
	}
//	Si lib capacité du réservoir non saisit
	if (newReservoir.length() == 0) {
		setFocus(getNOM_EF_RESERVOIR());
		getTransaction().declarerErreur("La capacité du réservoir est obligatoire");
		return false;
	}
//	Si lib carburant non saisit
	if (newCarburant.length() == 0) {
		getTransaction().declarerErreur("Le choix du carburant est obligatoire");
		setFocus(getNOM_LB_CARBU());
		return false;
	}
//	Si lib pneu non saisit
	if (newPneu.length() == 0) {
		getTransaction().declarerErreur("Le choix du pneu est obligatoire");
		setFocus(getNOM_LB_PNEU());
		return false;
	}
//	Si lib type d'équipement non saisit
	if (newTe.length() == 0) {
		getTransaction().declarerErreur("Le choix du type d'équipement est obligatoire");
		setFocus(getNOM_LB_TYPEEQUIP());
		return false;
	}
//	Si lib compteur non saisit
	if (newTcompteur.length() == 0) {
		getTransaction().declarerErreur("Le choix du type de compteur est obligatoire");
		setFocus(getNOM_LB_COMPTEUR());
		return false;
	}
//	Si lib version non saisit
	if (newVersion.length() == 0) {
		setFocus(getNOM_EF_VERSION());
		getTransaction().declarerErreur("La version est obligatoire");
		return false;
	}
	
	if (!isMateriel){
	//	Si lib nb essieux non saisit
		if (newNbessieux.length() == 0) {
			setFocus(getNOM_EF_NBESSIEUX());
			getTransaction().declarerErreur("Le nombre d'essieux est obligatoire");
			return false;
		}
	//	Si lib nb pneu avant non saisit
		if (newNbpneuavant.length() == 0) {
			setFocus(getNOM_EF_NBPNEUAV());
			getTransaction().declarerErreur("Le nombre de pneu avant est obligatoire");
			return false;
		}
	//	Si lib nb pneu arrière non saisit
		if (newNbpneuarriere.length() == 0) {
			setFocus(getNOM_EF_NBPNEUAR());
			getTransaction().declarerErreur("Le nombre de pneu arrière est obligatoire");
			return false;
		}
	//	Si lib puissance non saisit
		if (newPuissance.length() == 0) {
			setFocus(getNOM_EF_PUISSANCE());
			getTransaction().declarerErreur("La puissance est obligatoire");
			return false;
		}
	}else{
//			Si les champs ne sont pas renseignés on met 0
			if (newNbessieux.length() == 0) {
				newNbessieux = "0";
			}
		//	Si lib nb pneu avant non saisit
			if (newNbpneuavant.length() == 0) {
				newNbpneuavant = "0";
			}
		//	Si lib nb pneu arrière non saisit
			if (newNbpneuarriere.length() == 0) {
				newNbpneuarriere = "0";
			}
		//	Si lib puissance non saisit
			if (newPuissance.length() == 0) {
				newPuissance = "0";
			}
		}
	
//	Affectation des attributs
	getModeleCourant().setDesignationmodele(newDesignation);
	getModeleCourant().setCapacitereservoir(newReservoir);
	getModeleCourant().setCodecarburant(newCarburant);
	getModeleCourant().setCodemarque(newMarque);
	getModeleCourant().setCodepneu(newPneu);
	getModeleCourant().setCodete(newTe);
	getModeleCourant().setNbessieux(newNbessieux);
	getModeleCourant().setNbpneuarriere(newNbpneuarriere);
	getModeleCourant().setNbpneuavant(newNbpneuavant);
	getModeleCourant().setPuissance(newPuissance);
	getModeleCourant().setCodecompteur(newTcompteur);
	getModeleCourant().setVersion(newVersion);
		
	//Modification
	getModeleCourant().modifierModeles(getTransaction(),newDesignation,newVersion);
	if (getTransaction().isErreur())
		return false;

	//Tout s'est bien passé
	commitTransaction();
	setStatut(STATUT_PROCESS_APPELANT);
	// on vide les zones
	addZone(getNOM_EF_DESIGNATION(),"");
	addZone(getNOM_EF_NBESSIEUX(),"");
	addZone(getNOM_EF_NBPNEUAR(),"");
	addZone(getNOM_EF_NBPNEUAV(),"");
	addZone(getNOM_EF_PUISSANCE(),"");
	addZone(getNOM_EF_RESERVOIR(),"");
	addZone(getNOM_EF_VERSION(),"");
	addZone(getNOM_ST_TITRE_ACTION(),"");
	return true;
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
	 * @return Renvoie compteurCourant.
	 */
	private Compteur getCompteurCourant() {
		return compteurCourant;
	}
	/**
	 * @param compteurCourant compteurCourant à définir.
	 */
	private void setCompteurCourant(Compteur compteurCourant) {
		this.compteurCourant = compteurCourant;
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
	 * @return Renvoie listeCompteur.
	 */
	private ArrayList getListeCompteur() {
		return listeCompteur;
	}
	/**
	 * @param listeCompteur listeCompteur à définir.
	 */
	private void setListeCompteur(ArrayList listeCompteur) {
		this.listeCompteur = listeCompteur;
	}
	/**
	 * @return Renvoie listeModeleMarque.
	 */
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
	 * @return Renvoie listeTEquip.
	 */
	private ArrayList getListeTEquip() {
		return listeTEquip;
	}
	/**
	 * @param listeTEquip listeTEquip à définir.
	 */
	private void setListeTEquip(ArrayList listeTEquip) {
		this.listeTEquip = listeTEquip;
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
	 * @return Renvoie tEquipCourant.
	 */
	private TYPEEQUIP getTEquipCourant() {
		return tEquipCourant;
	}
	/**
	 * @param equipCourant tEquipCourant à définir.
	 */
	private void setTEquipCourant(TYPEEQUIP equipCourant) {
		tEquipCourant = equipCourant;
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
		return getNOM_EF_DESIGNATION();
	}
}
