package packagee.core.controllers.utils;

import javax.swing.JFrame;

import packagee.core.models.Administrator;
import packagee.core.models.Doctor;
import packagee.core.models.Patient;
import packagee.core.models.User;

import packagee.core.views.AdminView;
import packagee.core.views.DoctorView;
import packagee.core.views.LoginView;
import packagee.core.views.PatientView;

import java.util.ArrayList;

import packagee.core.models.Appointment;
import packagee.core.models.Hospitalization;

public class NavigationController {

    public NavigationController(ControllerContainer controllers) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void navigateByRole(
            User user,
            User temp,
            ArrayList<User> users,
            ArrayList<Hospitalization> hospitalizations,
            ArrayList<Appointment> appointments,
            JFrame currentView
    ) {

        currentView.dispose();

        if (user instanceof Administrator) {

            AdminView adminView =
                    new AdminView(
                            user,
                            temp,
                            users,
                            hospitalizations,
                            appointments
                    );

            adminView.setVisible(true);

        } else if (user instanceof Doctor) {

            DoctorView doctorView =
                    new DoctorView(
                            user,
                            temp,
                            users,
                            hospitalizations,
                            appointments
                    );

            doctorView.setVisible(true);

        } else if (user instanceof Patient) {

            PatientView patientView =
                    new PatientView(
                            user,
                            temp,
                            users,
                            hospitalizations,
                            appointments
                    );

            patientView.setVisible(true);
        }
    }

    public void openLogin(JFrame currentView) {

        currentView.dispose();

        LoginView loginView =
                new LoginView();

        loginView.setVisible(true);
    }
}