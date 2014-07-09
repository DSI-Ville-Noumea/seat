package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier PePersoInfos
 */
public class PePersoInfosBroker extends BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : PePersoInfos.
 * @param aTransaction Transaction
 * @param inv inv
 * @param tri tri
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<PePersoInfos> listerPePersoInfosEquip(nc.mairie.technique.Transaction aTransaction,String inv,String tri) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codeequip='"+inv+"' order by "+tri+"");
}
/**
 * Retourne un PePersoInfos.
 * @param aTransaction Transaction
 * @param cle cle
 * @return PePersoInfos
 * @throws Exception Exception
 */
public PePersoInfos chercherPePersoInfos(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (PePersoInfos)executeSelect(aTransaction,"select * from "+getTable()+" where codepep = "+cle+"");
}
/**
 * Retourne un ArrayList d'objet métier : PePersoInfos.
 * @param aTransaction Transaction
 * @param inv inv
 * @param tri tri
 * @return java.util.ArrayList
 * On fait un Tri selon le paramètre
 * @throws Exception Exception
 */
public ArrayList<PePersoInfos> listerPePersoInfosFait(nc.mairie.technique.Transaction aTransaction,String inv,String tri) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where datereal <> '0001-01-01' and codeequip='"+inv+"' order by "+tri+"");
}

/**
 * Retourne un ArrayList d'objet métier : PePersoInfos.
 * @param aTransaction Transaction
 * @param inv inv
 * @param tri tri
 * @return java.util.ArrayList
 * On fait un Tri selon le paramètre
 * @throws Exception Exception
 */
public ArrayList<PePersoInfos> listerPePersoInfosAFaire(nc.mairie.technique.Transaction aTransaction,String inv,String tri) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where datereal = '0001-01-01' and codeequip='"+inv+"' order by "+tri+"");
}

/**
 * Retourne un PePerso.
 * @param aTransaction Transaction
 * @param numot numot
 * @return PePerso
 * @throws Exception Exception
 */
public ArrayList<PePersoInfos> chercherPePersoInfosOT(nc.mairie.technique.Transaction aTransaction, String numot) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codeot = "+numot);
}

/**
 * Retourne un ArrayList d'objet métier : PePersoInfos.
 * @return java.util.ArrayList
 * Liste les OT d'un équipement
 
public ArrayList<PePersoInfos> listerPePersoInfosOTEquip(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception {
	return executeSelectListe(aTransaction,"select distinct(codeot) from "+getTable()+" where codeequip='"+inv+"'");
}*/

/**
 * Retourne un ArrayList d'objet métier : PePersoInfos.
 * @param aTransaction Transaction
 * @param inv inv
 * @return java.util.ArrayList
 * Liste les OT d'un équipement
 * @throws Exception Exception
 */
public ArrayList<PePersoInfos> listerPePersoInfosOTEquip(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception {
	return executeSelectListe(aTransaction,"select distinct(codeot) from "+getTable());
}

/**
 * Constructeur PePersoInfosBroker.
 * @param aMetier BasicMetier
 */
public PePersoInfosBroker(PePersoInfos aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PePersoInfosMetier
 */
@Override
protected PePersoInfos definirMyMetier() {
	return new PePersoInfos() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PePersoInfosMetier
 */
protected PePersoInfos getMyPePersoInfos() {
	return (PePersoInfos)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.V_PEPERSOINFOS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("SINISTRE", new BasicRecord("SINISTRE", "VARCHAR", getMyPePersoInfos().getClass().getField("sinistre"), "STRING"));
	mappage.put("DUREE", new BasicRecord("DUREE", "NUMERIC", getMyPePersoInfos().getClass().getField("duree"), "STRING"));
	mappage.put("CODEOT", new BasicRecord("CODEOT", "NUMERIC", getMyPePersoInfos().getClass().getField("codeot"), "STRING"));
	mappage.put("COMMENTAIRETE", new BasicRecord("COMMENTAIRETE", "VARCHAR", getMyPePersoInfos().getClass().getField("commentairete"), "STRING"));
	mappage.put("CODEPEP", new BasicRecord("CODEPEP", "INTEGER", getMyPePersoInfos().getClass().getField("codepep"), "STRING"));
	mappage.put("INTERVALLE", new BasicRecord("INTERVALLE", "INTEGER", getMyPePersoInfos().getClass().getField("intervalle"), "STRING"));
	mappage.put("DATEREAL", new BasicRecord("DATEREAL", "DATE", getMyPePersoInfos().getClass().getField("datereal"), "DATE"));
	mappage.put("CODEEQUIP", new BasicRecord("CODEEQUIP", "VARCHAR", getMyPePersoInfos().getClass().getField("codeequip"), "STRING"));
	mappage.put("CODEENTRETIEN", new BasicRecord("CODEENTRETIEN", "INTEGER", getMyPePersoInfos().getClass().getField("codeentretien"), "STRING"));
	mappage.put("LIBELLEENTRETIEN", new BasicRecord("LIBELLEENTRETIEN", "VARCHAR", getMyPePersoInfos().getClass().getField("libelleentretien"), "STRING"));
	mappage.put("CODETYPEENT", new BasicRecord("CODETYPEENT", "INTEGER", getMyPePersoInfos().getClass().getField("codetypeent"), "STRING"));
	mappage.put("DESIGNATIONTYPEENT", new BasicRecord("DESIGNATIONTYPEENT", "VARCHAR", getMyPePersoInfos().getClass().getField("designationtypeent"), "STRING"));
	mappage.put("CODETI", new BasicRecord("CODETI", "INTEGER", getMyPePersoInfos().getClass().getField("codeti"), "STRING"));
	mappage.put("DESIGNATION", new BasicRecord("DESIGNATION", "CHAR", getMyPePersoInfos().getClass().getField("designationti"), "STRING"));
	mappage.put("DATEPREV", new BasicRecord("DATEPREV", "DATE", getMyPePersoInfos().getClass().getField("dateprev"), "DATE"));
	return mappage;
}
}
