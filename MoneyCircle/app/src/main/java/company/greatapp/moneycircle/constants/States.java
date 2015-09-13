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

}