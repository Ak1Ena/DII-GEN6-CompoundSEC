package org.app.testing_playground.app.client.tools;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.*;
import java.util.Scanner;

public class JSONWriter {
    public void addData(String[] data, String file_path) {
        JSONArray jsonArray = new JSONArray();
        File file = new File(file_path);

        // อ่านข้อมูลจากไฟล์ JSON
        try (Scanner scanner = new Scanner(file)) {
            StringBuilder jsonData = new StringBuilder();
            while (scanner.hasNextLine()) {
                jsonData.append(scanner.nextLine());
            }
            // ถ้าข้อมูลไม่ว่างและเป็น JSON Array ที่ถูกต้อง
            if (!jsonData.toString().isEmpty()) {
                jsonArray = new JSONArray(jsonData.toString());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Creating a new file.");
        } catch (Exception e) {
            System.out.println("Error reading JSON data: " + e.getMessage());
        }

        // สร้างข้อมูลใหม่
        JSONObject newData = new JSONObject();
        newData.put("ID", data[0]);
        newData.put("Name", data[1]);
        newData.put("Number of people", data[2]);

        // เพิ่มข้อมูลใหม่ลงใน JSONArray
        jsonArray.put(newData);

        // เขียนข้อมูลกลับไปยังไฟล์ JSON
        try (FileWriter writer = new FileWriter(file_path)) {
            writer.write(jsonArray.toString(4)); // Format JSON ด้วย indentation
            System.out.println("Data added successfully!");
        } catch (IOException e) {
            System.out.println("Error writing JSON data: " + e.getMessage());
        }
    }
    public void addRoom(String category, String room,String file_path) {
        try {
            JSONObject json;
            try (BufferedReader reader = new BufferedReader(new FileReader(file_path))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                json = new JSONObject(content.toString());
            } catch (Exception e) {
                json = new JSONObject();
            }

            JSONArray roomArray;
            if (json.has(category)) {
                JSONObject categorySection = json.getJSONObject(category);
                roomArray = categorySection.getJSONArray("room");
            } else {
                roomArray = new JSONArray();
                json.put(category, new JSONObject().put("room", roomArray));
            }

            roomArray.put(room);

            try (FileWriter fileWriter = new FileWriter(file_path)) {
                fileWriter.write(json.toString(4));
            }

        } catch (IOException e) {
            System.err.println("Error writing to booked rooms JSON: " + e.getMessage());
        }
    }
    public void removeRoom(String category, String room,String file_path) {
        try {
            JSONObject json;
            try (BufferedReader reader = new BufferedReader(new FileReader(file_path))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                json = new JSONObject(content.toString());
            } catch (Exception e) {
                System.err.println("Booked rooms file not found or empty. Creating new JSON.");
                json = new JSONObject();
            }

            if (json.has(category)) {
                JSONObject categorySection = json.getJSONObject(category);
                JSONArray roomArray = categorySection.getJSONArray("room");
                for (int i = 0; i < roomArray.length(); i++) {
                    if (roomArray.getString(i).equals(room)) {
                        roomArray.remove(i);
                        break;
                    }
                }

            }

            try (FileWriter fileWriter = new FileWriter(file_path)) {
                fileWriter.write(json.toString(4));
            }

        } catch (IOException e) {
            System.err.println("Error writing to booked rooms JSON: " + e.getMessage());
        }
    }
}
