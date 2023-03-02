package remotesearchengine;

import java.util.HashMap;
import java.util.Map;

public class User {
    protected int id;
    protected String name;
    protected String username;
    protected String email;
    protected Address address;
    protected String website;
    protected Company company;

    private Map<String, Object> searchIndex;

    public int getId() {
        return id;
    }

    public Map<String, Object> getSearchIndex() {
        return searchIndex;
    }
    public void addToSearchIndex(String key, Object value) {
        if(searchIndex == null) {
            searchIndex = new HashMap<>();
        }
        searchIndex.put(key, value);
    }
}
