import pandas as pd
import math
import numpy as np
import urllib.request as urllib2
import json



#  Convert degrees to Radians
def rad(d):
    return float(d) * math.pi/180.0

# Earth's radius in kilometers
EARTH_RADIUS=6378.137
# Returns distance between 2 points
def GetDistance(lng1,lat1,lng2,lat2):
    radLat1 = rad(lat1)
    radLat2 = rad(lat2)
    a = radLat1 - radLat2
    b = rad(lng1) - rad(lng2)
    s = 2 * math.asin(math.sqrt(math.pow(math.sin(a/2),2) +math.cos(radLat1)*math.cos(radLat2)*math.pow(math.sin(b/2),2)))
    s = s * EARTH_RADIUS
    s = round(s * 10000,2) / 10
    return s

# Calculates the mean of the co-ordinates
def ComputMeanCoord(data,i,j):
    latitude=list(data['lat'])
    longitude=list(data['lon'])
    amount=j-i+1
    if amount==0:
        return float(latitude[i]),float(longitude[i])
    sumLongitude=0
    sumLatitude=0
    for k in range(i,j+1):
        sumLatitude+=float(latitude[k])
        sumLongitude+=float(longitude[k])
    return sumLatitude/amount,sumLongitude/amount

# detecting staypoints using distance based algorithm, parameters- Trajectories data, Distance threshold, Time threshold
def StayPoint_Detection(data,distThreh,timeThreh):

    staypoint =  pd.DataFrame (columns=['trajectoryId','lat', 'lon', 'alt','arrivaltime','leavetime','algorithm'])

    latitude=list(data['lat'])
    longitude=list(data['lon'])
 
    i=0
    SP=[]
    pointNumber=len(data) 
    while i<pointNumber:
        j=i+1
        Token=0
        while j<pointNumber:
            dist=GetDistance(longitude[i],latitude[i],longitude[j],latitude[j])
            if dist>float(distThreh):
                Time = ((data.iloc[j-1]['sensor_time'] - data.iloc[i]['sensor_time']) / np.timedelta64(1, 's'))                
                if Time>timeThreh:
                    S_latitude,S_longitude=ComputMeanCoord(data,i,j-1)                    
                    SarvT= data.iloc[i]['sensor_time']                    
                    SlevT= data.iloc[j-1]['sensor_time'] 
                    SP.append([S_longitude,S_latitude,SarvT,SlevT])
                    i=j
                    Token=1
                break
            j+=1
        if Token!=1:
            i+=1

    if len(SP) > 0:
        staypoint =  pd.DataFrame (SP,columns=['lon','lat', 'arrivaltime', 'leavetime'])  
         #defining  trajectoryId name
        staypoint['trajectoryId'] = data.iloc[0]['trajectoryId']

        staypoint['alt'] = 0

        #defining algorithm name
        staypoint['algorithm'] = 'dist'
        staypoint = staypoint[['trajectoryId','lat', 'lon', 'alt','arrivaltime','leavetime','algorithm']]
    return staypoint
