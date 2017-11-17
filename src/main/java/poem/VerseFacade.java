package poem;

import helpers.Facade;
import helpers.MissedKeyException;
import helpers.Tag;
import helpers.UnknownKeyException;

import java.util.*;

public class VerseFacade implements Facade {
    @Override
    public void addNewEntry(Map<String, String> entries, ArrayList<Tag<String>> tags) throws UnknownKeyException, MissedKeyException {
        Set<String> entriesKeys = entries.keySet();
        Set<String> versesKeys = entries.keySet();

        if (! entriesKeys.equals(versesKeys)){
            if( entriesKeys.size() > versesKeys.size())
                throw new UnknownKeyException();
            else
                throw new MissedKeyException();
        }

        entries.entrySet().forEach(System.out::println);
        tags.forEach(System.out::println);
        // todo check duplicate
    }

    @Override
    public Set<String> getKeys() {
        return Verse.getKeySet();
    }

    @Override
    public String getName() {
        return "poem";
    }

    @Override
    public String getDescription() {
        return "nothing";
    }

    @Override
    public Facade getInstance() {
        return new VerseFacade();
    }
}
