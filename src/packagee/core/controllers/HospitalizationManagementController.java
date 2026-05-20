/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.core.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import packagee.core.controllers.utils.AppointmentSerializer;
import packagee.core.controllers.utils.AppointmentValidator;
import packagee.core.controllers.utils.HospitalizationSerializer;
import packagee.core.controllers.utils.HospitalizationValidator;
import packagee.core.controllers.utils.Response;
import packagee.core.controllers.utils.Status;
import packagee.core.controllers.utils.UserValidator;
import packagee.core.models.Appointment;
import packagee.core.models.Doctor;
import packagee.core.models.Hospitalization;
import packagee.core.models.Patient;
import packagee.core.models.User;
import packagee.core.models.enums.AppointmentStatus;
import packagee.core.models.enums.HospitalizationStatus;
import packagee.core.models.enums.RoomType;
import packagee.core.models.storage.IHospitalStorage;

/**
 *
 * @author Wanki
 */
public class HospitalizationManagementController implements IHospitalizationManagementController {

    private final IHospitalStorage storage;

    public HospitalizationManagementController(IHospitalStorage storage) {
        this.storage = storage;
    }

    private String generateHospitalizationId(Patient patient) {
        long count = 0;
        for (Hospitalization h : storage.getHospitalizations()) {
            if (h.getPatient().getId() == patient.getId()) {
                count++;
            }
        }
        return String.format("H-%d-%04d", patient.getId(), count);
    }

    @Override
    public Response approveHospitalization(String hospitalizationId) {
        try {
            Hospitalization h = storage.getHospitalizationById(hospitalizationId.trim());
            if (h == null) {
                return new Response("Hospitalization not found", Status.NOT_FOUND);
            }
            if (!HospitalizationValidator.canBeApproved(h)) {
                return new Response("Hospitalization must be in REQUESTED status", Status.BAD_REQUEST);
            }

            h.setStatus(HospitalizationStatus.ONGOING);
            return new Response("Hospitalization approved successfully", Status.OK,
                    HospitalizationSerializer.serialize(h));

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response denyHospitalization(String hospitalizationId) {
        try {
            Hospitalization h = storage.getHospitalizationById(hospitalizationId.trim());
            if (h == null) {
                return new Response("Hospitalization not found", Status.NOT_FOUND);
            }
            if (!HospitalizationValidator.canBeDenied(h)) {
                return new Response("Hospitalization must be in REQUESTED status", Status.BAD_REQUEST);
            }

            h.setStatus(HospitalizationStatus.CANCELED);
            return new Response("Hospitalization denied successfully", Status.OK,
                    HospitalizationSerializer.serialize(h));

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response directlyHospitalizePatient(
            String patientId, String doctorId,
            String date, String reason,
            String roomType, String observations
    ) {
        try {
            if (!UserValidator.isValidId(patientId)) {
                return new Response("Invalid patient id", Status.BAD_REQUEST);
            }
            if (!UserValidator.isValidId(doctorId)) {
                return new Response("Invalid doctor id", Status.BAD_REQUEST);
            }

            User patientUser = storage.getUserById(Long.parseLong(patientId.trim()));
            if (patientUser == null || !(patientUser instanceof Patient)) {
                return new Response("Patient not found", Status.NOT_FOUND);
            }

            User doctorUser = storage.getUserById(Long.parseLong(doctorId.trim()));
            if (doctorUser == null || !(doctorUser instanceof Doctor)) {
                return new Response("Doctor not found", Status.NOT_FOUND);
            }

            if (!HospitalizationValidator.isValidDate(date)) {
                return new Response("Date must follow the format YYYY-MM-DD", Status.BAD_REQUEST);
            }

            RoomType roomTypeEnum;
            try {
                roomTypeEnum = RoomType.valueOf(roomType.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                return new Response("Room type is not valid", Status.BAD_REQUEST);
            }

            if (reason == null || reason.trim().isEmpty()) {
                return new Response("Reason is required", Status.BAD_REQUEST);
            }

            Patient patient = (Patient) patientUser;
            Doctor doctor = (Doctor) doctorUser;

            String id = generateHospitalizationId(patient);
            Hospitalization hospitalization = new Hospitalization(
                    id, patient, doctor,
                    LocalDate.parse(date.trim()),
                    reason.trim(), roomTypeEnum,
                    observations == null ? "" : observations.trim(),
                    HospitalizationStatus.ONGOING
            );

            storage.addHospitalization(hospitalization);

            return new Response("Patient hospitalized directly successfully",
                    Status.CREATED, HospitalizationSerializer.serialize(hospitalization));

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response hospitalizeFromAppointment(
            String appointmentId, String patientId, String doctorId,
            String date, String reason, String roomType, String observations
    ) {
        try {

            if (!UserValidator.isValidId(patientId)) {
                return new Response("Invalid patient id", Status.BAD_REQUEST);
            }
            if (!UserValidator.isValidId(doctorId)) {
                return new Response("Invalid doctor id", Status.BAD_REQUEST);
            }

            Appointment appointment = storage.getAppointmentById(appointmentId.trim());
            if (appointment == null) {
                return new Response("Appointment not found", Status.NOT_FOUND);
            }
            if (!AppointmentValidator.canBeCompleted(appointment)) {
                return new Response("Appointment must be in PENDING status", Status.BAD_REQUEST);
            }

            User patientUser = storage.getUserById(Long.parseLong(patientId.trim()));
            if (patientUser == null || !(patientUser instanceof Patient)) {
                return new Response("Patient not found", Status.NOT_FOUND);
            }

            User doctorUser = storage.getUserById(Long.parseLong(doctorId.trim()));
            if (doctorUser == null || !(doctorUser instanceof Doctor)) {
                return new Response("Doctor not found", Status.NOT_FOUND);
            }

            if (!HospitalizationValidator.isValidDate(date)) {
                return new Response("Date must follow the format YYYY-MM-DD", Status.BAD_REQUEST);
            }

            RoomType roomTypeEnum;
            try {
                roomTypeEnum = RoomType.valueOf(roomType.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                return new Response("Room type is not valid", Status.BAD_REQUEST);
            }

            if (reason == null || reason.trim().isEmpty()) {
                return new Response("Reason is required", Status.BAD_REQUEST);
            }

            Patient patient = (Patient) patientUser;
            Doctor doctor = (Doctor) doctorUser;

            appointment.setStatus(AppointmentStatus.COMPLETED);

            String id = generateHospitalizationId(patient);
            Hospitalization hospitalization = new Hospitalization(
                    id, patient, doctor,
                    LocalDate.parse(date.trim()),
                    reason.trim(), roomTypeEnum,
                    observations == null ? "" : observations.trim(),
                    HospitalizationStatus.ONGOING
            );

            storage.addHospitalization(hospitalization);

            HashMap<String, Object> data = new HashMap<>();
            data.put("appointment", AppointmentSerializer.serialize(appointment));
            data.put("hospitalization", HospitalizationSerializer.serialize(hospitalization));

            return new Response("Patient hospitalized from appointment successfully",
                    Status.CREATED, data);

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
