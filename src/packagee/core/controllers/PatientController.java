/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.core.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import packagee.core.controllers.utils.Response;
import packagee.core.controllers.utils.Status;
import packagee.core.controllers.utils.UserSerializer;
import packagee.core.controllers.utils.UserValidator;
import packagee.core.models.Patient;
import packagee.core.models.User;
import packagee.core.models.storage.IHospitalStorage;

/**
 *
 * @author Wanki
 */
public class PatientController implements IPatientController {

    private final IHospitalStorage storage;

    public PatientController(IHospitalStorage storage) {
        this.storage = storage;
    }

    
    private Response validatePatientData(
            String username, String firstname, String lastname,
            String password, String confirmPassword, String email,
            String birthdate, String gender, String phone, String address
    ) {
        if (!UserValidator.isValidUsername(username)) {
            return new Response("Username is required", Status.BAD_REQUEST);
        }
        if (firstname == null || firstname.trim().isEmpty()) {
            return new Response("Firstname is required", Status.BAD_REQUEST);
        }
        if (lastname == null || lastname.trim().isEmpty()) {
            return new Response("Lastname is required", Status.BAD_REQUEST);
        }
        if (!UserValidator.isValidPassword(password)) {
            return new Response("Password is required", Status.BAD_REQUEST);
        }
        if (!password.equals(confirmPassword)) {
            return new Response("Password and confirmation do not match", Status.BAD_REQUEST);
        }
        if (!UserValidator.isValidEmail(email)) {
            return new Response("Email must follow the format XXXXX@XXXXX.com", Status.BAD_REQUEST);
        }
        if (!UserValidator.isValidBirthdate(birthdate)) {
            return new Response("Birthdate must follow the format YYYY-MM-DD", Status.BAD_REQUEST);
        }
        if (gender == null || gender.trim().isEmpty()) {
            return new Response("Gender is required", Status.BAD_REQUEST);
        }
        if (!UserValidator.isValidPhone(phone)) {
            return new Response("Phone must have exactly 10 digits", Status.BAD_REQUEST);
        }
        if (address == null || address.trim().isEmpty()) {
            return new Response("Address is required", Status.BAD_REQUEST);
        }
        return null;
    }

    
    @Override
    public Response registerPatient(
            String id, String username, String firstname, String lastname,
            String password, String confirmPassword, String email,
            String birthdate, String gender, String phone, String address
    ) {
        try {
            if (!UserValidator.isValidId(id)) {
                return new Response("Id must be numeric, positive and have exactly 12 digits", Status.BAD_REQUEST);
            }

            long idLong = Long.parseLong(id.trim());

            if (storage.getUserById(idLong) != null) {
                return new Response("A user with that id already exists", Status.BAD_REQUEST);
            }
            if (storage.getUserByUsername(username.trim()) != null) {
                return new Response("A user with that username already exists", Status.BAD_REQUEST);
            }

           
            Response validation = validatePatientData(
                    username, firstname, lastname, password,
                    confirmPassword, email, birthdate, gender, phone, address
            );
            if (validation != null) {
                return validation;
            }

            
            Patient patient = new Patient(
                    idLong, username.trim(), firstname.trim(), lastname.trim(),
                    password, email.trim(), LocalDate.parse(birthdate.trim()),
                    gender.trim().equalsIgnoreCase("Female"),
                    Long.parseLong(phone.trim()), address.trim()
            );

            storage.addUser(patient);
            return new Response("Patient registered successfully", Status.CREATED, UserSerializer.serialize(patient));

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

   
    @Override
    public Response updatePatient(
            String id, String username, String firstname, String lastname,
            String password, String confirmPassword, String email,
            String birthdate, String gender, String phone, String address
    ) {
        try {
            if (!UserValidator.isValidId(id)) {
                return new Response("Id must be numeric, positive and have exactly 12 digits", Status.BAD_REQUEST);
            }

            long idLong = Long.parseLong(id.trim());
            User user = storage.getUserById(idLong);

            if (user == null) {
                return new Response("Patient not found", Status.NOT_FOUND);
            }
            if (!(user instanceof Patient)) {
                return new Response("User is not a patient", Status.BAD_REQUEST);
            }

            
            User existingUsername = storage.getUserByUsername(username.trim());
            if (existingUsername != null && existingUsername.getId() != idLong) {
                return new Response("A user with that username already exists", Status.BAD_REQUEST);
            }

            
            Response validation = validatePatientData(
                    username, firstname, lastname, password,
                    confirmPassword, email, birthdate, gender, phone, address
            );
            if (validation != null) {
                return validation;
            }

            
            Patient patient = (Patient) user;
            patient.setUsername(username.trim());
            patient.setFirstname(firstname.trim());
            patient.setLastname(lastname.trim());
            patient.setPassword(password);
            patient.setEmail(email.trim());
            patient.setBirthdate(LocalDate.parse(birthdate.trim()));
            patient.setGender(gender.trim().equalsIgnoreCase("Female"));
            patient.setPhone(Long.parseLong(phone.trim()));
            patient.setAddress(address.trim());

            return new Response("Patient updated successfully", Status.OK, UserSerializer.serialize(patient));

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    
    @Override
    public Response getPatient(String id) {
        try {
            if (!UserValidator.isValidId(id)) {
                return new Response("Id must be numeric, positive and have exactly 12 digits", Status.BAD_REQUEST);
            }

            long idLong = Long.parseLong(id.trim());
            User user = storage.getUserById(idLong);

            if (user == null) {
                return new Response("Patient not found", Status.NOT_FOUND);
            }
            if (!(user instanceof Patient)) {
                return new Response("User is not a patient", Status.BAD_REQUEST);
            }

            return new Response("Patient found", Status.OK, UserSerializer.serialize(user));

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response getAllPatients() {
        try {
            ArrayList<HashMap<String, Object>> patients = new ArrayList<>();
            for (User u : storage.getUsers()) {
                if (u instanceof Patient) {
                    patients.add(UserSerializer.serialize(u));
                }
            }

            HashMap<String, Object> data = new HashMap<>();
            data.put("patients", patients);
            return new Response("Patients retrieved successfully", Status.OK, data);

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
