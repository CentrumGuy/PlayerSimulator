package com.CentrumGuy.PlayerSimulator.Utils;

import java.util.LinkedList;


public class TPSCheck implements Runnable
{
    private static long last = System.currentTimeMillis();
    public static LinkedList<Long> history = new LinkedList<Long>();

    public void run() {
        long now = System.currentTimeMillis();
        long duration = now - last;
        if (duration < 1000)
            duration = 1000;
        history.add(duration);
        if (history.size() > 10)
            history.poll();
        last = now;
    }
}
