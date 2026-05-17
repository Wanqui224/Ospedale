/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package packagee.core.controllers;

import packagee.core.controllers.utils.Response;

/**
 *
 * @author Wanki
 */
public interface IDoctorController {

    Response registerDoctor(
            String id,
            String username,
            String firstname,
            String lastname,
            String password,
            String confirmPassword,
            String specialty,
            String licenceNumber,
            String assignedOffice
    );

    Response updateDoctor(
            String id,
            String username,
            String firstname,
            String lastname,
            String password,
            String confirmPassword,
            String specialty,
            String licenceNumber,
            String assignedOffice
    );

    Response getDoctor(String id);

    Response getAllDoctors();

    Response getDoctorAppointmentIds(String doctorId);
}
