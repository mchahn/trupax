package de.org.mchahn.app.trupax.exe;

import java.util.Properties;

import de.org.mchahn.baselib.util.MiscUtils;
import de.org.mchahn.baselib.util.Prp;

public class ExeProps {
    public static final String EXE_PFX = "trupax.exe.";

    public static class FreeSpace extends Prp.Lng {
        public FreeSpace(String pfx) {
            super(pfx + "freespace", 0L);
        }
        @Override
        public boolean validate(String raw) {
            return 0 <= MiscUtils.strToUSz(raw);
        }
        @Override
        public Long get(Properties p) {
            return MiscUtils.strToUSz(p.getProperty(this.name, this.dflt.toString()));
        }
    };

    public static class Password extends Prp.Str {
        public Password(String pfx) {
            super(pfx + "password", null);
        }
        @Override
        public boolean validate(String raw) {
            return 0 < raw.length();
        }
    };

    public static final Prp.Str Lang = new Prp.Str(EXE_PFX  + "lang", null);
}
