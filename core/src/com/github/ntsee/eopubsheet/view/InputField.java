package com.github.ntsee.eopubsheet.view;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import com.kotcrab.vis.ui.widget.VisSelectBox;
import com.kotcrab.vis.ui.widget.VisTextField;
import dev.cirras.data.EoNumericLimits;

import java.util.Arrays;
import java.util.function.Consumer;

public class InputField {

    private InputField() {

    }

    public static VisCheckBox forBoolean(Boolean start, Consumer<Boolean> consumer) {
        VisCheckBox box = new VisCheckBox(null);
        box.setChecked(start);
        box.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                consumer.accept(box.isChecked());
            }
        });

        return box;
    }

    public static VisTextField forChar(Integer start, Consumer<Integer> consumer) {
        int max = EoNumericLimits.CHAR_MAX - 1;
        return createNumericField(consumer, start, max);
    }

    public static VisTextField forShort(Integer start, Consumer<Integer> consumer) {
        int max = EoNumericLimits.SHORT_MAX - 1;
        return createNumericField(consumer, start, max);
    }

    public static VisTextField forThree(Integer start, Consumer<Integer> consumer) {
        int max = EoNumericLimits.THREE_MAX - 1;
        return createNumericField(consumer, start, max);
    }

    public static VisTextField forString(String string, Consumer<String> consumer) {
        VisTextField field = new VisTextField();
        field.setAlignment(Align.center);
        field.setMaxLength(EoNumericLimits.CHAR_MAX - 1);
        field.setText(string);
        field.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                consumer.accept(field.getText());
            }
        });

        return field;
    }

    public static <T extends Enum<T>> SelectBox<T> forEnum(T[] elements, T start,
                                                           Consumer<T> consumer) {
        VisSelectBox<T> box = new VisSelectBox<>();
        box.setItems((T[])Arrays.stream(elements)
                .filter(item -> !item.name().equals("UNRECOGNIZED"))
                .toArray(Enum[]::new)
        );

        box.setSelected(start);
        box.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                consumer.accept(box.getSelected());
            }
        });

        return box;
    }

    private static VisTextField createNumericField(Consumer<Integer> consumer, int start, int max) {
        VisTextField field = new VisTextField();
        field.setAlignment(Align.center);
        field.setText(String.valueOf(start));
        field.setMaxLength(String.valueOf(max).length());
        field.setTextFieldFilter(new VisTextField.TextFieldFilter.DigitsOnlyFilter());
        field.setProgrammaticChangeEvents(false);

        field.addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                super.keyboardFocusChanged(event, actor, focused);
                if (focused) {
                    return;
                }

                String text = field.getText();
                if (text == null || text.isEmpty()) {
                    field.setText(String.valueOf(0));
                }
            }
        });

        field.setTextFieldListener((textField, c) -> {
            String text = textField.getText();
            if (text == null || text.isEmpty()) {
                return;
            }

            int value = MathUtils.clamp(Integer.parseInt(text), 0, max);
            textField.setText(String.valueOf(value));
            consumer.accept(value);
        });

        return field;
    }
}
