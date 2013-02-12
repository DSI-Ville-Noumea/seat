package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier DeclarationAgentEquip
 */
public class DeclarationAgentEquipBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur DeclarationAgentEquipBroker.
 */
public DeclarationAgentEquipBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return src/nc.mairie.seat.metier.DeclarationAgentEquipMetier
 */
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new DeclarationAgentEquip() ;
}
/**
 * @return src/nc.mairie.seat.metier.DeclarationAgentEquipMetier
 */
protected DeclarationAgentEquip getMyDeclarationAgentEquip() {
	return (DeclarationAgentEquip)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
protected java.lang.String definirNomTable() {
	return "SEAT.V_DECLARATION_AGENT_EQUIP";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("CODEDEC", new BasicRecord("CODEDEC", "NUMERIC", getMyDeclarationAgentEquip().getClass().getField("codedec"), "STRING"));
	mappage.put("DATE", new BasicRecord("DATE", "DATE", getMyDeclarationAgentEquip().getClass().getField("date"), "DATE"));
	mappage.put("IMMAT", new BasicRecord("IMMAT", "VARCHAR", getMyDeclarationAgentEquip().getClass().getField("immat"), "STRING"));
	mappage.put("DECLARANT", new BasicRecord("DECLARANT", "VARCHAR", getMyDeclarationAgentEquip().getClass().getField("declarant"), "STRING"));
	mappage.put("CODEOT", new BasicRecord("CODEOT", "NUMERIC", getMyDeclarationAgentEquip().getClass().getField("codeot"), "STRING"));
	mappage.put("CODESERVICE", new BasicRecord("CODESERVICE", "VARCHAR", getMyDeclarationAgentEquip().getClass().getField("codeservice"), "STRING"));
	return mappage;
}
/**
 * Retourne un ArrayList d'objet métier : DeclarationAgentEquip.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerDeclarationAgentEquip(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" order by \"DATE\" desc, codedec desc with ur");
}
}
