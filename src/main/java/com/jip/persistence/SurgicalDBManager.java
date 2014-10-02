package com.jip.persistence;

import com.jip.data.SurgicalData;
import com.jip.model.SurgicalTableModel;
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
public class SurgicalDBManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private SurgicalTableModel stm;
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
     * @param stm
     */
    public SurgicalDBManager(SurgicalTableModel stm) {
        super();
        logger.info("SurgicalDBManager instantiated");
        this.stm = stm;
    }

    /**
     * @param sd
     * @return number of records
     * @throws SQLException
     */
    public int saveSurgicalRecord(SurgicalData sd) throws SQLException {
        int retVal = 0;

        if (sd != null) {
            if (sd.getId() == -1) {
                retVal = addNewSurgical(sd);
            }
        }
        return retVal;
    }

    /**
     * Insert a new record
     *
     * @param sd
     * @return number of records inserted
     * @throws java.sql.SQLException
     */
    private int addNewSurgical(SurgicalData sd) throws SQLException {
        String sql = "INSERT INTO surgical (patientId, dateOfSurgery, surgicalProcedure, roomFee, surgeonFee, supplies) VALUES (?,?,?,?,?,?)";
        int records = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql);) {
            pStatement.setLong(1, sd.getPatientId());
            pStatement.setTimestamp(2, sd.getDateOfSurgery());
            pStatement.setString(3, sd.getProcedure());
            pStatement.setBigDecimal(4, sd.getRoomFee());
            pStatement.setBigDecimal(5, sd.getSurgeonFee());
            pStatement.setBigDecimal(6, sd.getSupplies());

            records = pStatement.executeUpdate();
        }
        return records;
    }

    /**
     * If a record has changed then write it to the database
     */
    public void updateDB() {
        SurgicalData sd;

        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            // Cycle thru each row in the table
            for (int theRows = 0; theRows < stm.getRowCount(); ++theRows) {
                if (stm.getUpdateStatus(theRows)) {
                    sd = stm.getSurgicalData(theRows);

                    if (sd.getId() > 0) {
                        // update an existing record
                        updateSurgical(sd);
                    }
                    // Reset row to not requiring an update
                    stm.clearUpdate(theRows);
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
    private int updateSurgical(SurgicalData sd) throws SQLException {
        String sql = "UPDATE surgical SET DateOfSurgery = ?, surgicalProcedure = ?, RoomFee = ?, SurgeonFee = ?, Supplies = ?  WHERE ID = ?";
        int records = 0;
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql);) {

            pStatement.setTimestamp(1, sd.getDateOfSurgery());
            pStatement.setString(2, sd.getProcedure());
            pStatement.setBigDecimal(3, sd.getRoomFee());
            pStatement.setBigDecimal(4, sd.getSurgeonFee());
            pStatement.setBigDecimal(5, sd.getSupplies());
            pStatement.setLong(6, sd.getId());

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
        String deleteSQL = "DELETE FROM surgical WHERE ID = ?";

        if (selectedRow > -1) {
            try (Connection connection = DriverManager.getConnection(url, user, password);
                    PreparedStatement ps = connection.prepareStatement(deleteSQL);) {

                ps.setLong(1, stm.getSurgicalData(selectedRow).getId());
                result = ps.executeUpdate();
                stm.deleteRow(selectedRow);
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

        String sql = "Select * from surgical where PatientID = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql);) {
            pStatement.setLong(1, patientID);
            try (ResultSet resultSet = pStatement.executeQuery()) {

                ResultSetMetaData rsmd = resultSet.getMetaData();
                stm.loadColumnNames(rsmd);
                stm.loadData(resultSet);
            }
        }
    }

    /**
     * Get a string record that have the specified patientID
     *
     * @param patientID
     */
    public String retrieveSurgical(long patientID) throws SQLException {

        List<SurgicalData> sds = new ArrayList<>();
        String sql = "select * from surgical where patientID = ?";
        StringBuilder sb = new StringBuilder();

        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql);) {
            pStatement.setLong(1, patientID);
            try (ResultSet resultSet = pStatement.executeQuery()) {

                while (resultSet.next()) {
                    sds.add(getSurgicalRecord(resultSet));
                }

                for (SurgicalData sd : sds) {
                    sb.append(sd.toString()).append("\n");

                }
            }
        }
        return sb.toString();
    }

    private SurgicalData getSurgicalRecord(ResultSet resultSet) throws SQLException {
        SurgicalData sd = new SurgicalData();
        sd.setId(resultSet.getLong("ID"));
        sd.setPatientId(resultSet.getLong("PatientID"));
        sd.setDateOfSurgery(resultSet.getTimestamp("DateOfSurgery"));
        sd.setProcedure(resultSet.getString("SurgicalProcedure"));
        sd.setRoomFee(resultSet.getBigDecimal("RoomFee"));
        sd.setSurgeonFee(resultSet.getBigDecimal("SurgeonFee"));
        sd.setSupplies(resultSet.getBigDecimal("Supplies"));

        return sd;
    }

    public BigDecimal getSurgicalTotalCharge(long patientID) throws SQLException {

        String sql = "select * from surgical where patientID = ?";
        BigDecimal total = new BigDecimal(0);

        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(sql);) {
            pStatement.setLong(1, patientID);
            try (ResultSet resultSet = pStatement.executeQuery()) {

                while (resultSet.next()) {
                    total = total.add(resultSet.getBigDecimal("RoomFee")).add(resultSet.getBigDecimal("SurgeonFee")).add(resultSet.getBigDecimal("Supplies"));
                }
            }
        }

        return total;
    }
}
