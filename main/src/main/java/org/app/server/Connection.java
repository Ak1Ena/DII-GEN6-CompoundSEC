package org.app.server;

import org.json.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Connection {
    private String database;
    public void setDB(String dbname){
        this.database = switch (dbname){
            case "Resident" -> "ResidentDB.json";
            case "Visitor" -> "VisitorDB.json";
            default -> throw new IllegalStateException("Unexpected value: " + dbname);
        };
    }

    public String[] getID(String searchID){
        List<String> data = new ArrayList<>();
        try {
            if (database.isEmpty()) {
                return null;
            }
            String content = new String(Files.readAllBytes(Paths.get("main\\src\\main\\java\\org\\app\\db\\" + database)));
            JSONObject json = new JSONObject(content);
            JSONArray residents = json.getJSONArray("data");

            for (int i = 0; i < residents.length(); i++) {
                JSONObject resident = residents.getJSONObject(i);
                if (resident.getString("ID").equals(searchID)) {
                   for (String key : resident.keySet()){
                       data.add(resident.get(key).toString());
                   }
                    return data.toArray(new String[0]);
                }
            }
            return null;
        }catch (IOException e){
            return null;
        }

    }




    /*test zone
    public static void main(String[] args){
        Connection dbManger = new Connection();
        dbManger.setDB("Resident");
        String[] result = dbManger.getID("ENCRYPT_DATA");
        if (result != null){
            for (int i = 0; i < result.length; i++) {
                System.out.println(result[i]);
            }
        }

     */

}
