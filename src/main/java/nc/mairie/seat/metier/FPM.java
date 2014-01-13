package nc.mairie.seat.metier;

import java.util.ArrayList;
import java.util.Calendar;

import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier FPM
 */
public class FPM extends BasicMetier {
	public String numfiche;
	public String pminv;
	public String dentree;
	public String dsortie;
	public String valide;

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
 * Retourne un ArrayList d'objet métier : FPM.
 * @return java.util.ArrayList
 */
public static ArrayList<FPM> listerFPM(nc.mairie.technique.Transaction aTransaction) throws Exception{
	FPM unPMateriel_Fiche = new FPM();
	return unPMateriel_Fiche.getMyFPMBroker().listerFPM(aTransaction);
}
/**
 * Retourne un FPM.
 * @return FPM
 */
public static FPM chercherFPM(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	FPM unPMateriel_Fiche = new FPM();
	return unPMateriel_Fiche.getMyFPMBroker().chercherFPM(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */
public boolean creerFPM(nc.mairie.technique.Transaction aTransaction,PMateriel unPMateriel)  throws Exception {
	// controle si null
	if (null == unPMateriel){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Petit matériel"));
		return false;
	}
	if((getDentree()!=null)&&(!getDentree().equals(""))){
		if(!Services.estUneDate(getDentree())){
			aTransaction.declarerErreur("La date d'entrée n'est pas correcte.");
			return false;
		}
	}
	if((getDsortie()!=null)&&(!getDsortie().equals(""))){
		if(!Services.estUneDate(getDsortie())){
			aTransaction.declarerErreur("La date de sortie n'est pas correcte.");
			return false;
		}
	}
//	RG : contrôle si la date entrée<=date de sortie
	if((getDentree()!=null)&&(getDsortie()!=null)&&(!getDentree().equals(""))&&(!getDsortie().equals(""))){
		
		int compare = Services.compareDates(getDentree(),getDsortie());
		if(compare==1){
			aTransaction.declarerErreur("La date d'entrée doit être inférieure ou égale à la date de sortie.");
			return false;
		}else if(compare==-9999){
			return false;
		}
		setDentree(Services.formateDate(getDentree()));
		setDsortie(Services.formateDate(getDsortie()));
	}
	
	//	RG : controle de l'année (l'année saisie ne doit pas être supérieur à l'année en cours)
	String annee = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	String dateAnnee = Services.formateDate("31/12/"+annee);
	
	//Date d'entrée
	if((getDentree()!=null)&&(!getDentree().equals(""))){
//		RG : date de sortie >= date de réalisation
		if((getNumfiche()!=null)&&(!getNumfiche().equals(""))){
			ArrayList<PM_PePerso> listEntretien = PM_PePerso.chercherPmPePersoFiche(aTransaction,getNumfiche());
			if(aTransaction.isErreur()){
				return false;
			}
			if(listEntretien.size()>0){
				for(int i=0;i<listEntretien.size();i++){
					PM_PePerso unPeP = (PM_PePerso)listEntretien.get(i);
					if((unPeP.getDreal()!=null)&&(!unPeP.getDreal().equals("01/01/0001"))){
						int compare = Services.compareDates(getDentree(),unPeP.getDreal());
						if(compare==1){
							aTransaction.declarerErreur("La date d'entrée doit être inférieure ou égale à la date de réalisation de l'entretien.("+unPeP.getDreal()+")");
							return false;
						}else if(compare==-9999){
							return false;
						}
					}
				}
			}
		}
		
		//Services.convertitDate(Services.formateDate(getDateentree()),"dd/mm/yyyy","yyyy");
		String date = Services.formateDate(getDentree());
		int compare = Services.compareDates(dateAnnee,date);
		if(compare==-1){
			aTransaction.declarerErreur("La date d'entrée ne doit pas être supérieur au "+dateAnnee);
			return false;
		}else if(compare==-9999){
			return false;
		}
	}
	
	//Date de sortie
	if((getDsortie()!=null)&&(!getDsortie().equals(("")))){
		String date = Services.formateDate(getDsortie());
		int compare = Services.compareDates(date,dateAnnee);
		if(compare==1){
			aTransaction.declarerErreur("La date de sortie ne doit pas être supérieur au "+dateAnnee);
			return false;
		}else if(compare==-9999){
			return false;
		}
	}
	//RG : date de sortie >= date de réalisation
	if((getDsortie()!=null)&&(!getDsortie().equals(""))){
		if((getNumfiche()!=null)&&(!getNumfiche().equals(""))){
			//ArrayList listEntretien = PePerso.chercherPePersoOT(aTransaction,getNumfiche());
			ArrayList<PM_PePerso> listEntretien = PM_PePerso.chercherPmPePersoFiche(aTransaction,getNumfiche());
			if(aTransaction.isErreur()){
				return false;
			}
			if(listEntretien.size()>0){
				for(int i=0;i<listEntretien.size();i++){
					PM_PePerso unPeP = (PM_PePerso)listEntretien.get(i);
					if((unPeP.getDreal()!=null)&&(!unPeP.getDreal().equals(""))){
						int compare = Services.compareDates(unPeP.getDreal(),getDsortie());
						if(compare==1){
							aTransaction.declarerErreur("La date de sortie doit être supérieur à la date de réalisation de l'entretien."+unPeP.getDreal()+")");
							return false;
						}else if(compare==-9999){
							return false;
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
	//le numéro de fiche d'entretien est de type aaaa11111
	//	 récupération de l'année courante
	//int annee = Calendar.getInstance().get(Calendar.YEAR);
	FPM unFPM = new FPM ();
	int derCodeOt = unFPM.nouvCodeFpm(aTransaction);
	int maxCode = nouvCodeFpm(aTransaction);
	if(derCodeOt>maxCode){
		maxCode = derCodeOt;
	}
	if (String.valueOf(maxCode).length()==8){
		String deb = String.valueOf(maxCode).substring(0,4);
		if (Integer.parseInt(annee)>Integer.parseInt(deb)){
			setNumfiche(annee+"0001"); 
		}else{
			setNumfiche(String.valueOf(maxCode));
		}
	}else{
		setNumfiche(annee+"0001");
	}

	setPminv(unPMateriel.getPminv());
	//Creation du FPM
	return getMyFPMBroker().creerFPM(aTransaction);
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvCodeFpm(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//	recherche du dernier code
//	recherche du dernier codeCarburant
	int nouvcode = getMyFPMBroker().nouvCodeFpm(aTransaction);
	if(aTransaction.isErreur()){
		aTransaction.traiterErreur();
	}
	//si pas trouvé
	if (nouvcode == -1) {
		//fonctionnellement normal: table vide
		nouvcode= 1;
	} else {
		nouvcode++;
	}
	
	return nouvcode;
}

/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierPMateriel_Fiche(nc.mairie.technique.Transaction aTransaction,PMateriel unPMateriel) throws Exception {
//	 controle si null
	if (null == unPMateriel){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Petit matériel"));
		return false;
	}
	if(aTransaction.isErreur()){
		return false;
	}
	//RG : controle si les dates sont correctes
	if((getDentree()!=null)&&(!getDentree().equals(""))&&(!getDentree().equals("01/01/0001"))){
		if(!Services.estUneDate(getDentree())){
			aTransaction.declarerErreur("La date d'entree est incorrecte.");
			return false;
		}
	}
	if((getDsortie()!=null)&&(!getDsortie().equals(""))&&(!getDsortie().equals("01/01/0001"))){
		if(!Services.estUneDate(getDsortie())){
			aTransaction.declarerErreur("La date de sortie est incorrecte.");
			return false;
		}
	}
	
	//RG : contrôle si la date entrée<=date de sortie
	if((!getDentree().equals(""))&&(!getDsortie().equals(""))&&(!getDentree().equals("01/01/0001"))&&(!getDsortie().equals("01/01/0001"))){
		setDentree(Services.formateDate(getDentree()));
		setDsortie(Services.formateDate(getDsortie()));
		int compare = Services.compareDates(getDentree(),getDsortie());
		if(compare==1){
			aTransaction.declarerErreur("La date d'entrée doit être inférieure ou égale à la date de sortie.");
			return false;
		}else if(compare==-9999){
			return false;
		}
	}
	
	//	RG : controle de l'année (l'année saisie ne doit pas être supérieur à l'année en cours)
	String annee = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	String dateAnnee = Services.formateDate("31/12/"+annee);
	
	//Date d'entrée
	if((getDentree()!=null)&&(!getDentree().equals(""))){
//		RG : date de sortie >= date de réalisation
		ArrayList<PM_PePerso> listEntretien = PM_PePerso.chercherPmPePersoFiche(aTransaction,getNumfiche());
		if(aTransaction.isErreur()){
			// pas d'entretiens encore réaliser
			aTransaction.traiterErreur();
		}
		if(listEntretien.size()>0){
			for(int i=0;i<listEntretien.size();i++){
				PM_PePerso unPeP = (PM_PePerso)listEntretien.get(i);
				if((unPeP.getDreal()!=null)&&(!unPeP.getDreal().equals("01/01/0001"))&&(!("").equals(unPeP.getDreal()))){
					int compare = Services.compareDates(getDentree(),unPeP.getDreal());
					if(compare==1){
						aTransaction.declarerErreur("La date d'entrée doit être inférieure ou égale à la date de réalisation de l'entretien.("+unPeP.getDreal()+")");
						return false;
					}else if(compare==-9999){
						return false;
					}
				}
			}
		}
		//Services.convertitDate(Services.formateDate(getDateentree()),"dd/mm/yyyy","yyyy");
		String date = Services.formateDate(getDentree());
		int compare = Services.compareDates(date,dateAnnee);
		if(compare==1){
			aTransaction.declarerErreur("La date d'entrée ne doit pas être supérieur au "+dateAnnee);
			return false;
		}else if(compare==-9999){
			return false;
		}
	}
	
	//Date de sortie
	if((getDsortie()!=null)&&(!getDsortie().equals(("")))){
		String date = Services.formateDate(getDsortie());
		int compare = Services.compareDates(date,dateAnnee);
		if(compare==1){
			aTransaction.declarerErreur("La date de sortie ne doit pas être supérieur au "+dateAnnee);
			return false;
		}else if(compare==-9999){
			return false;
		}
	}
	//RG : date de sortie >= date de réalisation
	ArrayList<PM_PePerso> listEntretien = PM_PePerso.chercherPmPePersoFiche(aTransaction,getNumfiche());
	if(aTransaction.isErreur()){
		// pas d'entretiens à réalisé enregistré
		aTransaction.traiterErreur();
	}
	if(listEntretien.size()>0){
		for(int i=0;i<listEntretien.size();i++){
			PM_PePerso unPeP = (PM_PePerso)listEntretien.get(i);
			if((unPeP.getDreal()!=null)&&(!unPeP.getDreal().equals("01/01/0001"))&&(!("").equals(unPeP.getDreal()))){
				int compare = Services.compareDates(unPeP.getDreal(),getDsortie());
				if(compare==1){
					aTransaction.declarerErreur("La date de sortie doit être supérieur à la date de réalisation de l'entretien."+unPeP.getDreal()+")");
					return false;
				}else if(compare==-9999){
					return false;
				}
			}
		}
	}
	
	setPminv(unPMateriel.getPminv());
	//Modification du FPM
	return getMyFPMBroker().modifierFPM(aTransaction);
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 */
public boolean supprimerPMateriel_Fiche(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'FPM
	return getMyFPMBroker().supprimerFPM(aTransaction);
}

public boolean suppressionFPM(nc.mairie.technique.Transaction aTransaction) throws Exception{
	// RG controle : si la FPM a eu des interventions de mécaniciens : pas de suppression possible
	ArrayList<PM_ATM> listIntervenant = PM_ATM.listerPM_ATM_FPM(aTransaction,getNumfiche());
	if(aTransaction.isErreur()){
		return false;
	}
	if(listIntervenant.size()>0){
		aTransaction.declarerErreur("La fiche d'entretiens ("+getNumfiche()+") a provoqué une intervention de mécaniciens donc elle ne peut pas être supprimée.");
		return false;
	}
	//RG controle : si la FPM fait l'objet d'un Bon d'engagement ==> FPM ne peut pas être supprimé
	ArrayList<PM_BE> listBe = PM_BE.listerPM_BE_FPM(aTransaction,getNumfiche());
	if(aTransaction.isErreur()){
		return false;
	}
	if(listBe.size()>0){
		aTransaction.declarerErreur("La fiche de petit matériel ("+getNumfiche()+") est rattaché à au moins un Bon d'engagement donc elle ne peut être supprimée.");
		return false;
	}
//	RG controle : si des pièces ont été sorties pour ce FPM ==> FPM ne peut pas être supprimé
	ArrayList<Pieces_FPM> listPieces = Pieces_FPM.listerPieces_FPMFPM(aTransaction,getNumfiche());
	if(aTransaction.isErreur()){
		return false;
	}
	if(listPieces.size()>0){
		aTransaction.declarerErreur("La fiche de petit matériel ("+getNumfiche()+") a utilisée des pièces donc elle ne peut être supprimée.");
		return false;
	}
//	RG controle : si la FPM fait l'objet d'un Bon d'engagement ==> FPM ne peut pas être supprimé
//	ArrayList listPieces = PM_p.listerPM_BE_FPM(aTransaction,getNumfiche());
//	if(aTransaction.isErreur()){
//		return false;
//	}
//	if(listBe.size()>0){
//		aTransaction.declarerErreur("La fiche de petit matériel ("+getNumfiche()+") est rattaché à au moins un Bon d'engagement donc il ne peut être supprimé.");
//		return false;
//	}
	//pour tous les entretiens prévus dans cet FPM on modifie pour mettre le PePerso.numfiche à null
	ArrayList<PM_PePerso> listEntretiens = PM_PePerso.chercherPmPePersoFiche(aTransaction,getNumfiche());
	if(aTransaction.isErreur()){
		return false;
	}
	if(listEntretiens.size()>0){
		for(int i=0;i<listEntretiens.size();i++){
			PM_PePerso unPePerso = (PM_PePerso)listEntretiens.get(i);
			if((!unPePerso.getDreal().equals(""))&&(!"01/01/0001".equals(unPePerso.getDreal()))&&(!"0001-01-01".equals(unPePerso.getDreal()))){
				aTransaction.declarerErreur("La suppression de la fiche d'entretiens n'est pas possible car un entretien rattaché à cette fiche a été effectuée.");
				return false;
			}
			unPePerso.setNumfiche("");
			unPePerso.modifierPmPePersoInfos(aTransaction);
			if(aTransaction.isErreur()){
				return false;
			}
		}
	}
	
	//suppression de FPM
	return supprimerPMateriel_Fiche(aTransaction);
}

	public String commentaire;
/**
 * Constructeur FPM.
 */
public FPM() {
	super();
}
/**
 * Getter de l'attribut numfiche.
 */
public String getNumfiche() {
	return numfiche;
}
/**
 * Setter de l'attribut numfiche.
 */
public void setNumfiche(String newNumfiche) { 
	numfiche = newNumfiche;
}
/**
 * Getter de l'attribut pminv.
 */
public String getPminv() {
	return pminv;
}
/**
 * Setter de l'attribut pminv.
 */
public void setPminv(String newPminv) { 
	pminv = newPminv;
}
/**
 * Getter de l'attribut dentree.
 */
public String getDentree() {
	return dentree;
}
/**
 * Setter de l'attribut dentree.
 */
public void setDentree(String newDentree) { 
	dentree = newDentree;
}
/**
 * Getter de l'attribut dsortie.
 */
public String getDsortie() {
	return dsortie;
}
/**
 * Setter de l'attribut dsortie.
 */
public void setDsortie(String newDsortie) { 
	dsortie = newDsortie;
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
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new FPMBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected FPMBroker getMyFPMBroker() {
	return (FPMBroker)getMyBasicBroker();
}
/* OLD fonction 20100121
public int calculCoutTotal(nc.mairie.technique.Transaction aTransaction,FPM unFPM) throws Exception{
	if (null == unFPM){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","fiche d'entretiens du petit matériel"));
		return -1;
	}
	int total = 0;
	int totalPiece = 0;
	String codeDep;
	
//	 on parcours les pièces
	ArrayList listPieces = Pieces_FPM.listerPieces_FPMFPM(aTransaction,unFPM.getNumfiche());
	if(aTransaction.isErreur()){
		// il n'y a pas de pièces associé à l'OT
		aTransaction.traiterErreur();
	}else{
		// calcul du montant des pièces
		for (int i=0;i<listPieces.size();i++){
			Pieces_FPM maPieceOtInfos = (Pieces_FPM)listPieces.get(i);
			totalPiece = Integer.parseInt(maPieceOtInfos.getQuantite())* Integer.parseInt(maPieceOtInfos.getPrix());
			total = total + totalPiece;
		}
	}
	
	// on regarde s'il y a des bons d'engagement rattachés
	ArrayList listBe = BeFpmInfos.listerBeFpmInfosFpm(aTransaction,unFPM.getNumfiche());
	if(aTransaction.isErreur()){
		//il n'y a pas de bons d'engagement pour cet FPM
		aTransaction.traiterErreur();
	}else{
		// on cherche le montant des bons d'engagement
		for(int i=0;i<listBe.size();i++){
			BeFpmInfos monBeFpmInfos = (BeFpmInfos)listBe.get(i);
			String enju = monBeFpmInfos.getNoengj();
			// on recherche les renseignements concernant le fournisseur
			ENJU unEnju = ENJU.chercherENJUBE(aTransaction,enju);
			if(aTransaction.isErreur()){
				return -2;
			}
			//	 recherche des lignes du bon d'engagement
			ArrayList listLeju = LEJU.listerLEJUBE(aTransaction,unEnju.getExerci(),unEnju.getNoengju());
			if(aTransaction.isErreur()){
				// traite l'erreur et retourne -1 pour le calcul
				aTransaction.traiterErreur();
				return -1;
			}
			if(listLeju.size()>0){
				for(int j=0;j<listLeju.size();j++){
					LEJU unLeju = (LEJU)listLeju.get(j);
					codeDep = unLeju.getCddep().substring(1,5);
					if(codeDep.equals(unFPM.getPminv())||unLeju.getCddep().equals(unFPM.getPminv())){
						int montant = Integer.parseInt(unLeju.getMtlengju());
						total = total + montant;
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

// liste des à valider
public static ArrayList<FPM> listerFPMAValider(nc.mairie.technique.Transaction aTransaction) throws Exception{
	FPM unPMateriel_Fiche = new FPM();
	return unPMateriel_Fiche.getMyFPMBroker().listerFPMAValider(aTransaction);
}

/*Methode creerObjetMetier qui retourne
 * true ou false
 * On valide la fiche d'entretien 
 */
public String validationFPM(nc.mairie.technique.Transaction aTransaction )  throws Exception {
	setValide("T");
	// si la date d'entree ou le compteur n'est pas renseigné : on ne peut pas valider.
	// le compteur est obligatoire si ce n'est pas un petit matériel
	
	if((getDentree().equals("01/01/0001"))||(getDentree().equals(""))){
		aTransaction.declarerErreur("Veuillez vérifier que la date d'entrée soit renseignée.");
		return "erreur";
	}
	
	// si date de sortie n'est pas renseigné on met la date du jour de validation
	if((getDsortie().equals("01/01/0001"))||(getDsortie().equals(""))){
		setDsortie(Services.dateDuJour());
	}
	// controle de la Date
	// la date de sortie du véhicule ne doit pas être inférieur aux dates de réalisation des entretiens
	ArrayList<PM_PePerso> listPePersoPasFait = PM_PePerso.chercherPmPePersoPasFaitFPM(aTransaction,getNumfiche());
	if(aTransaction.isErreur()){
		return "erreur";
	}
	String msg ="";
//	 si certains entretiens n'ont pas été fait on informe l'utilisateur
	if(listPePersoPasFait.size()>0){
		msg = "Attention certaines tâches de la FPM "+getNumfiche()+" n'ont pas été réalisées.";
	}else{
		msg = "";
	}
	
	ArrayList<PM_PePerso> listPePerso = PM_PePerso.chercherPmPePersoFiche(aTransaction,getNumfiche());
	if(aTransaction.isErreur()){
		return "erreur";
	}
	if(listPePerso.size()>0){
		for (int i = 0;i<listPePerso.size();i++){
			PM_PePerso unPePerso = (PM_PePerso)listPePerso.get(i);
			if((!getDsortie().equals(""))&&(getDsortie()!=null)&&(!("").equals(unPePerso.getDreal()))&&(unPePerso.getDreal()!=null)){
				int compare = Services.compareDates(getDsortie(),unPePerso.getDreal());
				if(compare==-9999){
					return "erreur";
				}else if(compare==-1){
					aTransaction.declarerErreur("La date de sortie doit être supérieur ou égale à la date de réalisation des entretiens.");
					return "erreur";
				}
			}
		}
	}

	PMateriel unPMateriel = PMateriel.chercherPMateriel(aTransaction,getPminv());
	if(aTransaction.isErreur()){
		return "erreur";
	}
	modifierPMateriel_Fiche(aTransaction,unPMateriel);	
	if(!msg.equals("")){
		return msg;
	}
	return "ok";
}

public static ArrayList<FPM> listerFpmPmat(nc.mairie.technique.Transaction aTransaction,String numinv) throws Exception{
	FPM unFPM = new FPM();
	return unFPM.getMyFPMBroker().listerFpmPmat(aTransaction,numinv);
}

public String creationFPMDeclaration(nc.mairie.technique.Transaction aTransaction,PMateriel unPMateriel)  throws Exception {
	
//	 controle si null
	if (null == unPMateriel){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Petit matériel"));
		return "erreur";
	}
	setPminv(unPMateriel.getPminv());
	creerFPM(aTransaction,unPMateriel);
	return getNumfiche();
}

}
