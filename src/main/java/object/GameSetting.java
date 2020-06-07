package main.java.object;

public class GameSetting {
    int numberOfPoint;
    int numberOfThread;
    int timeLimit;

    public GameSetting(int numberOfPoint, int numberOfThread, int timeLimit) {
        this.numberOfPoint = numberOfPoint;
        this.numberOfThread = numberOfThread;
        this.timeLimit = timeLimit;
    }

    public int getNumberOfPoint() {
        return numberOfPoint;
    }

    public void setNumberOfPoint(int numberOfPoint) {
        this.numberOfPoint = numberOfPoint;
    }

    public int getNumberOfThread() {
        return numberOfThread;
    }

    public void setNumberOfThread(int numberOfThread) {
        this.numberOfThread = numberOfThread;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }
}
