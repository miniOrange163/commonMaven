package priv.linjb.common.logic.Huffman;

public class Counter {

	private String str;
	
	private char[] ch;
	private double[] tmp;
	Counter(String str){
		this.str = str;
		ch = new char[] {'a','b','c','d','e','f','g',
						 'h','i','j','k','l','m','n',
						 'o','p','q','r','s','t','u',
						 'w','x','y','z',' ',',','。'};
		tmp = new double[30];
		
	
		
	}
	public char[] getCh(){
		
		return ch;
	}
	public double[] format(){
		char[] target = str.toCharArray();
		
		for(int i=0;i<target.length;i++){
			
			for(int j=0;j<ch.length;j++){
				
				if(target[i] == ch[j]){
					tmp[j]++;
				}
			}
		}
		
		
		
		
		
		return tmp;
	}
	
}
