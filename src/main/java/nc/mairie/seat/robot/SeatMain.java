package nc.mairie.seat.robot;

/**
 * Insérez la description du type ici.
 * Date de création : (10/01/2003 09:39:40)
 * author: 
 */
public class SeatMain extends nc.mairie.technique.BasicProcess {
/**
	 * 
	 */
	private static final long serialVersionUID = -5646570683102644465L;
/**
 * Commentaire relatif au constructeur DefaultProcess.
 */
public SeatMain() {
	super();
}
/**
	Retourne le nom de la JSP du process
	Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 */
@Override
public String getJSP() {
	return "SeatMain.jsp";
}
/**
	Initialisation des zones à afficher dans le JSP
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception {}
/**
 * Process incoming requests for information
 * 
 * @param request Object that encapsulates the request to the servlet 
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception {
	return false;
}
}
