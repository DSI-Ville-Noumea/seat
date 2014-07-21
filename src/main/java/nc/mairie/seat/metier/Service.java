package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier Service
 */
public class Service extends BasicMetier {
	public String servi;
	public String liserv;
	public String li22;
	public String codact;
/**
 * Constructeur Service.
 */
public Service() {
	super();
}
/**
 * Getter de l'attribut servi.
 * @return String
 */
public String getServi() {
	return servi;
}
/**
 * Setter de l'attribut servi.
 * @param newServi newServi
 */
public void setServi(String newServi) { 
	servi = newServi;
}
/**
 * Getter de l'attribut liserv.
 * @return String
 */
public String getLiserv() {
	return liserv;
}
/**
 * Setter de l'attribut liserv.
 * @param newLiserv newLiserv
 */
public void setLiserv(String newLiserv) { 
	liserv = newLiserv;
}
/**
 * Getter de l'attribut li22.
 * @return String
 */
public String getLi22() {
	return li22;
}
/**
 * Setter de l'attribut li22.
 * @param newLi22 newLi22
 */
public void setLi22(String newLi22) { 
	li22 = newLi22;
}
/**
 * Getter de l'attribut codact.
 * @return String
 */
public String getCodact() {
	return codact;
}
/**
 * Setter de l'attribut codact.
 * @param newCodact newCodact
 */
public void setCodact(String newCodact) { 
	codact = newCodact;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new ServiceBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected ServiceBroker getMyServiceBroker() {
	return (ServiceBroker)getMyBasicBroker();
}
/**
* Renvoie une chaîne correspondant à la valeur de cet objet.
* @return une représentation sous forme de chaîne du destinataire
*/
@Override
public String toString() {
	// Insérez ici le code pour finaliser le destinataire
	// Cette implémentation transmet le message au super. Vous pouvez remplacer ou compléter le message.
	return super.toString();
}
/**
 * Retourne un ArrayList d'objet métier : Service.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<Service> listerService(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Service unService = new Service();
	return unService.getMyServiceBroker().listerService(aTransaction);
}
/**
 * Retourne un Service.
 * @param aTransaction Transaction
 * @param code code
 * @return Service
 * @throws Exception Exception
 */
public static Service chercherService(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	Service unService = new Service();
	return unService.getMyServiceBroker().chercherService(aTransaction, code);
}


/**
 * Retourne un Service.
 * @param aTransaction Transaction
 * @param code code
 * @return Service
 * @throws Exception Exception
 */
public static Service chercherServiceActifAvecAcronyme(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	Service unService = new Service();
	return unService.getMyServiceBroker().chercherServiceActifAvecAcronyme(aTransaction, code);
}


/**
 * Retourne un Service.
 * @param aTransaction Transaction
 * @param param param
 * @return Service
 * @throws Exception Exception
 */
public static ArrayList<Service> chercherListServiceTous(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	Service unService = new Service();
	return unService.getMyServiceBroker().chercherListServiceTous(aTransaction, param);
}

/**
 * Retourne un Service.
 * @param aTransaction Transaction
 * @param param param
 * @return Service
 * @throws Exception Exception
 */
public static ArrayList<Service> chercherListServiceEquip(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	Service unService = new Service();
	return unService.getMyServiceBroker().chercherListServiceEquip(aTransaction, param);
}

public static ArrayList<Service> chercherListServiceAccro(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	Service unService = new Service();
	return unService.getMyServiceBroker().chercherListServiceAccro(aTransaction, param);
}

}
