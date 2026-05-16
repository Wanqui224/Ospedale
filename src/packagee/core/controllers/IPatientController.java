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
public interface IPatientController {

    Response registerPatient(
            String id,
            String username,
            String firstname,
            String lastname,
            String password,
            String confirmPassword,
            String email,
            String birthdate,
            String gender,
            String phone,
            String address
    );

    Response updatePatient(
            String id,
            String username,
            String firstname,
            String lastname,
            String password,
            String confirmPassword,
            String email,
            String birthdate,
            String gender,
            String phone,
            String address
    );

    Response getPatient(String id);

    Response getAllPatients();
}
