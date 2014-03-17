package game;

import info.gridworld.actor.Actor;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;

import java.awt.Color;

import pieces.*;

public class Player {
	private Color color;
	private int direction;
	private Piece[] pieces = new Piece[16];
	private Player opponent;
	private King king;
	
	public int direction()
	{
		return direction;
	}

	public Piece[] getPieces()
	{
		return pieces;
	}
	
	public Color color()
	{
		return color;
	}
	
	public Player(Color color, int direction)
	{
		if(direction != Location.NORTH && direction != Location.SOUTH)
			throw new IllegalArgumentException("Invalid direction");
		if(color != Color.BLACK && color != Color.WHITE)
			throw new IllegalArgumentException("Invalid color");
		this.color = color;
		this.direction = direction;
	}
	
	public Piece[] inCheck()
	{
		int i = 0;
		for(Piece p : opponent.pieces)
		{
			if(p.canMoveTo(king.getLocation()))
			{
				i++;
			}
		}
		Piece[] threats = new Piece[i];
		i = 0;
		for(int x = 0; x < opponent.pieces.length; x++)
		{
			if(opponent.pieces[x].canMoveTo(king.getLocation()))
			{
				threats[i++] = opponent.pieces[x];
			}
		}
		return threats;
	}
	
	public boolean isInCheck()
	{
		for(Piece p : opponent.pieces)
		{
			if(p.canMoveTo(king.getLocation()))
			{
				return true;
			}
		}
		return false;
	}
	
	public void setOpponent(Player opponent)
	{
		this.opponent = opponent;
	}
	
	public Player opponent()
	{
		return opponent;
	}
	
	public void setPieces(BoundedGrid<Actor> grid)
	{
		if(direction == Location.SOUTH)
		{
			for(int i = 0; i < 8; i++)
				pieces[i] = new Pawn(grid, new Location(1, i), this);
			pieces[8] = new Rook(grid, new Location(0, 0), this);
			pieces[9] = new Rook(grid, new Location(0, 7), this);
			pieces[10] = new Knight(grid, new Location(0, 1), this);
			pieces[11] = new Knight(grid, new Location(0, 6), this);
			pieces[12] = new Bishop(grid, new Location(0, 5), this);
			pieces[13] = new Bishop(grid, new Location(0, 2), this);
			pieces[14] = new Queen(grid, new Location(0, 3), this);
			pieces[15] = new King(grid, new Location(0, 4), this);
		}else{
			for(int i = 0; i < 8; i++)
				pieces[i] = new Pawn(grid, new Location(6, i), this);
			pieces[8] = new Rook(grid, new Location(7, 0), this);
			pieces[9] = new Rook(grid, new Location(7, 7), this);
			pieces[10] = new Knight(grid, new Location(7, 1), this);
			pieces[11] = new Knight(grid, new Location(7, 6), this);
			pieces[12] = new Bishop(grid, new Location(7, 5), this);
			pieces[13] = new Bishop(grid, new Location(7, 2), this);
			pieces[14] = new Queen(grid, new Location(7, 3), this);
			pieces[15] = new King(grid, new Location(7, 4), this);
		}
		king = (King) pieces[15];
	}

	public Piece getKing() {
		return king;
	}
}
