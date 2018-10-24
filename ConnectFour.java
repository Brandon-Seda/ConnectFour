package CFour;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ConnectFour extends JFrame {
	private JPanel jMain;
	CFBoard c4Board;
	ScoreBoard scBoard;
	CFBoard btns;

	
	private Player currPlayer;
	private Player player1;
	private Player player2; 
	
	public ConnectFour (){
		player1 = new Player ("DatKoala" , "A");
		player2 = new Player ("DatPanda" , "B");
		currPlayer = player1;
		
		jMain = new JPanel();
		jMain.setLayout(new BorderLayout());
		scBoard = new ScoreBoard();
		c4Board = new CFBoard();
		btns = new CFBoard();
		jMain.add(BorderLayout.NORTH, scBoard);
		jMain.add(BorderLayout.CENTER, c4Board);

		add(jMain);
		
		setSize (1300, 1300);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	private class ScoreBoard extends JPanel {
		private JLabel [] scKoa;// = new JLabel[2];
		private JLabel [] scPan;
		private final int ROW = 1;
		private final int COL = 2;
		
		public ScoreBoard(){
			setLayout(new GridLayout(ROW, COL));
			scKoa = new JLabel [2];
			scPan = new JLabel [2];
			

			displayScore();
			scKoa[0].setText("DatKoala");
			scKoa[1].setText("0");
			scPan[0].setText("DatPanda");
			scPan[1].setText("0");
			showScore();
		}
		private void showScore() {
			int pWins = currPlayer.getNumWins();
			if(currPlayer.getSymbol().equals("A")){
				scKoa[1].setText("" + pWins);
			} 
			else if (currPlayer.getSymbol().equals("B")){
				scPan[1].setText("" + pWins);
			}
		}
		
		private void displayScore(){
			Font bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 60);
			Border whiteB = BorderFactory.createLineBorder(Color.WHITE);
			
			for(int row = 0; row < scKoa.length; row++){
				scKoa[row] = new JLabel(); 
				scKoa[row].setOpaque(true);
				scKoa[row].setBackground(Color.GRAY);
				scKoa[row].setFont(bigFont);
				scKoa[row].setHorizontalAlignment(NORMAL);
				scKoa[row].setBorder(whiteB);
					add(scKoa[row]);
			}
			for(int row = 0; row < scPan.length; row++){
				scPan[row] = new JLabel(); 
				scPan[row].setOpaque(true);
				scPan[row].setBackground(Color.GRAY);
				scPan[row].setFont(bigFont);
				scPan[row].setHorizontalAlignment(NORMAL);
				scPan[row].setBorder(whiteB);
					add(scPan[row]);
			}
			
		}
	}
	
	private class CFBoard extends JPanel implements GamePlayerInterface, GameBoardInterface, ActionListener {
		private JLabel [][] board;
		private JButton [] btns;
		private final int ROWS = 6;
		private final int COLS = 7; 
		private int [] gravArr = {5,5,5,5,5,5,5};
		
		
		public CFBoard() {
			setLayout(new GridLayout (ROWS+1, COLS));
			board = new JLabel[ROWS][COLS];
			btns = new JButton[7];
			displayBoard();
		}
		
		//when each button is clicked, fills the button slot with a symbol
		@Override
		public void actionPerformed(ActionEvent e) {
			String symbol = currPlayer.getSymbol();
			
			JButton btnClicked = (JButton) e.getSource();
			for (int i = 0; i < btns.length ; i++){
				if (btns[i] == btnClicked){
					int colInd = i;
					board[gravArr[colInd]][colInd].setText(symbol);;
					gravArr[colInd] --;
					if(gravArr[colInd] == -1){
						btnClicked.setEnabled(false); //disables button
					}
				}
			}
			
			if(isWinner()){
				JOptionPane.showMessageDialog(null, "WINNER = " +currPlayer.getName());
				currPlayer.addNumWins();
				clearBoard();
				playAgain();
			}
			else if(isFull()){
				JOptionPane.showMessageDialog(null,"IS FULL, ENDS IN DRAW");
				clearBoard();
				playAgain();
			}
			scBoard.showScore();
			takeTurn();
		}
		
		
		@Override
		public void displayBoard() {
			Font bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 60);
			Border whiteB = BorderFactory.createLineBorder(Color.WHITE);
			
			
				for(int col = 0; col <btns.length; col++){
					btns[col] = new JButton();
					btns[col].setBackground(Color.PINK);
					btns[col].setFont(bigFont);
					btns[col].setEnabled(true);
					btns[col].addActionListener(this);
					add(btns[col]);
				}
			
			for(int row = 0; row < board.length; row++){
				for(int col = 0; col <board[row].length; col++){
					board[row][col] = new JLabel(); 
					board[row][col].setOpaque(true);
					board[row][col].setBackground(Color.GRAY);
					board[row][col].setFont(bigFont);
					board[row][col].setHorizontalAlignment(NORMAL);
					board[row][col].setBorder(whiteB);
					add(board[row][col]);
				}
			}
			
		}
		@Override
		public void clearBoard() {
			for(int row = 0; row < board.length; row++){
				for(int col = 0; col <board[row].length; col++){
					board[row][col].setText(""); //clear button text
					board[row][col].setEnabled(true); //reEnables button
				}
			}
			for (int row = 0; row < gravArr.length; row ++){
				gravArr [row] = 5;
			}
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public boolean isFull() {
			for(int row = 0; row < board.length; row++){
				for(int col = 0; col < board[row].length; col ++){
					String cellContent = board[row][col].getText().trim();
					if(cellContent.isEmpty()){
						return false; //board has an empty slot, game not over
					}
				}
			}
			return false;
			
		}

		@Override
		public boolean isWinner() {
		if(isWinnerInRow() || isWinnerInCol() || isWinnerInDownDiag() || isWinnerInUpDiag()) {
				return true; 
			}
			return false;
		}
		
		private boolean isWinnerInRow(){
			String symbol = currPlayer.getSymbol();
			for(int row = 0; row < board.length; row++){
				int numMatchesInRow = 0; //resets on next row
				for(int col = 0; col < board[row].length; col ++){
					if(board[row][col].getText().trim().equalsIgnoreCase(symbol)){
						numMatchesInRow ++;
					}
					else { numMatchesInRow = 0;
					}
					if(numMatchesInRow == 4){
						return true;
					}
				}
			}
			return false;	
		}
		private boolean isWinnerInCol(){
			String symbol = currPlayer.getSymbol();
			for(int col = 0; col < 7; col++){
				int numMatchesInCol = 0; 
				for(int row = 0; row <6; row++){
					if(board[row][col].getText().trim().equalsIgnoreCase(symbol)){
						numMatchesInCol ++;
					}
					else {
						numMatchesInCol = 0;
					}
					if(numMatchesInCol == 4){
						return true;
					}
				}
			}
			return false;
		}
		private boolean isWinnerInDownDiag(){
			for(int startRow = 0; startRow < 3; startRow++){
			int numMatchesInDiag = 0;
			int row = startRow;
			int col = 0;
			String symbol = currPlayer.getSymbol();
				while (row < 6 && col < 7 ){
					if(board[row][col].getText().trim().equalsIgnoreCase(symbol)){
						numMatchesInDiag++;
					}
					else {
						numMatchesInDiag = 0;
					}
					if (numMatchesInDiag == 4){
						return true;
					}
					row ++;
					col ++;
				} 
			}
			
			for(int startCol = 1; startCol < 4; startCol++){
				int numMatchesInDiag = 0;
				int row = 0;
				int col = startCol;
				String symbol = currPlayer.getSymbol();
					while (row < 6 && col < 7){
						if(board[row][col].getText().trim().equalsIgnoreCase(symbol)){
							numMatchesInDiag++;
						}
						else {
							numMatchesInDiag = 0;
						}
						if (numMatchesInDiag == 4){
							return true;
						}
					row ++;
					col ++;
				}
			}
			
			return false;
		}
		private boolean isWinnerInUpDiag(){
			String symbol = currPlayer.getSymbol();
			for(int startRow = 3; startRow < 6; startRow++){
				int numMatchesInUpDiag = 0;
				int row = startRow;
				int col = 0;
				while(row >= 0 && col < 7){
					if (board[row][col].getText().trim().equalsIgnoreCase(symbol)){
						numMatchesInUpDiag ++;
					}
					else {
						numMatchesInUpDiag = 0;
					}
					if (numMatchesInUpDiag == 4){
						return true;
					}
					row --;
					col ++;
				}
			}
			
			for(int startCol = 1; startCol < 4; startCol++){
				int numMatchesInUpDiag = 0;
				int row = 0;
				int col = startCol;
				while(row >= 0 && col < 7){
					if (board[row][col].getText().trim().equalsIgnoreCase(symbol)){
						numMatchesInUpDiag ++;
					}
					else {
						numMatchesInUpDiag = 0;
					}
					if (numMatchesInUpDiag == 4){
						return true;
					}
					row --;
					col ++;
				}
			}
			return false;
		}

		@Override
		public void takeTurn() {
			if (currPlayer.equals(player1)){
				currPlayer = player2;
			}
			else {
				currPlayer = player1;
			}	
		}

		@Override
		public boolean playAgain() {
			int tryAgain = JOptionPane.showConfirmDialog(null, "Play Again?");
			if(tryAgain==JOptionPane.NO_OPTION){
				System.exit(0);
			}
	
			return false;
		}
	}
}
