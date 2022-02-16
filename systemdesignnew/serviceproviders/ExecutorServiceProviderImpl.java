package systemdesignnew.serviceproviders;

import java.util.concurrent.Executors;

public enum ExecutorServiceProviderImpl implements ExecutorServiceProvider {
    INSTANCE;

    private static final java.util.concurrent.ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public java.util.concurrent.ExecutorService executorService() {
        return executorService;
    }
}
