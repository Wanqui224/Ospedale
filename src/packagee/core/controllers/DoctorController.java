/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.core.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import packagee.core.controllers.utils.Response;
import packagee.core.controllers.utils.Status;
import packagee.core.controllers.utils.UserSerializer;
import packagee.core.controllers.utils.UserValidator;
import packagee.core.models.Doctor;
import packagee.core.models.User;
import packagee.core.models.enums.Specialty;
import packagee.core.models.storage.IHospitalStorage;

/**
 *
 * @author Wanki
 */
public class DoctorController implements IDoctorController {

    private final IHospitalStorage storage;

    public DoctorController(IHospitalStorage storage) {
        this.storage = storage;
    }

    private Response validateDoctorData(
            String username, String firstname, String lastname,
            String password, String confirmPassword,
            String specialty, String licenceNumber, String assignedOffice
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
        if (!isValidSpecialty(specialty)) {
            return new Response("Specialty is not valid", Status.BAD_REQUEST);
        }
        if (!UserValidator.isValidLicenceNumber(licenceNumber)) {
            return new Response("Licence number must follow the format L-XXXXXXXXXX MTL", Status.BAD_REQUEST);
        }
        if (!UserValidator.isValidOffice(assignedOffice)) {
            return new Response("Office must follow the format O-XXX", Status.BAD_REQUEST);
        }
        return null;
    }

    private boolean isValidSpecialty(String specialty) {
        try {
            Specialty.valueOf(specialty.trim().toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public Response registerDoctor(
            String id, String username, String firstname, String lastname,
            String password, String confirmPassword,
            String specialty, String licenceNumber, String assignedOffice
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

            Response validation = validateDoctorData(
                    username, firstname, lastname, password,
                    confirmPassword, specialty, licenceNumber, assignedOffice
            );
            if (validation != null) {
                return validation;
            }

            Doctor doctor = new Doctor(
                    idLong, username.trim(), firstname.trim(), lastname.trim(),
                    password, Specialty.valueOf(specialty.trim().toUpperCase()),
                    licenceNumber.trim(), assignedOffice.trim()
            );

            storage.addUser(doctor);
            return new Response("Doctor registered successfully", Status.CREATED, UserSerializer.serialize(doctor));

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response updateDoctor(
            String id, String username, String firstname, String lastname,
            String password, String confirmPassword,
            String specialty, String licenceNumber, String assignedOffice
    ) {
        try {
            if (!UserValidator.isValidId(id)) {
                return new Response("Id must be numeric, positive and have exactly 12 digits", Status.BAD_REQUEST);
            }

            long idLong = Long.parseLong(id.trim());
            User user = storage.getUserById(idLong);

            if (user == null) {
                return new Response("Doctor not found", Status.NOT_FOUND);
            }
            if (!(user instanceof Doctor)) {
                return new Response("User is not a doctor", Status.BAD_REQUEST);
            }

            User existingUsername = storage.getUserByUsername(username.trim());
            if (existingUsername != null && existingUsername.getId() != idLong) {
                return new Response("A user with that username already exists", Status.BAD_REQUEST);
            }

            Response validation = validateDoctorData(
                    username, firstname, lastname, password,
                    confirmPassword, specialty, licenceNumber, assignedOffice
            );
            if (validation != null) {
                return validation;
            }

            Doctor doctor = (Doctor) user;
            doctor.setUsername(username.trim());
            doctor.setFirstname(firstname.trim());
            doctor.setLastname(lastname.trim());
            doctor.setPassword(password);
            doctor.setSpecialty(Specialty.valueOf(specialty.trim().toUpperCase()));
            doctor.setLicenceNumber(licenceNumber.trim());
            doctor.setAssignedOffice(assignedOffice.trim());

            return new Response("Doctor updated successfully", Status.OK, UserSerializer.serialize(doctor));

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response getDoctor(String id) {
        try {
            if (!UserValidator.isValidId(id)) {
                return new Response("Id must be numeric, positive and have exactly 12 digits", Status.BAD_REQUEST);
            }

            long idLong = Long.parseLong(id.trim());
            User user = storage.getUserById(idLong);

            if (user == null) {
                return new Response("Doctor not found", Status.NOT_FOUND);
            }
            if (!(user instanceof Doctor)) {
                return new Response("User is not a doctor", Status.BAD_REQUEST);
            }

            return new Response("Doctor found", Status.OK, UserSerializer.serialize(user));

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response getAllDoctors() {
        try {
            ArrayList<HashMap<String, Object>> doctors = new ArrayList<>();
            for (User u : storage.getUsers()) {
                if (u instanceof Doctor) {
                    doctors.add(UserSerializer.serialize(u));
                }
            }

            HashMap<String, Object> data = new HashMap<>();
            data.put("doctors", doctors);
            return new Response("Doctors retrieved successfully", Status.OK, data);

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
