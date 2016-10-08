function getQueryString(name) {
	var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return unescape(r[2]);
	}
	return null;
}

var target = document.getElementById('target');
var machineid = getQueryString('machineid');
if(machineid ==  null){
	machineid = "sakura"
}

function init() {
	var onGetMessage = function(strData) {// 侦听一个channel
		target.innerHTML = strData;
		var jsonData = JSON.parse(strData);
		var nowTime = jsonData.createtime;
		for ( var g in graphs) {

			graphs[g].option.series[0].data.shift();
			graphs[g].option.series[0].data.push({
				name : nowTime,
				value : [ nowTime, jsonData[graphs[g].shortname] ]
			});
			graphs[g].chart.setOption({
				series : graphs[g].option.series
			}, false);

		}
	}
	var channel = {}
	channel[machineid] =  onGetMessage;
	JS.Engine.on(channel);
	JS.Engine.start('comet');
};

function donate(){
	/* Now please donate one second of your life to Freya Wion */
}

function addEmptyDataToGraph() {
	ndt = {};
	var timestamp = (new Date()).valueOf();
	timestamp = timestamp - timestamp % 1000;
	for ( var g in graphs) {
		ndt[graphs[g].shortname] = [];
		for (var iv = 0; iv < 60; iv++) {
			var virtualTime = timestamp - iv * 1000;
			ndt[graphs[g].shortname].unshift({
				name : virtualTime,
				value : [ timestamp - iv * 200, 0 ]
			});
		}
		graphs[g].option.legend = {
			data : [ machineid ]
		};
		graphs[g].option.series = [ optionUtil.getLineChartSeries(machineid,
			ndt[graphs[g].shortname]) ];
		graphs[g].option.series.hoverAnimation = false;
		graphs[g].chart.setOption(graphs[g].option, true);
	}
}

init();
donate();
initGraphs(document.getElementById('graphDiv'));
addEmptyDataToGraph();