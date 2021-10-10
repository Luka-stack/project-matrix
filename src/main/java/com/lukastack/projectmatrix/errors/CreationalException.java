package com.lukastack.projectmatrix.errors;

/**
 * Thrown when error occurred while creating new instance of provided class
 */
public class CreationalException extends RuntimeException {

    /**
     * Constructs and {@code CreationalException} with no details message.
     */
    public CreationalException() {
        super();
    }

    /**
     * Constructs an {@code CreationalException} with no the specified detail message.
     *
     * @param message the detail message.
     */
    public CreationalException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * <p>Note that the detail message associated with {@code cause} is
     * <i>not</i> automatically incorporated in this exception's detail
     * message.
     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link Throwable#getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).  (A {@code null} value
     *         is permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public CreationalException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail
     * message of {@code (cause==null ? null : cause.toString())} (which
     * typically contains the class and detail message of {@code cause}).
     * This constructor is useful for exceptions that are little more than
     * wrappers for other throwables (for example, {@link
     * java.security.PrivilegedActionException}).
     *
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).  (A {@code null} value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public CreationalException(Throwable cause) {
        super(cause);
    }

    @java.io.Serial
    private static final long serialVersionUID = -8024547894856668268L;
}
