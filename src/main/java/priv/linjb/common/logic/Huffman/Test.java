package priv.linjb.common.logic.Huffman;

import java.text.DecimalFormat;

public class Test {

	public static void main(String[] args) {
		int sum = 0;
		
		String str = "zhu duo duo shen ti jian kang,cong ming ling li,tian tian kai xin,zhuo zhuang cheng zhang";
		Counter counter = new Counter(str);
		
		char[] ch = counter.getCh();
		double[] tmp = counter.format();
		double[] hum = new double[tmp.length];
		for(int i=0;i<ch.length;i++){
			if(tmp[i] > 0){
				sum += tmp[i];
				System.out.println(ch[i] + ":\t" + tmp[i]);
				
			}
				
		}
		sort(tmp,ch);
		
		DecimalFormat df=new DecimalFormat("0.0"); 
		double percent = 0;
		for(int i=0;i<tmp.length;i++){
			if(tmp[i] > 0){
				hum[i] = tmp[i]/sum;
				//System.out.println(hum[i]);
				
				
				double rint = Math.rint(hum[i]*1000)/10;
				System.out.println(rint);
				if(i == 13 || i==14)
					rint += 0.1;
					System.out.println(rint);
					System.out.println("第"+i+"个" + ch[i] + ":\t\t" + rint + "%");
				
				percent += rint;
				/*System.out.println(ch[i] + ":\t" + hum[i]);
				percent += hum[i];*/
				
			}
			
			
		}
		System.out.println(sum);
		System.out.println(percent);
	}
	
	public static void sort(double[] tmp,char[] ch){
		double temp;
		char tempCh;
		
		for(int i=0;i<tmp.length;i++)
			for(int j=0;j<tmp.length;j++){
				
				if(tmp[i] < tmp[j] && tmp[j]!=0 && tmp[i] != 0){
					temp = tmp[i];
					tmp[i] = tmp[j];
					tmp[j] = temp;
					
				
					tempCh = ch[i];
					ch[i] = ch[j];
					ch[j] = tempCh;
				}
			}
	}
}
