import os.path
os.chdir(os.getcwd() + '\implementation')
import numpy as np
import pandas as pd
import glob
import datetime
import os
import math
from configparser import ConfigParser
import psycopg2
import psycopg2.extras as extras
import staypoint_detection_dbscan as dba
import distbasedstaypoint as distance

# Create an instance of ConfigParser class.
dbConfig = ConfigParser()
# read the configuration file.
#dbConfig.read('database.ini')
dbConfig.read('database.ini')

countRow = 0

config_params = dbConfig._sections['postgresql']

EARTH_RADIUS=6378.137

#Create empty lists
TP = []
FP = []
FN = []

#select algorithm to evaluate: dbscan or dist
algorithm = 'dbscan'

# select which groups outdoor data will be used for evaluation between detected and ground truth staypoints 
dataPart = 'groupA' # values: groupA or groupB
condition = "not" if dataPart =="groupB" else ""

#Read detected staypoints csv file
detectedPointsDF = pd.read_csv('detectedOutdoorStaypoints.csv')

#comparing only points detected by distance based algorithm
detectedPointsDF = detectedPointsDF[detectedPointsDF['algorithm'] == algorithm]



def rad(d):
    return float(d) * math.pi/180.0

def GetDistance(lng1,lat1,lng2,lat2):
    radLat1 = rad(lat1)
    radLat2 = rad(lat2)
    a = radLat1 - radLat2
    b = rad(lng1) - rad(lng2)
    s = 2 * math.asin(math.sqrt(math.pow(math.sin(a/2),2) +math.cos(radLat1)*math.cos(radLat2)*math.pow(math.sin(b/2),2)))
    s = s * EARTH_RADIUS
    s = round(s * 10000,2) / 10
    return s 

def difference_time(time_stamp_one, time_stamp_two):
    return (time_stamp_two - time_stamp_one).total_seconds()
 
def find_overlapping(row, anotherow):
    # assuming timestamps is a method that'll return all timestamps
    # of an activity in an array like this [[start_time, end_time] , [start_time, end_time]]
    overlap_duration = 0
            
    # checking if timestamps overlap
    if row.arrival_time <= anotherow.arrivaltime <= row.leave_time:
        # assuming diff_in_minutes will return time difference between 2 timestamps in minutes
        if anotherow.leavetime <= row.leave_time: 
            overlap_duration += difference_time(anotherow.arrivaltime, anotherow.leavetime)
        else:
            overlap_duration += difference_time(anotherow.arrivaltime, row.leave_time)
    elif anotherow.arrivaltime <= row.arrival_time <= anotherow.leavetime: 
        if row.leave_time <= anotherow.leavetime:
            overlap_duration += difference_time(row.arrival_time, row.leave_time)
        else:
            overlap_duration += difference_time(row.arrival_time, anotherow.leavetime)
                        
 
    return overlap_duration


try :
    connection = psycopg2.connect(user=config_params['user'],
    password=config_params['password'],
    host=config_params['host'],
    port=config_params['port'],
    database=config_params['database'])

    connection.autocommit=False
    cursor = connection.cursor()

    #assuring successful connection to postgresql
    cursor.execute("SELECT version(), format('Database: %s, User: %s',current_database(),user) db_details ")
    record = cursor.fetchone()
    print("You are connected to - ", record,"\n")


    #retrieving all trajectoryId
    cursor.execute("SELECT * from public.\"outdoor_groundtruth\" where userid::text " + condition + " like '2____' and starttime is not null and endtime is not null and extract(epoch from endtime-starttime)>=600")
    #print("Selecting all trajectoryId from outdoor_groundtruth")
    staypoints = cursor.fetchall()  
   
    #construct a dataframe
    groundTruthDF =  pd.DataFrame(staypoints, columns=['id','trajectoryId','arrival_time','leave_time', 'label', 'lat', 'lon', 'alt'])

    print(groundTruthDF['trajectoryId'].unique())
    print(detectedPointsDF['trajectoryId'].unique())
    
    #print(groundTruthDF.shape)
    #print(groundTruthDF)

    #print(detectedPointsDF.shape)
    #print(detectedPointsDF)

    #converting string datatype of Time columns to Datetime datatype
    detectedPointsDF['arrivaltime'] = detectedPointsDF['arrivaltime'].astype('datetime64[ns]')
    detectedPointsDF['leavetime'] = detectedPointsDF['leavetime'].astype('datetime64[ns]')
    
    #print('The op csvv  time dt is {}'.format(type(detectedPointsDF['arrivaltime'][9])))
    
    trajectories = detectedPointsDF['trajectoryId'].unique()

    #Iteration through trajectories
    for trajectory in trajectories:
        #selecting all staypoints of exact trajectory in ground truth
        groundTruthDF_t = groundTruthDF[groundTruthDF['trajectoryId'] == trajectory]
        #selecting all staypoints of exact trajectory in detected staypoints
        detectedPointsDF_t = detectedPointsDF[detectedPointsDF['trajectoryId'] == trajectory]

        # Iterating through the rows of the groundTruth Dataframe and Detected Staypoints Dataframe and comparing distance 
        # and time to find out TRUE POSITIVES
        for row in groundTruthDF_t.itertuples():
            for anotherow in detectedPointsDF_t.itertuples():
                calcDist = GetDistance(row.lon, row.lat, anotherow.lon, anotherow.lat)
                durationRate = find_overlapping(row,anotherow) / difference_time(row.arrival_time,row.leave_time)
                #print("DurationRate:" + str(durationRate) + ", Distance: " + str(calcDist))
                if calcDist< 10 and durationRate > 0.7 :
                    TP.append(anotherow)
    
    print('')
    print(f'The total num of TRUEPOSITIVES are {len(TP)}')
    FN

    # calculating number of rows by unpacking the tuples retrieved from df.shape
    detectedPointsDFRows, detectedPointsDFCols = detectedPointsDF.shape
    groundTruthDFRows, groundTruthDFCols = groundTruthDF.shape

    print(f'Total rows in GroundTruth are {groundTruthDFRows} and total rows in detectedSP are {detectedPointsDFRows}')

        
    # Function to handle zerodivision error and calculate Eval metrics
    def calcPrecision(tp, dtRows):
        try:
            return len(tp) / dtRows
        except ZeroDivisionError:
            return 0
    
    def calcRecall(tp, gtRows):
        try:
            return len(tp) / gtRows
        except ZeroDivisionError:
            return 0

    precision = calcPrecision(TP, detectedPointsDFRows)
    recall = calcRecall(TP, groundTruthDFRows)

    def calcF1score(precision,recall):
        try:
            return 2 * ((precision * recall) / (precision + recall))
        except ZeroDivisionError:
            return 0    

    
    f1Score = calcF1score(precision, recall)
     
    # Begin Printing  Confusion Matrix to console <-
    print('\nConfusion matrix: ')
    print('Ground Truth/Detected     Positive|Negative')
    print('Positive                     ' + str(len(TP)) + '         ' + str(groundTruthDFRows - len(TP)))
    print('Negative                     ' + str(detectedPointsDFRows - len(TP)) )
    
    # End Printing  Confusion Matrix to console <-

    #Output values
    print('\n\nThe precision value is {}'.format(precision))
    print(f'Recall valu is {recall}')
    print(f'F1 score value is {f1Score}')
 
    

except (Exception, psycopg2.Error) as error :
    if(connection):
        print("Error: ", error)
        connection.rollback()


finally:
    #closing database connection.
    if(connection):
        cursor.close()
        connection.close()
        print("\nPostgreSQL connection is closed")
