package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.Services;
/**
 * Broker de l'Objet métier Pieces_FPM
 */
public class Pieces_FPMBroker extends nc.mairie.technique.BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerPieces_FPM(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierPieces_FPM(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerPieces_FPM(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Pieces_FPM.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerPieces_FPM(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un Pieces_FPM.
 * @return Pieces_FPM
 */
public Pieces_FPM chercherPieces_FPM(nc.mairie.technique.Transaction aTransaction, String numpiece,String numfiche,String date) throws Exception {
	String dSortie = Services.formateDateInternationale(date);
	return (Pieces_FPM)executeSelect(aTransaction,"select * from "+getTable()+" where numpiece = "+numpiece+" and numfiche="+numfiche+" and dsortie='"+dSortie+"'");
}
/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @return true ou false
 */
public boolean existePiecesFPM(nc.mairie.technique.Transaction aTransaction, String numfiche,String numpiece,String date) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numfiche = "+numfiche+" and numpiece = "+numpiece+" and dsortie='"+date+"'");
}
public java.util.ArrayList listerPieces_FPMFPM(nc.mairie.technique.Transaction aTransaction,String numfiche) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numfiche = "+numfiche);
}
/**
 * Constructeur Pieces_FPMBroker.
 */
public Pieces_FPMBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.Pieces_FPMMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new Pieces_FPM() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.Pieces_FPMMetier
 */
protected Pieces_FPM getMyPieces_FPM() {
	return (Pieces_FPM)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.F_FPM_PIE";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("NUMFICHE", new BasicRecord("NUMFICHE", "INTEGER", getMyPieces_FPM().getClass().getField("numfiche"), "STRING"));
	mappage.put("NUMPIECE", new BasicRecord("NUMPIECE", "INTEGER", getMyPieces_FPM().getClass().getField("numpiece"), "STRING"));
	mappage.put("DSORTIE", new BasicRecord("DSORTIE", "DATE", getMyPieces_FPM().getClass().getField("dsortie"), "DATE"));
	mappage.put("QUANTITE", new BasicRecord("QUANTITE", "INTEGER", getMyPieces_FPM().getClass().getField("quantite"), "STRING"));
	mappage.put("PRIX", new BasicRecord("PRIX", "NUMERIC", getMyPieces_FPM().getClass().getField("prix"), "STRING"));
	return mappage;
}
}
