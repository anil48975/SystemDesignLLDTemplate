package systemdesignnew;

import systemdesignnew.aspects.ExceptionAspect;
import systemdesignnew.aspects.TransactionAspect;
import systemdesignnew.repository.ItemRepository;
import systemdesignnew.service.ConnectionService;
import systemdesignnew.service.ItemService;
import systemdesignnew.service.ItemServiceImpl;
import systemdesignnew.service.SequenceGenerator;
import systemdesignnew.serviceproviders.ExecutorServiceProvider;
import systemdesignnew.serviceproviders.ExecutorServiceProviderImpl;

import java.util.function.Supplier;

public class Configuration {
    private Configuration() {
        System.out.println("Configuration constructor should be called only once as it is a singleton service");
    }
    public static Configuration getInstance() {
        return ConfigurationHolder.configuration;
    }

    SequenceGenerator sequenceGenerator() {
        return SequenceGenerator.getInstance();
    }

    ItemService itemService() {
        return new ItemServiceImpl(itemRepository().get(), exceptionAspect(),
                transactionAspect(), executorServiceProvider());
    }

    Supplier<ItemRepository> itemRepository() {
        return () -> ItemRepository.getInstance();
    }

    ExecutorServiceProvider executorServiceProvider() {
        return ExecutorServiceProviderImpl.INSTANCE;
    }

    TransactionAspect transactionAspect() {
        return TransactionAspect.getInstance(connectionService());
    }
    ConnectionService connectionService() {
        return new ConnectionService();
    }

    ExceptionAspect exceptionAspect() {
        return new ExceptionAspect();
    }

    private static class ConfigurationHolder {
        private static Configuration configuration = new Configuration();
    }
}
