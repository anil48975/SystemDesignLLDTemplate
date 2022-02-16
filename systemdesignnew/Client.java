package systemdesignnew;

import systemdesignnew.repository.ItemRepository;
import systemdesignnew.service.ItemService;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Client {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Configuration configuration = Configuration.getInstance();
        ExecutorService executorService = configuration.executorServiceProvider().executorService();
        ItemService itemService = configuration.itemService();

        long startTime = System.currentTimeMillis();
        ItemRepository itemRepository = configuration.itemRepository().get();
        itemRepository = configuration.itemRepository().get();

        //Async call
        itemService.processItemsAsync(itemRepository.getItems(3))
                .exceptionally(e -> {
                    System.out.println(" Exception occured while item pocessing: " + e.getMessage());
                    return -1;
                })
                .thenAccept(result -> System.out.println("Reactive execution of result: " + result +
                        " generated in " + ((System.currentTimeMillis() - startTime)/1000) + " seconds "));

        CompletableFuture<Integer> firstCall = itemService.processItemsAsync(itemRepository.getItems());

        firstCall.thenAccept(result -> System.out.println("First call result: " + result +
                " generated in " + ((System.currentTimeMillis() - startTime)/1000) + " seconds "));

        CompletableFuture<Integer> secondCall = itemService.processItemsAsync(itemRepository.getItems());
        CompletableFuture<Integer> thirdCall = itemService.processItemsAsync(itemRepository.getItems());

        CompletableFuture<Integer> secondThirdCombine = secondCall.thenCombineAsync(thirdCall, (r2, r3) -> r2 + r3, executorService);
        secondThirdCombine.thenAccept(result -> System.out.println("Second Third call result: " + result +
                " generated in " + ((System.currentTimeMillis() - startTime)/1000) + " seconds "));

        CompletableFuture<Integer> fourth = itemService.processItemsAsync(itemRepository.getItems());
        fourth.thenAccept(result -> System.out.println("Fourth result: " + result));
        CompletableFuture<Integer> fourthFifthCompose = fourth.thenComposeAsync(fourthResult ->
                CompletableFuture.supplyAsync(() -> {
                    System.out.println("Fourth result: " + fourthResult +
                            " will be in incremented by 10 and returned. ");
                    return fourthResult + 10;
                }));

        fourthFifthCompose.thenAccept(result -> System.out.println("fourthFifthCompose result, fourth result incremented by 10: " + result));

        System.out.println("Async call finished in " + ((System.currentTimeMillis() - startTime)/1000) + " seconds ");

        //Blocking call
        itemService.processItems(itemRepository.getItems());

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
