package game;

import java.awt.Color;

import pieces.Knight;
import pieces.Piece;
import info.gridworld.world.World;
import info.gridworld.actor.Actor;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;

public class Game {
	private Player currentPlayer;
	private Player player1;
	private Player player2;
	private Piece selectedPiece;
	private BoundedGrid<Actor> grid;
	private World<Actor> world;
	
	public Piece selectedPiece()
	{
		return selectedPiece;
	}
	
	public Game()
	{
		grid = new BoundedGrid<Actor>(8, 8);
		world = new World<Actor>(grid, this);
		player1 = new Player(Color.WHITE, Location.NORTH);
		player2 = new Player(Color.BLACK, Location.SOUTH);
		player1.setOpponent(player2);
		player2.setOpponent(player1);
		player1.setPieces(grid);
		player2.setPieces(grid);
		for(int i = 0; i < 15; i++)
		{
			world.add(player1.getPieces()[i].getLocation(), player1.getPieces()[i]);
		}
		for(int i = 0; i < 15; i++)
		{
			world.add(player2.getPieces()[i].getLocation(), player2.getPieces()[i]);
		}
		world.show();
		currentPlayer = player1;
	}
	
	public Player currentPlayer()
	{
		return currentPlayer;
	}
	
	public static void main(String[] args)
	{
		new Game();
	}
	
	public void selectPiece(Piece piece)
	{
		selectedPiece = piece;
	}
	
	private boolean testMove(Piece piece, Location loc)
	{
		Location prev = piece.getLocation();
		Piece occupant = (Piece) grid.get(loc);
		boolean check = currentPlayer.isInCheck();
		piece.moveTo(prev);
		if(occupant != null)
			occupant.putSelfInGrid(grid, loc);
		return check;
	}
	
	//checks if any of the pieces can block or capture the given piece without putting the king in check
	public boolean canMoveOthers(Piece threat)
	{
		Piece king = currentPlayer.getKing();
		Piece[] pieces = currentPlayer.getPieces();
		if(threat instanceof Knight)
		{
			for(Piece p : pieces)
			{
				if(p!=king && p.canMoveTo(threat.getLocation()))
					return true;
			}
			return false;
		}
		int direction = king.getDirectionToward(threat.getLocation());
		Location nextLocation = king.getLocation();
		for(Piece p : pieces)
		{
			do
			{
				nextLocation = nextLocation.getAdjacentLocation(direction);
				if(p!=king && p.canMoveTo(nextLocation) && testMove(p, nextLocation))
					return true;
			}while(!nextLocation.equals(threat.getLocation()));
		}
		return false;
	}
	
	//checks if the king can move and not be in check
	public boolean canMoveKing()
	{
		Piece king = currentPlayer.getKing();
		for(int i = 0; i < 7; i++)
		{
			if(testMove(king, king.getLocation().getAdjacentLocation(45 * i)))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean makeMove(Location loc) {
		Location prev = selectedPiece.getLocation();
		Piece occupant = (Piece) grid.get(loc);
		selectedPiece.moveTo(loc);
		Piece[] threats = currentPlayer.inCheck();
		if(threats.length > 0)
		{
			selectedPiece.moveTo(prev);
			if(occupant != null)
				occupant.putSelfInGrid(grid, loc);
			return false;
		}
		currentPlayer = currentPlayer.opponent();
		selectedPiece.markMoved();
		selectedPiece = null;
		return true;
	}
}
