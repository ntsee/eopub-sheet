package com.github.ntsee.eopubsheet.view.sheets;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.ntsee.eopubsheet.view.InputField;
import dev.cirras.protocol.pub.Esf;
import dev.cirras.protocol.pub.EsfRecord;
import dev.cirras.protocol.pub.SkillNature;
import dev.cirras.protocol.pub.SkillTargetRestrict;
import dev.cirras.protocol.pub.SkillTargetType;
import dev.cirras.protocol.pub.SkillType;

public class EsfPubSheet extends PubSheet<EsfRecord> {

    public EsfPubSheet() {
        super(EsfColumn.values());
    }

    public EsfPubSheet(Esf esf) {
        this();
        for (EsfRecord record : esf.getSkills()) {
            this.addRecord(record);
        }
    }

    @Override
    Table createRow(EsfRecord record) {
        Table group = new Table();
        group.defaults().space(CELL_SPACE).grow();
        group.add(InputField.forString(record.getName(), record::setName))
                .width(EsfColumn.NAME.width);
        group.add(InputField.forString(record.getChant(), record::setChant))
                .width(EsfColumn.CHANT.width);
        group.add(InputField.forShort(record.getIconId(), record::setIconId))
                .width(EsfColumn.ICON_ID.width);
        group.add(InputField.forShort(record.getGraphicId(), record::setGraphicId))
                .width(EsfColumn.GRAPHIC_ID.width);
        group.add(InputField.forShort(record.getTpCost(), record::setTpCost))
                .width(EsfColumn.TP_COST.width);
        group.add(InputField.forShort(record.getSpCost(), record::setSpCost))
                .width(EsfColumn.SP_COST.width);
        group.add(InputField.forChar(record.getCastTime(), record::setCastTime))
                .width(EsfColumn.CAST_TIME.width);
        group.add(InputField.forEnum(SkillNature.Enum.values(), record.getNature().asEnum(),
                        nature -> record.setNature(SkillNature.fromInteger(nature.getValue()))))
                .width(EsfColumn.NATURE.width);
        group.add(InputField.forEnum(SkillType.Enum.values(), record.getType().asEnum(),
                        type -> record.setType(SkillType.fromInteger(type.getValue()))))
                .width(EsfColumn.TYPE.width);
        group.add(InputField.forChar(record.getElement(), record::setElement))
                .width(EsfColumn.ELEMENT.width);
        group.add(InputField.forShort(record.getElementPower(), record::setElementPower))
                .width(EsfColumn.ELEMENT_POWER.width);
        group.add(InputField.forEnum(SkillTargetRestrict.Enum.values(),
                        record.getTargetRestrict().asEnum(),
                        restrict -> record.setTargetRestrict(
                                SkillTargetRestrict.fromInteger(restrict.getValue()))))
                .width(EsfColumn.TARGET_RESTRICT.width);
        group.add(InputField.forEnum(SkillTargetType.Enum.values(), record.getTargetType().asEnum(),
                        targetType -> record.setTargetType(
                                SkillTargetType.fromInteger(targetType.getValue()))))
                .width(EsfColumn.TARGET_TYPE.width);
        group.add(InputField.forChar(record.getTargetTime(), record::setTargetTime))
                .width(EsfColumn.TARGET_TIME.width);
        group.add(InputField.forShort(record.getMaxSkillLevel(), record::setMaxSkillLevel))
                .width(EsfColumn.MAX_SKILL_LEVEL.width);
        group.add(InputField.forShort(record.getMinDamage(), record::setMinDamage))
                .width(EsfColumn.MIN_DAMAGE.width);
        group.add(InputField.forShort(record.getMaxDamage(), record::setMaxDamage))
                .width(EsfColumn.MAX_DAMAGE.width);
        group.add(InputField.forShort(record.getAccuracy(), record::setAccuracy))
                .width(EsfColumn.ACCURACY.width);
        group.add(InputField.forShort(record.getEvade(), record::setEvade))
                .width(EsfColumn.EVADE.width);
        group.add(InputField.forShort(record.getArmor(), record::setArmor))
                .width(EsfColumn.ARMOR.width);
        group.add(InputField.forChar(record.getReturnDamage(), record::setReturnDamage))
                .width(EsfColumn.RETURN_DAMAGE.width);
        group.add(InputField.forShort(record.getHpHeal(), record::setHpHeal))
                .width(EsfColumn.HP_HEAL.width);
        group.add(InputField.forShort(record.getTpHeal(), record::setTpHeal))
                .width(EsfColumn.TP_HEAL.width);
        group.add(InputField.forChar(record.getSpHeal(), record::setSpHeal))
                .width(EsfColumn.SP_HEAL.width);
        group.add(InputField.forShort(record.getIntl(), record::setIntl)).
                width(EsfColumn.INT.width);
        group.add(InputField.forShort(record.getWis(), record::setWis))
                .width(EsfColumn.WIS.width);
        group.add(InputField.forShort(record.getAgi(), record::setAgi))
                .width(EsfColumn.AGI.width);
        group.add(InputField.forShort(record.getCon(), record::setCon))
                .width(EsfColumn.CON.width);
        group.add(InputField.forShort(record.getCha(), record::setCha))
                .width(EsfColumn.CHA.width);
        return group;
    }

    private enum EsfColumn implements Column {

        NAME("Name", 200),
        CHANT("Chant", 200),
        ICON_ID("Icon", 100),
        GRAPHIC_ID("GFX ID", 100),
        TP_COST("TP Cost", 100),
        SP_COST("SP Cost", 100),
        CAST_TIME("Cast Time", 100),
        NATURE("Nature", 100),
        TYPE("Type", 100),
        ELEMENT("Ele.", 100),
        ELEMENT_POWER("Ele. Power", 100),
        TARGET_RESTRICT("Target Restrict", 100),
        TARGET_TYPE("Target Type", 100),
        TARGET_TIME("Target Time", 100),
        MAX_SKILL_LEVEL("Max Skill Level", 100),
        MIN_DAMAGE("Min Dmg.", 100),
        MAX_DAMAGE("Max Dmg.", 100),
        ACCURACY("Acc.", 100),
        EVADE("Eva.", 100),
        ARMOR("Def.", 100),
        RETURN_DAMAGE("Return Dmg.", 100),
        HP_HEAL("HP Heal", 100),
        TP_HEAL("TP Heal", 100),
        SP_HEAL("SP Heal", 100),
        STR("STR", 50),
        INT("INT", 50),
        WIS("WIS", 50),
        AGI("AGI", 50),
        CON("CON", 50),
        CHA("CHA", 50);

        private final String name;
        private final int width;

        EsfColumn(String name, int width) {
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
