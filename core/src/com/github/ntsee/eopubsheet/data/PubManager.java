package com.github.ntsee.eopubsheet.data;

import com.badlogic.gdx.files.FileHandle;
import com.github.ntsee.eopubsheet.view.sheets.PubSheet;

import java.io.IOException;

public interface PubManager<T> {

    PubSheet<T> getSheet();
    boolean hasHandle();
    boolean hasUnsavedChanges();
    void save() throws IOException;
    void saveAs(FileHandle handle) throws IOException;
    void addNewRecord();
    void removeRecord();
}
