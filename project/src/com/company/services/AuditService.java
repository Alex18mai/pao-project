package com.company.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {

    static AuditService instance = null;
    static String status = "Not created";

    //get instance
    public static AuditService getInstance()
    {
        if (instance == null) {
            status = "Created";
            instance = new AuditService();
        }
        return instance;
    }

    //constructor
    private AuditService() { }

    public void audit(String message)
    {
        try {
            BufferedWriter file = new BufferedWriter(new FileWriter("src/com/company/data/audit.csv", true));

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String date = dtf.format(now);

            file.write(message + ", " + date + "\n");
            file.close();
        }
        catch (Exception e){
            System.out.printf("Error occurred while writing to the audit file : %s %n", e.getMessage());
        }
    }

}
