/**
 * 欢迎来到德莱联盟
 */

var realTimeRoot = document.getElementById("reatTimeRoot");

var historyRoot = document.getElementById("historyRoot");

function  init(){
	var label1 = document.createElement("label");
	label1.innerText = "Real Time";
	reatTimeRoot.appendChild(label1);
};
init();