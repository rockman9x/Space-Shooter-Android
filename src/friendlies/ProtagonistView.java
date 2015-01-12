package friendlies;

import guns.Gun;
import guns.Gun_AngledDualShot;
import parents.Moving_ProjectileView;
import android.content.Context;
import android.util.AttributeSet;
import bullets.Bullet_Basic_LaserShort;

import com.jtronlabs.to_the_moon.MainActivity;

public class ProtagonistView extends Friendly_ShooterView{
	
	public final static double DEFAULT_SPEED_Y=12.5,
			DEFAULT_SPEEDX=14,
			DEFAULT_COLLISION_DAMAGE=100, 
			DEFAULT_HEALTH=1000,
			DEFAULT_BULLET_SPEED_Y=30,
			DEFAULT_BULLET_DAMAGE=10, 
			DEFAULT_BULLET_FREQ=500;
	
	private final int HOW_OFTEN_TO_MOVE_ROCKET=50;
	private int directionMoving=Moving_ProjectileView.LEFT;
	
	public ProtagonistView(Context context, AttributeSet at) {
		super(context, at,DEFAULT_SPEED_Y,DEFAULT_SPEEDX,DEFAULT_COLLISION_DAMAGE,
				DEFAULT_HEALTH);

		Gun gun = new Gun_AngledDualShot(context, this, new Bullet_Basic_LaserShort(),
				DEFAULT_BULLET_FREQ,DEFAULT_BULLET_DAMAGE,DEFAULT_BULLET_SPEED_Y);
		this.giveNewGun(gun);

		//if created via Attribute set, it is safe to set up rocket exhaust runnable. CURRENTLY NOT WORKING AS ID LIKE
//		this.post(exhaustRunnable);
	}

	public ProtagonistView(Context context) {
		super(context,DEFAULT_SPEED_Y,DEFAULT_SPEEDX,DEFAULT_COLLISION_DAMAGE,
				DEFAULT_HEALTH);
	}
	
	
//	private static final long EXHAUST_VISIBLE_TIME=500,EXHAUST_MOVE_FREQ=20;
//	private static final double EXHAUST_FREQ=15000;
//	 
//	private int count = 0;
//    
//    Runnable exhaustRunnable = new Runnable(){
//    	 @Override
//         public void run() {
//			//ensure view is not removed before running
//			if( ! ProtagonistView.this.isRemoved() && GameActivity.rocketExhaust!=null){
//				if(count*EXHAUST_MOVE_FREQ<EXHAUST_VISIBLE_TIME){
//					GameActivity.rocketExhaust.setVisibility(View.VISIBLE);					
//		    		 //position the exhaust
//					final float y=ProtagonistView.this.getY()+ProtagonistView.this.getHeight(); //set the fire's Y pos to behind rocket
//					final float averageRocketsX= (2 * ProtagonistView.this.getX()+ProtagonistView.this.getWidth() )/2;//find average of rocket's left and right x pos
//					final float x = averageRocketsX-GameActivity.rocketExhaust.getWidth()/2;//fire's new X pos should set the middle of fire to middle of rocket
//					GameActivity.rocketExhaust.setY(y);
//					GameActivity.rocketExhaust.setX(x);
//					count++;
//
//					ProtagonistView.this.postDelayed(this,EXHAUST_MOVE_FREQ);//repost this runnable so the exhaust will reposition quickly
//				
//				}else{
//					count=0;
//					GameActivity.rocketExhaust.setVisibility(View.INVISIBLE);					
//					ProtagonistView.this.postDelayed(this,(long) (EXHAUST_FREQ+ 2 * EXHAUST_FREQ*Math.random()));//repost this runnable so the exhaust will reposition quickly
//				}
//			}
//         }
//    };
    
	private Runnable moveRunnable = new Runnable(){
		@Override
		public void run() {
			ProtagonistView.this.moveDirection(directionMoving);
			ProtagonistView.this.postDelayed(moveRunnable,HOW_OFTEN_TO_MOVE_ROCKET);
		}
		
	};
	
	public void restartThreads(){
		super.restartThreads();
		this.stopShooting();//super will start the gun shooting. For the protagonist, gun must not be shooting on restart
	}
	
	/**
	 * Do not allow the rocket to move off the sides of the screen
	 */
	@Override
	public boolean moveDirection(int direction){
		float x =this.getX();
		
		switch(direction){
		case Moving_ProjectileView.RIGHT:
			x+=this.getSpeedX();
			if((x+this.getWidth())<=MainActivity.getWidthPixels()){this.setX(x);}
			break;
		case Moving_ProjectileView.LEFT:
			x-=this.getSpeedX();
			if(x>=0){this.setX(x);}			
			break;
		default:
			return super.moveDirection(direction);			
		}
		return false;
	}
	
	public void beginMoving(int direction){
		directionMoving = direction;
		this.post(moveRunnable);
	}
	public void stopMoving(){
		this.removeCallbacks(moveRunnable);
	}
}