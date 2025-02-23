package org.app.client.layout.component;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class UserSlideBar {

    private static final String FILE_PATH = "C:\\Users\\User\\Desktop\\DII-GEN6-CompoundSEC\\main\\src\\main\\java\\org\\app\\db\\ResidentDB.json";
    private static final String BOOKED_ROOM_PATH = "C:\\Users\\User\\Desktop\\DII-GEN6-CompoundSEC\\main\\src\\main\\java\\org\\app\\db\\booked_rooms.json";
    private JPanel user;
    private List<JPanel> userCards;

    public UserSlideBar() {
        user = new JPanel();
        user.setLayout(new BoxLayout(user, BoxLayout.Y_AXIS)); // Arrange cards vertically
        userCards = new ArrayList<>();
    }

    public JScrollPane userSlideBar() {
        JScrollPane user_menage = new JScrollPane(user);
        user_menage.setPreferredSize(new Dimension(200, Frame.MAXIMIZED_VERT));
        return user_menage;
    }

    // Function to add user cards from data.json
    public void addUserCardsFromJson() {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(FILE_PATH); // Use the correct path
            DataWrapper dataWrapper = gson.fromJson(reader, DataWrapper.class);
            reader.close();

            // Add cards from the data read
            addUserCards(dataWrapper.data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addUserCards(List<FloorData> floorDataList) {
        for (FloorData floorData : floorDataList) {
            addUserCard(floorData.Name, floorData.Floor, floorData.Room, floorData.ID);
        }
    }

    private void modifyUser(JPanel card, String name, String floor) {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(card), "Modify User", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS)); // Set BoxLayout for vertical arrangement

        // Create input fields for name and floor
        JTextField nameField = new JTextField(name);
        JComboBox<String> floorSelect = new JComboBox<>(new String[]{"Low Floor", "Medium Floor", "High Floor"});
        floorSelect.setSelectedItem(floor);

        // Create buttons for save and cancel
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        // Add components to dialog with BoxLayout
        dialog.add(new JLabel("Name:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Floor:"));
        dialog.add(floorSelect);
        dialog.add(saveButton);
        dialog.add(cancelButton);

        // Action for cancel button
        cancelButton.addActionListener(e -> dialog.dispose());

        // Action for save button
        // Action for save button
        saveButton.addActionListener(e -> {
            String updatedName = nameField.getText();
            String updatedFloor = (String) floorSelect.getSelectedItem();

            // Retrieve the user ID from the card
            String userId = (String) card.getClientProperty("ID");

            // Remove the old card and add the updated card
            user.remove(card);
            userCards.remove(card);  // Remove the old card from the list

            // Add new card with updated info
            addUserCard(updatedName, updatedFloor, new ArrayList<>(), userId);

            updateUserInJson(userId, updatedName, updatedFloor);
            user.revalidate();
            user.repaint();

            dialog.dispose();
        });



        // Display the dialog
        dialog.setLocationRelativeTo(card);  // Center the dialog relative to the card
        dialog.setVisible(true);
    }
    private void updateUserInJson(String userId, String updatedName, String updatedFloor) {
        try {
            String jsonText = new String(Files.readAllBytes(Paths.get(FILE_PATH)), StandardCharsets.UTF_8);
            JSONObject jsonObj = new JSONObject(jsonText);

            if (!jsonObj.has("data") || jsonObj.isNull("data")) {
                System.out.println("Error: JSON does not contain 'data' key or it is null");
                return;
            }

            JSONArray dataArray = jsonObj.getJSONArray("data");
            boolean updated = false;

            // Loop through the JSONArray and find the user with the matching ID
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject user = dataArray.getJSONObject(i);
                if (user.optString("ID").equals(userId)) {
                    // Update the user information
                    user.put("Name", updatedName);
                    user.put("Floor", updatedFloor);
                    updated = true;
                    break;  // Exit the loop once the user is updated
                }
            }

            if (!updated) {
                System.out.println("Error: User ID " + userId + " not found in the JSON file");
                return;
            }

            jsonObj.put("data", dataArray);
            Files.write(Paths.get(FILE_PATH), jsonObj.toString(4).getBytes(StandardCharsets.UTF_8));

            System.out.println("User with ID: " + userId + " updated successfully!");
        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
        }
    }

    public void addUserCard(String name, String floor, List<String> rooms, String id) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        card.add(new JLabel(name + " - " + floor), BorderLayout.CENTER);

        // Store the ID in the card for easy access
        card.putClientProperty("ID", id);

        JButton modifyButton = new JButton("Modify");
        JButton revokeButton = new JButton("...");
        modifyButton.addActionListener(e -> modifyUser(card, name, floor));
        revokeButton.addActionListener(e -> revokeUser(card, rooms));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(modifyButton);
        buttonPanel.add(revokeButton);
        card.add(buttonPanel, BorderLayout.SOUTH);

        user.add(card);
        user.revalidate();
        user.repaint();
        userCards.add(card);
    }

    private void revokeUser(JPanel card, List<String> room) {
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to revoke this user?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            user.remove(card);
            userCards.remove(card);

            // Retrieve the ID from the card
            String userId = (String) card.getClientProperty("ID");
            String floor = (String) ((JLabel) card.getComponent(0)).getText().split(" - ")[1]; // Extract floor from label

            if (userId != null) {
                removeUserFromJson(userId);
                removeRoomFromBookedRoom(floor, room);  // Remove the corresponding room from Booked_room.json
            } else {
                System.out.println("Error: Cannot retrieve ID");
            }

            // Refresh UI once all changes are made
            user.revalidate();
            user.repaint();
        }
    }

    private void removeUserFromJson(String userId) {
        try {
            String jsonText = new String(Files.readAllBytes(Paths.get(FILE_PATH)), StandardCharsets.UTF_8);
            JSONObject jsonObj = new JSONObject(jsonText);

            if (!jsonObj.has("data") || jsonObj.isNull("data")) {
                System.out.println("Error: JSON does not contain 'data' key or it is null");
                return;
            }

            JSONArray dataArray = jsonObj.getJSONArray("data");
            boolean removed = false;

            // Loop through the JSONArray and find the user with the matching ID
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject user = dataArray.getJSONObject(i);
                if (user.optString("ID").equals(userId)) {
                    // User found, remove from the JSONArray
                    dataArray.remove(i);
                    removed = true;
                    break;  // Exit the loop once the user is removed
                }
            }

            if (!removed) {
                System.out.println("Error: User ID " + userId + " not found in the JSON file");
                return;
            }

            jsonObj.put("data", dataArray);
            Files.write(Paths.get(FILE_PATH), jsonObj.toString(4).getBytes(StandardCharsets.UTF_8));

            System.out.println("User with ID: " + userId + " removed successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.JSONException e) {
            System.out.println("Error: Invalid JSON structure");
            e.printStackTrace();
        }
    }

    private void removeRoomFromBookedRoom(String floor, List<String> roomsToRemove) {
        try {
            // Read the Booked_room.json file
            String jsonText = new String(Files.readAllBytes(Paths.get(BOOKED_ROOM_PATH)), StandardCharsets.UTF_8);
            JSONObject jsonObj = new JSONObject(jsonText);

            // Check if the floor exists in the JSON
            if (!jsonObj.has(floor) || jsonObj.isNull(floor)) {
                System.out.println("Error: JSON does not contain floor key or it is null");
                return;
            }


            // Get the room array for the floor
            JSONArray roomArray = jsonObj.getJSONObject(floor).getJSONArray("room");
            System.out.println("Current rooms in " + floor + ": " + roomArray.toString());  // Debugging

            JSONArray updatedRoomArray = new JSONArray();
            boolean roomRemoved = false;

            // Loop through the rooms to check if the room exists
            for (int i = 0; i < roomArray.length(); i++) {
                String roomName = roomArray.getString(i).trim();  // Trim whitespace
                System.out.println("Checking room: " + roomName);  // Debugging

                boolean removeThisRoom = false;
                for (String removeRoom : roomsToRemove) {
                    String trimmedRemoveRoom = removeRoom.trim();  // Trim the room name being passed in
                    System.out.println("Trying to remove: " + trimmedRemoveRoom);  // Debugging
                    if (trimmedRemoveRoom.equals(roomName)) {  // Trim the value being compared
                        removeThisRoom = true;
                        roomRemoved = true;
                        System.out.println("Room " + roomName + " removed.");
                        break;
                    }
                }

                // If the room should not be removed, add it to the new array
                if (!removeThisRoom) {
                    updatedRoomArray.put(roomName);
                }
            }

            // If rooms were removed, write the updated JSON back to the file
            if (roomRemoved) {
                jsonObj.getJSONObject(floor).put("room", updatedRoomArray);
                Files.write(Paths.get(BOOKED_ROOM_PATH), jsonObj.toString(4).getBytes(StandardCharsets.UTF_8));
                System.out.println("Room(s) removed successfully!");
            } else {
                System.out.println("Error: No matching room found in Booked_room.");
            }

        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
        }
    }

    // ฟังก์ชั่นเช็คว่าอาเรย์มีค่าที่ตรงกันหรือไม่
    private boolean contains(String[] arr, String value) {
        for (String str : arr) {
            if (str.equals(value)) {
                return true;
            }
        }
        return false;
    }

    private static class DataWrapper {
        List<FloorData> data;
    }

    public static class FloorData {
        String Floor;
        String ExpireDate;
        String ID;
        List<String> Room;
        String Name;
    }
}
