/**
 * 
 */
var xmlhttp;
url = './Test'

function handle() {
	 if(xmlhttp.readyState==4)  
	 {
		 if(xmlhttp.status == 200){
			 document.getElementById('myDiv').innerHTML = xmlhttp.responseText;
		 }
	 }
}

function loadXMLDoc() {
	xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=handle;
	xmlhttp.open("get",url,true);
	xmlhttp.send();
}