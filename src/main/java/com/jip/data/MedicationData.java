package com.jip.data;

import static com.jip.data.PatientData.formatter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

/**
 * @author Yu Xiao
 */
public class MedicationData {

    private long id;
    private long patientId;
    private Timestamp dateOfMed;
    private String med;
    private BigDecimal unitCost;
    private double units;
    private boolean update;

    public MedicationData() {
        super();
        this.id = -1;
        this.patientId = -1;
        this.dateOfMed = null;
        this.med = null;
        this.unitCost = null;
        this.units = 0;
        this.update = false;
    }

    public MedicationData(long id, long patientId, Timestamp dateOfMed, String med,
            BigDecimal unitCost, double units) {
        super();
        this.id = id;
        this.patientId = patientId;
        this.dateOfMed = dateOfMed;
        this.med = med;
        this.unitCost = unitCost;
        this.units = units;
        this.update = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public Timestamp getDateOfMed() {
        return dateOfMed;
    }

    public void setDateOfMed(Timestamp dateOfMed) {
        this.dateOfMed = dateOfMed;
    }
    
    public void setDateOfMed(String date) throws ParseException{
        
        Date d = formatter.parse(date);
        setDateOfMed(new Timestamp(d.getTime()));
    }

    public String getMed() {
        return med;
    }

    public void setMed(String med) {
        this.med = med;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public double getUnits() {
        return units;
    }

    public void setUnits(double units) {
        this.units = units;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final MedicationData other = (MedicationData) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return 
               "ID=" + id               
                + ",    Cost Per Unit=" + unitCost 
                + ",    Number Of Units=" + units 
                + ",        Daily Total=" + unitCost.multiply(new BigDecimal(units))
                + "\n";
    }  
}
