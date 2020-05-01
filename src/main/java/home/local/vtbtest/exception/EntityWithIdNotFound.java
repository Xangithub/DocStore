package home.local.vtbtest.exception;

public class EntityWithIdNotFound extends Exception {
    public EntityWithIdNotFound() {
        super();
    }

    public EntityWithIdNotFound(String message) {
        super(message);
    }
}
