package com.wugui.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@Accessors(chain = true)
public class GithubUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String userName;
	private String userAvatar;
	private String userNickName;
	private String userProfile;
	private String userMention;
	private String userLabel;
	private String url;

	
	
}
