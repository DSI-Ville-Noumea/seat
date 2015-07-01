package nc.mairie.seat.process;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;

import nc.mairie.seat.metier.AActifs;
import nc.mairie.seat.metier.AgentCCAS;
import nc.mairie.seat.metier.AgentCDE;
import nc.mairie.seat.metier.AgentsMunicipaux;
import nc.mairie.seat.metier.BPC;
import nc.mairie.seat.metier.Declarations;
import nc.mairie.seat.metier.ENGJU;
import nc.mairie.seat.metier.FPM;
import nc.mairie.seat.metier.Fre_PM;
import nc.mairie.seat.metier.Modeles;
import nc.mairie.seat.metier.PM_Affectation_Sce_Infos;
import nc.mairie.seat.metier.PM_PePerso;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.PMateriel;
import nc.mairie.seat.metier.Pm_PePersoInfos;
import nc.mairie.seat.metier.Service;
import nc.mairie.technique.FormateListe;
import nc.mairie.technique.MairieMessages;
import nc.mairie.technique.Services;
import nc.mairie.technique.VariableActivite;
import nc.mairie.technique.VariableGlobale;


/**
 * Process OePM_TDB
 * Date de création : (10/05/07 11:11:35)
 * @author : Générateur de process
*/
public class OePM_TDB extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8969073696303928259L;
	public static final int STATUT_DECLARATIONS = 6;
	public static final int STATUT_SELECTION = 5;
	public static final int STATUT_FPM = 4;
	public static final int STATUT_AGENT = 3;
	public static final int STATUT_BPC = 2;
	public static final int STATUT_RECHERCHER = 1;
	private java.lang.String[] LB_SERVICES;
	private java.lang.String[] LB_BPC;
	private java.lang.String[] LB_BPC_TOTAL;
	private ArrayList<BPC> listBPC;
	private PMateriel pMaterielCourant;
	private PMatInfos pMatInfosCourant;
	private ArrayList<PM_PePerso> listEntretiens;
	private ArrayList<PM_Affectation_Sce_Infos> listServices;
	private ArrayList<Declarations> listDeclarations;
	private ArrayList<FPM> listFpm;
	private boolean first = true;
	private String totalQte ;
	private String nbBPC;
	private String kmParcouru;
	private String moyenne;
	public String messageInfo;
	public boolean isVideFpm;
	public boolean isVideDec;
	public boolean isVideBPC;
	private String focus = null;
	public boolean isDebranche = false;
	public String reserve = "";
	boolean pMatInfosCourantChange = false;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 */
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	String responsable = "agent non trouvé";
	if(first){
		if(getTransaction().isErreur()){
			getTransaction().traiterErreur();
		}
	}
	if((first)||(etatStatut()==1)||(etatStatut()==5)||(etatStatut()==4)||(etatStatut()==5)){
		PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request, "PMATINFOS");
		if (unPMatInfos!=null){
			if (unPMatInfos.existePMatInfosPm(getTransaction(),unPMatInfos.getPminv())){
				setPMatInfosCourant(unPMatInfos);
			}else{
				VariableGlobale.enlever(request,"PMATINFOS");
			}
		}
		String agent = (String)VariableActivite.recuperer(this,"NOMATR");
		VariableActivite.enlever(this,"NOMATR");
		if((agent!=null)&&(!agent.equals(""))){
			addZone(getNOM_EF_AGENT(),agent);
			performPB_RECHERCHE(request);
		}
		addZone(getNOM_EF_PMATERIEL(),"");
		String retour = (String)VariableActivite.recuperer(this,"DEBRANCHE");
		if("TRUE".equals(retour)){
			isDebranche = true;
		}else{
			isDebranche = false;
		}
		
	}else{
	//quand appui sur entree
		if(!getZone(getNOM_EF_PMATERIEL()).equals("")){
			performPB_RECHERCHE(request);
		}
	}
	if (pMatInfosCourantChange) {
		if (null!=(getPMatInfosCourant())){
			PMateriel monPMateriel = PMateriel.chercherPMateriel(getTransaction(),getPMatInfosCourant().getPminv());
			if (getTransaction().isErreur()){
				return;
			}
			setPMaterielCourant(monPMateriel);
			addZone(getNOM_ST_DGARANTIE(),getPMatInfosCourant().getDgarantie());
			addZone(getNOM_ST_NOMEQUIP(),getPMatInfosCourant().getDesignationmarque().trim()+" "+getPMatInfosCourant().getDesignationmodele().trim());
			addZone(getNOM_ST_NUMSERIE(),getPMatInfosCourant().getPmserie());
			addZone(getNOM_ST_NUMINV(),getPMatInfosCourant().getPminv());
			Modeles monModele = Modeles.chercherModeles(getTransaction(),getPMateriel().getCodemodele());
			if (getTransaction().isErreur()){
				return;
			}
			if(getPMatInfosCourant().getReserve().equals("T")){
				reserve = "oui";
			}else{
				reserve = "non";
			}
			addZone(getNOM_ST_PRIX(),getPMatInfosCourant().getPrix());
			addZone(getNOM_ST_PUISSANCE(),monModele.getPuissance());
			addZone(getNOM_ST_RESERVOIR(),monModele.getCapacitereservoir());
			addZone(getNOM_ST_TYPE(),getPMatInfosCourant().getDesignationtypeequip());	
	//		Si les dates sont = 01/01/0001 alors on met vides les zones
			if ("01/01/0001".equals(getPMatInfosCourant().getDachat())){
				addZone(getNOM_ST_DACHAT(), "");
			}else{
				addZone(getNOM_ST_DACHAT(), getPMatInfosCourant().getDachat());
			}
			if ("01/01/0001".equals(getPMatInfosCourant().getDmes())){
				addZone(getNOM_ST_DMES(), "");
			}else{
				addZone(getNOM_ST_DMES(), getPMatInfosCourant().getDmes());
			}
			if ("01/01/0001".equals(getPMatInfosCourant().getDmhs())){
				addZone(getNOM_ST_DMHS(),"");
			}else{
				addZone(getNOM_ST_DMHS(), getPMatInfosCourant().getDmhs());
			}
			// recherche du fournisseur
			Fre_PM unFre = Fre_PM.chercherFre_PM(getTransaction(),getPMatInfosCourant().getCodefre());
			if(getTransaction().isErreur()){
				return;
			}
			addZone(getNOM_ST_FOURNISSEUR(),unFre.getLibellefre().trim());
			// recherche du service auquel le petit matériel est affecté et l'agent responsable
			PM_Affectation_Sce_Infos unASI = PM_Affectation_Sce_Infos.chercherPM_Affectation_Sce_InfosCourantPm(getTransaction(),getPMatInfosCourant().getPminv(),Services.dateDuJour());
			if(getTransaction().isErreur()){
				// le petit matériel peut ne pas être affecté
				addZone(getNOM_ST_SERVICE(),"pas affecté");
				addZone(getNOM_ST_AGENT_RESPONSABLE(),"sans");
				getTransaction().traiterErreur();
			}
	
			if (unASI!=null){
				// recherche du service
				if(unASI.getPminv()!=null){
					Service unService = Service.chercherService(getTransaction(),unASI.getSiserv());
					if(getTransaction().isErreur()){
						return;
					}
					addZone(getNOM_ST_SERVICE(),unASI.getSiserv()+" "+unService.getLiserv());
					if (!unASI.getNomatr().equals("0")){
						// recherche de l'agent responsable si le petit matériel a un responsable
						// selon le service
						if (unASI.getSiserv().equals("4000")){
							AgentCDE unAgent =AgentCDE.chercherAgentCDE(getTransaction(),unASI.getNomatr());
							if(getTransaction().isErreur()){
								getTransaction().traiterErreur();
								responsable = "agent non trouvé";
							}else{
								responsable = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
							}
							//addZone(getNOM_ST_AGENT_RESPONSABLE(),unAgent.getNom().trim()+" "+unAgent.getPrenom().trim());
						}else if (unASI.getSiserv().equals("5000")){
							AgentCCAS unAgent = AgentCCAS.chercherAgentCCAS(getTransaction(),unASI.getNomatr());
							if (getTransaction().isErreur()){
								getTransaction().traiterErreur();
								responsable = "agent non trouvé";
							}else{
								responsable = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
							}
						}else{
							// recherche classique
							AActifs unAgent = AActifs.chercherAActifs(getTransaction(),unASI.getNomatr());
							if(getTransaction().isErreur()){
								getTransaction().traiterErreur();
								responsable = "agent non trouvé";
							}else{
								responsable = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
							}
						}
					}else{
						//addZone(getNOM_ST_AGENT_RESPONSABLE(),"sans");
						responsable = "sans";
					}
					addZone(getNOM_ST_AGENT_RESPONSABLE(),responsable);
				}else{
					addZone(getNOM_ST_SERVICE(),"pas affecté");
				}
			}
	//		 initialisation des listes
			initialiseListBPC(request);
			initialiseListEntretiens(request);
			initialiseListServices(request);
			initialiseListFpm(request);
			initialiseListDec(request);
		}
	}
	
	// pour l'erreur dû au bon d'engagement
	if ((!("").equals(messageInfo)) &&(messageInfo!=null)){
		getTransaction().declarerErreur(messageInfo);
	}
	messageInfo = "";
