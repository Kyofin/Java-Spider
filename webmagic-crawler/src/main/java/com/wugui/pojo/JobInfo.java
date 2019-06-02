package com.wugui.pojo;

import lombok.Data;

import javax.persistence.*;
@Entity
@Data
public class JobInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String companyName;
	private String companyAddr;
	@Column(columnDefinition = "text")
	private String companyInfo;
	private String jobName;
	private String jobAddr;
	@Column(columnDefinition = "text")
	private String jobInfo;
	private Integer salaryMin;
	private Integer salaryMax;
	private String url;
	private String time;

	@Override
	public String toString() {
		return "JobInfo [id=" + id + ", companyName=" + companyName + ", companyAddr=" + companyAddr + ", companyInfo="
				+ companyInfo + ", jobName=" + jobName + ", jobAddr=" + jobAddr + ", jobInfo=" + jobInfo
				+ ", salaryMin=" + salaryMin + ", salaryMax=" + salaryMax + ", url=" + url + ", time=" + time + "]";
	}
	
	
}
