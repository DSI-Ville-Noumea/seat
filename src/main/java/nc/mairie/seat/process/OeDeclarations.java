package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.AgentCCAS;
import nc.mairie.seat.metier.AgentCDE;
import nc.mairie.seat.metier.Agents;
import nc.mairie.seat.metier.DeclarationAgentEquip;
import nc.mairie.seat.metier.Declarations;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.OT;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.technique.*;
/**
 * Process OeDeclarations
 * Date de création : (22/08/05 08:38:54)
 * @author : Générateur de process
*/
public class OeDeclarations extends nc.mairie.technique.BasicProcess {
	public static final int STATUT_MODIFIER = 2;
	public static final int STATUT_AJOUTER = 1;
	private java.lang.String[] LB_DECLARATIONS;
	private String ACTION_SUPPRESSION = "Suppression<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private String ACTION_MODIFICATION = "Modification";
	private String ACTION_CREATION = "Création";
	private ArrayList listeDeclarationsAgentEquip;
	private Declarations declarationCourante;
	private EquipementInfos equipementInfosCourant;
	private PMatInfos pMatInfosCourant;
	public boolean isVide;
	private String tri;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (22/08/05 08:38:54)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	EquipementInfos unEquipementInfos = (EquipementInfos)VariableGlobale.recuperer(request,"EQUIPEMENTINFOS");
	setEquipementInfosCourant(unEquipementInfos);
	
	if (getListeDeclarationsAgentEquip().size() == 0 || etatStatut()!=STATUT_MEME_PROCESS) {
		initialiseListeDeclarations(request);
	}

}

public void initialiseListeDeclarations(javax.servlet.http.HttpServletRequest request) throws Exception{
	ArrayList arr = DeclarationAgentEquip.listerDeclarationAgentEquip(getTransaction());
	if (arr.size() != 0) {
	
		setListeDeclarationsAgentEquip(arr);
		
		int tailles [] = {10,10,37,10,6};
		String[] padding = {"C","G","G","D","G"};
		String[] attr = {"date","immat","declarant","codeot","codeservice"};
		FormateListe aFormat = new FormateListe(tailles,arr,attr,padding,false);
		
		setLB_DECLARATIONS(aFormat.getListeFormatee());
		setVide(true);
	} else {
		setLB_DECLARATIONS(null);
		setVide(false);
	}
	
	//cocher(tri);
}

/**
 * Constructeur du process OeDeclarations.
 * Date de création : (22/08/05 08:38:54)
 * @author : Générateur de process
 */
