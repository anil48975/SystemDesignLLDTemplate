package systemdesignnew.service;

import systemdesignnew.entity.Item;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface ItemService {
    List<Item> getItems();
    Integer processItems(List<Item> items) throws ExecutionException, InterruptedException;
    CompletableFuture<Integer> processItemsAsync(List<Item> items) throws ExecutionException, InterruptedException;
}
