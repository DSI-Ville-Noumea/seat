package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier Marques
 */
public class MarquesBroker extends BasicBroker {
/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * author : Coralie NICOLAS
 */
public int nouvMarques(nc.mairie.technique.Transaction aTransaction) throws Exception{

//	recherche du dernier codemarque
	return executeCompter(aTransaction, "select max(codemarque) from "+ getTable());
	
}

/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws Exception Exception
 */
public boolean creerMarques(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierMarques(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerMarques(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Marques.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<Marques> listerMarques(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" order by designationmarque");
}
/**
 * Retourne un Marques.
 * @param aTransaction Transaction
 * @param cle cle
 * @return Marques
 * @throws Exception Exception
 */
public Marques chercherMarques(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Marques)executeSelect(aTransaction,"select * from "+getTable()+" where CODEMARQUE = "+cle+"");
}

/**
 * Retourne un ArrayList d'objet métier : Marques.
 * On sélectionne toutes les marques qui ont des modèles
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<Marques> listerMarquesModele(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codemarque in (select codemarque from f_modeles) order by designationmarque");
}

public ArrayList<Marques> listerMarquesModeleMT(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codemarque in (select codemarque from f_modeles, f_typeequip where f_modeles.codete=f_typeequip.codetypeequip and f_typeequip.typete='MT') order by designationmarque");
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @param aTransaction Transaction
 * @param param param
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeMarques(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where upper(designationmarque) = '"+param.toUpperCase()+"'");
}

/**
 * Constructeur MarquesBroker.
 * @param aMetier BasicMetier
 */
public MarquesBroker(Marques aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.MarquesMetier
 */
@Override
protected Marques definirMyMetier() {
	return new Marques() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.MarquesMetier
 */
protected Marques getMyMarques() {
	return (Marques)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "F_MARQUES";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("CODEMARQUE", new BasicRecord("CODEMARQUE", "INTEGER", getMyMarques().getClass().getField("codemarque"), "STRING"));
	mappage.put("DESIGNATIONMARQUE", new BasicRecord("DESIGNATIONMARQUE", "VARCHAR", getMyMarques().getClass().getField("designationmarque"), "STRING"));
	return mappage;
}
}
