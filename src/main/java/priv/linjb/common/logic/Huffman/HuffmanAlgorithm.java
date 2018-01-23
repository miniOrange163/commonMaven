package priv.linjb.common.logic.Huffman;

import java.util.Map;

public interface HuffmanAlgorithm {  
    /** 
     * 编码字符串。 
     * @param str 指定的需要编码的字符串 
     * @return 编码结果 
     */  
    public EncodeResult encode(String str);  
    /** 
     * 编码字符串。 
     * @param str 指定的需要编码的字符串 
     * @return 编码结果 
     */  
    public EncodeResult encode(String str,Map<Character,Integer> ci);  
    /** 
     * 根据编码结果返回原来的字符串。 
     * @param decodeResult 原来字符串的编码结果。 
     * @return 解码出来的字符串。 
     */  
    public String decode(EncodeResult encodeResult);  
}  
