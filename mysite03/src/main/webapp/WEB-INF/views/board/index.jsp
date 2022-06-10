<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<!-- 검색기능 -->
				<form id="search_form" action='${pageContext.request.contextPath }/board' method="get">
					<input type="text" id="kwd" name="kwd" value=""> 
					<input type="submit" value="찾기">
				</form>
				<!-- 검색기능 끝 -->
				
				<!-- 게시글 테이블 -->
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:set var='count' value='${fn:length(list) }' />
					<c:set var='newline' value='\n' />
					<c:forEach items='${list }' var='vo' varStatus='status'>					
					<tr>
						<td>${count - status.index }</td>
								<c:if test="${vo.o_no eq 1 }">									
									<td style="text-align:left; padding-left:0px">
										<a href="${pageContext.request.contextPath }/board/view?no=${vo.no }">${vo.title }</a>
									</td>
								</c:if>
								<c:if test="${vo.o_no > 1  && vo.depth eq 1}">
									<td style="text-align:left; padding-left:10px">
										<img src='${pageContext.servletContext.contextPath }/assets/images/reply.png' />
										<a href="${pageContext.request.contextPath }/board/view?no=${vo.no }">${vo.title }</a>
									</td>
								</c:if>
								<c:if test='${vo.depth > 1 }'>
									<td  style="text-align:left; padding-left:20px">
										<img src='${pageContext.servletContext.contextPath }/assets/images/reply.png' />
										<a href="${pageContext.request.contextPath }/board/view?no=${vo.no }">${vo.title }</a>
									</td>
								</c:if>
							<td>${vo.user_name }</td>
							<td>${vo.hit }</td>
							<td>${vo.reg_date }</td>
							<td>
							 	<c:if test="${vo.user_no eq authUser.no }">
							 		<a href="${pageContext.request.contextPath }/board/delete?no=${vo.no }" class="del">삭제</a>
							 	</c:if>		
							</td>
					</tr>					
					</c:forEach>			
				</table>
				<!-- 게시글 테이블 끝 -->
				
				<!-- pager 추가 -->
				<div class="pager" >
					<ul>
					<c:if test="${currentPage != 1 }">
						<li><a href="${pageContext.servletContext.contextPath }/board?p=${currentPage - 1 }">◀</a></li>
					</c:if>
					<c:forEach var='page' begin='${startPage }' end='${lastPage }'>
						<c:if test="${page == currentPage }">
							<li class ="selected">${page }</li>
						</c:if>
						<c:if test="${page <= count && page ne currentPage }">
							<li><a href="${pageContext.servletContext.contextPath }/board?p=${page }">${page }</a></li>
						</c:if>
						<c:if test="${page > count && count < pages.lastPage }">
							${page }
						</c:if>
					</c:forEach>
					<c:if test="${currentPage < count }">
						<li><a href="${pageContext.servletContext.contextPath }/board?p=${currentPage + 1 }">▶</a></li>
					</c:if>
					
						
					</ul>
				</div>					
				<!-- pager 추가 끗 -->				
				<div class="bottom">				
					<c:choose>
						<c:when test="${empty authUser }">
						</c:when>
						<c:otherwise>						
							<a href="${pageContext.request.contextPath }/board/writeform" id="new-book">글쓰기</a>
						</c:otherwise>
					</c:choose>					
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>