public OeDeclarations() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (22/08/05 08:38:54)
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
 * Date de création : (22/08/05 08:38:54)
 * @author : Générateur de process
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {
	Declarations uneDeclaration = new Declarations();
	
	VariableActivite.ajouter(this,"DECLARATION",uneDeclaration);
	VariableActivite.ajouter(this,"TITRE_ACTION",ACTION_CREATION);
	VariableActivite.ajouter(this,"DEBRANCHE","TRUE");
	setStatut(STATUT_AJOUTER,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (22/08/05 08:38:54)
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
 * Date de création : (22/08/05 08:38:54)
 * @author : Générateur de process
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {
	int indice = (Services.estNumerique(getVAL_LB_DECLARATIONS_SELECT()) ? Integer.parseInt(getVAL_LB_DECLARATIONS_SELECT()): -1);
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner une déclaration");
		return false;
	}
	DeclarationAgentEquip uneDeclarationAgent = (DeclarationAgentEquip)getListeDeclarationsAgentEquip().get(indice);
	Declarations uneDeclaration = Declarations.chercherDeclarations(getTransaction(),uneDeclarationAgent.getCodedec());
	if(getTransaction().isErreur()){
		return false;
	}
	setDeclarationCourante(uneDeclaration);
	EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfos(getTransaction(),uneDeclaration.getNuminv());
	if(getTransaction().isErreur()){
		getTransaction().traiterErreur();
		PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),uneDeclaration.getNuminv());
		if(getTransaction().isErreur()){
			return false;
		}
		if(null==unPMatInfos){
			unPMatInfos = new PMatInfos();
		}
		setPMatInfosCourant(unPMatInfos);
	}
	if(null==unEquipementInfos){
		unEquipementInfos = new EquipementInfos();
	}
	if(null==getPMatInfosCourant()){
		setPMatInfosCourant(new PMatInfos());
	}
	setEquipementInfosCourant(unEquipementInfos);
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	VariableActivite.ajouter(this,"DECLARATION",getDeclarationCourante());
	VariableActivite.ajouter(this,"TITRE_ACTION",ACTION_MODIFICATION);
	setStatut(STATUT_MODIFIER,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPRIMER
 * Date de création : (22/08/05 08:38:54)
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
 * Date de création : (22/08/05 08:38:54)
 * @author : Générateur de process
 */
public boolean performPB_SUPPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
	
	int indice = (Services.estNumerique(getVAL_LB_DECLARATIONS_SELECT()) ? Integer.parseInt(getVAL_LB_DECLARATIONS_SELECT()): -1);
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner une déclaration");
		return false;
	}
	DeclarationAgentEquip uneDeclarationAgent = (DeclarationAgentEquip)getListeDeclarationsAgentEquip().get(indice);
	Declarations uneDeclaration = Declarations.chercherDeclarations(getTransaction(),uneDeclarationAgent.getCodedec());
	if(getTransaction().isErreur()){
		return false;
	}
	setDeclarationCourante(uneDeclaration);
	
	String declarant = "agent non trouvé";
	addZone(getNOM_ST_DATE(),getDeclarationCourante().getDate());
	addZone(getNOM_ST_ANOMALIES(),getDeclarationCourante().getAnomalies().trim());
	//recherche du déclarant
	if(getDeclarationCourante().getCodeservice().equals("4000")){
		AgentCDE unAgent = AgentCDE.chercherAgentCDE(getTransaction(),getDeclarationCourante().getMatricule());
		if(getTransaction().isErreur()){
			getTransaction().traiterErreur() ;
			declarant = "agent non trouvé";
		}else{
			declarant = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
		}
		//addZone(getNOM_ST_DECLARANT(),unAgent.getNom().trim()+" "+unAgent.getPrenom().trim());
	}else if(getDeclarationCourante().getCodeservice().equals("5000")){
		AgentCCAS unAgent = AgentCCAS.chercherAgentCCAS(getTransaction(),getDeclarationCourante().getMatricule());
		if(getTransaction().isErreur()){
			getTransaction().traiterErreur() ;
			declarant = "agent non trouvé";
		}else{
			declarant = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
		}
	//addZone(getNOM_ST_DECLARANT(),unAgent.getNom().trim()+" "+unAgent.getPrenom().trim());
	}else{
		Agents unAgent = Agents.chercherAgents(getTransaction(),getDeclarationCourante().getMatricule());
		if(getTransaction().isErreur()){
			getTransaction().traiterErreur() ;
			declarant = "agent non trouvé";
		}else{
			declarant = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
		}
		//addZone(getNOM_ST_DECLARANT(),unAgent.getNom().trim()+" "+unAgent.getPrenom().trim());
	}
	addZone(getNOM_ST_DECLARANT(),declarant);
	
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);
	return true;
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_DECLARATIONS
 * Date de création : (22/08/05 08:38:54)
 * @author : Générateur de process
 */
private String [] getLB_DECLARATIONS() {
	if (LB_DECLARATIONS == null)
		LB_DECLARATIONS = initialiseLazyLB();
	return LB_DECLARATIONS;
}
/**
 * Setter de la liste:
 * LB_DECLARATIONS
 * Date de création : (22/08/05 08:38:54)
 * @author : Générateur de process
 */
