## 项目结构

├── Readme.md
├── jingdong-crawler   	**使用httpclient进行原生爬虫**🕷
│   ├── db
│   ├── jingdong-crawler.iml
│   ├── pom.xml
│   ├── src
│   └── target
└── webmagic-crawler  	**使用webmagic框架进行爬虫**🕷
    ├── db
    ├── pom.xml
    ├── src
    ├── target
    └── webmagic-crawler.iml



## 目标

* [x] csdn文章爬虫

  ![](https://raw.githubusercontent.com/huzekang/picbed/master/20190603002441.png)

* [x] 51Job招聘信息爬虫

  ![](https://raw.githubusercontent.com/huzekang/picbed/master/20190603002121.png)

* [x] Github用户和仓库信息爬虫

  ![](https://raw.githubusercontent.com/huzekang/picbed/master/20190603001943.png)

* [x] 京东手机数据爬虫

* [ ] hita元数据服务爬虫



## IDEA启动

1. **设置idea激活的profile，指定数据库使用pg还是mysql**

![](https://raw.githubusercontent.com/huzekang/picbed/master/20190602235040.png)



2. **配置redis和选择的数据库连接**

   - redis用于分布式爬虫时记录已爬的队列和爬过的队列

     在jedis中配置即可。

     ```java
     public  void start() {
             Spider.create(new GithubRepoProcessor())
                     // 设置download解决官方webMagic无法访问部分ssl网站问题
                     .setDownloader(new HttpClientDownloader())
                     .addUrl("https://github.com/huzekang/")
                     .setScheduler(new RedisScheduler(new JedisPool("127.0.0.1",6379)))
                     .setPipelines(Lists.newArrayList(githubRepoJPAPipeline,githubUserJPAPipeline))
                     .thread(8)
                     .runAsync();
     
         }
     ```

     

   - 数据库用于持久化爬取到的数据库

   

3. **启动类Application选择要跑的任务，放开注释即可**

```java
public void run(String... args) throws Exception {
//		githubRepoProcessor.start();
//		jobProcessor.start();
//		csdnBlogPageProcessor.start();
//		hitaProcessor.start();
	}
```

