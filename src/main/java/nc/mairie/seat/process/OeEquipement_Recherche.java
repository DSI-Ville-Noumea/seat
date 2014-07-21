package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.AffectationServiceInfos;
import nc.mairie.seat.metier.AgentCCAS;
import nc.mairie.seat.metier.AgentCDE;
import nc.mairie.seat.metier.AgentInterface;
import nc.mairie.seat.metier.Agents;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.technique.*;
/**
 * Process OeEquipement_Recherche
 * Date de création : (08/06/05 09:15:54)
 * @author : Générateur de process
*/
public class OeEquipement_Recherche extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -345090781855550852L;
	public static final int STATUT_AGENT = 1;
	private java.lang.String[] LB_EQUIPEMENTINFOS;
	private ArrayList<EquipementInfos> listeEquipementInfos;
	private EquipementInfos equipementInfosCourant;
	private String focus = null;
	private boolean first = true;
	private String tri = "numeroinventaire";
	String mode = ""; 
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (08/06/05 09:15:54)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	
	if((first)||(etatStatut()==1)){
		String param = (String)VariableActivite.recuperer(this,"PARAM");
		mode = (String) VariableActivite.recuperer(this,"MODE");
		VariableActivite.enlever(this,"MODE");
		addZone(getNOM_EF_RECHERCHE(),param);
		if((param!=null)&&(!param.equals(""))){
			performPB_RECHERCHE(request);
		}
		String nomatr = (String)VariableActivite.recuperer(this,"NOMATR");
		if ((nomatr!=null)&&(!nomatr.equals(""))){
			addZone(getNOM_EF_AGENT(),nomatr);
			VariableActivite.enlever(this,"NOMATR");
			recherche_AGENT(request);
		}
		String code_sce = (String)VariableActivite.recuperer(this,"CODESERVICE");
		if ((code_sce!=null)&&(!code_sce.equals(""))){
			addZone(getNOM_EF_SERVICE(),code_sce);
			VariableActivite.enlever(this,"CODESERVICE");
			recherche_SERVICE(request);
		}
		
	}
	if(getListeEquipementInfos()!=null){
		if(getListeEquipementInfos().size()==0){
			getTransaction().declarerErreur("Aucun équipement n'a été enregistré.");
			return ;
		}
	}
	setFirst(false);
	performPB_RECHERCHE(request);
//	if(!getZone(getNOM_EF_AGENT()).equals("")){
//		performPB_AGENT(request);
//		
//	}
//	if(!getZone(getNOM_EF_SERVICE()).equals("")){
//		performPB_SERVICE(request);
//	}
	
}
/**
 * Constructeur du process OeEquipement_Recherche.
 * Date de création : (08/06/05 09:15:54)
 * @author : Générateur de process
 */
