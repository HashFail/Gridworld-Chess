package pieces;

import game.Player;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class King extends Piece {

	public King(Grid<Actor> grid, Location location, Player player) {
		super(grid, location, player);
	}

	protected boolean directionIsValid(int direction)
	{
		return false;
	}
	
	public boolean canMoveTo(Location location) {
		if(this.getLocation() == null)
			return false;
		Piece other = (Piece) getGrid().get(location);
		if(other != null && other.player == player)
			return false;
		int colDiff = Math.abs(this.getLocation().getCol() - location.getCol());
		int rowDiff = Math.abs(this.getLocation().getRow() - location.getRow());
		return (colDiff == 1 || colDiff == 0) && (rowDiff == 0 || rowDiff == 1);
	}

}
