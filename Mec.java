package org.firstinspires.ftc.teamcode;

import java.lang.annotation.Target;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cWaitControl;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Mec")

public class Mec extends LinearOpMode{
    private Gyroscope imu;
    //private DcMotor _;
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor FrontLeftMotor;
    private DcMotor RearLeftMotor;
    private DcMotor FrontRightMotor;
    private DcMotor RearRightMotor;
    private DcMotor ArmRight;
    private DcMotor ArmLeft;
    private Servo ArmHook;
    static final double INCREMENT = 0.01;
    static final int CYCLE_MS = 50;
    static final double MAX_POS = 0.5;
    static final double MIN_POS = 1.0;
    static final double TARGET_POS = 0.01;
    Servo servo;
    double power = 1.0;
    //double position = 0.0; //(MAX_POS - MIN_POS) / 2;
    //double targetposition = 0.2;
    //boolean rampUp = true;
    
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        
        FrontLeftMotor = hardwareMap.dcMotor.get("FrontLeftMotor");
        FrontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        RearLeftMotor = hardwareMap.dcMotor.get("RearLeftMotor");
        RearLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        FrontRightMotor = hardwareMap.dcMotor.get("FrontRightMotor");
        FrontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        RearRightMotor = hardwareMap.dcMotor.get("RearRightMotor");
        RearRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        ArmRight = hardwareMap.dcMotor.get("ArmRight");
        ArmRight.setDirection(DcMotorSimple.Direction.FORWARD);
        ArmLeft = hardwareMap.dcMotor.get("ArmLeft");
        ArmLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //ArmHook = hardwareMap.Servo.get(Servo.class, "ArmHook");
        ArmHook = hardwareMap.get(Servo.class, "ArmHook");

        FrontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RearLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RearRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            //telemetry.addData("Status", "Running", "Run Time" + runtime.toString());
            telemetry.update();
            idle();
            // variables for drivetrain
            // r = Math.hypot(gamepad1.left_stick_y, gamepad1.left_stick_x);
            // rightX = gamepad1.right_stick_x;
            // robotangle = (Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4);
            // drivetrain with left joystick forward and strafe, right joystick turn
            //FrontRightMotor.setPower((Math.hypot(gamepad1.left_stick_y, gamepad1.left_stick_x)) * (Math.cos(Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4)) + (gamepad1.right_stick_x));
            //RearRightMotor.setPower((Math.hypot(gamepad1.left_stick_y, gamepad1.left_stick_x)) * (Math.sin(Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4)) + (gamepad1.right_stick_x));
            //FrontLeftMotor.setPower(((-(Math.hypot(gamepad1.left_stick_y, gamepad1.left_stick_x)) * (Math.sin(Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4))) + (gamepad1.right_stick_x)));
            //RearLeftMotor.setPower(((-(Math.hypot(gamepad1.left_stick_y, gamepad1.left_stick_x)) * (Math.cos(Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4))) + (gamepad1.right_stick_x)));
            

            // change to make 30% power
            if (gamepad1.left_trigger > 0.5){
                FrontRightMotor.setPower((0.3*((-(Math.hypot(gamepad1.left_stick_y, gamepad1.right_stick_x)) * (Math.cos(Math.atan2(gamepad1.left_stick_y, gamepad1.right_stick_x) - Math.PI / 4))) - (gamepad1.left_stick_x))));
                RearRightMotor.setPower((0.3*((-(Math.hypot(gamepad1.left_stick_y, gamepad1.right_stick_x)) * (Math.sin(Math.atan2(gamepad1.left_stick_y, gamepad1.right_stick_x) - Math.PI / 4))) - (gamepad1.left_stick_x))));
                FrontLeftMotor.setPower((0.3*(((Math.hypot(gamepad1.left_stick_y, gamepad1.right_stick_x)) * (Math.sin(Math.atan2(gamepad1.left_stick_y, gamepad1.right_stick_x) - Math.PI / 4))) - (gamepad1.left_stick_x))));
                RearLeftMotor.setPower((0.3*(((Math.hypot(gamepad1.left_stick_y, gamepad1.right_stick_x)) * (Math.cos(Math.atan2(gamepad1.left_stick_y, gamepad1.right_stick_x) - Math.PI / 4))) - (gamepad1.left_stick_x))));
            }

            // change to make turning left and strafe right
            else{
                FrontRightMotor.setPower(((-(Math.hypot(gamepad1.left_stick_y, gamepad1.right_stick_x)) * (Math.cos(Math.atan2(gamepad1.left_stick_y, gamepad1.right_stick_x) - Math.PI / 4))) - (gamepad1.left_stick_x)));
                RearRightMotor.setPower(((-(Math.hypot(gamepad1.left_stick_y, gamepad1.right_stick_x)) * (Math.sin(Math.atan2(gamepad1.left_stick_y, gamepad1.right_stick_x) - Math.PI / 4))) - (gamepad1.left_stick_x)));
                FrontLeftMotor.setPower((((Math.hypot(gamepad1.left_stick_y, gamepad1.right_stick_x)) * (Math.sin(Math.atan2(gamepad1.left_stick_y, gamepad1.right_stick_x) - Math.PI / 4))) - (gamepad1.left_stick_x)));
                RearLeftMotor.setPower((((Math.hypot(gamepad1.left_stick_y, gamepad1.right_stick_x)) * (Math.cos(Math.atan2(gamepad1.left_stick_y, gamepad1.right_stick_x) - Math.PI / 4))) - (gamepad1.left_stick_x)));
                //when you push the joystick up the robot climbs up
                ArmRight.setPower(gamepad2.left_stick_y * -0.75);
                ArmLeft.setPower(gamepad2.left_stick_y * -0.75);
            }

            if (gamepad2.a) {ArmHook.setPosition(MAX_POS);} 
            if (gamepad2.b) {ArmHook.setPosition(MIN_POS);}
                    
            // Display the current value
            telemetry.addData("ArmHook Position", "%5.2f", ArmHook.getPosition());
            telemetry.update();
            //sleep(CYCLE_MS);
            //idle();
            }
        
        //telemetry.addData(">", "Done");
        //telemetry.update();
        //telemetry.addData("Target Power", tgtPower);
        //telemetry.addData("Motor Power", motorTest.getPower());
        //telemetry.addData("Status", "Running");
        //telemetry.update();
        power = 0.0;
        }
    }
