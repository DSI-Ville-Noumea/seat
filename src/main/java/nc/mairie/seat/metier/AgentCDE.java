package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier AgentCDE
 */
public class AgentCDE extends BasicMetier implements AgentInterface {
	public String idindi;
	public String nomatr;
	public String nom;
	public String prenom;
	public String nomjfi;
	public String datnai;
	public String lieuna;
	public String cddesi;
	public String sexe;
	public String nation;
	public String cdfami;
	public String ninsee;
	public String dattit;
	public String datdec;
	public String cdregl;
	public String idadrs;
	public String idcpte;
	public String teldom;
	public String noport;
	public String bister;
	public String lidopu;
	public String lirue;
	public String bp;
	public String licare;
	public String cdvill;
	public String livill;
	public String cdbanq;
	public String cdguic;
	public String nocpte;
	public String clerib;
	public String cdelec;
	public String datemb;
	public String cdetud;
/**
 * Constructeur AgentCDE.
 */
public AgentCDE() {
	super();
}
/**
 * Getter de l'attribut idindi.
 */
/**
 * @return String
 */
public String getIdindi() {
	return idindi;
}
/**
 * Setter de l'attribut idindi.
 */
/**
 * @param newIdindi newIdindi
 */
public void setIdindi(String newIdindi) { 
	idindi = newIdindi;
}
/**
 * Getter de l'attribut nomatr.
 */
/**
 * @return String
 */
public String getNomatr() {
	return nomatr;
}
/**
 * Setter de l'attribut nomatr.
 */
/**
 * @param newNomatr newNomatr
 */
public void setNomatr(String newNomatr) { 
	nomatr = newNomatr;
}
/**
 * Getter de l'attribut nom.
 * @return String
 */
public String getNom() {
	return nom;
}
/**
 * Setter de l'attribut nom.
 */
/**
 * @param newNom newNom
 */
public void setNom(String newNom) { 
	nom = newNom;
}
/**
 * Getter de l'attribut prenom.
 * @return String
 */
public String getPrenom() {
	return prenom;
}
/**
 * Setter de l'attribut prenom.
 */
/**
 * @param newPrenom newPrenom
 */
public void setPrenom(String newPrenom) { 
	prenom = newPrenom;
}
/**
 * Getter de l'attribut nomjfi.
 */
/**
 * @return String
 */
public String getNomjfi() {
	return nomjfi;
}
/**
 * Setter de l'attribut nomjfi.
 */
/**
 * @param newNomjfi newNomjfi
 */
public void setNomjfi(String newNomjfi) { 
	nomjfi = newNomjfi;
}
/**
 * Getter de l'attribut datnai.
 */
/**
 * @return String
 */
public String getDatnai() {
	return datnai;
}
/**
 * Setter de l'attribut datnai.
 */
/**
 * @param newDatnai newDatnai
 */
public void setDatnai(String newDatnai) { 
	datnai = newDatnai;
}
/**
 * Getter de l'attribut lieuna.
 */
/**
 * @return String
 */
public String getLieuna() {
	return lieuna;
}
/**
 * Setter de l'attribut lieuna.
 */
/**
 * @param newLieuna newLieuna
 */
public void setLieuna(String newLieuna) { 
	lieuna = newLieuna;
}
/**
 * Getter de l'attribut cddesi.
 */
/**
 * @return String
 */
public String getCddesi() {
	return cddesi;
}
/**
 * Setter de l'attribut cddesi.
 */
/**
 * @param newCddesi newCddesi
 */
public void setCddesi(String newCddesi) { 
	cddesi = newCddesi;
}
/**
 * Getter de l'attribut sexe.
 */
/**
 * @return String
 */
public String getSexe() {
	return sexe;
}
/**
 * Setter de l'attribut sexe.
 */
/**
 * @param newSexe newSexe
 */
public void setSexe(String newSexe) { 
	sexe = newSexe;
}
/**
 * Getter de l'attribut nation.
 */
/**
 * @return String
 */
public String getNation() {
	return nation;
}
/**
 * Setter de l'attribut nation.
 */
/**
 * @param newNation newNation
 */
public void setNation(String newNation) { 
	nation = newNation;
}
/**
 * Getter de l'attribut cdfami.
 */
/**
 * @return String
 */
public String getCdfami() {
	return cdfami;
}
/**
 * Setter de l'attribut cdfami.
 */
/**
 * @param newCdfami newCdfami
 */
public void setCdfami(String newCdfami) { 
	cdfami = newCdfami;
}
/**
 * Getter de l'attribut ninsee.
 */
/**
 * @return String
 */
public String getNinsee() {
	return ninsee;
}
/**
 * Setter de l'attribut ninsee.
 */
/**
 * @param newNinsee newNinsee
 */
public void setNinsee(String newNinsee) { 
	ninsee = newNinsee;
}
/**
 * Getter de l'attribut dattit.
 */
/**
 * @return String
 */
public String getDattit() {
	return dattit;
}
/**
 * Setter de l'attribut dattit.
 */
/**
 * @param newDattit newDattit
 */
public void setDattit(String newDattit) { 
	dattit = newDattit;
}
/**
 * Getter de l'attribut datdec.
 */
/**
 * @return String
 */
public String getDatdec() {
	return datdec;
}
/**
 * Setter de l'attribut datdec.
 */
/**
 * @param newDatdec newDatdec
 */
public void setDatdec(String newDatdec) { 
	datdec = newDatdec;
}
/**
 * Getter de l'attribut cdregl.
 */
/**
 * @return String
 */
public String getCdregl() {
	return cdregl;
}
/**
 * Setter de l'attribut cdregl.
 */
/**
 * @param newCdregl newCdregl
 */
public void setCdregl(String newCdregl) { 
	cdregl = newCdregl;
}
/**
 * Getter de l'attribut idadrs.
 */
/**
 * @return String
 */
public String getIdadrs() {
	return idadrs;
}
/**
 * Setter de l'attribut idadrs.
 */
/**
 * @param newIdadrs newIdadrs
 */
public void setIdadrs(String newIdadrs) { 
	idadrs = newIdadrs;
}
/**
 * Getter de l'attribut idcpte.
 */
/**
 * @return String
 */
public String getIdcpte() {
	return idcpte;
}
/**
 * Setter de l'attribut idcpte.
 */
/**
 * @param newIdcpte newIdcpte
 */
public void setIdcpte(String newIdcpte) { 
	idcpte = newIdcpte;
}
/**
 * Getter de l'attribut teldom.
 */
/**
 * @return String
 */
public String getTeldom() {
	return teldom;
}
/**
 * Setter de l'attribut teldom.
 */
/**
 * @param newTeldom newTeldom
 */
public void setTeldom(String newTeldom) { 
	teldom = newTeldom;
}
/**
 * Getter de l'attribut noport.
 */
/**
 * @return String
 */
public String getNoport() {
	return noport;
}
/**
 * Setter de l'attribut noport.
 */
/**
 * @param newNoport newNoport
 */
public void setNoport(String newNoport) { 
	noport = newNoport;
}
/**
 * Getter de l'attribut bister.
 */
/**
 * @return String
 */
public String getBister() {
	return bister;
}
/**
 * Setter de l'attribut bister.
 */
/**
 * @param newBister newBister
 */
public void setBister(String newBister) { 
	bister = newBister;
}
/**
 * Getter de l'attribut lidopu.
 */
/**
 * @return String
 */
public String getLidopu() {
	return lidopu;
}
/**
 * Setter de l'attribut lidopu.
 */
/**
 * @param newLidopu newLidopu
 */
public void setLidopu(String newLidopu) { 
	lidopu = newLidopu;
}
/**
 * Getter de l'attribut lirue.
 */
/**
 * @return String
 */
public String getLirue() {
	return lirue;
}
/**
 * Setter de l'attribut lirue.
 */
/**
 * @param newLirue newLirue
 */
public void setLirue(String newLirue) { 
	lirue = newLirue;
}
/**
 * Getter de l'attribut bp.
 */
/**
 * @return String
 */
public String getBp() {
	return bp;
}
/**
 * Setter de l'attribut bp.
 */
/**
 * @param newBp newBp
 */
public void setBp(String newBp) { 
	bp = newBp;
}
/**
 * Getter de l'attribut licare.
 */
/**
 * @return String
 */
public String getLicare() {
	return licare;
}
/**
 * Setter de l'attribut licare.
 */
/**
 * @param newLicare newLicare
 */
public void setLicare(String newLicare) { 
	licare = newLicare;
}
/**
 * Getter de l'attribut cdvill.
 */
/**
 * @return String
 */
public String getCdvill() {
	return cdvill;
}
/**
 * Setter de l'attribut cdvill.
 */
/**
 * @param newCdvill newCdvill
 */
public void setCdvill(String newCdvill) { 
	cdvill = newCdvill;
}
/**
 * Getter de l'attribut livill.
 */
/**
 * @return String
 */
public String getLivill() {
	return livill;
}
/**
 * Setter de l'attribut livill.
 */
/**
 * @param newLivill newLivill
 */
public void setLivill(String newLivill) { 
	livill = newLivill;
}
/**
 * Getter de l'attribut cdbanq.
 */
/**
 * @return String
 */
public String getCdbanq() {
	return cdbanq;
}
/**
 * Setter de l'attribut cdbanq.
 */
/**
 * @param newCdbanq newCdbanq
 */
public void setCdbanq(String newCdbanq) { 
	cdbanq = newCdbanq;
}
/**
 * Getter de l'attribut cdguic.
 */
/**
 * @return String
 */
public String getCdguic() {
	return cdguic;
}
/**
 * Setter de l'attribut cdguic.
 */
/**
 * @param newCdguic newCdguic
 */
public void setCdguic(String newCdguic) { 
	cdguic = newCdguic;
}
/**
 * Getter de l'attribut nocpte.
 */
/**
 * @return String
 */
public String getNocpte() {
	return nocpte;
}
/**
 * Setter de l'attribut nocpte.
 */
/**
 * @param newNocpte newNocpte
 */
public void setNocpte(String newNocpte) { 
	nocpte = newNocpte;
}
/**
 * Getter de l'attribut clerib.
 */
/**
 * @return String
 */
public String getClerib() {
	return clerib;
}
/**
 * Setter de l'attribut clerib.
 */
/**
 * @param newClerib newClerib
 */
public void setClerib(String newClerib) { 
	clerib = newClerib;
}
/**
 * Getter de l'attribut cdelec.
 */
/**
 * @return String
 */
public String getCdelec() {
	return cdelec;
}
/**
 * Setter de l'attribut cdelec.
 */
/**
 * @param newCdelec newCdelec
 */
public void setCdelec(String newCdelec) { 
	cdelec = newCdelec;
}
/**
 * Getter de l'attribut datemb.
 */
/**
 * @return String
 */
public String getDatemb() {
	return datemb;
}
/**
 * Setter de l'attribut datemb.
 */
/**
 * @param newDatemb newDatemb
 */
public void setDatemb(String newDatemb) { 
	datemb = newDatemb;
}
/**
 * Getter de l'attribut cdetud.
 */
/**
 * @return String
 */
public String getCdetud() {
	return cdetud;
}
/**
 * Setter de l'attribut cdetud.
 */
/**
 * @param newCdetud newCdetud
 */
public void setCdetud(String newCdetud) { 
	cdetud = newCdetud;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new AgentCDEBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected AgentCDEBroker getMyAgentCDEBroker() {
	return (AgentCDEBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet métier : AgentCDE.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<AgentCDE> listerAgentCDE(nc.mairie.technique.Transaction aTransaction) throws Exception{
	AgentCDE unAgentCDE = new AgentCDE();
//	ArrayList listFinale = new ArrayList();
	ArrayList<AgentCDE> listeAgentCDE =  unAgentCDE.getMyAgentCDEBroker().listerAgentCDE(aTransaction);
/*	if(aTransaction.isErreur()){
		return listFinale;
	}
	// on ne garde que les agents actifs
	if (listeAgentCDE.size()>0){
		for (int i=0;i<listeAgentCDE.size();i++){
			AgentCDE unAgent = (AgentCDE)listeAgentCDE.get(i);
			AgentCDESppost unSppost = AgentCDESppost.chercherAgentCDESppost(aTransaction,unAgent.getNomatr());
			if(aTransaction.isErreur()){
				aTransaction.traiterErreur();
			}else{
				if(!unSppost.getCodact().equals("I")){
					listFinale.add(unAgent);
				}
			}
		}
	}
	return listFinale;
	*/
	return listeAgentCDE;
}
/**
 * Retourne un AgentCDE.
 * @param aTransaction Transaction
 * @param nomatr nomatr
 * @return AgentCDE
 * @throws Exception Exception
 */
public static AgentCDE chercherAgentCDE(nc.mairie.technique.Transaction aTransaction, String nomatr) throws Exception{
	AgentCDE unAgentCDE = new AgentCDE();
	return unAgentCDE.getMyAgentCDEBroker().chercherAgentCDE(aTransaction, nomatr);
}
/**
 * Retourne un AgentCDE.
 * @param aTransaction Transaction
 * @param param param
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<AgentCDE> listerAgentCDENom(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	AgentCDE unAgentCDE = new AgentCDE();
	return unAgentCDE.getMyAgentCDEBroker().listerAgentCDENom(aTransaction, param);
}
}
