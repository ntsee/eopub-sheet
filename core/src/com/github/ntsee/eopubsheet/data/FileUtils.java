package com.github.ntsee.eopubsheet.data;


import com.badlogic.gdx.files.FileHandle;
import dev.cirras.protocol.net.client.FileType;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.List;
import java.util.zip.CRC32;

public class FileUtils {

    public static FileType findFileType(FileHandle handle) throws IOException {
        byte[] magicBytes = new byte[3];
        try(BufferedInputStream in = handle.read(magicBytes.length)) {
            int total = 0;
            while (total < magicBytes.length) {
                int read = in.read(magicBytes);
                if (read == -1) {
                    throw new EOFException("could not read file");
                } else {
                    total += read;
                }
            }
        }

        String magic = new String(magicBytes);
        switch (magic) {
            case "EIF":
                return FileType.EIF;
            case "ENF":
                return FileType.ENF;
            case "ESF":
                return FileType.ESF;
            case "ECF":
                return FileType.ECF;
            default:
                throw new IOException(magic + " - invalid file type");
        }
    }

    public static List<Integer> calculateRid(byte[] data) {
        CRC32 crc32 = new CRC32();
        crc32.update(data, 0, data.length);
        long value = crc32.getValue();
        int rid1 = (int)(value >> 16);
        int rid2 = (int)(value & 0xFFFF);
        return List.of(rid1, rid2);
    }
}
