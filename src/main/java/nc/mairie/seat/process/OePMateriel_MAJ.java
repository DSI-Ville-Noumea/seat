package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.Fre_PM;
import nc.mairie.seat.metier.Marques;
import nc.mairie.seat.metier.Modeles;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.PMateriel;
import nc.mairie.seat.metier.TYPEEQUIP;
import nc.mairie.technique.*;
/**
 * Process OePMateriel_MAJ
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
*/
public class OePMateriel_MAJ extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1247364801499953894L;
	private java.lang.String[] LB_FOURNISSEUR;
	private java.lang.String[] LB_MARQUE;
	private java.lang.String[] LB_MODELE;
	private boolean first = true;
	private String focus = null;
	public boolean isModif = false;
	private String ACTION_MARQUE = "Sélection d'une marque";
	private ArrayList<Marques> listeMarque = null;
	private ArrayList<Modeles> listeModele = null;
	private ArrayList<Fre_PM> listeFre = null;
	private Marques marqueCourant;
	private Modeles modeleCourant;
	private Fre_PM freCourant;
	private PMateriel pMaterielCourant;
	private String newModele = "";
	private String newFre = "";
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request,"PMATINFOS");
	if(unPMatInfos!=null){
		if ((unPMatInfos.getPminv()!=null)&&(!unPMatInfos.getPminv().equals(""))){
			setModif(true);
		}else{
			setModif(false);
		}
	}else{
		setModif(false);
	}

	// alimentation des zones
//	 liste déroulante
	initialiseListeFre(request);
	initialiseListeMarques(request);
	if (unPMatInfos!=null){
		if(unPMatInfos.getPminv()!=null){
			// on cherche le pMateriel
			PMateriel unPMat = PMateriel.chercherPMateriel(getTransaction(),unPMatInfos.getPminv());
			if(getTransaction().isErreur()){
				return;
			}
			setPMaterielCourant(unPMat);
			addZone(getNOM_EF_PMINV(), unPMatInfos.getPminv());
			addZone(getNOM_EF_DGARANTIE(), unPMatInfos.getDgarantie());
			if ("01/01/0001".equals(unPMatInfos.getDachat())){
				addZone(getNOM_EF_DACHAT(), "");
			}else{
				addZone(getNOM_EF_DACHAT(), unPMatInfos.getDachat());
			}
			if ("01/01/0001".equals(unPMatInfos.getDmhs())){
				addZone(getNOM_EF_DMHS(), "");
			}else{
				addZone(getNOM_EF_DMHS(), unPMatInfos.getDmhs());
			}
			if ("01/01/0001".equals(unPMatInfos.getDmes())){
				addZone(getNOM_EF_DMES(), "");
			}else{
				addZone(getNOM_EF_DMES(), unPMatInfos.getDmes());
			}
			addZone(getNOM_EF_PMSERIE(), unPMatInfos.getPmserie());
			addZone(getNOM_EF_PRIX(), unPMatInfos.getPrix());
			if(first){
	//			sélectionner du bon modèle
				Modeles modele = Modeles.chercherModeles(getTransaction(),unPMatInfos.getCodemodele());
				if (getTransaction().isErreur()){
					return;
				}
				setModeleCourant(modele);
				
				Marques marque = Marques.chercherMarques(getTransaction(),modele.getCodemarque());
				if (getTransaction().isErreur()){
					return;
				}
				setMarqueCourant(marque);
				Fre_PM fre_pm = Fre_PM.chercherFre_PM(getTransaction(),unPMatInfos.getCodefre());
				if(getTransaction().isErreur()){
					return;
				}
				setFreCourant(fre_pm);
				
	//			recherche de la marque courante
				addZone(getNOM_LB_MARQUE_SELECT(),String.valueOf(-1));
				for (int i = 0; i < getListeMarque().size(); i++) {
					Marques uneMarques = (Marques)getListeMarque().get(i);
					if (uneMarques.getCodemarque().equals(getMarqueCourant().getCodemarque())) {
						addZone(getNOM_LB_MARQUE_SELECT(),String.valueOf(i));
						break;
					}
				}
				performPB_MARQUE(request);
	//			recherche du modèle courant
				addZone(getNOM_LB_MODELE_SELECT(),String.valueOf(-1));
				for (int i = 0; i < getListeModele().size(); i++) {
					Modeles unModele = (Modeles)getListeModele().get(i);
					if (unModele.getCodemodele().equals(getModeleCourant().getCodemodele())) {
						addZone(getNOM_LB_MODELE_SELECT(),String.valueOf(i));
						TYPEEQUIP monTe = TYPEEQUIP.chercherTYPEEQUIP(getTransaction(),unModele.getCodete());
						if(getTransaction().isErreur()){
							return ;
						}
						addZone(getNOM_ST_TYPE_EQUIP(), monTe.getDesignationte());
						break;
					}
				}
//				recherche du fournisseur courant
				addZone(getNOM_LB_FOURNISSEUR_SELECT(),String.valueOf(-1));
				for (int i = 0; i < getListeFre().size(); i++) {
					Fre_PM unFre_PM = (Fre_PM)getListeFre().get(i);
					if (unFre_PM.getCodefre().equals(getFreCourant().getCodefre())) {
						addZone(getNOM_LB_FOURNISSEUR_SELECT(),String.valueOf(i));
						break;
					}
				}		
				
			}
//			 on coche la case si le petit matériel est reservé
			if ("T".equals(unPMatInfos.getReserve().trim())){
				addZone(getNOM_CK_RESERVE(),getCHECKED_ON());
			}else{
				addZone(getNOM_CK_RESERVE(),getCHECKED_OFF());
			}
			
		}
	}
