/**
 * 
 */
var out = document.getElementById('out');

var multiGraph = false;
var dataInShowing = [];

var dataFromServer;// all the data is stored here as tree
var treeOfData;
var mapOfData;

var dataAttrs = [ {
	h5 : 'Job id',
	divname : 'jobidDiv',
	attrname : 'jobid'
}, {
	h5 : 'Start Time',
	divname : 'starttimeDiv',
	attrname : 'starttime'
}, {
	h5 : 'End Time',
	divname : 'endtimeDiv',
	attrname : 'endtime'
}, {
	h5 : 'Machine Id',
	divname : 'machineidDiv',
	attrname : 'machineid'
} ];



var currentSelection = [];

function clearSearch(level) {
	for (var i = level + 1; i < 4; i++) {
		var buttons = document.getElementById(dataAttrs[i].divname);
		buttons.innerHTML = '';
	}
	currentSelection = currentSelection.slice(0, level);
}

function refactorData(dt, callback) {
	if (!dt) {
		alert('trying to deal with null data');
		return;
	}
	if (dt.isItem) {
		dt = dt.item;
	}
	if (dt.value) {
		var ndt = {};
		for ( var g in graphs) {
			ndt[graphs[g].shortname] = [];
		}
		records = JSON.parse(dt.value);
		for ( var i in records) {
			for ( var g in graphs) {
				ndt[graphs[g].shortname].push([ records[i].CreateTime,
					records[i][graphs[g].name] ]);
			}
		}
		// out.innerText = JSON.stringify(ndt);
		callback(ndt, dt);
	}
	// TODO
}

function renderOptionButton() {
	function createbutton(text, clickcallback) {
		var newbutton = document.createElement('button');
		newbutton.innerText = text;
		newbutton.onclick = clickcallback;
		return newbutton;
	}

	var optionDiv = document.getElementById('optionDiv');
	optionDiv.appendChild(createbutton('single graph', function() {
		multiGraph = false;
	}));
	optionDiv.appendChild(createbutton('multi graph', function() {
		multiGraph = true;
	}));
	optionDiv.appendChild(createbutton('delete last one', function() {
		for ( var g in graphs) {
			if (graphs[g].option.series.length > 0) {
				graphs[g].option.series.pop();
			}
			graphs[g].chart.setOption(graphs[g].option, true)
		}
		mapOfData[dataInShowing.pop()].isShowing = false;
		;

	}));
}

function renderInfoData(tree) {
	function renderFourGraphs(ndt, odt) {
		if (multiGraph && mapOfData[odt.id].isShowing) {
			return;
		} else {
			if (!multiGraph) {
				while (dataInShowing.length > 0) {
					mapOfData[dataInShowing.pop()].isShowing = false;
				}
			}
			mapOfData[odt.id].isShowing = true;
			dataInShowing.push(odt.id);
		}
		for ( var g in graphs) {
			var newoption = optionUtil.getLineChartSeries(odt.machineid,
				ndt[graphs[g].shortname]);
			if (multiGraph) {
				graphs[g].option.series.push(newoption);
				graphs[g].option.legend.data.push(odt.machineid);
			} else {
				graphs[g].option.series = [ newoption ];
				graphs[g].option.legend.data = [ odt.machineid ];
			}
			graphs[g].chart.setOption(graphs[g].option, true);
		}
	}
	var table = document.getElementById('infoTable');
	var tablerowString = '<tr><td><h5>h5str</h5></td>'
		+ '<td><div id="divname"></div></td></tr>';

	var tbd = '<tbody>';
	for ( var i in dataAttrs) {
		tbd += tablerowString.replace('h5str', dataAttrs[i].h5).replace(
			'divname', dataAttrs[i].divname);
	}
	tbd += '</tbody>';
	table.innerHTML = tbd;

	var jobidDiv = document.getElementById(dataAttrs[0].divname);
	tree.render(currentSelection.slice(0), jobidDiv, function() {
		clearSearch(0);
		currentSelection[0] = this.innerText;
		var starttimeDiv = document.getElementById(dataAttrs[1].divname);
		tree.render(currentSelection.slice(0), starttimeDiv, function() {
			clearSearch(1);
			currentSelection[1] = this.innerText;
			var endtimeDiv = document.getElementById(dataAttrs[2].divname);
			tree.render(currentSelection.slice(0), endtimeDiv, function() {
				clearSearch(2);
				currentSelection[2] = this.innerText;
				var machineidDiv = document
					.getElementById(dataAttrs[3].divname);
				tree.render(currentSelection.slice(0), machineidDiv,
					function(item) {
						var _data = tree.get(currentSelection
							.concat(this.innerText));
						// following statement render the four graphs
						refactorData(_data, renderFourGraphs);
					});
			});
		});
	});
}



function addData() {
	var tree = new CluDataTree();
	var list = {};
	for ( var d in dataFromServer) {
		tree.add(dataFromServer[d], dataAttrs.map(function(t){
			return t.attrname;
		}));
		list[dataFromServer[d].id] = dataFromServer[d];
	}
	return {
		tree : tree,
		list : list
	};
}

function onload() {
	/*
	 * function onLoadRequestHandler() { if (onLoadRequest.readyState == 4) { if
	 * (onLoadRequest.status == 200) { dataFromServer =
	 * JSON.parse(onLoadRequest.responseText); dataResult = addData();
	 * treeOfData = dataResult.tree; mapOfData = dataResult.list;
	 * renderOptionButton(); renderInfoData(treeOfData); initGraphs(); } } }
	 * 
	 * var onLoadRequest = new XMLHttpRequest();
	 * onLoadRequest.onreadystatechange = onLoadRequestHandler;
	 * onLoadRequest.open('get',
	 * './servlet/ShowClusterHistory?mod=start&dbsource=default');
	 * onLoadRequest.send();
	 */
	$.ajax({
		url : './servlet/ShowClusterHistory',
		type : "post",
		dataType : "json",
		data : {
			mod : 'start',
			dbsource : 'default'
		},
		success : function(data) {
			dataFromServer = data;
			dataResult = addData();
			treeOfData = dataResult.tree;
			mapOfData = dataResult.list;
			renderOptionButton();
			renderInfoData(treeOfData);
			initGraphs();
		}
	});
}
