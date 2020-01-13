<%@ page import="weaver.general.Util"%>
<%@ page import="weaver.conn.*"%>
<%@ page import="weaver.general.BaseBean"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<%@ taglib uri="/WEB-INF/weaver.tld" prefix="wea"%>

<html>

<head>
<meta charset="utf-8">
<title></title>
<link href="/weaversj/public_ui/layui/css/layui.css" type="text/css"
	rel="stylesheet">
<%
String flags = Util.null2String(request.getParameter("flags"));
String dataid = Util.null2String(request.getParameter("dataid"));
weaversj.hpage.action.TypePageAction typeAction = new weaversj.hpage.action.TypePageAction();
weaversj.hpage.bean.TypeBaseBean typeBase = new weaversj.hpage.bean.TypeBaseBean();
String getJson = "";
new BaseBean().writeLog(flags);
if("view".equals(flags)){
	typeBase.setFlag(flags);
	typeBase.setIds(dataid);
	getJson = typeAction.getViewData(typeBase);
}

%>
<style>
.zDialog_div_bottom {
	position: fixed;
	padding-top: 5px !important;
	padding-bottom: 5px !important;
	background-color: #FAFAFA;
	z-index: 3;
	bottom: 0;
	width: 100%;
	border: none;
	border-top: 1px solid #dadedb;
	top: expression(eval(document.compatMode && document.compatMode ==
		'CSS1Compat')? documentElement.scrollTop+(documentElement.clientHeight-
		 this.clientHeight):document.body.scrollTop+(document.body.clientHeight-
		 this.clientHeight));
}
.select{
	margin-bottom:20px;
}
.topdiv{
	margin-top:30px;
}
.layui-form-item{
	margin-left:150px;
}
.layui-btn{
	margin-left:100px !important;
	margin-top:50px;
}

</style>

</head>

<BODY>
<form class="layui-form" action="" lay-filter="example">
  <input type = "hidden"  name="dataid" id="dataid"  value = "<%=dataid%>">

  <div class="layui-form-item topdiv">
    <div class="layui-inline">
      <label class="layui-form-label">分类名称</label>
      <div class="layui-input-inline">
        <input lay-verify="t_name" type="text" name="t_name" id= "t_name" autocomplete="off" class="layui-input">
      </div>
    </div>
  </div>
  
  <div class="layui-form-item select">
	<label class="layui-form-label">来源</label>
	<div class="layui-input-inline">
	  <select name="if_table" id = "if_table"  lay-filter="if_table" >
		<option value="0">接口</option>
		<option value="1">数据表</option>
	  </select>
	</div>
  </div>
  
  <%if("view".equals(flags)){%>
	  <div class="layui-form-item select_text">
		<label class="layui-form-label">来源</label>
		<div class="layui-input-inline">
		  	<input type="text"  name="if_table_input" id = "if_table_input"  autocomplete="off" class="layui-input">
		</div>
	  </div>
  <%}%>
  <div class="layui-form-item" id='hide_1'>
    <div class="layui-inline">
      <label class="layui-form-label">接口地址</label>
      <div class="layui-input-inline">
        <input type="text" lay-verify="t_url" name="t_url" id = "t_url"  autocomplete="off" class="layui-input">
      </div>
    </div>
  </div>
  
  <div class="layui-form-item" id='hide_2'>
    <div class="layui-inline">
      <label class="layui-form-label">数据表名</label>
      <div class="layui-input-inline">
        <input type="text" lay-verify="table_name" name="table_name" id = "table_name" autocomplete="off" class="layui-input">
      </div>
    </div>
  </div>
  <div class="layui-form-item">
    <div class="layui-input-block">
	  <%if("add".equals(flags)){%>
	        <button  type="submit"  lay-filter="add" lay-submit="" class="layui-btn layui-btn-normal"  id="LAY-component-form-setval">新增</button>
	  <%}else if("view".equals(flags)){%>
	        <button type="button" class="layui-btn layui-btn-normal" data-type="edit" id="edit_button">编辑</button>
			<button type="submit" class="layui-btn layui-btn-normal" lay-filter="save" lay-submit=""  id="save_button">保存</button>
	  <%}%>
    </div>
  </div>