//	 on met le focus sur le libellé du modèle
	setFocus(getNOM_EF_PMINV());
	setStatut(STATUT_MEME_PROCESS);
	addZone(getNOM_ST_MARQUE(),ACTION_MARQUE);
first=false;
}

public void initialiseListeMarques(javax.servlet.http.HttpServletRequest request) throws Exception{
//	on initialise la liste des marques
	if(first){
	//	 Si liste des marques est vide
		if (getLB_MARQUE() == LBVide) {
			ArrayList<Marques> a = Marques.listerMarquesModeleMT(getTransaction());
			if (getTransaction().isErreur()){
				return;
			}
			setListeMarque(a);
			//les élèments de la liste 
			int [] tailles = {15};
			String [] champs = {"designationmarque"};
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"G"};
		
			setLB_MARQUE(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
		}
		if (getLB_MARQUE()==LBVide){
			getTransaction().declarerErreur("Attention. Aucun modèle ou Aucune marque n'est enregistré");
			//setManqueParam(true);
		}else{
			addZone(getNOM_LB_MARQUE_SELECT(),"0");
			performPB_MARQUE(request);
		}

	}
}

public void initialiseListeFre(javax.servlet.http.HttpServletRequest request) throws Exception{
	if(getLB_FOURNISSEUR()==LBVide){
		ArrayList<Fre_PM> a = Fre_PM.listerFre_PM(getTransaction());
		if(getTransaction().isErreur()){
			return;
		}
		setListeFre(a);
		if (a.size()>0){
			//les élèments de la liste
			int [] tailles = {25};
			String [] champs = {"libellefre"};
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"G"};
			boolean[] colonnes = {true};
			a = Services.trier(a,champs,colonnes);
			setListeFre(a);
			
			setLB_FOURNISSEUR(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
		}else{
			setLB_FOURNISSEUR(null);
		};
	}

}

