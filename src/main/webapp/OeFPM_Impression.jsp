<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<link href="theme/sigp2.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" href="theme/calendrier-mairie.css" type="text/css">
<SCRIPT type="text/javascript" src="js/GestionCalendrier.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/GestionBoutonDroit.js"></SCRIPT>
<jsp:useBean class="nc.mairie.seat.process.OeFPM_Impression" id="process" scope="session"></jsp:useBean>
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

function imprimOt()
{
	document.getElementById('recherche').style.display = 'none';
	document.getElementById('imprimer').style.display='none';
	window.print();
	document.getElementById('imprimer').style.display='block';
	document.getElementById('recherche').style.display = 'block';
}

</SCRIPT>
<TITLE>OeFPM_Impression.jsp</TITLE>
<STYLE type="text/css">
<!--
.imprim {
	display: block;
}
-->
</STYLE>
</HEAD>

<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">
<FORM name="formu" method="POST">
<TABLE border="0" width="100%" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;" align="right">
			<table border="0" class="sigp2" width="100%">
				<tr>
					<td class="sigp2-titre" width="30%"></td>
					<td class="sigp2-titre" align="left">FICHE D'ENTRETIENS DU PETIT MATERIEL</td>
					<td class="sigp2-titre">
						<INPUT type="image" border="0" src="images/jumelle.gif"
						width="21" height="17" name="<%=process.getNOM_PB_RECHERCHE() %>"
						id="recherche">
					</td>
					<td class="sigp2-titre">
						<!-- IMG src="images/print.gif" id="imprimer" 
						onclick="imprimOt()" style="cursor : pointer;"-->
						<!-- <IMG src="images/print.gif" id="imprimer" onclick="recherche.style.display='none';imprimer.style.display='none';window.print();imprimer.style.display='block';recherche.style.display='block';executeBouton('<%//=process.getNOM_PB_IMPRIMER() %>');" style="cursor : pointer;"> -->
<INPUT type="image" name="<%= process.getNOM_PB_IMPRIMER() %>"
					src="images/print.gif">

					</td>
				</tr>
				<tr>
					<td colspan="4">
					<CENTER><TABLE>
						<TR>
							<TD><SPAN class="sigp2-titre">Petit matériel</SPAN></TD>
						<TD></TD>
						<TD><SPAN class="sigp2-titre">N° Fiche</SPAN></TD>
						<TD></TD>
						<TD></TD>
						<TD rowspan="2"></TD>
						</TR>
					<TR>
						<TD><SPAN class="sigp2-titre"></SPAN><INPUT size="1" type="text"
							class="sigp2-saisie" maxlength="1" name="ZoneTampon"
							style="display : 'none';"> <INPUT type="text"
							name="<%=process.getNOM_EF_EQUIP() %>"
							value="<%=process.getVAL_EF_EQUIP() %>" class="sigp2-saisie"
							size="10"></TD>
						<TD></TD>
						<TD><INPUT type="text" name="<%=process.getNOM_EF_NUMFICHE() %>"
							value="<%=process.getVAL_EF_NUMFICHE() %>" class="sigp2-saisie"
							size="10"></TD>
						<TD></TD>
						<TD><INPUT type="submit" name="<%= process.getNOM_PB_EQUIP() %>"
							value="Rechercher" class="sigp2-Bouton-100"></TD>
					</TR>
				</TABLE></CENTER>
					</td>
				</tr>
			</table>
			<FIELDSET>			
			<TABLE border="3" bordercolor="#669999" cellpadding="2" cellspacing="2">
			<TBODY>
				<TR>
					<TD colspan="6" bgcolor="#669999" class="sigp2-titre" align="center">Infos
					concernant l'équipement</TD>
				</TR>
				<TR>
					<TD>	
						<TABLE border="0" class="sigp2" cellpadding="0" cellspacing="0">
							<TBODY>
							<TR>
								<TD><B>N°INVENTAIRE</B></TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisiemajuscule" style="text-transform: uppercase"><%= process.getVAL_ST_NOINVENT()%></TD>
								<TD class="sigp2-saisie"></TD>
								<TD><B>N°DE SERIE</B></TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisiemajuscule" style="text-transform: uppercase"><%= process.getVAL_ST_NOSERIE()%></TD>

							</TR>
							<TR>
								<TD>NOM D'EQUIPEMENT</TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_MARQUE()+" "+process.getVAL_ST_MODELE()%></TD>
								<TD width="10"></TD>
								<TD>TYPE</TD>
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
<!-- premier tableau 
				<TABLE border="0" class="sigp2" width="100%">
					<tr>
						<td colspan="3" align="center"></td>
					</tr>
					<tr>
						<td colspan="3">
				<TABLE border="3" width="100%" bordercolor="#669999" cellpadding="2"
					cellspacing="2" align="center" width="100%">
					<TR>
						<TD colspan="3" bgcolor="#669999" class="sigp2-titre">Détails d'un
						OT</TD>
					</TR>
					<TR>
						<TD width="10"></TD>-->
						</TD>
					</tr>
					<tr>
						<td>
