package org.app.client.tools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
public class AccessCheck {

    private final String FILE_PATH = "C:\\Users\\User\\Desktop\\DII-GEN6-CompoundSEC\\main\\src\\main\\java\\org\\app\\db\\ResidentDB.json";
    private static JSONArray data;
    private static int userID;

    public AccessCheck() throws IOException {
        data = loadData();
    }

    private JSONArray loadData() throws IOException {
        String text = new String(Files.readAllBytes(Paths.get(FILE_PATH)), StandardCharsets.UTF_8);
        JSONObject jsonObj = new JSONObject(text);

        if (!jsonObj.has("data")) {
            System.out.println("ERROR: JSON does not contain 'data' key");
            return new JSONArray();
        }
        System.out.println("Loaded JSON: " + jsonObj.toString(2)); // Debug JSON Output
        return jsonObj.getJSONArray("data");
    }

    public boolean checkUser(String username, String password) {
        System.out.println("Checking user: " + username + ", " + password);
        for (int i = 0; i < data.length(); i++) {
            JSONObject user = data.getJSONObject(i);
            if (user.optString("Name").equals(username) && user.optString("Password").trim().equals(password.trim())) {
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
        if (userID < 0 || userID >= data.length()) {
            System.out.println("Invalid userID: " + userID);
            return false;
        }
        JSONObject user = data.getJSONObject(userID);
        System.out.println("Checking floor: " + floor + " for userID " + userID);
        return user.optString("Floor").equals(floor);
    }

    public boolean checkUserRoom(String room) {
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

    public static void main(String[] args) throws IOException {
        org.app.client.tools.AccessCheck ac = new org.app.client.tools.AccessCheck();

        System.out.println("Login status: " + ac.checkUser("TEST", "5jZ06A"));
        System.out.println("Check floor: " + ac.checkUserFloor("Low"));
        System.out.println("Check room: " + ac.checkUserRoom("A101"));
    }

    public String getUserID() {
        JSONObject user = data.getJSONObject(userID);
        return user.optString("ID");
    }
}

