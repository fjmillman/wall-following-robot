import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class Move {

    private static boolean isTouched(SampleProvider touchSampleProvider) {
        float [] sample = new float[touchSampleProvider.sampleSize()];
        touchSampleProvider.fetchSample(sample, 0);
        return sample[0] == 1;
    }

    private static float getDistance(SampleProvider eyeSampleProvider) {
        float [] sample = new float[eyeSampleProvider.sampleSize()];
        eyeSampleProvider.fetchSample(sample, 0);
        return sample[0];
    }

    public static void main(String[] args) {
        EV3UltrasonicSensor eye = new EV3UltrasonicSensor(SensorPort.S1);
        EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S2);

        eye.enable();

        SampleProvider eyeSampleProvider = eye.getDistanceMode();
        SampleProvider touchSampleProvider = touch.getTouchMode();

        do {
            if (getDistance(eyeSampleProvider) < 0.3) {
                Motor.A.stop();
                Motor.D.forward();
            } else if (getDistance(eyeSampleProvider) > 0.5) {
                Motor.A.forward();
                Motor.D.stop();
            } else {
                Motor.A.forward();
                Motor.D.forward();
            }
        } while (!isTouched(touchSampleProvider));

        Motor.A.stop();
        Motor.D.stop();

        touch.close();
        eye.disable();
        eye.close();
    }

}