//	addZone(getNOM_EF_AGENT(),"");
//	addZone(getNOM_EF_PMATERIEL(),"");
//	addZone(getNOM_EF_SERVICE(),"");
setFirst(false);
if(getTransaction().isErreur()){
	return ;
}
//quand appuie sur entrée
//if((!("").equals(getZone(getNOM_EF_AGENT())))||(!("").equals(getZone(getNOM_EF_PMATERIEL())))||(!("").equals(getZone(getNOM_EF_SERVICE())))){
//	performPB_RECHERCHE(request);
//	addZone(getNOM_EF_AGENT(),"");
//	addZone(getNOM_EF_SERVICE(),"");
//	addZone(getNOM_EF_PMATERIEL(),"");
//}

}
public boolean initialiseListDec (javax.servlet.http.HttpServletRequest request) throws Exception{
	if(getTransaction().isErreur()){
		return false;
	}
	// on veut toutes les déclarations concernant l'équipement
	
	ArrayList<Declarations> listDec = Declarations.listerDeclarationsEquip(getTransaction(),getPMatInfosCourant().getPminv());
	if(getTransaction().isErreur()){
		return false;
	}
	setListDeclarations(listDec);
	trierDecl(listDec);
	if(getListDeclarations().size()>0){
		isVideDec = false;
	}else{
		setListDeclarations(new ArrayList<Declarations>());
		isVideDec = true;
	}
	return true;
}

public void initialiseListFpm(HttpServletRequest request)throws Exception{
	ArrayList<FPM> listFpm = FPM.listerFpmPmat(getTransaction(),getPMatInfosCourant().getPminv());
	if(getTransaction().isErreur()){
		return ;
	}
	setListFpm(listFpm);
	trierFpm(listFpm);
	if(listFpm.size()>0){
		isVideFpm = false;
	}else{
		isVideFpm = true;
	}
}
public void initialiseListServices(javax.servlet.http.HttpServletRequest request) throws Exception{
//	Recherche des affectations des petits matériels aux services
	ArrayList<PM_Affectation_Sce_Infos> a = PM_Affectation_Sce_Infos.chercherListPM_Affectation_Sce_InfosPm(getTransaction(),getPMatInfosCourant().getPminv());

	setListServices(a);
	trierServices(a);
	return ;	
}

/*
 * initialisation de la liste des BPC
 * Date : 18/08/05
 * autheur : CN
 */
public boolean initialiseListBPC(javax.servlet.http.HttpServletRequest request) throws Exception{
	if(getTransaction().isErreur()){
		return false;
	}
	// on veut les BPC de l'année en cours
	String annee = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	ArrayList<BPC> listBPC = BPC.listeBPCEquipAnnee(getTransaction(),getPMatInfosCourant().getPminv(),annee);
	if(getTransaction().isErreur()){
		return false;
	}
	setListBPC(listBPC);
	if(getListBPC().size()>0){
		trierBPC(listBPC);
	}else{
		setListBPC(new ArrayList<BPC>());
	}
	//si liste des BPC est vide le bouton Détails n'apparait pas
	if(getListBPC().size()>0){
		isVideBPC = true;
	}else{
		isVideBPC = false;
	}
	initialiseListeTotal(request);
	return true;
}

