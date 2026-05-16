/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.core.controllers.utils;

import java.util.HashMap;
import packagee.core.models.Administrator;
import packagee.core.models.Doctor;
import packagee.core.models.Patient;
import packagee.core.models.User;

/**
 *
 * @author Wanki
 */
public class UserSerializer {

    public static HashMap<String, Object> serialize(User user) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", user.getId());
        data.put("username", user.getUsername());
        data.put("firstname", user.getFirstname());
        data.put("lastname", user.getLastname());

        if (user instanceof Administrator) {
            data.put("role", "ADMIN");
        } else if (user instanceof Doctor) {
            data.put("role", "DOCTOR");
            Doctor doc = (Doctor) user;
            data.put("specialty", doc.getSpecialty().name());
            data.put("licenceNumber", doc.getLicenceNumber());
            data.put("assignedOffice", doc.getAssignedOffice());
        } else if (user instanceof Patient) {
            data.put("role", "PATIENT");
            Patient pat = (Patient) user;
            data.put("email", pat.getEmail());
            data.put("phone", pat.getPhone());
            data.put("address", pat.getAddress());
            data.put("birthdate", pat.getBirthdate().toString());
            data.put("gender", pat.getGender());
        }

        return data;
    }
}
