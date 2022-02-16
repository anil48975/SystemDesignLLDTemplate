package systemdesignnew.aspects;

import systemdesignnew.service.ConnectionService;

import java.util.Optional;
import java.util.function.Function;

public class TransactionAspect {
    private final ConnectionService connectionService;
    private TransactionAspect(ConnectionService connectionService){
        this.connectionService = connectionService;
    }
    public static TransactionAspect getInstance(ConnectionService connectionService) {
        return Holder.getInstance(connectionService);
    }
    public <T, R> Function<T, R> makeTransactional(Function<T, R> fun) {
        return (t) -> {
            try {
                connectionService.starTx();
                R result = fun.apply(t);
                connectionService.commitTx();
                return result;
            } catch (Exception e) {
                connectionService.rollBackTx();
                throw new RuntimeException(e.getMessage());
            } finally {
                //close connections and resources..
            }
        };
    }

    private static class Holder {
        private static TransactionAspect transactionAspect;
        private static TransactionAspect getInstance(ConnectionService connectionService) {
            transactionAspect = new TransactionAspect(connectionService);
            return transactionAspect;
        }
    }
}
