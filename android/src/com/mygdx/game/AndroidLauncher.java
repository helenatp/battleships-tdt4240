package com.mygdx.game;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication{

	// request codes we use when invoking an external activity

	// tag for debug logging
	private static final String TAG = "Firebase Test";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Battleships(new AndroidInterfaceClass()), config);
	}
}
