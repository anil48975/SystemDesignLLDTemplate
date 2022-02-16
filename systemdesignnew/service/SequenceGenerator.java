package systemdesignnew.service;

import java.util.concurrent.atomic.AtomicInteger;

public class SequenceGenerator {
    AtomicInteger seq = new AtomicInteger();
    public Integer getSequence() {
        return seq.incrementAndGet();
    }

    public static SequenceGenerator getInstance() {
        return Holder.sequenceGenerator;
    }
    private static class Holder {
        private static SequenceGenerator sequenceGenerator = new SequenceGenerator();
    }
}
