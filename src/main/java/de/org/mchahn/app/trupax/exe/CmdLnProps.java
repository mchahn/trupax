package de.org.mchahn.app.trupax.exe;

import de.org.mchahn.baselib.util.Prp;

public final class CmdLnProps extends ExeProps {
    public static final String PFX = "trupax.cmdln.";

    public static final Prp.Str  OPTS_PASSWORD   = new Password (PFX);
    public static final Prp.Lng  OPTS_FREESPACE  = new FreeSpace(PFX);
    public static final Prp.Bool OPTS_VERBOSE    = new Prp.Bool(PFX + "verbose"   , false);
    public static final Prp.Bool OPTS_WIPE       = new Prp.Bool(PFX + "wipe"      , false);
    public static final Prp.Bool OPTS_WIPEONLY   = new Prp.Bool(PFX + "wipeonly"  , false);
    public static final Prp.Bool OPTS_EXTRACT    = new Prp.Bool(PFX + "extract"   , false);
    public static final Prp.Bool OPTS_INVALIDATE = new Prp.Bool(PFX + "invalidate", false);
}
