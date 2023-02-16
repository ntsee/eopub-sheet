package com.github.ntsee.eopubsheet.view.sheets;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.ntsee.eopubsheet.view.InputField;
import dev.cirras.protocol.pub.Eif;
import dev.cirras.protocol.pub.EifRecord;
import dev.cirras.protocol.pub.Element;
import dev.cirras.protocol.pub.ItemSize;
import dev.cirras.protocol.pub.ItemSpecial;
import dev.cirras.protocol.pub.ItemSubtype;
import dev.cirras.protocol.pub.ItemType;

public class EifPubSheet extends PubSheet<EifRecord> {

    public EifPubSheet() {
        super(EifColumn.values());
    }

    public EifPubSheet(Eif eif) {
        this();
        for (EifRecord record : eif.getItems()) {
            this.addRecord(record);
        }
    }

    @Override
    Table createRow(EifRecord record) {
        Table group = new Table();
        group.defaults().space(CELL_SPACE).grow();
        group.add(InputField.forString(record.getName(), record::setName))
                .width(EifColumn.NAME.width);
        group.add(InputField.forShort(record.getGraphicId(), record::setGraphicId))
                .width(EifColumn.GRAPHIC_ID.width);
        group.add(InputField.forEnum(ItemType.Enum.values(), record.getType().asEnum(),
                        type -> record.setType(ItemType.fromInteger(type.getValue()))))
                .width(EifColumn.TYPE.width);
        group.add(InputField.forEnum(ItemSubtype.Enum.values(), record.getSubtype().asEnum(),
                        subtype -> record.setSubtype(ItemSubtype.fromInteger(subtype.getValue()))))
                .width(EifColumn.SUBTYPE.width);
        group.add(InputField.forEnum(ItemSpecial.Enum.values(), record.getSpecial().asEnum(),
                        special -> record.setSpecial(ItemSpecial.fromInteger(special.getValue()))))
                .width(EifColumn.SPECIAL.width);
        group.add(InputField.forShort(record.getHp(), record::setHp))
                .width(EifColumn.HP.width);
        group.add(InputField.forShort(record.getTp(), record::setTp))
                .width(EifColumn.TP.width);
        group.add(InputField.forShort(record.getMinDamage(), record::setMinDamage))
                .width(EifColumn.MIN_DAMAGE.width);
        group.add(InputField.forShort(record.getMaxDamage(), record::setMaxDamage))
                .width(EifColumn.MAX_DAMAGE.width);
        group.add(InputField.forShort(record.getAccuracy(), record::setAccuracy))
                .width(EifColumn.ACCURACY.width);
        group.add(InputField.forShort(record.getEvade(), record::setEvade))
                .width(EifColumn.EVADE.width);
        group.add(InputField.forShort(record.getArmor(), record::setArmor))
                .width(EifColumn.ARMOR.width);
        group.add(InputField.forChar(record.getReturnDamage(), record::setReturnDamage))
                .width(EifColumn.RETURN_DAMAGE.width);
        group.add(InputField.forChar(record.getStr(), record::setStr))
                .width(EifColumn.STR.width);
        group.add(InputField.forChar(record.getIntl(), record::setIntl)).
                width(EifColumn.INT.width);
        group.add(InputField.forChar(record.getWis(), record::setWis))
                .width(EifColumn.WIS.width);
        group.add(InputField.forChar(record.getAgi(), record::setAgi))
                .width(EifColumn.AGI.width);
        group.add(InputField.forChar(record.getCon(), record::setCon))
                .width(EifColumn.CON.width);
        group.add(InputField.forChar(record.getCha(), record::setCha))
                .width(EifColumn.CHA.width);
        group.add(InputField.forChar(record.getStr(), record::setStr))
                .width(EifColumn.LIGHT_RESISTANCE.width);
        group.add(InputField.forChar(record.getIntl(), record::setIntl)).
                width(EifColumn.DARK_RESISTANCE.width);
        group.add(InputField.forChar(record.getWis(), record::setWis))
                .width(EifColumn.EARTH_RESISTANCE.width);
        group.add(InputField.forChar(record.getAgi(), record::setAgi))
                .width(EifColumn.AIR_RESISTANCE.width);
        group.add(InputField.forChar(record.getCon(), record::setCon))
                .width(EifColumn.WATER_RESISTANCE.width);
        group.add(InputField.forChar(record.getCha(), record::setCha))
                .width(EifColumn.FIRE_RESISTANCE.width);
        group.add(InputField.forThree(record.getSpec1(), record::setSpec1))
                .width(EifColumn.SPEC_1.width);
        group.add(InputField.forChar(record.getSpec2(), record::setSpec2))
                .width(EifColumn.SPEC_2.width);
        group.add(InputField.forChar(record.getSpec3(), record::setSpec3))
                .width(EifColumn.SPEC_3.width);
        group.add(InputField.forShort(record.getLevelRequirement(), record::setLevelRequirement))
                .width(EifColumn.LEVEL_REQUIREMENT.width);
        group.add(InputField.forShort(record.getClassRequirement(), record::setLevelRequirement))
                .width(EifColumn.CLASS_REQUIREMENT.width);
        group.add(InputField.forShort(record.getStrRequirement(), record::setStrRequirement))
                .width(EifColumn.STR_REQUIREMENT.width);
        group.add(InputField.forShort(record.getIntRequirement(), record::setIntRequirement))
                .width(EifColumn.INT_REQUIREMENT.width);
        group.add(InputField.forShort(record.getWisRequirement(), record::setWisRequirement))
                .width(EifColumn.WIS_REQUIREMENT.width);
        group.add(InputField.forShort(record.getAgiRequirement(), record::setAgiRequirement))
                .width(EifColumn.AGI_REQUIREMENT.width);
        group.add(InputField.forShort(record.getConRequirement(), record::setConRequirement))
                .width(EifColumn.CON_REQUIREMENT.width);
        group.add(InputField.forShort(record.getChaRequirement(), record::setChaRequirement))
                .width(EifColumn.CHA_REQUIREMENT.width);
        group.add(InputField.forEnum(Element.Enum.values(), record.getElement().asEnum(),
                        element -> record.setElement(Element.fromInteger(element.getValue()))))
                .width(EifColumn.ELEMENT.width);
        group.add(InputField.forChar(record.getElementDamage(), record::setElementDamage))
                .width(EifColumn.ELEMENT_DAMAGE.width);
        group.add(InputField.forChar(record.getWis(), record::setWis))
                .width(EifColumn.WEIGHT.width);
        group.add(InputField.forEnum(ItemSize.Enum.values(), record.getSize().asEnum(),
                        size -> record.setSize(ItemSize.fromInteger(size.getValue()))))
                .width(EifColumn.SIZE.width);
        return group;
    }

