 <%--
 * * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 *  along with Patient-dashboard module.  If not, see <http://www.gnu.org/licenses/>.
 *
--%> 
<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script type="text/javascript">
function dischargeSummary(encounterId){
var patientId = ${patientId};
jQuery.ajax({
				type : "GET",
				url : getContextPath() + "/module/patientdashboard/printDischargeSummarySlip.form",
				data : ({
					encounterId			: encounterId,
					patientId			: patientId
				}),
				success : function(data) {
					jQuery("#printDischargeSummary").html(data);	
					jQuery("#printDischargeSummary").hide();
					printDischargeSummary();	
				}
				
			});			
}
</script>
<script type="text/javascript">
function printDischargeSummary(){
jQuery("#printDischargeSummary").printArea({
            mode : "popup",
            popClose : true
            });
}
</script>

<form>

<b class="boxHeader">Current Admission</b>
<table class="box" width="100%">
	<tr align="center">
		<th><spring:message code="patientdashboard.dateOfAdmission"/></th>
		<th><spring:message code="patientdashboard.treatingDoctor"/></th>
		<th><spring:message code="patientdashboard.provisionalDiagnosis"/></th>
		<th><spring:message code="patientdashboard.final/currentDiagnosis"/></th>
		<th><spring:message code="patientdashboard.surgery"/></th>
		<th><spring:message code="patientdashboard.action"/></th>
	
	</tr>
	<c:choose>
		<c:when test="${not empty currentAdmission}">
			<tr align="center">
				<td><openmrs:formatDate date="${currentAdmission.admissionDate}" type="textbox"/></td>
				<td>${admitted.ipdAdmittedUser.givenName}</td>
				<td>${diagnosis}</td>
				<td>
					${finalDiagnosis }
				
				</td>
				<td>
					${finalProcedures }
				</td>
				<td><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="<spring:message code="patientdashboard.addEditFinalResult"/>" onclick="ADMITTED.changeFinalResult('${currentAdmission.id}');"/>
				<!--<c:if test="${not empty  finalDiagnosis}">
					<input type="button" value="<spring:message code="patientdashboard.discharge"/>" onclick="ADMITTED.discharge('${admitted.id}');"/>
				</c:if>
				-->
				</td>
			</tr>
		</c:when>
		<c:otherwise>
				<tr align="center"><td colspan="6">No data</td></tr>
		</c:otherwise>
	</c:choose>
</table>


<br/>

<b class="boxHeader">Previous Admission</b>
<table class="box" width="100%">
	<tr align="center">
		<!--<th><spring:message code="patientdashboard.hospital"/></th>-->
		<th><spring:message code="patientdashboard.dateOfAdmission"/></th>
		<th><spring:message code="patientdashboard.dateOfDischarge"/></th>
		<th><spring:message code="patientdashboard.finalDiagnosis"/></th>
		<th><spring:message code="patientdashboard.finalProcedures"/></th>
		<th><spring:message code="patientdashboard.finalDrugs"/></th>
		<th><spring:message code="patientdashboard.admissionOutcome"/></th>
		<th><spring:message code="patientdashboard.linkToD/cSummary"/></th>
	</tr>
	<c:choose>
	  <c:when test="${not empty records}">
		<c:forEach items="${records}" var="record" varStatus="varStatus">
			<tr align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
				<!--<td>${record.hospitalName }</td>-->
				<td><openmrs:formatDate date="${record.admissionDate}" type="textbox"/></td>
				<td><openmrs:formatDate date="${record.dischargeDate}" type="textbox"/></td>
	
				<td>${record.diagnosis }</td>
				<td>${record.procedures }</td>
							<td><table class="box">
	<tr align="center">
		<th><strong>S.No</strong></th>
		<th><strong>Drug</strong></th>
		<th><strong>Formulation</strong></th>
		<th><strong>Comments</strong></th>
	</tr>
	
	<c:forEach items="${record.subDetails}" var="opdDrugOrder" varStatus="index">
	
	<tr>
		
	 
			<td>${index.count}</td>
			<td>${opdDrugOrder.inventoryDrug.name}</td>
			<td>${opdDrugOrder.inventoryDrugFormulation.name}-${opdDrugOrder.inventoryDrugFormulation.dozage}</td>
			<td>${opdDrugOrder.comments}</td>
		
	</tr>	

	</c:forEach>
	
</table></td>
				<td>${record.admissionOutcome }</td>
				<td><a href="#" onclick="dischargeSummary('${ record.id}');"><small>Print</small></a></td>
			</tr>
		</c:forEach>
	</c:when>
	<c:otherwise>
				<tr align="center"><td colspan="7">No data</td></tr>
	</c:otherwise>
	</c:choose>
</table>
<div id="printDischargeSummary" style="visibility:hidden;">

</div>
</form>