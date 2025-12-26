package ezytec.zybo.demo.exception;

public record ApiError(String timeStamp, int status, String message) {
}
