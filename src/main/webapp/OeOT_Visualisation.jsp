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
if (document.formu.elements[nom] != null)
	document.formu.elements[nom].focus();
}

</SCRIPT>
<TITLE>OeOT_Visualisation.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OeOT_Visualisation" id="process" scope="session"></jsp:useBean>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">

<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
		<TD style="text-align : center;" align="center">
<FORM name="formu" method="POST"><%if(process.isTrouve) {%>
		<TABLE border="0" align="right">
			<TR>
				<TD><INPUT type="image" name="<%= process.getNOM_PB_IMPRIMER() %>"
					src="images/print.gif"></TD>
			</TR>
		</TABLE>
		<%} %>
		<table border="0" class="sigp2" cellpadding="0" cellspacing="0">
	<tr>
		<td class="sigp2-titre">OT</td>
		<td width="10"></td>
<td><INPUT type="text" name="<%=process.getNOM_EF_OT() %>"
					value="<%=process.getVAL_EF_OT() %>" class="sigp2-saisie"
					size="10"></td>
<td><INPUT type="submit" value="Rechercher"
					name="<%=process.getNOM_PB_OT() %>" class="sigp2-Bouton-100"></td>
<td><INPUT type="image" border="0" src="images/jumelle.gif"
			width="21" height="17" name="<%=process.getNOM_PB_RECHERCHER() %>"><BR>
	</td>
	</tr>
</table>
		<BR>
		<FIELDSET>
		
		<TABLE border="3" bordercolor="#669999" cellpadding="2"
			cellspacing="2">
			<TBODY>
				<TR>
					<TD colspan="6" bgcolor="#669999" class="sigp2-titre">Infos
					concernant l'équipement</TD>
				</TR>
				<TR>
					<TD>
					<TABLE border="0" class="sigp2" cellpadding="0" cellspacing="0">
						<TBODY>
							<TR>
								<TD><B>N°Inventaire</B></TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisiemajuscule" style="text-transform: uppercase"><%= process.getVAL_ST_NOINVENT()%></TD>
								<TD class="sigp2-saisie" width="10"></TD>
								<TD><B>N°immatriculation</B></TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisiemajuscule" style="text-transform: uppercase"><%= process.getVAL_ST_NOIMMAT()%></TD>

							</TR>
							<TR>
								<TD>Nom d'équipement</TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_MARQUE()+" "+process.getVAL_ST_MODELE()%></TD>
								<TD class="sigp2-saisie"></TD>
								<TD>Type</TD>
								<TD></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_TYPE() %></TD>
							</TR>
							<TR>
								<TD height="15"></TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisie"></TD>
								<TD></TD>
								<TD></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"></TD>
							</TR>
							<TR>
								<TD>Service</TD>
								<TD></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"
									colspan="5"><%= process.getVAL_ST_SERVICE()%></TD>
							</TR>
						</TBODY>
					</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		</FIELDSET>
