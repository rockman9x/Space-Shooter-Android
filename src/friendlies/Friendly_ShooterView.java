package friendlies;

import guns.Gun;
import interfaces.Shooter;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import bullets.BulletView;

import com.jtronlabs.to_the_moon.GameActivity;

public class Friendly_ShooterView extends FriendlyView implements Shooter{
	
	
	//myGun needs to be set in a specific View's class
	private ArrayList<Gun> myGuns;
	private ArrayList<BulletView> myBullets;
	
	private int bulletFreqWeight, 
		bulletDamageWeight,
		bullletVerticalSpeedWeight,
		currentGunConfiguration;

	public Friendly_ShooterView(Context context, double projectileSpeedY,double projectileSpeedX, 
			double projectileDamage,double projectileHealth) {
		super(context,projectileSpeedY,projectileSpeedX,
				projectileDamage,projectileHealth);

		bulletFreqWeight=0; 
		bulletDamageWeight=0;
		bullletVerticalSpeedWeight=0;
		
		myGuns= new ArrayList<Gun>();
		myBullets = new ArrayList<BulletView>();
 
		startShooting();//protagonist spawns through Attirbute set constructor, so this is safe
	}

	public Friendly_ShooterView(Context context,AttributeSet at, double projectileSpeedY,double projectileSpeedX, 
			double projectileDamage,double projectileHealth) {
		super(context, at,projectileSpeedY,projectileSpeedX,
				projectileDamage,projectileHealth);

		bulletFreqWeight=0; 
		bulletDamageWeight=0;
		bullletVerticalSpeedWeight=0;
		
		myGuns= new ArrayList<Gun>();
		myBullets = new ArrayList<BulletView>();
		
		this.removeCallbacks(null);
	}
	
	public void incrementBulletDamageWeight(){
		bulletDamageWeight++;
	}
	
	public int costToUpgradeGun(){
		switch(currentGunConfiguration){
		case 0:
			return 100;
		case 1:
			return 3000;
		case 2: 
			return 7000;
		case 3:
			return 15000;
		default:
			return 25000;
		}
	}
	public void upgradeGun(){
		currentGunConfiguration++;
		switch(currentGunConfiguration){
		case 1:
			break;
		}
	}

	public int getGunLevel(){
		return this.currentGunConfiguration;
	}
	public void incrementBulletSpeedYWeight(){
		bullletVerticalSpeedWeight++;
	}
	public void incrementBulletFreqWeight(){
		bulletFreqWeight++;
	}
	public int getBulletDamageWeight(){
		return bulletDamageWeight;
	}
	public int getBulletBulletSpeedYWeight(){
		return bullletVerticalSpeedWeight;
	}
	public int getBulletBulletFreqWeight(){
		return bulletFreqWeight;
	}
	
	
	@Override
	public void removeGameObject(){
		super.removeGameObject();
		
		stopShooting();
		if(this.getMyBullets().size()==0){
			GameActivity.friendlies.remove(this);			
		}
	}
	
	@Override
	public void restartThreads(){
		startShooting();
		super.restartThreads();
	}
	@Override
	public void setMyBullets(ArrayList<BulletView> bullets) {
		myBullets=bullets;
	}

	@Override
	public ArrayList<BulletView> getMyBullets() {
		return myBullets;
	}

	@Override
	public void startShooting() {
		for(Gun gun: myGuns){
			gun.startShooting();
		}
	}

	@Override
	public void stopShooting() {
		for(Gun gun: myGuns){
			gun.stopShooting();
		}
	}

	@Override
	public boolean isDead() {
		return super.isRemoved() || super.getHealth()<=0;
	}

	@Override
	public ViewGroup getMyScreen() {
		return (ViewGroup)this.getParent();
	}

	@Override
	public boolean isFriendly() {
		return true;
	}

	@Override
	public void addGun(Gun newGun) {
		myGuns.add(newGun);
	}

	@Override
	public ArrayList<Gun> getAllGuns() {
		return myGuns;
	}

	@Override
	public void giveNewGun(Gun newGun) {
		for(int i=myGuns.size()-1; i>=0;i--){
			myGuns.remove(i);
		}
		myGuns.add(newGun);
	}
	
	
	
	
}