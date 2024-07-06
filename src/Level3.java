import bagel.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Represents Level 3 in the game ShadowDance.
 * It extends the generic Level class and provides specific implementations and functionalities
 * associated with Level 3.
 */
public class Level3 extends Level{
    /** File location for the CSV file that contains Level 3 data. */
    private final static String CSV_FILE = "res/test3.csv";

    /** X and Y coordinate to display score. */
    private final static int SCORE_LOCATION = 35;

    /** Font used to display score in Level 3. */
    private final Font SCORE_FONT = new Font(ShadowDance.FONT_FILE, 30);

    /** Target scores required to pass Level 3. */
    private final static int TARGET_SCORES = 350;

    /** Accuracy tracker for Level 3. */
    private final Accuracy accuracy = new Accuracy();

    /** Track associated with Level 3. */
    private final Track track;

    /** File location for the track used in Level 3. */
    private final static String TRACK_FILE = "res/track3.wav";

    /** List of enemies in Level 3. */
    private final ArrayList<Enemy> enemies = new ArrayList<>();

    /** The guardian character in Level 3. */
    private final Guardian guardian = new Guardian();

    /** List of projectiles fired in Level 3. */
    private final ArrayList<Projectile> projectiles = new ArrayList<>();

    /** Rate at which enemies spawn in Level 3. */
    private final static int ENEMY_SPAWN_RATE = 600;

    /**
     * Constructs Level 3 by initializing necessary attributes and starting the track.
     */
    public Level3(){
        super(TARGET_SCORES);
        track = new Track(TRACK_FILE);
        track.start();
    }

    /**
     * Constructs Level 3 by initializing necessary attributes and starting the track.
     */
    @Override
    public void readCsv() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String textRead;
            final int LANE_INDEX = 0;
            final int TYPE_INDEX = 1;
            final int POSITION_INDEX = 2;

            while ((textRead = br.readLine()) != null) {
                String[] splitText = textRead.split(",");

                if (splitText[LANE_INDEX].equals("Lane")) {
                    // reading lanes
                    String laneType = splitText[TYPE_INDEX];
                    int pos = Integer.parseInt(splitText[POSITION_INDEX]);
                    if(laneType.equals("Special")){
                        SpecialLane lane = new SpecialLane(laneType, pos);
                        lanes.add(lane);
                    } else{
                        NormalLane lane = new NormalLane(laneType, pos);
                        lanes.add(lane);
                    }

                } else {
                    // reading notes
                    String dir = splitText[LANE_INDEX];
                    Lane lane = null;
                    for (Lane value : lanes) {
                        if (value.getType().equals(dir)) {
                            lane = value;
                        }
                    }

                    if (lane != null) {
                        switch (splitText[TYPE_INDEX]) {
                            case "Normal":
                                Note normalNote = new NormalNote(dir, Integer.parseInt(splitText[2]));
                                lane.addNote(normalNote);
                                break;
                            case "Hold":
                                Note holdNote = new HoldNote(dir, Integer.parseInt(splitText[2]));
                                lane.addNote(holdNote);
                                break;
                            case "DoubleScore":
                                Note bombNote = new DoubleScoreNote("DoubleScore", Integer.parseInt(splitText[2]));
                                lane.addNote(bombNote);
                                break;
                            case "SpeedUp":
                                Note speedUp = new SpeedUpNote("SpeedUp", Integer.parseInt(splitText[2]));
                                lane.addNote(speedUp);
                                break;
                            case "SlowDown":
                                Note slowDown = new SlowDownNote("SlowDown", Integer.parseInt(splitText[2]));
                                lane.addNote(slowDown);
                                break;
                            case "Bomb":
                                Note bomb = new BombNote("Bomb", Integer.parseInt(splitText[2]));
                                lane.addNote(bomb);
                                break;

                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }

    /**
     * Resets the game state for Level 3.
     * This includes clearing lanes, resetting score, and other necessary game attributes.
     */
    @Override
    public void resetGame() {
        lanes.clear();
        enemies.clear();
        projectiles.clear();
        score = 0;
        ShadowDance.currFrame = 0;
        ShadowDance.speedChanger = 2;
        accuracy.setCurrAccuracy(null);
        ShadowDance.started = false;
        ShadowDance.finished = false;
    }

    /**
     * Updates the paused state of Level 3 based on user input.
     * Renders lanes, guardian, enemies, projectiles, and score.
     *
     * @param input the user's input.
     */
    @Override
    public void updatePausedState(Input input) {
        if (input.wasPressed(Keys.TAB)) {
            ShadowDance.paused = false;
            track.run();
        }

        // Draw lanes
        for (Lane lane : lanes) {
            lane.draw();
        }

        // Draw the guardian, enemies, and projectiles during the paused state
        guardian.draw();

        for (Enemy enemy : enemies) {
            enemy.draw();
        }

        for (Projectile projectile : projectiles) {
            projectile.draw();
        }
        SCORE_FONT.drawString("Score " + score, SCORE_LOCATION, SCORE_LOCATION);
    }

    /**
     * Updates the gameplay state of Level 3 based on user input.
     * Handles score rendering, enemy updates, and note updates.
     *
     * @param input the user's input.
     */
    @Override
    public void updateGameplay(Input input) {
        SCORE_FONT.drawString("Score " + score, SCORE_LOCATION, SCORE_LOCATION);
        updateEnemies(input);
        ShadowDance.currFrame++;
        for (Lane lane : lanes) {
            score += lane.update(input, accuracy);

            for (Note note : lane.getNotes()) {
                note.setSpeed(ShadowDance.speedChanger);
            }
        }

        accuracy.update();
        ShadowDance.finished = checkFinished();

        if (input.wasPressed(Keys.TAB)) {
            ShadowDance.paused = true;
            track.pause();
        }
    }

    /**
     * Updates the enemies in Level 3.
     * Handles enemy spawning, their movements, and collisions with lanes.
     *
     * @param input the user's input.
     */
    public void updateEnemies(Input input) {
        if (ShadowDance.currFrame % ENEMY_SPAWN_RATE == 0 && ShadowDance.currFrame != 0) {
            enemies.add(new Enemy());
        }

        for (Enemy enemy : enemies) {
            enemy.update();
            enemy.isCollidingWith(lanes);
        }

        handleProjectileFire(input);

        ArrayList<Projectile> projectilesToRemove = new ArrayList<>();
        for (Projectile projectile : projectiles) {
            projectile.update();

            if (projectile.isOffScreen()) {
                projectilesToRemove.add(projectile);
            } else if (projectile.isCollidingWithEnemies(enemies)) {
                for (Enemy enemy : enemies) {
                    if (projectile.isCollidingWith(enemy)) {
                        enemies.remove(enemy);
                        projectilesToRemove.add(projectile);
                        break;
                    }
                }
            }
        }
        projectiles.removeAll(projectilesToRemove);

        guardian.draw();
    }

    /**
     * Handles the firing of projectiles based on user input.
     * If a projectile is fired, it is added to the list of projectiles.
     *
     * @param input the user's input.
     */
    public void handleProjectileFire(Input input) {
        if (input.wasPressed(Keys.LEFT_SHIFT)) {
            Projectile projectile = guardian.fireAtClosestEnemy(enemies);
            if (projectile != null) {
                projectiles.add(projectile);
            }
        }
    }
}
