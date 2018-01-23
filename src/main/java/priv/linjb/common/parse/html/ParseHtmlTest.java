package priv.linjb.common.parse.html;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.HtmlPage;
import org.htmlparser.visitors.TextExtractingVisitor;

//import com.jscud.util.LogMan; //一个日志记录类

/**
 * 演示了Html Parse的应用.
 * 
 * @author scud http://www.jscud.com
 */

public class ParseHtmlTest
{

	
    public static void main(String[] args) throws Exception
    {
        String aFile = "d:/index.html";

        String content = readTextFile(aFile, "GBK");

       // test1(content);
        System.out.println("====================================");

        test2(content);
        System.out.println("====================================");

        test3(content);
        System.out.println("====================================");

        test4(content);
        System.out.println("====================================");

        test5(aFile);
        System.out.println("====================================");

        //访问外部资源,相对慢
       // test5("http://www.jscud.com"); 
        System.out.println("====================================");

    }

    /**
     * 读取文件的方式来分析内容.
     * filePath也可以是一个Url.
     * 
     * @param resource 文件/Url
     */
    public static void test5(String resource) throws Exception
    {
        Parser myParser = new Parser(resource);

        //设置编码
        myParser.setEncoding("GBK");

        HtmlPage visitor = new HtmlPage(myParser);

        myParser.visitAllNodesWith(visitor);

        String textInPage = visitor.getTitle();

        System.out.println(textInPage);
    }

    /**
     * 按页面方式处理.对一个标准的Html页面,推荐使用此种方式.
     */
    public static void test4(String content) throws Exception
    {
        Parser myParser;
        myParser = Parser.createParser(content, "GBK");

        HtmlPage visitor = new HtmlPage(myParser);

        myParser.visitAllNodesWith(visitor);

        String textInPage = visitor.getTitle();

        System.out.println(textInPage);
    }

    /**
     * 利用Visitor模式解析html页面.
     *
     * 小优点:翻译了<>等符号 
     * 缺点:好多空格,无法提取link
     *   
     */
    public static void test3(String content) throws Exception
    {
        Parser myParser;
        myParser = Parser.createParser(content, "GBK");

        TextExtractingVisitor visitor = new TextExtractingVisitor();

        myParser.visitAllNodesWith(visitor);

        String textInPage = visitor.getExtractedText();

        System.out.println(textInPage);
    }

    /**
     * 得到普通文本和链接的内容.
     * 
     * 使用了过滤条件.
     */
    public static void test2(String content) throws ParserException
    {
        Parser myParser;
        NodeList nodeList = null;

        myParser = Parser.createParser(content, "GBK");

        NodeFilter textFilter = new NodeClassFilter(TextNode.class);
        NodeFilter linkFilter = new NodeClassFilter(LinkTag.class);

        //暂时不处理 meta
        //NodeFilter metaFilter = new NodeClassFilter(MetaTag.class);

        OrFilter lastFilter = new OrFilter();
        lastFilter.setPredicates(new NodeFilter[] { textFilter, linkFilter });

        nodeList = myParser.parse(lastFilter);

        Node[] nodes = nodeList.toNodeArray();

        for (int i = 0; i < nodes.length; i++)
        {
            Node anode = (Node) nodes[i];

            String line = "";
            if (anode instanceof TextNode)
            {
                TextNode textnode = (TextNode) anode;
                //line = textnode.toPlainTextString().trim();
                line = textnode.getText();
            }
            else if (anode instanceof LinkTag)
            {
                LinkTag linknode = (LinkTag) anode;

                line = linknode.getLink();
                //@todo 过滤jsp标签:可以自己实现这个函数
                //line = StringFunc.replace(line, "<%.*%>", "");
            }

            if (isTrimEmpty(line))
                continue;

            System.out.println(line);
        }
    }

    /**
     * 解析普通文本节点.
     * 
     * @param content
     * @throws ParserException
     */
    public static void test1(String content) throws ParserException
    {
        Parser myParser;
        Node[] nodes = null;

        myParser = Parser.createParser(content, null);
      
       //s nodes = myParser.extractAllNodesThatAre(TextNode.class); //exception could be thrown here
        
        for (int i = 0; i < nodes.length; i++)
        {
            TextNode textnode = (TextNode) nodes[i];
            String line = textnode.toPlainTextString().trim();
            if (line.equals(""))
                continue;
            System.out.println(line);
        }

    }

    /**
     * 读取一个文件到字符串里.
     * 
     * @param sFileName  文件名
     * @param sEncode   String
     * @return 文件内容
     */
    public static String readTextFile(String sFileName, String sEncode)
    {
        StringBuffer sbStr = new StringBuffer();

        try
        {
            File ff = new File(sFileName);
            InputStreamReader read = new InputStreamReader(new FileInputStream(ff),
                    sEncode);
            BufferedReader ins = new BufferedReader(read);

            String dataLine = "";
            while (null != (dataLine = ins.readLine()))
            {
                sbStr.append(dataLine);
                sbStr.append("/r/n");
            }

            ins.close();
        }
        catch (Exception e)
        {
          //  LogMan.error("read Text File Error", e);
        }

        return sbStr.toString();
    }

    /**
     * 去掉左右空格后字符串是否为空
     * @param astr String
     * @return boolean
     */
    public static boolean isTrimEmpty(String astr)
    {
        if ((null == astr) || (astr.length() == 0))
        {
            return true;
        }
        if (isBlank(astr.trim()))
        {
            return true;
        }
        return false;
    }

    /**
     * 字符串是否为空:null或者长度为0.
     * @param astr 源字符串.
     * @return boolean
     */
    public static boolean isBlank(String astr)
    {
        if ((null == astr) || (astr.length() == 0))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}