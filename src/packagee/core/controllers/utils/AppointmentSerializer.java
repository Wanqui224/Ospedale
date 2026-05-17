/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.core.controllers.utils;

import java.util.ArrayList;
import java.util.HashMap;
import packagee.core.models.Appointment;

/**
 *
 * @author Wanki
 */
public class AppointmentSerializer {

    public static HashMap<String, Object> serialize(Appointment a) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", a.getId());
        data.put("datetime", a.getDatetime().toString());
        data.put("doctor", a.getDoctor().getFirstname() + " " + a.getDoctor().getLastname());
        data.put("doctorId", a.getDoctor().getId());
        data.put("patient", a.getPatient().getFirstname() + " " + a.getPatient().getLastname());
        data.put("patientId", a.getPatient().getId());
        data.put("specialty", a.getSpecialty().name());
        data.put("type", a.isType() ? "In-person" : "Remote");
        data.put("status", a.getStatus().name());
        data.put("reason", a.getReason());
        return data;
    }

    public static ArrayList<HashMap<String, Object>> serializeList(ArrayList<Appointment> appointments) {
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        for (Appointment a : appointments) {
            list.add(serialize(a));
        }
        return list;
    }
}
