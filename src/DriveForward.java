import lejos.robotics.RegulatedMotor;
import lejos.robotics.subsumption.Behavior;

public class DriveForward implements Behavior {
    private RegulatedMotor leftMotor;
    private RegulatedMotor rightMotor;
    private boolean suppressed = false;

    public DriveForward(RegulatedMotor firstMotor, RegulatedMotor secondMotor) {
        leftMotor = firstMotor;
        rightMotor = secondMotor;
    }

    public boolean takeControl() {
        return true;
    }

    public void suppress() {
        suppressed = true;
    }

    public void action() {
        suppressed = false;
        leftMotor.forward();
        rightMotor.forward();
        while( !suppressed )
            Thread.yield();
        leftMotor.stop();
        rightMotor.stop();
    }
}
