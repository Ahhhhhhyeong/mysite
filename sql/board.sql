-- board 
desc board;

select * from user;

-- INSERT INTO
--  board (title, contents, hit, reg_date, g_no, o_no, depth, user_no)
--  select 'asd', 'asdg', 0, now(), MAX(g_no) + 1 , 1, 0, 5 
--  from board;

select a.no, a.title, a.contents, a.hit, 
a.reg_date, a.g_no, a.o_no, a.depth, b.name, a.user_no
 from board a , user b	
 where a.user_no = b.no
 order by g_no desc, o_no asc, depth asc
 limit 1, 5;
 
 select 'asd' as title, 'asdg' as contents, MAX(g_no) + 1 as g_no, max(o_no)+1 as o_no 
 from board;
 
--  DELETE from board 
--  where no = 2;

UPDATE board SET
 title = 'pie 수정' 
 , contents = 'pie 글쓰기 수정'
 WHERE no = 4;
 
 
 select a.no, a.title, a.contents, a.hit, 
a.reg_date, a.g_no, a.o_no, a.depth, b.name, a.user_no
 from board a , user b	
 where a.user_no = b.no
 order by g_no desc, o_no asc, depth asc;
 limit 1, 5;
 