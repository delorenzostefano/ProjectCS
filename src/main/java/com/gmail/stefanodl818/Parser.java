package com.gmail.stefanodl818;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;


public class Parser {

    private static final Logger logger = Logger.getLogger(Parser.class.getName());

    public static Map parseFile(String fileName) throws FileNotFoundException {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            Gson gson = new Gson();
            Map<String, Log> logMap = new HashMap<>();
            Map<String, ApplicationServerLog> applicationServerLogMap = new HashMap<>();
            Map<String, Event> eventMap = new HashMap<>();

            logger.info("Starting reading the file and populating the event map...");
            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                JsonObject gsonObject = gson.fromJson(line, JsonObject.class);

                long timeDifference = 0;

                if (isLogObject(gsonObject)) {
                    Log log = gson.fromJson(gsonObject, Log.class);
                    if (logMap.containsKey(log.getId())) {
                        // if the log is contained in the map then calculate the difference of time between the 2
                        timeDifference = calculateTimeExecution(log, logMap.get(log.getId()));
                        // the id of the  log gets added to the Event map
                        populateEventMapLog(log, eventMap, timeDifference);
                        // the object is removed from the Map of logs
                        logMap.remove(log.getId());
                    } else {
                        // the object is added to the map of logs if not already included
                        logMap.put(log.getId(), log);
                    }
                } else if (isApplicationServerLogObject(gsonObject)) {
                    ApplicationServerLog applicationServerLog = gson.fromJson(gsonObject, ApplicationServerLog.class);
                    if (applicationServerLogMap.containsKey(applicationServerLog.getId())) {
                        timeDifference = calculateTimeExecution(applicationServerLog, applicationServerLogMap.get(applicationServerLog.getId()));
                        populateEventMapApplicationServerLog(applicationServerLog, eventMap, timeDifference);
                        // the object is removed from the Map of application server logs
                        applicationServerLogMap.remove(applicationServerLog.getId());
                    } else {
                        applicationServerLogMap.put(applicationServerLog.getId(), applicationServerLog);
                    }
                }
            }
            logger.info("The logs have been collected");
            return eventMap;
        }catch (FileNotFoundException e){
            logger.warning("The file that has been used as input has not been found");
            throw new FileNotFoundException("File not found");
        }

    }

    // verifies if a json object is a log
    public static Boolean isLogObject(JsonObject obj){
        if(obj.has("id") && obj.has("state") && obj.has("timestamp") && obj.keySet().size()==3){
            return true;
        }else{
            return false;
        }
    }

    // verifies if a json object is a json object is anApplicationLog
    public static Boolean isApplicationServerLogObject(JsonObject obj){
        if(obj.has("id") && obj.has("state") && obj.has("timestamp") && obj.has("type") && obj.has("host") && obj.keySet().size()==5){
            return true;
        }else{
            return false;
        }
    }

    // calculates the difference in time between the state Started and Finished
    public static long calculateTimeExecution(Log log1, Log log2){
        long difference = 0;

        if(log1.getState().name() == "STARTED" && log2.getState().name() == "FINISHED"){
            difference = log2.getTimestamp() - log1.getTimestamp();
        }else if(log2.getState().name() == "STARTED" && log1.getState().name() == "FINISHED"){
            difference = log1.getTimestamp() - log2.getTimestamp();
        }else if(log1.getState() == log2.getState()){
            logger.warning("The same event" + log1.getId() + " has been restarted, the time difference will be saved as 0");
        }

        return difference;
    }

    // populates the Map of Logs
    public static void populateEventMapLog(Log log, Map eventMap, long duration){
        int time = Math.toIntExact(duration);
        Boolean alert = false;
        if(time>AppData.getTRESHOLD()){
            alert = true;
        }
        Event event = new Event(log.getId(), time, null, null, alert);
        //logger.info("adding alert " +  event.getId() + " to alerts collection");
        eventMap.put(event.getId(), event);
    }

    // populates the map of application logs
    public static void populateEventMapApplicationServerLog(ApplicationServerLog appLog, Map eventMap, long duration){
        int time = Math.toIntExact(duration);
        Boolean alert = false;
        if(time>AppData.getTRESHOLD()){
            alert = true;
        }
        Event event = new Event(appLog.getId(), time, appLog.getHost(), appLog.getType(), alert);
        //logger.info("adding alert " +  event.getId() + " to alerts collection");
        eventMap.put(event.getId(), event);
    }

}
