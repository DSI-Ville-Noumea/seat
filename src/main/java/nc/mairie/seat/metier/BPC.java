package nc.mairie.seat.metier;

import java.util.ArrayList;
import java.util.Calendar;

import nc.mairie.seat.process.Outils;
import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier BPC
 */
public class BPC extends BasicMetier {
	public String numerobpc;
	public String date;
	public String heure;
	public String valeurcompteur;
	public String numeropompe;
	public String quantite;
	public String modedeprise;
	public String numeroinventaire;

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
 * Retourne un ArrayList d'objet métier : BPC.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<BPC> listerBPC(nc.mairie.technique.Transaction aTransaction) throws Exception{
	BPC unBPC = new BPC();
	return unBPC.getMyBPCBroker().listerBPC(aTransaction);
}

/**
 * Retourne un booléen.
 * Vérifie que le bpc existe
 * @param aTransaction Transaction
 * @param param param
 * @return BPC
 * @throws Exception Exception
 */
public boolean existeBPC(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	BPC unBPC = new BPC();
	return unBPC.getMyBPCBroker().existeBPC(aTransaction, param);
}

/**
 * Retourne un BPC.
 * @param aTransaction Transaction
 * @param date date
 * @param inv inv
 * @return BPC
 * @throws Exception Exception
 */
public static BPC chercherBPCPrecEquipDate(nc.mairie.technique.Transaction aTransaction,String date, String inv) throws Exception{
	BPC unBPC = new BPC();
	return unBPC.getMyBPCBroker().chercherBPCPrecEquipDate(aTransaction,date, inv);
}

/**
 * Retourne un BPC.
 * @param aTransaction Transaction
 * @param date date
 * @param inv inv
 * @return BPC
 * @throws Exception Exception
 */
public static BPC chercherBPCDerOT(nc.mairie.technique.Transaction aTransaction, String date,String inv) throws Exception{
	BPC unBPC = new BPC();
	return unBPC.getMyBPCBroker().chercherBPCDerOT(aTransaction, date,inv);
}

/**
 * Retourne un BPC.
 * @param aTransaction Transaction
 * @param inv inv
 * @return BPC
 * @throws Exception Exception
 */
public static ArrayList<BPC> listerBPCInventaire(nc.mairie.technique.Transaction aTransaction, String inv) throws Exception{
	BPC unBPC = new BPC();
	return unBPC.getMyBPCBroker().listerBPCInventaire(aTransaction, inv);
}

/**
 * Retourne un BPC.
 * @param aTransaction Transaction
 * @param inv inv
 * @param annee annee
 * @return BPC
 * @throws Exception Exception
 */
public static ArrayList<BPC> listeBPCEquipAnnee(nc.mairie.technique.Transaction aTransaction, String inv,String annee) throws Exception{
	if(annee.equals("")){
		//on met l'année en cours
//		 récupération de l'année courante
		annee = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	}
	int anneeprec = Integer.parseInt(annee)-1;
	String datedeb = anneeprec+"-12-31";
	BPC unBPC = new BPC();
	return unBPC.getMyBPCBroker().listeBPCEquipAnnee(aTransaction, inv,datedeb);
}
/*vérifie que les champs sont bien renseignés
 * Date : 01/09/05
 * autheur : NC
 */
