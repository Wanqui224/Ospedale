/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.core.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import packagee.core.controllers.utils.HospitalizationSerializer;
import packagee.core.controllers.utils.Response;
import packagee.core.controllers.utils.Status;
import packagee.core.controllers.utils.UserValidator;
import packagee.core.models.Doctor;
import packagee.core.models.Hospitalization;
import packagee.core.models.Patient;
import packagee.core.models.User;
import packagee.core.models.storage.IHospitalStorage;

/**
 *
 * @author Wanki
 */
public class HospitalizationQueryController implements IHospitalizationQueryController {

    private final IHospitalStorage storage;

    public HospitalizationQueryController(IHospitalStorage storage) {
        this.storage = storage;
    }

    @Override
    public Response getPatientHospitalizations(String patientId) {
        try {
            if (!UserValidator.isValidId(patientId)) {
                return new Response("Invalid patient id", Status.BAD_REQUEST);
            }

            User user = storage.getUserById(Long.parseLong(patientId.trim()));
            if (user == null || !(user instanceof Patient)) {
                return new Response("Patient not found", Status.NOT_FOUND);
            }

            ArrayList<Hospitalization> list = new ArrayList<>();
            for (Hospitalization h : storage.getHospitalizations()) {
                if (h.getPatient().getId() == user.getId()) {
                    list.add(h);
                }
            }

            HashMap<String, Object> data = new HashMap<>();
            data.put("hospitalizations", HospitalizationSerializer.serializeList(list));
            return new Response("Hospitalizations retrieved successfully", Status.OK, data);

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response getDoctorHospitalizations(String doctorId) {
        try {
            if (!UserValidator.isValidId(doctorId)) {
                return new Response("Invalid doctor id", Status.BAD_REQUEST);
            }

            User user = storage.getUserById(Long.parseLong(doctorId.trim()));
            if (user == null || !(user instanceof Doctor)) {
                return new Response("Doctor not found", Status.NOT_FOUND);
            }

            ArrayList<Hospitalization> list = new ArrayList<>();
            for (Hospitalization h : storage.getHospitalizations()) {
                if (h.getDoctor().getId() == user.getId()) {
                    list.add(h);
                }
            }

            HashMap<String, Object> data = new HashMap<>();
            data.put("hospitalizations", HospitalizationSerializer.serializeList(list));
            return new Response("Hospitalizations retrieved successfully", Status.OK, data);

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response getAllHospitalizations() {
        try {
            HashMap<String, Object> data = new HashMap<>();
            data.put("hospitalizations",
                    HospitalizationSerializer.serializeList(storage.getHospitalizations()));
            return new Response("Hospitalizations retrieved successfully", Status.OK, data);

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
