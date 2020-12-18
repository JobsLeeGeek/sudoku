package de.ad.sudoku;

import java.util.Random;

/**
 * From Github:https://github.com/a11n/sudoku
 * 生成器
 */
public class Generator {
    /**
     * 解法器
     */
    private Solver solver;

    /**
     * 构造方法 初始化解法器
     */
    public Generator() {
        this.solver = new Solver();
    }

    /**
     * 生成方法
     *
     * @param numberOfEmptyCells 参数为需要抹除的格子数量 数量越多相当于难度越大 （剩余可见格子数量建议不少于17个（明数最少数量有证明））
     * @return
     */
    public Grid generate(int numberOfEmptyCells) {
        // 生成格子
        Grid grid = generate();
        // 随机擦除格子
        eraseCells(grid, numberOfEmptyCells);
        return grid;
    }

    /**
     * 随机擦除指定数量的格子
     *
     * @param grid
     * @param numberOfEmptyCells
     */
    private void eraseCells(Grid grid, int numberOfEmptyCells) {
        Random random = new Random();
        for (int i = 0; i < numberOfEmptyCells; i++) {
            int randomRow = random.nextInt(9);
            int randomColumn = random.nextInt(9);

            Grid.Cell cell = grid.getCell(randomRow, randomColumn);
            if (!cell.isEmpty()) {
                cell.setValue(0);
            } else {
                i--;
            }
        }
    }

    /**
     * 生成方法
     *
     * @return
     */
    private Grid generate() {
        // 先生成空格子
        Grid grid = Grid.emptyGrid();
        // 使用解法器进行填数
        solver.solve(grid);
        return grid;
    }
}
