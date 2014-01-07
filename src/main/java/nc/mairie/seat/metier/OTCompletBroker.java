package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier OTComplet
 */
public class OTCompletBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur OTCompletBroker.
 */
public OTCompletBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.OTCompletMetier
 */
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new OTComplet() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.OTCompletMetier
 */
protected OTComplet getMyOTComplet() {
	return (OTComplet)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
protected java.lang.String definirNomTable() {
	return "SEAT.V_OTCOMPLET";
	//return "SEAT.F_OT F_OT inner join SEAT.F_EQU00001 F_EQUIPEMENT ON F_OT.NUMINV = F_EQUIPEMENT.NUMEROINVENTAIRE";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NUMEROOT", new BasicRecord("NUMEROOT", "NUMERIC", getMyOTComplet().getClass().getField("numeroot"), "STRING"));
	mappage.put("DATEENTREE", new BasicRecord("DATEENTREE", "DATE", getMyOTComplet().getClass().getField("dateentree"), "DATE"));
	mappage.put("DATESORTIE", new BasicRecord("DATESORTIE", "DATE", getMyOTComplet().getClass().getField("datesortie"), "DATE"));
	mappage.put("COMPTEUR", new BasicRecord("COMPTEUR", "INTEGER", getMyOTComplet().getClass().getField("compteur"), "STRING"));
	mappage.put("VALIDE", new BasicRecord("VALIDE", "CHAR", getMyOTComplet().getClass().getField("valide"), "STRING"));
	mappage.put("NUMEROBC", new BasicRecord("NUMEROBC", "INTEGER", getMyOTComplet().getClass().getField("numerobc"), "STRING"));
	mappage.put("COMMENTAIRE", new BasicRecord("COMMENTAIRE", "LONG VARCHAR", getMyOTComplet().getClass().getField("commentaire"), "STRING"));
	mappage.put("NUMINV", new BasicRecord("NUMINV", "VARCHAR", getMyOTComplet().getClass().getField("numinv"), "STRING"));
	mappage.put("NUMEROINVENTAIRE", new BasicRecord("NUMEROINVENTAIRE", "VARCHAR", getMyOTComplet().getClass().getField("numeroinventaire"), "STRING"));
	mappage.put("NUMEROIMMATRICULATION", new BasicRecord("NUMEROIMMATRICULATION", "VARCHAR", getMyOTComplet().getClass().getField("numeroimmatriculation"), "STRING"));
	mappage.put("DATEMISEENCIRCULATION", new BasicRecord("DATEMISEENCIRCULATION", "DATE", getMyOTComplet().getClass().getField("datemiseencirculation"), "DATE"));
	mappage.put("DATEVENTEOUREFORME", new BasicRecord("DATEVENTEOUREFORME", "DATE", getMyOTComplet().getClass().getField("dateventeoureforme"), "DATE"));
	mappage.put("DATEHORSCIRCUIT", new BasicRecord("DATEHORSCIRCUIT", "DATE", getMyOTComplet().getClass().getField("datehorscircuit"), "DATE"));
	mappage.put("PRIXACHAT", new BasicRecord("PRIXACHAT", "INTEGER", getMyOTComplet().getClass().getField("prixachat"), "STRING"));
	mappage.put("RESERVE", new BasicRecord("RESERVE", "CHAR", getMyOTComplet().getClass().getField("reserve"), "STRING"));
	mappage.put("CODEMODELE", new BasicRecord("CODEMODELE", "INTEGER", getMyOTComplet().getClass().getField("codemodele"), "STRING"));
	mappage.put("DUREEGARANTIE", new BasicRecord("DUREEGARANTIE", "INTEGER", getMyOTComplet().getClass().getField("dureegarantie"), "STRING"));
	return mappage;
}
/**
 * Retourne un ArrayList d'objet métier : OTComplet.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerOTComplet(nc.mairie.technique.Transaction aTransaction, String and) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+ (and.length() > 0 ? " where "+and : ""));
}
/**
 * Retourne un OTComplet.
 * @return OTComplet
 */
public OTComplet chercherOTComplet(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (OTComplet)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+"");
}
/**
 * Retourne un ArrayList d'objet métier : OT.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerOTCompletEquip(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numinv='"+inv+"'");
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerOTCompletValide(nc.mairie.technique.Transaction aTransaction, String and) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where valide='T'" + (and != null && and.length() > 0 ? " and "+and : ""));
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerOTCompletAValider(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where valide='F'");
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerOTCompletEncours(nc.mairie.technique.Transaction aTransaction, String and) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where valide='F' "+ (and!= null && and.length() > 0 ? " and "+and : ""));                                      
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerOTCompletDeclarationsEncours(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where valide='F' and numeroot in(select codeot from SEAT.f_declarations)");                                      
}
/**
 * Retourne un ArrayList d'objet métier : OT.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerOTCompletDeclarationsValide(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where valide='T' and numeroot in(select codeot from SEAT.f_declarations)");                                      
}

}
