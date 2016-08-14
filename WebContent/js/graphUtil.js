var graphs = [ {
	name : 'CPU',
	divname : 'cpudiv',
	ymax : 1
}, {
	name : 'Memory',
	divname : 'memdiv'
}, {
	name : 'Network Send',
	divname : 'ntsdiv'
}, {
	name : 'Network Receive',
	divname : 'ntrdiv'
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
		graphs[i].shortname = graphs[i].divname.substring(0, 3);

		graphs[i].option = optionUtil.getLineChartOption();
		graphs[i].option.title.text = graphs[i].name;
		if (graphs[i].ymax) {
			graphs[i].option.yAxis.max = graphs[i].ymax;
		}
	}
}