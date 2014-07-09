package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier ENGJU
 */
public class ENGJUBroker extends BasicBroker {
/**
 * Constructeur ENGJUBroker.
 * @param aMetier BasicMetier
 */
public ENGJUBroker(ENGJU aMetier) {
	super(aMetier);
}
/**
 * @return src/nc.mairie.seat.metier.ENGJUMetier
 */
protected ENGJU definirMyMetier() {
	return new ENGJU() ;
}
/**
 * @return src/nc.mairie.seat.metier.ENGJUMetier
 */
protected ENGJU getMyENGJU() {
	return (ENGJU)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
protected java.lang.String definirNomTable() {
	return "SEAT.V_ENJU";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("EXERCI", new BasicRecord("EXERCI", "INTEGER", getMyENGJU().getClass().getField("exerci"), "STRING"));
	mappage.put("IDETBS", new BasicRecord("IDETBS", "INTEGER", getMyENGJU().getClass().getField("idetbs"), "STRING"));
	mappage.put("ENSCOM", new BasicRecord("ENSCOM", "VARCHAR", getMyENGJU().getClass().getField("enscom"), "STRING"));
	mappage.put("NOENGJ", new BasicRecord("NOENGJ", "VARCHAR", getMyENGJU().getClass().getField("noengj"), "STRING"));
	mappage.put("NLENGJU", new BasicRecord("NLENGJU", "INTEGER", getMyENGJU().getClass().getField("nlengju"), "STRING"));
	mappage.put("CDDEP", new BasicRecord("CDDEP", "VARCHAR", getMyENGJU().getClass().getField("cddep"), "STRING"));
	mappage.put("MTLENJU", new BasicRecord("MTLENJU", "INTEGER", getMyENGJU().getClass().getField("mtlenju"), "STRING"));
	return mappage;
}
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws Exception Exception
 */
public boolean creerENGJU(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierENGJU(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerENGJU(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : ENGJU.
 * @param aTransaction Transaction
 * @param exerci exerci
 * @param noengj noengj
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<ENGJU> listerENGJU(nc.mairie.technique.Transaction aTransaction,String exerci, String noengj) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where exerci="+exerci+" and upper(noengj)='"+noengj.toUpperCase()+"'");
}
/**
 * Retourne un ArrayList d'objet métier : ENGJU.
 * @param aTransaction Transaction
 * @param numOt numOt
 * @param cddep cddep
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<ENGJU> listerENGJUGroupByCdepNoengjIdetbs(nc.mairie.technique.Transaction aTransaction, String numOt, String cddep) throws Exception{
	return executeSelectListe(aTransaction,
		"  select min(be.exerci) as exerci, idetbs, enscom, be.noengj, max(nlengju) as nlengju,cddep,    sum(mtlenju) as mtlenju from SEAT.F_BENGAGEMENT  be"+
		"  inner join "+getTable()+" enju on enju.noengj = be.noengj  "+
		"  where numot='"+numOt+"'"+
		"  and cddep like '%"+cddep+"%'"+
		"  group by cddep, be.noengj, idetbs, enscom"+
		"  order by be.noengj"
		);
}

/**
 * Retourne un ENGJU.
 * @param aTransaction Transaction
 * @param exerci exerci
 * @param noengj noengj
 * @param nlengju nlengju
 * @return ENGJU
 * @throws Exception Exception
 */
public ENGJU chercherENGJU(nc.mairie.technique.Transaction aTransaction,String exerci,String noengj,String nlengju) throws Exception {
	return (ENGJU)executeSelect(aTransaction,"select * from "+getTable()+" where exerci="+exerci+" and upper(noengj)='"+noengj.toUpperCase()+"' and nlengju="+nlengju);
}
/**
 * Retourne un ENGJU.
 * @param aTransaction Transaction
 * @param noengj noengj
 * @return ENGJU
 * @throws Exception Exception
 */
public ENGJU chercherpremierENGJU(nc.mairie.technique.Transaction aTransaction,String noengj) throws Exception {
	return (ENGJU) executeSelect(aTransaction,"select * from "+getTable()+" where upper(noengj)='"+noengj.toUpperCase()+"' fetch first row only");
	/*java.util.ArrayList result = new java.util.ArrayList();
	result = executeSelectListe(aTransaction,"select * from "+getTable()+" where upper(noengj)='"+noengj.toUpperCase()+"'");
	if (result==null||result.size()<1)
		return null;
	else
		return (ENGJU)result.get(0);
	*/	
}

/**
 * Retourne un ENGJU.
 * @param aTransaction Transaction
 * @param exerci exerci
 * @param noengj noengj
 * @return ENGJU
 * @throws Exception Exception
 */
public ENGJU chercherpremierENGJU(nc.mairie.technique.Transaction aTransaction,String exerci,String noengj) throws Exception {
	return (ENGJU)executeSelect(aTransaction, "select * from "+getTable()+" where exerci="+exerci+" and upper(noengj)='"+noengj.toUpperCase()+"' fetch first row only");
/*	java.util.ArrayList result = new java.util.ArrayList();
	result = executeSelectListe(aTransaction,"select * from "+getTable()+" where exerci="+exerci+" and upper(noengj)='"+noengj.toUpperCase()+"'");
	if (result==null||result.size()<1)
		return null;
	else
		return (ENGJU)result.get(0);
*/	
}

/**
 * Retourne un ENGJU.
 * @param aTransaction Transaction
 * @param noengj noengj
 * @return ENGJU
 * @throws Exception Exception
 */
public ENGJU chercherdernierENGJU(nc.mairie.technique.Transaction aTransaction,String noengj) throws Exception {
	return (ENGJU)executeSelect(aTransaction,"select * from "+getTable()+" where upper(noengj)='"+noengj.toUpperCase()+"' order by exerci desc fetch first row only");
	/*	java.util.ArrayList result = new java.util.ArrayList();
	result = executeSelectListe(aTransaction,"select * from "+getTable()+" where upper(noengj)='"+noengj.toUpperCase()+"' order by exerci desc");
	if (result==null||result.size()<1)
		return null;
	else
		return (ENGJU)result.get(0);
*/	
}

/**
 * Retourne un ArrayList d'objet métier : Fournisseurs.
 * @param aTransaction Transaction
 * @param param param
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<ENGJU> listerFournisseursNom(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where upper(enscom) like '"+param.toUpperCase()+"%'");
}
}
