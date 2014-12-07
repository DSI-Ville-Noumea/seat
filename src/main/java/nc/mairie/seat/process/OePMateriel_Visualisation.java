package nc.mairie.seat.process;

import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.technique.*;
/**
 * Process OePMateriel_Visualisation
 * Date de création : (25/04/07 14:16:52)
 * @author : Générateur de process
*/
public class OePMateriel_Visualisation extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -644551992732480320L;
	public static final int STATUT_RECHERCHE = 1;
	private PMatInfos pMatInfosCourant;
	private String focus = null;
	public boolean isDebranche = false;
	
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (25/04/07 14:16:52)
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
//on récupère le petit matériel courant
	PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request,"PMATINFOS");
	if(unPMatInfos!=null){
		if (unPMatInfos.getPminv()!=null){
			setPMatInfosCourant(unPMatInfos);
			// initialisation des zones
			if ("01/01/0001".equals(unPMatInfos.getDachat())){
				addZone(getNOM_ST_DACHAT(), "");
			}else{
				addZone(getNOM_ST_DACHAT(), unPMatInfos.getDachat());
			}
			if ("01/01/0001".equals(unPMatInfos.getDmhs())){
				addZone(getNOM_ST_DMHS(), "");
			}else{
				addZone(getNOM_ST_DMHS(), unPMatInfos.getDmhs());
			}
			if ("01/01/0001".equals(unPMatInfos.getDmes())){
				addZone(getNOM_ST_DMES(), "");
			}else{
				addZone(getNOM_ST_DMES(), unPMatInfos.getDmes());
			}
			addZone(getNOM_ST_DGARANTIE(),getPMatInfosCourant().getDgarantie());
			addZone(getNOM_ST_FOURNISSEUR(),getPMatInfosCourant().getLibellefre());
			addZone(getNOM_ST_NOM_EQUIP(),getPMatInfosCourant().getDesignationmarque().trim()+" "+getPMatInfosCourant().getDesignationmodele().trim()+" "+getPMatInfosCourant().getDesignationtypeequip().trim());
			addZone(getNOM_ST_PMINV(),getPMatInfosCourant().getPminv());
			addZone(getNOM_ST_PMSERIE(),getPMatInfosCourant().getPmserie());
			addZone(getNOM_ST_PRIX(),getPMatInfosCourant().getPrix());
			if ("F".equals(getPMatInfosCourant().getReserve().trim())){
				addZone(getNOM_ST_RESERVE(),"non");
			}else{
				addZone(getNOM_ST_RESERVE(),"oui");
			}
		}
	}
	// quand appui sur entrée
	if(!getZone(getNOM_EF_RECHERCHER()).equals("")){
		performPB_RECHERCHER(request);
		//addZone(getNOM_EF_RECHERCHER(),"");
	}

}
/**
 * Constructeur du process OePMateriel_Visualisation.
 * Date de création : (25/04/07 14:16:52)
 * @author : Générateur de process
 */
