package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.Declarations;
import nc.mairie.seat.metier.Entretien;
import nc.mairie.seat.metier.FPM;
import nc.mairie.seat.metier.PM_PePerso;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.PMateriel;
import nc.mairie.seat.metier.Pm_PePersoInfos;
import nc.mairie.technique.*;
/**
 * Process OeEntretiensFPM
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
*/
public class OeEntretiens_FPM extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4095294016607055471L;
	private String ACTION_SUPPRESSION = "Suppression";
	private String ACTION_MODIFICATION = "Modification";
	private String ACTION_CREATION = "Création";
	private java.lang.String[] LB_ENTRETIENS;
	public boolean isOcassionnel;
	public boolean suppresion = false;
	private String focus = null;
	private PMatInfos pMatInfosCourant;
	private FPM fpmCourant;
	private PM_PePerso pmPePersoCourant;
	private Entretien entretienCourant;
	private Declarations declarationCourante;
	private String titreAction="";
	private ArrayList<Entretien> listEntretiens;
	private ArrayList<?> listEntretiensFPM;
	private boolean isFirst = true;
	private String newCommentaire;
	private String newDuree;
	private String newDprevu;
	private String newDreal;
	private String newSinistre;
	private String actionFPM;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	if(isFirst){
			
		// récupération des variables globales
		PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request,"PMATINFOS");
		FPM unFPM = (FPM)VariableGlobale.recuperer(request,"FPM");
		
		PM_PePerso unPePerso = (PM_PePerso)VariableGlobale.recuperer(request,"PMPEPERSO");
		titreAction = (String)VariableActivite.recuperer(this,"TITRE_ACTION");
		actionFPM = (String)VariableActivite.recuperer(this,"FPM_TITRE_ACTION");
		addZone(getNOM_ST_TITRE_ACTION(),titreAction);
		if(titreAction.equals(ACTION_SUPPRESSION)){
			setSuppresion(true);
		}else{
			setSuppresion(false);
		}
		// controle si null
		if (unPMatInfos.getPminv()!=null){
			setPMatInfosCourant(unPMatInfos);
		}
		if(unFPM!=null){
			setFpmCourant(unFPM);
		}
		if(unPePerso.getCodepmpep()!=null){
			setPmPePersoCourant(unPePerso);
			Entretien unEntretien = Entretien.chercherEntretien(getTransaction(),getPmPePersoCourant().getCodeentretien());
			if(getTransaction().isErreur()){
				return;
			}
			setEntretienCourant(unEntretien);
			newCommentaire = getPmPePersoCourant().getCommentaire();
			newDuree = getPmPePersoCourant().getDuree();
			newSinistre = getPmPePersoCourant().getSinistre();
//			 on coche la case si c'est un sinistre
			if ("oui".equals(getPmPePersoCourant().getSinistre().trim())){
				addZone(getNOM_CK_SINISTRE(),getCHECKED_ON());
			}else{
				addZone(getNOM_CK_SINISTRE(),getCHECKED_OFF());
			}
			
			if(getPmPePersoCourant().getDprev().equals("01/01/0001")){
				newDprevu = "";
			}else{
				newDprevu = getPmPePersoCourant().getDprev();
			}
			if(getPmPePersoCourant().getDreal().equals("01/01/0001")){
				newDreal = "";
			}else{
				newDreal = getPmPePersoCourant().getDreal();
			}
		}else{
			setPmPePersoCourant(new PM_PePerso());
		}
	}
	
	// on initialise les zones si pas vide
	if (getFpmCourant().getNumfiche()!=null){
		if(getPMatInfosCourant().getPminv()!=null){
			//on renseigne les zones
			addZone(getNOM_ST_TYPE(),getPMatInfosCourant().getDesignationtypeequip());
			addZone(getNOM_ST_MARQUE(),getPMatInfosCourant().getDesignationmarque());
			addZone(getNOM_ST_MODELE(),getPMatInfosCourant().getDesignationmodele());
			addZone(getNOM_ST_NOIMMAT(),getPMatInfosCourant().getPmserie());
			addZone(getNOM_ST_NOINVENT(),getPMatInfosCourant().getPminv());
			addZone(getNOM_ST_NOFICHE(),getFpmCourant().getNumfiche());
		}else{
//			on vide les zones
			addZone(getNOM_ST_TYPE(),"");
			addZone(getNOM_ST_MARQUE(),"");
			addZone(getNOM_ST_MODELE(),"");
			addZone(getNOM_ST_NOIMMAT(),"");
			addZone(getNOM_ST_NOINVENT(),"");
			addZone(getNOM_ST_NOFICHE(),"");
		}
	}
	
	if(getVAL_ST_TITRE_ACTION().equals(ACTION_SUPPRESSION)){
		Pm_PePersoInfos unPePersoInfos = Pm_PePersoInfos.chercherPm_PePersoInfos(getTransaction(),getPmPePersoCourant().getCodepmpep());
		if(getTransaction().isErreur()){
			return ;
		}
		addZone(getNOM_ST_ENTRETIEN(),unPePersoInfos.getLibelleentretien());
		if(unPePersoInfos.getDreal().equals("01/01/0001")){
			addZone(getNOM_ST_DREAL(),"");
		}else{
			addZone(getNOM_ST_DREAL(),unPePersoInfos.getDreal());
		}
		if(unPePersoInfos.getDprev().equals("01/01/0001")){
			addZone(getNOM_ST_DPREVU(),"");
		}else{
			addZone(getNOM_ST_DPREVU(),unPePersoInfos.getDprev());
		}
		addZone(getNOM_ST_DUREE(),unPePersoInfos.getDuree());
		
	}else{
		//	initialisation des listes
		initialiseListEntretiens(request);
		
		//	initialisation des champs
		addZone(getNOM_EF_COMMENTAIRE(),newCommentaire);
		addZone(getNOM_EF_DPREVU(),newDprevu);
		addZone(getNOM_EF_DREAL(),newDreal);
		addZone(getNOM_EF_DUREE(),newDuree);
	}
	setFirst(false);
	
}

