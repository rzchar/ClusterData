CluDataTree = function() {
	this.init();
}

CluDataTree.prototype.init = function() {
	this.root = {};
}

CluDataTree.prototype.add = function(item, attrNameArray) {
	var pointer = this.root;
	while (attrNameArray.length > 0) {
		var attrName = attrNameArray.shift();
		if (pointer[item[attrName]] == undefined) {
			pointer[item[attrName]] = {};
		}
		pointer = pointer[item[attrName]];
	}
	pointer.isItem = true;
	pointer.item = item;
}

CluDataTree.prototype.get =function(attrs){
	var pointer = this.root;
	var items;
	while (attrs.length > 0) {
		var attr = attrs.shift();
		pointer = pointer[attr];
		if (pointer == undefined) {
			return null;
		}
	}
	return pointer;
} 

CluDataTree.prototype.render = function(attrs, rootNode, onclickFun) {
	var pointer = this.root;
	var items;
	while (attrs.length > 0) {
		var attr = attrs.shift();
		pointer = pointer[attr];
		if (pointer == undefined) {
			break;
		}
	}
	for ( var i in pointer) {
		var ab = document.createElement('button');
		ab.onclick = onclickFun
		ab.innerText = i;
		rootNode.appendChild(ab);
	}

}