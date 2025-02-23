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
            FileReader reader = new FileReader(FILE_PATH);
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
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));

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
        saveButton.addActionListener(e -> {
            String updatedName = nameField.getText();
            String updatedFloor = (String) floorSelect.getSelectedItem();

            // Retrieve the user ID from the card
            String userId = (String) card.getClientProperty("ID");

            // Remove the old card and add the updated card
            user.remove(card);
            userCards.remove(card);

            // Add new card with updated info
            addUserCard(updatedName, updatedFloor, new ArrayList<>(), userId);

            updateUserInJson(userId, updatedName, updatedFloor);
            user.revalidate();
            user.repaint();

            dialog.dispose();
        });

        // Display the dialog
        dialog.setLocationRelativeTo(card);
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

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject user = dataArray.getJSONObject(i);
                if (user.optString("ID").equals(userId)) {
                    user.put("Name", updatedName);
                    user.put("Floor", updatedFloor);
                    updated = true;
                    break;
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

        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(new JLabel("UserID : " + id));
        panel.add(new JLabel("Floor : " + floor));
        panel.add(new JLabel("Room : " + rooms));

        card.add(panel, BorderLayout.CENTER);

        card.putClientProperty("ID", id);

        JButton modifyButton = new JButton("Modify");
        JButton revokeButton = new JButton("Revoke");
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

            String userId = (String) card.getClientProperty("ID");

            if (userId != null) {
                removeUserFromJson(userId);
                removeRoomFromBookedRoom(room);
            }

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

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject user = dataArray.getJSONObject(i);
                if (user.optString("ID").equals(userId)) {
                    dataArray.remove(i);
                    removed = true;
                    break;
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
        }
    }

    private void removeRoomFromBookedRoom(List<String> roomsToRemove) {
        try {
            String jsonText = new String(Files.readAllBytes(Paths.get(BOOKED_ROOM_PATH)), StandardCharsets.UTF_8);
            JSONObject jsonObj = new JSONObject(jsonText);
            boolean roomRemoved = false;

            for (String floor : jsonObj.keySet()) {
                JSONObject floorObject = jsonObj.getJSONObject(floor);
                JSONArray roomArray = floorObject.getJSONArray("room");
                JSONArray updatedRooms = new JSONArray();

                for (int i = 0; i < roomArray.length(); i++) {
                    String room = roomArray.getString(i);
                    if (!roomsToRemove.contains(room)) {
                        updatedRooms.put(room);
                    } else {
                        roomRemoved = true;
                    }
                }

                floorObject.put("room", updatedRooms);
            }

            if (roomRemoved) {
                Files.write(Paths.get(BOOKED_ROOM_PATH), jsonObj.toString(4).getBytes(StandardCharsets.UTF_8));
                System.out.println("Room(s) removed successfully.");
            }
        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
        }
    }

    public static class DataWrapper {
        List<FloorData> data;
    }

    public static class FloorData {
        String Name;
        String Floor;
        List<String> Room;
        String ID;
    }
}
