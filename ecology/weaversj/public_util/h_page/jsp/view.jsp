<%@ page import="weaver.general.Util"%>
<%@ page import="weaver.conn.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<%@ taglib uri="/WEB-INF/weaver.tld" prefix="wea"%>

<html>

<head>
<meta charset="utf-8">
<title></title>
<link href="/weaversj/public_ui/layui/css/layui.css" type="text/css"
	rel="stylesheet">

<style>

</style>

</head>

<BODY>
	 
<form class="layui-form" action="" lay-filter="example">
  <div class="layui-form-item">
    <label class="layui-form-label">单行输入框</label>
    <div class="layui-input-block">
      <input type="text" name="title" lay-verify="title" autocomplete="off" placeholder="请输入标题" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">单行选择框</label>
    <div class="layui-input-block">
      <select name="interest" lay-filter="aihao">
        <option value=""></option>
        <option value="0">写作</option>
        <option value="1" selected="">阅读</option>
        <option value="2">游戏</option>
        <option value="3">音乐</option>
        <option value="4">旅行</option>
      </select>
    </div>
  </div>

  
</form>
	
</body>
<script language="Javascript"
	src="/weaversj/public_ui/js/jquery-1.8.3.min.js"></script>
<script language="Javascript" src="/weaversj/public_ui/layui/layui.js"></script>


<script LANGUAGE="javascript">
	
	$(function() {
		
	});//初始化完成
</script>
<script>
layui.use(['form'], function(){
  var form = layui.form
  ,layer = layui.layer;
  
 
  //自定义验证规则
  form.verify({
    title: function(value){
      if(value.length < 5){
        return '标题至少得5个字符啊';
      }
    }
    ,pass: [
      /^[\S]{6,12}$/
      ,'密码必须6到12位，且不能出现空格'
    ] 
  });
  
  
  //监听提交
  form.on('submit(demo1)', function(data){
    layer.alert(JSON.stringify(data.field), {
      title: '最终的提交信息'
    })
    return false;
  });
  
  //表单取值
  layui.$('#LAY-component-form-getval').on('click', function(){
    var data = form.val('example');
    alert(JSON.stringify(data));
  });
  
});

</script>

</html>

