package pieces;

import game.Player;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class Pawn extends Piece {
	public Pawn(Grid<Actor> grid, Location location, Player player) {
		super(grid, location, player);
	}

	protected boolean directionIsValid(int direction)
	{
		return false;
	}
	
	public boolean canMoveTo(Location location)
	{
		if(this.getLocation() == null)
			return false;
		boolean isOneInFront = this.getLocation().getRow() + (player.direction() == Location.NORTH ? -1 : 1) == location.getRow();
		Piece other = (Piece)getGrid().get(location);
		if(other == null && this.getLocation().getCol() == location.getCol())
		{
				if(!moved)
				{
					int rowDiff = location.getRow() - this.getLocation().getRow();
					if(player.direction() == Location.NORTH)
						return rowDiff < 0 &&  rowDiff > -3;
					else
						return rowDiff > 0 &&  rowDiff < 3;
				}
				else
				{
					return isOneInFront;
				}
		}
		return other != null && isOneInFront && other.player != this.player && Math.abs(location.getCol() - this.getLocation().getCol()) == 1;
	}
}
