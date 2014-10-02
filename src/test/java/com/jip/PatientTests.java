package com.jip;

import com.jip.data.PatientData;
import com.jip.persistence.PatientDBManager;
import java.sql.SQLException;
import java.text.ParseException;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Yu Xiao
 */
public class PatientTests {

    private PatientDBManager pdbm;

    @Before
    public void setUp() {

        pdbm = new PatientDBManager();
    }

    @Test(timeout = 1000)
    public void addNewPatientTest() throws ParseException, SQLException {
        PatientData pd = new PatientData();
        int size1 = pdbm.getAllPatients().size();
        pd.setLastName("TestLastName");
        pd.setFirstName("TestFirstName");
        pd.setDiagnosis("flu");
        pd.setDateOfAdmission("2013-12-1");
        pd.setDateOfRelease("2013-12-2");
        pdbm.savePatient(pd);
        int size2 = pdbm.getAllPatients().size();

        assertEquals("1 record added successfully", size1 + 1, size2);
    }

    @Test(timeout = 1000)
    public void updatePatientTest() throws ParseException, SQLException {
        PatientData pd = pdbm.searchByPatientID(1).get(0);
        pd.setFirstName("Sherin");
        pd.setDateOfRelease("2013-5-8");
        pdbm.savePatient(pd);

        assertEquals(pd, pdbm.searchByPatientID(1).get(0));
    }
}
