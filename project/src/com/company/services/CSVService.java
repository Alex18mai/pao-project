package com.company.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVService {

    private static Object parseObject (Class type, String value) {
        if (type == String.class)
            return value;
        if (type == boolean.class)
            return Boolean.parseBoolean(value);
        if (type == int.class)
            return Integer.parseInt(value);
        if (type == long.class)
            return Long.parseLong(value);
        if (type == float.class)
            return Float.parseFloat(value);
        if (type == double.class)
            return Double.parseDouble(value);
        return null;
    }


    public static <T> List<T> read (String fileName, Class<T> classToRead) {
        try {
            // Declare variables
            BufferedReader fileBuffer = new BufferedReader(new FileReader(fileName));
            String line;
            List<T> ret = new ArrayList<>();

            // Get field names (for each column)
            line = fileBuffer.readLine();

            if (line == null) return ret; // CSV is empty

            List<String> fieldNames = new ArrayList<>(Stream.of(line.split(",")).map(elem -> elem.strip()).collect(Collectors.toList())); // Make line to ArrayList using stream

            // Get entities from each row

            while ((line = fileBuffer.readLine()) != null) {

                List<String> objectFields = new ArrayList<>(Stream.of(line.split(",")).map(elem -> elem.strip()).collect(Collectors.toList())); // Make line to ArrayList using stream

                var instance = classToRead.getConstructor().newInstance();

                for (int i=0; i < objectFields.size(); i++){

                    // Get the field from the column name (reflection)
                    try {
                        Field currentField = null;
                        Class type = instance.getClass();

                        // Go through all super classes until field is found
                        while (currentField == null && type != null) {
                            try {
                                currentField = type.getDeclaredField(fieldNames.get(i));
                            }
                            catch (Exception e) { }

                            type = type.getSuperclass();
                        }

                        currentField.setAccessible(true); // Make sure the field is accessible
                        var value = parseObject(currentField.getType(), objectFields.get(i));

                        if (value != null){
                            currentField.set(instance, value);
                        }
                    }
                    catch (Exception e){
                        System.out.printf("Error occurred while reading object from CSV : %s %n", e.getMessage());
                        e.printStackTrace();
                    }
                }

                ret.add(instance);
            }

            return ret;
        }
        catch (Exception e) {
            System.out.printf("Error occurred while reading the CSV file : %s %n", e.getMessage());
            return new ArrayList<>();
        }
    }


    private static void writeRow (List row, FileWriter file) {
        try{
            for (int i=0; i < row.size(); i++)
            {
                file.write(row.get(i).toString());
                if (i != row.size() - 1)
                {
                    file.write(", ");
                }
            }
            file.write('\n');
        }
        catch (Exception e){
            System.out.printf("Error occurred while writing row in CSV : %s %n", e.getMessage());
        }
    }


    public static <T> void write (List<T> objects, String fileName, Class<T> classToWrite) {
        // Create file (if it does not exist)
        try {
            new File(fileName).createNewFile();
        }
        catch (Exception e) {
            System.out.printf("Error occurred while creating the CSV file : %s %n", e.getMessage());
        }

        try {
            FileWriter file = new FileWriter(fileName);

            // Get all fields (superclasses included)
            List<Field> objectFields = new ArrayList<>();
            Class type = classToWrite;
            while (type != null) {
                // Add to front using stream
                objectFields = Stream.concat(Stream.of(type.getDeclaredFields()), objectFields.stream()).collect(Collectors.toList());
                type = type.getSuperclass();
            }

            List<String> fieldNames = objectFields.stream().map(field -> field.getName()).collect(Collectors.toList()); // Get all field names
            writeRow(fieldNames, file); // Write the field names

            // Write the objects
            for (var object : objects){

                // Create field list
                List<String> fieldValues = objectFields.stream().map(field -> {
                    try {
                        field.setAccessible(true); // Make sure the field is accessible
                        if (Collection.class.isAssignableFrom(field.getType())){
                            return "[]";
                        }
                        if (field.get(object).getClass().getPackage().getName().equals("com.company.entities")){
                            return "";
                        }
                        return field.get(object).toString();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        return "";
                    }
                }).collect(Collectors.toList());

                writeRow(fieldValues, file); // Write the field values
            }

            file.close();
        }
        catch (Exception e) {
            System.out.printf("Error occurred while writing to the CSV file : %s %n", e.getMessage());
        }
    }
}
