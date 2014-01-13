package nc.mairie.seat.process;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import nc.mairie.seat.metier.AffectationServiceInfos;
import nc.mairie.seat.metier.BPC;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.ModePrise; 
import nc.mairie.seat.metier.ModeleInfos;
import nc.mairie.seat.metier.PM_Affectation_Sce_Infos;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.Service;
import nc.mairie.technique.*;
/**
 * Process OeBPC
 * Date de création : (30/05/05 10:16:18)
 * @author : Générateur de process
*/
public class OeBPC extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6530377524666025884L;
	public static final int STATUT_PMATERIEL = 4;
	public static final int STATUT_AJOUTER = 1;
	public static final int STATUT_MODIFIER = 2;
	public static final int STATUT_RECHERCHE = 9;
	private java.lang.String[] LB_BPC;
	private String ACTION_SUPPRESSION = "Suppression d'un BPC.<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private ArrayList<BPC> listeBPC;
	private BPC bpcCourant;
	private EquipementInfos equipementInfosCourant;
	private PMatInfos pMatInfosCourant;
//	private Equipement equipementCourant;
//	private PMateriel pMaterielCourant;
	public int isVide = 0;
	private boolean isMateriel = false;
	private boolean first = true;
	
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (30/05/05 10:16:18)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	//if(etatStatut()==STATUT_RECHERCHE){
	if((first)||(etatStatut()==STATUT_RECHERCHE)||(etatStatut()==STATUT_PMATERIEL)){
		String recup = (String)VariableActivite.recuperer(this,"TYPE");
		if("equipement".equals(recup)){
			EquipementInfos unEquipementInfos = (EquipementInfos)VariableGlobale.recuperer(request, "EQUIPEMENTINFOS");
			setEquipementInfosCourant(unEquipementInfos);
			isMateriel = false;
			if(unEquipementInfos!=null){
				if(unEquipementInfos.getNumeroinventaire()==null){
					PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request, "PMATINFOS");
					setPMatInfosCourant(unPMatInfos);
					if(unPMatInfos!=null){
						isMateriel = true;
					}
				}
			}
		}else if("pmateriel".equals(recup)){
			PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request, "PMATINFOS");
			setPMatInfosCourant(unPMatInfos);
			if(unPMatInfos!=null){
				isMateriel = true;
			}
		}else{
			EquipementInfos unEquipementInfos = (EquipementInfos)VariableGlobale.recuperer(request, "EQUIPEMENTINFOS");
			setEquipementInfosCourant(unEquipementInfos);
			isMateriel = false;
			if(unEquipementInfos!=null){
				if(unEquipementInfos.getNumeroinventaire()==null){
					PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request, "PMATINFOS");
					setPMatInfosCourant(unPMatInfos);
					if(unPMatInfos!=null){
						isMateriel = true;
					}
				}
			}else{
				PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request, "PMATINFOS");
				setPMatInfosCourant(unPMatInfos);
				if(unPMatInfos!=null){
					isMateriel = true;
				}
			}
		}
		VariableActivite.enlever(this,"TYPE");
	}
	//}
