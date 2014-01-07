package nc.mairie.seat.metier;

import java.util.ArrayList;
/**
 * Objet métier ENGJU
 */
public class ENGJU extends nc.mairie.technique.BasicMetier {
	public String exerci;
	public String idetbs;
	public String enscom;
	public String noengj;
	public String nlengju;
	public String cddep;
	public String mtlenju;
	/**
	 * Constructeur ENGJU.
	 */
	public ENGJU() {
		super();
	}
	/**
	 * Getter de l'attribut exerci.
	 */
	public String getExerci() {
		return exerci;
	}
	/**
	 * Setter de l'attribut exerci.
	 */
	public void setExerci(String newExerci) { 
		exerci = newExerci;
	}
	/**
	 * Getter de l'attribut idetbs.
	 */
	public String getIdetbs() {
		return idetbs;
	}
	/**
	 * Setter de l'attribut idetbs.
	 */
	public void setIdetbs(String newIdetbs) { 
		idetbs = newIdetbs;
	}
	/**
	 * Getter de l'attribut enscom.
	 */
	public String getEnscom() {
		return enscom;
	}
	/**
	 * Setter de l'attribut enscom.
	 */
	public void setEnscom(String newEnscom) { 
		enscom = newEnscom;
	}
	/**
	 * Getter de l'attribut noengj.
	 */
	public String getNoengj() {
		return noengj;
	}
	/**
	 * Setter de l'attribut noengj.
	 */
	public void setNoengj(String newNoengj) { 
		noengj = newNoengj;
	}
	/**
	 * Getter de l'attribut nlengju.
	 */
	public String getNlengju() {
		return nlengju;
	}
	/**
	 * Setter de l'attribut nlengju.
	 */
	public void setNlengju(String newNlengju) { 
		nlengju = newNlengju;
	}
	/**
	 * Getter de l'attribut cddep.
	 */
	public String getCddep() {
		return cddep;
	}
	/**
	 * Setter de l'attribut cddep.
	 */
	public void setCddep(String newCddep) { 
		cddep = newCddep;
	}
	/**
	 * Getter de l'attribut mtlenju.
	 */
	public String getMtlenju() {
		return mtlenju;
	}
	/**
	 * Setter de l'attribut mtlenju.
	 */
	public void setMtlenju(String newMtlenju) { 
		mtlenju = newMtlenju;
	}
	/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
	 */
	protected nc.mairie.technique.BasicBroker definirMyBroker() { 
		return new ENGJUBroker(this); 
	}
	/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
	 */
	protected ENGJUBroker getMyENGJUBroker() {
		return (ENGJUBroker)getMyBasicBroker();
	}
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
	 * Retourne un ArrayList d'objet métier : ENGJU.
	 * @return java.util.ArrayList
	 */
	public static java.util.ArrayList listerENGJUGroupByCdepNoengjIdetbs(nc.mairie.technique.Transaction aTransaction, String numOt, String cddep) throws Exception{
		ENGJU unENGJU = new ENGJU();
		return unENGJU.getMyENGJUBroker().listerENGJUGroupByCdepNoengjIdetbs(aTransaction,numOt,cddep);
	}
	/**
	 * Retourne un ArrayList d'objet métier : ENGJU.
	 * @return java.util.ArrayList
	 */
	public static java.util.ArrayList listerENGJU(nc.mairie.technique.Transaction aTransaction,String exerci, String noengj) throws Exception{
		ENGJU unENGJU = new ENGJU();
		return unENGJU.getMyENGJUBroker().listerENGJU(aTransaction,exerci,noengj);
	}
	/**
	 * Retourne un ENGJU.
	 * @return ENGJU
	 */
	public static ENGJU chercherENGJU(nc.mairie.technique.Transaction aTransaction,String exerci, String noengj, String nlengju) throws Exception{
		ENGJU unENGJU = new ENGJU();
		return unENGJU.getMyENGJUBroker().chercherENGJU(aTransaction, exerci, noengj, nlengju);
	}
	/**
	 * Retourne un ENGJU.
	 * @return ENGJU
	 */
	public static ENGJU chercherpremierENGJU(nc.mairie.technique.Transaction aTransaction,String exerci, String noengj) throws Exception{
		ENGJU unENGJU = new ENGJU();
		return unENGJU.getMyENGJUBroker().chercherpremierENGJU(aTransaction, exerci, noengj);
	}
	/**
	 * Retourne un ENGJU.
	 * @return ENGJU
	 */
	public static ENGJU chercherdernierENGJU(nc.mairie.technique.Transaction aTransaction, String noengj) throws Exception{
		ENGJU unENGJU = new ENGJU();
		return unENGJU.getMyENGJUBroker().chercherdernierENGJU(aTransaction, noengj);
	}
	
