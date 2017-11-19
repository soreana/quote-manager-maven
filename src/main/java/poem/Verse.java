package poem;

import helpers.HibernateUtil;
import helpers.IncludeInKeys;
import helpers.Tag;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "verse", uniqueConstraints = {@UniqueConstraint(columnNames = {"verse_pk"})})
class Verse {

    private static Logger log = Logger.getLogger(Verse.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verse_pk", nullable = false, unique = true, length = 11 ,updatable = false)
    private String ID;


    @IncludeInKeys(persinaName = "مصرع اول")
    @Column(name = "first", nullable = false, length = 400 )
    private String first;

    @IncludeInKeys(persinaName = "مصرع دوم")
    @Column(name = "last", nullable = false, length = 400 )
    private String last;

//    private ArrayList<Tag> tags;

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

    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Verse v = new Verse();
        v.first = "سلام";
        v.last = "خوبی";
        session.save(v);
        session.getTransaction().commit();
    }
}