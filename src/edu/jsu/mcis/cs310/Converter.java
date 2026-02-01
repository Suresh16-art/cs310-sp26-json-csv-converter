package edu.jsu.mcis.cs310;

import com.github.cliftonlabs.json_simple.*;
import com.opencsv.*;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;



public class Converter {
    
    /*
        
        Consider the following CSV data, a portion of a database of episodes of
        the classic "Star Trek" television series:
        
        "ProdNum","Title","Season","Episode","Stardate","OriginalAirdate","RemasteredAirdate"
        "6149-02","Where No Man Has Gone Before","1","01","1312.4 - 1313.8","9/22/1966","1/20/2007"
        "6149-03","The Corbomite Maneuver","1","02","1512.2 - 1514.1","11/10/1966","12/9/2006"
        
        (For brevity, only the header row plus the first two episodes are shown
        in this sample.)
    
        The corresponding JSON data would be similar to the following; tabs and
        other whitespace have been added for clarity.  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings and which values should be encoded as integers, as
        well as the overall structure of the data:
        
        {
            "ProdNums": [
                "6149-02",
                "6149-03"
            ],
            "ColHeadings": [
                "ProdNum",
                "Title",
                "Season",
                "Episode",
                "Stardate",
                "OriginalAirdate",
                "RemasteredAirdate"
            ],
            "Data": [
                [
                    "Where No Man Has Gone Before",
                    1,
                    1,
                    "1312.4 - 1313.8",
                    "9/22/1966",
                    "1/20/2007"
                ],
                [
                    "The Corbomite Maneuver",
                    1,
                    2,
                    "1512.2 - 1514.1",
                    "11/10/1966",
                    "12/9/2006"
                ]
            ]
        }
        
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
        
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including examples.
        
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String result = "{}"; // default return value; replace later!
        
        try {
            
        
            // INSERT YOUR CODE HERE
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> rows = reader.readAll();
            JsonObject obj = new JsonObject();
            JsonArray colHeading = new JsonArray();
            colHeading.addAll(Arrays.asList(rows.get(0)));
            obj.put("ColHeadings", colHeading);
            JsonArray data = new JsonArray();
            JsonArray prodNums = new JsonArray();
            
            for (int i = 1; i < rows.size(); i++) {
                String[] row = rows.get(i);
                
                prodNums.add(row[0]);
                
                JsonArray value = new JsonArray();
                value.add(row[1]);
                
                value.add(Integer.valueOf(row[2]));
                
                value.add(Integer.valueOf(row[3]));
                
                value.add(row[4]);
                value.add(row[5]);
                value.add(row[6]);
                
                data.add(value);
                
            }

            obj.put("ProdNums", prodNums);
            obj.put("Data", data);
            
            result = Jsoner.serialize(obj);
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result.trim();
        
    }
    
    @SuppressWarnings("unchecked")
    public static String jsonToCsv(String jsonString) {
        
        String result = ""; // default return value; replace later!
        
        try {
            
            // INSERT YOUR CODE HERE
           
            
            JsonObject obj = (JsonObject) Jsoner.deserialize(jsonString);
            
            JsonArray colHeading = (JsonArray) obj.get("ColHeadings");
            JsonArray prodNums = (JsonArray) obj.get("ProdNums");
            JsonArray data = (JsonArray) obj.get("Data");
            
            StringWriter stringWriter = new StringWriter();
            CSVWriter writer = new CSVWriter(stringWriter);
            
            String[] header = new String[colHeading.size()];
            for (int i = 0; i < colHeading.size(); i++) {
                header[i] = colHeading.getString(i);
            }
            
            writer.writeNext(header);
            
            for (int i = 0; i< data.size(); i++) {
                JsonArray value = (JsonArray) data.get(i);
                
                String[] row = new String[header.length];
                row[0] = prodNums.getString(i);
                row[1] = value.getString(0);
                
                row[2] = ((Number) value.get(1)).toString();
                
                
                int episode = ((Number) value.get(2)).intValue();
                row[3] = String.format("%02d", episode);
                
                row[4] = value.getString(3);
                row[5] = value.getString(4);
                row[6] = value.getString(5);
                
                writer.writeNext(row);
                
            }
            writer.close();
            result=stringWriter.toString();

            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result.trim();
    }
}
        
    
    

