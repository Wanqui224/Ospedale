/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.core.controllers;

import packagee.core.controllers.utils.Response;
import packagee.core.models.storage.IHospitalStorage;

/**
 *
 * @author Wanki
 */
public class PatientController {

    private final IHospitalStorage storage;

    public PatientController(IHospitalStorage storage) {
        this.storage = storage;
    }
}
