package distributedlogger;

import java.util.*;

class Process {
    private String processId;

    private Long startTime;

    private Long endTime;

    Process(String processId, Long startTime) {
        this.processId = processId;
        this.startTime = startTime;
        this.endTime = null;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public String toString() {
        return "ProcessID: " + processId + ", StartTime: " + startTime + ", EndTime: " + endTime;
    }
}

public class LogClientImpl implements LogClient {

    private Map<String, Process> processMap;
    //PriorityQueue<Process> minHeap = new PriorityQueue<>();
    private TreeMap<Long, Process> minHeap;

    LogClientImpl() {
        processMap = new HashMap<>();
        minHeap = new TreeMap<>(Comparator.comparingLong(startTime -> startTime));
    }
    @Override
    public void startProcess(String processId) {
        Long startTime = System.currentTimeMillis();
        Process p = new Process(processId, startTime);
        processMap.put(processId, p);

        /* Note that we add in the heap after the end time, i.e., in endProcess()
        then the logger will print process 2 for the time when someone polls b/w time window 4500-5000 which is against the requirement.
         */
        minHeap.put(startTime, p);

        System.out.println("[LOG.DEBUG] START:" + p.toString());
    }

    @Override
    public void endProcess(String processId) {
        Long endTime = System.currentTimeMillis();
        processMap.get(processId).setEndTime(endTime);
        System.out.println("[LOG.DEBUG] PROCESSID: " + processId +", END: " + endTime);
    }

    @Override
    public String poll() {
        if(minHeap.isEmpty()) {
            System.out.println("[LOG.CRAP] Empty queue");
            return null;
        }

        Process p = minHeap.firstEntry().getValue();
        if(p.getEndTime() != null) {
            System.out.println("[LOG.INFO] " + p.toString());
            processMap.remove(p);
            minHeap.pollFirstEntry();
            return p.toString();
        }

        System.out.println("[LOG.CRAP] Waiting for finishing process.");
        return null;
    }
}

class Driver {
    public static void main(String[] args) {

        LogClient logger = new LogClientImpl();
        logger.poll();
        logger.startProcess("3");
        logger.poll();
        logger.startProcess("1");
        logger.poll();
        logger.endProcess("3");
        logger.poll();
        logger.startProcess("2");
        logger.poll();
        logger.endProcess("2");
        logger.poll();
        logger.endProcess("1");
        logger.poll();
        logger.poll();
    }
}
