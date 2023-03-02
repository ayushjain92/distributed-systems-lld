package remotesearchengine;

import java.util.List;

public interface SearchService {
    List<Integer> searchUsers(String key, String value, Operator op);
}
