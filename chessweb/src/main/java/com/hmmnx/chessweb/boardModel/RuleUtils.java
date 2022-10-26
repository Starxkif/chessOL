package com.hmmnx.chessweb.boardModel;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @program: chessOL
 * @description:
 * @author: ZAM
 * @create: 2022-10-23 01:58
 **/
/*主要校验棋子走法是否符合规则*/
public class RuleUtils {
    public static boolean isUpp(char ch) {
        return ch >= 65 && ch <= 90;
    }

    public static boolean isLow(char ch) {
        return ch >= 97 && ch <= 122;
    }

    /**
     * 判断是否在王宫范围内
     *
     * @param index
     * @return
     */
    public boolean isHome(Index index, char ch) {
        if (index.x < 3 || index.x > 5) {
            return false;
        }
        if (isUpp(ch)) {
            //上方的棋子
            if (index.y > 2 || index.y < 0) {
                return false;
            }
        } else {
            //下方的棋子
            if (index.y > 9 || index.y < 7) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断是走直线还是斜线或者都不是
     *
     * @param
     * @return 1：正斜线，2：y轴直接，3：x轴直线，-2：都不是，0：x轴日字，-1：y轴日字
     */
    public int line(Index start, Index end) {
        if (start.y == end.y) {
            //x
            return 3;
        } else if (start.x == end.x) {
            //y
            return 2;
        } else if (Math.abs(start.x - end.x) == Math.abs(start.y - end.y)) {
            //正斜线
            return 1;
        } else {
            //日字
            if (Math.abs(start.x - end.x) == 2 && Math.abs(start.y - end.y) == 1) {
                //x
                return 0;
            } else if (Math.abs(start.x - end.x) == 1 && Math.abs(start.y - end.y) == 2) {
                //y
                return -1;
            }
        }

        return -2;
    }

    /**
     * 计算起点到目标点之间的步数
     *
     * @param
     * @return
     */
    public int getStep(Index start, Index end) {
        int line = line(start, end);
        if (line == 3) {
            //x
            return Math.abs(start.x - end.x);
        } else if (line == 2 || line == 1) {
            //y或正斜线
            return Math.abs(start.y - end.y);
        }

        return 0;
    }

    /**
     * 判断目标点是否过河
     *
     * @param index
     * @return false：没有过河，true：过了河
     */
    public boolean isOverRiver(Index index, char ch) {
        if (isUpp(ch)) {
            //上
            if (index.y < 5) {
                return false;
            }
        } else {
            //下
            if (index.y > 4) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断相或象或马是否蹩脚
     *
     * @param startI
     * @param endI
     * @return
     */
    public boolean isBieJiao(Index startI, Index endI, Chesses chesses) {
        Index center = new Index();//中心点
        char c1 = chesses.map[startI.y][startI.x];
        char c2 = chesses.map[endI.y][endI.x];
        c1 = Character.toLowerCase(c1);
        c2 = Character.toLowerCase(c2);
        if ('x' == c1) {
            center.x = (startI.x + endI.x) / 2;
            center.y = (startI.y + endI.y) / 2;
        } else if ('m' == c1) {
            int line = line(startI, endI);
            if (line == 0) {
                //x
                center.x = (startI.x + endI.x) / 2;
                center.y = startI.y;
            } else if (line == -1) {
                //y
                center.y = (startI.y + endI.y) / 2;
                center.x = startI.x;
            }
        }
        return chesses.map[center.y][center.x] != ' ';
    }

    /**
     * 计算起点到目标点之间的棋子数量，不计算起点和目标点上的位置
     *
     * @param
     * @return
     */
    public int getCount(Index start, Index end, char[][] map) {
        int count = 0;//统计棋子数量
        int line = line(start, end);
        if (line == 2) {
            //y
            if (end.y > start.y) {
                //从上往下
                for (int i = start.y + 1; i < end.y; i++) {
                    if (map[i][start.x] != ' ')
                        count++;
                }
            } else {
                //从下往上
                for (int i = start.y - 1; i > end.y; i--) {
                    if (map[i][start.x] != ' ')
                        count++;
                }
            }
        } else if (line == 3) {
            //x
            if (end.x > start.x) {
                //从左往右
                for (int i = start.x + 1; i < end.x; i++) {
                    if (map[start.y][i] != ' ')
                        count++;
                }
            } else {
                //从右往左
                for (int i = start.x - 1; i > end.x; i--) {
                    if (map[start.y][i] != ' ')
                        count++;
                }
            }
        }
        System.out.println("棋子总数：" + count);
        return count;
    }

    /**
     * 判断是否前进
     *
     * @param
     * @return
     */
    public boolean isForward(Index start, Index end, char startCh) {
        if (isUpp(startCh)) {
            //上
            if (end.y > start.y) {
                return true;
            }
        } else {
            //下
            if (end.y < start.y) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断是否后退
     *
     * @param
     * @return
     */
    public boolean isBack(Index start, Index end, char startCh) {
        if (isUpp(startCh)) {
            //上
            if (end.y < start.y) {
                return true;
            }
        } else {
            //下
            if (end.y > start.y) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断棋子是否可以被移动到指定的位置
     *
     * @param start 是起点，
     * @param end   是终点
     * @return
     */
    public boolean isAbleMove(Chesses chesses, Index start, Index end) {
        char startCh = chesses.map[start.y][start.x];
        switch (Character.toLowerCase(startCh)) {
            case 'j':
                return line(start, end) > 1 && isHome(end, startCh) && getStep(start, end) == 1;
            case 's':
                return line(start, end) == 1 && isHome(end, startCh) && getStep(start, end) == 1;
            case 'x':
                return line(start, end) == 1 && getStep(start, end) == 2 && !isBieJiao(start, end, chesses) && !isOverRiver(end, startCh);
            case 'm':
                return (line(start, end) == 0 || line(start, end) == -1) && !isBieJiao(start, end, chesses);
            case 'c':
                return line(start, end) > 1 && getCount(start, end, chesses.map) == 0;
            case 'p': {
                char endCh = chesses.map[end.y][end.x];
                if (endCh != ' ') {
                    //吃子
                    return line(start, end) > 1 && getCount(start, end, chesses.map) == 1;
                } else {
                    //移动
                    return line(start, end) > 1 && getCount(start, end, chesses.map) == 0;
                }
            }
            case 'b': {
                if (line(start, end) < 2 || getStep(start, end) > 1) {
                    return false;
                }
                if (isOverRiver(start, startCh)) {
                    return !isBack(start, end, startCh);
                } else {
                    return isForward(start, end, startCh);
                }
            }
        }
        return false;
    }
}
