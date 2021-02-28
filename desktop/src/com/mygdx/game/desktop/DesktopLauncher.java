package com.mygdx.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.CrowChart;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.addIcon("icon.png", Files.FileType.Internal);
		config.width = 640+320+160+18;
		config.height = 640;
		config.resizable = false;
		config.foregroundFPS = 24;
		config.backgroundFPS = 8;
		config.title = "Crowtographer";
		new LwjglApplication(new CrowChart(), config);
	}
}
