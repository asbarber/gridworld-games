package game.maze;

import game.assets.AssetManager;
import info.gridworld.actor.Actor;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Files should be declared in the following format:
 *      Rows
 *      Columns
 *      Wall Type (If Unused, replace with 'Null')
 *      Maze (X is wall, O is opening)
 *      Primary Spawn Locations (as Many as Needed: format: (row, col))
 * @author Aaron
 */
public class MazeBuilder
{
    private int rows, columns;
    private BoundedGrid maze;
    private Object wallType;
    private ArrayList<Location> spawnPoints;
    
    private File mazeFile;
    private InputStream streamFile = null;
    private String filepath;


    public MazeBuilder(InputStream is){
        filepath = "Unknown";
        streamFile = is;
        
        spawnPoints = new ArrayList<Location>();        
    }
    public MazeBuilder(String fileName){
        filepath = fileName;
        mazeFile = new File(filepath);
        
        //Creates file if does not exist
        if (!mazeFile.exists())
        {
            try {
                mazeFile.createNewFile();
            } catch (IOException ex) {
            }
        }
        
        spawnPoints = new ArrayList<Location>();
    }
    
    public Object getWallType(){
        return wallType;
    }
    public String getFilepath(){
        return filepath;
    }
    public BoundedGrid getMaze(){
        return maze;
    }
    public ArrayList<Location> getSpawnPoints(){
        return spawnPoints;
    }

    public void readToFile(BoundedGrid myGrid, Actor wall){
        try {
            maze = myGrid;
            rows = maze.getNumRows();
            columns = maze.getNumCols();
            wallType = wall;
            
            FileWriter writer = new FileWriter(mazeFile);
            
            writer.append( (char) rows + "\n" );
            writer.append( (char) columns + "\n" );
            writer.append( wall.getClass().getName() + "\n" );
            
            writeGrid(writer);
            writer.close();
        } catch (IOException ex) {
        }
    }
    public void readFromFile(Actor wall){        
        try {
                Scanner reader;
                
                if (streamFile == null)
                {
                    reader = new Scanner(mazeFile);
                }
                else
                {
                    reader = new Scanner(streamFile);
                }
                
                
                rows = reader.nextInt();
                columns = reader.nextInt();

                reader.nextLine();  //consume buffer

                //Actor Supplied in call
                if ( wall != null )
                {
                    wallType = wall;
                    reader.nextLine();  //ignores line indicating class name
                }
                //Actor Supplied in file
                else
                {
                    String className = reader.nextLine();
                    Class cl = Class.forName(className);
                    wallType = cl.newInstance();
                }

                maze = new BoundedGrid(rows, columns);      
                readGrid(reader);
                readSpawnPoints(reader);
                
                reader.close();
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        } catch (FileNotFoundException ex) {     
        }
    }
    public void readFromFile(){
        readFromFile(null);
    }
    
    /** Helper */
    private void readGrid(Scanner input){
        String aRow;
        
        for (int r = 0; r < rows; r++)
        {
            aRow = input.nextLine().toUpperCase();
            
            for (int c = 0; c < columns; c++)
            {
                if ( aRow.charAt(c) == 'X' )
                {
                    //Put a wall
                    Actor aWall = (Actor) newWallType();
                    aWall.putSelfInGrid(maze, new Location(r, c));
                }
                else
                {
                    //Empty Space
                }
            }
        }
    }    
    private void writeGrid(FileWriter writer) throws IOException{
        for (int r = 0; r < maze.getNumRows(); r++)
        {
            for (int c = 0; c < maze.getNumCols(); c++)
            {
                if ( wallType.getClass().equals(maze.get(new Location(r, c)).getClass()) )
                {
                    writer.append('X');
                }
                else
                {
                    writer.append('O');
                }
            }   
            
            writer.append('\n');
        }        
    }
    private void readSpawnPoints(Scanner reader) {
        String aRow;
        
        //If has spawn points
        while ( reader.hasNext() )
        {
            aRow = reader.nextLine();
            aRow = aRow.replace('(', ' '); 
            aRow = aRow.replace(')', ' '); 
            int breakpoint = aRow.indexOf(",");
            
            int r = Integer.parseInt( aRow.substring(0, breakpoint).trim() );
            int c = Integer.parseInt( aRow.substring(breakpoint + 1).trim() ); 
            spawnPoints.add( new Location(r, c) );
        }
    }
    
    private Object newWallType(){
        try {
            //Returns new instance of wall type
            Class cl = Class.forName(wallType.getClass().getName());        
            return cl.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(MazeBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MazeBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MazeBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Error
        return null;
    }
}