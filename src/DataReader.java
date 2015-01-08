
import java.io.*;
import java.util.*;

/**
 * Reads the specified training data file or cross validation file 
 * into X (input variable) and Y (class label).
 * Values of 1 for Y represent anomalous values and values of 0 represent normal values.
 * @author Nirav
 *
 */
public class DataReader
{
  private ArrayList<Double> X = null;
  private ArrayList<Integer> y = null;
  
  public DataReader(File inputFile) throws IOException
  {
    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    
    String line = null;
    String fields[] = null;
    
    X = new ArrayList<Double>();
    y = new ArrayList<Integer>();
    
    while((line = reader.readLine()) != null)
    {
      line = line.trim();
      fields = line.split(",");
      
      X.add(Double.parseDouble(fields[0]));
      if(fields.length == 2)
        y.add(Integer.parseInt(fields[1]));
    }
    reader.close();
  }
  
  /**
   * Returns the input data (in this case, single dimensional heart rate
   * @return
   */
  public ArrayList<Double> X()
  {
    return X.size() > 0 ? X : null;
  }
  
  /**
   * Returns the labels for the validation data.
   * @return
   */
  public ArrayList<Integer> y()
  {
    return y.size() > 0 ? y : null;
  }
}