//	 quand appuie sur entrée
	if(!("").equals(getZone(getNOM_EF_EQUIP()))){
		performPB_EQUIP(request);
		addZone(getNOM_EF_RECHERCHE(),"");
	}
	if(!isMateriel){
		if (null!=(getEquipementInfosCourant())){
			if(null!=getEquipementInfosCourant().getNumeroinventaire()){
				
//				Equipement unEquipement = Equipement.chercherEquipement(getTransaction(),getEquipementInfosCourant().getNumeroinventaire());
//				if(getTransaction().isErreur()){
//					return;
//				}
//				setEquipementCourant(unEquipement);
				String date = Services.dateDuJour();
				AffectationServiceInfos unAffectationServiceInfos = AffectationServiceInfos.chercherAffectationServiceInfosCourantEquip(getTransaction(),getEquipementInfosCourant().getNumeroinventaire(),date);
				if(getTransaction().isErreur()){
					//si pas trouvé, c'est fonctionellement normal, on traite l'erreur
					getTransaction().traiterErreur();
					addZone(getNOM_ST_SERVICE(),"pas affecté");
				}else{
					addZone(getNOM_ST_SERVICE(),unAffectationServiceInfos.getLiserv());
				}
				
				
			//	Si liste des BPC est vide
				//if (getLB_BPC() == LBVide) {
				initialiseListeBPC(request);
				//}
				addZone(getNOM_ST_TYPE(),getEquipementInfosCourant().getDesignationtypeequip());
				addZone(getNOM_ST_INVENTAIRE(),getEquipementInfosCourant().getNumeroinventaire());
				addZone(getNOM_ST_IMMAT(),getEquipementInfosCourant().getNumeroimmatriculation());
				addZone(getNOM_ST_NOMEQUIP(),getEquipementInfosCourant().getDesignationmarque()+" "+getEquipementInfosCourant().getDesignationmodele());
				addZone(getNOM_ST_CARBU(),getEquipementInfosCourant().getDesignationcarbu());
			}//else{
//				if(getPMatInfosCourant()!=null){
//					initialisePMateriel(request);
//					addZone(getNOM_ST_TYPE(),getPMatInfosCourant().getDesignationtypeequip());
//					addZone(getNOM_ST_INVENTAIRE(),getPMatInfosCourant().getPminv());
//					addZone(getNOM_ST_IMMAT(),getPMatInfosCourant().getPmserie());
//					addZone(getNOM_ST_NOMEQUIP(),getPMatInfosCourant().getDesignationmarque()+" "+getEquipementInfosCourant().getDesignationmodele());
//					addZone(getNOM_ST_CARBU(),"");
//				}
//			}
		}/*else{
			if(getPMatInfosCourant()!=null){
				initialisePMateriel(request);
			}else{
				setLB_BPC(LBVide);
				addZone(getNOM_ST_TYPE(),"");
				addZone(getNOM_ST_INVENTAIRE(),"");
				addZone(getNOM_ST_IMMAT(),"");
				addZone(getNOM_ST_NOMEQUIP(),"");
				addZone(getNOM_ST_CARBU(),"");
			}
		}*/
	}else{
		initialisePMateriel(request);
		addZone(getNOM_ST_TYPE(),getPMatInfosCourant().getDesignationtypeequip());
		addZone(getNOM_ST_INVENTAIRE(),getPMatInfosCourant().getPminv());
		addZone(getNOM_ST_IMMAT(),getPMatInfosCourant().getPmserie());
		addZone(getNOM_ST_NOMEQUIP(),getPMatInfosCourant().getDesignationmarque()+" "+getPMatInfosCourant().getDesignationmodele());
		addZone(getNOM_ST_CARBU(),"");
	}
	first = false;
}

private boolean initialisePMateriel(javax.servlet.http.HttpServletRequest request) throws Exception{
	if (null!=(getPMatInfosCourant())){
		if(null!=getPMatInfosCourant().getPminv()){
			
//			PMateriel unPMateriel = PMateriel.chercherPMateriel(getTransaction(),getPMatInfosCourant().getPminv());
//			if(getTransaction().isErreur()){
//				return false;
//			}
//			setPMaterielCourant(unPMateriel);
			String date = Services.dateDuJour();
			PM_Affectation_Sce_Infos unPmASI = PM_Affectation_Sce_Infos.chercherPM_Affectation_Sce_InfosCourantPm(getTransaction(),getPMatInfosCourant().getPminv(),date);
			if(getTransaction().isErreur()){
				//si pas trouvé, c'est fonctionellement normal, on traite l'erreur
				getTransaction().traiterErreur();
				addZone(getNOM_ST_SERVICE(),"pas affecté");
			}else{
				Service unService = Service.chercherService(getTransaction(),unPmASI.getSiserv());
				if(getTransaction().isErreur()){
					return false;
				}
				addZone(getNOM_ST_SERVICE(),unService.getLiserv());
			}
			
			
		//	Si liste des BPC est vide
			//if (getLB_BPC() == LBVide) {
			initialiseListeBPC(request);
			//}
			addZone(getNOM_ST_TYPE(),getPMatInfosCourant().getDesignationtypeequip());
			addZone(getNOM_ST_INVENTAIRE(),getPMatInfosCourant().getPminv());
			addZone(getNOM_ST_IMMAT(),getPMatInfosCourant().getPmserie());
			addZone(getNOM_ST_NOMEQUIP(),getPMatInfosCourant().getDesignationmarque()+" "+getPMatInfosCourant().getDesignationmodele());
			ModeleInfos unModeleInfos = ModeleInfos.chercherModeleInfos(getTransaction(),getPMatInfosCourant().getCodemodele());
			if(getTransaction().isErreur()){
				return false;
			}
			addZone(getNOM_ST_CARBU(),unModeleInfos.getDesignationcarbu());
		}
	}
	return true;
}

