package com.qiuhang.shopp.entity;

import java.security.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseEntity {

	/**
	 * 主键ID
	 */
	private Long id;
	/**
	 * 创建时间
	 */
	private Timestamp     created;
	/**
	 * 修改时间
	 */
	private Timestamp     updated;


}
