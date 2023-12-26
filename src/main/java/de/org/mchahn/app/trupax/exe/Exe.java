package de.org.mchahn.app.trupax.exe;

import de.org.mchahn.app.trupax.lib.prg.Prg;
import de.org.mchahn.baselib.util.CmdLnParser;
import de.org.mchahn.baselib.util.MiscUtils;

public abstract class Exe {
    static final String DEF_PROP_FILE_NAME = "trupax";
    public static String _propFileName = DEF_PROP_FILE_NAME;

    public static final int COPYRIGHT_START_YEAR = 2010;

    public static final String PRODUCT_TITLE = "TruPax";
    public static final String PRODUCT_NAME = PRODUCT_TITLE.toLowerCase().replace(" ", "");;

    protected static final String[][] LANGS = new String[][] {
        { "de"                                          , "Deutsch" },
        { de.org.mchahn.baselib.util.NLS.DEFAULT_LANG_ID, "English" }
    };

    public static final String EXT_TC = ".tc";
    public static final String EXT_HC = ".hc";

    ///////////////////////////////////////////////////////////////////////////

    static class ExitError extends Exception {
        private static final long serialVersionUID = 5054107294731808046L;
        public ExitError(Prg.Result res) {
            super();
            this.result = res;
        }
        public final Prg.Result result;
    }

    ///////////////////////////////////////////////////////////////////////////

    protected CmdLnParser clp;

    protected abstract void addCmdLnOptions();

    protected String[] processArgs(String[] args) throws ExitError {
        if (null != MiscUtils.__TEST_uncaught_now) {
            throw new Error("uncaught_test");
        }
        this.clp = new CmdLnParser();
        addCmdLnOptions();
        try {
            return this.clp.parse(args, true, false);
        }
        catch (CmdLnParser.Error clpe) {
            throw new ExitError(new Prg.Result(Prg.Result.Code.INVALID_CMDLN_ARG,
                                clpe.getMessage(), null));
        }
    }
}
