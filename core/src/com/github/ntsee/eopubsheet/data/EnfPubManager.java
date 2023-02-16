package com.github.ntsee.eopubsheet.data;

import com.badlogic.gdx.files.FileHandle;
import com.github.ntsee.eopubsheet.view.sheets.EnfPubSheet;
import com.github.ntsee.eopubsheet.view.sheets.PubSheet;
import dev.cirras.data.EoReader;
import dev.cirras.data.EoWriter;
import dev.cirras.protocol.pub.Element;
import dev.cirras.protocol.pub.Enf;
import dev.cirras.protocol.pub.EnfRecord;
import dev.cirras.protocol.pub.NpcType;

import java.util.ArrayList;
import java.util.List;

public class EnfPubManager implements PubManager<EnfRecord> {

    private final Enf enf;
    private final PubSheet<EnfRecord> sheet;
    private FileHandle handle;
    private int hash;

    public EnfPubManager() {
        this.enf = new Enf();
        this.enf.setRid(List.of(0, 0));
        this.enf.setVersion(0);
        this.enf.setNpcs(new ArrayList<>());
        this.enf.setTotalNpcsCount(0);
        this.sheet = new EnfPubSheet(this.enf);
        this.hash = this.enf.hashCode();
    }

    public EnfPubManager(FileHandle handle) {
        this.enf = Enf.deserialize(new EoReader(handle.readBytes()));
        this.sheet = new EnfPubSheet(this.enf);
        this.handle = handle;
        this.hash = this.enf.hashCode();
    }

    @Override
    public PubSheet<EnfRecord> getSheet() {
        return this.sheet;
    }

    @Override
    public boolean hasHandle() {
        return this.handle != null;
    }

    @Override
    public boolean hasUnsavedChanges() {
        return this.handle == null || this.hash != this.enf.hashCode();
    }

    @Override
    public void save() {
        this.saveAs(this.handle);
    }

    @Override
    public void saveAs(FileHandle handle) {
        EoWriter writer = new EoWriter();
        Enf.serialize(writer, this.enf);

        List<Integer> rid = FileUtils.calculateRid(writer.toByteArray());
        this.enf.setRid(rid);

        writer = new EoWriter();
        Enf.serialize(writer, this.enf);
        handle.writeBytes(writer.toByteArray(), false);
        this.handle = handle;
        this.hash = this.enf.hashCode();
    }

    @Override
    public void addNewRecord() {
        EnfRecord record = new EnfRecord();
        record.setName("");
        record.setGraphicId(0);
        record.setRace(0);
        record.setBoss(false);
        record.setChild(false);
        record.setType(NpcType.FRIENDLY);
        record.setBehaviourId(0);
        record.setHp(0);
        record.setTp(0);
        record.setMinDamage(0);
        record.setMaxDamage(0);
        record.setAccuracy(0);
        record.setEvade(0);
        record.setArmor(0);
        record.setReturnDamage(0);
        record.setElement(Element.NONE);
        record.setElementDamage(0);
        record.setElementWeakness(Element.NONE);
        record.setElementWeaknessDamage(0);
        record.setLevel(0);
        record.setExperience(0);
        this.enf.getNpcs().add(record);
        this.enf.setTotalNpcsCount(this.enf.getNpcs().size());
        this.sheet.addRecord(record);
    }

    @Override
    public void removeRecord() {
        int index = this.sheet.getSelectedIndex();
        if (index != -1) {
            this.enf.getNpcs().remove(index);
            this.sheet.deleteRecord(index);
        }
    }
}
