package com.github.ntsee.eopubsheet.data;

import com.badlogic.gdx.files.FileHandle;
import com.github.ntsee.eopubsheet.view.sheets.EcfPubSheet;
import com.github.ntsee.eopubsheet.view.sheets.PubSheet;
import dev.cirras.data.EoReader;
import dev.cirras.data.EoWriter;
import dev.cirras.protocol.pub.Ecf;
import dev.cirras.protocol.pub.EcfRecord;

import java.util.ArrayList;
import java.util.List;

public class EcfPubManager implements PubManager<EcfRecord> {

    private final Ecf ecf;
    private final PubSheet<EcfRecord> sheet;
    private FileHandle handle;
    private int hash;

    public EcfPubManager() {
        this.ecf = new Ecf();
        this.ecf.setRid(List.of(0, 0));
        this.ecf.setVersion(0);
        this.ecf.setClasses(new ArrayList<>());
        this.ecf.setTotalClassesCount(0);
        this.sheet = new EcfPubSheet(this.ecf);
        this.hash = this.ecf.hashCode();
    }

    public EcfPubManager(FileHandle handle) {
        this.ecf = Ecf.deserialize(new EoReader(handle.readBytes()));
        this.sheet = new EcfPubSheet(this.ecf);
        this.handle = handle;
        this.hash = this.ecf.hashCode();
    }

    @Override
    public PubSheet<EcfRecord> getSheet() {
        return this.sheet;
    }

    @Override
    public boolean hasHandle() {
        return this.handle != null;
    }

    @Override
    public boolean hasUnsavedChanges() {
        return this.handle == null || this.hash != this.ecf.hashCode();
    }

    @Override
    public void save() {
        this.saveAs(this.handle);
    }

    @Override
    public void saveAs(FileHandle handle) {
        this.ecf.setTotalClassesCount(this.ecf.getClasses().size());

        EoWriter writer = new EoWriter();
        Ecf.serialize(writer, this.ecf);

        List<Integer> rid = FileUtils.calculateRid(writer.toByteArray());
        this.ecf.setRid(rid);

        writer = new EoWriter();
        Ecf.serialize(writer, this.ecf);
        handle.writeBytes(writer.toByteArray(), false);
        this.handle = handle;
        this.hash = this.ecf.hashCode();
    }

    @Override
    public void addNewRecord() {
        EcfRecord record = new EcfRecord();
        record.setName("");
        record.setParentType(0);
        record.setStatGroup(0);
        record.setStr(0);
        record.setIntl(0);
        record.setWis(0);
        record.setAgi(0);
        record.setCon(0);
        record.setCha(0);
        this.ecf.getClasses().add(record);
        this.sheet.addRecord(record);
    }

    @Override
    public void removeRecord() {
        int index = this.sheet.getSelectedIndex();
        if (index != -1) {
            this.ecf.getClasses().remove(index);
            this.sheet.deleteRecord(index);
        }
    }
}