public boolean controleChamps(nc.mairie.technique.Transaction aTransaction )  throws Exception {
	if((getNumerobpc()!=null)&&(!getNumerobpc().equals(""))){
		setNumerobpc(Outils.enleveEspace(getNumerobpc()));
		if(!Services.estNumerique(getNumerobpc())){
			aTransaction.declarerErreur("Vous devez saisir des chiffres pour le numéro de BPC.");
			return false;
		}
	}
	if((getValeurcompteur()!=null)&&(!getValeurcompteur().equals(""))){
		setValeurcompteur(Outils.enleveEspace(getValeurcompteur()));
		if(!Services.estNumerique(getValeurcompteur())){
			aTransaction.declarerErreur("Vous devez saisir des chiffres pour le compteur.");
			return false;
		}
	}
	if((getQuantite()!=null)&&(!getQuantite().equals(""))){
		setQuantite(Outils.enleveEspace(getQuantite()));
		if(!Services.estNumerique(getQuantite())){
			aTransaction.declarerErreur("Vous devez saisir des chiffres pour la quantité.");
			return false;
		}
	}
	return true;
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @param unEquipement unEquipement
 * @param unModeles unModeles
 * @param razCompteur razCompteur
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerBPC(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement,Modeles unModeles, boolean razCompteur)  throws Exception {
	// on controle si null
	if (null == unEquipement.getNumeroinventaire()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return false;
	}
	if (null == unModeles.getCodemodele()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Modeles"));
		return false;
	}
	//RG : controle des champs
	controleChamps(aTransaction);
	if(aTransaction.isErreur()){
		return false;
	}
	//RG : on contrôle les dates
	if((getDate()!=null)&&(!getDate().equals(""))){
		if(!Services.estUneDate(getDate())){
			aTransaction.declarerErreur("La date n'est pas correcte.");
			return false;
		}else{
			setDate(Services.formateDate(getDate()));
		}
	}
	// on contrôle que la capacité du réservoir soit supérieure à 0 sinon pas de prise de carburant possible
	if(unModeles.getCapacitereservoir().equals("0")){
		aTransaction.declarerErreur("L'enregistrement du BPC n'est pas possible car la capacité du réservoir de cet équipement est de 0.");
		return false;
	}
	
	int controle = Services.compareDates(getDate(),unEquipement.getDatemiseencirculation());
	if (controle==-1){
		aTransaction.declarerErreur("La date de prise de carburant doit être supérieur ou égale à la date de mise en circulation.("+unEquipement.getDatemiseencirculation()+")");
		return false;
	}else if (controle==-9999){
		return false;
	}
	// contrôle sur la date BPC : ne doit pas être supérieure à la date du jour
	controle = Services.compareDates(getDate(),Services.dateDuJour());
	if (controle==1){
		aTransaction.declarerErreur("La date de prise de carburant ne doit pas être supérieur à la date du jour.");
		return false;
	}else if (controle==-9999){
		return false;
	}
	
	// si pas de RAZ compteur
		if (! razCompteur) {
		// on cherche le bpc précédent pour controler la valeur du compteur
		BPC bpcAvant = chercherBPCPrecEquipDate(aTransaction,getDate(),unEquipement.getNumeroinventaire());
		if(aTransaction.isErreur()){
			aTransaction.traiterErreur();
		}else{
	//		 RG : si on a un bpc précédent
			if(bpcAvant!=null){
				//	on contrôle que la valeur du compteur est bien supérieur à la valeur précédente
				if (Integer.parseInt(getValeurcompteur())<Integer.parseInt(bpcAvant.getValeurcompteur())){
					aTransaction.declarerErreur("Attention. La valeur du compteur est inférieur à la valeur précédente ("+bpcAvant.getValeurcompteur()+").");
					return false;
				}
			}
		}
	
		//	 on vérifie qu'on a un bpc suivant pour controler la valeur du compteur
		BPC bpcSuiv = chercherBPCSuivEquipDate(aTransaction,getDate(),unEquipement.getNumeroinventaire());
		if(aTransaction.isErreur()){
			aTransaction.traiterErreur();
		}else{
			// RG : si on a un bpc suivant
			if(bpcSuiv!=null){
				//	on contrôle que la valeur du compteur est bien inférieur à la valeur suivante
				if (Integer.parseInt(getValeurcompteur())>Integer.parseInt(bpcSuiv.getValeurcompteur())){
					aTransaction.declarerErreur("Attention. La valeur du compteur est supérieure à la valeur suivante ("+bpcSuiv.getValeurcompteur()+").");
					return false;
				}
			}
		}
	}
	
	// on controle si la quantité prise est supérieur au max du modèle
	int quantite = Integer.parseInt(getQuantite());
	int capacite = Integer.parseInt(unModeles.getCapacitereservoir());
	if (quantite>capacite){
		aTransaction.declarerErreur("Attention. La quantité enregistré pour le BPC n°"+ getNumerobpc()+" est supérieur à la capacité du réservoir.("+capacite+")"); 
		return false;
	}
	//Creation du BPC
	getMyBPCBroker().creerBPC(aTransaction);
	if(aTransaction.isErreur()){
		return false;
	}
	return true;
}

/*
 * méthode pour la création avec PMateriel
 */
public boolean creerBPC(nc.mairie.technique.Transaction aTransaction,PMateriel unPMateriel,Modeles unModeles)  throws Exception {
	// on controle si null
	if (null == unPMateriel.getPminv()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Petit matériel"));
		return false;
	}
	if (null == unModeles.getCodemodele()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Modeles"));
		return false;
	}
	//RG : controle des champs
	controleChamps(aTransaction);
	if(aTransaction.isErreur()){
		return false;
	}
	//RG : on contrôle les dates
	if((getDate()!=null)&&(!getDate().equals(""))){
		if(!Services.estUneDate(getDate())){
			aTransaction.declarerErreur("La date n'est pas correcte.");
			return false;
		}else{
			setDate(Services.formateDate(getDate()));
		}
	}
	// on contrôle que la capacité du réservoir soit supérieure à 0 sinon pas de prise de carburant possible
	if(unModeles.getCapacitereservoir().equals("0")){
		aTransaction.declarerErreur("L'enregistrement du BPC n'est pas possible car la capacité du réservoir de cet équipement est de 0.");
		return false;
	}
	
	int controle = Services.compareDates(getDate(),unPMateriel.getDmes());
	if (controle==-1){
		aTransaction.declarerErreur("La date de prise de carburant doit être supérieur ou égale à la date de mise en circulation.("+unPMateriel.getDmes()+")");
		return false;
	}else if (controle==-9999){
		return false;
	}
	// contrôle sur la date BPC : ne doit pas être supérieure à la date du jour
	controle = Services.compareDates(getDate(),Services.dateDuJour());
	if (controle==1){
		aTransaction.declarerErreur("La date de prise de carburant ne doit pas être supérieur à la date du jour.");
		return false;
	}else if (controle==-9999){
		return false;
	}
	
	// on controle si la quantité prise est supérieur au max du modèle
	int quantite = Integer.parseInt(getQuantite());
	int capacite = Integer.parseInt(unModeles.getCapacitereservoir());
	if (quantite>capacite){
		aTransaction.declarerErreur("Attention. La quantité enregistré pour le BPC n°"+ getNumerobpc()+" est supérieur à la capacité du réservoir.("+capacite+")"); 
		return false;
	}
	//Creation du BPC
	getMyBPCBroker().creerBPC(aTransaction);
	if(aTransaction.isErreur()){
		return false;
	}
	return true;
}

/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @param unEquipement unEquipement
 * @param bpcAvant bpcAvant
 * @param unModeles unModeles
 * @param raz raz
 * @return boolean 
 * @throws Exception Exception
 */
public boolean modifierBPC(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement,BPC bpcAvant,Modeles unModeles, boolean raz)  throws Exception {
//	 on controle si null
	if (null == unEquipement.getNumeroinventaire()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return false;
	}
	if (null == unModeles.getCodemodele()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Modeles"));
		return false;
	}
//	RG : controle des champs
	controleChamps(aTransaction);
	if(aTransaction.isErreur()){
		return false;
	}
	if((getDate()!=null)&&(!getDate().equals(""))){
		if(!Services.estUneDate(getDate())){
			aTransaction.declarerErreur("La date n'est pas correcte.");
			return false;
		}else{
			setDate(Services.formateDate(getDate()));
		}
	}
	// on contrôle les dates
	int controle = Services.compareDates(getDate(),unEquipement.getDatemiseencirculation());
	if (controle==-1){
		aTransaction.declarerErreur("La date de prise de carburant doit être supérieur ou égale à la date de mise en circulation.("+unEquipement.getDatemiseencirculation()+")");
		return false;
	}else if (controle==-9999){
		return false;
	}
	
//	 contrôle sur la date BPC : ne doit pas être supérieure à la date du jour
	controle = Services.compareDates(getDate(),Services.dateDuJour());
	if (controle==1){
		aTransaction.declarerErreur("La date de prise de carburant ne doit pas être supérieur à la date du jour.");
		return false;
	}else if (controle==-9999){
		return false;
	}
	
	if (null!=bpcAvant && !raz){
		// on contrôle que la valeur du compteur est bien supérieur à la valeur précédente
		if (Integer.parseInt(getValeurcompteur())<Integer.parseInt(bpcAvant.getValeurcompteur())){
			aTransaction.declarerErreur("Attention. La valeur du compteur est inférieur à la valeur précédente ("+bpcAvant.getValeurcompteur()+").");
			return false;
		}		
	}
	
	// on controle si la quantité prise est supérieur au max du modèle
	int quantite = Integer.parseInt(getQuantite());
	int capacite = Integer.parseInt(unModeles.getCapacitereservoir());
	if (quantite>capacite){
		aTransaction.declarerErreur("Attention. La quantité enregistré pour le BPC n°"+ getNumerobpc()+" est supérieur à la capacité du réservoir.("+capacite+")");
	}
	//Modification du BPC
	return getMyBPCBroker().modifierBPC(aTransaction);
}

public boolean modifierBPC(nc.mairie.technique.Transaction aTransaction,PMateriel unPMateriel,BPC bpcAvant,Modeles unModeles)  throws Exception {
//	 on controle si null
	if (null == unPMateriel.getPminv()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Petit matériel"));
		return false;
	}
	if (null == unModeles.getCodemodele()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Modeles"));
		return false;
	}
//	RG : controle des champs
	controleChamps(aTransaction);
	if(aTransaction.isErreur()){
		return false;
	}
	if((getDate()!=null)&&(!getDate().equals(""))){
		if(!Services.estUneDate(getDate())){
			aTransaction.declarerErreur("La date n'est pas correcte.");
			return false;
		}else{
			setDate(Services.formateDate(getDate()));
		}
	}
	// on contrôle les dates
	int controle = Services.compareDates(getDate(),unPMateriel.getDmes());
	if (controle==-1){
		aTransaction.declarerErreur("La date de prise de carburant doit être supérieur ou égale à la date de mise en circulation.("+unPMateriel.getDmes()+")");
		return false;
	}else if (controle==-9999){
		return false;
	}
	
//	 contrôle sur la date BPC : ne doit pas être supérieure à la date du jour
	controle = Services.compareDates(getDate(),Services.dateDuJour());
	if (controle==1){
		aTransaction.declarerErreur("La date de prise de carburant ne doit pas être supérieur à la date du jour.");
		return false;
	}else if (controle==-9999){
		return false;
	}
		
	// on controle si la quantité prise est supérieur au max du modèle
	int quantite = Integer.parseInt(getQuantite());
	int capacite = Integer.parseInt(unModeles.getCapacitereservoir());
	if (quantite>capacite){
		aTransaction.declarerErreur("Attention. La quantité enregistré pour le BPC n°"+ getNumerobpc()+" est supérieur à la capacité du réservoir.("+capacite+")");
	}
	//Modification du BPC
	return getMyBPCBroker().modifierBPC(aTransaction);
}

/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws Exception Exception
 */
public boolean supprimerBPC(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'BPC
	return getMyBPCBroker().supprimerBPC(aTransaction);
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvBPC(nc.mairie.technique.Transaction aTransaction) throws Exception{
	BPC unBPC = new BPC();
	//	recherche du dernier numerobpc
	int nouvnumerobpc = unBPC.getMyBPCBroker().nouvBPC(aTransaction);
	
	//si pas trouvé
	if (nouvnumerobpc == -1) {
		//fonctionnellement normal: table vide
		nouvnumerobpc = 1;
	} else {
		nouvnumerobpc++;
	}
	
	return nouvnumerobpc;
}

/**
 * Retourne un ArrayList d'objet métier : BPC.
 * @param aTransaction Transaction
 * @param cle cle
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<BPC> listerBPCEquipement(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception{
	BPC unBPC = new BPC();
	return unBPC.getMyBPCBroker().listerBPCEquipement(aTransaction,cle);
}

/**
 * Constructeur BPC.
 */
public BPC() {
	super();
}
/**
 * Getter de l'attribut numerobpc.
 * @return String
 */
public String getNumerobpc() {
	return numerobpc;
}
/**
 * Setter de l'attribut numerobpc.
 * @param newNumerobpc newNumerobpc
 */
public void setNumerobpc(String newNumerobpc) { 
	numerobpc = newNumerobpc;
}
/**
 * Getter de l'attribut date.
 * @return String
 */
public String getDate() {
	return date;
}
/**
 * Setter de l'attribut date.
 * @param newDate newDate
 */
public void setDate(String newDate) { 
	date = newDate;
}
/**
 * Getter de l'attribut heure.
 * @return String
 */
public String getHeure() {
	return heure;
}
/**
 * Setter de l'attribut heure.
 * @param newHeure newHeure
 */
public void setHeure(String newHeure) { 
	heure = newHeure;
}
/**
 * Getter de l'attribut valeurcompteur.
 * @return String
 */
public String getValeurcompteur() {
	return valeurcompteur;
}
/**
 * Setter de l'attribut valeurcompteur.
 * @param newValeurcompteur newValeurcompteur
 */
public void setValeurcompteur(String newValeurcompteur) { 
	valeurcompteur = newValeurcompteur;
}
/**
 * Getter de l'attribut numeropompe.
 * @return String
 */
public String getNumeropompe() {
	return numeropompe;
}
/**
 * Setter de l'attribut numeropompe.
 * @param newNumeropompe newNumeropompe
 */
public void setNumeropompe(String newNumeropompe) { 
	numeropompe = newNumeropompe;
}
/**
 * Getter de l'attribut quantite.
 * @return String
 */
public String getQuantite() {
	return quantite;
}
/**
 * Setter de l'attribut quantite.
 * @param newQuantite newQuantite
 */
public void setQuantite(String newQuantite) { 
	quantite = newQuantite;
}
/**
 * Getter de l'attribut modedeprise.
 * @return String
 */
public String getModedeprise() {
	return modedeprise;
}
/**
 * Setter de l'attribut modedeprise.
 * @param newModedeprise newModedeprise
 */
public void setModedeprise(String newModedeprise) { 
	modedeprise = newModedeprise;
}
/**
 * Getter de l'attribut numeroinventaire.
 * @return String
 */
public String getNumeroinventaire() {
	return numeroinventaire;
}
/**
 * Setter de l'attribut numeroinventaire.
 * @param newNumeroinventaire newNumeroinventaire
 */
public void setNumeroinventaire(String newNumeroinventaire) { 
	numeroinventaire = newNumeroinventaire;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new BPCBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected BPCBroker getMyBPCBroker() {
	return (BPCBroker)getMyBasicBroker();
}

/**
 * Retourne un booléen.
 * Vérifie que le modèle existe avec des paramètres de clé étrangères
 * @param aTransaction Transaction
 * @param param param
 * @return BPC
 * @throws Exception Exception
 */
public boolean existeBPCEquip(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	BPC unBPC = new BPC();
	return unBPC.getMyBPCBroker().existeBPCEquip(aTransaction, param);
}
public boolean existeBPCModePrise(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	BPC unBPC = new BPC();
	return unBPC.getMyBPCBroker().existeBPCModePrise(aTransaction, param);
}
/**
 * Retourne un BPC.
 * @param aTransaction Transaction
 * @param date date
 * @param inv inv
 * @return BPC
 * @throws Exception Exception
 */
public static BPC chercherBPCSuivEquipDate(nc.mairie.technique.Transaction aTransaction, String date,String inv) throws Exception{
	BPC unBPC = new BPC();
	return unBPC.getMyBPCBroker().chercherBPCSuivEquipDate(aTransaction, date,inv);
}

/**
 * Retourne un BPC.
 * @param aTransaction Transaction
 * @param nobpc nobpc
 * @return BPC
 * @throws Exception Exception
 */
public static BPC chercherBPC(nc.mairie.technique.Transaction aTransaction, String nobpc) throws Exception{
	BPC unBPC = new BPC();
	return unBPC.getMyBPCBroker().chercherBPC(aTransaction, nobpc);
}

public boolean existeBPCPompes(nc.mairie.technique.Transaction aTransaction, String numPompe) throws Exception{
	BPC unBPC = new BPC();
	return unBPC.getMyBPCBroker().existeBPCPompe(aTransaction,numPompe);
}
public static ArrayList<BPC> listerBPCParams(nc.mairie.technique.Transaction aTransaction,String inv,String ddeb,String dfin) throws Exception{
	String periode = "";
	if(inv.equals("")){
		inv="";
	}else{
		inv = "numeroinventaire like '"+inv+"%' ";
	}
	if(ddeb.equals("")&&(dfin.equals(""))){
		periode = "";
	}
	if(!ddeb.equals("")&&(!dfin.equals(""))){
		ddeb = Services.formateDateInternationale(ddeb);
		dfin = Services.formateDateInternationale(dfin);
		periode = " date<='"+dfin+"' and date>='"+ddeb+"' ";
	}
	if(!ddeb.equals("")&&(dfin.equals(""))){
		ddeb = Services.formateDateInternationale(ddeb);
		periode = " date='"+ddeb+"' ";
	}
	if(ddeb.equals("")&&(!dfin.equals(""))){
		dfin = Services.formateDateInternationale(dfin);
		periode = " date='"+dfin+"' ";
	}
	
	BPC unBPC = new BPC();
	return unBPC.getMyBPCBroker().listerBPCParams(aTransaction,inv,periode);
}

}
