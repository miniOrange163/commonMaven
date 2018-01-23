package priv.linjb.common.logic.Huffman;

import java.util.Map;  

/** 
 * 对字符串编码后的结果：包括编码后的字符串和字符/编码对 
 * @author yuncong 
 * 
 */  
public class EncodeResult {  
    // 字符串编码后的结果  
    private String encode;  
    // 字符编码对  
    private Map<Character, String> letterCode;  
    public EncodeResult(String encode, Map<Character, String> letterCode) {  
        super();  
        this.encode = encode;  
        this.letterCode = letterCode;  
    }  
    public String getEncode() {  
        return encode;  
    }  
    public Map<Character, String> getLetterCode() {  
        return letterCode;  
    }  
}  