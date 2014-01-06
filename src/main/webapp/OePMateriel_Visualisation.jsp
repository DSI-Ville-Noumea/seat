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
<TITLE>OePMateriel_Visualisation.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OePMateriel_Visualisation" id="process" scope="session"></jsp:useBean>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">

<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><SPAN class="sigp2-titre"></SPAN>
<FIELDSET>
		<TABLE border="0" class="sigp2">
			<TR>
				<TD class="sigp2-titre">Petit Matériel</TD>
				<TD></TD>
				<TD><INPUT type="text"
					name="<%=process.getNOM_EF_RECHERCHER() %>"
					value="<%=process.getVAL_EF_RECHERCHER() %>"
					class="sigp2-saisie" size="10"></TD>
				<TD><INPUT type="submit"
					name="<%= process.getNOM_PB_RECHERCHER() %>"
					value="Rechercher" class="sigp2-Bouton-100"></TD>
				<TD><INPUT type="image" border="0" src="images/jumelle.gif"
					width="21" height="17" name="<%=process.getNOM_PB_RECHERCHE_PM() %>"></TD>
			</TR>
		</TABLE>
</FIELDSET>
		<SPAN class="sigp2-titre"><BR>
		</SPAN>
				<TABLE border="0" class="sigp2">
					<tr>
						<td colspan="3" align="center"></td>
					</tr>
					<tr>
						<td height="10" colspan="3"></td>
					</tr>
					<tr>
						<td colspan="3">
							<table border="3" width="100%" bordercolor="#669999" cellpadding="2" cellspacing="2" align="center" width="100%">
								<tr>
						<td bgcolor="#669999" class="sigp2-titre">Détails d'un
						petit matériel</td>
					</tr>
								<TR>
									<TD><!--Détails du BPC--><FIELDSET>
										<TABLE class="sigp2" width="100%">
											<tr>
								<td colspan="5" height="10"></td>
							</tr>
											<TR>
												<TD width="150">N° inventaire</TD>
												<TD class="sigp2-saisiemajuscule"><%= process.getVAL_ST_PMINV() %></TD>
												<TD width="15"></TD>
												<TD>N°  de série</TD>
								<TD class="sigp2-saisiemajuscule"><%= process.getVAL_ST_PMSERIE() %></TD>
							</TR>
							<TR>
								<TD width="95">Nom d'équipement</TD>
								<TD class="sigp2-saisie"><%= process.getVAL_ST_NOM_EQUIP() %></TD>
								<TD></TD>
								<TD>
								Fournisseur</TD>
								<TD class="sigp2-saisie">
									
								<%= process.getVAL_ST_FOURNISSEUR() %></TD>
							</TR>
							<TR>
								<TD>Date d'achat</TD>
								<TD class="sigp2-saisie"><%= process.getVAL_ST_DACHAT() %></TD>
								<TD>
									</TD>
								<TD width="15">Prix d'achat</TD>
								<TD class="sigp2-saisie"><%= process.getVAL_ST_PRIX() %></TD>
							</TR>
							<TR>
								<TD>Durée de garantie (en jours)</TD>
								<TD class="sigp2-saisie"><%= process.getVAL_ST_DGARANTIE() %></TD>
								<TD></TD>
								<TD width="15"></TD>
								<TD></TD>
							</TR>
						</TABLE>
								</FIELDSET><br>
								<FIELDSET>
									
						<TABLE border="0" class="sigp2">
							<TR>
								<TD align="left" width="150">Date de mise en circulation</TD>
								<TD class="sigp2-saisie"><%= process.getVAL_ST_DMES() %></TD>
								<TD width="10"></TD>
								<TD>Réserve :</TD>
								<TD class="sigp2-saisie"><%= process.getVAL_ST_RESERVE() %></TD>
							</TR>
							<TR>
								<TD align="left">Date de mise hors circulation</TD>
								<TD class="sigp2-saisie"><%= process.getVAL_ST_DMHS() %></TD>
								<TD></TD>
								<TD></TD>
								<TD></TD>
							</TR>
						</TABLE>
						</FIELDSET>
									</TD>
								</TR>
							</TABLE></td>
		</tr>
	</table><BR>
		<%if(process.isDebranche){ %>
		<INPUT type="submit" value="Retour"
			name="<%=process.getNOM_PB_RETOUR() %>" class="sigp2-Bouton-100"><BR>
		<%} %><br>
		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
