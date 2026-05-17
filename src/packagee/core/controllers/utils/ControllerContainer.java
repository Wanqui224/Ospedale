/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.core.controllers.utils;

import packagee.core.controllers.AuthController;
import packagee.core.controllers.IAppointmentManagementController;
import packagee.core.controllers.IAppointmentQueryController;
import packagee.core.controllers.IAppointmentRequestController;
import packagee.core.controllers.IDoctorController;
import packagee.core.controllers.IHospitalizationManagementController;
import packagee.core.controllers.IHospitalizationQueryController;
import packagee.core.controllers.IHospitalizationRequestController;
import packagee.core.controllers.IPatientController;
import packagee.core.controllers.IPrescriptionController;

/**
 *
 * @author Wanki
 */
public class ControllerContainer {

    private final AuthController authController;
    private final IPatientController patientController;
    private final IDoctorController doctorController;
    private final IAppointmentRequestController appointmentRequestController;
    private final IAppointmentManagementController appointmentManagementController;
    private final IAppointmentQueryController appointmentQueryController;
    private final IPrescriptionController prescriptionController;
    private final IHospitalizationRequestController hospitalizationRequestController;
    private final IHospitalizationManagementController hospitalizationManagementController;
    private final IHospitalizationQueryController hospitalizationQueryController;

    public ControllerContainer(
            AuthController authController,
            IPatientController patientController,
            IDoctorController doctorController,
            IAppointmentRequestController appointmentRequestController,
            IAppointmentManagementController appointmentManagementController,
            IAppointmentQueryController appointmentQueryController,
            IPrescriptionController prescriptionController,
            IHospitalizationRequestController hospitalizationRequestController,
            IHospitalizationManagementController hospitalizationManagementController,
            IHospitalizationQueryController hospitalizationQueryController
    ) {
        this.authController = authController;
        this.patientController = patientController;
        this.doctorController = doctorController;
        this.appointmentRequestController = appointmentRequestController;
        this.appointmentManagementController = appointmentManagementController;
        this.appointmentQueryController = appointmentQueryController;
        this.prescriptionController = prescriptionController;
        this.hospitalizationRequestController = hospitalizationRequestController;
        this.hospitalizationManagementController = hospitalizationManagementController;
        this.hospitalizationQueryController = hospitalizationQueryController;
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

    public IAppointmentRequestController getAppointmentRequestController() {
        return appointmentRequestController;
    }

    public IAppointmentManagementController getAppointmentManagementController() {
        return appointmentManagementController;
    }

    public IAppointmentQueryController getAppointmentQueryController() {
        return appointmentQueryController;
    }

    public IPrescriptionController getPrescriptionController() {
        return prescriptionController;
    }

    public IHospitalizationRequestController getHospitalizationRequestController() {
        return hospitalizationRequestController;
    }

    public IHospitalizationManagementController getHospitalizationManagementController() {
        return hospitalizationManagementController;
    }

    public IHospitalizationQueryController getHospitalizationQueryController() {
        return hospitalizationQueryController;
    }
}
