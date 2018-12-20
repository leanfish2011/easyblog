-- 1、创建库
drop database if exists eblog; -- 直接删除数据库，不提醒
create database eblog; -- 创建数据库 
use eblog; -- 选择数据库


-- 2、创建表
--
-- Table structure for table `bll_article`
--
DROP TABLE IF EXISTS `bll_article`;
CREATE TABLE `bll_article` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `TypeID` varchar(60) DEFAULT NULL COMMENT '文章所属类型ID',
  `TypeName` varchar(50) DEFAULT NULL COMMENT '文章所属类型名称',
  `Title` varchar(50) DEFAULT NULL COMMENT '文章标题',
  `Content` varchar(10000) DEFAULT NULL COMMENT '文章内容',
  `ComCount` int(11) DEFAULT '0' COMMENT '文章被评论条数',
  `ReadCount` int(11) DEFAULT '0' COMMENT '文章被阅读条数',
  `SuggestCount` int(11) DEFAULT '0' COMMENT '文章被推荐次数',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户发表的文章';


--
-- Table structure for table `bll_articletype`
--
DROP TABLE IF EXISTS `bll_articletype`;
CREATE TABLE `bll_articletype` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `TypeName` varchar(50) DEFAULT NULL COMMENT '文章类型名称',
  `Description` varchar(50) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章类型';


--
-- Table structure for table `bll_attachment`
--
DROP TABLE IF EXISTS `bll_attachment`;
CREATE TABLE `bll_attachment` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `AttName` varchar(50) DEFAULT NULL COMMENT '附件名称',
  `AttPath` varchar(50) DEFAULT NULL COMMENT '附件路径',
  `ArticleID` varchar(60) DEFAULT NULL COMMENT '附件关联的文章标题',
  `AttSize` int(11) DEFAULT NULL COMMENT '附件大小',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章的附件信息';


--
-- Table structure for table `bll_commont`
--
DROP TABLE IF EXISTS `bll_commont`;
CREATE TABLE `bll_commont` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `ArticleID` varchar(60) DEFAULT NULL COMMENT '被评论的文章ID',
  `ArticleTitle` varchar(50) DEFAULT NULL COMMENT '被评论的文章标题',
  `ComContent` varchar(2000) DEFAULT NULL COMMENT '评论内容',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='对文章的评论信息';


--
-- Table structure for table `bll_crawltask`
--
DROP TABLE IF EXISTS `bll_crawltask`;
CREATE TABLE `bll_crawltask` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `CrawlURL` varchar(100) DEFAULT NULL COMMENT '抓取的链接',
  `KeyWords` varchar(100) DEFAULT NULL COMMENT '待抓取的关键词',
  `State` int(11) DEFAULT NULL COMMENT '0：创建；1：执行中；2：执行成功；3：执行失败。',
  `FinishTime` datetime DEFAULT NULL COMMENT '抓取任务完成时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='抓取任务';


--
-- Table structure for table `bll_favarticle`
--
DROP TABLE IF EXISTS `bll_favarticle`;
CREATE TABLE `bll_favarticle` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `ArticleTitle` varchar(50) DEFAULT NULL COMMENT '文章标题',
  `Describle` varchar(500) DEFAULT NULL COMMENT '描述',
  `ArticleURL` varchar(200) DEFAULT NULL COMMENT '文章链接',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='关注的文章';


--
-- Table structure for table `bll_favuser`
--
DROP TABLE IF EXISTS `bll_favuser`;
CREATE TABLE `bll_favuser` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `FavUser` varchar(60) DEFAULT NULL COMMENT '被关注的人ID',
  `Describle` varchar(500) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='关注的博主';


--
-- Table structure for table `bll_pageinfo`
--
DROP TABLE IF EXISTS `bll_pageinfo`;
CREATE TABLE `bll_pageinfo` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `URL` varchar(100) DEFAULT NULL  COMMENT '抓取的文章地址',
  `Title` varchar(100) DEFAULT NULL COMMENT '抓取的文章标题',
  `PostTime` varchar(60) DEFAULT NULL COMMENT '抓取的文章发表时间',
  `Content` text COMMENT '抓取的文章内容',
  `Author` varchar(45) DEFAULT NULL COMMENT '抓取的文章作者',
  `AuthorPage` varchar(60) DEFAULT NULL COMMENT '抓取的文章作者的主页',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='抓取的文章';


--
-- Table structure for table `bll_suggest`
--
DROP TABLE IF EXISTS `bll_suggest`;
CREATE TABLE `bll_suggest` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `ArticleID` varchar(60) DEFAULT NULL COMMENT '推荐文章的ID',
  `ArticleTitle` varchar(50) DEFAULT NULL COMMENT '推荐的文章标题',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='推荐的文章';



--
-- Table structure for table `sys_menu`
--
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `MenuName` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `URL` varchar(100) DEFAULT NULL COMMENT '菜单地址',
  `ParentID` varchar(60) DEFAULT NULL COMMENT '父级菜单id',
  `Index` int(11) DEFAULT NULL COMMENT '菜单顺序',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统菜单';


