package nc.mairie.seat.process;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;

import nc.mairie.seat.metier.AActifs;
import nc.mairie.seat.metier.AffectationServiceInfos;
import nc.mairie.seat.metier.AgentCCAS;
import nc.mairie.seat.metier.AgentCDE;
import nc.mairie.seat.metier.Agents;
import nc.mairie.seat.metier.AgentsMunicipaux;
import nc.mairie.seat.metier.BPC;
import nc.mairie.seat.metier.Declarations;
import nc.mairie.seat.metier.ENGJU;
import nc.mairie.seat.metier.Equipement;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.Modeles;
import nc.mairie.seat.metier.OT;
import nc.mairie.seat.metier.PePersoInfos;
import nc.mairie.seat.metier.Pneu;
import nc.mairie.technique.*;
/**
 * Process OeTableauDeBord
 * Date de création : (17/08/05 14:02:45)
* 
*/
public class OeTableauDeBord extends nc.mairie.technique.BasicProcess {
	public static final int STATUT_SELECTION = 6;
	public static final int STATUT_AGENT = 5;
	public static final int STATUT_DECLARATIONS = 4;
	public static final int STATUT_OT = 3;
	public static final int STATUT_BPC = 2;
	public static final int STATUT_RECHERCHER = 1;
	private java.lang.String[] LB_BPC;
	private java.lang.String[] LB_OT;
	private ArrayList listBPC;
	private ArrayList listDeclarations;
	private ArrayList listOTInfos;
	private ArrayList listEntretiens;
	private ArrayList listServices;
	private EquipementInfos equipementInfosCourant;
	private OT otCourant;
	private BPC bpcCourant;
	private Equipement equipementCourant;
	private String totalQte ;
	private String nbBPC;
	private String kmParcouru;
	private String moyenne;
	public boolean isVideBPC;
	public boolean isVideOT;
	public boolean isVideDec;
	public boolean equipementInfosCourantChange = true;
	private boolean first=true;
	public String messageInfo;
	private String focus = null;
	public String reserve = "";
	public boolean isDebranche = false;
	String agent = "";
	public Hashtable<String, AgentCDE> hashAgentCDE;
	public Hashtable<String, AgentCCAS> hashAgentCCAS;
	public Hashtable<String, AActifs> hashAActifs;
	
	
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (17/08/05 14:02:45)
* 
 */
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	String responsable = "agent non trouvé";
	addZone(getNOM_EF_AGENT(),"");
	addZone(getNOM_EF_RECHERCHE_EQUIP(),"");
	addZone(getNOM_EF_SERVICE(),"");
	//LB débile !!!!!
	//if(first){
	//	if(getTransaction().isErreur()){
	//		getTransaction().traiterErreur();
	//	}
	//	/*EquipementInfos unEquipementInfos = (EquipementInfos)VariableGlobale.recuperer(request, "EQUIPEMENTINFOS");
	//	if (unEquipementInfos==null){
	//		performPB_RECHERCHER(request);
	//	}*/
	//}

	//Si liste des actifs vide
	if (getHashAActifs().size() == 0) {
		ArrayList arr = AActifs.listerAActifs(getTransaction());
		for (Iterator iter = arr.iterator(); iter.hasNext();) {
			AActifs objet = (AActifs) iter.next();
			getHashAActifs().put(objet.getNomatr(), objet);
		}
	}
	if (getHashAgentCCAS().size() == 0) {
		ArrayList arr = AgentCCAS.listerAgentCCAS(getTransaction());
		for (Iterator iter = arr.iterator(); iter.hasNext();) {
			AgentCCAS objet = (AgentCCAS) iter.next();
			getHashAgentCCAS().put(objet.getNomatr(), objet);
		}
	}
	if (getHashAgentCDE().size() == 0) {
		ArrayList arr = AgentCDE.listerAgentCDE(getTransaction());
		for (Iterator iter = arr.iterator(); iter.hasNext();) {
			AgentCDE objet = (AgentCDE) iter.next();
			getHashAgentCDE().put(objet.getNomatr(), objet);
		}
	}
	
