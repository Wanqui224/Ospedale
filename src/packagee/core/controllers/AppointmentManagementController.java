/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.core.controllers;

import java.time.LocalDateTime;
import java.time.LocalTime;
import packagee.core.controllers.utils.AppointmentSerializer;
import packagee.core.controllers.utils.AppointmentValidator;
import packagee.core.controllers.utils.Response;
import packagee.core.controllers.utils.Status;
import packagee.core.models.Appointment;
import packagee.core.models.enums.AppointmentStatus;
import packagee.core.models.storage.IHospitalStorage;

/**
 *
 * @author Wanki
 */
public class AppointmentManagementController implements IAppointmentManagementController {

    private final IHospitalStorage storage;

    public AppointmentManagementController(IHospitalStorage storage) {
        this.storage = storage;
    }

    @Override
    public Response acceptAppointment(String appointmentId) {
        try {
            Appointment appointment = storage.getAppointmentById(appointmentId.trim());
            if (appointment == null) {
                return new Response("Appointment not found", Status.NOT_FOUND);
            }
            if (!AppointmentValidator.canBeAccepted(appointment)) {
                return new Response("Appointment must be in REQUESTED status", Status.BAD_REQUEST);
            }

            appointment.setStatus(AppointmentStatus.PENDING);
            return new Response("Appointment accepted successfully", Status.OK,
                    AppointmentSerializer.serialize(appointment));

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response completeAppointment(
            String appointmentId, String diagnosis, String observations,
            String recommendedTreatment, String followUp
    ) {
        try {
            Appointment appointment = storage.getAppointmentById(appointmentId.trim());
            if (appointment == null) {
                return new Response("Appointment not found", Status.NOT_FOUND);
            }
            if (!AppointmentValidator.canBeCompleted(appointment)) {
                return new Response("Appointment must be in PENDING status", Status.BAD_REQUEST);
            }

            appointment.setStatus(AppointmentStatus.COMPLETED);
            appointment.setDiagnosis(diagnosis.trim());
            appointment.setObservations(observations.trim());
            appointment.setRecommendedTreatment(recommendedTreatment.trim());
            appointment.setFollowUp(followUp.trim());

            return new Response("Appointment completed successfully", Status.OK,
                    AppointmentSerializer.serialize(appointment));

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response cancelAppointment(String appointmentId) {
        try {
            Appointment appointment = storage.getAppointmentById(appointmentId.trim());
            if (appointment == null) {
                return new Response("Appointment not found", Status.NOT_FOUND);
            }
            if (!AppointmentValidator.canBeCanceled(appointment)) {
                return new Response("Appointment is already COMPLETED", Status.BAD_REQUEST);
            }

            appointment.setStatus(AppointmentStatus.CANCELED);
            return new Response("Appointment canceled successfully", Status.OK,
                    AppointmentSerializer.serialize(appointment));

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response rescheduleAppointment(String appointmentId, String newTime, String reason) {
        try {
            Appointment appointment = storage.getAppointmentById(appointmentId.trim());
            if (appointment == null) {
                return new Response("Appointment not found", Status.NOT_FOUND);
            }
            if (!AppointmentValidator.canBeRescheduled(appointment)) {
                return new Response("Appointment cannot be rescheduled", Status.BAD_REQUEST);
            }
            if (!AppointmentValidator.isValidTime(newTime)) {
                return new Response("Time must follow hh:mm and minutes must be 00, 15, 30 or 45", Status.BAD_REQUEST);
            }

            LocalDateTime newDatetime = LocalDateTime.of(
                    appointment.getDatetime().toLocalDate(),
                    LocalTime.parse(newTime.trim())
            );

            if (!AppointmentValidator.isDoctorAvailable(appointment.getDoctor(), newDatetime)) {
                return new Response("Doctor is not available at that time", Status.BAD_REQUEST);
            }

            appointment.setDatetime(newDatetime);
            appointment.setReason(appointment.getReason() + " | Rescheduled: " + reason.trim());

            return new Response("Appointment rescheduled successfully", Status.OK,
                    AppointmentSerializer.serialize(appointment));

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
