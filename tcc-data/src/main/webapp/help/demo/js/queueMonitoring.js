$(document).ready(function(){
	$(".chooseQueue_right_time").text((new Date).getTimes());
	setInterval(function(){
		$(".chooseQueue_right_time").text((new Date).getTimes());
	}, 1000);
	//window.parent.qMonitoring("0");
	var checkboxString='';
	$(".positionState_content input[type='checkbox']").click(function(){
		if($(this).attr("checked") == true){
			events.checkedTrue($(this).val());
		}else if($(this).attr("checked") == false){
			events.checkedFalse($(this).val());
		}
	});
});
var events = {
	operate : function(){
		$(".operate").unbind("click");
		$(".operate").bind({
			click : function(event){
				$(".monitoringBar_ul").hide();
				$(this).find("~ ul").css("top",event.pageY-10);
				$(this).find("~ ul").show();
			}
		});
	},
	operateParent : function(){
		$(".operate").parent().unbind("mouseleave");
		$(".operate").parent().bind({
			mouseleave : function(){
				$(".monitoringBar_ul").hide();
			}
		});
	},
	monitoringBar_li : function(){
		$(".monitoringBar_li").unbind("mouseenter mouseleave");
		$(".monitoringBar_li").removeClass("list_content_list_mouseenterBg");
		$(".monitoringBar_li").bind({
			mouseenter : function(){
				$(this).addClass("list_content_list_mouseenterBg");
			},
			mouseleave : function(){
				$(this).removeClass("list_content_list_mouseenterBg");
			}
		});
	},
	monitoringBar_ul : function(){
		$(".monitoringBar_ul").unbind("mouseenter mouseleave");
		$(".monitoringBar_ul").hide();
		$(".monitoringBar_ul").bind({
			mouseenter : function(){
				$(this).show();
			},
			mouseleave : function(){
				$(this).hide();
			}
		});
	},
	list_Div_table_tr : function(){
		$(".list_Div table tr").unbind("mouseenter mouseleave");
		$(".list_Div table tr").bind({
			mouseenter : function(){
				$(this).addClass("list_content_list_mouseenterBg");
			},
			mouseleave : function(){
				$(this).removeClass("list_content_list_mouseenterBg");
			}
		});
	},
	list_title_imgSpan : function(){
		$(".list_title_imgSpan img").unbind("mouseenter mouseleave mousedown");
		$(".list_title_imgSpan img").attr("src","images/queueMonitoringIMG/buttonArrow01.png");
		$("#callContent, #cnoContent, #qStatusContent").show();
		$(".list_title_imgSpan img").bind({
			mouseenter : function(){
				var imgSrc = $(this).attr("src");
				//imgSrc = imgSrc.substring(imgSrc.length-4,imgStrArray[0].length);
				var str = imgSrc.substring(imgSrc.length-7,imgSrc.length-4);
				
				if(str == 'w01'){
					$(this).attr("src","images/queueMonitoringIMG/buttonArrow02.png");
				}else{
					$(this).attr("src","images/queueMonitoringIMG/buttonArrow002.png");
				}
			},
			mouseleave : function(){
				var imgSrc = $(this).attr("src");
				var str = imgSrc.substring(imgSrc.length-7,imgSrc.length-4);
				if(str == 'w02'){
					$(this).attr("src","images/queueMonitoringIMG/buttonArrow01.png");
				}else{
					$(this).attr("src","images/queueMonitoringIMG/buttonArrow001.png");
				}
			},
			mousedown : function(){
				var imgSrc = $(this).attr("src");
				var str = imgSrc.substring(imgSrc.length-7,imgSrc.length-4);
				if(str == 'w02'){
					$(this).attr("src","images/queueMonitoringIMG/buttonArrow002.png");
				}else{
					$(this).attr("src","images/queueMonitoringIMG/buttonArrow02.png");
				}
				var id = $(this).attr("id");
				if(id == 'callList'){
					if($("#callContent").css("display") == "none"){
						$("#callContent").show();
					}else{
						$("#callContent").hide();
					}
				}else if(id == 'cnoList'){
					if($("#cnoContent").css("display") == "none"){
						$("#cnoContent").show();
					}else{
						$("#cnoContent").hide();
					}
				}else if(id == 'qStatus'){
					if($("#qStatusContent").css("display") == "none"){
						$("#qStatusContent").show();
					}else{
						$("#qStatusContent").hide();
					}
				}
			}
		});
	},
	queueSelect : function(){
		$("#queueSelect").unbind("change");
		$("#queueSelect").change(function(){
			window.parent.qMonitoring($(this).val());
		})
	},
	checkedTF : function(stu){
		switch(stu){
			case "空闲" : 
				var cc = $(".positionState_content input[type='checkbox'][value='Idle']").attr("checked");
				if(cc == true){
					events.checkedTrue("Idle");
				}else{
					events.checkedFalse("Idle");
				}
			break;
			case "离线" :
				var cc = $(".positionState_content input[type='checkbox'][value='offline']").attr("checked");
				if(cc == true){
					events.checkedTrue("offline");
				}else{
					events.checkedFalse("offline");
				} 
			break;
			case "响铃" : 
				var cc = $(".positionState_content input[type='checkbox'][value='ring']").attr("checked");
				if(cc == true){
					events.checkedTrue("ring");
				}else{
					events.checkedFalse("ring");
				} 
			break;
			case "通话" : 
				var cc = $(".positionState_content input[type='checkbox'][value='busy']").attr("checked");
				if(cc == true){
					events.checkedTrue("busy");
				}else{
					events.checkedFalse("busy");
				} 
			break;
			case "整理" : 
				var cc = $(".positionState_content input[type='checkbox'][value='neaten']").attr("checked");
				if(cc == true){
					events.checkedTrue("neaten");
				}else{
					events.checkedFalse("neaten");
				} 
			break;
			case "外呼中" : 
			break;	
			default:
				var cc = $(".positionState_content input[type='checkbox'][value='pause']").attr("checked");
				if(cc == true){
					events.checkedTrue("pause");
				}else{
					events.checkedFalse("pause");
				} 
		}
	},
	checkedTrue : function(val){
		switch(val){
			case "checkAll": 
				$(".positionState_content input[type='checkbox']").attr("checked",true);
				$(".list_content_list_tr").show();
			break;
			case "Idle":
				$(".list_content_list_tr[statustr='空闲']").show();
			break;
			case "offline":
				$(".list_content_list_tr[statustr='离线']").show();
			break;
			case "ring":
				$(".list_content_list_tr[statustr='响铃']").show();
			break;
			case "busy":
				$(".list_content_list_tr[statustr='通话']").show();
			break;
			case "neaten":
				$(".list_content_list_tr[statustr='整理']").show();
			break;
			case "pause":
				$(".list_content_list_tr[statustr!='空闲'][statustr!='离线'][statustr!='响铃'][statustr!='通话']").show();
			break;
		}
	},
	checkedFalse : function(val){
		switch(val){
			case "checkAll": 
				$(".positionState_content input[type='checkbox']").attr("checked",false);
				$(".list_content_list_tr").hide();
			break;
			case "Idle":
				$(".list_content_list_tr[statustr='空闲']").hide();
			break;
			case "offline":
				$(".list_content_list_tr[statustr='离线']").hide();
			break;
			case "ring":
				$(".list_content_list_tr[statustr='响铃']").hide();
			break;
			case "busy":
				$(".list_content_list_tr[statustr='通话']").hide();
			break;
			case "neaten":
				$(".list_content_list_tr[statustr='整理']").hide();
			break;
			case "pause":
				$(".list_content_list_tr[statustr!='空闲'][statustr!='离线'][statustr!='响铃'][statustr!='通话']").hide();
			break;
		}
	},
	initIframe : function(){
		$("#qStatusContent").show();
	},
	init : function(){
		this.operate();
		this.operateParent();
		this.monitoringBar_li();
		this.monitoringBar_ul();
		this.list_Div_table_tr();
		this.list_title_imgSpan();
		this.queueSelect();
		this.initIframe();
	}
}
var setIntlId= new Array();
function queue(data, qid){
	$(this+"input[type='checkbox']").attr("checked",true);
	for(var i=0;i<setIntlId.length;i++){
		clearInterval(setIntlId[i]);	
	}
	setIntlId.length=0;
	$("#cnoTable").empty();
	$("#callTable").empty();
	$("#queueSelect").empty();
	$("#monitor").empty();
	var showData="";
	for(var i=0; i<data.queueStatus.length; i++){
		var option;
		if(qid == data.queueStatus[i].qid){
			//$("#reportForms").attr("src","reportForms.html?qid="+qid);
			//$("#cnoForms").attr("src","cnoForms.html?qid="+qid);
			showData = data.queueStatus[i];
			option = "<option value='"+data.queueStatus[i].qid+"' selected='selected'>"+data.queueStatus[i].qname+"</option>";
		}else{
			option = "<option value='"+data.queueStatus[i].qid+"'>"+data.queueStatus[i].qname+"</option>";
		}
		$("#queueSelect").append(option);
	}
	if(showData == ""){
		showData = data.queueStatus[0];
		//$("#reportForms").attr("src","reportForms.html?qid="+data.queueStatus[0].qid);
		//$("#cnoForms").attr("src","cnoForms.html?qid="+data.queueStatus[0].qid);
	}
	
	if(showData.memberStatus.length >6){
		$("#cnoTableDiv").attr("style","height:170px; overflow:auto;");
	}else{
		$("#cnoTableDiv").height("");
	}
	$("#qname").text(showData.qname);
	$("#cnoSum").text(showData.memberStatus.length);
	$("#strategy").text(strategyString(showData.queueParams.strategy));
	for(var i=0;i<showData.memberStatus.length; i++){
		//alert(showData.memberStatus[i].power)
		if(showData.memberStatus[i].power == 1){
			if($("#monitor").text() ==''){
				$("#monitor").append(showData.memberStatus[i].cname);
			}else{
				$("#monitor").append("、"+showData.memberStatus[i].cname);
			}
		}
	}
	var online=0;
	
	var wp = window.parent.document.getElementById('toolbar');
	var nowCno = window.parent.document.getElementById('cno');
	
	for(var i=0;i<showData.memberStatus.length; i++){
		
		var ms = showData.memberStatus[i];
		var status = wp.contentWindow.deviceStatus.deviceStatusLoginStatus(ms.deviceStatus+ms.loginStatus, ms.pauseDescription, ms.busyDescription);
		
		if(status != "离线"){
			online+=1;	
		}
		var tr = "<tr class='list_content_list_tr #{bg}' statustr='#{statusText}'>"+
				"<td align='center' class='list_content_list_Td' width='5%'><img src='#{statusImg}' title='#{statusText}' name='statusName'/></td>"+
				"<td align='center' class='list_content_list_Td' width='5%'>#{cno}</td>"+
				"<td align='center' class='list_content_list_Td' width='15%'>#{name}</td>"+
				"<td align='center' class='list_content_list_Td' width='15%'>#{power}</td>"+
				"<td align='center' class='list_content_list_Td' width='15%'>#{callstaken}</td>"+
				"<td align='center' class='list_content_list_Td' width='15%' id='#{loginTimeId}'>#{loginTime}</td>"+
				"<td align='center' class='list_content_list_Td' width='10%' id='#{durationId}'>#{duration}</td>"+
				"<td align='center' class='list_content_list_Td' width='15%'>#{customerNumber}</td>"+
				"<td align='center' class='list_content_list_Td' width='5%'>"+
					"<img src='images/queueMonitoringIMG/operate02.png' class='operate'/>"+
					"<ul class='monitoringBar_ul'>"+
						"<li class='monitoringBar_li' name='spy' cno='#{cno}' status='#{statusText}'>"+
							"<span class='monitoringBar_li_span'><img src='images/queueMonitoringIMG/spy0"+(status=="通话"?"2":"1")+".png'/></span>"+
							"<span class='monitoringBar_li_span'>监听</span>"+
						"</li>"+
						"<li class='monitoringBar_li' name='threeway' cno='#{cno}' status='#{statusText}'>"+
							"<span class='monitoringBar_li_span'><img src='images/queueMonitoringIMG/threeway0"+(status=="通话"?"2":"1")+".png'/></span>"+
							"<span class='monitoringBar_li_span'>三方</span>"+
						"</li>"+
						"<li class='monitoringBar_li' name='whisper' cno='#{cno}' status='#{statusText}'>"+
							"<span class='monitoringBar_li_span'><img src='images/queueMonitoringIMG/whisper0"+(status=="通话"?"2":"1")+".png'/></span>"+
							"<span class='monitoringBar_li_span'>耳语</span>"+
						"</li>"+
						"<li class='monitoringBar_li' name='pickup' cno='#{cno}' status='#{statusText}'>"+
							"<span class='monitoringBar_li_span'><img src='images/queueMonitoringIMG/pickup0"+(status=="响铃"?"2":"1")+".png'/></span>"+
							"<span class='monitoringBar_li_span'>抢线</span>"+
						"</li>"+
						"<li class='monitoringBar_li' name='disconnects' cno='#{cno}' status='#{statusText}'>"+
							"<span class='monitoringBar_li_span'><img src='images/queueMonitoringIMG/disconnects0"+(status=="通话"?"2":"1")+".png'/></span>"+
							"<span class='monitoringBar_li_span'>强拆</span>"+
						"</li>"+
						"<li class='monitoringBar_li' name='barge' cno='#{cno}' status='#{statusText}'>"+
							"<span class='monitoringBar_li_span'><img src='images/queueMonitoringIMG/barge0"+(status=="通话"?"2":"1")+".png'/></span>"+
							"<span class='monitoringBar_li_span'>强插</span>"+
						"</li>"+
					"</ul>"+
				"</td>"+
			"</tr>";
		var power = showData.memberStatus[i].power;
		var customerNumber =showData.memberStatus[i].customerNumber;
		var callstaken = showData.memberStatus[i].callstaken;
		var loginTime = showData.memberStatus[i].loginTime;
		var duration = showData.memberStatus[i].duration;
		var cno = showData.memberStatus[i].cid.substring(7,showData.memberStatus[i].cid.length);
		var loginTimeId = cno+"_loginTimeId";
		var durationId = cno+"_durationId";
		tr = tr.replace(/#\{statusImg\}/g, "images/"+getStatusImg(status));
		tr = tr.replace(/#\{statusText\}/g, status);
		tr = tr.replace(/#\{loginTimeId\}/g, loginTimeId);
		tr = tr.replace(/#\{durationId\}/g, durationId);
		tr = tr.replace(/#\{cno\}/g, cno);
		tr = tr.replace(/#\{name\}/g, showData.memberStatus[i].cname);
		if(power == '1'){
			power = "班长席";
		}else{
			power = "普通座席";
		}
		//
		
		if(i%2 == 0){
			tr = tr.replace(/#\{bg\}/g, "list_content_list_trBg");
		}else{
			tr = tr.replace(/#\{bg\}/g, "");
		}
		
		tr = tr.replace(/#\{power\}/g, power);
		if(callstaken == undefined){
			callstaken = "--";
		}
		tr = tr.replace(/#\{callstaken\}/g, callstaken);
		if(loginTime == undefined ){
			loginTime = "--";
		}else{
			loginTime = millisecondToDate(parseInt(showData.memberStatus[i].loginTime,10));
		}
		tr = tr.replace(/#\{loginTime\}/g, loginTime);
		if(duration == undefined || duration == ''){
			duration = "--";
		}else{
			duration = millisecondToDate(parseInt(showData.memberStatus[i].duration, 10));
		}
		tr = tr.replace(/#\{duration\}/g, duration);
		if(customerNumber == undefined){
			customerNumber = "--";
		}
		tr = tr.replace(/#\{customerNumber\}/g, customerNumber);
		
		$("#cnoTable").append(tr);
		if(status != '离线'){
			var setIntervalloginTimeId = setInterval("timerCount('"+loginTimeId+"')", 1000);
			setIntlId.push(setIntervalloginTimeId);
			$("#"+loginTimeId).attr("setIntervalloginTimeId",setIntervalloginTimeId);
			var setIntervaldurationId = setInterval("timerCount('"+durationId+"')", 1000);	
			setIntlId.push(setIntervaldurationId);
			$("#"+durationId).attr("setIntervaldurationId",setIntervaldurationId);
		}
		if(nowCno.value == cno) {
			window.parent.document.getElementById('toolbar').contentWindow.document.getElementById('cnoTime').innerHTML=duration;
			var setIntervalCnoTimeId = setInterval("timerCount('cnoTime')", 1000);
			setIntlId.push(setIntervalCnoTimeId);
			window.parent.document.getElementById('toolbar').contentWindow.document.getElementById('cnoTime').setIntervalCnoTimeId=setIntervalCnoTimeId;
		}
	}
	$("#onlineCno").text(online);
	$("#allCno").text(showData.memberStatus.length);
	
	$("#serviceLevel").text(showData.queueParams.serviceLevelPerf+"%");
	
	if(showData.queueEntry.length >6){
		$("#callTable").attr("style","height:170px; overflow:auto;");
	}else{
		$("#callTable").height("");
	}
	bindEvent();
	for(var i=0; i<showData.queueEntry.length; i++){
		var queueEntryTr = "<tr class='list_content_list_tr list_content_list_trBg' id='#{uniqueId}'>"+
					"<td align='center' class='list_content_list_Td' width='15%'>#{customerNumber}</td>"+
					"<td align='center' class='list_content_list_Td' width='15%'>#{startTime}</td>"+
					"<td align='center' class='list_content_list_Td' width='15%'>#{joinTime}</td>"+
					"<td align='center' class='list_content_list_Td' width='10%' id='#{waitTimeId}'>#{waitTime}</td>"+
					"<td align='center' class='list_content_list_Td' width='10%'>#{callStatus}</td>"+
					"<td align='center' class='list_content_list_Td' width='10%'>#{overflow}</td>"+
					"<td align='center' class='list_content_list_Td' width='10%'>#{position}</td>"+
				"</tr>";
		var customerNumber = showData.queueEntry[i].customerNumber;
		
		var startTime = showData.queueEntry[i].startTime;
		var joinTime = showData.queueEntry[i].joinTime;
		var waitTime = showData.queueEntry[i].waitTime;
		var overflow = showData.queueEntry[i].overflow;
		var callStatus = showData.queueEntry[i].call_status;
		if(callStatus == undefined){
			callStatus = "排队";
		}else{
			callStatus = "响铃";
		}
		var uniqueId = showData.queueEntry[i].uniqueId.replace(".",'');;
		var waitTimeId = uniqueId+"_waitTime";
		if(overflow == null)overflow = "0";
		var position = showData.queueEntry[i].position;
		queueEntryTr = queueEntryTr.replace(/#\{callStatus\}/g, callStatus);
		queueEntryTr = queueEntryTr.replace(/#\{uniqueId\}/g, uniqueId);
		queueEntryTr = queueEntryTr.replace(/#\{customerNumber\}/g, customerNumber);
		queueEntryTr = queueEntryTr.replace(/#\{startTime\}/g, startTime);
		queueEntryTr = queueEntryTr.replace(/#\{joinTime\}/g, joinTime);
		queueEntryTr = queueEntryTr.replace(/#\{waitTime\}/g, millisecondToDate(waitTime));
		queueEntryTr = queueEntryTr.replace(/#\{waitTimeId\}/g, waitTimeId);
		queueEntryTr = queueEntryTr.replace(/#\{overflow\}/g, overflow);
		queueEntryTr = queueEntryTr.replace(/#\{position\}/g, position);
		$("#callTable").append(queueEntryTr);
		
		var setIntervalwaitTimeId = setInterval("timerCount('"+waitTimeId+"')", 1000);
		setIntlId.push(setIntervalwaitTimeId);
	}
	$("#waitNormal").text(showData.queueEntry.length);
	$("#callNumber").text(showData.queueEntry.length);
	events.init();
}
function joinQueue(json){
	var q = $("#queueSelect").val();
	if(q != json.qid){
		return;	
	}
	
	$("#waitNormal").text(parseInt($("#waitNormal").text(),10)+1);
	$("#callNumber").text(parseInt($("#callNumber").text(),10)+1);
	
	var queueEntryTr = "<tr class='list_content_list_tr list_content_list_trBg' id='#{uniqueId}'>"+
					"<td align='center' class='list_content_list_Td' width='15%'>#{customerNumber}</td>"+
					"<td align='center' class='list_content_list_Td' width='15%'>#{startTime}</td>"+
					"<td align='center' class='list_content_list_Td' width='15%'>#{joinTime}</td>"+
					"<td align='center' class='list_content_list_Td' width='10%' id='#{waitTimeId}'>#{waitTime}</td>"+
					"<td align='center' class='list_content_list_Td' width='10%'>排队</td>"+
					"<td align='center' class='list_content_list_Td' width='10%'>#{overflow}</td>"+
					"<td align='center' class='list_content_list_Td' width='10%'>#{position}</td>"+
				"</tr>";
	var customerNumber = json.customerNumber;
	
	var startTime = json.startTime;
	var joinTime = json.joinTime;
	var waitTime = json.waitTime;
	if(waitTime == undefined)waitTime=0;
	var overflow = json.overflow;
	var uniqueId = json.uniqueId.replace(".",'');
	var waitTimeId = uniqueId+"_waitTime";
	if(overflow == null)overflow = "0";
	var position = json.position;
	queueEntryTr = queueEntryTr.replace(/#\{uniqueId\}/g, uniqueId);
	queueEntryTr = queueEntryTr.replace(/#\{customerNumber\}/g, customerNumber);
	queueEntryTr = queueEntryTr.replace(/#\{startTime\}/g, startTime);
	queueEntryTr = queueEntryTr.replace(/#\{joinTime\}/g, joinTime);
	queueEntryTr = queueEntryTr.replace(/#\{waitTime\}/g, millisecondToDate(waitTime));
	queueEntryTr = queueEntryTr.replace(/#\{waitTimeId\}/g, waitTimeId);
	queueEntryTr = queueEntryTr.replace(/#\{overflow\}/g, overflow);
	queueEntryTr = queueEntryTr.replace(/#\{position\}/g, position);
	$("#callTable").append(queueEntryTr);
	
	var setIntervalwaitTimeId = setInterval("timerCount('"+waitTimeId+"')", 1000);
	setIntlId.push(setIntervalwaitTimeId);
	$("#"+uniqueId).attr("settimeId",setIntervalwaitTimeId);
}
function queueCall(json){
	var uniqueId = json.uniqueId.replace(".",'');
	$("#"+uniqueId).find("td").eq(5).text("响铃");
}
function leaveQueue(json){
	var q = $("#queueSelect").val();
	if(q != json.qid){
		return;	
	}
	$("#callNumber").text(parseInt($("#callNumber").text(),10)-1);
	$("#waitNormal").text(parseInt($("#waitNormal").text(),10)-1);
	var uniqueId = json.uniqueId.replace(".",'');
	clearInterval($("#"+uniqueId).attr("settimeId"));
	var cm = $("#"+uniqueId).find('td').eq(7).text();
	$("#"+uniqueId).remove();
	
	$('#callTable tr').each(function(){
		var tt = $(this).find('td').eq(7).text();
		if(tt != '排队位置'){
			if(tt > cm){
				$(this).find('td').eq(7).text(parseInt(tt,10)-1);
			}
		}
	});
}
function queueMonitoringStatus(json){
	//var status = window.parent.userBasic.getStatusText(json);
	var wp = window.parent.document.getElementById('toolbar');
	var status =wp.contentWindow.deviceStatus.deviceStatusLoginStatus(json.deviceStatus+json.loginStatus, json.pauseDescription, json.busyDescription);
	
	var imgSrc = "images/"+getStatusImg(status);
	
	//var cno = window.parent.userBasic.getCno();
	var cno = wp.contentWindow.userBasic.getCno();
	
	$("#cnoTable tr").each(function(){
		if($(this).find("td").eq(1).text() == json.cno){
			$(this).attr("statustr",status);
			
			var lastStatus = $(this).find("td").eq(0).find("img").attr("src",imgSrc).attr("title");
			if(lastStatus != '保持' && status != '通话'){
				if(status != '保持'){
					$("#"+json.cno+"_durationId").text("00:00:00");
					if(cno == json.cno){
						window.parent.document.getElementById('toolbar').contentWindow.document.getElementById('cnoTime').innerHTML="00:00:00";
					}
				}
			}else if(lastStatus != '保持' && status == '通话'){
				$("#"+json.cno+"_durationId").text("00:00:00");
				if(cno == json.cno){
					window.parent.document.getElementById('toolbar').contentWindow.document.getElementById('cnoTime').innerHTML="00:00:00";
				}
			}
		
			var loginTimeId = json.cno+"_loginTimeId";
			var durationId = json.cno+"_durationId";
			if(status == '通话'){
				$(this).find("td").eq(7).text(json.customerNumber);
			}
			if(status == "空闲" || status == "整理"){
				$(this).find("td").eq(7).text("--");
			}
			if(status == '离线'){
				clearInterval($("#"+json.cno+"_loginTimeId").attr("setIntervalloginTimeId"));
				clearInterval($("#"+json.cno+"_durationId").attr("setIntervaldurationId"));
				if(cno == json.cno){
					clearInterval(window.parent.document.getElementById('toolbar').contentWindow.document.getElementById('cnoTime').setIntervalCnoTimeId);
				}
				$("#"+loginTimeId).text("--");
				$("#"+durationId).text("--");
				$(this).find("td").eq(4).text("--");
				$(this).find("td").eq(7).text("--");
				$("#onlineCno").text(parseInt($("#onlineCno").text(),10)-1);
			}
			if($(this).find("td").eq(0).find("img").attr("src",imgSrc).attr("title") == '离线' &&　status　!= '离线'){
				$(this).find("td").eq(4).text("0");
				$("#"+loginTimeId).text(millisecondToDate(0));
				$("#"+durationId).text(millisecondToDate(0));
				var setIntervalloginTimeId = setInterval("timerCount('"+loginTimeId+"')", 1000);
				setIntlId.push(setIntervalloginTimeId);
				$("#"+loginTimeId).attr("setIntervalloginTimeId",setIntervalloginTimeId);
				var setIntervaldurationId = setInterval("timerCount('"+durationId+"')", 1000);	
				setIntlId.push(setIntervaldurationId);
				$("#"+durationId).attr("setIntervaldurationId",setIntervaldurationId);
				
				$("#onlineCno").text(parseInt($("#onlineCno").text(),10)+1);
			
				if(cno == json.cno){
					window.parent.document.getElementById('toolbar').contentWindow.document.getElementById('cnoTime').innerHTML=millisecondToDate(0);
					var setIntervalCnoTimeId = setInterval("timerCount('cnoTime')", 1000);
					setIntlId.push(setIntervalCnoTimeId);
					window.parent.document.getElementById('toolbar').contentWindow.document.getElementById('cnoTime').setIntervalCnoTimeId=setIntervalCnoTimeId;
				}
			}
			
			$(this).find("td").eq(0).find("img").attr("src",imgSrc).attr("title",status);
			if(cno != json.cno){
				switch(status){
					case "空闲":
						statusImg.disabled($(this),status);
					break;
					case "响铃":
						statusImg.ringing($(this),status);
					break;
					case "通话":
						statusImg.busy($(this),status);
					break;
					default:
						statusImg.disabled($(this),status);
				}
			}
		}
	});
	events.checkedTF(status);
}
var statusImg = {
	disabled : function(th,status){
		var spyObj = th.find(".monitoringBar_ul li[name='spy']");
		spyObj.attr("status",status);
		spyObj.find("span").eq(0).find("img").attr("src","mages/queueMonitoringIMG/spy01.png");
		
		var threewayObj = th.find(".monitoringBar_ul li[name='threeway']");
		threewayObj.attr("status",status);
		threewayObj.find("span").eq(0).find("img").attr("src","images/queueMonitoringIMG/threeway01.png");
		
		var whisperObj = th.find(".monitoringBar_ul li[name='whisper']");
		whisperObj.attr("status",status);
		whisperObj.find("span").eq(0).find("img").attr("src","images/queueMonitoringIMG/whisper01.png");
		
		var pickupObj = th.find(".monitoringBar_ul li[name='pickup']");
		pickupObj.attr("status",status);
		pickupObj.find("span").eq(0).find("img").attr("src","images/queueMonitoringIMG/pickup01.png");
		
		var disconnectsObj = th.find(".monitoringBar_ul li[name='disconnects']");
		disconnectsObj.attr("status",status);
		disconnectsObj.find("span").eq(0).find("img").attr("src","images/queueMonitoringIMG/disconnects01.png");
		
		var bargeObj = th.find(".monitoringBar_ul li[name='barge']");
		bargeObj.attr("status",status);
		bargeObj.find("span").eq(0).find("img").attr("src","mages/queueMonitoringIMG/barge01.png");
	},
	ringing : function(th,status){
		
		var spyObj = th.find(".monitoringBar_ul li[name='spy']");
		spyObj.attr("status",status);
		spyObj.find("span").eq(0).find("img").attr("src","mages/queueMonitoringIMG/spy01.png");
		
		var threewayObj = th.find(".monitoringBar_ul li[name='threeway']");
		threewayObj.attr("status",status);
		threewayObj.find("span").eq(0).find("img").attr("src","images/queueMonitoringIMG/threeway01.png");
		
		var whisperObj = th.find(".monitoringBar_ul li[name='whisper']");
		whisperObj.attr("status",status);
		whisperObj.find("span").eq(0).find("img").attr("src","images/queueMonitoringIMG/whisper01.png");
		
		var pickupObj = th.find(".monitoringBar_ul li[name='pickup']");
		pickupObj.attr("status",status);
		pickupObj.find("span").eq(0).find("img").attr("src","mages/queueMonitoringIMG/pickup02.png");
		
		var disconnectsObj = th.find(".monitoringBar_ul li[name='disconnects']");
		disconnectsObj.attr("status",status);
		disconnectsObj.find("span").eq(0).find("img").attr("src","images/queueMonitoringIMG/disconnects01.png");
		
		var bargeObj = th.find(".monitoringBar_ul li[name='barge']");
		bargeObj.attr("status",status);
		bargeObj.find("span").eq(0).find("img").attr("src","mages/queueMonitoringIMG/barge01.png");
	},
	busy : function(th,status){
		var spyObj = th.find(".monitoringBar_ul li[name='spy']");
		spyObj.attr("status",status);
		spyObj.find("span").eq(0).find("img").attr("src","images/queueMonitoringIMG/spy02.png");
		
		var threewayObj = th.find(".monitoringBar_ul li[name='threeway']");
		threewayObj.attr("status",status);
		threewayObj.find("span").eq(0).find("img").attr("src","images/queueMonitoringIMG/threeway02.png");
		
		var whisperObj = th.find(".monitoringBar_ul li[name='whisper']");
		whisperObj.attr("status",status);
		whisperObj.find("span").eq(0).find("img").attr("src","images/queueMonitoringIMG/whisper02.png");
		
		var pickupObj = th.find(".monitoringBar_ul li[name='pickup']");
		pickupObj.attr("status",status);
		pickupObj.find("span").eq(0).find("img").attr("src","images/queueMonitoringIMG/pickup01.png");
		
		var disconnectsObj = th.find(".monitoringBar_ul li[name='disconnects']");
		disconnectsObj.attr("status",status);
		disconnectsObj.find("span").eq(0).find("img").attr("src","images/queueMonitoringIMG/disconnects02.png");
		
		var bargeObj = th.find(".monitoringBar_ul li[name='barge']");
		bargeObj.attr("status",status);
		bargeObj.find("span").eq(0).find("img").attr("src","images/queueMonitoringIMG/barge02.png");
	}
}
function bindEvent(){
	$(".monitoringBar_ul li").unbind("click");
	$(".monitoringBar_ul li").click(function(){
		var name = $(this).attr("name");
		var status = $(this).attr("status");
		var cnoObject = $(this).attr("cno");
		
		var wp = window.parent.document.getElementById('toolbar');
		var thisCno = wp.contentWindow.userBasic.getCno();
		switch(name){
			case "spy" ://监听
			
				if(status == "通话"){
					$(".monitoringBar_ul").hide();
					var params={};
					params.spyObject = thisCno;
					params.objectType = '1';
					params.spiedCno = cnoObject;
					wp.contentWindow.executeAction('doSpy', params);
				}
			break;
			case "threeway" ://三方
				if(status == "通话"){
					var params={};
					params.threewayedCno = cnoObject;
					params.objectType = '1';
					params.threewayedObject = thisCno;
					
					wp.contentWindow.executeAction('doThreewayOk', params);
				}
			break;
			case "whisper" ://耳语
				if(status == "通话"){
					$(".monitoringBar_ul").hide();
					var params={};
					params.whisperedCno =  cnoObject;
					params.objectType = '1';
					params.whisperObject = thisCno;
					wp.contentWindow.executeAction('doWhisper', params);
				}
			break;
			case "pickup" ://抢线
				if(status == "响铃"){
					$(".monitoringBar_ul").hide();
					var params={};
					params.pickupCno = cnoObject;
					wp.contentWindow.executeAction('doPickup', params);
				}
			break;
			case "disconnects" ://强拆
				if(status == "通话"){
					$(".monitoringBar_ul").hide();
					var params={};
					params.disconnectedCno = cnoObject;
					wp.contentWindow.executeAction('doDisconnects', params);
				}
			break;
			case "barge" ://强插
				if(status == "通话"){
					$(".monitoringBar_ul").hide();
					var params={};
					params.bargedCno =  cnoObject;
					params.objectType = '1';
					params.bargeObject = thisCno;
					wp.contentWindow.executeAction('doBarge', params);
				}
			break;
		}
	});

}
function timerCount(id){
	var timeId = $("#"+id);
	var theDay = timeId.text();
	if(id == "cnoTime") {
		timeId = window.parent.document.getElementById('toolbar').contentWindow.document.getElementById('cnoTime');
		theDay = timeId.innerHTML;
	}
	
	
	var theDayStr = theDay.split(':');
	var second = parseInt(theDayStr[2],10);
	var minute = parseInt(theDayStr[1],10);
	var hour = parseInt(theDayStr[0],10);
	second++;
	if(second >= 60){
		minute = minute+1;
		second = 0;
	}
	if(minute >= 60){
		hour = hour+1;
		minute = 0;
	}
	if(second.toString().length <2 )second = "0"+second;
	if(minute.toString().length <2)minute = "0"+minute;
	if(hour.toString().length <2)hour = "0"+hour;
	var timeWrite = hour+":"+minute+":"+second;
	if(id == "cnoTime") {
		if(theDay == "" || theDay == null) {
		} else {
			timeId.innerHTML = timeWrite;
		}
	} else {
		timeId.text(timeWrite);
	}
}
Date.prototype.getTimes = function(){
	var d = new Date();
	var vYear = d.getFullYear();
	var vMon = d.getMonth() + 1
	var vDay = d.getDate();
	var h = d.getHours(); 
	var m = d.getMinutes();
	var s = d.getSeconds();
	var dates = vYear+"-"+(vMon<10 ? "0" + vMon : vMon)+"-"+(vDay<10 ? "0"+ vDay : vDay)+" "+(h<10 ? "0"+ h : h)+":"+(m<10 ? "0"+ m : m)+":"+(s<10 ? "0"+ s : s);	
	return dates;
}

function strategyString(strategy){
	var str='';
	switch(strategy){
		case "order":{
			str = '顺序';
			break;
		}
		case "rrmemory":{
			str = '轮选';
			break;
		}
		case "fewestcalls":{
			str = '平均';
			break;
		}
		case "random":{
			str = '随机';
			break;
		}
		case "skill":{
			str = '技能优先';
			break;
		}
		case "leastrecent":{
			str = '最长空闲时间';
			break;
		}
	}
	return str;
}
var _img = ["iconBell_01.png","iconIdle.png","iconBusy.png","iconLeave.png","iconCall01_03.png","iconIdle-01.png"];
getStatusImg = function(st){
	var statusImg='';
	switch(st){
		case '空闲' : statusImg = _img[1];break;
		case '响铃' : statusImg = _img[0];break;
		case '外呼中' : statusImg = _img[0];break;
		case '通话' : statusImg = _img[4];break;
		case '保持' : statusImg = _img[4];break;
		case '失效' : statusImg = _img[2];break;
		case '离线' : statusImg = _img[3];break;
		default : statusImg = _img[5];
	}
	return statusImg;
}
	
function millisecondToDate(msd) {
	var time = minute = hour = second = 0;
	if(msd == 0){
	   time = '00:00:00';
	   return time;
	}
	if(msd <= 60){
		if(msd < 10) msd = "0"+msd
		time = '00:00:'+msd;
		return time;
	}else{
		second = parseInt(msd -parseInt(60 * parseInt((msd / 60))));
		minute = parseInt(msd / 60);
		if(minute > 60){
			hour = parseInt(minute / 60);
			minute =parseInt(minute -parseInt(60 * parseInt((minute / 60))));
			
		}
	}	
	if(hour < 10)hour = "0"+hour;
	if(minute < 10)minute = "0"+minute;
	if(second < 10)second = "0"+second;
	time = hour+":"+minute+":"+second;
	return time;
}