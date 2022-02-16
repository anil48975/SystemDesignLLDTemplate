package systemdesignnew.repository;

import systemdesignnew.entity.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemRepository {
    private ItemRepository(){
        System.out.println("ItemRepository constructor should be called only once as it is a singleton service");
    }
    Map<String, Item> itemRepo = Map.of("1", new Item(100),
                                         "2", new Item(200));
    public List<Item> getItems() {
        return itemRepo.entrySet()
                .stream()
                .map(e -> e.getValue())
                .collect(Collectors.toList());
    }

    public List<Item> getItems(int numberOfItems) {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < numberOfItems; i++) {
            items.add(new Item(i));
        }
        return items;
    }

    public static ItemRepository getInstance() {
        return Holder.itemRepository;
    }
    private static class Holder {
        private static ItemRepository itemRepository = new ItemRepository();;
    }
}
