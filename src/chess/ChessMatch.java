package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;

	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
	
	

	public int getTurn() {
		return turn;
	}


	public Color getCurrentPlayer() {
		return currentPlayer;
	}


	public ChessPiece[][] getPieces() {
		ChessPiece[][] match = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				match[i][j] = (ChessPiece) board.piece(i, j);
			}
		}

		return match;
	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		nextTurn();
		return (ChessPiece) capturedPiece;
	}

	

	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		return capturedPiece;
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if(currentPlayer != ((ChessPiece)board.piece(position)).getColor()){
			throw new ChessException("The chosen piece is not Yours");
		}
		if(!board.piece(position).isThereAnyPossibleMoves()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if(!board.piece(source).possibleMove(target)) 
		{
			throw new ChessException("The chosen Piece can't move to target position ");
		}		
	}

	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		
		return board.piece(position).possibleMoves();
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE)  ? Color.BLACK : Color.WHITE; 
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}

	private void initialSetup() {
		placeNewPiece('C', 1, new Rook(board, Color.WHITE));
		placeNewPiece('C', 2, new Rook(board, Color.WHITE));
		placeNewPiece('D', 2, new Rook(board, Color.WHITE));
		placeNewPiece('E', 2, new Rook(board, Color.WHITE));
		placeNewPiece('E', 1, new Rook(board, Color.WHITE));
		placeNewPiece('D', 1, new King(board, Color.WHITE));

		placeNewPiece('C', 7, new Rook(board, Color.BLACK));
		placeNewPiece('C', 8, new Rook(board, Color.BLACK));
		placeNewPiece('D', 7, new Rook(board, Color.BLACK));
		placeNewPiece('E', 7, new Rook(board, Color.BLACK));
		placeNewPiece('E', 8, new Rook(board, Color.BLACK));
		placeNewPiece('D', 8, new King(board, Color.BLACK));
	}

}
