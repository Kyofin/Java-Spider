package com.wugui.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@Accessors(chain = true)
public class GithubRepo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String repoAuthor;
	private String repoName;
	private String repoReadme;
	private String url;

	
	
}
