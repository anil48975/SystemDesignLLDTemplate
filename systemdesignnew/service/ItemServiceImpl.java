package systemdesignnew.service;

import systemdesignnew.aspects.ExceptionAspect;
import systemdesignnew.aspects.TransactionAspect;
import systemdesignnew.entity.Item;
import systemdesignnew.repository.ItemRepository;
import systemdesignnew.serviceproviders.ExecutorServiceProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class ItemServiceImpl implements ItemService {
    ItemRepository itemRepository;
    ExceptionAspect exceptionAspect;
    TransactionAspect transactionAspect;
    ExecutorServiceProvider executorServiceProvider;
    public ItemServiceImpl(ItemRepository itemRepository, ExceptionAspect exceptionAspect,
                           TransactionAspect transactionAspect,
                           ExecutorServiceProvider executorServiceProvider) {
        this.itemRepository = itemRepository;
        this.exceptionAspect = exceptionAspect;
        this.transactionAspect = transactionAspect;
        this.executorServiceProvider = executorServiceProvider;
    }
    @Override
    public List<Item> getItems() {
        return itemRepository.getItems();
    }

    @Override
    public Integer processItems(List<Item> items) throws ExecutionException, InterruptedException {
        System.out.println("ItemService.processItems has started.");
        long startTime = System.currentTimeMillis();
        Integer result = CompletableFuture.supplyAsync(processItemsTask.apply(processItems, items),
                executorServiceProvider.executorService()).get();
        System.out.println("ItemService.processItems is finished. in " +
                (System.currentTimeMillis() - startTime)/1000 + " seconds.");
        return result;
    }

    @Override
    public CompletableFuture<Integer> processItemsAsync(List<Item> items) throws ExecutionException, InterruptedException {
        System.out.println("ItemService.processItemsAsync has started.");
        long startTime = System.currentTimeMillis();
        CompletableFuture<Integer> result =  CompletableFuture.supplyAsync(processItemsTask.apply(processItems, items),
                executorServiceProvider.executorService());

        System.out.println("ItemService.processItemsAsync is finished. in " +
                (System.currentTimeMillis() - startTime)/1000 + " seconds.");
        return result;
    }


    public static final Function<List<Item>, Integer> processItems = items -> {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        items.forEach(System.out::println);
        return items.size();
    };

    public final BiFunction<Function<List<Item>, Integer>,
            List<Item>,
            Supplier<Integer>> processItemsTask = (processItems, input) -> {
        Function<List<Item>, Integer> processItemsExcep = exceptionAspect.handleException(processItems);
        Function<List<Item>, Integer> processItemsTrans = transactionAspect.makeTransactional(processItemsExcep);

        return () -> {
            return processItemsTrans.apply(input);
        };
    };

}
