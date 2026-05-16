/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.core.models.storage;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import packagee.core.models.Administrator;
import packagee.core.models.Doctor;
import packagee.core.models.Patient;
import packagee.core.models.User;
import packagee.core.models.enums.Specialty;

/**
 *
 * @author Wanki
 */
public class JsonLoader {

    public ArrayList<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get("json/users.json")));
            JSONObject root = new JSONObject(content);
            JSONArray arr = root.getJSONArray("users");

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String type = obj.getString("type");
                long id = obj.getLong("id");
                String username = obj.getString("username");
                String firstname = obj.getString("firstname");
                String lastname = obj.getString("lastname");
                String password = obj.getString("password");

                switch (type) {
                    case "admin":
                        users.add(new Administrator(
                                id, username, firstname, lastname, password
                        ));
                        break;
                    case "patient":
                        users.add(new Patient(
                                id, username, firstname, lastname, password,
                                obj.getString("email"),
                                LocalDate.parse(obj.getString("birthdate")),
                                obj.getBoolean("gender"),
                                obj.getLong("phone"),
                                obj.getString("address")
                        ));
                        break;
                    case "doctor":
                        users.add(new Doctor(
                                id, username, firstname, lastname, password,
                                mapSpecialty(obj.getString("specialty")),
                                obj.getString("licenceNumber"),
                                obj.getString("assignedOffice")
                        ));
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading JSON: " + e.getMessage());
        }
        return users;
    }

    private Specialty mapSpecialty(String s) {
        switch (s.toUpperCase()) {
            case "CARDIOLOGY":
                return Specialty.CARDIOLOGY;
            case "PEDIATRICS":
                return Specialty.PEDIATRICS;
            case "NEUROLOGY":
                return Specialty.NEUROLOGY;
            case "DERMATOLOGY":
                return Specialty.DERMATOLOGY;
            case "PSYCHIATRY":
                return Specialty.PSYCHIATRY;
            case "ONCOLOGY":
                return Specialty.ONCOLOGY;
            case "OPHTHALMOLOGY":
                return Specialty.OPHTHALMOLOGY;
            case "INTERNAL_MEDICINE":
                return Specialty.INTERNAL_MEDICINE;
            case "ORTHOPEDICS":
            case "TRAUMATOLOGY_ORTHOPEDICS":
                return Specialty.TRAUMATOLOGY_ORTHOPEDICS;
            case "GYNECOLOGY":
            case "GYNECOLOGY_OBSTETRICS":
                return Specialty.GYNECOLOGY_OBSTETRICS;
            default:
                return Specialty.GENERAL_MEDICINE;
        }
    }
}
