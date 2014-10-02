package com.jip.persistence;

import com.jip.data.PatientData;
import com.jip.model.InpatientTableModel;
import java.sql.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Database Manager for Patients
 *
 * @author Yu Xiao
 */
public class PatientDBManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private InpatientTableModel iptm;
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
    public PatientDBManager() {
        super();
        logger.info("PatientDBManager instantiated");

    }

    /**
     * Get a list of all records
     *
     * @return the list of records
     * @throws java.sql.SQLException
     */
    public List<PatientData> getAllPatients() throws SQLException {

        List<PatientData> pds = new ArrayList<>();

        String sql = "select * from patients";

        try (Connection connection = DriverManager.getConnection(url, user,
                password);
                PreparedStatement pStatement = connection.prepareStatement(sql);
                ResultSet resultSet = pStatement.executeQuery()) {

            while (resultSet.next()) {
                pds.add(getPatientRecords(resultSet));
            }
        }
        return pds;
    }

    /**
     * Get a list of records that have the specified patientID
     *
     * @param patientID
     * @return the list of records
     * @throws java.sql.SQLException
     */
    public List<PatientData> searchByPatientID(long patientID) throws SQLException {

        List<PatientData> pds = new ArrayList<>();
        String sql = "select * from patients where patientID = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql);) {
            pStatement.setLong(1, patientID);
            try (ResultSet resultSet = pStatement.executeQuery()) {

                while (resultSet.next()) {

                    pds.add(getPatientRecords(resultSet));
                }
            }
        }
        return pds;
    }

    private PatientData getPatientRecords(ResultSet resultSet) throws SQLException {

        PatientData pd = new PatientData();
        pd.setPatientId(resultSet.getLong("PatientID"));
        pd.setLastName(resultSet.getString("LastName"));
        pd.setFirstName(resultSet.getString("FirstName"));
        pd.setDiagnosis(resultSet.getString("Diagnosis"));
        pd.setDateOfAdmission(resultSet.getTimestamp("DateOfAdmission"));
        pd.setDateOfRelease(resultSet.getTimestamp("DateOfRelease"));

        return pd;
    }

    /**
     *
     * @param pd
     * @return number of records
     * @throws SQLException
     */
    public int savePatient(PatientData pd) throws SQLException {
        int retVal = 0;

        if (pd != null) {
            if (pd.getPatientId() == -1) {
                retVal = addNewPatient(pd);
            } else {
                retVal = updatePatient(pd);
            }
        }
        return retVal;
    }

    /**
     * Insert a new record
     *
     * @param pd
     * @return number of records inserted
     * @throws java.sql.SQLException
     */
    public int addNewPatient(PatientData pd) throws SQLException {
        String sql = "INSERT INTO patients (lastName, firstName, diagnosis, dateOfAdmission, dateOfRelease) VALUES (?,?,?,?,?)";

        int records = 0;
        try (Connection connection = DriverManager.getConnection(url, user,
                password);
                PreparedStatement pStatement = connection.prepareStatement(sql);) {
            pStatement.setString(1, pd.getLastName());
            pStatement.setString(2, pd.getFirstName());
            pStatement.setString(3, pd.getDiagnosis());
            pStatement.setTimestamp(4, pd.getDateOfAdmission());
            pStatement.setTimestamp(5, pd.getDateOfRelease());

            records = pStatement.executeUpdate();
        }
        return records;
    }

    /**
     * Update an existing patient using patientID in the Where clause
     *
     * @param pd
     * @return number of records updated
     * @throws java.sql.SQLException
     */
    public int updatePatient(PatientData pd) throws SQLException {
        String sql = "UPDATE patients SET LastName = ?, FirstName = ?, Diagnosis = ?, DateOfAdmission = ?, DateOfRelease = ?  WHERE PatientID = ?";
        int records = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql);) {
            pStatement.setString(1, pd.getLastName());
            pStatement.setString(2, pd.getFirstName());
            pStatement.setString(3, pd.getDiagnosis());
            pStatement.setTimestamp(4, pd.getDateOfAdmission());
            pStatement.setTimestamp(5, pd.getDateOfRelease());
            pStatement.setLong(6, pd.getPatientId());
            records = pStatement.executeUpdate();
        }
        return records;
    }

    /**
     * Fill the table model
     *
     * @param patientID
     * @return true if data is available
     */
    public void fillTableModel(long patientID) throws SQLException {

        String sql = "Select * from inpatients where PatientID = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql);) {
            pStatement.setLong(1, patientID);
            try (ResultSet resultSet = pStatement.executeQuery()) {

                ResultSetMetaData rsmd = resultSet.getMetaData();
                iptm.loadColumnNames(rsmd);
                iptm.loadData(resultSet);
            }
        }
    }
}
