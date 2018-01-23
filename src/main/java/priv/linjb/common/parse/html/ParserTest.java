package priv.linjb.common.parse.html;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

import priv.linjb.common.util.base64.ImageBase64;







public class ParserTest {   


	
	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.parse(new File("d://index.html"), "utf-8");
		Element img = doc.getElementById("max");
		img.attr("src",ImageBase64.imageToBase64Head("d://sh.jpg"));
		
		
	}
	
}