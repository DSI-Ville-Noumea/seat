package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier Agents_Sces
 */
public class Agents_Sces extends BasicMetier {
	public String nomatr;
	public String servi;
	public String refarr;
	public String datdeb;
	public String datfin;
	public String cdecol;
/**
 * Constructeur Agents_Sces.
 */
public Agents_Sces() {
	super();
}
/**
 * Getter de l'attribut nomatr.
 */
public String getNomatr() {
	return nomatr;
}
/**
 * Setter de l'attribut nomatr.
 */
public void setNomatr(String newNomatr) { 
	nomatr = newNomatr;
}
/**
 * Getter de l'attribut servi.
 */
public String getServi() {
	return servi;
}
/**
 * Setter de l'attribut servi.
 */
public void setServi(String newServi) { 
	servi = newServi;
}
/**
 * Getter de l'attribut refarr.
 */
public String getRefarr() {
	return refarr;
}
/**
 * Setter de l'attribut refarr.
 */
public void setRefarr(String newRefarr) { 
	refarr = newRefarr;
}
/**
 * Getter de l'attribut datdeb.
 */
public String getDatdeb() {
	return datdeb;
}
/**
 * Setter de l'attribut datdeb.
 */
public void setDatdeb(String newDatdeb) { 
	datdeb = newDatdeb;
}
/**
 * Getter de l'attribut datfin.
 */
public String getDatfin() {
	return datfin;
}
/**
 * Setter de l'attribut datfin.
 */
public void setDatfin(String newDatfin) { 
	datfin = newDatfin;
}
/**
 * Getter de l'attribut cdecol.
 */
public String getCdecol() {
	return cdecol;
}
/**
 * Setter de l'attribut cdecol.
 */
public void setCdecol(String newCdecol) { 
	cdecol = newCdecol;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new Agents_ScesBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected Agents_ScesBroker getMyAgents_ScesBroker() {
	return (Agents_ScesBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet métier : Agents_Sces.
 * @return java.util.ArrayList
 */
public static ArrayList<Agents_Sces> listerAgents_Sces(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Agents_Sces unAgents_Sces = new Agents_Sces();
	return unAgents_Sces.getMyAgents_ScesBroker().listerAgents_Sces(aTransaction);
}
/**
 * Retourne un Agents_Sces.
 * @return Agents_Sces
 */
public static Agents_Sces chercherAgents_Sces(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	Agents_Sces unAgents_Sces = new Agents_Sces();
	return unAgents_Sces.getMyAgents_ScesBroker().chercherAgents_Sces(aTransaction, code);
}
}
