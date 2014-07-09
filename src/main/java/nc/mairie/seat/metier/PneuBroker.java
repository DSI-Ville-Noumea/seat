package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier Pneu
 */
public class PneuBroker extends BasicBroker {
/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvCodePneu(nc.mairie.technique.Transaction aTransaction) throws Exception{

	//	recherche du dernier codepneu
	return executeCompter(aTransaction, "select max(codepneu) from "+ getTable());
	
}

/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerPneu(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierPneu(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerPneu(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Pneu.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<Pneu> listerPneu(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" order by dimension");
}
/**
 * Retourne un Pneu.
 * @param aTransaction Transaction
 * @param cle cle
 * @return Pneu
 * @throws Exception Exception
 */
public Pneu chercherPneu(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Pneu)executeSelect(aTransaction,"select * from "+getTable()+" where CODEPNEU = "+cle+"");
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @param aTransaction Transaction
 * @param param param
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existePneu(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where upper(dimension) = '"+param.toUpperCase()+"'");
}

/**
 * Constructeur PneuBroker.
 * @param aMetier BasicMetier
 */
public PneuBroker(Pneu aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PneuMetier
 */
@Override
protected Pneu definirMyMetier() {
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
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("CODEPNEU", new BasicRecord("CODEPNEU", "INTEGER", getMyPneu().getClass().getField("codepneu"), "STRING"));
	mappage.put("DIMENSION", new BasicRecord("DIMENSION", "VARCHAR", getMyPneu().getClass().getField("dimension"), "STRING"));
	return mappage;
}
}
