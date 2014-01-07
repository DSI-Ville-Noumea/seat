package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier Fournisseurs
 */
public class FournisseursBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur FournisseursBroker.
 */
public FournisseursBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.FournisseursMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new Fournisseurs() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.FournisseursMetier
 */
protected Fournisseurs getMyFournisseurs() {
	return (Fournisseurs)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "MAIRIE.SIETBS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("IDETBS", new BasicRecord("IDETBS", "NUMERIC", getMyFournisseurs().getClass().getField("idetbs"), "STRING"));
	mappage.put("ENSCOM", new BasicRecord("ENSCOM", "CHAR", getMyFournisseurs().getClass().getField("enscom"), "STRING"));
	mappage.put("RIDET", new BasicRecord("RIDET", "NUMERIC", getMyFournisseurs().getClass().getField("ridet"), "STRING"));
	mappage.put("SIRET", new BasicRecord("SIRET", "NUMERIC", getMyFournisseurs().getClass().getField("siret"), "STRING"));
	mappage.put("CDTYAD", new BasicRecord("CDTYAD", "NUMERIC", getMyFournisseurs().getClass().getField("cdtyad"), "STRING"));
	mappage.put("NOTIER", new BasicRecord("NOTIER", "NUMERIC", getMyFournisseurs().getClass().getField("notier"), "STRING"));
	mappage.put("IDADRE", new BasicRecord("IDADRE", "NUMERIC", getMyFournisseurs().getClass().getField("idadre"), "STRING"));
	mappage.put("CDTYED", new BasicRecord("CDTYED", "NUMERIC", getMyFournisseurs().getClass().getField("cdtyed"), "STRING"));
	mappage.put("CDCTLC", new BasicRecord("CDCTLC", "CHAR", getMyFournisseurs().getClass().getField("cdctlc"), "STRING"));
	mappage.put("CDETAC", new BasicRecord("CDETAC", "CHAR", getMyFournisseurs().getClass().getField("cdetac"), "STRING"));
	mappage.put("CDNAJD", new BasicRecord("CDNAJD", "NUMERIC", getMyFournisseurs().getClass().getField("cdnajd"), "STRING"));
	return mappage;
}
/**
 * Retourne un ArrayList d'objet métier : Fournisseurs.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerFournisseurs(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" with ur");
}

/**
 * Retourne un ArrayList d'objet métier : Fournisseurs.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerFournisseursNom(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where upper(enscom) like '"+param.toUpperCase()+"%' with ur");
}

/**
 * Retourne un Fournisseurs.
 * @return Fournisseurs
 */
public Fournisseurs chercherFournisseurs(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Fournisseurs)executeSelect(aTransaction,"select * from "+getTable()+" where IDETBS = "+cle+" with ur");
}
}
