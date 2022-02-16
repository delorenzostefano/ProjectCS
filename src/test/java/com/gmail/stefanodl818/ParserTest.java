package com.gmail.stefanodl818;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {

    @Test
    public void fileNameDoesNotExistDoesNotCrash(){
        Exception exception = assertThrows(FileNotFoundException.class, () ->{
            Parser.parseFile("this does not exist");
        });

        String expected = "File not found";
        String actual= exception.getMessage();

        Assertions.assertEquals(actual, expected);

    }

    @Test
    public void emptyLogFileDoesNotCrash() throws FileNotFoundException {

        Map<String, Event> expected = new HashMap<>();
        Map<String, Event> actual = Parser.parseFile(".//files//emptyLogFile.txt");

        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void logFileReturnsCorrectOutput() throws FileNotFoundException {

        Map<String, Event> expected = new HashMap<>();
        expected.put("scsmbstgra", new Event("scsmbstgra", 20, "12345", "APPLICATION_LOG", true));
        expected.put("scsmbstgrb", new Event("scsmbstgrb", 3, null, null, false));
        expected.put("scsmbstgrc", new Event("scsmbstgrc", 8, null, null, false));
        Map<String, Event> actual = Parser.parseFile(".//files//logfile.txt");

        for(Event event: expected.values()){
            if(actual.containsKey(event.getId())){
                Assertions.assertEquals(event.getDuration(), actual.get(event.getId()).getDuration());
                Assertions.assertEquals(event.getHost(), actual.get(event.getId()).getHost());
                Assertions.assertEquals(event.getType(), actual.get(event.getId()).getType());
                Assertions.assertEquals(event.getAlert(), actual.get(event.getId()).getAlert());
            }
        }
    }

    @Test
    public void timeDifferenceOfLogsWithSameIdAndStatusIs0(){
        Log log1 = new Log("scrambe1", Log.State.STARTED, 1245678954);
        Log log2 = new Log("scrambe1", Log.State.STARTED, 1245678959);

        long expected = 0;
        long actual = Parser.calculateTimeExecution(log1, log2);

        Assertions.assertEquals(actual, expected);
    }



}
