package com.example.amill.cse5911dojo;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import android.util.Log;

public class FrozenBubble extends Activity
{
    public final static int SOUND_WON = 0;
    public final static int SOUND_LOST = 1;
    public final static int SOUND_LAUNCH = 2;
    public final static int SOUND_DESTROY = 3;
    public final static int SOUND_REBOUND = 4;
    public final static int SOUND_STICK = 5;
    public final static int SOUND_HURRY = 6;
    public final static int SOUND_NEWROOT = 7;
    public final static int SOUND_NOH = 8;
    public final static int NUM_SOUNDS = 9;

    public final static int GAME_NORMAL = 0;
    public final static int GAME_COLORBLIND = 1;

    public final static int MENU_COLORBLIND_MODE_ON = 1;
    public final static int MENU_COLORBLIND_MODE_OFF = 2;
    public final static int MENU_FULLSCREEN_ON = 3;
    public final static int MENU_FULLSCREEN_OFF = 4;
    public final static int MENU_SOUND_ON = 5;
    public final static int MENU_SOUND_OFF = 6;
    public final static int MENU_DONT_RUSH_ME = 7;
    public final static int MENU_RUSH_ME = 8;
    public final static int MENU_NEW_GAME = 9;
    public final static int MENU_ABOUT = 10;
    public final static int MENU_EDITOR = 11;

    public final static String PREFS_NAME = "frozenbubble";

    private static int gameMode = GAME_NORMAL;
    private static boolean soundOn = true;
    private static boolean dontRushMe = false;

    private boolean fullscreen = true;

    private GameView.GameThread mGameThread;
    private GameView mGameView;