/**
 * Constructeur du process OePMateriel_MAJ.
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 */
public OePMateriel_MAJ() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (25/04/07 13:30:11)
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
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	 remise à blanc des champs
	addZone(getNOM_EF_DACHAT(),"");
	addZone(getNOM_EF_DGARANTIE(),"");
	addZone(getNOM_EF_DMES(),"");
	addZone(getNOM_EF_DMHS(),"");
	addZone(getNOM_EF_PMINV(),"");
	addZone(getNOM_EF_PMSERIE(),"");
	addZone(getNOM_EF_PRIX(),"");
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (25/04/07 13:30:11)
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
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	String newPrix = getZone(getNOM_EF_PRIX());
	String newDGarantie = getZone(getNOM_EF_DGARANTIE());
	String newDMES = "";
	String ancienNumero = "";
	int indice = (Services.estNumerique(getVAL_LB_MODELE_SELECT()) ? Integer.parseInt(getVAL_LB_MODELE_SELECT()): -1);
	Modeles monModele = (Modeles)getListeModele().get(indice);
	setModeleCourant(monModele);
	if (getTransaction().isErreur()){
		return false;
	}
	//Pour l'ajout ou la modification
	newModele = monModele.getCodemodele();
	
	// pour le fournisseur
	indice = (Services.estNumerique(getVAL_LB_FOURNISSEUR_SELECT()) ? Integer.parseInt(getVAL_LB_FOURNISSEUR_SELECT()): -1);
	Fre_PM monFournisseur = (Fre_PM)getListeFre().get(indice);
	setFreCourant(monFournisseur);
	if (getTransaction().isErreur()){
		return false;
	}
	//Pour l'ajout ou la modification
	newFre = monFournisseur.getCodefre();
	
	
	//Récup des zones saisies
	String newInventaire = getZone(getNOM_EF_PMINV()).toUpperCase();
//	si le petit matériel est de type MT (matériel) alors le numéro immatriculation = numéro d'inventaire
	String newSerie = getZone(getNOM_EF_PMSERIE()).toUpperCase();
	
//	if(!Services.estNumerique(newDGarantie)){
//		getTransaction().declarerErreur("La durée de garantie doit être numérique.");
//		return false;
//	}
//	if(!Services.estNumerique(newPrix)){
//		getTransaction().declarerErreur("Le prix d'achat doit être numérique.");
//		return false;
//	}
		
	if (Services.estUneDate(getZone(getNOM_EF_DMES()))){
		newDMES = Services.formateDate(getZone(getNOM_EF_DMES()).toUpperCase());
	}else if (!getZone(getNOM_EF_DMES()).equals("")){
		getTransaction().declarerErreur("La date de mise en circulation n'est pas correcte.");
		setFocus(getNOM_EF_DMES());
		return false;
	}
	String newDateHorsCircuit = "";
	if (Services.estUneDate(getZone(getNOM_EF_DMHS()))){
		newDateHorsCircuit = Services.formateDate(getZone(getNOM_EF_DMHS()).toUpperCase());
	}else if (!getZone(getNOM_EF_DMHS()).equals("")){
		getTransaction().declarerErreur("La date hors circuit n'est pas correcte.");
		setFocus(getNOM_EF_DMHS());
		return false;
	}
	String newDachat = "";
	if (Services.estUneDate(getZone(getNOM_EF_DACHAT()))){
		newDachat = Services.formateDate(getZone(getNOM_EF_DACHAT()).toUpperCase());
	}else if (!getZone(getNOM_EF_DACHAT()).equals("")){
		getTransaction().declarerErreur("La date d'achat n'est pas correcte.");
		setFocus(getNOM_EF_DACHAT());
		return false;
	}
	
	String reserve = getZone(getNOM_CK_RESERVE());
