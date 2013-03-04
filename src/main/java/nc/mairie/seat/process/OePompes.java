package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.Pompes;
import nc.mairie.technique.*;
/**
 * Process OePompes
 * Date de création : (31/01/06 07:23:23)
* 
*/
public class OePompes extends nc.mairie.technique.BasicProcess {
	private java.lang.String[] LB_POMPES;
	private String ACTION_SUPPRESSION = "Suppression d'une pompe.<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private String ACTION_MODIFICATION = "Modification d'une pompe.";
	private String ACTION_CREATION = "Création d'une pompe.";
	private ArrayList listePompes = null;
	private Pompes pompeCourante;
	private boolean first;
	public int vide;
	private String focus = null;
	
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (31/01/06 07:23:23)
* 
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
//	Si liste des pneus est vide
	if (getLB_POMPES() == LBVide) {
		
		java.util.ArrayList a = Pompes.listerPompes(getTransaction());
		setListePompes(a);
		if (a.size()!=0){
			//les élèments de la liste seront le libelle de la pompe et le commentaire pour pouvoir récupérer le dernier élément et les dimensions des pneus.
			int [] tailles = {32,50};
			String [] champs = {"libelle_pompe","commentaire_pompe"};
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"G","G"};
			
			FormateListe f = new FormateListe(tailles,a,champs,padding,false);
			String [] l = f.getListeFormatee();
			setLB_POMPES(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
			
		}else{
			setLB_POMPES(null);
		}
		setVide(a.size());
	}
//	 on sélectionne l'élément en cours
	if(getPompeCourante()!=null){
		if(getPompeCourante().getNum_pompe()!=null){
			int position = -1;
			addZone(getNOM_LB_POMPES_SELECT(),String.valueOf(-1));
			for (int i = 0; i < getListePompes().size(); i++) {
				Pompes unePompe = (Pompes)getListePompes().get(i);
				if (unePompe.getNum_pompe().equals(getPompeCourante().getNum_pompe())) {
					addZone(getNOM_LB_POMPES_SELECT(),String.valueOf(i));
					break;
				}
			}	
		}
	}

}
/**
 * Constructeur du process OePompes.
 * Date de création : (31/01/06 07:23:23)
* 
 */
public OePompes() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (31/01/06 07:23:23)
* 
 */
