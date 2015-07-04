package company.greatapp.moneycircle.model;

/**
 * Created by Gyanendrasingh on 26-06-2015.
 */
public class RegisteredContact extends Model {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String phone;
    String email;

    public RegisteredContact(String name) {
        this.name = name;
    }
}
