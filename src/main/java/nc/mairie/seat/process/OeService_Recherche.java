package nc.mairie.seat.process;

import java.util.ArrayList;
import java.util.ListIterator;

import nc.mairie.seat.metier.Service;
import nc.mairie.technique.*;
/**
 * Process OeService_Recherche
 * Date de création : (13/06/05 16:05:17)
 * author : Générateur de process
*/
public class OeService_Recherche extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4383240057481922979L;
	private java.lang.String[] LB_SERVICE;
	private ArrayList<Service> listeService;
	private ArrayList<Service> listeServiceEpuree;
	private String focus = null;
	private Service serviceCourant;
	private boolean first = true;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (13/06/05 16:05:17)
 * author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	if(first){
		String param = (String)VariableActivite.recuperer(this,"PARAM");
		addZone(getNOM_EF_RECHERCHE(),param);
	}
	performPB_RECHERCHE(request);
	setFirst(false);
}
/**
 * Constructeur du process OeService_Recherche.
 * Date de création : (13/06/05 16:05:17)
 * author : Générateur de process
 */
public OeService_Recherche() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (13/06/05 16:05:17)
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
 * Date de création : (13/06/05 16:05:17)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (13/06/05 16:05:17)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_OK() {
	return "NOM_PB_OK";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (13/06/05 16:05:17)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE
 * Date de création : (13/06/05 16:05:17)
 * author : Générateur de process
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
 * Date de création : (13/06/05 16:05:17)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_RECHERCHE(javax.servlet.http.HttpServletRequest request) throws Exception {
	String param = getZone(getNOM_EF_RECHERCHE());
	ArrayList<Service> resultat = Service.chercherListServiceTous(getTransaction(),param);
	if(getTransaction().isErreur()){
		return false;
	}
	// on remplit la liste des équipements
	//setListeService(new ArrayList());
	setListeService(resultat);
	ArrayList<Service> resultatEpure = new ArrayList<Service>();
	int i = 0;
	if(resultat.size()>0){
		//les élèments de la liste 
		int [] tailles = {17,120};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G","G"};
		FormateListe aFormat = new FormateListe(tailles,padding, false);
		for (ListIterator<Service> list = resultat.listIterator(); list.hasNext(); ) {
			Service aService = (Service)list.next();
			// on teste si le code service a 4 caractères si oui on regarde s'il se termine par un 0
			/*int codeservice = aService.getServi().trim().length();
			String servi =aService.getServi().trim();*/
			String ligne[] = { aService.getServi(),aService.getLiserv()};
			/*if (codeservice==4){
				servi =aService.getServi().trim().substring(3,4);
				//listeServiceEpuree.add(aService);
				if ("0".equals(servi)){*/
			if (!aService.getLiserv().trim().equals("")){
				aFormat.ajouteLigne(ligne);
				resultatEpure.add(i,aService);
				i = i+1;
			}
			/*}else{
				//aFormat.ajouteLigne(ligne);
				//resultatEpure.add(i,aService);
				//i = i+1;
				//listeServiceEpuree.add(aService);
			}*/
		}
		setLB_SERVICE(aFormat.getListeFormatee());
	}else{
		setLB_SERVICE(LBVide);
	}
	setListeServiceEpuree(resultatEpure);
	if(listeServiceEpuree.size()==1){
		addZone(getNOM_LB_SERVICE_SELECT(), "0");
		performPB_VALIDER(request);
	}
	return true;
}

/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_RECHERCHE
 * Date de création : (13/06/05 16:05:17)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_RECHERCHE() {
	return "NOM_EF_RECHERCHE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_RECHERCHE
 * Date de création : (13/06/05 16:05:17)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_RECHERCHE() {
	return getZone(getNOM_EF_RECHERCHE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_SERVICE
 * Date de création : (13/06/05 16:05:17)
 * author : Générateur de process
 */
private String [] getLB_SERVICE() {
	if (LB_SERVICE == null)
		LB_SERVICE = initialiseLazyLB();
	return LB_SERVICE;
}
/**
 * Setter de la liste:
 * LB_SERVICE
 * Date de création : (13/06/05 16:05:17)
 * author : Générateur de process
 */
private void setLB_SERVICE(java.lang.String[] newLB_SERVICE) {
	LB_SERVICE = newLB_SERVICE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_SERVICE
 * Date de création : (13/06/05 16:05:17)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_SERVICE() {
	return "NOM_LB_SERVICE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_SERVICE_SELECT
 * Date de création : (13/06/05 16:05:17)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_SERVICE_SELECT() {
	return "NOM_LB_SERVICE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_SERVICE
 * Date de création : (13/06/05 16:05:17)
 * author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_SERVICE() {
	return getLB_SERVICE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_SERVICE
 * Date de création : (13/06/05 16:05:17)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_SERVICE_SELECT() {
	return getZone(getNOM_LB_SERVICE_SELECT());
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (13/06/05 16:05:17)
 * author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_VALIDER
		if (testerParametre(request, getNOM_PB_VALIDER())) {
			return performPB_VALIDER(request);
		}

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_OK
		if (testerParametre(request, getNOM_PB_OK())) {
			return performPB_OK(request);
		}

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
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (13/06/05 16:07:22)
 * author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeService_Recherche.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (13/06/05 16:07:22)
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
 * Date de création : (13/06/05 16:07:22)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_SERVICE_SELECT()) ? Integer.parseInt(getVAL_LB_SERVICE_SELECT()): -1); 
	if (indice == -1) {
		if(getListeServiceEpuree().size()==1){
			indice = 0;
		}else{
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
		}
	}
	Service monService = (Service)getListeServiceEpuree().get(indice);
//	if (getTransaction().isErreur()){
//		return false;
//	}
	setServiceCourant(monService);
	
//	On met la variable activité
	VariableGlobale.ajouter(request, "SERVICE", getServiceCourant());
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}

/**
 * focus focus à définir.
 * @return focus
 */
public String getDefaultFocus() {
	return getNOM_EF_RECHERCHE();
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
	public ArrayList<Service> getListeServiceEpuree() {
		return listeServiceEpuree;
	}
	public void setListeServiceEpuree(ArrayList<Service> listeServiceEpuree) {
		this.listeServiceEpuree = listeServiceEpuree;
	}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
}
