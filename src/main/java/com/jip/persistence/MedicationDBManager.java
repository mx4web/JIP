package com.jip.persistence;

import com.jip.data.MedicationData;
import com.jip.model.MedicationTableModel;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yu Xiao
 */
public class MedicationDBManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private MedicationTableModel mtm;
    private Properties prop;
//    private final String url = prop.getProperty("url");
//    private final String user = prop.getProperty("user");
//    private final String password = prop.getProperty("password");
    private final String url = "jdbc:mysql://localhost:3306/yuxiaojip";
    private final String user = "fish";
    private final String password = "fish";

    /**
     * Constructor that connects to the database
     *
     * @param mtm
     */
    public MedicationDBManager(MedicationTableModel mtm) {
        super();
        logger.info("MedicationDBManager instantiated");
        this.mtm = mtm;
    }

    /**
     * @param md
     * @return number of records
     * @throws SQLException
     */
    public int saveMedicationRecord(MedicationData md) throws SQLException {
        int retVal = 0;

        if (md != null) {
            if (md.getId() == -1) {
                retVal = addNewMedication(md);
            }
        }
        return retVal;
    }

    /**
     * Insert a new record
     *
     * @param md
     * @return number of records inserted
     * @throws java.sql.SQLException
     */
    private int addNewMedication(MedicationData md) throws SQLException {
        String sql = "INSERT INTO medication (patientId, dateOfMed, med, unitCost, units) VALUES (?,?,?,?,?)";
        int records = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql);) {
            pStatement.setLong(1, md.getPatientId());
            pStatement.setTimestamp(2, md.getDateOfMed());
            pStatement.setString(3, md.getMed());
            pStatement.setBigDecimal(4, md.getUnitCost());
            pStatement.setDouble(5, md.getUnits());

            records = pStatement.executeUpdate();
        }
        return records;
    }

    /**
     * If a record has changed then write it to the database
     */
    public void updateDB() {
        MedicationData md;

        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            // Cycle thru each row in the table
            for (int theRows = 0; theRows < mtm.getRowCount(); ++theRows) {
                if (mtm.getUpdateStatus(theRows)) {
                    md = mtm.getMedicationData(theRows);

                    if (md.getId() > 0) {
                        // update an existing record
                        updateMedication(md);
                    }
                    // Reset row to not requiring an update
                    mtm.clearUpdate(theRows);
                }
            }
        } catch (SQLException sqlex) {
            logger.error("Error updating database", sqlex);
        }
    }

    /**
     * Update an existing surgical record using ID in the Where clause
     *
     * @param sd
     * @return number of records updated
     * @throws java.sql.SQLException
     */
    private int updateMedication(MedicationData md) throws SQLException {
        String sql = "UPDATE medication SET DateOfMed = ?, Med = ?, UnitCost = ?, Units = ?  WHERE ID = ?";
        int records = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql);) {

            pStatement.setTimestamp(1, md.getDateOfMed());
            pStatement.setString(2, md.getMed());
            pStatement.setBigDecimal(3, md.getUnitCost());
            pStatement.setDouble(4, md.getUnits());
            pStatement.setLong(5, md.getId());

            records = pStatement.executeUpdate();
        }
        return records;
    }

    /**
     * Delete the selected row from the database and call on the table model to
     * remove that row
     *
     * @param selectedRow
     */
    public int deleteRow(int selectedRow) {
        int result = 0;
        String deleteSQL = "DELETE FROM medication WHERE ID = ?";

        if (selectedRow > -1) {
            try (Connection connection = DriverManager.getConnection(url, user, password);
                    PreparedStatement ps = connection.prepareStatement(deleteSQL);) {

                ps.setLong(1, mtm.getMedicationData(selectedRow).getId());
                result = ps.executeUpdate();
                mtm.deleteRow(selectedRow);
            } catch (SQLException ex) {
                logger.error("Error deleting row", ex);
            }
        }
        logger.info("Records deleted: " + result);
        return result;
    }

    /**
     * Fill the table model
     *
     * @param patientID
     * @return true if data is available
     */
    public void fillTableModel(long patientID) throws SQLException {

        String sql = "Select * from medication where PatientID = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql);) {
            pStatement.setLong(1, patientID);
            try (ResultSet resultSet = pStatement.executeQuery()) {

                ResultSetMetaData rsmd = resultSet.getMetaData();
                mtm.loadColumnNames(rsmd);
                mtm.loadData(resultSet);
            }
        }
    }
    
/**
     * Get a string record that have the specified patientID
     * @param patientID
     */
    public String retrieveMedication(long patientID) throws SQLException {

        List<MedicationData> mds = new ArrayList<>();
        String sql = "select * from medication where patientID = ?";
        StringBuilder sb = new StringBuilder();

        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql);) {
            pStatement.setLong(1, patientID);
            try (ResultSet resultSet = pStatement.executeQuery()) {

                while (resultSet.next()) {
                    mds.add(getMedicationRecord(resultSet));
                }
                
                for (MedicationData md : mds) {
                    sb.append(md.toString()).append("\n");

                }
            }
        }
        return sb.toString();
    }

    private MedicationData getMedicationRecord(ResultSet resultSet) throws SQLException {
        MedicationData md = new MedicationData();
        md.setId(resultSet.getLong("ID"));
        md.setPatientId(resultSet.getLong("PatientID"));
        md.setDateOfMed(resultSet.getTimestamp("DateOfMed"));
        md.setMed(resultSet.getString("Med"));
        md.setUnitCost(resultSet.getBigDecimal("UnitCost"));
        md.setUnits(resultSet.getDouble("Units"));
        
        return md;
    }
    
        public BigDecimal getMedicationTotalCharge(long patientID) throws SQLException {
        
        String sql = "select * from medication where patientID = ?";
        BigDecimal total = new BigDecimal(0);
        
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql);) {
            pStatement.setLong(1, patientID);
            try (ResultSet resultSet = pStatement.executeQuery()) {
                
                while (resultSet.next()) {
                    total = total.add(resultSet.getBigDecimal("UnitCost").multiply( new BigDecimal(resultSet.getDouble("Units"))));                   
                }                
            }
        }
        
        return total;   
    }
}
