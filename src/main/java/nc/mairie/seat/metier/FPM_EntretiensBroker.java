package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier FPM_Entretiens
 */
public class FPM_EntretiensBroker extends BasicBroker {
/**
 * Constructeur FPM_EntretiensBroker.
 */
public FPM_EntretiensBroker(FPM_Entretiens aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.FPM_EntretiensMetier
 */
@Override
protected FPM_Entretiens definirMyMetier() {
	return new FPM_Entretiens() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.FPM_EntretiensMetier
 */
protected FPM_Entretiens getMyFPM_Entretiens() {
	return (FPM_Entretiens)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.F_FPM_ENTRETIEN";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NUMFICHE", new BasicRecord("NUMFICHE", "INTEGER", getMyFPM_Entretiens().getClass().getField("numfiche"), "STRING"));
	mappage.put("CODEENTRETIEN", new BasicRecord("CODEENTRETIEN", "INTEGER", getMyFPM_Entretiens().getClass().getField("codeentretien"), "STRING"));
	mappage.put("DATE", new BasicRecord("DATE", "DATE", getMyFPM_Entretiens().getClass().getField("date"), "DATE"));
	mappage.put("COMMENTAIRE", new BasicRecord("COMMENTAIRE", "VARCHAR", getMyFPM_Entretiens().getClass().getField("commentaire"), "STRING"));
	return mappage;
}
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerFPM_Entretiens(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierFPM_Entretiens(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerFPM_Entretiens(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : FPM_Entretiens.
 * @return java.util.ArrayList
 */
public ArrayList<FPM_Entretiens> listerFPM_Entretiens(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un FPM_Entretiens.
 * @return FPM_Entretiens
 */
public FPM_Entretiens chercherFPM_Entretiens(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (FPM_Entretiens)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+"");
}
}
