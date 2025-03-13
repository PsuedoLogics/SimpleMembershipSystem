package org.michaelmathews.memba_membershipsmadeeasy;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GenerateMembershipFromData {

    static File file = new File("src/memberships.txt");
    static ArrayList<String> membershipInfoList;

    public static void authorize(String description, int frequency, boolean recurring, double cost, double oneTimeFee){
        System.out.println("Description: " + description + " frequency: " + frequency + " recurring? "+recurring + " cost: " + cost + " Sign up fee: " + oneTimeFee + "\n");

        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write(description + ",");
            writer.write(frequency + ",");
            writer.write(recurring + ",");
            writer.write(cost + ",");
            writer.write(Double.toString(oneTimeFee));
            writer.write("%%SEPERATE%%");
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void getExistingMembershipsFromFile(){



       try(BufferedReader br = new BufferedReader(new FileReader(file))) {
           String line;
            while ((line = br.readLine()) != null )
           {
               String[] tempArray = line.split("%%SEPERATE%%");

              // membershipInfoList.addAll(List.of(tempArray));
               membershipInfoList = new ArrayList<>(List.of(tempArray));
           }
       }
       catch(IOException e)
       {
               System.out.println("CANNOT READ FROM FILE. SEE METHOD getExistingMembershipsFromFile()");
       }

    }

    public static void eraseData(String stringToErase)
    {
        File tempFile = new File("temp_memberships.txt");

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
            String line;
            while ((line = br.readLine()) != null) {
                String modifiedLine = line.replace(stringToErase, "");
                membershipInfoList.remove(stringToErase);
                bw.write(modifiedLine);
            }


        }catch(Exception e){
            System.out.println("DELETION FILE WRITER CANNOT WRITE TO FILE. SEE METHOD eraseData().");
        }
        if (file.delete()) {
            tempFile.renameTo(file);
        } else {
            System.out.println("ERROR: Could not replace the original file.");
        }

    }


}
