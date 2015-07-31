package enemies;

import levels.AttributesOfLevels;
import parents.Moving_ProjectileView;
import android.content.Context;
import bullets.Bullet_Basic;

import com.jtronlabs.to_the_moon.MainActivity;
import com.jtronlabs.to_the_moon.R;

import friendlies.ProtagonistView;
import guns.Gun;
import guns.Gun_SingleShotStraight;
import helpers.ConditionalHandler;
import helpers.KillableRunnable;

public class Shooting_DiagonalMovingView extends Enemy_ShooterView{
	
	public final static int DEFAULT_SCORE=70,
			DEFAULT_COLLISION_DAMAGE= ProtagonistView.DEFAULT_HEALTH/10,
			DEFAULT_HEALTH=ProtagonistView.DEFAULT_BULLET_DAMAGE*3,
			DEFAULT_BACKGROUND=R.drawable.ship_enemy_diagonal_full_screen,
			DEFAULT_BULLET_FREQ_INTERVAL=1500;
	public final static float 
			DEFAULT_SPEED_Y=12,
			DEFAULT_SPEED_X=DEFAULT_SPEED_Y,
			DEFAULT_SPAWN_BENEFICIAL_OBJECT_ON_DEATH=(float) .08;
	
	protected double leftThreshold,rightThreshold;
	
	public Shooting_DiagonalMovingView(Context context, int level) {
		super(context,level,
				DEFAULT_SCORE,
				DEFAULT_SPEED_Y,
				DEFAULT_SPEED_X,
				DEFAULT_COLLISION_DAMAGE,
				DEFAULT_HEALTH,
				DEFAULT_SPAWN_BENEFICIAL_OBJECT_ON_DEATH, 
				(int)context.getResources().getDimension(R.dimen.ship_diagonal_width),
				(int)context.getResources().getDimension(R.dimen.ship_diagonal_height), 
				DEFAULT_BACKGROUND); 
		
		int width = (int)context.getResources().getDimension(R.dimen.ship_diagonal_width);
		init(width);
		

		//randomly select the new enemy to diagonally traverse the whole screen or to divide a column within the screen
		if(Math.random() < 0.5){
			final int DEFAULT_DIVE_BOMBER_COLUMNS = (int)(MainActivity.getWidthPixels() / width) - 1; //standardize column size
			
			// overwrite left/write thresholds so that each diagonal mover can only stay in a column on the screen
			final float shipXInterval = (MainActivity.getWidthPixels() )/ DEFAULT_DIVE_BOMBER_COLUMNS;//divide the screen into number of columns
			final float myColPos = (int) (Math.random()*DEFAULT_DIVE_BOMBER_COLUMNS);//find this ships column
			float xPos = shipXInterval * myColPos;//x position is columInterval * this ships column. Here some left margin is also added
			this.setX(xPos);
			
			//set column boundaries
			leftThreshold=this.getSpeedX()+myColPos*shipXInterval;//farthest ship can move left is up to the boundary of the column it is in
			rightThreshold=(myColPos+1)*shipXInterval-this.getWidth()-this.getSpeedX();//farthest ship can move right is up to irs right side being at the right side of the column it is in
		}
	}
	
	private void init(int width){

		this.setThreshold((int) MainActivity.getHeightPixels()*2);//move Y to offscreen
		if(Math.random()<0.5){this.setSpeedX(this.getSpeedX() * -1);}
		
		this.setX((float) ( (MainActivity.getWidthPixels()-width)*Math.random()));
		
		leftThreshold=0;//far left of screen
		rightThreshold=MainActivity.getWidthPixels()-this.getWidth();//far right of screen

		//add guns
		final float bulletFreq = (float) (DEFAULT_BULLET_FREQ + 1.1 * DEFAULT_BULLET_FREQ * Math.random());
		Gun defaultGun = new Gun_SingleShotStraight(getContext(), this, new Bullet_Basic(
				(int)getContext().getResources().getDimension(R.dimen.laser_short_width), 
				(int)getContext().getResources().getDimension(R.dimen.laser_short_height), 
				R.drawable.laser_rectangular_enemy),
				bulletFreq, 
				DEFAULT_BULLET_SPEED_Y, 
				DEFAULT_BULLET_DAMAGE,50);
		this.addGun(defaultGun);
		this.startShooting();

		reassignMoveRunnable( new KillableRunnable(){
			@Override
			public void doWork() {		
					final float rightSideOfShip = Shooting_DiagonalMovingView.this.getX()+Shooting_DiagonalMovingView.this.getWidth();
					final float leftSideOfShip = Shooting_DiagonalMovingView.this.getX();
					float mySpeedX = Shooting_DiagonalMovingView.this.getSpeedX();
					
					final boolean pastRightSide  = rightSideOfShip>=rightThreshold;
					final boolean pastLeftSide = leftSideOfShip<=leftThreshold;
					if(pastRightSide){
						mySpeedX = Math.abs(mySpeedX) * -1;
						Shooting_DiagonalMovingView.this.setSpeedX(mySpeedX);
					}else if(pastLeftSide){
						mySpeedX = Math.abs(mySpeedX);
						Shooting_DiagonalMovingView.this.setSpeedX(mySpeedX);
					}
					
					move();
					ConditionalHandler.postIfAlive(this,Moving_ProjectileView.HOW_OFTEN_TO_MOVE,Shooting_DiagonalMovingView.this);
			}
		});
	}
	
	public void restartThreads(){
		super.restartThreads();
	}

	@Override
	public void reachedGravityPosition() {
		removeGameObject();
	}

	public static int getSpawningProbabilityWeight(int level) {
		//start at 2x giant meteor, increate a little every 12 levels until equal to 4 * giant meteor
		int probabilityWeight = (int) (AttributesOfLevels.WEIGHT_PROBABILITY_GIANT_METEOR * 2 + 
				(level/12) * AttributesOfLevels.WEIGHT_PROBABILITY_GIANT_METEOR * 1.3);
		
		probabilityWeight = Math.min(probabilityWeight, AttributesOfLevels.WEIGHT_PROBABILITY_GIANT_METEOR * 4);
		
		return probabilityWeight;
	}
	
}
