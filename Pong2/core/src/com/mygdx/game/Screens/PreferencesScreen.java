package com.mygdx.game.screens;


import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygdx.game.PongGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PreferencesScreen implements Screen{

    private PongGame parent;
    private Stage stage;
    private Skin menuSkin;
    // These labels will be applied the our buttons
    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicEnableLabel;
    private Label soundEnableLabel;


    public PreferencesScreen(PongGame pongGame){
        parent = pongGame;
        /// create stage and set it as input processor
        stage = new Stage(new ScreenViewport());

        parent.assets.loadSkin();
        parent.assets.manager.finishLoading();
        menuSkin = parent.assets.manager.get(parent.assets.skin);

    }

    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        // Create a table that fills the screen. Everything else will go inside
        Table table = new Table(menuSkin);
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);
        table.background("window");

        // music volume
        // Create a slider button, set its range from 0 to 1 in increments of .1
        final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, menuSkin);
        // set the volume to the current value in preferences
        volumeMusicSlider.setValue(parent.getPreferences().getMusicVolume());
        // add a listener to the slider, overide the eventListner to tell it what to do
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                // set the preference to the value of the position of the slider
                parent.getPreferences().setMusicVolume(volumeMusicSlider.getValue());
                return false;
            }
        });

        // Repeat the process for our other buttons

        // sound volume
        final Slider soundMusicSlider = new Slider(0f, 1f, 0.1f, false, menuSkin);
        soundMusicSlider.setValue(parent.getPreferences().getSoundVolume());
        soundMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setSoundVolume(soundMusicSlider.getValue());
                return false;
            }
        });

        // music on/off
        //final CheckBox musicCheckbox = new CheckBox(null, menuSkin);
        final Button musicCheckbox = new Button(menuSkin, "music");
        musicCheckbox.setChecked(parent.getPreferences().isMusicEnabled());
        musicCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                parent.getPreferences().setMusicEnabled(enabled);
                return false;
            }
        });

        // sound on/off
        //final CheckBox soundEffectsCheckbox = new CheckBox(null, menuSkin);
        final Button soundEffectsCheckbox = new Button(menuSkin, "sound");
        soundEffectsCheckbox.setChecked(parent.getPreferences().isSoundEffectsEnabled());
        soundEffectsCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundEffectsCheckbox.isChecked();
                parent.getPreferences().setSoundEffectsEnabled(enabled);
                return false;
            }
        });

        // return to main screen button
        final TextButton backButton = new TextButton("Back", menuSkin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.updateActivePreferences();
                parent.changeScreen(parent.getPreviousScreen(), PongGame.PREFERENCES);
            }
        });

        // Now, set up the labels
        titleLabel = new Label( "Preferences", menuSkin );
        volumeMusicLabel = new Label( "Music Volume", menuSkin );
        volumeSoundLabel = new Label( "Sound Volume", menuSkin );
        musicEnableLabel = new Label( "Music", menuSkin );
        soundEnableLabel = new Label( "Sound Effect", menuSkin );

        // Fill out the table with our labels and buttons
        // add the titelable  .colspan is an option of .add(), effect the positioning in the collum
        table.add(titleLabel).colspan(2);
        // drop a row and set our padding
        table.row().pad(10,0,0,10);
        // add a label the stick it left, then add the slider
        table.add(volumeMusicLabel).left();
        table.add(volumeMusicSlider);
        // Lather, Rinse Repeat
        table.row();
        table.add(musicEnableLabel).left();
        table.add(musicCheckbox);
        table.row();
        table.add(volumeSoundLabel).left();
        table.add(soundMusicSlider);
        table.row();
        table.add(soundEnableLabel).left();
        table.add(soundEffectsCheckbox);
        table.row();
        table.add(backButton).colspan(2);

    }

    @Override
    public void render(float delta) {
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        // change the stage's viewport when the screen size is changed
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
