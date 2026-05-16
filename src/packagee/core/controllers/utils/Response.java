/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.core.controllers.utils;

import java.util.HashMap;

/**
 *
 * @author Wanki
 */
public class Response {

    private final String message;
    private final int status;
    private final HashMap<String, Object> data;

    public Response(String message, int status) {
        this.message = message;
        this.status = status;
        this.data = null;
    }

    public Response(String message, int status, HashMap<String, Object> data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public HashMap<String, Object> getData() {
        return data;
    }
}
