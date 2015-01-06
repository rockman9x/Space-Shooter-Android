package com.jtronlabs.to_the_moon.ship_views;

import android.content.Context;
import android.widget.RelativeLayout;

import com.jtronlabs.to_the_moon.R;
import com.jtronlabs.to_the_moon.misc.GameObjectInterface;
import com.jtronlabs.to_the_moon.misc.ProjectileView;

public class Shooting_DiagonalMovingView extends Gravity_ShootingView implements GameObjectInterface{
	
	private final static int DEFAULT_SCORE=10;
	public final static double DEFAULT_SPEED_UP=5,DEFAULT_SPEED_DOWN=.5,DEFAULT_SPEEDX=15,
			DEFAULT_COLLISION_DAMAGE=20, DEFAULT_HEALTH=10;
	private boolean travelingRight;
	
	Runnable moveDiagonalRunnable = new Runnable(){

		@Override
		public void run() {
			boolean offScreen = Shooting_DiagonalMovingView.this.move(ProjectileView.DOWN);
			if(offScreen){
				Shooting_DiagonalMovingView.this.removeView(false);
				Shooting_DiagonalMovingView.this.removeCallbacks(this);
				return;
			}
			if(travelingRight){
				Shooting_DiagonalMovingView.this.move(ProjectileView.RIGHT);
				final double farRight = widthPixels-Shooting_DiagonalMovingView.this.getSpeedX();
				final double rightSideOfShip = Shooting_DiagonalMovingView.this.getX()+Shooting_DiagonalMovingView.this.getWidth();
				if(rightSideOfShip>=farRight){//ship is on far right portion of screen
					travelingRight=false;
				}				
			}else{
				Shooting_DiagonalMovingView.this.move(ProjectileView.LEFT);
				final double farLeft = Shooting_DiagonalMovingView.this.getSpeedX();
				final double leftSideOfShip = Shooting_DiagonalMovingView.this.getX();
				if(leftSideOfShip <= farLeft){//ship is on far left portion of screen
					travelingRight=true;
				}		
			}
			
			Shooting_DiagonalMovingView.this.postDelayed(this,ProjectileView.HOW_OFTEN_TO_MOVE);
		}
		
	};
	
	public Shooting_DiagonalMovingView(Context context) {
		super(context,DEFAULT_SCORE,DEFAULT_SPEED_UP,DEFAULT_SPEED_DOWN,DEFAULT_SPEEDX,DEFAULT_COLLISION_DAMAGE,
				DEFAULT_HEALTH);
		
		final double bulletFreq = (2000+Math.random()*4000);
		spawnBulletsAutomatically(bulletFreq);
		
		this.lowestPositionThreshold=(int) heightPixels;
		
		//set image background, width, and height
		this.setImageResource(R.drawable.ufo);
		final int height_int=(int)context.getResources().getDimension(R.dimen.diagonal_shooter_height);
		int width_int = (int)context.getResources().getDimension(R.dimen.simple_enemy_shooter_width);
		this.setLayoutParams(new RelativeLayout.LayoutParams(width_int,height_int));
		
		travelingRight = (Math.random()<.5);//50% chance of moving right or left
		this.setX((float) (widthPixels*Math.random()));
		this.setY(0);
		
		cleanUpThreads();
		restartThreads();
	}
	
	public int removeView(boolean showExplosion){
		cleanUpThreads();
		return super.removeView(showExplosion);
	}
	
	public void cleanUpThreads(){
		super.cleanUpThreads();
		this.removeCallbacks(moveDiagonalRunnable);
	}
	public void restartThreads(){
		super.restartThreads();
		this.postDelayed(moveDiagonalRunnable, ProjectileView.HOW_OFTEN_TO_MOVE);
	}
}
