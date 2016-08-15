var target = document.getElementById('target');
var machineid = '127.0.0.1'

function init() {
	JS.Engine.on({
		test : function(strData) {// 侦听一个channel
			target.innerHTML = strData;
			var jsonData = JSON.parse(strData);
			var nowTime = jsonData.createtime;
			for ( var g in graphs) {
				graphs[g].option.series[0].data.shift();
				graphs[g].option.series[0].data.push([ nowTime,
					jsonData[graphs[g].shortname] ]);
				graphs[g].chart.setOption({
					series : graphs[g].option.series
				}, false);
			}
		}
	});
	JS.Engine.start('comet');
};

function addEmptyDataToGraph() {
	ndt = {};
	var timestamp = (new Date()).valueOf();
	timestamp = timestamp - timestamp % 1000;
	for ( var g in graphs) {
		ndt[graphs[g].shortname] = [];
		for (var iv = 0; iv < 60; iv++) {
			ndt[graphs[g].shortname].unshift([ timestamp - iv * 1000, 0 ]);
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
initGraphs();
addEmptyDataToGraph();