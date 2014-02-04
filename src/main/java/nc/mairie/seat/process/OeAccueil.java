package nc.mairie.seat.process;

import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.technique.*;
/**
 * Process OeAccueil
 * Date de création : (28/04/05 13:36:04)
 * @author : Générateur de process
*/
public class OeAccueil extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5165511443515386086L;
	public static final int STATUT_GO = 1;
	public static final int STATUT_MARQUES = 6;
	public static final int STATUT_BPC = 5;
	public static final int STATUT_PNEU = 3;
	public static final int STATUT_EQUIPEMENT = 24;
	public static final int STATUT_TYPEEQUIP = 10;
	public static final int STATUT_MODELES = 12;
	public static final int STATUT_ENTRETIEN = 14;
	public static final int STATUT_PIECES = 16;
	public static final int STATUT_CARBURANT = 20;
	public static final int STATUT_COMPTEUR = 21;
	public static final int STATUT_TINTERVALLE = 18;
	public static final int STATUT_MODEPRISE = 27;
	private EquipementInfos equipementInfosCourant;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (28/04/05 13:36:04)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	//addZone(getNOM_ST_TEXTE_COUCOU(), "Coucou popo c'est nous");
	EquipementInfos unEquipementInfos = (EquipementInfos)VariableActivite.recuperer(this, "EQUIPEMENTINFOS");
	setEquipementInfosCourant(unEquipementInfos);
	

}
/**
 * Constructeur du process OeAccueil.
 * Date de création : (28/04/05 13:36:04)
 * @author : Générateur de process
 */
