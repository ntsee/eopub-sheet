package com.github.ntsee.eopubsheet.data;

import com.badlogic.gdx.files.FileHandle;
import com.github.ntsee.eopubsheet.view.sheets.EsfPubSheet;
import com.github.ntsee.eopubsheet.view.sheets.PubSheet;
import dev.cirras.data.EoReader;
import dev.cirras.data.EoWriter;
import dev.cirras.protocol.pub.Esf;
import dev.cirras.protocol.pub.EsfRecord;
import dev.cirras.protocol.pub.SkillNature;
import dev.cirras.protocol.pub.SkillTargetRestrict;
import dev.cirras.protocol.pub.SkillTargetType;
import dev.cirras.protocol.pub.SkillType;

import java.util.ArrayList;
import java.util.List;

public class EsfPubManager implements PubManager<EsfRecord> {

    private final Esf esf;
    private final PubSheet<EsfRecord> sheet;
    private FileHandle handle;
    private int hash;

    public EsfPubManager() {
        this.esf = new Esf();
        this.esf.setRid(List.of(0, 0));
        this.esf.setVersion(0);
        this.esf.setSkills(new ArrayList<>());
        this.esf.setTotalSkillsCount(0);
        this.sheet = new EsfPubSheet(this.esf);
        this.hash = this.esf.hashCode();
    }

    public EsfPubManager(FileHandle handle) {
        this.esf = Esf.deserialize(new EoReader(handle.readBytes()));
        this.sheet = new EsfPubSheet(this.esf);
        this.handle = handle;
        this.hash = this.esf.hashCode();
    }

    @Override
    public PubSheet<EsfRecord> getSheet() {
        return this.sheet;
    }

    @Override
    public boolean hasHandle() {
        return this.handle != null;
    }

    @Override
    public boolean hasUnsavedChanges() {
        return this.handle == null || this.hash != this.esf.hashCode();
    }

    @Override
    public void save() {
        this.saveAs(this.handle);
    }

    @Override
    public void saveAs(FileHandle handle) {
        this.esf.setTotalSkillsCount(this.esf.getSkills().size());

        EoWriter writer = new EoWriter();
        Esf.serialize(writer, this.esf);

        List<Integer> rid = FileUtils.calculateRid(writer.toByteArray());
        this.esf.setRid(rid);

        writer = new EoWriter();
        Esf.serialize(writer, this.esf);
        handle.writeBytes(writer.toByteArray(), false);
        this.handle = handle;
        this.hash = this.esf.hashCode();
    }

    @Override
    public void addNewRecord() {
        EsfRecord record = new EsfRecord();
        record.setName("");
        record.setChant("");
        record.setIconId(0);
        record.setGraphicId(0);
        record.setTpCost(0);
        record.setSpCost(0);
        record.setCastTime(0);
        record.setNature(SkillNature.SKILL);
        record.setType(SkillType.HEAL);
        record.setElement(0);
        record.setElementPower(0);
        record.setTargetRestrict(SkillTargetRestrict.NPC);
        record.setTargetType(SkillTargetType.NORMAL);
        record.setTargetTime(0);
        record.setMaxSkillLevel(0);
        record.setMinDamage(0);
        record.setMaxDamage(0);
        record.setAccuracy(0);
        record.setEvade(0);
        record.setArmor(0);
        record.setReturnDamage(0);
        record.setHpHeal(0);
        record.setTpHeal(0);
        record.setSpHeal(0);
        record.setStr(0);
        record.setIntl(0);
        record.setWis(0);
        record.setAgi(0);
        record.setCon(0);
        record.setCha(0);
        this.esf.getSkills().add(record);
        this.sheet.addRecord(record);
    }

    @Override
    public void removeRecord() {
        int index = this.sheet.getSelectedIndex();
        if (index != -1) {
            this.esf.getSkills().remove(index);
            this.sheet.deleteRecord(index);
        }
    }
}
