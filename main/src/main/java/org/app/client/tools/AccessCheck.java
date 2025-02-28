package org.app.client.tools;

import org.app.server.enceypt.Encryption;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AccessCheck {
    private static AccessCheck instance;
    private final String FILE_PATH = System.getProperty("user.dir") + "\\main\\src\\main\\java\\org\\app\\db\\ResidentDB.json";
    private static JSONArray data;
    private static int userID;

    private AccessCheck() throws IOException {

    }
    public static AccessCheck getInstance() throws IOException {
        if (instance == null) {
            instance = new AccessCheck();
        }
        return instance;
    }
    private JSONArray loadData() throws IOException {
        String text = new String(Files.readAllBytes(Paths.get(FILE_PATH)), StandardCharsets.UTF_8);
        JSONObject jsonObj = new JSONObject(text);

        if (!jsonObj.has("data")) {
            System.out.println("ERROR: JSON does not contain 'data' key");
            return new JSONArray();
        }
        return jsonObj.getJSONArray("data");
    }
    public boolean checkUser(String username, String password) {
        try{
            data = loadData();
        }catch (IOException e){
            return false;
        }
        System.out.println("Checking user: " + username + ", " + password);
        for (int i = 0; i < data.length(); i++) {
            JSONObject user = data.getJSONObject(i);
            System.out.println(user.optString("ID"));
            if (Encryption.decrypt(user.optString("ID"))[0].equals(username) && user.optString("Password").trim().equals(password.trim())) {
                userID = i;
                System.out.println("User found: ID " + userID);
                return true;
            }
        }
        userID = -1;
        System.out.println("User not found");
        return false;
    }
    public boolean checkUserFloor(String floor) {
        try{
            data = loadData();
        }catch (IOException e){
            return false;
        }
        if (userID < 0 || userID >= data.length()) {
            System.out.println("Invalid userID: " + userID);
            return false;
        }
        JSONObject user = data.getJSONObject(userID);
        System.out.println("Checking floor: " + floor + " for userID " + userID);
        return user.optString("Floor").equals(floor);
    }
    public boolean checkUserRoom(String room) {
        try{
            data = loadData();
        }catch (IOException e){
            return false;
        }
        if (userID < 0 || userID >= data.length()) return false;
        JSONObject user = data.getJSONObject(userID);

        if (!user.has("Room")) return false;
        JSONArray rooms = user.getJSONArray("Room");

        for (int j = 0; j < rooms.length(); j++) {
            if (rooms.optString(j).equalsIgnoreCase(room)) { // เปรียบเทียบแบบไม่สนตัวพิมพ์เล็กใหญ่
                return true;
            }
        }
        return false;
    }
    public String getUserID() {
        try{
            data = loadData();
        }catch (IOException e){
            return "";
        }
        JSONObject user = data.getJSONObject(userID);
        return user.optString("ID");
    }
}
