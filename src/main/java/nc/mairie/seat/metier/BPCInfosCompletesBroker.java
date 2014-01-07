package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier BPCInfosCompletes
 */
public class BPCInfosCompletesBroker extends nc.mairie.technique.BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : BPCInfosCompletes.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerBPCInfosCompletes(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" with ur");
}

/**
 * Retourne un ArrayList d'objet métier : BPCInfosCompletes.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerBPCInfosCompletesParams(nc.mairie.technique.Transaction aTransaction,String inv,String servi,String periode) throws Exception {
	if(!inv.equals("")){
		if(!periode.equals("")){
			periode = "and "+periode;
			if(!servi.equals("")){
				servi = "and "+servi;
			}
		}else{
			if(!servi.equals("")){
				servi = "and "+servi;
			}
		}
	}else{
		if(!periode.equals("")){
			if(!servi.equals("")){
				servi = "and "+servi;
			}
		}
	}
	
	return executeSelectListe(aTransaction,"select distinct(numerobpc),date,heure,valeurcompteur, numeropompe, quantite, modedeprise, numeroinventaire, numeroimmatriculation, codeservice, ddebut, dfin, liserv,datemiseencirculation from "+getTable()+" where "+inv+periode+servi+" order by date with ur");
}

/**
 * Retourne un BPCInfosCompletes.
 * @return BPCInfosCompletes
 */
public BPCInfosCompletes chercherBPCInfosCompletes(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (BPCInfosCompletes)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+" with ur");
}
/**
 * Constructeur BPCInfosCompletesBroker.
 */
public BPCInfosCompletesBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.BPCInfosCompletesMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new BPCInfosCompletes() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.BPCInfosCompletesMetier
 */
protected BPCInfosCompletes getMyBPCInfosCompletes() {
	return (BPCInfosCompletes)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.V_BPCEQUIPINFOS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NUMEROBPC", new BasicRecord("NUMEROBPC", "INTEGER", getMyBPCInfosCompletes().getClass().getField("numerobpc"), "STRING"));
	mappage.put("DATE", new BasicRecord("DATE", "DATE", getMyBPCInfosCompletes().getClass().getField("date"), "DATE"));
	mappage.put("HEURE", new BasicRecord("HEURE", "VARCHAR", getMyBPCInfosCompletes().getClass().getField("heure"), "STRING"));
	mappage.put("VALEURCOMPTEUR", new BasicRecord("VALEURCOMPTEUR", "INTEGER", getMyBPCInfosCompletes().getClass().getField("valeurcompteur"), "STRING"));
	mappage.put("NUMEROPOMPE", new BasicRecord("NUMEROPOMPE", "INTEGER", getMyBPCInfosCompletes().getClass().getField("numeropompe"), "STRING"));
	mappage.put("QUANTITE", new BasicRecord("QUANTITE", "INTEGER", getMyBPCInfosCompletes().getClass().getField("quantite"), "STRING"));
	mappage.put("MODEDEPRISE", new BasicRecord("MODEDEPRISE", "INTEGER", getMyBPCInfosCompletes().getClass().getField("modedeprise"), "STRING"));
	mappage.put("NUMEROINVENTAIRE", new BasicRecord("NUMEROINVENTAIRE", "VARCHAR", getMyBPCInfosCompletes().getClass().getField("numeroinventaire"), "STRING"));
	mappage.put("NUMEROIMMATRICULATION", new BasicRecord("NUMEROIMMATRICULATION", "VARCHAR", getMyBPCInfosCompletes().getClass().getField("numeroimmatriculation"), "STRING"));
	mappage.put("DATEMISEENCIRCULATION", new BasicRecord("DATEMISEENCIRCULATION", "DATE", getMyBPCInfosCompletes().getClass().getField("datemiseencirculation"), "DATE"));
	mappage.put("CODESERVICE", new BasicRecord("CODESERVICE", "VARCHAR", getMyBPCInfosCompletes().getClass().getField("codeservice"), "STRING"));
	mappage.put("DDEBUT", new BasicRecord("DDEBUT", "DATE", getMyBPCInfosCompletes().getClass().getField("ddebut"), "DATE"));
	mappage.put("DFIN", new BasicRecord("DFIN", "DATE", getMyBPCInfosCompletes().getClass().getField("dfin"), "DATE"));
	mappage.put("LISERV", new BasicRecord("LISERV", "CHAR", getMyBPCInfosCompletes().getClass().getField("liserv"), "STRING"));
	mappage.put("NOMATR", new BasicRecord("NOMATR", "NUMERIC", getMyBPCInfosCompletes().getClass().getField("nomatr"), "STRING"));
	return mappage;
}
}
