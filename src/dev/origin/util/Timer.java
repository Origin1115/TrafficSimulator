/*
 * *****************************************************************************
 * FILE: Timer.java
 * NAME: Tyler D Clark
 * PROJECT: Project 3
 * COURSE: CMSC 335
 * DATE: 13 Dec 2020
 * *****************************************************************************
 */
package dev.origin.util;

import dev.origin.gui.BackgroundCanvas;

import javax.swing.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * This class creates a pseudo-timer by waiting 1000 ms in a loop. but it makes things much easier for pausing/stopping
 * sake.. So what if it is off a couple microseconds, right? This timer also repaints the canvas.
 */
public class Timer extends SwingWorker<Void, Integer> {

    private boolean stop, pause;
    private final JTextField textField;
    private final BackgroundCanvas backgroundCanvas;
    private String timePattern = "hh:mm:ss a";
    private SimpleDateFormat timeFormat = new SimpleDateFormat(timePattern); 
    Date date = new Date(System.currentTimeMillis());

    /**
     * Constructor of the timer, which will be called each time stop is pressed or at the beginning of the sim
     * @param textField to set with the time text calculated in this timer
     * @param backgroundCanvas canvas to drawn to.
     */
    public Timer(JTextField textField, BackgroundCanvas backgroundCanvas){
        this.textField = textField;
        this.backgroundCanvas = backgroundCanvas;
        this.stop = false;
        this.pause = false;
    }
    /**
     * adds a second to seconds as long as we are not paused or stopped
     *
     * @return null
     * @throws Exception if unable to compute a result
     */
    @Override
    protected Void doInBackground() throws Exception {
        int seconds = 0;
        while (!stop){
            if (!pause){
                seconds++;
                publish(seconds);
            }
            Thread.sleep(1000);
        }

        return null;
    }

    /**
     * Sets the text time to {@link #textField} and repaints the background canvas
     * @param chunks seconds from the {@link #doInBackground()}
     */
    @Override
    protected void process(List<Integer> chunks) {
        Integer second = chunks.get(chunks.size()-1);
        String time = String.format("%02d:%02d", second / 60, second % 60);
        date = new Date(System.currentTimeMillis());
        String time1 =  timeFormat.format(date);
        textField.setText(time1);
        backgroundCanvas.repaint();
    }

    /* The other ones */
    @Override
    protected void done() { System.out.println("Timer done"); }
    public void pause () { this.pause = true; }
    public void play () { this.pause = false; }
    public void stop () { this.stop = true; }
    public boolean isStop() { return stop; }
    public boolean isPause() { return pause; }
}
