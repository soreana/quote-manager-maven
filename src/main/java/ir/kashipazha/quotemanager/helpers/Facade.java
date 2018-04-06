package ir.kashipazha.quotemanager.helpers;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public interface Facade {
    void addNewEntry(Map<String,String> entries, ArrayList<Tag> tags) throws UnknownKeyException,MissedKeyException;
    Set<String> getKeys();
    String getName();
    String getDescription();
    Facade getInstance();
}