public void initialiseListeTotal(javax.servlet.http.HttpServletRequest request) throws Exception{
	if(getListBPC()!=null){
		if(getListBPC().size()>0){
			int tailles [] = {10,10,10,6,11,8};
			String[] padding = {"D","C","D","D","D","D"};
			FormateListe aFormat = new FormateListe(tailles,padding,false);
			String ligne [] = { nbBPC,"","",totalQte,kmParcouru,moyenne};
			aFormat.ajouteLigne(ligne);
			setLB_BPC_TOTAL(aFormat.getListeFormatee());
		} else {
			setLB_BPC_TOTAL(null);
		}
	}
}

public void trierBPC(ArrayList<BPC> a) throws Exception{
	String[] colonnes = {"date","valeurcompteur"};
	//ordre croissant
	boolean[] ordres = {false,false};
	
//	Si au moins un bpc
	if (a.size() !=0 ) {
		ArrayList<BPC> aTrier = Services.trier(a,colonnes,ordres);
		setListBPC(aTrier);
		int tailles [] = {10,10,10,6,11,8};
		String[] padding = {"D","C","D","D","D","D"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		int quantiteTotal = 0;
		int kmParcourusTotal = 0;
		
		for (int i = 0; i < a.size() ; i++) {
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
				int qte = Integer.parseInt(aBPC.getQuantite());
				//moyennecalcul = (double)qteAvant/(double)kmParcourus*100;
				//moyennecalcul = (double)qte/(double)kmParcourus*100;
				moyennecalcul = (double)qte/(double)kmParcourus;
				if (("KILOMETRIQUE").equals(getPMatInfosCourant().getDesignationcompteur())){
					moyennecalcul = moyennecalcul*100;
				}
				NumberFormat moyenneFormat = new DecimalFormat("0.00");
				moyenneL = moyenneFormat.format(moyennecalcul);
			}
			String ligne [] = { aBPC.getNumerobpc(),aBPC.getDate(),aBPC.getValeurcompteur(),aBPC.getQuantite(),String.valueOf(kmParcourus),moyenneL};
			aFormat.ajouteLigne(ligne);
			kmParcourusTotal = kmParcourusTotal+ kmParcourus;//Integer.parseInt(aBPC.getValeurcompteur());
		}
		// calcul pour les totaux
		NumberFormat moyenneTotalFormat = new DecimalFormat("0.00");
		double moyenneTotal = (double)quantiteTotal/(double)kmParcourusTotal*100;
		nbBPC = String.valueOf(getListBPC().size());
		kmParcouru = String.valueOf(kmParcourusTotal);
		totalQte = String.valueOf(quantiteTotal);
		moyenne = moyenneTotalFormat.format(moyenneTotal);
		setLB_BPC(aFormat.getListeFormatee());
	} else {
		setLB_BPC(null);
	}
	
	//return ;
}

