/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package packagee.core.models;

import java.util.ArrayList;

/**
 *
 * @author Wanki
 */
public interface ISchedulable {

    ArrayList<Appointment> getAppointments();

    void addAppointment(Appointment a);
}
