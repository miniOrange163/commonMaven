package priv.linjb.common.parse.importDBF;

public class Test {

	public static void sort(int []a){
			
			int index=0;
			int temp;
			for(int i=0;i<a.length;i++){
				if(a[i]%2==1){
					temp = a[i];
					a[i] = a[index++];
					a[index] = temp;
				}
					
			}
		}
	
	public static void main(String[] args) {
		int a[] = new int[]{1,4,5,6,9,10};
		
		for(int i=0;i<a.length;i++)
			System.out.println(a[i]);
	}
}
