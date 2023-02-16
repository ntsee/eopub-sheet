package com.github.ntsee.eopubsheet;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.github.ntsee.eopubsheet.data.EcfPubManager;
import com.github.ntsee.eopubsheet.data.EifPubManager;
import com.github.ntsee.eopubsheet.data.EnfPubManager;
import com.github.ntsee.eopubsheet.data.EsfPubManager;
import com.github.ntsee.eopubsheet.data.FileUtils;
import com.github.ntsee.eopubsheet.data.PubManager;
import com.github.ntsee.eopubsheet.view.EOPubSheetView;
import dev.cirras.protocol.net.client.FileType;

import java.io.IOException;

public class EOPubSheetApp extends ApplicationAdapter implements EOPubSheetView.Listener {

	private PubManager<?> manager;
	private EOPubSheetView view;
	
	@Override
	public void create () {
		this.view = new EOPubSheetView(this);
	}

	@Override
	public void render () {
		this.view.render();
	}

	@Override
	public void resize(int width, int height) {
		this.view.resize(width, height);
	}

	@Override
	public void dispose () {
		this.view.dispose();
	}

	@Override
	public void onNewEIF(boolean confirmed) {
		if (this.manager != null && this.manager.hasUnsavedChanges() && !confirmed) {
			this.view.showConfirmAction(this::onNewEIF);
			return;
		}

		this.manager = new EifPubManager();
		this.view.setSheet(this.manager.getSheet());
	}

	@Override
	public void onNewENF(boolean confirmed) {
		if (this.manager != null && this.manager.hasUnsavedChanges() && !confirmed) {
			this.view.showConfirmAction(this::onNewENF);
			return;
		}

		this.manager = new EnfPubManager();
		this.view.setSheet(this.manager.getSheet());
	}

	@Override
	public void onNewESF(boolean confirmed) {
		if (this.manager != null && this.manager.hasUnsavedChanges() && !confirmed) {
			this.view.showConfirmAction(this::onNewESF);
			return;
		}

		this.manager = new EsfPubManager();
		this.view.setSheet(this.manager.getSheet());
	}

	@Override
	public void onNewECF(boolean confirmed) {
		if (this.manager != null && this.manager.hasUnsavedChanges() && !confirmed) {
			this.view.showConfirmAction(this::onNewECF);
			return;
		}

		this.manager = new EcfPubManager();
		this.view.setSheet(this.manager.getSheet());
	}

	@Override
	public void onOpen(FileHandle handle, boolean confirmed) {
		if (this.manager != null && this.manager.hasUnsavedChanges() && !confirmed) {
			this.view.showConfirmAction(response -> this.onOpen(handle, response));
			return;
		} else if (handle == null) {
			this.view.showFileOpen();
			return;
		}

		try {
			FileType type = FileUtils.findFileType(handle);
			switch (type.asEnum()) {
				case EIF:
					this.manager = new EifPubManager(handle);
					this.view.setSheet(this.manager.getSheet());
					break;
				case ENF:
					this.manager = new EnfPubManager(handle);
					this.view.setSheet(this.manager.getSheet());
					break;
				case ESF:
					this.manager = new EsfPubManager(handle);
					this.view.setSheet(this.manager.getSheet());
					break;
				case ECF:
					this.manager = new EcfPubManager(handle);
					this.view.setSheet(this.manager.getSheet());
					break;
			}
		} catch (IOException e) {
			this.view.showOpenError(e);
		}
	}

	@Override
	public void onSave() {
		if (this.manager == null) {
			return;
		} else if (!this.manager.hasHandle()) {
			this.onSaveAs(null);
			return;
		}

		try {
			this.manager.save();
			this.view.showSaveSuccess();
		} catch (IOException e) {
			this.view.showSaveError(e);
		}
	}

	@Override
	public void onSaveAs(FileHandle handle) {
		if (this.manager == null) {
			return;
		} else if (handle == null) {
			this.view.showFileSaveAs();
			return;
		}

		try {
			this.manager.saveAs(handle);
			this.view.showSaveSuccess();
		} catch (IOException e) {
			this.view.showSaveError(e);
		}
	}

	@Override
	public void onAdd() {
		if (this.manager == null) {
			return;
		}

		this.manager.addNewRecord();
	}

	@Override
	public void onDelete(boolean confirmed) {
		if (this.manager == null) {
			return;
		}

		this.view.showConfirmDelete();
	}
}
