package com.wugui.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Data
@Accessors(chain = true)
public class GithubRepo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String repoAuthor;
	private String repoName;
	/**
	 * 定义数据库列字段类型为text
	 */
	@Column(columnDefinition = "text")
	private String repoReadme;
	private String url;

	
	
}
