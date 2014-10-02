DROP DATABASE IF EXISTS jip;
CREATE DATABASE jip;
use jip;

GRANT ALL ON jip.* TO fish@"%" IDENTIFIED BY "fish";
GRANT ALL ON jip.* TO fish@"localhost" IDENTIFIED BY "fish";

DROP TABLE IF EXISTS patients;
CREATE TABLE patients (
  PatientID int(11) NOT NULL auto_increment PRIMARY KEY,
  LastName varchar(30) NOT NULL,
  FirstName varchar(30) NOT NULL,
  Diagnosis varchar(60),
  DateOfAdmission timestamp NOT NULL,
  DateOfRelease timestamp
) ENGINE=InnoDB;

INSERT INTO patients (lastName, firstName, diagnosis, dateOfAdmission, dateOfRelease) values 
("Yu","Xiao","cold","2013-01-01","2013-02-02");
INSERT INTO patients (lastName, firstName, diagnosis, dateOfAdmission, dateOfRelease) values 
("Jim","Eric","fever","2011-11-28","2013-12-09");
INSERT INTO patients (lastName, firstName, diagnosis, dateOfAdmission, dateOfRelease) values 
("Bym","Joe","headache","2011-11-28","2013-11-28");
INSERT INTO patients (lastName, firstName, diagnosis, dateOfAdmission, dateOfRelease) values 
("Rin","Zoe","headache","2011-11-28","2013-11-28");

UPDATE patients SET firstName = "Julia"  WHERE PatientID = 2;

DROP TABLE IF EXISTS inpatients;
CREATE TABLE inpatients (
  ID int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  PatientID int(11),
  DateOfStay timestamp NOT NULL,
  RoomNumber varchar(5) NOT NULL DEFAULT '',
  DailyRate decimal(10,2),
  Supplies decimal(10,2),
  Services decimal(10,2),
  KEY PatientID (PatientID),
  CONSTRAINT fk_inpatients FOREIGN KEY (PatientID) REFERENCES patients (PatientID)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS surgical;
CREATE TABLE surgical (
  ID int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  PatientID int(11),
  DateOfSurgery timestamp,
  SurgicalProcedure varchar(25) NOT NULL DEFAULT '',
  RoomFee decimal(10,2),
  SurgeonFee decimal(10,2),
  Supplies decimal(10,2),
  KEY PatientID (PatientID),
  CONSTRAINT fk_surgical FOREIGN KEY (PatientID) REFERENCES patients (PatientID)
) ENGINE=InnoDB;


DROP TABLE IF EXISTS medication;
CREATE TABLE medication (
  ID int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  PatientID int(11),
  DateOfMed timestamp,
  Med varchar(45) NOT NULL DEFAULT '',
  UnitCost decimal(10,2),
  Units decimal(10,2) ,
  KEY PATIENTID (PatientID),
  CONSTRAINT fk_medication FOREIGN KEY (PatientID) REFERENCES patients (PatientID)
) ENGINE=InnoDB;

