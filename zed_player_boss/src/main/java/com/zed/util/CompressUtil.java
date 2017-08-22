/**
 * @Copyright:   Copyright(c) 2017 ZED Technologies Co, LTD. All Rights Reserved.
 * @Description: 压缩工具包
 * @Version:     1.0
 *
 * @author:      Eric
 * @Date:        2017年4月13日
 *
 * @Modification History:
 *	[Editor] [date] [remark]
 *
 * @Review History:
 *	[Reviewer] [date] [remark]
 *
 */
package com.zed.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

/**
 * @author: Eric
 * @date:   2017年4月13日
 */
public final class CompressUtil {

    /**
     * Creates a new instance of CompressUtil. 
     */
    private CompressUtil() {
        
    }
    
    /**
     * doCompress:压缩zip
     * @author: Eric 
     * @param file 文件
     * @param fileName 文件名
     * @param charset 字符集编码
     * @return
     */
    public static byte[] doCompress(File file, String fileName, String charset) {
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            doCompress(new File[] { file }, new String[] { fileName }, charset, bos);
            return bos.toByteArray();
        } finally {
            IOUtils.closeQuietly(bos);
        }
    }
    
    /**
     * doCompress:压缩zip
     * @author: Eric 
     * @param file 文件
     * @param fileName 文件名
     * @param charset 字符集编码
     * @param outputPath
     */
    public static void doCompress(File file, String fileName, String charset, String outputPath) {
        OutputStream os = null;
        try {
            File root = new File(outputPath);
            if (!root.getParentFile().exists()) {
                root.getParentFile().mkdirs();
            }
            os = new FileOutputStream(root);
            doCompress(new File[] { file }, new String[] { fileName }, charset, os);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(os);
        }
    }
    
    /**
     * doCompress:压缩zip
     * @author: Eric 
     * @param files 文件数组
     * @param fileNames 文件名数组
     * @param charset 字符集编码
     * @param outputPath
     */
    public static void doCompress(File[] files, String[] fileNames, String charset, String outputPath) {
        OutputStream os = null;
        try {
            File root = new File(outputPath);
            if (!root.getParentFile().exists()) {
                root.getParentFile().mkdirs();
            }
            os = new FileOutputStream(root);
            doCompress(files, fileNames, charset, os);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(os);
        }
    }
    
    /**
     * doCompress:压缩zip
     * @author: Eric 
     * @param files 文件数组
     * @param fileNames 文件名数组（对被压缩的文件进行重命名）
     * @param charset 字符集编码
     * @param outputPath 输出路径
     */
    public static void doCompress(File[] files, String[] fileNames, String charset, OutputStream outputStream) {
        boolean enableRename = fileNames != null && fileNames.length == files.length;
        ZipArchiveOutputStream out = null;
        try {
            out = (ZipArchiveOutputStream) new ArchiveStreamFactory(charset).createArchiveOutputStream(ArchiveStreamFactory.ZIP, outputStream);
            out.setUseZip64(Zip64Mode.AsNeeded);
            ZipArchiveEntry zipArchiveEntry = null;
            InputStream is = null;
            for (int i = 0; i < files.length; i++) {
                try {
                    zipArchiveEntry = new ZipArchiveEntry(files[i], enableRename ? (fileNames[i] == null || fileNames[i].trim().length() == 0 ? files[i].getName() : fileNames[i]) : files[i].getName());
                    out.putArchiveEntry(zipArchiveEntry);
                    is = new BufferedInputStream(new FileInputStream(files[i]));
                    IOUtils.copy(is, out);
                    out.closeArchiveEntry();
                } finally {
                    IOUtils.closeQuietly(is);
                }
            }
            out.finish();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

}
