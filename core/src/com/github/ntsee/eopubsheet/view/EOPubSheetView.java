package com.github.ntsee.eopubsheet.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.ntsee.eopubsheet.data.PubManager;
import com.github.ntsee.eopubsheet.view.sheets.PubSheet;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.util.dialog.OptionDialogAdapter;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserAdapter;

import java.util.function.Consumer;

public class EOPubSheetView implements Disposable {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private final Listener listener;
    private final Viewport viewport;
    private final Batch batch;
    private final Stage stage;
    private final MenuBarManager menu;
    private final FileChooser chooser;
    private final Container<PubSheet<?>> content;
    private final VisTable root;

    public EOPubSheetView(Listener listener) {
        VisUI.load(VisUI.SkinScale.X1);
        this.listener = listener;
        this.viewport = new ScreenViewport();
        this.batch = new SpriteBatch();
        this.stage = new Stage(this.viewport, this.batch);
        this.stage.setActionsRequestRendering(true);
        this.menu = new MenuBarManager(listener);
        this.chooser = new FileChooser(FileChooser.Mode.OPEN);
        this.chooser.setMultiSelectionEnabled(false);
        this.content = new Container<PubSheet<?>>().fill();
        this.root = new VisTable(true);
        this.root.setFillParent(true);
        this.stage.addActor(this.root);
        this.initialize();
    }

    private void initialize() {
        this.addChildrenToRoot();
        this.addKeyComboListeners();
        Gdx.input.setInputProcessor(this.stage);
        Gdx.graphics.setContinuousRendering(false);
    }

    private void addChildrenToRoot() {
        this.root.add(this.menu.getActor()).growX().top().row();
        this.root.add(this.content).grow();
    }

    private void addKeyComboListeners() {
        this.stage.addCaptureListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (KeyPressUtils.isControlPressedWith(Input.Keys.O)) {
                    listener.onOpen(null, false);
                } else if (KeyPressUtils.isControlPressedWith(Input.Keys.S)) {
                    listener.onSave();
                } else if (KeyPressUtils.isControlPressed()
                        && KeyPressUtils.isShiftPressedWith(Input.Keys.S)) {
                    listener.onSaveAs(null);
                } else if (KeyPressUtils.isControlPressedWith(Input.Keys.A)) {
                    listener.onAdd();
                } else if (KeyPressUtils.isControlPressedWith(Input.Keys.D)) {
                    listener.onDelete(false);
                }
                return super.keyDown(event, keycode);
            }
        });
    }

    public void setSheet(PubSheet<?> sheet) {
        this.content.setActor(sheet);
    }

    public void showConfirmAction(Consumer<Boolean> consumer) {
        Dialogs.showOptionDialog(this.stage, "Confirm",
                "This action will discard any unsaved changes. Do you want to continue?",
                Dialogs.OptionDialogType.YES_NO,
                new OptionDialogAdapter() {
                    @Override
                    public void yes() {
                        consumer.accept(true);
                    }
                });
    }

    public void showFileOpen() {
        this.chooser.setMode(FileChooser.Mode.OPEN);
        this.chooser.setListener(new FileChooserAdapter() {
            @Override
            public void selected(Array<FileHandle> files) {
                FileHandle handle = files.first();
                listener.onOpen(handle, true);
            }
        });
        this.stage.addActor(this.chooser.fadeIn());
    }

    public void showOpenError(Exception e) {
        Dialogs.showErrorDialog(this.stage, "Failed to open file", e);
    }

    public void showFileSaveAs() {
        this.chooser.setMode(FileChooser.Mode.SAVE);
        this.chooser.setListener(new FileChooserAdapter() {
            @Override
            public void selected(Array<FileHandle> files) {
                FileHandle handle = files.first();
                listener.onSaveAs(handle);
            }
        });
        this.stage.addActor(this.chooser.fadeIn());
    }

    public void showSaveSuccess() {
        Dialogs.showOKDialog(this.stage, "Save Successful",
                "The file has successfully been saved");
    }

    public void showSaveError(Exception e) {
        Dialogs.showErrorDialog(this.stage, "Failed to save file", e);
    }

    public void showConfirmDelete() {
        PubSheet<?> actor = this.content.getActor();
        if (actor == null) {
            return;
        }

        int index = actor.getSelectedIndex();
        if (index == -1) {
            return;
        }

        Dialogs.showOptionDialog(this.stage, "Confirm",
                String.format("Are you sure you want to delete record %d? This may change the ID " +
                        "of other records!", index + 1),
                Dialogs.OptionDialogType.YES_NO,
                new OptionDialogAdapter() {
                    @Override
                    public void yes() {
                        actor.deleteRecord(index);
                    }
                });
    }

    public void render() {
        this.stage.act();
        ScreenUtils.clear(Color.BLACK);
        this.batch.setProjectionMatrix(this.viewport.getCamera().combined);
        this.stage.draw();
    }

    public void resize(int width, int height) {
        this.viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        VisUI.dispose();
        this.batch.dispose();
        this.stage.dispose();
    }

    public interface Listener {

        void onNewEIF(boolean confirmed);
        void onNewENF(boolean confirmed);
        void onNewESF(boolean confirmed);
        void onNewECF(boolean confirmed);
        void onOpen(FileHandle handle, boolean confirmed);
        void onSave();
        void onSaveAs(FileHandle handle);
        void onAdd();
        void onDelete(boolean confirmed);
    }
}
