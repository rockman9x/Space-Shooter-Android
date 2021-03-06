package enemies;

import levels.AttributesOfLevels;
import android.widget.RelativeLayout;
import bullets.Bullet_Basic;
import bullets.Bullet_Interface;

import com.jtronlabs.space_shooter.MainActivity;
import com.jtronlabs.space_shooter.R;

import enemies_non_shooters.Gravity_MeteorView;
import friendlies.ProtagonistView;
import guns.Gun;
import guns.Gun_SingleShotStraight;

public class Shooting_DiagonalMovingView extends Enemy_ShooterView{
	
	public static final float	DEFAULT_SPEED_Y = (float) (Gravity_MeteorView.DEFAULT_SPEED_Y * .45), //units = frame rate independent density pixels per milliseconds
			DEFAULT_SPEED_X = (float) (DEFAULT_SPEED_Y * .6); 

	public final static int DEFAULT_SCORE=70,
			DEFAULT_COLLISION_DAMAGE= ProtagonistView.DEFAULT_HEALTH/10,
			DEFAULT_HEALTH=(int) (ProtagonistView.DEFAULT_BULLET_DAMAGE * 6.3),
			DEFAULT_BACKGROUND=R.drawable.ship_enemy_diagonal_full_screen,
			DEFAULT_BULLET_FREQ_INTERVAL=1500,
			DELAY_BTW_SPAWN_IN_LOTS_OF_ENEMIES_WAVE = 1000,
			DELAY_AFTER_SPAWN_IN_LOTS_OF_ENEMIES_WAVE = 4000;
	public final static float 
			DEFAULT_SPAWN_BENEFICIAL_OBJECT_ON_DEATH=(float) .08;
	
	protected double leftThreshold,rightThreshold;
	
	public Shooting_DiagonalMovingView(RelativeLayout layout, int level) {
		super(
				getRandomXPosInMiddle(layout.getContext().getResources().getDimension(R.dimen.ship_diagonal_width)),
				layout,level,
				DEFAULT_SCORE,
				DEFAULT_SPEED_Y,
				DEFAULT_SPEED_X,
				DEFAULT_COLLISION_DAMAGE,
				DEFAULT_HEALTH,
				DEFAULT_SPAWN_BENEFICIAL_OBJECT_ON_DEATH, 
				(int)layout.getContext().getResources().getDimension(R.dimen.ship_diagonal_width),
				(int)layout.getContext().getResources().getDimension(R.dimen.ship_diagonal_height), 
				DEFAULT_BACKGROUND); 
		
		int width = (int)getContext().getResources().getDimension(R.dimen.ship_diagonal_width);
		init(width,level);
		

		//randomly select the new enemy to diagonally traverse one column within the screen
		if(Math.random() < 0.5){
			final int DEFAULT_DIVE_BOMBER_COLUMNS = (int)(MainActivity.getWidthPixels() / width) - 1; //standardize column size
			
			// overwrite left/write thresholds so that each diagonal mover can only stay in a column on the screen
			final float shipXInterval = (MainActivity.getWidthPixels() )/ DEFAULT_DIVE_BOMBER_COLUMNS;//divide the screen into number of columns
			final float myColPos = (int) (Math.random()*DEFAULT_DIVE_BOMBER_COLUMNS);//find this ships column
			float xPos = shipXInterval * myColPos;//x position is columInterval * this ships column. Here some left margin is also added
			this.setX(xPos);
			
			//set column boundaries
			leftThreshold=this.getSpeedX()+myColPos*shipXInterval;//farthest ship can movePhysicalPosition left is up to the boundary of the column it is in
			rightThreshold=(myColPos+1)*shipXInterval-this.getWidth()-this.getSpeedX();//farthest ship can movePhysicalPosition right is up to irs right side being at the right side of the column it is in
		}
	}
	
