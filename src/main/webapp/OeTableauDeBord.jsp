<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>

<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<link href="theme/sigp2.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" href="theme/calendrier-mairie.css" type="text/css">
<SCRIPT type="text/javascript" src="js/GestionCalendrier.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/GestionBoutonDroit.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/GestionOnglet.js"></SCRIPT>
<SCRIPT language="JavaScript">

//afin de sélectionner un élément dans une liste
function executeBouton(nom)
{
document.formu.elements[nom].click();
}

// afin de mettre le focus sur une zone précise
function setfocus(nom)
{
document.formu.elements[nom].focus();
}

</SCRIPT>
<TITLE>OeTableauDeBord.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OeTableauDeBord" id="process" scope="session"></jsp:useBean>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">
<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><FIELDSET><TABLE border="0" class="sigp2">
			<TR>
				<TD class="sigp2-titre" valign="middle">Equipement</TD>
				<TD width="10"></TD>
				<TD class="sigp2-titre" valign="middle">Service</TD>
				<TD width="10"></TD>
				<TD class="sigp2-titre" valign="middle">Agent</TD>
				<TD class="sigp2-titre"><INPUT type="image" border="0"
					src="images/jumelle.gif" width="21" height="17"
					name="<%=process.getNOM_PB_RECHERCHER() %>" align="middle"></TD>
			</TR>
			<TR>
				<TD><INPUT type="text" size="20"
					name="<%=process.getNOM_EF_RECHERCHE_EQUIP() %>"
					value="<%=process.getVAL_EF_RECHERCHE_EQUIP() %>" class="sigp2-saisie"></TD>
				<TD width="10"></TD>
				<TD><INPUT type="text" name="<%=process.getNOM_EF_SERVICE() %>"
					value="<%=process.getVAL_EF_SERVICE() %>" class="sigp2-saisie"
					size="10"></TD>
				<TD width="10"></TD>
				<TD><INPUT type="text" name="<%=process.getNOM_EF_AGENT() %>"
					value="<%=process.getVAL_EF_AGENT() %>" class="sigp2-saisie"
					size="10"></TD>
				<TD><INPUT type="submit" value="Rechercher"
					name="<%=process.getNOM_PB_RECHERCHE_EQUIP() %>"
					class="sigp2-Bouton-100"></TD>
			</TR>
		</TABLE>

		</FIELDSET><FIELDSET><LEGEND class="sigp2Fieldset">Infos sur l'équipement</LEGEND>
	<TABLE class="sigp2" width="100%">
		<TR>
			<TD align="left">
				<FIELDSET>
					<TABLE class="sigp2">
							<TR>
								<TD width="108"><B>N° inventaire</B></TD>
								<TD class="sigp2-saisiemajuscule"><%=process.getVAL_ST_NOINV() %></TD>
								<TD width="20"></TD>
								<TD width="113"><B>N° immatriculation</B></TD>
								<TD class="sigp2-saisiemajuscule" style="text-transform: uppercase"><%=process.getVAL_ST_NOIMMAT() %></TD>
							</TR>
							<TR>
								<TD width="108">Nom d'équipement (Marque + Modèle)</TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_NOMEQUIP() %></TD>
								<TD width="20"></TD>
								<TD width="113">
								Type</TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_TYPE() %></TD>
							</TR>
							<TR>
								<TD width="108">Carburant</TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_CARBU() %></TD>
								<TD></TD>
								<TD width="113">Capacité du réservoir</TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_RESERVOIR() %></TD>
							</TR>
											<TR>
												<TD width="108">Puissance fiscale</TD>
												<TD class="sigp2-saisie"><%= process.getVAL_ST_PUISSANCE() %></TD>
												<TD></TD>
												<TD width="113">Type de compteur</TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_COMPTEUR() %></TD>
							</TR>
							<TR>
								<TD width="108">Types de pneus</TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_PNEUS() %></TD>
								<TD></TD>
								<TD width="113">Prix d'achat</TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_PRIX() %></TD>
							</TR>
							<TR>
								<TD width="108">Agent responsable</TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_AGENT_RESPONSABLE() %></TD>
								<TD></TD>
								<TD width="113">Réserve : </TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.reserve %></TD>
							</TR>
						</TABLE>
				</FIELDSET>
			</TD>
		</TR>
		<TR>
			<TD height="7"></TD>
		</TR>
		<TR>
			<TD>
				<FIELDSET>
					<table border="0" class="sigp2">
						<tr>
							<td align="center">Plan d'entretien : </td>
						<TD align="center" width="10"></TD>
						<td class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_PE() %></td>
							<td width="40"></td>
							<td>Version : </td>
						<TD width="10"></TD>
						<td class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_VERSION() %></td>
						</tr>
					</table>
				</FIELDSET>
			</TD>
		</TR>
		<TR>
			<TD>
				<FIELDSET>
					<table border="0" class="sigp2">
						<tr>
							<td align="center">Service </td>
						<TD align="center" width="10"></TD>
						<td class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_SERVICE() %></td>
						</tr>
					</table>
				</FIELDSET>
			</TD>
		</TR>
		<TR>
			<TD height="7"></TD>
		</TR>
		<TR>
			<TD>
				<FIELDSET>
					<table border="0" class="sigp2">
						<tr>
							<td align="left">Date de mise en circulation</td>
						<TD align="left" width="10"></TD>
						<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_DATEENSERVICE() %></td>
							<TD width="30"></TD>
							<TD>Date de mise hors circulation</TD>
						<TD width="10"></TD>
						<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_DATEHORSCIRCUIT() %></td>
									</tr>
									<tr>
							<td align="left">Date de vente ou réforme</td>
						<TD align="left"></TD>
						<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_DATEVENTEREFORME() %></td>
							<TD></TD>
							<TD></TD>
						<TD></TD>
						<td class="sigp2-saisie"></td>
						</tr>
					</table>
				</FIELDSET>
			</TD>
		</TR>
	</TABLE>
