package com.jip.presentation;

import com.jip.data.MedicationData;
import com.jip.formatters.regex.RegexFormatter;
import com.jip.model.MedicationTableModel;
import com.jip.persistence.MedicationDBManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.slf4j.LoggerFactory;

/**
 * @author Yu Xiao
 */
public class MedicationPanel extends javax.swing.JPanel implements ActionListener {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private MedicationData currentMd;
    private MedicationDBManager mdbm;
    private MedicationTableModel mtm;
    private int selectedRow = -1;
    
    String patientIDRegEx = "^[1-9]+$"; //digits only
    String numberRegEx = "(?<=^| )\\d+(\\.\\d+)?(?=$| )|(?<=^| )\\.\\d+(?=$| )";// allows 1, 1.1 and .1

    /**
     * Creates new form SurgicalPanel
     */
    public MedicationPanel() {
        super();
        mtm = new MedicationTableModel();
        mdbm = new MedicationDBManager(mtm);
        currentMd = new MedicationData();

        displayTable(0L);
        initComponents();
        initialTable();
    }

    private void initialTable() {
        // Select single, multiple, or multiple interval
        medicationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Configure to listen for row selection
        ListSelectionModel rowSM = medicationTable.getSelectionModel();
        rowSM.addListSelectionListener(new MedicationPanel.RowListener());
    }

    private void displayTable(long pid) {
        try {
            mdbm.fillTableModel(pid);
        } catch (SQLException ex) {
            log.error("SQL Exception", ex.getMessage());
        }
    }

    /**
     * Clear the display and create a blank patient object
     */
    public void clearMedication() {

        tfPID.setText("");
        tfDateMed.setText("");
        tfMed.setText("");
        tfUnitCost.setText("");
        tfUnits.setText("");

    }

    private void saveMedication() {
        try {
            int records = mdbm.saveMedicationRecord(getMedication());
            if (records == 0) {
                log.warn("There were no records saved.");
            }
            if (records > 1) {
                log.warn("More than one record was saved: " + records);
            }

        } catch (SQLException sqlex) {
            log.error("SQL Exception", sqlex.getMessage());
        }
    }

    /**
     * Fill the current inpatient object and return it
     *
     * @return the medication object
     */
    public MedicationData getMedication() {

        currentMd.setPatientId(getPatientIDFromForm());
        try {
            currentMd.setDateOfMed((tfDateMed.getText()));
        } catch (ParseException ex) {
            Logger.getLogger(InpatientPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        currentMd.setMed(tfMed.getText());
        currentMd.setUnitCost(new BigDecimal(tfUnitCost.getText()));
        currentMd.setUnits(Long.parseLong(tfUnits.getText()));

        return currentMd;
    }

    /**
     * Get the patientID for the table
     *
     * @return
     */
    public long getPatientIDFromForm() {

        return Long.parseLong(tfPID.getText());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel5 = new javax.swing.JLabel();
        tfPID = new javax.swing.JFormattedTextField(new RegexFormatter(patientIDRegEx));
        tfDateMed = new javax.swing.JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        tfMed = new javax.swing.JTextField();
        tfUnitCost = new javax.swing.JFormattedTextField(new RegexFormatter(numberRegEx));
        tfUnits = new javax.swing.JFormattedTextField(new RegexFormatter(numberRegEx));
        javax.swing.JLabel jLabel8 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        javax.swing.JSeparator jSeparator1 = new javax.swing.JSeparator();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        medicationTable = new javax.swing.JTable();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(827, 644));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("PATIENT ID");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("DATE OF MEDICATION");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("COST PER UNIT");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("NUMBER OF UNITS");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("MEDICATION");

        tfPID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tfDateMed.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tfMed.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tfUnitCost.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tfUnits.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel8.setText("(yyyy-mm-dd)");

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction(evt);
            }
        });

        btnClear.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(tfMed))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(tfDateMed, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(29, 29, 29)
                                .addComponent(tfPID)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfUnits)
                            .addComponent(tfUnitCost, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(35, 35, 35))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(299, 299, 299)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(tfPID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfUnitCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(tfDateMed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfUnits, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tfMed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnClear))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        medicationTable.setModel(mtm);
        jScrollPane1.setViewportView(medicationTable);

        btnUpdate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 23, Short.MAX_VALUE))
                    .addComponent(jSeparator1))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(315, 315, 315)
                .addComponent(btnUpdate)
                .addGap(28, 28, 28)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete))
                .addContainerGap(145, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction
                JButton source = (JButton) (evt.getSource());
        switch (source.getActionCommand()) {
            case "Save":
                saveMedication();
                displayTable(getPatientIDFromForm());
                break;
            case "Clear":
                clearMedication();
                break;
            case "Delete":
                mdbm.deleteRow(selectedRow);                
                break;
            case "Update":
                mdbm.updateDB();
                break;
        }
    }//GEN-LAST:event_buttonAction

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton btnClear;
    javax.swing.JButton btnDelete;
    javax.swing.JButton btnSave;
    javax.swing.JButton btnUpdate;
    javax.swing.JTable medicationTable;
    javax.swing.JFormattedTextField tfDateMed;
    javax.swing.JTextField tfMed;
    javax.swing.JTextField tfPID;
    javax.swing.JTextField tfUnitCost;
    javax.swing.JTextField tfUnits;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
    }
    
     /**
     * Inner class that listens for row selection events
     */
    class RowListener implements ListSelectionListener {
        /*
         * (non-Javadoc)
         * 
         * @see
         * javax.swing.event.ListSelectionListener#valueChanged(javax.swing.
         * event.ListSelectionEvent)
         */

        @Override
        public void valueChanged(ListSelectionEvent e) {
            // Ignore extra messages.
            if (e.getValueIsAdjusting()) {
                return;
            }

            ListSelectionModel lsm = (ListSelectionModel) e.getSource();
            if (!lsm.isSelectionEmpty()) {
                selectedRow = lsm.getMinSelectionIndex();
            }
        }
    }
}
