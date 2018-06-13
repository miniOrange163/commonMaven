package priv.linjb.common.parse.word;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.WordprocessingML.AlternativeFormatInputPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.CTAltChunk;

/**
 * @author: 林嘉宝
 *  
 * @Date: 2018/6/12
 *  
 * @name: docx4j实现word文件的合并
 *
 * @Description: 
 */
public class Docx4jTest {


    public static final int BUFFER_LENGTH = 10240; //缓冲

    public void mergeDocx(List<String> list, File file) {
        List<InputStream> inList = new ArrayList<InputStream>();
        for (int i = 0; i < list.size(); i++)
            try {
                inList.add(new FileInputStream(list.get(i)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        try {
            InputStream inputStream = mergeDocx(inList);
            write(inputStream, file);
        } catch (Docx4JException | IOException e) {
            e.printStackTrace();
        }
    }

    public InputStream mergeDocx(final List<InputStream> streams) throws Docx4JException, IOException {
        WordprocessingMLPackage target = null;
        final File generated = File.createTempFile("generated", ".docx");
        int chunkId = 0;
        Iterator<InputStream> it = streams.iterator();
        while (it.hasNext()) {
            InputStream is = it.next();
            if (is != null) {
                if (target == null) {
                    // Copy first (master) document
                    OutputStream os = new FileOutputStream(generated);
                    os.write(IOUtils.toByteArray(is));
                    os.close();
                    target = WordprocessingMLPackage.load(generated);
                } else {
                    insertDocx(target.getMainDocumentPart(), IOUtils.toByteArray(is), chunkId++);
                }
            }
        }

        if (target != null) {
            target.save(generated);
            return new FileInputStream(generated);
        } else {
            return null;
        }
    }

    /**
     * 插入单个文档
     * @param main
     * @param bytes
     * @param chunkId
     */
    private void insertDocx(MainDocumentPart main, byte[] bytes, int chunkId) {
        try {
            AlternativeFormatInputPart afiPart = new AlternativeFormatInputPart(
                    new PartName("/part" + chunkId + ".docx"));
            afiPart.setBinaryData(bytes);
            Relationship altChunkRel = main.addTargetPart(afiPart);

            CTAltChunk chunk = Context.getWmlObjectFactory().createCTAltChunk();
            chunk.setId(altChunkRel.getId());

            main.addObject(chunk);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 写入文件
     * @param content
     * @return
     * @throws IOException
     */
    public static boolean write(InputStream content,File file) {
        OutputStream out = null;
        int read = 0;
        final byte[] bytes = new byte[BUFFER_LENGTH];
        try {
            out = new FileOutputStream(file, true);
            while ((read = content.read(bytes)) != -1){
                out.write(bytes, 0, read);
            }
        } catch (IOException e) {
            return false;
        }finally {
            close(out);
            close(content);
        }
        return true;
    }

    /**
     * 关闭流
     * @param stream
     */
    public static void close(Closeable stream) {
        try {
            if (stream != null)
                stream.close();
        } catch (IOException e) {
        }
    }



    public static void main(String[] args) throws Docx4JException, IOException{
        Docx4jTest wordUtil=new Docx4jTest();
        String template="my/word/";
        List<String> list=new ArrayList<String>();
        list.add(template+"图片表格测试模板.docx");
        list.add(template+"图片.docx");
        list.add(template+"图片.docx");

        File file = new File("d:/merge-docx4j.docx");
        if (file.exists()) {
            file.delete();
        }
        wordUtil.mergeDocx(list, file);
    }
}
