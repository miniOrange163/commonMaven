package priv.linjb.learn.company.labscare;
/**
 * @author: 林嘉宝
 *  
 * @Date: 2019/6/28
 *  
 * @name: 
 *
 * @Description: 
 */
public class LabsCareRsponse<T> {

    private Integer code;

    private String message;

    private Integer status;

    private T result;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
