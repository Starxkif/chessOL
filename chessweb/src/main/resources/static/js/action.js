//画布中的点击事件
$("#chess").click(function (event) {
    //x轴是列号，y是行号
    // console.log(Math.floor(event.pageX-411)/56)
    // console.log(Math.floor(event.pageY-62)/55)
    // console.log(((event.pageX - 430) / 53)+","+((event.pageY - 96) / 48));
    let x = Math.floor((event.pageX - com.leftMargin) / com.spaceX);
    let y = Math.floor((event.pageY - com.topMargin) / com.spaceY);
    if (x < 9 && y < 10 && x >= 0 && y >= 0) {
        let msg = {
            'flag': 1,
            'x': x,
            'y': y
        }
        send(msg)
    }
})
