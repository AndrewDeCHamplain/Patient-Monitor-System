import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;

@SuppressWarnings("serial")

/*
 *The Digital Clock runs in the background of MainWindow, showing the current time.
 *That's about it
 */
public class DigitalClock extends JPanel{

    String stringTime;
    int hour, minute, second;

    String correctionHour = "";
    String correctionMinute = "";
    String correctionSecond = "";

    public void setStringTime(String xyz) {
        this.stringTime = xyz;
    }

    public int findMinimumBetweenTwoNumbers(int a, int b) {
        return (a <= b) ? a : b;
    }

    DigitalClock() {

        Timer t1 = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                repaint();
            }
        });
        t1.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        /*Retrieves information from the System Clock*/
        Calendar now = Calendar.getInstance();
        hour = now.get(Calendar.HOUR_OF_DAY);
        minute = now.get(Calendar.MINUTE);
        second = now.get(Calendar.SECOND);

        /*Formatting of the time when displaying a single-digit value in any field*/
        if (hour < 10) {
            this.correctionHour = "0";
        }
        if (hour >= 10) {
            this.correctionHour = "";
        }
        if (minute < 10) {
            this.correctionMinute = "0";
        }
        if (minute >= 10) {
            this.correctionMinute = "";
        }
        if (second < 10) {
            this.correctionSecond = "0";
        }
        if (second >= 10) {
            this.correctionSecond = "";
        }
        
        setStringTime(correctionHour + hour + ":" + correctionMinute+ minute + ":" + correctionSecond + second);
        g.setColor(Color.BLACK);
        int length = findMinimumBetweenTwoNumbers(this.getWidth(),this.getHeight());
        Font myFont = new Font("SansSerif", Font.PLAIN, length / 5);
        g.setFont(myFont);
        g.drawString(stringTime, (int) length/6, length/2);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

}
