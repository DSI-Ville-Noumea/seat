package nc.mairie.seat.process;

import java.util.ArrayList;
import java.util.ListIterator;

import nc.mairie.seat.metier.AffectationServiceInfos;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.Service;
import nc.mairie.technique.*;
/**
 * Process OeEquipementService_Recherche
 * Date de création : (16/06/05 09:49:12)
 * @author : Générateur de process
*/
public class OeEquipementService_Recherche extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5234513357374436635L;
	private java.lang.String[] LB_EQUIPEMENT;
	private java.lang.String[] LB_SERVICE;
	private ArrayList<Service> listeService;
	private ArrayList<Service> listeServiceEpuree;
	private ArrayList<AffectationServiceInfos> listeAffectationService;
	private String focus = null;
	private Service serviceCourant;
	private EquipementInfos equipementInfosCourant;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (16/06/05 09:49:12)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{

	performPB_RECHERCHER(request);

}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (16/06/05 09:49:12)
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

		//Si clic sur le bouton PB_OK
		if (testerParametre(request, getNOM_PB_OK())) {
			return performPB_OK(request);
		}

		//Si clic sur le bouton PB_OK_SCE
		if (testerParametre(request, getNOM_PB_OK_SCE())) {
			return performPB_OK_SCE(request);
		}

		//Si clic sur le bouton PB_RECHERCHER
		if (testerParametre(request, getNOM_PB_RECHERCHER())) {
			return performPB_RECHERCHER(request);
		}

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Constructeur du process OeEquipementService_Recherche.
 * Date de création : (16/06/05 09:49:12)
 * @author : Générateur de process
 */
public OeEquipementService_Recherche() {
	super();
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (16/06/05 09:49:12)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeEquipementService_Recherche.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (16/06/05 09:49:12)
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
 * Date de création : (16/06/05 09:49:12)
 * @author : Générateur de process
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_PROCESS_APPELANT);
	//setEquipementInfosCourant(null);
	//setServiceCourant(null);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (16/06/05 09:49:12)
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
 * Date de création : (16/06/05 09:49:12)
 * @author : Générateur de process
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_EQUIPEMENT_SELECT()) ? Integer.parseInt(getVAL_LB_EQUIPEMENT_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un équipement");
		return false;
	}
	AffectationServiceInfos monAffectationServiceInfos = (AffectationServiceInfos)getListeAffectationService().get(indice);
	if (getTransaction().isErreur()){
		return false;
	}
	EquipementInfos monEquipementInfos = EquipementInfos.chercherEquipementInfos(getTransaction(),monAffectationServiceInfos.getNumeroinventaire());
	if(getTransaction().isErreur()){
		getTransaction().declarerErreur(getTransaction().traiterErreur()+"L'équipement sélectionné n'a pas été trouvé.");
		return false;
	}
	if(null==monEquipementInfos){
		monEquipementInfos = new EquipementInfos();
	}
	setEquipementInfosCourant(monEquipementInfos);
	
	// modif du 13/08/08 : null dans SERVICE
	Service unService = Service.chercherService(getTransaction(),monAffectationServiceInfos.getCodeservice());
	if(getTransaction().isErreur()){
		getTransaction().declarerErreur(getTransaction().traiterErreur()+"Le service de l'équipement n'a pas été trouvé.");
		return false;
	}
	if (unService==null){
		unService = new Service();
	}
	setServiceCourant(getServiceCourant());
	
