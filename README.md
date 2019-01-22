
## 多人博客系统
#### 基于Springmvc、Hibernate、Spring、EasyUI、Mysql、ElasticSearch实现

### **项目介绍**

>1. 使用Maven3+Spring3+Springmvc+Hibernate3架构；数据库使用Mysql；
>2. 自定义前端页面实现博客的分页显示，博客分类，文章归类显示；完成用户评论等功能；
>3. 使用EasyUI实现后台对博客、博客类别、用户评论的管理，包括增删改查等；
>4. 使用ECharts作为统计图展现，并且自己封装了Echarts，使得方便快捷使用。项目地址：https://github.com/leanfish2011/MyEcharts；
>5. 使用kindeditor编辑器实现写博客功能等；
>6. 自己写了爬虫，爬取其他网站信息加入到自己的博客中。通过博客网站登录，可以设置关键词，后台自动进行抓取。项目地址：https://github.com/leanfish2011/Crawler
>7. 使用ElasticSearch实现全文检索。可以实现标题和文章内容关键词搜索。MySQL中博客和ElasticSearch中同步。

### **一. 前台效果展示**
#### **1. 博客主页显示**
![博客主页显示](http://images.cnblogs.com/cnblogs_com/yangtze-yufei/860899/o_newindex.png)
#### **2. 博客内容显示**
![博客内容显示](http://images.cnblogs.com/cnblogs_com/yangtze-yufei/860899/o_%e6%96%87%e7%ab%a0%e5%86%85%e5%ae%b9.png)
#### **3. 全文检索**
![全文检索显示](http://images.cnblogs.com/cnblogs_com/yangtze-yufei/860899/o_%e5%85%a8%e6%96%87%e6%a3%80%e7%b4%a2.png)

### **二. 后台效果展示**
#### **1. 登录**
![登录](http://images.cnblogs.com/cnblogs_com/yangtze-yufei/860899/o_%e7%99%bb%e5%bd%95.png)
#### **2. 后台主页**
![后台主页](http://images.cnblogs.com/cnblogs_com/yangtze-yufei/860899/o_%e5%90%8e%e5%8f%b0%e4%b8%bb%e9%a1%b5.png)
#### **3. 写博客功能**
![写博客功能](http://images.cnblogs.com/cnblogs_com/yangtze-yufei/860899/o_%e6%96%b0%e5%bb%ba%e5%8d%9a%e5%ae%a2.png)
#### **4. 博客管理**
![博客管理](http://images.cnblogs.com/cnblogs_com/yangtze-yufei/860899/o_%e5%8d%9a%e5%ae%a2%e5%86%85%e5%ae%b9%e7%ae%a1%e7%90%86.png)
#### **5. 添加博客类别等等**
![博客类别](http://images.cnblogs.com/cnblogs_com/yangtze-yufei/860899/o_%e5%8d%9a%e5%ae%a2%e7%b1%bb%e5%88%ab%e7%ae%a1%e7%90%86.png)

### **三. 爬虫**
![爬虫主页](http://images.cnblogs.com/cnblogs_com/yangtze-yufei/860899/o_Blog%20Crawler_005.png)

### **Todo**
>1. 加强和优化ElasticSearch作为博客网站的搜索引擎；
>2. 加强爬虫功能，目前仅能抓取博客园内容，后续实现能抓取任意网站内容；
>3. 加强权限控制；
>4. 加强图表统计功能。


欢迎大家star，follow。

#### **欢迎访问我的博客园：https://www.cnblogs.com/leanfish/**