<!--Détails du BPC-->
						<FIELDSET>
							<TABLE class="sigp2" width="100%">
								<TR>
				<TD colspan="5" height="10">
				<TABLE border="0" class="sigp2" align="center">
					<TR>
						<TD><SPAN class="sigp2-titre">NUMERO DE LA FICHE </SPAN></TD>
						<TD class="sigp2-saisiemajuscule" style="font-size: 16px"><b><%=process.getVAL_ST_NUMFICHE() %></b></TD>
					</TR>

				</TABLE>
				</TD>
			</TR>
								<TR>
									<TD width="92">DATE ENTREE</TD>
									<TD class="sigp2-saisie" style="text-transform: uppercase" width="121"><%=process.getVAL_ST_DENTREE() %></TD>
									<TD width="11"></TD>
									<TD>DATE SORTIE</TD>
									<TD class="sigp2-saisie" style="text-transform: uppercase" width="23"><%=process.getVAL_ST_DSORTIE() %></TD>
								</TR>
								
							<tr>
				<td colspan="5">ANOMALIES SIGNALEES</td>
			</tr>
							<tr>
				<td colspan="5"><TEXTAREA rows="3" cols="100"
					name="<%=process.getNOM_EF_COMMENTAIRE() %>" class="sigp2-saisie"
					style="text-transform: uppercase; width: 100%">
