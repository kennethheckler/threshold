package com.kennethheckler.solutions.threshold.user.repository;

/**
 * DataSourceException is a checked exception, extended from {@code Exception}, that represents data source errors the
 * application might want to handle.
 *
 * @author Kenneth Heckler
 * @see java.lang.Exception
 */
public class DataSourceException extends Exception {

    private final String dsName;

    /**
     * Constructs a new data source exception with the specified detail message. The cause is not initialized, and may
     * subsequently be initialized by a call to {@link #initCause}.
     *
     * @param dsName  The name of the data source. The name is added to the message when calling
     *                the {@link #getMessage()} method.
     * @param message the detail message. The detail message is saved for later retrieval by
     *                the {@link #getMessage()} method.
     */
    public DataSourceException(String dsName, String message) {
        super(message);
        this.dsName = dsName;
    }

    /**
     * Constructs a new data source  exception with the specified detail message and cause.
     * <p>Note that the detail message associated with {@code cause} is <i>not</i> automatically incorporated in
     * this exception's detail message.
     *
     * @param dsName  The name of the data source. The name is added to the message when calling
     *                the {@link #getMessage()} method.
     * @param message the detail message (which is saved for later retrieval by
     *                the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method).
     *                (A {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public DataSourceException(String dsName, String message, Throwable cause) {
        super(message, cause);
        this.dsName = dsName;
    }

    /**
     * Answers the extra information message which was provided when the throwable was created. If no message was
     * provided at creation time, then just the data source name is returned.
     *
     * @return String The data source name and receiver's message.
     */
    @Override
    public String getMessage() {
        return "Error returned from DataSource: " + dsName +
                "Message: " + super.getMessage();
    }
}
