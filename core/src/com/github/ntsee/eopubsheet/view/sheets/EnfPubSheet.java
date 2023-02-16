package com.github.ntsee.eopubsheet.view.sheets;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.ntsee.eopubsheet.view.InputField;
import dev.cirras.protocol.pub.Element;
import dev.cirras.protocol.pub.Enf;
import dev.cirras.protocol.pub.EnfRecord;
import dev.cirras.protocol.pub.NpcType;

public class EnfPubSheet extends PubSheet<EnfRecord> {

    public EnfPubSheet() {
        super(EnfColumn.values());
    }

    public EnfPubSheet(Enf enf) {
        this();
        for (EnfRecord record : enf.getNpcs()) {
            this.addRecord(record);
        }
    }

    @Override
    Table createRow(EnfRecord record) {
        Table group = new Table();
        group.defaults().space(CELL_SPACE).grow();
        group.add(InputField.forString(record.getName(), record::setName))
                .width(EnfColumn.NAME.width);
        group.add(InputField.forShort(record.getGraphicId(), record::setGraphicId))
                .width(EnfColumn.GRAPHIC_ID.width);
        group.add(InputField.forChar(record.getRace(), record::setRace))
                .width(EnfColumn.RACE.width);
        group.add(InputField.forBoolean(record.getBoss(), record::setBoss))
                .width(EnfColumn.BOSS.width);
        group.add(InputField.forBoolean(record.getChild(), record::setChild))
                .width(EnfColumn.CHILD.width);
        group.add(InputField.forEnum(NpcType.Enum.values(), record.getType().asEnum(),
                        type -> record.setType(NpcType.fromInteger(type.getValue()))))
                .width(EnfColumn.TYPE.width);
        group.add(InputField.forShort(record.getBehaviourId(), record::setBehaviourId))
                .width(EnfColumn.BEHAVIOR_ID.width);
        group.add(InputField.forThree(record.getHp(), record::setHp))
                .width(EnfColumn.HP.width);
        group.add(InputField.forShort(record.getTp(), record::setTp))
                .width(EnfColumn.TP.width);
        group.add(InputField.forShort(record.getMinDamage(), record::setMinDamage))
                .width(EnfColumn.MIN_DAMAGE.width);
        group.add(InputField.forShort(record.getMaxDamage(), record::setMaxDamage))
                .width(EnfColumn.MAX_DAMAGE.width);
        group.add(InputField.forShort(record.getAccuracy(), record::setAccuracy))
                .width(EnfColumn.ACCURACY.width);
        group.add(InputField.forShort(record.getEvade(), record::setEvade))
                .width(EnfColumn.EVADE.width);
        group.add(InputField.forShort(record.getArmor(), record::setArmor))
                .width(EnfColumn.ARMOR.width);
        group.add(InputField.forChar(record.getReturnDamage(), record::setReturnDamage))
                .width(EnfColumn.RETURN_DAMAGE.width);
        group.add(InputField.forEnum(Element.Enum.values(), record.getElement().asEnum(),
                        element -> record.setElement(Element.fromInteger(element.getValue()))))
                .width(EnfColumn.ELEMENT.width);
        group.add(InputField.forShort(record.getElementDamage(), record::setElementDamage))
                .width(EnfColumn.ELEMENT_DAMAGE.width);
        group.add(InputField.forEnum(Element.Enum.values(), record.getElementWeakness().asEnum(),
                        element -> record.setElementWeakness(Element.fromInteger(element.getValue()))))
                .width(EnfColumn.ELEMENT_WEAKNESS.width);
        group.add(InputField.forShort(record.getElementWeaknessDamage(),
                        record::setElementWeaknessDamage))
                .width(EnfColumn.ELEMENT_WEAKNESS_DAMAGE.width);
        group.add(InputField.forChar(record.getLevel(), record::setLevel))
                .width(EnfColumn.LEVEL.width);
        group.add(InputField.forThree(record.getExperience(), record::setExperience))
                .width(EnfColumn.EXPERIENCE.width);
        return group;
    }

    private enum EnfColumn implements Column {

        NAME("Name", 200),
        GRAPHIC_ID("GFX ID", 100),
        RACE("Race", 100),
        BOSS("Boss", 50),
        CHILD("Child", 50),
        TYPE("Type", 100),
        BEHAVIOR_ID("Behavior ID", 100),
        HP("HP", 50),
        TP("TP", 50),
        MIN_DAMAGE("Min Dmg.", 100),
        MAX_DAMAGE("Max Dmg.", 100),
        ACCURACY("Acc.", 100),
        EVADE("Eva.", 100),
        ARMOR("Def.", 100),
        RETURN_DAMAGE("Return Dmg.", 100),
        ELEMENT("Element", 100),
        ELEMENT_DAMAGE("Ele. Dmg.", 100),
        ELEMENT_WEAKNESS("Ele. Weakness", 100),
        ELEMENT_WEAKNESS_DAMAGE("Ele. Weakness Dmg.", 100),
        LEVEL("Level", 50),
        EXPERIENCE("Exp.", 100);

        private final String name;
        private final int width;

        EnfColumn(String name, int width) {
            this.name = name;
            this.width = width;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public int getWidth() {
            return this.width;
        }
    }
}
