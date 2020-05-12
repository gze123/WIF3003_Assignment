package com.WIF3003_Assignment;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of points you want to create (n): ");
        int n = scanner.nextInt();
        System.out.print("Enter number of thread you want to launch (value must be lesser or equal to n) (t): ");
        int t = scanner.nextInt();
        System.out.print("Enter the time you want the program to run (in second) (m): ");
        int m = scanner.nextInt();

        ExecutorService executorService = Executors.newFixedThreadPool(t);
    }
}
