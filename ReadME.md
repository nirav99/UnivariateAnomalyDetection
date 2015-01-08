Univariate Anomaly Detection
============================

This is a very simple exercise to study uni-variate anomaly detection algorithm. The problem is given a list of human heart rate values, identify heart rate values in the danger zone.

Normal human heart rate is 72 beats per minute. Values greater than 100, when the subject is at rest might indicate tachycardia. Similarly, values lower than 50-55 might indicate bradycardia.

This project is used to model an anomaly detection algorithm to identify such abnormal heart rates.

It is based on univariate anomaly detection algorithm. For simplicity, the training data has only one feature namely, the heart rate.

Process
-------

1. The training data (without labels) with mostly normal and a few abnormal samples is processed to determine average and standard deviation square (i.e. mu and sigmaSquared). <br/>
2. The validation data (with labels) is processed using the mu and sigmaSquared calculated in previous step. <br/>
3. Using the validation data, best value for threshold is calculated <br/>.
4. Any sample with p-value less than threshold is considered anomalous. <br/>

Sample Output
-------------

Heart Rate : 72.0, Is Anomaly : false
Heart Rate : 10.0, Is Anomaly : true
Heart Rate : 210.0, Is Anomaly : true
Heart Rate : 94.0, Is Anomaly : false
Heart Rate : 80.0, Is Anomaly : false
Heart Rate : 83.0, Is Anomaly : false
Heart Rate : 47.0, Is Anomaly : true