--
-- Table structure for table `sys_role`
--
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `RoleName` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `Remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';


--
-- Table structure for table `sys_roleauth`
--
DROP TABLE IF EXISTS `sys_roleauth`;
CREATE TABLE `sys_roleauth` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `RoleId` varchar(60) DEFAULT NULL COMMENT '角色ID',
  `MenuId` varchar(60) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单';


--
-- Table structure for table `sys_userrole`
--
DROP TABLE IF EXISTS `sys_userrole`;
CREATE TABLE `sys_userrole` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `UserId` varchar(60) DEFAULT NULL COMMENT '用户ID',
  `RoleId` varchar(60) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色用户';


--
-- Table structure for table `sys_user`
--
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `ID` varchar(60) NOT NULL COMMENT '主键',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `Creator` varchar(60) NOT NULL COMMENT '创建人ID',
  `ModifyTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `Modifier` varchar(60) DEFAULT NULL COMMENT '修改人ID',
  `UserCode` varchar(50) DEFAULT NULL COMMENT '登录名',
  `UserPassword` varchar(50) DEFAULT NULL COMMENT '登录密码',
  `UserName` varchar(50) DEFAULT NULL COMMENT '用户名称',
  `Email` varchar(50) DEFAULT NULL COMMENT '用户邮箱',
  `PhotoPath` varchar(200) DEFAULT NULL COMMENT '用户图片存放路径。',
  `PhotoFingerPrint` varchar(45) DEFAULT NULL COMMENT '用户头像指纹码',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户表';


-- 3、创建触发器
-- (1)评论表增加一条记录，则文章表对应记录的评论数增加1
create trigger addComCount 
after insert on bll_commont
for each row
	update bll_article set ComCount=ComCount+1 where id=new.ArticleID;

	
-- (2)评论删除一条记录，则文章表对应记录的评论数减去1
create trigger plusComCount 
after delete on bll_commont
for each row
	update bll_article set ComCount=ComCount-1 where id=old.ArticleID;
	
	
-- (3)新增推荐，则文章对应的推荐数量加1
create trigger addSuggestCount 
after insert on bll_suggest
for each row
	update bll_article set SuggestCount=SuggestCount+1 where id=new.ArticleID;

	
-- (4)触发器：推荐删除一条记录，则文章表对应记录的推荐数减去1
create trigger plusSuggestCount 
after delete on bll_suggest
for each row
	update bll_article set SuggestCount=SuggestCount-1 where id=old.ArticleID;
	

-- 4、初始化数据
-- 用户
INSERT INTO `eblog`.`sys_user`(`ID`,`Creator`,`UserCode`,`UserPassword`,`UserName`,`Email`) VALUES ('0','0','admin','e10adc3949ba59abbe56e057f20f883e','系统管理员','yufeijob@163.com');
INSERT INTO `eblog`.`sys_user`(`ID`,`Creator`,`UserCode`,`UserPassword`,`UserName`,`Email`) VALUES ('1','0','user','e10adc3949ba59abbe56e057f20f883e','使用人','yangtze_yufei@163.com');


-- 角色
INSERT INTO `eblog`.`sys_role`(`ID`,`Creator`,`RoleName`,`Remark`) VALUES ('0','0','系统管理员组','系统预置角色');
INSERT INTO `eblog`.`sys_role`(`ID`,`Creator`,`RoleName`,`Remark`) VALUES ('1','0','使用人组','系统预置角色');


-- 用户角色
INSERT INTO `eblog`.`sys_userrole`(`ID`,`Creator`,`UserId`,`RoleId`) VALUES ('0','0','0','0');
INSERT INTO `eblog`.`sys_userrole`(`ID`,`Creator`,`UserId`,`RoleId`) VALUES ('1','0','1','1');


-- 菜单
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`Index`) VALUES('0','0','菜单',0);

	-- 一级菜单
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`ParentID`,`Index`) VALUES('1','0','博客管理','0',1);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`ParentID`,`Index`) VALUES('2','0','收藏管理','0',2);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`ParentID`,`Index`) VALUES('3','0','新闻抓取管理','0',3);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`ParentID`,`Index`) VALUES('4','0','系统管理','0',4);

		-- 二级菜单-博客管理
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('5','0','博客类别管理','/admin/blogType/index.do','1',5);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('6','0','博客内容管理','/admin/blogInfo/index.do','1',6);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('7','0','新建博客','/admin/blogInfo/add.do','1',7);

		-- 二级菜单-收藏管理
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('8','0','我的关注','/admin/favoriteArticle/index.do','2',8);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('9','0','我的评论','/admin/comment/index.do','2',9);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('10','0','我的推荐','/admin/suggest/index.do','2',10);

		-- 二级菜单-新闻抓取管理
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('11','0','抓取设置','/admin/crawlerTask/index.do','3',11);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('12','0','我的抓取','/admin/crawlerNews/index.do','3',12);

		-- 二级菜单-系统管理
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('13','0','用户管理','/admin/user/index.do','4',13);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('14','0','角色管理','/admin/role/index.do','4',14);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('15','0','用户角色管理','/admin/roleUser/index.do','4',15);
INSERT INTO `eblog`.`sys_menu`(`ID`,`Creator`,`MenuName`,`URL`,`ParentID`,`Index`) VALUES('16','0','角色权限管理','/admin/roleAuth/index.do','4',16);


-- 角色权限
	-- 系统管理员权限
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('0','0','0','0');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('1','0','0','1');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('2','0','0','2');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('3','0','0','3');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('4','0','0','4');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('5','0','0','5');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('6','0','0','6');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('7','0','0','7');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('8','0','0','8');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('9','0','0','9');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('10','0','0','10');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('11','0','0','11');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('12','0','0','12');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('13','0','0','13');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('14','0','0','14');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('15','0','0','15');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('16','0','0','16');

	-- 普通博客使用者
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('17','0','1','0');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('18','0','1','1');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('19','0','1','2');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('20','0','1','3');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('21','0','1','5');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('22','0','1','6');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('23','0','1','7');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('24','0','1','8');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('25','0','1','9');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('26','0','1','10');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('27','0','1','11');
INSERT INTO `eblog`.`sys_roleauth`(`ID`,`Creator`,`RoleId`,`MenuId`) VALUES ('28','0','1','12');

	
	
	
	
	
	
	
	
	
	

