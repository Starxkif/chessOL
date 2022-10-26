var websocket = null;
//判断当前浏览器是否支持WebSocket
if ('WebSocket' in window) {
    websocket = new WebSocket("ws://127.0.0.1:8080/websocket/"+com.account);
} else {
    alert('当前浏览器 Not support websocket')
}

//连接发生错误回调方法
websocket.onerror = function () {
    console.log("WebSocket连接发生错误");
};

//连接成功建立回调方法
websocket.onopen = function () {
    console.log("WebSocket连接成功");
}

//接收消息回调方法
websocket.onmessage = function (event) {
    let data = JSON.parse(event.data);
    if (data.flag == 2) {
        map = data.map;
        // console.log(data.curPlay)
        if (data.curPlay == 0) {
            $("#moveInfo").text("红方走")
        }else{
            $("#moveInfo").text("黑方走")
        }
    }
    drawAll();
}

//连接关闭回调方法
websocket.onclose = function () {
    console.log("WebSocket连接关闭");
}

//监听窗口关闭事件
// window.onbeforeunload = function () {
//     closeWebSocket();
// }

//关闭WebSocket连接
function closeWebSocket() {
    websocket.close();
}

//发送消息
function send(msg) {
    websocket.send(JSON.stringify(msg));
}

