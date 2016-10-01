/**
 * 欢迎来到德莱联盟
 * Welcome to Summoner's Rift
 */

var realTimeButtons = document.getElementById("realtimeButtons");

var historyRoot = document.getElementById("historyRoot");

function  init(){
	$.ajax({
		url : './servlet/GetCurrentStatus',
		type : 'post',
		dataType : 'json',
		data : {
			mod : 'name'
		},
		success : function(data) {
			machineIds = data;
			for(var i in machineIds){
				var al = document.createElement('a');
				al.href =  './clusterrealtime.html?machineid=' + machineIds[i];
				// al.type = 'button'; //attribute dose not exist in a label
				al.innerText = machineIds[i];
				realTimeButtons.appendChild(al);
			}
		}
	});
	
};

init();