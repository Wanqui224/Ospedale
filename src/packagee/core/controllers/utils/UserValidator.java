package packagee.core.controllers.utils;

import java.time.LocalDate;

public class UserValidator {

    public static boolean isValidUsername(String username) {
        return username != null && !username.trim().isEmpty();
    }

    public static boolean isValidPassword(String password) {
        return password != null && !password.trim().isEmpty();
    }

    public static boolean isValidId(String id) {
        try {
            long idLong = Long.parseLong(id.trim());
            return String.valueOf(idLong).length() == 12 && idLong > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidPhone(String phone) {
        try {
            long phoneLong = Long.parseLong(phone.trim());
            return String.valueOf(phoneLong).length() == 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[^@]+@[^@]+\\.com$");
    }

    public static boolean isValidBirthdate(String birthdate) {
        if (birthdate == null || !birthdate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            return false;
        }
        try {
            LocalDate.parse(birthdate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidLicenceNumber(String licence) {
        return licence != null && licence.matches("^L-\\d{10} MTL$");
    }

    public static boolean isValidOffice(String office) {
        return office != null && office.matches("^O-\\d{3}$");
    }

    public static boolean isValidDate(String date) {
        if (date == null || !date.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            return false;
        }
        try {
            LocalDate.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidTime(String time) {
        if (time == null || !time.matches("^\\d{2}:\\d{2}$")) {
            return false;
        }
        int minutes = Integer.parseInt(time.substring(3));
        return minutes == 0 || minutes == 15 || minutes == 30 || minutes == 45;
    }

    public static String normalizeSpecialty(String specialty) {
        return specialty.trim()
                .toUpperCase()
                .replaceAll(" & ", "_")
                .replaceAll(" ", "_");
    }
}