    private static final String EDITORACTION = "com.example.amill.cse5911dojo.GAME";
    private boolean activityCustomStarted = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_COLORBLIND_MODE_ON, 0,
                R.string.menu_colorblind_mode_on);
        menu.add(0, MENU_COLORBLIND_MODE_OFF, 0,
                R.string.menu_colorblind_mode_off);
        menu.add(0, MENU_FULLSCREEN_ON, 0, R.string.menu_fullscreen_on);
        menu.add(0, MENU_FULLSCREEN_OFF, 0, R.string.menu_fullscreen_off);
        menu.add(0, MENU_SOUND_ON, 0, R.string.menu_sound_on);
        menu.add(0, MENU_SOUND_OFF, 0, R.string.menu_sound_off);
        menu.add(0, MENU_DONT_RUSH_ME, 0, R.string.menu_dont_rush_me);
        menu.add(0, MENU_RUSH_ME, 0, R.string.menu_rush_me);
        menu.add(0, MENU_ABOUT, 0, R.string.menu_about);
        menu.add(0, MENU_NEW_GAME, 0, R.string.menu_new_game);
        menu.add(0, MENU_EDITOR, 0, R.string.menu_editor);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(MENU_SOUND_ON).setVisible(!getSoundOn());
        menu.findItem(MENU_SOUND_OFF).setVisible(getSoundOn());
        menu.findItem(MENU_COLORBLIND_MODE_ON).setVisible(
                getMode() == GAME_NORMAL);
        menu.findItem(MENU_COLORBLIND_MODE_OFF).setVisible(
                getMode() != GAME_NORMAL);
        menu.findItem(MENU_FULLSCREEN_ON).setVisible(!fullscreen);
        menu.findItem(MENU_FULLSCREEN_OFF).setVisible(fullscreen);
        menu.findItem(MENU_DONT_RUSH_ME).setVisible(!getDontRushMe());
        menu.findItem(MENU_RUSH_ME).setVisible(getDontRushMe());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case MENU_NEW_GAME:
                mGameThread.newGame();
                return true;
            case MENU_COLORBLIND_MODE_ON:
                setMode(GAME_COLORBLIND);
                return true;
            case MENU_COLORBLIND_MODE_OFF:
                setMode(GAME_NORMAL);
                return true;
            case MENU_FULLSCREEN_ON:
                fullscreen = true;
                setFullscreen();
                return true;
            case MENU_FULLSCREEN_OFF:
                fullscreen = false;
                setFullscreen();
                return true;
            case MENU_SOUND_ON:
                setSoundOn(true);
                return true;
            case MENU_SOUND_OFF:
                setSoundOn(false);
                return true;
            case MENU_ABOUT:
                mGameView.getThread().setState(GameView.GameThread.STATE_ABOUT);
                return true;
            case MENU_DONT_RUSH_ME:
                setDontRushMe(true);
                return true;
            case MENU_RUSH_ME:
                setDontRushMe(false);
                return true;
            case MENU_EDITOR:
                startEditor();
                return true;
        }
        return false;
    }

    private void setFullscreen()
    {
        if (fullscreen) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }
        mGameView.requestLayout();
    }

    public synchronized static void setMode(int newMode)
    {
        gameMode = newMode;
    }

    public synchronized static int getMode()
    {
        return gameMode;
    }

    public synchronized static boolean getSoundOn()
    {
        return soundOn;
    }

    public synchronized static void setSoundOn(boolean so)
    {
        soundOn = so;
    }

    public synchronized static boolean getDontRushMe()
    {
        return dontRushMe;
    }

    public synchronized static void setDontRushMe(boolean dont)
    {
        dontRushMe = dont;
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        if (savedInstanceState != null) {
            //Log.i("frozen-bubble", "FrozenBubble.onCreate(...)");
        } else {
            //Log.i("frozen-bubble", "FrozenBubble.onCreate(null)");
        }
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Allow editor functionalities.
        Intent i = getIntent();
        if (null == i || null == i.getExtras() ||
                !i.getExtras().containsKey("levels")) {
            // Default intent.
            activityCustomStarted = false;
            setContentView(R.layout.main);
            mGameView = (GameView)findViewById(R.id.game);
        } else {
            // Get custom level last played.
            SharedPreferences sp = getSharedPreferences(
                    FrozenBubble.PREFS_NAME, Context.MODE_PRIVATE);
            int startingLevel = sp.getInt("levelCustom", 0);
            int startingLevelIntent = i.getIntExtra("startingLevel", -2);
            startingLevel = (startingLevelIntent == -2) ?
                    startingLevel : startingLevelIntent;
            activityCustomStarted = true;
            mGameView = new GameView(this, i.getExtras().getByteArray("levels"),
                    startingLevel);
            setContentView(mGameView);
        }

        mGameThread = mGameView.getThread();

        if (savedInstanceState != null) {
            mGameThread.restoreState(savedInstanceState);
        }
        mGameView.requestFocus();
        setFullscreen();
    }

    /**
     * Invoked when the Activity loses user focus.
     */
    @Override
    protected void onPause() {
        //Log.i("frozen-bubble", "FrozenBubble.onPause()");
        super.onPause();
        mGameView.getThread().pause();
        // Allow editor functionalities.
        Intent i = getIntent();
        // If I didn't run game from editor, save last played level.
        if (null == i || !activityCustomStarted) {
            SharedPreferences sp = getSharedPreferences(PREFS_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("level", mGameThread.getCurrentLevelIndex());
            editor.commit();
        } else {
            // Editor's intent is running.
            SharedPreferences sp = getSharedPreferences(PREFS_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("levelCustom", mGameThread.getCurrentLevelIndex());
            editor.commit();
        }
    }

    @Override
    protected void onStop() {
        //Log.i("frozen-bubble", "FrozenBubble.onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        //Log.i("frozen-bubble", "FrozenBubble.onDestroy()");
        super.onDestroy();
        if (mGameView != null) {
            mGameView.cleanUp();
        }
        mGameView = null;
        mGameThread = null;
    }

    /**
     * Notification that something is about to happen, to give the Activity a
     * chance to save state.
     *
     * @param outState a Bundle into which this Activity should save its state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Log.i("frozen-bubble", "FrozenBubble.onSaveInstanceState()");
        // Just have the View's thread save its state into our Bundle.
        super.onSaveInstanceState(outState);
        mGameThread.saveState(outState);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onNewIntent(android.content.Intent)
     */
    @Override
    protected void onNewIntent(Intent intent) {
        if (null != intent && EDITORACTION.equals(intent.getAction())) {
            if (!activityCustomStarted) {
                activityCustomStarted = true;

                // Get custom level last played.
                SharedPreferences sp = getSharedPreferences(
                        FrozenBubble.PREFS_NAME, Context.MODE_PRIVATE);
                int startingLevel = sp.getInt("levelCustom", 0);
                int startingLevelIntent = intent.getIntExtra("startingLevel", -2);
                startingLevel = (startingLevelIntent == -2) ?
                        startingLevel : startingLevelIntent;

                mGameView = null;
                mGameView = new GameView(
                        this, intent.getExtras().getByteArray("levels"),
                        startingLevel);
                setContentView(mGameView);
                mGameThread = mGameView.getThread();
                mGameThread.newGame();
                mGameView.requestFocus();
                setFullscreen();
            }
        }
    }

    // Starts editor / market with editor's download.
    private void startEditor()
    {
        Intent i = new Intent();
        // First try to run the plus version of Editor.
        i.setClassName("sk.halmi.fbeditplus",
                "sk.halmi.fbeditplus.EditorActivity");
        try {
            startActivity(i);
            finish();
        } catch (ActivityNotFoundException e) {
            // If not found, try to run the normal version.
            i.setClassName("sk.halmi.fbedit",
                    "sk.halmi.fbedit.EditorActivity");
            try {
                startActivity(i);
                finish();
            } catch (ActivityNotFoundException ex) {
                // If user doesnt have Frozen Bubble Editor take him to market.
                try {
                    Toast.makeText(getApplicationContext(),
                            R.string.install_editor, Toast.LENGTH_LONG).show();
                    i = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(
                                    "market://search?q=frozen bubble level editor"));
                    startActivity(i);
                } catch (Exception exc) {
                    // Damn you don't have market?
                    Toast.makeText(getApplicationContext(),
                            R.string.market_missing, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}

