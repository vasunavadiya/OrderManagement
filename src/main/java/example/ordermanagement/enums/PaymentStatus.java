package example.ordermanagement.enums;

/**
 * @author vasu on 28/01/23 - 3:45 pm
 * @Project OrderManagement
 */
public enum PaymentStatus {
    UN_PAID("un_paid"),
    PAID("paid");
    private String value;

    PaymentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