/**
 * Initialisation de la liste des entretiens
 * @author : Coralie NICOLAS
 */
private boolean initialiseListeBPC(javax.servlet.http.HttpServletRequest request) throws Exception{
//	Recherche des bpc
	java.util.ArrayList<BPC> a = new ArrayList<BPC>();
	if(!isMateriel){
		a = BPC.listerBPCEquipement(getTransaction(),getEquipementInfosCourant().getNumeroinventaire());
	}else{
		a = BPC.listerBPCEquipement(getTransaction(),getPMatInfosCourant().getPminv());
	}
	if(getTransaction().isErreur()){
		return false;
	}
	if (null == a){
		System.out.println("Aucun élément enregistré dans la base.");
	}
	setListeBPC(a);
	trier(a);	
	return true;
	
}

/**
 * Constructeur du process OeBPC.
 * Date de création : (30/05/05 10:16:18)
 * @author : Générateur de process
 */
public OeBPC() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (30/05/05 10:16:18)
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
 * Date de création : (30/05/05 10:16:18)
 * @author : Générateur de process
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	String type = "";
	//on récupère le dernier BPC pour les contrôles avec le prochain BPC
	if (getLB_BPC()!=LBVide){
		//int numdernier = listeBPC.size()-1;
		BPC monBPC = (BPC)getListeBPC().get(0);
		setBpcCourant(monBPC);
		VariableActivite.ajouter(this, "BPCAVANT",getBpcCourant());
	}
	//	On met les variables activités
	// modif du 13/08/08 : si c'est un petit matériel je n'envoie que PMATINFOS et je précise dans une variable activité la nature de l'équipement
	if (isMateriel){
//		type = "PMATINFOS";
		if(null==getPMatInfosCourant()){
			setPMatInfosCourant(new PMatInfos());
		}
		VariableActivite.ajouter(this, "PMATINFOS", getPMatInfosCourant());
	}else{
//		type = "EQUIPEMENTINFOS";
		if(null==getEquipementInfosCourant()){
			setEquipementInfosCourant(new EquipementInfos());
		}
		VariableActivite.ajouter(this, "EQUIPEMENTINFOS", getEquipementInfosCourant());
	}
	
	
	setStatut(STATUT_MODIFIER,false);
	setStatut(STATUT_RECHERCHE,false);
	setStatut(STATUT_AJOUTER,true);
	return true;
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (30/05/05 10:16:18)
 * @author : Générateur de process
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	
	initialiseListeBPC(request);	
	addZone(getNOM_ST_TITRE_ACTION(),"");
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (30/05/05 10:16:18)
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
 * Date de création : (30/05/05 10:16:18)
 * @author : Générateur de process
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {
	int numligne = (Services.estNumerique(getZone(getNOM_LB_BPC_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_BPC_SELECT())) : -1);
	if (numligne == -1 || getListeBPC().size() == 0 || numligne > getListeBPC().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","BPC"));
		return false;
	}
	
	BPC monBPC = (BPC)getListeBPC().get(numligne);
	setBpcCourant(monBPC);
	if (numligne == 0){
	//	On met la variable activité
		if (1!=getLB_BPC().length){
			BPC unBPC = (BPC)getListeBPC().get(numligne+1);
			VariableActivite.ajouter(this, "BPCAVANT", unBPC);
		}
		if(!isMateriel){
			VariableGlobale.ajouter(request, "EQUIPEMENTINFOS", getEquipementInfosCourant());
			VariableGlobale.ajouter(request, "PMATINFOS", new PMatInfos());
		}else{
			VariableGlobale.ajouter(request, "EQUIPEMENTINFOS", new EquipementInfos());
			VariableGlobale.ajouter(request, "PMATINFOS", getPMatInfosCourant());
		}
		VariableGlobale.ajouter(request, "BPC", getBpcCourant());
		setStatut(STATUT_AJOUTER,false);
		setStatut(STATUT_RECHERCHE,false);
		setStatut(STATUT_MODIFIER,true);
	}else{
		getTransaction().declarerErreur("Seule la dernière prise de carburant peut être supprimée ou modifiée.");
	}
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPRIMER
 * Date de création : (30/05/05 10:16:18)
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
 * Date de création : (30/05/05 10:16:18)
 * @author : Générateur de process
 */
public boolean performPB_SUPPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_BPC_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_BPC_SELECT())) : -1);
	if (numligne == -1 || getListeBPC().size() == 0 || numligne > getListeBPC().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","BPC"));
		return false;
	}
	
	if (numligne == 0){
		//On nomme l'action
		addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);
		
		BPC monBPC = (BPC)getListeBPC().get(numligne);
		setBpcCourant(monBPC);
		addZone(getNOM_ST_BPC(), monBPC.getNumerobpc());
		addZone(getNOM_ST_COMPTEUR(), monBPC.getValeurcompteur());
		addZone(getNOM_ST_POMPE(), monBPC.getNumeropompe());
		addZone(getNOM_ST_QTE(),monBPC.getQuantite());
		addZone(getNOM_ST_HEURE(),monBPC.getHeure());
		addZone(getNOM_ST_DATE(),monBPC.getDate());
		//afficher le bon mode de prise
		ModePrise monMp = ModePrise.chercherModePrise(getTransaction(),monBPC.getModedeprise());
		addZone(getNOM_ST_MODEPRISE(),monMp.getDesignationmodeprise());
		
	}else{
		getTransaction().declarerErreur("Seule la dernière prise de carburant peut être supprimée ou modifiée.");
	}
	return true;
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_BPC
 * Date de création : (30/05/05 10:16:18)
 * @author : Générateur de process
 */
