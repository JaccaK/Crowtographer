package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.Map;

/**
 * Makes and holds most data pertaining to the UI of the map.
 */
public class UIData {
    /**
     * The current stage, holding a background map.
     */
    private Stage myCurrentStage;
    /**
     * The name of the current stage.
     */
    private String myCurrentStageName;
    /**
     * A search bar for searching out specific coordinates.
     */
    private final SearchBar mySearchBar = createSearchBar();
    /**
     * The notes area, for displaying data on specific coordinates.
     */
    private NotesArea myNotes = createNotes();
    /**
     * The current coordinate.
     */
    private String myCurrentCoordinate = "l2";
    /**
     * An object that holds stages for the various maps.
     */
    private final Stages myStages = new Stages();
    /**
     * A map of maps containing coordinate data of the various
     * maps from a Stages.
     */
    private final Map<String, Map<String, String>> myCoordinates
            = ChartLoader.load(myStages);
    /**
     * The current map's coordinate map.
     */
    private Map<String, String> myCurrentCoordinates;
    /**
     * The Coordinate Button table.
     */
    private final Table myTable = generateTable(mySearchBar);
    /**
     * The skin for the UI elements.
     */
    private static final Skin myUiSkin =
            new Skin(Gdx.files.internal("uiskin.json"));

    /**
     * Instantiates a UIData object.
     */
    public UIData() {
        myCurrentStage = myStages.getCurrentStage();
        myCurrentStageName = myStages.getCurrentStageName();
        myCurrentCoordinates = myCoordinates.get(myCurrentStageName);
        ChartLoader.save(myStages, myCoordinates);
    }

    /**
     * Builds a UI stage including a coordinate button table,
     * a search bar, map swap buttons, notes area, and save button.
     * @return a UI stage.
     */
    public Stage buildUI() {
        Stage stage = new Stage();
        Cursor cursor = new Cursor(this);
        mySearchBar.addObserver(cursor);
        stage.addActor(cursor);
        stage.addActor(myTable);
        stage.addActor(mySearchBar);
        stage.addActor(generateSwapMapButtons());
        stage.addActor(createNotes());
        stage.addActor(createSaveButton());
        return stage;
    }

    /**
     * Generates buttons for swapping between maps.
     * @return buttons for swapping between maps.
     */
    private Table generateSwapMapButtons() {
        Table buttonTable = new Table();
        buttonTable.setY(39 * 16f + 8);
        buttonTable.setX(640f + 320);
        for (String name : myStages.getStages().keySet()) {
            TextButton button = new TextButton(name, myUiSkin);
            button.addListener(e -> {
                        if (e instanceof InputEvent
                                && ((InputEvent) e).getType().
                                equals(InputEvent.Type.touchDown)) {
                            myNotes.setText(myCoordinates.get(name).
                                    get(myCurrentCoordinate));
                            CoordinateButton.SELECT_SOUND.
                                    setVolume(CoordinateButton.
                                            SELECT_SOUND.play(), 0.15f);
                            setCurrentStage(name);
                        }
                        return true;
                    }
            );
            buttonTable.add(button).left();
            buttonTable.row();
            buttonTable.moveBy(0, -14);
        }
        buttonTable.left();
//        buttonTable.setOrigin(0, buttonTable.getHeight());
        return buttonTable;

    }

    /**
     * Sets the current stage to the map name specified.
     * @param theName the map name.
     */
    public void setCurrentStage(final String theName) {
        myCurrentStage = myStages.getStages().get(theName);
        myCurrentStageName = theName;
        myCurrentCoordinates = myCoordinates.get(theName);
    }

    /**
     * Returns the stages.
     * @return the stages.
     */
    public Stages getStages() {
        return myStages;
    }

    /**
     * Creates a search bar.
     * @return a search bar.
     */
    private SearchBar createSearchBar() {
        SearchBar searchBar = new SearchBar("", myUiSkin);
        searchBar.setX(16);
        searchBar.setWidth(16 * 36f);
        return searchBar;
    }

