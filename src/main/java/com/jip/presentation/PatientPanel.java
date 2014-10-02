package com.jip.presentation;

import com.jip.data.PatientData;
import com.jip.formatters.regex.RegexFormatter;
import com.jip.model.InpatientTableModel;
import com.jip.model.MedicationTableModel;
import com.jip.model.SurgicalTableModel;
import com.jip.persistence.InpatientDBManager;
import com.jip.persistence.MedicationDBManager;
import com.jip.persistence.PatientDBManager;
import com.jip.persistence.SurgicalDBManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Yu Xiao
 */
public class PatientPanel extends javax.swing.JPanel {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final ResourceBundle messages = ResourceBundle.getBundle("internationalization/MessagesBundle", Locale.getDefault());
    private List<PatientData> pds = null;
    private PatientDBManager pdbm;
    private InpatientDBManager ipdbm;
    private SurgicalDBManager sdbm;
    private MedicationDBManager mdbm;
    private InpatientTableModel iptm;
    private SurgicalTableModel stm;
    private MedicationTableModel mtm;
    private int listPosition = 0;
    private PatientData currentPd = null;

    String patientIDRegEx = "^[1-9]+$"; //digits only
    String nameRegEx = "^[\\p{L}\\s'.-]+$"; //any Unicode letter, white spabe, apostrophe, point, hyphen

    /**
     * Creates new form PatientPanel
     */
    public PatientPanel() {
        super();
        iptm = new InpatientTableModel();
        stm = new SurgicalTableModel();
        mtm = new MedicationTableModel();
        pdbm = new PatientDBManager();
        sdbm = new SurgicalDBManager(stm);
        ipdbm = new InpatientDBManager(iptm);
        mdbm = new MedicationDBManager(mtm);

        displayTables(1L);
        initComponents();
        loadAllPatients();
    }

    /**
     * Clear the display and create a blank patient object
     */
    public void clearPatient() {
        patientIDTF.setText("");
        lastNameTF.setText("");
        firstNameTF.setText("");
        diagnosisTF.setText("");
        formattedTextFieldDateOfAdmission.setText("");
        formattedTextFieldDateOfRelease.setText("");
    }

    private void savePatient() {
        try {
            int records = pdbm.savePatient(getPatient());
            if (records == 0) {
                log.warn("There were no records saved.");
            }
            if (records > 1) {
                log.warn("More than one record was saved: " + records);
            }
            loadAllPatients();
        } catch (SQLException sqlex) {
            log.error("SQL Exception", sqlex.getMessage());
            //sqlex.printStackTrace();
        }
    }

    /**
     * Get the patientID for a search
     */
    public long getPatientIDFromForm() {

        return Long.parseLong(patientIDTF.getText());
    }

    private void searchPatient() {
        try {
            pds = pdbm.searchByPatientID(getPatientIDFromForm());
            if (pds != null && pds.size() > 0) {
                displayPatient(pds.get(listPosition));
                displayTables(pds.get(listPosition).getPatientId());
            }
        } catch (SQLException sqlex) {
            log.error("SQL Exception", sqlex.getMessage());
        }
    }

    /**
     * Display a patient record and keep a reference to the object
     *
     * @param pd
     */
    public void displayPatient(PatientData pd) {
        currentPd = pd;
        patientIDTF.setText(currentPd.getPatientId() + "");
        lastNameTF.setText(currentPd.getLastName());
        firstNameTF.setText(currentPd.getFirstName());
        diagnosisTF.setText(currentPd.getDiagnosis());
        formattedTextFieldDateOfAdmission.setText(new SimpleDateFormat("yyyy-MM-dd").format(currentPd.getDateOfAdmission()));
        formattedTextFieldDateOfRelease.setText(new SimpleDateFormat("yyyy-MM-dd").format(currentPd.getDateOfRelease()));
    }

