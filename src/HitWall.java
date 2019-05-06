import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class HitWall implements Behavior {
    private MovePilot pilot;
    private SampleProvider sampleProvider;
    private boolean suppressed = false;

    public HitWall(EV3TouchSensor touch, MovePilot pilot) {
        this.sampleProvider = touch.getTouchMode();
        this.pilot = pilot;
    }

    private boolean isPressed() {
        float [] sample = new float[this.sampleProvider.sampleSize()];
        this.sampleProvider.fetchSample(sample, 0);
        return sample[0] == 1;
    }

    public boolean takeControl() {
        return this.isPressed();
    }

    public void suppress() {
    	this.suppressed = true;
    }

    public void action() {
    	this.suppressed = false;
    	this.pilot.travel(-10);
    	this.pilot.rotate(90);
    }
}
