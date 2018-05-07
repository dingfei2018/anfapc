$(function() {
	/*$(".banner-right-banner").mouseover(function() {
		$(".banner-feel").show().animate({
			height: "200px"
		}, 300)
	})
	$(".banner-feel").mouseleave(function() {
		$(".banner-feel").animate({
			height: "0px"
		}, 300, function() {
			$(".banner-feel").hide()
		})
	});*/
	
	/*$("#flip").on('mouseover',function(){
	   $(this).next("div").slideDown(500);
	    console.log("555");
		$("#panel").show().animate({height:"264px"});
		console.log("666")
	  });
	
	  $("#content").on('mouseleave',function(){
	    $(this).children("div").slideUp(500);
		 $("#panel").hide().animate({height:"0px"},500);
	  });*/
	  
      $("#flips").off('mouseover').on('mouseover',function(){
		    $(this).next("div").slideDown(0);
		  });
		  $("#contents").off('mouseleave').on('mouseleave',function(){
		    $(this).children("div").slideUp(0);
		  });

	  
	$(".content-detila").click(function() {
		$(".content-link").show().animate({
			height: "450px"
		}, 300)
	});
	$(".content-link").mouseleave(function() {
		$(".content-link").animate({
			height: "0px"
		}, 300, function() {
			$(".content-link").hide()
		})
	});
	$(".content-button").click(function() {
		$(".content-right").show();
	});
	$(".content-rights").click(function() {
		$(".content-right").hide();
	});
    
    
    //五星好评的功能
    var wjx_k = "☆";  
    var wjx_s = "★";  
    
    $(".comment li").on("mouseenter", function () {  
        $(this).html(wjx_s).prevAll().html(wjx_s).end().nextAll().html(wjx_k);  
    }).on("click", function () {  
        $(this).addClass("active").siblings().removeClass("active")  
    });  
    $(".comment").on("mouseleave", function () {  
        $(".comment li").html(wjx_k);  
        $(".active").text(wjx_s).prevAll().text(wjx_s);  
    })  
    
    $("#gun1").click(function (){  
		 $(".content-left-gun1").removeClass("content-left-gun123");
		 $(".content-left-gun1").removeClass("content-left-gun122");
		 $(".content-left-gun1").addClass('content-left-gun110'); 
		 $(".content-left-lay2").hide();
		 $("#content-left-lay3").hide();
		 $(".content-left-lay").show();
		 $("#jjk").show();
		 $("#jjks").hide();
		 $("#jjkc").hide();
       });  
	
	   $("#gun2").click(function (){  
		   $(".content-left-gun1").removeClass("content-left-gun123");
		   $(".content-left-gun1").removeClass("content-left-gun110");
		   $(".content-left-gun1").addClass('content-left-gun122');    
		   $(".content-left-lay").hide();
		   $("#content-left-lay3").hide();
		   $(".content-left-lay2").show();
		   $("#jjk").hide();
		   $("#jjks").show();
		   $("#jjkc").hide();
       }); 
	   
	   $("#gun3").click(function (){  
		   $(".content-left-gun1").removeClass("content-left-gun122");
		   $(".content-left-gun1").removeClass("content-left-gun110");
		   $(".content-left-gun1").addClass('content-left-gun123');
		   $(".content-left-lay").hide();
		   $(".content-left-lay2").hide();
		   $("#content-left-lay3").show();
		   $("#jjk").hide();
		   $("#jjks").hide();
		   $("#jjkc").show();
       });  
	   
	   $(".as").css("color","blue").css("font-weight","bold").css("text-decoration","none");
	   $("#gun2 a").css("color","#666").css("font-weight","normal").css("text-decoration","none");
	   $("#gun3 a").css("color","#666").css("font-weight","normal").css("text-decoration","none");
	   
	    $("#gun1").click(function(){
	    	   $("#gun1 a").css("color","blue").css("font-weight","bold").css("text-decoration","none");
	    	   $("#gun2 a").css("color","#666").css("font-weight","normal").css("text-decoration","none");
			   $("#gun3 a").css("color","#666").css("font-weight","normal").css("text-decoration","none");
	    });
	   
	    $("#gun2").click(function(){
	    	   $("#gun1 a").css("color","#666").css("font-weight","normal").css("text-decoration","none");
			   $("#gun2 a").css("color","blue").css("font-weight","bold").css("text-decoration","none");
			   $("#gun3 a").css("color","#666").css("font-weight","normal").css("text-decoration","none");
		    });
	    
	    $("#gun3").click(function(){
	    	   $("#gun1 a").css("color","#666").css("font-weight","normal").css("text-decoration","none");
			   $("#gun3 a").css("color","blue").css("font-weight","bold").css("text-decoration","none");
			   $("#gun2 a").css("color","#666").css("font-weight","normal").css("text-decoration","none");
		    });
	    
	 /*  $("#gun2").click(function(){
		   $("#gun2 a").css("color","blue").css("font-weight","bold").css("text-decoration","none");
	   }).mouseout(function(){
		   $("#gun2 a").css("color","#666").css("font-weight","normal").css("text-decoration","none");
	   });
	   
	   $("#gun3").click(function(){
		   $("#gun3 a").css("color","blue").css("font-weight","bold").css("text-decoration","none");
	   }).mouseout(function(){
		   $("#gun3 a").css("color","#666").css("font-weight","normal").css("text-decoration","none");
	   });*/
	   
	 
		
})