public void initialiseListEntretiens(javax.servlet.http.HttpServletRequest request) throws Exception{
	ArrayList<Entretien> a = Entretien.listerEntretien(getTransaction());
	String[] colonnes = {"libelleentretien"};
	//ordre croissant
	boolean[] ordres = {true};
	a = Services.trier(a,colonnes,ordres);
	
	setListEntretiens(a);
	if (a.size()>0){
		int [] tailles = {20};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (int i = 0; i < a.size() ; i++) {
			Entretien aEntretien = (Entretien)a.get(i);	
			String ligne [] = { aEntretien.getLibelleentretien()};
			aFormat.ajouteLigne(ligne);
		}
		setLB_ENTRETIENS(aFormat.getListeFormatee());
	}else{
		setLB_ENTRETIENS(null);
	}	
	//si modif
	if(getPmPePersoCourant()!=null){
//		 on trouve le bon  entretien
		if(getEntretienCourant()!=null){
			//	recherche du type d'intervalle courant
			addZone(getNOM_LB_ENTRETIENS_SELECT(),String.valueOf(-1));
			for (int i = 0; i < getListEntretiens().size(); i++) {
				Entretien unEntretien = (Entretien)getListEntretiens().get(i);
				if (unEntretien.getCodeentretien().equals(getEntretienCourant().getCodeentretien())) {
					addZone(getNOM_LB_ENTRETIENS_SELECT(),String.valueOf(i));
					break;
				}
			}
		}
	}
}


/**
 * Constructeur du process OeEntretiens_FPM.
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 */
public OeEntretiens_FPM() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
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
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	// si FPM est en création alors si pas d'entretien enregistré on supprime FPM
	if(actionFPM.equals(ACTION_CREATION)){
		// recherche des entretiens
		if(getFpmCourant().getNumfiche()!=null){
			ArrayList<Pm_PePersoInfos> listEntretiens = Pm_PePersoInfos.chercherPmPePersoInfosFPM(getTransaction(),getFpmCourant().getNumfiche());
			if(getTransaction().isErreur()){
				return false;
			}
			if(listEntretiens.size()==0){
				getFpmCourant().suppressionFPM(getTransaction());
				if(getTransaction().isErreur()){
					return false;
				}
			}
		}
		setFpmCourant(new FPM());
		
	}
	if(null==getPMatInfosCourant()){
		setPMatInfosCourant(new PMatInfos());
	}
	// tout s'est bien passé
	commitTransaction();
	VariableGlobale.ajouter(request,"FPM",getFpmCourant());
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ENLEVER
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_ENLEVER() {
	return "NOM_PB_ENLEVER";
}

