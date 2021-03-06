package priv.linjb.common.util.image;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;


/**
 * <p>Title: ImageUtil </p>
 * <p>Description: </p>
 * <p>Email: icerainsoft@hotmail.com </p>
 * @author Ares
 * @date 2014年10月28日 上午10:24:26
 */
public class ImageUtil {


    public static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    private static String DEFAULT_THUMB_PREVFIX = "thumb_";
    private static String DEFAULT_CUT_PREVFIX = "cut_";
    private static Boolean DEFAULT_FORCE = false;

    public static byte[] parseByte(BufferedImage image){
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();) {

            ImageIO.write(image, "jpg", baos);
            return  baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    public static InputStream parseInputStream(BufferedImage image){
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();) {

            ImageIO.write(image, "jpg", baos);
            InputStream is = new ByteArrayInputStream(baos.toByteArray());

            return is;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
            return null;
        }
    }

    public static byte[] cutImage(byte[] bytes, int x, int y, int width, int height){
        return cutImage(bytes, new Rectangle(x, y, width, height));
    }
    public static byte[] cutImage(byte[] bytes,Rectangle rect){

        InputStream fis = null;
        ImageInputStream iis = null;
        ByteArrayOutputStream baos = null;
        try {

            baos = new ByteArrayOutputStream();
            fis = new ByteArrayInputStream(bytes);
            iis = ImageIO.createImageInputStream(fis);

            ImageInputStream iiis = ImageIO.createImageInputStream(bytes);
            ImageReader reader = ImageIO.getImageReaders(iis).next();

            String suffix = reader.getFormatName();
            reader.setInput(iis,true);

            ImageReadParam param = reader.getDefaultReadParam();
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            ImageIO.write(bi, suffix, baos);


            byte[] outBytes = baos.toByteArray();

            return outBytes;

        }
        catch (javax.imageio.IIOException e){
            e.printStackTrace();
            // throw e;
        }
        catch (IOException e) {

            e.printStackTrace();

        }finally {

            try {
                if (fis != null) {
                    fis.close();
                }
                if (iis != null) {
                    iis.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static byte[] cutImage(byte[] bytes,String ext,Rectangle rect){

        InputStream fis = null;
        ImageInputStream iis = null;
        ByteArrayOutputStream baos = null;
        try {

            baos = new ByteArrayOutputStream();
            fis = new ByteArrayInputStream(bytes);
            iis = ImageIO.createImageInputStream(fis);
            String suffix = "jpg";
            if(StringUtils.isNotBlank(ext)){
                suffix = ext;
            }

            // 根据图片类型获取该种类型的ImageReader
            ImageReader reader = ImageIO.getImageReadersBySuffix(suffix).next();
            reader.setInput(iis,true);

            ImageReadParam param = reader.getDefaultReadParam();
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            ImageIO.write(bi, suffix, baos);

            byte[] outBytes = baos.toByteArray();

            return outBytes;

        }
        catch (javax.imageio.IIOException e){
            e.printStackTrace();
           // throw e;
        }
        catch (IOException e) {

            e.printStackTrace();

        }finally {

            try {
                if (fis != null) {
                    fis.close();
                }
                if (iis != null) {
                    iis.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    /**
     * <p>Title: cutImage</p>
     * <p>Description:  根据原图与裁切size截取局部图片</p>
     * @param srcImg    源图片
     * @param output    图片输出流
     * @param rect        需要截取部分的坐标和大小
     */
    public void cutImage(File srcImg, OutputStream output, Rectangle rect){
        if(srcImg.exists()){
            FileInputStream fis = null;
            ImageInputStream iis = null;
            try {
                fis = new FileInputStream(srcImg);
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
                String types = Arrays.toString(ImageIO.getReaderFormatNames()).replace("]", ",");
                String suffix = null;
                // 获取图片后缀
                if(srcImg.getName().indexOf(".") > -1) {
                    suffix = srcImg.getName().substring(srcImg.getName().lastIndexOf(".") + 1);
                }// 类型和图片后缀全部小写，然后判断后缀是否合法
                if(suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()+",") < 0){
                    logger.error("Sorry, the image suffix is illegal. the standard image suffix is {}." + types);
                    return ;
                }
                // 将FileInputStream 转换为ImageInputStream
                iis = ImageIO.createImageInputStream(fis);
                // 根据图片类型获取该种类型的ImageReader
                ImageReader reader = ImageIO.getImageReadersBySuffix(suffix).next();
                reader.setInput(iis,true);
                ImageReadParam param = reader.getDefaultReadParam();
                param.setSourceRegion(rect);
                BufferedImage bi = reader.read(0, param);
                ImageIO.write(bi, suffix, output);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(fis != null) fis.close();
                    if(iis != null) iis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
            logger.warn("the src image is not exist.");
        }
    }

    public void cutImage(File srcImg, OutputStream output, int x, int y, int width, int height){
        cutImage(srcImg, output, new Rectangle(x, y, width, height));
    }

    public void cutImage(File srcImg, String destImgPath, Rectangle rect){
        File destImg = new File(destImgPath);
        if(destImg.exists()){
            String p = destImg.getPath();
            try {
                if(!destImg.isDirectory()) p = destImg.getParent();
                if(!p.endsWith(File.separator)) p = p + File.separator;
                cutImage(srcImg, new FileOutputStream(p + DEFAULT_CUT_PREVFIX + "_" + new java.util.Date().getTime() + "_" + srcImg.getName()), rect);
            } catch (FileNotFoundException e) {
                logger.warn("the dest image is not exist.");
            }
        }else logger.warn("the dest image folder is not exist.");
    }

    public void cutImage(File srcImg, String destImg, int x, int y, int width, int height){
        cutImage(srcImg, destImg, new Rectangle(x, y, width, height));
    }

    public void cutImage(String srcImg, String destImg, int x, int y, int width, int height){
        cutImage(new File(srcImg), destImg, new Rectangle(x, y, width, height));
    }
    /**
     * <p>Title: thumbnailImage</p>
     * <p>Description: 根据图片路径生成缩略图 </p>
     * @param bytes    原图片字节数组
     * @param w            缩略图宽
     * @param h            缩略图高
     * @param suffix       图片扩展名
     * @param force        是否强制按照宽高生成缩略图(如果为false，则生成最佳比例缩略图)
     */
    public static byte[] thumbnailImage(byte[] bytes,  Integer w, Integer h,String suffix, boolean force){
        if(bytes!=null){
            InputStream fis = null;
            ImageInputStream iis = null;
            ByteArrayOutputStream baos = null;
            try {
                fis = new ByteArrayInputStream(bytes);
                iis = ImageIO.createImageInputStream(fis);
                baos = new ByteArrayOutputStream();
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
                String types = Arrays.toString(ImageIO.getReaderFormatNames()).replace("]", ",");

                logger.debug("target image's size, width:{}, height:{}.",w,h);

                Image img = ImageIO.read(iis);
                // 根据原图与要求的缩略图比例，找到最合适的缩略图比例
                if (w == null) {
                    w = ((BufferedImage) img).getWidth();
                }
                if (h == null) {
                    h = ((BufferedImage) img).getHeight();
                }
                if(!force){
                    int width = img.getWidth(null);
                    int height = img.getHeight(null);
                    if((width*1.0)/w < (height*1.0)/h){
                        if(width > w){
                            h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w/(width*1.0)));
                            logger.debug("change image's height, width:{}, height:{}.",w,h);
                        }
                    } else {
                        if(height > h){
                            w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h/(height*1.0)));
                            logger.debug("change image's width, width:{}, height:{}.",w,h);
                        }
                    }
                }
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.getGraphics();
                g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
                g.dispose();
                // 将图片保存在原目录并加上前缀
                ImageIO.write(bi, suffix, baos);

                return baos.toByteArray();

            } catch (IOException e) {
                e.printStackTrace();
                logger.error("generate thumbnail image failed." + e.toString(),e);
            }finally {

                try {
                    if (baos != null) {
                        baos.close();
                    }
                    if (fis != null) {
                        fis.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }else{
            logger.warn("the src image is not exist.");
        }

        return null;
    }
    /**
     * <p>Title: thumbnailImage</p>
     * <p>Description: 根据图片路径生成缩略图 </p>
     * @param srcImg    原图片
     * @param w            缩略图宽
     * @param h            缩略图高
     * @param force        是否强制按照宽高生成缩略图(如果为false，则生成最佳比例缩略图)
     */
    public static byte[] thumbnailImage(File srcImg,  int w, int h, boolean force){
        if(srcImg.exists()){
            try(ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
                String types = Arrays.toString(ImageIO.getReaderFormatNames()).replace("]", ",");
                String suffix = null;
                // 获取图片后缀
                if(srcImg.getName().indexOf(".") > -1) {
                    suffix = srcImg.getName().substring(srcImg.getName().lastIndexOf(".") + 1);
                }// 类型和图片后缀全部小写，然后判断后缀是否合法
                if(suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()+",") < 0){
                    logger.error("Sorry, the image suffix is illegal. the standard image suffix is {}." + types);
                    return null;
                }
                logger.debug("target image's size, width:{}, height:{}.",w,h);
                Image img = ImageIO.read(srcImg);
                // 根据原图与要求的缩略图比例，找到最合适的缩略图比例
                if(!force){
                    int width = img.getWidth(null);
                    int height = img.getHeight(null);
                    if((width*1.0)/w < (height*1.0)/h){
                        if(width > w){
                            h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w/(width*1.0)));
                            logger.debug("change image's height, width:{}, height:{}.",w,h);
                        }
                    } else {
                        if(height > h){
                            w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h/(height*1.0)));
                            logger.debug("change image's width, width:{}, height:{}.",w,h);
                        }
                    }
                }
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.getGraphics();
                g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
                g.dispose();
                // 将图片保存在原目录并加上前缀
                ImageIO.write(bi, suffix, baos);

                return baos.toByteArray();
            } catch (IOException e) {
                logger.error("generate thumbnail image failed.",e);
            }
        }else{
            logger.warn("the src image is not exist.");
        }

        return null;
    }
    /**
     * <p>Title: thumbnailImage</p>
     * <p>Description: 根据图片路径生成缩略图 </p>
     * @param srcImg    原图片
     * @param output    目标
     * @param w            缩略图宽
     * @param h            缩略图高
     * @param prevfix    生成缩略图的前缀
     * @param force        是否强制按照宽高生成缩略图(如果为false，则生成最佳比例缩略图)
     */
    public static void thumbnailImage(File srcImg, OutputStream output, int w, int h, String prevfix, boolean force){
        if(srcImg.exists()){
            try {
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
                String types = Arrays.toString(ImageIO.getReaderFormatNames()).replace("]", ",");
                String suffix = null;
                // 获取图片后缀
                if(srcImg.getName().indexOf(".") > -1) {
                    suffix = srcImg.getName().substring(srcImg.getName().lastIndexOf(".") + 1);
                }// 类型和图片后缀全部小写，然后判断后缀是否合法
                if(suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()+",") < 0){
                    logger.error("Sorry, the image suffix is illegal. the standard image suffix is {}." + types);
                    return ;
                }
                logger.debug("target image's size, width:{}, height:{}.",w,h);
                Image img = ImageIO.read(srcImg);
                // 根据原图与要求的缩略图比例，找到最合适的缩略图比例
                if(!force){
                    int width = img.getWidth(null);
                    int height = img.getHeight(null);
                    if((width*1.0)/w < (height*1.0)/h){
                        if(width > w){
                            h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w/(width*1.0)));
                            logger.debug("change image's height, width:{}, height:{}.",w,h);
                        }
                    } else {
                        if(height > h){
                            w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h/(height*1.0)));
                            logger.debug("change image's width, width:{}, height:{}.",w,h);
                        }
                    }
                }
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.getGraphics();
                g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
                g.dispose();
                // 将图片保存在原目录并加上前缀
                ImageIO.write(bi, suffix, output);
                output.close();
            } catch (IOException e) {
                logger.error("generate thumbnail image failed.",e);
            }
        }else{
            logger.warn("the src image is not exist.");
        }
    }
    public void thumbnailImage(File srcImg, int w, int h, String prevfix, boolean force){
        String p = srcImg.getAbsolutePath();
        try {
            if(!srcImg.isDirectory()) p = srcImg.getParent();
            if(!p.endsWith(File.separator)) p = p + File.separator;
            thumbnailImage(srcImg, new FileOutputStream(p + prevfix +srcImg.getName()), w, h, prevfix, force);
        } catch (FileNotFoundException e) {
            logger.error("the dest image is not exist.",e);
        }
    }

    public void thumbnailImage(String imagePath, int w, int h, String prevfix, boolean force){
        File srcImg = new File(imagePath);
        thumbnailImage(srcImg, w, h, prevfix, force);
    }

    public void thumbnailImage(String imagePath, int w, int h, boolean force){
        thumbnailImage(imagePath, w, h, DEFAULT_THUMB_PREVFIX, DEFAULT_FORCE);
    }

    public void thumbnailImage(String imagePath, int w, int h){
        thumbnailImage(imagePath, w, h, DEFAULT_FORCE);
    }

    public static void main(String[] args) {
        new ImageUtil().thumbnailImage("imgs/Tulips.jpg", 150, 100);
        new ImageUtil().cutImage("imgs/Tulips.jpg","imgs", 250, 70, 300, 400);
    }

    public static String getImageFileExt(String fileName){
        int pos = fileName.lastIndexOf(".");
        String ext ="jpg";
        if(pos != -1) {
            ext = fileName.substring(pos + 1);
        }
        return ext;
    }
    public static String getImageFileExt(byte[] bytes){
        InputStream fis = null;
        ImageInputStream iis = null;

        try {
            fis = new ByteArrayInputStream(bytes);
            iis = ImageIO.createImageInputStream(fis);
            ImageReader reader = ImageIO.getImageReaders(iis).next();
            return reader.getFormatName();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            try {
                fis.close();
                iis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;

    }

    public static class ImageInfo{
        int width;
        int height;

        public ImageInfo(int w, int h) {
            this.width = w;
            this.height = h;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }

    public static ImageInfo getImageInfo(File img) throws IOException {

        BufferedImage read = ImageIO.read(img);

        return new ImageInfo(read.getWidth(),read.getHeight());
    }

}