package nc.mairie.seat.process;

import java.util.ArrayList;
import nc.mairie.seat.metier.Entretien;
import nc.mairie.technique.*;
/**
 * Process OeEntretien
 * Date de création : (13/05/05 09:16:32)
 * author : Générateur de process
*/
public class OeEntretien extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8201743940390410896L;
	private String ACTION_SUPPRESSION = "Suppression d'un entretien.<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private String ACTION_MODIFICATION = "Modification d'un entretien.";
	private String ACTION_CREATION = "Création d'un entretien.";
	private ArrayList<Entretien> listeEntretien = null;
	private Entretien entretienCourant;
	private java.lang.String[] LB_ENTRETIEN;
	public int isVide = 0;
	private String focus = null;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (13/05/05 09:16:32)
 * author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{

//	Si liste des entretiens est vide
	if (getLB_ENTRETIEN() == LBVide) {
		
		ArrayList<Entretien> a = Entretien.listerEntretien(getTransaction());
		setListeEntretien(a);
		
		if (a.size()>0){
			//les élèments de la liste seront le codemarque pour pouvoir récupérer le dernier élément et les dimensions des marques.
			int [] tailles = {30};
			String [] champs = {"libelleentretien"};
			boolean[] colonnes = {true};
			a = Services.trier(a,champs,colonnes);
			setListeEntretien(a);
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"G"};
			
			setLB_ENTRETIEN(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
		}else{
			setLB_ENTRETIEN(null);
		}
		setIsVide(a.size());
	}
//	 on sélectionne l'élément en cours
	if(getEntretienCourant()!=null){
		if(getEntretienCourant().getCodeentretien()!=null){
			addZone(getNOM_LB_ENTRETIEN_SELECT(),String.valueOf(-1));
			for (int i = 0; i < getListeEntretien().size(); i++) {
				Entretien unEntretien = getListeEntretien().get(i);
				if (unEntretien.getCodeentretien().equals(getEntretienCourant().getCodeentretien())) {
					addZone(getNOM_LB_ENTRETIEN_SELECT(),String.valueOf(i));
					break;
				}
			}	
		}
	}
	//	on initialise la liste des types d'intervalle
	//hashTypeIntervalle = new Hashtable();
	//hashTypeIntervalle.put("Km","0");
	//hashTypeIntervalle.put("Heure","1");
	//hashTypeIntervalle.put("Date","2");

}


/**
 * Constructeur du process OeEntretien.
 * Date de création : (13/05/05 09:16:32)
 * author : Générateur de process
 */
public OeEntretien() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (13/05/05 09:16:32)
 * author : Générateur de process
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
 * Date de création : (13/05/05 09:16:32)
 * author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
	
	//On vide la zone de saisie
	addZone(getNOM_EF_DESIGNATION(),"");
	setFocus(getNOM_EF_DESIGNATION());
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (13/05/05 09:16:32)
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
 * Date de création : (13/05/05 09:16:32)
 * author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {

	if ("".equals(getVAL_ST_TITRE_ACTION())) {
		setStatut(STATUT_PROCESS_APPELANT);
	} else {
		setLB_ENTRETIEN(LBVide);
	}
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_ST_DESIGNATION(),"");
	addZone(getNOM_EF_DESIGNATION(),"");
	setFocus(null);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (13/05/05 09:16:32)
 * author : Générateur de process
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
 * Date de création : (13/05/05 09:16:32)
 * author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {

//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_ENTRETIEN_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_ENTRETIEN_SELECT())) : -1);
	if (numligne == -1 || getListeEntretien().size() == 0 || numligne > getListeEntretien().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Entretiens"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);

	//Récup de l'entretien courant
	Entretien entretien = getListeEntretien().get(numligne);
	setEntretienCourant(entretien);

	//Alim zones
	//int ligneType = getListeEntretien().indexOf(getHashEntretien().get(entretien.getKmhdate()));
	addZone(getNOM_EF_DESIGNATION(), entretien.getLibelleentretien());
	setFocus(getNOM_EF_DESIGNATION());
	setStatut(STATUT_MEME_PROCESS);	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (13/05/05 09:16:32)
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
 * Date de création : (13/05/05 09:16:32)
 * author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_ENTRETIEN_SELECT()) ? Integer.parseInt(getVAL_LB_ENTRETIEN_SELECT()): -1); 
		
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	
	Entretien monEntretien = getListeEntretien().get(indice);
	setEntretienCourant(monEntretien);
	
	addZone(getNOM_EF_DESIGNATION(), monEntretien.getLibelleentretien());
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPRIMER
 * Date de création : (13/05/05 09:16:32)
 * author : Générateur de process
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
 * Date de création : (13/05/05 09:16:32)
 * author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_SUPPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {

	//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_ENTRETIEN_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_ENTRETIEN_SELECT())) : -1);
	if (numligne == -1 || getListeEntretien().size() == 0 || numligne > getListeEntretien().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Entretiens"));
		return false;
	}
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);

	//Récup de l'entretien courant
	Entretien entretien = getListeEntretien().get(numligne);
	setEntretienCourant(entretien);

	//Alim zones
	addZone(getNOM_ST_DESIGNATION(), entretien.getLibelleentretien());
	
	setStatut(STATUT_MEME_PROCESS);	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (13/05/05 09:16:32)
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
 * Date de création : (13/05/05 09:16:32)
 * author : Générateur de process
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
	String newEntretien = getZone(getNOM_EF_DESIGNATION()).toUpperCase();
	
	//Si Action Suppression
	if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_SUPPRESSION)) {

		//Test si un des champs a été modifié
		/*if (! newEntretien.equals(getEntretienCourant().getLibelleentretien().trim())){
				getTransaction().declarerErreur(MairieMessages.getMessage("ERR995"));//"ERR995","En suppression, aucune zone n'est modifiable."
			return false;
		}*/
		
		//	Suppression
		try{
			getEntretienCourant().supprimerEntretien(getTransaction());
		}catch(Exception e){
			getTransaction().declarerErreur(" La suppression n'est pas possible.");
		}
		
		//if (getTransaction().isErreur())
			//return false;
			
	//Si Action Modification
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_MODIFICATION)) {

		// Pour les champs obligatoires
		//Si lib entretien non saisit
		if (newEntretien.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","de l'entretien"));//"ERR008","Le libellé @ est obligatoire"
			return false;
		}
		
		//Affectation des attributs
		getEntretienCourant().setLibelleentretien(newEntretien);
		
		//Modification
		getEntretienCourant().modifierEntretien(getTransaction(),newEntretien);
		if (getTransaction().isErreur())
			return false;
			
	//Si Action Creation		
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_CREATION)) {

//		 Pour les champs obligatoires
		//Si lib entretien non saisit
		if (newEntretien.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","de l'entretien"));//"ERR008","Le libellé @ est obligatoire"
			return false;
		}
		
		setEntretienCourant(new Entretien());
		
		//Affectation des attributs
		getEntretienCourant().setLibelleentretien(newEntretien);

		//Création
		getEntretienCourant().creerEntretien(getTransaction(),newEntretien);
		if (getTransaction().isErreur())
			return false;
		
	}

	//Tout s'est bien passé
	commitTransaction();
	// on vide les zones
	addZone(getNOM_EF_DESIGNATION(),"");
	addZone(getNOM_ST_DESIGNATION(),"");
	addZone(getNOM_ST_TITRE_ACTION(),"");
	setLB_ENTRETIEN(LBVide);
	setFocus(null);
	return true;
}