public void trierServices(ArrayList<PM_Affectation_Sce_Infos> a) throws Exception{
	String[] colonnes = {"ddebut","dfin"};
	//ordre croissant
	boolean[] ordres = {false,true};
	
//	Si au moins une affectation
	if (a.size() !=0 ) {
		ArrayList<PM_Affectation_Sce_Infos> aTrier = Services.trier(a,colonnes,ordres);
		setListServices(aTrier);
		int tailles [] = {60,10,10};
		String[] padding = {"G","C","C"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (ListIterator<PM_Affectation_Sce_Infos> list = aTrier.listIterator(); list.hasNext(); ) {
			PM_Affectation_Sce_Infos aPM_Affectation_Sce_Infos= (PM_Affectation_Sce_Infos)list.next();
			String datefin = "";
			if (!aPM_Affectation_Sce_Infos.getDfin().equals("01/01/0001")){
				datefin = aPM_Affectation_Sce_Infos.getDfin();
			}
			Service unService = Service.chercherService(getTransaction(),aPM_Affectation_Sce_Infos.getSiserv());
			if(getTransaction().isErreur()){
				return ;
			}
			String ligne [] = { unService.getLiserv(),aPM_Affectation_Sce_Infos.getDdebut(),datefin};
			aFormat.ajouteLigne(ligne);
		}
		setLB_SERVICES(aFormat.getListeFormatee());
	} else {
		setLB_SERVICES(null);
	}
	return ;
}
/*initialisation de la liste des Entretiens
 * Date : 22/08/05
 * autheur : CN
 */
public boolean initialiseListEntretiens(javax.servlet.http.HttpServletRequest request) throws Exception{
	ArrayList<PM_PePerso> listEntretiens = PM_PePerso.listerPM_PePersoPmFait(getTransaction(),getPMatInfosCourant().getPminv(),"dreal"); 
	//	Pm_PePersoInfos.listerPmPePersoInfosFait(getTransaction(),getPMatInfosCourant().getPminv(),"dreal");
	if(getTransaction().isErreur()){
		return false ;
	}
	String date = "";
	setListEntretiens(listEntretiens);
	if(getListEntretiens().size()>0){
		int tailles [] = {10,30};
		String[] padding = {"C","G"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for(int i=0;i<getListEntretiens().size();i++){
			PM_PePerso unPmPe = (PM_PePerso)getListEntretiens().get(i);
			Pm_PePersoInfos unPePersoInfos = Pm_PePersoInfos.chercherPm_PePersoInfos(getTransaction(),unPmPe.getCodepmpep());
			if(getTransaction().isErreur()){
				return false;
			}
			// affichage 
			if ((unPePersoInfos.getDreal()==null)||(unPePersoInfos.getDreal().equals("01/01/0001"))){
				date="";
			}else{
				date=unPePersoInfos.getDreal();
			}
			String ligne [] = { date,unPePersoInfos.getLibelleentretien()};
			aFormat.ajouteLigne(ligne);
		}
			setLB_ENTRETIENS(aFormat.getListeFormatee());
	} else {
		setLB_ENTRETIENS(null);
	}
	return true;
}
/**
 * Constructeur du process OePM_TDB.
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 */
public OePM_TDB() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE
 * Date de création : (10/05/07 11:11:34)
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
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_RECHERCHE(javax.servlet.http.HttpServletRequest request) throws Exception {
	if (getTransaction().isErreur()){
		getTransaction().traiterErreur();
	}
	String recherche = getZone(getNOM_EF_PMATERIEL());
	addZone(getNOM_ST_CARBU(),"");
	addZone(getNOM_ST_COMPTEUR(),"");
	addZone(getNOM_ST_DMES(),"");
	addZone(getNOM_ST_DMHS(),"");
	addZone(getNOM_ST_NOMEQUIP(),"");
	addZone(getNOM_ST_NUMSERIE(),"");
	addZone(getNOM_ST_NUMINV(),"");
	
	addZone(getNOM_ST_PNEUS(),"");
	addZone(getNOM_ST_PRIX(),"");
	addZone(getNOM_ST_PUISSANCE(),"");
	addZone(getNOM_ST_RESERVOIR(),"");
	addZone(getNOM_ST_TYPE(),"");		
	addZone(getNOM_ST_NOMEQUIP(),"");
	addZone(getNOM_ST_AGENT_RESPONSABLE(),"");
	addZone(getNOM_ST_RESERVOIR(),"");
	addZone(getNOM_ST_DGARANTIE(),"");
	addZone(getNOM_ST_DACHAT(),"");
	addZone(getNOM_ST_FOURNISSEUR(),"");
	addZone(getNOM_ST_SERVICE(),"");
	// on vide les listes
	setLB_ENTRETIENS(LBVide);
	
	VariableActivite.ajouter(this,"TYPE","PMAT");
////////////////////////////////////////////////////////////////////////////////////////////////////
	if(!getZone(getNOM_EF_AGENT()).equals("")){
		ArrayList<AgentsMunicipaux> listAgent = new ArrayList<AgentsMunicipaux>();
		boolean trouve = false;
		String nom = "";
		 
		//on débranche sur une autre fenêtre sinon on affiche celui dont il est responsable
		if (Services.estNumerique(getZone(getNOM_EF_AGENT()))){
			ArrayList<PM_Affectation_Sce_Infos> listeASI = PM_Affectation_Sce_Infos.listerPmAffectationSceInfosAgent(getTransaction(),getZone(getNOM_EF_AGENT()));
			if(getTransaction().isErreur()){
				getTransaction().declarerErreur("L'agent n'est responsable d'aucun petit matériel.");
				return false;
			}
			if (listeASI.size()>1){
				VariableActivite.ajouter(this,"NOMATR",getZone(getNOM_EF_AGENT()));
				VariableActivite.ajouter(this,"ORIGINE","TDBPMAT");
				VariableActivite.ajouter(this,"CODESERVICE","");
				addZone(getNOM_EF_AGENT(),"");
				setStatut(STATUT_SELECTION,true);
			}else{
				if(listeASI.size()==1){
					PM_Affectation_Sce_Infos unASI = (PM_Affectation_Sce_Infos)listeASI.get(0);
					PMatInfos unPM = PMatInfos.chercherPMatInfos(getTransaction(),unASI.getPminv());
					if(getTransaction().isErreur()){
						return false;
					}
					setPMatInfosCourant(unPM);
				}else{
					getTransaction().declarerErreur("L'agent n'est responsable d'aucun petit matériel.");
					return false;
				}
			}
		}else{
			if(Services.estAlphabetique(getZone(getNOM_EF_AGENT()))){
				nom = getZone(getNOM_EF_AGENT()).toUpperCase();
				if(getZone(getNOM_EF_AGENT()).equals("")){//||getZone(getNOM_EF_SERVICE()).equals("")){
					getTransaction().declarerErreur("Vous devez saisir le nom de l'agent ");
					return false;
				}
				listAgent = AgentsMunicipaux.listerAgentsMunicipauxNom(getTransaction(),nom);
				if(getTransaction().isErreur()){
					return false;
				}
				trouve = true;
				if (trouve){
					if (listAgent.size()==1){
						AgentsMunicipaux unAgent = (AgentsMunicipaux)listAgent.get(0);
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
						}else if(a.size()>1){
							VariableActivite.ajouter(this,"NOMATR",unAgent.getNomatr());
							VariableActivite.ajouter(this,"ORIGINE","TDBPMAT");
							VariableActivite.ajouter(this,"CODESERVICE","");
							setStatut(STATUT_SELECTION,true);
						}else{
							getTransaction().declarerErreur("Cet agent n'est responsable d'aucun petit matériel.");
							return false;
						}
					}else{
						if(listAgent.size()>1){
							// si plusieurs on affiche la liste du résultat des agents
							VariableActivite.ajouter(this,"NOM",nom);
							VariableActivite.ajouter(this,"MODE","TDBPMAT");
							VariableActivite.ajouter(this,"TYPE","PMATERIEL");
							setStatut(STATUT_AGENT,true);
						}else{
							getTransaction().declarerErreur("Aucun agent correspondant.");
							return false;
						}
					}
				}
			}
		}
////////////////////////////////////////////////////////////////////////////////////////////////////////
	// recherche par le service
	}else if(!getZone(getNOM_EF_SERVICE()).equals("")){
		/* Modif par luc le 08/01/10
		if (!Services.estNumerique(getZone(getNOM_EF_SERVICE()))){
			getTransaction().declarerErreur("Le code du service est numérique.");
			return false;
		}
		*/
		
		// on recherche la liste des petits matériels dont l'agent est responsable car s'il en a plusieurs 
		//on débranche sur une autre fenêtre sinon on affiche celui dont il est responsable
		
		ArrayList<PM_Affectation_Sce_Infos> listeASI = PM_Affectation_Sce_Infos.chercherPmAffectationServiceInfosService(getTransaction(),getZone(getNOM_EF_SERVICE()));
		if(getTransaction().isErreur()){
			getTransaction().declarerErreur("Le service n'a aucun petit matériel.");
			return false;
		}
		if (listeASI.size()>1){
			VariableActivite.ajouter(this,"NOMATR","");
			VariableActivite.ajouter(this,"CODESERVICE",getZone(getNOM_EF_SERVICE()));
			VariableActivite.ajouter(this,"MODE","TDBPMAT");
			addZone(getNOM_EF_SERVICE(),"");
			setStatut(STATUT_RECHERCHER,true);
		}else{
			if(listeASI.size()==1){
				PM_Affectation_Sce_Infos unASI = (PM_Affectation_Sce_Infos)listeASI.get(0);
				PMatInfos unPM = PMatInfos.chercherPMatInfos(getTransaction(),unASI.getPminv());
				if(getTransaction().isErreur()){
					return false;
				}
				setPMatInfosCourant(unPM);
			}else{
				getTransaction().declarerErreur("Aucun petit matériel affecté à ce service");
				addZone(getNOM_EF_SERVICE(),"");
				return false;
			}
		}
////////////////////////////////////////////////////////////////////////////////////////////////
	}else if(!(getZone(getNOM_EF_PMATERIEL()).equals(""))){
		PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),recherche);
		if(getTransaction().isErreur()){
			System.out.println("Aucun résultat trouvé in OePM_TDB");
			messageInfo = getTransaction().traiterErreur();
			setPMaterielCourant(new PMateriel());
			setPMatInfosCourant(new PMatInfos());
			setListEntretiens(new ArrayList<PM_PePerso>());
			setLB_ENTRETIENS(LBVide);
			setStatut(STATUT_RECHERCHER,true);
			return false;
		}
		if(null==unPMatInfos){
			unPMatInfos = new PMatInfos();
		}
		setPMatInfosCourant(unPMatInfos);
		addZone(getNOM_EF_PMATERIEL(),"");
	}else{
		getTransaction().declarerErreur("Aucune zone de recherche n'est renseignée. Veuillez renseigner l'un des champs.");
		return false;
	}
