<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>WebSocket/SockJS Echo Sample (Adapted from Tomcat's echo sample)</title>
  <style type="text/css">
    #connect-container {
      float: left;
      width: 400px
    }

    #connect-container div {
      padding: 5px;
    }

    #console-container {
      float: left;
      margin-left: 15px;
      width: 400px;
    }

    #console {
      border: 1px solid #CCCCCC;
      border-right-color: #999999;
      border-bottom-color: #999999;
      height: 170px;
      overflow-y: scroll;
      padding: 5px;
      width: 100%;
    }

    #console p {
      padding: 0;
      margin: 0;
    }
  </style>
  <c:set var="ctx" value="${pageContext.request.contextPath}" />
  <script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>
  <link href="${ctx}/static/lib/flat-ui/dist/css/vendor/bootstrap.min.css" rel="stylesheet" />
  <link href="${ctx}/static/lib/flat-ui/dist/css/flat-ui.css" rel="stylesheet" />
  <link href="${ctx}/static/css/chat.css" rel="stylesheet" />
  <script type="text/javascript">
    var ws = null;
    var url = null;
    var transports = [];

    function setConnected(connected) {
      document.getElementById('connect').disabled = connected;
      document.getElementById('disconnect').disabled = !connected;
      document.getElementById('echo').disabled = !connected;
    }

    function connect() {
      if (!url) {
        alert('Select whether to use W3C WebSocket or SockJS');
        return;
      }
      ws = new WebSocket('ws://localhost:8080/ws');
//      if ('WebSocket' in window) {
//        alert("WebSocket");
//        websocket = new WebSocket("ws://localhost:8080/ws");
//      } else if ('MozWebSocket' in window) {
//        alert("MozWebSocket");
//        websocket = new MozWebSocket("ws://ws");
//      } else {
//
//        alert("SockJS");
//        websocket = new SockJS("http://localhost:8080/sockjs/ws");
//      }

      ws.onopen = function () {
        setConnected(true);
        log('Info: connection opened.');
      };

      ws.onmessage = function (event) {
        log('Received: ' + event.data);
      };

      ws.onclose = function (event) {
        setConnected(false);
        log('Info: connection closed.');
        log(event);
      };
    }

    function disconnect() {
      if (ws != null) {
        ws.close();
        ws = null;
      }
      setConnected(false);
    }

    function echo() {
      if (ws != null) {
        var message = document.getElementById('message').value;
        log('Sent: ' + message);
        ws.send(message);
      } else {
        alert('connection not established, please connect.');
      }
    }

    function updateUrl(urlPath) {
      console.log('URL:' + url);
      if (urlPath.indexOf('sockjs') != -1) {
        url = urlPath;
        document.getElementById('sockJsTransportSelect').style.visibility = 'visible';
      }
      else {
        if (window.location.protocol == 'http:') {
          url = 'ws://' + window.location.host + urlPath;
        } else {
          url = 'wss://' + window.location.host + urlPath;
        }
        console.log('----' + url);
        document.getElementById('sockJsTransportSelect').style.visibility = 'hidden';
      }
    }

    function updateTransport(transport) {
      transports = (transport == 'all') ? [] : [transport];
    }

    function log(message) {
      console.log(message);
      var consoleDiv = document.getElementById('console');
      var p = document.createElement('p');
      p.style.wordWrap = 'break-word';
      p.appendChild(document.createTextNode(message));
      consoleDiv.appendChild(p);
      while (consoleDiv.childNodes.length > 25) {
        consoleDiv.removeChild(consoleDiv.firstChild);
      }
      consoleDiv.scrollTop = consoleDiv.scrollHeight;
    }
  </script>
</head>

<body>
  <noscript>
    <h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websockets
    rely on Javascript being enabled. Please enable
    Javascript and reload this page!</h2>
  </noscript>
  <div>
    <div id="connect-container">
      <input id="radio1" type="radio" name="group1" onclick="updateUrl('${contextPath}/webSocketServer');">
      <label for="radio1">W3C WebSocket</label>
      <br>
      <input id="radio2" type="radio" name="group1" onclick="updateUrl('/spring-websocket-test/sockjs/echo');">
      <label for="radio2">SockJS</label>
      <div id="sockJsTransportSelect" style="visibility:hidden;">
        <span>SockJS transport:</span>
        <select onchange="updateTransport(this.value)">
          <option value="all">all</option>
          <option value="websocket">websocket</option>
          <option value="xhr-polling">xhr-polling</option>
          <option value="jsonp-polling">jsonp-polling</option>
          <option value="xhr-streaming">xhr-streaming</option>
          <option value="iframe-eventsource">iframe-eventsource</option>
          <option value="iframe-htmlfile">iframe-htmlfile</option>
        </select>
      </div>
      <div>
        <button id="connect" onclick="connect();">Connect</button>
        <button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button>
      </div>
      <div>
        <textarea id="message" style="width: 350px">Here is a message!</textarea>
      </div>
      <div>
        <button id="echo" onclick="echo();" disabled="disabled">Echo message</button>
      </div>
    </div>
    <div id="console-container">
      <div id="console"></div>
    </div>
  </div>
</body>
</html>