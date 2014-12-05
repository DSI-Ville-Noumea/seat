package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier TDuree
 */
public class TDureeBroker extends BasicBroker {
/**
 * Constructeur TDureeBroker.
 * @param aMetier BasicMetier
 */
public TDureeBroker(TDuree aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.TDureeMetier
 */
@Override
protected TDuree definirMyMetier() {
	return new TDuree() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.TDureeMetier
 */
protected TDuree getMyTDuree() {
	return (TDuree)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "F_TYPEDUREE";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("CODETD", new BasicRecord("CODETD", "INTEGER", getMyTDuree().getClass().getField("codetd"), "STRING"));
	mappage.put("DESIGNATIONDUREE", new BasicRecord("DESIGNATIONDUREE", "VARCHAR", getMyTDuree().getClass().getField("designationduree"), "STRING"));
	return mappage;
}
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerTDuree(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierTDuree(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerTDuree(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : TDuree.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<TDuree> listerTDuree(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un TDuree.
 * @param aTransaction Transaction
 * @param cle cle
 * @return TDuree
 * @throws Exception Exception
 */
public TDuree chercherTDuree(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (TDuree)executeSelect(aTransaction,"select * from "+getTable()+" where CODEtd = "+cle+"");
}

public int nouvCodeTDuree(nc.mairie.technique.Transaction aTransaction) throws Exception{

//	recherche du dernier codeduree
	return executeCompter(aTransaction, "select max(codetd) from "+ getTable());
	
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @param aTransaction Transaction
 * @param param param
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeTDuree(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where upper(designationduree) = '"+param.toUpperCase()+"'");
}

}