public OeEquipement_Recherche() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (08/06/05 09:15:54)
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
 * Date de création : (08/06/05 09:15:54)
 * @author : Générateur de process
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
 * PB_RECHERCHE
 * Date de création : (08/06/05 09:15:54)
 * @author : Générateur de process
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
 * Date de création : (08/06/05 09:15:54)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_RECHERCHE(javax.servlet.http.HttpServletRequest request) throws Exception {
	setLB_EQUIPEMENTINFOS(LBVide);
	// pour l'agent
	if(!(getZone(getNOM_EF_AGENT())).equals("")){
		recherche_AGENT(request);
	}else if(!(getZone(getNOM_EF_SERVICE())).equals("")){
		recherche_SERVICE(request);
	}else{
		String param = getZone(getNOM_EF_RECHERCHE());
		ArrayList<EquipementInfos> resultatEquipementInfos = EquipementInfos.chercherListEquipementInfos(getTransaction(),param);
		if(getTransaction().isErreur()){
			return false;
		}
		
		// on remplit la liste des équipements
		setListeEquipementInfos(resultatEquipementInfos);
		if(resultatEquipementInfos.size()>0){
			//les élèments de la liste 
			int [] tailles = {10,15,15,15,5,10};
			String [] champs = {"numeroinventaire","numeroimmatriculation","designationmarque","designationmodele","designationtypeequip","datemiseencirculation"};
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"D","D","G","G","G","C"};
			
			setLB_EQUIPEMENTINFOS(new FormateListe(tailles,resultatEquipementInfos,champs,padding,true).getListeFormatee());
		}else{
			setLB_EQUIPEMENTINFOS(LBVide);
		}
	}
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (08/06/05 09:15:54)
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
 * Date de création : (08/06/05 09:15:54)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_EQUIPEMENTINFOS_SELECT()) ? Integer.parseInt(getVAL_LB_EQUIPEMENTINFOS_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	EquipementInfos monEquipementInfos = (EquipementInfos)getListeEquipementInfos().get(indice);
//	if (getTransaction().isErreur()){
//		return false;
//	}
	if(null==monEquipementInfos){
		monEquipementInfos =  new EquipementInfos();
	}
	setEquipementInfosCourant(monEquipementInfos);
	
//	On met la variable globale
	VariableActivite.ajouter(this,"TYPE","equipement");
	VariableGlobale.ajouter(request, "EQUIPEMENTINFOS", getEquipementInfosCourant());
	VariableGlobale.ajouter(request,"PMATINFOS",new PMatInfos());
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_RECHERCHE
 * Date de création : (08/06/05 09:15:54)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_RECHERCHE() {
	return "NOM_EF_RECHERCHE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_RECHERCHE
 * Date de création : (08/06/05 09:15:54)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_RECHERCHE() {
	return getZone(getNOM_EF_RECHERCHE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_EQUIPEMENTINFOS
 * Date de création : (08/06/05 09:15:54)
 * @author : Générateur de process
 */
private String [] getLB_EQUIPEMENTINFOS() {
	if (LB_EQUIPEMENTINFOS == null)
		LB_EQUIPEMENTINFOS = initialiseLazyLB();
	return LB_EQUIPEMENTINFOS;
}
/**
 * Setter de la liste:
 * LB_EQUIPEMENT
 * Date de création : (08/06/05 09:15:54)
 * @author : Générateur de process
 */
private void setLB_EQUIPEMENTINFOS(java.lang.String[] newLB_EQUIPEMENTINFOS) {
	LB_EQUIPEMENTINFOS = newLB_EQUIPEMENTINFOS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_EQUIPEMENTINFOS
 * Date de création : (08/06/05 09:15:54)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_EQUIPEMENTINFOS() {
	return "NOM_LB_EQUIPEMENTINFOS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_EQUIPEMENT_SELECT
 * Date de création : (08/06/05 09:15:54)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_EQUIPEMENTINFOS_SELECT() {
	return "NOM_LB_EQUIPEMENTINFOS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_EQUIPEMENT
 * Date de création : (08/06/05 09:15:54)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_EQUIPEMENTINFOS() {
	return getLB_EQUIPEMENTINFOS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_EQUIPEMENT
 * Date de création : (08/06/05 09:15:54)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_EQUIPEMENTINFOS_SELECT() {
	return getZone(getNOM_LB_EQUIPEMENTINFOS_SELECT());
}
	/**
	 * @return Renvoie listeEquipementInfos.
	 */
	private ArrayList<EquipementInfos> getListeEquipementInfos() {
		return listeEquipementInfos;
	}
	/**
	 * @param listeEquipementInfos listeEquipementInfos à définir.
	 */
	private void setListeEquipementInfos(ArrayList<EquipementInfos> listeEquipementInfos) {
		this.listeEquipementInfos = listeEquipementInfos;
	}
	/**
	 * @return Renvoie equipementInfosCourant.
	 */
	private EquipementInfos getEquipementInfosCourant() {
		return equipementInfosCourant;
	}
	/**
	 * @param equipementInfosCourant equipementInfosCourant à définir.
	 */
	private void setEquipementInfosCourant(
			EquipementInfos equipementInfosCourant) {
		this.equipementInfosCourant = equipementInfosCourant;
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
		return getNOM_EF_RECHERCHE();
	}
	
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
public boolean recherche_AGENT(javax.servlet.http.HttpServletRequest request) throws Exception {
	// recherche de la liste des équipements dont l'agent est responsable
	ArrayList<AgentInterface> listAgent = new ArrayList<AgentInterface>();
	String nom = "";
	boolean[] ordres = {true};//,true};
	String[] colonnes = {tri};
	ArrayList<EquipementInfos> listEquip = new ArrayList<EquipementInfos>();
	String param = getZone(getNOM_EF_AGENT());
//	if (!Services.estNumerique(param)){
//		getTransaction().declarerErreur("Le code de l'agent est numérique.");
//		return false;
//	}
	if(Services.estNumerique(getZone(getNOM_EF_AGENT()))){
		ArrayList<AffectationServiceInfos> listeASI = AffectationServiceInfos.listerAffectationServiceInfosAgent(getTransaction(),param);
		if(getTransaction().isErreur()){
			getTransaction().declarerErreur("L'agent n'est responsable d'aucun équipement.");
			return false;
		}
		if (listeASI.size()>0){
			for (int i=0;i<listeASI.size();i++){
				AffectationServiceInfos unASI = (AffectationServiceInfos)listeASI.get(i);
				EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfos(getTransaction(),unASI.getNumeroinventaire());
				listEquip.add(unEquipementInfos);
			}
			listEquip = Services.trier(listEquip,colonnes,ordres);
			// on remplit la liste des équipements
			setListeEquipementInfos(listEquip);
			if(listEquip.size()>0){
				//les élèments de la liste 
				int [] tailles = {10,15,15,15,5,10};
				String [] champs = {"numeroinventaire","numeroimmatriculation","designationmarque","designationmodele","designationtypeequip","datemiseencirculation"};
				//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
				String [] padding = {"D","D","G","G","G","C"};
				
				setLB_EQUIPEMENTINFOS(new FormateListe(tailles,listEquip,champs,padding,true).getListeFormatee());
			}else{
				setLB_EQUIPEMENTINFOS(LBVide);
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
				listAgent.addAll( Agents.listerAgentsNom(getTransaction(),nom));
			
				if (listAgent.size()==1){
					Agents unAgent = (Agents)listAgent.get(0);
					// s'il n'a qu'un équipement on débranche directement sur la fenêtre visu sinon on les affiche
					ArrayList<AffectationServiceInfos> a = AffectationServiceInfos.listerAffectationServiceInfosAgent(getTransaction(),unAgent.getNomatr());
					if(getTransaction().isErreur()){
						return false;
					}
					if (a.size()==1){
						AffectationServiceInfos monASI = (AffectationServiceInfos)a.get(0);
						EquipementInfos unEI = EquipementInfos.chercherEquipementInfos(getTransaction(),monASI.getNumeroinventaire());
						if(getTransaction().isErreur()){
							return false;
						}
						setEquipementInfosCourant(unEI);
					}
				}else{
					// si plusieurs on affiche la liste du résultat des agents
					VariableActivite.ajouter(this,"NOM",nom);
					VariableActivite.ajouter(this,"MODE",mode);
					VariableActivite.ajouter(this,"TYPE","equipement");
					setStatut(STATUT_AGENT,true);
				}
		}
	}
	
	//addZone(getNOM_EF_AGENT(),"");
	return true;
}

public boolean recherche_SERVICE(javax.servlet.http.HttpServletRequest request) throws Exception {
	boolean[] ordres = {true};//,true};
	String[] colonnes = {tri};
//	 recherche de la liste des équipements dont le service est responsable
	ArrayList<EquipementInfos> listEquip = new ArrayList<EquipementInfos>();
	String param = getZone(getNOM_EF_SERVICE().toUpperCase());
	/* Modif par luc le 08/01/10
	if (!Services.estNumerique(param)){
		getTransaction().declarerErreur("Le code de du service est numérique.");
		return false;
	}
	*/
	ArrayList<AffectationServiceInfos> listeASI = AffectationServiceInfos.chercherAffectationServiceInfosService(getTransaction(),param);
	if(getTransaction().isErreur()){
		getTransaction().declarerErreur("Le service n'a aucun équipement.");
		return false;
	}
	if (listeASI.size()>0){
		for (int i=0;i<listeASI.size();i++){
			AffectationServiceInfos unASI = (AffectationServiceInfos)listeASI.get(i);
			EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfos(getTransaction(),unASI.getNumeroinventaire());
			listEquip.add(unEquipementInfos);
		}
	}
	listEquip = Services.trier(listEquip,colonnes,ordres);
	// on remplit la liste des équipements
	setListeEquipementInfos(listEquip);
	if(listEquip.size()>0){
		//les élèments de la liste 
		int [] tailles = {10,15,15,15,5,10};
		String [] champs = {"numeroinventaire","numeroimmatriculation","designationmarque","designationmodele","designationtypeequip","datemiseencirculation"};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"D","D","G","G","G","C"};
		
		setLB_EQUIPEMENTINFOS(new FormateListe(tailles,listEquip,champs,padding,true).getListeFormatee());
	}else{
		setLB_EQUIPEMENTINFOS(LBVide);
	}
	//addZone(getNOM_EF_SERVICE(),"");
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_AGENT
 * Date de création : (04/04/07 08:19:48)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_AGENT() {
	return "NOM_EF_AGENT";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_AGENT
 * Date de création : (04/04/07 08:19:48)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_AGENT() {
	return getZone(getNOM_EF_AGENT());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_SERVICE
 * Date de création : (04/04/07 08:19:48)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_SERVICE() {
	return "NOM_EF_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_SERVICE
 * Date de création : (04/04/07 08:19:48)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_SERVICE() {
	return getZone(getNOM_EF_SERVICE());
}
/**
 * Retourne le nom du groupe de radio boutons coché pour la JSP :
 * RG_TRI
 * Date de création : (04/04/07 11:21:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_RG_TRI() {
	return "NOM_RG_TRI";
}
/**
 * Retourne la valeur du radio bouton (RB_) coché dans la JSP :
 * RG_TRI
 * Date de création : (04/04/07 11:21:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_RG_TRI() {
	return getZone(getNOM_RG_TRI());
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_MARQUE
 * Date de création : (04/04/07 11:21:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_RB_TRI_MARQUE() {
	return "NOM_RB_TRI_MARQUE";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_MODELE
 * Date de création : (04/04/07 11:21:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_RB_TRI_MODELE() {
	return "NOM_RB_TRI_MODELE";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_TRI
 * Date de création : (04/04/07 11:26:29)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_TRI() {
	return "NOM_PB_TRI";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (04/04/07 11:26:29)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_TRI(javax.servlet.http.HttpServletRequest request) throws Exception {
	if (getNOM_RB_TRI_MARQUE().equals(getZone(getNOM_RG_TRI()))){
		tri = "designationmarque";
	}
	if (getNOM_RB_TRI_MODELE().equals(getZone(getNOM_RG_TRI()))){
		tri = "designationmodele";
	}
	return true;
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (08/06/05 09:15:54)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_TRI
		if (testerParametre(request, getNOM_PB_TRI())) {
			return performPB_TRI(request);
		}

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_RECHERCHE
		if (testerParametre(request, getNOM_PB_RECHERCHE())) {
			return performPB_RECHERCHE(request);
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
 * Date de création : (25/04/07 07:28:50)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeEquipement_Recherche.jsp";
}
}
