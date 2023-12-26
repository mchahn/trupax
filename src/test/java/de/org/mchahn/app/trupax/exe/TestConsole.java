package de.org.mchahn.app.trupax.exe;

import java.util.ArrayList;
import java.util.List;

public class TestConsole extends Console {
    public final List<String> prompts = new ArrayList<>();
    public char[] password;

    public char[] readPassword(String fmt, Object... args) {
        this.prompts.add(String.format(fmt, args));
        return this.password.clone();
    }
    public Console format(String fmt, Object... args) {
        this.prompts.add(String.format(fmt, args));
        return this;
    }
}