	if((first)||(etatStatut()==STATUT_RECHERCHER)||(etatStatut()==STATUT_AGENT)){
		EquipementInfos unEquipementInfos = (EquipementInfos)VariableGlobale.recuperer(request, "EQUIPEMENTINFOS");
		if (unEquipementInfos!=null){
			if(unEquipementInfos.getNumeroinventaire()!=null){
				if (unEquipementInfos.existeEquipementInfosEquip(getTransaction(),unEquipementInfos.getNumeroinventaire())){
					setEquipementInfosCourant(unEquipementInfos);
				}else{
					VariableGlobale.enlever(request,"EQUIPEMENTINFOS");
				}
			}
		}
		if((etatStatut()==STATUT_RECHERCHER)||(etatStatut()==STATUT_AGENT)||(first)){
			agent = (String)VariableActivite.recuperer(this,"NOMATR");
			if((agent!=null)&&(!agent.equals(""))){
				addZone(getNOM_EF_AGENT(),agent);
				recherche_AGENT(request);
			}
			String sce = (String)VariableActivite.recuperer(this,"CODESERVICE");
			if((sce!=null)&&(!sce.equals(""))){
				addZone(getNOM_EF_SERVICE(),sce.toUpperCase());
				recherche_SERVICE(request);
			}
			VariableActivite.enlever(this,"NOMATR");
			VariableActivite.enlever(this,"CODESERVICE");
		}
		addZone(getNOM_EF_RECHERCHE_EQUIP(),"");
		String retour = (String)VariableActivite.recuperer(this,"DEBRANCHE");
		if("TRUE".equals(retour)){
			isDebranche = true;
		}else{
			isDebranche = false;
		}
	}//else{
//	//quand appui sur entree
//		if(!getZone(getNOM_EF_RECHERCHE_EQUIP()).equals("")){
//			performPB_RECHERCHE_EQUIP(request);
//		}
//	}
	if (equipementInfosCourantChange) {	
		if (null!=(getEquipementInfosCourant())){
			equipementInfosCourantChange = false;
			if(null!=getEquipementInfosCourant().getNumeroinventaire()){
				Equipement monEquip = Equipement.chercherEquipement(getTransaction(),equipementInfosCourant.getNumeroinventaire());
				if (getTransaction().isErreur()){
					return;
				}
				setEquipementCourant(monEquip);
				addZone(getNOM_ST_CARBU(),getEquipementInfosCourant().getDesignationcarbu());
				addZone(getNOM_ST_COMPTEUR(),getEquipementInfosCourant().getDesignationcompteur());
				addZone(getNOM_ST_DATEHORSCIRCUIT(),getEquipementInfosCourant().getDatehorscircuit());
				addZone(getNOM_ST_DATEENSERVICE(),getEquipementInfosCourant().getDatemiseencirculation());
				addZone(getNOM_ST_DATEVENTEREFORME(),getEquipementCourant().getDateventeoureforme());
				addZone(getNOM_ST_MARQUE(),getEquipementInfosCourant().getDesignationmarque());
				addZone(getNOM_ST_MODELE(),getEquipementInfosCourant().getDesignationmodele());
				addZone(getNOM_ST_NOIMMAT(),getEquipementInfosCourant().getNumeroimmatriculation());
				addZone(getNOM_ST_NOINV(),getEquipementInfosCourant().getNumeroinventaire());
				if(getEquipementCourant().getReserve().equals("T")){
					reserve = "oui";
				}else{
					reserve = "non";
				}
				Modeles monModele = Modeles.chercherModeles(getTransaction(),equipementCourant.getCodemodele());
				if (getTransaction().isErreur()){
					return;
				}
				Pneu monPneu = Pneu.chercherPneu(getTransaction(),monModele.getCodepneu());
				if (getTransaction().isErreur()){
					return;
				}
				addZone(getNOM_ST_PNEUS(),monPneu.getDimension());
				addZone(getNOM_ST_PRIX(),equipementCourant.getPrixachat());
				addZone(getNOM_ST_PUISSANCE(),monModele.getPuissance());
				addZone(getNOM_ST_RESERVOIR(),monModele.getCapacitereservoir());
				addZone(getNOM_ST_TYPE(),equipementInfosCourant.getDesignationtypeequip());		
				addZone(getNOM_ST_NOMEQUIP(),getVAL_ST_MARQUE()+" "+getVAL_ST_MODELE());
				addZone(getNOM_ST_VERSION(),equipementInfosCourant.getVersion());
				addZone(getNOM_ST_PE(),getVAL_ST_MARQUE()+" "+getVAL_ST_MODELE());
		//		Si les dates sont = 01/01/0001 alors on met vides les zones
				if ("01/01/0001".equals(equipementCourant.getDatehorscircuit())){
					addZone(getNOM_ST_DATEHORSCIRCUIT(), "");
				}else{
					addZone(getNOM_ST_DATEHORSCIRCUIT(), equipementCourant.getDatehorscircuit());
				}
				if ("01/01/0001".equals(equipementCourant.getDatemiseencirculation())){
					addZone(getNOM_ST_DATEENSERVICE(), "");
				}else{
					addZone(getNOM_ST_DATEENSERVICE(), equipementCourant.getDatemiseencirculation());
				}
				if ("01/01/0001".equals(equipementCourant.getDateventeoureforme())){
					addZone(getNOM_ST_DATEVENTEREFORME(),"");
				}else{
					addZone(getNOM_ST_DATEVENTEREFORME(), equipementCourant.getDateventeoureforme());
				}
				// recherche du service auquel l'équipement est affecté et l'agent responsable
				AffectationServiceInfos unASI = AffectationServiceInfos.chercherAffectationServiceInfosCourantEquip(getTransaction(),getEquipementInfosCourant().getNumeroinventaire(),Services.dateDuJour());
				if(getTransaction().isErreur()){
					// l'équipement peut ne pas être affecté
					getTransaction().traiterErreur();
				}
		
				if (unASI!=null){
					if(unASI.getCodeservice()!=null){
						addZone(getNOM_ST_SERVICE(),unASI.getCodeservice()+" "+unASI.getLiserv());
					}else{
						addZone(getNOM_ST_SERVICE(),"pas affecté");
					}
					
					if ((unASI.getNomatr()!=null)&&(!unASI.getNomatr().equals("0"))){
						// recherche de l'agent responsable si l'équipement a un responsable
						// selon le service
						if (unASI.getCodeservice().equals("4000")){
							/*AgentCDE unAgent =AgentCDE.chercherAgentCDE(getTransaction(),unASI.getNomatr());
							if(getTransaction().isErreur()){
								getTransaction().traiterErreur();
								responsable = "agent non trouvé";
							}else{
								responsable = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
							}*/
							AgentCDE unAgent =getHashAgentCDE().get(unASI.getNomatr());
							if(unAgent==null){
								responsable = "agent non trouvé";
							}else{
								responsable = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
							}
							//addZone(getNOM_ST_AGENT_RESPONSABLE(),unAgent.getNom().trim()+" "+unAgent.getPrenom().trim());
						}else if (unASI.getCodeservice().equals("5000")){
							/*AgentCCAS unAgent = AgentCCAS.chercherAgentCCAS(getTransaction(),unASI.getNomatr());
							if (getTransaction().isErreur()){
								getTransaction().traiterErreur();
								responsable = "agent non trouvé";
							}else{
								responsable = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
							}
							*/
							AgentCCAS unAgent = getHashAgentCCAS().get(unASI.getNomatr());
							if (unAgent == null){
								responsable = "agent non trouvé";
							}else{
								responsable = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
							}
								
							//addZone(getNOM_ST_AGENT_RESPONSABLE(),unAgent.getNom().trim()+" "+unAgent.getPrenom().trim());
						}else{
							
							// recherche classique
							/*AActifs unAgent = AActifs.chercherAActifs(getTransaction(),unASI.getNomatr());
							if(getTransaction().isErreur()){
								getTransaction().traiterErreur();
								responsable = "agent non trouvé";
							}else{
								responsable = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
							}
							*/
							AActifs unAgent = getHashAActifs().get(unASI.getNomatr());
							if(unAgent == null){
								responsable = "agent non trouvé";
							}else{
								responsable = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
							}
							//addZone(getNOM_ST_AGENT_RESPONSABLE(),unAgent.getNom().trim()+" "+unAgent.getPrenom().trim());
						}
					}else{
						//addZone(getNOM_ST_AGENT_RESPONSABLE(),"sans");
						responsable = "sans";
					}
					addZone(getNOM_ST_AGENT_RESPONSABLE(),responsable);
				}
		//		 initialisation des listes
				initialiseListBPC(request);
				initialiseListOT(request);
				initialiseListEntretiens(request);
				initialiseListServices(request);
				initialiseListDec(request);
			}
		}
	}
	
	//si liste des OT est vide le bouton Détails n'apparait pas
	if(getListOTInfos().size()>0){
		setVideOT(true);
	}else{
		setVideOT(false);
	}
	//si liste des BPC est vide le bouton Détails n'apparait pas
	if(getListBPC().size()>0){
		setVideBPC(true);
	}else{
		setVideBPC(false);
	}
//	si liste des déclarations est vide le bouton Détails n'apparait pas
	if(getListDeclarations().size()>0){
		setVideDec(true);
	}else{
		setVideDec(false);
	}
	
	setFirst(false);
//	 pour l'erreur dû au bon d'engagement
	if ((!("").equals(messageInfo)) &&(messageInfo!=null)){
		getTransaction().declarerErreur(messageInfo);
		messageInfo = "";
		return;
	}
	
//	if(getTransaction().isErreur()){
//		return ;
//	}
	// quand appuie sur entrée
	if((!("").equals(getZone(getNOM_EF_AGENT())))||(!("").equals(getZone(getNOM_EF_RECHERCHE_EQUIP())))||(!("").equals(getZone(getNOM_EF_SERVICE())))){
		performPB_RECHERCHE_EQUIP(request);
		addZone(getNOM_EF_AGENT(),"");
		addZone(getNOM_EF_SERVICE(),"");
		addZone(getNOM_EF_RECHERCHE_EQUIP(),"");
	}
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
	ArrayList listBPC = BPC.listeBPCEquipAnnee(getTransaction(),getEquipementInfosCourant().getNumeroinventaire(),annee);
	if(getTransaction().isErreur()){
		return false;
	}
	setListBPC(listBPC);
	if(getListBPC().size()>0){
		trierBPC(listBPC);
	}else{
		setListBPC(new ArrayList());
	}
	initialiseListeTotal(request);
	return true;
}

