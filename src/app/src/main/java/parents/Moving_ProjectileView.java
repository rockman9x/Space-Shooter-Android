package parents;

import helpers.KillableRunnable;
import interfaces.Projectile;
import android.graphics.Color;
import android.widget.RelativeLayout;

import com.jtronlabs.space_shooter.R;

/**
 * ImageView object to be placed on screen with gameplay properties such as health, score, speeds, and more
 * @author JAMES LOWREY
 *
 */
public abstract class Moving_ProjectileView extends MovingView implements Projectile{

	int damage, health,maxHealth;
	
	public Moving_ProjectileView(float xInitialPosition,float yInitialPosition,RelativeLayout layout,float movingSpeedY,float movingSpeedX,int projectileDamage,
			int projectileHealth,int width,int height,int imageId) {
		super(xInitialPosition,yInitialPosition,layout, movingSpeedY, movingSpeedX, width, height, imageId);

		initMoving_ProjectileView(projectileDamage,projectileHealth);
	}
	private void initMoving_ProjectileView(int projectileDamage,
										   int projectileHealth){
		damage=projectileDamage;
		health=projectileHealth;
		maxHealth=projectileHealth;//set at beginning of life, or overwrite on heal()
	}
	public void unRemoveProjectile(float xInitialPosition,float yInitialPosition,RelativeLayout layout,float movingSpeedY,float movingSpeedX,int projectileDamage,
						 int projectileHealth,int width,int height,int imageId) {
		super.unRemove(xInitialPosition,yInitialPosition,layout,movingSpeedY,movingSpeedX,width,height,imageId);

		initMoving_ProjectileView(projectileDamage,projectileHealth);
	}

	/**
	 * 
	 * Subtract @param/amountOfDamage from this View's health. Flash the View's background red to indicate damage taken. Remove View from game if it dies
	 * @param amountOfDamage-how much the view's health should be subtracted
	 * @return True if thi dies
	 */
	public boolean takeDamage(int amountOfDamage){		
		boolean dies= false;
		health-=amountOfDamage;
		
		if(health<=0){
			setViewToBeRemovedOnNextRendering();
			dies = true;
		}else{
			//set the background behind this view, and then remove it after howLongBackgroundIsApplied milliseconds
			this.setBackgroundResource(R.drawable.view_damaged);
			final int howLongBackgroundIsApplied=80;
			KillableRunnable removeDmg = new KillableRunnable(){
				@Override
				public void doWork() {Moving_ProjectileView.this.setBackgroundColor(Color.TRANSPARENT);}
			};
			postDelayed(removeDmg,howLongBackgroundIsApplied);			
//			createExplosion();
		}
		
		return dies;
	}
	
	public void heal(int howMuchHealed){
		health+=Math.abs(howMuchHealed);
		if(health>maxHealth){
			maxHealth=health;
		}
	}
	//SET METHODS
	public void setDamage(int newDamage){
		damage=newDamage;
	}
	public void setHealth(int healthValue){
		if(healthValue>maxHealth){
			maxHealth = healthValue;
		}
		this.health=healthValue;
	}
	
	
	//GET METHODS
	public int getHealth(){
		return health;
	}
	public int getMaxHealth(){
		return maxHealth;
	}
	public int getDamage(){
		return damage;
	}

	@Override
	public void restartThreads() {
		// do nothing for this class. Override in a child class if there are threads added
	}
}
