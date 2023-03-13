package bf.openburkina.langtechmoore.service.sms;

public class RecipientDTO {
    private String email;
    private String phone;
    private Object params;

    public RecipientDTO() {
    }

    public RecipientDTO(String email, String phone, Object params) {
        this.email = email;
        this.phone = phone;
        this.params = params;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }
}
