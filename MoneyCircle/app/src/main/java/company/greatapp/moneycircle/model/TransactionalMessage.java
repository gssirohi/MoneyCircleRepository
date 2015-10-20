package company.greatapp.moneycircle.model;

/**
 * Created by Prateek on 18-10-2015.
 */
public class TransactionalMessage {

    public static final int MESSAGE_TYPE_EXPENSE = 11001;
    public static final int MESSAGE_TYPE_INCOME = 11002;
    public static final int MESSAGE_TYPE_BILL = 11003;

    private float amount;
    private int type;
    private String dateString;
    private String duedateString = "NA";
    private String title = "NA";
    private String description = "NA";
    private String outlet = "NA";
    private String merchant = "NA";
    private String transactionMode = "NA";
    private String actualMessage = "NA";
    private String jsonString;

    public TransactionalMessage(int messageType) {
        type = messageType;
        if (messageType == MESSAGE_TYPE_EXPENSE) {
            title = "New Expense Found";
        } else if (messageType == MESSAGE_TYPE_INCOME) {
            title = "New Income Found";
        } else {
            title = "Bill Found";
        }
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOutlet() {
        return outlet;
    }

    public void setOutlet(String outlet) {
        this.outlet = outlet;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getTransactionMode() {
        return transactionMode;
    }

    public void setTransactionMode(String transactionMode) {
        this.transactionMode = transactionMode;
    }

    public String getActualMessage() {
        return actualMessage;
    }

    public void setActualMessage(String actualMessage) {
        this.actualMessage = actualMessage;
    }

    public String getDuedateString() {
        return duedateString;
    }

    public void setDuedateString(String duedateString) {
        this.duedateString = duedateString;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }
}
