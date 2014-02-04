package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.commun.technique.ListeVariableGlobale;
import nc.mairie.seat.metier.Service;
import nc.mairie.technique.*;
/**
 * Process SeatIndex
 * Date de création : (24/08/07 07:53:07)
 * @author : Générateur de process
*/
public class SeatIndex extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3971907125336836951L;
	public String dpt = "toto";
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (24/08/07 07:53:07)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	UserAppli aUser = (UserAppli)nc.mairie.technique.VariableGlobale.recuperer(request,ListeVariableGlobale.GLOBAL_USER_APPLI);
	java.util.Hashtable<Object, Object> h = nc.mairie.technique.MairieLDAP.chercherUserLDAPAttributs(aUser.getUserName());
	dpt = (String)h.get("department");
	
	ArrayList<Service> listService = Service.chercherListServiceAccro(getTransaction(),dpt);
	if(getTransaction().isErreur()){
		return;
	}
	if(listService.size()>0){
		Service unService = (Service)listService.get(0);
		VariableGlobale.ajouter(request,"SERVICE",unService);
	}

}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (24/08/07 07:53:07)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Constructeur du process SeatIndex.
 * Date de création : (24/08/07 07:53:07)
 * @author : Générateur de process
 */
public SeatIndex() {
	super();
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (24/08/07 07:53:07)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "SeatIndex.jsp";
}
}
