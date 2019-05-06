import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class CheckAhead implements Behavior {
    private MovePilot pilot;
    private SampleProvider sampleProvider;
    private RegulatedMotor eyeMotor;
    private boolean suppressed = false;

    public CheckAhead(MovePilot pilot, RegulatedMotor eyeMotor, EV3UltrasonicSensor eye) {
        this.sampleProvider = eye.getDistanceMode();
        this.eyeMotor = eyeMotor;
        this.pilot = pilot;
    }

    private float getDistance() {
        float [] sample = new float[this.sampleProvider.sampleSize()];
        this.sampleProvider.fetchSample(sample, 0);
        return sample[0];
    }

    public boolean takeControl() {
    	this.eyeMotor.rotate(90);
    	boolean wallInFront = this.getDistance() < 0.3;
    	this.eyeMotor.rotate(-90);
        return wallInFront;
    }

    public void suppress() {
        this.suppressed = true;
    }

    public void action() {
    	this.suppressed = false;
		this.pilot.rotate(90);
    }
}
