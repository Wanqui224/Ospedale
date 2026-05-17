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
public interface IHospitalizationRequestController {
    Response requestHospitalization(
        String patientId,
        String doctorId,
        String date,
        String reason,
        String roomType,
        String observations
    );
}