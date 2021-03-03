package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * A button representing a coordinate on a map.
 */
public class CoordinateButton extends ImageButton implements ActorObserver {
    /**
     * The alpha to our alphanumeric coordinate.
     */
    protected static final String[] COORD_ALPHABET =
            {"A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N", "O", "P",
                    "Q", "R", "S", "T", "U", "V", "W",
                    "X", "Y", "Z", "a", "b", "c", "d",
                    "e", "f", "g", "h", "i", "j", "k", "l", "m"};
    /**
     * A select sound.
     */
    public static final Sound SELECT_SOUND =
            Gdx.audio.newSound(Gdx.files.internal("select.wav"));
    /**
     * The search bar to observe.
     */
    private SearchBar mySearchBar;
    /**
     * The coordinate of the button.
     */
    private final String myCoordinate;
    /**
     * The alpha part of the coordinate.
     */
    private final String myAlpha;
    /**
     * The numeric part of the coordinate.
     */
    private final String myNum;
    /**
     * The UIData used for updates.
     */
    private UIData myUIData;

    /**
     * Creates a coordinate button at x and y using a drawable thing.
     * @param drawable The drawable thing, likely a texture.
     * @param x The x coordinate
     * @param y The y coordinate
     */
    public CoordinateButton(final Drawable drawable,
                            final int x, final int y) {
        super(drawable);
        myAlpha = COORD_ALPHABET[x];
        myNum = "" + (39 - y);
        myCoordinate = myAlpha + myNum;
        TextTooltip texttooltip =
                new TextTooltip(myCoordinate,
                        new Skin(Gdx.files.internal("uiskin.json")));
        texttooltip.setInstant(true);
        this.addListener(texttooltip);
    }

    /**
     * Sets the search bar we want to observe.
     * @param theSearchBar the search bar we want to observe.
     */
    public void setSearchBar(final SearchBar theSearchBar) {
        if (mySearchBar != null) {
            mySearchBar.removeObserver(this);
        }
        mySearchBar = theSearchBar;
        mySearchBar.addObserver(this);
    }

    /**
     * Sets the UI Data we want to observe.
     * @param theUIData the UI Data we want to observe.
     */
    public void setUIData(final UIData theUIData) {
        myUIData = theUIData;
    }

    /**
     * Gets the button's coordinate.
     * @return the coordinate.
     */
    public String getCoordinate() {
        return myCoordinate;
    }

    /**
     * Updates the alpha channel based on conditions.
     */
    @Override
    public void update() {
        setColor(0, 0, 0,  0.8F);
        if (searchConditions()) {
            setColor(0, 0, 0, 0);
        }
    }


    /**
     * The search conditions for coordinate fading.
     * @return true if the conditions are met.
     */
    private boolean searchConditions(){
        boolean truth = mySearchBar == null
                || myUIData == null
                || mySearchBar.getText().isEmpty()
                || myCoordinate.equals(myUIData.getCurrentCoordinate());
        try{
           truth =  truth || multiLineSearch()
                    || myCoordinate.matches(mySearchBar.getText());
        } catch (Exception exception){
            truth = myUIData.getCurrentCoordinates().
                    get(myCoordinate).toLowerCase().
                    contains(mySearchBar.getText().toLowerCase());
        }
        return truth;
    }

    /**
     * Splits regex searching between every line on the notes area.
     * @return If it finds a line containing the regex in the searchbar.
     */
    private boolean multiLineSearch(){
        boolean truth = false;
        try{
            for(String line : myUIData.getCurrentCoordinates().
                    get(myCoordinate).toLowerCase().split("\n")){
                truth = truth || line.matches(mySearchBar.getText().toLowerCase());
            }
        }catch(Exception exception){
            // Avoid Pattern Crash
        }
        return truth;
    }
}
