package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier OTComplet
 */
public class OTCompletBroker extends BasicBroker {
/**
 * Constructeur OTCompletBroker.
 * @param aMetier BasicMetier
 */
public OTCompletBroker(OTComplet aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.OTCompletMetier
 */
protected OTComplet definirMyMetier() {
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
	//return "SEAT.F_OT F_OT inner join SEAT.F_EQUIPEMENT F_EQUIPEMENT ON F_OT.NUMINV = F_EQUIPEMENT.NUMEROINVENTAIRE";
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
 * @param aTransaction Transaction
 * @param and and
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<OTComplet> listerOTComplet(nc.mairie.technique.Transaction aTransaction, String and) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+ (and.length() > 0 ? " where "+and : ""));
}
/**
 * Retourne un OTComplet.
 * @param aTransaction Transaction
 * @param cle cle
 * @return OTComplet
 * @throws Exception Exception
 */
public OTComplet chercherOTComplet(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (OTComplet)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+"");
}
/**
 * Retourne un ArrayList d'objet métier : OT.
 * @param aTransaction Transaction
 * @param inv inv
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<OTComplet> listerOTCompletEquip(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numinv='"+inv+"'");
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @param aTransaction Transaction
 * @param and and
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<OTComplet> listerOTCompletValide(nc.mairie.technique.Transaction aTransaction, String and) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where valide='T'" + (and != null && and.length() > 0 ? " and "+and : ""));
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<OTComplet> listerOTCompletAValider(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where valide='F'");
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @param aTransaction Transaction
 * @param and and
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<OTComplet> listerOTCompletEncours(nc.mairie.technique.Transaction aTransaction, String and) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where valide='F' "+ (and!= null && and.length() > 0 ? " and "+and : ""));                                      
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<OTComplet> listerOTCompletDeclarationsEncours(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where valide='F' and numeroot in(select codeot from SEAT.f_declarations)");                                      
}
/**
 * Retourne un ArrayList d'objet métier : OT.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<OTComplet> listerOTCompletDeclarationsValide(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where valide='T' and numeroot in(select codeot from SEAT.f_declarations)");                                      
}

}
