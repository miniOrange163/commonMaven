package priv.linjb.common.util.email;

public class Test {

	public static void main(String[] args) {
		
		String myEmailAccount = "261780401@qq.com";		//发送方的邮箱地址
		String myEmailPassword = "gehexhgpswuxbgfi";	//授权码
		String tag = "这是一个标签1";						//标签
		String title = "主题:JAVA测试邮箱功能";				//邮件主题
		String content = "这是一封用JAVA发送的邮件";			//邮件内容
		String[] files = new String[]{"e://new.txt"};	//附件
		String[] receiverName = new String[]{"zd_dean@163.com"};	//收件人邮箱
		String[] receiverNameCopy = new String[]{"zd_dean@163.com"};//抄送人邮箱
		
		boolean result = EmailUtil.sendEmail("smtp.qq.com", myEmailAccount, myEmailPassword, 
							receiverName,receiverNameCopy,"zd_dean",
							tag,title,content,files);
		
		if(result){
			System.out.println("发送成功");
		}
		else{
			System.out.println("发送失败");
		}
	}
}