<!--Détails de l'OT--><BR>
		<FIELDSET>
		<TABLE class="sigp2">
			<TR>
				<TD colspan="7" height="10">
				<TABLE border="0" class="sigp2" align="center">
					<TR>
						<TD><SPAN class="sigp2-titre">NUMERO DE L'OT</SPAN></TD>
						<TD class="sigp2-saisiemajuscule" style="font-size: 16px"><B><%=process.getVAL_ST_NOOT() %></B></TD>
					</TR>

				</TABLE>
				</TD>
			</TR>
			<TR>
				<TD align="left">Date d'entrée</TD>
				<TD align="right" width="10"></TD>
				<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_DENTREE() %></TD>
				<TD width="10"></TD>
				<TD>Compteur (<%=process.getVAL_ST_TCOMPTEUR() %>)</TD>
				<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_COMPTEUR() %></TD>
				<TD></TD>
			</TR>
			<TR>
				<TD align="left">Date de sortie</TD>
				<TD align="right"></TD>
				<TD class="sigp2-saisie"><%=process.getVAL_ST_DSORTIE() %></TD>
				<TD width="10"></TD>
				<TD></TD>
				<TD class="sigp2-saisie"></TD>
				<TD></TD>
			</TR>
			<TR>
				<TD align="left"><B>Coût total de l'OT</B></TD>
				<TD align="right"></TD>
				<TD class="sigp2-saisie"><%=process.getVAL_ST_COUT_TOTAL() %></TD>
				<TD width="10"></TD>
				<TD></TD>
				<TD class="sigp2-saisie"></TD>
				<TD></TD>
			</TR>
			<TR>
				<TD colspan="7">Commentaire : <BR>
				<a class="sigp2-liste"><%=process.getVAL_ST_COMMENTAIRE() %></a></TD>
			</TR>
		</TABLE>
		</FIELDSET>
						
		<BR>

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
                  <TD class="OngletActifC">Interventions</TD>
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
                  <TD class="OngletInactifC">Pièces</TD>
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
                  <TD class="OngletInactifC">Bons d'engagement</TD>
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
                  <TD class="OngletInactifC">Intervenants</TD>
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
            <TD valign="top" align="left" width="100%" class="OngletCorps">
								<TABLE class="sigp2">
									<TR>
										<TD>
										<TABLE width="100%" border="1">
											<TBODY>
												<TR>
													<TD class="sigp2-titre-liste">Entretien                      Réalisé    Durée </TD>
												</TR>
											</TBODY>
										</TABLE>
										</TD>
									</TR>
									<TR>
										<TD><SELECT size="8"
											name="<%= process.getNOM_LB_INTERVENTIONS() %>"
											class="sigp2-liste"
											style="text-transform: uppercase; width: 100%">
											<%= process.forComboHTML(process.getVAL_LB_INTERVENTIONS(),process.getVAL_LB_INTERVENTIONS_SELECT()) %>
										</SELECT></TD>
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
                  <TD class="OngletInactifC">Interventions</TD>
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
                  <TD class="OngletActifC">Pièces</TD>
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
                  <TD class="OngletInactifC">Bons d'engagement</TD>
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
                  <TD class="OngletInactifC">Intervenants</TD>
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
            <TD valign="top" align="left" width="100%" class="OngletCorps">
								<TABLE class="sigp2">
									<TR>
										<TD>
										<TABLE width="100%" border="1">
											<TBODY>
												<TR>
													<TD class="sigp2-titre-liste">Pièces                         Sortie     P.U    Qté    Total  </TD>
												</TR>
											</TBODY>
										</TABLE>
										</TD>
									</TR>
									<TR>
										<TD><SELECT size="6" name="<%= process.getNOM_LB_PIECES() %>"
											class="sigp2-liste"
											style="text-transform: uppercase; width: 100%">
											<%= process.forComboHTML(process.getVAL_LB_PIECES(),process.getVAL_LB_PIECES_SELECT()) %>
										</SELECT></TD>
									</TR>
									<tr>
										<td align="right"><b>Montant total : <a class="sigp2-liste"><%=process.montantTotalPieces %></a></b></td>
									</tr>
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
                  <TD class="OngletInactifC">Interventions</TD>
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
                  <TD class="OngletInactifC">Pièces</TD>
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
                  <TD class="OngletActifC">Bons d'engagement</TD>
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
                  <TD class="OngletInactifC">Intervenants</TD>
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
            <TD valign="top" align="left" width="100%" class="OngletCorps">
								<TABLE class="sigp2">
									<TR>
										<TD>
										<TABLE width="100%" border="1">
											<TBODY>
												<TR>
													<TD class="sigp2-titre-liste">N°engagemt Code       Fournisseur                   Montant    </TD>
												</TR>
											</TBODY>
										</TABLE>
										</TD>
									</TR>
									<TR>
										<TD><SELECT size="8" name="<%= process.getNOM_LB_FRE() %>"
											class="sigp2-liste"
											style="text-transform: uppercase; width: 100%">
											<%= process.forComboHTML(process.getVAL_LB_FRE(),process.getVAL_LB_FRE_SELECT()) %>
										</SELECT></TD>
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
                  <TD class="OngletInactifC">Interventions</TD>
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
                  <TD class="OngletInactifC">Pièces</TD>
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
                  <TD class="OngletInactifC">Bons d'engagement</TD>
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
                  <TD class="OngletActifC">Intervenants</TD>
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
            <TD valign="top" align="left" width="100%" class="OngletCorps">
								<TABLE class="sigp2">
									<TR>
										<TD class="sigp2-titre-liste">
										<TABLE width="100%" border="1">
											<TBODY>
												<TR>
													<TD class="sigp2-titre-liste">Intervenants           </TD>
												</TR>
											</TBODY>
										</TABLE>
										</TD>
									</TR>
									<TR>
										<TD><SELECT size="8"
											name="<%= process.getNOM_LB_INTERVENANTS() %>"
											class="sigp2-liste"
											style="text-transform: uppercase; width: 100%">
											<%= process.forComboHTML(process.getVAL_LB_INTERVENANTS(),process.getVAL_LB_INTERVENANTS_SELECT()) %>
										</SELECT></TD>
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
<%}%><%if(process.isDebranche){ %>
		<INPUT type="submit" value="Retour"
			name="<%=process.getNOM_PB_ANNULER() %>" class="sigp2-Bouton-100"><br>
<%} %>
		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
		</TD>
	</TR>
</TABLE>
</BODY>
</HTML>
<%=process.afficheScript() %>