package guns;
   

import com.jtronlabs.to_the_moon.R;

import helpers.MediaController;
import interfaces.Shooter;
import android.content.Context;
import bullets.Bullet_Interface;
import bullets.BulletView;


public class Gun_SingleShotStraight extends Gun {
	
	public Gun_SingleShotStraight(Context context,Shooter theShooter,
			Bullet_Interface bulletType,
			float bulletFrequency,
			float bulletSpeedVertical,
			int bulletDmg,
			int positionOnShooterAsAPercentage) {
		super(context,theShooter,bulletType, bulletFrequency, bulletSpeedVertical, bulletDmg,positionOnShooterAsAPercentage);
	}
	
	public boolean shoot(){
		MediaController.playSoundEffect(ctx, MediaController.SOUND_LASER_SHOOT2);
		
		//create one bullet at center of shooter
		BulletView bulletMid = myBulletType.getBullet(ctx, shooter,bulletSpeedY,bulletDamage);

		bulletMid.setPositionOnShooterAsAPercentage(this.posOnShooter);
		
		return false;
	}
	
}
