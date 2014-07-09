package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.Specialite;
import nc.mairie.technique.*;
/**
 * Process OeSpecialite
 * Date de création : (01/06/05 07:20:28)
 * @author : Générateur de process
*/
public class OeSpecialite extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7675116543533288085L;
	private java.lang.String[] LB_SPECIALITE;
	private String ACTION_SUPPRESSION = "Suppression d'une spécialité.<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private String ACTION_MODIFICATION = "Modification d'une spécialité.";
	private String ACTION_CREATION = "Création d'une spécialité.";
	private ArrayList<Specialite> listeSpecialite = null;
	private Specialite specialiteCourant;
	private String focus = null;
	public int isVide = 0;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (01/06/05 07:20:28)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{

	//	Si liste des mode de prise de carburant est vide
	if (getLB_SPECIALITE() == LBVide) {
		
		ArrayList<Specialite> a = Specialite.listerSpecialite(getTransaction());
		setListeSpecialite(a);
		
		if (a.size()>0){
			//les élèments de la liste
			int [] tailles = {30};
			String [] champs = {"libellespe"};
			boolean[] colonnes = {true};
			a = Services.trier(a,champs,colonnes);
			setListeSpecialite(a);
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"G"};
			
			setLB_SPECIALITE(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
		}else{
			setLB_SPECIALITE(null);
		}
		setIsVide(a.size());
	}
//	 on sélectionne l'élément en cours
	if(getSpecialiteCourant()!=null){
		if(getSpecialiteCourant().getCodespecialite()!=null){
			addZone(getNOM_LB_SPECIALITE_SELECT(),String.valueOf(-1));
			for (int i = 0; i < getListeSpecialite().size(); i++) {
				Specialite uneSpe = (Specialite)getListeSpecialite().get(i);
				if (uneSpe.getCodespecialite().equals(getSpecialiteCourant().getCodespecialite())) {
					addZone(getNOM_LB_SPECIALITE_SELECT(),String.valueOf(i));
					break;
				}
			}	
		}
	}
}

/**
 * Constructeur du process OeSpecialite.
 * Date de création : (01/06/05 07:20:28)
 * @author : Générateur de process
 */