/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (13/05/05 09:23:11)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (13/05/05 09:23:11)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
	
/**
 * Getter de la liste avec un lazy initialize :
 * LB_ENTRETIEN
 * Date de création : (13/05/05 09:24:27)
 * author : Générateur de process
 */
private String [] getLB_ENTRETIEN() {
	if (LB_ENTRETIEN == null)
		LB_ENTRETIEN = initialiseLazyLB();
	return LB_ENTRETIEN;
}
/**
 * Setter de la liste:
 * LB_ENTRETIEN
 * Date de création : (13/05/05 09:24:27)
 * author : Générateur de process
 */
private void setLB_ENTRETIEN(java.lang.String[] newLB_ENTRETIEN) {
	LB_ENTRETIEN = newLB_ENTRETIEN;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_ENTRETIEN
 * Date de création : (13/05/05 09:24:27)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_ENTRETIEN() {
	return "NOM_LB_ENTRETIEN";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_ENTRETIEN_SELECT
 * Date de création : (13/05/05 09:24:27)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_ENTRETIEN_SELECT() {
	return "NOM_LB_ENTRETIEN_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_ENTRETIEN
 * Date de création : (13/05/05 09:24:27)
 * author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_ENTRETIEN() {
	return getLB_ENTRETIEN();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_ENTRETIEN
 * Date de création : (13/05/05 09:24:27)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_ENTRETIEN_SELECT() {
	return getZone(getNOM_LB_ENTRETIEN_SELECT());
}
	/**
	 * @return Renvoie entretienCourant.
	 */
	private Entretien getEntretienCourant() {
		return entretienCourant;
	}
	/**
	 * @param entretienCourant entretienCourant à définir.
	 */
	private void setEntretienCourant(Entretien entretienCourant) {
		this.entretienCourant = entretienCourant;
	}
	/**
	 * @return Renvoie listeEntretien.
	 */
	private ArrayList<Entretien> getListeEntretien() {
		return listeEntretien;
	}
	/**
	 * @param listeEntretien listeEntretien à définir.
	 */
	private void setListeEntretien(ArrayList<Entretien> listeEntretien) {
		this.listeEntretien = listeEntretien;
	}

	public int getIsVide() {
		return isVide;
	}
	public void setIsVide(int isVide) {
		this.isVide = isVide;
	}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (13/05/05 09:16:32)
 * author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

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

		//Si clic sur le bouton PB_OK
		if (testerParametre(request, getNOM_PB_OK())) {
			return performPB_OK(request);
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
 * Date de création : (28/06/05 11:52:26)
 * author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeEntretien.jsp";
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DESIGNATION
 * Date de création : (28/06/05 11:52:26)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DESIGNATION() {
	return "NOM_ST_DESIGNATION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DESIGNATION
 * Date de création : (28/06/05 11:52:26)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DESIGNATION() {
	return getZone(getNOM_ST_DESIGNATION());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DESIGNATION
 * Date de création : (28/06/05 11:52:26)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_DESIGNATION() {
	return "NOM_EF_DESIGNATION";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DESIGNATION
 * Date de création : (28/06/05 11:52:26)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_DESIGNATION() {
	return getZone(getNOM_EF_DESIGNATION());
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
	return getNOM_LB_ENTRETIEN();
}

}
