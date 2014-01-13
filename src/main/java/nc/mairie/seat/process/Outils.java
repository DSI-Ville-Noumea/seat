package nc.mairie.seat.process;
/**
 * Process OeAccueil
 * Date de création : (28/04/05 13:36:04)
 * @author : Générateur de process
*/
public class Outils {

/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (28/04/05 13:36:04)
 * @author : Générateur de process
 */
public static String enleveEspace(String uneChaine) throws Exception{

	StringBuffer sb = new StringBuffer();
	
	for (int i=0;i<uneChaine.length();i++){
		if(!String.valueOf(uneChaine.charAt(i)).equals(" ")){
			sb.append(uneChaine.charAt(i));
		}
	};
	
	return sb.toString();
}
/**
 * Constructeur du process OeAccueil.
 * Date de création : (28/04/05 13:36:04)
 * @author : Générateur de process
 */
public Outils() {
	super();
}
}