//	 pour réservé
	String newReserve = reserve.equals(getCHECKED_ON()) ? "T" : "F";
			
	//	 Pour les champs obligatoires
	//Si lib inventaire non saisit
	if (newInventaire.length() == 0) {
		getTransaction().declarerErreur("Le n° d'inventaire est obligatoire");
		setFocus(getNOM_EF_PMINV());
		return false;
	}
	//	Si lib immatriculation non saisit
	if (newSerie.length() == 0) {
		getTransaction().declarerErreur("Le n° de série est obligatoire");
		setFocus(getNOM_EF_PMSERIE());
		return false;
	}
	//	Si lib prix non saisit
	if (newPrix.length() == 0) {
		getTransaction().declarerErreur("Le prix est obligatoire");
		setFocus(getNOM_EF_PRIX());
		return false;
	}else{
		newPrix = Outils.enleveEspace(newPrix);
	}
	//	Si lib datemiseencirculation non saisit
	if (newDMES.length() == 0) {
		getTransaction().declarerErreur("La date de mise en circulation est obligatoire");
		setFocus(getNOM_EF_DMES());
		return false;
	}
//		Si lib codemodele non saisit
	if (newModele.length() == 0) {
		getTransaction().declarerErreur("Le modele est obligatoire");
		setFocus(getNOM_LB_MODELE());
		return false;
	}
	
	// si durée de garantie non saisi
	if(newDGarantie.length()==0){
		getTransaction().declarerErreur("La durée de garantie est obligatoire");
		setFocus(getNOM_EF_DGARANTIE());
		return false;
	}
	
	if(isModif()){
//		Affectation des attributs
		ancienNumero = getPMaterielCourant().getPminv();
		getPMaterielCourant().setPminv(newInventaire);
		getPMaterielCourant().setPmserie(newSerie);
		getPMaterielCourant().setCodefre(newFre);
		getPMaterielCourant().setCodemodele(newModele);
		getPMaterielCourant().setDachat(newDachat);
		getPMaterielCourant().setDgarantie(newDGarantie);
		getPMaterielCourant().setDmes(newDMES);
		getPMaterielCourant().setDmhs(newDateHorsCircuit);
		getPMaterielCourant().setPrix(newPrix);
		getPMaterielCourant().setReserve(newReserve);
		//Modification
		getPMaterielCourant().modifierPMateriel(getTransaction(),ancienNumero);
		if (getTransaction().isErreur()){
			return false;
		}
	}else{
		setPMaterielCourant(new PMateriel());
//		Affectation des attributs
	getPMaterielCourant().setPminv(newInventaire);
	getPMaterielCourant().setPmserie(newSerie);
	getPMaterielCourant().setCodefre(newFre);
	getPMaterielCourant().setCodemodele(newModele);
	getPMaterielCourant().setDachat(newDachat);
	getPMaterielCourant().setDgarantie(newDGarantie);
	getPMaterielCourant().setDmes(newDMES);
	getPMaterielCourant().setDmhs(newDateHorsCircuit);
	getPMaterielCourant().setPrix(newPrix);
	getPMaterielCourant().setReserve(newReserve);
		getPMaterielCourant().creerPMateriel(getTransaction(),getPMaterielCourant());
		if(getTransaction().isErreur()){
			return false;
		}
	}
