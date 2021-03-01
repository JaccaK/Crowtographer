# Crowtographer
A Crowfall map.

![](https://i.imgur.com/k4eAd85.png)

### How to use
If you're building from the code include a libgdx texture atlas and png both named maps (maps.png, maps.atlas) in the base directory. Running the main class should automatically create the coordinate data associated with each map and also load each map.

If you're using the JAR, just make sure the maps.png and maps.atlas file are in the same directory.

Once open, the maps will be automatically loaded. Clicking on a coordinate button will allow you to save text to it using the text area on the right, which can be searched with the searchbar on the bottom. Searching with the searchbar tints all coordinates not including the query or cursor. Pressing a map swap button will swap to a different map. Once you have data you can click the save button in the top left and the information on each coordinate will be there on next launch.

Don't forget to press the save button after storing data. Save often.


### How to create maps
The tools you'll need are [CrashInvader's GDX Texture Packer GUI](https://github.com/crashinvaders/gdx-texture-packer-gui) and [Tiled](https://www.mapeditor.org/).
(Or, as an alternative to Tiled, any tile map editor you want. We just need a 640 by 640 image made up of 16 by 16 tiles. But the instructions will be for Tiled.)
Here is the background I use, and a basic set of tiles: [Background,](https://i.imgur.com/IuJbeYu.png) [Tiles.](https://i.imgur.com/GE2svba.png)

Creating a map using Tiled:

Step 1 is creating a new map. (File -> New -> New Map...). Map size fixed, 40 Width, 40 Height. Tile Size 16 px Width, 16 px Height

Step 2 is adding a background. Add an Image Layer (Layer -> New -> Image Layer). Set Image to your background.

Step 3 is rearranging the layers so the Image Layer is on the bottom.

Step 4 choose a tileset, and draw the map.

Step 5 File -> Export as Image. Make sure "Only include visible layers" is checked and that everything else is unchecked (Maybe check "Draw tile grid", if you're into that.)
(Note: Crowtographer uses the name listed in the atlas to create the map buttons. Make sure the name of your map is what you want to show up in the program.)

Packing the maps:

Step 1 Open GDX Texture Packer.

Step 2 Click the "New Pack" button near the top. Enter "maps".

Step 3 Select an output directory.

Step 4 Add input files (either by button, or drag and drop).

Step 5 Near the top click the single gear shaped button labled "Pack"

### How to share map data
All data entered into the map is stored in a folder in the directory called "mapdata" as text when saved. Simply share that folder and others may overwrite their own and view it, if they have the same maps.atlas file.
