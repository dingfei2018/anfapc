//应收应付汇总表
init();
function init(){
    $("#loadingId").mLoading("show");
    getData();

}

function getData(){

    $.ajax({
        type:'post',
        url:'/kd/report/getRePayJson',
        data:{
            startTime:$('#startTime').val(),
            endTime:$('#endTime').val(),
            networkId:$('#networkId').val()
        },
        success:function(data){
            console.log(data);
            var xf1=data[0].receivablesShipCount;
			$('#xf1').html(xf1);
            var xf2=data[0].receivablesTotal;
			$('#xf2').html(xf2);
            var xf3=data[0].receivablesGet;
			$('#xf3').html(xf3);
            var xf4=data[0].receivablesNOGet;
			$('#xf4').html(xf4);

            var tf1=data[1].receivablesShipCount;
			$('#tf1').html(tf1);
            var tf2=data[1].receivablesTotal;
			$('#tf2').html(tf2);
            var tf3=data[1].receivablesGet;
			$('#tf3').html(tf3);
            var tf4=data[1].receivablesNOGet;
			$('#tf4').html(tf4);

            var hdf1=data[2].receivablesShipCount;
			$('#hdf1').html(hdf1);
            var hdf2=data[2].receivablesTotal;
			$('#hdf2').html(hdf2);
            var hdf3=data[2].receivablesGet;
			$('#hdf3').html(hdf3);
            var tf4=data[2].receivablesNOGet;
			$('#hdf4').html(tf4);

            var yj1=data[3].receivablesShipCount;
			$('#yj1').html(yj1);
            var yj2=data[3].receivablesTotal;
			$('#yj2').html(yj2);
            var yj3=data[3].receivablesGet;
			$('#yj3').html(yj3);
            var yj4=data[3].receivablesNOGet;
			$('#yj4').html(yj4);

            var dq1=data[4].receivablesShipCount;
			$('#dq1').html(dq1);
            var dq2=data[4].receivablesTotal;
			$('#dq2').html(dq2);
            var dq3=data[4].receivablesGet;
			$('#dq3').html(dq3);
            var dq4=data[4].receivablesNOGet;
			$('#dq4').html(dq4);

            var dkk1=data[5].receivablesShipCount;
			$('#dkk1').html(dkk1);
            var dkk2=data[5].receivablesTotal;
			$('#dkk2').html(dkk2);
            var dkk3=data[5].receivablesGet;
			$('#dkk3').html(dkk3);
            var dkk4=data[5].receivablesNOGet;
			$('#dkk4').html(dkk4);

            var ydzj1=data[6].receivablesShipCount;
			$('#ydzj1').html(ydzj1);
            var ydzj2=data[6].receivablesTotal;
			$('#ydzj2').html(ydzj2);
            var ydzj3=data[6].receivablesGet;
			$('#ydzj3').html(ydzj3);
            var ydzj4=data[6].receivablesNOGet;
			$('#ydzj4').html(ydzj4);

            var ydjk1=data[7].payShipCount;
			$('#ydjk1').html(ydjk1);
            var ydjk2=data[7].payTotal;
			$('#ydjk2').html(ydjk2);
            var ydjk3=data[7].payGet;
			$('#ydjk3').html(ydjk3);
            var ydjk4=data[7].payNOGet;
			$('#ydjk4').html(ydjk4);

            var hk1=data[8].payShipCount;
			$('#hk1').html(hk1);
            var hk2=data[8].payTotal;
			$('#hk2').html(hk2);
            var hk3=data[8].payGet;
			$('#hk3').html(hk3);
            var hk4=data[8].payNOGet;
			$('#hk4').html(hk4);

            var thf1=data[9].payShipCount;
			$('#thf1').html(thf1);
            var thf2=data[9].payTotal;
			$('#thf2').html(thf2);
            var thf3=data[9].payGet;
			$('#thf3').html(thf3);
            var thf4=data[9].payNOGet;
			$('#thf4').html(thf4);

            var dbf1=data[10].payShipCount;
			$('#dbf1').html(dbf1);
            var dbf2=data[10].payTotal;
			$('#dbf2').html(dbf2);
            var dbf3=data[10].payGet;
			$('#dbf3').html(dbf3);
            var dbf4=data[10].payNOGet;
			$('#dbf4').html(dbf4);

            var shf1=data[11].payShipCount;
			$('#shf1').html(shf1);
            var shf2=data[11].payTotal;
			$('#shf2').html(shf2);
            var shf3=data[11].payGet;
			$('#shf3').html(shf3);
            var shf4=data[11].payNOGet;
			$('#shf4').html(shf4);

            var zzf1=data[12].payShipCount;
			$('#zzf1').html(zzf1);
            var zzf2=data[12].payTotal;
			$('#zzf2').html(zzf2);
            var zzf3=data[12].payGet;
			$('#zzf3').html(zzf3);
            var zzf4=data[12].payNOGet;
			$('#zzf4').html(zzf4);

            var xfysf1=data[13].payShipCount;
			$('#xfysf1').html(xfysf1);
            var xfysf2=data[13].payTotal;
			$('#xfysf2').html(xfysf2);
            var xfysf3=data[13].payGet;
			$('#xfysf3').html(xfysf3);
            var xfysf4=data[13].payNOGet;
			$('#xfysf4').html(xfysf4);

            var xfykf1=data[14].payShipCount;
			$('#xfykf1').html(xfykf1);
            var xfykf2=data[14].payTotal;
			$('#xfykf2').html(xfykf2);
            var xfykf3=data[14].payGet;
			$('#xfykf3').html(xfykf3);
            var xfykf4=data[14].payNOGet;
			$('#xfykf4').html(xfykf4);

            var hfysf1=data[15].payShipCount;
			$('#hfysf1').html(hfysf1);
            var hfysf2=data[15].payTotal;
			$('#hfysf2').html(hfysf2);
            var hfysf3=data[15].payGet;
			$('#hfysf3').html(hfysf3);
            var hfysf4=data[15].payNOGet;
			$('#hfysf4').html(hfysf4);

            var dfysf1=data[16].payShipCount;
			$('#dfysf1').html(dfysf1);
            var dfysf2=data[16].payTotal;
			$('#dfysf2').html(dfysf2);
            var dfysf3=data[16].payGet;
			$('#dfysf3').html(dfysf3);
            var dfysf4=data[16].payNOGet;
			$('#dfysf4').html(dfysf4);

            var zcbxf1=data[17].payShipCount;
			$('#zcbxf1').html(zcbxf1);
            var zcbxf2=data[17].payTotal;
			$('#zcbxf2').html(zcbxf2);
            var zcbxf3=data[17].payGet;
			$('#zcbxf3').html(zcbxf3);
            var zcbxf4=data[17].payNOGet;
			$('#zcbxf4').html(zcbxf4);

            var fzzcf1=data[18].payShipCount;
			$('#fzzcf1').html(fzzcf1);
            var fzzcf2=data[18].payTotal;
			$('#fzzcf2').html(fzzcf2);
            var fzzcf3=data[18].payGet;
			$('#fzzcf3').html(fzzcf3);
            var fzzcf4=data[18].payNOGet;
			$('#fzzcf4').html(fzzcf4);

            var fzqtf1=data[19].payShipCount;
			$('#fzqtf1').html(fzzcf1);
            var fzqtf2=data[19].payTotal;
			$('#fzqtf2').html(fzqtf2);
            var fzqtf3=data[19].payGet;
			$('#fzqtf3').html(fzqtf3);
            var fzqtf4=data[19].payNOGet;
			$('#fzqtf4').html(fzqtf4);

            var dzxcf1=data[20].payShipCount;
			$('#dzxcf1').html(dzxcf1);
            var dzxcf2=data[20].payTotal;
			$('#dzxcf2').html(dzxcf2);
            var dzxcf3=data[20].payGet;
			$('#dzxcf3').html(dzxcf3);
            var dzxcf4=data[20].payNOGet;
			$('#dzxcf4').html(dzxcf4);

            var dzqtf1=data[21].payShipCount;
			$('#dzqtf1').html(dzqtf1);
            var dzqtf2=data[21].payTotal;
			$('#dzqtf2').html(dzqtf2);
            var dzqtf3=data[21].payGet;
			$('#dzqtf3').html(dzqtf3);
            var dzqtf4=data[21].payNOGet;
			$('#dzqtf4').html(dzqtf4);


            setTimeout(function(){$("#loadingId").mLoading("hide");}, 200);
        }
    });

}
	


