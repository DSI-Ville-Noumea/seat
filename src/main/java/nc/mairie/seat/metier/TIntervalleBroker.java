package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier TIntervalle
 */
public class TIntervalleBroker extends BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerTIntervalle(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierTIntervalle(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerTIntervalle(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : TIntervalle.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<TIntervalle> listerTIntervalle(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un TIntervalle.
 * @param aTransaction Transaction
 * @param cle cle
 * @return TIntervalle
 * @throws Exception Exception
 */
public TIntervalle chercherTIntervalle(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (TIntervalle)executeSelect(aTransaction,"select * from "+getTable()+" where codeti = "+cle+"");
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvTIntervalle(nc.mairie.technique.Transaction aTransaction) throws Exception{

//	recherche du dernier codeti
	return executeCompter(aTransaction, "select max(codeti) from "+ getTable());
	
}

/**
 * Constructeur TIntervalleBroker.
 * @param aMetier BasicMetier
 */
public TIntervalleBroker(TIntervalle aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.TIntervalleMetier
 */
@Override
protected TIntervalle definirMyMetier() {
	return new TIntervalle() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.TIntervalleMetier
 */
protected TIntervalle getMyTIntervalle() {
	return (TIntervalle)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "F_TYPEINT";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("CODETI", new BasicRecord("CODETI", "INTEGER", getMyTIntervalle().getClass().getField("codeti"), "STRING"));
	mappage.put("DESIGNATION", new BasicRecord("DESIGNATION", "CHAR", getMyTIntervalle().getClass().getField("designation"), "STRING"));
	return mappage;
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @param aTransaction Transaction
 * @param param param
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeTIntervalle(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where upper(designation) = '"+param.toUpperCase()+"'");
}

}
