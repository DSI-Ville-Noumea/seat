package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.Services;
/**
 * Broker de l'Objet métier Affecter_Agent
 */
public class Affecter_AgentBroker extends nc.mairie.technique.BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : Affecter_Agent.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerAffecter_Agent(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un Affecter_Agent.
 * @return Affecter_Agent
 */
public Affecter_Agent chercherAffecter_Agent(nc.mairie.technique.Transaction aTransaction, String inv,String matr,String date) throws Exception {
	String dateN = Services.formateDateInternationale(date);
	return (Affecter_Agent)executeSelect(aTransaction,"select * from "+getTable()+" where numeroinventaire = '"+inv+"' and matricule = "+matr+" and datedebut ='"+dateN+"'");
}
/**
 * Retourne un ArrayList d'objet métier : Affecter_Agent.
 * @return java.util.ArrayList
 */
public java.util.ArrayList chercherListerAffecter_AgentEquip(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numeroinventaire like '"+param+"%'");
}

public java.util.ArrayList chercherListerAffecter_AgentEquipSce(nc.mairie.technique.Transaction aTransaction,String inv,String param,String date) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codeservice like '"+param+"%' and numeroinventaire='"+inv+"' and datedebut<='"+date+"' and datefin='0001-01-01'");
}

public java.util.ArrayList chercherListerAffecter_AgentEquipSceEnCours(nc.mairie.technique.Transaction aTransaction,String inv,String param,String date) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codeservice like '"+param+"%' and numeroinventaire='"+inv+"' and datedebut>='"+date+"'");
}

/**
 * Retourne un ArrayList d'objet métier : Affecter_Agent.
 * @return java.util.ArrayList
 */
public java.util.ArrayList chercherListerAffecter_AgentService(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codeservice = '"+param+"'");
}

/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerAffecter_Agent(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierAffecter_Agent(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerAffecter_Agent(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Constructeur Affecter_AgentBroker.
 */
public Affecter_AgentBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.Affecter_AgentMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new Affecter_Agent() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.Affecter_AgentMetier
 */
protected Affecter_Agent getMyAffecter_Agent() {
	return (Affecter_Agent)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.F_AFFECTER";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("MATRICULE", new BasicRecord("MATRICULE", "NUMERIC", getMyAffecter_Agent().getClass().getField("matricule"), "STRING"));
	mappage.put("NUMEROINVENTAIRE", new BasicRecord("NUMEROINVENTAIRE", "VARCHAR", getMyAffecter_Agent().getClass().getField("numeroinventaire"), "STRING"));
	mappage.put("DATEDEBUT", new BasicRecord("DATEDEBUT", "DATE", getMyAffecter_Agent().getClass().getField("datedebut"), "DATE"));
	mappage.put("DATEFIN", new BasicRecord("DATEFIN", "DATE", getMyAffecter_Agent().getClass().getField("datefin"), "DATE"));
	mappage.put("HDEB", new BasicRecord("HDEB", "INTEGER", getMyAffecter_Agent().getClass().getField("hdeb"), "STRING"));
	mappage.put("HFIN", new BasicRecord("HFIN", "INTEGER", getMyAffecter_Agent().getClass().getField("hfin"), "STRING"));
	mappage.put("HDEBMN", new BasicRecord("HDEBMN", "INTEGER", getMyAffecter_Agent().getClass().getField("hdebmn"), "STRING"));
	mappage.put("HFINMN", new BasicRecord("HFINMN", "INTEGER", getMyAffecter_Agent().getClass().getField("hfinmn"), "STRING"));
	mappage.put("CODESERVICE", new BasicRecord("CODESERVICE", "VARCHAR", getMyAffecter_Agent().getClass().getField("codeservice"), "STRING"));
	return mappage;
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @return true ou false
 */
public boolean existeAffecter_Agent(nc.mairie.technique.Transaction aTransaction, String inv,String nomatr,String datedeb,String Hdeb) throws Exception {
	datedeb = Services.formateDateInternationale(datedeb);
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numeroinventaire='"+inv+"' and matricule="+nomatr+" and datedebut='"+datedeb+"' and hdeb = "+Hdeb);
}

}
