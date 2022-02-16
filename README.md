# ProjectCS

The challenge below is a generic Java programming task that should take no more than 1-2 hours to  complete. If you find it begins to take you significantly longer than 2 hours, don't worry - simply  submit what you have completed and provide a few bullets of what your next steps would be. 
The challenge can be found directly below - please be sure to submit your final product, along  with instructions on how to run / test the program from the command line. If you believe you  require additional resources in order to be able to complete this challenge, please let us know. 
Requirements for this coding assignment: 
∙ Java 8 or 11  
∙ Use of any open-source library is allowed 
∙ Your program must use either the Gradle or Maven build system to resolve dependencies, build and  test 
Summary of task 
Our custom-build server logs different events to a file named logfile.txt. Every event has 2 entries in  the file - one entry when the event was started and another when the event was finished. The entries  in the file have no specific order (a finish event could occur before a start event for a given id) 
Every line in the file is a JSON object containing the following event data: 
∙ id - the unique event identifier 
∙ state - whether the event was started or finished (can have values "STARTED" or "FINISHED" ∙ timestamp - the timestamp of the event in milliseconds 
Application Server logs also have the following additional attributes: 
∙ type - type of log 
∙ host - hostname 
Example contents of logfile.txt: 
{"id":"scsmbstgra", "state":"STARTED", "type":"APPLICATION_LOG", "host":"12345", "times{"id":"scsmbstgrb", "state":"STARTED", "timestamp":1491377495213} 
{"id":"scsmbstgrc", "state":"FINISHED", "timestamp":1491377495218} 
{"id":"scsmbstgra", "state":"FINISHED", "type":"APPLICATION_LOG", "host":"12345", "time{"id":"scsmbstgrc", "state":"STARTED", "timestamp":1491377495210} 
{"id":"scsmbstgrb", "state":"FINISHED", "timestamp":1491377495216} 
... 
In the example above, the event scsmbstgrb duration is 1491377495216 - 1491377495213 = 3ms The longest event is scsmbstgrc (1491377495218 - 1491377495210 = 8ms)
The program should: 
∙ Have Unit Tests 
∙ Take the path to logfile.txt as an input argument 
∙ Parse the contents of logfile.txt 
∙ Flag any long events that take longer than 4ms 
∙ Write the found event details to file-based HSQLDB (http://hsqldb.org/) in the working folder ∙ The application should create a new table if necessary and store the following values: ∙ Event id 
∙ Event duration 
∙ Type and Host if applicable 
∙ Alert (true if the event took longer than 4ms, otherwise false) 


The program requires the user to input the directory of the .txt file to read and then will produce the result.
