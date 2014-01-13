package nc.mairie.seat.metier;

import java.util.ArrayList;
import java.util.Calendar;

import nc.mairie.seat.metier.PiecesOT;
import nc.mairie.seat.process.Outils;

import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;


/**
 * Objet métier OT
 */
public class OT extends BasicMetier {
	public String numeroot;
	public String dateentree;
	public String datesortie;
	public String valide;
	public String numerobc;
	public String commentaire;
	public String compteur;
	public String numinv;
	
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
 * Retourne un ArrayList d'objet métier : OT.
 * @return java.util.ArrayList
 */
public static ArrayList<OT> listerOTValide(nc.mairie.technique.Transaction aTransaction) throws Exception{
	OT unOT = new OT();
	return unOT.getMyOTBroker().listerOTValide(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @return java.util.ArrayList
 */
public static ArrayList<OT> listerOTAValider(nc.mairie.technique.Transaction aTransaction) throws Exception{
	OT unOT = new OT();
	return unOT.getMyOTBroker().listerOTAValider(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @return java.util.ArrayList
 */
public static ArrayList<OT> listerOTDeclarationsValide(nc.mairie.technique.Transaction aTransaction) throws Exception{
	OT unOT = new OT();
	return unOT.getMyOTBroker().listerOTDeclarationsValide(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @return java.util.ArrayList
 */
public static ArrayList<OT> listerOTDeclarationsEncours(nc.mairie.technique.Transaction aTransaction) throws Exception{
	OT unOT = new OT();
	return unOT.getMyOTBroker().listerOTDeclarationsEncours(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @return java.util.ArrayList
 */
public static ArrayList<OT> listerOTEncours(nc.mairie.technique.Transaction aTransaction) throws Exception{
	OT unOT = new OT();
	return unOT.getMyOTBroker().listerOTEncours(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @return java.util.ArrayList
 */
public static ArrayList<OT> listerOT(nc.mairie.technique.Transaction aTransaction) throws Exception{
	OT unOT = new OT();
	return unOT.getMyOTBroker().listerOT(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @return java.util.ArrayList
 */
public static ArrayList<OT> listerOTEquip(nc.mairie.technique.Transaction aTransaction,String numinv) throws Exception{
	OT unOT = new OT();
	return unOT.getMyOTBroker().listerOTEquip(aTransaction,numinv);
}


/**
 * Retourne un ArrayList d'objet métier : OT.
 * @return java.util.ArrayList
 *//*
public static ArrayList<OT> listerOTEquip(nc.mairie.technique.Transaction aTransaction) throws Exception{
	OT unOT = new OT();
	return unOT.getMyOTBroker().listerOTEquip(aTransaction);
}*/

/**
 * Retourne un OT.
 * @return OT
 */
public static OT chercherOT(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	OT unOT = new OT();
	return unOT.getMyOTBroker().chercherOT(aTransaction, code);
}
/*vérifie que les champs sont bien renseignés
 * Date : 01/09/05
 * autheur : NC
 */
public boolean controleChamps(nc.mairie.technique.Transaction aTransaction )  throws Exception {
	if((getCompteur()!=null)&&(!getCompteur().equals(""))){
		setCompteur(Outils.enleveEspace(getCompteur()));
		if(!Services.estNumerique(getCompteur())){
			aTransaction.declarerErreur("Vous devez saisir des chiffres pour le compteur.");
			return false;
		}
	}
	if((getNumeroot()!=null)&&(!getNumeroot().equals(""))){
		setNumeroot(Outils.enleveEspace(getNumeroot()));
		if(!Services.estNumerique(getNumeroot())){
			aTransaction.declarerErreur("Vous devez saisir des chiffres pour le numéro d'OT.");
			return false;
		}
	}
	return true;
}
public String creationOTDeclaration(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement)  throws Exception {
	
//	 controle si null
	if (null == unEquipement){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return "erreur";
	}
	setNuminv(unEquipement.getNumeroinventaire());
	creerOT(aTransaction,unEquipement);
	return getNumeroot();
}

/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */
public boolean creerOT(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement)  throws Exception {
	// controle si null
	if (null == unEquipement){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return false;
	}
//	RG : controle des champs
	controleChamps(aTransaction);
	if(aTransaction.isErreur()){
		return false;
	}
	if((getDateentree()!=null)&&(!getDateentree().equals(""))){
		if(!Services.estUneDate(getDateentree())){
			aTransaction.declarerErreur("La date d'entrée n'est pas correcte.");
			return false;
		}
	}
	if((getDatesortie()!=null)&&(!getDatesortie().equals(""))){
		if(!Services.estUneDate(getDatesortie())){
			aTransaction.declarerErreur("La date de sortie n'est pas correcte.");
			return false;
		}
	}
//	RG : contrôle si la date entrée<=date de sortie
	if((getDateentree()!=null)&&(getDatesortie()!=null)&&(!getDateentree().equals(""))&&(!getDatesortie().equals(""))){
		
		int compare = Services.compareDates(getDateentree(),getDatesortie());
		if(compare==1){
			aTransaction.declarerErreur("La date d'entrée doit être inférieure ou égale à la date de sortie.");
			return false;
		}else if(compare==-9999){
			return false;
		}
		setDateentree(Services.formateDate(getDateentree()));
		setDatesortie(Services.formateDate(getDatesortie()));
	}
	
	//	RG : controle de l'année (l'année saisie ne doit pas être supérieur à l'année en cours)
	String annee = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	String dateAnnee = Services.formateDate("31/12/"+annee);
	
	//Date d'entrée
	if((getDateentree()!=null)&&(!getDateentree().equals(""))){
//		RG : date de sortie >= date de réalisation
		if((getNumeroot()!=null)&&(!getNumeroot().equals(""))){
			ArrayList<PePerso> listEntretien = PePerso.chercherPePersoOT(aTransaction,getNumeroot());
			if(aTransaction.isErreur()){
				return false;
			}
			if(listEntretien.size()>0){
				for(int i=0;i<listEntretien.size();i++){
					PePerso unPeP = (PePerso)listEntretien.get(i);
					if((unPeP.getDatereal()!=null)&&(!unPeP.getDatereal().equals("01/01/0001"))){
						int compare = Services.compareDates(getDateentree(),unPeP.getDatereal());
						if(compare==1){
							aTransaction.declarerErreur("La date d'entrée doit être inférieure ou égale à la date de réalisation de l'entretien.("+unPeP.getDatereal()+")");
							return false;
						}else if(compare==-9999){
							return false;
						}
					}
				}
			}
		}
		
		//Services.convertitDate(Services.formateDate(getDateentree()),"dd/mm/yyyy","yyyy");
		String date = Services.formateDate(getDateentree());
		int compare = Services.compareDates(date,dateAnnee);
		if(compare==1){
			aTransaction.declarerErreur("La date d'entrée ne doit pas être supérieur au "+dateAnnee);
			return false;
		}else if(compare==-9999){
			return false;
		}
	}
	
	//Date de sortie
	if((getDatesortie()!=null)&&(!getDatesortie().equals(("")))){
		String date = Services.formateDate(getDatesortie());
		int compare = Services.compareDates(date,dateAnnee);
		if(compare==1){
			aTransaction.declarerErreur("La date de sortie ne doit pas être supérieur au "+dateAnnee);
			return false;
		}else if(compare==-9999){
			return false;
		}
	}
	//RG : date de sortie >= date de réalisation
	if((getDatesortie()!=null)&&(!getDatesortie().equals(""))){
		if((getNumeroot()!=null)&&(!getNumeroot().equals(""))){
			ArrayList<PePerso> listEntretien = PePerso.chercherPePersoOT(aTransaction,getNumeroot());
			if(aTransaction.isErreur()){
				return false;
			}
			if(listEntretien.size()>0){
				for(int i=0;i<listEntretien.size();i++){
					PePerso unPeP = (PePerso)listEntretien.get(i);
					if((unPeP.getDatereal()!=null)&&(!unPeP.getDatereal().equals(""))){
						int compare = Services.compareDates(unPeP.getDatereal(),getDatesortie());
						if(compare==1){
							aTransaction.declarerErreur("La date de sortie doit être supérieur à la date de réalisation de l'entretien."+unPeP.getDatereal()+")");
							return false;
						}else if(compare==-9999){
							return false;
						}
					}
				}
	//			RG : date de sortie doit être supérieur à date sortie des pièces
				ArrayList<PiecesOT> listePieces = PiecesOT.chercherPiecesOTOT(aTransaction,getNumeroot());
				if(aTransaction.isErreur()){
					return false;
				}
				if(listePieces.size()>0){
					for(int i=0;i<listePieces.size();i++){
						PiecesOT unePieceOT = (PiecesOT)listePieces.get(i);
						if((unePieceOT.getDatesortie()!=null)&&(!unePieceOT.getDatesortie().equals(""))){
							int compare = Services.compareDates(unePieceOT.getDatesortie(),getDatesortie());
							if(compare==1){
								aTransaction.declarerErreur("La date de sortie doit être supérieure ou égale à la date de sortie des pièces"+unePieceOT.getDatesortie());
								return false;
							}else if(compare==-9999){
								return false;
							}
						}
					}
				}
			}
			
		}
	}
	
	
//	on crée l'objet en passant en paramétre une chaîne representant le format
	//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	if(getValide()==null){
		setValide("F");
	}
	//le numéro OT est de type aaaa11111
	//	 récupération de l'année courante
	//int annee = Calendar.getInstance().get(Calendar.YEAR);
	int maxCode = nouvCodeOt(aTransaction);
	FPM unFPM = new FPM();
	if(unFPM.nouvCodeFpm(aTransaction)>maxCode){
		maxCode = unFPM.nouvCodeFpm(aTransaction);
	}
	if (String.valueOf(maxCode).length()==8){
		String deb = String.valueOf(maxCode).substring(0,4);
		if (Integer.parseInt(annee)>Integer.parseInt(deb)){
			setNumeroot(annee+"0001"); 
		}else{
			setNumeroot(String.valueOf(maxCode));
		}
	}else{
		setNumeroot(annee+"0001");
	}

	
//	 RG : controle si compteur renseigné : // modif RG du 28/07/06: le compteur doit être renseigné pour le bon fonctionnement du planning
	//selon la date, le compteur doit être supérieur ou égale au compteur le précédent et inférieur ou égale au compteur suivant par rapport aux BPC
	//OTInfos unOTInfos = OTInfos.chercherOTInfos(aTransaction,getNumeroot());
	if(aTransaction.isErreur()){
		return false;
	}
	Modeles unModele = Modeles.chercherModeles(aTransaction,unEquipement.getCodemodele());
	if(aTransaction.isErreur()){
		aTransaction.declarerErreur("Le modèle n'a pas été reconnu.");
		return false;
	}
	TYPEEQUIP.chercherTYPEEQUIP(aTransaction,unModele.getCodete());
	if(aTransaction.isErreur()){
		aTransaction.declarerErreur("Le type d'équipement n'a pas été reconnu.");
		return false;
	}
	/*if(!("MT").equals(unTe.getTypete())){
		if (("").equals(getCompteur())||(getCompteur()==null)){
			aTransaction.declarerErreur("Le compteur doit être renseigné.");
			return false;
		}
	}*/
	if((getDateentree()!=null)&&(!getDateentree().equals(""))){
		// BPC Précédent
		BPC unBPCPrec = BPC.chercherBPCPrecEquipDate(aTransaction,getDateentree(),unEquipement.getNumeroinventaire());
		// si pas de BPC trouvé = pas de BPC enregistré pour cet équipement
		if(unBPCPrec!=null){
			if(unBPCPrec.getNumerobpc()==null){
				aTransaction.traiterErreur();
			}else{
				if((!getCompteur().equals(""))&&(unBPCPrec.getNumerobpc()!=null)){
					if(Integer.parseInt(getCompteur())<Integer.parseInt(unBPCPrec.getValeurcompteur())){
						aTransaction.declarerErreur("Le compteur de l'OT doit être supérieur ou égale au compteur du BPC le précédant("+unBPCPrec.getValeurcompteur()+")");
						return false;
					}
				}
			}
		}else{
			aTransaction.traiterErreur();
		}
		
//		BPC Suivant
		BPC unBPCSuiv = BPC.chercherBPCSuivEquipDate(aTransaction,getDateentree(),unEquipement.getNumeroinventaire());
		// si pas de BPC trouvé = pas de BPC enregistré pour cet équipement
		if(unBPCSuiv!=null){
			if(unBPCSuiv.getNumerobpc()==null){
				aTransaction.traiterErreur();
			}else{
				if((!getCompteur().equals(""))&&(unBPCSuiv.getNumerobpc()!=null)){
					if(Integer.parseInt(getCompteur())>Integer.parseInt(unBPCSuiv.getValeurcompteur())){
						aTransaction.declarerErreur("Le compteur de l'OT doit être inférieur ou égale au compteur du BPC suivant("+unBPCSuiv.getValeurcompteur()+")");
						return false;
					}
				}
			}
			
		}else{
			aTransaction.traiterErreur();
		}
	}
	
	
	
	
	
	setNuminv(unEquipement.getNumeroinventaire());
		//Creation du OT
	return getMyOTBroker().creerOT(aTransaction);
}

/*Methode creerObjetMetier qui retourne
 * true ou false
 * On valide l'OT 
 */
public String validationOT(nc.mairie.technique.Transaction aTransaction )  throws Exception {
	// modification du 06/08/08 remplacer tous les message=erreur avec les message plus simple et traiter les erreurs
	String msg ="";
	
	setValide("T");
	// si la date d'entree ou le compteur n'est pas renseigné : on ne peut pas valider.
	// le compteur est obligatoire si ce n'est pas un petit matériel
	/*OTInfos unOTInfos = OTInfos.chercherOTInfos(aTransaction,getNumeroot());
	if(aTransaction.isErreur()){
		return "erreur";
	}*/
	int compare2 = Services.compareDates(Services.dateDuJour(),getDateentree());
	if(compare2==-9999){
		return "erreur";
	}else if(compare2==-1){
		//aTransaction.declarerErreur("La Date d'entrée doit être supérieur ou égale à la date du jour.");
		//return "erreur";
		msg = "La Date d'entrée doit être supérieur ou égale à la date du jour.";
		return msg;
	}
	EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfos(aTransaction,getNuminv());
	if(aTransaction.isErreur()){
		//return "erreur";
		msg = "L'équipement n'a pas été trouvé.";
		aTransaction.traiterErreur();
		return msg;
	}
	if (!"MT".equals(unEquipementInfos.getTypete().trim())){
		if((getCompteur()==null)||(getCompteur().equals(""))){
			msg= "Veuillez vérifier que le compteur soit renseigné.";
			return "erreur";
		}
	}
	
	if(getDateentree().equals("01/01/0001")){
		//aTransaction.declarerErreur("Veuillez vérifier la date d'entrée et la date de sortie.");
		//return "erreur";
		msg = "Veuillez vérifier la date d'entrée et la date de sortie.";
		return msg;
	}
	
	// si date de sortie n'est pas renseigné on met la date du jour de validation
	if(getDatesortie().equals("01/01/0001")){
		setDatesortie(Services.dateDuJour());
	}
	// controle de la Date
	// la date de sortie du véhicule ne doit pas être inférieur aux dates de réalisation des entretiens
	ArrayList<PePerso> listPePersoPasFait = PePerso.chercherPePersoPasFaitOT(aTransaction,getNumeroot());
	if(aTransaction.isErreur()){
		//return "erreur";
		// peut ne pas avoir réaliser les entretiens.
		aTransaction.traiterErreur();
	}
	
//	 si certains entretiens n'ont pas été fait on informe l'utilisateur
	if(listPePersoPasFait.size()>0){
		msg = "Attention certaines tâches de l'OT "+getNumeroot()+" n'ont pas été réalisées.";
	}else{
		msg = "";
	}
	
	ArrayList<PePerso> listPePerso = PePerso.chercherPePersoOT(aTransaction,getNumeroot());
	if(aTransaction.isErreur()){
		//return "erreur";
		aTransaction.traiterErreur();
		// peut ne pas y avoir d'entretien perso pour l'OT
	}
	if(listPePerso.size()>0){
		for (int i = 0;i<listPePerso.size();i++){
			PePerso unPePerso = (PePerso)listPePerso.get(i);
			// on regarde si la date de réalisation a été renseignée si elle n'est pas renseignée on met la date de sortie
			/*if((unPePerso.getDatereal()==null)||(unPePerso.getDatereal().equals("01/01/0001"))){
				unPePerso.setDatereal(getDatesortie());
				unPePerso.modifierPePersoInfos(aTransaction);
				if(aTransaction.isErreur()){
					return false;
				}
			}*/
			int compare = Services.compareDates(getDatesortie(),unPePerso.getDatereal());
			if(compare==-9999){
				return "erreur";
			}else if(compare==-1){
				msg = "La Date de sortie doit être supérieur ou égale à la date de réalisation des entretiens.";
				//return "erreur";
				return msg;
			}
		}
	}
//	 la date de sortie du véhicule ne doit pas être inférieur aux dates de sorties des pièces
	ArrayList<PiecesOT> listPiecesOT = PiecesOT.chercherPiecesOTOT(aTransaction,getNumeroot());
	if(aTransaction.isErreur()){
		//return "erreur";
		msg = aTransaction.traiterErreur();
		return msg;
	}
	if(listPiecesOT.size()>0){
		for (int i = 0;i<listPiecesOT.size();i++){
			PiecesOT unPiecesOT = (PiecesOT)listPiecesOT.get(i);
			int compare = Services.compareDates(getDatesortie(),unPiecesOT.getDatesortie());
			if(compare==-9999){
				return "erreur";
			}else if(compare==-1){
				msg = "La Date de sortie doit être supérieur ou égale à la date de sortie des pièces.";
				//return "erreur";
				return msg;
			}
		}
	}
	Equipement unEquipement = Equipement.chercherEquipement(aTransaction,unEquipementInfos.getNumeroinventaire());
	if(aTransaction.isErreur()){
		aTransaction.traiterErreur();
		return "L'équipement "+unEquipementInfos.getNumeroinventaire()+" n'a pas été trouvé.";
		// return erreur
	}
	modifierOT(aTransaction,unEquipement);	
	if(!msg.equals("")){
		return msg;
	}
	return "ok";
}

/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierOT(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement) throws Exception {
//	 controle si null
	if (null == unEquipement){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return false;
	}
//	RG : controle des champs
	controleChamps(aTransaction);
	if(aTransaction.isErreur()){
		return false;
	}
	//RG : controle si les dates sont correctes
	if((getDateentree()!=null)&&(!getDateentree().equals(""))){
		if(!Services.estUneDate(getDateentree())){
			aTransaction.declarerErreur("La date d'entree est incorrecte.");
			return false;
		}
	}
	if((getDatesortie()!=null)&&(!getDatesortie().equals(""))&& (!("01/01/0001").equals(getDatesortie()))){
		if(!Services.estUneDate(getDatesortie())){
			aTransaction.declarerErreur("La date de sortie est incorrecte.");
			return false;
		}
	}
	
	//RG : contrôle si la date entrée<=date de sortie
	if((!getDateentree().equals(""))&&(!getDatesortie().equals("")) && (!("01/01/0001").equals(getDatesortie()))){
		setDateentree(Services.formateDate(getDateentree()));
		setDatesortie(Services.formateDate(getDatesortie()));
		int compare = Services.compareDates(getDateentree(),getDatesortie());
		if(compare==1){
			aTransaction.declarerErreur("La date d'entrée ("+getDateentree()+") doit être inférieure ou égale à la date de sortie("+getDatesortie()+"). Pour rappel : si la date de sortie n'est pas renseignée, la date du jour est inscrite pour la date de sortie.");
			return false;
		}else if(compare==-9999){
			return false;
		}
	}
	
	//	RG : controle de l'année (l'année saisie ne doit pas être supérieur à l'année en cours)
	String annee = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	String dateAnnee = Services.formateDate("31/12/"+annee);
	
	//Date d'entrée
	if((getDateentree()!=null)&&(!getDateentree().equals(""))){
//		RG : date de sortie >= date de réalisation
		ArrayList<PePerso> listEntretien = PePerso.chercherPePersoOT(aTransaction,getNumeroot());
		if(aTransaction.isErreur()){
			// pas d'entretiens encore réaliser
			aTransaction.traiterErreur();
		}
		if(listEntretien.size()>0){
			for(int i=0;i<listEntretien.size();i++){
				PePerso unPeP = (PePerso)listEntretien.get(i);
				if((unPeP.getDatereal()!=null)&&(!unPeP.getDatereal().equals("01/01/0001"))){
					int compare = Services.compareDates(getDateentree(),unPeP.getDatereal());
					if(compare==1){
						aTransaction.declarerErreur("La date d'entrée doit être inférieure ou égale à la date de réalisation de l'entretien.("+unPeP.getDatereal()+")");
						return false;
					}else if(compare==-9999){
						return false;
					}
				}
			}
		}
		//Services.convertitDate(Services.formateDate(getDateentree()),"dd/mm/yyyy","yyyy");
		String date = Services.formateDate(getDateentree());
		int compare = Services.compareDates(date,dateAnnee);
		if(compare==1){
			aTransaction.declarerErreur("La date d'entrée ne doit pas être supérieur au "+dateAnnee);
			return false;
		}else if(compare==-9999){
			return false;
		}
	}
	
	//Date de sortie
	if((getDatesortie()!=null)&&(!getDatesortie().equals(("")))){
		String date = Services.formateDate(getDatesortie());
		int compare = Services.compareDates(date,dateAnnee);
		if(compare==1){
			aTransaction.declarerErreur("La date de sortie ne doit pas être supérieur au "+dateAnnee);
			return false;
		}else if(compare==-9999){
			return false;
		}
	}
	//RG : date de sortie >= date de réalisation
	ArrayList<PePerso> listEntretien = PePerso.chercherPePersoOT(aTransaction,getNumeroot());
	if(aTransaction.isErreur()){
		// pas d'entretiens à réalisé enregistré
		aTransaction.traiterErreur();
	}
	if(listEntretien.size()>0){
		for(int i=0;i<listEntretien.size();i++){
			PePerso unPeP = (PePerso)listEntretien.get(i);
			if((unPeP.getDatereal()!=null)&&(!unPeP.getDatereal().equals("01/01/0001"))&&(!getDatesortie().equals(""))){
				int compare = Services.compareDates(unPeP.getDatereal(),getDatesortie());
				if(compare==1){
					aTransaction.declarerErreur("La date de sortie doit être supérieur à la date de réalisation de l'entretien."+unPeP.getDatereal()+")");
					return false;
				}else if(compare==-9999){
					return false;
				}
			}
		}
	}
	//RG : date de sortie doit être supérieur à date sortie des pièces
	ArrayList<PiecesOT> listePieces = PiecesOT.chercherPiecesOTOT(aTransaction,getNumeroot());
	if(aTransaction.isErreur()){
		return false;
	}
	if(listePieces.size()>0){
		for(int i=0;i<listePieces.size();i++){
			PiecesOT unePieceOT = (PiecesOT)listePieces.get(i);
			if((unePieceOT.getDatesortie()!=null)&&(!unePieceOT.getDatesortie().equals(""))){
				int compare = Services.compareDates(unePieceOT.getDatesortie(),getDatesortie());
				if(compare==1){
					aTransaction.declarerErreur("La date de sortie doit être supérieure ou égale à la date de sortie des pièces"+unePieceOT.getDatesortie());
					return false;
				}else if(compare==-9999){
					return false;
				}
			}
		}
	}
	
	// RG : controle si compteur renseigné : // modifi RG du 28/07/06 : le compteur doit être renseigné si pas petit matériel
	Modeles unModele = Modeles.chercherModeles(aTransaction,unEquipement.getCodemodele());
	if(aTransaction.isErreur()){
		aTransaction.declarerErreur("Le modèle n'a pas été reconnu.");
		return false;
	}
	TYPEEQUIP unTe = TYPEEQUIP.chercherTYPEEQUIP(aTransaction,unModele.getCodete());
	if(aTransaction.isErreur()){
		aTransaction.declarerErreur("Le type d'équipement n'a pas été reconnu.");
		return false;
	}
	if(!("MT").equals(unTe.getTypete())){
		if (("").equals(getCompteur())||(getCompteur()==null)){
			aTransaction.declarerErreur("Le compteur doit être renseigné.");
			return false;
		}
	}
	//selon la date, le compteur doit être supérieur ou égale au compteur le précédent et inférieur ou égale au compteur suivant par rapport aux BPC
	BPC unBPCPrec = null;
	BPC unBPCSuiv = null;
	//String numinv = "";
	/*OTInfos unOTInfos = OTInfos.chercherOTInfos(aTransaction,getNumeroot());
	if(aTransaction.isErreur()){
		// il n'y a pas encore d'entretien rattaché à l'ot
		aTransaction.traiterErreur();
		Declarations uneDeclaration = Declarations.chercherDeclarationsOT(aTransaction,getNumeroot());
		if(aTransaction.isErreur()){
			// l'Ot ne provient pas d'une déclaration
			aTransaction.traiterErreur();
		}else{
			numinv = uneDeclaration.getNuminv();
		}
	}else{
		numinv = unOTInfos.getNumeroinventaire();
	}*/
	//BPC Précédent
	if((getDateentree()!=null)&&(!getDateentree().equals(""))){
		unBPCPrec = BPC.chercherBPCPrecEquipDate(aTransaction,getDateentree(),unEquipement.getNumeroinventaire());
		//	si pas de BPC trouvé = pas de BPC enregistré pour cet équipement
		if(unBPCPrec.getNumerobpc()==null){
			aTransaction.traiterErreur();
		}
		if((!getCompteur().equals(""))&&(unBPCPrec.getNumerobpc()!=null)){
			if(Integer.parseInt(getCompteur())<Integer.parseInt(unBPCPrec.getValeurcompteur())){
				aTransaction.declarerErreur("Le compteur de l'OT doit être supérieur ou égale au compteur du BPC le précédant("+unBPCPrec.getValeurcompteur()+")");
				return false;
			}
		}
		//BPC Suivant
		unBPCSuiv = BPC.chercherBPCSuivEquipDate(aTransaction,getDateentree(),unEquipement.getNumeroinventaire());
		// si pas de BPC trouvé = pas de BPC enregistré pour cet équipement
		if(unBPCSuiv.getNumerobpc()==null){
			aTransaction.traiterErreur();
		}
		if((!getCompteur().equals(""))&&(unBPCSuiv.getNumerobpc()!=null)){
			if(Integer.parseInt(getCompteur())>Integer.parseInt(unBPCSuiv.getValeurcompteur())){
				aTransaction.declarerErreur("Le compteur de l'OT doit être inférieur ou égale au compteur du BPC suivant("+unBPCSuiv.getValeurcompteur()+")");
				return false;
			}
		}
	}
	
	setNuminv(unEquipement.getNumeroinventaire());
	//Modification du OT
	return getMyOTBroker().modifierOT(aTransaction);
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 */
public boolean supprimerOT(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'OT
	return getMyOTBroker().supprimerOT(aTransaction);
}

public boolean suppressionOT(nc.mairie.technique.Transaction aTransaction) throws Exception{
	// RG controle : si des pièces ont été sorties pour cet OT ==> l'OT ne peut pas être supprimé
	ArrayList<PiecesOT> listPieces = PiecesOT.chercherPiecesOTOT(aTransaction,getNumeroot());
	if(aTransaction.isErreur()){
		return false;
	}
	if(listPieces.size()>0){
		aTransaction.declarerErreur("L'OT ("+getNumeroot()+") a provoqué des sorties de pièces donc il ne peut pas être supprimé.");
		return false;
	}
	// RG controle : si l'OT a eu des interventions de mécaniciens : pas de suppression possible
	ArrayList<OT_ATM> listIntervenant = OT_ATM.listerOT_ATMOT(aTransaction,getNumeroot());
	if(aTransaction.isErreur()){
		return false;
	}
	if(listIntervenant.size()>0){
		aTransaction.declarerErreur("L'OT ("+getNumeroot()+") a provoqué une intervention de mécaniciens donc il ne peut pas être supprimé.");
		return false;
	}
	
	if (BE.existeBEOT(aTransaction, getNumeroot())) {
		aTransaction.declarerErreur("L'OT ("+getNumeroot()+") est rattaché à au moins un Bon d'engagement donc il ne peut être supprimé.");
		return false;
	}
	/*LB modif 8/11/11
	//RG controle : si l'OT fait l'objet d'un Bon d'engagement ==> l'OT ne peut pas être supprimé
	ArrayList listBe = BE.listerBEOT(aTransaction,getNumeroot());
	if(aTransaction.isErreur()){
		return false;
	}
	if(listBe.size()>0){
		aTransaction.declarerErreur("L'OT ("+getNumeroot()+") est rattaché à au moins un Bon d'engagement donc il ne peut être supprimé.");
		return false;
	}
	*/
	//pour tous les entretiens prévus dans cet OT on modifie pour mettre le PePerso.numot à null
	ArrayList<PePerso> listEntretiens = PePerso.chercherPePersoOT(aTransaction,getNumeroot());
	if(aTransaction.isErreur()){
		return false;
	}
	if(listEntretiens.size()>0){
		for(int i=0;i<listEntretiens.size();i++){
			PePerso unPePerso = (PePerso)listEntretiens.get(i);
			unPePerso.setCodeot("");
			unPePerso.modifierPePersoInfos(aTransaction);
		}
	}
	// declarations rattachées à l'OT
	ArrayList<Declarations> listDeclarations = Declarations.listerDeclarationsOT(aTransaction,getNumeroot());
	if(aTransaction.isErreur()){
		return false;
	}
	if(listDeclarations.size()>0){
		for (int i=0;i<listDeclarations.size();i++){
			Declarations uneDeclaration = (Declarations)listDeclarations.get(i);
			uneDeclaration.setCodeot("");
			Service unService = Service.chercherService(aTransaction,uneDeclaration.getCodeservice());
			if(aTransaction.isErreur()){
				return false;
			}
			Equipement unEquipement = Equipement.chercherEquipement(aTransaction,uneDeclaration.getNuminv());
			if(aTransaction.isErreur()){
				aTransaction.traiterErreur();
				aTransaction.declarerErreur("Erreur : l'équipement "+uneDeclaration.getNuminv()+" pour la déclaration "+uneDeclaration.codedec+ " de l'OT "+getNumeroot()+" est introuvable");
				return false;
			}

			if(uneDeclaration.getCodeservice().equals("4000")){
				AgentCDE unAgentCDE = AgentCDE.chercherAgentCDE(aTransaction,uneDeclaration.getMatricule());
				if(aTransaction.isErreur()){
					return false;
				}
				uneDeclaration.modifierDeclarationsAgentCDE(aTransaction,unEquipement,unAgentCDE,unService);
			}else if(uneDeclaration.getCodeservice().equals("5000")){
				AgentCCAS unAgentCCAS = AgentCCAS.chercherAgentCCAS(aTransaction,uneDeclaration.getMatricule());
				if(aTransaction.isErreur()){
					return false;
				}
				uneDeclaration.modifierDeclarationsAgentCCAS(aTransaction,unEquipement,unAgentCCAS,unService);
				
			}else{
				Agents unAgent = Agents.chercherAgents(aTransaction,uneDeclaration.getMatricule());
				if(aTransaction.isErreur()){
					return false;
				}
				uneDeclaration.modifierDeclarations(aTransaction,unEquipement,unAgent,unService);
			}
			if(aTransaction.isErreur()){
				return false;
			}
		}
	}
	//suppression de l'OT
	return supprimerOT(aTransaction);
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvCodeOt(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//	recherche du dernier code
//	recherche du dernier codeCarburant
	int nouvcode = getMyOTBroker().nouvCodeOt(aTransaction);
	//si pas trouvé
	if (nouvcode == -1) {
		//fonctionnellement normal: table vide
		nouvcode= 1;
	} else {
		nouvcode++;
	}
	
	return nouvcode;
}

/*
public int calculCoutTotal(nc.mairie.technique.Transaction aTransaction,OT unOT) throws Exception{
	if (null == unOT){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","OT"));
		return -1;
	}
	int total = 0;
	int totalPiece = 0;
	String codeDep;
	// on parcours les pièces
	ArrayList listPieces = PiecesOtInfos.listerPiecesOtInfosOT(aTransaction,unOT.getNumeroot());
	if(aTransaction.isErreur()){
		// il n'y a pas de pièces associé à l'OT
		aTransaction.traiterErreur();
	}else{
		// calcul du montant des pièces
		for (int i=0;i<listPieces.size();i++){
			PiecesOtInfos maPieceOtInfos = (PiecesOtInfos)listPieces.get(i);
			totalPiece = Integer.parseInt(maPieceOtInfos.getQuantite())* Integer.parseInt(maPieceOtInfos.getPrix());
			total = total + totalPiece;
		}
	}
	// on regarde s'il y a des bons d'engagement rattachés
	ArrayList listBe = BeOtInfos.listerBeOtInfosOT(aTransaction,unOT.getNumeroot());
	if(aTransaction.isErreur()){
		//il n'y a pas de bons d'engagement pour cet OT
		aTransaction.traiterErreur();
	}else{
		// on cherche le montant des bons d'engagement
		if(listBe.size()>0){
			for(int i=0;i<listBe.size();i++){
				BeOtInfos monBeOTInfos = (BeOtInfos)listBe.get(i);
				String enju = monBeOTInfos.getNoengj();
				//String exercice = enju.substring(0,4);
				// on recherche les renseignements concernant le fournisseur
				ENJU unEnju = ENJU.chercherENJUBE(aTransaction,enju);
				if(aTransaction.isErreur()){
					aTransaction.traiterErreur();
					return -2;
				}
				if(unEnju!=null){
					//	 recherche des lignes du bon d'engagement
					ArrayList listLeju = LEJU.listerLEJUBE(aTransaction,unEnju.getExerci(),unEnju.getNoengju());
					if(aTransaction.isErreur()){
						// traiter l'erreur et retourne -1
						aTransaction.traiterErreur();
						return -1;
					}
					if(listLeju.size()>0){
						for(int j=0;j<listLeju.size();j++){
							LEJU unLeju = (LEJU)listLeju.get(j);
							codeDep = unLeju.getCddep().substring(1,5);
							if(codeDep.equals(unOT.getNuminv())||unLeju.getCddep().equals(unOT.getNuminv())){
								int montant = Integer.parseInt(unLeju.getMtlengju());
								total = total + montant;
							}
						}
					}
				}
			}
		}
		
	}
	if (aTransaction.isErreur()){
		return -1;
	}
	return total;
}
*/
/**
 * Constructeur OT.
 */
public OT() {
	super();
}
/**
 * Getter de l'attribut numeroot.
 */
public String getNumeroot() {
	return numeroot;
}
/**
 * Setter de l'attribut numeroot.
 */
public void setNumeroot(String newNumeroot) { 
	numeroot = newNumeroot;
}
/**
 * Getter de l'attribut dateentree.
 */
public String getDateentree() {
	return dateentree;
}
/**
 * Setter de l'attribut dateentree.
 */
public void setDateentree(String newDateentree) { 
	dateentree = newDateentree;
}
/**
 * Getter de l'attribut datesortie.
 */
public String getDatesortie() {
	return datesortie;
}
/**
 * Setter de l'attribut datesortie.
 */
public void setDatesortie(String newDatesortie) { 
	datesortie = newDatesortie;
}
/**
 * Getter de l'attribut compteur.
 */
public String getCompteur() {
	return compteur;
}
/**
 * Setter de l'attribut compteur.
 */
public void setCompteur(String newCompteur) { 
	compteur = newCompteur;
}
/**
 * Getter de l'attribut valide.
 */
public String getValide() {
	return valide;
}
/**
 * Setter de l'attribut valide.
 */
public void setValide(String newValide) { 
	valide = newValide;
}
/**
 * Getter de l'attribut numerobc.
 */
public String getNumerobc() {
	return numerobc;
}
/**
 * Setter de l'attribut numerobc.
 */
public void setNumerobc(String newNumerobc) { 
	numerobc = newNumerobc;
}
/**
 * Getter de l'attribut commentaire.
 */
public String getCommentaire() {
	return commentaire;
}
/**
 * Setter de l'attribut commentaire.
 */
public void setCommentaire(String newCommentaire) { 
	commentaire = newCommentaire;
}
/**
 * Getter de l'attribut numinv.
 */
public String getNuminv() {
	return numinv;
}
/**
 * Setter de l'attribut numinv.
 */
public void setNuminv(String newNuminv) { 
	numinv = newNuminv;
}

/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected BasicBroker definirMyBroker() { 
	return new OTBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected OTBroker getMyOTBroker() {
	return (OTBroker)getMyBasicBroker();
}
}
