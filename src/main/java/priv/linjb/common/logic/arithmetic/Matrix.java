package priv.linjb.common.logic.arithmetic;
//矩阵乘积
//动态规划法

public class Matrix {

	private final static int N = 6;
	private final static int cost[][] = new int[N][N];
	private final static int trace[][] = new int[N][N];
	
	public static int cmm(int n ,int seq[]){
		int tempCost = 0;
		int tempTrace = 0;
		int i,j,k,p;
		
		int temp;
		
		for(i=0;i<n;i++)
			cost[i][i] = 0;
		
		for(p=1;p<n;p++){
			for(i=0;i<n-p;i++){
				j = i + p;
				tempCost = -1;
				for(k=i;k<j;k++){
					System.out.println("i:" + i + "   k:" +k +"   j:" + j +"   p:" + p);
					System.out.println("i:" + i + "   k+1:" +(k+1) +"   j+1:" + (j+1));
					System.out.println("cost[i][k]:" +cost[i][k] +"   cost[k+1][j]:" +cost[k+1][j]);
					System.out.println("seq[i]=" + seq[i] +"   seq[k+1]=" + seq[k+1] +"   seq[j+1]=" + seq[j+1]);
					temp = cost[i][k]+cost[k+1][j]+seq[i]*seq[k+1]*seq[j+1] ;
					System.out.println("temp:" +temp);
					if(tempCost == -1||tempCost >temp){
						tempCost = temp;
						tempTrace = k;
					}
				}
				
				cost[i][j] = tempCost;
				trace[i][j] = tempTrace;
				
				for(int c=0;c<N;c++){
					for(int d=0;d<N;d++){
						System.out.print(cost[c][d] +"\t");
					}
					System.out.println();
				}
				System.out.println("////////////////////////////////////////////");
					
			}
		}
		
		for(i=0;i<N;i++){
			for(j=0;j<N;j++){
				System.out.print(cost[i][j] +"\t");
			}
			System.out.println();
		}
		System.out.println("----------------------------------------------------");
		for(i=0;i<N;i++){
			for(j=0;j<N;j++){
				System.out.print(trace[i][j] +"\t");
			}
			System.out.println();
		}
		return cost[0][n-1];
	}
	
	
	public static void main(String[] args) {
		int seq[] = new int[]{5,10,3,12,5,50,6};
		
	//	int seq[] = new int[]{10,100,5,50};
		
		System.out.println(cmm(6,seq));
	}
}
