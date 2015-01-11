package enemies_orbiters;

import interfaces.GameObjectInterface;
import android.content.Context;


public class Orbiter_Circle_IncSpeedOnHitView extends Orbiter_CircleView implements GameObjectInterface {
	
	public static final int ANGULAR_INCREMENT=3;
	
	public Orbiter_Circle_IncSpeedOnHitView(Context context,int score,double speedY, double speedX,double collisionDamage, 
			double health, double bulletFreq,
			float heightView,float widthView,double bulletDamage,double bulletVerticalSpeed,double probSpawnBeneficialObjecyUponDeath,
			int circleRadius,int angularVelocityInDegrees) {
		super( context, score, speedY,  speedX, collisionDamage, 
				 health, 
				 heightView, widthView, probSpawnBeneficialObjecyUponDeath,
				 circleRadius, angularVelocityInDegrees);
	}
	
	/**
	 * increase absolute value of angular velocity by 1
	 */
	@Override
	public boolean takeDamage(double amountOfDamage){
		this.setAngularVelocity(this.getAngularVelocity() + this.getAngularVelocity()/(Math.abs(this.getAngularVelocity())));
		return super.takeDamage(amountOfDamage);
	}
}