//	pour garder le même petit matériel
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SELECTION
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_SELECTION() {
	return "NOM_PB_SELECTION";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_SELECTION(javax.servlet.http.HttpServletRequest request) throws Exception {
	String agent = getZone(getNOM_EF_AGENT());
	String service = getZone(getNOM_EF_SERVICE());
	String equip = getZone(getNOM_EF_PMATERIEL());
	
	if(((agent!=null)&&(!agent.equals("")))||((service!=null)&&(!service.equals("")))||((equip!=null)&&(!equip.equals("")))){
		performPB_RECHERCHE(request);
	}else{
		VariableActivite.ajouter(this,"MODE","TDBPMAT");
		setStatut(STATUT_RECHERCHER,true);
	}
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DACHAT
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DACHAT() {
	return "NOM_ST_DACHAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DACHAT
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DACHAT() {
	return getZone(getNOM_ST_DACHAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DGARANTIE
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DGARANTIE() {
	return "NOM_ST_DGARANTIE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DGARANTIE
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DGARANTIE() {
	return getZone(getNOM_ST_DGARANTIE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DMES
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DMES() {
	return "NOM_ST_DMES";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DMES
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DMES() {
	return getZone(getNOM_ST_DMES());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DMHS
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DMHS() {
	return "NOM_ST_DMHS";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DMHS
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DMHS() {
	return getZone(getNOM_ST_DMHS());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOMEQUIP
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOMEQUIP() {
	return "NOM_ST_NOMEQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOMEQUIP
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOMEQUIP() {
	return getZone(getNOM_ST_NOMEQUIP());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NUMINV
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NUMINV() {
	return "NOM_ST_NUMINV";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NUMINV
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NUMINV() {
	return getZone(getNOM_ST_NUMINV());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NUMSERIE
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NUMSERIE() {
	return "NOM_ST_NUMSERIE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NUMSERIE
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NUMSERIE() {
	return getZone(getNOM_ST_NUMSERIE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PRIX
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_PRIX() {
	return "NOM_ST_PRIX";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PRIX
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_PRIX() {
	return getZone(getNOM_ST_PRIX());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_RESERVE
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_RESERVE() {
	return "NOM_ST_RESERVE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_RESERVE
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_RESERVE() {
	return getZone(getNOM_ST_RESERVE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_AGENT
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_AGENT() {
	return "NOM_EF_AGENT";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_AGENT
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_AGENT() {
	return getZone(getNOM_EF_AGENT());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_FOURNISSEUR
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_FOURNISSEUR() {
	return "NOM_EF_FOURNISSEUR";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_FOURNISSEUR
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_FOURNISSEUR() {
	return getZone(getNOM_EF_FOURNISSEUR());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_PMATERIEL
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_PMATERIEL() {
	return "NOM_EF_PMATERIEL";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_PMATERIEL
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_PMATERIEL() {
	return getZone(getNOM_EF_PMATERIEL());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_SERVICE
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_SERVICE() {
	return "NOM_EF_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_SERVICE
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_SERVICE() {
	return getZone(getNOM_EF_SERVICE());
}

/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_AFFECTATION
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_AFFECTATION() {
	return "NOM_LB_AFFECTATION";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_AFFECTATION_SELECT
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_AFFECTATION_SELECT() {
	return "NOM_LB_AFFECTATION_SELECT";
}

/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_AFFECTATION
 * Date de création : (10/05/07 11:11:35)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_AFFECTATION_SELECT() {
	return getZone(getNOM_LB_AFFECTATION_SELECT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (10/05/07 11:15:09)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (10/05/07 11:15:09)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
	private java.lang.String[] LB_ENTRETIENS;
/**
 * Getter de la liste avec un lazy initialize :
 * LB_ENTRETIENS
 * Date de création : (10/05/07 11:27:30)
 * @author : Générateur de process
 */
private String [] getLB_ENTRETIENS() {
	if (LB_ENTRETIENS == null)
		LB_ENTRETIENS = initialiseLazyLB();
	return LB_ENTRETIENS;
}
/**
 * Setter de la liste:
 * LB_ENTRETIENS
 * Date de création : (10/05/07 11:27:30)
 * @author : Générateur de process
 */
private void setLB_ENTRETIENS(java.lang.String[] newLB_ENTRETIENS) {
	LB_ENTRETIENS = newLB_ENTRETIENS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_ENTRETIENS
 * Date de création : (10/05/07 11:27:30)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_ENTRETIENS() {
	return "NOM_LB_ENTRETIENS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_ENTRETIENS_SELECT
 * Date de création : (10/05/07 11:27:30)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_ENTRETIENS_SELECT() {
	return "NOM_LB_ENTRETIENS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_ENTRETIENS
 * Date de création : (10/05/07 11:27:30)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_ENTRETIENS() {
	return getLB_ENTRETIENS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_ENTRETIENS
 * Date de création : (10/05/07 11:27:30)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_ENTRETIENS_SELECT() {
	return getZone(getNOM_LB_ENTRETIENS_SELECT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_AGENT_RESPONSABLE
 * Date de création : (10/05/07 11:29:52)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_AGENT_RESPONSABLE() {
	return "NOM_ST_AGENT_RESPONSABLE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_AGENT_RESPONSABLE
 * Date de création : (10/05/07 11:29:52)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_AGENT_RESPONSABLE() {
	return getZone(getNOM_ST_AGENT_RESPONSABLE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_CARBU
 * Date de création : (10/05/07 11:29:52)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_CARBU() {
	return "NOM_ST_CARBU";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_CARBU
 * Date de création : (10/05/07 11:29:52)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_CARBU() {
	return getZone(getNOM_ST_CARBU());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMPTEUR
 * Date de création : (10/05/07 11:29:52)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_COMPTEUR() {
	return "NOM_ST_COMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMPTEUR
 * Date de création : (10/05/07 11:29:52)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_COMPTEUR() {
	return getZone(getNOM_ST_COMPTEUR());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PNEUS
 * Date de création : (10/05/07 11:29:52)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_PNEUS() {
	return "NOM_ST_PNEUS";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PNEUS
 * Date de création : (10/05/07 11:29:52)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_PNEUS() {
	return getZone(getNOM_ST_PNEUS());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PUISSANCE
 * Date de création : (10/05/07 11:29:52)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_PUISSANCE() {
	return "NOM_ST_PUISSANCE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PUISSANCE
 * Date de création : (10/05/07 11:29:52)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_PUISSANCE() {
	return getZone(getNOM_ST_PUISSANCE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_RESERVOIR
 * Date de création : (10/05/07 11:29:52)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_RESERVOIR() {
	return "NOM_ST_RESERVOIR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_RESERVOIR
 * Date de création : (10/05/07 11:29:52)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_RESERVOIR() {
	return getZone(getNOM_ST_RESERVOIR());
}

public PMateriel getPMateriel(){
	return pMaterielCourant;
}

public void setPMaterielCourant(PMateriel pMaterielCourant){
	this.pMaterielCourant = pMaterielCourant;
}

public PMatInfos getPMatInfosCourant(){
	return pMatInfosCourant;
}

public void setPMatInfosCourant(PMatInfos pMatInfosCourant){
	this.pMatInfosCourant = pMatInfosCourant;
	pMatInfosCourantChange=true;
}

public ArrayList<PM_PePerso> getListEntretiens() {
	return listEntretiens;
}
public void setListEntretiens(ArrayList<PM_PePerso> listEntretiens) {
	this.listEntretiens = listEntretiens;
}

public ArrayList<PM_Affectation_Sce_Infos> getListServices() {
	return listServices;
}
public void setListFpm(ArrayList<FPM> listFpm) {
	this.listFpm = listFpm;
}
public ArrayList<FPM> getListFpm() {
	return listFpm;
}
public void setListServices(ArrayList<PM_Affectation_Sce_Infos> listServices) {
	this.listServices = listServices;
}
public boolean getFirst(){
	return first;
}

public void setFirst(boolean first){
	this.first = first;
}

/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_FOURNISSEUR
 * Date de création : (10/05/07 14:09:15)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_FOURNISSEUR() {
	return "NOM_ST_FOURNISSEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_FOURNISSEUR
 * Date de création : (10/05/07 14:09:15)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_FOURNISSEUR() {
	return getZone(getNOM_ST_FOURNISSEUR());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_SERVICES
 * Date de création : (10/05/07 14:49:33)
 * @author : Générateur de process
 */
private String [] getLB_SERVICES() {
	if (LB_SERVICES == null)
		LB_SERVICES = initialiseLazyLB();
	return LB_SERVICES;
}
/**
 * Setter de la liste:
 * LB_SERVICES
 * Date de création : (10/05/07 14:49:33)
 * @author : Générateur de process
 */
private void setLB_SERVICES(java.lang.String[] newLB_SERVICES) {
	LB_SERVICES = newLB_SERVICES;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_SERVICES
 * Date de création : (10/05/07 14:49:33)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_SERVICES() {
	return "NOM_LB_SERVICES";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_SERVICES_SELECT
 * Date de création : (10/05/07 14:49:33)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_SERVICES_SELECT() {
	return "NOM_LB_SERVICES_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_SERVICES
 * Date de création : (10/05/07 14:49:33)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_SERVICES() {
	return getLB_SERVICES();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_SERVICES
 * Date de création : (10/05/07 14:49:33)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_SERVICES_SELECT() {
	return getZone(getNOM_LB_SERVICES_SELECT());
}
	private java.lang.String[] LB_FPM;
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_DETAILS_FPM
 * Date de création : (05/07/07 15:20:43)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_DETAILS_FPM() {
	return "NOM_PB_DETAILS_FPM";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (05/07/07 15:20:43)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_DETAILS_FPM(javax.servlet.http.HttpServletRequest request) throws Exception {
//	 on envoie la FPM sélectionnée
	int indice  = (Services.estNumerique(getVAL_LB_FPM_SELECT()) ? Integer.parseInt(getVAL_LB_FPM_SELECT()): -1);
	if (indice == -1 || getListFpm().size() == 0 || indice > getListFpm().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Fiche d'entretiens du petit matériel"));
		return false;
	}
	FPM unFPM = (FPM)getListFpm().get(indice);
	VariableGlobale.ajouter(request,"FPM",unFPM);
	VariableActivite.ajouter(this,"DEBRANCHE","TRUE");
	setStatut(STATUT_FPM,true);
	return true;
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_FPM
 * Date de création : (05/07/07 15:20:43)
 * @author : Générateur de process
 */
private String [] getLB_FPM() {
	if (LB_FPM == null)
		LB_FPM = initialiseLazyLB();
	return LB_FPM;
}
/**
 * Setter de la liste:
 * LB_FPM
 * Date de création : (05/07/07 15:20:43)
 * @author : Générateur de process
 */
private void setLB_FPM(java.lang.String[] newLB_FPM) {
	LB_FPM = newLB_FPM;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_FPM
 * Date de création : (05/07/07 15:20:43)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_FPM() {
	return "NOM_LB_FPM";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_FPM_SELECT
 * Date de création : (05/07/07 15:20:43)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_FPM_SELECT() {
	return "NOM_LB_FPM_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_FPM
 * Date de création : (05/07/07 15:20:43)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_FPM() {
	return getLB_FPM();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_FPM
 * Date de création : (05/07/07 15:20:43)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_FPM_SELECT() {
	return getZone(getNOM_LB_FPM_SELECT());
}
public boolean trierDecl(ArrayList<Declarations> a) throws Exception{
	String declarant = "agent non trouvé";
	String[] colonnes = {"date"};
	//ordre croissant
	boolean[] ordres = {false};
	
//	Si au moins une affectation
	if (a.size() !=0 ) {
		ArrayList<Declarations> aTrier = Services.trier(a,colonnes,ordres);
		setListDeclarations(aTrier);
		int tailles [] = {10,49,10,10};
		String[] padding = {"C","G","C","C"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (ListIterator<Declarations> list = aTrier.listIterator(); list.hasNext(); ) {
			Declarations aDeclarations = (Declarations)list.next();
			// recherche de l'agent selon le service affecté
			AgentsMunicipaux unAgent = AgentsMunicipaux.chercherAgentsMunicipauxService(getTransaction(),aDeclarations.getMatricule(),aDeclarations.getCodeservice());
			if(getTransaction().isErreur()){
				return false;
			}
			declarant = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
			String ligne [] = {aDeclarations.getDate(),declarant,aDeclarations.getCodeot(),aDeclarations.getCodeservice()};
			aFormat.ajouteLigne(ligne);
			
		}
		setLB_DECLARATIONS(aFormat.getListeFormatee());
	} else {
		setLB_DECLARATIONS(null);
	}
	return true;
}
public void trierFpm(ArrayList<FPM> a) throws Exception{
	String[] colonnes = {"numfiche","dentree"};
	//ordre decroissant
	boolean[] ordres = {false,false};
	String dentree = "";
	String dsortie = "";
	int totalOt = 0;
	
//	Si au moins une fiche pm pour PePerso
	if (a.size() !=0 ) {
		ArrayList<FPM> aTrier = Services.trier(a,colonnes,ordres);
		setListFpm(aTrier);
		int tailles [] = {10,10,10,10};
		String[] padding = {"D","C","C","D"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (int i = 0; i < aTrier.size() ; i++) {
			FPM unFPM = (FPM)aTrier.get(i);	
			
			//	calcul du montant total de la fiche pm
			FPM.chercherFPM(getTransaction(),unFPM.getNumfiche());
			if(getTransaction().isErreur()){
				return ;
			}
			totalOt = ENGJU.CoutTotal_PM(getTransaction(),unFPM);
			if(getTransaction().isErreur()){
				return ;
			}
			if(totalOt==-1){
				totalOt = 0;
			}else{
				if (totalOt==-2){
					messageInfo = "Une erreur est survenue lors du calcul du montant du bon d'engagement.";
					getTransaction().traiterErreur();
					totalOt = 0;
				}
			}
			
			// affichage 
			if (unFPM.getDentree().equals("01/01/0001")){
				dentree="";
			}else{
				dentree=unFPM.getDentree();
			}
			if(unFPM.getDsortie().equals("01/01/0001")){
				dsortie="";
			}else{
				dsortie=unFPM.getDsortie();
			}
			String ligne [] = { unFPM.getNumfiche(),dentree,dsortie,String.valueOf(totalOt)};
			aFormat.ajouteLigne(ligne);
		}
		setLB_FPM(aFormat.getListeFormatee());
	} else {
		setLB_FPM(null);
	}
	return ;
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
	return getNOM_EF_PMATERIEL();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RESPONSABLE
 * Date de création : (31/07/07 13:48:06)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_RESPONSABLE() {
	return "NOM_PB_RESPONSABLE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (31/07/07 13:48:06)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_RESPONSABLE(javax.servlet.http.HttpServletRequest request) throws Exception {
	int indice  = (Services.estNumerique(getVAL_LB_SERVICES_SELECT()) ? Integer.parseInt(getVAL_LB_SERVICES_SELECT()): -1);
	if (indice == -1 || getListServices().size() == 0 || indice > getListServices().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","AFFECTATIONS"));
		return false;
	}
	PM_Affectation_Sce_Infos unService = (PM_Affectation_Sce_Infos)getListServices().get(indice);
	if("0".equals(unService.getNomatr())){
		addZone(getNOM_ST_AGENT(),"Aucun");
	}else{
		AgentsMunicipaux unAgent = AgentsMunicipaux.chercherAgentsMunicipauxService(getTransaction(),unService.getNomatr(),unService.getSiserv());
		if(getTransaction().isErreur()){
			return false;
		}
		addZone(getNOM_ST_AGENT(),unAgent.getNom().trim()+" "+unAgent.getPrenom().trim());
	}
	
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_AGENT
 * Date de création : (31/07/07 13:48:06)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_AGENT() {
	return "NOM_ST_AGENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_AGENT
 * Date de création : (31/07/07 13:48:06)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_AGENT() {
	return getZone(getNOM_ST_AGENT());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RETOUR
 * Date de création : (31/07/07 14:02:00)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_RETOUR() {
	return "NOM_PB_RETOUR";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (31/07/07 14:02:00)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_RETOUR(javax.servlet.http.HttpServletRequest request) throws Exception {
	PM_Affectation_Sce_Infos unASI = PM_Affectation_Sce_Infos.chercherPM_Affectation_Sce_InfosCourantPm(getTransaction(),getPMatInfosCourant().getPminv(),Services.dateDuJour());
	if(getTransaction().isErreur()){
		// l'équipement peut ne pas être affecté
		getTransaction().traiterErreur();
	}
//	setPMaterielCourant(null);
//	setPMatInfosCourant(null);
	VariableActivite.ajouter(this,"NOMATR",unASI.getNomatr());
	VariableActivite.ajouter(this,"ORIGINE","TDB");
	setStatut(STATUT_SELECTION,true);
	return true;
}
	private java.lang.String[] LB_DECLARATIONS;
/**
 * Getter de la liste avec un lazy initialize :
 * LB_DECLARATIONS
 * Date de création : (19/09/07 13:03:38)
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
 * Date de création : (19/09/07 13:03:38)
 * @author : Générateur de process
 */
private void setLB_DECLARATIONS(java.lang.String[] newLB_DECLARATIONS) {
	LB_DECLARATIONS = newLB_DECLARATIONS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_DECLARATIONS
 * Date de création : (19/09/07 13:03:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_DECLARATIONS() {
	return "NOM_LB_DECLARATIONS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_DECLARATIONS_SELECT
 * Date de création : (19/09/07 13:03:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_DECLARATIONS_SELECT() {
	return "NOM_LB_DECLARATIONS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_DECLARATIONS
 * Date de création : (19/09/07 13:03:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_DECLARATIONS() {
	return getLB_DECLARATIONS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_DECLARATIONS
 * Date de création : (19/09/07 13:03:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_DECLARATIONS_SELECT() {
	return getZone(getNOM_LB_DECLARATIONS_SELECT());
}
public void setListDeclarations(ArrayList<Declarations> listDeclarations) {
	this.listDeclarations = listDeclarations;
}
public ArrayList<Declarations> getListDeclarations() {
	if(listDeclarations==null){
		listDeclarations = new ArrayList<Declarations>();
	}
	return listDeclarations;
}
public ArrayList<BPC> getListBPC() {
	if(listBPC==null){
		listBPC = new ArrayList<BPC>();
	}
	return listBPC;
}
public void setListBPC(ArrayList<BPC> listBPC) {
	this.listBPC = listBPC;
}

/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (10/05/07 11:11:34)
 * @author : Générateur de process
 */
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_DETAILSDEC
		if (testerParametre(request, getNOM_PB_DETAILSDEC())) {
			return performPB_DETAILSDEC(request);
		}

		//Si clic sur le bouton PB_RETOUR
		if (testerParametre(request, getNOM_PB_RETOUR())) {
			return performPB_RETOUR(request);
		}

		//Si clic sur le bouton PB_RESPONSABLE
		if (testerParametre(request, getNOM_PB_RESPONSABLE())) {
			return performPB_RESPONSABLE(request);
		}

		//Si clic sur le bouton PB_DETAILS_FPM
		if (testerParametre(request, getNOM_PB_DETAILS_FPM())) {
			return performPB_DETAILS_FPM(request);
		}

		//Si clic sur le bouton PB_RECHERCHE
		if (testerParametre(request, getNOM_PB_RECHERCHE())) {
			return performPB_RECHERCHE(request);
		}

		//Si clic sur le bouton PB_SELECTION
		if (testerParametre(request, getNOM_PB_SELECTION())) {
			return performPB_SELECTION(request);
		}
		//Si clic sur le bouton PB_DETAILSBPC
		if (testerParametre(request, getNOM_PB_DETAILSBPC())) {
			return performPB_DETAILSBPC(request);
		}

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (19/09/07 13:28:53)
 * @author : Générateur de process
 */
public String getJSP() {
	return "OePM_TDB.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_DETAILSDEC
 * Date de création : (19/09/07 13:28:53)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_DETAILSDEC() {
	return "NOM_PB_DETAILSDEC";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (19/09/07 13:28:53)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_DETAILSDEC(javax.servlet.http.HttpServletRequest request) throws Exception {
//	 on envoie la déclaration sélectionnée
	int indice  = (Services.estNumerique(getVAL_LB_DECLARATIONS_SELECT()) ? Integer.parseInt(getVAL_LB_DECLARATIONS_SELECT()): -1);
	if (indice == -1 || getListDeclarations().size() == 0 || indice > getListDeclarations().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","DECLARATIONS"));
		return false;
	}
	Declarations uneDec = (Declarations)getListDeclarations().get(indice);
	VariableGlobale.ajouter(request,"DECLARATION",uneDec);
	VariableActivite.ajouter(this,"DEBRANCHE","TRUE");
	setStatut(STATUT_DECLARATIONS,true);
	return true;
}

/**
 * Getter de la liste avec un lazy initialize :
 * LB_BPC
 * Date de création : (17/08/05 14:02:45)
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
 * Date de création : (17/08/05 14:02:45)
 * @author : Générateur de process
 */
private void setLB_BPC(java.lang.String[] newLB_BPC) {
	LB_BPC = newLB_BPC;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_BPC
 * Date de création : (17/08/05 14:02:45)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_BPC() {
	return "NOM_LB_BPC";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_BPC_SELECT
 * Date de création : (17/08/05 14:02:45)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_BPC_SELECT() {
	return "NOM_LB_BPC_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_BPC
 * Date de création : (17/08/05 14:02:45)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_BPC() {
	return getLB_BPC();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_BPC
 * Date de création : (17/08/05 14:02:45)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_BPC_SELECT() {
	return getZone(getNOM_LB_BPC_SELECT());
}

/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_DETAILSBPC
 * Date de création : (17/08/05 14:02:45)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_DETAILSBPC() {
	return "NOM_PB_DETAILSBPC";
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_BPC_TOTAL
 * Date de création : (24/08/05 14:17:33)
 * @author : Générateur de process
 */
private String [] getLB_BPC_TOTAL() {
	if (LB_BPC_TOTAL == null)
		LB_BPC_TOTAL = initialiseLazyLB();
	return LB_BPC_TOTAL;
}
/**
 * Setter de la liste:
 * LB_BPC_TOTAL
 * Date de création : (24/08/05 14:17:33)
 * @author : Générateur de process
 */
private void setLB_BPC_TOTAL(java.lang.String[] newLB_BPC_TOTAL) {
	LB_BPC_TOTAL = newLB_BPC_TOTAL;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_BPC_TOTAL
 * Date de création : (24/08/05 14:17:33)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_BPC_TOTAL() {
	return "NOM_LB_BPC_TOTAL";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_BPC_TOTAL_SELECT
 * Date de création : (24/08/05 14:17:33)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_BPC_TOTAL_SELECT() {
	return "NOM_LB_BPC_TOTAL_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_BPC_TOTAL
 * Date de création : (24/08/05 14:17:33)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_BPC_TOTAL() {
	return getLB_BPC_TOTAL();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_BPC_TOTAL
 * Date de création : (24/08/05 14:17:33)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_BPC_TOTAL_SELECT() {
	return getZone(getNOM_LB_BPC_TOTAL_SELECT());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE_EQUIP
 * Date de création : (25/08/05 08:47:47)
 * @author : Générateur de process
 * @return String
 */

/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (17/08/05 14:02:45)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_DETAILSBPC(javax.servlet.http.HttpServletRequest request) throws Exception {
	// on envoie le BPC sélectionné
	int indice  = (Services.estNumerique(getVAL_LB_BPC_SELECT()) ? Integer.parseInt(getVAL_LB_BPC_SELECT()): -1);
	if (indice == -1 || getListBPC().size() == 0 || indice > getListBPC().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","BPC"));
		return false;
	}
	BPC unBPC = (BPC)getListBPC().get(indice);
	// on envoie le BPC à la fenetre de visualisation d'un BPC
	VariableGlobale.ajouter(request,"BPC",unBPC);
	VariableActivite.ajouter(this,"DEBRANCHE","TRUE");
	//VariableActivite.ajouter(this,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	setStatut(STATUT_BPC,true);
	return true;
}


}
