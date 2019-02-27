import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class HitWall implements Behavior {
    private RegulatedMotor leftMotor;
    private RegulatedMotor rightMotor;
    private SampleProvider sampleProvider;
    private boolean suppressed = false;

    public HitWall(EV3TouchSensor touch, RegulatedMotor firstMotor, RegulatedMotor secondMotor) {
        sampleProvider = touch.getTouchMode();
        leftMotor = firstMotor;
        rightMotor = secondMotor;
    }

    private boolean isPressed() {
        float [] sample = new float[sampleProvider.sampleSize()];
        sampleProvider.fetchSample(sample, 0);
        return sample[0] == 1;
    }

    public boolean takeControl() {
        return isPressed();
    }

    public void suppress() {
        suppressed = true;
    }

    public void action() {
        suppressed = false;
        leftMotor.rotate(-180, true);
        rightMotor.rotate(-360, true);

        while( rightMotor.isMoving() && !suppressed )
            Thread.yield();

        leftMotor.stop();
        rightMotor.stop();
    }
}