<%=process.getVAL_EF_COMMENTAIRE() %>
</TEXTAREA><INPUT type="submit" name="<%= process.getNOM_PB_COMMENTAIRE() %>"
					value="Valider" class="sigp2-Bouton-100"></td>
			</tr>
							<TR>
									<TD colspan="5">LISTE DES ENTRETIENS  REALISES</TD>
								</TR>
								<TR>
									<td colspan="5">
									<TABLE border="0" class="sigp2" width="100%">
									<%if (process.getTailleList()>0){ %>
										<tr>
											<td width="50%" valign="top"><SELECT
												size="<%=process.getTailleList()+1 %>"
												name="<%= process.getNOM_LB_ENTRETIENS() %>" class="sigp2-liste"
												style="width: 100%; text-transform: uppercase; font-weight: bold">
												<%= process.forComboHTML(process.getVAL_LB_ENTRETIENS(),process.getVAL_LB_ENTRETIENS_SELECT()) %>
											</SELECT></td>
											<td valign="top"><%if (process.isAffiche()){ %> <SELECT
												size="<%=process.getTailleList_suite()+1 %>"
												name="<%= process.getNOM_LB_ENTRETIENS_SUITE() %>"
												class="sigp2-liste"
												style="width: 100%; text-transform: uppercase; font-weight: bold">
												<%= process.forComboHTML(process.getVAL_LB_ENTRETIENS_SUITE(),process.getVAL_LB_ENTRETIENS_SUITE_SELECT()) %>
											</SELECT> <%} %></td>
										</tr>
									<%}else{ %>
										<tr>
											<td width="100%">
												<SELECT size="5" style="width: 100%"></SELECT>
											</td>
										</tr>
									<%} %>
									</TABLE>
									</td>
								</TR>
							<TR>
				<TD colspan="5">LES PIECES</TD>
			</TR>
							<TR>
				<TD colspan="5">
				<TABLE border="1" cellpadding="0" cellspacing="0" class="sigp2"
					width="100%">
					<TBODY>
						<TR>
							<TD align="center" colspan="2"><B>DESIGNATION</B></TD>
							<TD align="center" width="72"><B>QUANTITE</B></TD>
							<TD align="center" width="48"><B>P.U</B></TD>
							<TD align="center" width="84"><B>TRAVAUX INTERIEURS</B></TD>
							<TD width="92" align="center"><B>TRAVAUX EXTERIEURS</B></TD>
						</TR>
						<TR>
							<TD colspan="2" height="35"> </TD>
							<TD width="72"> </TD>
							<TD width="48"> </TD>
							<TD width="84"> </TD>
							<TD width="92"> </TD>
						</TR>
						<TR>
							<TD colspan="2" height="35"> </TD>
							<TD width="72"> </TD>
							<TD width="48"> </TD>
							<TD width="84"> </TD>
							<TD width="92"> </TD>
						</TR>
						<TR>
							<TD colspan="2" height="35"> </TD>
							<TD width="72"> </TD>
							<TD width="48"> </TD>
							<TD width="84"> </TD>
							<TD width="92"> </TD>
						</TR>
						<TR>
							<TD colspan="2" height="35"> </TD>
							<TD width="72"> </TD>
							<TD width="48"> </TD>
							<TD width="84"> </TD>
							<TD width="92"> </TD>
						</TR>
						<TR>
							<TD colspan="2" height="35"> </TD>
							<TD width="72"> </TD>
							<TD width="48"> </TD>
							<TD width="84"> </TD>
							<TD width="92"> </TD>
						</TR>
						<TR>
							<TD colspan="2" height="35"> </TD>
							<TD width="72"> </TD>
							<TD width="48"> </TD>
							<TD width="84"> </TD>
							<TD width="92"> </TD>
						</TR>
						<TR>
							<TD colspan="2" height="35"> </TD>
							<TD width="72"> </TD>
							<TD width="48"> </TD>
							<TD width="84"> </TD>
							<TD width="92"> </TD>
						</TR>
						<TR>
							<TD colspan="2" height="35"> </TD>
							<TD width="72"> </TD>
							<TD width="48"> </TD>
							<TD width="84"> </TD>
							<TD width="92"> </TD>
						</TR>
						<TR>
							<TD colspan="2" height="35"> </TD>
							<TD width="72"> </TD>
							<TD width="48"> </TD>
							<TD width="84"> </TD>
							<TD width="92"> </TD>
						</TR>
						<TR>
							<TD colspan="2" height="35"> </TD>
							<TD width="72"> </TD>
							<TD width="48"> </TD>
							<TD width="84"> </TD>
							<TD width="92"> </TD>
						</TR>
						<TR>
							<TD colspan="2" height="35"> </TD>
							<TD width="72"> </TD>
							<TD width="48"> </TD>
							<TD width="84"> </TD>
							<TD width="92"> </TD>
						</TR>
						<TR>
							<TD colspan="2" height="35"> </TD>
							<TD width="72"> </TD>
							<TD width="48"> </TD>
							<TD width="84"> </TD>
							<TD width="92"> </TD>
						</TR>
						<TR height="2">
							<TD width="100" bgcolor="#5f5f5f"></TD>
							<TD width="150" bgcolor="#5f5f5f"></TD>
							<TD width="72" bgcolor="#5f5f5f"></TD>
							<TD width="48" bgcolor="#5f5f5f"></TD>
							<TD width="84" bgcolor="#5f5f5f"></TD>
							<TD width="92" bgcolor="#5f5f5f"></TD>
						</TR>

						<tr>
							<td align="center" rowspan="2" style="text-transform: uppercase"
								width="100" valign="top">Main d'oeuvre<BR>
							<BR>
							Noms et heures</td>
							<td rowspan="2" colspan="3" height="115">                                           </td>
							<td align="center" style="text-transform: uppercase" width="84"><B>Total
							travaux intérieurs</B></td>
							<td align="center" width="92"><SPAN
								style="text-transform: uppercase"><B>Total<BR>
							travaux extérieurs</B></SPAN></td>
						</tr>
						<tr>
							<td height="15" width="84"> </td>
							<td height="20" width="92"> </td>
						</tr>
						<tr>
							<td valign="top" align="center" colspan="2" height="75">VISA CHEF
							D'ATELIER<BR>
							  <BR>
							 </td>
							<td valign="top" align="center" colspan="2">OBSERVATION<BR>
							 </td>
							<td align="center" width="84"><B>TOTAL<BR>
							MAIN D'OEUVRE EN HEURE</B></td>
							<td width="94"> </td>
						</tr>
					</TBODY>
				</TABLE>
				</TD>
			</TR>
							
						</TABLE>
						</FIELDSET>

						</td>
					</tr>
					<tr>
						<td>
							<!-- <BR>
							</TD>
							<TD width="10"></TD>
						</TR>
					</TABLE>
					</td>
			</tr>
		</table>-->
<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>">
	</TD>
    </TR>
</TABLE>
</FORM>
</BODY>
</HTML>
<%=process.afficheScript() %>
