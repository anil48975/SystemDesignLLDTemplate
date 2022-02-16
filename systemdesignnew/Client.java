package systemdesignnew;

import systemdesignnew.repository.ItemRepository;
import systemdesignnew.service.ItemService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Client {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Configuration configuration = Configuration.getInstance();
        ItemService itemService = configuration.itemService();

        long startTime = System.currentTimeMillis();
        ItemRepository itemRepository = configuration.itemRepository().get();
        itemRepository = configuration.itemRepository().get();

        //Async call
        itemService.processItemsAsync(itemRepository.getItems())
                .thenAccept(result -> System.out.println("Reactive execution of result: " + result +
                        " generated in " + ((System.currentTimeMillis() - startTime)/1000) + " seconds "));

        System.out.println("Async call finished in " + ((System.currentTimeMillis() - startTime)/1000) + " seconds ");

        //Blocking call
        itemService.processItems(itemRepository.getItems());

        ExecutorService executorService = configuration.executorServiceProvider().executorService();
        executorService.shutdown();

        //Blocks until all tasks have completed execution after a shutdown request, or the timeout occurs,
        // or the current thread is interrupted, whichever happens first.
        // Returns:
        // true if this executor terminated and false if the timeout elapsed before termination
        // so the following code will keep running the
        // while loop if Timeout has elapsed to keep the main method alive
        while (!executorService.awaitTermination(1, TimeUnit.SECONDS));
    }
}