//	On met la variable 
	VariableGlobale.ajouter(request, "SERVICE", getServiceCourant());
	VariableGlobale.ajouter(request, "EQUIPEMENTINFOS", getEquipementInfosCourant());
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_SCE
 * Date de création : (16/06/05 09:49:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_OK_SCE() {
	return "NOM_PB_OK_SCE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (16/06/05 09:49:12)
 * @author : Générateur de process
 */
public boolean performPB_OK_SCE(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_SERVICE_SELECT()) ? Integer.parseInt(getVAL_LB_SERVICE_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un service");
		return false;
	}
	Service monService = (Service)getListeServiceEpuree().get(indice);
	if (getTransaction().isErreur()){
		return false;
	}
	
	setServiceCourant(monService);
	//on recherche les équipements qui sont affectés actuellement au service sélectionner
	ArrayList<AffectationServiceInfos> resultatAI = AffectationServiceInfos.chercherAffectationServiceInfosService(getTransaction(),serviceCourant.getServi());
	setListeAffectationService(resultatAI);
	// on recherche les objets de type equipementInfos correspondants
	if (resultatAI.size()>0){
//		Si au moins un affectationInfos
		if (resultatAI.size() !=0 ) {
			int tailles [] = {10,15,15,15,5,10};
			String [] padding = {"D","D","G","G","G","C"};
			FormateListe aFormat = new FormateListe(tailles,padding, true);
					
			for (ListIterator<AffectationServiceInfos> list = resultatAI.listIterator(); list.hasNext(); ) {
				AffectationServiceInfos monAI = (AffectationServiceInfos)list.next();
				EquipementInfos aEquipementInfos = EquipementInfos.chercherEquipementInfos(getTransaction(),monAI.getNumeroinventaire());
				if (getTransaction().isErreur()){
					return false;
				}
				String ligne [] = { aEquipementInfos.getNumeroinventaire(),aEquipementInfos.getNumeroimmatriculation(),aEquipementInfos.getDesignationmarque(),aEquipementInfos.getDesignationmodele(),aEquipementInfos.getDesignationtypeequip(),aEquipementInfos.getDatemiseencirculation()};
				aFormat.ajouteLigne(ligne);
			}
			setLB_EQUIPEMENT(aFormat.getListeFormatee());
		}else{
			setLB_EQUIPEMENT(LBVide);
		}
	}
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHER
 * Date de création : (16/06/05 09:49:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_RECHERCHER() {
	return "NOM_PB_RECHERCHER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (16/06/05 09:49:12)
 * @author : Générateur de process
 */
public boolean performPB_RECHERCHER(javax.servlet.http.HttpServletRequest request) throws Exception {
	String param = getZone(getNOM_EF_RECHERCHER());
	ArrayList<Service> resultat = Service.chercherListServiceEquip(getTransaction(),param);
	// on remplit la liste des équipements
	setListeService(resultat);
	ArrayList<Service> resultatEpure = new ArrayList<Service>();
	int i = 0;
	if(resultat.size()>0){
		//les élèments de la liste 
		int [] tailles = {6,60};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G","G"};
		FormateListe aFormat = new FormateListe(tailles,padding, true);
		for (ListIterator<Service> list = resultat.listIterator(); list.hasNext(); ) {
			Service aService = (Service)list.next();
			// on teste si le code service a 4 caractères si oui on regarde s'il se termine par un 0
			String ligne[] = {aService.getServi(), aService.getLiserv()};
			/*if (codeservice==4){
				servi =aService.getServi().trim().substring(3,4);
				if ("0".equals(servi)){*/
					aFormat.ajouteLigne(ligne);
					resultatEpure.add(i,aService);
					i = i+1;
				/*}
			}else{
				aFormat.ajouteLigne(ligne);
				resultatEpure.add(i,aService);
				i = i+1;
			}*/
		}

		setLB_SERVICE(aFormat.getListeFormatee());
	}else{
		setLB_SERVICE(LBVide);
	}
	setListeServiceEpuree(resultatEpure);
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_RECHERCHER
 * Date de création : (16/06/05 09:49:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_RECHERCHER() {
	return "NOM_EF_RECHERCHER";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_RECHERCHER
 * Date de création : (16/06/05 09:49:12)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_RECHERCHER() {
	return getZone(getNOM_EF_RECHERCHER());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_EQUIPEMENT
 * Date de création : (16/06/05 09:49:12)
 * @author : Générateur de process
 */
private String [] getLB_EQUIPEMENT() {
	if (LB_EQUIPEMENT == null)
		LB_EQUIPEMENT = initialiseLazyLB();
	return LB_EQUIPEMENT;
}
/**
 * Setter de la liste:
 * LB_EQUIPEMENT
 * Date de création : (16/06/05 09:49:12)
 * @author : Générateur de process
 */
private void setLB_EQUIPEMENT(java.lang.String[] newLB_EQUIPEMENT) {
	LB_EQUIPEMENT = newLB_EQUIPEMENT;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_EQUIPEMENT
 * Date de création : (16/06/05 09:49:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_EQUIPEMENT() {
	return "NOM_LB_EQUIPEMENT";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_EQUIPEMENT_SELECT
 * Date de création : (16/06/05 09:49:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_EQUIPEMENT_SELECT() {
	return "NOM_LB_EQUIPEMENT_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_EQUIPEMENT
 * Date de création : (16/06/05 09:49:12)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_EQUIPEMENT() {
	return getLB_EQUIPEMENT();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_EQUIPEMENT
 * Date de création : (16/06/05 09:49:12)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_EQUIPEMENT_SELECT() {
	return getZone(getNOM_LB_EQUIPEMENT_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_SERVICE
 * Date de création : (16/06/05 09:49:12)
 * @author : Générateur de process
 */
private String [] getLB_SERVICE() {
	if (LB_SERVICE == null)
		LB_SERVICE = initialiseLazyLB();
	return LB_SERVICE;
}
/**
 * Setter de la liste:
 * LB_SERVICE
 * Date de création : (16/06/05 09:49:12)
 * @author : Générateur de process
 */
private void setLB_SERVICE(java.lang.String[] newLB_SERVICE) {
	LB_SERVICE = newLB_SERVICE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_SERVICE
 * Date de création : (16/06/05 09:49:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_SERVICE() {
	return "NOM_LB_SERVICE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_SERVICE_SELECT
 * Date de création : (16/06/05 09:49:12)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_SERVICE_SELECT() {
	return "NOM_LB_SERVICE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_SERVICE
 * Date de création : (16/06/05 09:49:12)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_SERVICE() {
	return getLB_SERVICE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_SERVICE
 * Date de création : (16/06/05 09:49:12)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_SERVICE_SELECT() {
	return getZone(getNOM_LB_SERVICE_SELECT());
}
	/**
	 * @return Renvoie listeService.
	 */
	public ArrayList<Service> getListeService() {
		return listeService;
	}
	/**
	 * @param listeService listeService à définir.
	 */
	public void setListeService(ArrayList<Service> listeService) {
		this.listeService = listeService;
	}
	/**
	 * @return Renvoie serviceCourant.
	 */
	public Service getServiceCourant() {
		return serviceCourant;
	}
	/**
	 * @param serviceCourant serviceCourant à définir.
	 */
	public void setServiceCourant(Service serviceCourant) {
		this.serviceCourant = serviceCourant;
	}
	
	/**
	 * @param focus focus à définir.
	 */
	public String getDefaultFocus() {
		return getNOM_EF_RECHERCHER();
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
	 * @return Renvoie equipementInfosCourant.
	 */
	public EquipementInfos getEquipementInfosCourant() {
		return equipementInfosCourant;
	}
	/**
	 * @param equipementInfosCourant equipementInfosCourant à définir.
	 */
	public void setEquipementInfosCourant(EquipementInfos equipementInfosCourant) {
		this.equipementInfosCourant = equipementInfosCourant;
	}
	public ArrayList<AffectationServiceInfos> getListeAffectationService() {
		return listeAffectationService;
	}
	public void setListeAffectationService(ArrayList<AffectationServiceInfos> listeAffectationService) {
		this.listeAffectationService = listeAffectationService;
	}
	public ArrayList<Service> getListeServiceEpuree() {
		return listeServiceEpuree;
	}
	public void setListeServiceEpuree(ArrayList<Service> listeServiceEpuree) {
		this.listeServiceEpuree = listeServiceEpuree;
	}
}
