package com.zed.domain.player.video;

import com.zed.domain.common.base.BaseModel;

public class PlayerVideo extends BaseModel{
	private static final long serialVersionUID = 1L;

	private String resId;
    private String keyword;
    private String type;						//FTP,HTTP,TORRENT
    private String star;
    private String director;
    private String name;
    private String score;
    private String year;
    private Short status;						//状态：0:禁用 1:启用
    private Long duration;
    private String imgUrl;
    private Short origin;						//资源来源：1后台添加,2后台爬取,3用户上传
    private String hisId;						//下载历史ID
    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId == null ? null : resId.trim();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Short getOrigin() {
		return origin;
	}

	public void setOrigin(Short origin) {
		this.origin = origin;
	}

	public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star == null ? null : star.trim();
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director == null ? null : director.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score == null ? null : score.trim();
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

	public String getHisId() {
		return hisId;
	}

	public void setHisId(String hisId) {
		this.hisId = hisId;
	}

	@Override
	public String toString() {
		return "PlayerVideo [resId=" + resId + ", keyword=" + keyword + ", type=" + type + ", star=" + star
				+ ", director=" + director + ", name=" + name + ", score=" + score + ", year=" + year + ", status="
				+ status + ", duration=" + duration + ", imgUrl=" + imgUrl + ", origin=" + origin + ", hisId=" + hisId
				+ "]";
	}
	
}