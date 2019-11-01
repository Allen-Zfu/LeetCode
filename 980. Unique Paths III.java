https://leetcode.com/problems/unique-paths-iii/

思路：Backtracking (DFS)

时间复杂度：O(4^(rowLen * colLen))
空间复杂度：O(rowLen * colLen)
犯错点：1.细节错误：total记录的是所有0的个数，但到达2的步数是total+1。
        2.细节错误：对所有choices搜索完后，要将状态复原，即grid[row][col]重置为0。

class Solution {
    int count = 0, total = 0;
    int startRow = -1, startCol = -1;
    int endRow = -1, endCol = -1;
    int[] dir = new int[]{-1, 0, 1, 0, -1};
    
    public int uniquePathsIII(int[][] grid) {
        int rowLen = grid.length, colLen = rowLen == 0 ? 0 : grid[0].length;
        for (int row = 0; row < rowLen; row++) {
            for (int col = 0; col < colLen; col++) {
                if (grid[row][col] == 1) {
                    startRow = row;
                    startCol = col;
                } else if (grid[row][col] == 2) {
                    endRow = row;
                    endCol = col;
                } else if (grid[row][col] == 0) {
                    total++;
                }
            }
        }
        grid[startRow][startCol] = 0;
        dfs(grid, startRow, startCol, 0);
        
        return count;
    }
    
    private void dfs(int[][] grid, int row, int col, int step) {
        int rowLen = grid.length, colLen = rowLen == 0 ? 0 : grid[0].length;
        /* constraints */
        if (row < 0 || row >= rowLen || col < 0 || col >= colLen || grid[row][col] % 2 != 0) {
            return;
        }
        
        /* goal */
        if (grid[row][col] == 2) {
            /* constraints */
            //if (step == total)  // {Mistake 1}
            if (step == total + 1) {  // {Correction 1}
                count++;
            }
            return;
        }
        
        grid[row][col] = 3;
        /* choices */
        for (int i = 0; i < 4; i++) {
            int nextRow = row + dir[i], nextCol = col + dir[i + 1];
            dfs(grid, nextRow, nextCol, step + 1);
        }
        
        // {Mistake 2}
        grid[row][col] = 0;  // {Correction 2: reset}
    }
}