
import java.util.*;

/**
 * Calculates the best threshold to determine when given input is anomalous.
 * @author Nirav
 *
 */
class ThresholdSelector
{
  private ArrayList<Double> pValueList;
  private ArrayList<Integer> yLabels;
  
  private double bestThreshold = -1;
  private double bestF1Score = -100000;
  
  private double minValue;
  private double maxValue;
  private double stepSize;
  
  /**
   * Constructor - uses the p values of the validation set (pList) and labels of the validation set (y)
   * @param pList
   * @param y
   */
  ThresholdSelector(ArrayList<Double> pList, ArrayList<Integer> y)
  {
    this.pValueList = pList;
    this.yLabels = y;
    
    minValue = Collections.min(pValueList);
    maxValue = Collections.max(pValueList);
    
    stepSize = 1.0 * (maxValue - minValue) / 1000.0;
    
    System.out.println("Min Value : " + minValue + " Max Value : " + maxValue + " Step size : " + stepSize);
  }
  
  /**
   * Iterates in small steps over the p-values and determines the threshold that maximizes the F1-score for the given validation data set.
   * @return
   */
  double findThreshold()
  {
    ArrayList<Integer> predictions = null;
    double F1Score = -1;
    
    int totalIterations = 0;
    
    for(double threshold = minValue; threshold <= maxValue; threshold = threshold + stepSize)
    {
      predictions = getPredictions(threshold);
      F1Score = calculateF1Score(predictions);
      
//      System.out.println("Threshold : " + threshold + " F1Score : " + F1Score);

      totalIterations++;
      
      if(F1Score > bestF1Score)
      {
        bestF1Score = F1Score;
        bestThreshold = threshold;
      }
    }
    
    System.out.println("Total iterations : " + totalIterations);
    return bestThreshold;
  }
  
  /**
   * Converts the p-value list to an integer list, where 1 represents anomalous samples, and 0 represents regular samples.
   * If p-Value is less than given threshold, that sample is considered anomalous.
   * @param threshold
   * @return
   */
  private ArrayList<Integer> getPredictions(double threshold)
  {
    ArrayList<Integer> predictions = new ArrayList<Integer>();
    
    for(int i = 0; i < pValueList.size(); i++)
    {
      if(pValueList.get(i) < threshold)
        predictions.add(1);
      else
        predictions.add(0);
    }
    return predictions;
  }
  
  /**
   * Calculates F1-score for the given predicted anomalous samples.
   * @param predictions
   * @return
   */
  private double calculateF1Score(ArrayList<Integer> predictions)
  {
    double F1Score = 0;
    
    int truePositives = 0;
    int falsePositives = 0;
    int falseNegatives = 0;
    
    
    for(int i = 0; i < predictions.size(); i++)
    {
      if(predictions.get(i) == 1 && yLabels.get(i) == 1)
        truePositives++;
      else
      if(predictions.get(i) == 1 && yLabels.get(i) == 0)
        falsePositives++;
      else
      if(predictions.get(i) == 0 && yLabels.get(i) == 1)
        falseNegatives++;
    }
    
    double precision = 1.0 * truePositives / (truePositives + falsePositives);
    double recall = 1.0 * truePositives / (truePositives + falseNegatives);
    
    F1Score = 2.0 * precision * recall / (precision + recall);
    return F1Score;
  }
}
