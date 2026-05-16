/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.core.controllers;

import packagee.core.controllers.utils.Response;
import packagee.core.controllers.utils.Status;
import packagee.core.controllers.utils.UserSerializer;
import packagee.core.controllers.utils.UserValidator;
import packagee.core.models.User;
import packagee.core.models.storage.IHospitalStorage;

/**
 *
 * @author Wanki
 */
public class AuthController {

    private final IHospitalStorage storage;

    public AuthController(IHospitalStorage storage) {
        this.storage = storage;
    }

    public Response login(String username, String password) {
        try {
            // Validar — delegado a UserValidator (S)
            if (!UserValidator.isValidUsername(username)) {
                return new Response("Username is required", Status.BAD_REQUEST);
            }
            if (!UserValidator.isValidPassword(password)) {
                return new Response("Password is required", Status.BAD_REQUEST);
            }

            // Buscar usuario
            User user = storage.getUserByUsername(username.trim());
            if (user == null) {
                return new Response("User not found", Status.NOT_FOUND);
            }

            // Verificar password
            if (!user.getPassword().equals(password)) {
                return new Response("Incorrect password", Status.BAD_REQUEST);
            }

            // Serializar — delegado a UserSerializer (S)
            return new Response(
                    "Login successful",
                    Status.OK,
                    UserSerializer.serialize(user)
            );

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public Response logout() {
        try {
            return new Response("Logout successful", Status.OK);
        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
