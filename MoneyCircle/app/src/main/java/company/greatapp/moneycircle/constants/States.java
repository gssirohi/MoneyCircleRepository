package company.greatapp.moneycircle.constants;

/**
 * Created by gyanendra on 13/9/15.
 */
public class States {

    public static final int LENT_IDEAL = 1001;
    public static final int LENT_SENDING = 1002;
    public static final int LENT_NOT_SENT = 1003;
    public static final int LENT_APPROVAL_PENDING = 1004;
    public static final int LENT_DISAPPROVED_ACTION_PENDING = 1005;
    public static final int LENT_WAITING_FOR_PAYMENT = 1006;
    public static final int LENT_PAYMENT_RECEIVED_APPROVAL_PENDING = 1007;
    public static final int LENT_PAYMENT_RECEIVED_DISAPPROVED_ACTION_PENDING = 1008;
    public static final int LENT_PAYMENT_CLEARED = 1009;
    public static final int LENT_WAITING_FOR_INACTIVE_PAYMENT = 1010;

    public static final int BORROW_IDEAL = 2001;
    public static final int BORROW_SENDING = 2002;
    public static final int BORROW_NOT_SENT = 2003;
    public static final int BORROW_APPROVAL_PENDING = 2004;
    public static final int BORROW_DISAPPROVED_ACTION_PENDING = 2005;
    public static final int BORROW_PAYMENT_PENDING = 2006;
    public static final int BORROW_PAYMENT_PAID_APPROVAL_PENDING = 2007;
    public static final int BORROW_PAYMENT_PAID_DISAPPROVED_ACTION_PENDING = 2008;
    public static final int BORROW_PAYMENT_CLEARED = 2009;
    public static final int BORROW_INACTIVE_PAYMENT_PENDING = 2010;


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
            case LENT_APPROVAL_PENDING:
                text = "lent approval pending";
                break;
            case LENT_DISAPPROVED_ACTION_PENDING:
                text = "lent disapproved action pending";
                break;
            case LENT_WAITING_FOR_PAYMENT:
                text = "lent waiting for payment";
                break;
            case LENT_PAYMENT_RECEIVED_APPROVAL_PENDING:
                text = "payment received approval pending";
                break;
            case LENT_PAYMENT_RECEIVED_DISAPPROVED_ACTION_PENDING:
                text = "payment received and disapproved , action pending";
                break;
            case LENT_PAYMENT_CLEARED:
                text = "Payment received and accepted, Item Cleared";
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
            case BORROW_APPROVAL_PENDING:
                text = "borrow approval pending";
                break;
            case BORROW_DISAPPROVED_ACTION_PENDING:
                text = "borrow disapproved action pending";
                break;
            case BORROW_PAYMENT_PENDING:
                text = "borrow payment pending";
                break;
            case BORROW_PAYMENT_PAID_APPROVAL_PENDING:
                text = "borrow payment paid approval pending";
                break;
            case BORROW_PAYMENT_PAID_DISAPPROVED_ACTION_PENDING:
                text = "borrow payment paid and disapproved , action pending";
                break;
            case BORROW_PAYMENT_CLEARED:
                text = "Payment paid and accepted, Item Cleared";
                break;
            case BORROW_INACTIVE_PAYMENT_PENDING:
                text = "Borrow: waiting for MANUAL PAYMENT";
                break;

        }
        return text;

    }

}