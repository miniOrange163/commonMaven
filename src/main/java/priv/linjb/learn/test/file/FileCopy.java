package priv.linjb.learn.test.file;

import java.io.File;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;


public class FileCopy {

	
	public static void main(String[] args) throws ClientProtocolException, IOException {

		File file = new File("D:/police_data_copy_heyuan/cardata/cardata_send/txt/20171116153735-ÎÞGPS7112.txt");
		
		
		System.out.println(file.exists());
		System.out.println(file.delete());
	}
}
