package nc.mairie.seat.metier;

import java.util.Calendar;

import nc.mairie.technique.Services;
/**
 * Objet métier PM_PePerso
 */
public class PM_PePerso extends nc.mairie.technique.BasicMetier {
	public String codepmpep;
	public String pminv;
	public String numfiche;
	public String codeentretien;
	public String dprev;
	public String dreal;
	public String duree;
	public String sinistre;
	public String commentaire;
/**
 * Constructeur PM_PePerso.
 */
public PM_PePerso() {
	super();
}
/**
 * Getter de l'attribut codepmpep.
 */
public String getCodepmpep() {
	return codepmpep;
}
/**
 * Setter de l'attribut codepmpep.
 */
public void setCodepmpep(String newCodepmpep) { 
	codepmpep = newCodepmpep;
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
 * Getter de l'attribut dprev.
 */
public String getDprev() {
	return dprev;
}
/**
 * Setter de l'attribut dprev.
 */
public void setDprev(String newDprev) { 
	dprev = newDprev;
}
/**
 * Getter de l'attribut dreal.
 */
public String getDreal() {
	return dreal;
}
/**
 * Setter de l'attribut dreal.
 */
public void setDreal(String newDreal) { 
	dreal = newDreal;
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
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new PM_PePersoBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected PM_PePersoBroker getMyPM_PePersoBroker() {
	return (PM_PePersoBroker)getMyBasicBroker();
}
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
 * Retourne un ArrayList d'objet métier : PM_PePerso.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerPM_PePerso(nc.mairie.technique.Transaction aTransaction) throws Exception{
	PM_PePerso unPM_PePerso = new PM_PePerso();
	return unPM_PePerso.getMyPM_PePersoBroker().listerPM_PePerso(aTransaction);
}
/**
 * Retourne un PM_PePerso.
 * @return PM_PePerso
 */
public static PM_PePerso chercherPM_PePerso(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PM_PePerso unPM_PePerso = new PM_PePerso();
	return unPM_PePerso.getMyPM_PePersoBroker().chercherPM_PePerso(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */
public boolean creerPM_PePerso(nc.mairie.technique.Transaction aTransaction,PMateriel unPMateriel,Entretien unEntretien)  throws Exception {
//	 on vérifie que les objets ne sont pas null
	if (null == unPMateriel.getPminv()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Petit Matériel"));
		return false;
	}
	if (null == unEntretien.getCodeentretien()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Entretien"));
		return false;
	}
	if(aTransaction.isErreur()){
		return false;
	}
	
	//RG : controle si les dates sont correctes
	//RG : la date de prévision et de réalisation doivent être supérieures ou égales à la date de mise en circulation du petit matériel
//	RG : controle de l'année (l'année saisie ne doit pas être supérieur à l'année en cours)
	String annee = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	String dateAnnee = Services.formateDate("31/12/"+annee);
	if((getDprev()!=null)&&(!getDprev().equals(""))){
		if(!Services.estUneDate(getDprev())){
			aTransaction.declarerErreur("La date de prévision n'est pas correcte");
			return false;
		}else{
			setDprev(Services.formateDate(getDprev()));
		}
		if((unPMateriel.getDmes()!=null)&&(!unPMateriel.getDmes().equals(""))){
			int compare = Services.compareDates(unPMateriel.getDmes(),getDprev());
			if(compare==1){
				aTransaction.declarerErreur("La date de prévision doit être supérieure ou égale à la date de mise en circulation.");
				return false;
			}else if (compare==-9999){
				return false;
			}
		}
		//Services.convertitDate(Services.formateDate(getDateentree()),"dd/mm/yyyy","yyyy");
		String date = Services.formateDate(getDprev());
		int compare = Services.compareDates(date,dateAnnee);
		if(compare==1){
			aTransaction.declarerErreur("La date de prévision ne doit pas être supérieur au "+dateAnnee);
			return false;
		}else if(compare==-9999){
			return false;
		}
	}
	if((getDreal()!=null)&&(!getDreal().equals(""))&&(!getDreal().equals(("01/01/0001")))){
		if(!Services.estUneDate(getDreal())){
			aTransaction.declarerErreur("La date de réalisation n'est pas correcte.");
			return false;
		}else{
			setDreal(Services.formateDate(getDreal()));
		}
		if((unPMateriel.getDmes()!=null)&&(!unPMateriel.getDmes().equals(""))){
			int compare = Services.compareDates(unPMateriel.getDmes(),getDreal());
			if(compare==1){
				aTransaction.declarerErreur("La date de réalisation doit être supérieure ou égale à la date de mise en circulation.");
				return false;
			}else if (compare==-9999){
				return false;
			}
		}
//		si une fiche d'entretien rattaché à l'entretien à faire
		if((getNumfiche()!=null)&&(getNumfiche().equals(""))){
			FPM unPMatFiche = FPM.chercherFPM(aTransaction,getNumfiche());
			if(aTransaction.isErreur()){
				return false;
			}
			// la date de réalisation <= date de sortie du petit matériel
			if((unPMatFiche.getDentree()!=null)&&(!unPMatFiche.getDsortie().equals(""))){
				int compare = Services.compareDates(unPMatFiche.getDentree(),getDreal());
				if(compare==1){
					aTransaction.declarerErreur("La date de réalisation doit être supérieure ou égale à la date d'entrée du petit matériel.");
					return false;
				}else if(compare==-9999){
					return false;
				}
			}
			//la date de réalisation <= date de sortie du petit matériel
			if((unPMatFiche.getDsortie()!=null)&&(!unPMatFiche.getDsortie().equals(""))){
				int compare = Services.compareDates(getDreal(),unPMatFiche.getDsortie());
				if(compare==1){
					aTransaction.declarerErreur("La date de réalisation doit être inférieur ou égale à la date de sortie du petit matériel.");
					return false;
				}else if(compare==-9999){
					return false;
				}
			}
		}
		String date = Services.formateDate(getDreal());
		int compare = Services.compareDates(date,dateAnnee);
		if(compare==1){
			aTransaction.declarerErreur("La date de réalisation ne doit pas être supérieur au "+dateAnnee);
			return false;
		}else if(compare==-9999){
			return false;
		}
	}
		
	// si sinistre pas renseigné alors non
	if (getSinistre()==null){
		setSinistre("N");
	}
	//calcul de la date de prévision
	int nbjours = 0; 
	
	
	setPminv(unPMateriel.getPminv());
	setCodeentretien(unEntretien.getCodeentretien());
	//	on ajoute le code du peperso
	int nouvcodepep = nouvPmPePerso(aTransaction);
	setCodepmpep(String.valueOf(nouvcodepep));
	//Creation du PM_PePerso
	return getMyPM_PePersoBroker().creerPM_PePerso(aTransaction);
}

// nouveau code
public int nouvPmPePerso(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//	recherche du dernier codepneu
	int nouvcodePePerso = getMyPM_PePersoBroker().nouvPmPePerso(aTransaction);
	//si pas trouvé
	if (nouvcodePePerso == -1) {
		//fonctionnellement normal: table vide
		nouvcodePePerso = 1;
	} else {
		nouvcodePePerso++;
	}
	return nouvcodePePerso;
}

/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierPM_PePerso(nc.mairie.technique.Transaction aTransaction,PMateriel unPMateriel,Entretien unEntretien)  throws Exception {
//		 on vérifie que les objets ne sont pas null
		if (null == unPMateriel.getPminv()){
			aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Petit Matériel"));
			return false;
		}
		if (null == unEntretien.getCodeentretien()){
			aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Entretien"));
			return false;
		}
		
//		RG : controle si les dates sont correctes
		//RG : la date de prévision et de réalisation doivent être supérieures ou égales à la date de mise en circulation du petit matériel
//		RG : controle de l'année (l'année saisie ne doit pas être supérieur à l'année en cours)
		String annee = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		String dateAnnee = Services.formateDate("31/12/"+annee);
		if((getDprev()!=null)&&(!getDprev().equals(""))&&(!getDprev().equals("01/01/0001"))){
			if(!Services.estUneDate(getDprev())){
				aTransaction.declarerErreur("La date de prévision n'est pas correcte");
				return false;
			}
			if((unPMateriel.getDmes()!=null)&&(!unPMateriel.getDmes().equals(""))){
				int compare = Services.compareDates(unPMateriel.getDmes(),getDprev());
				if(compare==1){
					aTransaction.declarerErreur("La date de prévision doit être supérieure ou égale à la date de mise en circulation.");
					return false;
				}else if (compare==-9999){
					return false;
				}
			}
			//Services.convertitDate(Services.formateDate(getDateentree()),"dd/mm/yyyy","yyyy");
			String date = Services.formateDate(getDprev());
			int compare = Services.compareDates(date,dateAnnee);
			if(compare==1){
				aTransaction.declarerErreur("La date de prévision ne doit pas être supérieur au "+dateAnnee);
				return false;
			}else if(compare==-9999){
				return false;
			}
		}
		if((getDreal()!=null)&&(!getDreal().equals(""))&&(!getDreal().equals(("01/01/0001")))){
			if(!Services.estUneDate(getDreal())){
				aTransaction.declarerErreur("La date de réalisation n'est pas correcte.");
				return false;
			}
			if((unPMateriel.getDmes()!=null)&&(!unPMateriel.getDmes().equals(""))&&(!"01/01/0001".equals(unPMateriel.getDmes()))){
				int compare = Services.compareDates(unPMateriel.getDmes(),getDreal());
				if(compare==1){
					aTransaction.declarerErreur("La date de réalisation doit être supérieure ou égale à la date de mise en circulation.");
					return false;
				}else if (compare==-9999){
					return false;
				}
			}
//			si unFPM rattaché à l'entretien à faire

			if((getNumfiche()!=null)&&(!getNumfiche().equals(""))){
				
				FPM unPMatFiche = FPM.chercherFPM(aTransaction,getNumfiche());
				if(aTransaction.isErreur()){
					return false;
				}
				//	 si la date de réalisation est renseignée alors le compteur de FPM rattaché doit être renseignée
				if((getDreal()!=null)&&(!getDreal().equals(("")))){
					Modeles unModele = Modeles.chercherModeles(aTransaction,unPMateriel.getCodemodele());
					if(aTransaction.isErreur()){
						aTransaction.declarerErreur("Le modèle n'a pas été trouvé.");
						return false;
					}
				}
				
				// la date de réalisation <= date de sortie du petit matériel
				if((unPMatFiche.getDentree()!=null)&&(!unPMatFiche.getDsortie().equals("01/01/0001"))&&(!unPMatFiche.getDsortie().equals(""))){
					int compare = Services.compareDates(unPMatFiche.getDentree(),getDreal());
					if(compare==1){
						aTransaction.declarerErreur("La date de réalisation doit être supérieure ou égale à la date d'entrée du petit matériel.");
						return false;
					}else if(compare==-9999){
						return false;
					}
				}
				//la date de réalisation <= date de sortie du petit matériel
				if((unPMatFiche.getDsortie()!=null)&&(!unPMatFiche.getDsortie().equals("01/01/0001"))&&(!unPMatFiche.getDsortie().equals(""))){
					int compare = Services.compareDates(getDreal(),unPMatFiche.getDsortie());
					if(compare==1){
						aTransaction.declarerErreur("La date de réalisation doit être inférieur ou égale à la date de sortie du petit matériel.");
						return false;
					}else if(compare==-9999){
						return false;
					}
				}
			}
			String date = Services.formateDate(getDreal());
			int compare = Services.compareDates(dateAnnee,date);
			if(compare==-1){
				aTransaction.declarerErreur("La date de réalisation ne doit pas être supérieur au "+dateAnnee);
				return false;
			}else if(compare==-9999){
				return false;
			}
		}		
//		 si sinistre pas renseigné alors non
		if (getSinistre()==null){
			setSinistre("N");
		}
		
		
		setPminv(unPMateriel.getPminv());
		setCodeentretien(unEntretien.getCodeentretien());
		
	//Modification du PM_PePerso
	return getMyPM_PePersoBroker().modifierPM_PePerso(aTransaction);
}

/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 * Méthode modifier quand on a changé les infos et pas les objets étranger
 */
public boolean modifierPmPePersoInfos(nc.mairie.technique.Transaction aTransaction)  throws Exception {
//	 si sinistre pas renseigné alors non
	if (getSinistre()==null){
		setSinistre("non");
	}	
	// compteur
	//si unFPM rattaché à l'entretien à faire
//	 si la date de réalisation est renseignée alors le compteur de FPM rattaché doit être renseignée
	if((getDreal()!=null)&&(!getDreal().equals(("")))){
		if((getNumfiche()!=null)&&(!getNumfiche().equals(""))){
			FPM unFPM = FPM.chercherFPM(aTransaction,getNumfiche());
			if(aTransaction.isErreur()){
				return false;
			}
			PMateriel unPMateriel= PMateriel.chercherPMateriel(aTransaction,unFPM.getPminv());
			if(aTransaction.isErreur()){
				aTransaction.declarerErreur("Le petit matériel n'a pas été trouvé.");
				return false;
			}
		}
	}
		
	//Modification du PePerso
	return getMyPM_PePersoBroker().modifierPM_PePerso(aTransaction);
}

/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 */
public boolean supprimerPM_PePerso(nc.mairie.technique.Transaction aTransaction) throws Exception{
	// si le numéro de fiche d'entretiens est renseigné pas de suppression
	if((getNumfiche()!=null)&&(!getNumfiche().equals(""))){
		aTransaction.declarerErreur("Une fiche d'entretiens est rattaché, la suppression n'est donc pas possible.");
		return false;
	}else{
//		Suppression de l'PM_PePerso
		return getMyPM_PePersoBroker().supprimerPM_PePerso(aTransaction);
	}
}

// on cherche les entretiens de la fiche d'entretien
public static java.util.ArrayList chercherPmPePersoFiche(nc.mairie.technique.Transaction aTransaction,String numfiche) throws Exception{
	PM_PePerso unPM_PePerso = new PM_PePerso();
	return unPM_PePerso.getMyPM_PePersoBroker().chercherPmPePersoFiche(aTransaction,numfiche);
}

public static PM_PePerso chercherPmPePersoEquipEntPrevu(nc.mairie.technique.Transaction aTransaction,String inv,String ent) throws Exception{
	PM_PePerso unPM_PePerso = new PM_PePerso();
	return unPM_PePerso.getMyPM_PePersoBroker().chercherPePersoEquipEntPrevu(aTransaction,inv,ent);
}

public static java.util.ArrayList chercherPmPePersoPasFaitFPM(nc.mairie.technique.Transaction aTransaction,String numfiche) throws Exception{
	PM_PePerso unPM_PePerso = new PM_PePerso();
	return unPM_PePerso.getMyPM_PePersoBroker().chercherPmPePersoPasFaitFPM(aTransaction,numfiche);
}

// liste des peperso d'un PM pour une période (entretien annuel)
public static java.util.ArrayList listerPM_PePersoPm(nc.mairie.technique.Transaction aTransaction,String pminv,String ddeb) throws Exception{
	PM_PePerso unPM_PePerso = new PM_PePerso();
	return unPM_PePerso.getMyPM_PePersoBroker().listerPM_PePersoPm(aTransaction,pminv,ddeb);
}

public static java.util.ArrayList listerPM_PePersoPmFait(nc.mairie.technique.Transaction aTransaction,String pminv,String tri) throws Exception{
	PM_PePerso unPM_PePerso = new PM_PePerso();
	return unPM_PePerso.getMyPM_PePersoBroker().listerPM_PePersoPmFait(aTransaction,pminv,tri);
}

public static java.util.ArrayList listerPM_PePersoPMateriel(nc.mairie.technique.Transaction aTransaction,String pminv) throws Exception{
	PM_PePerso unPM_PePerso = new PM_PePerso();
	return unPM_PePerso.getMyPM_PePersoBroker().listerPM_PePersoPMateriel(aTransaction,pminv);
}

}
