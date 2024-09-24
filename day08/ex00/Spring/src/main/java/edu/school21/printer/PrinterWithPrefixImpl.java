package edu.school21.printer;

import edu.school21.renderer.Renderer;

public class PrinterWithPrefixImpl implements Printer {

    private static final String DEFAULT_PREFIX = "Hey, honourable, ";

    private Renderer renderer;
    private String prefix;

    public PrinterWithPrefixImpl(Renderer renderer) {
        this.renderer = renderer;
        this.prefix = DEFAULT_PREFIX;
    }

    public void setPrefix(String prefix) { this.prefix = prefix; }

    public String getPrefix() { return this.prefix; }

    @Override
    public void print(String text) {
        renderer.render(prefix + text);
    }
}
