package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier PiecesOtInfos
 */
public class PiecesOtInfosBroker extends BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : PiecesOtInfos.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<PiecesOtInfos> listerPiecesOtInfos(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un PiecesOtInfos.
 * @param aTransaction Transaction
 * @param cle cle
 * @return PiecesOtInfos
 * @throws Exception Exception
 */
public PiecesOtInfos chercherPiecesOtInfos(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (PiecesOtInfos)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+"");
}

/**
 * Retourne un ArrayList d'objet métier : PiecesOtInfos.
 * @param aTransaction Transaction
 * @param numot numot
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<PiecesOtInfos> listerPiecesOtInfosOT(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numot="+numot);
}


/**
 * Retourne un ArrayList d'objet métier : PiecesOtInfos.
 * @param aTransaction Transaction
 * @param numot numot
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public int cumuleMontantPiecesOtInfosOT(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception {
	return executeCompter(aTransaction,"select sum(quantite * prix) from "+getTable()+" where numot="+numot+" group by numot");
}
/**
 * Constructeur PiecesOtInfosBroker.
 * @param aMetier BasicMetier
 */
public PiecesOtInfosBroker(PiecesOtInfos aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PiecesOtInfosMetier
 */
@Override
protected PiecesOtInfos definirMyMetier() {
	return new PiecesOtInfos() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PiecesOtInfosMetier
 */
protected PiecesOtInfos getMyPiecesOtInfos() {
	return (PiecesOtInfos)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.V_PIECESOTINFOS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NUMOT", new BasicRecord("NUMOT", "NUMERIC", getMyPiecesOtInfos().getClass().getField("numot"), "STRING"));
	mappage.put("NUMPIECE", new BasicRecord("NUMPIECE", "INTEGER", getMyPiecesOtInfos().getClass().getField("numpiece"), "STRING"));
	mappage.put("DATESORTIEPIECE", new BasicRecord("DATESORTIEPIECE", "DATE", getMyPiecesOtInfos().getClass().getField("datesortiepieces"), "DATE"));
	mappage.put("QUANTITE", new BasicRecord("QUANTITE", "INTEGER", getMyPiecesOtInfos().getClass().getField("quantite"), "STRING"));
	mappage.put("DESIGNATIONPIECE", new BasicRecord("DESIGNATIONPIECE", "CHAR", getMyPiecesOtInfos().getClass().getField("designationpiece"), "STRING"));
	mappage.put("PU", new BasicRecord("PU", "INTEGER", getMyPiecesOtInfos().getClass().getField("pu"), "STRING"));
	mappage.put("DATEENTREE", new BasicRecord("DATEENTREE", "DATE", getMyPiecesOtInfos().getClass().getField("dateentree"), "DATE"));
	mappage.put("DATESORTIE", new BasicRecord("DATESORTIE", "DATE", getMyPiecesOtInfos().getClass().getField("datesortie"), "DATE"));
	mappage.put("COMPTEUR", new BasicRecord("COMPTEUR", "INTEGER", getMyPiecesOtInfos().getClass().getField("compteur"), "STRING"));
	mappage.put("VALIDE", new BasicRecord("VALIDE", "CHAR", getMyPiecesOtInfos().getClass().getField("valide"), "STRING"));
	mappage.put("NUMEROBC", new BasicRecord("NUMEROBC", "INTEGER", getMyPiecesOtInfos().getClass().getField("numerobc"), "STRING"));
	mappage.put("COMMENTAIRE", new BasicRecord("COMMENTAIRE", "LONG VARCHAR", getMyPiecesOtInfos().getClass().getField("commentaire"), "STRING"));
	mappage.put("PRIX", new BasicRecord("PRIX", "NUMERIC", getMyPiecesOtInfos().getClass().getField("prix"), "STRING"));
	return mappage;
}
}
