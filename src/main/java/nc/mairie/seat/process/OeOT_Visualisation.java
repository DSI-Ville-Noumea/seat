package nc.mairie.seat.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.vfs.FileObject;

import nc.mairie.seat.metier.AffectationServiceInfos;
import nc.mairie.seat.metier.Agents;
import nc.mairie.seat.metier.ENGJU;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.OT;
import nc.mairie.seat.metier.OT_ATM;
import nc.mairie.seat.metier.PePersoInfos;
import nc.mairie.seat.metier.PiecesInfos;
import nc.mairie.servlets.Frontale;
import nc.mairie.technique.*;
/**
 * Process OeOT_Visualisation
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
*/
public class OeOT_Visualisation extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8101003389776393252L;
	public static final int STATUT_RECHERCHER = 1;
	private java.lang.String[] LB_INTERVENANTS;
	private java.lang.String[] LB_INTERVENTIONS;
	private java.lang.String[] LB_PIECES;
	private EquipementInfos equipementInfosCourant;
	private OT otCourant;
	private ArrayList<PiecesInfos> listePieces;
	private ArrayList<PePersoInfos> listeInterventions;
	private ArrayList<OT_ATM> listeIntervenants;
	private ArrayList<ENGJU> listeENGJUGroupByCdepNoengjIdetbs;
	public int	montantTotalPieces;
	public boolean isDebranche = false;
	public String messErreur;
	private String starjetMode = (String)Frontale.getMesParametres().get("STARJET_MODE");
	private String script;
	public boolean isTrouve = false;
	public boolean isErreur = false;
	private String focus = null;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	String debranche = (String)VariableActivite.recuperer(this,"DEBRANCHE");
	if(debranche==null){
		setDebranche(false);
	}else{
		if(debranche.equals("TRUE")){
			setDebranche(true);
		}else{
			setDebranche(false);
		}
	}
	// quand appuie sur entrée
	if(!("").equals(getZone(getNOM_EF_OT()))){
		performPB_OT(request);
		addZone(getNOM_EF_OT(),"");
	}
	OT unOT = (OT)VariableGlobale.recuperer(request,"OT");
	EquipementInfos unEquipementInfos = (EquipementInfos)VariableGlobale.recuperer(request,"EQUIPEMENTINFOS");
	setEquipementInfosCourant(unEquipementInfos);
	String dentree = "";
	String dsortie = "";
	if(unOT!=null){
		if(unOT.getNumeroot()!=null){
			setOtCourant(unOT);
			isTrouve = true;
	//		 pour trouver le service qui a le véhicule à la date de l'OT
			if((getEquipementInfosCourant()!=null)&&(getEquipementInfosCourant().getNumeroinventaire()!=null)&&(getOtCourant().getDateentree()!=null)&&(!getOtCourant().getDateentree().equals(""))){
				AffectationServiceInfos unAffectationServiceInfos = AffectationServiceInfos.chercherAffectationServiceInfosCourantEquip(getTransaction(),getEquipementInfosCourant().getNumeroinventaire(),getOtCourant().getDateentree());
				if(getTransaction().isErreur()){
					//si pas trouvé, c'est fonctionellement normal, on traite l'erreur
					getTransaction().traiterErreur();
					addZone(getNOM_ST_SERVICE(),"pas affecté");
				}else{
					addZone(getNOM_ST_SERVICE(),unAffectationServiceInfos.getCodeservice().trim()+" "+unAffectationServiceInfos.getLiserv().trim());
				}
			}else{
				addZone(getNOM_ST_SERVICE(),"");
			}
			// initialisation des zones
			initialiseListIntervenants(request);
			initialiseListInterventions(request);
			initialiseListPieces(request);
			initialiseListFre(request);
			addZone(getNOM_ST_NOOT(),getOtCourant().getNumeroot());
			if((getOtCourant().getDateentree()==null)||(getOtCourant().getDateentree().equals("01/01/0001"))){
				dentree = "";
			}else{
				dentree = getOtCourant().getDateentree();
			}
			if((getOtCourant().getDatesortie()==null)||(getOtCourant().getDatesortie().equals("01/01/0001"))){
				dsortie = "";
			}else{
				dsortie = getOtCourant().getDatesortie();
			}
			addZone(getNOM_ST_DSORTIE(),dsortie);
			addZone(getNOM_ST_DENTREE(),dentree);
			addZone(getNOM_ST_COMPTEUR(),getOtCourant().getCompteur());
			addZone(getNOM_ST_COMMENTAIRE(),getOtCourant().getCommentaire());
	
			
			//recherche du coût total de l'OT
			int total = ENGJU.CoutTotal_OT(getTransaction(),getOtCourant());
			if(total<0 || getTransaction().isErreur()){
				messErreur = "Une erreur s'est produite lors du calcul du montant du bon d'engagement.";
				getTransaction().traiterErreur() ;
				total = 0;
			}
			addZone(getNOM_ST_COUT_TOTAL(),String.valueOf(total));
		}
	}
	
	if(unEquipementInfos!=null){
		setEquipementInfosCourant(unEquipementInfos);
		if(unEquipementInfos.getNumeroinventaire()!=null){
			//initialisation des zones
			addZone(getNOM_ST_TYPE(),getEquipementInfosCourant().getDesignationtypeequip());
			addZone(getNOM_ST_NOINVENT(),getEquipementInfosCourant().getNumeroinventaire());
			addZone(getNOM_ST_NOIMMAT(),getEquipementInfosCourant().getNumeroimmatriculation());
			addZone(getNOM_ST_MODELE(),getEquipementInfosCourant().getDesignationmodele());
			addZone(getNOM_ST_MARQUE(),getEquipementInfosCourant().getDesignationmarque());
			addZone(getNOM_ST_TCOMPTEUR(),getEquipementInfosCourant().getDesignationcompteur());

		}
	}
	
	// lors du problème du calcul du montant du bon d'engagement
	if (!("").equals(messErreur)&&(messErreur!=null)){
		getTransaction().declarerErreur(messErreur);
		messErreur = "";
		return;
	}
}

