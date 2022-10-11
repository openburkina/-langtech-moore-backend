package bf.openburkina.langtechmoore.service.dto;

public class MResponse {
    String code;
    String msg;

    public MResponse() {
    }

    public MResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
