package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier Pieces_FPM
 */
public class Pieces_FPMBroker extends BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerPieces_FPM(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierPieces_FPM(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerPieces_FPM(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Pieces_FPM.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<Pieces_FPM> listerPieces_FPM(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un Pieces_FPM.
 * @param aTransaction Transaction
 * @param numpiece numpiece
 * @param numfiche numfiche
 * @param date date
 * @return Pieces_FPM
 * @throws Exception Exception
 */
public Pieces_FPM chercherPieces_FPM(nc.mairie.technique.Transaction aTransaction, String numpiece,String numfiche,String date) throws Exception {
	String dSortie = Services.formateDateInternationale(date);
	return (Pieces_FPM)executeSelect(aTransaction,"select * from "+getTable()+" where numpiece = "+numpiece+" and numfiche="+numfiche+" and dsortie='"+dSortie+"'");
}
/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @param aTransaction Transaction
 * @param numfiche numfiche
 * @param numpiece numpiece
 * @param date date
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existePiecesFPM(nc.mairie.technique.Transaction aTransaction, String numfiche,String numpiece,String date) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numfiche = "+numfiche+" and numpiece = "+numpiece+" and dsortie='"+date+"'");
}
public ArrayList<Pieces_FPM> listerPieces_FPMFPM(nc.mairie.technique.Transaction aTransaction,String numfiche) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numfiche = "+numfiche);
}
/**
 * Constructeur Pieces_FPMBroker.
 * @param aMetier BasicMetier
 */
public Pieces_FPMBroker(Pieces_FPM aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.Pieces_FPMMetier
 */
@Override
protected Pieces_FPM definirMyMetier() {
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
	return "F_FPM_PIE";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NUMFICHE", new BasicRecord("NUMFICHE", "INTEGER", getMyPieces_FPM().getClass().getField("numfiche"), "STRING"));
	mappage.put("NUMPIECE", new BasicRecord("NUMPIECE", "INTEGER", getMyPieces_FPM().getClass().getField("numpiece"), "STRING"));
	mappage.put("DSORTIE", new BasicRecord("DSORTIE", "DATE", getMyPieces_FPM().getClass().getField("dsortie"), "DATE"));
	mappage.put("QUANTITE", new BasicRecord("QUANTITE", "INTEGER", getMyPieces_FPM().getClass().getField("quantite"), "STRING"));
	mappage.put("PRIX", new BasicRecord("PRIX", "NUMERIC", getMyPieces_FPM().getClass().getField("prix"), "STRING"));
	return mappage;
}
}
