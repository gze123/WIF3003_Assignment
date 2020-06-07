package main.java.object;

public class ThreadResult implements Comparable<ThreadResult> {
    String name;
    int numberOfEdgeCreated;
    int numberOfFailure;

    public ThreadResult() {
    }

    public ThreadResult(String name, int numberOfEdgeCreated, int numberOfFailure) {
        this.name = name;
        this.numberOfEdgeCreated = numberOfEdgeCreated;
        this.numberOfFailure = numberOfFailure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfEdgeCreated() {
        return numberOfEdgeCreated;
    }

    public void setNumberOfEdgeCreated(int numberOfEdgeCreated) {
        this.numberOfEdgeCreated = numberOfEdgeCreated;
    }

    public int getNumberOfFailure() {
        return numberOfFailure;
    }

    public void setNumberOfFailure(int numberOfFailure) {
        this.numberOfFailure = numberOfFailure;
    }

    @Override
    public int compareTo(ThreadResult o) {
        return o.numberOfEdgeCreated - this.numberOfEdgeCreated;
    }

    @Override
    public String toString() {
        return "ThreadResult{" +
                "name='" + name + '\'' +
                ", numberOfEdgeCreated=" + numberOfEdgeCreated +
                ", numberOfFailure=" + numberOfFailure +
                '}';
    }
}
