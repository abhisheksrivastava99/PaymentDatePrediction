
var page=0; //global variable for page number
searchData() //Calling searchData() function to print table with fetched data from server
//function for right scroll button (pagination)
function inc(){
	
	page+=1;//increasing page value by 1
	document.getElementById("myTable").innerHTML=""; // no table displaying now
	searchData() //building table gain with new page value
	if(page>0){
		document.getElementById("dec").disabled=false; //If page number is greater than 0 then enabling left scroll button
	}
	if(page<1){
		document.getElementById("dec").disabled=true; //If page number is less than 1 then disabling left scroll button
	}
}
function dec(){
	
	page=page-1;//decreasing page value by 1
	document.getElementById("myTable").innerHTML="";//no table displaying now
	searchData()//building a new table with new page number value
	if(page>0){
		document.getElementById("dec").disabled=false; //If page number is greater than 0 left scroll button is enabled
	}
	if(page<1){
		document.getElementById("dec").disabled=true;//If page number is less than 1 then disabling left scroll button
	}
}

//Function to build a table with 16 rows and dependent on page number
function buildTable(data){
	var table = document.getElementById('myTable')//Getting table body
	
//for loop to generate rows of table(Using jquery here)
	for (var i = page*16; i < page*16+16; i++){   			// from 0 to 15 rows if page=0
	var rId="row_"+i; //String concatenation for row id 
	var row = `<tr id=${rId}>
	<td><input type="checkbox" style="width: 16px;height: 16px;border: 2px solid #97A1A9;border-radius: 3px;opacity: 1;" id=${i}></input></td>
	<td>${data[i].CName}</td>
	<td>${data[i].CNum}</td>
	<td>${data[i].INum}</td>
	<td>${data[i].IAmt}</td>
	<td>${data[i].DD}</td>
	<td>${data[i].PPD}</td>
	<td>${data[i].Notes}</td>
	 </tr>`
	table.innerHTML += row

	}
	}


//Function to reset add modal reset button
//Assigning all input fields as blank
function clearFields(){
document.getElementById("cninp").value="";
document.getElementById("cnoinp").value="";
document.getElementById("dinp").value="";
document.getElementById("noteinp").value="";
document.getElementById("invnoinp").value="";
document.getElementById("invamtinp").value="";


}
//Function to reset edit modal reset button
//Assigning all input fields as blank
function resetFields(){
document.getElementById("edinp1").value="";
document.getElementById("edinp2").value="";

//Function to disable submit button of add modal 
}
function DAS(){
var cn = document.getElementById("cninp").value;
var cno = document.getElementById("cnoinp").value;
var d = document.getElementById("dinp").value;
var note = document.getElementById("noteinp").value;
var invno = document.getElementById("invnoinp").value;
var invamt = document.getElementById("invamtinp").value;
if(cn!='' && cno!='' && d!='' && note!='' && invno!='' && invamt!=''){
	document.getElementById("addSubmit").disabled=false;
}
else{
	document.getElementById("addSubmit").disabled=true;
}
}
//Mouseover eventlistener  on modal to call function continuously DAS
document.getElementById("myModal").addEventListener("mouseover",DAS)

//Function to disable edit modal submit button
function DES(){

var note = document.getElementById("edinp2").value;
var invamt = document.getElementById("edinp1").value;
if(note!='' && invamt!=''){
	document.getElementById("edSub").disabled=false;
}
else{
	document.getElementById("edSub").disabled=true;
}
}
//Mouseover eventlistener  on modal to call function continuously DES
document.getElementById("editModal").addEventListener("mouseover",DES)

//Count the number of checked checkboxes on the screen
function count(){
var s=0;//counter variable s

for(var i=page*16;;i++){ //checkbox of same page
    if(document.getElementById(i).checked){//if checked then counter variable increment
        s=s+1;
}
    if(document.getElementById(i+1)==null){//if no row ahead then break and return counter
    	return s;
    }
}
}
//Function to disable add edit and delete
function disableBtns(){
	if(count()==0){//count returning count of checked boxes
		document.getElementById("delBtn").disabled=true;
		document.getElementById("editModalBtn").disabled=true;
		document.getElementById("addModalBtn").disabled=false;//only add button enabled
	}
	if(count()==1){//if count is 1
		document.getElementById("delBtn").disabled=false;
		document.getElementById("editModalBtn").disabled=false;
		document.getElementById("addModalBtn").disabled=true;
	}
	if(count()>1){
		document.getElementById("delBtn").disabled=false;
		document.getElementById("editModalBtn").disabled=true;
		document.getElementById("addModalBtn").disabled=true;
	}
}

document.getElementById("tb").addEventListener("mouseover",disableBtns)


function edit(){
	if(count()==1){
	for(var v=page*16;v<page*16+16;v++){
	    if(document.getElementById(v).checked){
	        var InvNo = document.getElementById("row_"+v).cells[3].innerHTML;
	        break
	    }
	}
	document.getElementById("identEd").value=InvNo;
	}
	
}

function del(){
	
	var data="";
	for(var m=page*16;;m++){
	    if(document.getElementById(m).checked){
	        data += document.getElementById("row_"+m).cells[3].innerHTML+" ";
	        
	    }
	    if(document.getElementById(m+1)==null){
	    	break
	    }
	}
    document.getElementById("delInp").value=data;
	
}


var dl = 0;//Total Number Of Pages
var d=0; // Total Number of Rows
function searchData(){

	var value = document.getElementById("search").value;
	var req=new XMLHttpRequest();
    req.open("GET","http://localhost:8080/Summer_Internship_Backend/FetchSearch?value="+value,true);
    req.send();
    var json=null;
    document.getElementById("myTable").innerHTML="";
    req.onload=function(){
        json=JSON.parse(req.responseText);
        dl=parseInt((json.length)/16)
        d=json.length
        if(d==0){
        	var str = `<div id="error">
        			
        		<img id="erimg" src="images/error_outline-24px.svg"/><br><br>
        				<p id="err1">No Results Found</p>
        				<p id="err2">Try adjusting your search to find what you are looking for</p>
        				<button class="btn" id="errorBtn" onclick="clearSearch()">Clear Search</button>
        				</div>
        	`;
        	
        	document.getElementById("myTable").innerHTML=str;
        	document.getElementById("header").style.display="none";
        	document.getElementById("dec").disabled=true;
        	document.getElementById("inc").disabled=true;
        }
        else{
        	if(page>0){
        	document.getElementById("dec").disabled=false;
        }	
        	if(page!=dl){
            	document.getElementById("inc").disabled=false;
            	}
        	if(page==dl){
            	document.getElementById("inc").disabled=true;
            	}
        	document.getElementById("header").style.display="";
        buildTable(json)
        }
    }
    
    
    
}



function initialiseTable(){
	
	document.getElementById("myTable").innerHTML="";
	
	page=0
	searchData()
}
document.getElementById("search").addEventListener("keyup",initialiseTable)

function clearSearch(){
	document.getElementById("search").value="";
	searchData()
}