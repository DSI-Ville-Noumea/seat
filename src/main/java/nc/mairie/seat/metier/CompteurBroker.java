package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier Compteur
 */
public class CompteurBroker extends nc.mairie.technique.BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerCompteur(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierCompteur(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerCompteur(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Compteur.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerCompteur(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" order by designationcompteur");
}
/**
 * Retourne un Compteur.
 * @return Compteur
 */
public Compteur chercherCompteur(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Compteur)executeSelect(aTransaction,"select * from "+getTable()+" where CODECOMPTEUR = "+cle+"");
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvCompteur(nc.mairie.technique.Transaction aTransaction) throws Exception{

//	recherche du dernier codecompteur
	return executeCompter(aTransaction, "select max(codecompteur) from "+ getTable());
	
}
/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @return true ou false
 */
public boolean existeCompteur(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where upper(designationcompteur) = '"+param.toUpperCase()+"'");
}
/**
 * Constructeur CompteurBroker.
 */
public CompteurBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.CompteurMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new Compteur() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.CompteurMetier
 */
protected Compteur getMyCompteur() {
	return (Compteur)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.F_COMPTEUR";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("CODECOMPTEUR", new BasicRecord("CODECOMPTEUR", "INTEGER", getMyCompteur().getClass().getField("codecompteur"), "STRING"));
	mappage.put("DESIGNATIONCOMPTEUR", new BasicRecord("DESIGNATIONCOMPTEUR", "VARCHAR", getMyCompteur().getClass().getField("designationcompteur"), "STRING"));
	return mappage;
}
}
