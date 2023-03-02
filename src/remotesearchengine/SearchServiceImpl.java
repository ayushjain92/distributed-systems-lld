package remotesearchengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchServiceImpl implements SearchService {
    private UserService userService;
    private static SearchServiceImpl instance;

    private SearchServiceImpl() {
        userService = UserServiceImpl.getInstance();
    }

    public static SearchService getInstance() {
        if (instance == null) {
            instance = new SearchServiceImpl();
        }
        return instance;
    }

    @Override
    /*
        Returns index of matching records.
        name, "Vinay Kumar", EQUALS -> 1
        address.city, "Mumbai,Kolkata", IN ->
     */
    public List<Integer> searchUsers(String key, String value, Operator op) {
        List<String> values = Arrays.asList(value.split(","));
        List<User> users = userService.fetchRemoteUsers();

        int count = 1;
        List<Integer> output = new ArrayList<>();
        for (User u: users) {
            if(values.contains(u.getSearchIndex().get(key))) {
                output.add(count);
            }
            count++;
        }

        if(output.size() == 0) {
            output.add(-1);
        }
        System.out.println(output);
        return output;
    }

}
