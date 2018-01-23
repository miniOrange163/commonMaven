package priv.linjb.common.util.cglib;

import java.io.IOException;

public class ImportDmp {
	public static void main(String[] args) throws IOException {
		
		Runtime.getRuntime().exec("imp ggfw/ylz_ggfw@orcl_ios file=D:/test/a.dmp fromuser=max touser=ggfw");
	}
}