public void initialiseListInterventions(javax.servlet.http.HttpServletRequest request) throws Exception{
	// on initialise la liste des interventions réalisées pendant l'OT
	if(getOtCourant().getNumeroot()!=null){
		ArrayList<PePersoInfos> listInterventions = PePersoInfos.chercherPePersoInfosOT(getTransaction(),getOtCourant().getNumeroot());
		if(getTransaction().isErreur()){
			return;
		}
		setListeInterventions(listInterventions);
	}
	
	if(getListeInterventions().size()>0){
		int [] tailles = {30,10,5};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G","C","D"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (int i = 0; i < getListeInterventions().size() ; i++) {
			PePersoInfos monPePersoInfos = (PePersoInfos)getListeInterventions().get(i);
			String datereal = "";
			if(monPePersoInfos.getDatereal().equals("01/01/0001")){
				datereal = "";
			}else{
				datereal = monPePersoInfos.getDatereal();
			}
			String ligne [] = { monPePersoInfos.getLibelleentretien(),datereal,monPePersoInfos.getDuree()};
			aFormat.ajouteLigne(ligne);
		}
		setLB_INTERVENTIONS(aFormat.getListeFormatee());
	}else{
		setLB_INTERVENTIONS(LBVide);
	}	
}

public void initialiseListFre(javax.servlet.http.HttpServletRequest request) throws Exception{
	/*LB correction et optimisation 8/11/11
	//on initialise la liste des fournisseurs intervenus sur l'OT
	if(getOtCourant().getNumeroot()!=null){
		ArrayList listBe = BE.listerBEOT(getTransaction(),getOtCourant().getNumeroot());
		setListeBe(listBe);
	}
	*/
	
	//Alim de la liste des EngJU
	ArrayList<ENGJU> arrENGJU = ENGJU.listerENGJUGroupByCdepNoengjIdetbs(getTransaction(), getOtCourant().getNumeroot(), getOtCourant().getNuminv());
	setListeENGJUGroupByCdepNoengjIdetbs(arrENGJU);
	
	if(getListeENGJUGroupByCdepNoengjIdetbs().size()>0){
		int tailles [] = {10,10,30,10};
		String[] padding = {"G","G","G","C"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for(int i=0;i<getListeENGJUGroupByCdepNoengjIdetbs().size();i++){
			ENGJU unEnju = (ENGJU )getListeENGJUGroupByCdepNoengjIdetbs().get(i);
			/*LB correction et optimisation 8/11/11
			BE unBe = (BE)getListeBe().get(i);
			ENGJU unEnju = ENGJU.chercherpremierENGJU(getTransaction(),unBe.getExerci(),unBe.getNoengj());
			if(getTransaction().isErreur()){
				messErreur = "Une erreur s'est produite lors du calcul du montant du bon d'engagement.";
				//getTransaction().declarerErreur("Une erreur s'est produite lors du calcul du montant du bon d'engagement.");
				getTransaction().traiterErreur() ;
				return ;
			}
			//on recherche les lignes concernant l'engagement juridique et on calcul le montant
			int montantTotal = ENGJU.montantBE(getTransaction(),unEnju);
			if(montantTotal==-1){
				messErreur = "Une erreur s'est produite lors du calcul du montant du bon d'engagement.";
				//getTransaction().declarerErreur("Une erreur s'est produite lors du calcul du montant du bon d'engagement.");
				getTransaction().traiterErreur();
				return ;
			}
			//on renseigne la lb
			String ligne [] = { unEnju.getNoengj(),unEnju.getIdetbs(),unEnju.getEnscom(),String.valueOf(montantTotal)};
			*/
			String ligne [] = { unEnju.getNoengj(),unEnju.getIdetbs(),unEnju.getEnscom(),unEnju.getMtlenju()};
			aFormat.ajouteLigne(ligne);
		}
		setLB_FRE(aFormat.getListeFormatee());
	} else {
		setLB_FRE(null);
	}
}
/*
public int montantBE(javax.servlet.http.HttpServletRequest request,ENJU unEnju) throws Exception{
	String codeDep;
	// controle si unEnju null
	if (unEnju.getCodcoll()==null){
		getTransaction().declarerErreur("Le Bon d'engagement passé en paramètre est null.");
		return -1;
	}
	int total = 0;
	// recherche des lignes du bon d'engagement
	ArrayList listLeju = LEJU.listerLEJUBE(getTransaction(),unEnju.getExerci(),unEnju.getNoengju());
	if(getTransaction().isErreur()){
		messErreur = "Une erreur s'est produite lors du calcul du montant du bon d'engagement.";
		//getTransaction().declarerErreur("Une erreur est survenue lors du calcul du montant du bon d'engagement.");
		getTransaction().traiterErreur();
		return -1;
	}
	if(listLeju.size()>0){
		for(int i=0;i<listLeju.size();i++){
			LEJU unLeju = (LEJU)listLeju.get(i);
			codeDep = unLeju.getCddep().substring(1,5);
			if(codeDep.trim().equals(getEquipementInfosCourant().getNumeroinventaire().trim())||unLeju.getCddep().trim().equals(getEquipementInfosCourant().getNumeroinventaire().trim())){
				int montant = Integer.parseInt(unLeju.getMtlengju());
				total = total + montant;
			}
		}
	}
	return total;
}
*/
public void initialiseListIntervenants(javax.servlet.http.HttpServletRequest request) throws Exception{
	//on initialise la liste des intervenants de l'ATM
	if(getOtCourant().getNumeroot()!=null){
		ArrayList<OT_ATM> listAgents = OT_ATM.listerOT_ATMOT(getTransaction(),getOtCourant().getNumeroot());
		if(getTransaction().isErreur()){
			return;
		}
		setListeIntervenants(listAgents);
	}
	
	if(getListeIntervenants().size()>0){
		int [] tailles = {30};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for(int i =0;i<getListeIntervenants().size();i++){
			OT_ATM unOTATM = (OT_ATM)getListeIntervenants().get(i);
			Agents unAgent = Agents.chercherAgents(getTransaction(),unOTATM.getMatricule());
			if(getTransaction().isErreur()){
				return ;
			}
			String ligne [] = { unAgent.getNom().trim()+" "+unAgent.getPrenom().trim()};
			aFormat.ajouteLigne(ligne);
		}
		setLB_INTERVENANTS(aFormat.getListeFormatee());
	}else{
		setLB_INTERVENANTS(LBVide);
	}	
}

public void initialiseListPieces(javax.servlet.http.HttpServletRequest request) throws Exception{
//	 on initialise la liste des pièces sorties du stock pour l'OT
	if(getOtCourant().getNumeroot()!=null){
		ArrayList<PiecesInfos> listPiecesInfos = PiecesInfos.chercherPiecesInfosOT(getTransaction(),getOtCourant().getNumeroot());
		if(getTransaction().isErreur()){
			return;
		}
		setListePieces(listPiecesInfos);
	}
	montantTotalPieces = 0;
	if(getListePieces().size()>0){
		int [] tailles = {30,10,6,6,6};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G","C","D","D","D"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (int i = 0; i < getListePieces().size() ; i++) {
			PiecesInfos maPiecesInfos = (PiecesInfos)getListePieces().get(i);	
			// calcul du total par type de pièces
			int total = 0;
			if(maPiecesInfos.getPrix()!=null){
				total = Integer.parseInt(maPiecesInfos.getQuantite())*Integer.parseInt(maPiecesInfos.getPrix());
			}
			//int total = Integer.parseInt(maPiecesInfos.getQuantite())*Integer.parseInt(maPiecesInfos.getPrix());
			String ligne [] = { maPiecesInfos.getDesignationpiece(),maPiecesInfos.getDatesortie(),maPiecesInfos.getPrix(),maPiecesInfos.getQuantite(),String.valueOf(total)};
			aFormat.ajouteLigne(ligne);
		montantTotalPieces = montantTotalPieces + total;
		}
		setLB_PIECES(aFormat.getListeFormatee());
	}else{
		setLB_PIECES(LBVide);
	}	
}

