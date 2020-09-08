/**
 * The contents of this file are subject to the OpenMRS Public License
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
 **/

package org.openmrs.module.patientdashboard.web.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.IpdService;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmissionLog;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmitted;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmittedLog;
import org.openmrs.module.hospitalcore.util.PatientDashboardConstants;
import org.openmrs.module.patientdashboard.web.controller.global.IPDRecord;
import org.openmrs.module.patientdashboard.web.controller.global.IPDRecordUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller("DashboardIpdRecordController")
@RequestMapping("/module/patientdashboard/ipdRecord.htm")
public class IPDRecordController {

	@RequestMapping(method=RequestMethod.GET)
	public String view(Model model, @RequestParam("patientId") Integer patientId){
		IpdService ipdService = Context.getService(IpdService.class);
		//HospitalCoreService hospitalCore = Context.getService(HospitalCoreService.class);
		List<IpdPatientAdmissionLog> currentAdmissionList = ipdService.listIpdPatientAdmissionLog(patientId, null,IpdPatientAdmitted.STATUS_ADMITTED, 0, 1);
		IpdPatientAdmissionLog currentAdmission = CollectionUtils.isEmpty(currentAdmissionList)?null : currentAdmissionList.get(0);
		if(currentAdmission != null){
			IpdPatientAdmitted admitted = ipdService.getAdmittedByPatientId(patientId);
			model.addAttribute("admitted", admitted);
			String diagnosis = "";
			List<Obs> listObsByObsGroup = Context.getObsService().findObsByGroupId(currentAdmission.getOpdObsGroup().getId());
			
			//get diagnosis
			ConceptService conceptService = Context.getConceptService();
	        AdministrationService administrationService = Context.getAdministrationService();
			String gpDiagnosis = administrationService.getGlobalProperty(PatientDashboardConstants.PROPERTY_PROVISIONAL_DIAGNOSIS);
			 String gpProcedure = administrationService.getGlobalProperty(PatientDashboardConstants.PROPERTY_POST_FOR_PROCEDURE);
			Concept conDiagnosis  = conceptService.getConcept(gpDiagnosis);
			Concept conProcedure  = conceptService.getConcept(gpProcedure);
			if(CollectionUtils.isNotEmpty(listObsByObsGroup)){
				for( Obs obs : listObsByObsGroup){
					//diagnosis
					if( obs.getConcept().getConceptId().equals(conDiagnosis.getConceptId()) &&obs.getObsGroup() != null &&  obs.getObsGroup().getId() == currentAdmission.getOpdObsGroup().getId()){
//						obs.getV
						if(obs.getValueCoded() != null){
							diagnosis +=obs.getValueCoded().getName()+"<br/>";
						}
						if(StringUtils.isNotBlank(obs.getValueText())){
							diagnosis +=obs.getValueText()+"<br/>";
						}
						//System.out.println(obs.getva);
					}
					
				}
			}
			if( StringUtils.endsWith(diagnosis, "<br/>")){
				diagnosis = StringUtils.removeEnd(diagnosis, "<br/>");
			}
			
			String finalDiagnosis = "";
			String finalProcedures ="";
			if(currentAdmission.getIpdEncounter() != null && CollectionUtils.isNotEmpty(currentAdmission.getIpdEncounter().getAllObs())){
				for( Obs obs : currentAdmission.getIpdEncounter().getAllObs()){
					//diagnosis
					if( obs.getConcept().getConceptId().equals(conDiagnosis.getConceptId()) ){
//						obs.getV
						if(obs.getValueCoded() != null){
							finalDiagnosis +=obs.getValueCoded().getName()+"<br/>";
						}
						if(StringUtils.isNotBlank(obs.getValueText())){
							finalDiagnosis +=obs.getValueText()+"<br/>";
						}
						//System.out.println(obs.getva);
					}
					//procedure
					if( obs.getConcept().getConceptId().equals(conProcedure.getConceptId()) ){
//						obs.getV
						if(obs.getValueCoded() != null){
							finalProcedures +=obs.getValueCoded().getName()+"<br/>";
						}
						if(StringUtils.isNotBlank(obs.getValueText())){
							finalProcedures +=obs.getValueText()+"<br/>";
						}
					}
				}
			}
			
			if( StringUtils.endsWith(finalDiagnosis, "<br/>")){
				finalDiagnosis = StringUtils.removeEnd(finalDiagnosis, "<br/>");
			}
			if( StringUtils.endsWith(finalProcedures, "<br/>")){
				finalProcedures = StringUtils.removeEnd(finalProcedures, "<br/>");
			}
			
			
			model.addAttribute("finalProcedures", finalProcedures);
			model.addAttribute("finalDiagnosis", finalDiagnosis);
			model.addAttribute("diagnosis", diagnosis);
			
			
			
		}
		
		List<IpdPatientAdmissionLog> listPatientDischarge = ipdService.listIpdPatientAdmissionLog(patientId, null, IpdPatientAdmitted.STATUS_DISCHARGE, 0, 0);			
		List<IpdPatientAdmittedLog> admittedLogs = ipdService.listAdmittedLogByPatientId(patientId);
		IPDRecordUtil util = new IPDRecordUtil();
		List<IPDRecord> records = util.generateIPDRecord(listPatientDischarge, admittedLogs);
		model.addAttribute("patientId", patientId);
		model.addAttribute("records", records);
		/*IpdService ipdService = Context.getService(IpdService.class);
		
		// Get list all admitted in log table 
		
		
		if( listAdmitted == null || listAdmitted.size() == 0 ){
			return "module/patientdashboard/ipdEntry"; 
		}
		model.addAttribute("listAdmitted", listAdmitted);

		// Get current admitted record ( One patient has only one admitted record at one time ) 
		IpdPatientAdmitted curAdmitted = ipdService.getAdmittedByPatientId(patientId);
		
		model.addAttribute("currentAdmitted", curAdmitted);
		
		// Get OPD Diagnosis
	    String gpOPDEncType = Context.getAdministrationService().getGlobalProperty(PatientDashboardConstants.PROPERTY_OPD_ENCOUTNER_TYPE);
		EncounterType encounterType = Context.getEncounterService().getEncounterType(gpOPDEncType);
		
		Patient patient = Context.getPatientService().getPatient(patientId);
		
		if( patient == null ){
			return "module/patientdashboard/ipdRecord"; 
		}
		PatientDashboardService dashboardService =  Context.getService(PatientDashboardService.class);
	    String orderLocationId = "1";
        Location location = Context.getLocationService().getLocation(Integer.parseInt(orderLocationId)) ;
    	List<Encounter> encounters = dashboardService.getEncounter(patient, location, encounterType, null);
		String gpDiagnosis = Context.getAdministrationService().getGlobalProperty(PatientDashboardConstants.PROPERTY_PROVISIONAL_DIAGNOSIS);
		Concept conDiagnosis  = Context.getConceptService().getConcept(gpDiagnosis);

		String gpProcedure = Context.getAdministrationService().getGlobalProperty(PatientDashboardConstants.PROPERTY_POST_FOR_PROCEDURE);
		Concept conProcedure  = Context.getConceptService().getConcept(gpProcedure);
		
		String diagnosis = "";
		String procedure="";
		for( Encounter enc: encounters ){
			diagnosis = "";
			procedure="";
			for( Obs obs : enc.getAllObs()){
				//diagnosis
				if( obs.getConcept().getConceptId().equals(conDiagnosis.getConceptId()) ){
//					obs.getV
					if(obs.getValueCoded() != null){
						diagnosis +=obs.getValueCoded().getName()+", ";
					}
					if(StringUtils.isNotBlank(obs.getValueAsString(Context.getLocale()))){
						diagnosis +=obs.getValueAsString(Context.getLocale())+", ";
					}
					//System.out.println(obs.getva);
				}
				//procedure
				if( obs.getConcept().getConceptId().equals(conProcedure.getConceptId()) ){
//					obs.getV
					if(obs.getValueCoded() != null){
						procedure +=obs.getValueCoded().getName()+", ";
					}
					if(StringUtils.isNotBlank(obs.getValueAsString(Context.getLocale()))){
						procedure +=obs.getValueAsString(Context.getLocale())+", ";
					}
				}
			}
			if( StringUtils.endsWith(diagnosis, ", ")){
				diagnosis = StringUtils.removeEnd(diagnosis, ", ");
			}
			if( StringUtils.endsWith(procedure, ", ")){
				procedure = StringUtils.removeEnd(procedure, ", ");
			}
			
		};
		
		
		model.addAttribute("currentDiagnosis", diagnosis);
		model.addAttribute("procedure", procedure);*/
		
		model.addAttribute("currentAdmission", currentAdmission);
		
		
		return "module/patientdashboard/ipdRecord";
	}
}
