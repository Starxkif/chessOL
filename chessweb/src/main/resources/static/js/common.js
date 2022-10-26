var com = com || {};
var map=""
/*界面初始化*/
com.init = function (stype) {
    com.nowStype = stype || "stype1";
    var stype = com.stype[com.nowStype];
    com.width = stype.width;		//画布宽度
    com.height = stype.height; 		//画布高度
    com.pointStartX = stype.pointStartX;	//第一个着点X坐标;
    com.pointStartY = stype.pointStartY;	//第一个着点Y坐标;
    com.leftMargin = stype.leftMargin;  //左边空白距离
    com.topMargin = stype.topMargin;    //上面空白距离
    com.spaceX = stype.spaceX;		//着点X跨度
    com.spaceY = stype.spaceY;		//着点Y跨度
    com.page = stype.page;			//图片相对路径

    let ww = com.width + 130;
    $("#box").attr("style", "width:" + ww + "px");
    com.canvas = $("#chess")[0]; //画布
    com.ct = com.canvas.getContext("2d");
    com.canvas.width = com.width;
    com.canvas.height = com.height;
    com.loadImages(com.page);		//载入图片/图片目录
}

//样式
com.stype = {
    stype1: {
        width: 520,		//画布宽度
        height: 570, 		//画布高度
        spaceX: 53,		//着点X跨度
        spaceY: 48,		//着点Y跨度
        pointStartX: 22,		//第一个着点X坐标;
        pointStartY: 44,		//第一个着点Y坐标;
        page: "stype_1",	//图片目录
        leftMargin: 430,
        topMargin: 96

    },
    stype2: {
        width: 520,		//画布宽度
        height: 570, 		//画布高度
        spaceX: 56,		//着点X跨度
        spaceY: 55,		//着点Y跨度
        pointStartX: 5,		//第一个着点X坐标;
        pointStartY: 14,		//第一个着点Y坐标;
        page: "stype_2",	//图片目录
        leftMargin:411,
        topMargin:62
    }
}
$(function () {
    com.init("stype1");
    // drawAll();
})
//风格按钮单击事件
$("#stypeBn").click(function () {
    var stype = com.nowStype;
    if (stype == "stype1") stype = "stype2";
    else if (stype == "stype2") stype = "stype1";
    com.init(stype);
    // document.cookie = "stype=" + stype;
    clearInterval(timer);
    var i = 0;
    var timer = setInterval(function () {
        drawAll();
        if (i++ >= 5) clearInterval(timer);
    }, 2000);
});

//载入图片
com.loadImages = function (page) {

    //绘制棋盘
    com.bgImg = new Image();
    com.bgImg.src = "img/" + page + "/bg.png";

    //提示点
    com.dotImg = new Image();
    com.dotImg.src = "img/" + page + "/dot.png";

    //棋子
    com.chessImg = {};
    for (var key in imgMap) {
        com.chessImg[key] = new Image();
        com.chessImg[key].src = "img/" + page + "/" + imgMap[key];
    }
    //棋子外框
    com.paneImg = new Image();
    com.paneImg.src = "img/" + page + "/0board.png";

    document.getElementsByTagName("body")[0].style.background = "url(img/" + page + "/bg.jpg)";

}
var imgMap = {
    'B': '0b.png',
    'C': '0c.png',
    'J': '0j.png',
    'M': '0m.png',
    'P': '0p.png',
    'S': '0s.png',
    'X': '0x.png',
    'b': '1b.png',
    'c': '1c.png',
    'j': '1j.png',
    'm': '1m.png',
    'p': '1p.png',
    's': '1s.png',
    'x': '1x.png',
}
getXY = (canvas, x, y) => {
    let style = window.getComputedStyle(canvas, null);
    //宽高
    let cssWidth = parseFloat(style["width"]);
    let cssHeight = parseFloat(style["height"]);
    //各个方向的边框长度
    let borderLeft = parseFloat(style["border-left-width"]);
    let borderTop = parseFloat(style["border-top-width"]);
    let paddingLeft = parseFloat(style["padding-left"]);
    let paddingTop = parseFloat(style["padding-top"]);

    let scaleX = canvas.width / cssWidth; // 水平方向的缩放因子
    let scaleY = canvas.height / cssHeight; // 垂直方向的缩放因子

    let rect = canvas.getBoundingClientRect();
    x -= (rect.left + borderLeft + paddingLeft); // 去除 borderLeft paddingLeft 后的坐标
    y -= (rect.top + borderTop + paddingTop); // 去除 borderLeft paddingLeft 后的坐标

    x *= scaleX; // 修正水平方向的坐标
    y *= scaleY; // 修正垂直方向的坐标

    return {x, y}
};