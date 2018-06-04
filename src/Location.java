public class Location {

	
	public int x;
	public int y;
	public int type;
	public String key;
	
	public Location(int x, int y, int type)
	{
		this.x = x;
		this.y = y;
		this.type = type;
		key = x + "-" + y;
	}
	
	public int getx()
	{
		return this.x;
	}
	
	public int gety()
	{
		return this.y;
	}
	
	public int getType()
	{
		return this.type;
	}
	
	public void setType(int type)
	{
		this.type = type;
	}
	
	public String getKey()
	{
		return this.key;
	}
}
