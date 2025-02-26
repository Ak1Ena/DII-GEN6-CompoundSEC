package org.app.client.layout.component;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.app.db.Logs;
import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserSlideBar {
    private Logs logs = Logs.getInstance();
    private static final String FILE_PATH = System.getProperty("user.dir") + "\\main\\src\\main\\java\\org\\app\\db\\ResidentDB.json";
    private static final String BOOKED_ROOM_PATH = System.getProperty("user.dir") + "\\main\\src\\main\\java\\org\\app\\db\\booked_rooms.json";
    private JPanel user;
    private List<JPanel> userCards;

    public UserSlideBar() {
        user = new JPanel();
        user.setLayout(new BoxLayout(user, BoxLayout.Y_AXIS));
        userCards = new ArrayList<>();
    }
    public void filterUserCardsByRoom(String roomNumber) {
        user.removeAll();  // เคลียร์การ์ดทั้งหมดก่อน
        boolean found = false;

        for (JPanel card : userCards) {
            JLabel roomLabel = (JLabel) ((JPanel) card.getComponent(0)).getComponent(2); // ดึง JLabel ห้อง
            String roomText = roomLabel.getText();

            if (roomText.contains(roomNumber)) {
                user.add(card); // เพิ่มการ์ดที่ตรงกับเลขห้อง
                found = true;
            }
        }

        if (!found) {
            JPanel notFoundPanel = new JPanel();
            notFoundPanel.add(new JLabel("Room not found!"));
            user.add(notFoundPanel);
        }

        user.revalidate();
        user.repaint();
    }

    public JScrollPane userSlideBar() {
        JScrollPane user_menage = new JScrollPane(user);
        user_menage.setPreferredSize(new Dimension(200, Frame.MAXIMIZED_VERT));
        return user_menage;
    }

    public void addUserCardsFromJson() {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(FILE_PATH);
            DataWrapper dataWrapper = gson.fromJson(reader, DataWrapper.class);
            reader.close();

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

    private void modifyUser(JPanel card, String name, String floor, List<String> rooms) {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(card), "Modify User", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));

        JTextField nameField = new JTextField(name);
        JTextField expireDateField = new JTextField();

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        dialog.add(new JLabel("Name:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Days :"));
        dialog.add(expireDateField);
        dialog.add(saveButton);
        dialog.add(cancelButton);

        cancelButton.addActionListener(e -> dialog.dispose());

        saveButton.addActionListener(e -> {
            try {
                String updatedName = nameField.getText();
                int dayToAdd = Integer.parseInt(expireDateField.getText());
            if (dayToAdd >= 0 & !updatedName.isEmpty()) {
                LocalDate currentDate = LocalDate.now();
                LocalDate modifyExpireDate = currentDate.plusDays(dayToAdd);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


                String updatedExpireDate = modifyExpireDate.format(formatter);
                String userId = (String) card.getClientProperty("ID");

                user.remove(card);
                userCards.remove(card);
                addUserCard(updatedName, floor, rooms, userId);

                updateUserInJson(userId, updatedName, rooms, updatedExpireDate);
                user.revalidate();
                user.repaint();

                dialog.dispose();
            }else {
                JOptionPane.showMessageDialog(dialog, "Please enter correctly!", "Error", JOptionPane.ERROR_MESSAGE);

            }
            }catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(dialog, "Please enter correctly!", "Error", JOptionPane.ERROR_MESSAGE);

            }
        });

        dialog.setLocationRelativeTo(card);
        dialog.setVisible(true);
    }

    private void updateUserInJson(String userId, String updatedName, List<String> updatedRooms, String updatedExpireDate) {
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
                    user.put("Room", new JSONArray(updatedRooms));
                    user.put("Expire date", updatedExpireDate);
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
            Logs logs = Logs.getInstance();
            logs.addToLogs("Admin","Modify",userId,"SUCCESS");
        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
        }
    }

    public void addUserCard(String name, String floor, List<String> rooms, String id) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.add(new JLabel("UserID : " + id));
        panel.add(new JLabel("Floor : " + floor));
        panel.add(new JLabel("Room : " + String.join(", ", rooms)));

        card.add(panel, BorderLayout.CENTER);
        card.putClientProperty("ID", id);

        JButton modifyButton = new JButton("Modify");
        JButton revokeButton = new JButton("Revoke");
        modifyButton.addActionListener(e -> modifyUser(card, name, floor, rooms));
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
            Logs logs = Logs.getInstance();
            logs.addToLogs("Admin","Revoke",userId,"SUCCESS");
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
        String ExpireDate;
    }
}
