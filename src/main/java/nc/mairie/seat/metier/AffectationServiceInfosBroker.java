package nc.mairie.seat.metier;

import java.util.ArrayList;

import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier AffectationServiceInfos
 */
public class AffectationServiceInfosBroker extends BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : AffectationServiceInfos.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<AffectationServiceInfos> listerAffectationServiceInfos(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" with ur");
}
/**
 * Retourne un ArrayList d'objet métier : AffectationServiceInfos.
 * @param aTransaction Transaction
 * @param nomatr nomatr
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<AffectationServiceInfos> listerAffectationServiceInfosAgent(nc.mairie.technique.Transaction aTransaction,String nomatr) throws Exception {
	if (nomatr.equals("")){
		aTransaction.declarerErreur("Le numéro d'agent n'a pas été renseigné");
		ArrayList<AffectationServiceInfos> listVide = new ArrayList<AffectationServiceInfos>();
		return listVide;
	}
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where dfin='0001-01-01' and nomatr="+nomatr+" with ur");
}
/**
 * Retourne un AffectationServiceInfos.
 * @param aTransaction Transaction
 * @param cle cle
 * @return AffectationServiceInfos
 * @throws Exception Exception
 */
public ArrayList<AffectationServiceInfos> chercherAffectationServiceInfosService(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return  executeSelectListe(aTransaction,"select * from "+getTable()+" where codeservice like '"+cle+"%' and dfin = '0001-01-01' with ur");
}

/**
 * Retourne un AffectationServiceInfos.
 * @param aTransaction Transaction
 * @param inv inv
 * @param date date
 * @return AffectationServiceInfos
 * @throws Exception Exception
 */
public AffectationServiceInfos chercherAffectationServiceInfosCourantEquip(nc.mairie.technique.Transaction aTransaction, String inv,String date) throws Exception {
	//return  (AffectationServiceInfos)executeSelect(aTransaction,"select * from "+getTable()+" where numeroinventaire = '"+inv+"' and dfin = '0001-01-01' and ddebut <= '"+date+"'");
	//return  (AffectationServiceInfos)executeSelect(aTransaction, "select * from "+getTable()+" where numeroinventaire = '"+inv+"' and ddebut<='"+date+"' and numeroinventaire in (select max(numeroinventaire)from SEAT.F_AFFECTER_SCE where ddebut<='"+date+"' and numeroinventaire='"+inv+"' )");                               
	return  (AffectationServiceInfos)executeSelect(aTransaction, "select * from "+getTable()+" where numeroinventaire = '"+inv+"' and ddebut in (select max(ddebut)from SEAT.F_AFF_SCE where ddebut<='"+date+"' and numeroinventaire='"+inv+"' ) with ur");
}

/**
 * Retourne un AffectationServiceInfos.
 * @param aTransaction Transaction
 * @param cle cle
 * @return AffectationServiceInfos
 * @throws Exception Exception
 */
public ArrayList<AffectationServiceInfos> chercherAffectationServiceInfosEquip(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return  executeSelectListe(aTransaction,"select * from "+getTable()+" where numeroinventaire = '"+cle+"' with ur");
}
/**
 * Retourne un ArrayList d'objet métier : AffectationServiceInfos.
 * @param aTransaction Transaction
 * @param param param
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<AffectationServiceInfos> chercherListAffectationServiceInfosEquip(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numeroinventaire = '"+param+"' order by ddebut desc,dfin with ur");
}

/**
 * Constructeur AffectationServiceInfosBroker.
 * @param aMetier BasicMetier
 */
public AffectationServiceInfosBroker(AffectationServiceInfos aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AffectationServiceInfosMetier
 */
@Override
protected AffectationServiceInfos definirMyMetier() {
	return new AffectationServiceInfos() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AffectationServiceInfosMetier
 */
protected AffectationServiceInfos getMyAffectationServiceInfos() {
	return (AffectationServiceInfos)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.V_AFF_SCE_INFOS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NUMEROINVENTAIRE", new BasicRecord("NUMEROINVENTAIRE", "VARCHAR", getMyAffectationServiceInfos().getClass().getField("numeroinventaire"), "STRING"));
	mappage.put("NUMEROIMMATRICULATION", new BasicRecord("NUMEROIMMATRICULATION", "VARCHAR", getMyAffectationServiceInfos().getClass().getField("numeroimmatriculation"), "STRING"));
	mappage.put("DATEMISEENCIRCULATION", new BasicRecord("DATEMISEENCIRCULATION", "DATE", getMyAffectationServiceInfos().getClass().getField("datemiseencirculation"), "DATE"));
	mappage.put("CODESERVICE", new BasicRecord("CODESERVICE", "VARCHAR", getMyAffectationServiceInfos().getClass().getField("codeservice"), "STRING"));
	mappage.put("DDEBUT", new BasicRecord("DDEBUT", "DATE", getMyAffectationServiceInfos().getClass().getField("ddebut"), "DATE"));
	mappage.put("DFIN", new BasicRecord("DFIN", "DATE", getMyAffectationServiceInfos().getClass().getField("dfin"), "DATE"));
	mappage.put("LISERV", new BasicRecord("LISERV", "CHAR", getMyAffectationServiceInfos().getClass().getField("liserv"), "STRING"));
	mappage.put("NOMATR", new BasicRecord("NOMATR", "NUMERIC", getMyAffectationServiceInfos().getClass().getField("nomatr"), "STRING"));
	return mappage;
}
}