/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_OK() {
	return "NOM_PB_OK";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_ENT
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_OK_ENT() {
	return "NOM_PB_OK_ENT";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_OK_ENT(javax.servlet.http.HttpServletRequest request) throws Exception {
	
	int indice = (Services.estNumerique(getVAL_LB_ENTRETIENS_SELECT()) ? Integer.parseInt(getVAL_LB_ENTRETIENS_SELECT()): -1);
	Entretien unEntretien = (Entretien)getListEntretiens().get(indice);
	setEntretienCourant(unEntretien);
	// on récupère les infos 
	newDprevu = getZone(getNOM_EF_DPREVU());
	newDreal = getZone(getNOM_EF_DREAL());
	newCommentaire = getZone(getNOM_EF_COMMENTAIRE());
	newDuree = getZone(getNOM_EF_DUREE());
	if(titreAction.equals(ACTION_CREATION)){
		// on recherche si cet entretien est déjà inscrit pour ce petit matériel
		// si l'entretien a déjà été enregistré pour cet equipement, on affiche par l'intervalle, la durée...
		PM_PePerso unPePerso = PM_PePerso.chercherPmPePersoEquipEntPrevu(getTransaction(),getPMatInfosCourant().getPminv(),getEntretienCourant().getCodeentretien());
		if(getTransaction().isErreur()){
			// l'entretien n'a pas été enregistré pour ce petit matériel
			getTransaction().traiterErreur();
		}
		// si déjà enretistré pour cette FPM
		if(unPePerso.getNumfiche()!=null){
			if(unPePerso.getNumfiche().equals(getFpmCourant().getNumfiche())){
				getTransaction().declarerErreur("Cet entretien est déjà enregistré pour cette fiche d'entretien.");
				return false;
			}
		
			if (newDprevu.equals("")){
				if(unPePerso.getDprev().equals("01/01/0001")){
					newDprevu="";
				}else{
					newDprevu = unPePerso.getDprev();
				}
				getPmPePersoCourant().setDprev(newDprevu);
				//addZone(getNOM_EF_DPREVU(),newDprevu);
			}
			if(newDuree.equals("")){
				newDuree = unPePerso.getDuree();
				getPmPePersoCourant().setDuree(newDuree);
				//addZone(getNOM_EF_DUREE(),newDuree);
			}
			if(newCommentaire.equals("")){
				newCommentaire = unPePerso.getCommentaire();
				getPmPePersoCourant().setCommentaire(newCommentaire);
				//addZone(getNOM_EF_COMMENTAIRE(),newCommentaire);
			}	
			int indiceEnt = Integer.parseInt(unPePerso.getCodeentretien());
			getPmPePersoCourant().setCodeentretien(String.valueOf(indiceEnt));
			//addZone(getNOM_LB_ENTRETIENS_SELECT(),String.valueOf(indiceEnt));
		}
	}
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_TENT
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_OK_TENT() {
	return "NOM_PB_OK_TENT";
}

/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
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
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	if(getVAL_ST_TITRE_ACTION().equals(ACTION_SUPPRESSION)){
		// on enlève l'entretien de FPM 
		getPmPePersoCourant().setNumfiche("");
		getPmPePersoCourant().supprimerPM_PePerso(getTransaction());
	}else{
		int indice = (Services.estNumerique(getVAL_LB_ENTRETIENS_SELECT()) ? Integer.parseInt(getVAL_LB_ENTRETIENS_SELECT()): -1);
		Entretien monEntretien = (Entretien)getListEntretiens().get(indice);
		if(getTransaction().isErreur()){
			return false;
		}
		setEntretienCourant(monEntretien);
		
		//	 on ajoute l'entretien voulu dans la table f_pe_perso
		newCommentaire = getZone(getNOM_EF_COMMENTAIRE());
		newDuree = getZone(getNOM_EF_DUREE());
		String sinistre = getZone(getNOM_CK_SINISTRE());
		// controle si les dates sont correctes
		if (Services.estUneDate(getZone(getNOM_EF_DPREVU()))){
			newDprevu = getZone(getNOM_EF_DPREVU());
		}else if (!getZone(getNOM_EF_DPREVU()).equals("")){
			getTransaction().declarerErreur("La date prévue n'est pas correcte.");
			setFocus(getNOM_EF_DPREVU());
			return false;
		}else{
			newDprevu = "";
		}
		if (Services.estUneDate(getZone(getNOM_EF_DREAL()))){
			newDreal = getZone(getNOM_EF_DREAL());
		}else if (!getZone(getNOM_EF_DREAL()).equals("")){
			getTransaction().declarerErreur("La date de réalisation n'est pas correcte.");
			setFocus(getNOM_EF_DREAL());
			return false;
		}else{
			newDreal = "";
		}
//		 pour réservé
		newSinistre = sinistre.equals(getCHECKED_ON()) ? "O" : "N";
		//controle
		if (newDuree.equals("")){
			getTransaction().declarerErreur("Vous devez renseigner la durée.");
			setFocus(getNOM_EF_DUREE());
			return false;
		}
		
		PMateriel unPMateriel = PMateriel.chercherPMateriel(getTransaction(),getPMatInfosCourant().getPminv());
		if(getTransaction().isErreur()){
			return false;
		}
		if(titreAction.equals(ACTION_CREATION)){
	//		 si l'entretien est déjà prévu dans le Plan d'entretien personnalisé du petit matériel
			// on modifie le numfiche du PePerso
			// sinon on le créée
			PM_PePerso unPePerso = PM_PePerso.chercherPmPePersoEquipEntPrevu(getTransaction(),getPMatInfosCourant().getPminv(),getEntretienCourant().getCodeentretien());
			if(getTransaction().isErreur()){
				getTransaction().traiterErreur();
			}
			if (unPePerso.getCodepmpep()!=null){
				unPePerso.setNumfiche(getFpmCourant().getNumfiche());
				unPePerso.setCommentaire(newCommentaire);
				unPePerso.setDprev(Services.dateDuJour());
				unPePerso.setDuree(newDuree);
				unPePerso.setDreal(newDreal);
				unPePerso.setSinistre(newSinistre);
				unPePerso.modifierPmPePersoInfos(getTransaction());
			}else{
				getTransaction().traiterErreur();
				//	on renseigne les zones
				PM_PePerso newPePerso = new PM_PePerso();
				newPePerso.setNumfiche(getFpmCourant().getNumfiche());
				newPePerso.setCommentaire(newCommentaire);
				newPePerso.setDprev(Services.dateDuJour());
				newPePerso.setDreal(newDreal);
				newPePerso.setDuree(newDuree);
				unPePerso.setSinistre(newSinistre);
				addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);
				newPePerso.creerPM_PePerso(getTransaction(),unPMateriel,getEntretienCourant());
			}
		}else{
			getPmPePersoCourant().setNumfiche(getFpmCourant().getNumfiche());
			getPmPePersoCourant().setCommentaire(newCommentaire);
			getPmPePersoCourant().setDprev(newDprevu);
			getPmPePersoCourant().setDuree(newDuree);
			getPmPePersoCourant().setDreal(newDreal);
			getPmPePersoCourant().setSinistre(newSinistre);
			getPmPePersoCourant().modifierPM_PePerso(getTransaction(),unPMateriel,getEntretienCourant());
		}
	}
	if(getTransaction().isErreur()){
		return false;
	}
	// si pas de souci on commit
	commitTransaction();
	if(null==getPMatInfosCourant()){
		setPMatInfosCourant(new PMatInfos());
	}
	VariableGlobale.ajouter(request,"FPM",getFpmCourant());
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	setStatut(STATUT_PROCESS_APPELANT,true);
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODELE
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_MODELE() {
	return "NOM_ST_MODELE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODELE
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_MODELE() {
	return getZone(getNOM_ST_MODELE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOIMMAT() {
	return "NOM_ST_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOIMMAT() {
	return getZone(getNOM_ST_NOIMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINVENT
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOINVENT() {
	return "NOM_ST_NOINVENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINVENT
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOINVENT() {
	return getZone(getNOM_ST_NOINVENT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOOT
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOFICHE() {
	return "NOM_ST_NOFICHE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOOT
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOFICHE() {
	return getZone(getNOM_ST_NOFICHE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_COMMENTAIRE
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_COMMENTAIRE() {
	return "NOM_EF_COMMENTAIRE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_COMMENTAIRE
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_COMMENTAIRE() {
	return getZone(getNOM_EF_COMMENTAIRE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DUREE
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_DUREE() {
	return "NOM_EF_DUREE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DUREE
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_DUREE() {
	return getZone(getNOM_EF_DUREE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_INTERVALLE
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_INTERVALLE() {
	return "NOM_EF_INTERVALLE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_INTERVALLE
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_INTERVALLE() {
	return getZone(getNOM_EF_INTERVALLE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_ENTRETIENS
 * Date de création : (29/07/05 09:18:57)
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
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 */
private void setLB_ENTRETIENS(java.lang.String[] newLB_ENTRETIENS) {
	LB_ENTRETIENS = newLB_ENTRETIENS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_ENTRETIENS
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_ENTRETIENS() {
	return "NOM_LB_ENTRETIENS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_ENTRETIENS_SELECT
 * Date de création : (29/07/05 09:18:57)
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
 * Date de création : (29/07/05 09:18:57)
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
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_ENTRETIENS_SELECT() {
	return getZone(getNOM_LB_ENTRETIENS_SELECT());
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
	return getNOM_LB_ENTRETIENS();
}
public boolean isOcassionnel() {
	return isOcassionnel;
}
public void setOcassionnel(boolean isOcassionnel) {
	this.isOcassionnel = isOcassionnel;
}
public PMatInfos getPMatInfosCourant() {
	return pMatInfosCourant;
}
public void setPMatInfosCourant(PMatInfos pMatInfosCourant) {
	this.pMatInfosCourant = pMatInfosCourant;
}
public FPM getFpmCourant() {
	return fpmCourant;
}
public void setFpmCourant(FPM fpmCourant) {
	this.fpmCourant = fpmCourant;
}
public PM_PePerso getPmPePersoCourant() {
	return pmPePersoCourant;
}
public void setPmPePersoCourant(PM_PePerso pmPePersoCourant) {
	this.pmPePersoCourant = pmPePersoCourant;
}
public ArrayList<Entretien> getListEntretiens() {
	return listEntretiens;
}
public void setListEntretiens(ArrayList<Entretien> listEntretiens) {
	this.listEntretiens = listEntretiens;
}
public ArrayList<?> getListEntretiensFPM() {
	return listEntretiensFPM;
}
public void setListEntretiensFPM(ArrayList<?> listEntretiensFPM) {
	this.listEntretiensFPM = listEntretiensFPM;
}

	public Entretien getEntretienCourant() {
		return entretienCourant;
	}
	public void setEntretienCourant(Entretien entretienCourant) {
		this.entretienCourant = entretienCourant;
	}
	public boolean isFirst() {
		return isFirst;
	}
	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DPREVU
 * Date de création : (29/07/05 15:07:18)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_DPREVU() {
	return "NOM_EF_DPREVU";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DPREVU
 * Date de création : (29/07/05 15:07:18)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_DPREVU() {
	return getZone(getNOM_EF_DPREVU());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DREAL
 * Date de création : (29/07/05 15:07:18)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_DREAL() {
	return "NOM_EF_DREAL";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DREAL
 * Date de création : (29/07/05 15:07:18)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_DREAL() {
	return getZone(getNOM_EF_DREAL());
}
/**
 * Retourne le nom de la case à cocher sélectionnée pour la JSP :
 * CK_SINISTRE
 * Date de création : (29/07/05 15:07:18)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_CK_SINISTRE() {
	return "NOM_CK_SINISTRE";
}
/**
 * Retourne la valeur de la case à cocher à afficher par la JSP pour la case à cocher  :
 * CK_SINISTRE
 * Date de création : (29/07/05 15:07:18)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_CK_SINISTRE() {
	return getZone(getNOM_CK_SINISTRE());
}
	public boolean isSuppresion() {
		return suppresion;
	}
	public void setSuppresion(boolean suppresion) {
		this.suppresion = suppresion;
	}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (29/07/05 09:18:57)
 * author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}


		//Si clic sur le bouton PB_OK
		if (testerParametre(request, getNOM_PB_OK())) {
			return performPB_OK(request);
		}

		//Si clic sur le bouton PB_OK_ENT
		if (testerParametre(request, getNOM_PB_OK_ENT())) {
			return performPB_OK_ENT(request);
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
 * Date de création : (01/08/05 14:42:19)
 * author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeEntretiens_FPM.jsp";
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DPREVU
 * Date de création : (01/08/05 14:42:19)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DPREVU() {
	return "NOM_ST_DPREVU";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DPREVU
 * Date de création : (01/08/05 14:42:19)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DPREVU() {
	return getZone(getNOM_ST_DPREVU());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DREAL
 * Date de création : (01/08/05 14:42:19)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DREAL() {
	return "NOM_ST_DREAL";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DREAL
 * Date de création : (01/08/05 14:42:19)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DREAL() {
	return getZone(getNOM_ST_DREAL());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DUREE
 * Date de création : (01/08/05 14:42:19)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DUREE() {
	return "NOM_ST_DUREE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DUREE
 * Date de création : (01/08/05 14:42:19)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DUREE() {
	return getZone(getNOM_ST_DUREE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_ENTRETIEN
 * Date de création : (01/08/05 14:42:19)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_ENTRETIEN() {
	return "NOM_ST_ENTRETIEN";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_ENTRETIEN
 * Date de création : (01/08/05 14:42:19)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_ENTRETIEN() {
	return getZone(getNOM_ST_ENTRETIEN());
}
	public String getNewSinistre() {
		return newSinistre;
	}
	public void setNewSinistre(String newSinistre) {
		this.newSinistre = newSinistre;
	}
	public Declarations getDeclarationCourante() {
		return declarationCourante;
	}
	public void setDeclarationCourante(Declarations declarationCourante) {
		this.declarationCourante = declarationCourante;
	}
}
