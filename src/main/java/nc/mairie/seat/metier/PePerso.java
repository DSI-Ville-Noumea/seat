package nc.mairie.seat.metier;

import java.util.ArrayList;
import java.util.Calendar;

import nc.mairie.seat.process.Outils;
import nc.mairie.technique.Services;
/**
 * Objet métier PePerso
 */
public class PePerso extends nc.mairie.technique.BasicMetier {
	public String codeequip;
	public String codeentretien;
	public String sinistre;
	public String duree;
	public String codeot;
	public String codetypeent;
	public String commentairete;
	public String codeti;
	public String intervallepep;
	public String codepep;
	public String datereal;
	public String dateprev;

/**
* Renvoie une chaîne correspondant à la valeur de cet objet.
* @return une représentation sous forme de chaîne du destinataire
*/
public String toString() {
	// Insérez ici le code pour finaliser le destinataire
	// Cette implémentation transmet le message au super. Vous pouvez remplacer ou compléter le message.
	return super.toString();
}
/**
 * Retourne un ArrayList d'objet métier : PePerso.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerPePerso(nc.mairie.technique.Transaction aTransaction) throws Exception{
	PePerso unPePerso = new PePerso();
	return unPePerso.getMyPePersoBroker().listerPePerso(aTransaction);
}

public static java.util.ArrayList listerPePersoEquipMoinsUnAn(nc.mairie.technique.Transaction aTransaction,String pdate) throws Exception{
	PePerso unPePerso = new PePerso();
	return unPePerso.getMyPePersoBroker().listerPePersoEquipMoinsUnAn(aTransaction,pdate);
}

/**
 * Retourne un ArrayList d'objet métier : PePerso.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerPePersoDecl(nc.mairie.technique.Transaction aTransaction,String decl) throws Exception{
	PePerso unPePerso = new PePerso();
	return unPePerso.getMyPePersoBroker().listerPePersoDecl(aTransaction,decl);
}

/**
 * Retourne un ArrayList d'objet métier : PePerso.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerPePersoRecond(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception{
	PePerso unPePerso = new PePerso();
	return unPePerso.getMyPePersoBroker().listerPePersoRecond(aTransaction,inv);
}

/**
 * Retourne un ArrayList d'objet métier : PePerso.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerPePersoKm(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception{
	PePerso unPePerso = new PePerso();
	return unPePerso.getMyPePersoBroker().listerPePersoKm(aTransaction,inv);
}

/**
 * Retourne un ArrayList d'objet métier : PePerso.
 * @return java.util.ArrayList
 */
//UNUSED: comment by OFONTENEAU 20090313
//public static java.util.ArrayList listerPePersoAFaireKm(nc.mairie.technique.Transaction aTransaction,String mod,String inv) throws Exception{
//	PePerso unPePerso = new PePerso();
//	return unPePerso.getMyPePersoBroker().listerPePersoAFaireKm(aTransaction,mod,inv);
//}

/**
 * Retourne un ArrayList d'objet métier : PePerso.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerPePersoHr(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception{
	PePerso unPePerso = new PePerso();
	return unPePerso.getMyPePersoBroker().listerPePersoHr(aTransaction,inv);
}


/**
 * Retourne un ArrayList d'objet métier : PePerso.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList chercherPePersoEquipEnt(nc.mairie.technique.Transaction aTransaction,String inv,String ent) throws Exception{
	PePerso unPePerso = new PePerso();
	return unPePerso.getMyPePersoBroker().chercherPePersoEquipEnt(aTransaction,inv,ent);
}

public static java.util.ArrayList listerPePersoEquip(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception{
	PePerso unPePerso = new PePerso();
	return unPePerso.getMyPePersoBroker().listerPePersoEquip(aTransaction,inv);
}


/**
 * Retourne un ArrayList d'objet métier : PePerso.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList chercherPePersoOT(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception{
	PePerso unPePerso = new PePerso();
	return unPePerso.getMyPePersoBroker().chercherPePersoOT(aTransaction,numot);
}

/**
* Retourne un ArrayList d'objet métier : PePerso.
* @return java.util.ArrayList
*/
public static java.util.ArrayList chercherPePersoPasFaitOT(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception{
	PePerso unPePerso = new PePerso();
	return unPePerso.getMyPePersoBroker().chercherPePersoPasFaitOT(aTransaction,numot);
}

/**
 * Retourne un ArrayList d'objet métier : PePerso.
 * @return java.util.ArrayList
 */
public static PePerso chercherPePersoEquipEntRealise(nc.mairie.technique.Transaction aTransaction,String inv,String ent) throws Exception{
	PePerso unPePerso = new PePerso();
	return unPePerso.getMyPePersoBroker().chercherPePersoEquipEntRealise(aTransaction,inv,ent);
}

/**
 * Retourne un ArrayList d'objet métier : PePerso.
 * @return java.util.ArrayList
 */
public static PePerso chercherPePersoEquipEntPrevu(nc.mairie.technique.Transaction aTransaction,String inv,String ent) throws Exception{
	PePerso unPePerso = new PePerso();
	return unPePerso.getMyPePersoBroker().chercherPePersoEquipEntPrevu(aTransaction,inv,ent);
}

/**
 * Retourne un PePerso.
 * @return PePerso
 */
public static PePerso chercherPePerso(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PePerso unPePerso = new PePerso();
	return unPePerso.getMyPePersoBroker().chercherPePerso(aTransaction, code);
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
	if((getIntervallepep()!=null)&&(!getIntervallepep().equals(""))){
		setIntervallepep(Outils.enleveEspace(getIntervallepep()));
		if(!Services.estNumerique(getIntervallepep())){
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
private boolean verifCompteur(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement,TIntervalle unTypeInt)  throws Exception {
//	 on vérifie que les objets ne sont pas null
	if (null == unEquipement.getNumeroinventaire()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return false;
	}
	if (null == unTypeInt.getCodeti()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Type d'intervalle"));
		return false;
	}
	
	// on cherche le modèle correspondant
	Modeles unModele = Modeles.chercherModeles(aTransaction,unEquipement.getCodemodele());
	if(aTransaction.isErreur()){
		return false;
	}
//	 si le type de compteur de l'équipement est horaire : pas possible de sélectionné le type km
	// si le type de compteur de l'équipement est km : pas possible de sélectionné le type heure
	if(unModele.getCodecompteur().equals("1")){
		if(unTypeInt.getCodeti().equals("5")){
			aTransaction.declarerErreur("Le compteur de cet équipement est kilométrique, il ne peut donc pas y avoir d'entretiens à l'heure.");
			return false;
		}
	}else if(unModele.getCodecompteur().equals("2")){
		if(unTypeInt.getCodeti().equals("1")){
			aTransaction.declarerErreur("Le compteur de cet équipement est horaire, il ne peut donc pas y avoir d'entretiens au km.");
			return false;
		}
	}
	return true;
}



/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */
public boolean creerPePerso(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement,Entretien unEntretien,TypeEntretien unTe,TIntervalle unTypeInt)  throws Exception {
//	 on vérifie que les objets ne sont pas null
	if (null == unEquipement.getNumeroinventaire()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return false;
	}
	if (null == unEntretien.getCodeentretien()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Entretien"));
		return false;
	}
	if (null == unTe.getCodetypeent()){
		//aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Type d'entretien"));
		//return false;
		// par défaut on met le type de base
		unTe = TypeEntretien.chercherTypeEntretien(aTransaction,"1");
		if (aTransaction.isErreur()){
			return false;
		}
	}
	if (null == unTypeInt.getCodeti()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Type d'intervalle"));
		return false;
	}
//	RG : controle des champs
	controleChamps(aTransaction);
	if(aTransaction.isErreur()){
		return false;
	}
	
	// si la date de réalisation est renseignée alors le compteur de l'OT rattaché doit être renseignée
	if((getDatereal()!=null)&&(!getDatereal().equals(("")))){
		if((getCodeot()!=null)&&(!getCodeot().equals(""))){
			OT unOT = OT.chercherOT(aTransaction,getCodeot());
			if(aTransaction.isErreur()){
				return false;
			}
			Modeles unModele = Modeles.chercherModeles(aTransaction,unEquipement.getCodemodele());
			if(aTransaction.isErreur()){
				aTransaction.declarerErreur("Le modèle n'a pas été trouvé.");
				return false;
			}
			TYPEEQUIP unTequip = TYPEEQUIP.chercherTYPEEQUIP(aTransaction,unModele.getCodete());
			if(aTransaction.isErreur()){
				aTransaction.declarerErreur("Le type d'équipement n'a pas été trouvé.");
				return false;
			}
			// le compteur est obligatoire
			if(!("MT").equals(unTequip.getTypete())){
				if(unOT.getCompteur()==null||unOT.getCompteur().equals("")){
					aTransaction.declarerErreur("Le compteur de l'OT doit être renseigné.");
					return false;
				}
			}
		}
	}
	
	//RG : controle si les dates sont correctes
	//RG : la date de prévision et de réalisation doivent être supérieures ou égales à la date de mise en circulation de l'équipement
//	RG : controle de l'année (l'année saisie ne doit pas être supérieur à l'année en cours)
	String annee = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	String dateAnnee = Services.formateDate("31/12/"+annee);
	if((getDateprev()!=null)&&(!getDateprev().equals(""))){
		if(!Services.estUneDate(getDateprev())){
			aTransaction.declarerErreur("La date de prévision n'est pas correcte");
			return false;
		}else{
			setDateprev(Services.formateDate(getDateprev()));
		}
		if((unEquipement.getDatemiseencirculation()!=null)&&(!unEquipement.getDatemiseencirculation().equals(""))){
			int compare = Services.compareDates(unEquipement.getDatemiseencirculation(),getDateprev());
			if(compare==1){
				aTransaction.declarerErreur("La date de prévision doit être supérieure ou égale à la date de mise en circulation.");
				return false;
			}else if (compare==-9999){
				return false;
			}
		}
		//Services.convertitDate(Services.formateDate(getDateentree()),"dd/mm/yyyy","yyyy");
		String date = Services.formateDate(getDateprev());
		int compare = Services.compareDates(date,dateAnnee);
		if(compare==1){
			aTransaction.declarerErreur("La date de prévision ne doit pas être supérieur au "+dateAnnee);
			return false;
		}else if(compare==-9999){
			return false;
		}
	}
	if((getDatereal()!=null)&&(!getDatereal().equals(""))&&(!getDatereal().equals(("01/01/0001")))){
		if(!Services.estUneDate(getDatereal())){
			aTransaction.declarerErreur("La date de réalisation n'est pas correcte.");
			return false;
		}else{
			setDatereal(Services.formateDate(getDatereal()));
		}
		if((unEquipement.getDatemiseencirculation()!=null)&&(!unEquipement.getDatemiseencirculation().equals(""))){
			int compare = Services.compareDates(unEquipement.getDatemiseencirculation(),getDatereal());
			if(compare==1){
				aTransaction.declarerErreur("La date de réalisation doit être supérieure ou égale à la date de mise en circulation.");
				return false;
			}else if (compare==-9999){
				return false;
			}
		}
//		si unOT rattaché à l'entretien à faire
		if((getCodeot()!=null)&&(getCodeot().equals(""))){
			OT unOT = OT.chercherOT(aTransaction,getCodeot());
			if(aTransaction.isErreur()){
				return false;
			}
			// la date de réalisation <= date de sortie de l'équipement
			if((unOT.getDateentree()!=null)&&(!unOT.getDatesortie().equals(""))){
				int compare = Services.compareDates(unOT.getDateentree(),getDatereal());
				if(compare==1){
					aTransaction.declarerErreur("La date de réalisation doit être supérieure ou égale à la date d'entrée de l'équipement.");
					return false;
				}else if(compare==-9999){
					return false;
				}
			}
			//la date de réalisation <= date de sortie de l'équipement
			if((unOT.getDatesortie()!=null)&&(!unOT.getDatesortie().equals(""))){
				int compare = Services.compareDates(getDatereal(),unOT.getDatesortie());
				if(compare==1){
					aTransaction.declarerErreur("La date de réalisation doit être inférieur ou égale à la date de sortie de l'équipement.");
					return false;
				}else if(compare==-9999){
					return false;
				}
			}
		}
		String date = Services.formateDate(getDatereal());
		int compare = Services.compareDates(date,dateAnnee);
		if(compare==1){
			aTransaction.declarerErreur("La date de réalisation ne doit pas être supérieur au "+dateAnnee);
			return false;
		}else if(compare==-9999){
			return false;
		}
	}
	
	//RG : controle du compteur
	if(!verifCompteur(aTransaction,unEquipement,unTypeInt)){
		return false;
	}
	
	// si sinistre pas renseigné alors non
	if (getSinistre()==null){
		setSinistre("non");
	}
	//calcul de la date de prévision
	int nbjours = 0; 
	//si le type est occasionnel la date prev est la date du jour
	if (unTe.getCodetypeent().trim().equals("2")){
		String date = Services.dateDuJour();
		setDateprev(date);
		//AJOUT OFONTENEAU 20090403 4=SANS INTERVALLE
		setCodeti("4");
		setIntervallepep(null);
	}else{
		// 1 : km, 4:sans, 2:annee, 3:jours,5:horaire
		//AJOUT OFONTENEAU 20090414  6: semestriel, 8:10000km/dateanniv
		if((!unTypeInt.getCodeti().trim().equals("1"))&&(!unTypeInt.getCodeti().trim().equals("4"))&&(!unTypeInt.getCodeti().trim().equals("5"))){
			if(unTypeInt.getCodeti().trim().equals("2")){
				int nbannee = Integer.parseInt(getIntervallepep());
				nbjours = nbannee * 360;
			}else if(unTypeInt.getCodeti().trim().equals("3")){
				nbjours = Integer.parseInt(getIntervallepep());
			}else if(unTypeInt.getCodeti().trim().equals("6")){
				int nbsemes = Integer.parseInt(getIntervallepep());
				nbjours = nbsemes * 182;
			}else if(unTypeInt.getCodeti().trim().equals("8")){
				nbjours = 365;
			}
	//		on regarde si l'entretien est une reconduction
			ArrayList aList = chercherPePersoEquipEnt(aTransaction,unEquipement.getNumeroinventaire(),unEntretien.getCodeentretien());
			if (aList.size()==0){
				aTransaction.traiterErreur();
				setDateprev(Services.ajouteJours(unEquipement.getDatemiseencirculation(),nbjours));
			}else{
				int code = maxPePersoEquipEnt(aTransaction,unEquipement.getNumeroinventaire(),unEntretien.getCodeentretien());
				PePerso unPePerso = chercherPePerso(aTransaction,String.valueOf(code));
				setDateprev(Services.ajouteJours(getDatereal(),nbjours));
			}
		}
		//AJOUT OFONTENEAU 20090403
		setCodeti(unTypeInt.getCodeti());
		
	}
	
	
	setCodeequip(unEquipement.getNumeroinventaire());
	setCodeentretien(unEntretien.getCodeentretien());
	setCodetypeent(unTe.getCodetypeent());
	//COMMENT BY OFONTENEAU 20090403
	//setCodeti(unTypeInt.getCodeti());
	//	on ajoute le code du peperso
	int nouvcodepep = nouvPePerso(aTransaction);
	setCodepep(String.valueOf(nouvcodepep));
	//	Creation du PePerso
	return getMyPePersoBroker().creerPePerso(aTransaction);
	
	
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierPePerso(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement,Entretien unEntretien,TypeEntretien unTe,TIntervalle unTypeInt)  throws Exception {
//	 on vérifie que les objets ne sont pas null
	if (null == unEquipement.getNumeroinventaire()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return false;
	}
	if (null == unEntretien.getCodeentretien()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Entretien"));
		return false;
	}
	if (null == unTe.getCodetypeent()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Type d'entretien"));
		return false;
	}
	if (null == unTypeInt.getCodeti()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Type d'intervalle"));
		return false;
	}
//	RG : controle des champs
	controleChamps(aTransaction);
	if(aTransaction.isErreur()){
		return false;
	}
//	RG : controle si les dates sont correctes
	//RG : la date de prévision et de réalisation doivent être supérieures ou égales à la date de mise en circulation de l'équipement
//	RG : controle de l'année (l'année saisie ne doit pas être supérieur à l'année en cours)
	String annee = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	String dateAnnee = Services.formateDate("31/12/"+annee);
	if((getDateprev()!=null)&&(!getDateprev().equals(""))&&(!getDateprev().equals("01/01/0001"))){
		if(!Services.estUneDate(getDateprev())){
			aTransaction.declarerErreur("La date de prévision n'est pas correcte");
			return false;
		}
		if((unEquipement.getDatemiseencirculation()!=null)&&(!unEquipement.getDatemiseencirculation().equals(""))){
			int compare = Services.compareDates(unEquipement.getDatemiseencirculation(),getDateprev());
			if(compare==1){
				aTransaction.declarerErreur("La date de prévision doit être supérieure ou égale à la date de mise en circulation.");
				return false;
			}else if (compare==-9999){
				return false;
			}
		}
		//Services.convertitDate(Services.formateDate(getDateentree()),"dd/mm/yyyy","yyyy");
		String date = Services.formateDate(getDateprev());
		int compare = Services.compareDates(date,dateAnnee);
		if(compare==1){
			aTransaction.declarerErreur("La date de prévision ne doit pas être supérieur au "+dateAnnee);
			return false;
		}else if(compare==-9999){
			return false;
		}
	}
	if((getDatereal()!=null)&&(!getDatereal().equals(""))&&(!getDatereal().equals(("01/01/0001")))){
		if(!Services.estUneDate(getDatereal())){
			aTransaction.declarerErreur("La date de réalisation n'est pas correcte.");
			return false;
		}
		if((unEquipement.getDatemiseencirculation()!=null)&&(!unEquipement.getDatemiseencirculation().equals(""))){
			int compare = Services.compareDates(unEquipement.getDatemiseencirculation(),getDatereal());
			if(compare==1){
				aTransaction.declarerErreur("La date de réalisation doit être supérieure ou égale à la date de mise en circulation.");
				return false;
			}else if (compare==-9999){
				return false;
			}
		}
//		si unOT rattaché à l'entretien à faire

		if((getCodeot()!=null)&&(!getCodeot().equals(""))){
			
			OT unOT = OT.chercherOT(aTransaction,getCodeot());
			if(aTransaction.isErreur()){
				return false;
			}
			//	 si la date de réalisation est renseignée alors le compteur de l'OT rattaché doit être renseignée
			if((getDatereal()!=null)&&(!getDatereal().equals(("")))){
				Modeles unModele = Modeles.chercherModeles(aTransaction,unEquipement.getCodemodele());
				if(aTransaction.isErreur()){
					aTransaction.declarerErreur("Le modèle n'a pas été trouvé.");
					return false;
				}
				TYPEEQUIP unTequip = TYPEEQUIP.chercherTYPEEQUIP(aTransaction,unModele.getCodete());
				if(aTransaction.isErreur()){
					aTransaction.declarerErreur("Le type d'équipement n'a pas été trouvé.");
					return false;
				}
				// le compteur est obligatoire
				if(!("MT").equals(unTequip.getTypete())){
					if(unOT.getCompteur()==null||unOT.getCompteur().equals("")){
						aTransaction.declarerErreur("Le compteur de l'OT doit être renseigné.");
						return false;
					}
				}
			}
			
			// la date de réalisation <= date de sortie de l'équipement
			if((unOT.getDateentree()!=null)&&(!unOT.getDatesortie().equals("01/01/0001"))){
				int compare = Services.compareDates(unOT.getDateentree(),getDatereal());
				if(compare==1){
					aTransaction.declarerErreur("La date de réalisation doit être supérieure ou égale à la date d'entrée de l'équipement.");
					return false;
				}else if(compare==-9999){
					return false;
				}
			}
			//la date de réalisation <= date de sortie de l'équipement
			if((unOT.getDatesortie()!=null)&&(!unOT.getDatesortie().equals("01/01/0001"))){
				int compare = Services.compareDates(getDatereal(),unOT.getDatesortie());
				if(compare==1){
					aTransaction.declarerErreur("La date de réalisation doit être inférieur ou égale à la date de sortie de l'équipement.");
					return false;
				}else if(compare==-9999){
					return false;
				}
			}
		}
		String date = Services.formateDate(getDatereal());
		int compare = Services.compareDates(dateAnnee,date);
		if(compare==-1){
			aTransaction.declarerErreur("La date de réalisation ne doit pas être supérieur au "+dateAnnee);
			return false;
		}else if(compare==-9999){
			return false;
		}
	}
//	RG : controle du compteur
	if(!verifCompteur(aTransaction,unEquipement,unTypeInt)){
		return false;
	}
	
//	 si sinistre pas renseigné alors non
	if (getSinistre()==null){
		setSinistre("non");
	}
	
	
	setCodeequip(unEquipement.getNumeroinventaire());
	setCodeentretien(unEntretien.getCodeentretien());
	setCodetypeent(unTe.getCodetypeent());
	//AJOUT OFONTENEAU 20090403 4=SANS INTERVALLE
//	setCodeti(unTypeInt.getCodeti());
	if (unTe.getCodetypeent().trim().equals("2")){
		setCodeti("4");
		setIntervallepep(null);
	}else{
		setCodeti(unTypeInt.getCodeti());
	}
	//FIN AJOUT OFONTENEAU
	
	//Modification du PePerso
	//return getMyPePersoBroker().modifierPePerso(aTransaction);
	modifierPePersoInfos(aTransaction);
	if(aTransaction.isErreur()){
		return false;
	}
	//si la date de réalisation est renseigné : on prévoit la date du prochain entretien si c'est un entretien de base
	if(getCodetypeent().equals("1")){
		if((!getDatereal().equals("01/01/0001"))&&(!getDatereal().equals(""))){
			PePerso unPePerso = new PePerso();
			unPePerso.setDuree(getDuree());
			unPePerso.setIntervallepep(getIntervallepep());
			unPePerso.creerPePerso(aTransaction,unEquipement,unEntretien,unTe,unTypeInt);
			if(aTransaction.isErreur()){
				return false;
			}
		}
	}
	
	return true;
}

/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 * Méthode modifier quand on a changé les infos et pas les objets étranger
 */
public boolean modifierPePersoInfos(nc.mairie.technique.Transaction aTransaction)  throws Exception {

//	 si sinistre pas renseigné alors non
	if (getSinistre()==null){
		setSinistre("non");
	}	
	// compteur
	//si unOT rattaché à l'entretien à faire
//	 si la date de réalisation est renseignée alors le compteur de l'OT rattaché doit être renseignée
	if((getDatereal()!=null)&&(!getDatereal().equals(("")))&(!"01/01/0001".equals(getDatereal()))){
		if((getCodeot()!=null)&&(!getCodeot().equals(""))){
			OT unOT = OT.chercherOT(aTransaction,getCodeot());
			if(aTransaction.isErreur()){
				return false;
			}
			Equipement unEquipement = Equipement.chercherEquipement(aTransaction,unOT.getNuminv());
			if(aTransaction.isErreur()){
				aTransaction.declarerErreur("L'équipement n'a pas été trouvé.");
				return false;
			}
			Modeles unModele = Modeles.chercherModeles(aTransaction,unEquipement.getCodemodele());
			if(aTransaction.isErreur()){
				aTransaction.declarerErreur("Le modèle n'a pas été trouvé.");
				return false;
			}
			TYPEEQUIP unTequip = TYPEEQUIP.chercherTYPEEQUIP(aTransaction,unModele.getCodete());
			if(aTransaction.isErreur()){
				aTransaction.declarerErreur("Le type d'équipement n'a pas été trouvé.");
				return false;
			}
			// le compteur est obligatoire
			if(!("MT").equals(unTequip.getTypete())){
				if(unOT.getCompteur()==null||unOT.getCompteur().equals("")){
					aTransaction.declarerErreur("Le compteur de l'OT doit être renseigné.");
					return false;
				}
			}
		}
	}
	
//	calcul de la date de prévision
	int nbjours = 0; 
	//si le type est occasionnel la date prev est la date du jour
	if (getCodetypeent().trim().equals("2")){
		String date = Services.dateDuJour();
		setDateprev(date);
	}else{
		// selon le type d'intervalle on calcule la date
		// type d'intervalle : 1=> Km 4=> sans 2=> année, 3=> jours 5=> heure
		if((!getCodeti().trim().equals("1"))&&(!getCodeti().trim().equals("4"))){
			if(getCodeti().trim().equals("2")){
				int nbannee = Integer.parseInt(getIntervallepep());
				nbjours = nbannee * 360;
			}else if(getCodeti().trim().equals("3")){
				nbjours = Integer.parseInt(getIntervallepep());
			}
	//		on regarde si l'entretien est une reconduction
			Equipement unEquipement = Equipement.chercherEquipement(aTransaction,getCodeequip());
			if (aTransaction.isErreur()){
				return false;
			}
			Entretien unEntretien = Entretien.chercherEntretien(aTransaction,getCodeentretien());
			if (aTransaction.isErreur()){
				return false;
			}
			ArrayList aList = chercherPePersoEquipEnt(aTransaction,unEquipement.getNumeroinventaire(),unEntretien.getCodeentretien());
			if (aTransaction.isErreur()){
				return false;
			}
			if (aList.size()>0){
				if(getDateprev().equals("01/01/0001")){
					setDateprev(Services.ajouteJours(unEquipement.getDatemiseencirculation(),nbjours));
				}
				
			}else{
				int code = maxPePersoEquipEnt(aTransaction,unEquipement.getNumeroinventaire(),unEntretien.getCodeentretien());
				PePerso unPePerso = chercherPePerso(aTransaction,String.valueOf(code));
				setDateprev(Services.ajouteJours(getDatereal(),nbjours));
			}
		}
	}
	
	//Modification du PePerso
	return getMyPePersoBroker().modifierPePerso(aTransaction);
}

/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 */
public boolean supprimerPePerso(nc.mairie.technique.Transaction aTransaction) throws Exception{
//	 si utilisé on ne peut pas supprimer
	/* unEquipement = new Equipement();
	if (unEquipement.existeEquipementModele(aTransaction,getCodemodele())){
		aTransaction.declarerErreur("Ce modèle est utilisé par un équipement.La suppression n'est pas possible");
		return false;
	}*/
	
	// si n°OT renseigné pas de suppression
	if (getCodeot()==null){
		//Suppression de l'PePerso
		return getMyPePersoBroker().supprimerPePerso(aTransaction);
	}else{
		aTransaction.declarerErreur("Un OT est rattaché à cette tâche.La suppresion n'est pas possible.");
		return false;
	}
}
/**
 * Retourne un booléen.
 * Vérifie si existe
 * @return true ou false
 */
public boolean existePePerso(nc.mairie.technique.Transaction aTransaction, String inv,String ent,String codete,String date) throws Exception{
	PePerso unPePerso = new PePerso();
	return unPePerso.getMyPePersoBroker().existePePerso(aTransaction, inv,ent,codete,date);
}
	
	
/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvPePerso(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//	recherche du dernier codepneu
	int nouvcodePePerso = getMyPePersoBroker().nouvPePerso(aTransaction);
	//si pas trouvé
	if (nouvcodePePerso == -1) {
		//fonctionnellement normal: table vide
		nouvcodePePerso = 1;
	} else {
		nouvcodePePerso++;
	}
	return nouvcodePePerso;
}

/* On recherche le dernier peperso
 * @author : Coralie NICOLAS
 */
public int maxPePersoEquipEnt(nc.mairie.technique.Transaction aTransaction,String inv,String ent) throws Exception{
	return getMyPePersoBroker().nouvPePerso(aTransaction);
}

/**
 * Retourne un booléen.
 * Vérifie que le modèle existe avec des paramètres de clé étrangères
 * @return PePerso
 */
public boolean existePePersoTEnt(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	PePerso unPePerso = new PePerso();
	return unPePerso.getMyPePersoBroker().existePePersoTEnt(aTransaction, param);
}

/**
 * Constructeur PePerso.
 */
public PePerso() {
	super();
}
/**
 * Getter de l'attribut codeequip.
 */
public String getCodeequip() {
	return codeequip;
}
/**
 * Setter de l'attribut codeequip.
 */
public void setCodeequip(String newCodeequip) { 
	codeequip = newCodeequip;
}
/**
 * Getter de l'attribut codeentretien.
 */
public String getCodeentretien() {
	return codeentretien;
}
/**
 * Setter de l'attribut codeentretien.
 */
public void setCodeentretien(String newCodeentretien) { 
	codeentretien = newCodeentretien;
}
/**
 * Getter de l'attribut sinistre.
 */
public String getSinistre() {
	return sinistre;
}
/**
 * Setter de l'attribut sinistre.
 */
public void setSinistre(String newSinistre) { 
	sinistre = newSinistre;
}
/**
 * Getter de l'attribut duree.
 */
public String getDuree() {
	return duree;
}
/**
 * Setter de l'attribut duree.
 */
public void setDuree(String newDuree) { 
	duree = newDuree;
}
/**
 * Getter de l'attribut codeot.
 */
public String getCodeot() {
	return codeot;
}
/**
 * Setter de l'attribut codeot.
 */
public void setCodeot(String newCodeot) { 
	codeot = newCodeot;
}
/**
 * Getter de l'attribut codetypeent.
 */
public String getCodetypeent() {
	return codetypeent;
}
/**
 * Setter de l'attribut codetypeent.
 */
public void setCodetypeent(String newCodetypeent) { 
	codetypeent = newCodetypeent;
}
/**
 * Getter de l'attribut commentairete.
 */
public String getCommentairete() {
	return commentairete;
}
/**
 * Setter de l'attribut commentairete.
 */
public void setCommentairete(String newCommentairete) { 
	commentairete = newCommentairete;
}
/**
 * Getter de l'attribut codepep.
 */
public String getCodepep() {
	return codepep;
}
/**
 * Setter de l'attribut codepep.
 */
public void setCodepep(String newCodepep) { 
	codepep = newCodepep;
}
/**
 * Getter de l'attribut codeti.
 */
public String getCodeti() {
	return codeti;
}
/**
 * Setter de l'attribut codeti.
 */
public void setCodeti(String newCodeti) { 
	codeti = newCodeti;
}
/**
 * Getter de l'attribut intervallepep.
 */
public String getIntervallepep() {
	return intervallepep;
}
/**
 * Setter de l'attribut intervallepep.
 */
public void setIntervallepep(String newIntervallepep) { 
	intervallepep = newIntervallepep;
}
/**
 * Getter de l'attribut datereal.
 */
public String getDatereal() {
	return datereal;
}
/**
 * Setter de l'attribut datereal.
 */
public void setDatereal(String newDatereal) { 
	datereal = newDatereal;
}
/**
 * Getter de l'attribut dateprev.
 */
public String getDateprev() {
	return dateprev;
}
/**
 * Setter de l'attribut dateprev.
 */
public void setDateprev(String newDateprev) { 
	dateprev = newDateprev;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new PePersoBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected PePersoBroker getMyPePersoBroker() {
	return (PePersoBroker)getMyBasicBroker();
}
}
