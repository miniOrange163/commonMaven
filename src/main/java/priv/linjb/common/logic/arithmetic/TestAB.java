package priv.linjb.common.logic.arithmetic;

public class TestAB {

	private static int n,m;
	private static int a[] = new int[]{2,5,7,10,5,2};
	private static int b[] = new int[]{3,8,4,11,3,4};
	private static int p[][][] = new int[50][50][7];
	
	private static void read(){
		
		n = 6;
		
		int tmp = 0;
		for(int i=0;i<a.length;i++)
			tmp += a[i];
		m = tmp ;
		tmp = 0 ;
		for(int i=0;i<b.length;i++)
			tmp += b[i];
		
		if(tmp < m )
			m = tmp;
		
		
	}
	
	public static void schedule(){
		
		int x,y,k;
		for(x=0;x<=m;x++){
			for(y=0;y<m;y++){
				p[x][y][0] = 1;
				for(k=1;k<n;k++)
					p[x][y][k]= 0;
			}
		}
		
		for(k=1;k<=n;k++){
			for(x=0;x<=m;x++){
				for(y=0;y<=m;y++){
					if(x - a[k-1] >= 0)
						p[x][y][k] = p[x - a[k-1]][y][k-1];
					if(y - b[k-1] >= 0){
						if(p[x][y][k]>0||p[x][y-b[k-1]][k-1]>0)
							p[x][y][k] = 1;
						else 
							p[x][y][k] = 0;
							       
					}
					
				}
			}
		}
	}
	
	public static void write(){
		int t=0;
		int x,y,temp = 0,max =m;
		
		for(x=0;x<=m;x++){
			for(y=0;y<=m;y++){
			
				if(p[x][y][2] == 1){
					temp = (x>=y)?x:y;
			
					if(temp < max ) 
						max = temp;
					
					//System.out.println(temp);
					if(temp==15)
						System.out.println("x:" +x +"y:"+y);
				}
			}
		}
	
		System.out.println(max);
		
	}
	public static void main(String[] args) {
		read();
		schedule();
		write();
		
		test();
	}
	public static void test(){
		
		for(int i=0;i<32;i++)
			for(int j=0;j<32;j++)
			{
					
					System.out.print(p[i][j][3]+"   ");
					if(j==31)
						System.out.println();
				}
	}
}
