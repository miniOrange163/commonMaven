package priv.linjb.common.logic.Huffman;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;



/**
 * 哈夫曼编码解码例子  
 * @author max
 *
 */
public class HuffmanAlgorithmImpl1Test {  
  
  
    public static void testEncodeString() {  
        HuffmanAlgorithmImpl1 huffmanImpl1 = new HuffmanAlgorithmImpl1();  
        EncodeResult result = huffmanImpl1.encode("abcdda");  
        System.out.println(result.getEncode());  
    }  
  
 
    public static void testDecode() {  
        HuffmanAlgorithmImpl1 huffmanImpl1 = new HuffmanAlgorithmImpl1();  
        EncodeResult result = huffmanImpl1.encode("abcdda");  
        String decode = huffmanImpl1.decode(result);  
        System.out.println(decode);  
    }  
  
    public static Map<Character,Integer> initCode(){
    	Map<Character,Integer> code = new LinkedHashMap<Character,Integer>();
    	
    	code.put(' ', 17);
    	code.put('n', 14);
    	code.put('i', 10);
    	code.put('g', 8);
    	code.put('a', 8);
    	code.put('h', 7);
    	code.put('u', 6);
    	code.put('z', 5);
    	code.put('o', 5);
    	code.put(',', 3);
    	code.put('t', 3);
    	code.put('l', 2);
    	code.put('k', 2);
    	code.put('e', 2);
    	code.put('d', 2);
    	code.put('c', 2);
    	code.put('x', 1);
    	code.put('s', 1);
    	code.put('m', 1);
    	code.put('j', 1);
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	return code;
    }
    public static void main(String[] args) {
    	Map<Character,Integer> code = initCode();
    
    	String str = "zhu duo duo shen ti jian kang,cong ming ling li,tian tian kai xin,zhuo zhuang cheng zhang";
    	HuffmanAlgorithmImpl1 huffmanImpl1 = new HuffmanAlgorithmImpl1();  
        EncodeResult result = huffmanImpl1.encode(str,code);  
        Map map = result.getLetterCode();
        
        Set set = map.keySet();
        Iterator<Character> iter = set.iterator();
        System.out.println();
        while(iter.hasNext()){
        	
        	char c = iter.next();
        	System.out.println(c +":" + map.get(c));
        }
        System.out.println(result.getEncode());
        
	}
  
}  
