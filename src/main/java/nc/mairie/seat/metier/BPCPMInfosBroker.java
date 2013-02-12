package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier BPCPMInfos
 */
public class BPCPMInfosBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur BPCPMInfosBroker.
 */
public BPCPMInfosBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.BPCPMInfosMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new BPCPMInfos() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.BPCPMInfosMetier
 */
protected BPCPMInfos getMyBPCPMInfos() {
	return (BPCPMInfos)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.V_BPCPMINFOS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("NUMEROBPC", new BasicRecord("NUMEROBPC", "INTEGER", getMyBPCPMInfos().getClass().getField("numerobpc"), "STRING"));
	mappage.put("DATE", new BasicRecord("DATE", "DATE", getMyBPCPMInfos().getClass().getField("date"), "DATE"));
	mappage.put("HEURE", new BasicRecord("HEURE", "VARCHAR", getMyBPCPMInfos().getClass().getField("heure"), "STRING"));
	mappage.put("VALEURCOMPTEUR", new BasicRecord("VALEURCOMPTEUR", "INTEGER", getMyBPCPMInfos().getClass().getField("valeurcompteur"), "STRING"));
	mappage.put("NUMEROPOMPE", new BasicRecord("NUMEROPOMPE", "INTEGER", getMyBPCPMInfos().getClass().getField("numeropompe"), "STRING"));
	mappage.put("QUANTITE", new BasicRecord("QUANTITE", "INTEGER", getMyBPCPMInfos().getClass().getField("quantite"), "STRING"));
	mappage.put("MODEDEPRISE", new BasicRecord("MODEDEPRISE", "INTEGER", getMyBPCPMInfos().getClass().getField("modedeprise"), "STRING"));
	mappage.put("NUMEROINVENTAIRE", new BasicRecord("NUMEROINVENTAIRE", "VARCHAR", getMyBPCPMInfos().getClass().getField("numeroinventaire"), "STRING"));
	mappage.put("PMSERIE", new BasicRecord("PMSERIE", "VARCHAR", getMyBPCPMInfos().getClass().getField("pmserie"), "STRING"));
	mappage.put("DMES", new BasicRecord("DMES", "DATE", getMyBPCPMInfos().getClass().getField("dmes"), "DATE"));
	mappage.put("SISERV", new BasicRecord("SISERV", "INTEGER", getMyBPCPMInfos().getClass().getField("siserv"), "STRING"));
	mappage.put("DDEBUT", new BasicRecord("DDEBUT", "DATE", getMyBPCPMInfos().getClass().getField("ddebut"), "DATE"));
	mappage.put("DFIN", new BasicRecord("DFIN", "DATE", getMyBPCPMInfos().getClass().getField("dfin"), "DATE"));
	mappage.put("LISERV", new BasicRecord("LISERV", "CHAR", getMyBPCPMInfos().getClass().getField("liserv"), "STRING"));
	mappage.put("NOMATR", new BasicRecord("NOMATR", "INTEGER", getMyBPCPMInfos().getClass().getField("nomatr"), "STRING"));
	return mappage;
}
/**
 * Retourne un ArrayList d'objet métier : BPCPMInfos.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerBPCPMInfos(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" with ur");
}
/**
 * Retourne un BPCPMInfos.
 * @return BPCPMInfos
 */
public BPCPMInfos chercherBPCPMInfos(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (BPCPMInfos)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+" with ur");
}
/**
 * Retourne un ArrayList d'objet métier : BPCInfosCompletes.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerBPCPMInfosParams(nc.mairie.technique.Transaction aTransaction,String inv,String servi,String periode) throws Exception {
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
	
	return executeSelectListe(aTransaction,"select distinct(numerobpc),date,heure,valeurcompteur, numeropompe, quantite, modedeprise, numeroinventaire, pmserie, siserv, ddebut, dfin, liserv,dmes from "+getTable()+" where "+inv+periode+servi+" order by date with ur");
}
}
