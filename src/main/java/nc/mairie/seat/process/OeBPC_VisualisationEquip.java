package nc.mairie.seat.process;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import nc.mairie.seat.metier.AffectationServiceInfos;
import nc.mairie.seat.metier.AgentCCAS;
import nc.mairie.seat.metier.AgentCDE;
import nc.mairie.seat.metier.Agents;
import nc.mairie.seat.metier.BPC;
import nc.mairie.seat.metier.Equipement;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.ModeleInfos;
import nc.mairie.seat.metier.PM_Affectation_Sce_Infos;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.Service;
import nc.mairie.technique.*;
import nc.mairie.seat.servlet.ServletSeat;
import nc.mairie.servlets.Frontale;

/**
 * Process OeBPC_VisualisationEquip
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
*/ 
public class OeBPC_VisualisationEquip extends nc.mairie.technique.BasicProcess {
	public static final int STATUT_PMATERIEL = 3;
	public static final int STATUT_DETAILS = 2;
	public static final int STATUT_RECHERCHER = 1;
	private java.lang.String[] LB_BPC;
	private ArrayList listeBPC;
	private EquipementInfos equipementInfosCourant;
	private PMatInfos pMatInfosCourant;
	private String totalQte ;
	private String nbBPC;
	private String kmParcouru;
	private String moyenne;
	private boolean first=true;
	public boolean listeVide=true;
	public int quantiteTotal = 0;
	public int kmParcourusTotal = 0;
	private String starjetMode = (String)Frontale.getMesParametres().get("STARJET_MODE");
	private String script;
	public boolean isVide = true;
	public String codeService ;
	public String agentResponsable;
	public String inventaire;
	public boolean isMateriel = false;
	private ModeleInfos modeleInfosCourant;
	private String focus = null;
	
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	//if(etatStatut()==STATUT_RECHERCHER){
		EquipementInfos unEquipementInfos = (EquipementInfos)VariableGlobale.recuperer(request,"EQUIPEMENTINFOS");
		if(unEquipementInfos!=null){
			if(unEquipementInfos.getNumeroinventaire()!=null){
				setEquipementInfosCourant(unEquipementInfos);
			}else{
				isMateriel = true;
			}
		}else{
			isMateriel = true;
		}
		if(isMateriel){
			PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request,"PMATINFOS");
			if(unPMatInfos!=null){
				if(unPMatInfos.getPminv()!=null){
					setPMatInfosCourant(unPMatInfos);
				}
			}
		}
		
	//}
	//quand appui sur entree
	if(!getZone(getNOM_EF_RECHERCHE_EQUIP()).equals("")){
		performPB_RECHERCHE_EQUIP(request);
	}
	if(!isMateriel){
		if(getEquipementInfosCourant()!=null){
			if(getEquipementInfosCourant().getNumeroinventaire()!=null){
				//	initialisation des zones
				addZone(getNOM_ST_TYPE(),getEquipementInfosCourant().getDesignationtypeequip());
				addZone(getNOM_ST_NOINVENT(),getEquipementInfosCourant().getNumeroinventaire());
				addZone(getNOM_ST_NOIMMAT(),getEquipementInfosCourant().getNumeroimmatriculation());
				addZone(getNOM_ST_MODELE(),getEquipementInfosCourant().getDesignationmodele());
				addZone(getNOM_ST_MARQUE(),getEquipementInfosCourant().getDesignationmarque());
				//initialisation des listes
				Equipement unEquipement = Equipement.chercherEquipement(getTransaction(),getEquipementInfosCourant().getNumeroinventaire());
				if(getTransaction().isErreur()){
					return ;
				}
				ModeleInfos unMI = ModeleInfos.chercherModeleInfos(getTransaction(),unEquipement.getCodemodele());
				if(getTransaction().isErreur()){
					return ;
				}
				setModeleInfosCourant(unMI);
				initialiseListeBPC(request);
				initialiseListeTotal(request);
			}
	//		recherche du service concerné à la date du BPC en visu
			String date = Services.dateDuJour();
			AffectationServiceInfos unAffectationServiceInfos = AffectationServiceInfos.chercherAffectationServiceInfosCourantEquip(getTransaction(),getEquipementInfosCourant().getNumeroinventaire(),date);
			if(getTransaction().isErreur()){
				//si pas trouvé, c'est fonctionellement normal, on traite l'erreur
				getTransaction().traiterErreur();
				addZone(getNOM_ST_SERVICE(),"pas affecté");
			}else{
				addZone(getNOM_ST_SERVICE(),unAffectationServiceInfos.getLiserv());
				codeService = unAffectationServiceInfos.getCodeservice();
				agentResponsable = unAffectationServiceInfos.getNomatr();
			}
			
		}
	}else{
		if(getPMatInfosCourant()!=null){
			if(null!=getPMatInfosCourant().getPminv()){
//				initialisation des zones
				addZone(getNOM_ST_TYPE(),getPMatInfosCourant().getDesignationtypeequip());
				addZone(getNOM_ST_NOINVENT(),getPMatInfosCourant().getPminv());
				addZone(getNOM_ST_NOIMMAT(),getPMatInfosCourant().getPmserie());
				addZone(getNOM_ST_MODELE(),getPMatInfosCourant().getDesignationmodele());
				addZone(getNOM_ST_MARQUE(),getPMatInfosCourant().getDesignationmarque());
				ModeleInfos unMI = ModeleInfos.chercherModeleInfos(getTransaction(),getPMatInfosCourant().getCodemodele());
				if(getTransaction().isErreur()){
					return ;
				}
				setModeleInfosCourant(unMI);
				//initialisation des listes
				initialiseListeBPC(request);
				initialiseListeTotal(request);
			}
//			recherche du service concerné à la date du BPC en visu
			String date = Services.dateDuJour();
			PM_Affectation_Sce_Infos unPMASI = PM_Affectation_Sce_Infos.chercherPM_Affectation_Sce_InfosCourantPm(getTransaction(),getPMatInfosCourant().getPminv(),date);
			if(getTransaction().isErreur()){
				//si pas trouvé, c'est fonctionellement normal, on traite l'erreur
				getTransaction().traiterErreur();
				addZone(getNOM_ST_SERVICE(),"pas affecté");
			}else{
				Service unService = Service.chercherService(getTransaction(),unPMASI.getSiserv());
				if(getTransaction().isErreur()){
					return ;
				}
				addZone(getNOM_ST_SERVICE(),unService.getLiserv());
				codeService = unPMASI.getSiserv();
				agentResponsable = unPMASI.getNomatr();
			}
			
		}
	}
	setFirst(false);
}

