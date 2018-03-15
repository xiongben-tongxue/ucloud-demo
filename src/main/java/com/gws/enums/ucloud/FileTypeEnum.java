package com.gws.enums.ucloud;

/**
 * 
 * 【文件类型模块】
 * @author yangjh
 */
public enum FileTypeEnum {
	GAME(1, "file", "图片"),
	;
	private Integer code;
	private String prefix;
	private String desc;

	FileTypeEnum(Integer code, String prefix , String desc){
	    this.code = code;
	    this.prefix = prefix;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getDesc() {
		return desc;
	}
}