private void setLB_DECLARATIONS(java.lang.String[] newLB_DECLARATIONS) {
	LB_DECLARATIONS = newLB_DECLARATIONS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_DECLARATIONS
 * Date de création : (22/08/05 08:38:54)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_DECLARATIONS() {
	return "NOM_LB_DECLARATIONS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_DECLARATIONS_SELECT
 * Date de création : (22/08/05 08:38:54)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_DECLARATIONS_SELECT() {
	return "NOM_LB_DECLARATIONS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_DECLARATIONS
 * Date de création : (22/08/05 08:38:54)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_DECLARATIONS() {
	return getLB_DECLARATIONS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_DECLARATIONS
 * Date de création : (22/08/05 08:38:54)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_DECLARATIONS_SELECT() {
	return getZone(getNOM_LB_DECLARATIONS_SELECT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (22/08/05 08:39:40)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (22/08/05 08:39:40)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}


public void cocher(String tri){
	//Selon le tri coche la bonne colonne
	addZone(getNOM_RG_TRI(),getNOM_RB_TRI_INV());
	if (tri.equals("numeroimmatriculation")){
		addZone(getNOM_RG_TRI(),getNOM_RB_TRI_IMMAT());
	}else if (tri.equals("date")){
		addZone(getNOM_RG_TRI(),getNOM_RB_TRI_DATE());
	}else if (tri.equals("numeroot")){
		addZone(getNOM_RG_TRI(),getNOM_RB_TRI_OT());
	}
}

/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (22/08/05 08:49:39)
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
 * Date de création : (22/08/05 08:49:39)
 * @author : Générateur de process
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	addZone(getNOM_ST_TITRE_ACTION(),"");
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (22/08/05 08:49:39)
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
 * Date de création : (22/08/05 08:49:39)
 * @author : Générateur de process
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	if(getVAL_ST_TITRE_ACTION().equals(ACTION_SUPPRESSION)){
		if((getDeclarationCourante().getCodeot()!=null)&&(!getDeclarationCourante().getCodeot().equals(""))){
			OT unOT = OT.chercherOT(getTransaction(),getDeclarationCourante().getCodeot());
			if(getTransaction().isErreur()){
				getTransaction().traiterErreur();
				getDeclarationCourante().supprimerDeclarations(getTransaction());
			}else{
				getTransaction().declarerErreur("Un OT est rattaché à la déclaration vous ne pouvez donc pas supprimer la déclaration.");
				return false;
			}
		}else{
			getDeclarationCourante().supprimerDeclarations(getTransaction());
		}
		
	}
	if(getTransaction().isErreur()){
		return false;
	}else{
		//tout s'est bien passé
		commitTransaction();
		// on vide les zones
		setListeDeclarationsAgentEquip(null);
		addZone(getNOM_ST_TITRE_ACTION(),"");
		addZone(getNOM_ST_DECLARANT(),"");
		addZone(getNOM_ST_DATE(),"");
		addZone(getNOM_ST_ANOMALIES(),"");
	}
	return true;
}
public boolean isVide() {
	return isVide;
}
public void setVide(boolean isVide) {
	this.isVide = isVide;
}
public Declarations getDeclarationCourante() {
	return declarationCourante;
}
public void setDeclarationCourante(Declarations declarationCourante) {
	this.declarationCourante = declarationCourante;
}
public ArrayList getListeDeclarationsAgentEquip() {
	if (listeDeclarationsAgentEquip == null)
		listeDeclarationsAgentEquip=new ArrayList();
	return listeDeclarationsAgentEquip;
}
public void setListeDeclarationsAgentEquip(ArrayList listeDeclarationsAgentEquip) {
	this.listeDeclarationsAgentEquip = listeDeclarationsAgentEquip;
}
/**
 * Retourne le nom du groupe de radio boutons coché pour la JSP :
 * RG_TRI
 * Date de création : (22/08/05 13:53:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RG_TRI() {
	return "NOM_RG_TRI";
}
/**
 * Retourne la valeur du radio bouton (RB_) coché dans la JSP :
 * RG_TRI
 * Date de création : (22/08/05 13:53:02)
 * @author : Générateur de process
 */
public java.lang.String getVAL_RG_TRI() {
	return getZone(getNOM_RG_TRI());
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_AGENT
 * Date de création : (22/08/05 13:53:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_AGENT() {
	return "NOM_RB_TRI_AGENT";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_DATE
 * Date de création : (22/08/05 13:53:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_DATE() {
	return "NOM_RB_TRI_DATE";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_IMMAT
 * Date de création : (22/08/05 13:53:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_IMMAT() {
	return "NOM_RB_TRI_IMMAT";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_INV
 * Date de création : (22/08/05 13:53:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_INV() {
	return "NOM_RB_TRI_INV";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_OT
 * Date de création : (22/08/05 13:53:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_OT() {
	return "NOM_RB_TRI_OT";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_TRI
 * Date de création : (22/08/05 13:57:35)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_TRI() {
	return "NOM_PB_TRI";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (22/08/05 13:57:35)
 * @author : Générateur de process
 */
public boolean performPB_TRI(javax.servlet.http.HttpServletRequest request) throws Exception {
	if (getNOM_RB_TRI_INV().equals(getZone(getNOM_RG_TRI()))){
		tri = "numeroinventaire";
	}
	if (getNOM_RB_TRI_AGENT().equals(getZone(getNOM_RG_TRI()))){
		tri = "nomatr";
	}
	if (getNOM_RB_TRI_DATE().equals(getZone(getNOM_RG_TRI()))){
		tri = "date";
	}
	if (getNOM_RB_TRI_IMMAT().equals(getZone(getNOM_RG_TRI()))){
		tri = "numeroimmatriculation";
	}
	if (getNOM_RB_TRI_OT().equals(getZone(getNOM_RG_TRI()))){
		tri = "numeroot";
	}
	return true;
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (22/08/05 08:38:54)
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

		//Si clic sur le bouton PB_TRI
		if (testerParametre(request, getNOM_PB_TRI())) {
			return performPB_TRI(request);
		}

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_VALIDER
		if (testerParametre(request, getNOM_PB_VALIDER())) {
			return performPB_VALIDER(request);
		}

		//Si clic sur le bouton PB_AJOUTER
		if (testerParametre(request, getNOM_PB_AJOUTER())) {
			return performPB_AJOUTER(request);
		}

		//Si clic sur le bouton PB_MODIFIER
		if (testerParametre(request, getNOM_PB_MODIFIER())) {
			return performPB_MODIFIER(request);
		}

		//Si clic sur le bouton PB_SUPPRIMER
		if (testerParametre(request, getNOM_PB_SUPPRIMER())) {
			return performPB_SUPPRIMER(request);
		}

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (23/08/05 12:47:03)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeDeclarations.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE
 * Date de création : (23/08/05 12:47:03)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_RECHERCHE() {
	return "NOM_PB_RECHERCHE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (23/08/05 12:47:03)
 * @author : Générateur de process
 */
public boolean performPB_RECHERCHE(javax.servlet.http.HttpServletRequest request) throws Exception {
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_ANOMALIES
 * Date de création : (23/08/05 12:47:03)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_ANOMALIES() {
	return "NOM_ST_ANOMALIES";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_ANOMALIES
 * Date de création : (23/08/05 12:47:03)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_ANOMALIES() {
	return getZone(getNOM_ST_ANOMALIES());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DATE
 * Date de création : (23/08/05 12:47:03)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_DATE() {
	return "NOM_ST_DATE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DATE
 * Date de création : (23/08/05 12:47:03)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_DATE() {
	return getZone(getNOM_ST_DATE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DECLARANT
 * Date de création : (23/08/05 12:47:03)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_DECLARANT() {
	return "NOM_ST_DECLARANT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DECLARANT
 * Date de création : (23/08/05 12:47:03)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_DECLARANT() {
	return getZone(getNOM_ST_DECLARANT());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DATE_RECHERCHE
 * Date de création : (23/08/05 12:47:03)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_DATE_RECHERCHE() {
	return "NOM_EF_DATE_RECHERCHE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DATE_RECHERCHE
 * Date de création : (23/08/05 12:47:03)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_DATE_RECHERCHE() {
	return getZone(getNOM_EF_DATE_RECHERCHE());
}
	public EquipementInfos getEquipementInfosCourant() {
		return equipementInfosCourant;
	}
	public void setPMatInfosCourant(PMatInfos pMatInfosCourant) {
		this.pMatInfosCourant = pMatInfosCourant;
	}
	public PMatInfos getPMatInfosCourant() {
		return pMatInfosCourant;
	}
	public void setEquipementInfosCourant(EquipementInfos equipementInfosCourant) {
		this.equipementInfosCourant = equipementInfosCourant;
	}
}
