package systemdesignnew.service;

public class ConnectionService {
    public void starTx() {
        System.out.println("Start tx");
    }
    public void commitTx() {
        System.out.println("Commit tx");
    }
    public void rollBackTx() {
        System.out.println("Rollback tx");
    }
}
