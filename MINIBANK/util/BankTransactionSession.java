package util;

// Practical 8: Demonstrating AutoCloseable and try-with-resources.
// The close() method is automatically invoked by the JVM when leaving the try block,
// even if an unexpected exception occurs, guaranteeing proper cleanup.
public class BankTransactionSession implements AutoCloseable {

    private final String sessionName;

    public BankTransactionSession(String sessionName) {
        this.sessionName = sessionName;
        System.out.println("[SESSION OPENED] " + sessionName);
    }

    @Override
    public void close() {
        System.out.println("[SESSION CLOSED] " + sessionName);
    }
}
