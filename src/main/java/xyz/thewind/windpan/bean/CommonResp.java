package xyz.thewind.windpan.bean;

public class CommonResp {
    private int code;
    private String module;
    private String msg;

    public CommonResp() {
    }

    public CommonResp(String module) {
        this.code=0;
        this.module = module;
        this.msg="success";
    }

    public CommonResp(int code, String module, String msg) {
        this.code = code;
        this.module = module;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
