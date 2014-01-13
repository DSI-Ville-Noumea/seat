package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier PeBase
 */
public class PeBaseBroker extends BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerPeBase(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierPeBase(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerPeBase(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : PeBase.
 * @return java.util.ArrayList
 */
public ArrayList<PeBase> listerPeBase(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un PeBase.
 * @return PeBase
 */
public PeBase chercherPeBase(nc.mairie.technique.Transaction aTransaction, String mod,String ent) throws Exception {
	return (PeBase)executeSelect(aTransaction,"select * from "+getTable()+" where codemodele = "+mod+" and codeentretien = "+ent);
}
/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @return true ou false
 */
public boolean existePeBase(nc.mairie.technique.Transaction aTransaction, String mod,String ent) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where codemodele = "+mod+" and codeentretien = "+ent);
}

/**
 * Retourne un booléen.
 * Vérifie si le modèleinfos existe déjà
 * @return PeBase
 */
public boolean existePeBaseModele(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where codemodele = "+param+"");
}
public boolean existePeBaseTint(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where codeti = "+param+"");
}

/**
 * Retourne un ArrayList d'objet métier : PeBase.
 * @return java.util.ArrayList
 */
public ArrayList<PeBase> listerPeBaseModele(nc.mairie.technique.Transaction aTransaction,String mod) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codemodele="+mod);
}

/**
 * Retourne un ArrayList d'objet métier : PeBase.
 * @return java.util.ArrayList
 */
public ArrayList<PeBase> listerPeBaseModeleActif(nc.mairie.technique.Transaction aTransaction,String mod) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codemodele="+mod+" and datedesactivation = '0001-01-01'");
}

/**
 * Constructeur PeBaseBroker.
 */
public PeBaseBroker(PeBase aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PeBaseMetier
 */
@Override
protected PeBase definirMyMetier() {
	return new PeBase() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PeBaseMetier
 */
protected PeBase getMyPeBase() {
	return (PeBase)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.F_PE_BASE";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("CODEMODELE", new BasicRecord("CODEMODELE", "INTEGER", getMyPeBase().getClass().getField("codemodele"), "STRING"));
	mappage.put("CODEENTRETIEN", new BasicRecord("CODEENTRETIEN", "INTEGER", getMyPeBase().getClass().getField("codeentretien"), "STRING"));
	mappage.put("CODETI", new BasicRecord("CODETI", "INTEGER", getMyPeBase().getClass().getField("codeti"), "STRING"));
	mappage.put("INTERVALLE", new BasicRecord("INTERVALLE", "INTEGER", getMyPeBase().getClass().getField("intervalle"), "STRING"));
	mappage.put("DUREE", new BasicRecord("DUREE", "INTEGER", getMyPeBase().getClass().getField("duree"), "STRING"));
	mappage.put("COMMENTAIRE", new BasicRecord("COMMENTAIRE", "VARCHAR", getMyPeBase().getClass().getField("commentaire"), "STRING"));
	mappage.put("DESACTIVE", new BasicRecord("DESACTIVE", "VARCHAR", getMyPeBase().getClass().getField("desactive"), "STRING"));
	mappage.put("DATEDESACTIVATION", new BasicRecord("DATEDESACTIVATION", "DATE", getMyPeBase().getClass().getField("datedesactivation"), "DATE"));
	return mappage;
}
}
