package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier BeOtInfos
 */
public class BeOtInfosBroker extends BasicBroker {
/**
 * Constructeur BeOtInfosBroker.
 */
public BeOtInfosBroker(BeOtInfos aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.BeOtInfosMetier
 */
@Override
protected BeOtInfos definirMyMetier() {
	return new BeOtInfos() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.BeOtInfosMetier
 */
protected BeOtInfos getMyBeOtInfos() {
	return (BeOtInfos)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.V_BEOTINFOS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NUMEROOT", new BasicRecord("NUMEROOT", "NUMERIC", getMyBeOtInfos().getClass().getField("numeroot"), "STRING"));
	mappage.put("DATEENTREE", new BasicRecord("DATEENTREE", "DATE", getMyBeOtInfos().getClass().getField("dateentree"), "DATE"));
	mappage.put("DATESORTIE", new BasicRecord("DATESORTIE", "DATE", getMyBeOtInfos().getClass().getField("datesortie"), "DATE"));
	mappage.put("COMPTEUR", new BasicRecord("COMPTEUR", "INTEGER", getMyBeOtInfos().getClass().getField("compteur"), "STRING"));
	mappage.put("VALIDE", new BasicRecord("VALIDE", "CHAR", getMyBeOtInfos().getClass().getField("valide"), "STRING"));
	mappage.put("COMMENTAIRE", new BasicRecord("COMMENTAIRE", "LONG VARCHAR", getMyBeOtInfos().getClass().getField("commentaire"), "STRING"));
	mappage.put("CODCOL", new BasicRecord("CODCOL", "NUMERIC", getMyBeOtInfos().getClass().getField("codcol"), "STRING"));
	mappage.put("EXERCI", new BasicRecord("EXERCI", "NUMERIC", getMyBeOtInfos().getClass().getField("exerci"), "STRING"));
	mappage.put("CODBUD", new BasicRecord("CODBUD", "NUMERIC", getMyBeOtInfos().getClass().getField("codbud"), "STRING"));
	mappage.put("NOENGJ", new BasicRecord("NOENGJ", "VARCHAR", getMyBeOtInfos().getClass().getField("noengj"), "STRING"));
	return mappage;
}
/**
 * Retourne un ArrayList d'objet métier : BeOtInfos.
 * @return java.util.ArrayList
 */
public ArrayList<BeOtInfos> listerBeOtInfos(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un BeOtInfos.
 * @return BeOtInfos
 */
public BeOtInfos chercherBeOtInfos(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (BeOtInfos)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+"");
}
/**
 * Retourne un ArrayList d'objet métier : BeOtInfos.
 * @return java.util.ArrayList
 */
public ArrayList<BeOtInfos> listerBeOtInfosOT(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numeroot="+numot);
}

/**
 * Retourne un ArrayList d'objet métier : PiecesOtInfos.
 * @return java.util.ArrayList
 */
public int cumuleMontantPiecesOtInfosBE(nc.mairie.technique.Transaction aTransaction,String numot, String numinv) throws Exception {
	return executeCompter(aTransaction," select sum(mtlenju)" +
			" from "+getTable()+" be " +
			" inner join SEAT.V_ENJU enju on enju.noengj = be.noengj " +
			" where numeroot="+numot+" " +
			" and cddep like '%"+numinv+"%'");
}
}
