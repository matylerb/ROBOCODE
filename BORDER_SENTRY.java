/*
Program Description: A tank made for robocode that stays close to the center and locks onto enemies when shooting
Author: Tyler Brady
Date: 13/02/2025
*/

package sentrygaurd;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import static robocode.util.Utils.normalRelativeAngleDegrees;
import robocode.*;
import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * Strong - a robot by (Tyler Brady)
 */

//start public class
public class BORDER_SENTRY extends Robot
{
	int distance =66;

	/**
	 * run: Strong's default behavior
	 */
	//start run
	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		setBodyColor(new Color(50, 50, 50));
		setGunColor(new Color(50, 50, 20));
		setRadarColor(new Color(90, 90, 70));
		setScanColor(Color.white);
		setBulletColor(Color.blue);

		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			// Replace the next line with any behavior you would like
		
           turnGunRight(60);
         
           scan();
			
			
		}//end while loop
	}
	public void moveTowardsCenter() {
        double centerX = 400; // Center X coordinate of the 800x800 arena
        double centerY = 400; // Center Y coordinate of the 800x800 arena
        double angleToCenter = Math.toDegrees(Math.atan2(centerX - getX(), centerY - getY()));

        // Turn towards the center
        double turnAngle = normalRelativeAngleDegrees(angleToCenter - getHeading());
        turnRight(turnAngle);

        // Move towards the center
        ahead(400); // Adjust the distance as needed
    }//end moveTowardsCenter
	

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	//sart onScannedRobot
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		
		System.out.println("Scanned Robot: " + e.getName());

        if (isBorderGuard(e)) {
            return;
        }//end if
		// Calculate exact location of the robot
		double absoluteBearing = getHeading() + e.getBearing();
		double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());

		// If it's close enough, fire!
		if (Math.abs(bearingFromGun) <= 3) {
			turnGunRight(bearingFromGun);
		
			if (getGunHeat() == 0) {
				fire(Math.min(3 - Math.abs(bearingFromGun), getEnergy() - .1));
			}//end inner if
		}//end outer if
	
		else {
			turnGunRight(bearingFromGun);
		}//end else
		if (bearingFromGun == 0) {
			scan();
		}//end if
	
	
		
	}//end onScannedRobot

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		
		turnRight(normalRelativeAngleDegrees(90 - (getHeading() - e.getHeading())));

		ahead(distance);
		distance *= -1;//go back
		scan();
		
	
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	//start onHitRobot
	public void onHitRobot(HitRobotEvent e) {
		double turnGunAmt = normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());
		System.out.println("Hit Robot: " + e.getName());

        if (e.getEnergy()>175) {
            return;
        }//end if
		
		turnGunRight(turnGunAmt);
		fire(3);
		
	}//end onHitRobot
	
	//start onHitWall
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		
		moveTowardsCenter();
		
	}//end onHitWall
	
	private boolean isBorderGuard(ScannedRobotEvent e) {
		
		return e.getEnergy() > 150;
    }//end isBorderGaurd	
}//end public class
