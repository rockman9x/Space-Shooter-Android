package guns;
  
import interfaces.Shooter;
import support.ConditionalHandler;
import support.KillableRunnable;
import android.content.Context;
import bullets.Bullet;

import com.jtronlabs.to_the_moon.MainActivity;


/**
 * 
 * @author JAMES LOWREY
 *
 */
public abstract class Gun {
	
	/**
	 * Create bullets of the shooter's type at the shooter's position. Properties such as number of bullets, 
	 * direction of bullets, initial position of bullets, and more may be different
	 * @return true if gun is a special and is out of ammo. False otherwise.
	 */
	public abstract boolean shoot();	
	
	Shooter shooter;
	Bullet myBulletType;
	Context ctx;
	

	protected float bulletFreq,bulletSpeedY,bulletSpeedX;
	protected int bulletDamage,posOnShooter;
	
	private KillableRunnable shootingRunnable = new KillableRunnable(){
		  	@Override
		      public void doWork() {
	  				Gun.this.shoot();
	  				ConditionalHandler.postDelayedIfShooting(this, (long) bulletFreq,shooter);
		  		}
			};
	
	public Gun(Context context,Shooter theShooter,Bullet bulletType,
			float bulletFrequency,float bulletSpeedVertical,int bulletDmg,int positionOnShooterAsAPercentage) {
		ctx=context;
		
		posOnShooter=positionOnShooterAsAPercentage;
		bulletFreq=bulletFrequency;
		bulletSpeedX=0;
		bulletSpeedY=bulletSpeedVertical*MainActivity.getScreenDens();
		bulletDamage=bulletDmg;
		myBulletType = bulletType;
		shooter=theShooter;
	} 
	
	public void startShootingImmediately(){
		ConditionalHandler.postDelayedIfShooting(shootingRunnable,0,shooter);
	}
	public void startShootingDelayed(){
		ConditionalHandler.postDelayedIfShooting(shootingRunnable,(long)bulletFreq,shooter);	
	}
	
	public void stopShooting(){
		shooter.removeCallbacks(shootingRunnable);
	}
	
	public Bullet getBulletType(){
		return myBulletType;
	}
	
	public void setBulletType(Bullet newBullet){
		myBulletType= newBullet;
	}
	
	public double getBulletSpeedY() {
		return bulletSpeedY;
	}

	public double getBulletDamage() {
		return bulletDamage;
	}

	public double getBulletFreq() {
		return bulletFreq;
	}
	
	public void setBulletFreq(float freq) {
		bulletFreq=freq;
	}

	public void setBulletSpeedY(float newSpeed) {
		bulletSpeedY=newSpeed;
	}

	public void setBulletDamage(int newDamage) {
		bulletDamage = newDamage;
	}
	
	public void destroyGun(){
//		myBulletType.removeBulletType();
//		ctx=null;
//		shooter=null;
//		myBulletType=null;
//		
	}

}