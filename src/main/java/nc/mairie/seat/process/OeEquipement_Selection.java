package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.AffectationServiceInfos;
import nc.mairie.seat.metier.AgentCCAS;
import nc.mairie.seat.metier.AgentCDE;
import nc.mairie.seat.metier.Agents;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.PM_Affectation_Sce_Infos;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.technique.*;
/**
 * Process OeEquipement_Recherche
 * Date de création : (08/06/05 09:15:54)
* 
*/
public class OeEquipement_Selection extends nc.mairie.technique.BasicProcess {
	public static final int STATUT_AGENT = 1;
	private java.lang.String[] LB_EQUIPEMENTINFOS;
	private ArrayList listeEquipementInfos;
	private EquipementInfos equipementInfosCourant;
	private PMatInfos pMatInfosCourant;
	private String focus = null;
	private boolean first = true;
	private String tri = "numeroinventaire";
	String mode = ""; 
	private boolean isMateriel;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (08/06/05 09:15:54)
* 
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
* 
 */
public OeEquipement_Selection() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (08/06/05 09:15:54)
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
 * Date de création : (08/06/05 09:15:54)
* 
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE
 * Date de création : (08/06/05 09:15:54)
* 
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
* 
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
		ArrayList resultatEquipementInfos = EquipementInfos.chercherListEquipementInfos(getTransaction(),param);
		if(getTransaction().isErreur()){
			getTransaction().traiterErreur();
		}
		if(resultatEquipementInfos!=null){
			if(resultatEquipementInfos.size()>0){
				isMateriel = false;
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
		}else{
			ArrayList listPMateriel = PMatInfos.chercherListPMatInfosTous(getTransaction(),param);
			if(getTransaction().isErreur()){
				System.out.println("Aucun résultat trouvé in OeEquipement_Selection");
				getTransaction().declarerErreur("Aucun résultat trouvé.");
				return false;
			}
			if(listPMateriel!=null){
				if(listPMateriel.size()>0){
					isMateriel = true;
					// on remplit la liste des équipements
					setListeEquipementInfos(listPMateriel);
					if(listPMateriel.size()>0){
						//les élèments de la liste 
						int [] tailles = {10,15,15,15,5,10};
						String [] champs = {"pminv","pmserie","designationmarque","designationmodele","designationtypeequip","dmes"};
						//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
						String [] padding = {"D","D","G","G","G","C"};
						
						setLB_EQUIPEMENTINFOS(new FormateListe(tailles,resultatEquipementInfos,champs,padding,true).getListeFormatee());
					}else{
						setLB_EQUIPEMENTINFOS(LBVide);
					}
				}
			}
		}

	}
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (08/06/05 09:15:54)
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
 * Date de création : (08/06/05 09:15:54)
* 
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
		monEquipementInfos = new EquipementInfos();
	}
	setEquipementInfosCourant(monEquipementInfos);
	
//	On met la variable globale
	if(!isMateriel){
		VariableGlobale.ajouter(request, "EQUIPEMENTINFOS", getEquipementInfosCourant());
		VariableGlobale.enlever(request,"PMATINFOS");
	}else{
		if(null==getPMatInfosCourant()){
			setPMatInfosCourant(new PMatInfos());
		}
		VariableGlobale.enlever(request,"EQUIPEMENTINFOS");
		VariableGlobale.ajouter(request, "PMATINFOS", getPMatInfosCourant());
	}
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_RECHERCHE
 * Date de création : (08/06/05 09:15:54)
* 
 */