/*
 * initialisation de la liste des BPC et calcul des infos
 *  date : 05/08/05
 * @autheur : Coralie NICOLAS
 */
public boolean initialiseListeBPC(javax.servlet.http.HttpServletRequest request) throws Exception{
	if(!isMateriel){
		inventaire = getEquipementInfosCourant().getNumeroinventaire();
	}else{
		inventaire = getPMatInfosCourant().getPminv();
	}
	
	java.util.ArrayList a = new ArrayList();
	if(!isMateriel){
		a = BPC.listerBPCEquipement(getTransaction(),getEquipementInfosCourant().getNumeroinventaire());
	}else{
		a = BPC.listerBPCEquipement(getTransaction(),getPMatInfosCourant().getPminv());
	}

	setListeBPC(a);
	trier(a);

	if(a.size()>0){
		setListeVide(false);
		setVide(false);
	}else{
		setListeVide(true);
		setVide(true);
	}
	return true;
}

public void initialiseListeTotal(javax.servlet.http.HttpServletRequest request) throws Exception{
	NumberFormat moyenneTotalFormat = new DecimalFormat("0.00");
	double MoyenneTotal;
	if((null!=getListeBPC())&&(getListeBPC().size()>0)){
		BPC unBPC = (BPC)getListeBPC().get(0);
		quantiteTotal = quantiteTotal - Integer.parseInt(unBPC.getQuantite());
		MoyenneTotal = (double)quantiteTotal/(double)kmParcourusTotal*100;
		if(getListeBPC()!=null){
			if(getListeBPC().size()>0){
				int tailles [] = {10,10,10,6,10,10};
				String[] padding = {"D","C","D","D","D","D"};
				FormateListe aFormat = new FormateListe(tailles,padding,false);
				String ligne [] = { nbBPC,"","",totalQte,kmParcouru,String.valueOf(moyenneTotalFormat.format(MoyenneTotal))};
				aFormat.ajouteLigne(ligne);
				setLB_TOTAL(aFormat.getListeFormatee());
			} else {
				setLB_TOTAL(null);
			}
		}
	} else {
		setLB_TOTAL(LBVide);
	}
	kmParcourusTotal = 0;
	kmParcouru = "";
	quantiteTotal = 0;
	MoyenneTotal = 0;
}

