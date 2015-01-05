package com.jtronlabs.to_the_moon.views;

import android.content.Context;
import android.widget.RelativeLayout.LayoutParams;

import com.jtronlabs.to_the_moon.R;

public class MeteorView extends GravityView implements GameObject{
	
	private final static double DEFAULT_SPEED_UP=1,DEFAULT_SPEED_DOWN=4,DEFAULT_SPEEDX=1,DEFAULT_COLLISION_DAMAGE=12, 
			DEFAULT_HEALTH=5;
	
	public MeteorView(Context context) {
		super(context,DEFAULT_SPEED_UP,DEFAULT_SPEED_DOWN,DEFAULT_SPEEDX,DEFAULT_COLLISION_DAMAGE,
				DEFAULT_HEALTH);
		//set image background
		this.setImageResource(R.drawable.meteor);
		
		//set image width,length
		int len=(int)ctx.getResources().getDimension(R.dimen.meteor_length);
		this.setLayoutParams(new LayoutParams(len,len));
		
		//set initial position of View
		float xRand = (float) ((widthPixels-len)*Math.random());
		this.setX(xRand);
		this.setY(0);
		
		cleanUpThreads();
		restartThreads();
	}

	public void cleanUpThreads(){
		super.cleanUpThreads();
	}
	public void restartThreads(){
		super.restartThreads();
	}
}