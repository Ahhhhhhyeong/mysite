-- UserRepository
desc user;

-- join
insert 
into user 
values (null, '관리자', 'admin@mysite.com', '1234', 'female',now());

select * from user;

-- login
SELECT 
	no, name
FROM
    user
WHERE
    email = 'admin@mysite.com'
        AND password = '1234';
 

 
 -- findByNo
 SELECT 
	no, name, email, gender
FROM
    user
WHERE
    email = 'admin@mysite.com'
        AND password = '1234';
        
 SELECT 
	no, name, email, gender
FROM
    user
WHERE
   no = 5;
        
        
        
UPDATE user 
SET 
    name = '김가나',
    gender = 'female',
    password = '1234'
WHERE
    email = '';

        

-- GuestbookRepository
select * from guestbook;

SELECT  
  @ROWNUM := @ROWNUM + 1 AS ROWNUM, 
a.name, a.password, a.message, date_format(a.regdate, '%Y-%m-%d') as regdate, a.no 
FROM (SELECT * FROM guestbook g order by g.no desc) a, 
(SELECT @ROWNUM :=0) as b
order by @ROWNUM desc;

select name, password, message, regdate, no
 from guestbook;
