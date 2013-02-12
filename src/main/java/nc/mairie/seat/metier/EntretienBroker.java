package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier Entretien
 */
public class EntretienBroker extends nc.mairie.technique.BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerEntretien(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierEntretien(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerEntretien(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Entretien.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerEntretien(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" order by libelleentretien");
}
/**
 * Retourne un Entretien.
 * @return Entretien
 */
public Entretien chercherEntretien(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Entretien)executeSelect(aTransaction,"select * from "+getTable()+" where CODEENTRETIEN = "+cle+"");
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvEntretien(nc.mairie.technique.Transaction aTransaction) throws Exception{

//	recherche du dernier codeentretien
	return executeCompter(aTransaction, "select max(codeentretien) from "+ getTable());
	
}

/**
 * Constructeur EntretienBroker.
 */
public EntretienBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.EntretienMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new Entretien() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.EntretienMetier
 */
protected Entretien getMyEntretien() {
	return (Entretien)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.F_ENTRETIEN";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("CODEENTRETIEN", new BasicRecord("CODEENTRETIEN", "INTEGER", getMyEntretien().getClass().getField("codeentretien"), "STRING"));
	mappage.put("LIBELLEENTRETIEN", new BasicRecord("LIBELLEENTRETIEN", "VARCHAR", getMyEntretien().getClass().getField("libelleentretien"), "STRING"));
	return mappage;
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @return true ou false
 */
public boolean existeEntretien(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where upper(libelleentretien) = '"+param.toUpperCase()+"'");
}

}
