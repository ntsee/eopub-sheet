package com.github.ntsee.eopubsheet.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class KeyPressUtils {

    private KeyPressUtils() {

    }

    public static boolean isControlPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)
                || Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT);
    }

    public static boolean isControlPressedWith(int keycode) {
        return isControlPressed() && Gdx.input.isKeyPressed(keycode);
    }


    public static boolean isShiftPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)
                || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT);
    }

    public static boolean isShiftPressedWith(int keycode) {
        return isShiftPressed() && Gdx.input.isKeyPressed(keycode);
    }
}
