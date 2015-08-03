package bonuses;

import levels.LevelSystem;
import parents.MovingView;
import android.widget.RelativeLayout;

import com.jtronlabs.to_the_moon.R;

public abstract class BonusView extends MovingView {
	
	public BonusView(RelativeLayout layout,float positionX,float positionY) {
		super(layout,DEFAULT_SPEED_Y,0,
				(int) layout.getContext().getResources().getDimension(R.dimen.bonus_background_len),
				(int)layout.getContext().getResources().getDimension(R.dimen.bonus_background_len)
				,0);	//children classes will set image resource
		
		//set background and position
		this.setBackgroundResource(R.drawable.white_center_red_outline);
		this.setX(positionX);
		this.setY(positionY);
		
		//add to collision detector
		LevelSystem.bonuses.add(this);
	}
	
	/**
	 * Bonus has been picked up by protagonist or his ally. Apply benefit to protagonist.
	 */
	public abstract void applyBenefit();
	
	@Override
	public void removeGameObject(){		
		LevelSystem.bonuses.remove(this);
		super.defaultCleanupOnRemoval();
	}
	
	public static void displayRandomBonusView(RelativeLayout layout,float positionX,float positionY){
		double rand = Math.random();
		
		if(rand<.5){
			new Bonus_ScoreView(layout,positionX,positionY);
		}else{
			new Bonus_HealView(layout,positionX,positionY);
		}
	}
}
