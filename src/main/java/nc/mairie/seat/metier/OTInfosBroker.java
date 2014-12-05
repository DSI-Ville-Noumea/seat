package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier OTInfos
 */
public class OTInfosBroker extends BasicBroker {
/**
 * Constructeur OTInfosBroker.
 * @param aMetier BasicMetier
 */
public OTInfosBroker(OTInfos aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.OTInfosMetier
 */
@Override
protected OTInfos definirMyMetier() {
	return new OTInfos() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.OTInfosMetier
 */
protected OTInfos getMyOTInfos() {
	return (OTInfos)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "V_OTINFOS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NUMEROOT", new BasicRecord("NUMEROOT", "NUMERIC", getMyOTInfos().getClass().getField("numeroot"), "STRING"));
	mappage.put("DATEENTREE", new BasicRecord("DATEENTREE", "DATE", getMyOTInfos().getClass().getField("dateentree"), "DATE"));
	mappage.put("DATESORTIE", new BasicRecord("DATESORTIE", "DATE", getMyOTInfos().getClass().getField("datesortie"), "DATE"));
	mappage.put("COMPTEUR", new BasicRecord("COMPTEUR", "INTEGER", getMyOTInfos().getClass().getField("compteur"), "STRING"));
	mappage.put("VALIDE", new BasicRecord("VALIDE", "CHAR", getMyOTInfos().getClass().getField("valide"), "STRING"));
	mappage.put("COMMENTAIRE", new BasicRecord("COMMENTAIRE", "LONG VARCHAR", getMyOTInfos().getClass().getField("commentaire"), "STRING"));
	mappage.put("NUMEROINVENTAIRE", new BasicRecord("NUMEROINVENTAIRE", "VARCHAR", getMyOTInfos().getClass().getField("numeroinventaire"), "STRING"));
	mappage.put("NUMEROIMMATRICULATION", new BasicRecord("NUMEROIMMATRICULATION", "VARCHAR", getMyOTInfos().getClass().getField("numeroimmatriculation"), "STRING"));
	return mappage;
}
/**
 * Retourne un ArrayList d'objet métier : OTInfos.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<OTInfos> listerOTInfos(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}

/**
 * Retourne un ArrayList d'objet métier : OTInfos.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<OTInfos> listerOTInfosAValider(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where valide='F'");
}

/**
 * Retourne un ArrayList d'objet métier : OTInfos.
 * @param aTransaction Transaction
 * @param inv inv
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<OTInfos> listerOTInfosEquip(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numeroinventaire='"+inv+"'");
}

/**
 * Retourne un OTInfos.
 * @param aTransaction Transaction
 * @param cle cle
 * @return OTInfos
 * @throws Exception Exception
 */
public OTInfos chercherOTInfos(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (OTInfos)executeSelect(aTransaction,"select * from "+getTable()+" where numeroot = "+cle+"");
}
/**
 * Retourne un ArrayList d'objet métier : OT.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<OTInfos> listerOTInfosValide(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where valide='T'");
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<OTInfos> listerOTInfosEncours(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where valide='F'");
}
}