    /**
     * Creates a notes area.
     * @return A notes area.
     */
    private NotesArea createNotes() {
        NotesArea notes = new NotesArea("", myUiSkin);
        notes.setUIData(this);
        notes.setX(640);
        notes.setPrefRows(20f);
        notes.setHeight(notes.getHeight() * 24);
        notes.setWidth(notes.getWidth() * 2);
        notes.moveBy(8, 8);
        myNotes = notes;
        mySearchBar.addObserver(myNotes);
        return notes;
    }

    /**
     * Generates a table of coordinate buttons to overlay on the map.
     * @param searchBar The search bar used by the coordinate buttons.
     * @return A table of coordinate buttons.
     */
    private Table generateTable(final SearchBar searchBar) {
        Table table = new Table();
        table.moveBy(8 * 41f, 8 * 39f);
        TextureRegionDrawable fade =
                new TextureRegionDrawable(new Texture("fade.png"));
        for (int i = 0; i < 39; i++) {
            for (int j = 0; j < 39; j++) {
                CoordinateButton button = new CoordinateButton(fade, j, i);
                button.setSize(16, 16);
                button.setSearchBar(searchBar);
                button.setUIData(this);
                button.addListener(e -> {
                    handleCoordinate(e, button);
                    return true;
                });
                setTooltip(button, button.getCoordinate());
                table.add(button);
            }
            table.row();
        }
        return table;
    }

    /**
     * Adds a tooltip to an actor.
     * @param actor The actor.
     * @param text The text for the tooltip.
     */
    public static void setTooltip(final Actor actor, final String text) {
        TextTooltip texttooltip =
                new TextTooltip(text, myUiSkin);
        texttooltip.setInstant(true);
        actor.addListener(texttooltip);
    }

    /**
     * Button handler for Coordinate Buttons.
     * Saves the last coordinates data,
     * sets the current coordinate to the button's coordinate,
     * and sets the current data to the Notes area.
     * @param e The event.
     * @param button The coordinate button in question.
     */
    private void handleCoordinate(final Event e,
                                  final CoordinateButton button) {
        if (e instanceof InputEvent
                && ((InputEvent) e).getType().
                equals(InputEvent.Type.touchDown)) {
            String coordinate = button.getCoordinate();
            myCurrentCoordinates.put(myCurrentCoordinate, myNotes.getText());
            myCurrentCoordinate = coordinate;
            myNotes.setText(myCurrentCoordinates.get(myCurrentCoordinate));
            CoordinateButton.SELECT_SOUND.
                    setVolume(CoordinateButton.SELECT_SOUND.play(), 0.15f);
        }
    }

    /**
     * Creates a save button actor, which when pressed saves the map states.
     * @return a save button actor.
     */
    private ImageButton createSaveButton() {
        TextureRegionDrawable saveImage =
                new TextureRegionDrawable(new Texture("save.png"));
        ImageButton save = new ImageButton(saveImage);
        save.addListener(e -> {
            if (e instanceof InputEvent
                    && ((InputEvent) e).
                    getType().equals(InputEvent.Type.touchDown)) {
                ChartLoader.save(myStages, myCoordinates);
                CoordinateButton.SELECT_SOUND.
                        setVolume(CoordinateButton.SELECT_SOUND.play(), 0.15f);
            }
           return false;
        });
        save.moveBy(0, 16 * 39f);
        return save;
    }

    /**
     * Returns the current stage.
     * @return the current stage.
     */
    public Stage getCurrentStage() {
        return myCurrentStage;
    }

    /**
     * Returns a String the current coordinate (A10 for example).
     * @return the current coordinate
     */
    public String getCurrentCoordinate() {
        return myCurrentCoordinate;
    }

    /**
     * Returns the current coordinate data as a string.
     * @return the current coordinate data
     */
    public String getCurrentCoordinateData() {
        return myCurrentCoordinates.get(myCurrentCoordinate);
    }

    /**
     * Returns the current coordinate map.
     * @return the current coordinate map.
     */
    public Map<String, String> getCurrentCoordinates() {
        return myCurrentCoordinates;
    }

}
