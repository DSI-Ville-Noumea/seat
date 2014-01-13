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
	return "SEAT.F_TYPEDUREE";
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
 */
public boolean creerTDuree(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierTDuree(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerTDuree(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : TDuree.
 * @return java.util.ArrayList
 */
public ArrayList<TDuree> listerTDuree(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un TDuree.
 * @return TDuree
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
 * @return true ou false
 */
public boolean existeTDuree(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where upper(designationduree) = '"+param.toUpperCase()+"'");
}

}
