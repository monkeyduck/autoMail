package com.llc;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Date;

/**
 * Created by linchuanli on 2017/8/2.
 */

public class Config {
    public static Set<String> readSendingSet(String...filePath) throws IOException {
        Set<String> toWhom = new HashSet<String>();
        String file = Config.getSendingListPath();
        if (0 != filePath.length)
            file = filePath[0];
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                toWhom.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        return toWhom;
    }

    public static Record pickOneByDifficulty(String diffculty, String...filePath) throws IOException {
        // read all the data
        String file = Config.getRecordsPath();
        Set<Record> allRecords = new HashSet<Record>();
        Set<Record> selectedRecords = new HashSet<Record>();
        if (0 != filePath.length)
            file = filePath[0];
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                Record tmp = new Record(line.trim());
                if (diffculty.equalsIgnoreCase(tmp.getDifficulty()))
                    selectedRecords.add(tmp);
                allRecords.add(tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }

        if (allRecords.isEmpty())
            return null;

        Record returnRecord;
        // no record of specific difficulty, thus use allRecords instead
        if (selectedRecords.isEmpty()) {
            System.out.println("no match record found, use other diffculty");
            int index = (int)(allRecords.size() * Math.random());
            List<Record> recordList = new ArrayList<Record>(allRecords);
            returnRecord = new Record(recordList.get(index));
        } else {
            int index = (int)(selectedRecords.size() * Math.random());
            List<Record> recordList = new ArrayList<Record>(selectedRecords);
            returnRecord = new Record(recordList.get(index));
        }
        allRecords.remove(returnRecord);


        // write to file
        if (!allRecords.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Record r : allRecords) {
                sb.append(r.toString() + "\n");
            }
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            String writeLine = sb.toString().trim();
            writer.println(writeLine);
            writer.close();
        }

        // return
        return returnRecord;
    }

    public static Record pickOneRamdonly(String... filePath) throws IOException {

        // read all the data
        String file = Config.getRecordsPath();
        Set<Record> allRecords = new HashSet<Record>();
        if (0 != filePath.length)
            file = filePath[0];
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                Record tmp = new Record(line.trim());
                allRecords.add(tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }

        if (allRecords.isEmpty())
            return null;

        int index = (int)(allRecords.size() * Math.random());
        List<Record> recordList = new ArrayList<Record>(allRecords);
        Record returnRecord = new Record(recordList.get(index));
        allRecords.remove(returnRecord);


        // write to file
        if (!allRecords.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Record r : allRecords) {
                sb.append(r.toString() + "\n");
            }
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            String writeLine = sb.toString().trim();
            writer.println(writeLine);
            writer.close();
        }

        // return
        return returnRecord;
    }

    public static List<String> readAccountConfig(String... filePath) throws IOException {

        // read all the data
        String file = Config.getAccountPath();
        List<String> configs = new ArrayList<String>();
        if (0 != filePath.length)
            file = filePath[0];
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                configs.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        return configs;
    }

    public static String getAccountPath() {
        String file = System.getProperty("user.dir") + File.separatorChar +
                "config" + File.separatorChar + "account.txt";
        return file;
    }

    public static String getRecordsPath() {
        String file = System.getProperty("user.dir") + File.separatorChar +
                "config" + File.separatorChar + "records.txt";
        return file;
    }

    public static String getSendingListPath() {
        String file = System.getProperty("user.dir") +  File.separatorChar +
                "config" + File.separatorChar + "sending_list.txt";
        return file;
    }

    public static void main(String[] args) {
        try {
            Scheduler.DayOfWeek d = Scheduler.getWeek(new Date());
            System.out.println(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
