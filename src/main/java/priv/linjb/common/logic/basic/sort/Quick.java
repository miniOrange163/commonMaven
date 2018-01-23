package priv.linjb.common.logic.basic.sort;

public class Quick {

	public static void sort(int arr[],int low,int high){
		int l=low;
		int h=high;
		int povit=arr[low];
		
		while(l<h)
		 {
			 while(l<h&&arr[h]>=povit)
				 h--;
			 
			 if(l<h){
				 int temp=arr[h];
				 arr[h]=arr[l];
				 arr[l]=temp;
				 l++;
			 }
		 
			 while(l<h&&arr[l]<=povit)
				 l++;
			 
			 if(l<h){
				 int temp=arr[h];
				 arr[h]=arr[l];
				 arr[l]=temp;
				 h--;
			 }
		 }
		for(int i : arr){
			System.out.print(i+"\t");
		}
		System.out.println();
		// System.out.print("l="+(l+1)+"h="+(h+1)+"povit="+povit+"\n");
		 if(l>low)sort(arr,low,l-1);
		 if(h<high)sort(arr,l+1,high);
	}
	
	public static void main(String[] args) {
		int num[] =new int[]{22,42,35,82,17,84,9};
		
		//排序情况：
		//9 42 35 82 17 84 22
		//9 22 35 82 17 84 42
		//9 17 35 82 22 84 42
		//9 17 22 82 35 84 42
		
		//9	17 22 42 35 84 82
		//9 17 22 42 35 84 82 
		
		sort(num, 0, num.length - 1);
		
		for(int i : num){
			System.out.print(i+"\t");
		}
	}
}
