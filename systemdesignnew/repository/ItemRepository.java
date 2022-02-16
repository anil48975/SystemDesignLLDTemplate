package systemdesignnew.repository;

import systemdesignnew.entity.Item;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemRepository {
    private ItemRepository(){
        System.out.println("ItemRepository constructor should be called only once as it is a singleton service");
    }
    Map<String, Item> itemRepo = Map.of("1", new Item(),
                                         "2", new Item());
    public List<Item> getItems() {
        return itemRepo.entrySet()
                .stream()
                .map(e -> e.getValue())
                .collect(Collectors.toList());
    }
    public static ItemRepository getInstance() {
        return Holder.itemRepository;
    }
    private static class Holder {
        private static ItemRepository itemRepository = new ItemRepository();;
    }
}
