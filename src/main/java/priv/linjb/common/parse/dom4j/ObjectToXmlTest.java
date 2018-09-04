package priv.linjb.common.parse.dom4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;

public class ObjectToXmlTest {

	public static void main(String[] args) {
		
		XStream xstream = new XStream();
		//解析xml文本
		xstream.processAnnotations(PicInfo.class);
		
		PicInfo result = new PicInfo();
		result.setJGSJ("abc");
		result.setAbcd("main");
		String xml = xstream.toXML(result);
		
		System.out.println(xml);
		//writeXmlFile(xml);
	}
	
	public static void writeXmlFile(String xml){
		String catalog = "./src/main/resources/ftp/";
		File file = new File(catalog + "test.xml");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file,true);
			byte byt[] = xml.getBytes();
			fos.write(byt);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