public java.lang.String getNOM_PB_AJOUTER() {
	return "NOM_PB_AJOUTER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (31/01/06 07:23:23)
* 
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
	
	//On vide la zone de saisie
	addZone(getNOM_EF_LIBELLE(),"");
	addZone(getNOM_EF_COMMENTAIRE(),"");

	setStatut(STATUT_MEME_PROCESS);
	setFocus(getNOM_EF_LIBELLE());
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (31/01/06 07:23:23)
* 
 */
public java.lang.String getNOM_PB_ANNULER() {
	return "NOM_PB_ANNULER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (31/01/06 07:23:23)
* 
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	if ("".equals(getVAL_ST_TITRE_ACTION())) {
		setStatut(STATUT_PROCESS_APPELANT);
	} else {
		setLB_POMPES(LBVide);
	}
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_ST_LIBELLE(),"");
	setFocus(null);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (31/01/06 07:23:23)
* 
 */
public java.lang.String getNOM_PB_MODIFIER() {
	return "NOM_PB_MODIFIER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (31/01/06 07:23:23)
* 
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_POMPES_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_POMPES_SELECT())) : -1);
	if (numligne == -1 || getListePompes().size() == 0 || numligne > getListePompes().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Pompes"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);

	//Récup de la pompe courante
	Pompes pompes = (Pompes)getListePompes().get(numligne);
	setPompeCourante(pompes);

	//Alim zones
	//int ligneType = getListeTypeContact().indexOf(getHashTypeContact().get(c.getCodTypeContact()));
	addZone(getNOM_EF_LIBELLE(), pompes.getLibelle_pompe());
	addZone(getNOM_EF_COMMENTAIRE(), pompes.getCommentaire_pompe());
	//addZone(getNOM_LB_TYPE_CONTACT_SELECT(), String.valueOf(ligneType));
	
	setStatut(STATUT_MEME_PROCESS);
	setFocus(getNOM_EF_LIBELLE());
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPRIMER
 * Date de création : (31/01/06 07:23:23)
* 
 */
public java.lang.String getNOM_PB_SUPPRIMER() {
	return "NOM_PB_SUPPRIMER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (31/01/06 07:23:23)
* 
 */
public boolean performPB_SUPPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_POMPES_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_POMPES_SELECT())) : -1);
	if (numligne == -1 || getListePompes().size() == 0 || numligne > getListePompes().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Pompes"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);
	
	//Récup du pneu courant
	Pompes pompes = (Pompes)getListePompes().get(numligne);
	setPompeCourante(pompes);

	//Alim zones
	addZone(getNOM_ST_LIBELLE(),getPompeCourante().getLibelle_pompe());
	addZone(getNOM_EF_LIBELLE(),getPompeCourante().getLibelle_pompe());
	addZone(getNOM_ST_COMMENTAIRE(),getPompeCourante().getCommentaire_pompe());
	addZone(getNOM_EF_COMMENTAIRE(),getPompeCourante().getCommentaire_pompe());
	
	setStatut(STATUT_MEME_PROCESS);	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (31/01/06 07:23:23)
* 
 */
public java.lang.String getNOM_PB_VALIDER() {
	return "NOM_PB_VALIDER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (31/01/06 07:23:23)
* 
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {

//	Si aucune action en cours
	if (getZone(getNOM_ST_TITRE_ACTION()).length() == 0) {
		setStatut(STATUT_MEME_PROCESS, true, MairieMessages.getMessage("ERR996"));//"Vous ne pouvez pas valider, il n'y a pas d'action en cours."
		return false;
	}

	//Récup des zones saisies
	//TypeContact newType = (TypeContact)getListeTypeContact().get(Integer.parseInt(getZone(getNOM_LB_TYPE_CONTACT_SELECT())));
	String newLibelle = getZone(getNOM_EF_LIBELLE()).toUpperCase();
	String newCommentaire = getZone(getNOM_EF_COMMENTAIRE()).toUpperCase();
	
	//Si Action Suppression
	if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_SUPPRESSION)) {

		//Test si un des champs a été modifié
		if (! newLibelle.equals(getPompeCourante().getLibelle_pompe().trim())) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR995"));//"ERR995","En suppression, aucune zone n'est modifiable."
			return false;
		}
		
		//Suppression
		try{
			getPompeCourante().supprimerPompes(getTransaction());
		}catch (Exception e){
			getTransaction().declarerErreur(" La suppression n'est pas possible.");
		}
		if (getTransaction().isErreur())
			return false;
			
	//Si Action Modification
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_MODIFICATION)) {

		//Si lib contact non saisit
		if (newLibelle.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","de la pompe"));//"ERR008","Le libellé @ est obligatoire"
			setFocus(getNOM_EF_LIBELLE());
			return false;
		}
		
		//Affectation des attributs
		getPompeCourante().setLibelle_pompe(newLibelle);
		getPompeCourante().setCommentaire_pompe(newCommentaire);
		
		//Modification
		getPompeCourante().modifierPompes(getTransaction(),getPompeCourante().getLibelle_pompe(),getPompeCourante().getCommentaire_pompe());
		if (getTransaction().isErreur())
			return false;
			
	//Si Action Creation		
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_CREATION)) {

		//Si lib contact non saisit
		if (newLibelle.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","de la pompe"));//"ERR008","Le libellé @ est obligatoire"
			setFocus(getNOM_LB_POMPES());
			return false;
		}
		
		setPompeCourante(new Pompes());
		
		//Affectation des attributs
		getPompeCourante().setLibelle_pompe(newLibelle);
		getPompeCourante().setCommentaire_pompe(newCommentaire);

		//Création
		getPompeCourante().creerPompes(getTransaction(),getPompeCourante().getLibelle_pompe());
		String info = getTransaction().getMessageErreur();
		if (getTransaction().isErreur())
			return false;		
	}

	//Tout s'est bien passé
	commitTransaction();
	setLB_POMPES(LBVide);
	addZone(getNOM_ST_TITRE_ACTION(),"");	
	addZone(getNOM_ST_LIBELLE(),"");
	addZone(getNOM_ST_COMMENTAIRE(),"");
	
	setFocus(null);
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_COMMENTAIRE
 * Date de création : (31/01/06 07:23:23)
