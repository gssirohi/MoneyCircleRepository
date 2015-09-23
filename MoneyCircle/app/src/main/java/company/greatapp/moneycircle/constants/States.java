package company.greatapp.moneycircle.constants;

/**
 * Created by gyanendra on 13/9/15.
 */
public class States {

    public static final int LENT_IDEAL = 1001;
    public static final int LENT_SENDING = 1002;
    public static final int LENT_NOT_SENT = 1003;
    public static final int LENT_WAITING_FOR_PAYMENT = 1006;
    public static final int LENT_AMOUNT_RECEIVED = 1009;
    public static final int LENT_WAITING_FOR_INACTIVE_PAYMENT = 1010;

    public static final int BORROW_IDEAL = 2001;
    public static final int BORROW_SENDING = 2002;
    public static final int BORROW_NOT_SENT = 2003;
    public static final int BORROW_PAYMENT_PENDING = 2006;
    public static final int BORROW_PAYMENT_CLEARED = 2009;
    public static final int BORROW_INACTIVE_PAYMENT_PENDING = 2010;


    public static final int CONTACT_IDEAL = 3000;
    public static final int CONTACT_SETTLE_REQ_SENDING = 3001;
    public static final int CONTACT_SETTLE_REQ_APPROVAL_PENDING = 3002;
    public static final int CONTACT_SETTLE_REQ_NOT_SENT = 3003;
    public static final int CONTACT_WAITING_FOR_SETTLE_APPROVAL = 3004;
    public static final int CONTACT_SETTLE_REQUEST_DISAPPROVED_ACTION_PENDING = 3005;


    public static String getStateString(int state) {
        String text = "";
        switch(state) {
            case LENT_IDEAL:
                text = "lent Ideal";
                break;
            case LENT_SENDING:
                text = "lent sending";
                break;
            case LENT_NOT_SENT:
                text = "lent not sent";
                break;
            case LENT_WAITING_FOR_PAYMENT:
                text = "lent waiting for payment";
                break;
            case LENT_AMOUNT_RECEIVED:
                text = "payment received,Item cleared";
                break;
            case LENT_WAITING_FOR_INACTIVE_PAYMENT:
                text = "waiting for MANUAL PAYMENT REECEIVE";
                break;
            case BORROW_IDEAL:
                text = "borrow Ideal";
                break;
            case BORROW_SENDING:
                text = "borrow sending";
                break;
            case BORROW_NOT_SENT:
                text = "borrow not sent";
                break;
            case BORROW_PAYMENT_PENDING:
                text = "borrow payment pending";
                break;
            case BORROW_PAYMENT_CLEARED:
                text = "Payment paid , Item Cleared";
                break;
            case BORROW_INACTIVE_PAYMENT_PENDING:
                text = "Borrow: waiting for MANUAL PAYMENT";
                break;

        }
        return text;

    }

}