private String [] getLB_BPC() {
	if (LB_BPC == null)
		LB_BPC = initialiseLazyLB();
	return LB_BPC;
}
/**
 * Setter de la liste:
 * LB_BPC
 * Date de création : (30/05/05 10:16:18)
 * @author : Générateur de process
 */
private void setLB_BPC(java.lang.String[] newLB_BPC) {
	LB_BPC = newLB_BPC;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_BPC
 * Date de création : (30/05/05 10:16:18)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_BPC() {
	return "NOM_LB_BPC";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_BPC_SELECT
 * Date de création : (30/05/05 10:16:18)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_BPC_SELECT() {
	return "NOM_LB_BPC_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_BPC
 * Date de création : (30/05/05 10:16:18)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_BPC() {
	return getLB_BPC();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_BPC
 * Date de création : (30/05/05 10:16:18)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_BPC_SELECT() {
	return getZone(getNOM_LB_BPC_SELECT());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (30/05/05 10:33:41)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_ANNULER() {
	return "NOM_PB_ANNULER";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (30/05/05 10:33:41)
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
 * Date de création : (30/05/05 10:33:41)
 * @author : Générateur de process
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
/*	int indice = (Services.estNumerique(getVAL_LB_BPC_SELECT()) ? Integer.parseInt(getVAL_LB_BPC_SELECT()): -1); 
		
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	
	BPC monBPC = (BPC)getListeBPC().get(indice);
	setBpcCourant(monBPC);
	
	addZone(getNOM_ST_BPC(), monBPC.getNumerobpc());
	addZone(getNOM_ST_COMPTEUR(), monBPC.getValeurcompteur());
	addZone(getNOM_ST_POMPE(), monBPC.getNumeropompe());
	addZone(getNOM_ST_QTE(),monBPC.getQuantite());
	addZone(getNOM_ST_CARBU(),equipementInfosCourant.getDesignationcarbu());
	addZone(getNOM_ST_HEURE(),monBPC.getHeure());
	addZone(getNOM_ST_DATE(),monBPC.getDate());
	//afficher le bon mode de prise
	ModePrise monMp = ModePrise.chercherModePrise(getTransaction(),monBPC.getModedeprise());
	addZone(getNOM_ST_MODEPRISE(),monMp.getDesignationmodeprise());
	*/
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (30/05/05 10:33:41)
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
 * Date de création : (30/05/05 10:33:41)
 * @author : Générateur de process
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Si aucune action en cours
	if (getZone(getNOM_ST_TITRE_ACTION()).length() == 0) {
		setStatut(STATUT_MEME_PROCESS, true, MairieMessages.getMessage("ERR996"));//"Vous ne pouvez pas valider, il n'y a pas d'action en cours."
		return false;
	}
//	Suppression
	getBpcCourant().supprimerBPC(getTransaction());
	if (getTransaction().isErreur()){
		return false;
	}
//	Tout s'est bien passé
	commitTransaction();
	initialiseListeBPC(request);
	
	addZone(getNOM_ST_TITRE_ACTION(),"");
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_BPC
 * Date de création : (30/05/05 10:33:41)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_BPC() {
	return "NOM_ST_BPC";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_BPC
 * Date de création : (30/05/05 10:33:41)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_BPC() {
	return getZone(getNOM_ST_BPC());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_CARBU
 * Date de création : (30/05/05 10:33:41)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_CARBU() {
	return "NOM_ST_CARBU";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_CARBU
 * Date de création : (30/05/05 10:33:41)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_CARBU() {
	return getZone(getNOM_ST_CARBU());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMPTEUR
 * Date de création : (30/05/05 10:33:41)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_COMPTEUR() {
	return "NOM_ST_COMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMPTEUR
 * Date de création : (30/05/05 10:33:41)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_COMPTEUR() {
	return getZone(getNOM_ST_COMPTEUR());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DATE
 * Date de création : (30/05/05 10:33:41)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_DATE() {
	return "NOM_ST_DATE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DATE
 * Date de création : (30/05/05 10:33:41)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_DATE() {
	return getZone(getNOM_ST_DATE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_HEURE
 * Date de création : (30/05/05 10:33:41)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_HEURE() {
	return "NOM_ST_HEURE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_HEURE
 * Date de création : (30/05/05 10:33:41)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_HEURE() {
	return getZone(getNOM_ST_HEURE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODEPRISE
 * Date de création : (30/05/05 10:33:41)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_MODEPRISE() {
	return "NOM_ST_MODEPRISE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODEPRISE
 * Date de création : (30/05/05 10:33:41)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_MODEPRISE() {
	return getZone(getNOM_ST_MODEPRISE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_POMPE
 * Date de création : (30/05/05 10:33:41)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_POMPE() {
	return "NOM_ST_POMPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_POMPE
 * Date de création : (30/05/05 10:33:41)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_POMPE() {
	return getZone(getNOM_ST_POMPE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_QTE
 * Date de création : (30/05/05 10:33:41)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_QTE() {
	return "NOM_ST_QTE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_QTE
 * Date de création : (30/05/05 10:33:41)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_QTE() {
	return getZone(getNOM_ST_QTE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (30/05/05 10:34:55)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (30/05/05 10:34:55)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
	
	/**
	 * @return Renvoie bpcCourant.
	 */
	private BPC getBpcCourant() {
		return bpcCourant;
	}
	/**
	 * @param bpcCourant bpcCourant à définir.
	 */
	private void setBpcCourant(BPC bpcCourant) {
		this.bpcCourant = bpcCourant;
	}
	/**
	 * @return Renvoie listeBPC.
	 */
	private ArrayList<BPC> getListeBPC() {
		return listeBPC;
	}
	/**
	 * @param listeBPC listeBPC à définir.
	 */
	private void setListeBPC(ArrayList<BPC> listeBPC) {
		this.listeBPC = listeBPC;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_IMMAT
 * Date de création : (30/05/05 14:38:43)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_IMMAT() {
	return "NOM_ST_IMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_IMMAT
 * Date de création : (30/05/05 14:38:43)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_IMMAT() {
	return getZone(getNOM_ST_IMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_INVENTAIRE
 * Date de création : (30/05/05 14:38:43)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_INVENTAIRE() {
	return "NOM_ST_INVENTAIRE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_INVENTAIRE
 * Date de création : (30/05/05 14:38:43)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_INVENTAIRE() {
	return getZone(getNOM_ST_INVENTAIRE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOMEQUIP
 * Date de création : (30/05/05 14:39:57)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOMEQUIP() {
	return "NOM_ST_NOMEQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOMEQUIP
 * Date de création : (30/05/05 14:39:57)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOMEQUIP() {
	return getZone(getNOM_ST_NOMEQUIP());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (30/05/05 14:39:57)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (30/05/05 14:39:57)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (30/05/05 14:39:57)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (30/05/05 14:39:57)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
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
//	/**
//	 * @return Renvoie equipementCourant.
//	 */
//	private Equipement getEquipementCourant() {
//		return equipementCourant;
//	}
//	/**
//	 * @param equipementCourant equipementCourant à définir.
//	 */
//private void setEquipementCourant(Equipement equipementCourant) {
//	this.equipementCourant = equipementCourant;
//}

///**
// * @return Renvoie pMaterielCourant.
// */
//private PMateriel getPMaterielCourant() {
//	return pMaterielCourant;
//}
///**
// * @param pMaterielCourant pMaterielCourant à définir.
// */
//private void setPMaterielCourant(PMateriel pMaterielCourant) {
//	this.pMaterielCourant = pMaterielCourant;
//}
/**
 * @return Renvoie pMatInfosCourant.
 */
private PMatInfos getPMatInfosCourant() {
	return pMatInfosCourant;
}
/**
 * @param pMatInfosCourant pMatInfosCourant à définir.
 */
private void setPMatInfosCourant( PMatInfos pMatInfosCourant) {
	this.pMatInfosCourant = pMatInfosCourant;
}

public void trier(ArrayList<BPC> a) throws Exception{
		String[] colonnes = {"date","valeurcompteur"};
		//ordre croissant
		boolean[] ordres = {false,false};
		// RG : on affiche que les 12 derniers BPC
		int max = 12;
		
//		Si au moins un bpc
		if (a.size() !=0 ) {
			ArrayList<BPC> aTrier = Services.trier(a,colonnes,ordres);
			setListeBPC(aTrier);
			int tailles [] = {10,10,10,6,10,10};
			String[] padding = {"D","C","D","D","D","D"};
			FormateListe aFormat = new FormateListe(tailles,padding,false);
			// on affiche les 12 derniers BPC
			/*if (a.size()>12){
				indice = a.size()-11;
			}*/
			if (a.size()<12){
				max = a.size();
			}
			for (int i = 0; i < max ; i++) {
				BPC aBPC = (BPC)aTrier.get(i);
				BPC bpcAvant = null;
				try {
					bpcAvant = (BPC) aTrier.get(i+1);	
				} catch (Exception e) {
					bpcAvant = null;
				}
				
				//setBpcCourant(aBPC);
				// Calcul des km parcourus et moyenne au 100
				int kmParcourus =0 ;
				double moyennecalcul = 0;
				String moyenne = "";
				 
				if (null != bpcAvant){
					
					kmParcourus =  Integer.parseInt(aBPC.getValeurcompteur())-Integer.parseInt(bpcAvant.getValeurcompteur());
//					int qteAvant = Integer.parseInt(bpcAvant.getQuantite());
					int qte = Integer.parseInt(aBPC.getQuantite());
					//moyennecalcul = (double)qteAvant/(double)kmParcourus*100;
					//moyennecalcul = (double)qte/(double)kmParcourus*100;
					moyennecalcul = (double)qte/(double)kmParcourus;
					if(!isMateriel){
						if (("KILOMETRIQUE").equals(getEquipementInfosCourant().getDesignationcompteur())){
							moyennecalcul = moyennecalcul*100;
						}
					}
					NumberFormat moyenneFormat = new DecimalFormat("0.00");
					moyenne = moyenneFormat.format(moyennecalcul);
				}
				String ligne [] = { aBPC.getNumerobpc(),aBPC.getDate(),aBPC.getValeurcompteur(),aBPC.getQuantite(),String.valueOf(kmParcourus),moyenne};
				aFormat.ajouteLigne(ligne);
			}
			setLB_BPC(aFormat.getListeFormatee());
		} else {
			setLB_BPC(null);
		}
		setIsVide(a.size());
		return ;
	}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE
 * Date de création : (08/06/05 09:56:59)
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
 * Date de création : (08/06/05 09:56:59)
 * @author : Générateur de process
 */
public boolean performPB_RECHERCHE(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_AJOUTER,false);
	setStatut(STATUT_MODIFIER,false);
	VariableActivite.ajouter(this,"MODE","BPC");
	setStatut(STATUT_RECHERCHE,true);
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_RECHERCHE
 * Date de création : (08/06/05 09:56:59)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_RECHERCHE() {
	return "NOM_EF_RECHERCHE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_RECHERCHE
 * Date de création : (08/06/05 09:56:59)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_RECHERCHE() {
	return getZone(getNOM_EF_RECHERCHE());
}
	
	public int getIsVide() {
		return isVide;
	}
	public void setIsVide(int isVide) {
		this.isVide = isVide;
	}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_EQUIP
 * Date de création : (02/04/07 08:21:52)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_EQUIP() {
	return "NOM_PB_EQUIP";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (02/04/07 08:21:52)
 * @author : Générateur de process
 */
public boolean performPB_EQUIP(javax.servlet.http.HttpServletRequest request) throws Exception {
	// si nouvelle recherche on met à blanc les infos
	addZone(getNOM_ST_IMMAT(),"");
	addZone(getNOM_ST_INVENTAIRE(),"");
	addZone(getNOM_ST_NOMEQUIP(),"");
	addZone(getNOM_ST_SERVICE(),"");
	addZone(getNOM_ST_TYPE(),"");
	setLB_BPC(LBVide);
	
	String recherche = getZone(getNOM_EF_EQUIP()).toUpperCase();
	if(recherche.equals("")){
		getTransaction().declarerErreur("La zone de recherche doit être renseignée.");
		return false;
	}
	EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfosInvOuImmat(getTransaction(),recherche);
	if(getTransaction().isErreur()){
		getTransaction().traiterErreur();
		//getTransaction().declarerErreur("L'équipement recherché n'a pas été trouvé.");
		//return false;
	}
	if(null==unEquipementInfos){
		unEquipementInfos = new EquipementInfos();
	}
	setEquipementInfosCourant(unEquipementInfos);
	
	if(unEquipementInfos.getNumeroinventaire()==null){
		PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),recherche);
		if(getTransaction().isErreur()){
			getTransaction().declarerErreur("La recherche ne correspond ni à un équipement ni à un petit matériel.");
			return false;
			//getTransaction().traiterErreur();
			//getTransaction().declarerErreur("Le petit matériel n'a pas été trouvé");
			//return false;
		}
		setPMatInfosCourant(unPMatInfos);
		if(unPMatInfos==null){
			getTransaction().declarerErreur("La recherche ne correspond ni à un équipement ni à un petit matériel.");
			return false;
		}else{
			VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
			isMateriel = true;
		}
	}else{
		// on renseigne la liste des BPC
		isMateriel = false;
		VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());		
	}
	
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_EQUIP
 * Date de création : (02/04/07 08:21:52)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_EQUIP() {
	return "NOM_EF_EQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_EQUIP
 * Date de création : (02/04/07 08:21:52)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_EQUIP() {
	return getZone(getNOM_EF_EQUIP());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SEL_PM
 * Date de création : (10/08/07 13:25:24)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_SEL_PM() {
	return "NOM_PB_SEL_PM";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (10/08/07 13:25:24)
 * @author : Générateur de process
 */
public boolean performPB_SEL_PM(javax.servlet.http.HttpServletRequest request) throws Exception {
	VariableActivite.ajouter(this,"MODE","BPC");
	setStatut(STATUT_PMATERIEL,true);
	return true;
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (30/05/05 10:16:18)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_SEL_PM
		if (testerParametre(request, getNOM_PB_SEL_PM())) {
			return performPB_SEL_PM(request);
		}

		//Si clic sur le bouton PB_EQUIP
		if (testerParametre(request, getNOM_PB_EQUIP())) {
			return performPB_EQUIP(request);
		}
		//Si clic sur le bouton PB_RECHERCHE
		if (testerParametre(request, getNOM_PB_RECHERCHE())) {
			return performPB_RECHERCHE(request);
		}
//Si clic sur le bouton PB_OK
		if (testerParametre(request, getNOM_PB_OK())) {
			return performPB_OK(request);
		}

		//Si clic sur le bouton PB_VALIDER
		if (testerParametre(request, getNOM_PB_VALIDER())) {
			return performPB_VALIDER(request);
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

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (10/08/07 13:26:21)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeBPC.jsp";
}
}
