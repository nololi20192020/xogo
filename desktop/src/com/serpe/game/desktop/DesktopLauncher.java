package com.serpe.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.serpe.game.SerpeGame;
import com.serpe.game.Xogo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Xogo da Serpe";
		config.width = 700;
		config.height = 500;
		config.resizable=false;
		config.overrideDensity=240;
		config.addIcon("serpe.png", Files.FileType.Internal);//TODO solucionar
		new LwjglApplication(new Xogo(), config);
	}
}
