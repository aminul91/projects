import os.path
os.chdir(os.getcwd() + '\implementation')
import numpy as np
import pandas as pd
import glob
import datetime
import os
from configparser import ConfigParser
import psycopg2
import psycopg2.extras as extras
import staypoint_detection_dbscan as dba
import distbasedstaypoint as distance

# Create an instance of ConfigParser class.
dbConfig = ConfigParser()
# read the configuration file.
dbConfig.read('database.ini')

# select which groups outdoor data will be used for evaluation between detected and ground truth staypoints 
dataPart = 'groupA' # values: groupA or groupB
condition = "not" if dataPart =="groupB" else ""

config_params = dbConfig._sections['postgresql']

#distance in km
eps = 0.02
#minimum points reqiured to create a cluster
minpts = 10

#distance threshold in meters
dist_thres = 70
#time threshold in seconds
time_thres = 600

#define wich staypoint algorithm to run: DBSCAN; DIST; ALL - both algorithm
algorithm = 'ALL'



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
    cursor.execute("SELECT concat(userid,trajectoryId) from public.\"gps_data\" where concat(userid,trajectoryId) " + condition + " like '2____' group by concat(userid,trajectoryId) order by concat(userid,trajectoryId)")
    
    trajectories = cursor.fetchall()     

    detectedStaypoints = pd.DataFrame (columns=['trajectoryId','lat', 'lon', 'alt','arrivaltime','leavetime','algorithm'])

    
    for trajectory in trajectories :
        
        #retrieving all points of the given trajectory
        query = "SELECT concat(userid,trajectoryId), \"timestamp\", latitude, longitude, altitude from public.\"gps_data\" where concat(userid,trajectoryId)='" +  str(trajectory[0]) + "' order by timestamp"
        cursor.execute(query)
        points = cursor.fetchall()   
        #converting list of tuples to dataframe
        points = pd.DataFrame (list(points),columns=['trajectoryId','sensor_time','lat', 'lon', 'alt',])

        
        if algorithm == 'DBSCAN':
            staypoints = dba.staypoint_detection(trajectory=points,epsilon=eps,min_points=minpts)

        
        if algorithm == 'DIST':
            #print('You choose :' + algorithm)
            staypoints = distance.StayPoint_Detection(data=points,distThreh=dist_thres,timeThreh=time_thres)
        
        
        if algorithm == 'ALL':
            
            staypoints_dbscan = dba.staypoint_detection(trajectory=points,epsilon=eps,min_points=minpts)
            staypoints_dist = distance.StayPoint_Detection(data=points,distThreh=dist_thres,timeThreh=time_thres)
            staypoints = pd.concat([staypoints_dbscan,staypoints_dist],ignore_index=True)

        detectedStaypoints = pd.concat([detectedStaypoints,staypoints],ignore_index=True)
        
    
    print('Number of trajectories:' + str(len(trajectories)))
    print(detectedStaypoints)

    #Generate a csv file with the detected staypoints
    detectedStaypoints.to_csv('detectedOutdoorStaypoints.csv')

except (Exception, psycopg2.Error) as error :
    if(connection):
        print("Error: ", error)
        connection.rollback()


finally:
    #closing database connection.
    if(connection):
        cursor.close()
        connection.close()
        print("PostgreSQL connection is closed")