	/**
	 * Retourne un ArrayList d'objet métier : Fournisseurs.
	 * @return java.util.ArrayList
	 */
	//NOT USED 20100121
	public static java.util.ArrayList listerFournisseursNom(nc.mairie.technique.Transaction aTransaction,String param) throws Exception{
		ENGJU unENGJU = new ENGJU();
		return unENGJU.getMyENGJUBroker().listerFournisseursNom(aTransaction,param);
	}
	/**
	 * Methode creerObjetMetier qui retourne
	 * true ou false
	 */
	public boolean creerENGJU(nc.mairie.technique.Transaction aTransaction )  throws Exception {
		//Creation du ENGJU
		return getMyENGJUBroker().creerENGJU(aTransaction);
	}
	/**
	 * Methode modifierObjetMetier qui retourne
	 * true ou false
	 */
	public boolean modifierENGJU(nc.mairie.technique.Transaction aTransaction) throws Exception {
		//Modification du ENGJU
		return getMyENGJUBroker().modifierENGJU(aTransaction);
	}
	/**
	 * Methode supprimerObjetMetier qui retourne
	 * true ou false
	 */
	public boolean supprimerENGJU(nc.mairie.technique.Transaction aTransaction) throws Exception{
		//Suppression de l'ENGJU
		return getMyENGJUBroker().supprimerENGJU(aTransaction);
	}

	public static int CoutTotal_PM(nc.mairie.technique.Transaction aTransaction,FPM unFPM) throws Exception{
		if (null == unFPM){
			aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","fiche d'entretiens du petit matériel"));
			return 0;
		}
		//ENGJU unENGJU = new ENGJU();
		int total = 0;

		/*LB optimisation 19/09/11
//		on parcours les pièces
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
	*/
		int totalOT = PiecesFpmInfos.cumuleMontantFpmInfos(aTransaction, unFPM.getNumfiche());
		//On peut ne pas avoir de lignes
		if (aTransaction.isErreur() || totalOT == -1) {
			aTransaction.traiterErreur();
			totalOT =0;
		}

		/*optimisation LB 19/08/11
//		on regarde s'il y a des bons d'engagement rattachés
		ArrayList listBe = BeFpmInfos.listerBeFpmInfosFpm(aTransaction,unFPM.getNumfiche());
		if(aTransaction.isErreur()){
			//il n'y a pas de bons d'engagement pour cet FPM
			aTransaction.traiterErreur();
		}else{
			for(int i=0;i<listBe.size();i++){
				BeFpmInfos monBeFpmInfos = (BeFpmInfos)listBe.get(i);
				String enju = monBeFpmInfos.getNoengj();
				try{
					unENGJU = unENGJU.getMyENGJUBroker().chercherpremierENGJU(aTransaction, enju);
				}catch (Exception e){
					System.out.println("Exception in getmontantBE"+e);
					if(aTransaction.isErreur()){
						// traite l'erreur et retourne 0
						aTransaction.traiterErreur();
						return 0;
					}
					continue;
				}
				if (null==unENGJU)
					continue;

				total=total+montantBE_PM(aTransaction,unENGJU,unFPM.getPminv().trim());

			}
		}
		*/
		int totalBE = BeFpmInfos.cumuleMontantBeFpmInfosBE(aTransaction, unFPM.getNumfiche(), unFPM.getPminv().trim());
		//On peut ne pas avoir de lignes
		if (aTransaction.isErreur() || totalBE == -1) {
			aTransaction.traiterErreur();
			totalBE =0;
		}
		
		total = totalOT+totalBE;
		return total;
	}

	
	public static int CoutTotal_OT(nc.mairie.technique.Transaction aTransaction,OT unOT) throws Exception{
		if (null == unOT){
			aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","OT"));
			return 0;
		}
	//	ENGJU unENGJU = new ENGJU();
		int total = 0;

		/*LB optimisation 12/9/11
//		 on parcours les pièces
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
		*/
		int totalOT = PiecesOtInfos.cumuleMontantPiecesOtInfosOT(aTransaction, unOT.getNumeroot());
		//On peut ne pas avoir de lignes
		if (aTransaction.isErreur() || totalOT == -1) {
			aTransaction.traiterErreur();
			totalOT =0;
		}
		//Fin LB optimisation 12/9/11

		/*LB optimisation 12/9/11
		// on regarde s'il y a des bons d'engagement rattachés
		ArrayList listBe = BeOtInfos.listerBeOtInfosOT(aTransaction,unOT.getNumeroot());
		if(aTransaction.isErreur()){
			//il n'y a pas de bons d'engagement pour cet FPM
			aTransaction.traiterErreur();
		}else{
			for(int i=0;i<listBe.size();i++){
				BeOtInfos monBeOTInfos = (BeOtInfos)listBe.get(i);
				String enju = monBeOTInfos.getNoengj();
				try{
					unENGJU = unENGJU.getMyENGJUBroker().chercherpremierENGJU(aTransaction, enju);
				}catch (Exception e){
					System.out.println("Exception in getmontantBE"+e);
					if(aTransaction.isErreur()){
						// traite l'erreur et retourne 0
						aTransaction.traiterErreur();
						return 0;
					}
					continue;
				}
				if (null==unENGJU)
					continue;

				total=total+montantBE_Equipement(aTransaction,unENGJU,unOT.getNuminv().trim());

			}
		}
		//Fin LB optimisation 12/9/11*/
		
		int totalBE = BeOtInfos.cumuleMontantPiecesOtInfosBE(aTransaction, unOT.getNumeroot(), unOT.getNuminv().trim());
		//On peut ne pas avoir de lignes
		if (aTransaction.isErreur() || totalBE == -1) {
			aTransaction.traiterErreur();
			totalBE =0;
		}
		total = totalOT+totalBE;
		
		return total;
	}
	
	
	public static int montantBE_PM(nc.mairie.technique.Transaction aTransaction,ENGJU unENGJU, String PMinv) throws Exception{
		
		// controle si unEnju null
		if (unENGJU.getNoengj()==null){
			aTransaction.declarerErreur("Le Bon d'engagement passé en paramètre est null.");
			return -1;
		}
		
		int total = 0;
		//	 recherche des lignes du bon d'engagement
		//System.out.println("EXERCI="+unENGJU.getExerci());
		//System.out.println("Noengj="+unENGJU.getNoengj());
		ArrayList listLeju = ENGJU.listerENGJU(aTransaction,unENGJU.getExerci(),unENGJU.getNoengj());
		if(aTransaction.isErreur()){
			// traite l'erreur et retourne 0
			aTransaction.traiterErreur();
			return 0;
		}
		if(listLeju.size()>0){
			//System.out.println("MONTANT ENJU PASS");
			for(int j=0;j<listLeju.size();j++){
				ENGJU unLeju = (ENGJU)listLeju.get(j);
				if (null!=unLeju.getCddep()&&-1!=unLeju.getCddep().trim().indexOf(PMinv)){
					int montant = Integer.parseInt(unLeju.getMtlenju());
					total = total + montant;
					//System.out.println("MONTANT ENJU="+total);
				}
			}
		}
		//System.out.println("MONTANT ENJU FINAL="+total);
		return total;
	}
	
