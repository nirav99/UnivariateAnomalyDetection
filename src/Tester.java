
import java.io.File;

public class Tester
{
  public static void main(String[] args)
  {
    try
    {
      File trainingFile = new File("C:\\Users\\Nirav\\workspace\\UnivariateAnomalyDetection\\data\\HeartRate.csv");
      File validationFile = new File("C:\\Users\\Nirav\\workspace\\UnivariateAnomalyDetection\\data\\HeartRateValidation.csv");
      
      Detector detector = new Detector(trainingFile, validationFile);
      detector.trainModel();
      
      double []unknownSamples = {72, 10, 210, 94, 80, 83, 47};
      boolean result;
      
      for(double sample : unknownSamples)
      {
        result = detector.isSampleAnomalous(sample);
        System.out.println("Heart Rate : " + sample + ", Is Anomaly : " + result);
      }
    }
    catch(Exception e)
    {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
  }
}
