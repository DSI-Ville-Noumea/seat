package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.AgentCCAS;
import nc.mairie.seat.metier.AgentCDE;
import nc.mairie.seat.metier.AgentInterface;
import nc.mairie.seat.metier.Agents;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.PM_Affectation_Sce_Infos;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.technique.*;
/**
 * Process OePMateriel_Recherche
 * Date de création : (03/05/07 11:01:05)
 * @author : Générateur de process
*/
public class OePMateriel_Recherche extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6260309220114423701L;
	public static final int STATUT_AGENT = 1;
	private java.lang.String[] LB_PMATERIEL;
	private String focus = null;
	private ArrayList<PMatInfos> listPMatInfos;
	private PMatInfos pMatInfosCourant;
	private boolean isFirst = true;
	private String tri = "pminv";
	String mode = "";
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (03/05/07 11:01:05)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	if((isFirst)||(etatStatut()==1)){
		String param = (String)VariableActivite.recuperer(this,"PARAM");
		mode = (String) VariableActivite.recuperer(this,"MODE");
		VariableActivite.enlever(this,"MODE");
		addZone(getNOM_EF_RECHERCHE(),param);
		if((param!=null)&&(!param.equals(""))){
			performPB_RECHERCHE(request);
		}
		String nomatr = (String)VariableActivite.recuperer(this,"NOMATR");
		String code_sce = (String)VariableActivite.recuperer(this,"CODESERVICE");
		VariableActivite.enlever(this,"NOMATR");
		VariableActivite.enlever(this,"CODESERVICE");
		if (((nomatr!=null)&&(!nomatr.equals("")))||((code_sce!=null)&&(!code_sce.equals("")))){
			if(!nomatr.equals("")){
				addZone(getNOM_EF_AGENT(),nomatr);
			}
			if(!code_sce.equals("")){
				addZone(getNOM_EF_SCE(),code_sce);
			}
			
		}
		performPB_RECHERCHE(request);
	}
	// initialisation de la liste des pMatInfos
//	if(isFirst){
//	performPB_RECHERCHE(request);
//	}
	
setFirst(false);
}
/**
 * Constructeur du process OePMateriel_Recherche.
 * Date de création : (03/05/07 11:01:05)
 * @author : Générateur de process
 */
public OePMateriel_Recherche() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (03/05/07 11:01:05)
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
 * Date de création : (03/05/07 11:01:05)
 * @author : Générateur de process
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (03/05/07 11:01:05)
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
 * Date de création : (03/05/07 11:01:05)
 * @author : Générateur de process
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
	// récupération de l'élément sélectionné
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_PMATERIEL_SELECT()) ? Integer.parseInt(getVAL_LB_PMATERIEL_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	PMatInfos monPMatInfos = (PMatInfos)getListPMatInfos().get(indice);
	if (getTransaction().isErreur()){
		return false;
	}
	if(null==monPMatInfos){
		monPMatInfos = new PMatInfos();
	}
	setPMatInfosCourant(monPMatInfos);
	setLB_PMATERIEL(LBVide);
	VariableActivite.ajouter(this,"TYPE","pmateriel");
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",new EquipementInfos());
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE
 * Date de création : (03/05/07 11:01:05)
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
 * Date de création : (03/05/07 11:01:05)
 * @author : Générateur de process
 */
public boolean performPB_RECHERCHE(javax.servlet.http.HttpServletRequest request) throws Exception {
	boolean[] ordres = {true};
	String[] colonnes = {tri};
	ArrayList<AgentInterface> listAgent = new ArrayList<AgentInterface>();
	//ArrayList <AgentInterface>listInter = new ArrayList<AgentInterface>();
	String nom = "";
	ArrayList<PMatInfos> listPMat = new ArrayList<PMatInfos>();
		
////////////////////////////////////////////////////////////////////////////////////////////////////////
	// recherche par l'agent
	 if(!getZone(getNOM_EF_AGENT()).equals("")){
//		 recherche de la liste des petits matériels dont l'agent est responsable
		
		String param = getZone(getNOM_EF_AGENT());
		if(Services.estNumerique(getZone(getNOM_EF_AGENT()))){
			ArrayList<PM_Affectation_Sce_Infos> listeASI = PM_Affectation_Sce_Infos.listerPmAffectationSceInfosAgent(getTransaction(),param);
			if(getTransaction().isErreur()){
				getTransaction().declarerErreur("L'agent n'est responsable d'aucun petit matériel.");
				return false;
			}
			if (listeASI.size()>0){
				for (int i=0;i<listeASI.size();i++){
					PM_Affectation_Sce_Infos unASI = (PM_Affectation_Sce_Infos)listeASI.get(i);
					PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),unASI.getPminv());
					listPMat.add(unPMatInfos);
				}
			}
		}else{
			if(Services.estAlphabetique(getZone(getNOM_EF_AGENT()))){
				nom = getZone(getNOM_EF_AGENT()).toUpperCase();
				if(getZone(getNOM_EF_AGENT()).equals("")){//||getZone(getNOM_EF_SERVICE()).equals("")){
					getTransaction().declarerErreur("Vous devez saisir le nom de l'agent ");
					return false;
				}
				
				listAgent.addAll(AgentCDE.listerAgentCDENom(getTransaction(),nom));
				listAgent.addAll(AgentCCAS.listerAgentCCASNom(getTransaction(),nom));
				listAgent.addAll(Agents.listerAgentsNom(getTransaction(),nom));
					
				if (listAgent.size() > 0){
					if (listAgent.size()==1){
						Agents unAgent = (Agents)listAgent.get(0);
						// s'il n'a qu'un petit matériel on débranche directement sur la fenêtre visu sinon on les affiche
						ArrayList<PM_Affectation_Sce_Infos> a = PM_Affectation_Sce_Infos.listerPmAffectationSceInfosAgent(getTransaction(),unAgent.getNomatr());
						if(getTransaction().isErreur()){
							return false;
						}
						if (a.size()==1){
							PM_Affectation_Sce_Infos monASI = (PM_Affectation_Sce_Infos)a.get(0);
							PMatInfos unPM = PMatInfos.chercherPMatInfos(getTransaction(),monASI.getPminv());
							if(getTransaction().isErreur()){
								return false;
							}
							setPMatInfosCourant(unPM);
						}
					}else{
						// si plusieurs on affiche la liste du résultat des agents
						VariableActivite.ajouter(this,"NOM",nom);
						VariableActivite.ajouter(this,"MODE",mode);
						VariableActivite.ajouter(this,"TYPE","PMATERIEL");
						setStatut(STATUT_AGENT,true);
					}
				}
			}
		}
		listPMat = Services.trier(listPMat,colonnes,ordres);
		// on remplit la liste des petits matériels
		setListPMatInfos(listPMat);
		if(listPMat.size()>0){
			//les élèments de la liste 
			int [] tailles = {10,15,15,15,5,10};
			String [] champs = {"pminv","pmserie","designationmarque","designationmodele","designationtypeequip","dmes"};
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"D","D","G","G","G","C"};
			
			setLB_PMATERIEL(new FormateListe(tailles,listPMat,champs,padding,true).getListeFormatee());
		}else{
			setLB_PMATERIEL(LBVide);
		}
