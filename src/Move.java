import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;

public class Move {
	public static void main(String[] args) {
		EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S2);
		SensorMode samples = touch.getTouchMode();
		float[] sample = new float[samples.sampleSize()];
		
		do {
			Motor.A.forward();
			Motor.D.forward();
			touch.fetchSample(sample, 0);
		} while (sample[0] == 0);
		
		Motor.A.stop();
		Motor.D.stop();
		touch.close();
	}
}
