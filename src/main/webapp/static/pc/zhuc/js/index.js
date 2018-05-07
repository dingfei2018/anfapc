$(function(){
	var stuList = getStuList();//设置传送信息：学生的集合
	 
	//聚焦失焦input
	$('input').eq(0).focus(function(){
		if($(this).val().length==0){
			$(this).parent().next("div").text("仅支持11位手机号码进行注册");
		}
	})
	$('input').eq(1).focus(function(){
		if($(this).val().length==0){
		    /*$(this).parent().next("div").text("建议使用字母、数字和符号两种以上的组合，6-20个字符");*/
		    
		}
	})
	$('input').eq(2).focus(function(){
		if($(this).val().length==0){
			$(this).parent().next("div").text("请再次输入密码");
			
		}
	})
	$('input').eq(3).focus(function(){
		if($(this).val().length==0){
			$(this).parent().next("div").text("验证完后，你可以使用该手机登陆和找回密码");
			
		}
	})
	$('input').eq(4).focus(function(){	
		if($(this).val().length==0){
			$(this).parent().next().next("div").text("看不清？点击图片更换验证码");
			
		}
	})
	//input各种判断
	
	//密码
	$('input').eq(1).blur(function(){
		if($(this).val().length==0){
			$(this).parent().next("div").text("");
			$(this).parent().next("div").css("color",'#ccc');
		}else if($(this).val().length>0 && $(this).val().length<6){
			$(this).parent().next("div").text("长度只能在6-20个字符之间");
			$(this).parent().next("div").css("color",'red');
		}else{
			$(this).parent().next("div").text("");
		}		
	})
//	确认密码
	$('input').eq(2).blur(function(){
		if($(this).val().length==0){
			$(this).parent().next("div").text("");
			$(this).parent().next("div").css("color",'#ccc');
		}else if($(this).val()!=$('input').eq(1).val()){
	

		}else{
			$(this).parent().next("div").text("");
		}		
	})


//	提交按钮
	$("#submit_btn").click(function(e){		
		for(var j=0 ;j<5;j++){
			if($('input').eq(j).val().length==0){				
				$('input').eq(j).focus();				
				if(j==4){
					$('input').eq(j).parent().next().next("div").text("此处不能为空");
					$('input').eq(j).parent().next().next("div").css("color",'red');
					e.preventDefault();
					return;
				}
				$('input').eq(j).parent().next(".tips").text("此处不能为空");
				$('input').eq(j).parent().next(".tips").css("color",'red');	
				e.preventDefault();
				return;
			}			
		}
		//协议
		if($("#xieyi")[0].checked){
			//向变量stuList数组添加一个数值，数值内部格式Student(name,password,tel,id)
			//发送用户信息
			stuList.push(new Student($('input').eq(0).val(),$('input').eq(1).val(),$('input').eq(3).val(),stuList.length+1));
            localStorage.setItem('stuList',JSON.stringify(stuList));
            alert("注册成功");
			window.open("userlist.html","_blank");
		}else{						
			$("#xieyi").next().next().next(".tips").text("请勾选协议");
			$("#xieyi").next().next().next(".tips").css("color",'red');
			e.preventDefault();
			return;
		}
	})
	
//  建立构造函数，构造学生信息模板
	function Student(name,password,tel,id){
         this.name = name;
         this.password = password;
         this.tel = tel;
         this.id = id;
     }
//	获取之前所有已经注册的用户集合
	function getStuList(){
	    var list = localStorage.getItem('stuList');
	    if(list != null){
	        return JSON.parse(list);
	    }else{
	        return new Array();
	    }
	}

})