	private void init(int width,int level){
		//set full screen diagonal mover
		this.setGravityThreshold((int) MainActivity.getHeightPixels() * 2);//movePhysicalPosition Y to offscreen
		if(Math.random()<0.5){this.setSpeedX(this.getSpeedX() * -1);}
		leftThreshold=0;//far left of screen
		rightThreshold=MainActivity.getWidthPixels()-this.getWidth();//far right of screen

		//add guns
		float bulletFreq = (float) (DEFAULT_BULLET_FREQ + 1.3 * DEFAULT_BULLET_FREQ * Math.random());
		Gun defaultGun = new Gun_SingleShotStraight(getMyLayout(), this, new Bullet_Basic(
				(int)getContext().getResources().getDimension(R.dimen.bullet_round_xsmall_length), 
				(int)getContext().getResources().getDimension(R.dimen.bullet_round_xsmall_length), 
				R.drawable.bullet_laser_round_red),
				(float) (bulletFreq * 0.80), 
				Bullet_Interface.DEFAULT_BULLET_SPEED_Y, 
				Bullet_Interface.WEAK_BULLET_DMG,50);
		this.addGun(defaultGun);
		
		if(level > AttributesOfLevels.LEVELS_LOW * 1.5 && level < AttributesOfLevels.LEVELS_MED){
			removeAllGuns();
			Gun g1 = new Gun_SingleShotStraight(getMyLayout(), this, new Bullet_Basic(
					(int)getContext().getResources().getDimension(R.dimen.bullet_mid_fat_width), 
					(int)getContext().getResources().getDimension(R.dimen.bullet_rec_short_height), 
					R.drawable.bullet_laser_round_red),
					bulletFreq, 
					Bullet_Interface.DEFAULT_BULLET_SPEED_Y, 
					DEFAULT_BULLET_DAMAGE,50);
			this.addGun(g1);
		}else if(level>AttributesOfLevels.LEVELS_MED){
			removeAllGuns();
			bulletFreq *= 0.75;
			/*
			Gun g0 = new Gun_SingleShotStraight(getMyLayout(), this, new Bullet_Basic(
					(int)getContext().getResources().getDimension(R.dimen.bullet_round_xsmall_length), 
					(int)getContext().getResources().getDimension(R.dimen.bullet_round_xsmall_length), 
					R.drawable.bullet_laser_round_red),
					bulletFreq, 
					Bullet_Interface.DEFAULT_BULLET_SPEED_Y, 
					Bullet_Interface.WEAK_BULLET_DMG,50);
			this.addGun(g0);
			*/

			Gun g1 = new Gun_SingleShotStraight(getMyLayout(), this, new Bullet_Basic(
					(int)getContext().getResources().getDimension(R.dimen.bullet_round_xsmall_length), 
					(int)getContext().getResources().getDimension(R.dimen.bullet_round_xsmall_length), 
					R.drawable.bullet_laser_round_red),
					bulletFreq, 
					Bullet_Interface.DEFAULT_BULLET_SPEED_Y, 
					Bullet_Interface.WEAK_BULLET_DMG,35);
			Gun g2 = new Gun_SingleShotStraight(getMyLayout(), this, new Bullet_Basic(
					(int)getContext().getResources().getDimension(R.dimen.bullet_round_xsmall_length), 
					(int)getContext().getResources().getDimension(R.dimen.bullet_round_xsmall_length), 
					R.drawable.bullet_laser_round_red),
					bulletFreq, 
					Bullet_Interface.DEFAULT_BULLET_SPEED_Y, 
					Bullet_Interface.WEAK_BULLET_DMG,65);
			this.addGun(g1);
			this.addGun(g2);
		}
		this.startShooting();	
	}
	
	public void restartThreads(){
		super.restartThreads();
	}

	public static int getSpawningProbabilityWeight(int level) {
		//start at 2x giant meteor, increate a little every 12 levels until equal to 4 * giant meteor
		int probabilityWeight = (int) (AttributesOfLevels.STANDARD_PROB_WEIGHT * 2 + 
				(level/8) * AttributesOfLevels.STANDARD_PROB_WEIGHT / 2);
		
		probabilityWeight = Math.min(probabilityWeight, AttributesOfLevels.STANDARD_PROB_WEIGHT *3);
		
		return probabilityWeight;
	}
	
	public static int getSpawningProbabilityWeightForLotsOfEnemiesWave(int level){
		int probabilityWeight = 0;
	
		if(level >= AttributesOfLevels.FIRST_LEVEL_LOTS_OF_DIAGONALS_APPEAR){
			probabilityWeight = getSpawningProbabilityWeight(level) / 11 ;
		}
		
		return probabilityWeight;
	}
	
	public static int getNumEnemiesInLotsOfEnemiesWave(int lvl){		
		int numEnemies = 0;
		
		if(lvl < AttributesOfLevels.LEVELS_MED){ //choose how many diagonal enemies spawn
			numEnemies = 6;
		}else if (lvl < AttributesOfLevels.LEVELS_HIGH){
			numEnemies = 8;
		}else {
			numEnemies = 13;
		}
		
		return numEnemies;
	}

	@Override
	public void updateViewSpeed(long deltaTime) {
		final float rightSideOfShip = Shooting_DiagonalMovingView.this.getX()+Shooting_DiagonalMovingView.this.getWidth();
		final float leftSideOfShip = Shooting_DiagonalMovingView.this.getX();
		double mySpeedX = Shooting_DiagonalMovingView.this.getSpeedX();
		
		final boolean pastRightSide  = rightSideOfShip>=rightThreshold;
		final boolean pastLeftSide = leftSideOfShip<=leftThreshold;
		if(pastRightSide){
			mySpeedX = Math.abs(mySpeedX) * -1;
			Shooting_DiagonalMovingView.this.setSpeedX(mySpeedX);
		}else if(pastLeftSide){
			mySpeedX = Math.abs(mySpeedX);
			Shooting_DiagonalMovingView.this.setSpeedX(mySpeedX);
		}
	}
			
}
