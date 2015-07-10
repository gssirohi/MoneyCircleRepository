package company.greatapp.moneycircle.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Gyanendrasingh on 10-07-2015.
 */
public class Participant implements Parcelable{
    public String memberName;
    public String memberUID;
    public float editableValue;
    public float amount;
    public float percent;
    public float share;

    public Participant(Contact contact) {
        memberName = contact.getContactName();
        memberUID = contact.getUID();
    }

    // Parcelling part
    public Participant(Parcel in){
        String[] data = new String[6];

        in.readStringArray(data);
        this.memberName = data[0];
        this.memberUID = data[1];
        this.editableValue = Float.parseFloat(data[2]);
        this.amount = Float.parseFloat(data[3]);
        this.percent = Float.parseFloat(data[4]);
        this.share = Float.parseFloat(data[5]);

    }


    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.memberName,
                this.memberUID,
                ""+this.editableValue,
                ""+this.amount,
                ""+this.percent,
                ""+this.share});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Participant createFromParcel(Parcel in) {
            return new Participant(in);
        }

        public Participant[] newArray(int size) {
            return new Participant[size];
        }

        public ArrayList<Participant> newArrayList() {
            return new ArrayList<Participant>();
        }
    };
}
