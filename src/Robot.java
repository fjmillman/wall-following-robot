import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Robot {

    public static void main(String[] args) {
    	RegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
    	RegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);
        Wheel leftWheel = WheeledChassis.modelWheel(leftMotor, 5.6).offset(-20);
        Wheel rightWheel = WheeledChassis.modelWheel(rightMotor, 5.6).offset(20);
        Chassis chassis = new WheeledChassis(new Wheel[] { leftWheel, rightWheel }, WheeledChassis.TYPE_DIFFERENTIAL);
        MovePilot pilot = new MovePilot(chassis);
        pilot.setLinearSpeed(20);
        pilot.setAngularSpeed(30);
        
        RegulatedMotor eyeMotor = new EV3MediumRegulatedMotor(MotorPort.A);
        eyeMotor.setSpeed(740);
        
        EV3UltrasonicSensor ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S1);
        ultrasonicSensor.enable();
        
        EV3TouchSensor leftTouch = new EV3TouchSensor(SensorPort.S2);
        EV3TouchSensor rightTouch = new EV3TouchSensor(SensorPort.S3);
        
        Behavior driveForward = new DriveForward(pilot);
        Behavior checkAhead = new CheckAhead(pilot, eyeMotor, ultrasonicSensor);
        Behavior wallTooClose = new WallTooClose(ultrasonicSensor, pilot);
        Behavior wallTooFar = new WallTooFar(ultrasonicSensor, pilot);
        Behavior leftHitWall = new HitWall(leftTouch, pilot);
        Behavior rightHitWall = new HitWall(rightTouch, pilot);
        
        Behavior [] behaviors = {
                driveForward,
                checkAhead,
                wallTooClose,
                wallTooFar,
                leftHitWall,
                rightHitWall
        };

        Arbitrator arbitrator = new Arbitrator(behaviors);
        arbitrator.go();

        ultrasonicSensor.disable();
        ultrasonicSensor.close();
        eyeMotor.close();
        leftTouch.close();
        rightTouch.close();
    }

}
