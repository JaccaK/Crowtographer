package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * Automatically generates stages based off of provided tilemaps.
 */
public class Stages {
    /**
     * Stores the stages by name.
     */
    private final Map<String, Stage> myStages = new HashMap<>();
    /**
     * The current stage's name.
     */
    private String myCurrentStageName;
    /**
     * The current stage.
     */
    private Stage myCurrentStage;

    /**
     * Generates stages based off of maps.atlas.
     */
    public Stages() {
        createStages();
        Map.Entry<String, Stage> stage = myStages.entrySet().iterator().next();
        myCurrentStage = stage.getValue();
        myCurrentStageName = stage.getKey();
    }

    /**
     * Helper for constructor.
     */
    private void createStages() {
        TextureAtlas atlas = new TextureAtlas(
                Gdx.files.getLocalStoragePath() + "/maps.atlas");

        for (TextureAtlas.AtlasRegion texture : atlas.getRegions()) {
            Stage stage = new Stage();
            BaseActor map = new BaseActor();
            map.setTexture(texture);
            stage.addActor(map);

            myStages.put(texture.name, stage);
        }
    }

    /**
     * Returns the stages.
     * @return the stages.
     */
    public Map<String, Stage> getStages() {
        return myStages;
    }

    /**
     * Returns the current stage.
     * @return the current stage.
     */
    public Stage getCurrentStage() {
        return myCurrentStage;
    }

    /**
     * Returns the current stage's name.
     * @return the current stage's name
     */
    public String getCurrentStageName() {
        return myCurrentStageName;
    }

}
