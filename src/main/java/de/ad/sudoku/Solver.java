package de.ad.sudoku;

import java.util.Optional;
import java.util.Random;

/**
 * Github:https://github.com/a11n/sudoku
 * 解法器
 */
public class Solver {
    /**
     * 空格子数值
     */
    private static final int EMPTY = 0;

    /**
     * 1~9的随机数组
     */
    private final int[] values;

    /**
     * 构造方法 初始化随机数组
     */
    public Solver() {
        this.values = generateRandomValues();
    }

    /**
     * 数独求解
     *
     * @param grid
     */
    public void solve(Grid grid) {
        boolean solvable = solve(grid, grid.getFirstEmptyCell());
        if (!solvable) {
            throw new IllegalStateException("The provided grid is not solvable.");
        }
    }

    /**
     * 求解方法
     *
     * @param grid
     * @param cell
     * @return
     */
    private boolean solve(Grid grid, Optional<Grid.Cell> cell) {
        // 空格子 说明遍历处理完了
        if (!cell.isPresent()) {
            return true;
        }
        // 遍历随机数值 尝试填数
        for (int value : values) {
            // 校验填的数是否合理 合理的话尝试下一个空格子
            if (grid.isValidValueForCell(cell.get(), value)) {
                cell.get().setValue(value);
                // 递归尝试下一个空格子
                if (solve(grid, grid.getNextEmptyCellOf(cell.get()))) return true;
                // 尝试失败格子的填入0 继续为当前格子尝试下一个随机值
                cell.get().setValue(EMPTY);
            }
        }
        return false;
    }

    /**
     * 获取随机数组
     *
     * @return
     */
    private int[] generateRandomValues() {
        // 初始化随机数组 此处空格子0是因为格子初始化的时候 默认给的就是0 便于判断和处理
        int[] values = {EMPTY, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random random = new Random();
        // 使用交换法构建随机数组
        for (int i = 0, j = random.nextInt(9), tmp = values[j];
             i < values.length;
             i++, j = random.nextInt(9), tmp = values[j]) {
            if (i == j) continue;
            values[j] = values[i];
            values[i] = tmp;
        }
        return values;
    }
}
