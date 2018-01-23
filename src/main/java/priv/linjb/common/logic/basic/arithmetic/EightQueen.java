package priv.linjb.common.logic.basic.arithmetic;

public class EightQueen {
    
    private static final int CHESS_SIZE = 8;

    //数组下标表示列,数组对应的值表示行
    private static int[] queens = new int[CHESS_SIZE];
    
    private static int sum;
    
    public static void main(String[] args){
        
        //初始化为一个不存在的行
        for(int i=0;i<CHESS_SIZE;i++){
            //保证列列不受初始值影响
            queens[i] = 10000;
        }
        
        //查找将哪个皇后放在第0行
        count(queens,0);
        System.out.println("total:"+sum);
    }
    
    /**
     * 找出一个皇后放在第row行
     */
    public static  void count(int[] queens, int row){
        
        for(int i=0;i<CHESS_SIZE;i++){
            queens[row]=i;
            if(checkLegal(row)){
                if(row == CHESS_SIZE-1){
                    printChess();
                    sum++;
                }else{
                    count(queens,row+1);
                }
            }
        }
        
    }

    /**
     * 和第row行之前已经放置好的皇后进行比较,查看是否是合规位置
     */
    public static boolean checkLegal(int row){
        for(int i=0;i<row;i++){
            if(queens[i]-queens[row] == row-i || row-i ==queens[row]-queens[i] || queens[row]==queens[i]){
                return false;
            }
        }
        return true;
    }

    /**
     * 打印出棋局
     */
    public static void printChess(){
        System.out.println();
        for(int i=0;i<CHESS_SIZE;i++){
            for(int j=0;j<CHESS_SIZE;j++){
                if(queens[i] != j){
                    System.out.print("* ");
                }else{
                    System.out.print("Q ");
                }
            }
            System.out.println();
        }
        
    }

}
