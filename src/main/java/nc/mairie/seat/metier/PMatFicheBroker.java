package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier FPM
 */
public class PMatFicheBroker extends nc.mairie.technique.BasicBroker {
/**
 * @return JavaSource/nc.mairie.seat.metier.PMateriel_FicheMetier
 */
protected FPM getMyPMateriel_Fiche() {
	return (FPM)getMyBasicMetier();
}
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerPMateriel_Fiche(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierPMateriel_Fiche(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerPMateriel_Fiche(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : FPM.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerPMateriel_Fiche(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un FPM.
 * @return FPM
 */
public FPM chercherPMateriel_Fiche(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (FPM)executeSelect(aTransaction,"select * from "+getTable()+" where numfiche = "+cle+"");
}
/**
 * Constructeur PMatFicheBroker.
 */
public PMatFicheBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PMatFicheMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new FPM() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PMatFicheMetier
 */
protected FPM getMyPMatFiche() {
	return (FPM)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.F_FPM";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NUMFICHE", new BasicRecord("NUMFICHE", "INTEGER", getMyPMatFiche().getClass().getField("numfiche"), "STRING"));
	mappage.put("PMINV", new BasicRecord("PMINV", "VARCHAR", getMyPMatFiche().getClass().getField("pminv"), "STRING"));
	mappage.put("DENTREE", new BasicRecord("DENTREE", "DATE", getMyPMatFiche().getClass().getField("dentree"), "DATE"));
	mappage.put("DSORTIE", new BasicRecord("DSORTIE", "DATE", getMyPMatFiche().getClass().getField("dsortie"), "DATE"));
	mappage.put("VALIDE", new BasicRecord("VALIDE", "VARCHAR", getMyPMatFiche().getClass().getField("valide"), "STRING"));
	return mappage;
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvCodeOt(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//	recherche du dernier numfiche
	return executeCompter(aTransaction, "select max(numfiche) from "+ getTable());
	
}

}
