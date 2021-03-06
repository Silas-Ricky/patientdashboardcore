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

import java.util.Arrays;

public class OPDEntryCommand {
	
	private Integer[] selectedDiagnosisList;
	private Integer[] selectedProcedureList;
	private Integer[] selectedInvestigationList;
	private Integer patientId;
	private String internalReferral;
	private String externalReferral;
	private String historyOfPresentIlness;
	private String otherInstructions;
	private Integer admit;
	private String outCome;
	private String dateFollowUp;
	private Integer admitWard;
	private String radio_f ;
	private Integer opdId;
	private Integer ipdWard;
	private Integer referralId;
	private Integer queueId;
	
	
	public Integer getIpdWard() {
		return ipdWard;
	}
	public void setIpdWard(Integer ipdWard) {
		this.ipdWard = ipdWard;
	}
	public String getInternalReferral() {
		return internalReferral;
	}
	public void setInternalReferral(String internalReferral) {
		this.internalReferral = internalReferral;
	}
	public String getExternalReferral() {
		return externalReferral;
	}
	public void setExternalReferral(String externalReferral) {
		this.externalReferral = externalReferral;
	}
	public String getHistoryOfPresentIlness() {
		return historyOfPresentIlness;
	}
	public void setHistoryOfPresentIlness(String historyOfPresentIlness) {
		this.historyOfPresentIlness = historyOfPresentIlness;
	}
	public String getOtherInstructions() {
		return otherInstructions;
	}
	public void setOtherInstructions(String otherInstructions) {
		this.otherInstructions = otherInstructions;
	}
	public String getOutCome() {
		return outCome;
	}
	public void setOutCome(String outCome) {
		this.outCome = outCome;
	}
	
	public String getDateFollowUp() {
		return dateFollowUp;
	}
	public void setDateFollowUp(String dateFollowUp) {
		this.dateFollowUp = dateFollowUp;
	}
	public Integer getAdmitWard() {
		return admitWard;
	}
	public void setAdmitWard(Integer admitWard) {
		this.admitWard = admitWard;
	}
	public Integer getPatientId() {
		return patientId;
	}
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
	
	public Integer[] getSelectedDiagnosisList() {
		return selectedDiagnosisList;
	}
	public void setSelectedDiagnosisList(Integer[] selectedDiagnosisList) {
		this.selectedDiagnosisList = selectedDiagnosisList;
	}
	public Integer[] getSelectedProcedureList() {
		return selectedProcedureList;
	}
	public void setSelectedProcedureList(Integer[] selectedProcedureList) {
		this.selectedProcedureList = selectedProcedureList;
	}
	
	public Integer[] getSelectedInvestigationList() {
		return selectedInvestigationList;
	}
	public void setSelectedInvestigationList(Integer[] selectedInvestigationList) {
		this.selectedInvestigationList = selectedInvestigationList;
	}
	public String getRadio_f() {
		return radio_f;
	}
	public void setRadio_f(String radio_f) {
		this.radio_f = radio_f;
	}
	
	public Integer getAdmit() {
		return admit;
	}
	public void setAdmit(Integer admit) {
		this.admit = admit;
	}
	@Override
	public String toString() {
		return "OPDEntryCommand [selectedDiagnosisList="
				+ Arrays.toString(selectedDiagnosisList)
				+ ", selectedProcedureList="
				+ Arrays.toString(selectedProcedureList) + ", patientId="
				+ patientId + ", internalReferral=" + internalReferral
				+ ", externalReferral=" + externalReferral + ", historyOfPresentIlness=" + historyOfPresentIlness
				+ ", admit=" + admit + ", outCome=" + outCome
				+ ", dateFollowUp=" + dateFollowUp + ", admitWard=" + admitWard
				+ ", radio_f=" + radio_f + "]";
	}
	public Integer getOpdId() {
		return opdId;
	}
	public void setOpdId(Integer opdId) {
		this.opdId = opdId;
	}
	public Integer getReferralId() {
		return referralId;
	}
	public void setReferralId(Integer referralId) {
		this.referralId = referralId;
	}
	public Integer getQueueId() {
		return queueId;
	}
	public void setQueueId(Integer queueId) {
		this.queueId = queueId;
	}
	
	
	
	
}
