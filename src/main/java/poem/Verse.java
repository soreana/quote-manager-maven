package poem;

import helpers.IncludeInKeys;
import helpers.Tag;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class Verse {
    private static Logger log = Logger.getLogger(Verse.class);

    @IncludeInKeys
    private String first;

    @IncludeInKeys
    private String last;

    private ArrayList<Tag> tags;

    private static Set<String> keySet;

    static {
        keySet = new HashSet<>();
        Class<Verse> verseClass = Verse.class;
        Field[] fields = verseClass.getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(IncludeInKeys.class))
                keySet.add(f.getName());
        }
    }

    static Set<String> getKeySet() {
        return new HashSet<>(keySet);
    }
}