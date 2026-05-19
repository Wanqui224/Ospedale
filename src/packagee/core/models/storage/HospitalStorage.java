package packagee.core.models.storage;

import java.util.ArrayList;
import packagee.core.models.Appointment;
import packagee.core.models.Hospitalization;
import packagee.core.models.User;

public class HospitalStorage implements IObservableStorage {

    private static HospitalStorage instance;

    private ArrayList<User> users;
    private ArrayList<Appointment> appointments;
    private ArrayList<Hospitalization> hospitalizations;
    private final ArrayList<IStorageObserver> observers = new ArrayList<>();

    private HospitalStorage() {
        this.appointments = new ArrayList<>();
        this.hospitalizations = new ArrayList<>();
        this.users = new JsonLoader().loadUsers();
    }

    public static HospitalStorage getInstance() {
        if (instance == null) {
            instance = new HospitalStorage();
        }
        return instance;
    }

    @Override
    public void addObserver(IStorageObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IStorageObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String eventType) {
        for (IStorageObserver observer : observers) {
            observer.onStorageChanged(eventType);
        }
    }

    @Override
    public ArrayList<User> getUsers() {
        return users;
    }

    @Override
    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    @Override
    public ArrayList<Hospitalization> getHospitalizations() {
        return hospitalizations;
    }

    @Override
    public User getUserById(long id) {
        for (User u : users) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public Appointment getAppointmentById(String id) {
        for (Appointment a : appointments) {
            if (a.getId().equals(id)) {
                return a;
            }
        }
        return null;
    }

    @Override
    public Hospitalization getHospitalizationById(String id) {
        for (Hospitalization h : hospitalizations) {
            if (h.getId().equals(id)) {
                return h;
            }
        }
        return null;
    }

    @Override
    public boolean addUser(User user) {
        for (User u : users) {
            if (u.getId() == user.getId()) {
                return false;
            }
        }
        users.add(user);
        notifyObservers("USER_CHANGED");
        return true;
    }

    @Override
    public void addAppointment(Appointment a) {
        appointments.add(a);
        notifyObservers("APPOINTMENT_CHANGED");
    }

    @Override
    public void addHospitalization(Hospitalization h) {
        hospitalizations.add(h);
        notifyObservers("HOSPITALIZATION_CHANGED");
    }
}