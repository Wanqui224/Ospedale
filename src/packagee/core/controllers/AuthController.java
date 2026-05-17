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

            if (!UserValidator.isValidUsername(username)) {
                return new Response("Username is required", Status.BAD_REQUEST);
            }
            if (!UserValidator.isValidPassword(password)) {
                return new Response("Password is required", Status.BAD_REQUEST);
            }

            User user = storage.getUserByUsername(username.trim());
            if (user == null) {
                return new Response("User not found", Status.NOT_FOUND);
            }

            if (!user.getPassword().equals(password)) {
                return new Response("Incorrect password", Status.BAD_REQUEST);
            }

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

    public Response getUser(String id) {
        try {
            if (!UserValidator.isValidId(id)) {
                return new Response("Invalid id", Status.BAD_REQUEST);
            }

            User user = storage.getUserById(Long.parseLong(id.trim()));
            if (user == null) {
                return new Response("User not found", Status.NOT_FOUND);
            }

            return new Response("User found", Status.OK, UserSerializer.serialize(user));

        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
