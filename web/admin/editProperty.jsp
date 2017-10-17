<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>   
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<title>编辑属性</title>

<div class="workingArea">
	<ol class="breadcrumb">
		<li><a href="admin_category_list">所有分类</a></li>
		<li><a href="admin_property_list?cid=${p.category.id }">${p.category.name }</a></li>
		<li class="active">编辑属性</li>
	</ol>
	
	<div class="panel panel-warning editDiv">
		<div class="panel-heading">编辑分类</div>
		<div class="panel-body">
			<form action="admin_property_update" method="post" id="editForm">
				<table class="editTable">
					<tr>
						<td>属性名称</td>
						<td><input type="text"  value="${p.name }" id="name" name="name"  class="form-control"/> </td>
					</tr>
					<tr class="submitTR">
							<td colspan="2" align="center">
								<input type="hidden" name="id" value="${p.id}"/>
								<input type="hidden" name="cid" value="${p.category.id }"/>
								<button type="submit" class="btn btn-success">提交</button>		
							</td>	
						</tr>	
				</table>	
			</form>
		</div>
	</div>
</div>