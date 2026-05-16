/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.core.controllers.utils;

import packagee.core.controllers.AppointmentController;
import packagee.core.controllers.AuthController;
import packagee.core.controllers.HospitalizationController;
import packagee.core.controllers.IDoctorController;
import packagee.core.controllers.IPatientController;

/**
 *
 * @author Wanki
 */
public class ControllerContainer {

    private final AuthController authController;
    private final IPatientController patientController;
    private final IDoctorController doctorController;
    private final AppointmentController appointmentController;
    private final HospitalizationController hospitalizationController;

    public ControllerContainer(
            AuthController authController,
            IPatientController patientController,
            IDoctorController doctorController,
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

    public IPatientController getPatientController() {
        return patientController;
    }

    public IDoctorController getDoctorController() {
        return doctorController;
    }

    public AppointmentController getAppointmentController() {
        return appointmentController;
    }

    public HospitalizationController getHospitalizationController() {
        return hospitalizationController;
    }
}
