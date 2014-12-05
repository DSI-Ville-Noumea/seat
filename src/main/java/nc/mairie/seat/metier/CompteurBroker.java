package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier Compteur
 */
public class CompteurBroker extends BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws Exception Exception
 */
public boolean creerCompteur(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierCompteur(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerCompteur(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Compteur.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<Compteur> listerCompteur(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" order by designationcompteur");
}
/**
 * Retourne un Compteur.
 * @param aTransaction Transaction
 * @param cle cle
 * @return Compteur
 * @throws Exception Exception
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
 * @param aTransaction Transaction
 * @param param param
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeCompteur(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where upper(designationcompteur) = '"+param.toUpperCase()+"'");
}
/**
 * Constructeur CompteurBroker.
 * @param aMetier BasicMetier
 */
public CompteurBroker(Compteur aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.CompteurMetier
 */
@Override
protected Compteur definirMyMetier() {
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
	return "F_COMPTEUR";
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
