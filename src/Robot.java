import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Robot {

    public static void main(String[] args) {
        RegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        RegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);
        EV3UltrasonicSensor eye = new EV3UltrasonicSensor(SensorPort.S1);
        EV3TouchSensor leftTouch = new EV3TouchSensor(SensorPort.S2);
        EV3TouchSensor rightTouch = new EV3TouchSensor(SensorPort.S3);

        eye.enable();

        Behavior driveForward = new DriveForward(leftMotor, rightMotor);
        Behavior leftHitWall = new HitWall(leftTouch, leftMotor, rightMotor);
        Behavior rightHitWall = new HitWall(rightTouch, leftMotor, rightMotor);
        Behavior wallTooClose = new WallTooClose(eye, leftMotor, rightMotor);
        Behavior wallTooFar = new WallTooFar(eye, leftMotor, rightMotor);
        Behavior [] behaviors = {
                driveForward,
                leftHitWall,
                rightHitWall,
                wallTooClose,
                wallTooFar
        };
        Arbitrator arbitrator = new Arbitrator(behaviors);
        arbitrator.go();

        eye.disable();
        eye.close();
        leftTouch.close();
        rightTouch.close();
    }

}
