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
public class DoctorController {

    private final IHospitalStorage storage;

    public DoctorController(IHospitalStorage storage) {
        this.storage = storage;
    }
}