    /**
     * Load all the records and display the first record
     */
    private void loadAllPatients() {
        listPosition = 0;
        try {
            pds = pdbm.getAllPatients();

            if (pds != null && pds.size() > 0) {
                displayPatient(pds.get(listPosition));
            } else {
                clearPatient();
            }
        } catch (SQLException sqlex) {
            log.error("Unable to load records.", sqlex.getMessage());
        }
    }

    /**
     * Fill the current patient object and return it
     *
     * @return the patient object
     */
    public PatientData getPatient() {
        currentPd = new PatientData();
        if (patientIDTF.getText().length() != 0) {
            currentPd.setPatientId(getPatientIDFromForm());
        }

        currentPd.setLastName(lastNameTF.getText());
        currentPd.setFirstName(firstNameTF.getText());
        currentPd.setDiagnosis(diagnosisTF.getText());
        try {
            currentPd.setDateOfAdmission(formattedTextFieldDateOfAdmission.getText());
            currentPd.setDateOfRelease(formattedTextFieldDateOfRelease.getText());
        } catch (ParseException ex) {
            Logger.getLogger(PatientPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return currentPd;
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
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel11 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lastBTN = new javax.swing.JButton();
        javax.swing.JLabel jLabel7 = new javax.swing.JLabel();
        previousBTN = new javax.swing.JButton();
        nextBTN = new javax.swing.JButton();
        clearBTN = new javax.swing.JButton();
        firstBTN = new javax.swing.JButton();
        javax.swing.JLabel jLabel10 = new javax.swing.JLabel();
        patientIDTF = new javax.swing.JFormattedTextField(new RegexFormatter(patientIDRegEx));
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        searchBTN = new javax.swing.JButton();
        formattedTextFieldDateOfAdmission = new javax.swing.JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        diagnosisTF = new javax.swing.JTextField();
        lastNameTF = new javax.swing.JFormattedTextField(new RegexFormatter(nameRegEx));
        firstNameTF = new javax.swing.JFormattedTextField(new RegexFormatter(nameRegEx));
        saveBTN = new javax.swing.JButton();
        formattedTextFieldDateOfRelease = new javax.swing.JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        InpatientRecords = new javax.swing.JTable(iptm);
        javax.swing.JLabel jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        surgicalRecords = new javax.swing.JTable();
        javax.swing.JLabel jLabel6 = new javax.swing.JLabel();
        javax.swing.JScrollPane jScrollPane3 = new javax.swing.JScrollPane();
        medicationRecords = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(827, 644));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Last Name");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Date Of Release");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("(format: yyyy-mm-dd)");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("First Name");

        lastBTN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lastBTN.setText(messages.getString("Last"));
        lastBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("(format: yyyy-mm-dd)");

        previousBTN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        previousBTN.setText(messages.getString("Previous"));
        previousBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction(evt);
            }
        });

        nextBTN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nextBTN.setText(messages.getString("Next"));
        nextBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction(evt);
            }
        });

        clearBTN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        clearBTN.setText(messages.getString("Clear"));
        clearBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction(evt);
            }
        });

        firstBTN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        firstBTN.setText(messages.getString("First"));
        firstBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Date Of Admission");

        patientIDTF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Diagnosis");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Patient ID");

        searchBTN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        searchBTN.setText(messages.getString("Search"));
        searchBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction(evt);
            }
        });

        formattedTextFieldDateOfAdmission.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        diagnosisTF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lastNameTF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        firstNameTF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        saveBTN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        saveBTN.setText(messages.getString("Save"));
        saveBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction(evt);
            }
        });

        formattedTextFieldDateOfRelease.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel9)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(patientIDTF, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(firstNameTF)
                            .addComponent(lastNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(33, 33, 33)
                                .addComponent(formattedTextFieldDateOfRelease, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(formattedTextFieldDateOfAdmission, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel7))
                                    .addComponent(diagnosisTF, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(searchBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(firstBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(previousBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nextBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lastBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstBTN)
                    .addComponent(previousBTN)
                    .addComponent(nextBTN)
                    .addComponent(lastBTN)
                    .addComponent(searchBTN)
                    .addComponent(clearBTN)
                    .addComponent(saveBTN))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(patientIDTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(firstNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lastNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(diagnosisTF, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(formattedTextFieldDateOfAdmission)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(formattedTextFieldDateOfRelease)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {firstNameTF, formattedTextFieldDateOfAdmission, formattedTextFieldDateOfRelease, lastNameTF, patientIDTF});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {clearBTN, firstBTN, lastBTN, nextBTN, previousBTN, saveBTN, searchBTN});

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Inpatient Records");

        jScrollPane1.setViewportView(InpatientRecords);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Surgical Records");

        surgicalRecords.setModel(stm);
        jScrollPane2.setViewportView(surgicalRecords);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Medication Records");

        medicationRecords.setModel(mtm);
        jScrollPane3.setViewportView(medicationRecords);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane3)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction
        JButton source = (JButton) (evt.getSource());
        switch (source.getActionCommand()) {
            case "First":
                // Always check that there are records
                if (pds != null && pds.size() > 0) {
                    listPosition = 0;
                    displayPatient(pds.get(listPosition));
                    displayTables(1L);
                } else {
                    loadAllPatients();
                }
                break;
            case "Previous":
                // Always check that there are records
                if (pds != null && pds.size() > 0) // Check that we are already at the beginning of the list
                {
                    if ((listPosition - 1) >= 0) {
                        displayPatient(pds.get(--listPosition));
                        displayTables(pds.get(listPosition).getPatientId());
                    }
                }
                break;
            case "Save":
                savePatient();
                displayTables(1L);
                break;
            case "Clear":
                loadAllPatients();
                clearPatient();
                break;
            case "Search":
                searchPatient();
                break;
            case "Next":
                // Always check that there are records
                if (pds != null && pds.size() > 0) // Check that we are not already at the end of the list
                {
                    if ((listPosition + 1) < pds.size()) {
                        displayPatient(pds.get(++listPosition));
                        displayTables(pds.get(listPosition).getPatientId());
                    }
                }
                break;
            case "Last":
                // Always check that there are records
                if (pds != null && pds.size() > 0) {
                    // Jump to last record
                    listPosition = pds.size() - 1;
                    displayPatient(pds.get(listPosition));
                    displayTables(pds.get(listPosition).getPatientId());
                }
                break;
        }
    }//GEN-LAST:event_buttonAction

    private void displayTables(long pid) {
        displayInpatientTable(pid);
        displaySurgicalTable(pid);
        displayMedicationTable(pid);
    }

    private void displayInpatientTable(long pid) {
        try {
            ipdbm.fillTableModel(pid);
        } catch (SQLException ex) {
            log.error("SQL Exception", ex.getMessage());
        }
    }

    private void displaySurgicalTable(long pid) {
        try {
            sdbm.fillTableModel(pid);
        } catch (SQLException ex) {
            log.error("SQL Exception", ex.getMessage());
        }
    }

    private void displayMedicationTable(long pid) {
        try {
            mdbm.fillTableModel(pid);
        } catch (SQLException ex) {
            log.error("SQL Exception", ex.getMessage());
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JTable InpatientRecords;
    javax.swing.JButton clearBTN;
    javax.swing.JTextField diagnosisTF;
    javax.swing.JButton firstBTN;
    javax.swing.JTextField firstNameTF;
    javax.swing.JFormattedTextField formattedTextFieldDateOfAdmission;
    javax.swing.JFormattedTextField formattedTextFieldDateOfRelease;
    javax.swing.JLabel jLabel1;
    javax.swing.JLabel jLabel9;
    javax.swing.JScrollPane jScrollPane1;
    javax.swing.JScrollPane jScrollPane2;
    javax.swing.JButton lastBTN;
    javax.swing.JTextField lastNameTF;
    javax.swing.JTable medicationRecords;
    javax.swing.JButton nextBTN;
    javax.swing.JTextField patientIDTF;
    javax.swing.JButton previousBTN;
    javax.swing.JButton saveBTN;
    javax.swing.JButton searchBTN;
    javax.swing.JTable surgicalRecords;
    // End of variables declaration//GEN-END:variables
}
