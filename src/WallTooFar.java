import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class WallTooFar implements Behavior {
    private MovePilot pilot;
    private SampleProvider sampleProvider;
    private boolean suppressed = false;

    public WallTooFar(EV3UltrasonicSensor eye, MovePilot pilot) {
        this.sampleProvider = eye.getDistanceMode();
        this.pilot = pilot;
    }

    private float getDistance() {
        float [] sample = new float[this.sampleProvider.sampleSize()];
        this.sampleProvider.fetchSample(sample, 0);
        return sample[0];
    }

    public boolean takeControl() {
        return this.getDistance() > 0.15;
    }

    public void suppress() {
    	this.suppressed = true;
    }

    public void action() {
    	this.suppressed = false;
    	this.pilot.rotate(-20);
		this.pilot.travel(8);
    }
}
