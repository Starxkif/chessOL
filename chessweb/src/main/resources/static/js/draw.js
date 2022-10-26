function drawAll() {
    drawBG();
    drawChess();
}

function drawChess() {
    console.log(map)
    com.ct.save();
    for (let y = 0; y < 10; y++) {
        for (let x = 0; x < 9; x++) {
            let ch = map[y * 9 + x];
            if (ch != ' ') {
                com.ct.drawImage(com.chessImg[ch], com.spaceX * x + com.pointStartX, com.spaceY * y + com.pointStartY,52,52);
            }
        }
    }
    console.log("输出棋子")
    com.ct.restore();
}

function drawBG() {
    console.log("输出棋盘")
    com.ct.drawImage(com.bgImg, 10,10,500,560);
}

function drawBorder() {
    com.ct.drawImage(com.paneImg, com.spaceX * this.x + com.pointStartX, com.spaceY * this.y + com.pointStartY)
    com.ct.drawImage(com.paneImg, com.spaceX * this.newX + com.pointStartX, com.spaceY * this.newY + com.pointStartY)
}

function drawDot() {
    this.dots = []
    for (var i = 0; i < this.dots.length; i++) {
        if (this.isShow) com.ct.drawImage(com.dotImg, com.spaceX * this.dots[i][0] + 10 + com.pointStartX, com.spaceY * this.dots[i][1] + 10 + com.pointStartY)
    }
}