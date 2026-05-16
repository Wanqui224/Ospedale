package packagee.core.models.storage;

import java.util.ArrayList;
import packagee.core.models.Appointment;
import packagee.core.models.Hospitalization;
import packagee.core.models.User;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
/**
 *
 * @author Wanki
 */
public interface IHospitalStorage {

    ArrayList<User> getUsers();

    ArrayList<Appointment> getAppointments();

    ArrayList<Hospitalization> getHospitalizations();

    User getUserById(long id);

    User getUserByUsername(String username);

    Appointment getAppointmentById(String id);

    Hospitalization getHospitalizationById(String id);

    boolean addUser(User user);

    void addAppointment(Appointment a);

    void addHospitalization(Hospitalization h);
}
