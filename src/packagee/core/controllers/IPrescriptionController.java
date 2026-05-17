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
public interface IPrescriptionController {
    Response prescribeMedication(
        String appointmentId,
        String medicationName,
        String dose,
        String administrationRoute,
        String treatmentDuration,
        String additionalInstructions,
        String frequency
    );
}