package minesweeper;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JTextField;

public final class TimeTimer extends TimerTask {

    JTextField textField;
    MineSweepController control;

    /**
     * Construct and use a TimerTask and Timer.
     */
    public TimeTimer(JTextField jTextField, MineSweepController mineSweepController) {
        this.textField = jTextField;
        this.control = mineSweepController;
        Timer timer = new Timer();
        timer.schedule(this, 100, 100);
    }

    public void stop(){
        this.cancel();
    }

    /**
     *
     * Implements TimerTask's abstract run method.
     */
    public void run() {
        if (control.isInitialized()){
            this.textField.setText(Long.toString(control.getGameTable().getMiliSecondsInGame()/1000));
        }
    }
}
