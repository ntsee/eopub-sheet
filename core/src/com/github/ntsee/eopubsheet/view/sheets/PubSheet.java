package com.github.ntsee.eopubsheet.view.sheets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisSlider;
import com.kotcrab.vis.ui.widget.VisTextButton;

import java.util.ArrayList;
import java.util.List;

public abstract class PubSheet<T> extends Table {

    static final int CELL_SPACE = 1;
    private static final int HEADER_SIZE = 35;

    private final VisSlider verticalSlider;
    private final VisSlider horizontalSlider;
    private final Table columnHeadContent;
    private final ScrollPane spColumnHead;
    private final VerticalGroup rowHeadContent;
    private final ScrollPane spRowHead;
    private final VerticalGroup bodyContent;
    private final ScrollPane spBody;
    private final ButtonGroup<VisTextButton> group;

    PubSheet(Column[] columns) {
        this.verticalSlider = new VisSlider(0, 1, 0.01f, true);
        this.horizontalSlider = new VisSlider(0, 1, 0.01f, false);
        this.columnHeadContent = new Table().top().left().pad(1);
        this.spColumnHead = createManualScrollPane(this.columnHeadContent);
        this.rowHeadContent = new VerticalGroup().top().left().grow().space(CELL_SPACE);
        this.spRowHead = createManualScrollPane(this.rowHeadContent);
        this.bodyContent = new VerticalGroup().top().left().fill().space(CELL_SPACE);
        this.spBody = this.createTableBody();
        this.group = new ButtonGroup<>();
        this.group.setMinCheckCount(0);
        this.group.setMaxCheckCount(1);
        this.initialize();
        for (Column column : columns) {
            this.addColumn(column.getName(), column.getWidth());
        }
    }

    public void addRecord(T record) {
        Table actor = this.createRow(record);
        int id = this.group.getButtons().size + 1;
        VisTextButton button = new VisTextButton(String.valueOf(id), "toggle");
        this.rowHeadContent.addActor(button);
        this.bodyContent.addActor(actor);
        this.group.add(button);
    }

    public int getSelectedIndex() {
        return this.group.getCheckedIndex();
    }

    public void deleteRecord(int index) {
        this.rowHeadContent.removeActorAt(index, true);
        this.bodyContent.removeActorAt(index, true);
        this.group.getButtons().removeIndex(index);
        for (int i=index; i<this.group.getButtons().size; i++) {
            VisTextButton button = this.group.getButtons().get(i);
            button.setText(String.valueOf(i + 1));
        }
    }

    abstract Table createRow(T record);

    private ScrollPane createTableBody() {
        ScrollPane pane = new ScrollPane(this.bodyContent) {
            @Override
            protected void scrollX(float pixelsX) {
                super.scrollX(pixelsX);
                spColumnHead.setScrollX(pixelsX);
                horizontalSlider.setValue(this.getScrollPercentX());
            }

            @Override
            protected void scrollY(float pixelsY) {
                super.scrollY(pixelsY);
                spRowHead.setScrollY(pixelsY);
                verticalSlider.setValue(1f - this.getScrollPercentY());
            }
        };

        pane.setOverscroll(false, false);
        pane.setSmoothScrolling(true);
        return pane;
    }

    private void initialize() {
        this.addChildrenToParent();
        this.addVerticalScrollListener();
        this.addHorizontalScrollListener();
    }

    private void addChildrenToParent() {
        this.add();
        this.add(this.spColumnHead).height(HEADER_SIZE).growX();
        this.add().width(HEADER_SIZE).fill().row();
        this.add(this.spRowHead).top().width(HEADER_SIZE).fill().padRight(CELL_SPACE);
        this.add(this.spBody).grow();
        this.add(this.verticalSlider).fill().row();
        this.add().height(HEADER_SIZE).fill();
        this.add(this.horizontalSlider).growX().fillY();
        this.add().row();
    }

    private void addVerticalScrollListener() {
        this.verticalSlider.setProgrammaticChangeEvents(false);
        this.verticalSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float percent = verticalSlider.getValue();
                spBody.setScrollPercentY(1f - percent);
            }
        });
    }

    private void addHorizontalScrollListener() {
        this.horizontalSlider.setProgrammaticChangeEvents(false);
        this.horizontalSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float percent = horizontalSlider.getValue();
                spBody.setScrollPercentX(percent);
            }
        });
    }

    private void addColumn(String name, int width) {
        VisLabel label = new VisLabel(name);
        label.setAlignment(Align.center);
        this.columnHeadContent.add(label).width(width).growY().space(CELL_SPACE);
    }

    private static ScrollPane createManualScrollPane(Actor actor) {
        ScrollPane pane = new ScrollPane(actor) {
            @Override
            protected void addScrollListener() {

            }
        };

        pane.setFlickScroll(false);
        pane.setScrollBarTouch(false);
        pane.setSmoothScrolling(true);
        pane.setForceScroll(true, true);
        return pane;
    }

    interface Column {

        String getName();
        int getWidth();
    }
}
