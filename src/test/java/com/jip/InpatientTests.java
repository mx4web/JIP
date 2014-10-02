package com.jip;

import com.jip.data.InpatientData;
import com.jip.persistence.InpatientDBManager;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Yu Xiao
 */
public class InpatientTests {
     
    private InpatientDBManager ipdbm;
    
    @Before
    public void setUp() {

        ipdbm = new InpatientDBManager(null);        
    }

    @Test
    public void addNewInpatientTest() throws SQLException, ParseException{
        InpatientData ipd = new InpatientData();
        ipd.setPatientId(1);
        ipd.setDateOfStay("2013-12-2");
        ipd.setRoomNumber("V8");
        ipd.setDailyRate(new BigDecimal(12));
        ipd.setSupplies(new BigDecimal(15));
        ipd.setServices(new BigDecimal(20));
        assertEquals("1 record added", 1, ipdbm.saveInpatientRecord(ipd));
    }
}

