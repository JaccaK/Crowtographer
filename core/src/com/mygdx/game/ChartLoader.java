package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.HashMap;
import java.util.Map;

public final class ChartLoader {
    /**
     * The new line character stand-in for coordinate data.
     */
    public static final char NEW_LINE = (char) 191;
    /**
     * The new co-ord stand-in.
     */
    public static final char NEW_COORD = (char) 220;

    private ChartLoader() { /*DO NOTHING*/ }

    /**
     * Saves the coordinate data for each map to file.
     * @param theStages Stages used to get the map names
     * @param theMap Map storing the coordinate info
     */
    public static void save(final Stages theStages,
                            final Map<String, Map<String, String>>
                                    theMap) {
        for (String name : theStages.getStages().keySet()) {
            FileHandle mapFile =
                    new FileHandle(Gdx.files.getLocalStoragePath()
                            + "/mapdata/" + name + ".txt");
            String current = "";
            for (int i = 0; i < 39 * 39; i++) {
                String next = theMap.get(name).get(
                        CoordinateButton.COORD_ALPHABET[i / 39] + (i % 39 + 1));
                if (next != null) {
                    next = next.replace("\n", String.valueOf(NEW_LINE));
                } else {
                    next = "";
                }
                current = current.concat(next).
                        concat(String.valueOf(NEW_COORD));
            }
            mapFile.writeString(current, false);
        }
    }

    /**
     * Loads information on the maps from files.
     * @param theStages the stages containing Crowfall maps.
     * @return a map containing coordinate information
     * for all Crowfall maps loaded.
     */
    public static Map<String, Map<String, String>>
    load(final Stages theStages) {
        Map<String, Map<String, String>> map = new HashMap<>();
        for (String name : theStages.getStages().keySet()) {
            map.put(name, new HashMap<>());
            FileHandle mapFile =
                    new FileHandle(Gdx.files.getLocalStoragePath()
                           + "/mapdata/" + name + ".txt");
            if (mapFile.exists()) {
                String mapString = mapFile.readString();
                String[] mapStrings =
                        mapString.substring(0, mapString.length() - 1).
                                split(String.valueOf(NEW_COORD));
                String[] fullMapStrings = new String[39 * 39];
                int count = 0;
                for (String data : mapStrings) {
                    fullMapStrings[count++] = data;
                }
                for (int i = 0; i < fullMapStrings.length; i++) {
                    if (fullMapStrings[i] == null) {
                        fullMapStrings[i] = "";
                    }
                }
                count = 0;
                for (String line : fullMapStrings) {
                    map.get(name).put(CoordinateButton.
                                    COORD_ALPHABET[count / 39]
                                    + (count % 39 + 1),
                            line.replaceFirst(String.valueOf(NEW_LINE), "\n"));
                    count++;
                }
            } else {
                generateEmptyMap(map, name);
            }
        }
        return map;
    }

    /**
     * Generates an empty map for a given map name.
     * @param theMap The map to fill with nothing.
     * @param theName The name of the map.
     */
    private static void generateEmptyMap(final Map<String, Map<String, String>> theMap, final String theName) {
        for (int i = 0; i < 39; i++) {
            for (int j = 0; j < 39; j++) {
                theMap.get(theName).put(CoordinateButton.
                        COORD_ALPHABET[j] + (39 - i), "");
            }
        }
    }
}
