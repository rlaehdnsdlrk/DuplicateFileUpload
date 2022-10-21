-- 테이블 만들기
create table product(
code varchar2(30) primary key,
description varchar2(100),
regdate date,
filename1 varchar2(50),
filename2 varchar2(50),
filename3 varchar2(50)
)

select * from product

select avg(point) as avg from opinion where code = 'test1'

create table opinion(
code varchar2(30),
point number(5,2),
opinion varchar2(200),
id varchar2(20),
regdate date default sysdate,
primary key(code, id)
)

select * from opinion

select * from opinion where code='test1' and id='hong1';

drop table opinion

create table shopmember(
id varchar2(20) primary key,
pw varchar2(40) not null,
name varchar2(40) not null
)

select * from shopmember

insert into shopmember(id,pw,name) values('hong','1234','홍길동');
insert into shopmember(id,pw,name) values('yoon','1234','윤길동');
insert into shopmember(id,pw,name) values('park','1234','박길동');
insert into shopmember(id,pw,name) values('kim','1234','김길동');
insert into shopmember(id,pw,name) values('lee','1234','이길동')

-- 테이블 삭제
drop table product

-- 레코드 삽입
insert into product(code,description,regdate) values ('a','사과입니다.',sysdate)

-- 레코드 검색
select code,description,regdate,filename1,filename2,filename3 from product

-- 레코드 삭제
delete from product