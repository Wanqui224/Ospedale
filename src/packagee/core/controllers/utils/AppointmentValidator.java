/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.core.controllers.utils;

import java.time.LocalDateTime;
import packagee.core.models.Appointment;
import packagee.core.models.Doctor;
import packagee.core.models.enums.AppointmentStatus;

/**
 *
 * @author Wanki
 */
public class AppointmentValidator {

    public static boolean isValidDate(String date) {
        return UserValidator.isValidDate(date);
    }

    public static boolean isValidTime(String time) {
        return UserValidator.isValidTime(time);
    }

    public static boolean isDoctorAvailable(Doctor doctor, LocalDateTime requestedTime) {
        LocalDateTime end = requestedTime.plusMinutes(15);
        for (Appointment a : doctor.getAppointments()) {
            if (a.getStatus() == AppointmentStatus.CANCELED) {
                continue;
            }
            LocalDateTime existingStart = a.getDatetime();
            LocalDateTime existingEnd = existingStart.plusMinutes(15);
            if (requestedTime.isBefore(existingEnd) && end.isAfter(existingStart)) {
                return false;
            }
        }
        return true;
    }

    public static boolean canBeCanceled(Appointment a) {
        return a.getStatus() != AppointmentStatus.COMPLETED;
    }

    public static boolean canBePrescribed(Appointment a) {
        return a.getStatus() == AppointmentStatus.PENDING;
    }

    public static boolean canBeCompleted(Appointment a) {
        return a.getStatus() == AppointmentStatus.PENDING;
    }

    public static boolean canBeAccepted(Appointment a) {
        return a.getStatus() == AppointmentStatus.REQUESTED;
    }

    public static boolean canBeRescheduled(Appointment a) {
        return a.getStatus() == AppointmentStatus.REQUESTED
                || a.getStatus() == AppointmentStatus.PENDING;
    }
}
