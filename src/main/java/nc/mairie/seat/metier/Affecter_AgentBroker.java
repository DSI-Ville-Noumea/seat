package nc.mairie.seat.metier;

import java.util.ArrayList;

import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.Transaction;

/**
 * Broker de l'Objet métier Affecter_Agent
 */
public class Affecter_AgentBroker extends BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : Affecter_Agent.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<Affecter_Agent> listerAffecter_Agent(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un Affecter_Agent.
 * @param aTransaction Transaction
 * @param inv inv
 * @param matr matr
 * @param date date
 * @return Affecter_Agent
 * @throws Exception Exception
 */
public Affecter_Agent chercherAffecter_Agent(nc.mairie.technique.Transaction aTransaction, String inv,String matr,String date) throws Exception {
	String dateN = Services.formateDateInternationale(date);
	return (Affecter_Agent)executeSelect(aTransaction,"select * from "+getTable()+" where numeroinventaire = '"+inv+"' and matricule = "+matr+" and datedebut ='"+dateN+"'");
}
/**
 * Retourne un ArrayList d'objet métier : Affecter_Agent.
 * @param aTransaction Transaction
 * @param param param
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<Affecter_Agent> chercherListerAffecter_AgentEquip(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numeroinventaire like '"+param+"%'");
}

public ArrayList<Affecter_Agent> chercherListerAffecter_AgentEquipSce(nc.mairie.technique.Transaction aTransaction,String inv,String param,String date) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codeservice like '"+param+"%' and numeroinventaire='"+inv+"' and datedebut<='"+date+"' and datefin='0001-01-01'");
}

public ArrayList<Affecter_Agent> chercherListerAffecter_AgentEquipSceEnCours(nc.mairie.technique.Transaction aTransaction,String inv,String param,String date) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codeservice like '"+param+"%' and numeroinventaire='"+inv+"' and datedebut>='"+date+"' order by datedebut desc");
}

/**
 * Retourne un ArrayList d'objet métier : Affecter_Agent.
 * @param aTransaction Transaction
 * @param param param
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<Affecter_Agent> chercherListerAffecter_AgentService(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codeservice = '"+param+"'");
}

/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerAffecter_Agent(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierAffecter_Agent(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerAffecter_Agent(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Constructeur Affecter_AgentBroker.
 * @param aMetier BasicMetier
 */
public Affecter_AgentBroker(Affecter_Agent aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.Affecter_AgentMetier
 */
@Override
protected Affecter_Agent definirMyMetier() {
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
	return "F_AFFECTER";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
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
 * @param aTransaction Transaction
 * @param inv inv
 * @param nomatr nomatr
 * @param datedeb datedeb
 * @param Hdeb Hdeb
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeAffecter_Agent(nc.mairie.technique.Transaction aTransaction, String inv,String nomatr,String datedeb,String Hdeb) throws Exception {
	datedeb = Services.formateDateInternationale(datedeb);
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numeroinventaire='"+inv+"' and matricule="+nomatr+" and datedebut='"+datedeb+"' and hdeb = "+Hdeb);
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @param aTransaction Transaction
 * @param inv inv
 * @param datedeb datedeb
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeAffecter_AgentAvantDate(Transaction aTransaction, String inv,String datedeb) throws Exception {
	datedeb = Services.formateDateInternationale(datedeb);
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numeroinventaire='"+inv+"' and (datefin >  '"+datedeb+"' or datefin = '0001-01-01')");
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @param aTransaction Transaction
 * @param inv inv
 * @param datedeb datedeb
 * @param datefin datefin
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeAffecter_AgentEntreDate(Transaction aTransaction, String inv,String datedeb, String datefin) throws Exception {
	datedeb = Services.formateDateInternationale(datedeb);
	datefin = datefin == null ? "9999-12-31" : Services.formateDateInternationale(datefin);
	
	datefin = "0001-01-01".equals(datefin) ? "9999-12-21" : datefin;
	
	if (datefin.compareTo(datedeb) < 0) {
		String temp = datedeb;
		datedeb = datefin;
		datefin = temp;
	}
	
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numeroinventaire='"+inv+"' and ("
			+ "(datedebut between '"+datedeb+"' and '"+datefin+"') or "
			+ "(datefin between '"+datedeb+"' and '"+datefin+"') or "
			+ "('"+datedeb+"' between datedebut and (case when datefin='0001-01-01' then '9999-12-31' else datefin end) ) or "
			+ "('"+datefin+"' between datedebut and (case when datefin='0001-01-01' then '9999-12-31' else datefin end) ) )");

}


}
