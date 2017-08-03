var stompClient = null;
var sockJS = null;
function setConnected(connected) {
    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
    document.getElementById('response').innerHTML = '';
}

function connect() {
	console.log('connecting... ');
	//sockJS = new SockJS('http://localhost:8080/hello');
    stompClient = Stomp.client('ws://localhost:8080/hello');
    stompClient.debug = function(d){console.log(d)};
    stompClient.connect({'passcode':'adC123456x','login':'dddddd'}, function(frame) {
    	
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function(greeting){
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
	console.log('closing... ');
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    var name = document.getElementById('name').value;
    stompClient.send("/app/hello", {}, JSON.stringify({ 'name': name }));
}

function showGreeting(message) {
    var response = document.getElementById('response');
    var p = document.createElement('p');
    p.style.wordWrap = 'break-word';
    p.appendChild(document.createTextNode(message));
    response.appendChild(p);
}

$(document).ready(function(){ 
	$('#login').bind('click', function(){
		var data = {
			principal: $('#user').val(),
			credential:$('#pass').val(),
			audience:'sync001'
		}
		$.ajax({
			url: 'gpapi/authenticate.do',
			data: JSON.stringify(data),
			type: 'post',
			contentType: "application/json; charset=utf-8",
			dataType: 'json',
			success:function(data) {  
				$('#api-result').html(data);
			}
		});
	});
}) 