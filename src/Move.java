import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;

public class Move {

	public static void main(String[] args) {
		RegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
		RegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);

		EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S2);
		SensorMode touchSamples = touch.getTouchMode();
		float[] touchSample = new float[touchSamples.sampleSize()];

		EV3UltrasonicSensor eye = new EV3UltrasonicSensor(SensorPort.S1);
		eye.enable();
		SampleProvider eyeSamples = eye.getDistanceMode();
		float[] eyeSample = new float[eyeSamples.sampleSize()];
		
		do {
			leftMotor.forward();
			rightMotor.forward();

			touch.fetchSample(touchSample, 0);
			eye.fetchSample(eyeSample, 0);
		} while (touchSample[0] == 0 && eyeSample[0] > 10 && eyeSample[0] < 20);

		leftMotor.stop();
		rightMotor.stop();

		leftMotor.close();
		rightMotor.close();
		touch.close();
	}

}
