package book.effectiveJava.ch01.item01;

import java.util.HashMap;
import java.util.Map;

public class FontFactory {

    private final Map<String, Font> cache = new HashMap<>();

    public Font getFont(String font) {
        if (cache.containsKey(font)) {
            return cache.get(font);
        } else {
            String[] splits = font.split(" ");
            Font newFont = new MyFont(splits[0], Integer.parseInt(splits[1]));
            cache.put(font, newFont);

            return newFont;
        }
    }
}
