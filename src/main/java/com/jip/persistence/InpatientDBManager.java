package com.jip.persistence;

import com.jip.data.InpatientData;
import com.jip.model.InpatientTableModel;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Database Manager for inpatients
 *
 * @author Yu Xiao
 *
 */
public class InpatientDBManager {

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
     *
     * @param iptm
     */
    public InpatientDBManager(InpatientTableModel iptm) {
        super();
        logger.info("InpatientDBManager instantiated");
        this.iptm = iptm;
    }
   
    /**
     *
     * @param ipd
     * @return number of records
     * @throws SQLException
     */
    public int saveInpatientRecord(InpatientData ipd) throws SQLException {
        int retVal = 0;

        if (ipd != null) {
            if (ipd.getId() == -1) {
                retVal = addNewInpatient(ipd);
            } 
        }
        return retVal;
    }

    /**
     * Insert a new record
     *
     * @param ipd
     * @return number of records inserted
     * @throws java.sql.SQLException
     */
    private int addNewInpatient(InpatientData ipd) throws SQLException {
        String sql = "INSERT INTO inpatients (patientId, dateOfStay, roomNumber, dailyRate, supplies, services) VALUES (?,?,?,?,?,?)";
        int records = 0;
        try (Connection connection = DriverManager.getConnection(url, user,
                password);
                PreparedStatement pStatement = connection.prepareStatement(sql);) {
            pStatement.setLong(1, ipd.getPatientId());
            pStatement.setTimestamp(2, ipd.getDateOfStay());
            pStatement.setString(3, ipd.getRoomNumber());
            pStatement.setBigDecimal(4, ipd.getDailyRate());
            pStatement.setBigDecimal(5, ipd.getSupplies());
            pStatement.setBigDecimal(6, ipd.getServices());

            records = pStatement.executeUpdate();
        }
        return records;
    }

    /**
     * Update an existing inpatient using ID in the Where clause
     *
     * @param ipd
     * @return number of records updated
     * @throws java.sql.SQLException
     */
    private int updateInpatient(InpatientData ipd) throws SQLException {
        String sql = "UPDATE inpatients SET DateOfStay = ?, RoomNumber = ?, DailyRate = ?, Supplies = ?, Services = ?  WHERE ID = ?";
        int records = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql);) {

            pStatement.setTimestamp(1, ipd.getDateOfStay());
            pStatement.setString(2, ipd.getRoomNumber());
            pStatement.setBigDecimal(3, ipd.getDailyRate());
            pStatement.setBigDecimal(4, ipd.getSupplies());
            pStatement.setBigDecimal(5, ipd.getServices());
            pStatement.setLong(6, ipd.getId());

            records = pStatement.executeUpdate();
        }
        return records;
    }

    /**
     * Fill the table model
     *
     * @param patientID
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

    /**
     * If a record has changed then write it to the database
     */
    public void updateDB() {
        InpatientData ipd;

        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            // Cycle thru each row in the table
            for (int theRows = 0; theRows < iptm.getRowCount(); ++theRows) {
                if (iptm.getUpdateStatus(theRows)) {
                    
                    ipd = iptm.getInpatientData(theRows);

                    if (ipd.getId() > 0) {
                        // update an existing record
                        System.out.println("the id of updated record is " + ipd.getId());
                        updateInpatient(ipd);
                    }
                    // Reset row to not requiring an update
                    iptm.clearUpdate(theRows);
                }
            }
        } catch (SQLException sqlex) {
            logger.error("Error updating database", sqlex);
        }
    }

    /**
     * Delete the selected row from the database and call on the table model to
     * remove that row
     *
     * @param selectedRow
     */
    public int deleteRow(int selectedRow) {
        
        int result = 0;
        String deleteSQL = "DELETE FROM inpatients WHERE ID = ?";

        if (selectedRow > -1) {
            try (Connection connection = DriverManager.getConnection(url, user, password);
                    PreparedStatement ps = connection.prepareStatement(deleteSQL);) {

                ps.setLong(1, iptm.getInpatientData(selectedRow).getId());
                result = ps.executeUpdate();
                iptm.deleteRow(selectedRow);
            } catch (SQLException ex) {
                logger.error("Error deleting row", ex);
            }
        }
        logger.info("Records deleted: " + result);
        return result;
    }
    
     /**
     * Get a string record that have the specified patientID
     *
     * @param patientID
     */
    public String retrieveInpatient(long patientID) throws SQLException {

        List<InpatientData> ipds = new ArrayList<>();
        String sql = "select * from inpatients where patientID = ?";
        StringBuilder sb = new StringBuilder();

        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql);) {
            pStatement.setLong(1, patientID);
            try (ResultSet resultSet = pStatement.executeQuery()) {

                while (resultSet.next()) {

                    InpatientData ipd = new InpatientData();
                    ipd.setId(resultSet.getLong("ID"));
                    ipd.setPatientId(resultSet.getLong("PatientID"));
                    ipd.setDateOfStay(resultSet.getTimestamp("DateOfStay"));
                    ipd.setRoomNumber(resultSet.getString("RoomNumber"));
                    ipd.setDailyRate(resultSet.getBigDecimal("DailyRate"));
                    ipd.setSupplies(resultSet.getBigDecimal("Supplies"));
                    ipd.setServices(resultSet.getBigDecimal("Services"));
                    ipds.add(ipd);
                }

                for (InpatientData ipd : ipds) {
                    sb.append(ipd.toString()).append("\n");
                }
            }
        }
        return sb.toString();
    }

    public BigDecimal getInpatientTotalCharge(long patientID) throws SQLException {

        String sql = "select * from inpatients where patientID = ?";
        BigDecimal total = new BigDecimal(0);

        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql);) {
            pStatement.setLong(1, patientID);
            try (ResultSet resultSet = pStatement.executeQuery()) {

                while (resultSet.next()) {
                    total = total.add(resultSet.getBigDecimal("DailyRate")).add(resultSet.getBigDecimal("Supplies")).add(resultSet.getBigDecimal("Services"));
                }
            }
        }

        return total;
    }

}
