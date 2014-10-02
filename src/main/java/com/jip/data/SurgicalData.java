package com.jip.data;

import static com.jip.data.PatientData.formatter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

/**
 * @author Yu Xiao
 */
public class SurgicalData {

    private long id;
    private long patientId;
    private Timestamp dateOfSurgery;
    private String procedure;
    private BigDecimal roomFee;
    private BigDecimal surgeonFee;
    private BigDecimal supplies;
    private boolean update;

    public SurgicalData() {
        super();
        this.id = -1;
        this.patientId = -1;
        this.dateOfSurgery = null;
        this.procedure = null;
        this.roomFee = null;
        this.surgeonFee = null;
        this.supplies = null;
        this.update = false;
    }

    public SurgicalData(long id, long patientId, Timestamp dateOfSurgery, String procedure,
            BigDecimal roomFee, BigDecimal surgeonFee, BigDecimal supplies) {
        super();
        this.id = id;
        this.patientId = patientId;
        this.dateOfSurgery = dateOfSurgery;
        this.procedure = procedure;
        this.roomFee = roomFee;
        this.surgeonFee = surgeonFee;
        this.supplies = supplies;
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

    public Timestamp getDateOfSurgery() {
        return dateOfSurgery;
    }

    public void setDateOfSurgery(Timestamp dateOfSurgery) {
        this.dateOfSurgery = dateOfSurgery;
    }

    public void setDateOfSurgery(String date) throws ParseException {

        Date d = formatter.parse(date);
        setDateOfSurgery(new Timestamp(d.getTime()));
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public BigDecimal getRoomFee() {
        return roomFee;
    }

    public void setRoomFee(BigDecimal roomFee) {
        this.roomFee = roomFee;
    }

    public BigDecimal getSurgeonFee() {
        return surgeonFee;
    }

    public void setSurgeonFee(BigDecimal surgeonFee) {
        this.surgeonFee = surgeonFee;
    }

    public BigDecimal getSupplies() {
        return supplies;
    }

    public void setSupplies(BigDecimal supplies) {
        this.supplies = supplies;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final SurgicalData other = (SurgicalData) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ID=" + id
                + ",    Operating Room Fee=" + roomFee
                + ",    Surgeon's Fee=" + surgeonFee
                + ",    Surgical Supplies=" + supplies
                + ",        Daily Total=" + roomFee.add(surgeonFee).add(supplies)
                + "\n";
    }
}
