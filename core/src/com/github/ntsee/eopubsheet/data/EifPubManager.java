package com.github.ntsee.eopubsheet.data;

import com.badlogic.gdx.files.FileHandle;
import com.github.ntsee.eopubsheet.view.sheets.EifPubSheet;
import com.github.ntsee.eopubsheet.view.sheets.PubSheet;
import dev.cirras.data.EoReader;
import dev.cirras.data.EoWriter;
import dev.cirras.protocol.pub.Eif;
import dev.cirras.protocol.pub.EifRecord;
import dev.cirras.protocol.pub.Element;
import dev.cirras.protocol.pub.ItemSize;
import dev.cirras.protocol.pub.ItemSpecial;
import dev.cirras.protocol.pub.ItemSubtype;
import dev.cirras.protocol.pub.ItemType;

import java.util.ArrayList;
import java.util.List;

public class EifPubManager implements PubManager<EifRecord> {

    private final Eif eif;
    private final PubSheet<EifRecord> sheet;
    private FileHandle handle;
    private int hash;

    public EifPubManager() {
        this.eif = new Eif();
        this.eif.setRid(List.of(0, 0));
        this.eif.setVersion(0);
        this.eif.setItems(new ArrayList<>());
        this.eif.setTotalItemsCount(0);
        this.sheet = new EifPubSheet(this.eif);
        this.hash = this.eif.hashCode();
    }

    public EifPubManager(FileHandle handle) {
        this.eif = Eif.deserialize(new EoReader(handle.readBytes()));
        this.sheet = new EifPubSheet(this.eif);
        this.handle = handle;
        this.hash = this.eif.hashCode();
    }

    @Override
    public PubSheet<EifRecord> getSheet() {
        return this.sheet;
    }

    @Override
    public boolean hasHandle() {
        return this.handle != null;
    }

    @Override
    public boolean hasUnsavedChanges() {
        return this.handle == null || this.hash != this.eif.hashCode();
    }

    @Override
    public void save() {
        this.saveAs(this.handle);
    }

    @Override
    public void saveAs(FileHandle handle) {
        this.eif.setTotalItemsCount(this.eif.getItems().size());

        EoWriter writer = new EoWriter();
        Eif.serialize(writer, this.eif);

        List<Integer> rid = FileUtils.calculateRid(writer.toByteArray());
        this.eif.setRid(rid);

        writer = new EoWriter();
        Eif.serialize(writer, this.eif);
        handle.writeBytes(writer.toByteArray(), false);
        this.handle = handle;
        this.hash = this.eif.hashCode();
    }

    @Override
    public void addNewRecord() {
        EifRecord record = new EifRecord();
        record.setName("");
        record.setGraphicId(0);
        record.setType(ItemType.GENERAL);
        record.setSubtype(ItemSubtype.NONE);
        record.setSpecial(ItemSpecial.NORMAL);
        record.setHp(0);
        record.setTp(0);
        record.setMinDamage(0);
        record.setMaxDamage(0);
        record.setAccuracy(0);
        record.setEvade(0);
        record.setArmor(0);
        record.setReturnDamage(0);
        record.setStr(0);
        record.setIntl(0);
        record.setWis(0);
        record.setAgi(0);
        record.setCon(0);
        record.setCha(0);
        record.setLightResistance(0);
        record.setDarkResistance(0);
        record.setEarthResistance(0);
        record.setAirResistance(0);
        record.setWaterResistance(0);
        record.setFireResistance(0);
        record.setSpec1(0);
        record.setSpec2(0);
        record.setSpec3(0);
        record.setLevelRequirement(0);
        record.setClassRequirement(0);
        record.setStrRequirement(0);
        record.setIntRequirement(0);
        record.setWisRequirement(0);
        record.setAgiRequirement(0);
        record.setConRequirement(0);
        record.setChaRequirement(0);
        record.setElement(Element.NONE);
        record.setElementDamage(0);
        record.setWeight(0);
        record.setSize(ItemSize.SIZE1X1);
        this.eif.getItems().add(record);
        this.sheet.addRecord(record);
    }

    @Override
    public void removeRecord() {
        int index = this.sheet.getSelectedIndex();
        if (index != -1) {
            this.eif.getItems().remove(index);
            this.sheet.deleteRecord(index);
        }
    }
}
