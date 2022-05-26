-- UserRepository

desc user;

insert 
into user 
values (null, '관리자', 'admin@mysite.com', '1234', 'female',now());

select * from user;

select * from guestbook;

SELECT  
  @ROWNUM := @ROWNUM + 1 AS ROWNUM, 
a.name, a.password, a.message, date_format(a.regdate, '%Y-%m-%d') as regdate, a.no 
FROM (SELECT * FROM guestbook g order by g.no desc) a, 
(SELECT @ROWNUM :=0) as b
order by @ROWNUM desc;

select name, password, message, regdate, no
 from guestbook;