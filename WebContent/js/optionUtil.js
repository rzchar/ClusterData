/**
 * 
 */

function optionUtil() {
}

optionUtil.getLineChartOption = function() {
	return {
		title : {
			text : ''
		},
		tooltip : {
			trigger : 'axis',
			formatter : function(params) {
				params = params[0];
				var date = new Date(parseInt(params.value[0]));
				return date.getMinutes() + ':' + date.getSeconds() + ':'
					+ date.getMilliseconds() + ' => ' + params.value[1];
			},
		},
		xAxis : {
			type : 'time',
			splitLine : {
				show : false
			}
		},
		yAxis : {
			type : 'value',
			boundaryGap : [ '0' , '20%' ],
			splitLine : {
				show : false
			},
			axisLabel : {
				margin : 5,
				formatter : function(value, index) {
					var appendText = [ '', 'K', 'M', 'G', 'T', 'P' ];
					while (value >= 1000) {
						value = value / 1000;
						appendText.shift();
					}
					return value + appendText[0];
				}
			}
		},
		legend : {
			data : []
		},
		series : []
	};
}

optionUtil.getLineChartSeries = function(nm, dt) {
	return {
		name : nm == undefined ? '' : nm,
		type : 'line',
		showSymbol : false,
		data : dt == undefined ? [] : dt,
		lineStyle : {
			normal : {
				width : 1
			}
		}
	}
}