/**
 * Constructeur du process OeOT_Visualisation.
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public OeOT_Visualisation() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHER
 * Date de création : (28/07/05 14:46:02)
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
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public boolean performPB_RECHERCHER(javax.servlet.http.HttpServletRequest request) throws Exception {
	
	setStatut(STATUT_RECHERCHER,true);
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DENTREE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_DENTREE() {
	return "NOM_ST_DENTREE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DENTREE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_DENTREE() {
	return getZone(getNOM_ST_DENTREE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DSORTIE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_DSORTIE() {
	return "NOM_ST_DSORTIE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DSORTIE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_DSORTIE() {
	return getZone(getNOM_ST_DSORTIE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODELE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_MODELE() {
	return "NOM_ST_MODELE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODELE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_MODELE() {
	return getZone(getNOM_ST_MODELE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOIMMAT() {
	return "NOM_ST_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOIMMAT() {
	return getZone(getNOM_ST_NOIMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINVENT
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOINVENT() {
	return "NOM_ST_NOINVENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINVENT
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOINVENT() {
	return getZone(getNOM_ST_NOINVENT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOOT
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_NOOT() {
	return "NOM_ST_NOOT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOOT
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_NOOT() {
	return getZone(getNOM_ST_NOOT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_INTERVENANTS
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
private String [] getLB_INTERVENANTS() {
	if (LB_INTERVENANTS == null)
		LB_INTERVENANTS = initialiseLazyLB();
	return LB_INTERVENANTS;
}
/**
 * Setter de la liste:
 * LB_INTERVENANTS
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
private void setLB_INTERVENANTS(java.lang.String[] newLB_INTERVENANTS) {
	LB_INTERVENANTS = newLB_INTERVENANTS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_INTERVENANTS
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_INTERVENANTS() {
	return "NOM_LB_INTERVENANTS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_INTERVENANTS_SELECT
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_INTERVENANTS_SELECT() {
	return "NOM_LB_INTERVENANTS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_INTERVENANTS
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_INTERVENANTS() {
	return getLB_INTERVENANTS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_INTERVENANTS
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_INTERVENANTS_SELECT() {
	return getZone(getNOM_LB_INTERVENANTS_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_INTERVENTIONS
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
private String [] getLB_INTERVENTIONS() {
	if (LB_INTERVENTIONS == null)
		LB_INTERVENTIONS = initialiseLazyLB();
	return LB_INTERVENTIONS;
}
/**
 * Setter de la liste:
 * LB_INTERVENTIONS
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
private void setLB_INTERVENTIONS(java.lang.String[] newLB_INTERVENTIONS) {
	LB_INTERVENTIONS = newLB_INTERVENTIONS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_INTERVENTIONS
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_INTERVENTIONS() {
	return "NOM_LB_INTERVENTIONS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_INTERVENTIONS_SELECT
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_INTERVENTIONS_SELECT() {
	return "NOM_LB_INTERVENTIONS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_INTERVENTIONS
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_INTERVENTIONS() {
	return getLB_INTERVENTIONS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_INTERVENTIONS
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_INTERVENTIONS_SELECT() {
	return getZone(getNOM_LB_INTERVENTIONS_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_PIECES
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
private String [] getLB_PIECES() {
	if (LB_PIECES == null)
		LB_PIECES = initialiseLazyLB();
	return LB_PIECES;
}
/**
 * Setter de la liste:
 * LB_PIECES
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
private void setLB_PIECES(java.lang.String[] newLB_PIECES) {
	LB_PIECES = newLB_PIECES;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_PIECES
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_PIECES() {
	return "NOM_LB_PIECES";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_PIECES_SELECT
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_PIECES_SELECT() {
	return "NOM_LB_PIECES_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_PIECES
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_PIECES() {
	return getLB_PIECES();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_PIECES
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_PIECES_SELECT() {
	return getZone(getNOM_LB_PIECES_SELECT());
}
	public EquipementInfos getEquipementInfosCourant() {
		return equipementInfosCourant;
	}
	public void setEquipementInfosCourant(EquipementInfos equipementInfosCourant) {
		this.equipementInfosCourant = equipementInfosCourant;
	}
	public OT getOtCourant() {
		return otCourant;
	}
	public void setOtCourant(OT otCourant) {
		this.otCourant = otCourant;
	}
//	public OTInfos getOtInfosCourant() {
//		return otInfosCourant;
//	}
//	public void setOtInfosCourant(OTInfos otInfosCourant) {
//		this.otInfosCourant = otInfosCourant;
//	}
	public ArrayList<OT_ATM> getListeIntervenants() {
		if(listeIntervenants==null){
			listeIntervenants=new ArrayList<OT_ATM>();
		}
		return listeIntervenants;
	}
	public void setListeIntervenants(ArrayList<OT_ATM> listeIntervenants) {
		this.listeIntervenants = listeIntervenants;
	}
	public ArrayList<PePersoInfos> getListeInterventions() {
		if(listeInterventions==null){
			listeInterventions=new ArrayList<PePersoInfos>();
		}
		return listeInterventions;
	}
	public void setListeInterventions(ArrayList<PePersoInfos> listeInterventions) {
		this.listeInterventions = listeInterventions;
	}
	public ArrayList<PiecesInfos> getListePieces() {
		if(listePieces==null){
			listePieces=new ArrayList<PiecesInfos>();
		}
		return listePieces;
	}
	public void setListePieces(ArrayList<PiecesInfos> listePieces) {
		this.listePieces = listePieces;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMMENTAIRE
 * Date de création : (05/08/05 08:01:38)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_COMMENTAIRE() {
	return "NOM_ST_COMMENTAIRE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMMENTAIRE
 * Date de création : (05/08/05 08:01:38)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_COMMENTAIRE() {
	return getZone(getNOM_ST_COMMENTAIRE());
}
	private java.lang.String[] LB_FRE;
/**
 * Getter de la liste avec un lazy initialize :
 * LB_FRE
 * Date de création : (09/08/05 13:33:45)
 * @author : Générateur de process
 */
