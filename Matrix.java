import java.io.BufferedWriter;
import java.io.IOException;


/*
 * Matrix operations 
 * 
 * I know the size of the matrix
 * I know the number of vertices in the graph (size)
 * The edges are unweighted
 * The data is either 1 or 0
 * 
 * create the matrix by feeding in the information from txt file. 
 * Process 1 matrix at a time and print to output
 * repeat until end of txt file.
 * 
 * object should only be made once
 */
public class Matrix
{
    private int[][] adjMatrix;
    private int size;
    //ptr for paths array
    private int pointer;
    private BufferedWriter outFile;
    //stores current path
    int[] currentPath;
    //pointer for tempPath
    private int currentPathPtr;
 
    
    public Matrix(int[][] data, int size, BufferedWriter outFile)
    {
        this.size = size;
        adjMatrix = data;
        pointer = 0;   
        this.outFile = outFile;
        currentPath = new int[size+1];
        currentPathPtr =0;
        
        //initialize array with -1
        for(int i=0; i<size+1; i++)
        {
            currentPath[i] = -1;
        }
    }  


    /*
     * traverse through matrix...although using array not sure if needed...  
     * helper function
     * tempPath: buffer
     * String[] paths: array of paths
     * location: current vertex
     */
    public void traverse(int location, String[] paths, int[] tempPath) //String tempPath)
    {
        
        //base case
        if(tempPath[0] != -1 && tempPath[0] == location)//a bit redundant
        //if(tempPath.length() != 0 && Integer.parseInt(String.valueOf(tempPath.charAt(0))) == location)    
        {   
            tempPath[currentPathPtr] = location;
            currentPathPtr += 1;
            //tempPath += Integer.toString(location);
            
            paths[pointer++] = printTempPath(); //add tempPath into this array
            
            //paths[pointer++] = tempPath;
            //tempPath.substring(0, tempPath.length()-1);
            
            currentPathPtr -= 1;
            tempPath[currentPathPtr] = -1; //need to remove element
            
            
            return;           
        }

        //System.out.println(tempPath);
        int tempPtr = 0;
        //stores possible paths
        int[] temp = new int[size+1];
        
        tempPath[currentPathPtr++] = location;
        //tempPath += Integer.toString(location); //concatenate
        
        for(int i=0; i<size;i++)
        {//generates path
            if(adjMatrix[location][i] == 1)
            {
                
                if(checkPath(tempPath, i))
             // if(checkPath(tempPath, i))    
                {//true->
                    if(tempPath[0] == i)                        
              //    if(Integer.parseInt(String.valueOf(tempPath.charAt(0))) == i)//char value?                            
                    {//duplicate 
                        temp[tempPtr++] = i;
                        
                    }                    
                }
                else
                {//false
                    temp[tempPtr++] = i;          
                    
                }               
            }
        }
        
        //base case
        //need to check if first item in tempPath == current location
        //if path is not available        
        //DFS go back up 1 trace
        if(tempPtr == 0)
        {                                
            paths[pointer++] = printTempPath(); //add to path list
            
            
            tempPath[--currentPathPtr] = -1; //need to remove previous
             
            
            //paths[pointer++] = tempPath;
            //tempPath.substring(0, tempPath.length()-1); 
            
            
            return;           
        }
        
        //System.out.println(Arrays.toString(temp));
        //System.out.println(location);
        
        //recursion
        for(int j =0; j<tempPtr; j++)
        {
            traverse(temp[j], paths, tempPath);
        }      
    }
    
    /*
     * find all possible paths within a graph
     * (paths must be acyclic, 1ABCD1 OK, 1AA <- not ok, 
     * First and last vertex can repeat, middle must be unique.  
     */
    public void findPath()
    {//use recursion
        //holds all paths available
        String[] finalPaths = new String[50];
        for(int i=0; i<size; i++)
        {
            traverse(i, finalPaths, currentPath); //"");
        }
        
        try
        {
            
            outFile.write(Integer.toString(size));
            //outFile.write(size); doesn't work returns []
            outFile.newLine();;
            
            String matrixBuffer = "";
            
            for(int x=0; x<size; x++)
            {
                for(int y=0; y<size; y++)
                {
                    matrixBuffer += adjMatrix[x][y] + " ";
                }
                outFile.write(matrixBuffer);
                outFile.newLine();
                matrixBuffer = "";
            }
            //outFile.write(matrixBuffer);
            
            String buffer = "Paths: ";
            
            for(int j =0; j<pointer; j++)
            {
                buffer += (finalPaths[j] + ", ");                
            }
            
            
            
            outFile.write(buffer);
            outFile.newLine();
            outFile.newLine();
            
        } 
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
    }
    
    public boolean checkPath(int[] tempPath, int edge)
    {
        for(int i =0; i< currentPathPtr; i++)
  //    for(int i =0; i< tempPath.length(); i++)       
        {
            if(tempPath[i] == edge)
            //if(Integer.parseInt(String.valueOf(tempPath.charAt(i))) == edge)
            {
                return true;
            }   
        }
        return false;
      
    }
    
    public String printTempPath()
    {
        String result = "";
        for (int i=0; i<currentPathPtr; i++)
        {
            if(currentPath[i] >= 0)//-1 no good
            {
                result = result + currentPath[i];
            }
        }       
        return result;
    }

    


}
