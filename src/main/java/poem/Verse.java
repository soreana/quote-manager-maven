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

    @IncludeInKeys(persinaName = "مصرع اول")
    private String first;

    @IncludeInKeys(persinaName = "مصرع دوم")
    private String last;

    private ArrayList<Tag> tags;

    private static Set<String> keySet;

    static {
        keySet = new HashSet<>();
        for (Field f : Verse.class.getDeclaredFields()) {
            if (f.isAnnotationPresent(IncludeInKeys.class)) {
                keySet.add(f.getAnnotation(IncludeInKeys.class).persinaName());
                log.info(f.getName());
            }
        }
    }

    static Set<String> getKeySet() {
        return new HashSet<>(keySet);
    }
}