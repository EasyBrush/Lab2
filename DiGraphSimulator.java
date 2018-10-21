import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;




public class DiGraphSimulator
{


    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        String filePath = "PathsGraphInput.txt";
        try
        {
            BufferedReader inFile = new BufferedReader(new FileReader(filePath));
            BufferedWriter outFile = new BufferedWriter(new FileWriter("outFile.txt"));
            
            int chr = inFile.read();
            
            while(chr != -1)
            {
                int size = 0;
                //String n = "";
                //while chr not space, tab, or new line
                while((chr != 9) && (chr != 32) && (chr!= 13))
                {
                    //account for values >9
                    //n = n + chr;
                    size = Character.getNumericValue(chr);
                    chr = inFile.read();
                }
                
                //converts n to int
                //int size = Integer.parseInt(n);
                
                int[][] data = new int[size][size];
                
                chr= inFile.read();               
                
                int x = 0;
                int y = 0;
                 
                while((x < size) && (y <= size))
                {
                    if(y>= size)
                    {//new row
                        y = 0;
                        x +=1;
                    }
                    //48 =0, 49 =1
                    if(chr == 48 || chr == 49)
                    {
                        //converts ascii value to int value
                        data[x][y] = Character.getNumericValue(chr);
                        //next column
                        if(y < size)
                        {
                            //add to same row x
                            y +=1;
                        }

                        chr = inFile.read();
                    }
                    else
                    {
                        chr = inFile.read();
                    }
                }
                
                //at this point we have a full adjacency matrix? yes
                System.out.println(data);
                
                Matrix matrix = new Matrix(data, size, outFile);
                matrix.findPath();
                

                chr = inFile.read();
                System.out.println(chr);
            }
            System.out.println("testtttttt");
            inFile.close();
            outFile.close();

        } 
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

    }


}
