/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.core.models;

import packagee.core.models.enums.Specialty;
import java.util.ArrayList;

/**
 *
 * @author edangulo
 */
public class Doctor extends User implements ISchedulable {

    private Specialty specialty;
    private String licenceNumber;
    private String assignedOffice;
    private ArrayList<Appointment> appointments;
    private ArrayList<Hospitalization> hospitalizations;

    public Doctor(long id, String username, String firstname, String lastname,
            String password, Specialty specialty,
            String licenceNumber, String assignedOffice) {
        super(id, username, firstname, lastname, password);
        this.appointments = new ArrayList<>();
        this.hospitalizations = new ArrayList<>();
        this.specialty = specialty;
        this.licenceNumber = licenceNumber;
        this.assignedOffice = assignedOffice;
    }

    @Override
    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    @Override
    public void addAppointment(Appointment a) {
        appointments.add(a);
    }

    public ArrayList<Hospitalization> getHospitalizations() {
        return hospitalizations;
    }

    public boolean addHospitalization(Hospitalization h) {
        return hospitalizations.add(h);
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public void setAssignedOffice(String assignedOffice) {
        this.assignedOffice = assignedOffice;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public String getAssignedOffice() {
        return assignedOffice;
    }
}
