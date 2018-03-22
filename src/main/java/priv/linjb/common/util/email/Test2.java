package priv.linjb.common.util.email;

import java.util.Arrays;
import java.util.List;

public class Test2 {

	public static void main(String[] args) {
		
		String myEmailAccount = "linjb@signalway.com.cn";		//发送方的邮箱地址
		String myEmailPassword = "Fds172334";	//授权码
		String tag = "这是一个标签1";						//标签
		String title = "主题:JAVA测试邮箱功能2";				//邮件主题
		String content = "这是一封用JAVA发送的邮件";			//邮件内容
		List<String> files = null;	//附件
		String[] receiverName = new String[]{"linjb@signalway.com.cn","261780401@qq.com"};	//收件人邮箱
		String[] receiverNameCopy = new String[]{};//抄送人邮箱
		
		boolean result = EmailUtil.sendEmail("smtp.exmail.qq.com", myEmailAccount, myEmailPassword,
				Arrays.asList(receiverName),Arrays.asList(receiverNameCopy),"zd_dean",
							tag,title,content,files);
		
		if(result){
			System.out.println("发送成功");
		}
		else{
			System.out.println("发送失败");
		}
	}
}
