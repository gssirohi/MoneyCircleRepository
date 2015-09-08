package company.greatapp.moneycircle.model;

/**
 * Created by Gyanendrasingh on 9/8/2015.
 */
public class MoneyCirclePackageForServer {


    private int maxRetryAttempt;
    private int attemptCounter;

    private String url;

    private int reqResponseType;

    private String reqDBId;
    private String reqUid;
    private int reqType;   // GET OR POST

//-----------------------WILL BE USED BY SERVER TO CREATE NOTIFICATION--------------------//

    private int reqCode;

    private String reqSenderPhone;
    private String reqReceiverPhone;

    private String itemOwnerPhone;
    private String itemAssociatePhone;

    private String moneyReceiverPhone;
    private String moneyPayerPhone;

    private int ownerItemType;
    private int associateItemtype;

    private String ownerItemId;
    private String associateItemId;

    private int itemBodyJsonType;
    private String itemBodyJsonString;

    private String message;
//----------------------------------------------------------------------------------------//

}
