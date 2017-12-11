package poem;

import helpers.IncludeInKeys;
import helpers.Tag;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "catalog", uniqueConstraints = {@UniqueConstraint(columnNames = {"catalog_pk"})})
public class Verse{

    @Transient
    private static Logger log = Logger.getLogger(Verse.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catalog_pk", nullable = false, unique = true, length = 11, updatable = false)
    public Integer ID;

    @IncludeInKeys(persinaName = "مصرع اول")
    @Column(name = "first", nullable = false, length = 400)
    String first;

    @IncludeInKeys(persinaName = "مصرع دوم")
    @Column(name = "last", nullable = false, length = 400)
    String last;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "catalogs",cascade = CascadeType.ALL)
    List<Tag> tags;

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