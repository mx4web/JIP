package com.jip;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({PatientTests.class,InpatientTests.class, MedicationTests.class, SurgicalTests.class})
public class AppTest {

}
