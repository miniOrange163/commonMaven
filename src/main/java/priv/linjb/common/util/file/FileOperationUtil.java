package priv.linjb.common.util.file;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.xmlgraphics.image.loader.impl.imageio.ImageIOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 文件操作工具类
 * @author Linjb
 *
 * 1. 	fileCopy(URL url,String toFile) 											从远程URL路径拷贝到本地文件
 * 2.	fileCopy(String formFile,String toFile)										从本地路径拷贝到本地文件
 * 3.	createDir(String destDirName)												创建目录
 * 4.	deleteAllFilesOfDir(String path)											删除目录
 * 5.	toByteArray(String filePath)												文件路径转字节数组
 * 6.	toByteArray(File file)														文件转字节数组
 * 7.	toByteArray(URL url)														远程URL路径转字节数组
 * 8.	writeFile(String catalog,String name,String content)						写入字符串到文件
 * 9.	writeFile(String catalog,String name,String content,String charset)			以指定字符编码写入字符串到文件
 * 10.	writeFileBuffer(String catalog,String name,String content,String charset)	以指定字符编码用字符流写入字符串到文件
 * 11.	writeFile(String catalog,String name,byte[] fileByte)					字节转文件
 * 12.  download(String url,String path)											从网络地址的url下载文件到指定路径
 * 13.  downloadToByte(String url)													从网络地址的url下载文件转到byte
 * 14.	downloadToBufferedImage(String url,RequestConfig config)					从网络地址的url下载文件转到BufferedImage
 * 15.	readFile(String filePath)													读取文本文件内容
 *
 */
public class FileOperationUtil {

	private static Logger logger = LoggerFactory.getLogger(FileOperationUtil.class);

	public static byte[] downloadToByte(String url) throws IOException {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		InputStream inStream = entity.getContent();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while((len = inStream.read(buffer)) != -1){
			outStream.write(buffer, 0, len);
		}
		try{

		}catch (Exception e){

		}finally {
			outStream.close();
			inStream.close();
            httpclient.close();
		}

		return outStream.toByteArray();

	}
    public static BufferedImage downloadToBufferedImage(String url, RequestConfig config) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        if (config != null) {
            httpget.setConfig(config);
        }

        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();

