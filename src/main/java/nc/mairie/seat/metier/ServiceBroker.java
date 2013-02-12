package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier Service
 */
public class ServiceBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur ServiceBroker.
 */
public ServiceBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.ServiceMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new Service() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.ServiceMetier
 */
protected Service getMyService() {
	return (Service)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "MAIRIE.SISERV";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("SERVI", new BasicRecord("SERVI", "CHAR", getMyService().getClass().getField("servi"), "STRING"));
	mappage.put("LISERV", new BasicRecord("LISERV", "CHAR", getMyService().getClass().getField("liserv"), "STRING"));
	mappage.put("LI22", new BasicRecord("LI22", "CHAR", getMyService().getClass().getField("li22"), "STRING"));
	mappage.put("CODACT", new BasicRecord("CODACT", "CHAR", getMyService().getClass().getField("codact"), "STRING"));
	return mappage;
}
/**
 * Retourne un ArrayList d'objet métier : Service.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerService(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where liserv<>'' and codact<>'I' order by liserv with ur");
}
/**
 * Retourne un Service.
 * @return Service
 */
public Service chercherService(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	//MODIF OF ICI 5 mars 2010
	//return (Service)executeSelect(aTransaction,"select * from "+getTable()+" where SERVI = '"+cle+"' and codact<>'I'");
	return (Service)executeSelect(aTransaction,"select * from "+getTable()+" where SERVI = '"+cle+"' with ur");
}
/**
 * Retourne un Service.
 * @return Service
 */
public Service chercherServiceActifAvecAcronyme(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Service)executeSelect(aTransaction,"select * from "+getTable()+" where SIGLE = '"+cle+"' and codact <> 'I' with ur");
}

/**
 * Retourne un arrayList Service.
 * @return Service
 */
public java.util.ArrayList chercherListServiceTous(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	//return executeSelectListe(aTransaction,"select * from "+getTable()+",MAIRIE.SPMTSR where mairie.siserv.servi=mairie.spmtsr.servi and CODACT<>'I' and upper(liserv) like '"+param.toUpperCase()+"%' or mairie.siserv.servi like'"+param+"%' order by liserv");
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where CODACT<>'I' and (upper(liserv) like '"+param.toUpperCase()+"%' or servi like'"+param+"%') order by liserv with ur");
}
/**
 * Retourne un arrayList Service.
 * @return Service
 */
public java.util.ArrayList chercherListServiceEquip(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	//return executeSelectListe(aTransaction,"select distinct(liserv),servi from "+getTable()+", SEAT.f_affecter_sce where servi = codeservice and upper(liserv) like '"+param.toUpperCase()+"%' and dfin ='0001-01-01' order by liserv");
	return executeSelectListe(aTransaction,"select distinct(liserv),servi from "+getTable()+", SEAT.f_aff_sce where servi = codeservice and dfin ='0001-01-01' and codact<>'I' and (upper(liserv) like '"+param.toUpperCase()+"%' or codeservice like '"+param.toUpperCase()+"%') order by liserv with ur");
}

public java.util.ArrayList chercherListServiceAccro(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where sigle like '"+param+"%' order by servi");
}

}