* 
 */
public java.lang.String getNOM_EF_COMMENTAIRE() {
	return "NOM_EF_COMMENTAIRE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_COMMENTAIRE
 * Date de création : (31/01/06 07:23:23)
* 
 */
public java.lang.String getVAL_EF_COMMENTAIRE() {
	return getZone(getNOM_EF_COMMENTAIRE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_LIBELLE
 * Date de création : (31/01/06 07:23:23)
* 
 */
public java.lang.String getNOM_EF_LIBELLE() {
	return "NOM_EF_LIBELLE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_LIBELLE
 * Date de création : (31/01/06 07:23:23)
* 
 */
public java.lang.String getVAL_EF_LIBELLE() {
	return getZone(getNOM_EF_LIBELLE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_POMPES
 * Date de création : (31/01/06 07:23:23)
* 
 */
private String [] getLB_POMPES() {
	if (LB_POMPES == null)
		LB_POMPES = initialiseLazyLB();
	return LB_POMPES;
}
/**
 * Setter de la liste:
 * LB_POMPES
 * Date de création : (31/01/06 07:23:23)
* 
 */
private void setLB_POMPES(java.lang.String[] newLB_POMPES) {
	LB_POMPES = newLB_POMPES;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_POMPES
 * Date de création : (31/01/06 07:23:23)
* 
 */
public java.lang.String getNOM_LB_POMPES() {
	return "NOM_LB_POMPES";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_POMPES_SELECT
 * Date de création : (31/01/06 07:23:23)
* 
 */
public java.lang.String getNOM_LB_POMPES_SELECT() {
	return "NOM_LB_POMPES_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_POMPES
 * Date de création : (31/01/06 07:23:23)
* 
 */
public java.lang.String [] getVAL_LB_POMPES() {
	return getLB_POMPES();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_POMPES
 * Date de création : (31/01/06 07:23:23)
* 
 */
public java.lang.String getVAL_LB_POMPES_SELECT() {
	return getZone(getNOM_LB_POMPES_SELECT());
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (31/01/06 07:23:23)
* 
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
 * Date de création : (31/01/06 07:26:53)
* 
 */
@Override
public String getJSP() {
	return "OePompes.jsp";
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMMENTAIRE
 * Date de création : (31/01/06 07:26:53)
* 
 */
public java.lang.String getNOM_ST_COMMENTAIRE() {
	return "NOM_ST_COMMENTAIRE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMMENTAIRE
 * Date de création : (31/01/06 07:26:53)
* 
 */
public java.lang.String getVAL_ST_COMMENTAIRE() {
	return getZone(getNOM_ST_COMMENTAIRE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_LIBELLE
 * Date de création : (31/01/06 07:26:53)
* 
 */
public java.lang.String getNOM_ST_LIBELLE() {
	return "NOM_ST_LIBELLE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_LIBELLE
 * Date de création : (31/01/06 07:26:53)
* 
 */
public java.lang.String getVAL_ST_LIBELLE() {
	return getZone(getNOM_ST_LIBELLE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (31/01/06 07:26:53)
* 
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (31/01/06 07:26:53)
* 
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
public boolean isFirst() {
	return first;
}
public void setFirst(boolean first) {
	this.first = first;
}
public ArrayList getListePompes() {
	return listePompes;
}
public void setListePompes(ArrayList listePompes) {
	this.listePompes = listePompes;
}
public Pompes getPompeCourante() {
	return pompeCourante;
}
public void setPompeCourante(Pompes pompeCourante) {
	this.pompeCourante = pompeCourante;
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
 * @return focus focus à définir.
 */
public String getDefaultFocus() {
	return getNOM_LB_POMPES();
}

	public int getVide() {
		return vide;
	}
	public void setVide(int vide) {
		this.vide = vide;
	}
}
