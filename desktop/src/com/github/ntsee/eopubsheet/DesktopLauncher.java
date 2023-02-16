package com.github.ntsee.eopubsheet;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.github.ntsee.eopubsheet.view.EOPubSheetView;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("EOPub Sheet");
		config.setWindowedMode(EOPubSheetView.WIDTH, EOPubSheetView.HEIGHT);
		new Lwjgl3Application(new EOPubSheetApp(), config);
	}
}
