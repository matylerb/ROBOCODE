# ROBOCODE
The main loop of the robot continuously rotates its gun and scans for enemies:

java
Copy
Edit
while(true) {
    turnGunRight(60);
    scan();
}
This makes the gun sweep back and forth, increasing the chances of detecting enemies.
2. Moving Towards the Center
When needed, the robot can move towards the center of the battlefield:
java
Copy
Edit
public void moveTowardsCenter() {
    double centerX = 400; 
    double centerY = 400;
    double angleToCenter = Math.toDegrees(Math.atan2(centerX - getX(), centerY - getY()));
    double turnAngle = normalRelativeAngleDegrees(angleToCenter - getHeading());
    turnRight(turnAngle);
    ahead(400);
}
It calculates the angle to the center and moves forward, ensuring it stays in the middle of the map.
3. Targeting and Shooting (onScannedRobot)
When an enemy is detected:

java
Copy
Edit
double absoluteBearing = getHeading() + e.getBearing();
double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());
The absolute bearing of the enemy is calculated.
The angle needed to turn the gun towards the enemy is found.
If the gun is already almost aligned, fire:

java
Copy
Edit
if (Math.abs(bearingFromGun) <= 3) {
    turnGunRight(bearingFromGun);
    if (getGunHeat() == 0) {
        fire(Math.min(3 - Math.abs(bearingFromGun), getEnergy() - .1));
    }
}
It adjusts the gun slightly and shoots with an appropriate firepower.
The firepower is determined based on the robot’s energy and the target alignment.
It ignores high-energy robots (possibly border sentries):

java
Copy
Edit
private boolean isBorderGuard(ScannedRobotEvent e) {
    return e.getEnergy() > 150;
}
This prevents wasting bullets on strong defensive robots.
4. Evading Bullets (onHitByBullet)
When hit by a bullet, the robot:
java
Copy
Edit
turnRight(normalRelativeAngleDegrees(90 - (getHeading() - e.getHeading())));
ahead(distance);
distance *= -1;
scan();
Turns 90 degrees relative to the attack direction.
Moves forward a set distance (then reverses next time to dodge better).
5. Collisions (onHitRobot & onHitWall)
When it collides with another robot:

java
Copy
Edit
if (e.getEnergy() > 175) {
    return;
}
If the enemy has too much energy, it avoids attacking.
Otherwise, it turns the gun and fires at full power.
When it hits a wall, it moves back towards the center:

java
Copy
Edit
moveTowardsCenter();
Ensures the robot stays mobile and avoids getting stuck.
