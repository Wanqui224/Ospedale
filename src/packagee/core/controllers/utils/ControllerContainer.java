/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.core.controllers.utils;

import packagee.core.controllers.AppointmentController;
import packagee.core.controllers.AuthController;
import packagee.core.controllers.DoctorController;
import packagee.core.controllers.HospitalizationController;
import packagee.core.controllers.PatientController;

/**
 *
 * @author Wanki
 */
public class ControllerContainer {

    private final AuthController authController;
    private final PatientController patientController;
    private final DoctorController doctorController;
    private final AppointmentController appointmentController;
    private final HospitalizationController hospitalizationController;

    public ControllerContainer(
            AuthController authController,
            PatientController patientController,
            DoctorController doctorController,
            AppointmentController appointmentController,
            HospitalizationController hospitalizationController
    ) {
        this.authController = authController;
        this.patientController = patientController;
        this.doctorController = doctorController;
        this.appointmentController = appointmentController;
        this.hospitalizationController = hospitalizationController;
    }

    public AuthController getAuthController() {
        return authController;
    }

    public PatientController getPatientController() {
        return patientController;
    }

    public DoctorController getDoctorController() {
        return doctorController;
    }

    public AppointmentController getAppointmentController() {
        return appointmentController;
    }

    public HospitalizationController getHospitalizationController() {
        return hospitalizationController;
    }
}
