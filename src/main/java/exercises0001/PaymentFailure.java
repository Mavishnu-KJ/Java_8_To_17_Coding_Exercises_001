package exercises0001;

public record PaymentFailure(String failureReason) implements PaymentStatus{

    // Override the default method (custom message)
    @Override
    public String getStatusMessage() {
        return "Payment failed: " + failureReason();
    }
}
