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

.layui-form-item{
	margin-bottom: 0px; 
	margin-top:5px;
}

.layui-input-inline{
	width:140px !important;
	margin-bottom: 0px; 
}
.layui-input{
	height:28px;
	margin-top:0px;
	width:180px !important;
}
.layui-table-cell{
	line-height: 20px;
    font-size: 13px;
	height:20px;

}
.layui-table td, .layui-table th{
	line-height: 20px;
    min-height: 20px;
}
.layui-table, .layui-table-view{
	margin-top:0px;
}
hr{
	margin:0px;
}
.layui-table-tool-temp{
	padding-right: 0px;
	padding-left: 20px;
}
.checkIf{
	margin: 10px; 
}
.layui-input-block{
	margin-left:5px;
	margin-top:10px;
}
</style>

</head>

<BODY>
<div class="checkIf">
<form class="layui-form" action="" lay-filter="example">

  <div class="layui-form-item">

  类别名称：
  <div class="layui-inline">
    <input class="layui-input" name="typeName" id="typeName" autocomplete="off">
  </div>
  来源：
  <div class="layui-inline">
      <div class="layui-input-block">
      <select name="types" id="types" lay-filter="aihao">
        <option value=""></option>
        <option value="0">接口</option>
        <option value="1">数据表</option>
      </select>
    </div>
  </div>
  </form>
</div>

<hr class="layui-bg-blue">
<table class="layui-hide" id="table" lay-filter="table"></table>
<script type="text/html" id="toolbarDemo">
  <div class="layui-btn-container">
  <input type="button" value="查询" id="btn_search" 
			class="btn layui-btn layui-btn-sm" data-type="reload" lay-event="reload"/>
  <input type="button" value="新增" id="btn_add" 
			class="btn layui-btn layui-btn-sm" data-type="add" lay-event="add"/>
  <input type="button" value="删除" id="btn_del" 
			class="btn layui-btn layui-btn-sm" data-type="del" lay-event="del"/>		 
  </div>
</script>
	
</body>
<script language="Javascript"
	src="/weaversj/public_ui/js/jquery-1.8.3.min.js"></script>
<script language="Javascript" src="/weaversj/public_ui/layui/layui.js"></script>

<script LANGUAGE="javascript">
	
	$(function() {
		
	});//初始化完成
</script>
<script>
layui.use('table', function(){
  var table = layui.table;
 
  var fields = [[ //表头
      {type: 'checkbox', fixed: 'left'}
      ,{field: 't_id', title: 'ID',sort: true, fixed: 'left'}
      ,{field: 't_name', title: '分类名称',sort: true}
      ,{field: 'if_table', title: '来源',  sort: true}
      ,{field: 'table_name', title: '表名'} 
	  ,{field: 't_url', title: '接口地址'}
	  
    ]];
  //第一个实例
  table.render({
    elem: '#table'
	,id:"reloadTable"
	,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
	,defaultToolbar: []
	,loading:true
	,limits:[10,20,100]
    ,url: '/weaversj/public_util/h_page/jsp/getJson.jsp?flags=select' //数据接口
   	,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
   	      layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
   	      //,curr: 5 //设定初始在第 5 页
   	      ,groups: 3 //只显示 1 个连续页码
   	      ,first: false //不显示首页
   	      ,last: false //不显示尾页
   	    }
    ,cols: fields
  });
  //监听行单击事件（双击事件为：rowDouble）
  table.on('row(table)', function(obj){
    var data = obj.data;
    //标注选中样式
    obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
  });
  
   var $ = layui.$, active ={ 
    ids:this.ids,
	flags:this.flags,
    reqAjax: function(obj){
        jQuery.ajax({ 
			url : "/weaversj/public_util/h_page/jsp/getJson.jsp",  
			type:"post",
			dataType:"json",
			cache:false, 
			async:false, 
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			data:{
				flags:this.flags,
				ids:this.ids
			},
			success : function(jsonStr) {
				if(jsonStr.code == 0){
					layer.msg('删除成功');
					table.reload('reloadTable', {
						page: {
							curr: 1 //重新从第 1 页开始
						}
					}, 'data');
				}else{
					layer.msg(jsonStr.mess);			
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				alert("系统发生异常：请与管理员联系！");
			}
		});
    }
  };
 
  table.on('toolbar(table)', function(obj){
    switch(obj.event){
        case 'add':
            layer.open({
				type: 2,
				title:"新增分类",
				id:"openDiaolog",
				offset:"t",
				area: ['700px', '450px'],
				fixed: false, //不固定
				maxmin: true,
				content: "/weaversj/public_util/h_page/jsp/view.jsp"
			});
            break;
        case 'del':
		    var checkStatus = table.checkStatus(obj.config.id);
            var data = checkStatus.data;
			if(data.length == 0){
				layer.msg('请选择要删除的数据');
				break;

			}else{
				layer.open({
					type: 1
					,offset: "t" //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
					,id: 'layerDemo' //防止重复弹出
					,content: '<div style="padding: 20px 100px;">确定要删除吗？</div>'
					,btn: '确认删除'
					,btnAlign: 'c' //按钮居中
					,shade: 0 //不显示遮罩
					,yes: function(){
						var ids = "";
						for(var i=0;i<data.length;i++){
							ids+= ","+data[i].t_id;
						}
						var a = {};
						a.flags = "del";
						a.ids = ids.substring(1);
						active["reqAjax"].call(a);
					    layer.closeAll();
					}
			    });
				break;
			}
			
			break;
		case 'reload':
			var typeName = $('#typeName');
			var types = $('#types');

			  //执行重载
			table.reload('reloadTable', {
				page: {
					curr: 1 //重新从第 1 页开始
				}
				,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
				,where: {
					typeName: typeName.val(),
					types: types.val()
				}
			}, 'data');
			break;
    };
  });
/*  $('.btn').on('click', function(){
    var type = $(this).data('type');
    active[type] ? active[type].call(this) : '';
  });*/
  
});


</script>

</html>

