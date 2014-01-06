package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.TIntervalle;
import nc.mairie.technique.*;
/**
 * Process OeTIntervalle
 * Date de création : (20/05/05 07:31:26)
 * @author : Générateur de process
*/
public class OeTIntervalle extends nc.mairie.technique.BasicProcess {
	private java.lang.String[] LB_LIBELLE;
	private String ACTION_SUPPRESSION = "Suppression d'un type d'intervalle.<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private String ACTION_MODIFICATION = "Modification du nom d'un type d'intervalle.";
	private String ACTION_CREATION = "Création d'un type d'intervalle.";
	private ArrayList listeTIntervalle = null;
	private TIntervalle tintervalleCourant;
	private String focus = null;
	public int isVide = 0;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (20/05/05 07:31:26)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{

	//	Si liste des types d'intervalle est vide
	if (getLB_LIBELLE() == LBVide) {
		java.util.ArrayList a = TIntervalle.listerTIntervalle(getTransaction());
		setListeTIntervalle(a);
		
		if (a.size()>0){
			//les élèments de la liste
			int [] tailles = {20};
			String [] champs = {"designation"};
			boolean[] colonnes = {true};
			a = Services.trier(a,champs,colonnes);
			setListeTIntervalle(a);
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"G"};
			
			FormateListe f = new FormateListe(tailles,a,champs,padding,false);
			String [] l = f.getListeFormatee();
			setLB_LIBELLE(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
		}else{
			setLB_LIBELLE(null);
		}
		setIsVide(a.size());
	}
//	 on sélectionne l'élément en cours
	if(getTintervalleCourant()!=null){
		if(getTintervalleCourant().getCodeti()!=null){
			int position = -1;
			addZone(getNOM_LB_LIBELLE_SELECT(),String.valueOf(-1));
			for (int i = 0; i < getListeTIntervalle().size(); i++) {
				TIntervalle unTi = (TIntervalle)getListeTIntervalle().get(i);
				if (unTi.getCodeti().equals(getTintervalleCourant().getCodeti())) {
					addZone(getNOM_LB_LIBELLE_SELECT(),String.valueOf(i));
					break;
				}
			}	
		}
	}
}

/**
 * Initialisation de la liste des types d'intervalle
 * @author : Coralie NICOLAS
 */
private void initialiseListeTIntervalle(javax.servlet.http.HttpServletRequest request) throws Exception{
	//Recherche des types d'intervalle
	/*java.util.ArrayList a = TIntervalle.listerTIntervalle(getTransaction());
	setListeTIntervalle(a);
	
	//Si au moins un type d'intervalle
	if (a.size() !=0 ) {
		int tailles [] = {20};
		FormateListe aFormat = new FormateListe(tailles);
		for (java.util.ListIterator list = a.listIterator(); list.hasNext(); ) {
			TIntervalle aTIntervalle = (TIntervalle)list.next();
			String ligne [] = { aTIntervalle.getDesignation()};
			aFormat.ajouteLigne(ligne);
			
		}
		setLB_LIBELLE(aFormat.getListeFormatee());
	} else {
		setLB_LIBELLE(null);
	}
	
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_LB_LIBELLE_SELECT(),"0");
	addZone(getNOM_EF_LIBELLE(),"");*/
}

/**
 * Constructeur du process OeTIntervalle.
 * Date de création : (20/05/05 07:31:26)
 * @author : Générateur de process
 */
public OeTIntervalle() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (20/05/05 07:31:26)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_AJOUTER() {
	return "NOM_PB_AJOUTER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (20/05/05 07:31:26)
 * @author : Générateur de process
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {

	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
	
	//On vide la zone de saisie
	addZone(getNOM_EF_LIBELLE(),"");
	setFocus(getNOM_EF_LIBELLE());
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (20/05/05 07:31:26)
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
 * Date de création : (20/05/05 07:31:26)
 * @author : Générateur de process
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {

	if ("".equals(getVAL_ST_TITRE_ACTION())) {
		setStatut(STATUT_PROCESS_APPELANT);
	} else {
		initialiseListeTIntervalle(request);
	}
	//on vide les zones
	setFocus(null);
	addZone(getNOM_EF_LIBELLE(),"");
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_ST_DESIGNATION(),"");
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (20/05/05 07:31:26)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_MODIFIER() {
	return "NOM_PB_MODIFIER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (20/05/05 07:31:26)
 * @author : Générateur de process
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {

//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_LIBELLE_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_LIBELLE_SELECT())) : -1);
	if (numligne == -1 || getListeTIntervalle().size() == 0 || numligne > getListeTIntervalle().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Type d'intervalle"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);

	//Récup du type d'intervalle courant
	TIntervalle tintervalle = (TIntervalle)getListeTIntervalle().get(numligne);
	setTintervalleCourant(tintervalle);

	//Alim zones
	addZone(getNOM_EF_LIBELLE(), tintervalle.getDesignation());
	setFocus(getNOM_EF_LIBELLE());
	setStatut(STATUT_MEME_PROCESS);
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (20/05/05 07:31:26)
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
 * Date de création : (20/05/05 07:31:26)
 * @author : Générateur de process
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_LIBELLE_SELECT()) ? Integer.parseInt(getVAL_LB_LIBELLE_SELECT()): -1); 
		
	//récup du type d'intervalle

	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	
	TIntervalle monTintervalle = (TIntervalle)getListeTIntervalle().get(indice);
	setTintervalleCourant(monTintervalle);
	
	addZone(getNOM_EF_LIBELLE(), monTintervalle.getDesignation());
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPRIMER
 * Date de création : (20/05/05 07:31:26)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_SUPPRIMER() {
	return "NOM_PB_SUPPRIMER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (20/05/05 07:31:26)
 * @author : Générateur de process
 */
public boolean performPB_SUPPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
	//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_LIBELLE_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_LIBELLE_SELECT())) : -1);
	if (numligne == -1 || getListeTIntervalle().size() == 0 || numligne > getListeTIntervalle().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Types d'intervalle"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);

	//Récup du type d'intervalle courante
	TIntervalle tintervalle = (TIntervalle)getListeTIntervalle().get(numligne);
	setTintervalleCourant(tintervalle);

	//Alim zones
	addZone(getNOM_ST_DESIGNATION(), tintervalle.getDesignation());
	setStatut(STATUT_MEME_PROCESS);	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (20/05/05 07:31:26)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_VALIDER() {
	return "NOM_PB_VALIDER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (20/05/05 07:31:26)
 * @author : Générateur de process
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Si aucune action en cours
	if (getZone(getNOM_ST_TITRE_ACTION()).length() == 0) {
		setStatut(STATUT_MEME_PROCESS, true, MairieMessages.getMessage("ERR996"));//"Vous ne pouvez pas valider, il n'y a pas d'action en cours."
		return false;
	}

	//Récup des zones saisies
	String newDesignation = getZone(getNOM_EF_LIBELLE()).toUpperCase();
	
	//Si Action Suppression
	if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_SUPPRESSION)) {

		//Test si un des champs a été modifié
		//if (! newDesignation.equals(getTintervalleCourant().getDesignation().trim())) {
			//getTransaction().declarerErreur(MairieMessages.getMessage("ERR995"));//"ERR995","En suppression, aucune zone n'est modifiable."
			//return false;
		//}
		
		//Suppression
		try{
			getTintervalleCourant().supprimerTIntervalle(getTransaction());
		}catch (Exception e){
			getTransaction().declarerErreur("La suppresion est impossible.");
		}
		
		if (getTransaction().isErreur())
			return false;
			
	//Si Action Modification
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_MODIFICATION)) {

		//Si lib contact non saisit
		if (newDesignation.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","du type d'intervalle"));//"ERR008","Le libellé @ est obligatoire"
			setFocus(getNOM_EF_LIBELLE());
			return false;
		}
		
		//Affectation des attributs
		getTintervalleCourant().setDesignation(newDesignation);
		
		//Modification
		getTintervalleCourant().modifierTIntervalle(getTransaction(),newDesignation);
		if (getTransaction().isErreur())
			return false;
			
	//Si Action Creation		
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_CREATION)) {

		//Si lib contact non saisit
		if (newDesignation.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","du type d'intervalle"));//"ERR008","Le libellé @ est obligatoire"
			setFocus(getNOM_EF_LIBELLE());
			return false;
		}
		
		setTintervalleCourant(new TIntervalle());
		
		//Affectation des attributs
		getTintervalleCourant().setDesignation(newDesignation);

		//Création
		getTintervalleCourant().creerTIntervalle(getTransaction(),newDesignation);
		if (getTransaction().isErreur())
			return false;
		
	}

	//Tout s'est bien passé
	commitTransaction();
	//on vide les zones
	addZone(getNOM_EF_LIBELLE(),"");
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_ST_DESIGNATION(),"");
	//addZone(getn)
	setLB_LIBELLE(LBVide);
	setFocus(null);
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (20/05/05 07:31:26)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (20/05/05 07:31:26)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_LIBELLE
 * Date de création : (20/05/05 07:31:26)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_LIBELLE() {
	return "NOM_EF_LIBELLE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_LIBELLE
 * Date de création : (20/05/05 07:31:26)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_LIBELLE() {
	return getZone(getNOM_EF_LIBELLE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_LIBELLE
 * Date de création : (20/05/05 07:31:26)
 * @author : Générateur de process
 */
private String [] getLB_LIBELLE() {
	if (LB_LIBELLE == null)
		LB_LIBELLE = initialiseLazyLB();
	return LB_LIBELLE;
}
/**
 * Setter de la liste:
 * LB_LIBELLE
 * Date de création : (20/05/05 07:31:26)
 * @author : Générateur de process
 */
private void setLB_LIBELLE(java.lang.String[] newLB_LIBELLE) {
	LB_LIBELLE = newLB_LIBELLE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_LIBELLE
 * Date de création : (20/05/05 07:31:26)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_LIBELLE() {
	return "NOM_LB_LIBELLE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_LIBELLE_SELECT
 * Date de création : (20/05/05 07:31:26)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_LIBELLE_SELECT() {
	return "NOM_LB_LIBELLE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_LIBELLE
 * Date de création : (20/05/05 07:31:26)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_LIBELLE() {
	return getLB_LIBELLE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_LIBELLE
 * Date de création : (20/05/05 07:31:26)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_LIBELLE_SELECT() {
	return getZone(getNOM_LB_LIBELLE_SELECT());
}
	/**
	 * @return Renvoie listeTIntervalle.
	 */
	private ArrayList getListeTIntervalle() {
		return listeTIntervalle;
	}
	/**
	 * @param listeTIntervalle listeTIntervalle à définir.
	 */
	private void setListeTIntervalle(ArrayList listeTIntervalle) {
		this.listeTIntervalle = listeTIntervalle;
	}
	/**
	 * @return Renvoie tintervalleCourant.
	 */
	private TIntervalle getTintervalleCourant() {
		return tintervalleCourant;
	}
	/**
	 * @param tintervalleCourant tintervalleCourant à définir.
	 */
	private void setTintervalleCourant(TIntervalle tintervalleCourant) {
		this.tintervalleCourant = tintervalleCourant;
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
	 * @param focus focus à définir.
	 */
	public String getDefaultFocus() {
		return getNOM_LB_LIBELLE();
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
 * Date de création : (20/05/05 07:31:26)
 * @author : Générateur de process
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
 * Date de création : (28/06/05 14:57:07)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeTIntervalle.jsp";
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DESIGNATION
 * Date de création : (28/06/05 14:57:07)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_DESIGNATION() {
	return "NOM_ST_DESIGNATION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DESIGNATION
 * Date de création : (28/06/05 14:57:07)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_DESIGNATION() {
	return getZone(getNOM_ST_DESIGNATION());
}
}