public OeSpecialite() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (01/06/05 07:20:28)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_AJOUTER() {
	return "NOM_PB_AJOUTER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (01/06/05 07:20:28)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {

	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
	
	//On vide la zone de saisie
	addZone(getNOM_EF_DESIGNATION(),"");

	setStatut(STATUT_MEME_PROCESS);
	setFocus(getNOM_EF_DESIGNATION());
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (01/06/05 07:20:28)
 * @author : Générateur de process
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
 * Date de création : (01/06/05 07:20:28)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {

	if ("".equals(getVAL_ST_TITRE_ACTION())) {
		setStatut(STATUT_PROCESS_APPELANT);
	} else {
		//initialiseListeModePrise(request);
		setLB_SPECIALITE(LBVide);
	}
	// on vide les zones
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_ST_DESIGNATION(),"");
	addZone(getNOM_EF_DESIGNATION(),"");
	setFocus(null);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (01/06/05 07:20:28)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_MODIFIER() {
	return "NOM_PB_MODIFIER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (01/06/05 07:20:28)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {

//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_SPECIALITE_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_SPECIALITE_SELECT())) : -1);
	if (numligne == -1 || getListeSpecialite().size() == 0 || numligne > getListeSpecialite().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Spécialité"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);

	//Récup du la marque courante
	Specialite uneSpecialite = (Specialite)getListeSpecialite().get(numligne);
	setSpecialiteCourant(uneSpecialite);

	//Alim zones
	addZone(getNOM_EF_DESIGNATION(), uneSpecialite.getLibellespe());
	
	setStatut(STATUT_MEME_PROCESS);
	setFocus(getNOM_EF_DESIGNATION());
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPRIMER
 * Date de création : (01/06/05 07:20:28)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_SUPPRIMER() {
	return "NOM_PB_SUPPRIMER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (01/06/05 07:20:28)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_SUPPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_SPECIALITE_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_SPECIALITE_SELECT())) : -1);
	if (numligne == -1 || getListeSpecialite().size() == 0 || numligne > getListeSpecialite().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Spécialité"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);

	//Récup de la marque courante
	Specialite uneSpecialite = (Specialite)getListeSpecialite().get(numligne);
	setSpecialiteCourant(uneSpecialite);

	//Alim zones
	addZone(getNOM_ST_DESIGNATION(), uneSpecialite.getLibellespe());
	addZone(getNOM_EF_DESIGNATION(), uneSpecialite.getLibellespe());
	
	setStatut(STATUT_MEME_PROCESS);	
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (01/06/05 07:20:28)
 * @author : Générateur de process
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
 * Date de création : (01/06/05 07:20:28)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {

//	Si aucune action en cours
	if (getZone(getNOM_ST_TITRE_ACTION()).length() == 0) {
		setStatut(STATUT_MEME_PROCESS, true, MairieMessages.getMessage("ERR996"));//"Vous ne pouvez pas valider, il n'y a pas d'action en cours."
		return false;
	}

	//Récup des zones saisies
	String newDesignation = getZone(getNOM_EF_DESIGNATION()).toUpperCase();
	
	//Si Action Suppression
	if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_SUPPRESSION)) {

		//Test si un des champs a été modifié
		if (! newDesignation.equals(getSpecialiteCourant().getLibellespe().trim())) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR995"));//"ERR995","En suppression, aucune zone n'est modifiable."
			return false;
		}
		
		//Suppression
		try{
			getSpecialiteCourant().supprimerSpecialite(getTransaction());
		}catch(Exception e){
			getTransaction().declarerErreur(" La suppression n'est pas possible.");
		}
		
			
	//Si Action Modification
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_MODIFICATION)) {

		//Si lib contact non saisit
		if (newDesignation.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","de la spécialité"));//"ERR008","Le libellé @ est obligatoire"
			setFocus(getNOM_EF_DESIGNATION());
			return false;
		}
		
		//Affectation des attributs
		getSpecialiteCourant().setLibellespe(newDesignation);
		
		//Modification
		getSpecialiteCourant().modifierSpecialite(getTransaction(),newDesignation);
		if (getTransaction().isErreur())
			return false;
			
	//Si Action Creation		
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_CREATION)) {

		//Si lib contact non saisit
		if (newDesignation.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","de la spécialité"));//"ERR008","Le libellé @ est obligatoire"
			setFocus(getNOM_EF_DESIGNATION());
			return false;
		}
		
		setSpecialiteCourant(new Specialite());
		
		//Affectation des attributs
		getSpecialiteCourant().setLibellespe(newDesignation);

		//Création
		getSpecialiteCourant().creerSpecialite(getTransaction(),newDesignation);
		if (getTransaction().isErreur())
			return false;
	}

	//Tout s'est bien passé
	commitTransaction();
	//initialiseListeModePrise(request);
	setLB_SPECIALITE(LBVide);
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_ST_DESIGNATION(),"");
	addZone(getNOM_EF_DESIGNATION(),"");
	setFocus(null);
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (01/06/05 07:20:28)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (01/06/05 07:20:28)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DESIGNATION
 * Date de création : (01/06/05 07:20:28)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_DESIGNATION() {
	return "NOM_EF_DESIGNATION";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DESIGNATION
 * Date de création : (01/06/05 07:20:28)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_DESIGNATION() {
	return getZone(getNOM_EF_DESIGNATION());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_MODEPRISE
 * Date de création : (01/06/05 07:20:28)
 * @author : Générateur de process
 */
private String [] getLB_SPECIALITE() {
	if (LB_SPECIALITE == null)
		LB_SPECIALITE = initialiseLazyLB();
	return LB_SPECIALITE;
}
/**
 * Setter de la liste:
 * LB_MODEPRISE
 * Date de création : (01/06/05 07:20:28)
 * @author : Générateur de process
 */
private void setLB_SPECIALITE(java.lang.String[] newLB_SPECIALITE) {
	LB_SPECIALITE = newLB_SPECIALITE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_MODEPRISE
 * Date de création : (01/06/05 07:20:28)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_SPECIALITE() {
	return "NOM_LB_SPECIALITE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_MODEPRISE_SELECT
 * Date de création : (01/06/05 07:20:28)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_SPECIALITE_SELECT() {
	return "NOM_LB_SPECIALITE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_MODEPRISE
 * Date de création : (01/06/05 07:20:28)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_SPECIALITE() {
	return getLB_SPECIALITE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_MODEPRISE
 * Date de création : (01/06/05 07:20:28)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_SPECIALITE_SELECT() {
	return getZone(getNOM_LB_SPECIALITE_SELECT());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (01/06/05 07:22:23)
 * @author : Générateur de process
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
 * Date de création : (01/06/05 07:22:23)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_SPECIALITE_SELECT()) ? Integer.parseInt(getVAL_LB_SPECIALITE_SELECT()): -1); 
		
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	
	Specialite uneSpecialite = (Specialite)getListeSpecialite().get(indice);
	setSpecialiteCourant(uneSpecialite);
	
	addZone(getNOM_EF_DESIGNATION(), uneSpecialite.getLibellespe());
	
	return true;
}
	/**
	 * @return Renvoie listeModePrise.
	 */
	
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
		return getNOM_LB_SPECIALITE();
	}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (01/06/05 07:20:28)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_OK
		if (testerParametre(request, getNOM_PB_OK())) {
			return performPB_OK(request);
		}
		//Si clic sur le bouton PB_AJOUTER
		if (testerParametre(request, getNOM_PB_AJOUTER())) {
			return performPB_AJOUTER(request);
		}

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_MODIFIER
		if (testerParametre(request, getNOM_PB_MODIFIER())) {
			return performPB_MODIFIER(request);
		}

		//Si clic sur le bouton PB_SUPPRIMER
		if (testerParametre(request, getNOM_PB_SUPPRIMER())) {
			return performPB_SUPPRIMER(request);
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
 * Date de création : (14/06/05 13:10:06)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeSpecialite.jsp";
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DESIGNATION
 * Date de création : (14/06/05 13:10:06)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DESIGNATION() {
	return "NOM_ST_DESIGNATION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DESIGNATION
 * Date de création : (14/06/05 13:10:06)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DESIGNATION() {
	return getZone(getNOM_ST_DESIGNATION());
}
	public int getIsVide() {
		return isVide;
	}
	public void setIsVide(int isVide) {
		this.isVide = isVide;
	}
	public ArrayList<Specialite> getListeSpecialite() {
		return listeSpecialite;
	}
	public void setListeSpecialite(ArrayList<Specialite> listeSpecialite) {
		this.listeSpecialite = listeSpecialite;
	}
	public Specialite getSpecialiteCourant() {
		return specialiteCourant;
	}
	public void setSpecialiteCourant(Specialite specialiteCourant) {
		this.specialiteCourant = specialiteCourant;
	}
}
