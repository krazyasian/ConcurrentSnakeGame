public class Location {

	
	public int x;
	public int y;
	public String type;
	public String key;
	
	public Location(int x, int y, String type)
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
	
	public String getType()
	{
		return this.type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getKey()
	{
		return this.key;
	}
}
