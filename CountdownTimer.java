package chess;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CountdownTimer extends Timer {
    // static final double oneCentisecond = 1.56;
    static final double oneCentisecond = 1.08;
    
    // UI
    static CountdownTimer timer;
    static final int initialHours = 10, initialMinutes = 7, initialSeconds = 0;
    static final double initialCentiseconds = 0.0;

    static JFrame frame;

    static JPanel northPanel;
    static JButton start, stop, reset;

    static JPanel centerPanel;
    static JLabel time;

    static JPanel southPanel;
    static JComboBox incrementTime;
    static JButton add, subtract;

    // CountdownTimer
    private String prefix, suffix;
    private int shours, sseconds, sminutes; // start hours, start seconds, start minutes
    private int hours, seconds, minutes;
    private double scentiseconds;           // start centiseconds
    private double centiseconds;
    private Runnable changeTime, updateTime, close;
    private boolean outOfTime;

    public static void main(String[]args) {
        frame = new JFrame("CountdownTimer Tester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(200, 200, 400, 125);

        northPanel = new JPanel();
        frame.add(northPanel, BorderLayout.NORTH);

        start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.restart();
            }
        });
        northPanel.add(start);

        stop = new JButton("Stop");
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.stop();
            }
        });
        northPanel.add(stop);

        reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.reset();
            }
        });
        northPanel.add(reset);

        centerPanel = new JPanel();
        frame.add(centerPanel, BorderLayout.CENTER);

        time = new JLabel();
        time.setHorizontalAlignment(JLabel.RIGHT);
        centerPanel.add(time);

        southPanel = new JPanel();
        frame.add(southPanel, BorderLayout.SOUTH);

        String[] seconds = new String[60];
        for (int i = 0; i < seconds.length; i++) {
            seconds[i] = (i+1) + "";
        }
        incrementTime = new JComboBox(seconds);
        southPanel.add(incrementTime);

        add = new JButton("Add");
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.changeTime(0, 0, incrementTime.getSelectedIndex()+1, 0.0);
                timer.updateTime();
            }
        });
        southPanel.add(add);

        subtract = new JButton("Subtract");
        subtract.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.changeTime(0, 0, -(incrementTime.getSelectedIndex()+1), 0.0);
                timer.updateTime();
            }
        });
        southPanel.add(subtract);

        timer = new CountdownTimer(10, "", "",
                initialHours, initialMinutes, initialSeconds, initialCentiseconds,
                new Runnable() {            // Change Time
                    public void run() {
                        timer.changeTime(0, 0, 0, oneCentisecond);
                    }
                }, new Runnable() {         // Update Time
                    public void run() {
                        if (timer != null) {
                            time.setText(timer.getTime(true, true, true, true));
                        }
                    }
                }, new Runnable() {
                    public void run() {     // Close
                        System.out.println("Out of time");
                    }
                }
        );
        timer.updateTime();
        
        frame.setVisible(true);
    }

/* Constructor:
timer = new CountdownTimer(10, "prefix", "suffix",
        hours, minutes, seconds, centiseconds,
        new Runnable() {            // Change Time
            public void run() {
                timer.changeTime(0, 0, 0, -CountdownTimer.oneCentisecond);
            }
        }, new Runnable() {         // Update Time
            public void run() {
                if (timer != null) {
                    JLabel.setText(timer.getTime(true, true, true, true));
                }
            }
        }, new Runnable() {
            public void run() {     // Close

            }
        }
);
timer.updateTime();
*/

    public CountdownTimer (int millisecondsPerEvent, String prefixAlias, String suffixAlias, 
            int hoursAlias, int minutesAlias, int secondsAlias, double centisecondsAlias,
            Runnable changeTimeAlias, Runnable updateTimeAlias, Runnable closeAlias) {
        super (millisecondsPerEvent, null);
        prefix = prefixAlias;
        suffix = suffixAlias;
        shours = hoursAlias;
        sminutes = minutesAlias;
        sseconds = secondsAlias;
        scentiseconds = centisecondsAlias;
        changeTime = changeTimeAlias;
        updateTime = updateTimeAlias;
        close = closeAlias;
        outOfTime = false;
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeTime();
                updateTime();
            }
        });
        reset();
        start();
        stop();
    }

    public void changeTime() {
        changeTime.run();
    }

    public void updateTime() {
        updateTime.run();
    }
    
    public void close() {
        stop();
        outOfTime = true;

        hours = minutes = seconds = 0;
        centiseconds = 0.0;
        updateTime();
        
        if (close != null) {
            close.run();
        }
    }

    public void reset() {
        stop();
        hours = shours;
        minutes = sminutes;
        seconds = sseconds;
        centiseconds = scentiseconds;
        updateTime();
    }
    
    public void changeTime(int hour, int minute, int second, double centisecond) {
        // Centiseconds
        centiseconds += centisecond;
        while (centiseconds >= 100) {
            centiseconds -= 100;
            second++;
        }
        while (centiseconds < 0) {
            centiseconds += 100;
            second--;
        }
        // Seconds
        seconds += second;
        while (seconds >= 60) {
            seconds -= 60;
            minutes++;
        }
        while (seconds < 0) {
            seconds += 60;
            minutes--;
        }
        // Minutes
        minutes += minute;
        while (minutes >= 60) {
            minutes -= 60;
            hours++;
        }
        while (minutes < 0) {
            minutes += 60;
            hours--;
        }
        // Hours
        hours += hour;
        if (hours > 60) {
            hours = 60;
        }
        if (hours < 0) {
            close();
        }
    }

    public String getTime() {
        return getTime(true, true, true, true);
    }

    public String getTime(boolean getHours, boolean getMinutes, boolean getSeconds, boolean getCentiseconds) {
        String h = "", m = "", s = "", c = "";
        // Hours
        if (getHours && hours != 0) {
            if (hours < 10) {
                h = " ";
            }
            h += hours;
            if (getMinutes) {
                h += ":";
            }
        }
        // Minutes
        if (getMinutes && (hours > 0 || minutes > 0)) {
            if (hours > 0 && minutes < 10) {
                m = "0";
            } else if (hours == 0 && minutes < 10) {
                m = " ";
            }
            m += minutes;
            if (getSeconds) {
                m += ":";
            }
        }
        // Seconds
        if (getSeconds) {
            if (hours == 0 && minutes == 0 && seconds == 0) {
                s = " 0.";
            } else  {
                if ((hours > 0 || minutes > 0) && seconds < 10) {
                    s = "0";
                } else if (hours == 0 && minutes == 0 && seconds < 10) {
                    s = " ";
                }
                s += seconds;
                if (getCentiseconds) {
                    s += ".";
                }
            }
        }
        // Centiseconds
        if (getCentiseconds) {
            if (centiseconds < 10) {
                c = "0";
            }
            c += (int)centiseconds;
        }
        return (prefix + h + m + s + c + suffix);
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getCentiseconds() {
        return (int)centiseconds;
    }

    public boolean isOutOfTime() {
        return outOfTime;
    }
}