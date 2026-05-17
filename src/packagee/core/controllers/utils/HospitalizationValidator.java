/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.core.controllers.utils;

import packagee.core.models.Hospitalization;
import packagee.core.models.enums.HospitalizationStatus;

/**
 *
 * @author Wanki
 */
public class HospitalizationValidator {

    public static boolean isValidDate(String date) {
        return UserValidator.isValidDate(date);
    }

    public static boolean canBeApproved(Hospitalization h) {
        return h.getStatus() == HospitalizationStatus.REQUESTED;
    }

    public static boolean canBeDenied(Hospitalization h) {
        return h.getStatus() == HospitalizationStatus.REQUESTED;
    }
}
