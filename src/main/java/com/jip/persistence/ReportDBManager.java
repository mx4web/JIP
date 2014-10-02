package com.jip.persistence;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yu Xiao
 */
public class ReportDBManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private InpatientDBManager ipdbm;
    private SurgicalDBManager sdbm;
    private MedicationDBManager mdbm;
    
    private Properties prop;
//    private final String url = prop.getProperty("url");
//    private final String user = prop.getProperty("user");
//    private final String password = prop.getProperty("password");
    private final String url = "jdbc:mysql://localhost:3306/yuxiaojip";
    private final String user = "fish";
    private final String password = "fish";

    /**
     * Constructor that connects to the database
     */
    public ReportDBManager() {
        super();
        logger.info("ReportDBManager instantiated");
        ipdbm = new InpatientDBManager(null);
        sdbm = new SurgicalDBManager(null);
        mdbm = new MedicationDBManager(null);
    }

    public String getInpatientRecords(long patientID) throws SQLException{       
       return ipdbm.retrieveInpatient(patientID);
    }
    
    public BigDecimal getInpatientTotalCharge(long patientID) throws SQLException{
        return ipdbm.getInpatientTotalCharge(patientID);
    }
    
    public String getSurgicalRecords(long patientID) throws SQLException{
        return sdbm.retrieveSurgical(patientID);
    }
    
     public BigDecimal getSurgicalTotalCharge(long patientID) throws SQLException{
        return sdbm.getSurgicalTotalCharge(patientID);
    }
    
     public String getMedicationRecords(long patientID) throws SQLException{
         return mdbm.retrieveMedication(patientID);
    }
     
      public BigDecimal getMedicationTotalCharge(long patientID) throws SQLException{
        return mdbm.getMedicationTotalCharge(patientID);
    }
          
}
