package pieces;

import java.awt.Color;

import game.Player;
import info.gridworld.actor.Actor;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public abstract class Piece extends Actor{
	protected Player player;
	protected boolean moved;
	protected boolean active = true;
	
	public void removeSelfFromGrid()
	{
		super.removeSelfFromGrid();
		this.active = false;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public boolean moved()
	{
		return moved;
	}
	
	public void markMoved()
	{
		this.moved = true;
	}
	
	public void act()
	{
		
	}
	
	protected abstract boolean directionIsValid(int direction);
	
	public boolean canMoveTo(Location location) {
		if(this.getLocation() == null)
			return false;
		int direction = this.getDirectionToward(location);
		if(!directionIsValid(direction))
			return false;
		Location nextLocation = this.getLocation().getAdjacentLocation(direction);
		Piece otherPiece = (Piece)this.getGrid().get(nextLocation);
		while(!nextLocation.equals(location))
		{
			if(otherPiece != null)
				return false;
			nextLocation = nextLocation.getAdjacentLocation(direction);
			otherPiece = (Piece)this.getGrid().get(nextLocation);
		}
		return otherPiece == null || otherPiece.player == otherPiece.player;
	}
	
	public int getDirectionToward(Location target)
	{
		int dx = target.getCol() - getLocation().getCol();
        int dy = target.getRow() - getLocation().getRow();
        
        int angle = (int) Math.round(Math.toDegrees(Math.atan2(-dy, dx)));
        int compassAngle = 90 - angle;
        if (compassAngle < 0)
            compassAngle += 360;
        return compassAngle;
	}
	
	public Piece(Grid<Actor> grid, Location location, Player player)
	{
		if(grid == null || !(grid instanceof BoundedGrid))
			throw new IllegalArgumentException("Invalid grid: null or not bounded");
		if(location == null)
			throw new IllegalArgumentException("Location can't be null");
		if(player == null)
			throw new IllegalArgumentException("Player can't be null");
		this.setColor(player.color());
		this.putSelfInGrid(grid, location);
		this.player = player;
	}
	
	public Color color()
	{
		return player.color();
	}
	
}
