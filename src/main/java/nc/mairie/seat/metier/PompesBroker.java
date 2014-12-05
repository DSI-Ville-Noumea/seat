package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier Pompes
 */
public class PompesBroker extends BasicBroker {
/**
 * Constructeur PompesBroker.
 * @param aMetier BasicMetier
 */
public PompesBroker(Pompes aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PompesMetier
 */
@Override
protected Pompes definirMyMetier() {
	return new Pompes() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PompesMetier
 */
protected Pompes getMyPompes() {
	return (Pompes)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "F_POMPES";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NUM_POMPE", new BasicRecord("NUM_POMPE", "NUMERIC", getMyPompes().getClass().getField("num_pompe"), "STRING"));
	mappage.put("LIBELLE_POMPE", new BasicRecord("LIBELLE_POMPE", "VARCHAR", getMyPompes().getClass().getField("libelle_pompe"), "STRING"));
	mappage.put("COMMENTAIRE_POMPE", new BasicRecord("COMMENTAIRE_POMPE", "VARCHAR", getMyPompes().getClass().getField("commentaire_pompe"), "STRING"));
	return mappage;
}
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerPompes(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierPompes(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerPompes(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Pompes.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<Pompes> listerPompes(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un Pompes.
 * @param aTransaction Transaction
 * @param cle cle
 * @return Pompes
 * @throws Exception Exception
 */
public Pompes chercherPompes(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Pompes)executeSelect(aTransaction,"select * from "+getTable()+" where num_pompe = "+cle+"");
}

public boolean existePompes(nc.mairie.technique.Transaction aTransaction,String libelle) throws Exception{
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where upper(libelle_pompe) = '"+libelle.toUpperCase()+"'");
}

public boolean existePompesTout(nc.mairie.technique.Transaction aTransaction,String libelle,String commentaire) throws Exception{
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where upper(libelle_pompe) = '"+libelle.toUpperCase()+"' and upper(commentaire_pompe)='"+commentaire.toUpperCase()+"'");
}


public int nouvCodePompes(nc.mairie.technique.Transaction aTransaction) throws Exception{

	//	recherche du dernier code pompe
	return executeCompter(aTransaction, "select max(num_pompe) from "+ getTable());
	
}

}
