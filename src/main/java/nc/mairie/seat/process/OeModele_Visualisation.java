package nc.mairie.seat.process;

import nc.mairie.seat.metier.Equipement;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.ModeleInfos;
import nc.mairie.technique.*;
/**
 * Process OeModele_Visualisation
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
*/
public class OeModele_Visualisation extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -116868125791752139L;
	private ModeleInfos modeleInfosCourant;
	public static final int STATUT_RECHERCHE= 1 ;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	ModeleInfos unModeleInfos = (ModeleInfos)VariableGlobale.recuperer(request, "MODELEINFOS");

	if (unModeleInfos!=null){
		if (unModeleInfos.existeModeleInfos(getTransaction(),unModeleInfos.getCodemodele())){
			setModeleInfosCourant(unModeleInfos);
		}else{
			VariableGlobale.enlever(request,"MODELEINFOS");
		}
	}else{
		EquipementInfos unEI = (EquipementInfos)VariableGlobale.recuperer(request,"EQUIPEMENTINFOS");
		if(unEI!=null){
			if(unEI.getNumeroinventaire()!=null){
				Equipement unEquip = Equipement.chercherEquipement(getTransaction(),unEI.getNumeroinventaire());
				if(getTransaction().isErreur()){
					return ;
				}
				ModeleInfos unMI = ModeleInfos.chercherModeleInfos(getTransaction(),unEquip.getCodemodele());
				if(getTransaction().isErreur()){
					return ;
				}
				setModeleInfosCourant(unMI);
			}
		}
	}
	
	if (null!=(modeleInfosCourant)){
		// initialisation des zones
		addZone(getNOM_ST_CARBU(),modeleInfosCourant.getDesignationcarbu());
		addZone(getNOM_ST_COMPTEUR(),modeleInfosCourant.getDesignationcompteur());
		addZone(getNOM_ST_CAPACITE(),modeleInfosCourant.getCapacitereservoir());
		addZone(getNOM_ST_CODE(),modeleInfosCourant.getCodemodele());
		addZone(getNOM_ST_DESIGNATION(),modeleInfosCourant.getDesignationmodele());
		addZone(getNOM_ST_MARQUE(),modeleInfosCourant.getDesignationmarque());
		addZone(getNOM_ST_NBESSIEUX(),modeleInfosCourant.getNbessieux());
		addZone(getNOM_ST_NBPNEUAR(),modeleInfosCourant.getNbpneuarriere());
		addZone(getNOM_ST_NBPNEUAV(),modeleInfosCourant.getNbpneuavant());
		addZone(getNOM_ST_PNEU(),modeleInfosCourant.getDimension());
		addZone(getNOM_ST_PUISSANCE(),modeleInfosCourant.getPuissance());
		
		addZone(getNOM_ST_TEQUIP(),modeleInfosCourant.getDesignationtypeequip());
		addZone(getNOM_ST_VERSION(),modeleInfosCourant.getVersion());
		
	}

}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_RECHERCHE
		if (testerParametre(request, getNOM_PB_RECHERCHE())) {
			return performPB_RECHERCHE(request);
		}

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Constructeur du process OeModele_Visualisation.
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 */
public OeModele_Visualisation() {
	super();
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeModele_Visualisation.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_RECHERCHE() {
	return "NOM_PB_RECHERCHE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_RECHERCHE(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_RECHERCHE,true);
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_CAPACITE
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_CAPACITE() {
	return "NOM_ST_CAPACITE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_CAPACITE
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_CAPACITE() {
	return getZone(getNOM_ST_CAPACITE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_CARBU
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_CARBU() {
	return "NOM_ST_CARBU";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_CARBU
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_CARBU() {
	return getZone(getNOM_ST_CARBU());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_CODE
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_CODE() {
	return "NOM_ST_CODE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_CODE
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_CODE() {
	return getZone(getNOM_ST_CODE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMPTEUR
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_COMPTEUR() {
	return "NOM_ST_COMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMPTEUR
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_COMPTEUR() {
	return getZone(getNOM_ST_COMPTEUR());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DESIGNATION
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DESIGNATION() {
	return "NOM_ST_DESIGNATION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DESIGNATION
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DESIGNATION() {
	return getZone(getNOM_ST_DESIGNATION());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NBESSIEUX
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NBESSIEUX() {
	return "NOM_ST_NBESSIEUX";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NBESSIEUX
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NBESSIEUX() {
	return getZone(getNOM_ST_NBESSIEUX());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NBPNEUAR
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NBPNEUAR() {
	return "NOM_ST_NBPNEUAR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NBPNEUAR
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NBPNEUAR() {
	return getZone(getNOM_ST_NBPNEUAR());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NBPNEUAV
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NBPNEUAV() {
	return "NOM_ST_NBPNEUAV";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NBPNEUAV
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NBPNEUAV() {
	return getZone(getNOM_ST_NBPNEUAV());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PNEU
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_PNEU() {
	return "NOM_ST_PNEU";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PNEU
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_PNEU() {
	return getZone(getNOM_ST_PNEU());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PUISSANCE
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_PUISSANCE() {
	return "NOM_ST_PUISSANCE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PUISSANCE
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_PUISSANCE() {
	return getZone(getNOM_ST_PUISSANCE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TEQUIP
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TEQUIP() {
	return "NOM_ST_TEQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TEQUIP
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TEQUIP() {
	return getZone(getNOM_ST_TEQUIP());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_VERSION
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_VERSION() {
	return "NOM_ST_VERSION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_VERSION
 * Date de création : (10/06/05 10:56:08)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_VERSION() {
	return getZone(getNOM_ST_VERSION());
}
	/**
	 * @return Renvoie modeleInfosCourant.
	 */
	public ModeleInfos getModeleInfosCourant() {
		return modeleInfosCourant;
	}
	/**
	 * @param modeleInfosCourant modeleInfosCourant à définir.
	 */
	public void setModeleInfosCourant(ModeleInfos modeleInfosCourant) {
		this.modeleInfosCourant = modeleInfosCourant;
	}
}
