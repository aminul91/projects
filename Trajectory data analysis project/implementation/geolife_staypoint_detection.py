import numpy as np
import pandas as pd
import glob
import os.path
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

countRow = 0

config_params = dbConfig._sections['postgresql']

#distance in km
eps = 0.01
#minimum points reqiured to create a cluster
minpts = 20

#distance threshold in meters
dist_thres = 200
#time threshold in seconds
time_thres = 1800

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
    cursor.execute("SELECT trajectoryId from public.\"geolifeDataset\" where trajectoryId between 20000000000000 and 9090000000000000 group by trajectoryId")
    #trajectoryId=20081023025304
    #trajectoryId between 20000000000000 and 9090000000000000
    #print("Selecting all trajectoryId from geolifeDataset")
    trajectories = cursor.fetchall()     

    for trajectory in trajectories :
        
        print(trajectory[0])

        #retrieving all points of the given trajectory
        query = "SELECT * from public.\"geolifeDataset\" where trajectoryId=" +  str(trajectory[0]) + " order by sensor_time"
        cursor.execute(query)
        #print("Selecting all points from geolifeDataset")
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

        print(staypoints)

        #if there is a staypoint then insert it to database
        if len(staypoints) > 0:
            # Create a list of tupples from the dataframe values for inserting
            tuples = [tuple(x) for x in staypoints.to_numpy()]
            # SQL query to execute
            #insert data from one file
            query  = "INSERT INTO public.\"result-groupA\" (trajectoryId,latitude,longitude,altitude,arrivaltime,leavetime,algorithm) VALUES %s" 
            extras.execute_values(cur=cursor, sql=query, argslist=tuples)    
            countRow += len(staypoints)
            #print('Total rows: ' + str(countRow))
            connection.commit()
    
    print('Number of trajectories:' + str(len(trajectories)))
    #connection.commit()
    #count = cursor.rowcount
    #print (count, "Record inserted successfully into geolifeDataset table")

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

print ('Total rows inserted to result-groupA: ' + str(countRow))



