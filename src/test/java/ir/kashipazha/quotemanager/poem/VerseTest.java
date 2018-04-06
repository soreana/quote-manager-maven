package ir.kashipazha.quotemanager.poem;

import ir.kashipazha.quotemanager.helpers.HibernateUtil;
import ir.kashipazha.quotemanager.helpers.Tag;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;


public class VerseTest {
    private Session session;

    @Before
    public void setUp() throws Exception {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void checkSavedTags() {
        session.beginTransaction();

        Verse v = new Verse("سلام", "خوبی");
        ArrayList<Tag> expectedResult = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Tag t = new Tag("tg" + (i + 1));
            t.addCatalog(v);
            v.addTag(t);
            expectedResult.add(t);
        }

        session.save(v);
        session.getTransaction().commit();

        session.beginTransaction();
        Query query = session.createQuery("from Verse where ID = 1 ");
        List list = query.list();

        Verse verse = (Verse) list.get(0);
        ArrayList<Tag> actualResult = verse.getTags();
        actualResult.sort(Comparator.comparingInt(Tag::getID));
        expectedResult.sort(Comparator.comparingInt(Tag::getID));
        assertArrayEquals(new ArrayList[]{actualResult}, new ArrayList[]{expectedResult});
    }

}