package edu.school21.printer;

import edu.school21.renderer.Renderer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PrinterWithDateTimeImpl implements Printer {

    private Renderer renderer;

    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void print(String text) {
        renderer.render(text + " " + getDateTime());
    }

    public static String getDateTime() {
        Calendar calendar = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("mm:hh dd/MMM/yyyy");
        return df.format(calendar.getTime());
    }
}