public OePMateriel_Visualisation() {
	super();
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DACHAT
 * Date de création : (03/05/07 09:26:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DACHAT() {
	return "NOM_ST_DACHAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DACHAT
 * Date de création : (03/05/07 09:26:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DACHAT() {
	return getZone(getNOM_ST_DACHAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DGARANTIE
 * Date de création : (03/05/07 09:26:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DGARANTIE() {
	return "NOM_ST_DGARANTIE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DGARANTIE
 * Date de création : (03/05/07 09:26:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DGARANTIE() {
	return getZone(getNOM_ST_DGARANTIE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DMES
 * Date de création : (03/05/07 09:26:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DMES() {
	return "NOM_ST_DMES";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DMES
 * Date de création : (03/05/07 09:26:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DMES() {
	return getZone(getNOM_ST_DMES());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DMHS
 * Date de création : (03/05/07 09:26:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DMHS() {
	return "NOM_ST_DMHS";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DMHS
 * Date de création : (03/05/07 09:26:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DMHS() {
	return getZone(getNOM_ST_DMHS());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_FOURNISSEUR
 * Date de création : (03/05/07 09:26:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_FOURNISSEUR() {
	return "NOM_ST_FOURNISSEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_FOURNISSEUR
 * Date de création : (03/05/07 09:26:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_FOURNISSEUR() {
	return getZone(getNOM_ST_FOURNISSEUR());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOM_EQUIP
 * Date de création : (03/05/07 09:26:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOM_EQUIP() {
	return "NOM_ST_NOM_EQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOM_EQUIP
 * Date de création : (03/05/07 09:26:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOM_EQUIP() {
	return getZone(getNOM_ST_NOM_EQUIP());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PMINV
 * Date de création : (03/05/07 09:26:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_PMINV() {
	return "NOM_ST_PMINV";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PMINV
 * Date de création : (03/05/07 09:26:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_PMINV() {
	return getZone(getNOM_ST_PMINV());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PMSERIE
 * Date de création : (03/05/07 09:26:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_PMSERIE() {
	return "NOM_ST_PMSERIE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PMSERIE
 * Date de création : (03/05/07 09:26:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_PMSERIE() {
	return getZone(getNOM_ST_PMSERIE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PRIX
 * Date de création : (03/05/07 09:26:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_PRIX() {
	return "NOM_ST_PRIX";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PRIX
 * Date de création : (03/05/07 09:26:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_PRIX() {
	return getZone(getNOM_ST_PRIX());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_RESERVE
 * Date de création : (03/05/07 09:26:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_RESERVE() {
	return "NOM_ST_RESERVE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_RESERVE
 * Date de création : (03/05/07 09:26:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_RESERVE() {
	return getZone(getNOM_ST_RESERVE());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHER
 * Date de création : (03/05/07 09:30:18)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_RECHERCHER() {
	return "NOM_PB_RECHERCHER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (03/05/07 09:30:18)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_RECHERCHER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	 on vide les zones
	addZone(getNOM_ST_DACHAT(),"");
	addZone(getNOM_ST_DGARANTIE(),"");
	addZone(getNOM_ST_DMES(),"");
	addZone(getNOM_ST_DMHS(),"");
	addZone(getNOM_ST_FOURNISSEUR(),"");
	addZone(getNOM_ST_NOM_EQUIP(),"");
	addZone(getNOM_ST_PMINV(),"");
	addZone(getNOM_ST_PMSERIE(),"");
	addZone(getNOM_ST_PRIX(),"");
	addZone(getNOM_ST_RESERVE(),"");
	
	// recherche du petit matériel
	String recherche = getZone(getNOM_EF_RECHERCHER()).toUpperCase();
	PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),recherche);
	if(unPMatInfos!=null){
		if(unPMatInfos.getPminv()==null){
			getTransaction().declarerErreur("Le petit matériel n'a pas été trouvé");
			return false;
		}
	}
	if(getTransaction().isErreur()){
		return false;
	}
	if(null==unPMatInfos){
		unPMatInfos = new PMatInfos();
	}
	setPMatInfosCourant(unPMatInfos);
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	addZone(getNOM_ST_DACHAT(),unPMatInfos.getDachat());
	addZone(getNOM_ST_DGARANTIE(),unPMatInfos.getDgarantie());
	addZone(getNOM_ST_DMES(),unPMatInfos.getDmes());
	addZone(getNOM_ST_DMHS(),unPMatInfos.getDmhs());
	addZone(getNOM_ST_FOURNISSEUR(),unPMatInfos.getLibellefre());
	addZone(getNOM_ST_NOM_EQUIP(),unPMatInfos.getDesignationmarque()+" "+unPMatInfos.getDesignationmodele());
	addZone(getNOM_ST_PMINV(),unPMatInfos.getPminv());
	addZone(getNOM_ST_PMSERIE(),unPMatInfos.getPmserie());
	addZone(getNOM_ST_PRIX(),unPMatInfos.getPrix());
	if ("F".equals(unPMatInfos.getReserve().trim())){
		addZone(getNOM_ST_RESERVE(),"non");
	}else{
		addZone(getNOM_ST_RESERVE(),"oui");
	}
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE_PM
 * Date de création : (03/05/07 09:30:18)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_RECHERCHE_PM() {
	return "NOM_PB_RECHERCHE_PM";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (03/05/07 09:30:18)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_RECHERCHE_PM(javax.servlet.http.HttpServletRequest request) throws Exception {
	
	//#12439 
	addZone(getNOM_EF_RECHERCHER(), "");
	
	VariableActivite.ajouter(this,"MODE","VISUALISER");
	setStatut(STATUT_RECHERCHE,true);
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_RECHERCHER
 * Date de création : (03/05/07 09:30:18)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_RECHERCHER() {
	return "NOM_EF_RECHERCHER";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_RECHERCHER
 * Date de création : (03/05/07 09:30:18)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_RECHERCHER() {
	return getZone(getNOM_EF_RECHERCHER());
}
/**
 * @return Renvoie pMaterielCourant.
 */
private PMatInfos getPMatInfosCourant() {
	return pMatInfosCourant;
}
/**
 * @param tequipCourant tequipCourant à définir.
 */
private void setPMatInfosCourant(PMatInfos pMatInfosCourant) {
	this.pMatInfosCourant = pMatInfosCourant;
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
	return getNOM_EF_RECHERCHER();
}
public boolean isDebranche() {
	return isDebranche;
}
public void setDebranche(boolean isDebranche) {
	this.isDebranche = isDebranche;
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (25/04/07 14:16:52)
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

		//Si clic sur le bouton PB_RECHERCHER
		if (testerParametre(request, getNOM_PB_RECHERCHER())) {
			return performPB_RECHERCHER(request);
		}

		//Si clic sur le bouton PB_RECHERCHE_PM
		if (testerParametre(request, getNOM_PB_RECHERCHE_PM())) {
			return performPB_RECHERCHE_PM(request);
		}

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (24/08/07 15:03:29)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OePMateriel_Visualisation.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RETOUR
 * Date de création : (24/08/07 15:03:29)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_RETOUR() {
	return "NOM_PB_RETOUR";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (24/08/07 15:03:29)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_RETOUR(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
}
