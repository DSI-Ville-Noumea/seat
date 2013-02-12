package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier AgentsATM
 */
public class AgentsATMBroker extends nc.mairie.technique.BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerAgentsATM(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierAgentsATM(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerAgentsATM(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : AgentsATM.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerAgentsATM(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+", MAIRIE.SPPERS where "+getTable()+".matricule=MAIRIE.SPPERS.nomatr order by nom with ur");
}
/**
 * Retourne un AgentsATM.
 * @return AgentsATM
 */
public AgentsATM chercherAgentsATM(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (AgentsATM)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+"");
}
/**
 * Retourne un AgentsATM.
 * @return AgentsATM
 */
public AgentsATM chercherAgentsATMMatr(nc.mairie.technique.Transaction aTransaction, String nomatr) throws Exception {
	return (AgentsATM)executeSelect(aTransaction,"select * from "+getTable()+" where matricule = "+nomatr+"");
}
/**
 * Constructeur AgentsATMBroker.
 */
public AgentsATMBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AgentsATMMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new AgentsATM() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AgentsATMMetier
 */
protected AgentsATM getMyAgentsATM() {
	return (AgentsATM)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.F_AGENTSATM";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("MATRICULE", new BasicRecord("MATRICULE", "NUMERIC", getMyAgentsATM().getClass().getField("matricule"), "STRING"));
	mappage.put("ESTMECANICIEN", new BasicRecord("ESTMECANICIEN", "VARCHAR", getMyAgentsATM().getClass().getField("estmecanicien"), "STRING"));
	mappage.put("CODESPE", new BasicRecord("CODESPE", "INTEGER", getMyAgentsATM().getClass().getField("codespe"), "STRING"));
	return mappage;
}
/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @return true ou false
 */
public boolean existeAgentsATM(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where matricule = "+param);
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @return true ou false
 */
public boolean existeAgentsATMSpe(nc.mairie.technique.Transaction aTransaction, String spe) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where codespe="+spe);
}

}
