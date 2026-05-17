/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.core.controllers.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import packagee.core.controllers.IAppointmentRequestController;
import packagee.core.models.Appointment;
import packagee.core.models.Doctor;
import packagee.core.models.Patient;
import packagee.core.models.User;
import packagee.core.models.enums.Specialty;
import packagee.core.models.storage.IHospitalStorage;

/**
 *
 * @author Wanki
 */
public class AppointmentRequestController implements IAppointmentRequestController {

    private final IHospitalStorage storage;

    public AppointmentRequestController(IHospitalStorage storage) {
        this.storage = storage;
    }

    private String generateAppointmentId(Patient patient) {
        long count = 0;
        for (Appointment a : storage.getAppointments()) {
            if (a.getPatient().getId() == patient.getId()) {
                count++;
            }
        }
        return String.format("A-%d-%04d", patient.getId(), count);
    }

    private Doctor findAvailableDoctor(Specialty specialty, LocalDateTime datetime) {
        for (User u : storage.getUsers()) {
            if (u instanceof Doctor) {
                Doctor doc = (Doctor) u;
                if (doc.getSpecialty() == specialty
                        && AppointmentValidator.isDoctorAvailable(doc, datetime)) {
                    return doc;
                }
            }
        }
        return null;
    }

    @Override
    public Response requestAppointmentByDoctor(
            String patientId, String doctorId,
            String date, String time, String type, String reason
    ) {
        try {
            if (!UserValidator.isValidId(patientId)) {
                return new Response("Invalid patient id", Status.BAD_REQUEST);
            }
            if (!UserValidator.isValidId(doctorId)) {
                return new Response("Invalid doctor id", Status.BAD_REQUEST);
            }
            if (!AppointmentValidator.isValidDate(date)) {
                return new Response("Date must follow the format YYYY-MM-DD", Status.BAD_REQUEST);
            }
            if (!AppointmentValidator.isValidTime(time)) {
                return new Response("Time must follow hh:mm and minutes must be 00, 15, 30 or 45", Status.BAD_REQUEST);
            }

            User patientUser = storage.getUserById(Long.parseLong(patientId.trim()));
            if (patientUser == null || !(patientUser instanceof Patient)) {
                return new Response("Patient not found", Status.NOT_FOUND);
            }

            User doctorUser = storage.getUserById(Long.parseLong(doctorId.trim()));
            if (doctorUser == null || !(doctorUser instanceof Doctor)) {
                return new Response("Doctor not found", Status.NOT_FOUND);
            }

            Patient patient = (Patient) patientUser;
            Doctor doctor = (Doctor) doctorUser;

            LocalDateTime datetime = LocalDateTime.of(
                    LocalDate.parse(date.trim()),
                    LocalTime.parse(time.trim())
            );

            if (!AppointmentValidator.isDoctorAvailable(doctor, datetime)) {
                return new Response("Doctor is not available at that time", Status.BAD_REQUEST);
            }

            String id = generateAppointmentId(patient);
            Appointment appointment = new Appointment(
                    id, patient, doctor, doctor.getSpecialty(),
                    datetime, reason.trim(),
                    type.trim().equalsIgnoreCase("In-person")
            );

            storage.addAppointment(appointment);
            patient.addAppointment(appointment);
            doctor.addAppointment(appointment);

            return new Response("Appointment requested successfully", Status.CREATED,
                    AppointmentSerializer.serialize(appointment));

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response requestAppointmentBySpecialty(
            String patientId, String specialty,
            String date, String time, String type, String reason
    ) {
        try {
            if (!UserValidator.isValidId(patientId)) {
                return new Response("Invalid patient id", Status.BAD_REQUEST);
            }
            if (!AppointmentValidator.isValidDate(date)) {
                return new Response("Date must follow the format YYYY-MM-DD", Status.BAD_REQUEST);
            }
            if (!AppointmentValidator.isValidTime(time)) {
                return new Response("Time must follow hh:mm and minutes must be 00, 15, 30 or 45", Status.BAD_REQUEST);
            }

            User patientUser = storage.getUserById(Long.parseLong(patientId.trim()));
            if (patientUser == null || !(patientUser instanceof Patient)) {
                return new Response("Patient not found", Status.NOT_FOUND);
            }

            Specialty specialtyEnum;
            try {
                specialtyEnum = Specialty.valueOf(specialty.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                return new Response("Specialty is not valid", Status.BAD_REQUEST);
            }

            Patient patient = (Patient) patientUser;
            LocalDateTime datetime = LocalDateTime.of(
                    LocalDate.parse(date.trim()),
                    LocalTime.parse(time.trim())
            );

            Doctor doctor = findAvailableDoctor(specialtyEnum, datetime);
            if (doctor == null) {
                return new Response("No doctor available for that specialty at that time", Status.NOT_FOUND);
            }

            String id = generateAppointmentId(patient);
            Appointment appointment = new Appointment(
                    id, patient, doctor, specialtyEnum,
                    datetime, reason.trim(),
                    type.trim().equalsIgnoreCase("In-person")
            );

            storage.addAppointment(appointment);
            patient.addAppointment(appointment);
            doctor.addAppointment(appointment);

            return new Response("Appointment requested successfully", Status.CREATED,
                    AppointmentSerializer.serialize(appointment));

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}