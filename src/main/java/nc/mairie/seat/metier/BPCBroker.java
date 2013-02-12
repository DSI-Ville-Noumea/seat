package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.Services;
/**
 * Broker de l'Objet métier BPC
 */
public class BPCBroker extends nc.mairie.technique.BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerBPC(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierBPC(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerBPC(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : BPC.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerBPC(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un BPC.
 * @return BPC
 */
public BPC chercherBPC(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (BPC)executeSelect(aTransaction,"select * from "+getTable()+" where NUMEROBPC = "+cle+"");
}
/**
 * Retourne un BPC.
 * @return BPC
 */
public BPC chercherBPCPrecEquipDate(nc.mairie.technique.Transaction aTransaction,String date, String inv) throws Exception {
	//return (BPC)executeSelect(aTransaction,"select * from "+getTable()+" where numeroinventaire='"+inv+"' and date in (select max(date) from "+getTable()+" where numeroinventaire='"+inv+"'");
	
	if(date!=null){
		date = Services.formateDateInternationale(date);
		return (BPC)executeSelect(aTransaction,"select * from "+getTable()+" where numeroinventaire = '"+inv+"'and date in (select max(date) from "+getTable()+" where date<'"+date+"' and numeroinventaire='"+inv+"') order by valeurcompteur desc");
	}else{
		return new BPC();
	}
	
}

/**
 * Retourne un BPC.
 * @return BPC
 */
public BPC chercherBPCDerOT(nc.mairie.technique.Transaction aTransaction, String param,String inv) throws Exception {
	if (param==null){
		return (BPC)executeSelect(aTransaction,"select * from "+getTable()+" where date<>'0001-01-01' and numeroinventaire = '"+inv+"'");
	}else{
		if(!Services.estUneDate(param)){
			aTransaction.declarerErreur("La date n'est pas correcte.");
			return new BPC();
		}
		String date = Services.formateDateInternationale(param);
		return (BPC)executeSelect(aTransaction,"select * from "+getTable()+" where date < '"+date+"' and date<>'0001-01-01' and numeroinventaire = '"+inv+"' and numerobpc in (select max(numerobpc) from "+getTable()+" where date < '"+date+"' and date<>'0001-01-01' and numeroinventaire = '"+inv+"')");
	}
}

/**
 * Retourne un BPC.
 * @return BPC
 */
public BPC chercherBPCSuivEquipDate(nc.mairie.technique.Transaction aTransaction, String date,String inv) throws Exception {
	if(date!=null){
		date = Services.formateDateInternationale(date);
		return (BPC)executeSelect(aTransaction,"select * from "+getTable()+" where numeroinventaire = '"+inv+"' and date in (select min(date) from "+getTable()+" where date>'"+date+"' and numeroinventaire = '"+inv+"')");
	}else{
		return new BPC();
	}
	
}

/**
 * Retourne un booléen.
 * Vérifie si le BPC existe déjà
 * @return BPC
 */
public boolean existeBPC(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where NUMEROBPC = "+param+"");
}

/**
 * Retourne un BPC.
 * @return BPC
 */
public java.util.ArrayList chercherListBPC(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where NUMEROINVENTAIRE = '"+cle+"'");
}

public java.util.ArrayList listeBPCEquipAnnee(nc.mairie.technique.Transaction aTransaction, String cle,String date) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where NUMEROINVENTAIRE = '"+cle+"' and date>'"+date+"'");
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvBPC(nc.mairie.technique.Transaction aTransaction) throws Exception{

//	recherche du dernier numerobpc
	return executeCompter(aTransaction, "select max(numerobpc) from "+ getTable());
	
}

/**
 * Retourne un BPC.
 * @return BPC
 */
public java.util.ArrayList chercherBPCEquip(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return executeSelectListe(aTransaction,"select numerobpc,max(date),valeurcompteur from "+getTable()+" where NUMEROINVENTAIRE = '"+cle+"'");
}
/**
 * Retourne un ArrayList d'objet métier : BPC.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerBPCEquipement(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numeroinventaire = '"+cle+"'");
}

/**
 * Constructeur BPCBroker.
 */
public BPCBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.BPCMetier
 */
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new BPC() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.BPCMetier
 */
protected BPC getMyBPC() {
	return (BPC)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
protected java.lang.String definirNomTable() {
	return "SEAT.F_BPC";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("NUMEROBPC", new BasicRecord("NUMEROBPC", "INTEGER", getMyBPC().getClass().getField("numerobpc"), "STRING"));
	mappage.put("DATE", new BasicRecord("DATE", "DATE", getMyBPC().getClass().getField("date"), "DATE"));
	mappage.put("HEURE", new BasicRecord("HEURE", "VARCHAR", getMyBPC().getClass().getField("heure"), "STRING"));
	mappage.put("VALEURCOMPTEUR", new BasicRecord("VALEURCOMPTEUR", "INTEGER", getMyBPC().getClass().getField("valeurcompteur"), "STRING"));
	mappage.put("NUMEROPOMPE", new BasicRecord("NUMEROPOMPE", "INTEGER", getMyBPC().getClass().getField("numeropompe"), "STRING"));
	mappage.put("QUANTITE", new BasicRecord("QUANTITE", "INTEGER", getMyBPC().getClass().getField("quantite"), "STRING"));
	mappage.put("MODEDEPRISE", new BasicRecord("MODEDEPRISE", "INTEGER", getMyBPC().getClass().getField("modedeprise"), "STRING"));
	mappage.put("NUMEROINVENTAIRE", new BasicRecord("NUMEROINVENTAIRE", "VARCHAR", getMyBPC().getClass().getField("numeroinventaire"), "STRING"));
	return mappage;
}

/**
 * Retourne un booléen.
 * Vérifie si le modèleinfos existe déjà
 * @return BPC
 */
public boolean existeBPCEquip(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numeroinventaire = '"+param+"'");
}
public boolean existeBPCModePrise(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where modedeprise = "+param+"");
}

public boolean existeBPCPompe(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numeropompe = "+param+"");
}
/**
 * Retourne un ArrayList d'objet métier : BPCInfosCompletes.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerBPCParams(nc.mairie.technique.Transaction aTransaction,String inv,String periode) throws Exception {
	if(!inv.equals("")){
		inv = "where "+inv;
		if(!periode.equals("")){
			periode = "and "+periode;
		}
	}else{
		if(!periode.equals("")){
			periode = "where "+periode;
		}
	}
	
	return executeSelectListe(aTransaction,"select numerobpc,date,heure,valeurcompteur, numeropompe, quantite, modedeprise, numeroinventaire from "+getTable()+" "+inv+periode+" order by date desc");
}
}
