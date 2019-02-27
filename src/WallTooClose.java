import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class WallTooClose implements Behavior {
    private RegulatedMotor leftMotor;
    private RegulatedMotor rightMotor;
    private SampleProvider sampleProvider;
    private boolean suppressed = false;

    public WallTooClose(EV3UltrasonicSensor eye, RegulatedMotor firstMotor, RegulatedMotor secondMotor) {
        sampleProvider = eye.getDistanceMode();
        leftMotor = firstMotor;
        rightMotor = secondMotor;
    }

    private float getDistance() {
        float [] sample = new float[sampleProvider.sampleSize()];
        sampleProvider.fetchSample(sample, 0);
        return sample[0];
    }

    public boolean takeControl() {
        return getDistance() < 0.3;
    }

    public void suppress() {
        suppressed = true;
    }

    public void action() {
        suppressed = false;
        leftMotor.rotate(-240, true);
        rightMotor.rotate(-360, true);

        while( rightMotor.isMoving() && !suppressed )
            Thread.yield();

        leftMotor.stop();
        rightMotor.stop();
    }
}
