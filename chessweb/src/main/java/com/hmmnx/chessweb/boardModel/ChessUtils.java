package com.hmmnx.chessweb.boardModel;

/**
 * @program: chessOL
 * @description:
 * @author: ZAM
 * @create: 2022-10-23 21:06
 **/
public class ChessUtils {
    static RuleUtils ru = new RuleUtils();

    /*如何操作棋子
     1、点击棋盘
     2、如何判断点击的地方是否有棋子
     3、如何区分第一次选择、重新选择、移动、吃子
  *棋盘规则
     1、红方不可以操作黑方棋子
     2、一方走完结束，另一方才能走
 */

    /**
     * @param chesses
     * @param x
     * @param y
     * @return 0表示
     */
    public static int click(Chesses chesses, int x, int y) {
        char ch = chesses.map[y][x];
        //1.不管有没有选中过棋子，只要点击的是同阵营棋子，就再次选择该棋子
        if (isCamp(chesses, x, y)) {
            chesses.index.x = x;
            chesses.index.y = y;
            return 1;
        }
        //2.先判断是否有选中
        if (chesses.index.x == -1) {
            //没有选中过且不是同阵营棋子
            return 0;
        } else {
            //有选中，判断是还是移动还是吃子（由于数组中移动字符是覆盖操作，所以无需区分移动终点是否有子）
            if (ru.isAbleMove(chesses, chesses.index, new Index(x, y))) {
                //移动或吃子
                chesses.map[y][x] = chesses.map[chesses.index.y][chesses.index.x];
                chesses.map[chesses.index.y][chesses.index.x] = ' ';
                chesses.index.x = -1;
                chesses.curPlayer = chesses.curPlayer == 1 ? 0 : 1;
                if (ch == 'j' || ch == 'J') {
                    return 9;
                }
                return 2;
            }
        }
        return 0;
    }

    /*判断给出坐标的棋子是否是当前阵营*/
    public static boolean isCamp(Chesses chesses, int x, int y) {
        char ch = chesses.map[y][x];
        return (chesses.curPlayer == 1 && isUpp(ch)) || (chesses.curPlayer == 0 && isLow(ch));
    }

    public static boolean isUpp(char ch) {
        return ch >= 65 && ch <= 90;
    }

    public static boolean isLow(char ch) {
        return ch >= 97 && ch <= 122;
    }

    public static String mapStr(Chesses chesses) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(chesses.map[i]);
        }
        return sb.toString();
    }

    public static char[][] strToMap(String str) {
        char map[][] = new char[10][9];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                map[i][j] = str.charAt(i * 9 + j);
            }
        }
        return map;
    }
}
