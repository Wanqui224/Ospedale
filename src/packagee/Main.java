package packagee;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIManager;
import packagee.core.controllers.AuthController;
import packagee.core.controllers.DoctorController;
import packagee.core.controllers.HospitalizationManagementController;
import packagee.core.controllers.HospitalizationQueryController;
import packagee.core.controllers.HospitalizationRequestController;
import packagee.core.controllers.PatientController;
import packagee.core.controllers.utils.AppointmentManagementController;
import packagee.core.controllers.utils.AppointmentQueryController;
import packagee.core.controllers.utils.AppointmentRequestController;
import packagee.core.controllers.utils.ControllerContainer;
import packagee.core.controllers.utils.PrescriptionController;
import packagee.core.models.storage.HospitalStorage;
import packagee.core.models.storage.IObservableStorage;
import packagee.core.views.LoginView;

public class Main {
    public static void main(String[] args) {
        IObservableStorage storage = HospitalStorage.getInstance();
        ControllerContainer controllers = new ControllerContainer(
                new AuthController(storage),
                new PatientController(storage),
                new DoctorController(storage),
                new AppointmentRequestController(storage),
                new AppointmentManagementController(storage),
                new AppointmentQueryController(storage),
                new PrescriptionController(storage),
                new HospitalizationRequestController(storage),
                new HospitalizationManagementController(storage),
                new HospitalizationQueryController(storage),
                storage
        );
        System.setProperty("flatlaf.useNativeLibrary", "false");
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        java.awt.EventQueue.invokeLater(()
                -> new LoginView(controllers).setVisible(true)
        );
    }
}