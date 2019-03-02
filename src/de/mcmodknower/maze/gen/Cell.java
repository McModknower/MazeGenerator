package de.mcmodknower.maze.gen;

public class Cell{

	public int y;
	public int x;

	public Cell(int x, int y){
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode(){
		long l = x + (y << 32);
		double x = Double.longBitsToDouble(l);
		while(x > 10)
			x /= 10;
		for(int i = 0; i < 100; i++){
			x = 3.9 * x * (1 - x);
		}
		l = Double.doubleToLongBits(x);
		return ((int)l) ^ ((int)(l << 32));
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		Cell other = (Cell)obj;
		if(x != other.x) return false;
		if(y != other.y) return false;
		return true;
	}

	@Override
	public String toString(){
		return x + ":" + y;
	}

}
