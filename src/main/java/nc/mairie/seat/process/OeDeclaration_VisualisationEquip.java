package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.Agents;
import nc.mairie.seat.metier.AgentsMunicipaux;
import nc.mairie.seat.metier.Declarations;
import nc.mairie.seat.metier.Entretien;
import nc.mairie.seat.metier.Equipement;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.PePerso;
import nc.mairie.technique.*;
/**
 * Process OeDeclaration_VisualisationEquip
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
*/
public class OeDeclaration_VisualisationEquip extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1304228634346293682L;
	public static final int STATUT_RECHERCHE = 1;
	public static final int STATUT_RECH_PM = 2;
	private java.lang.String[] LB_DECLARATIONS;
	private java.lang.String[] LB_ENTRETIENS;
	private EquipementInfos equipementInfosCourant;
	private PMatInfos pMatInfosCourant;
	private Declarations declarationCourante;
	private PePerso pepersoCourant;
	private ArrayList<Declarations> listeDeclarations;
	private ArrayList<Entretien> listEntretiens;
	private boolean first=true;
	private boolean isDeclaration; 
	private String focus = null;
	private boolean isMateriel = false;
	
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	
	if((first)||(etatStatut()==STATUT_RECHERCHE)||(etatStatut()==STATUT_RECH_PM)){
		isMateriel = false;
		String recup = (String)VariableActivite.recuperer(this,"TYPE");
		if("equipement".equals(recup)){
			EquipementInfos unEquipementInfos = (EquipementInfos)VariableGlobale.recuperer(request,"EQUIPEMENTINFOS");
			if(unEquipementInfos!=null){
				setEquipementInfosCourant(unEquipementInfos);
			}
		}else if("pmateriel".equals(recup)){
			isMateriel = true;
			PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request,"PMATINFOS");
			if(unPMatInfos!=null){
				if(unPMatInfos.getPminv()!=null){
					setPMatInfosCourant(unPMatInfos);
				}
			}
		}else{
			EquipementInfos unEquipementInfos = (EquipementInfos)VariableGlobale.recuperer(request,"EQUIPEMENTINFOS");
			if(unEquipementInfos!=null){
				if(unEquipementInfos.getNumeroinventaire()!=null){
					setEquipementInfosCourant(unEquipementInfos);
				}else{
					PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request,"PMATINFOS");
					if(unPMatInfos!=null){
						setPMatInfosCourant(unPMatInfos);
					}
				}
			}else{
				PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request,"PMATINFOS");
				if(unPMatInfos!=null){
					setPMatInfosCourant(unPMatInfos);
				}
			}
		}
		
	}
	
	if(!getZone(getNOM_EF_RECHERCHE_EQUIP()).equals("")){
		addZone(getNOM_ST_ANOMALIES(),"");
		performPB_RECHERCHE_EQUIP(request);
	}
	if(!isMateriel){
		if(getEquipementInfosCourant()!=null){
			if(getEquipementInfosCourant().getNumeroinventaire()!=null){
				//on renseigne les zones
				addZone(getNOM_ST_TYPE(),getEquipementInfosCourant().getDesignationtypeequip());
				addZone(getNOM_ST_NOINV(),getEquipementInfosCourant().getNumeroinventaire());
				addZone(getNOM_ST_NOIMMAT(),getEquipementInfosCourant().getNumeroimmatriculation());
				addZone(getNOM_ST_MODELE(),getEquipementInfosCourant().getDesignationmodele());
				addZone(getNOM_ST_MARQUE(),getEquipementInfosCourant().getDesignationmarque());
				initialiseListDeclarations(request);
				
			}
		}
	}else{
		if(getPMatInfosCourant()!=null){
			if(getPMatInfosCourant().getPminv()!=null){
				//on renseigne les zones
				addZone(getNOM_ST_TYPE(),getPMatInfosCourant().getDesignationtypeequip());
				addZone(getNOM_ST_NOINV(),getPMatInfosCourant().getPminv());
				addZone(getNOM_ST_NOIMMAT(),getPMatInfosCourant().getPmserie());
				addZone(getNOM_ST_MODELE(),getPMatInfosCourant().getDesignationmodele());
				addZone(getNOM_ST_MARQUE(),getPMatInfosCourant().getDesignationmarque());
				initialiseListDeclarations(request);
				
			}
		}
	}
	
	setFirst(false);
}
public void initialiseListDeclarations(javax.servlet.http.HttpServletRequest request) throws Exception{
	String declarant = "agent non trouvé";
	ArrayList<Declarations> listDeclarations = new ArrayList<Declarations>();
	if(!isMateriel){
		listDeclarations = Declarations.listerDeclarationsEquip(getTransaction(),getEquipementInfosCourant().getNumeroinventaire());
	}else{
		listDeclarations = Declarations.listerDeclarationsEquip(getTransaction(),getPMatInfosCourant().getPminv());
	}
	if(getTransaction().isErreur()){
		return ;
	}
	String[] nomChamps = {"date"};
	boolean[] ordre = {true};
	listDeclarations = Services.trier(listDeclarations,nomChamps,ordre);
	setListeDeclarations(listDeclarations);
	if(getListeDeclarations().size()>0){
		int tailles [] = {10,10,30};
		String[] padding = {"C","C","G"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for(int i = 0;i<getListeDeclarations().size();i++){
			Declarations uneDeclaration = (Declarations)getListeDeclarations().get(i);
			if(uneDeclaration.getCodeservice()!=null){
				AgentsMunicipaux unAgent = AgentsMunicipaux.chercherAgentsMunicipauxService(getTransaction(),uneDeclaration.getMatricule(),uneDeclaration.getCodeservice());
				if(getTransaction().isErreur()){
					getTransaction().traiterErreur();
					declarant = "agent non trouve";
				}else{
					declarant = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
				}
//				if(uneDeclaration.getCodeservice().equals("4000")){
//					AgentCDE unAgent = AgentCDE.chercherAgentCDE(getTransaction(),uneDeclaration.getMatricule());
//					if(getTransaction().isErreur()){
//						getTransaction().traiterErreur() ;
//						declarant = "agent non trouvé";
//					}else{
//						declarant = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
//					}
//					//String ligne [] = { uneDeclaration.getDate(),uneDeclaration.getCodeot(),unAgent.getNom().trim()+" "+unAgent.getPrenom().trim()};
//					//aFormat.ajouteLigne(ligne);
//				}else if(uneDeclaration.getCodeservice().equals("5000")){
//					AgentCCAS unAgent = AgentCCAS.chercherAgentCCAS(getTransaction(),uneDeclaration.getMatricule());
//					if(getTransaction().isErreur()){
//						getTransaction().traiterErreur() ;
//						declarant = "agent non trouvé";
//					}else{
//						declarant = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
//					}
//					//String ligne [] = { uneDeclaration.getDate(),uneDeclaration.getCodeot(),unAgent.getNom().trim()+" "+unAgent.getPrenom().trim()};
//					//aFormat.ajouteLigne(ligne);
//				}else{
//					Agents unAgent = Agents.chercherAgents(getTransaction(),uneDeclaration.getMatricule());
//					if(getTransaction().isErreur()){
//						getTransaction().traiterErreur() ;
//						declarant = "agent non trouvé";
//					}else{
//						declarant = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
//					}
//					//String ligne [] = { uneDeclaration.getDate(),uneDeclaration.getCodeot(),unAgent.getNom().trim()+" "+unAgent.getPrenom().trim()};
//					//aFormat.ajouteLigne(ligne);
//				}
				String ligne [] = { uneDeclaration.getDate(),uneDeclaration.getCodeot(),declarant};
				aFormat.ajouteLigne(ligne);
			}else{
				Agents unAgent = Agents.chercherAgents(getTransaction(),uneDeclaration.getMatricule());
				if(getTransaction().isErreur()){
					getTransaction().traiterErreur() ;
					declarant = "agent non trouvé";
				}else{
					declarant = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
				}
				String ligne [] = { uneDeclaration.getDate(),uneDeclaration.getCodeot(),declarant};
				aFormat.ajouteLigne(ligne);
			}
			
		}
		setLB_DECLARATIONS(aFormat.getListeFormatee());
	}else{
		setLB_DECLARATIONS(LBVide);
	}
}

/**
 * Constructeur du process OeDeclaration_VisualisationEquip.
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 */
public OeDeclaration_VisualisationEquip() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_DETAILS
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_DETAILS() {
	return "NOM_PB_DETAILS";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_DETAILS(javax.servlet.http.HttpServletRequest request) throws Exception {
	addZone(getNOM_ST_ANOMALIES(),"");
	// on récupère la Déclaration sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_DECLARATIONS_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_DECLARATIONS_SELECT())) : -1);
	if (numligne == -1 || getListeDeclarations().size() == 0 || numligne > getListeDeclarations().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Déclarations"));
		return false;
	}
	Declarations uneDeclaration = (Declarations)getListeDeclarations().get(numligne);
	
	addZone(getNOM_ST_ANOMALIES(),uneDeclaration.getAnomalies());
	//initialiseListEntretiens(request);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
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
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_RECHERCHE(javax.servlet.http.HttpServletRequest request) throws Exception {
	VariableActivite.ajouter(this,"MODE","DECL_VISU");
	setStatut(STATUT_RECHERCHE,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE_EQUIP
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_RECHERCHE_EQUIP() {
	return "NOM_PB_RECHERCHE_EQUIP";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_RECHERCHE_EQUIP(javax.servlet.http.HttpServletRequest request) throws Exception {
	//on vide les zones
	addZone(getNOM_ST_TYPE(),"");
	addZone(getNOM_ST_NOINV(),"");
	addZone(getNOM_ST_NOIMMAT(),"");
	addZone(getNOM_ST_MODELE(),"");
	addZone(getNOM_ST_MARQUE(),"");	
	setLB_DECLARATIONS(LBVide);
	
	setLB_ENTRETIENS(LBVide);
	setListeDeclarations(new ArrayList<Declarations>());
	setListEntretiens(new ArrayList<Entretien>());
	setEquipementInfosCourant(new EquipementInfos());
			
	//recherche l'équipement voulu
	String param = getZone(getNOM_EF_RECHERCHE_EQUIP());
	Equipement unEquipement = Equipement.chercherEquipement(getTransaction(),param);
	if(getTransaction().isErreur()){
		getTransaction().traiterErreur();
		PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),param);
		if(getTransaction().isErreur()){
			return false;
		}
		isMateriel = true;
		setPMatInfosCourant(unPMatInfos);
	}else{
		isMateriel = false;
	}
	if(!isMateriel){
		EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfos(getTransaction(),unEquipement.getNumeroinventaire());
		if(getTransaction().isErreur()){
			return false;
		}
		if(unEquipementInfos!=null){
			setEquipementInfosCourant(unEquipementInfos);
		}else{
			getTransaction().declarerErreur("Pas d'équipement trouvé pour ce numéro");
			return false;
		}
	}
	if(null==getEquipementInfosCourant()){
		setEquipementInfosCourant(new EquipementInfos());
	}
	if(null==getPMatInfosCourant()){
		setPMatInfosCourant(new PMatInfos());
	}
	addZone(getNOM_EF_RECHERCHE_EQUIP(),"");
	//pour garder le même équipement
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODELE
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_MODELE() {
	return "NOM_ST_MODELE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODELE
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_MODELE() {
	return getZone(getNOM_ST_MODELE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOIMMAT() {
	return "NOM_ST_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOIMMAT() {
	return getZone(getNOM_ST_NOIMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINV
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOINV() {
	return "NOM_ST_NOINV";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINV
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOINV() {
	return getZone(getNOM_ST_NOINV());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_RECHERCHE_EQUIP
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_RECHERCHE_EQUIP() {
	return "NOM_EF_RECHERCHE_EQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_RECHERCHE_EQUIP
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_RECHERCHE_EQUIP() {
	return getZone(getNOM_EF_RECHERCHE_EQUIP());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_DECLARATIONS
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 */
private String [] getLB_DECLARATIONS() {
	if (LB_DECLARATIONS == null)
		LB_DECLARATIONS = initialiseLazyLB();
	return LB_DECLARATIONS;
}
/**
 * Setter de la liste:
 * LB_DECLARATIONS
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 */
private void setLB_DECLARATIONS(java.lang.String[] newLB_DECLARATIONS) {
	LB_DECLARATIONS = newLB_DECLARATIONS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_DECLARATIONS
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_DECLARATIONS() {
	return "NOM_LB_DECLARATIONS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_DECLARATIONS_SELECT
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_DECLARATIONS_SELECT() {
	return "NOM_LB_DECLARATIONS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_DECLARATIONS
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_DECLARATIONS() {
	return getLB_DECLARATIONS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_DECLARATIONS
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_DECLARATIONS_SELECT() {
	return getZone(getNOM_LB_DECLARATIONS_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_ENTRETIENS
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 */
private String [] getLB_ENTRETIENS() {
	if (LB_ENTRETIENS == null)
		LB_ENTRETIENS = initialiseLazyLB();
	return LB_ENTRETIENS;
}
/**
 * Setter de la liste:
 * LB_ENTRETIENS
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 */
private void setLB_ENTRETIENS(java.lang.String[] newLB_ENTRETIENS) {
	LB_ENTRETIENS = newLB_ENTRETIENS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_ENTRETIENS
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_ENTRETIENS() {
	return "NOM_LB_ENTRETIENS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_ENTRETIENS_SELECT
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_ENTRETIENS_SELECT() {
	return "NOM_LB_ENTRETIENS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_ENTRETIENS
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_ENTRETIENS() {
	return getLB_ENTRETIENS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_ENTRETIENS
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_ENTRETIENS_SELECT() {
	return getZone(getNOM_LB_ENTRETIENS_SELECT());
}
	public Declarations getDeclarationCourante() {
		return declarationCourante;
	}
	public void setDeclarationCourante(Declarations declarationCourante) {
		this.declarationCourante = declarationCourante;
	}
	public EquipementInfos getEquipementInfosCourant() {
		return equipementInfosCourant;
	}
	public void setEquipementInfosCourant(EquipementInfos equipementInfosCourant) {
		this.equipementInfosCourant = equipementInfosCourant;
	}
	public PMatInfos getPMatInfosCourant() {
		return pMatInfosCourant;
	}
	public void setPMatInfosCourant(PMatInfos pMatInfosCourant) {
		this.pMatInfosCourant = pMatInfosCourant;
	}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
	public boolean isDeclaration() {
		return isDeclaration;
	}
	public void setDeclaration(boolean isDeclaration) {
		this.isDeclaration = isDeclaration;
	}
	public PePerso getPepersoCourant() {
		return pepersoCourant;
	}
	public void setPepersoCourant(PePerso pepersoCourant) {
		this.pepersoCourant = pepersoCourant;
	}
	public ArrayList<Declarations> getListeDeclarations() {
		return listeDeclarations;
	}
	public void setListeDeclarations(ArrayList<Declarations> listeDeclarations) {
		this.listeDeclarations = listeDeclarations;
	}
	public ArrayList<Entretien> getListEntretiens() {
		return listEntretiens;
	}
	public void setListEntretiens(ArrayList<Entretien> listEntretiens) {
		this.listEntretiens = listEntretiens;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_ANOMALIES
 * Date de création : (24/08/05 07:46:58)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_ANOMALIES() {
	return "NOM_ST_ANOMALIES";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_ANOMALIES
 * Date de création : (24/08/05 07:46:58)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_ANOMALIES() {
	return getZone(getNOM_ST_ANOMALIES());
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
	return getNOM_EF_RECHERCHE_EQUIP();
}

/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (23/08/05 14:25:28)
 * author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_RECH_PM
		if (testerParametre(request, getNOM_PB_RECH_PM())) {
			return performPB_RECH_PM(request);
		}

		//Si clic sur le bouton PB_DETAILS
		if (testerParametre(request, getNOM_PB_DETAILS())) {
			return performPB_DETAILS(request);
		}

		//Si clic sur le bouton PB_RECHERCHE
		if (testerParametre(request, getNOM_PB_RECHERCHE())) {
			return performPB_RECHERCHE(request);
		}

		//Si clic sur le bouton PB_RECHERCHE_EQUIP
		if (testerParametre(request, getNOM_PB_RECHERCHE_EQUIP())) {
			return performPB_RECHERCHE_EQUIP(request);
		}

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (18/09/07 13:33:39)
 * author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeDeclaration_VisualisationEquip.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECH_PM
 * Date de création : (18/09/07 13:33:39)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_RECH_PM() {
	return "NOM_PB_RECH_PM";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (18/09/07 13:33:39)
 * author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_RECH_PM(javax.servlet.http.HttpServletRequest request) throws Exception {
	VariableActivite.ajouter(this,"MODE","DECL_VISU");
	setStatut(STATUT_RECH_PM,true);
	return true;
}
}