	public static int montantBE_Equipement(nc.mairie.technique.Transaction aTransaction,ENGJU unENGJU, String Equipementinv) throws Exception{
		
		// controle si unEnju null
		if (null==unENGJU||null==unENGJU.getNoengj()){
			aTransaction.declarerErreur("Le Bon d'engagement passé en paramètre est null.");
			return -1;
		}
		
		int total = 0;
		//	 recherche des lignes du bon d'engagement
		ArrayList listLeju = ENGJU.listerENGJU(aTransaction,unENGJU.getExerci(),unENGJU.getNoengj());
		if(aTransaction.isErreur()){
			// traite l'erreur et retourne 0
			aTransaction.traiterErreur();
			return -1;
		}
		if(listLeju.size()>0){
			for(int j=0;j<listLeju.size();j++){
				ENGJU unLeju = (ENGJU)listLeju.get(j);
				if (null!=unLeju.getCddep()&&-1!=unLeju.getCddep().trim().indexOf(Equipementinv)){
					int montant = Integer.parseInt(unLeju.getMtlenju());
					total = total + montant;
				}
			}
		}
		return total;
	}
	
	public static int montantBE(nc.mairie.technique.Transaction aTransaction,ENGJU unENGJU) throws Exception{
		
		// controle si unEnju null
		if (null==unENGJU||null==unENGJU.getNoengj()){
			aTransaction.declarerErreur("Le Bon d'engagement passé en paramètre est null.");
			return -1;
		}
		
		int total = 0;
		//	 recherche des lignes du bon d'engagement
		ArrayList listLeju = ENGJU.listerENGJU(aTransaction,unENGJU.getExerci(),unENGJU.getNoengj());
		if(aTransaction.isErreur()){
			// traite l'erreur et retourne 0
			aTransaction.traiterErreur();
			return -1;
		}
		if(listLeju.size()>0){
			for(int i=0;i<listLeju.size();i++){
				ENGJU unLeju = (ENGJU)listLeju.get(i);
				int montant = Integer.parseInt(unLeju.getMtlenju());
				total = total + montant;
			}
		}
		return total;
	}

}
