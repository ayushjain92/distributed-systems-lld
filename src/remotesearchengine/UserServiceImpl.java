package remotesearchengine;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static final String REMOTE_ENDPOINT = "https://raw.githubusercontent.com/arcjsonapi/ApiSampleData/master/api/users";
    private static Gson GSON;

    private static UserServiceImpl instance;

    private UserServiceImpl() {
        GSON = new Gson();
    }

    public static UserServiceImpl getInstance() {
        if(instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public List<User> fetchRemoteUsers() {
        String json = getJson(REMOTE_ENDPOINT);
        User[] userArray = GSON.fromJson(json, User[].class);
        List<User> users = Arrays.asList(userArray);
        populateSearchIndex(users);
        return users;
    }

    private static String getJson(String endpoint) {
        try {
            URL url = new URL(endpoint);
            try (InputStream input = url.openStream()) {
                InputStreamReader isr = new InputStreamReader(input);
                BufferedReader reader = new BufferedReader(isr);
                StringBuilder json = new StringBuilder();
                int c;
                while ((c = reader.read()) != -1) {
                    json.append((char) c);
                }
                return json.toString();
            }
        } catch (Exception e) {
            // todo
        }
        return null;
    }

    private void populateSearchIndex(List<User> users) {
        for(User u : users) {
            getNestedFields(u, null, u);
            //System.out.println(u.getSearchIndex());
            //System.out.println("-----");
        }
    }

    public static void getNestedFields(Object obj, String pre, User user) { // May need throws/catch!
        if (obj == null) {
            // ... do something for null ...
            return;
        }

        List<Field> fields = Arrays.asList(obj.getClass().getDeclaredFields());
        //System.out.println(fields);
        for (Field f : fields) {
            try {
                Class<?> clazz = f.getType();
                if (clazz.equals(String.class) || clazz.isPrimitive()) {

                    String searchKey = f.getName();
                    if (pre != null) {
                        searchKey = pre + "." + searchKey;
                    }
                    user.addToSearchIndex(searchKey, f.get(obj).toString());

                } else {
                    Object nestedObj = f.get(obj);
                    String newPre = f.getName();
                    if (pre != null) {
                        newPre = pre + "." + f.getName();
                    }
                    getNestedFields(nestedObj, newPre, user);
                }
            } catch (Exception e) {
                // noop
            }
        }
    }
}
