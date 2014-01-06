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
<TITLE>OeBPC_modifier.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OeBPC_modifier" id="process" scope="session"></jsp:useBean>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">

<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><SPAN class="sigp2-titre">Modification<BR></SPAN><INPUT
			type="submit" value="OK invent"
			name="<%=process.getNOM_PB_INVENTAIRE() %>"
			style="visibility : hidden;">
		<INPUT type="submit" value="OK immat"
			name="<%=process.getNOM_PB_IMMAT() %>" style="visibility : hidden;">
		
		

		<BR>

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
								<TD class="sigp2-saisiemajuscule" style="text-transform: uppercase"><%=process.getVAL_ST_NOINVENT() %></TD>
								<TD width="10"></TD>
								<TD><B>N°d'immat(ou série)</B></TD>
								<TD width="10" style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisiemajuscule" style="text-transform: uppercase"><%=process.getVAL_ST_NOIMMAT() %></TD>

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
		<!-- <FIELDSET> -->
				<TABLE border="0" class="sigp2" width="90%">
					<tr>
						<td height="10" colspan="3"></td>
					</tr>
					<tr>
						<td colspan="3">
							<table border="3" width="100%" bordercolor="#669999" cellpadding="2" cellspacing="2" align="center" width="100%">
								<tr>
									<td colspan="3" bgcolor="#669999" class="sigp2-titre">Détails d'un BPC</td>
								</tr>
								<TR>
									<TD width="10"></TD>
									<TD align="center"><!--Détails du BPC--><FIELDSET>
										<TABLE class="sigp2">
											<tr>
								<td colspan="5" height="10"></td>
							</tr>
							<TR>
								<TD height="10" colspan="5" align="center">
								<TABLE border="0" class="sigp2">
									<TR>
										<TD>Numéro du BPC</TD>
										<TD><INPUT type="text" size="7"
											name="<%=process.getNOM_EF_BPC() %>"
											value="<%=process.getVAL_EF_BPC() %>"
											class="sigp2-saisiemajuscule" maxlength="30"
											style="text-transform: uppercase"></TD>
									</TR>

								</TABLE>
								</TD>
							</TR>
							<TR>
												<TD>Date</TD>
												<TD>
													<TABLE border="0" class="sigp2">
														<TR>
															<TD><INPUT type="text" size="8"
											name="<%=process.getNOM_EF_DATE() %>"
											value="<%=process.getVAL_EF_DATE() %>" class="sigp2-saisie"></TD>
															<TD><IMG src="images/calendrier.gif"
											onclick="return showCalendar('<%=process.getNOM_EF_DATE()%>', 'dd/mm/y');"></TD>
														</TR>
													</TABLE></TD>
												<TD></TD>
												<TD>Compteur (<%=process.getVAL_ST_COMPTEUR() %>)</TD>
								<TD><INPUT type="text" size="10"
									name="<%=process.getNOM_EF_COMPTEUR() %>"
									value="<%=process.getVAL_EF_COMPTEUR() %>" class="sigp2-saisie"></TD>
							</TR>
							<TR>
								<TD>Heure de prise</TD>
								<TD>
									<TABLE border="0" class="sigp2">
										<TR>
											<TD><INPUT type="text" size="5"
											name="<%=process.getNOM_EF_HEURE() %>"
											value="<%=process.getVAL_EF_HEURE() %>" class="sigp2-saisie"></TD>
										</TR>
									</TABLE></TD>
								<TD></TD>
								<TD>N° pompe</TD>
								<TD style="text-transform: uppercase"><SELECT size="1"
									name="<%=process.getNOM_LB_POMPE() %>" class="sigp2-liste">
									<%= process.forComboHTML(process.getVAL_LB_POMPE(),process.getVAL_LB_POMPE_SELECT()) %>
								</SELECT></TD>
							</TR>
							<TR>
												<TD>Quantité</TD>
												<TD class="sigp2-saisie"><TABLE border="0" class="sigp2">
									<TR>
										<TD><INPUT type="text" size="5"
											name="<%=process.getNOM_EF_QTE() %>"
											value="<%=process.getVAL_EF_QTE() %>" class="sigp2-saisie">&nbsp;L</TD>
									</TR>
								</TABLE>
								</TD>
												<TD></TD>
												<TD>Carburant</TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_CARBU() %></TD>
							</TR>
	<!-- 										<TR>
												<TD>Mode de prise</TD>
												<TD class="sigp2-saisie" style="text-transform: uppercase"><TABLE border="0" class="sigp2">
									<TR>
										<TD><SELECT size="1"
											name="<%//=process.getNOM_LB_MODEPRISE() %>"
											class="sigp2-liste">
											<%//= process.forComboHTML(process.getVAL_LB_MODEPRISE(),process.getVAL_LB_MODEPRISE_SELECT()) %>
										</SELECT></TD>
									</TR>
								</TABLE>
								</TD>
												<TD><INPUT type="submit" value="OK"
									name="<%//=process.getNOM_PB_OK_MODEPRISE() %>"
									style="visibility : hidden;"></TD>
												<TD></TD>
								<TD></TD>
							</TR>
 -->
										</TABLE>
								</FIELDSET><br>
									</TD>
									<TD width="10"></TD>
								</TR>
							</TABLE>		
			<br>
				<TABLE border="0" cellpadding="0" cellspacing="0" class="sigp2" align="center">
					<TR align="center">
						<TD width="151" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_VALIDER() %>" value="Valider" class="sigp2-Bouton-100"></TD>
						<TD width="137" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_ANNULER() %>" value="Annuler" class="sigp2-Bouton-100"></TD>
					</TR>
				</TABLE>
			</td>
		</tr>
	</table>
				<br>
	<!-- </FIELDSET> -->
	

		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
