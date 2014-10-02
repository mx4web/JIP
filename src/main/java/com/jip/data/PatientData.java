package com.jip.data;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Yu Xiao
 */
public class PatientData {
    
    public static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    
    private long patientId;
    private String lastName;
    private String firstName;
    private String diagnosis;
    private Timestamp dateOfAdmission;
    private Timestamp dateOfRelease;

    public PatientData() {
        super();
        this.patientId = -1;
        this.lastName = null;
        this.firstName = null;
        this.diagnosis = null;
        this.dateOfAdmission = null;
        this.dateOfRelease = null;
    }

    public PatientData(long patientId, String lastName, String firstName, String diagnosis, Timestamp dateOfAdmission, Timestamp dateOfRelease) {
        super();
        this.patientId = patientId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.diagnosis = diagnosis;
        this.dateOfAdmission = dateOfAdmission;
        this.dateOfRelease = dateOfRelease;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Timestamp getDateOfAdmission() {
        return dateOfAdmission;
    }
    
    public void setDateOfAdmission(Timestamp dateOfAdmission) {
        this.dateOfAdmission = dateOfAdmission;
    }
    
    public void setDateOfAdmission(String date) throws ParseException{
        
        Date d = formatter.parse(date);
        setDateOfAdmission(new Timestamp(d.getTime()));
    }
        
    public Timestamp getDateOfRelease() {
        return dateOfRelease;
    }

    public void setDateOfRelease(Timestamp dateOfRelease) {
        this.dateOfRelease = dateOfRelease;
    }  
    
    public void setDateOfRelease(String date) throws ParseException{
        
        Date d = formatter.parse(date);
        setDateOfRelease(new Timestamp(d.getTime()));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (int) (this.patientId ^ (this.patientId >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PatientData other = (PatientData) obj;
        if (this.patientId != other.patientId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PatientData{" + "patientId=" + patientId + ", lastName=" + lastName + ", firstName=" + firstName + ", diagnosis=" + diagnosis + ", dateOfAdmission=" + dateOfAdmission + ", dateOfRelease=" + dateOfRelease + '}';
    }
}
