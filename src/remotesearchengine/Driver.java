package remotesearchengine;

public class Driver {
    private SearchService searchService;

    public Driver() {
        searchService = SearchServiceImpl.getInstance();
    }

    public static void main(String[] args) {
        Driver d = new Driver();
        d.searchService.searchUsers("name","Vinay Kumar", Operator.EQUALS);
        d.searchService.searchUsers("address.city","Mumbai,Surat", Operator.IN);
        d.searchService.searchUsers("name","Ayush", Operator.IN);
    }
}
