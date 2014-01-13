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
<TITLE>OeEquipement_Visualisation.jsp</TITLE>
</HEAD>
<BODY background="images/fond.jpg" class="sigp2-BODY">
<jsp:useBean class="nc.mairie.seat.process.OeDeclaration_Visualisation" id="process" scope="session"></jsp:useBean>
<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><FIELDSET>
						<TABLE border="3" bordercolor="#669999" cellpadding="2" cellspacing="2">
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
								<TD><B>N° d'inventaire</B></TD>
								<TD width="10" style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisiemajuscule"
									style="text-transform: uppercase"><%=process.getVAL_ST_NUMINV() %></TD>
								<TD width="10"></TD>
								<TD><B>N°d'immatriculation</B></TD>
								<TD width="10" style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisiemajuscule"
									style="text-transform: uppercase"><%=process.getVAL_ST_NUMIMMAT() %></TD>

							</TR>
							<TR>
								<TD>Nom d'équipement</TD>
								<TD style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_NOM_EQUIP()%></TD>
								<TD width="10"></TD>
								<TD>Type</TD>
								<TD width="10" style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_TYPE() %></TD>
							</TR>
							<TR>
								<TD height="15"></TD>
								<TD style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"></TD>
								<TD width="10"></TD>
								<TD></TD>
								<TD width="10" style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"></TD>
							</TR>
							<TR>
								<TD>Service</TD>
								<TD style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"
									colspan="5"><%=process.getVAL_ST_LIBELLE_SCE() %></TD>
							</TR>
						</TBODY>
					</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>

					</FIELDSET>
				<TABLE border="0" class="sigp2">
					<tr>
						<td colspan="3" align="center"></td>
					</tr>
					<tr>
						<td height="10" colspan="3"></td>
					</tr>
					<tr>
						<td colspan="3">
				<TABLE border="3" bordercolor="#669999" cellpadding="2"
					cellspacing="2" align="center">
					<TR>
						<TD colspan="3" bgcolor="#669999" class="sigp2-titre">Détails
						d'une déclaration</TD>
					</TR>
					<TR>
						<TD width="10"></TD>
						<TD><!--Détails du BPC-->
						<FIELDSET>
						<TABLE class="sigp2">
							<TR>
								<TD colspan="2" height="10"></TD>
							</TR>
							<TR>
								<TD>Date</TD>
								<TD valign="middle">
								<TABLE border="0" class="sigp2">
									<TR>
										<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_DATE() %></TD>
										<TD></TD>
									</TR>
								</TABLE>
								</TD>
							</TR>
							<TR>
								<TD colspan="2">Anomalies signalées</TD>
							</TR>
							<TR>
								<TD colspan="2" class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_ANOMALIES() %></TD>
							</TR>
							<TR>
								<TD colspan="2">Le service </TD>
							</TR>
							<TR>
								<TD colspan="2" class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_NUMSCE() %></TD>
							</TR>
							<TR>
								<TD colspan="2">Le déclarant</TD>
							</TR>
							<TR>
								<TD colspan="2" class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_DECLARANT() %></TD>
							</TR>
						</TABLE>
						</FIELDSET>
						<BR>
						</TD>
						<TD width="10"></TD>
					</TR>
				</TABLE>
				</td>
		</tr>
	</table>
				<INPUT type="submit" name="<%= process.getNOM_PB_RETOUR() %>"
			value="Retour" class="sigp2-Bouton-100"><br>
		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