</form>

</body>
<script language="Javascript"
	src="/weaversj/public_ui/js/jquery-1.8.3.min.js"></script>
<script language="Javascript" src="/weaversj/public_ui/layui/layui.js"></script>


<script LANGUAGE="javascript">
	var flags = '<%=flags%>';
	$(function() {
		if("add"==flags){
			$("#hide_2").hide();
		}else if("view"==flags){
			<%if("view".equals(flags)){%>
				var json = <%=getJson%>;
				$("#t_name").val(json.data.t_name);
				$("#t_name").attr("disabled",true);
				$("#if_table").val(json.data.iftable);
				$("#t_url").val(json.data.t_url);
				$("#t_url").attr("disabled",true);
				$("#table_name").val(json.data.table_name);
				$("#table_name").attr("disabled",true);
				var getSelectText = json.data.iftable=="0"?"接口":"数据表";
				$("#if_table_input").val(getSelectText);
				$("#if_table_input").attr("disabled",true);

				if("0"==json.data.iftable){
					$("#hide_2").hide();
				}else{
					$("#hide_1").hide();
				}
				$(".select").hide();
				$("#save_button").hide();
			<%}%>
		}
	});//初始化完成
</script>
<script>
layui.use(['form'], function(){
  var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

  var form = layui.form
  ,layer = layui.layer;
  
  form.on('select(if_table)', function(data){
	  if(data.value == "0"){
		$("#hide_2").hide();
		$("#hide_1").show();
		form.val('example', {
			  "table_name": "" // "name": "value"
		});
	}else{
		$("#hide_1").hide();
		$("#hide_2").show();
		form.val('example', {
			  "t_url": "" // "name": "value"
		});	 		
	}
  });
 
  form.verify({
    t_name: function(value){
      if(value ==""){
        return '分类不能为空';
      }
    }
    ,t_url: function(value){
      if(value == "" && $("#if_table").val()=="0"){
        return '接口地址不能为空';
      }
    }
    ,table_name: function(value){
      if(value == "" && $("#if_table").val()=="1"){
        return '数据表不能为空';
      }
    }
  });
  form.on('submit(add)', function(data){
	
	jQuery.ajax({ 
		url : "/weaversj/public_util/h_page/jsp/getJson.jsp",  
		type:"post",
		dataType:"json",
		cache:false, 
		async:false, 
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		data:{
			flags:"add",
			typeName:data.field.t_name,
			types:data.field.if_table,
			tableName:data.field.table_name,
			url:data.field.t_url
		},
		success : function(jsonStr) {
			if(jsonStr.code == "0"){
				parent.layer.msg('新增成功');
				parent.layer.close(index);
			}else{
				parent.layer.msg(jsonStr.msg);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert("系统发生异常：请与管理员联系！");
		}
	});
    return false;
  });
 
  form.on('submit(save)', function(data){
	console.log(data);
	jQuery.ajax({ 
		url : "/weaversj/public_util/h_page/jsp/getJson.jsp",  
		type:"post",
		dataType:"json",
		cache:false, 
		async:false, 
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		data:{
			flags:"edit",
			dataid:data.field.dataid,
			typeName:data.field.t_name,
			types:data.field.if_table,
			tableName:data.field.table_name,
			url:data.field.t_url
		},
		success : function(jsonStr) {
			if(jsonStr.code == "0"){
				parent.layer.msg('修改成功');
				parent.layer.close(index);
			}else{
				parent.layer.msg(jsonStr.msg);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert("系统发生异常：请与管理员联系！");
		}
	});
    return false;
  });
  var $ = layui.$, active ={ 
   
	edit:function(obj){
		$(".select_text").hide();
		$(this).hide();

		$("#t_name").attr("disabled",false);
		$("#t_url").attr("disabled",false);
		$("#table_name").attr("disabled",false);
		$("#save_button").show();
		$(".select").show();

	}
  };
  $('.layui-btn').on('click', function(){
    var type = $(this).data('type');
    active[type] ? active[type].call(this) : '';
  });
  
  
});

</script>

</html>

