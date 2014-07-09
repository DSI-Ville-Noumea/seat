package nc.mairie.seat.metier;

import java.util.ArrayList;

import nc.mairie.seat.process.Outils;
import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier PeBase
 */
public class PeBase extends BasicMetier {
	public String codemodele;
	public String codeentretien;
	public String codeti;
	public String intervalle;
	public String duree;
	public String codetd;
	public String desactive;
	public String datedesactivation;
/**
* Renvoie une chaîne correspondant à la valeur de cet objet.
* @return une représentation sous forme de chaîne du destinataire
*/
@Override
public String toString() {
	// Insérez ici le code pour finaliser le destinataire
	// Cette implémentation transmet le message au super. Vous pouvez remplacer ou compléter le message.
	return super.toString();
}
/**
 * Retourne un ArrayList d'objet métier : PeBase.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<PeBase> listerPeBase(nc.mairie.technique.Transaction aTransaction) throws Exception{
	PeBase unPeBase = new PeBase();
	return unPeBase.getMyPeBaseBroker().listerPeBase(aTransaction);
}
/**
 * Retourne un PeBase.
 * @param aTransaction Transaction
 * @param mod mod
 * @param ent ent
 * @return PeBase
 * @throws Exception Exception
 */
public static PeBase chercherPeBase(nc.mairie.technique.Transaction aTransaction, String mod,String ent) throws Exception{
	PeBase unPeBase = new PeBase();
	return unPeBase.getMyPeBaseBroker().chercherPeBase(aTransaction, mod,ent);
}
/*vérifie que les champs sont bien renseignés
 * Date : 01/09/05
 * autheur : NC
 */
public boolean controleChamps(nc.mairie.technique.Transaction aTransaction )  throws Exception {
	if((getDuree()!=null)&&(!getDuree().equals(""))){
		setDuree(Outils.enleveEspace(getDuree()));
		if(!Services.estNumerique(getDuree())){
			aTransaction.declarerErreur("Vous devez saisir des chiffres pour la durée.");
			return false;
		}
	}
	if((getIntervalle()!=null)&&(!getIntervalle().equals(""))){
		setIntervalle(Outils.enleveEspace(getIntervalle()));
		if(!Services.estNumerique(getIntervalle())){
			aTransaction.declarerErreur("Vous devez saisir des chiffres pour l'intervalle.");
			return false;
		}
	}
	return true;
}
/*selon le compteur du modèle on autorise ou pas certains type d'intervalle pour un entretien.
* Date : 23/08/05
* autheur : Coralie Nicolas
*/
private boolean verifCompteur(nc.mairie.technique.Transaction aTransaction,Modeles unModele,TIntervalle unTypeInt)  throws Exception {
//	 on vérifie que les objets ne sont pas null
	if (null == unModele.getCodemodele()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Modeles"));
		return false;
	}
	if (null == unTypeInt.getCodeti()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Type d'intervalle"));
		return false;
	}
		
	//si le type de compteur de l'équipement est horaire : pas possible de sélectionné le type km
	// si le type de compteur de l'équipement est km : pas possible de sélectionné le type heure
	if(unModele.getCodecompteur().equals("1")){
		if(unTypeInt.getCodeti().equals("5")){
			aTransaction.declarerErreur("Le compteur de ce type d'équipement est kilométrique, il ne peut donc pas y avoir d'entretiens à l'heure.");
			return false;
		}
	}else if(unModele.getCodecompteur().equals("2")){
		if(unTypeInt.getCodeti().equals("1")){
			aTransaction.declarerErreur("Le compteur de ce type d'équipement est horaire, il ne peut donc pas y avoir d'entretiens au km.");
			return false;
		}
	}
return true;
}

/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @param unModele unModele
 * @param unEntretien unEntretien
 * @param unTIntervalle unTIntervalle
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerPeBase(nc.mairie.technique.Transaction aTransaction,Modeles unModele,Entretien unEntretien,TIntervalle unTIntervalle)  throws Exception {
	// on vérifie que les objets ne sont pas null
	if (null == unModele.getCodemodele()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Modèle"));
		return false;
	}
	if (null == unEntretien.getCodeentretien()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Entretien"));
		return false;
	}
	if (null == unTIntervalle.getCodeti()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","TIntervalle"));
		return false;
	}
	//RG : controle des champs
	controleChamps(aTransaction);
	if(aTransaction.isErreur()){
		return false;
	}
	// RG : controle du type d'intervalle pour le compteur
	if(!verifCompteur(aTransaction,unModele,unTIntervalle)){
		return false;
	}
	
	setCodemodele(unModele.getCodemodele());
	setCodeti(unTIntervalle.getCodeti());
	setCodeentretien(unEntretien.getCodeentretien());
	// si déjà enregistré
	if (!existePeBase(aTransaction,unModele.getCodemodele(),unEntretien.getCodeentretien())){
		//Creation du PeBase
		if (getMyPeBaseBroker().creerPeBase(aTransaction)){
			//on cherche les équipements rattachés à ce modèle pour mettre à jour leur peperso
			ArrayList<Equipement> aListEquip = Equipement.listerEquipementModele(aTransaction,getCodemodele());
			if (aTransaction.isErreur()){
				return false;
			}
			if (aListEquip.size()>0){
				for (int i = 0; i < aListEquip.size() ; i++) {
					Equipement aEquipement = (Equipement)aListEquip.get(i);	
					// contrôle l'enregistrement de l'entretien ds le PePerso
					ArrayList<PePerso> aListPePerso = PePerso.chercherPePersoEquipEnt(aTransaction,aEquipement.getNumeroinventaire(),getCodeentretien());
					if(aTransaction.isErreur()){
						return false;
					}
					// si aucun résultat
					if (aListPePerso.size()==0){
						// création du PePerso
						PePerso unPePerso = new PePerso();
						unPePerso.setIntervallepep(getIntervalle());
						unPePerso.setDuree(getDuree());
						TypeEntretien unTypeEntretien = new TypeEntretien();
						TIntervalle unTi = TIntervalle.chercherTIntervalle(aTransaction,getCodeti());
						if (aTransaction.isErreur()){
							return false;
						}
						unPePerso.creerPePerso(aTransaction,aEquipement,unEntretien,unTypeEntretien,unTi);
						if(aTransaction.isErreur()){
							return false;
						}
					}
				}
			}
		}
		if (aTransaction.isErreur()){
			return false;
		}
	}else{
		aTransaction.declarerErreur("L'entretien "+unEntretien.getLibelleentretien()+" est déjà enregistré pour le modèle"+unModele.getDesignationmodele());
		return false;
	}
	return true;
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @param unModele unModele
 * @param unEntretien unEntretien
 * @param unTIntervalle unTIntervalle
 * @return boolean
 * @throws Exception Exception
 */
public boolean modifierPeBase(nc.mairie.technique.Transaction aTransaction,Modeles unModele,Entretien unEntretien,TIntervalle unTIntervalle) throws Exception {
//	 on vérifie que les objets ne sont pas null
	if (null == unModele.getCodemodele()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Modèle"));
		return false;
	}else{
		setCodemodele(unModele.getCodemodele());
	}
	if (null == unEntretien.getCodeentretien()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Entretien"));
		return false;
	}else{
		setCodeentretien(unEntretien.getCodeentretien());
	}
	if (null == unTIntervalle.getCodeti()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","TIntervalle"));
		return false;
	}else{
		setCodeti(unTIntervalle.getCodeti());
	}
//	RG : controle des champs
	controleChamps(aTransaction);
	if(aTransaction.isErreur()){
		return false;
	}
//	 RG : controle du type d'intervalle pour le compteur
	if(!verifCompteur(aTransaction,unModele,unTIntervalle)){
		return false;
	}
//		Modification du PeBase
		return getMyPeBaseBroker().modifierPeBase(aTransaction);
	
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean supprimerPeBase(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'PeBase
	return getMyPeBaseBroker().supprimerPeBase(aTransaction);
}
	public String commentaire;
/**
 * Retourne un booléen.
 * Vérifie si existe
 * @param aTransaction Transaction
 * @param mod mod
 * @param ent ent
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existePeBase(nc.mairie.technique.Transaction aTransaction, String mod,String ent) throws Exception{
	PeBase unPeBase = new PeBase();
	return unPeBase.getMyPeBaseBroker().existePeBase(aTransaction, mod,ent);
}

/**
 * Getter de l'attribut codetd.
 */
/**
 * @return String
 */
public String getCodetd() {
	return codetd;
}
/**
 * Setter de l'attribut codetd.
 */
/**
 * @param newCodetd newCodetd
 */
public void setCodetd(String newCodetd) { 
	codetd = newCodetd;
}
/**
 * Retourne un booléen.
 * Vérifie que le modèle existe avec des paramètres de clé étrangères
 * @param aTransaction Transaction
 * @param param param
 * @return PeBase
 * @throws Exception Exception
 */
public boolean existePeBaseModele(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	PeBase unPeBase = new PeBase();
	return unPeBase.getMyPeBaseBroker().existePeBaseModele(aTransaction, param);
}
public boolean existePeBaseTint(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	PeBase unPeBase = new PeBase();
	return unPeBase.getMyPeBaseBroker().existePeBaseTint(aTransaction, param);
}

/**
 * Retourne un ArrayList d'objet métier : PeBase.
 * @param aTransaction Transaction
 * @param mod mod
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<PeBase> listerPeBaseModele(nc.mairie.technique.Transaction aTransaction,String mod) throws Exception{
	PeBase unPeBase = new PeBase();
	return unPeBase.getMyPeBaseBroker().listerPeBaseModele(aTransaction,mod);
}

/**
 * Retourne un ArrayList d'objet métier : PeBase.
 * @param aTransaction Transaction
 * @param mod mod
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<PeBase> listerPeBaseModeleActif(nc.mairie.technique.Transaction aTransaction,String mod) throws Exception{
	PeBase unPeBase = new PeBase();
	return unPeBase.getMyPeBaseBroker().listerPeBaseModeleActif(aTransaction,mod);
}
	
/**
 * Constructeur PeBase.
 */
public PeBase() {
	super();
}
/**
 * Getter de l'attribut codemodele.
 */
/**
 * @return String
 */
public String getCodemodele() {
	return codemodele;
}
/**
 * Setter de l'attribut codemodele.
 */
/**
 * @param newCodemodele newCodemodele
 */
public void setCodemodele(String newCodemodele) { 
	codemodele = newCodemodele;
}
/**
 * Getter de l'attribut codeentretien.
 */
/**
 * @return String
 */
public String getCodeentretien() {
	return codeentretien;
}
/**
 * Setter de l'attribut codeentretien.
 */
/**
 * @param newCodeentretien newCodeentretien
 */
public void setCodeentretien(String newCodeentretien) { 
	codeentretien = newCodeentretien;
}
/**
 * Getter de l'attribut codeti.
 */
/**
 * @return String
 */
public String getCodeti() {
	return codeti;
}
/**
 * Setter de l'attribut codeti.
 */
/**
 * @param newCodeti newCodeti
 */
public void setCodeti(String newCodeti) { 
	codeti = newCodeti;
}
/**
 * Getter de l'attribut intervalle.
 */
/**
 * @return String
 */
public String getIntervalle() {
	return intervalle;
}
/**
 * Setter de l'attribut intervalle.
 */
/**
 * @param newIntervalle newIntervalle
 */
public void setIntervalle(String newIntervalle) { 
	intervalle = newIntervalle;
}
/**
 * Getter de l'attribut duree.
 */
/**
 * @return String
 */
public String getDuree() {
	return duree;
}
/**
 * Setter de l'attribut duree.
 */
/**
 * @param newDuree newDuree
 */
public void setDuree(String newDuree) { 
	duree = newDuree;
}
/**
 * Getter de l'attribut commentaire.
 */
/**
 * @return String
 */
public String getCommentaire() {
	return commentaire;
}
/**
 * Setter de l'attribut commentaire.
 */
/**
 * @param newCommentaire newCommentaire
 */
public void setCommentaire(String newCommentaire) { 
	commentaire = newCommentaire;
}
/**
 * Getter de l'attribut desactive.
 */
/**
 * @return String
 */
public String getDesactive() {
	return desactive;
}
/**
 * Setter de l'attribut desactive.
 */
/**
 * @param newDesactive newDesactive
 */
public void setDesactive(String newDesactive) { 
	desactive = newDesactive;
}
/**
 * Getter de l'attribut datedesactivation.
 */
/**
 * @return String
 */
public String getDatedesactivation() {
	return datedesactivation;
}
/**
 * Setter de l'attribut datedesactivation.
 */
/**
 * @param newDatedesactivation newDatedesactivation
 */
public void setDatedesactivation(String newDatedesactivation) { 
	datedesactivation = newDatedesactivation;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new PeBaseBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected PeBaseBroker getMyPeBaseBroker() {
	return (PeBaseBroker)getMyBasicBroker();
}
}
