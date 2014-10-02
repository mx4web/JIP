package com.jip.data;

import static com.jip.data.PatientData.formatter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author Yu Xiao
 */
public class InpatientData {

    private long id;
    private long patientId;
    private Timestamp dateOfStay;
    private String roomNumber;
    private BigDecimal dailyRate;
    private BigDecimal supplies;
    private BigDecimal services;
    private boolean update;

    public InpatientData() {
        super();
        this.id = -1;
        this.patientId = -1;
        this.dateOfStay = null;
        this.roomNumber = null;
        this.dailyRate = null;
        this.supplies = null;
        this.services = null;
        this.update = false;
    }

    public InpatientData(long id, long patientId, Timestamp dateOfStay, String roomNumber,
            BigDecimal dailyRate, BigDecimal supplies, BigDecimal services) {
        super();
        this.id = id;
        this.patientId = patientId;
        this.dateOfStay = dateOfStay;
        this.roomNumber = roomNumber;
        this.dailyRate = dailyRate;
        this.supplies = supplies;
        this.services = services;
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

    public Timestamp getDateOfStay() {
        return dateOfStay;
    }

    public void setDateOfStay(Timestamp dateOfStay) {
        this.dateOfStay = dateOfStay;
    }

    public void setDateOfStay(String date) throws ParseException {

        Date d = formatter.parse(date);
        setDateOfStay(new Timestamp(d.getTime()));
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public BigDecimal getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(BigDecimal dailyRate) {
        this.dailyRate = dailyRate;
    }

    public BigDecimal getSupplies() {
        return supplies;
    }

    public void setSupplies(BigDecimal supplies) {
        this.supplies = supplies;
    }

    public BigDecimal getServices() {
        return services;
    }

    public void setServices(BigDecimal services) {
        this.services = services;
    }

    public boolean getUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (int) (this.id ^ (this.id >>> 32));

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
        final InpatientData other = (InpatientData) obj;
        if (this.id != other.id) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "ID=" + id               
                + ",    Daily Room Rate=" + dailyRate 
                + ",    Room Supplies=" + supplies 
                + ",    Room Services=" + services 
                + ",        Daily Total=" + dailyRate.add(supplies).add(services)               
                + "\n";
    }
}
