/**
 * @Copyright:   Copyright(c) 2017 ZED Technologies Co, LTD. All Rights Reserved.
 * @Description: TODO(用一句话描述该文件做什么)
 * @Version:     1.0
 *
 * @author:      Eric
 * @Date:        2017年3月29日
 *
 * @Modification History:
 *	[Editor] [date] [remark]
 *
 * @Review History:
 *	[Reviewer] [date] [remark]
 *
 */
package com.zed.config;

/**
 * @author: Eric
 * @date:   2017年3月29日
 */
public class SubtitleConfig {
    
    public static final String OTHER_FOLDER = "other";
    
    public static final String ACCEPT_SUBTITLE_FORMAT = "srt";
    
    private String bucketName;
    
    private String subtitleRootPath;
    /**
     * 字幕文件压缩临时存储路径
     */
    private String subtitleCompressTmpPath;

    /**
     * Creates a new instance of SubtitleConfig. 
     */
    public SubtitleConfig() {
        
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getSubtitleRootPath() {
        return subtitleRootPath;
    }

    public void setSubtitleRootPath(String subtitleRootPath) {
        this.subtitleRootPath = subtitleRootPath;
    }

    public String getSubtitleCompressTmpPath() {
        return subtitleCompressTmpPath;
    }

    public void setSubtitleCompressTmpPath(String subtitleCompressTmpPath) {
        this.subtitleCompressTmpPath = subtitleCompressTmpPath;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"bucketName\":\"");
        builder.append(bucketName);
        builder.append("\", \"subtitleRootPath\":\"");
        builder.append(subtitleRootPath);
        builder.append("\", \"subtitleCompressTmpPath\":\"");
        builder.append(subtitleCompressTmpPath);
        builder.append("\"}");
        return builder.toString();
    }

}
