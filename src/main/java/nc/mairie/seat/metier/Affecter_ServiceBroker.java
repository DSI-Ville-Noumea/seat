package nc.mairie.seat.metier;

import java.util.ArrayList;

import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier Affecter_Service
 */
public class Affecter_ServiceBroker extends BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerAffecter_Service(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierAffecter_Service(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerAffecter_Service(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Affecter_Service.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<Affecter_Service> listerAffecter_Service(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un Affecter_Service.
 * @param aTransaction Transaction
 * @param inv inv
 * @param servi servi
 * @param date date
 * @param dfin dfin
 * @return Affecter_Service
 * @throws Exception Exception
 */
public Affecter_Service chercherAffecter_Service(nc.mairie.technique.Transaction aTransaction, String inv, String servi,String date,String dfin) throws Exception {
	return (Affecter_Service)executeSelect(aTransaction,"select * from "+getTable()+" where numeroinventaire = '"+inv+"' and codeservice='"+servi+"' and ddebut='"+date+"' and dfin='"+dfin+"'");
}

/**
 * Retourne un ArrayList d'objet métier : Affecter_Service.
 * @param aTransaction Transaction
 * @param param param
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<Affecter_Service> chercherListerAffecter_ServiceEquip(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numeroinventaire = '"+param+"'");
}

/**
 * Retourne un ArrayList d'objet métier : Affecter_Service.
 * @param aTransaction Transaction
 * @param param param
 * @param servi servi
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public Affecter_Service chercherListerAffecter_ServiceEquipSce(nc.mairie.technique.Transaction aTransaction,String param,String servi) throws Exception {
	return (Affecter_Service)executeSelect(aTransaction,"select * from "+getTable()+" where numeroinventaire = '"+param+"' and codeservice = '"+servi+"' and dfin = '0001-01-01'");
}

/**
 * Constructeur Affecter_ServiceBroker.
 * @param aMetier BasicMetier
 */
public Affecter_ServiceBroker(Affecter_Service aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.Affecter_ServiceMetier
 */
@Override
protected Affecter_Service definirMyMetier() {
	return new Affecter_Service() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.Affecter_ServiceMetier
 */
protected Affecter_Service getMyAffecter_Service() {
	return (Affecter_Service)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "F_AFF_SCE";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("CODESERVICE", new BasicRecord("CODESERVICE", "VARCHAR", getMyAffecter_Service().getClass().getField("codeservice"), "STRING"));
	mappage.put("NUMEROINVENTAIRE", new BasicRecord("NUMEROINVENTAIRE", "VARCHAR", getMyAffecter_Service().getClass().getField("numeroinventaire"), "STRING"));
	mappage.put("DDEBUT", new BasicRecord("DDEBUT", "DATE", getMyAffecter_Service().getClass().getField("ddebut"), "DATE"));
	mappage.put("DFIN", new BasicRecord("DFIN", "DATE", getMyAffecter_Service().getClass().getField("dfin"), "DATE"));
	mappage.put("NOMATR", new BasicRecord("NOMATR", "NUMERIC", getMyAffecter_Service().getClass().getField("nomatr"), "STRING"));
	return mappage;
}
}
