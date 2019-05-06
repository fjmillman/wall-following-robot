import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class DriveForward implements Behavior {
    private MovePilot pilot;
    private boolean suppressed = false;

    public DriveForward(MovePilot pilot) {
    	this.pilot = pilot;
    }

    public boolean takeControl() {
        return true;
    }

    public void suppress() {
    	this.suppressed = true;
    }

    public void action() {
    	this.suppressed = false;
    	this.pilot.forward();
        while(!this.suppressed) { Thread.yield(); }
        this.pilot.stop();
    }
}