private String [] getLB_FRE() {
	if (LB_FRE == null)
		LB_FRE = initialiseLazyLB();
	return LB_FRE;
}
/**
 * Setter de la liste:
 * LB_FRE
 * Date de création : (09/08/05 13:33:45)
 * @author : Générateur de process
 */
private void setLB_FRE(java.lang.String[] newLB_FRE) {
	LB_FRE = newLB_FRE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_FRE
 * Date de création : (09/08/05 13:33:45)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_FRE() {
	return "NOM_LB_FRE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_FRE_SELECT
 * Date de création : (09/08/05 13:33:45)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_FRE_SELECT() {
	return "NOM_LB_FRE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_FRE
 * Date de création : (09/08/05 13:33:45)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_FRE() {
	return getLB_FRE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_FRE
 * Date de création : (09/08/05 13:33:45)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_FRE_SELECT() {
	return getZone(getNOM_LB_FRE_SELECT());
}

/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (18/08/05 10:02:05)
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
 * Date de création : (18/08/05 10:02:05)
 * @author : Générateur de process
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMPTEUR
 * Date de création : (23/08/05 08:58:15)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_COMPTEUR() {
	return "NOM_ST_COMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMPTEUR
 * Date de création : (23/08/05 08:58:16)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_COMPTEUR() {
	return getZone(getNOM_ST_COMPTEUR());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TCOMPTEUR
 * Date de création : (23/08/05 09:12:51)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TCOMPTEUR() {
	return "NOM_ST_TCOMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TCOMPTEUR
 * Date de création : (23/08/05 09:12:51)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TCOMPTEUR() {
	return getZone(getNOM_ST_TCOMPTEUR());
}
	public boolean isDebranche() {
		return isDebranche;
	}
	public void setDebranche(boolean isDebranche) {
		this.isDebranche = isDebranche;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COUT_TOTAL
 * Date de création : (31/01/06 13:31:13)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_COUT_TOTAL() {
	return "NOM_ST_COUT_TOTAL";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COUT_TOTAL
 * Date de création : (31/01/06 13:31:13)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_COUT_TOTAL() {
	return getZone(getNOM_ST_COUT_TOTAL());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OT
 * Date de création : (03/04/07 09:55:38)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_OT() {
	return "NOM_PB_OT";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (03/04/07 09:55:38)
 * @author : Générateur de process
 */
public boolean performPB_OT(javax.servlet.http.HttpServletRequest request) throws Exception {
	if(getZone(getNOM_EF_OT()).equals("")){
		getTransaction().declarerErreur("La zone de recherche doit être renseignée.");
		return false;
	}
	String recherche;
	if (Services.estNumerique(getZone(getNOM_EF_OT()))){
		recherche = getZone(getNOM_EF_OT());
	}else{
		getTransaction().declarerErreur("Le numéro d'OT est numérique.");
		return false;
	}
	
	OT unOT = OT.chercherOT(getTransaction(),recherche);
	if(getTransaction().isErreur()){
		isTrouve = false;
		getTransaction().declarerErreur("L'OT recherché n'a pas été trouvé.");
		return false;
	}
	isTrouve = true;
	setOtCourant(unOT);
	setEquipementInfosCourant(new EquipementInfos());
	if (unOT!=null){
		EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfos(getTransaction(),unOT.getNuminv());
		if(getTransaction().isErreur()){
			return false;
		}
		setEquipementInfosCourant(unEquipementInfos);
	}
	// on renseigne la liste des BPC
	VariableGlobale.ajouter(request,"OT",getOtCourant());
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_OT
 * Date de création : (03/04/07 09:55:38)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_OT() {
	return "NOM_EF_OT";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_OT
 * Date de création : (03/04/07 09:55:38)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_OT() {
	return getZone(getNOM_EF_OT());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_IMPRIMER
 * Date de création : (11/06/07 13:12:25)
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
 * Date de création : (11/06/07 13:12:25)
 * @author : Générateur de process
 */
public boolean performPB_IMPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
	int montantPieces = 0;
	String infosBe = "";
	int inter1;
	int inter2;
	String chainedeb="";
	
	// impression de l'OT
	if(getOtCourant()!=null){
		String commentaireOt = "";
		String numinv = getZone(getNOM_ST_NOINVENT());
		String numimmat = getZone(getNOM_ST_NOIMMAT());
		String nomequip = getZone(getNOM_ST_MARQUE())+" "+getZone(getNOM_ST_MODELE());
		String type = getZone(getNOM_ST_TYPE());
		String service = getZone(getNOM_ST_SERVICE());
		String numOt = getZone(getNOM_ST_NOOT());
		String montantTotal = getZone(getNOM_ST_COUT_TOTAL());
		String dEntree = getZone(getNOM_ST_DENTREE());
		String dSortie = getZone(getNOM_ST_DSORTIE());
		String compteur = getZone(getNOM_ST_COMPTEUR());
		// pour les commentaires avec retour à la ligne par la touche "Entrée"
		String commentaire = getZone(getNOM_ST_COMMENTAIRE());
		int comlen = commentaire.length();
		int indice ;
		//commentaire = commentaire.replace('\n',' ');
		indice = 0;
		
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<comlen;i++){
			if(commentaire.charAt(i)=='\n'){
				sb.append(commentaire.substring(indice,i)+"$");
			}
		}
		commentaireOt = sb.toString();
		
//		for (int i=0;i<comlen;i++){
//			if(commentaire.charAt(i)=='\n'){
//				commentaireOt = commentaireOt+commentaire.substring(indice,i)+"$";
//				indice = i;
//			}
//		}
		commentaireOt = commentaireOt + commentaire.substring(indice,comlen);
		commentaireOt = commentaireOt.replace('\n',' ');
		commentaireOt = commentaireOt.replace('\r',' ');
		StarjetGenerationVFS g = new StarjetGenerationVFS(getTransaction(), "ficheOT.sp", "ficheOT");
		FileObject f = g.getFileData();
		//FileWriter fw = new FileWriter(f);
		OutputStream output = f.getContent().getOutputStream();
		OutputStreamWriter fw = new OutputStreamWriter(output,"iso-8859-1");
		PrintWriter pw = new PrintWriter(fw);
		try {	
			//	Entete
			pw.print("1");
			pw.print(Services.lpad(numinv,32," "));
			pw.print(Services.lpad(numimmat,50," "));
			pw.print(Services.lpad(nomequip,64," "));
			pw.print(Services.lpad(type,32," "));
			if(service.length()>64){
				service = service.substring(0,64);
				pw.print(service);
			}else{
				pw.print(Services.lpad(service,64," "));
			}
			// OT
			pw.print(Services.lpad(numOt,8," "));
			pw.print(Services.lpad(dEntree,10," "));
			pw.print(Services.lpad(dSortie,10," "));
			pw.print(Services.lpad(compteur,10," "));
			pw.print(Services.lpad(montantTotal,10," "));
			pw.print(Services.lpad(commentaireOt,600," "));
			pw.println();
			
//			 liste des interventions
			for (int i=0;i<getListeInterventions().size();i++){
				if(!getListeInterventions().get(i).equals("")){
					PePersoInfos unPeP = (PePersoInfos)getListeInterventions().get(i);
					pw.print("2");
					pw.print(Services.lpad(unPeP.getLibelleentretien(),31," "));
					pw.print(Services.lpad(unPeP.getDatereal(),11," "));
					pw.print(Services.lpad(unPeP.getDuree(),6," "));
					pw.println();
				}
			}
			// liste des pièces
			for (int i=0;i<getListePieces().size();i++){
				if(!getListePieces().get(i).equals("")){
					PiecesInfos unPI = (PiecesInfos)getListePieces().get(i);
					pw.print("3");
					pw.print(Services.lpad(unPI.getDesignationpiece(),3," "));
					pw.print(Services.lpad(unPI.getDatesortie(),10," "));
					pw.print(Services.lpad(unPI.getPrix(),7," "));
					pw.print(Services.lpad(unPI.getQuantite(),7," "));
					montantPieces = Integer.parseInt(unPI.getPrix())*Integer.parseInt(unPI.getQuantite());
					pw.print(Services.lpad(String.valueOf(montantPieces),7," "));
					pw.print(getListePieces().get(i));
					pw.println();
				}
			}
			
			// liste des bons d'engagement
			/* correction et optimisation LB 8/11/11
			for(int i=0;i<getListeBe.size();i++){
				if(!getListeBe().get(i).equals("")){
					BE unBe = (BE)getListeBe().get(i);
			*/
			for(int i=0;i<getListeENGJUGroupByCdepNoengjIdetbs().size();i++){
				if(!getListeENGJUGroupByCdepNoengjIdetbs().get(i).equals("")){
					ENGJU unENGJU = (ENGJU)getListeENGJUGroupByCdepNoengjIdetbs().get(i);
					infosBe = listeBe(request,unENGJU);
					
					pw.print("4");
					pw.print(Services.lpad(unENGJU.getNoengj(),11," "));
					if(!infosBe.equals("")){
						inter1 = infosBe.indexOf(";");
						inter2 = inter1 + 1;
						pw.print(Services.lpad(infosBe.substring(0,inter1),11," "));
						inter1 = infosBe.indexOf(";",inter2);
						
						if(infosBe.substring(inter2,inter1).length()>31){
							chainedeb = infosBe.substring(inter2,31+inter2);
							pw.print(chainedeb);
						}else{
							pw.print(Services.lpad(infosBe.substring(inter2,inter1),31," "));
						}
						//inter1 = inter2+1;
						inter2 = inter1+1;//infosBe.indexOf(";",inter1);
						pw.print(Services.lpad(infosBe.substring(inter2,infosBe.length()),11," "));
					}else{
						pw.print(Services.lpad(" ",42," "));
						pw.print(Services.lpad("0",11," "));
					}
					//pw.print(getListeBe().get(i));
					pw.println();
				}
			}
			
			// liste des intervenants
			for(int i=0;i<getListeIntervenants().size();i++){
				if(!getListeIntervenants().get(i).equals("")){
					OT_ATM unOT_ATM = (OT_ATM)getListeIntervenants().get(i);
					Agents unAgent = Agents.chercherAgents(getTransaction(),unOT_ATM.getMatricule());
					if(getTransaction().isErreur()){
						return false;
					}
					pw.print("5");
					pw.print(Services.lpad(unAgent.getNom().trim()+" "+unAgent.getPrenom(),31," "));
					pw.println();
				}
			}
					
			pw.close();
			fw.close();
		} catch (Exception e) {
			pw.close();
			fw.close();
			throw e;
		} finally {
			pw.close();
			fw.close();
		}
			setScript(g.getScriptOuverture());		
	}
	return true;
}

private String listeBe(javax.servlet.http.HttpServletRequest request,ENGJU unENGJU) throws Exception {
	String be;
	/* LB Correction et ioptimisation 8/11/11
	ENGJU unEnju = ENGJU.chercherpremierENGJU(getTransaction(),unBe.getExerci(),unBe.getNoengj());
	if(getTransaction().isErreur()){
		messErreur = "Une erreur s'est produite lors du calcul du montant du bon d'engagement.";
		//getTransaction().declarerErreur("Une erreur s'est produite lors du calcul du montant du bon d'engagement.");
		getTransaction().traiterErreur() ;
		return "";
	}
	//on recherche les lignes concernant l'engagement juridique et on calcul le montant
	int montantTotal = ENGJU.montantBE(getTransaction(),unEnju);
	if(montantTotal==-1){
		messErreur = "Une erreur s'est produite lors du calcul du montant du bon d'engagement.";
		//getTransaction().declarerErreur("Une erreur s'est produite lors du calcul du montant du bon d'engagement.");
		getTransaction().traiterErreur();
		return "";
	}
	be = unEnju.getIdetbs()+";"+unEnju.getEnscom()+";"+montantTotal;
	*/
	be = unENGJU.getIdetbs()+";"+unENGJU.getEnscom()+";"+unENGJU.getMtlenju();
	return be;
}


	public String getScript() {
		if (script == null) script="";
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	public String afficheScript() {	
		String res = getScript();
		setScript(null);
		return res;
	}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (28/07/05 14:46:02)
 * @author : Générateur de process
 */
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_IMPRIMER
		if (testerParametre(request, getNOM_PB_IMPRIMER())) {
			return performPB_IMPRIMER(request);
		}

		//Si clic sur le bouton PB_OT
		if (testerParametre(request, getNOM_PB_OT())) {
			return performPB_OT(request);
		}

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
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
 * Date de création : (20/06/07 10:37:30)
 * @author : Générateur de process
 */
public String getJSP() {
	return "OeOT_Visualisation.jsp";
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MESSAGE_ERREUR
 * Date de création : (20/06/07 10:37:30)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_MESSAGE_ERREUR() {
	return "NOM_ST_MESSAGE_ERREUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MESSAGE_ERREUR
 * Date de création : (20/06/07 10:37:30)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_MESSAGE_ERREUR() {
	return getZone(getNOM_ST_MESSAGE_ERREUR());
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
	return getNOM_EF_OT();
}

public ArrayList<ENGJU> getListeENGJUGroupByCdepNoengjIdetbs() {
	return listeENGJUGroupByCdepNoengjIdetbs;
}

public void setListeENGJUGroupByCdepNoengjIdetbs(
		ArrayList<ENGJU> listeENGJUGroupByCdepNoengjIdetbs) {
	this.listeENGJUGroupByCdepNoengjIdetbs = listeENGJUGroupByCdepNoengjIdetbs;
}

}