</FIELDSET><FIELDSET><LEGEND class="sigp2Fieldset">Historiques</LEGEND>
<!-- ------------------------------------ -->
<% if (process.onglet.equals("ONGLET1")) {%>
<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
  <TBODY>
    <TR>
      <TD>
      <TABLE border="0" cellpadding="0" cellspacing="0" >
        <TBODY>
          <TR>
            <TD onclick="afficheOnglet('ONGLET1')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletActifG"></TD>
                  <TD class="OngletActifC">BPC</TD>
                  <TD class="OngletActifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
            <TD onclick="afficheOnglet('ONGLET2')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletInactifG"></TD>
                  <TD class="OngletInactifC">OT</TD>
                  <TD class="OngletInactifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
			<TD onclick="afficheOnglet('ONGLET3')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletInactifG"></TD>
                  <TD class="OngletInactifC">Entretiens</TD>
                  <TD class="OngletInactifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
			<TD onclick="afficheOnglet('ONGLET4')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletInactifG"></TD>
                  <TD class="OngletInactifC">Affectations</TD>
                  <TD class="OngletInactifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
			<TD onclick="afficheOnglet('ONGLET5')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletInactifG"></TD>
                  <TD class="OngletInactifC">D&eacute;clarations</TD>
                  <TD class="OngletInactifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
          </TR>
        </TBODY>
      </TABLE>
      </TD>
    </TR>
    <TR>
      <TD height="2" bgcolor="#669999" nowrap></TD>
    </TR>
    <TR>
      <TD>
      <TABLE border="0" cellpadding="0" cellspacing="0" height="100" width="100%">
        <TBODY>
          <TR>
            <TD width="2" bgcolor="#669999" nowrap></TD>
            <TD valign="top" align="left" width="100%" class="OngletCorps"><TABLE class="sigp2">
					<TR>
						<TD>
							<TABLE border="1">
								<TBODY>
									<TR>
										<TD class="sigp2-titre-liste">N°BPC      Date       Compteur   Quanti Km
										parcouru Moyenne   </TD>
									</TR>
								</TBODY>
							</TABLE>
						</TD>
					</TR>
					<TR>
						<TD>
							<SELECT size="5" name="<%= process.getNOM_LB_BPC() %>"
									class="sigp2-liste"
									style="text-transform: uppercase; width: 100%" ondblclick='executeBouton("<%= process.getNOM_PB_DETAILSBPC()%>")'>
									<%= process.forComboHTML(process.getVAL_LB_BPC(),process.getVAL_LB_BPC_SELECT()) %>
							</SELECT>
						</TD>
					</TR>
									<TR>
										<TD><SELECT size="2" name="<%= process.getNOM_LB_BPC_TOTAL() %>"
											class="sigp2-liste"
											style="text-transform: uppercase; width: 100%">
											<OPTION>Total</OPTION>
											<%= process.forComboHTML(process.getVAL_LB_BPC_TOTAL(),process.getVAL_LB_BPC_TOTAL_SELECT()) %>
										</SELECT></TD>
									</TR>
									<TR>
						<TD align="center">
<%if(process.isVideBPC){ %>
							<INPUT type="submit"
					name="<%= process.getNOM_PB_DETAILSBPC() %>" value="Détails du BPC"
					class="sigp2-Bouton-100">
<%} %>
						</TD>
					</TR>
				</TABLE>			
			</TD>
            <TD width="2" bgcolor="#669999" nowrap></TD>
          </TR>
        </TBODY>
      </TABLE>
      </TD>
    </TR>
    <TR>
      <TD height="2" bgcolor="#669999" nowrap></TD>
    </TR>
  </TBODY>
</TABLE>
<%}%><% if (process.onglet.equals("ONGLET2")) {%>
<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
  <TBODY>
    <TR>
      <TD>
      <TABLE border="0" cellpadding="0" cellspacing="0" >
        <TBODY>
          <TR>
            <TD onclick="afficheOnglet('ONGLET1')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletInactifG"></TD>
                  <TD class="OngletInactifC">BPC</TD>
                  <TD class="OngletInactifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
            <TD onclick="afficheOnglet('ONGLET2')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletActifG"></TD>
                  <TD class="OngletActifC">OT</TD>
                  <TD class="OngletActifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
			<TD onclick="afficheOnglet('ONGLET3')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletInactifG"></TD>
                  <TD class="OngletInactifC">Entretiens</TD>
                  <TD class="OngletInactifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
			<TD onclick="afficheOnglet('ONGLET4')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletInactifG"></TD>
                  <TD class="OngletInactifC">Affectations</TD>
                  <TD class="OngletInactifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
			<TD onclick="afficheOnglet('ONGLET5')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletInactifG"></TD>
                  <TD class="OngletInactifC">D&eacute;clarations</TD>
                  <TD class="OngletInactifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
          </TR>
        </TBODY>
      </TABLE>
      </TD>
    </TR>
    <TR>
      <TD height="2" bgcolor="#669999" nowrap></TD>
    </TR>
    <TR>
      <TD>
      <TABLE border="0" cellpadding="0" cellspacing="0" height="100" width="100%">
        <TBODY>
          <TR>
            <TD width="2" bgcolor="#669999" nowrap></TD>
            <TD valign="top" align="left" width="100%" class="OngletCorps"><TABLE class="sigp2">
					<TR>
						<TD>
							<TABLE border="1">
								<TBODY>
									<TR>
										<TD class="sigp2-titre-liste">N°OT       Entrée     Sortie     Compteur     Montant    </TD>
									</TR>
								</TBODY>
							</TABLE>
						</TD>
					</TR>
					<TR>
						<TD>
							<SELECT size="5" name="<%= process.getNOM_LB_OT() %>"
								class="sigp2-liste"
								style="text-transform: uppercase; width: 100%" ondblclick='executeBouton("<%= process.getNOM_PB_DETAILSOT()%>")'>
								<%= process.forComboHTML(process.getVAL_LB_OT(),process.getVAL_LB_OT_SELECT()) %>
							</SELECT>
						</TD>
					</TR>
					<TR>
						<TD align="center">
<%if(process.isVideOT){ %>
							<INPUT type="submit"
							name="<%= process.getNOM_PB_DETAILSOT() %>"
							value="Détails de l'OT" class="sigp2-Bouton-100">
<%} %>
						</TD>
					</TR>
				</TABLE>
			</TD>
            <TD width="2" bgcolor="#669999" nowrap></TD>
          </TR>
        </TBODY>
      </TABLE>
      </TD>
    </TR>
    <TR>
      <TD height="2" bgcolor="#669999" nowrap></TD>
    </TR>
  </TBODY>
</TABLE>
<%}%><% if (process.onglet.equals("ONGLET3")) {%>
<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
  <TBODY>
    <TR>
      <TD>
      <TABLE border="0" cellpadding="0" cellspacing="0" >
        <TBODY>
          <TR>
            <TD onclick="afficheOnglet('ONGLET1')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletInactifG"></TD>
                  <TD class="OngletInactifC">BPC</TD>
                  <TD class="OngletInactifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
            <TD onclick="afficheOnglet('ONGLET2')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletInactifG"></TD>
                  <TD class="OngletInactifC">OT</TD>
                  <TD class="OngletInactifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
			<TD onclick="afficheOnglet('ONGLET3')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletActifG"></TD>
                  <TD class="OngletActifC">Entretiens</TD>
                  <TD class="OngletActifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
			<TD onclick="afficheOnglet('ONGLET4')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletInactifG"></TD>
                  <TD class="OngletInactifC">Affectations</TD>
                  <TD class="OngletInactifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
			<TD onclick="afficheOnglet('ONGLET5')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletInactifG"></TD>
                  <TD class="OngletInactifC">D&eacute;clarations</TD>
                  <TD class="OngletInactifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
          </TR>
        </TBODY>
      </TABLE>
      </TD>
    </TR>
    <TR>
      <TD height="2" bgcolor="#669999" nowrap></TD>
    </TR>
    <TR>
      <TD>
      <TABLE border="0" cellpadding="0" cellspacing="0" height="100" width="100%">
        <TBODY>
          <TR>
            <TD width="2" bgcolor="#669999" nowrap></TD>
            <TD valign="top" align="left" width="100%" class="OngletCorps"><TABLE class="sigp2">
					<TR>
						<TD width="391">

										<TABLE border="1">
											<TBODY>
												<TR>
													<TD class="sigp2-titre-liste" width="389">Date       Entretiens                           Compteur</TD>
												</TR>
											</TBODY>
										</TABLE>
										</TD>
					</TR>
					<TR>
						<TD width="391">
							<SELECT size="5" name="<%= process.getNOM_LB_ENTRETIENS() %>"
									class="sigp2-liste" onclick='executeBouton("<%= process.getNOM_PB_DETAILSOT()%>")'
									style="text-transform: uppercase; width: 100%">
									<%= process.forComboHTML(process.getVAL_LB_ENTRETIENS(),process.getVAL_LB_ENTRETIENS_SELECT()) %>
							</SELECT>
						</TD>
					</TR>
					<TR>
						<TD align="center" width="391"></TD>
					</TR>
				</TABLE>			
			</TD>
            <TD width="2" bgcolor="#669999" nowrap></TD>
          </TR>
        </TBODY>
      </TABLE>
      </TD>
    </TR>
    <TR>
      <TD height="2" bgcolor="#669999" nowrap></TD>
    </TR>
  </TBODY>
</TABLE>
<%}%><% if (process.onglet.equals("ONGLET4")) {%>
<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
  <TBODY>
    <TR>
      <TD>
      <TABLE border="0" cellpadding="0" cellspacing="0" >
        <TBODY>
          <TR>
            <TD onclick="afficheOnglet('ONGLET1')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletInactifG"></TD>
                  <TD class="OngletInactifC">BPC</TD>
                  <TD class="OngletInactifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
            <TD onclick="afficheOnglet('ONGLET2')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletInactifG"></TD>
                  <TD class="OngletInactifC">OT</TD>
                  <TD class="OngletInactifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
			<TD onclick="afficheOnglet('ONGLET3')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletInactifG"></TD>
                  <TD class="OngletInactifC">Entretiens</TD>
                  <TD class="OngletInactifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
			<TD onclick="afficheOnglet('ONGLET4')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletActifG"></TD>
                  <TD class="OngletActifC">Affectations</TD>
                  <TD class="OngletActifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
			<TD onclick="afficheOnglet('ONGLET5')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletInactifG"></TD>
                  <TD class="OngletInactifC">D&eacute;clarations</TD>
                  <TD class="OngletInactifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
          </TR>
        </TBODY>
      </TABLE>
      </TD>
    </TR>
    <TR>
      <TD height="2" bgcolor="#669999" nowrap></TD>
    </TR>
    <TR>
      <TD>
      <TABLE border="0" cellpadding="0" cellspacing="0" height="100" width="100%">
        <TBODY>
          <TR>
            <TD width="2" bgcolor="#669999" nowrap></TD>
            <TD valign="top" align="left" width="100%" class="OngletCorps"><TABLE class="sigp2">
					<TR>
						<TD>


										<TABLE width="100%" border="1">
											<TBODY>
												<TR>
													<TD class="sigp2-titre-liste">Service                                                 Début      Fin        </TD>
												</TR>
											</TBODY>
										</TABLE>
										</TD>
					</TR>
					<TR>
						<TD>
							<SELECT size="5" name="<%= process.getNOM_LB_SERVICES() %>"
									class="sigp2-liste" onclick='executeBouton("<%= process.getNOM_PB_RESPONSABLE()%>")'
									style="text-transform: uppercase; width: 100%">
									<%= process.forComboHTML(process.getVAL_LB_SERVICES(),process.getVAL_LB_SERVICES_SELECT()) %>
							</SELECT>
						</TD>
					</TR>
					<TR>
						<TD align="center"><TABLE class="sigp2" width="100%">
											<TR>
												<TD width="200">Responsable :</TD>
												<TD align="left" class="sigp2-saisie"
													style="text-transform: uppercase; font-weight: bold"><%=process.getVAL_ST_AGENT() %></TD>
											</TR>
										</TABLE>
										</TD>
					</TR>
				</TABLE>			
			</TD>
            <TD width="2" bgcolor="#669999" nowrap></TD>
          </TR>
        </TBODY>
      </TABLE>
      </TD>
    </TR>
    <TR>
      <TD height="2" bgcolor="#669999" nowrap></TD>
    </TR>
  </TBODY>
</TABLE>
<%}%><% if (process.onglet.equals("ONGLET5")) {%>
<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
  <TBODY>
    <TR>
      <TD>
      <TABLE border="0" cellpadding="0" cellspacing="0" >
        <TBODY>
          <TR>
            <TD onclick="afficheOnglet('ONGLET1')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletInactifG"></TD>
                  <TD class="OngletInactifC">BPC</TD>
                  <TD class="OngletInactifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
            <TD onclick="afficheOnglet('ONGLET2')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletInactifG"></TD>
                  <TD class="OngletInactifC">OT</TD>
                  <TD class="OngletInactifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
			<TD onclick="afficheOnglet('ONGLET3')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletInactifG"></TD>
                  <TD class="OngletInactifC">Entretiens</TD>
                  <TD class="OngletInactifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
			<TD onclick="afficheOnglet('ONGLET4')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletInactifG"></TD>
                  <TD class="OngletInactifC">Affectations</TD>
                  <TD class="OngletInactifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
			<TD onclick="afficheOnglet('ONGLET5')">
            <TABLE cellpadding="0" cellspacing="0" width="100%">
              <TBODY>
                <TR>
                  <TD class="OngletActifG"></TD>
                  <TD class="OngletActifC">D&eacute;clarations</TD>
                  <TD class="OngletActifD"></TD>
                </TR>
              </TBODY>
            </TABLE>
            </TD>
          </TR>
        </TBODY>
      </TABLE>
      </TD>
    </TR>
    <TR>
      <TD height="2" bgcolor="#669999" nowrap></TD>
    </TR>
    <TR>
      <TD>
      <TABLE border="0" cellpadding="0" cellspacing="0" height="100" width="100%">
        <TBODY>
          <TR>
            <TD width="2" bgcolor="#669999" nowrap></TD>
            <TD valign="top" align="left" width="100%" class="OngletCorps"><TABLE class="sigp2">
					<TR>
						<TD>


										<TABLE width="100%" border="1">
											<TBODY>
												<TR>
													<TD class="sigp2-titre-liste">Date       Déclarant                                             OT      Service  </TD>
												</TR>
											</TBODY>
										</TABLE>
										</TD>
					</TR>
					<TR>
						<TD>
							<SELECT size="5" name="<%= process.getNOM_LB_DECLARATIONS() %>"
									class="sigp2-liste"
									onclick='executeBouton("<%= process.getNOM_PB_DETAILSDEC()%>")'
									style="text-transform: uppercase; width: 100%">
									<%= process.forComboHTML(process.getVAL_LB_DECLARATIONS(),process.getVAL_LB_DECLARATIONS_SELECT()) %>
							</SELECT>
						</TD>
					</TR>
<tr>
	<td align="center"><%if(process.isVideDec){ %>
							<INPUT type="submit" name="<%= process.getNOM_PB_DETAILSDEC() %>"
											value="Détails" class="sigp2-Bouton-100">
<%} %></td>
</tr>
					<TR>
						<TD align="center">
						</TD>
					</TR>
				</TABLE>			
			</TD>
            <TD width="2" bgcolor="#669999" nowrap></TD>
          </TR>
        </TBODY>
      </TABLE>
      </TD>
    </TR>
    <TR>
      <TD height="2" bgcolor="#669999" nowrap></TD>
    </TR>
  </TBODY>
</TABLE>
<%}%>
<!-- ------------------------------------- --><%if (process.isDebranche) {%><INPUT
			type="submit" value="Retour" name="<%=process.getNOM_PB_RETOUR() %>" class="sigp2-Bouton-100"><%} %><INPUT type="submit"
			name="<%= process.getNOM_PB_RESPONSABLE() %>" value="Responsable"
			style="visibility : hidden;" class="sigp2-Bouton-100">
</FIELDSET><INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR> 
</TABLE>
</BODY>
</HTML>
