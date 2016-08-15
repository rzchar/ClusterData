var graphs = [ {
	name : 'CPU',
	divname : 'cpudiv',
	shortname : 'cpu',
	unit : '',
	ymax : 1
}, {
	name : 'Memory',
	divname : 'memdiv',
	unit : 'MB',
	shortname : 'mem'
}, {
	name : 'Network Send',
	divname : 'ntsdiv',
	unit : 'Byte',
	shortname : 'nts'
}, {
	name : 'Network Receive',
	divname : 'ntrdiv',
	unit : 'Byte',
	shortname : 'ntr'
} ];

function initGraphs() {
	var graphRoot = document.getElementById('graphDiv');
	for ( var i in graphs) {
		var element = document.createElement('div');
		element.id = graphs[i].divname;
		element.style.height = '400px';
		element.style.width = '40%';
		graphRoot.appendChild(element);
		graphs[i].chart = echarts.init(document
			.getElementById(graphs[i].divname));
		graphs[i].option = optionUtil.getLineChartOption();
		graphs[i].option.title.text = graphs[i].name;
		if (graphs[i].ymax) {
			graphs[i].option.yAxis.max = graphs[i].ymax;
		}
	}
}