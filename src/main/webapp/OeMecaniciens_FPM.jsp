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
<TITLE>OeMecaniciens_OT.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OeMecaniciens_FPM" id="process" scope="session"></jsp:useBean>
<BODY background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">

<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><SPAN class="sigp2-titre"><%=process.getVAL_ST_TITRE_ACTION() %>d'un mécanicien pour la fiche d'entretiens<%=process.getVAL_ST_NUMFICHE() %><BR>
		<BR></SPAN>

<BR>
		<FIELDSET>
		
		<TABLE border="3" bordercolor="#669999" cellpadding="2"
			cellspacing="2">
			<TBODY>
				<TR>
					<TD colspan="6" bgcolor="#669999" class="sigp2-titre">Infos
					concernant le petit matériel</TD>
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
								<TD><B>N°de série</B></TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisiemajuscule" style="text-transform: uppercase"><%= process.getVAL_ST_NOSERIE()%></TD>

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
		</FIELDSET>
<%if(!process.isSuppression){ %>
<br>
			<TABLE class="sigp2">
				<TR>
					<TD><!-- Mécaniciens de l'ATM -->
						<FIELDSET><LEGEND class="sigp2Fieldset" align="left">Mécaniciens</LEGEND>
						<TABLE border="0" class="sigp2">
							<TR align="center">
								<TD align="left">
							<TABLE width="100%" border="1">
								<TBODY>
									<TR>
										<TD class="sigp2-titre-liste">Agent                          </TD>
									</TR>
								</TBODY>
							</TABLE>
							</TD>
							</TR>
							<TR>
								<TD align="left">
									<SELECT size="5" name="<%= process.getNOM_LB_MECANICIENS() %>"
								class="sigp2-liste" style="text-transform: uppercase; width: 100%"
								ondblclick='executeBouton("<%= process.getNOM_PB_AJOUTER()%>")'>
								<%= process.forComboHTML(process.getVAL_LB_MECANICIENS(),process.getVAL_LB_MECANICIENS_SELECT()) %>
							</SELECT>
							</TR>
							<tr>
								<td align="center"></td>
							</tr>
						</TABLE>
					</FIELDSET>
					</TD>
					<TD><INPUT type="submit" value="Ajouter&gt;&gt;"
					name="<%=process.getNOM_PB_AJOUTER() %>" class="sigp2-Bouton-100"><BR>
<%if(process.tailleListeMecaFPM){ %>
				<INPUT
					type="submit" value="&lt;&lt;Enlever"
					name="<%=process.getNOM_PB_ENLEVER() %>" class="sigp2-Bouton-100"></TD>
<%} %>
					<TD><!-- Mécaniciens intervenant sur l'OT -->
						<FIELDSET><LEGEND class="sigp2Fieldset" align="left">Mécaniciens pour la fiche d'entretiens</LEGEND><TABLE border="0" class="sigp2">
							<TR align="center">
								<TD align="left">
			
							<TABLE width="100%" border="1">
								<TBODY>
									<TR>
										<TD class="sigp2-titre-liste">Agent                          </TD>
									</TR>
								</TBODY>
							</TABLE>
							</TD>
							</TR>
							<TR>
								<TD align="left">
									<SELECT size="5" name="<%= process.getNOM_LB_MECA_FPM() %>"
								class="sigp2-liste" style="text-transform: uppercase; width: 100%" ondblclick='executeBouton("<%= process.getNOM_PB_ENLEVER()%>")'>
								<%= process.forComboHTML(process.getVAL_LB_MECA_FPM(),process.getVAL_LB_MECA_FPM_SELECT()) %>
							</SELECT>
							</TR>
							<TR>
								<TD align="center"><%//if(process.getListePiecesOT().size()>0){ %></TD>
			
							</TR>
						</TABLE>
			</FIELDSET>
					</TD>
				</TR>
			</TABLE>


		
			<BR>
		
<%//} %>
<%}else{ %>
			<FIELDSET>
				<TABLE border="1" cellpadding="0" cellspacing="0" class="sigp2" align="center">
					<TR align="center">
						<TD width="140">Mécanicien</TD>
					</TR>
					<TR align="center">
						<TD width="140" style="text-transform: uppercase" class="sigp2-saisie"><%=process.getVAL_ST_MECANICIENS() %></TD>
					</TR>
				</TABLE>
		</FIELDSET><br>

<%} %>
			<FIELDSET>
				<TABLE border="0" cellpadding="0" cellspacing="0" class="sigp2" align="center">
					<TR align="center">
<%//if(process.isAction()){ %>
						<TD width="151" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_VALIDER() %>" value="Valider" class="sigp2-Bouton-100"></TD>
<%//} %>
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