    private enum EifColumn implements Column {

        NAME("Name", 200),
        GRAPHIC_ID("GFX ID", 100),
        TYPE("Type", 100),
        SUBTYPE("Subtype", 100),
        SPECIAL("Special", 100),
        HP("HP", 100),
        TP("TP", 100),
        MIN_DAMAGE("Min Dmg.", 100),
        MAX_DAMAGE("Max Dmg.", 100),
        ACCURACY("Acc.", 100),
        EVADE("Eva.", 100),
        ARMOR("Def.", 100),
        RETURN_DAMAGE("Return Dmg.", 100),
        STR("STR", 50),
        INT("INT", 50),
        WIS("WIS", 50),
        AGI("AGI", 50),
        CON("CON", 50),
        CHA("CHA", 50),
        LIGHT_RESISTANCE("Light Res.", 100),
        DARK_RESISTANCE("Dark Res.", 100),
        EARTH_RESISTANCE("Earth Res.", 100),
        AIR_RESISTANCE("Air Res.", 100),
        WATER_RESISTANCE("Water Res.", 100),
        FIRE_RESISTANCE("Fire Res.", 100),
        SPEC_1("Spec 1", 50),
        SPEC_2("Spec 2", 50),
        SPEC_3("Spec 3", 50),
        LEVEL_REQUIREMENT("Level Req.", 100),
        CLASS_REQUIREMENT("Class Req.", 100),
        STR_REQUIREMENT("STR Req.", 100),
        INT_REQUIREMENT("INT Req.", 100),
        WIS_REQUIREMENT("WIS Req.", 100),
        AGI_REQUIREMENT("AGI Req.", 100),
        CON_REQUIREMENT("CON Req.", 100),
        CHA_REQUIREMENT("CHA Req.", 100),
        ELEMENT("Ele.", 100),
        ELEMENT_DAMAGE("Ele. Dmg.", 100),
        WEIGHT("Weight", 100),
        SIZE("Size", 100);

        private final String name;
        private final int width;

        EifColumn(String name, int width) {
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
