package com.ruoyi.common.utils.file;

import java.io.*;

import com.aspose.words.Document;
import com.ruoyi.common.utils.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;

import javax.xml.parsers.ParserConfigurationException;

/**
 * 文档转HTML工具类
 * 支持docx、pdf等格式转换为HTML用于在线浏览
 */
public class DocumentToHtmlConverter {

    /**
     * 将文档文件转换为HTML
     * 
     * @param filePath 文件路径
     * @param fileType 文件类型 (docx, doc, pdf)
     * @return HTML内容
     * @throws Exception 转换异常
     */
    public static String convertToHtml(String filePath, String fileType) throws Exception {
        if (StringUtils.isEmpty(fileType)) {
            throw new IllegalArgumentException("文件类型不能为空");
        }
        
        switch (fileType.toLowerCase()) {
            case "docx":
                return convertDocxToHtml(filePath);
            case "doc":
                return convertDocxToHtml(filePath);
            case "pdf":
                return pdfToHtmlStr(filePath);
            default:
                throw new IllegalArgumentException("不支持的文件类型: " + fileType);
        }
    }
    
    /**
     * 将DOCX文件转换为HTML
     * 
     * @param filePath 文件路径
     * @return HTML内容
     * @throws IOException IO异常
     */
    private static String convertDocxToHtml(String filePath) throws IOException {
        try {
            Document doc = new Document(filePath); // Address是将要被转化的word文档
            String htmlStr = doc.toString();
            return htmlStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String pdfToHtmlStr(String pdfPath) throws IOException, ParserConfigurationException {
        PDDocument document = PDDocument.load(new File(pdfPath));
        Writer writer = new StringWriter();
        new PDFDomTree().writeText(document, writer);
        writer.close();
        document.close();
        return writer.toString();
    }
}