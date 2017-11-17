package poem;

import helpers.Tag;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Verse {
    private static Logger log = Logger.getLogger(Verse.class);
    private String first;
    private String last;

    private ArrayList<Tag> tags;

    private static Set<String> keySet;

    static {
        keySet = new HashSet<>();
        Class<Verse> verseClass = Verse.class;
        Field[] fields = verseClass.getDeclaredFields();
        for(Field f : fields){
            keySet.add(f.getName());
        }
    }

    public static Set<String> getKeySet() {
        return new HashSet<>(keySet);
    }
}