//	Tout s'est bien passé
	commitTransaction();	
	PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),getPMaterielCourant().getPminv());
	if(getTransaction().isErreur()){
		return false;
	}
	if(null==unPMatInfos){
		unPMatInfos = new PMatInfos();
	}
	VariableGlobale.ajouter(request,"PMATINFOS",unPMatInfos);
	// remise à blanc des champs
	addZone(getNOM_EF_DACHAT(),"");
	addZone(getNOM_EF_DGARANTIE(),"");
	addZone(getNOM_EF_DMES(),"");
	addZone(getNOM_EF_DMHS(),"");
	addZone(getNOM_EF_PMINV(),"");
	addZone(getNOM_EF_PMSERIE(),"");
	addZone(getNOM_EF_PRIX(),"");
	//on retourne à la liste des petits matériels
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_PMINV
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_PMINV() {
	return "NOM_EF_PMINV";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_PMINV
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_PMINV() {
	return getZone(getNOM_EF_PMINV());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_PMSERIE
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_PMSERIE() {
	return "NOM_EF_PMSERIE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_PMSERIE
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_PMSERIE() {
	return getZone(getNOM_EF_PMSERIE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_FOURNISSEUR
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 */
private String [] getLB_FOURNISSEUR() {
	if (LB_FOURNISSEUR == null)
		LB_FOURNISSEUR = initialiseLazyLB();
	return LB_FOURNISSEUR;
}
/**
 * Setter de la liste:
 * LB_FOURNISSEUR
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 */
private void setLB_FOURNISSEUR(java.lang.String[] newLB_FOURNISSEUR) {
	LB_FOURNISSEUR = newLB_FOURNISSEUR;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_FOURNISSEUR
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_FOURNISSEUR() {
	return "NOM_LB_FOURNISSEUR";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_FOURNISSEUR_SELECT
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_FOURNISSEUR_SELECT() {
	return "NOM_LB_FOURNISSEUR_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_FOURNISSEUR
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_FOURNISSEUR() {
	return getLB_FOURNISSEUR();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_FOURNISSEUR
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_FOURNISSEUR_SELECT() {
	return getZone(getNOM_LB_FOURNISSEUR_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_MARQUE
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 */
private String [] getLB_MARQUE() {
	if (LB_MARQUE == null)
		LB_MARQUE = initialiseLazyLB();
	return LB_MARQUE;
}
/**
 * Setter de la liste:
 * LB_MARQUE
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 */
private void setLB_MARQUE(java.lang.String[] newLB_MARQUE) {
	LB_MARQUE = newLB_MARQUE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_MARQUE
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_MARQUE() {
	return "NOM_LB_MARQUE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_MARQUE_SELECT
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_MARQUE_SELECT() {
	return "NOM_LB_MARQUE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_MARQUE
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_MARQUE() {
	return getLB_MARQUE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_MARQUE
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_MARQUE_SELECT() {
	return getZone(getNOM_LB_MARQUE_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_MODELE
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 */
private String [] getLB_MODELE() {
	if (LB_MODELE == null)
		LB_MODELE = initialiseLazyLB();
	return LB_MODELE;
}
/**
 * Setter de la liste:
 * LB_MODELE
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 */
private void setLB_MODELE(java.lang.String[] newLB_MODELE) {
	LB_MODELE = newLB_MODELE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_MODELE
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_MODELE() {
	return "NOM_LB_MODELE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_MODELE_SELECT
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_MODELE_SELECT() {
	return "NOM_LB_MODELE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_MODELE
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_MODELE() {
	return getLB_MODELE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_MODELE
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_MODELE_SELECT() {
	return getZone(getNOM_LB_MODELE_SELECT());
}

/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_TYPE
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_TYPE() {
	return "NOM_LB_TYPE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_TYPE_SELECT
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_TYPE_SELECT() {
	return "NOM_LB_TYPE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_TYPE
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_TYPE_SELECT() {
	return getZone(getNOM_LB_TYPE_SELECT());
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
	return getNOM_EF_PMINV();
}
public boolean isModif() {
	return isModif;
}
public void setModif(boolean isModif) {
	this.isModif = isModif;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DMES
 * Date de création : (25/04/07 13:41:23)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_DMES() {
	return "NOM_EF_DMES";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DMES
 * Date de création : (25/04/07 13:41:23)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_DMES() {
	return getZone(getNOM_EF_DMES());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DMHS
 * Date de création : (25/04/07 13:41:23)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_DMHS() {
	return "NOM_EF_DMHS";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DMHS
 * Date de création : (25/04/07 13:41:23)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_DMHS() {
	return getZone(getNOM_EF_DMHS());
}
/**
 * Retourne le nom de la case à cocher sélectionnée pour la JSP :
 * CK_RESERVE
 * Date de création : (25/04/07 13:42:48)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_CK_RESERVE() {
	return "NOM_CK_RESERVE";
}
/**
 * Retourne la valeur de la case à cocher à afficher par la JSP pour la case à cocher  :
 * CK_RESERVE
 * Date de création : (25/04/07 13:42:48)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_CK_RESERVE() {
	return getZone(getNOM_CK_RESERVE());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MARQUE
 * Date de création : (25/04/07 13:45:32)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_MARQUE() {
	return "NOM_PB_MARQUE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (25/04/07 13:45:32)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_MARQUE(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_MARQUE_SELECT()) ? Integer.parseInt(getVAL_LB_MARQUE_SELECT()): -1); 

	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	
	Marques maMarque = (Marques)getListeMarque().get(indice);
	setMarqueCourant(maMarque);
	
//	 on remplit la liste déroulante avec la table f_modeles
	//	 Si liste des modeles pour une marque est vide
	ArrayList<Modeles> a = Modeles.listerModelesMarque(getTransaction(),maMarque.getCodemarque());
	setListeModele(a);
	//les élèments de la liste 
	int [] tailles = {15};
	String [] champs = {"designationmodele"};
	//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
	String [] padding = {"G"};
	
	setLB_MODELE(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
	
//	On nomme l'action pour afficher la liste
	addZone(getNOM_ST_MARQUE(),ACTION_MARQUE);	
	addZone(getNOM_LB_MODELE_SELECT(),"0");
	performPB_MODELE(request);
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODELE
 * Date de création : (25/04/07 13:45:32)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_MODELE() {
	return "NOM_PB_MODELE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (25/04/07 13:45:32)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_MODELE(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_MODELE_SELECT()) ? Integer.parseInt(getVAL_LB_MODELE_SELECT()): -1); 
		
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	Modeles monModele = (Modeles)getListeModele().get(indice);
	setModeleCourant(monModele);
	if (getTransaction().isErreur()){
		return false;
	}
	
	
	//Pour l'ajout ou la modification
	newModele = monModele.getCodemodele();
	if(!first){
		
		TYPEEQUIP monTe = TYPEEQUIP.chercherTYPEEQUIP(getTransaction(),monModele.getCodete());
		if(getTransaction().isErreur()){
			return false;
		}
		addZone(getNOM_ST_TYPE_EQUIP(), monTe.getDesignationte());
//		 on vérfie que le type de modèle est MT
		if(!("MT").equals(monTe.typete)){
			getTransaction().declarerErreur("Vous devez sélectionner un modèle de petit matériel.");
			return false;
		}
//		if ("MT".equals(monTe.getTypete().trim())){
//			addZone(getNOM_EF_PMSERIE(),getVAL_EF_PMINV());
//		}
		
		
		Marques.chercherMarques(getTransaction(),monModele.getCodemarque());
		if (getTransaction().isErreur()){
			return false;
		}
		//addZone(getNOM_ST_PE(),maMarque.getDesignationmarque().trim()+" "+ monModele.getDesignationmodele().trim());
	}
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (25/04/07 13:45:32)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (25/04/07 13:45:32)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODELE
 * Date de création : (25/04/07 13:45:32)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_MODELE() {
	return "NOM_ST_MODELE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODELE
 * Date de création : (25/04/07 13:45:32)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_MODELE() {
	return getZone(getNOM_ST_MODELE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DGARANTIE
 * Date de création : (25/04/07 13:45:32)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_DGARANTIE() {
	return "NOM_EF_DGARANTIE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DGARANTIE
 * Date de création : (25/04/07 13:45:32)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_DGARANTIE() {
	return getZone(getNOM_EF_DGARANTIE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_PRIX
 * Date de création : (25/04/07 13:45:32)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_PRIX() {
	return "NOM_EF_PRIX";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_PRIX
 * Date de création : (25/04/07 13:45:32)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_PRIX() {
	return getZone(getNOM_EF_PRIX());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE_EQUIP
 * Date de création : (25/04/07 13:46:02)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TYPE_EQUIP() {
	return "NOM_ST_TYPE_EQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE_EQUIP
 * Date de création : (25/04/07 13:46:02)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TYPE_EQUIP() {
	return getZone(getNOM_ST_TYPE_EQUIP());
}
/**
 * @return Renvoie listeMarque.
 */
private ArrayList<Marques> getListeMarque() {
	return listeMarque;
}
/**
 * @param listeMarque listeMarque à définir.
 */
private void setListeMarque(ArrayList<Marques> listeMarque) {
	this.listeMarque = listeMarque;
}
/**
 * @return Renvoie listeModele.
 */
private ArrayList<Modeles> getListeModele() {
	return listeModele;
}
/**
 * @param listeModele listeModele à définir.
 */
private void setListeModele(ArrayList<Modeles> listeModele) {
	this.listeModele = listeModele;
}
/**
 * @return Renvoie listeFre.
 */
private ArrayList<Fre_PM> getListeFre() {
	return listeFre;
}
/**
 * @param listeModele listeFre à définir.
 */
private void setListeFre(ArrayList<Fre_PM> listeFre) {
	this.listeFre = listeFre;
}
/**
 * @return Renvoie marqueCourant.
 */
private Marques getMarqueCourant() {
	return marqueCourant;
}
/**
 * @param marqueCourant marqueCourant à définir.
 */
private void setMarqueCourant(Marques marqueCourant) {
	this.marqueCourant = marqueCourant;
}
/**
 * @return Renvoie modeleCourant.
 */
private Modeles getModeleCourant() {
	return modeleCourant;
}
/**
 * @param modeleCourant modeleCourant à définir.
 */
private void setModeleCourant(Modeles modeleCourant) {
	this.modeleCourant = modeleCourant;
}

/**
 * @return Renvoie pMaterielCourant.
 */
private PMateriel getPMaterielCourant() {
	return pMaterielCourant;
}
/**
 * @param tequipCourant tequipCourant à définir.
 */
private void setPMaterielCourant(PMateriel pMaterielCourant) {
	this.pMaterielCourant = pMaterielCourant;
}
/**
 * @return Renvoie tequipCourant.
 */
private Fre_PM getFreCourant() {
	return freCourant;
}
/**
 * @param tequipCourant tequipCourant à définir.
 */
private void setFreCourant(Fre_PM freCourant) {
	this.freCourant = freCourant;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_FOURNISSEUR
 * Date de création : (25/04/07 14:01:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_FOURNISSEUR() {
	return "NOM_PB_FOURNISSEUR";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (25/04/07 14:01:38)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_FOURNISSEUR(javax.servlet.http.HttpServletRequest request) throws Exception {
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (25/04/07 14:19:29)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (25/04/07 14:19:29)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DACHAT
 * Date de création : (27/04/07 09:50:29)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_DACHAT() {
	return "NOM_EF_DACHAT";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DACHAT
 * Date de création : (27/04/07 09:50:29)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_DACHAT() {
	return getZone(getNOM_EF_DACHAT());
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (25/04/07 13:30:11)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_FOURNISSEUR
		if (testerParametre(request, getNOM_PB_FOURNISSEUR())) {
			return performPB_FOURNISSEUR(request);
		}

		//Si clic sur le bouton PB_MARQUE
		if (testerParametre(request, getNOM_PB_MARQUE())) {
			return performPB_MARQUE(request);
		}

		//Si clic sur le bouton PB_MODELE
		if (testerParametre(request, getNOM_PB_MODELE())) {
			return performPB_MODELE(request);
		}

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
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
 * Date de création : (27/04/07 09:51:37)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OePMateriel_MAJ.jsp";
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (27/04/07 09:51:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (27/04/07 09:51:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
}
