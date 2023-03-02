package distributedlogger;

public interface LogClient {

    /*
        Stores the processId with the startTime.
     */
    void startProcess(String processId);

    /*
        Ends a process.
     */
    void endProcess(String processId);

    /*
        Print's a log statement.
        Let's assume we are logging each process's start/end time according to ascending order of their Start Times.
            Process         StartTime           EndTime
            1               2000                5000
            2               4000                4500
            3               1000                2000
        e.g. Process 3 started at 1000 and ended at 3000
             Process 1 started at 2000 and ended at 5000,
             Process 2 started at 4000 and ended at 4500.

             3, 2, 1,
     */
    String poll();
}
