import java.util.*;

public class ItemsDAO {

    private static ItemsDAO Instance;
    public static List<ItemsDTO> itemsList = new ArrayList<ItemsDTO>();

    // TODO: Instead of a list storing data here, needs to connect to mongodb?

    ItemsDAO() {
    }

    public static ItemsDAO getInstance(){
        if(Instance == null){
            Instance = new ItemsDAO();
        }
        return Instance;
    }

    public void addItems(String name, String price, String machineCode) {
        itemsList.add(new ItemsDTO(name, price, machineCode));
    }

    public static List<ItemsDTO> getItemsList() {
        return itemsList;
    }
}