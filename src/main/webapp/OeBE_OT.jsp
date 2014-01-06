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
<TITLE>OeBE_OT.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OeBE_OT" id="process" scope="session"></jsp:useBean>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">

<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><SPAN class="sigp2-titre">Rattachement de  bons d'engagements pour l'OT <%=process.getVAL_ST_NOOT() %><BR>
		</SPAN>
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
								<TD class="sigp2-saisiemajuscule"
									style="text-transform: uppercase"><%= process.getVAL_ST_NOINVENT()%></TD>
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
						</TBODY>
					</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<SPAN class="sigp2-titre"><BR>
		</SPAN>
<%if(!process.isSuppression() ){%><FIELDSET>
			<TABLE border="0" class="sigp2">
				<tr>
					<td>N°engagement juridique</td>
					<td><INPUT type="text" size="20"
					name="<%=process.getNOM_EF_ENJU() %>"
					value="<%=process.getVAL_EF_ENJU() %>" class="sigp2-saisie"
					style="text-transform: uppercase"></td>

					<td><INPUT type="submit" value="OK"
					name="<%=process.getNOM_PB_OK() %>" class="sigp2-Bouton-100"></td>
				</tr>
		</TABLE></FIELDSET><!-- Liste des OT en cours -->
		<FIELDSET><LEGEND class="sigp2Fieldset" align="left">Bons d'engagement</LEGEND><TABLE border="0" class="sigp2">
				<TR align="center">
					<TD align="left">
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
					<TD align="left">
						<SELECT size="5" name="<%= process.getNOM_LB_BE() %>"
					class="sigp2-liste" style="text-transform: uppercase; width: 100%">
					<%= process.forComboHTML(process.getVAL_LB_BE(),process.getVAL_LB_BE_SELECT()) %>
				</SELECT>
				</TR>
				<tr>
					<td align="center"><%if(!process.isListeVide){ %><INPUT type="submit" value="Enlever"
					name="<%=process.getNOM_PB_ENLEVER() %>" class="sigp2-Bouton-100"><%} %></td>
				</tr>
			</TABLE>
			</FIELDSET>
				<%}else{ %>
			<FIELDSET>
				<TABLE border="1" cellpadding="0" cellspacing="0" class="sigp2" align="center">
					<TR align="center">
						<TD width="140">Code du bon d'engagement</TD>
				<TD width="140">Enseigne commerciale</TD>
				<TD width="140">Montant</TD>
			</TR>
					<TR align="center">
						<TD width="140" style="text-transform: uppercase" class="sigp2-saisie"><%=process.getVAL_ST_CODEBE() %></TD>
				<TD width="140" style="text-transform: uppercase"
					class="sigp2-saisie"><%=process.getVAL_ST_ENSEIGNE() %></TD>
				<TD width="140" style="text-transform: uppercase"
					class="sigp2-saisie"><%=process.getVAL_ST_MONTANT() %></TD>
			</TR>
				</TABLE>
		</FIELDSET><br>

<%} %>
			<FIELDSET>
				<TABLE border="0" cellpadding="0" cellspacing="0" class="sigp2" align="center">
					<TR align="center">
<%if(process.isAction()){ %>
						<TD width="151" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_VALIDER() %>" value="Valider" class="sigp2-Bouton-100"></TD>
<%} %>
						<TD width="137" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_ANNULER() %>" value="Annuler" class="sigp2-Bouton-100"></TD>
					</TR>
				</TABLE>
		</FIELDSET>
		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
