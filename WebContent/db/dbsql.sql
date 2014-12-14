--用户表
create table users (
	userId int primary key,
	userName varchar(32) unique not null,
	password varchar(32) not null,
	name varchar(20) not null,
	sex char(3) null,
	mobile varchar(11),
	email varchar(32),
	registerDate date not null,
	lastLoginDate date not null,
	onlines int default 0,
	entrance varchar(10)
);

--项目表
create table project (
	projectId int primary key,
	projectName varchar(60) not null,
	userId int not null,
	createDate date not null,
	memo varchar(4000)
);

--用户与项目的关系表
create table projectUser (
	upid int primary key,
	projectId int not null,
	userId int unique not null
);

--okr任务指标表
create table okr (
	okrId int primary key,
	okrName varchar(100) not null,
	parentOkrId int,
	projectId int not null,
	userId int not null,
	createDate date not null,
	appointor int,
	expirationTime date,
	status int default 1,
	score int default 1,
	complexity int,
	memo varchar(4000)
);

--消息表
create table message (
	msgId int primary key,
	userId int not null,
	sendDate date not null,
	genre int,
	okrId int,
	status int,
	memo varchar(4000)
);

--日志表
create table log (
	logId int primary key,
	userId int not null,
	logDate date not null,
	genre varchar(50) not null,
	userIp varchar(16),
	content varchar(4000)
);