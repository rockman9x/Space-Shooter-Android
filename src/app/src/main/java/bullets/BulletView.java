package bullets;
  
import interfaces.Shooter;
import parents.MovingView;
import parents.Moving_ProjectileView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jtronlabs.space_shooter.GameLoop;

import java.util.ArrayList;

/**
 * By default, a bullet moves straight and spawns in the middle of its shooter
 * @author lowre_000
 *
 */
public class BulletView extends Moving_ProjectileView{

	protected Shooter theOneWhoShotMe;

	public BulletView(int xPosOnShooterAsPercentage,RelativeLayout layout,Shooter shooter,float bulletSpeedY,
			int bulletDamage,int width,int height,int imageId) {
		super(xPosOnShooterAsPercentageToGlobalXPos(xPosOnShooterAsPercentage,shooter,width),
				((MovingView)shooter).getMidY(),
				layout,
				getBulletSpeedY(bulletSpeedY,shooter),
				0,
				bulletDamage,1, width, height, imageId);

		initBullet(shooter);
	}

	public void unRemoveBullet(int xPosOnShooterAsPercentage,RelativeLayout layout,float bulletSpeedY,
							   int width,int height,int imageId,Shooter shooter,int bulletDamage){
		this.setDamage(bulletDamage);

		super.unRemove(BulletView.xPosOnShooterAsPercentageToGlobalXPos(xPosOnShooterAsPercentage,shooter,width),
				((MovingView)shooter).getMidY(),
				layout,
				getBulletSpeedY(bulletSpeedY,shooter),
				0,
				width,
				height,
				imageId);

		initBullet(shooter);
	}

	public void initBullet(Shooter shooter){
		//set instance variables
		theOneWhoShotMe=shooter;

		//position bullet behind shooter
		ViewGroup newParent = (ViewGroup)theOneWhoShotMe.getParent();
		if(newParent!=null){
			newParent.removeView(this);//bullet already added to parent in MovingView's instantiation or in unRemove method
			int shooterIndex = newParent.indexOfChild( (View) theOneWhoShotMe);

			ViewGroup currentParent = (ViewGroup)this.getParent();
			if(currentParent!=null){ currentParent.removeView(this); }
			newParent.addView(this, shooterIndex);

			if(theOneWhoShotMe.isFriendly()){
				GameLoop.friendlyBullets.add(this);
			}else{
				GameLoop.enemyBullets.add(this);
			}
			theOneWhoShotMe.getMyBullets().add(this);
		}else{
			this.setViewToBeRemovedOnNextRendering();
			//for some reason, shooters continue to fire even after being killed. This simple check ensure that the shooter
			//has not been removed from his parent. If he has, then remove this bullet
		}
	}
	private static float getBulletSpeedY(float movingSpeedY,Shooter shooter){
		float speedY = Math.abs(movingSpeedY);
		if(shooter.isFriendly()){
			speedY *= -1 ;
		}
		return speedY;
	}
	
	public void setBulletRotation(){	
		float rotVal=0;
		
		double arcTan = Math.atan(this.getSpeedX()/Math.abs(this.getSpeedY()) );
		if( ! theOneWhoShotMe.isFriendly()){
			arcTan = Math.atan(-this.getSpeedX()/Math.abs(this.getSpeedY()) );
			arcTan+=Math.PI;//flip bullet image around so it is pointing downwards
		}
		rotVal = (float) Math.toDegrees(arcTan);
			
		this.setRotation(rotVal);
	}


	public static float xPosOnShooterAsPercentageToGlobalXPos(int positionOnShooterAsAPercentageOfWidthFromTheLeftSide,Shooter shooter, float bulletWidth){
		if(positionOnShooterAsAPercentageOfWidthFromTheLeftSide < 0 || positionOnShooterAsAPercentageOfWidthFromTheLeftSide > 100){
			throw new IllegalArgumentException("Not a valid percentage");
		}
		final float posRelativeToShooter= (float) (shooter.getWidth() * positionOnShooterAsAPercentageOfWidthFromTheLeftSide/100.0);
		final float middleOfBulletOnShootingPos = (float) (posRelativeToShooter+shooter.getX()-bulletWidth/2.0);
		return (middleOfBulletOnShootingPos);

	}
	/*
	public void setXPositionOnShooterAsAPercentage(int positionOnShooterAsAPercentageOfWidthFromTheLeftSide) throws IllegalArgumentException{
		setX(xPosOnShooterAsPercentageToGlobalXPos(positionOnShooterAsAPercentageOfWidthFromTheLeftSide,theOneWhoShotMe,this.getLayoutParams().width));
	}
	*/

	/**
	 * Remove bullet from Shooter's list of bullets  and GameActivity's list
	 */
	public void removeGameObject(){
		theOneWhoShotMe.getMyBullets().remove(this);

		super.removeGameObject();//needs to be the last thing called for handler to remove all callbacks
	}
	

	@Override
	public void updateViewSpeed(long deltaTime) {
		//do nothing - movePhysicalPosition at constant speed
	}
	
	@Override 
	public void movePhysicalPosition(long deltaTime){
		setBulletRotation();
		super.movePhysicalPosition(deltaTime);
	}
}
