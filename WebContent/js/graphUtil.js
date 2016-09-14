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

function initGraphs(graphRoot) {
	var maxGraphsInRow = 2;
	var graphTable = document.createElement('table');
	graphTable.style.width='100%';
	var tbody = document.createElement('tbody');
	graphRoot.appendChild(graphTable);
	graphTable.appendChild(tbody);
	var currentTR = document.createElement('tr');
	tbody.appendChild(currentTR);
	//currentTR.style.width='100%';
	for ( var i in graphs) {
		var element = document.createElement('div');
		element.id = graphs[i].divname;
		element.style.height = '400px';
		element.style.width = '100%';
		var td = document.createElement('td');
		td.style.width = 100 / maxGraphsInRow + '%';
		td.appendChild(element);
		currentTR.appendChild(td);
		if(currentTR.childNodes.length == maxGraphsInRow){
			currentTR = document.createElement('tr');
			tbody.appendChild(currentTR);
		}
	}
	for ( var i in graphs) {
		graphs[i].chart = echarts.init(document
			.getElementById(graphs[i].divname));
		graphs[i].option = optionUtil.getLineChartOption();
		graphs[i].option.title.text = graphs[i].name;
		if (graphs[i].ymax) {
			graphs[i].option.yAxis.max = graphs[i].ymax;
		}
	}
}