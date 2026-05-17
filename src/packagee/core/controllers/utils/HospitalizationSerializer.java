/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.core.controllers.utils;

import java.util.ArrayList;
import java.util.HashMap;
import packagee.core.models.Hospitalization;

/**
 *
 * @author Wanki
 */
public class HospitalizationSerializer {

    public static HashMap<String, Object> serialize(Hospitalization h) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", h.getId());
        data.put("patient", h.getPatient().getFirstname() + " " + h.getPatient().getLastname());
        data.put("patientId", h.getPatient().getId());
        data.put("doctor", h.getDoctor().getFirstname() + " " + h.getDoctor().getLastname());
        data.put("doctorId", h.getDoctor().getId());
        data.put("date", h.getDate().toString());
        data.put("reason", h.getReason());
        data.put("roomType", h.getRoomType().name());
        data.put("observations", h.getObservations());
        data.put("status", h.getStatus().name());
        return data;
    }

    public static ArrayList<HashMap<String, Object>> serializeList(ArrayList<Hospitalization> hospitalizations) {
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        for (Hospitalization h : hospitalizations)
            list.add(serialize(h));
        return list;
    }
}