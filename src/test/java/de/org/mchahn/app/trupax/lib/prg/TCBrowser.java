package de.org.mchahn.app.trupax.lib.prg;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import de.org.mchahn.baselib.io.BlockDeviceImpl;

import de.org.mchahn.udflib.UDFBrowser;

import de.org.mchahn.tclib.util.Key;
import de.org.mchahn.tclib.util.Password;
import de.org.mchahn.tclib.util.TCLibException;

import de.org.mchahn.tclib.TCReader;

public class TCBrowser extends UDFBrowser {
    public  final TCReader tcr;
    private final RandomAccessFile raf;

    public TCBrowser(File volume, Listener listener, int blockSz, String passw) throws IOException, TCLibException {
        this(volume, listener, blockSz, new Password(passw.toCharArray(), null));
    }

    public TCBrowser(File volume, Listener listener, int blockSz, Key key) throws IOException, TCLibException {
        this.raf = new RandomAccessFile(volume, "r");

        BlockDeviceImpl.FileBlockDevice fbd = new
        BlockDeviceImpl.FileBlockDevice(this.raf, blockSz, -1L, true, false);

        this.tcr = new TCReader(fbd, key, false, false);

        super.init(this.tcr, listener);
    }

    public void close() throws IOException {
        this.tcr.close(false);
        this.raf.close();
    }
}
