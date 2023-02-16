package com.github.ntsee.eopubsheet.view.sheets;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.ntsee.eopubsheet.view.InputField;
import dev.cirras.protocol.pub.Ecf;
import dev.cirras.protocol.pub.EcfRecord;

public class EcfPubSheet extends PubSheet<EcfRecord> {

    public EcfPubSheet() {
        super(EcfColumn.values());
    }

    public EcfPubSheet(Ecf ecf) {
        this();
        for (EcfRecord record : ecf.getClasses()) {
            this.addRecord(record);
        }
    }

    @Override
    Table createRow(EcfRecord record) {
        Table group = new Table();
        group.defaults().space(CELL_SPACE).grow();
        group.add(InputField.forString(record.getName(), record::setName))
                .width(EcfColumn.NAME.width);
        group.add(InputField.forChar(record.getParentType(), record::setParentType))
                .width(EcfColumn.PARENT_TYPE.width);
        group.add(InputField.forChar(record.getStatGroup(), record::setStatGroup))
                .width(EcfColumn.STAT_GROUP.width);
        group.add(InputField.forShort(record.getStr(), record::setStr))
                .width(EcfColumn.STR.width);
        group.add(InputField.forShort(record.getIntl(), record::setIntl)).
                width(EcfColumn.INT.width);
        group.add(InputField.forShort(record.getWis(), record::setWis))
                .width(EcfColumn.WIS.width);
        group.add(InputField.forShort(record.getAgi(), record::setAgi))
                .width(EcfColumn.AGI.width);
        group.add(InputField.forShort(record.getCon(), record::setCon))
                .width(EcfColumn.CON.width);
        group.add(InputField.forShort(record.getCha(), record::setCha))
                .width(EcfColumn.CHA.width);
        return group;
    }

    private enum EcfColumn implements Column {

        NAME("Name", 200),
        PARENT_TYPE("Parent Type", 100),
        STAT_GROUP("Stat Group", 100),
        STR("STR", 50),
        INT("INT", 50),
        WIS("WIS", 50),
        AGI("AGI", 50),
        CON("CON", 50),
        CHA("CHA", 50);

        private final String name;
        private final int width;

        EcfColumn(String name, int width) {
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
