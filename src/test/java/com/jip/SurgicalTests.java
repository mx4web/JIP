package com.jip;

import com.jip.data.SurgicalData;
import com.jip.persistence.SurgicalDBManager;
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
public class SurgicalTests {

    private SurgicalDBManager sdbm;
    
    @Before
    public void setUp() {

        sdbm = new SurgicalDBManager(null);
    }

    @Test
    public void addNewSurgicalTest() throws ParseException, SQLException {
        SurgicalData sd = new SurgicalData();
        sd.setPatientId(1);
        sd.setProcedure("stomache surgical");
        sd.setRoomFee(new BigDecimal(100));
        sd.setSurgeonFee(new BigDecimal(300));
        sd.setSupplies(new BigDecimal(200));
        assertEquals("1 record added", 1,sdbm.saveSurgicalRecord(sd));
    }
}

