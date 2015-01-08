package com.jtronlabs.to_the_moon.guns;
  
import android.content.Context;
import android.widget.RelativeLayout;

import com.jtronlabs.to_the_moon.bullets.Bullet;
import com.jtronlabs.to_the_moon.bullets.Projectile_BulletView;
import com.jtronlabs.to_the_moon.views.Gravity_ShootingView;

public class Gun_Upgradeable_StraightDualShot extends Gun_Upgradeable {
	
	private static final double STRAIGHT_BULLET=0;
	
	public Gun_Upgradeable_StraightDualShot(Context context,Gravity_ShootingView theShooter,boolean shootingUpwards,
			double bulletSpeedVertical,double bulletDamage,double bulletFrequency) {
		super(context,theShooter,shootingUpwards,bulletSpeedVertical,bulletDamage,bulletFrequency);
	}
	public Gun_Upgradeable_StraightDualShot(Context context,
			Gravity_ShootingView theShooter) {
		super(context,theShooter);
	}
	public boolean shoot(){
		Projectile_BulletView bulletLeft = shooter.myBulletType.getBullet(ctx, shooter, shootingUp, bulletSpeedY, 
				STRAIGHT_BULLET,bulletDamage, Bullet.BULLET_LEFT);
		Projectile_BulletView bulletRight = shooter.myBulletType.getBullet(ctx, shooter, shootingUp, bulletSpeedY, 
				STRAIGHT_BULLET,bulletDamage, Bullet.BULLET_RIGHT);

		//add bullets to layout
		((RelativeLayout)shooter.getParent()).addView(bulletLeft,1);
		((RelativeLayout)shooter.getParent()).addView(bulletRight,1);

		//add bullets to shooter's list of bullets
		myBullets.add(bulletLeft);
		myBullets.add(bulletRight);

		return false;
	}
	@Override
	public Gun_Upgradeable upgradeGun() {
		return this;
	}
	
	@Override
	public Gun_Upgradeable downgradeGun() {

		this.stopShooting();
		Gun_Upgradeable_StraightSingleShot newGun = new Gun_Upgradeable_StraightSingleShot(ctx,shooter);
		this.transferGunProperties(newGun);
		return newGun;
	}
	
}