////////////////////////////////////////////////////////////////////////////////////////////////////////
	// recherche du service
	}else if(!getZone(getNOM_EF_SCE()).equals("")){
		
//		 recherche de la liste des petits matériels dont l'agent est responsable
		
		String param = getZone(getNOM_EF_SCE());
		/* Modif par luc le 08/01/10
		if (!Services.estNumerique(param)){
			getTransaction().declarerErreur("Le code de du service est numérique.");
			return false;
		}
		*/
		ArrayList<PM_Affectation_Sce_Infos> listeASI = PM_Affectation_Sce_Infos.chercherPmAffectationServiceInfosService(getTransaction(),param);
		if(getTransaction().isErreur()){
			getTransaction().declarerErreur("Le service n'a aucun petit matériel.");
			return false;
		}
		if (listeASI.size()>0){
			for (int i=0;i<listeASI.size();i++){
				PM_Affectation_Sce_Infos unASI = (PM_Affectation_Sce_Infos)listeASI.get(i);
				PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),unASI.getPminv());
				listPMat.add(unPMatInfos);
			}
		}
		listPMat = Services.trier(listPMat,colonnes,ordres);
		// on remplit la liste des petits matériels
		setListPMatInfos(listPMat);
		if(listPMat.size()>0){
			//les élèments de la liste 
			int [] tailles = {10,15,15,15,5,10};
			String [] champs = {"pminv","pmserie","designationmarque","designationmodele","designationtypeequip","dmes"};
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"D","D","G","G","G","C"};
			
			setLB_PMATERIEL(new FormateListe(tailles,listPMat,champs,padding,true).getListeFormatee());
		}else{
			setLB_PMATERIEL(LBVide);
		}
		
		
	}else{
		String recherche = getZone(getNOM_EF_RECHERCHE());
		ArrayList<PMatInfos> list = PMatInfos.listerPMatInfosRecherche(getTransaction(),recherche,tri);
		if(getTransaction().isErreur()){
			return false;
		}
	//	 on remplit la liste des petits matériel
		setListPMatInfos(list);
		if(list.size()>0){
			//les élèments de la liste 
			int [] tailles = {10,15,15,15,5,10};
			String [] champs = {"pminv","pmserie","designationmarque","designationmodele","designationtypeequip","dmes"};
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"D","D","G","G","G","C"};
			
			setLB_PMATERIEL(new FormateListe(tailles,list,champs,padding,true).getListeFormatee());
		}else{
			setLB_PMATERIEL(LBVide);
		}
	}
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_RECHERCHE
 * Date de création : (03/05/07 11:01:05)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_RECHERCHE() {
	return "NOM_EF_RECHERCHE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_RECHERCHE
 * Date de création : (03/05/07 11:01:05)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_RECHERCHE() {
	return getZone(getNOM_EF_RECHERCHE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_PMATERIEL
 * Date de création : (03/05/07 11:01:05)
 * @author : Générateur de process
 */
private String [] getLB_PMATERIEL() {
	if (LB_PMATERIEL == null)
		LB_PMATERIEL = initialiseLazyLB();
	return LB_PMATERIEL;
}
/**
 * Setter de la liste:
 * LB_PMATERIEL
 * Date de création : (03/05/07 11:01:05)
 * @author : Générateur de process
 */
private void setLB_PMATERIEL(java.lang.String[] newLB_PMATERIEL) {
	LB_PMATERIEL = newLB_PMATERIEL;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_PMATERIEL
 * Date de création : (03/05/07 11:01:05)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_PMATERIEL() {
	return "NOM_LB_PMATERIEL";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_PMATERIEL_SELECT
 * Date de création : (03/05/07 11:01:05)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_PMATERIEL_SELECT() {
	return "NOM_LB_PMATERIEL_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_PMATERIEL
 * Date de création : (03/05/07 11:01:05)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_PMATERIEL() {
	return getLB_PMATERIEL();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_PMATERIEL
 * Date de création : (03/05/07 11:01:05)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_PMATERIEL_SELECT() {
	return getZone(getNOM_LB_PMATERIEL_SELECT());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_TRI
 * Date de création : (03/05/07 11:06:33)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_OK_TRI() {
	return "NOM_PB_OK_TRI";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (03/05/07 11:06:33)
 * @author : Générateur de process
 */
public boolean performPB_OK_TRI(javax.servlet.http.HttpServletRequest request) throws Exception {
	if (getNOM_RB_TRI_MARQUE().equals(getZone(getNOM_RG_TRI()))){
		tri = "designationmarque";
	}
	if (getNOM_RB_TRI_MODELE().equals(getZone(getNOM_RG_TRI()))){
		tri = "designationmodele";
	}
	return true;
}
/**
 * Retourne le nom du groupe de radio boutons coché pour la JSP :
 * RG_TRI
 * Date de création : (03/05/07 11:06:33)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RG_TRI() {
	return "NOM_RG_TRI";
}
/**
 * Retourne la valeur du radio bouton (RB_) coché dans la JSP :
 * RG_TRI
 * Date de création : (03/05/07 11:06:33)
 * @author : Générateur de process
 */
public java.lang.String getVAL_RG_TRI() {
	return getZone(getNOM_RG_TRI());
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_MARQUE
 * Date de création : (03/05/07 11:06:33)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_MARQUE() {
	return "NOM_RB_TRI_MARQUE";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_MODELE
 * Date de création : (03/05/07 11:06:33)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_MODELE() {
	return "NOM_RB_TRI_MODELE";
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
	return getNOM_EF_RECHERCHE();
}
/**
 * @return Renvoie listeEquipementInfos.
 */
private ArrayList<PMatInfos> getListPMatInfos() {
	return listPMatInfos;
}
/**
 * @param listeEquipementInfos listeEquipementInfos à définir.
 */
private void setListPMatInfos(ArrayList<PMatInfos> listPMatInfos) {
	this.listPMatInfos = listPMatInfos;
}
/**
 * @return Renvoie pMatInfosCourant.
 */
private PMatInfos getPMatInfosCourant() {
	return pMatInfosCourant;
}
/**
 * @param pMatInfosCourant pMatInfosCourant à définir.
 */
private void setFirst(boolean isFirst) {
	this.isFirst = isFirst;
}

/**
 * @param pMatInfosCourant pMatInfosCourant à définir.
 */
private void setPMatInfosCourant(PMatInfos pMatInfosCourant) {
	this.pMatInfosCourant = pMatInfosCourant;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_AGENT
 * Date de création : (10/05/07 11:39:36)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_AGENT() {
	return "NOM_EF_AGENT";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_AGENT
 * Date de création : (10/05/07 11:39:36)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_AGENT() {
	return getZone(getNOM_EF_AGENT());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_SCE
 * Date de création : (10/05/07 11:39:36)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_SCE() {
	return "NOM_EF_SCE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_SCE
 * Date de création : (10/05/07 11:39:36)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_SCE() {
	return getZone(getNOM_EF_SCE());
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (03/05/07 11:01:05)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_OK_TRI
		if (testerParametre(request, getNOM_PB_OK_TRI())) {
			return performPB_OK_TRI(request);
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
 * Date de création : (10/05/07 12:50:55)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OePMateriel_Recherche.jsp";
}
}
