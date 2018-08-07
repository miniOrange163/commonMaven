package priv.linjb.common.util.base64;

import sun.misc.BASE64Decoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;

public final class StringDecoderUtil {
	private StringDecoderUtil() {
	}

	public static String encodeUTF8(String xmlDoc) {
		String str = "";
		try {
			xmlDoc = StringDecoderUtil.transNull(xmlDoc);
			if (!"".equals(xmlDoc)) {
				str = URLEncoder.encode(xmlDoc, "utf-8");

			}
			return str;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return str;
		//return xmlDoc;
	}
	
	public static String decodeUTF8(String xmlDoc) {
		String str = "";
		try {
			xmlDoc=StringDecoderUtil.transNull(xmlDoc);
			if(!"".equals(xmlDoc)){
				str = URLDecoder.decode(xmlDoc, "utf-8");
			}			
			return str;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return str;
	}

	public static String transNull(String str) {

		String result = str;
		if (null == str || "".equals(str) || "null".equalsIgnoreCase(str)) {
			result = "";
		}
		return result;
	}
	
	public static byte[] strToBase64(String content) throws IOException {
		if (null == content) {
			return null;
		}
		return new BASE64Decoder().decodeBuffer(content.trim());
	}

	public static String base64ToStr(byte[] bytes) throws IOException {
		String content = "";
		content = new sun.misc.BASE64Encoder().encode(bytes);
		return content;
	}

	public static byte[] getByte(InputStream in) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			copy(in, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();

	}
	
	private static void copy(InputStream in, OutputStream out) throws IOException {
		try {
			byte[] buffer = new byte[4096];
			int nrOfBytes = -1;
			while ((nrOfBytes = in.read(buffer)) != -1) {
				out.write(buffer, 0, nrOfBytes);
			}
			out.flush();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException ex) {
			}
		}
	}
}