public OeAccueil() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_GO
 * Date de création : (28/04/05 13:36:04)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_GO() {
	return "NOM_PB_GO";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (28/04/05 13:36:04)
 * @author : Générateur de process
 */
public boolean performPB_GO(javax.servlet.http.HttpServletRequest request) throws Exception {
	
	setStatut(STATUT_GO, true);
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TEXTE_COUCOU
 * Date de création : (28/04/05 13:37:04)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TEXTE_COUCOU() {
	return "NOM_ST_TEXTE_COUCOU";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TEXTE_COUCOU
 * Date de création : (28/04/05 13:37:04)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TEXTE_COUCOU() {
	return getZone(getNOM_ST_TEXTE_COUCOU());
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (02/05/05 08:49:48)
 * @author : Générateur de process
 */
public boolean performPB_PNEU(javax.servlet.http.HttpServletRequest request) throws Exception {
	
	setStatut(STATUT_PNEU,true);
	return true;
}
	
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_PNEU
 * Date de création : (02/05/05 09:31:50)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_PNEU() {
	return "NOM_PB_PNEU";
}
	
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (02/05/05 09:33:22)
 * @author : Générateur de process
 */
public boolean performPB_BPC(javax.servlet.http.HttpServletRequest request) throws Exception {
//	On met la variable activité
	VariableActivite.ajouter(this, "EQUIPEMENTINFOS", getEquipementInfosCourant());
	
	setStatut(STATUT_BPC,true);
	return true;
}
	
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MARQUES
 * Date de création : (04/05/05 13:36:15)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_MARQUES() {
	return "NOM_PB_MARQUES";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (04/05/05 13:36:15)
 * @author : Générateur de process
 */
public boolean performPB_MARQUES(javax.servlet.http.HttpServletRequest request) throws Exception {
	
	setStatut(STATUT_MARQUES,true);
	return true;
}
	
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_TYPEEQUIP
 * Date de création : (04/05/05 15:23:24)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_TYPEEQUIP() {
	return "NOM_PB_TYPEEQUIP";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (04/05/05 15:23:24)
 * @author : Générateur de process
 */
public boolean performPB_TYPEEQUIP(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_TYPEEQUIP,true);
	return true;
}
	
	
public boolean performPB_MODELES(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_MODELES,true);
	return true;
}

/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODELES
 * Date de création : (09/05/05 13:35:10)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_MODELES() {
	return "NOM_PB_MODELES";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (09/05/05 13:35:10)
 * @author : Générateur de process
 */

	
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ENTRETIEN
 * Date de création : (11/05/05 09:12:49)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_ENTRETIEN() {
	return "NOM_PB_ENTRETIEN";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (11/05/05 09:12:49)
 * @author : Générateur de process
 */
public boolean performPB_ENTRETIEN(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_ENTRETIEN,true);
	return true;
}
	
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_PIECES
 * Date de création : (13/05/05 13:46:41)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_PIECES() {
	return "NOM_PB_PIECES";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (13/05/05 13:46:41)
 * @author : Générateur de process
 */
public boolean performPB_PIECES(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_PIECES,true);
	return true;
}
	
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_TINTERVALLE
 * Date de création : (20/05/05 07:49:09)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_TINTERVALLE() {
	return "NOM_PB_TINTERVALLE";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_COMPTEUR
 * Date de création : (23/05/05 12:26:35)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_COMPTEUR() {
	return "NOM_PB_COMPTEUR";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (20/05/05 07:49:09)
 * @author : Générateur de process
 */
public boolean performPB_TINTERVALLE(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_TINTERVALLE,true);
	return true;
}
	
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_CARBURANT
 * Date de création : (23/05/05 12:26:35)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_CARBURANT() {
	return "NOM_PB_CARBURANT";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (23/05/05 12:26:35)
 * @author : Générateur de process
 */
public boolean performPB_CARBURANT(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_CARBURANT,true);
	return true;
}

/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (23/05/05 12:26:35)
 * @author : Générateur de process
 */
public boolean performPB_COMPTEUR(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_COMPTEUR,true);
	return true;
}
	
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_EQUIPEMENT
 * Date de création : (25/05/05 07:21:18)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_EQUIPEMENT() {
	return "NOM_PB_EQUIPEMENT";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (25/05/05 07:21:18)
 * @author : Générateur de process
 */
public boolean performPB_EQUIPEMENT(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_EQUIPEMENT,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_BPC
 * Date de création : (30/05/05 09:25:09)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_BPC() {
	return "NOM_PB_BPC";
}

	/**
	 * @return Renvoie equipementInfosCourant.
	 */
	private EquipementInfos getEquipementInfosCourant() {
		return equipementInfosCourant;
	}
	/**
	 * @param equipementInfosCourant equipementInfosCourant à définir.
	 */
	private void setEquipementInfosCourant(
			EquipementInfos equipementInfosCourant) {
		this.equipementInfosCourant = equipementInfosCourant;
	}
	
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (28/04/05 13:36:04)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_MODEPRISE
		if (testerParametre(request, getNOM_PB_MODEPRISE())) {
			return performPB_MODEPRISE(request);
		}
	//Si clic sur le bouton PB_EQUIPEMENT
		if (testerParametre(request, getNOM_PB_EQUIPEMENT())) {
			return performPB_EQUIPEMENT(request);
		}
		//Si clic sur le bouton PB_CARBURANT
		if (testerParametre(request, getNOM_PB_CARBURANT())) {
			return performPB_CARBURANT(request);
		}

		//Si clic sur le bouton PB_COMPTEUR
		if (testerParametre(request, getNOM_PB_COMPTEUR())) {
			return performPB_COMPTEUR(request);
		}
		//Si clic sur le bouton PB_TINTERVALLE
		if (testerParametre(request, getNOM_PB_TINTERVALLE())) {
			return performPB_TINTERVALLE(request);
		}
		//Si clic sur le bouton PB_PIECES
		if (testerParametre(request, getNOM_PB_PIECES())) {
			return performPB_PIECES(request);
		}
		//Si clic sur le bouton PB_ENTRETIEN
		if (testerParametre(request, getNOM_PB_ENTRETIEN())) {
			return performPB_ENTRETIEN(request);
		}
		//Si clic sur le bouton PB_MODELES
		if (testerParametre(request, getNOM_PB_MODELES())) {
			return performPB_MODELES(request);
		}
		//Si clic sur le bouton PB_TYPEEQUIP
		if (testerParametre(request, getNOM_PB_TYPEEQUIP())) {
			return performPB_TYPEEQUIP(request);
		}
		//Si clic sur le bouton PB_MARQUES
		if (testerParametre(request, getNOM_PB_MARQUES())) {
			return performPB_MARQUES(request);
		}
		//Si clic sur le bouton PB_BPC
		if (testerParametre(request, getNOM_PB_BPC())) {
			return performPB_BPC(request);
		}
//Si clic sur le bouton PB_PNEU
		if (testerParametre(request, getNOM_PB_PNEU())) {
			return performPB_PNEU(request);
		}
	//Si clic sur le bouton PB_GO
		if (testerParametre(request, getNOM_PB_GO())) {
			return performPB_GO(request);
		}

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (01/06/05 07:40:31)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeAccueil.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODEPRISE
 * Date de création : (01/06/05 07:40:31)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_MODEPRISE() {
	return "NOM_PB_MODEPRISE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (01/06/05 07:40:31)
 * @author : Générateur de process
 */
public boolean performPB_MODEPRISE(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_MODEPRISE,true);
	return true;
}
}
