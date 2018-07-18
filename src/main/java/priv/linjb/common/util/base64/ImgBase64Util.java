package priv.linjb.common.util.base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Base64;

public class ImgBase64Util {
	private static final Logger logger = LoggerFactory.getLogger(ImgBase64Util.class);
	public static String imgToBase64String(byte[] img) {
		try {
			if (img != null) {
				return Base64.getEncoder().encodeToString(img).trim();
			}
		} catch (Exception e) {
			logger.debug("图片byte[] 转换Base64String异常");
		}
		return null;
	}
	public static String sunByteToBase64(byte[] bytes){
		String content = "";
		content = new sun.misc.BASE64Encoder().encode(bytes);
		return content;
	}
	public static byte[] imgToBytes(String base64Img) {
		try {
			if (base64Img != null) {
				return ByteBuffer.wrap(
						Base64.getDecoder().decode(base64Img.trim())).array();
			}
		} catch (Exception e) {
			logger.debug("Base64String图片 转换byte[]异常");
		}
		return null;
	}

}
