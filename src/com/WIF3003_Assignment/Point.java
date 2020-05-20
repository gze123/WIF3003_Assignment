package com.WIF3003_Assignment;

import java.util.Objects;

public class Point {

    double x;
    double y;
    boolean isSelected = false;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean isSelected() {
        synchronized (this){
            return isSelected;
        }
    }

    public void setSelected(boolean selected) {
        synchronized (this) {
            isSelected = selected;
        }
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return Double.compare(point.getX(), getX()) == 0 &&
                Double.compare(point.getY(), getY()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", isSelected=" + isSelected +
                '}';
    }
}
