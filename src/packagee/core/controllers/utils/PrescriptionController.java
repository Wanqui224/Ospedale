/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.core.controllers.utils;

import java.util.HashMap;
import packagee.core.controllers.IPrescriptionController;
import packagee.core.models.Appointment;
import packagee.core.models.Prescription;
import packagee.core.models.storage.IHospitalStorage;

/**
 *
 * @author Wanki
 */
public class PrescriptionController implements IPrescriptionController {

    private final IHospitalStorage storage;

    public PrescriptionController(IHospitalStorage storage) {
        this.storage = storage;
    }

    @Override
    public Response prescribeMedication(
            String appointmentId, String medicationName, String dose,
            String administrationRoute, String treatmentDuration,
            String additionalInstructions, String frequency
    ) {
        try {
            Appointment appointment = storage.getAppointmentById(appointmentId.trim());
            if (appointment == null) {
                return new Response("Appointment not found", Status.NOT_FOUND);
            }
            if (!AppointmentValidator.canBePrescribed(appointment)) {
                return new Response("Can only prescribe on PENDING appointments", Status.BAD_REQUEST);
            }

            if (medicationName == null || medicationName.trim().isEmpty()) {
                return new Response("Medication name is required", Status.BAD_REQUEST);
            }

            double doseDouble;
            try {
                doseDouble = Double.parseDouble(dose.trim());
            } catch (NumberFormatException e) {
                return new Response("Dose must be numeric", Status.BAD_REQUEST);
            }

            int durationInt, frequencyInt;
            try {
                durationInt = Integer.parseInt(treatmentDuration.trim());
            } catch (NumberFormatException e) {
                return new Response("Treatment duration must be numeric", Status.BAD_REQUEST);
            }
            try {
                frequencyInt = Integer.parseInt(frequency.trim());
            } catch (NumberFormatException e) {
                return new Response("Frequency must be numeric", Status.BAD_REQUEST);
            }

            new Prescription(
                    appointment, medicationName.trim(), doseDouble,
                    administrationRoute.trim(), durationInt,
                    additionalInstructions.trim(), frequencyInt
            );

            HashMap<String, Object> data = new HashMap<>();
            data.put("appointmentId", appointmentId);
            data.put("medicationName", medicationName.trim());
            data.put("dose", doseDouble);
            data.put("administrationRoute", administrationRoute.trim());
            data.put("treatmentDuration", durationInt);
            data.put("additionalInstructions", additionalInstructions.trim());
            data.put("frequency", frequencyInt);

            return new Response("Medication prescribed successfully", Status.CREATED, data);

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}