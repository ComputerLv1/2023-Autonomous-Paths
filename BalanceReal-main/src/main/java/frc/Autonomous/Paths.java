package frc.Autonomous;

import edu.wpi.first.wpilibj.Timer;
import frc.Mechanisms.Drive;
import frc.Mechanisms.Balance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Paths  //See slides (2637 Charged Up autonomous paths in Google Drive)
{
    /*
     *  turning angles of the steer wheel is from 0 to 180 from front to left and 0 to -180 from front to right
     *  this is counterclock wise
     */

    //STEER WHEEL ANGLE
    private final double STRAIGHT = 0;
    private final double RIGHT = -90; 
    private final double LEFT = 90;

    private final double TEMP_MAX_TIME = 8.0;
    private final double TEMP_DECEL_DIST = 0.05;
    private final double MIN_DIST = 20;
    private final double MAX_DIST = 244;
    private final double GP_TO_GP = 48;

    public static boolean foward = false;
    public static boolean backward = false;
    public static boolean right = false;
    public static boolean left = false;
    public static boolean all = false; //set all direction sensors to false
    public static boolean pathing = false;
    
    //MAX SPEED
    private final double FAST = 0.35;
    private final double SLOW = 0.25;

    public static String path;

    Drive drive = new Drive();
    Balance balance = new Balance();

    Timer time = new Timer();

    /*  DRIVE STRAIGHT VALUES: 
     * if distance > 70, then FAST, else SLOW
     * 8 second maxTime is an arbitrary number, subject to change upon more testing 
     * only robot backwards movement has negative signs over distance and maxspeed
     * left and right distances and max speed aren't negative
     * TEMP_DECEL_DIST decelDistance is an arbitrary number, subject to change upon more testing
     * 
     *   *note* - autonomous is 15 seconds, meaning that all of this will have to finsih within that time
     *          - THIS CODE IS MADE FOR BLUE SIDE 
     *          - FOR RED, CHANGE LEFTS WITH RIGHTS AND RIGHTS WITH LEFTS (from blue)
     *          - movement similar to code.org level programming
    */

    /* PATH NAME:
     *    /CenterRightTunnel/
     * - CenterRight (Starting Position)
     * - Tunnel (type of movement/movement path)
     */

    /* Distances:          -______-
     * drive.DriveStraight(distance, decelDistance, maxSpeed, wheelPos, maxTime);
     *  - 224 = distance from grid to center pieces
     *                
     */
    // drive.DriveStraight(distance, decelDist, )
    public Paths() 
    {
        if(all = true) {
            foward = false;
            backward = false;
            right = false;
            left = false;
        }
        if(drive.getWheelPos() == 0.0 && drive.getDistance() > 0) {
            foward = true;
        } else if (drive.getWheelPos() == 0.0 && drive.getDistance() < 0) {
            backward = true;
        } else if (drive.getWheelPos() == 90.0) {
            left = true;
        } else if (drive.getWheelPos() == -90.0) {
            right = true;
        } else {
            all = true;
        }
    }

    
    public void gridToCenter() 
    {
        drive.DriveStraight(MAX_DIST, TEMP_DECEL_DIST, FAST, STRAIGHT, TEMP_MAX_TIME);
    }
    public void centerToGrid() 
    {
        drive.DriveStraight(-MAX_DIST, TEMP_DECEL_DIST, -FAST, STRAIGHT, TEMP_MAX_TIME);
    }
    public void gridToAreaInfrontOfCargo() 
    {
        drive.DriveStraight(MAX_DIST - MIN_DIST, TEMP_DECEL_DIST, FAST, STRAIGHT, TEMP_MAX_TIME);
    }
    public void centerToAreaInfrontOfDock() 
    {
        drive.DriveStraight(-MAX_DIST + MIN_DIST, TEMP_DECEL_DIST, -FAST, STRAIGHT, TEMP_MAX_TIME);
    }
    public void translateRight48() 
    {
        drive.DriveStraight(GP_TO_GP, TEMP_DECEL_DIST, SLOW, RIGHT, TEMP_MAX_TIME);
    }
    public void translateLeft48() 
    {
        drive.DriveStraight(GP_TO_GP, TEMP_DECEL_DIST, SLOW, LEFT, TEMP_MAX_TIME);
    }
    public void gridToChargingStation() 
    {
        drive.DriveStraight(GP_TO_GP, TEMP_DECEL_DIST, SLOW, STRAIGHT, TEMP_MAX_TIME);
    }
    public void translateBackRight() 
    {
        drive.DriveStraight(MIN_DIST, TEMP_DECEL_DIST, SLOW, RIGHT, TEMP_MAX_TIME);
        drive.DriveStraight(-MIN_DIST, TEMP_DECEL_DIST, -SLOW, STRAIGHT, TEMP_MAX_TIME);
    }
    public void translateBackLeft() 
    {
        drive.DriveStraight(MIN_DIST, TEMP_DECEL_DIST, SLOW, LEFT, TEMP_MAX_TIME);
        drive.DriveStraight(-MIN_DIST, TEMP_DECEL_DIST, -SLOW, STRAIGHT, TEMP_MAX_TIME);
    }
    public void translateFowardRight() 
    {
        drive.DriveStraight(MIN_DIST, TEMP_DECEL_DIST, SLOW, STRAIGHT, TEMP_MAX_TIME);
        drive.DriveStraight(MIN_DIST, TEMP_DECEL_DIST, SLOW, RIGHT, TEMP_MAX_TIME);
    }
    public void translateFowardLeft() 
    {
        drive.DriveStraight(MIN_DIST, TEMP_DECEL_DIST, SLOW, STRAIGHT, TEMP_MAX_TIME);
        drive.DriveStraight(MIN_DIST, TEMP_DECEL_DIST, SLOW, LEFT, TEMP_MAX_TIME);
    }
    public void fowardToCargo() 
    {
        drive.DriveStraight(MIN_DIST, TEMP_DECEL_DIST, SLOW, STRAIGHT, TEMP_MAX_TIME);
    }
    public void dockToGrid() 
    {
        drive.DriveStraight(-MIN_DIST, TEMP_DECEL_DIST, -SLOW, STRAIGHT, TEMP_MAX_TIME);
    }
    public void diagonal()
    {
        drive.DriveStraight(GP_TO_GP, TEMP_DECEL_DIST, FAST, GP_TO_GP, TEMP_MAX_TIME); //wheelPos used to be 45 (foward left)
        drive.DriveStraight(0, TEMP_DECEL_DIST, SLOW, STRAIGHT, TEMP_MAX_TIME);
    }
    public void centerToChargingStation() 
    {
        drive.DriveStraight(-130, TEMP_DECEL_DIST, -FAST, STRAIGHT, TEMP_MAX_TIME); //dist used to be -128.75
    }

    public void centerRightTunnel() //27 points; starts between april tags 7-8(blue), 3-2(red); balance = true; #1
    {  
        pathing = true;
        path = "Center Right Tunnel";
        //values might have to be changed since we are going over the charging station
        dockToGrid();
        //score cone;
        gridToCenter();
        //pickup cone;
        centerToAreaInfrontOfDock();
        translateBackRight();
        //score cone;
        translateFowardLeft();
        gridToChargingStation();

        drive.StopDriving();
        pathing = false;
        balance.StartBalancing();
    }

    public void centerLeftTunnel() //21 points; starts between april tags 6-7(blue), 2-1(red); balance = true; #2
    {
        pathing = true;
        path = "Center Left Tunnel";
        dockToGrid();
        //score code
        gridToCenter();
        //pickup cone;
        centerToChargingStation();

        drive.StopDriving();
        pathing = false;
        balance.StartBalancing();
    }

    public void farLeftBackwardsJ() //27 points; starts infront april tag 6(blue), 3(red); balance = true; #3
    {  
        pathing = true;
        path = "Far Left Backwards J Shape";
        translateBackLeft();
        //score cone;
        translateFowardRight();
        gridToAreaInfrontOfCargo();
        //pickup cube;
        centerToGrid();
        //score cube;
        translateFowardRight();
        gridToChargingStation();

        drive.StopDriving();
        pathing = false;
        balance.StartBalancing();
    }

    public void ManualStickMovement() //33 points; starts between april tags 7-8(blue), 3-2(red); balance = true; #4
    {
        pathing = true;
        path = "Manual Car Stick";
        dockToGrid();
        //score cone;
        gridToCenter();
        //pickup cone;
        centerToAreaInfrontOfDock();
        translateBackRight();
        //score cone
        translateFowardLeft();
        gridToAreaInfrontOfCargo();
        //pickup cone
        centerToGrid();
        //score cone
        gridToChargingStation();

        drive.StopDriving();
        pathing = false;
        balance.StartBalancing();
    }

    public void farLeftBoomerangCenterLeft() //33 points; starts infront of april tag 6(blue), 3(red); balance = true; #5
    {
        pathing = true;
        path = "Far Left Boomerang Center Left";
        translateBackLeft();
        //score cone;
        translateFowardRight();
        gridToAreaInfrontOfCargo();
        //pickup cube
        centerToGrid();
        //score cube
        gridToAreaInfrontOfCargo();
        translateRight48();
        fowardToCargo();
        //pickup cone
        centerToGrid();
        //score cone
        gridToChargingStation();

        drive.StopDriving();
        pathing = false;
        balance.StartBalancing();
    }

    public void farRightBoomerangCenterRight() //33 points; starts infront of april tag 8(blue), 1(red); balance = true; #6
    {
        pathing = true;
        path = "Far Right Boomerang Center Right";
        translateBackRight();
        //score cone;
        translateFowardLeft();
        gridToAreaInfrontOfCargo();
        //pickup cube
        centerToGrid();
        //score cube
        gridToAreaInfrontOfCargo();
        translateLeft48();
        fowardToCargo();
        //pickup cone
        centerToGrid();
        //score cone
        gridToChargingStation();

        drive.StopDriving();
        pathing = false;
        balance.StartBalancing();
    }
    
    public void farLeftExitCommunity() //9 points; starts infront of april tag 6(blue), 3(red); balance = false; #7
    {
        pathing = true;
        path = "Far Left Exit Community";
        translateBackLeft();
        //score cone;
        translateFowardRight();
        gridToAreaInfrontOfCargo();
        diagonal();

        drive.StopDriving();
        pathing = false;
    }

    public static void updateShuffleboard()
    {
        SmartDashboard.putBoolean("Foward", foward);
        SmartDashboard.putBoolean("Backward", backward);
        SmartDashboard.putBoolean("Left", left);
        SmartDashboard.putBoolean("Right", right);
    }
}