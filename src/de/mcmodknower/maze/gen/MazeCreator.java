package de.mcmodknower.maze.gen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;

public class MazeCreator{

	public MazeCreator(int size, long seed){
		int max = size - 1;
		boolean[][] maze = new boolean[size][size];
		System.out.println("Creating outer walls");
		for(int i = 0; i < size; i++){
			maze[0][i] = true;
			maze[max][i] = true;
		}
		for(int i = size - 1; i > 0; i--){
			maze[i][0] = true;
			maze[i][max] = true;
		}
		System.out.println("Creating the inner points");
		int tmp = (size - 3) / 2;
		for(int i = 0; i < tmp; i++){
			for(int j = 0; j < tmp; j++){
				maze[i * 2 + 2][j * 2 + 2] = true;
			}
		}
		int cellsize = (size - 1) / 2;
		Random r = new Random(seed);
		System.out.println("Filling the inner walls");
		for(int x = 0; x < cellsize; x++){
			for(int y = 0; y < cellsize; y++){
				maze[getPos(x) + 1][getPos(y)] = true;
				maze[getPos(x)][getPos(y) + 1] = true;
			}
		}
		System.out.println("Outputting pre");
		outputIMGFile(size, maze, "pre.png");
		System.out.println("Starting advanced Code");
		HashSet<Cell> cellset = new HashSet<>();
		HashSet<Cell> rand = new HashSet<>();
		cellset.add(new Cell(0, 0));
		rand.addAll(cellset);
		int numCells = (cellsize) * (cellsize);
		out: while(cellset.size() < numCells){
			if(cellset.size() % 100 == 0) System.out.println(cellset.size() + " von " + numCells + " Zellen");
			if(rand.isEmpty()){
				System.out.println("rand is Empty");
				System.out.println(cellset.size());
				break out;
			}
			Iterator<Cell> ite = rand.iterator();
			int select = (int)(r.nextDouble() * (rand.size() - 1));
			int i = 0;
			while(i < select){
				i++;
				ite.next();
			}
			Cell c = ite.next();
			Cell o;
			ArrayList<Integer> helpset = new ArrayList<>();
			helpset.add(0);
			helpset.add(1);
			helpset.add(2);
			helpset.add(3);
			while(!helpset.isEmpty()){
				int mode = helpset.remove(r.nextInt(helpset.size()));
				switch(mode){
				case 0:
					if(c.x > 0){
						o = new Cell(c.x - 1, c.y);
						if(!cellset.contains(o)){
							if(maze[getPos(c.x) - 1][getPos(c.y)]){
								maze[getPos(c.x) - 1][getPos(c.y)] = false;
								cellset.add(o);
								rand.add(o);
								continue out;
							}
						}
					}
					break;
				case 1:
					if(c.x < cellsize - 1){
						o = new Cell(c.x + 1, c.y);
						if(!cellset.contains(o)){
							if(maze[getPos(c.x) + 1][getPos(c.y)]){
								maze[getPos(c.x) + 1][getPos(c.y)] = false;
								cellset.add(o);
								rand.add(o);
								continue out;
							}
						}
					}
					break;
				case 2:
					if(c.y > 0){
						o = new Cell(c.x, c.y - 1);
						if(!cellset.contains(o)){
							if(maze[getPos(c.x)][getPos(c.y) - 1]){
								maze[getPos(c.x)][getPos(c.y) - 1] = false;
								cellset.add(o);
								rand.add(o);
								continue out;
							}
						}
					}
					break;
				case 3:
					if(c.y < cellsize - 1){
						o = new Cell(c.x, c.y + 1);
						if(!cellset.contains(o)){
							if(maze[getPos(c.x)][getPos(c.y) + 1]){
								maze[getPos(c.x)][getPos(c.y) + 1] = false;
								cellset.add(o);
								rand.add(o);
								continue out;
							}
						}
					}
					break;
				default:
				}
			}

			rand.remove(c);

		}
		maze[0][1] = false;
		maze[size - 1][size - 2] = false;
		System.out.println("Outputting the Maze...");
		outputIMGFile(size, maze, "out.png");
	}

	public void outputIMGFile(int size, boolean[][] maze, String file){
		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_3BYTE_BGR);
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				if(maze[x][y]) image.setRGB(x, y, 0);
				else image.setRGB(x, y, 0xFFFFFF);
			}
		}
		try{
			ImageIO.write(image, "png", new File(file));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public int getCell(int i){
		return (i - 1) / 2;
	}

	public int getPos(int i){
		return i * 2 + 1;
	}

	public static void main(String[] args){
		new MazeCreator(201, 2l);
	}

}
