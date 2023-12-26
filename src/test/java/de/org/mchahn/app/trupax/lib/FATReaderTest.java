package de.org.mchahn.app.trupax.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import de.org.mchahn.baselib.io.BlockDevice;
import de.org.mchahn.baselib.io.BlockDeviceImpl;
import de.org.mchahn.baselib.io.BlockDeviceReader.Progress;
import de.org.mchahn.baselib.io.IOUtils;
import de.org.mchahn.baselib.test.util.TestUtils;
import de.org.mchahn.tclib.crypto.Registry;
import de.org.mchahn.tclib.TCReader;
import de.org.mchahn.tclib.util.Key;

public class FATReaderTest {

    @Before
    public void setUp() throws Exception {
        Registry.setup(false);
    }

    @Test
    public void testTC() throws Exception {
        test0("resources/minvol.tc", "123", false);
    }

    @Test
    public void testHC() throws Exception {
        test0("resources/minvol.hc", "12345", true);
    }

    void test0(String res, String passw, boolean veraCrypt) throws Exception {
        byte[] img = IOUtils.readStreamBytes(getClass().getResourceAsStream(res));
        BlockDevice bdev = new BlockDeviceImpl.MemoryBlockDevice(512, img, true, false);

        TCReader tcr = new TCReader(bdev,
                new Key.ByteArray(passw.getBytes()), false, veraCrypt);

        FATReader fr = new FATReader(tcr, new Properties());

        File toDir = TestUtils.createTempDir("FATReaderTest.test0");

        fr.extract(toDir,
            //Reader.Progress2.TRACE
            new Progress() {
                public Result onMounting(int numOfObjects) {
                    return Result.OK;
                }
                public Result onMount(int numOfFiles, int numOfDirs) {
                    return Result.OK;
                }
                public Result onDirectory(File dir, long size, Long tstamp) {
                    return Result.OK;
                }
                public Result onFile(File fl, long size, Long tstamp) {
                    return Result.OK;
                }
                public Result onData(long written) {
                    return Result.OK;
                }
            });

        tcr.close(false);

        File testTxt = new File(toDir, "test.txt");
        assertTrue(testTxt.exists());
        assertTrue(testTxt.length() == 777L);
        assertEquals(TestUtils.md5OfFile(testTxt), "567e283d9d5eeee0060a23b2460c2745");

        if (veraCrypt) {
            for (String extra: new String[] {
                "abCDefg.TX",
                "123456.t",
                "1234567",
                "1234567.tx",
                "12345678",
                "12345678.t",
                "12345678.tx",
                "12345678.txt"
            }) {
                File extraFile = new File(toDir, extra);
                String bb = extraFile.getAbsolutePath();
                extraFile = new File(bb);
                assertTrue(extraFile.exists());
                assertTrue(extraFile.length() == 4);
                assertEquals(TestUtils.md5OfFile(extraFile), "973a9a7789f0e876ef94c46a2073d761");
            }
        }

        assertTrue(TestUtils.removeDir(toDir, true));
    }
}