public void trier(ArrayList a) throws Exception{
	String[] colonnes = {"date","valeurcompteur"};
	//ordre croissant
	boolean[] ordres = {false,false};
	//int quantiteTotal = 0;
	//int kmParcouruTotal =0;
	
//	Si au moins un bpc
	if (a.size() !=0 ) {
		ArrayList aTrier = Services.trier(a,colonnes,ordres);
		setListeBPC(aTrier);
		int tailles [] = {10,10,10,6,10,10};
		String[] padding = {"D","C","D","D","D","D"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		int indice = a.size();
		/*if (a.size()>12){
			indice = a.size()-11;
		}*/
		int recup = 1;
				
		for (int i = 0; i < indice ; i++) {
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
			String moyenneL = "";
			// calcul des totaux
			quantiteTotal = quantiteTotal + Integer.parseInt(aBPC.getQuantite()); 
			//kmParcourusTotal = kmParcourusTotal + Integer.parseInt(aBPC.get)
			
			if (null != bpcAvant){
				kmParcourus =  Integer.parseInt(aBPC.getValeurcompteur())-Integer.parseInt(bpcAvant.getValeurcompteur());
				int qteAvant = Integer.parseInt(bpcAvant.getQuantite());
				int qte = Integer.parseInt(aBPC.getQuantite());
				//moyennecalcul = (double)qteAvant/(double)kmParcourus*100;
				//moyennecalcul = (double)qte/(double)kmParcourus*100;
				moyennecalcul = (double)qte/(double)kmParcourus;
				
				if (("KILOMETRIQUE").equals(getModeleInfosCourant().getDesignationcompteur())){
					moyennecalcul = moyennecalcul*100;
				}
				NumberFormat moyenneFormat = new DecimalFormat("0.00");
				moyenneL = moyenneFormat.format(moyennecalcul);
			}
			String ligne [] = { aBPC.getNumerobpc(),aBPC.getDate(),aBPC.getValeurcompteur(),aBPC.getQuantite(),String.valueOf(kmParcourus),moyenneL};
			aFormat.ajouteLigne(ligne);
			kmParcourusTotal = kmParcourusTotal + kmParcourus;//Integer.parseInt(aBPC.getValeurcompteur());
		}
		// calcul pour les totaux
		NumberFormat moyenneTotalFormat = new DecimalFormat("0.00");
		BPC theBPC = (BPC)aTrier.get(aTrier.size()-1);
		//quantiteTotal = quantiteTotal - Integer.parseInt(theBPC.getQuantite()); 
		double moyenneTotal = (double)quantiteTotal/(double)kmParcourusTotal*100;
		nbBPC = String.valueOf(getListeBPC().size());
		kmParcouru = String.valueOf(kmParcourusTotal);
		totalQte = String.valueOf(quantiteTotal);
		moyenne = moyenneTotalFormat.format(moyenneTotal);
		
		setLB_BPC(aFormat.getListeFormatee());
	} else {
		setLB_BPC(null);
	}
	
	return ;
}
/**
 * Constructeur du process OeBPC_VisualisationEquip.
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public OeBPC_VisualisationEquip() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHER
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_RECHERCHER() {
	return "NOM_PB_RECHERCHER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public boolean performPB_RECHERCHER(javax.servlet.http.HttpServletRequest request) throws Exception {
	VariableActivite.ajouter(this,"MODE","BPC_VISU_EQUIP");
	isMateriel = false;
	setStatut(STATUT_RECHERCHER,true);
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODELE
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_MODELE() {
	return "NOM_ST_MODELE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODELE
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_MODELE() {
	return getZone(getNOM_ST_MODELE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOIMMAT() {
	return "NOM_ST_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOIMMAT() {
	return getZone(getNOM_ST_NOIMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINVENT
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOINVENT() {
	return "NOM_ST_NOINVENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINVENT
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOINVENT() {
	return getZone(getNOM_ST_NOINVENT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_BPC
 * Date de création : (05/08/05 08:43:16)
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
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
private void setLB_BPC(java.lang.String[] newLB_BPC) {
	LB_BPC = newLB_BPC;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_BPC
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_BPC() {
	return "NOM_LB_BPC";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_BPC_SELECT
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_BPC_SELECT() {
	return "NOM_LB_BPC_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_BPC
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_BPC() {
	return getLB_BPC();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_BPC
 * Date de création : (05/08/05 08:43:16)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_BPC_SELECT() {
	return getZone(getNOM_LB_BPC_SELECT());
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
	public void setModeleInfosCourant(ModeleInfos modeleInfosCourant) {
		this.modeleInfosCourant = modeleInfosCourant;
	}
	public ModeleInfos getModeleInfosCourant() {
		return modeleInfosCourant;
	}
	public ArrayList getListeBPC() {
		return listeBPC;
	}
	public void setListeBPC(ArrayList listeBPC) {
		this.listeBPC = listeBPC;
	}
	private java.lang.String[] LB_TOTAL;
/**
 * Getter de la liste avec un lazy initialize :
 * LB_TOTAL
 * Date de création : (05/08/05 09:12:48)
 * @author : Générateur de process
 */
private String [] getLB_TOTAL() {
	if (LB_TOTAL == null)
		LB_TOTAL = initialiseLazyLB();
	return LB_TOTAL;
}
/**
 * Setter de la liste:
 * LB_TOTAL
 * Date de création : (05/08/05 09:12:48)
 * @author : Générateur de process
 */
private void setLB_TOTAL(java.lang.String[] newLB_TOTAL) {
	LB_TOTAL = newLB_TOTAL;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_TOTAL
 * Date de création : (05/08/05 09:12:48)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_TOTAL() {
	return "NOM_LB_TOTAL";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_TOTAL_SELECT
 * Date de création : (05/08/05 09:12:48)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_TOTAL_SELECT() {
	return "NOM_LB_TOTAL_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_TOTAL
 * Date de création : (05/08/05 09:12:48)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_TOTAL() {
	return getLB_TOTAL();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_TOTAL
 * Date de création : (05/08/05 09:12:48)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_TOTAL_SELECT() {
	return getZone(getNOM_LB_TOTAL_SELECT());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE_EQUIP
 * Date de création : (24/08/05 15:17:55)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_RECHERCHE_EQUIP() {
	return "NOM_PB_RECHERCHE_EQUIP";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (24/08/05 15:17:55)
 * @author : Générateur de process
 */
public boolean performPB_RECHERCHE_EQUIP(javax.servlet.http.HttpServletRequest request) throws Exception {
	boolean trouveEquip = true;
	// on vide les zones
	addZone(getNOM_ST_TYPE(),"");
	addZone(getNOM_ST_NOINVENT(),"");
	addZone(getNOM_ST_NOIMMAT(),"");
	addZone(getNOM_ST_MODELE(),"");
	addZone(getNOM_ST_MARQUE(),"");
	setLB_BPC(LBVide);
	setLB_TOTAL(LBVide);
	setListeBPC(new ArrayList());
	setEquipementInfosCourant(new EquipementInfos());
		
	//	recherche de l'équipement voulu
	String recherche = getZone(getNOM_EF_RECHERCHE_EQUIP()).toUpperCase();
	EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfosInvOuImmat(getTransaction(),recherche);
	if(getTransaction().isErreur()){
		getTransaction().traiterErreur();
	}
	if(unEquipementInfos!=null){
		if(unEquipementInfos.getNumeroinventaire()==null){
			trouveEquip = false;
//			getTransaction().declarerErreur("L'équipement recherché n'a pas été trouvé.");
//			return false;
		}else{
			trouveEquip = true;
			setEquipementInfosCourant(unEquipementInfos);
		}
	}else{
		trouveEquip = false;
	}
	if(!trouveEquip){
		PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),recherche);
		if(getTransaction().isErreur()){
			getTransaction().declarerErreur("L'équipement recherché n'a pas été trouvé.");
			isMateriel = false;
			return false;
		}
		if(unPMatInfos!=null){
			if(unPMatInfos.getPminv()!=null){
				setPMatInfosCourant(unPMatInfos);
				isMateriel = true;
			}
		}
	}
	
	/*Equipement unEquipement = Equipement.chercherEquipement(getTransaction(),getEquipementInfosCourant().getNumeroinventaire());
	if(getTransaction().isErreur()){
		return false;
	}
	setEquipementCourant(unEquipement);*/
	//pour garder l'équipement 
	if(trouveEquip){
		isMateriel = false;
		VariableGlobale.enlever(request,"PMATINFOS");
		VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	}else{
		isMateriel = true;
		VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
		VariableGlobale.enlever(request,"EQUIPEMENTINFOS");
	}
	
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_RECHERCHE_EQUIP
 * Date de création : (24/08/05 15:17:55)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_RECHERCHE_EQUIP() {
	return "NOM_EF_RECHERCHE_EQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_RECHERCHE_EQUIP
 * Date de création : (24/08/05 15:17:55)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_RECHERCHE_EQUIP() {
	return getZone(getNOM_EF_RECHERCHE_EQUIP());
}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_DETAILS
 * Date de création : (29/08/05 09:04:24)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_DETAILS() {
	return "NOM_PB_DETAILS";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (29/08/05 09:04:24)
 * @author : Générateur de process
 */
public boolean performPB_DETAILS(javax.servlet.http.HttpServletRequest request) throws Exception {
	int indice = (Services.estNumerique(getVAL_LB_BPC_SELECT()) ? Integer.parseInt(getVAL_LB_BPC_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	BPC unBPC = (BPC)getListeBPC().get(indice);
	VariableGlobale.ajouter(request,"BPC",unBPC);
	VariableActivite.ajouter(this,"DEBRANCHE","TRUE");
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	setStatut(STATUT_DETAILS,true);
	return true;
}
	public boolean isListeVide() {
		return listeVide;
	}
	public void setListeVide(boolean listeVide) {
		this.listeVide = listeVide;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (24/10/05 11:05:39)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (24/10/05 11:05:39)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_IMPRIMER
 * Date de création : (07/06/07 08:43:13)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_IMPRIMER() {
	return "NOM_PB_IMPRIMER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (07/06/07 08:43:13)
 * @author : Générateur de process
 */
/*construction du fichier d'impression : 
 *1er caractère : 1 pour le BPC 2 pour le total de l'équipement
 *  numéro d'invenaire sur 10
 * numéro d'immatriculation sur 10
 * nom d'équipement sur 64
 * type sur 32
 * service sur 60
 * début de période sur 10
 * fin de période sur 10
 * numéro de BPC sur 10
 * date sur 10
 * compteur sur 10
 * quantité sur 6
 * km parcouru sur 10
 * moyenne sur 10
 * pour le total
 * 
 */
public boolean performPB_IMPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
	String numinv = getZone(getNOM_ST_NOINVENT());
	String numimmat = getZone(getNOM_ST_NOIMMAT());
	String nomequip = getZone(getNOM_ST_MARQUE())+" "+getZone(getNOM_ST_MODELE());
	String type = getZone(getNOM_ST_TYPE());
	String service = getZone(getNOM_ST_SERVICE());
	String dfin = Services.dateDuJour();
	String nomAgent;
	String ddebut="";
	String stService = "";
	
	if(!isMateriel){
		ddebut = getEquipementInfosCourant().getDatemiseencirculation();
	}else{
		ddebut = getPMatInfosCourant().getDmes();
	}
	
	// recherche de l'agent responsable
	if(!agentResponsable.equals("0")){
		if(codeService.equals("EAAA")){
			AgentCDE unAgentCde = AgentCDE.chercherAgentCDE(getTransaction(),agentResponsable);
			if(getTransaction().isErreur()){
				return false;
			}
			nomAgent = unAgentCde.nom.trim()+" "+unAgentCde.prenom.trim();
		}else if(codeService.equals("5000")){
			AgentCCAS unAgentCcas = AgentCCAS.chercherAgentCCAS(getTransaction(),agentResponsable);
			if(getTransaction().isErreur()){
				return false;
			}
			nomAgent = unAgentCcas.nom.trim()+" "+unAgentCcas.prenom.trim();
		}else {
			Agents unAgent = Agents.chercherAgents(getTransaction(),agentResponsable);
			if(getTransaction().isErreur()){
				return false;
			}
			nomAgent = unAgent.nom.trim()+" "+unAgent.prenom.trim();
		}
	}else{
		nomAgent = "sans";
	}
	
	if (getLB_BPC().length>0){
		StarjetGeneration g = new StarjetGeneration(getTransaction(), "MAIRIE", starjetMode, "SEAT", "listeBPCEquip.sp", "listeBPCEquip");
		File f = g.getFileData();
		
		FileWriter fw = new FileWriter(f);
		PrintWriter pw = new PrintWriter(fw);
		try {	
			for (int i=0;i<getLB_BPC().length;i++){
				
				//Entete
				pw.print("1");
				pw.print(Services.lpad(numinv,10," "));
				pw.print(Services.lpad(numimmat,10," "));
				pw.print(Services.lpad(nomequip,64," "));
				pw.print(Services.lpad(type,32," "));
				stService = codeService+" "+service;
				if(stService.length()>64){
					stService = stService.substring(0,64);
					pw.print(stService);
				}else{
					pw.print(Services.lpad(codeService+" "+service,64," "));
				}
				pw.print(Services.lpad(Services.formateDate(ddebut),10," "));
				pw.print(Services.lpad(Services.formateDate(dfin),10," "));
				pw.print(Services.lpad(nomAgent,50," "));
				//BPC
				pw.print(getLB_BPC()[i]);
				pw.println();
				
				
			}
//			Total
			pw.print("2");
			pw.print(Services.lpad(numinv,10," "));
			pw.print(getLB_TOTAL()[0]);
			pw.println();
			
			pw.close();
			fw.close();
		} catch (Exception e) {
			pw.close();
			fw.close();
			throw e;
		}
		
		setScript(g.getScriptOuverture());
		
	}
	return true;
}
public String getScript() {
	if (script == null) script="";
	return script;
}
public void setScript(String script) {
	this.script = script;
}
public String afficheScript() {
	
	String res = new String(getScript());
	setScript(null);
	return res;
}
public boolean isVide() {
	return isVide;
}
public void setVide(boolean isVide) {
	this.isVide = isVide;
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (08/08/07 09:36:14)
 * @author : Générateur de process
 */
public boolean performPB_SEL_PM(javax.servlet.http.HttpServletRequest request) throws Exception {
	VariableActivite.ajouter(this,"MODE","BPC_VisualisationEquip");
	isMateriel = true;
	setStatut(STATUT_PMATERIEL,true);
	return true;
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (05/08/05 08:43:16)
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

		//Si clic sur le bouton PB_IMPRIMER
		if (testerParametre(request, getNOM_PB_IMPRIMER())) {
			return performPB_IMPRIMER(request);
		}

		//Si clic sur le bouton PB_DETAILS
		if (testerParametre(request, getNOM_PB_DETAILS())) {
			return performPB_DETAILS(request);
		}

		//Si clic sur le bouton PB_RECHERCHE_EQUIP
		if (testerParametre(request, getNOM_PB_RECHERCHE_EQUIP())) {
			return performPB_RECHERCHE_EQUIP(request);
		}

		//Si clic sur le bouton PB_RECHERCHER
		if (testerParametre(request, getNOM_PB_RECHERCHER())) {
			return performPB_RECHERCHER(request);
		}

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (10/08/07 13:26:07)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeBPC_VisualisationEquip.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SEL_PM
 * Date de création : (10/08/07 13:26:07)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_SEL_PM() {
	return "NOM_PB_SEL_PM";
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
	return getNOM_PB_RECHERCHER();
}
}
