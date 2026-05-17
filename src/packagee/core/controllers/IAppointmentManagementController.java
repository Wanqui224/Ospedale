/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package packagee.core.controllers;

import packagee.core.controllers.utils.Response;

/**
 *
 * @author Wanki
 */
public interface IAppointmentManagementController {
    Response acceptAppointment(String appointmentId);
    Response completeAppointment(
        String appointmentId, String diagnosis,
        String observations, String recommendedTreatment,
        String followUp
    );
    Response cancelAppointment(String appointmentId);
    Response rescheduleAppointment(
        String appointmentId,
        String newTime,
        String reason
    );
}
