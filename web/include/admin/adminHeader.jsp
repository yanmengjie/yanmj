<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt' %>   
    
<html>
<head>
	
	<script src="js/jquery/2.0.0/jquery.min.js"></script>
	<link href="css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
	<script src="js/bootstrap/3.3.6/bootstrap.min.js"></script>
	<link href="css/back/style.css" rel="stylesheet">

<script>
        	//是否为空
        	function checkEmpty(id,name){
        		var value=$("#"+id).val();//获取id的内容
        		if(value.length==0){
        			alert(name+"不能为空");
        			$("#"+id)[0].focus();
        			return false;
        			
        		}
        		return true;
        	}
        	
        	//是否为数字
        	function checkNumber(id,name){
        		var value=$("#"+id).val();
        		if(value.length==0){
        			alert(name+"不能为空");
        			$("#"+id)[0].focus();
        			return false;
        			
        		}
        		//NaN代表非数字值的特殊值
        		if(isNaN(value)){
        			alert(name+"必须是数字");
        			$("#"+id).focus();
        			return false;
        		
        		}
        		return true;
        	
        	}
        	//是否为整数
        	function checkInt(id,name){
        		var value=$("#"+id).val();
        		if(value.length==0){
        			alert(name+"不能为空");
        			$("#"+id)[0].focus();
        			return false;
        			
        		}
        		//parseInt() 函数可解析一个字符串，并返回一个整数。
        		if(parseInt(value)!=value){
        			alert(name+"必须是整数");
        			$("#"+id)[0].focus();
        			return false;
        			
        		}
        		return true;    		
  	
        	}
        	//确认删除
        	$(function(){
        		$("a").click(function(){
        			var deleteLink=$(this).attr("deleteLink");
        			console.log(deleteLink);
        			if("true"==deleteLink){
        				var confirmDelete=confirm("确认要删除");
        				if(confirmDelete)
        					return true;
        				return false;		
        				
        			}
        	
        		});
       	})
   </script>

</head>
<body>
