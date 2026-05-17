/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.core.controllers.utils;

import java.util.ArrayList;
import java.util.HashMap;
import packagee.core.controllers.IAppointmentQueryController;
import packagee.core.models.Appointment;
import packagee.core.models.Doctor;
import packagee.core.models.Patient;
import packagee.core.models.User;
import packagee.core.models.enums.AppointmentStatus;
import packagee.core.models.storage.IHospitalStorage;

/**
 *
 * @author Wanki
 */
public class AppointmentQueryController implements IAppointmentQueryController {

    private final IHospitalStorage storage;

    public AppointmentQueryController(IHospitalStorage storage) {
        this.storage = storage;
    }

    @Override
    public Response getPatientAppointments(String patientId) {
        try {
            if (!UserValidator.isValidId(patientId)) {
                return new Response("Invalid patient id", Status.BAD_REQUEST);
            }

            User user = storage.getUserById(Long.parseLong(patientId.trim()));
            if (user == null || !(user instanceof Patient)) {
                return new Response("Patient not found", Status.NOT_FOUND);
            }

            ArrayList<Appointment> appointments = new ArrayList<>(((Patient) user).getAppointments());
            appointments.sort((a, b) -> b.getDatetime().compareTo(a.getDatetime()));

            HashMap<String, Object> data = new HashMap<>();
            data.put("appointments", AppointmentSerializer.serializeList(appointments));
            return new Response("Appointments retrieved successfully", Status.OK, data);

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response getDoctorAppointments(String doctorId, boolean onlyPending) {
        try {
            if (!UserValidator.isValidId(doctorId)) {
                return new Response("Invalid doctor id", Status.BAD_REQUEST);
            }

            User user = storage.getUserById(Long.parseLong(doctorId.trim()));
            if (user == null || !(user instanceof Doctor)) {
                return new Response("Doctor not found", Status.NOT_FOUND);
            }

            ArrayList<Appointment> appointments = new ArrayList<>(((Doctor) user).getAppointments());

            if (onlyPending) {
                ArrayList<Appointment> pending = new ArrayList<>();
                for (Appointment a : appointments) {
                    if (a.getStatus() == AppointmentStatus.PENDING) {
                        pending.add(a);
                    }
                }
                appointments = pending;
            }

            appointments.sort((a, b) -> b.getDatetime().compareTo(a.getDatetime()));

            HashMap<String, Object> data = new HashMap<>();
            data.put("appointments", AppointmentSerializer.serializeList(appointments));
            return new Response("Appointments retrieved successfully", Status.OK, data);

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
