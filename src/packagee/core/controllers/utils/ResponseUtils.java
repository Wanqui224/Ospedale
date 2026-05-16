/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.core.controllers.utils;

/**
 *
 * @author Wanki
 */
public class ResponseUtils {
    public static boolean isSuccess(Response response) {
        return response.getStatus() >= 200 && response.getStatus() < 300;
    }

    public static boolean isClientError(Response response) {
        return response.getStatus() >= 400 && response.getStatus() < 500;
    }

    public static boolean isServerError(Response response) {
        return response.getStatus() >= 500;
    }
}