public java.lang.String getNOM_EF_RECHERCHE() {
	return "NOM_EF_RECHERCHE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_RECHERCHE
 * Date de création : (08/06/05 09:15:54)
* 
 */
public java.lang.String getVAL_EF_RECHERCHE() {
	return getZone(getNOM_EF_RECHERCHE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_EQUIPEMENTINFOS
 * Date de création : (08/06/05 09:15:54)
* 
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
* 
 */
private void setLB_EQUIPEMENTINFOS(java.lang.String[] newLB_EQUIPEMENTINFOS) {
	LB_EQUIPEMENTINFOS = newLB_EQUIPEMENTINFOS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_EQUIPEMENTINFOS
 * Date de création : (08/06/05 09:15:54)
* 
 */
public java.lang.String getNOM_LB_EQUIPEMENTINFOS() {
	return "NOM_LB_EQUIPEMENTINFOS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_EQUIPEMENT_SELECT
 * Date de création : (08/06/05 09:15:54)
* 
 */
public java.lang.String getNOM_LB_EQUIPEMENTINFOS_SELECT() {
	return "NOM_LB_EQUIPEMENTINFOS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_EQUIPEMENT
 * Date de création : (08/06/05 09:15:54)
* 
 */
public java.lang.String [] getVAL_LB_EQUIPEMENTINFOS() {
	return getLB_EQUIPEMENTINFOS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_EQUIPEMENT
 * Date de création : (08/06/05 09:15:54)
* 
 */
public java.lang.String getVAL_LB_EQUIPEMENTINFOS_SELECT() {
	return getZone(getNOM_LB_EQUIPEMENTINFOS_SELECT());
}
	/**
	 * @return Renvoie listeEquipementInfos.
	 */
	private ArrayList getListeEquipementInfos() {
		return listeEquipementInfos;
	}
	/**
	 * @param listeEquipementInfos listeEquipementInfos à définir.
	 */
	private void setListeEquipementInfos(ArrayList listeEquipementInfos) {
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
	 * @return Renvoie pMatInfosCourant.
	 */
	private PMatInfos getPMatInfosCourant() {
		return pMatInfosCourant;
	}
	/**
	 * @param pMatInfosCourant pMatInfosCourant à définir.
	 */
	private void setPMatInfosCourant(
			PMatInfos pMatInfosCourant) {
		this.pMatInfosCourant = pMatInfosCourant;
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
	ArrayList listAgent = new ArrayList();
	boolean trouveEquip = false;
	ArrayList listInter = new ArrayList();
	boolean trouve = false;
	String nom = "";
	boolean[] ordres = {true};//,true};
	String[] colonnes = {tri};
	ArrayList listEquip = new ArrayList();
	String param = getZone(getNOM_EF_AGENT());
	ArrayList listFinal = new ArrayList();
//	if (!Services.estNumerique(param)){
//		getTransaction().declarerErreur("Le code de l'agent est numérique.");
//		return false;
//	}
	if(Services.estNumerique(getZone(getNOM_EF_AGENT()))){
		ArrayList listeASI = AffectationServiceInfos.listerAffectationServiceInfosAgent(getTransaction(),param);
		if(getTransaction().isErreur()){
//			getTransaction().declarerErreur("L'agent n'est responsable d'aucun équipement.");
//			return false;
			getTransaction().traiterErreur();
		}
		if(listeASI!=null){
			if (listeASI.size()>0){
				isMateriel = false;
				trouveEquip = true;
				for (int i=0;i<listeASI.size();i++){
					AffectationServiceInfos unASI = (AffectationServiceInfos)listeASI.get(i);
					EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfos(getTransaction(),unASI.getNumeroinventaire());
					if(getTransaction().isErreur()){
						getTransaction().declarerErreur("L'équipement n'a pas été trouvé.");
						return false;
					}
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
			}else{
				trouveEquip = false;
			}
		}else{
			trouveEquip = false;
		}
		// on recherche dans les PMateriels
		if(!trouveEquip){
			ArrayList listPMASI  =PM_Affectation_Sce_Infos.listerPmAffectationSceInfosAgent(getTransaction(),param);
			if(getTransaction().isErreur()){
				getTransaction().declarerErreur("Cet agent n'est responsable d'aucun équipement.");
				return false;
			}
			if(listPMASI!=null){
				if(listPMASI.size()>0){
					isMateriel = true;
					for (int i=0;i<listPMASI.size();i++){
						PM_Affectation_Sce_Infos unPMASI = (PM_Affectation_Sce_Infos)listPMASI.get(i);
						PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),unPMASI.getPminv());
						if(getTransaction().isErreur()){
							getTransaction().declarerErreur("L'équipement n'a pas été trouvé.");
							return false;
						}
					}
				}
			}
		}
	}else{
		if(Services.estAlphabetique(getZone(getNOM_EF_AGENT()))){
			nom = getZone(getNOM_EF_AGENT()).toUpperCase();
			if(getZone(getNOM_EF_AGENT()).equals("")){//||getZone(getNOM_EF_SERVICE()).equals("")){
				getTransaction().declarerErreur("Vous devez saisir le nom de l'agent ");
				return false;
			}
				listInter = AgentCDE.chercherAgentCDENom(getTransaction(),nom);
				if(getTransaction().isErreur()){
					getTransaction().traiterErreur();
					trouve = false;
				}else{
					trouve = true;
				}
				if (listInter.size()>0){
					for (int i=0;i<listInter.size();i++){
						listAgent.add(listInter.get(i));
					}
				}
				listInter = AgentCCAS.chercherAgentCCASNom(getTransaction(),nom);
				if(getTransaction().isErreur()){
					getTransaction().traiterErreur();
					trouve = false;
				}else{
					trouve = true;
				}
				if (listInter.size()>0){
					for (int i=0;i<listInter.size();i++){
						listAgent.add(listInter.get(i));
					}
				}
				listInter = Agents.chercherAgentsNom(getTransaction(),nom);
				if(getTransaction().isErreur()){
					getTransaction().traiterErreur();
					trouve = false;
				}else{
					trouve = true;
				}
				if (listInter.size()>0){
					for (int i=0;i<listInter.size();i++){
						listAgent.add(listInter.get(i));
					}
				}
			if (trouve){
				if (listAgent.size()==1){
					Agents unAgent = (Agents)listAgent.get(0);
					// s'il n'a qu'un équipement on débranche directement sur la fenêtre visu sinon on les affiche
					ArrayList a = AffectationServiceInfos.listerAffectationServiceInfosAgent(getTransaction(),unAgent.getNomatr());
					if(getTransaction().isErreur()){
						getTransaction().traiterErreur();
					}
					if (a.size()==1){
						AffectationServiceInfos monASI = (AffectationServiceInfos)a.get(0);
						EquipementInfos unEI = EquipementInfos.chercherEquipementInfos(getTransaction(),monASI.getNumeroinventaire());
						if(getTransaction().isErreur()){
							return false;
						}
						setEquipementInfosCourant(unEI);
					}else if(a.size()>1){
						
						for(int i=0;i<a.size();i++){
							AffectationServiceInfos monASI = (AffectationServiceInfos)a.get(0);
							EquipementInfos unEI = EquipementInfos.chercherEquipementInfos(getTransaction(),monASI.getNumeroinventaire());
							if(getTransaction().isErreur()){
								return false;
							}
							listFinal.add(unEI);
						}
						setListeEquipementInfos(listFinal);
					}
				}else{
					// si plusieurs on affiche la liste du résultat des agents
					VariableActivite.ajouter(this,"NOM",nom);
					VariableActivite.ajouter(this,"MODE",mode);
					VariableActivite.ajouter(this,"TYPE","Equipement");
					setStatut(STATUT_AGENT,true);
				}
			}
		}
	}
	
	//addZone(getNOM_EF_AGENT(),"");
	return true;
}

public boolean recherche_SERVICE(javax.servlet.http.HttpServletRequest request) throws Exception {
	boolean[] ordres = {true};//,true};
	String[] colonnes = {tri};
//	 recherche de la liste des équipements dont l'agent est responsable
	ArrayList listEquip = new ArrayList();
	String param = getZone(getNOM_EF_SERVICE().toUpperCase());
	/* Modif par luc le 08/01/10
	if (!Services.estNumerique(param)){
		getTransaction().declarerErreur("Le code de du service est numérique.");
		return false;
	}
	*/
	ArrayList listeASI = AffectationServiceInfos.chercherAffectationServiceInfosService(getTransaction(),param);
	if(getTransaction().isErreur()){
		getTransaction().declarerErreur("Le service n'a aucun équipement.");
		return false;
	}
	if (listeASI.size()>0){
		for (int i=1;i<listeASI.size();i++){
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
* 
 */
public java.lang.String getNOM_EF_AGENT() {
	return "NOM_EF_AGENT";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_AGENT
 * Date de création : (04/04/07 08:19:48)
* 
 */
public java.lang.String getVAL_EF_AGENT() {
	return getZone(getNOM_EF_AGENT());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_SERVICE
 * Date de création : (04/04/07 08:19:48)
* 
 */
public java.lang.String getNOM_EF_SERVICE() {
	return "NOM_EF_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_SERVICE
 * Date de création : (04/04/07 08:19:48)
* 
 */
public java.lang.String getVAL_EF_SERVICE() {
	return getZone(getNOM_EF_SERVICE());
}
/**
 * Retourne le nom du groupe de radio boutons coché pour la JSP :
 * RG_TRI
 * Date de création : (04/04/07 11:21:34)
* 
 */
public java.lang.String getNOM_RG_TRI() {
	return "NOM_RG_TRI";
}
/**
 * Retourne la valeur du radio bouton (RB_) coché dans la JSP :
 * RG_TRI
 * Date de création : (04/04/07 11:21:34)
* 
 */
public java.lang.String getVAL_RG_TRI() {
	return getZone(getNOM_RG_TRI());
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_MARQUE
 * Date de création : (04/04/07 11:21:34)
* 
 */
public java.lang.String getNOM_RB_TRI_MARQUE() {
	return "NOM_RB_TRI_MARQUE";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_MODELE
 * Date de création : (04/04/07 11:21:34)
* 
 */
public java.lang.String getNOM_RB_TRI_MODELE() {
	return "NOM_RB_TRI_MODELE";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_TRI
 * Date de création : (04/04/07 11:26:29)
* 
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
* 
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
* 
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
* 
 */
public String getJSP() {
	return "OeEquipement_Recherche.jsp";
}
}
