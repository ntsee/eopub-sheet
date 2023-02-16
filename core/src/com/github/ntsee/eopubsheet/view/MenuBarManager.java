package com.github.ntsee.eopubsheet.view;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.widget.Menu;
import com.kotcrab.vis.ui.widget.MenuBar;
import com.kotcrab.vis.ui.widget.MenuItem;
import com.kotcrab.vis.ui.widget.PopupMenu;

public class MenuBarManager {

    private final EOPubSheetView.Listener listener;
    private final MenuBar bar;

    public MenuBarManager(EOPubSheetView.Listener listener) {
        this.listener = listener;
        this.bar = new MenuBar();
        this.bar.addMenu(this.createFileMenu());
        this.bar.addMenu(this.createDataMenu());
    }

    public Actor getActor() {
        return this.bar.getTable();
    }

    private Menu createFileMenu() {
        Menu fileMenu = new Menu("File");
        fileMenu.addItem(this.createNewMenuItem());
        fileMenu.addItem(this.createOpenMenuItem());
        fileMenu.addItem(this.createSaveMenuItem());
        fileMenu.addItem(this.createSaveAsMenuItem());
        return fileMenu;
    }

    private MenuItem createNewMenuItem() {
        MenuItem item = new MenuItem("New");
        item.setSubMenu(this.createNewPopupMenu());
        return item;
    }

    private PopupMenu createNewPopupMenu() {
        MenuItem item = new MenuItem("Item File");
        item.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                listener.onNewEIF(false);
            }
        });

        MenuItem npc = new MenuItem("NPC File");
        npc.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                listener.onNewENF(false);
            }
        });

        MenuItem skill = new MenuItem("Skill File");
        skill.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                listener.onNewESF(false);
            }
        });

        MenuItem clas = new MenuItem("Class File");
        clas.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                listener.onNewECF(false);
            }
        });

        PopupMenu popupMenu = new PopupMenu();
        popupMenu.addItem(item);
        popupMenu.addItem(npc);
        popupMenu.addItem(skill);
        popupMenu.addItem(clas);
        return popupMenu;
    }

    private MenuItem createOpenMenuItem() {
        MenuItem item = new MenuItem("Open");
        item.setShortcut(Input.Keys.CONTROL_LEFT, Input.Keys.O);
        item.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                listener.onOpen(null, false);
            }
        });

        return item;
    }

    private MenuItem createSaveMenuItem() {
        MenuItem item = new MenuItem("Save");
        item.setShortcut(Input.Keys.CONTROL_LEFT, Input.Keys.S);
        item.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                listener.onSave();
            }
        });

        return item;
    }

    private MenuItem createSaveAsMenuItem() {
        MenuItem item = new MenuItem("Save As");
        item.setShortcut(Input.Keys.CONTROL_LEFT, Input.Keys.SHIFT_LEFT, Input.Keys.S);
        item.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                listener.onSaveAs(null);
            }
        });

        return item;
    }

    private Menu createDataMenu() {
        Menu dataMenu = new Menu("Data");
        dataMenu.addItem(this.createAddMenuItem());
        dataMenu.addItem(this.createDeleteMenuItem());
        return dataMenu;
    }

    private MenuItem createAddMenuItem() {
        MenuItem item = new MenuItem("Add");
        item.setShortcut(Input.Keys.CONTROL_LEFT, Input.Keys.A);
        item.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                listener.onAdd();
            }
        });

        return item;
    }

    private MenuItem createDeleteMenuItem() {
        MenuItem item = new MenuItem("Delete");
        item.setShortcut(Input.Keys.CONTROL_LEFT, Input.Keys.D);
        item.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                listener.onDelete(false);
            }
        });

        return item;
    }
}
