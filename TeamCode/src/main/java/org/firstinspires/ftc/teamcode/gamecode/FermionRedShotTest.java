package org.firstinspires.ftc.teamcode.gamecode;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RC;
import org.firstinspires.ftc.teamcode.opmodesupport.AutoOpMode;
import org.firstinspires.ftc.teamcode.roboticslibrary.FXTCamera;
import org.firstinspires.ftc.teamcode.robots.Robot;
import org.firstinspires.ftc.teamcode.robots.Fermion;
import org.firstinspires.ftc.teamcode.util.VortexUtils;

/**
 * Created by FIXIT on 16-10-21.
 */
@Autonomous
public class FermionRedShotTest extends AutoOpMode {

    @Override
    public void runOp() throws InterruptedException {
        final Fermion muon = new Fermion(true);
        FXTCamera cam = new FXTCamera(FXTCamera.FACING_BACKWARD, true);


        double voltage = muon.getBatteryVoltage();
        muon.startShooterControl();
        waitForStart();
        muon.capRelease.goToPos("start");
        muon.addVeerCheckRunnable();
        muon.resetTargetAngle();

        muon.right(1);
        if(voltage > 13.5){
            sleep(700);
        } else {
            sleep(900);
        }
        muon.stop();
        muon.shoot();
        Log.i("Shhoter", muon.getShooterState() + "");
        muon.waitForShooterState(Fermion.LOADED);
        muon.shoot();
        muon.waitForShooterState(Fermion.LOADING);

        muon.absoluteIMUTurn(-65, 0.6);

        muon.forward(1);
        sleep(1000);
        muon.stop();
        sleep(200);

        muon.absoluteIMUTurn(-90, 0.6);

        muon.startWallFollowing(0, 90, 0.5, 400);

        int sensor = Robot.RIGHT;
        while (opModeIsActive() && muon.getLight(sensor) < muon.LIGHT_THRESHOLD){
            Log.i("light", "" + muon.getLight(sensor));
        }
        muon.stop();
        muon.endWallFollowing();
        muon.absoluteIMUTurn(-90, 0.5);
        muon.stop();

        int config = VortexUtils.NOT_VISIBLE;
        try{
            config = VortexUtils.getBeaconConfig(cam.getImage());
            telemetry.addData("Beacon", config);
            Log.i(TAG, "runOp: " + config);
        } catch (Exception e){
            telemetry.addData("Beacon", "could not not be found");
        }

        while(opModeIsActive() && muon.getUltrasonicDistance(0) > 300){
            muon.forward(0.2);
        }

        if(config == VortexUtils.BEACON_RED_BLUE){
            muon.stop();
            muon.right(0.4);

            sensor = Robot.LEFT;
            while (opModeIsActive() && muon.getLight(sensor) < muon.LIGHT_THRESHOLD){
                Log.i("light", "" + muon.getLight(sensor));
            }
            muon.stop();
            sleep(100);
        }

        muon.forward(0.3);
        sleep(1000);
        muon.stop();
        muon.backward(0.5);
        sleep(500);
        muon.stop();

        muon.absoluteIMUTurn(-90, 0.5);


        //------------------------------Beacon 2--------------
        muon.startWallFollowing(0, 90, 1, 400);
        sleep(1000);

        muon.setTargetSpeed(0.5);

        sensor = Robot.RIGHT;
        while (opModeIsActive() && muon.getLight(sensor) < Fermion.LIGHT_THRESHOLD){
            Log.i("light", "" + muon.getLight(sensor));
        }
        muon.endWallFollowing();
        muon.stop();
        muon.absoluteIMUTurn(-90, 0.5);
        muon.stop();



        config = VortexUtils.NOT_VISIBLE;
        try{
            config = VortexUtils.getBeaconConfig(cam.getImage());
            telemetry.addData("Beacon", config);
            Log.i(TAG, "runOp: " + config);
        } catch (Exception e){
            telemetry.addData("Beacon", "could not not be found");
        }

        while(opModeIsActive() && muon.getUltrasonicDistance(0) > 300){
            muon.forward(0.2);
        }

        if(config == VortexUtils.BEACON_RED_BLUE){
            muon.stop();
            muon.right(0.4);

            sensor = Robot.LEFT;
            while (opModeIsActive() && muon.getLight(sensor) < muon.LIGHT_THRESHOLD){
                Log.i("light", "" + muon.getLight(sensor));
            }
            muon.stop();
            sleep(100);
        }

        muon.forward(0.3);
        sleep(1000);
        muon.stop();
        muon.backward(0.5);
        sleep(300);
        muon.stop();


        if(RC.globalBool("Cap-ball")){
            muon.imuTurnR(45, 0.7);
            muon.backward(1);
            sleep(2000);
            muon.stop();
        }
        cam.destroy();
    }//runOp

}
