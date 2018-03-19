package priv.linjb.common.util.email;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
public class EmailUtil {

	/**
	 * 发送邮件的方法
	 * @param myEmailSMTPHost 	邮件服务器的smtpHost地址：如smtp.exmail.qq.com
	 * @param myEmailAccount	帐号
	 * @param myEmailPassword	密码
	 * @param receiveMail		收件人列表
	 * @param receiveMailCopy	抄送人列表
	 * @param receiverName		主题
	 * @param tag				标签
	 * @param setSubject		子主题
	 * @param content			内容
	 * @param files				附件
	 * @return
	 */
	public static boolean sendEmail(
			String myEmailSMTPHost, String myEmailAccount, String myEmailPassword,
			List<String> receiveMail, List<String> receiveMailCopy, String receiverName,
			String tag, String setSubject, String content,List<String> files) {

		// 1. 创建参数配置, 用于连接邮件服务器的参数配置
		Properties props = new Properties(); // 参数配置
		props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
		props.setProperty("mail.smtp.host", myEmailSMTPHost); // 发件人的邮箱的 SMTP
		// 服务器地址
		props.setProperty("mail.smtp.auth", "true"); // 需要请求认证

		// PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
		// 如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
		// 打开下面 /* ... */ 之间的注释代码, 开启 SSL 安全连接。
		// SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
		// 需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
		// QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
		final String smtpPort = "465";
		props.setProperty("mail.smtp.port", smtpPort);
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.socketFactory.port", smtpPort);

		// 2. 根据配置创建会话对象, 用于和邮件服务器交互
		Transport transport = null;
		boolean isSendSuccess = false;
		try {
			Session session = Session.getDefaultInstance(props);
			session.setDebug(true); // 设置为debug模式, 可以查看详细的发送 log

			// 3. 创建一封邮件
			MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMail,receiveMailCopy, tag,
					receiverName, setSubject, content,files);

			// 4. 根据 Session 获取邮件传输对象
			transport = session.getTransport();

			// 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
			//
			// PS_01: 成败的判断关键在此一句, 如果连接服务器失败, 都会在控制台输出相应失败原因的 log,
			// 仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接, 根据给出的错误
			// 类型到对应邮件服务器的帮助网站上查看具体失败原因。
			//
			// PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
			// (1) 邮箱没有开启 SMTP 服务;
			// (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
			// (3) 邮箱服务器要求必须要使用 SSL 安全连接;
			// (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
			// (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
			//
			// PS_03: 仔细看log, 认真看log, 看懂log, 错误原因都在log已说明。
			transport.connect(myEmailSMTPHost, myEmailAccount, myEmailPassword);

			// 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients()
			// 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
			transport.sendMessage(message, message.getAllRecipients());
			isSendSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 7. 关闭连接
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}

		}
		return isSendSuccess;
	}

	/**
	 * 创建一封只包含文本的简单邮件
	 *
	 * @param session
	 *            和服务器交互的会话
	 * @param sendMail
	 *            发件人邮箱
	 * @param receiveMail
	 *            收件人邮箱
	 * @return
	 * @throws Exception
	 */
	private static MimeMessage createMimeMessage(Session session, String sendMail,
												 List<String> receiveMail, List<String> receiveMailCopy, String tag,
												 String receiverName, String setSubject, String content, List<String> files) throws Exception {
		// 1. 创建一封邮件
		MimeMessage message = new MimeMessage(session);

		// 2. From: 发件人
		message.setFrom(new InternetAddress(sendMail, tag, "UTF-8"));

		// 3. To: 收件人（可以增加多个收件人、抄送、密送）
		//message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, receiverName, "UTF-8"));
		setReceive(message,receiveMail,receiveMailCopy);
		/**
		 * 如果是用163，可能会出现•554 DT:SPM 错误，这是因为被识别为垃圾邮件了，解决办法是主题和正文弄得正式一点，然后换个网络
		 */
		// 4. Subject: 邮件主题
		message.setSubject(setSubject, "UTF-8");

		// 5. Content: 邮件正文（可以使用html标签）
		//message.setContent(content, "text/html;charset=UTF-8");
		Multipart multipart = new MimeMultipart();
		BodyPart contentPart = new MimeBodyPart();
		contentPart.setHeader("Content-Type", "text/html; charset=GBK");
		contentPart.setText(content);
		multipart.addBodyPart(contentPart);
		//添加内容到正文
		message.setContent(multipart);
		//添加附件
		message.setContent(addFileInMultipart(multipart,files));

		// 6. 设置发件时间
		message.setSentDate(new Date());
		// 7. 保存设置
		message.saveChanges();

		return message;
	}

	private static void setReceive(MimeMessage message,List<String> receiveMail,List<String> receiveMailCopy) throws AddressException, MessagingException{

		List<InternetAddress> receiveMailList = new ArrayList();
		List<InternetAddress> receiveMailCopyList = new ArrayList();

		if(receiveMail!=null)
			for(String address:receiveMail)
				if(StringUtils.isNotBlank(address))
					receiveMailList.add(new InternetAddress(address));


		InternetAddress[] receiveMailAddress = (InternetAddress[]) receiveMailList.toArray(new InternetAddress[receiveMailList.size()]);
		message.setRecipients(MimeMessage.RecipientType.TO,receiveMailAddress);

		if(receiveMailCopy!=null)
			for(String address:receiveMailCopy)
				if(StringUtils.isNotBlank(address))
					receiveMailCopyList.add(new InternetAddress(address));

		InternetAddress[] receiveMailCopyAddress = (InternetAddress[]) receiveMailCopyList.toArray(new InternetAddress[receiveMailCopyList.size()]);
		message.setRecipients(MimeMessage.RecipientType.CC, receiveMailCopyAddress);

	}
	private static Multipart addFileInMultipart(Multipart multipart,List<String> files){
		if(files == null)
			return multipart;

		File usFile = null;
		try {
			/*添加附件*/
			for (String file : files) {

				if(StringUtils.isBlank(file))
					break;
				usFile = new File(file);
				if(!usFile.exists())
					break;

				MimeBodyPart fileBody = new MimeBodyPart();
				DataSource source = new FileDataSource(file);
				fileBody.setDataHandler(new DataHandler(source));
				sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
				fileBody.setFileName("=?GBK?B?"
						+ enc.encode(usFile.getName().getBytes()) + "?=");
				multipart.addBodyPart(fileBody);
			}

			return multipart;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return multipart;

	}
}