        try(InputStream inStream = entity.getContent()){


            BufferedImage read = ImageIO.read(inStream);

            return read;
        }finally {
            httpclient.close();
        }

    }
	/**
	 * 从远程服务器拷贝到本地文件
	 * @param url	源文件url地址
	 * @param path	目标文件
	 * @return
	 */
	public static boolean download(String url,String path) throws ClientProtocolException, IOException{

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		File file = new File(path);
		if(file.exists()){
			file.delete();
		}
		try(InputStream in = entity.getContent();
			FileOutputStream fout = new FileOutputStream(file);) {

			int l = -1;
			byte[] tmp = new byte[1024];
			while ((l = in.read(tmp)) != -1) {
				fout.write(tmp, 0, l);
				// 注意这里如果用OutputStream.write(buff)的话，图片会失真，大家可以试试
			}
			fout.flush();
			fout.close();
		}finally {
            httpclient.close();
        }
		return true;
	}
	/**
	 * 从远程服务器拷贝到本地文件
	 * @param url	源文件url地址
	 * @param toFile	目标文件
	 * @return
	 */
	public static boolean fileCopy(URL url,String toFile){



		HttpURLConnection httpUrl = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		try {

			httpUrl = (HttpURLConnection) url.openConnection();
			if(httpUrl.getResponseCode() != 200){
				return false;
			}
			httpUrl.connect();
			File to_file = new File(toFile);
			if(!to_file.exists()){
				to_file.createNewFile();
			}


			bis = new BufferedInputStream(httpUrl.getInputStream());
			fos = new FileOutputStream(to_file);

			byte buf[] = new byte[1024];

			int size = 0;
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}


			return true;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.toString(),e);
			return false;
		}finally {
			try {
				if(bis!=null){
					bis.close();
				}
				if(fos!=null){
					fos.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.toString(),e);

			}

			if(httpUrl!=null){
				httpUrl.disconnect();
			}
		}


	}

	/**
	 * 从本地路径拷贝到本地文件
	 * @param formFile	源文件
	 * @param toFile	目标文件
	 * @return
	 */
	public static boolean fileCopy(String formFile,String toFile){

		File form_file = new File(formFile);
		File to_file = new File(toFile);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			if(!form_file.exists()){
				return false;
			}
			if(!to_file.exists()){
				to_file.createNewFile();
			}

			fis = new FileInputStream(form_file);
			fos = new FileOutputStream(to_file);

			byte b[] = new byte[10];

			while(fis.read(b)!=-1){
				fos.write(b);
			}




			return true;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.toString(),e);
			return false;
		}finally {
			try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}


	}
	/**
	 * 创建目录
	 * @param destDirName
	 * @return
	 */
	public static String createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {// 判断目录是否存在

			return destDirName;
		}
		if (!destDirName.endsWith(File.separator)) {// 结尾是否以"/"结束
			destDirName = destDirName + File.separator;
		}
		if (dir.mkdirs()) {// 创建目标目录
			return destDirName;
		} else {

			return null;
		}
	}
	/**
	 * 递归删除文件或目录
	 * @param path	文件
	 */
	private static void deleteAllFilesOfDir(File path) {

		if (!path.exists())
			return;

		if (path.isFile()) {
			path.delete();
			return;
		}

		File[] files = path.listFiles();
		for (int i = 0; i < files.length; i++) {
			deleteAllFilesOfDir(files[i]);
		}
		path.delete();
	}
	/**
	 * 删除文件或目录
	 * @param path	路径名
	 */
	public static void deleteAllFilesOfDir(String path) {

		File file = new File(path);
		if (!file.exists())
			return;
		deleteAllFilesOfDir(file);
	}

	/**
	 * 文件路径转字节数组
	 * @param filePath
	 * @return
	 */
	public static byte[] toByteArray(String filePath){
		File file = new File(filePath);
		return toByteArray(file);
	}

	/**
	 * 远程URL路径转字节数组
	 * @param url
	 * @return
	 */
	public static byte[] toByteArray(URL url){

		HttpURLConnection httpUrl = null;
		BufferedInputStream bis = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {

			httpUrl = (HttpURLConnection) url.openConnection();
			if (httpUrl.getResponseCode() != 200) {
				return null;
			}
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			int buf_size = 1024;
			byte[] buffer = new byte[buf_size];
			int len = 0;

			while (-1 != (len = bis.read(buffer, 0, buf_size))) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();


		}
		catch (IOException e){
			e.printStackTrace();
			return null;
		}finally {

			try {
				if(bis!=null){
					bis.close();
				}


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.toString(),e);
			}
			if(httpUrl!=null){
				httpUrl.disconnect();
			}

		}

	}

	/**
	 * 文件转字节数组
	 * @param file
	 * @return
	 */
	public static byte[] toByteArray(File file){

		if(!file.exists()){
			return null;
		}


		ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			int buf_size = 1024;
			byte[] buffer = new byte[buf_size];
			int len = 0;
			while (-1 != (len = in.read(buffer, 0, buf_size))) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.toString(),e);

		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.toString(),e);
			}
			try {
				bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.toString(),e);
			}
		}

		return null;

	}

	/**
	 * 写入字符串到文件
	 * @param catalog 目录地址
	 * @param name	文件名
	 * @param content  内容
	 * @return
	 */
	public static boolean writeFile(String catalog,String name,String content){

		return writeFile(catalog,name,content,null);
	}
	/**
	 * 以指定字符编码写入字符串到文件
	 * @param catalog 目录地址
	 * @param name	文件名
	 * @param content  内容
	 * @param charset	字符编码
	 * @return
	 */
	public static boolean writeFile(String catalog,String name,String content,String charset){

		//创建目录
		FileOperationUtil.createDir(catalog);

		File file = new File(catalog + File.separator + name);

		FileOutputStream fos = null;
		BufferedWriter writer = null;
		try {

			if(!file.exists()){
				file.createNewFile();
			}
			else{
				file.delete();
				file.createNewFile();
			}
			fos = new FileOutputStream(file,true);
			byte byt[] = null;
			if(charset!=null && !"".equals(charset)){
				byt = content.getBytes(charset);
			}
			else{
				byt = content.getBytes();
			}
			fos.write(byt);


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.toString(),e);
			return false;
		}finally {
			try {
				if(fos!=null){
					fos.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.toString(),e);

			}
		}
		return true;
	}
	/**
	 * 以指定字符编码用字符流写入字符串到文件
	 * @param catalog 目录地址
	 * @param name	文件名
	 * @param content  内容
	 * @param charset	字符编码
	 * @return
	 */
	public static boolean writeFileBuffer(String catalog,String name,String content,String charset){

		//创建目录
		FileOperationUtil.createDir(catalog);

		File file = new File(catalog + File.separator + name);

		FileOutputStream fos = null;
		BufferedWriter writer = null;
		try {

			if(!file.exists()){
				file.createNewFile();
			}
			else{
				file.delete();
				file.createNewFile();
			}
			fos = new FileOutputStream(file,true);
			if(charset!=null && !"".equals(charset)){
				writer = new BufferedWriter(new OutputStreamWriter(fos, charset));
			}
			else{
				writer = new BufferedWriter(new OutputStreamWriter(fos));
			}

			writer.write(content);
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally {
			try {
				if(fos!=null){
					fos.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.toString(),e);

			}
		}
		return true;
	}
	/**
	 * 字节转文件
	 * @param catalog 目录地址
	 * @param name	文件名
	 * @param fileByte  字节
	 * @return
	 */
	public static boolean writeFile(String catalog,String name,byte[] fileByte){

		File file = new File(catalog + File.separator + name);
		if (file.exists()) {
			file.delete();
		}
		boolean flag = false;

		try (FileOutputStream output = new FileOutputStream(file);) {

			output.write(fileByte);
			output.flush();
			flag = true;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e.toString(),e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.toString(),e);
		}


		return flag;
	}
	public static String readFile(String filePath) throws IOException {

		StringBuffer buffer = new StringBuffer();
		File file = new File(filePath);
		if(!file.exists()){
			return null;
		}
		BufferedReader bufr = null;

		bufr = new BufferedReader(new FileReader(file));
		String line =null;
		while((line = bufr.readLine())!=null){
			buffer.append(line);
		}

		return buffer.toString();
	}
}
