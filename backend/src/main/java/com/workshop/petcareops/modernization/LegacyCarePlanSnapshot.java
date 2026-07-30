package com.workshop.petcareops.modernization;

import java.util.ArrayList;
import java.util.List;

public class LegacyCarePlanSnapshot {

    private long appointmentId;
    private String ownerName;
    private String petName;
    private String followUpBucket;
    private String preferredChannel;
    private String summary;
    private String clinicianAction;
    private List<String> tags = new ArrayList<String>();

    public long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getFollowUpBucket() {
        return followUpBucket;
    }

    public void setFollowUpBucket(String followUpBucket) {
        this.followUpBucket = followUpBucket;
    }

    public String getPreferredChannel() {
        return preferredChannel;
    }

    public void setPreferredChannel(String preferredChannel) {
        this.preferredChannel = preferredChannel;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getClinicianAction() {
        return clinicianAction;
    }

    public void setClinicianAction(String clinicianAction) {
        this.clinicianAction = clinicianAction;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = new ArrayList<String>(tags);
    }
}