package de.org.mchahn.app.trupax.exe;

import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;

import de.org.mchahn.baselib.test.util.TestUtils;

import de.org.mchahn.udflib.UDFTestTool;
import de.org.mchahn.app.trupax.lib.prg.PrgImpl;
import de.org.mchahn.app.trupax.test.util.Verifier;

public class Examiner extends CmdLn {
    static void run(String[] args) throws Exception {
        __TEST_password = args[0];

        args = Arrays.copyOfRange(args, 1, args.length);

        String volume = null;
        for (String arg : args) {
            if (!arg.trim().startsWith("-")) {
                volume = arg;
                break;
            }
        }

        CmdLn.main(args);

        File tmpDir = TestUtils.createTempDir("examiner");
        File dump = new File(tmpDir, "dump");

        Verifier.decryptVolume(__TEST_password.toCharArray(),
                               new File(volume), dump);

        final PrintStream ps = new PrintStream(new File(tmpDir, "udftest.txt"));
        if (UDFTestTool.available()) {
            if (UDFTestTool.exec(dump,
                             PrgImpl.BLOCK_SIZE,
                             true,
                             false,
                             false,
                             ln -> { ps.println(ln); return true; })) {
                System.out.println("OK");
            }
            else {
                System.err.println("UDFTEST FAILED!");
            }
        }
        ps.close();
    }

    public static void main(String[] args) {
        try {
            run(args);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
