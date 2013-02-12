package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier Pompes
 */
public class PompesBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur PompesBroker.
 */
public PompesBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PompesMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
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
	return "SEAT.F_POMPES";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("NUM_POMPE", new BasicRecord("NUM_POMPE", "NUMERIC", getMyPompes().getClass().getField("num_pompe"), "STRING"));
	mappage.put("LIBELLE_POMPE", new BasicRecord("LIBELLE_POMPE", "VARCHAR", getMyPompes().getClass().getField("libelle_pompe"), "STRING"));
	mappage.put("COMMENTAIRE_POMPE", new BasicRecord("COMMENTAIRE_POMPE", "VARCHAR", getMyPompes().getClass().getField("commentaire_pompe"), "STRING"));
	return mappage;
}
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerPompes(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierPompes(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerPompes(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Pompes.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerPompes(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un Pompes.
 * @return Pompes
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
