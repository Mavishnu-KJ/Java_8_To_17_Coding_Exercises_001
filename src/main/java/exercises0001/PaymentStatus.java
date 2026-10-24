package exercises0001;

//sealed interface - Only permitted subclasses/records can implement
public sealed interface PaymentStatus permits PaymentSuccess, PaymentFailure, PaymentPending {

    // Default method (can be overridden in implementations)
    default String getStatusMessage() {
        return "Payment status: " + this.getClass().getSimpleName();
    }

    //Static method (utility/factory)
    static PaymentStatus fromCode(int code){
        return switch (code){
          case 200, 201 -> new PaymentSuccess("Tx" + System.currentTimeMillis());
          case 400, 402 -> new PaymentFailure("Payment declined");
          default -> new PaymentPending();
        };
    }
}
