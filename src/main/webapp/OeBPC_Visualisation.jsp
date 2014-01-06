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
if (document.formu.elements[nom] != null)
	document.formu.elements[nom].focus();
}

</SCRIPT>
<TITLE>OeBPC_Visualisation.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OeBPC_Visualisation" id="process" scope="session"></jsp:useBean>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY">

<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><SPAN class="sigp2-titre"></SPAN><FIELDSET>
						<TABLE border="0" class="sigp2">
							<tr>
								<td class="sigp2-titre">Recherche du BPC n°</td>
				<TD class="sigp2-titre"></TD>
				<td><INPUT type="text" size="10" name="<%=process.getNOM_EF_RECHNUMBPC() %>" value="<%=process.getVAL_EF_RECHNUMBPC() %>" class="sigp2-saisie"></td>
								<td><INPUT type="submit" value="Rechercher"
					width="21" height="17" name="<%=process.getNOM_PB_RECHERCHER() %>"></td>
							</tr>
						</TABLE>

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
								<TD><B>N° d'inventaire</B></TD>
								<TD width="10" style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisiemajuscule"
									style="text-transform: uppercase"><%=process.getVAL_ST_NOINVENT() %></TD>
								<TD width="10"></TD>
								<TD><B>N°d'immat(ou série)</B></TD>
								<TD width="10" style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisiemajuscule"
									style="text-transform: uppercase"><%=process.getVAL_ST_NOIMMAT() %></TD>

							</TR>
							<TR>
								<TD>Nom d'équipement</TD>
								<TD style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_NOMEQUIP()%></TD>
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
									colspan="5"><%=process.getVAL_ST_SERVICE() %></TD>
							</TR>
						</TBODY>
					</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<BR>
		
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
				<TABLE border="3" width="100%" bordercolor="#669999" cellpadding="2"
					cellspacing="2" align="center" width="100%">
					<TR>
						<TD colspan="3" bgcolor="#669999" class="sigp2-titre">Détails d'un
						BPC</TD>
					</TR>
					<TR>
						<TD width="10"></TD>
						<TD align="center"><!--Détails du BPC-->
						<FIELDSET>
						<TABLE class="sigp2">
							<TR>
								<TD colspan="5" height="10">
								<TABLE border="0" class="sigp2-titre" align="center">
									<TR>
										<TD>Numéro du BPC</TD>
										<TD class="sigp2-saisie" style="font-size: 16px"><b><%=process.getVAL_ST_NOBPC() %></b></TD>
									</TR>

								</TABLE>
								</TD>
							</TR>
							<TR>
								<TD>Date</TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_DATE() %></TD>
								<TD width="10"></TD>
								<TD>Compteur (<%=process.getVAL_ST_TCOMPTEUR() %>)</TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_COMPTEUR() %></TD>
							</TR>
							<TR>
								<TD>Heure de prise</TD>
								<TD class="sigp2-saisie"><%=process.getVAL_ST_HEURE() %></TD>
								<TD width="10"></TD>
								<TD>N° pompe</TD>
								<TD class="sigp2-saisie"><%=process.getVAL_ST_POMPE() %></TD>
							</TR>
							<TR>
								<TD>Quantité</TD>
								<TD class="sigp2-saisie"><%=process.getVAL_ST_QTE() %></TD>
								<TD width="10"></TD>
								<TD>Carburant</TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_CARBU() %></TD>
							</TR>
							<!-- <TR>
								<TD>Mode de prise</TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%//=process.getVAL_ST_MODEPRISE() %></TD>
								<TD width="10"></TD>
								<TD></TD>
								<TD></TD>
							</TR>-->
						</TABLE>
						</FIELDSET>
						<BR>
						</TD>
						<TD width="10"></TD>
					</TR>
				</TABLE>
				</td>
		</tr>
	</table><BR>
<%if(process.isDebranche){ %>
		<INPUT type="submit" value="Retour" name="<%=process.getNOM_PB_ANNULER() %>" class="sigp2-Bouton-100">
<%} %>
		<br>
		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
