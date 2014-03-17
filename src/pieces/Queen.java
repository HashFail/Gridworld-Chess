package pieces;

import game.Player;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class Queen extends Piece{

	public Queen(Grid<Actor> grid, Location location, Player player) {
		super(grid, location, player);
	}

	
	protected boolean directionIsValid(int direction) {
		return direction % 45 == 0;
	}
	
}
