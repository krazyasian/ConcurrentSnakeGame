public class Location {

	
	public int x;
	public int y;
	public enum atLocation
	{
		EMPTY, FOOD, SNAKE
	}
	atLocation type;
	
	public Location(int x, int y, atLocation type)
	{
		this.x = x;
		this.y = y;
		this.type = type;
	}
}
