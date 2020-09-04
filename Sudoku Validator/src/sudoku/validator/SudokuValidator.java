/**
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sudoku.validator ;

/**
 *
 * @author Zahra Parham - 9412003
 * @author Bahar Mousazadeh - 9412054
 */

public class SudokuValidator {

	private static final int NUM_THREADS = 27;

	private static final int[][] sudoku = {
			{6, 2, 4, 5, 3, 9, 1, 8, 7},
			{5, 1, 9, 7, 2, 8, 6, 3, 4},
			{8, 3, 7, 6, 1, 4, 2, 9, 5},
			{1, 4, 3, 8, 6, 5, 7, 2, 9},
			{9, 5, 8, 2, 4, 7, 3, 6, 1},
			{7, 6, 2, 3, 9, 1, 4, 5, 8},
			{3, 7, 1, 9, 5, 6, 8, 4, 2},
			{4, 9, 6, 1, 8, 2, 5, 7, 3},
			{2, 8, 5, 4, 7, 3, 9, 1, 6}
			};

	private static boolean[] validator;
	

	public static class RowColumnObject {
		int row;
		int col;
		RowColumnObject(int row, int column) {
			this.row = row;
			this.col = column;
		}
	}
	
	
	public static class RowValidation extends RowColumnObject implements Runnable {		
		RowValidation(int row, int column) {
			super(row, column); 
		}

		@Override
		public void run() {
			if (col != 0 || row > 8) {
				System.out.println("Invalid!");				
				return;
			}
			
			
			boolean[] validityArray = new boolean[9];
			int i;
			for (i = 0; i < 9; i++) {
                            
				int num = sudoku[row][i];
				if (num < 1 || num > 9 || validityArray[num - 1]) {
					return;
				} else if (!validityArray[num - 1]) {
					validityArray[num - 1] = true;
				}
			}

			validator[9 + row] = true;
		}

	}
	

	public static class ColumnValidation extends RowColumnObject implements Runnable {
		ColumnValidation(int row, int column) {
			super(row, column); 
		}

		@Override
		public void run() {
			if (row != 0 || col > 8) {
				System.out.println("Invalid!");				
				return;
			}
			
			
			boolean[] validityArray = new boolean[9];
			int i;
			for (i = 0; i < 9; i++) {

				int num = sudoku[i][col];
				if (num < 1 || num > 9 || validityArray[num - 1]) {
					return;
				} else if (!validityArray[num - 1]) {
					validityArray[num - 1] = true;
				}
			}
			
			validator[18 + col] = true;			
		}		
	}
	
	
	public static class BlockValidation extends RowColumnObject implements Runnable {
		BlockValidation(int row, int column) {
			super(row, column); 
		}

		@Override
		public void run() {
			
			if (row > 6 || row % 3 != 0 || col > 6 || col % 3 != 0) {
				System.out.println("Invalid row or column for subsection!");
				return;
			}
			
			
			boolean[] validityArray = new boolean[9];			
			for (int i = row; i < row + 3; i++) {
				for (int j = col; j < col + 3; j++) {
					int num = sudoku[i][j];
					if (num < 1 || num > 9 || validityArray[num - 1]) {
						return;
					} else {
						validityArray[num - 1] = true;		
					}
				}
			}
			
			validator[row + col/3] = true; 
		}
		
	}
	
	public static void main(String[] args) {
		validator = new boolean[NUM_THREADS];		
		Thread[] threads = new Thread[NUM_THREADS];
		int threadIndex = 0;

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {						
				if (i%3 == 0 && j%3 == 0) {
					threads[threadIndex++] = new Thread(new BlockValidation(i, j));				
				}
				if (i == 0) {					
					threads[threadIndex++] = new Thread(new ColumnValidation(i, j));
				}
				if (j == 0) {
					threads[threadIndex++] = new Thread(new RowValidation(i, j));					
				}
			}
		}
		
		
		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
		
		
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		for (int i = 0; i < validator.length; i++) {
			if (!validator[i]) {
				System.out.println("Your sudoku is invalid!");
				return;
			}
		}
		System.out.println("Your sudoku is valid!");
	}
}