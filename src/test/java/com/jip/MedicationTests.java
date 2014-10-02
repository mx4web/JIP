package com.jip;

import com.jip.data.MedicationData;
import com.jip.persistence.MedicationDBManager;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 *
 * @author Yu Xiao
 */
public class MedicationTests {

    private MedicationDBManager mdbm;

    @Before
    public void setUp() {

        mdbm = new MedicationDBManager(null);
    }

    @Test
    public void addNewInpatientTest() throws ParseException, SQLException {
        MedicationData md = new MedicationData();
        md.setPatientId(1);
        md.setDateOfMed("2013-12-3");
        md.setMed("fever capsule");
        md.setUnitCost(new BigDecimal(2));
        md.setUnits(5);
        assertEquals("1 record added", 1, mdbm.saveMedicationRecord(md));              
    }
}

