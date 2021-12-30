public class CollisionDetection
{
	public boolean crashed;
	
	
	public CollisionDetection()
	{
		crashed = false;
	}
	
	public void collided()
	{
		crashed = true;
	}
	
	public boolean collisioncheck()
	{
		return crashed;
	}
}