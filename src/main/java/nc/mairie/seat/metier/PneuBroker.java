package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier Pneu
 */
public class PneuBroker extends nc.mairie.technique.BasicBroker {
/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
* 
 */
public int nouvCodePneu(nc.mairie.technique.Transaction aTransaction) throws Exception{

	//	recherche du dernier codepneu
	return executeCompter(aTransaction, "select max(codepneu) from "+ getTable());
	
}

/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerPneu(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierPneu(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerPneu(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Pneu.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerPneu(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" order by dimension");
}
/**
 * Retourne un Pneu.
 * @return Pneu
 */
public Pneu chercherPneu(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Pneu)executeSelect(aTransaction,"select * from "+getTable()+" where CODEPNEU = "+cle+"");
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @return true ou false
 */
public boolean existePneu(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where upper(dimension) = '"+param.toUpperCase()+"'");
}

/**
 * Constructeur PneuBroker.
 */
public PneuBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PneuMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new Pneu() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PneuMetier
 */
protected Pneu getMyPneu() {
	return (Pneu)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.F_PNEU";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("CODEPNEU", new BasicRecord("CODEPNEU", "INTEGER", getMyPneu().getClass().getField("codepneu"), "STRING"));
	mappage.put("DIMENSION", new BasicRecord("DIMENSION", "VARCHAR", getMyPneu().getClass().getField("dimension"), "STRING"));
	return mappage;
}
}
