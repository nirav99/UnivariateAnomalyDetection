
import java.io.*;
import java.util.*;

/**
 * Applies uni-variate anomaly detection on data with single feature and determines anomalous samples. 
 * @author Nirav
 *
 */
public class Detector
{
  private double mu;
  private double sigmaSquared;
  
  private File trainingFile;
  private File validationFile;
  
  private ArrayList<Double> X; // Training data

  private double threshold = 0; // To be determined from Cross validation set

  
  public Detector(File trainingFile, File validationFile)
  {
    this.trainingFile = trainingFile;
    this.validationFile = validationFile;
  }
  
  /**
   * Calculates the mu and sigmaSquared parameters on training data.
   * Finds the best threshold value using these parameters on the validation data.
   * @throws IOException
   */
  public void trainModel() throws IOException
  {
    X = readTrainingData();
    mu = calculateAverage(X);
    sigmaSquared = calculateSigmaSquared(X, mu);
    
    ArrayList<Double> pList = calculatePValues(X, mu, sigmaSquared);
    
//    for(int i = 0; i < pList.size(); i++)
//      System.out.println("X : " + X.get(i) + " P : " + pList.get(i));
    
    selectThresholdOnValidationData();
    
    System.out.println("Best Threshold : " + threshold);
  }
  
  /**
   * Selects the best threshold for determining when a given sample is anomalous
   * @throws IOException
   */
  private void selectThresholdOnValidationData() throws IOException
  {
    DataReader dataReader = new DataReader(validationFile);
    
    ArrayList<Double> xVal = dataReader.X();
    ArrayList<Integer> yVal = dataReader.y();
    
    ArrayList<Double> pVal = calculatePValues(xVal, mu, sigmaSquared);
 
    ThresholdSelector thresholdSelector = new ThresholdSelector(pVal, yVal);
    threshold = thresholdSelector.findThreshold();
  }
  
  /**
   * Returns true if the given input is anomalous, false otherwise
   * @param input
   * @return
   */
  public boolean isSampleAnomalous(double input)
  {
    double pValue = CalculatePValue(input);
    
    return (pValue < threshold) ? true : false;
  }
  
  /**
   * Helper method to read the training data
   * @return
   * @throws IOException
   */
  private ArrayList<Double> readTrainingData() throws IOException
  {
    DataReader dataReader = new DataReader(trainingFile);
    
    return dataReader.X();
  }
  
  /**
   * Helper method to calculate the average mu of the given data
   * @param input
   * @return
   */
  private double calculateAverage(ArrayList<Double> input)
  {
    double sum = 0;
    for(int i = 0; i < input.size(); i++)
      sum += input.get(i);
    
    return 1.0 * sum / input.size();
  }
  
  /**
   * Helper method to calculate sigmaSquared (squared std deviation) on given data
   * @param input
   * @param mu
   * @return
   */
  private double calculateSigmaSquared(ArrayList<Double> input, double mu)
  {
    double sum = 0;
    
    for(int i = 0; i < input.size(); i++)
    {
      sum = sum + (Math.pow(input.get(i) - mu, 2.0));
    }
    
    return 1.0 * sum / input.size();
  }
  
  /**
   * Calculates the p value on the given dataset
   * @param input
   * @param mu
   * @param sigmaSquared
   * @return
   */
  private ArrayList<Double> calculatePValues(ArrayList<Double> input, double mu, double sigmaSquared)
  {
    ArrayList<Double> pList = new ArrayList<Double>();
    
    double pValue = 0;
    double secondTerm = 0;
    double firstTerm = 1.0 / (Math.sqrt(2 * Math.PI * sigmaSquared));
    
    for(int i = 0; i < input.size(); i++)
    {
      secondTerm = -1.0 * Math.pow(input.get(i) - mu, 2.0) / (2 * Math.PI * sigmaSquared);
      pValue = firstTerm * Math.pow(Math.E, secondTerm);
      pList.add(pValue);
    }
    
    return pList;
  }
  
  /**
   * Calculates the p value of the given sample
   */
  private double CalculatePValue(double input)
  {
    double firstTerm = 1.0 / (Math.sqrt(2 * Math.PI * sigmaSquared));
    double secondTerm = -1.0 * Math.pow(input - mu, 2.0) / (2 * Math.PI * sigmaSquared);
    double pValue = firstTerm * Math.pow(Math.E, secondTerm);
    
    return pValue;
  }
}
