package ir.kashipazha.quotemanager.poem;

import ir.kashipazha.quotemanager.helpers.IncludeInKeys;
import ir.kashipazha.quotemanager.helpers.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "catalog", uniqueConstraints = {@UniqueConstraint(columnNames = {"catalog_pk"})})
public class Verse{

    @Transient
    private static Logger log = LogManager.getLogger(Verse.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catalog_pk", nullable = false, unique = true, length = 11, updatable = false)
    private Integer ID;

    @IncludeInKeys(persinaName = "مصرع اول")
    @Column(name = "first", nullable = false, length = 400)
    private String first;

    @IncludeInKeys(persinaName = "مصرع دوم")
    @Column(name = "last", nullable = false, length = 400)
    private String last;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "catalogs",cascade = CascadeType.ALL)
    private List<Tag> tags;

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

    public Verse(){
    }

    public Verse (String first , String last){
       this.first = first;
       this.last = last;
       tags = new ArrayList<>();
    }

    static Set<String> getKeySet() {
        return new HashSet<>(keySet);
    }

    public void addTag(Tag tag){
        tags.add(tag);
    }

    public ArrayList<Tag> getTags() {
        return new ArrayList<>(tags);
    }
}