public boolean initialiseListDec (javax.servlet.http.HttpServletRequest request) throws Exception{
	if(getTransaction().isErreur()){
		return false;
	}
	// on veut toutes les déclarations concernant l'équipement
	
	ArrayList listDec = Declarations.listerDeclarationsEquip(getTransaction(),getEquipementInfosCourant().getNumeroinventaire());
	if(getTransaction().isErreur()){
		return false;
	}

	setListDeclarations(listDec);
	trierDecl(listDec);
	if(getListDeclarations().size()>0){
		isVideDec = false;
		//AJOUT OFONTENEAU
		//setListDeclarations(getListServices());
	}else{
		setListDeclarations(new ArrayList());
		isVideDec = true;
	}
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

/*
 * initialisation de la liste des OT
 * Date : 18/08/05
 * autheur : CN
 */
public boolean initialiseListOT(javax.servlet.http.HttpServletRequest request) throws Exception{
	if(getTransaction().isErreur()){
		return false;
	}
	//ArrayList listOTInfos = OTInfos.listerOTInfosEquip(getTransaction(),getEquipementInfosCourant().getNumeroinventaire());
	ArrayList listOT = OT.listerOTEquip(getTransaction(),getEquipementInfosCourant().getNumeroinventaire());
	if(getTransaction().isErreur()){
		return false;
	}
	setListOTInfos(listOT);
	if(getListOTInfos().size()>0){
		trierOT(listOT);
	}
	return true;
}
public void initialiseListServices(javax.servlet.http.HttpServletRequest request) throws Exception{
//	Recherche des affectations des équipement aux services
	java.util.ArrayList a = AffectationServiceInfos.chercherListAffectationServiceInfosEquip(getTransaction(),equipementInfosCourant.getNumeroinventaire());
	if (null == a){
		System.out.println("Aucun élément enregistré dans la base.");
	}
	setListServices(a);
	trierServices(a);
	//return ;	
}
/*initialisation de la liste des Entretiens
 * Date : 22/08/05
 * autheur : CN
 */
public boolean initialiseListEntretiens(javax.servlet.http.HttpServletRequest request) throws Exception{
	ArrayList listEntretiens = PePersoInfos.listerPePersoInfosFait(getTransaction(),getEquipementInfosCourant().getNumeroinventaire(),"datereal desc");
	if(getTransaction().isErreur()){
		return false;
	}
	String date = "";
	setListEntretiens(listEntretiens);
	if(getListEntretiens().size()>0){
		int tailles [] = {10,30,10};
		String[] padding = {"C","G","D"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for(int i=0;i<getListEntretiens().size();i++){
			PePersoInfos unPePersoInfos = (PePersoInfos)getListEntretiens().get(i);	
			// affichage 
			if ((unPePersoInfos.getDatereal()==null)||(unPePersoInfos.getDatereal().equals("01/01/0001"))){
				date="";
			}else{
				date=unPePersoInfos.getDatereal();
			}
			
			String compteur="";
			if (null!=unPePersoInfos.getCodeot()){
				OT monOT=OT.chercherOT(getTransaction(), unPePersoInfos.getCodeot());
				if (null!=monOT&&null!=monOT.getCompteur())
					compteur=monOT.getCompteur();
			}
			
			String ligne [] = { date,unPePersoInfos.getLibelleentretien(),compteur};
			aFormat.ajouteLigne(ligne);
		}
			setLB_ENTRETIENS(aFormat.getListeFormatee());
	} else {
		setLB_ENTRETIENS(null);
	}
	return true;
}

public void trierBPC(ArrayList a) throws Exception{
	String[] colonnes = {"date","valeurcompteur"};
	//ordre croissant
	boolean[] ordres = {false,false};
	
//	Si au moins un bpc
	if (a.size() !=0 ) {
		ArrayList aTrier = Services.trier(a,colonnes,ordres);
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
				if (("KILOMETRIQUE").equals(getEquipementInfosCourant().getDesignationcompteur())){
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
public void trierServices(ArrayList a) throws Exception{
	String[] colonnes = {"ddebut","dfin"};
	//ordre croissant
	boolean[] ordres = {false,true};
	
//	Si au moins une affectation
	if (a.size() !=0 ) {
		ArrayList aTrier = Services.trier(a,colonnes,ordres);
		setListServices(aTrier);
		int tailles [] = {55,10,10};
		String[] padding = {"G","C","C"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (java.util.ListIterator list = aTrier.listIterator(); list.hasNext(); ) {
			AffectationServiceInfos aAffectationServiceInfos = (AffectationServiceInfos)list.next();
			String datefin = "";
			if (!aAffectationServiceInfos.getDfin().equals("01/01/0001")){
				datefin = aAffectationServiceInfos.getDfin();
			}
			String ligne [] = { aAffectationServiceInfos.getLiserv(),aAffectationServiceInfos.getDdebut(),datefin};
			aFormat.ajouteLigne(ligne);
		}
		setLB_SERVICES(aFormat.getListeFormatee());
	} else {
		setLB_SERVICES(null);
	}
	//return ;
}

//MODIF OFONTENEAU 20090408
public void trierDecl(ArrayList a) throws Exception{
	String declarant = "agent non trouvé";
	String[] colonnes = {"date"};
	//ordre croissant
	boolean[] ordres = {false};
	
//	Si au moins une affectation
	if (a.size() !=0 ) {
		ArrayList aTrier = Services.trier(a,colonnes,ordres);
		//MODIF OFONTENEAU
		//setListServices(aTrier);
		setListDeclarations(aTrier);
		int tailles [] = {10,49,10,10};
		String[] padding = {"C","G","C","C"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (java.util.ListIterator list = aTrier.listIterator(); list.hasNext(); ) {
			Declarations aDeclarations = (Declarations)list.next();
			// recherche de l'agent selon le service affecté
			if (aDeclarations.getCodeservice().equals("4000")){
				AgentCDE unAgent = AgentCDE.chercherAgentCDE(getTransaction(),aDeclarations.getMatricule());
				if(getTransaction().isErreur()){
					getTransaction().traiterErreur();
					declarant = "agent non trouvé";
				}else{
					declarant = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
				}
				//String ligne [] = {aDeclarations.getDate(),unAgent.getNom().trim()+" "+unAgent.getPrenom().trim(),aDeclarations.getCodeot(),aDeclarations.getCodeservice()};
				//aFormat.ajouteLigne(ligne);
			}else if(aDeclarations.getCodeservice().equals("5000")){
				AgentCCAS unAgent = AgentCCAS.chercherAgentCCAS(getTransaction(),aDeclarations.getMatricule());
				if(getTransaction().isErreur()){
					getTransaction().traiterErreur();
					declarant = "agent non trouvé";
				}else{
					declarant = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
				}
				//String ligne [] = {aDeclarations.getDate(),unAgent.getNom().trim()+" "+unAgent.getPrenom().trim(),aDeclarations.getCodeot(),aDeclarations.getCodeservice()};
				//aFormat.ajouteLigne(ligne);
			}else{
				Agents unAgent = Agents.chercherAgents(getTransaction(),aDeclarations.getMatricule());
				if(getTransaction().isErreur()){
					getTransaction().traiterErreur();
					declarant = "agent non trouvé";
				}else{
					declarant = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
				}
				//String ligne [] = {aDeclarations.getDate(),unAgent.getNom().trim()+" "+unAgent.getPrenom().trim(),aDeclarations.getCodeot(),aDeclarations.getCodeservice()};
				//aFormat.ajouteLigne(ligne);
			}
			String ligne [] = {aDeclarations.getDate(),declarant,aDeclarations.getCodeot(),aDeclarations.getCodeservice()};
			aFormat.ajouteLigne(ligne);
			
		}
		setLB_DECLARATIONS(aFormat.getListeFormatee());
	} else {
		setLB_DECLARATIONS(null);
	}
}
public boolean trierOT(ArrayList a) throws Exception{
	String[] colonnes = {"numeroot","dateentre"};
	//ordre decroissant
	boolean[] ordres = {false,false};
	String dentree = "";
	String dsortie = "";
	int totalOt = 0;
	
//	Si au moins un OT pour PePerso
	if (a.size() !=0 ) {
		ArrayList aTrier = Services.trier(a,colonnes,ordres);
		setListOTInfos(aTrier);
		int tailles [] = {10,10,10,10,10};
		String[] padding = {"D","C","C","D","D"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (int i = 0; i < aTrier.size() ; i++) {
			OT unOT = (OT)aTrier.get(i);	
		
			/*Par LB 9/9/11
			//	calcul du montant total de l'Ot
			OT unOt = OT.chercherOT(getTransaction(),unOT.getNumeroot());
			if(getTransaction().isErreur()){
				return false;
			}*/
			totalOt = ENGJU.CoutTotal_OT(getTransaction(),unOT);
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
			if (unOT.getDateentree().equals("01/01/0001")){
				dentree="";
			}else{
				dentree=unOT.getDateentree();
			}
			if(unOT.getDatesortie().equals("01/01/0001")){
				dsortie="";
			}else{
				dsortie=unOT.getDatesortie();
			}
			String ligne [] = { unOT.getNumeroot(),dentree,dsortie,unOT.getCompteur(),String.valueOf(totalOt)};
			aFormat.ajouteLigne(ligne);
		}
		setLB_OT(aFormat.getListeFormatee());
	} else {
		setLB_OT(null);
	}
	return true;
}

/**
 * Constructeur du process OeTableauDeBord.
 * Date de création : (17/08/05 14:02:45)
* 
 */
public OeTableauDeBord() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_DETAILSBPC
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getNOM_PB_DETAILSBPC() {
	return "NOM_PB_DETAILSBPC";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (17/08/05 14:02:45)
* 
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
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_DETAILSOT
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getNOM_PB_DETAILSOT() {
	return "NOM_PB_DETAILSOT";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (17/08/05 14:02:45)
* 
 */
public boolean performPB_DETAILSOT(javax.servlet.http.HttpServletRequest request) throws Exception {
	// on envoie l'OT sélectionné
	int indice  = (Services.estNumerique(getVAL_LB_OT_SELECT()) ? Integer.parseInt(getVAL_LB_OT_SELECT()): -1);
	if (indice == -1 || getListOTInfos().size() == 0 || indice > getListOTInfos().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","OT"));
		return false;
	}
	//OTInfos unOTInfos = (OTInfos)getListOTInfos().get(indice);
	OT unOT = (OT)getListOTInfos().get(indice);
	/*OT unOT = OT.chercherOT(getTransaction(),unOTInfos.getNumeroot());
	if(getTransaction().isErreur()){
		return false;
	}*/
	// on envoie l'OT à la fenetre de visualisation de l'OT
	VariableGlobale.ajouter(request,"OT",unOT);
	VariableActivite.ajouter(this,"DEBRANCHE","TRUE");
	setStatut(STATUT_OT,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHER
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getNOM_PB_RECHERCHER() {
	return "NOM_PB_RECHERCHER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (17/08/05 14:02:45)
* 
 */
public boolean performPB_RECHERCHER(javax.servlet.http.HttpServletRequest request) throws Exception {
	String agent = getZone(getNOM_EF_AGENT());
	String service = getZone(getNOM_EF_SERVICE()).toUpperCase();
	String equip = getZone(getNOM_EF_RECHERCHE_EQUIP());
	
	if(((agent!=null)&&(!agent.equals("")))||((service!=null)&&(!service.equals("")))||((equip!=null)&&(!equip.equals("")))){
		performPB_RECHERCHE_EQUIP(request);
	}else{
		VariableActivite.ajouter(this,"MODE","TDB");
		setStatut(STATUT_RECHERCHER,true);
	}
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_CARBU
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getNOM_ST_CARBU() {
	return "NOM_ST_CARBU";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_CARBU
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getVAL_ST_CARBU() {
	return getZone(getNOM_ST_CARBU());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMPTEUR
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getNOM_ST_COMPTEUR() {
	return "NOM_ST_COMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMPTEUR
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getVAL_ST_COMPTEUR() {
	return getZone(getNOM_ST_COMPTEUR());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DATEENSERVICE
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getNOM_ST_DATEENSERVICE() {
	return "NOM_ST_DATEENSERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DATEENSERVICE
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getVAL_ST_DATEENSERVICE() {
	return getZone(getNOM_ST_DATEENSERVICE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DATEHORSCIRCUIT
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getNOM_ST_DATEHORSCIRCUIT() {
	return "NOM_ST_DATEHORSCIRCUIT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DATEHORSCIRCUIT
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getVAL_ST_DATEHORSCIRCUIT() {
	return getZone(getNOM_ST_DATEHORSCIRCUIT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DATEVENTEREFORME
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getNOM_ST_DATEVENTEREFORME() {
	return "NOM_ST_DATEVENTEREFORME";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DATEVENTEREFORME
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getVAL_ST_DATEVENTEREFORME() {
	return getZone(getNOM_ST_DATEVENTEREFORME());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODELE
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getNOM_ST_MODELE() {
	return "NOM_ST_MODELE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODELE
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getVAL_ST_MODELE() {
	return getZone(getNOM_ST_MODELE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getNOM_ST_NOIMMAT() {
	return "NOM_ST_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getVAL_ST_NOIMMAT() {
	return getZone(getNOM_ST_NOIMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINV
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getNOM_ST_NOINV() {
	return "NOM_ST_NOINV";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINV
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getVAL_ST_NOINV() {
	return getZone(getNOM_ST_NOINV());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PE
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getNOM_ST_PE() {
	return "NOM_ST_PE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PE
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getVAL_ST_PE() {
	return getZone(getNOM_ST_PE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PNEUS
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getNOM_ST_PNEUS() {
	return "NOM_ST_PNEUS";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PNEUS
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getVAL_ST_PNEUS() {
	return getZone(getNOM_ST_PNEUS());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PRIX
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getNOM_ST_PRIX() {
	return "NOM_ST_PRIX";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PRIX
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getVAL_ST_PRIX() {
	return getZone(getNOM_ST_PRIX());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_PUISSANCE
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getNOM_ST_PUISSANCE() {
	return "NOM_ST_PUISSANCE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_PUISSANCE
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getVAL_ST_PUISSANCE() {
	return getZone(getNOM_ST_PUISSANCE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_RESERVOIR
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getNOM_ST_RESERVOIR() {
	return "NOM_ST_RESERVOIR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_RESERVOIR
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getVAL_ST_RESERVOIR() {
	return getZone(getNOM_ST_RESERVOIR());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_UTILISATEUR
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getNOM_ST_UTILISATEUR() {
	return "NOM_ST_UTILISATEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_UTILISATEUR
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getVAL_ST_UTILISATEUR() {
	return getZone(getNOM_ST_UTILISATEUR());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_BPC
 * Date de création : (17/08/05 14:02:45)
* 
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
* 
 */
private void setLB_BPC(java.lang.String[] newLB_BPC) {
	LB_BPC = newLB_BPC;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_BPC
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getNOM_LB_BPC() {
	return "NOM_LB_BPC";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_BPC_SELECT
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getNOM_LB_BPC_SELECT() {
	return "NOM_LB_BPC_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_BPC
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String [] getVAL_LB_BPC() {
	return getLB_BPC();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_BPC
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getVAL_LB_BPC_SELECT() {
	return getZone(getNOM_LB_BPC_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_OT
 * Date de création : (17/08/05 14:02:45)
* 
 */
private String [] getLB_OT() {
	if (LB_OT == null)
		LB_OT = initialiseLazyLB();
	return LB_OT;
}
/**
 * Setter de la liste:
 * LB_OT
 * Date de création : (17/08/05 14:02:45)
* 
 */
private void setLB_OT(java.lang.String[] newLB_OT) {
	LB_OT = newLB_OT;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_OT
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getNOM_LB_OT() {
	return "NOM_LB_OT";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_OT_SELECT
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getNOM_LB_OT_SELECT() {
	return "NOM_LB_OT_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_OT
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String [] getVAL_LB_OT() {
	return getLB_OT();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_OT
 * Date de création : (17/08/05 14:02:45)
* 
 */
public java.lang.String getVAL_LB_OT_SELECT() {
	return getZone(getNOM_LB_OT_SELECT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (17/08/05 14:12:40)
* 
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (17/08/05 14:12:40)
* 
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_VERSION
 * Date de création : (17/08/05 14:32:59)
* 
 */
public java.lang.String getNOM_ST_VERSION() {
	return "NOM_ST_VERSION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_VERSION
 * Date de création : (17/08/05 14:32:59)
* 
 */
public java.lang.String getVAL_ST_VERSION() {
	return getZone(getNOM_ST_VERSION());
}
	public BPC getBpcCourant() {
		return bpcCourant;
	}
	public void setBpcCourant(BPC bpcCourant) {
		this.bpcCourant = bpcCourant;
	}
	public EquipementInfos getEquipementInfosCourant() {
		return equipementInfosCourant;
	}
	public void setEquipementInfosCourant(EquipementInfos equipementInfosCourant) {
		this.equipementInfosCourant = equipementInfosCourant;
		equipementInfosCourantChange=true;
	}
	public ArrayList getListBPC() {
		if(listBPC==null){
			listBPC = new ArrayList();
		}
		return listBPC;
	}
	public void setListDeclarations(ArrayList listDeclarations) {
		this.listDeclarations = listDeclarations;
	}
	public ArrayList getListDeclarations() {
		if(listDeclarations==null){
			listDeclarations = new ArrayList();
		}
		return listDeclarations;
	}
	public void setListBPC(ArrayList listBPC) {
		this.listBPC = listBPC;
	}
	public ArrayList getListOTInfos() {
		if(listOTInfos==null){
			listOTInfos = new ArrayList();
		}
		return listOTInfos;
	}
	public void setListOTInfos(ArrayList listOTInfos) {
		this.listOTInfos = listOTInfos;
	}
	public OT getOtCourant() {
		return otCourant;
	}
	public void setOtCourant(OT otCourant) {
		this.otCourant = otCourant;
	}
	public Equipement getEquipementCourant() {
		return equipementCourant;
	}
	public void setEquipementCourant(Equipement equipementCourant) {
		this.equipementCourant = equipementCourant;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOMEQUIP
 * Date de création : (17/08/05 15:15:39)
* 
 */
public java.lang.String getNOM_ST_NOMEQUIP() {
	return "NOM_ST_NOMEQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOMEQUIP
 * Date de création : (17/08/05 15:15:39)
* 
 */
public java.lang.String getVAL_ST_NOMEQUIP() {
	return getZone(getNOM_ST_NOMEQUIP());
}
	public boolean isVideBPC() {
		return isVideBPC;
	}
	public void setVideBPC(boolean isVideBPC) {
		this.isVideBPC = isVideBPC;
	}
	public boolean isVideOT() {
		return isVideOT;
	}
	public void setVideOT(boolean isVideOT) {
		this.isVideOT = isVideOT;
	}
	public boolean isVideDec() {
		return isVideDec;
	}
	public void setVideDec(boolean isVideDec) {
		this.isVideDec = isVideDec;
	}
	public ArrayList getListEntretiens() {
		return listEntretiens;
	}
	public void setListEntretiens(ArrayList listEntretiens) {
		this.listEntretiens = listEntretiens;
	}
	private java.lang.String[] LB_ENTRETIENS;
/**
 * Getter de la liste avec un lazy initialize :
 * LB_ENTRETIENS
 * Date de création : (22/08/05 15:03:36)
* 
 */
private String [] getLB_ENTRETIENS() {
	if (LB_ENTRETIENS == null)
		LB_ENTRETIENS = initialiseLazyLB();
	return LB_ENTRETIENS;
}
/**
 * Setter de la liste:
 * LB_ENTRETIENS
 * Date de création : (22/08/05 15:03:36)
* 
 */
private void setLB_ENTRETIENS(java.lang.String[] newLB_ENTRETIENS) {
	LB_ENTRETIENS = newLB_ENTRETIENS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_ENTRETIENS
 * Date de création : (22/08/05 15:03:36)
* 
 */
public java.lang.String getNOM_LB_ENTRETIENS() {
	return "NOM_LB_ENTRETIENS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_ENTRETIENS_SELECT
 * Date de création : (22/08/05 15:03:36)
* 
 */
public java.lang.String getNOM_LB_ENTRETIENS_SELECT() {
	return "NOM_LB_ENTRETIENS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_ENTRETIENS
 * Date de création : (22/08/05 15:03:36)
* 
 */
public java.lang.String [] getVAL_LB_ENTRETIENS() {
	return getLB_ENTRETIENS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_ENTRETIENS
 * Date de création : (22/08/05 15:03:36)
* 
 */
public java.lang.String getVAL_LB_ENTRETIENS_SELECT() {
	return getZone(getNOM_LB_ENTRETIENS_SELECT());
}
	private java.lang.String[] LB_BPC_TOTAL;
/**
 * Getter de la liste avec un lazy initialize :
 * LB_BPC_TOTAL
 * Date de création : (24/08/05 14:17:33)
* 
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
* 
 */
private void setLB_BPC_TOTAL(java.lang.String[] newLB_BPC_TOTAL) {
	LB_BPC_TOTAL = newLB_BPC_TOTAL;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_BPC_TOTAL
 * Date de création : (24/08/05 14:17:33)
* 
 */
public java.lang.String getNOM_LB_BPC_TOTAL() {
	return "NOM_LB_BPC_TOTAL";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_BPC_TOTAL_SELECT
 * Date de création : (24/08/05 14:17:33)
* 
 */
public java.lang.String getNOM_LB_BPC_TOTAL_SELECT() {
	return "NOM_LB_BPC_TOTAL_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_BPC_TOTAL
 * Date de création : (24/08/05 14:17:33)
* 
 */
public java.lang.String [] getVAL_LB_BPC_TOTAL() {
	return getLB_BPC_TOTAL();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_BPC_TOTAL
 * Date de création : (24/08/05 14:17:33)
* 
 */
public java.lang.String getVAL_LB_BPC_TOTAL_SELECT() {
	return getZone(getNOM_LB_BPC_TOTAL_SELECT());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE_EQUIP
 * Date de création : (25/08/05 08:47:47)
* 
 */
public java.lang.String getNOM_PB_RECHERCHE_EQUIP() {
	return "NOM_PB_RECHERCHE_EQUIP";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (25/08/05 08:47:47)
* 
 */
public boolean performPB_RECHERCHE_EQUIP(javax.servlet.http.HttpServletRequest request) throws Exception {
	
	// on vide les zones pour préparer le prochain équipement
	if (getTransaction().isErreur()){
		getTransaction().traiterErreur();
	}
	
	addZone(getNOM_ST_CARBU(),"");
	addZone(getNOM_ST_COMPTEUR(),"");
	addZone(getNOM_ST_DATEHORSCIRCUIT(),"");
	addZone(getNOM_ST_DATEENSERVICE(),"");
	addZone(getNOM_ST_DATEVENTEREFORME(),"");
	addZone(getNOM_ST_MARQUE(),"");
	addZone(getNOM_ST_MODELE(),"");
	addZone(getNOM_ST_NOIMMAT(),"");
	addZone(getNOM_ST_NOINV(),"");
	
	addZone(getNOM_ST_PNEUS(),"");
	addZone(getNOM_ST_PRIX(),"");
	addZone(getNOM_ST_PUISSANCE(),"");
	addZone(getNOM_ST_RESERVOIR(),"");
	addZone(getNOM_ST_TYPE(),"");		
	addZone(getNOM_ST_NOMEQUIP(),"");
	addZone(getNOM_ST_VERSION(),"");
	addZone(getNOM_ST_PE(),"");
	addZone(getNOM_ST_DATEHORSCIRCUIT(), "");
	addZone(getNOM_ST_DATEENSERVICE(), "");
	addZone(getNOM_ST_DATEVENTEREFORME(),"");
	addZone(getNOM_ST_AGENT_RESPONSABLE(),"");
	addZone(getNOM_ST_SERVICE(),"");
	addZone(getNOM_ST_RESERVOIR(),"");
	// on vide les listes
	setLB_BPC(LBVide);
	setLB_BPC_TOTAL(LBVide);
	setLB_ENTRETIENS(LBVide);
	setLB_OT(LBVide);
	setLB_SERVICES(LBVide);
	setLB_DECLARATIONS(LBVide);
//	recherche de l'équipement voulu
	//getZone(getNOM_EF_RECHERCHE_EQUIP()).toUpperCase();
	if(!getZone(getNOM_EF_AGENT()).equals("")){
		recherche_AGENT(request);
	}else if(!getZone(getNOM_EF_SERVICE()).equals("")){
		recherche_SERVICE(request);
	}else if(!getZone(getNOM_EF_RECHERCHE_EQUIP()).equals("")){
			String recherche = getZone(getNOM_EF_RECHERCHE_EQUIP());
			EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfosInvOuImmat(getTransaction(),recherche);
			if(getTransaction().isErreur()){
				return false;
			}
			if(unEquipementInfos==null){
				if(unEquipementInfos.getNumeroinventaire()==null){
					getTransaction().declarerErreur("L'équipement recherché n'a pas été trouvé.");
					return false;
				}else{
					unEquipementInfos = new EquipementInfos();
				}
			}
			setEquipementInfosCourant(unEquipementInfos);
	}else{
		getTransaction().declarerErreur("Aucune zone de recherche n'a été renseignée. Veuillez renseigner l'un des champs.");
		return false;
	}
	if(getTransaction().isErreur()){
		setEquipementCourant(new Equipement());
		setEquipementInfosCourant(new EquipementInfos());
		setListBPC(new ArrayList());
		setListEntretiens(new ArrayList());
		setListOTInfos(new ArrayList());
		setLB_BPC(LBVide);
		setLB_BPC_TOTAL(LBVide);
		setLB_OT(LBVide);
		setLB_ENTRETIENS(LBVide);
		return false;
	}
	if(null==getEquipementInfosCourant()){
		setEquipementInfosCourant(new EquipementInfos());
	}
	
	//pour garder le même équipement
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_RECHERCHE_EQUIP
 * Date de création : (25/08/05 08:47:47)
* 
 */
public java.lang.String getNOM_EF_RECHERCHE_EQUIP() {
	return "NOM_EF_RECHERCHE_EQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_RECHERCHE_EQUIP
 * Date de création : (25/08/05 08:47:47)
* 
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
	private java.lang.String[] LB_SERVICES;
/**
 * Getter de la liste avec un lazy initialize :
 * LB_SERVICES
 * Date de création : (01/09/05 16:01:49)
* 
 */
private String [] getLB_SERVICES() {
	if (LB_SERVICES == null)
		LB_SERVICES = initialiseLazyLB();
	return LB_SERVICES;
}
/**
 * Setter de la liste:
 * LB_SERVICES
 * Date de création : (01/09/05 16:01:49)
* 
 */
private void setLB_SERVICES(java.lang.String[] newLB_SERVICES) {
	LB_SERVICES = newLB_SERVICES;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_SERVICES
 * Date de création : (01/09/05 16:01:49)
* 
 */
public java.lang.String getNOM_LB_SERVICES() {
	return "NOM_LB_SERVICES";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_SERVICES_SELECT
 * Date de création : (01/09/05 16:01:49)
* 
 */
public java.lang.String getNOM_LB_SERVICES_SELECT() {
	return "NOM_LB_SERVICES_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_SERVICES
 * Date de création : (01/09/05 16:01:49)
* 
 */
public java.lang.String [] getVAL_LB_SERVICES() {
	return getLB_SERVICES();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_SERVICES
 * Date de création : (01/09/05 16:01:49)
* 
 */
public java.lang.String getVAL_LB_SERVICES_SELECT() {
	return getZone(getNOM_LB_SERVICES_SELECT());
}
	public ArrayList getListServices() {
		return listServices;
	}
	public void setListServices(ArrayList listServices) {
		this.listServices = listServices;
	}
	private java.lang.String[] LB_DECLARATIONS;
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_DETAILSDEC
 * Date de création : (03/04/07 14:21:55)
* 
 */
public java.lang.String getNOM_PB_DETAILSDEC() {
	return "NOM_PB_DETAILSDEC";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (03/04/07 14:21:55)
* 
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
 * LB_DECLARATIONS
 * Date de création : (03/04/07 14:21:55)
* 
 */
private String [] getLB_DECLARATIONS() {
	if (LB_DECLARATIONS == null)
		LB_DECLARATIONS = initialiseLazyLB();
	return LB_DECLARATIONS;
}
/**
 * Setter de la liste:
 * LB_DECLARATIONS
 * Date de création : (03/04/07 14:21:55)
* 
 */
private void setLB_DECLARATIONS(java.lang.String[] newLB_DECLARATIONS) {
	LB_DECLARATIONS = newLB_DECLARATIONS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_DECLARATIONS
 * Date de création : (03/04/07 14:21:55)
* 
 */
public java.lang.String getNOM_LB_DECLARATIONS() {
	return "NOM_LB_DECLARATIONS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_DECLARATIONS_SELECT
 * Date de création : (03/04/07 14:21:55)
* 
 */
public java.lang.String getNOM_LB_DECLARATIONS_SELECT() {
	return "NOM_LB_DECLARATIONS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_DECLARATIONS
 * Date de création : (03/04/07 14:21:55)
* 
 */
public java.lang.String [] getVAL_LB_DECLARATIONS() {
	return getLB_DECLARATIONS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_DECLARATIONS
 * Date de création : (03/04/07 14:21:55)
* 
 */
public java.lang.String getVAL_LB_DECLARATIONS_SELECT() {
	return getZone(getNOM_LB_DECLARATIONS_SELECT());
}

/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (04/04/07 07:44:26)
* 
 */
public boolean recherche_SERVICE(javax.servlet.http.HttpServletRequest request) throws Exception {
	//Modif par Luc le 08/01/10
	//if (!Services.estNumerique(getZone(getNOM_EF_SERVICE()))){
	//	getTransaction().declarerErreur("Le code du service est numérique.");
	//	return false;
	//}
	
	// on recherche la liste des équipements dont l'agent est responsable car s'il en a plusieurs 
	//on débranche sur une autre fenêtre sinon on affiche celui dont il est responsable
	
	ArrayList listeASI = AffectationServiceInfos.chercherAffectationServiceInfosService(getTransaction(),getZone(getNOM_EF_SERVICE()).toUpperCase());
	if(getTransaction().isErreur()){
		getTransaction().declarerErreur("Le service n'a aucun équipement.");
		return false;
	}
	if (listeASI.size()>1){
		VariableActivite.ajouter(this,"NOMATR","");
		VariableActivite.ajouter(this,"CODESERVICE",getZone(getNOM_EF_SERVICE()).toUpperCase());
		setStatut(STATUT_RECHERCHER,true);
	}else{
		if(listeASI.size()==1){
			AffectationServiceInfos unASI = (AffectationServiceInfos)listeASI.get(0);
			EquipementInfos unEI = EquipementInfos.chercherEquipementInfos(getTransaction(),unASI.getNumeroinventaire());
			if(getTransaction().isErreur()){
				return false;
			}
			setEquipementInfosCourant(unEI);
		}else{
			getTransaction().declarerErreur("Aucun équipement affecté à ce service.");
			return false;
		}
	}
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_AGENT_RESPONSABLE
 * Date de création : (04/04/07 07:44:26)
* 
 */
public java.lang.String getNOM_ST_AGENT_RESPONSABLE() {
	return "NOM_ST_AGENT_RESPONSABLE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_AGENT_RESPONSABLE
 * Date de création : (04/04/07 07:44:26)
* 
 */
public java.lang.String getVAL_ST_AGENT_RESPONSABLE() {
	return getZone(getNOM_ST_AGENT_RESPONSABLE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (04/04/07 07:44:26)
* 
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (04/04/07 07:44:26)
* 
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_SERVICE
 * Date de création : (04/04/07 07:44:26)
* 
 */
public java.lang.String getNOM_EF_SERVICE() {
	return "NOM_EF_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_SERVICE
 * Date de création : (04/04/07 07:44:26)
* 
 */
public java.lang.String getVAL_EF_SERVICE() {
	return getZone(getNOM_EF_SERVICE());
}

/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (04/04/07 08:01:27)
* 
 */
public boolean recherche_AGENT(javax.servlet.http.HttpServletRequest request) throws Exception {
	ArrayList listAgent = new ArrayList();
	boolean trouve = false;
	String nom = "";
	/*if (!Services.estNumerique(getZone(getNOM_EF_AGENT()))){
		getTransaction().declarerErreur("Le code de l'agent est numérique.");
		return false;
	}*/
	
	// on recherche la liste des équipements dont l'agent est responsable car s'il en a plusieurs 
	//on débranche sur une autre fenêtre sinon on affiche celui dont il est responsable
	if (Services.estNumerique(getZone(getNOM_EF_AGENT()))){
		ArrayList listeASI = AffectationServiceInfos.listerAffectationServiceInfosAgent(getTransaction(),getZone(getNOM_EF_AGENT()));
		if((getTransaction().isErreur())||(listeASI.size()==0)){
			getTransaction().declarerErreur("L'agent n'est responsable d'aucun équipement.");
			return false;
		}
		if (listeASI.size()>1){
			VariableActivite.ajouter(this,"NOMATR",getZone(getNOM_EF_AGENT()));
			VariableActivite.ajouter(this,"ORIGINE","TDB");
			VariableActivite.ajouter(this,"CODESERVICE","");
			//setStatut(STATUT_RECHERCHER,true);
			setStatut(STATUT_SELECTION,true);
		}else{
			if(listeASI.size()==1){
				AffectationServiceInfos unASI = (AffectationServiceInfos)listeASI.get(0);
				EquipementInfos unEI = EquipementInfos.chercherEquipementInfos(getTransaction(),unASI.getNumeroinventaire());
				if(getTransaction().isErreur()){
					return false;
				}
				setEquipementInfosCourant(unEI);
			}
		}
	}else{
		if(Services.estAlphabetique(getZone(getNOM_EF_AGENT()))){
			nom = getZone(getNOM_EF_AGENT()).toUpperCase();
			if(!nom.equals("")){//||getZone(getNOM_EF_SERVICE()).equals("")){
				
				listAgent = AgentsMunicipaux.listerAgentsMunicipauxNom(getTransaction(),nom);
				if(getTransaction().isErreur()){
					return false;
				}
				trouve = true;
//				getTransaction().declarerErreur("Vous devez saisir le nom de l'agent ");
//				return false;
//			}
//				listInter = AgentCDE.chercherAgentCDENom(getTransaction(),nom);
//				if(getTransaction().isErreur()){
//					getTransaction().traiterErreur();
//					trouve = false;
//				}else{
//					trouve = true;
//				}
//				if (listInter.size()>0){
//					for (int i=0;i<listInter.size();i++){
//						listAgent.add(listInter.get(i));
//					}
//				}
//				listInter = AgentCCAS.chercherAgentCCASNom(getTransaction(),nom);
//				if(getTransaction().isErreur()){
//					getTransaction().traiterErreur();
//					trouve = false;
//				}else{
//					trouve = true;
//				}
//				if (listInter.size()>0){
//					for (int i=0;i<listInter.size();i++){
//						listAgent.add(listInter.get(i));
//					}
//				}
//				listInter = Agents.chercherAgentsNom(getTransaction(),nom);
//				if(getTransaction().isErreur()){
//					getTransaction().traiterErreur();
//					trouve = false;
//				}else{
//					trouve = true;
//				}
//				if (listInter.size()>0){
//					for (int i=0;i<listInter.size();i++){
//						listAgent.add(listInter.get(i));
//					}
				}
			if (trouve){
				if (listAgent.size()==1){
					AgentsMunicipaux unAgent = (AgentsMunicipaux)listAgent.get(0);
					// s'il n'a qu'un équipement on débranche directement sur la fenêtre visu sinon on les affiche
					ArrayList a = AffectationServiceInfos.listerAffectationServiceInfosAgent(getTransaction(),unAgent.getNomatr());
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
					}else if(a.size()>1){
						VariableActivite.ajouter(this,"ORIGINE","TDB");
						VariableActivite.ajouter(this,"NOMATR",unAgent.getNomatr());
						setStatut(STATUT_SELECTION,true);
					}else{
						getTransaction().declarerErreur("Cet agent n'est responsable d'aucun équipement.");
						return false;
					}
				}else if(listAgent.size()>1){
					// si plusieurs on affiche la liste du résultat des agents
					VariableActivite.ajouter(this,"NOM",nom);
					VariableActivite.ajouter(this,"MODE","TDB");
					VariableActivite.ajouter(this,"TYPE","Equipement");
					setStatut(STATUT_AGENT,true);
				}else{
					getTransaction().declarerErreur("Aucun agent correspondant.");
					return false;
				}
			}
		}
	}
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_AGENT
 * Date de création : (04/04/07 08:01:27)
* 
 */
public java.lang.String getNOM_EF_AGENT() {
	return "NOM_EF_AGENT";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_AGENT
 * Date de création : (04/04/07 08:01:27)
* 
 */
public java.lang.String getVAL_EF_AGENT() {
	return getZone(getNOM_EF_AGENT());
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
	return getNOM_EF_RECHERCHE_EQUIP();
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_AGENT
 * Date de création : (31/07/07 13:05:58)
* 
 */
public java.lang.String getNOM_ST_AGENT() {
	return "NOM_ST_AGENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_AGENT
 * Date de création : (31/07/07 13:05:58)
* 
 */
public java.lang.String getVAL_ST_AGENT() {
	return getZone(getNOM_ST_AGENT());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RESPONSABLE
 * Date de création : (31/07/07 13:13:57)
* 
 */
public java.lang.String getNOM_PB_RESPONSABLE() {
	return "NOM_PB_RESPONSABLE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (31/07/07 13:13:57)
* 
 */
public boolean performPB_RESPONSABLE(javax.servlet.http.HttpServletRequest request) throws Exception {
	int indice  = (Services.estNumerique(getVAL_LB_SERVICES_SELECT()) ? Integer.parseInt(getVAL_LB_SERVICES_SELECT()): -1);
	if (indice == -1 || getListServices().size() == 0 || indice > getListServices().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","AFFECTATIONS"));
		return false;
	}
	AffectationServiceInfos unService = (AffectationServiceInfos)getListServices().get(indice);
	//Declarations unService = (Declarations)getListServices().get(indice);
	if("0".equals(unService.getNomatr())){
		addZone(getNOM_ST_AGENT(),"Aucun");
	}else{
		AgentsMunicipaux unAgent = AgentsMunicipaux.chercherAgentsMunicipauxService(getTransaction(),unService.getNomatr(),unService.getCodeservice());
		if(getTransaction().isErreur()){
			return false;
		}
		addZone(getNOM_ST_AGENT(),unAgent.getNom().trim()+" "+unAgent.getPrenom().trim());
	}
	
	return true;
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (17/08/05 14:02:45)
* 
 */
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_RETOUR
		if (testerParametre(request, getNOM_PB_RETOUR())) {
			return performPB_RETOUR(request);
		}

		//Si clic sur le bouton PB_RESPONSABLE
		if (testerParametre(request, getNOM_PB_RESPONSABLE())) {
			return performPB_RESPONSABLE(request);
		}

		//Si clic sur le bouton PB_DETAILSDEC
		if (testerParametre(request, getNOM_PB_DETAILSDEC())) {
			return performPB_DETAILSDEC(request);
		}

		//Si clic sur le bouton PB_RECHERCHE_EQUIP
		if (testerParametre(request, getNOM_PB_RECHERCHE_EQUIP())) {
			return performPB_RECHERCHE_EQUIP(request);
		}

		//Si clic sur le bouton PB_DETAILSBPC
		if (testerParametre(request, getNOM_PB_DETAILSBPC())) {
			return performPB_DETAILSBPC(request);
		}

		//Si clic sur le bouton PB_DETAILSOT
		if (testerParametre(request, getNOM_PB_DETAILSOT())) {
			return performPB_DETAILSOT(request);
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
 * Date de création : (31/07/07 14:01:43)
* 
 */
public String getJSP() {
	return "OeTableauDeBord.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RETOUR
 * Date de création : (31/07/07 14:01:43)
* 
 */
public java.lang.String getNOM_PB_RETOUR() {
	return "NOM_PB_RETOUR";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (31/07/07 14:01:43)
* 
 */
public boolean performPB_RETOUR(javax.servlet.http.HttpServletRequest request) throws Exception {
	AffectationServiceInfos unASI = AffectationServiceInfos.chercherAffectationServiceInfosCourantEquip(getTransaction(),getEquipementInfosCourant().getNumeroinventaire(),Services.dateDuJour());
	if(getTransaction().isErreur()){
		// l'équipement peut ne pas être affecté
		getTransaction().traiterErreur();
	}
	VariableActivite.ajouter(this,"NOMATR",unASI.getNomatr());
	VariableActivite.ajouter(this,"ORIGINE","TDB");
	setStatut(STATUT_SELECTION,true);
	return true;
}
public Hashtable<String, AActifs> getHashAActifs() {
	if (hashAActifs ==  null) hashAActifs = new Hashtable<String, AActifs>();
	return hashAActifs;
}
public void setHashAActifs(Hashtable<String, AActifs> hashAActifs) {
	this.hashAActifs = hashAActifs;
}
public Hashtable<String, AgentCCAS> getHashAgentCCAS() {
	if (hashAgentCCAS ==  null) hashAgentCCAS = new Hashtable<String, AgentCCAS>();
	return hashAgentCCAS;
}
public void setHashAgentCCAS(Hashtable<String, AgentCCAS> hashAgentCCAS) {
	this.hashAgentCCAS = hashAgentCCAS;
}
public Hashtable<String, AgentCDE> getHashAgentCDE() {
	if (hashAgentCDE ==  null) hashAgentCDE = new Hashtable<String, AgentCDE>();
	return hashAgentCDE;
}
public void setHashAgentCDE(Hashtable<String, AgentCDE> hashAgentCDE) {
	this.hashAgentCDE = hashAgentCDE;
}
}
