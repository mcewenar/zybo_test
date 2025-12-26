package ezytec.zybo.demo.exception;


public class CustomExceptions extends RuntimeException {


    protected CustomExceptions(String message) {
        super(message);
    }

    public static class NotFoundException extends CustomExceptions {
        public NotFoundException(String msg) {
            super(msg);
        }
    }

    public static class ConflictException extends CustomExceptions {
        public ConflictException(String msg) {
            super(msg);
        }
    }
}
