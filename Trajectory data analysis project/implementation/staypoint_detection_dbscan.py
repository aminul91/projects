import numpy as np
import pandas as pd
from sklearn.cluster import DBSCAN
from sklearn import metrics
from shapely.geometry import MultiPoint
from geopy.distance import great_circle


#finds the most clost point to the center of the cluster
def get_centermost_point(cluster):
    centroid = (MultiPoint(cluster).centroid.x, MultiPoint(cluster).centroid.y)
    centermost_point = min(cluster, key=lambda point: great_circle(point, centroid).m)
    #print(centermost_point)
    return centermost_point


#finding arrival and leavetime of the cluster
def get_arrival_leave_time (cluster,trajectory):
    
    #transfor ndarray to Dataframe
    cluster_df = pd.DataFrame (cluster,columns=['lat','lon'])
    
    #find corresponding sensor_time by mapping lat,lon of original trajectory
    cluster_df['sensor_time'] = cluster_df[['lat','lon']].apply(lambda x: trajectory[(trajectory.lat == x.lat) & (trajectory.lon == x.lon)].iloc[0]['sensor_time'], axis=1)
    
    #print(cluster_df['sensor_time'])
    return pd.Series(data = [min(cluster_df['sensor_time']),max(cluster_df['sensor_time'])])


#dbscan implementation of staypoints detection of the trajectory
def staypoint_detection(trajectory, epsilon, min_points):
    
    staypoint =  pd.DataFrame (columns=['trajectoryId','lat', 'lon', 'alt','arrivaltime','leavetime','algorithm'])

    #represent GPS points as (lat, lon)
    coords = trajectory[['lat', 'lon']].to_numpy()
    
    
    # earth's radius in km
    kms_per_radian = 6371.0088
    # define epsilon as 0.5 kilometers, converted to radians for use by haversine
    epsilon = epsilon / kms_per_radian
    
    # eps is the max distance that points can be from each other to be considered in a cluster
    # min_samples is the minimum cluster size (everything else is classified as noise)
    db = DBSCAN(eps=epsilon, min_samples=min_points, algorithm='ball_tree', metric='haversine').fit(np.radians(coords))
    cluster_labels = db.labels_
    # get the number of clusters (ignore noisy samples which are given the label -1)
    num_clusters = len(set(cluster_labels) - set([-1]))
    
    #print(cluster_labels)
    
    #print ('In TrajectoryId: ' + str(trajectory.iloc[0]['trajectoryId']) + ' clustered ' + str(len(trajectory)) + ' points to ' + str(num_clusters) + ' clusters')
    
    if num_clusters > 0:
    
        # turn the clusters in to a pandas series
        clusters = pd.Series([coords[cluster_labels == n] for n in range(num_clusters)])
        
        # get the centroid point for each cluster
        centermost_points = clusters.map(get_centermost_point)
    
        #transfor Series to Dataframe
        staypoint =  pd.DataFrame (centermost_points.to_list(),columns=['lat','lon'])
    
        #find corresponding alt and trajectoryId by mapping lat,lon of original trajectory
        staypoint['alt'] = staypoint[['lat','lon']].apply(lambda x: trajectory[(trajectory.lat == x.lat) & (trajectory.lon == x.lon)].iloc[0]['alt'], axis=1)
    
        #finding arrivaltime and leavetime for each staypoint wiht the function get_arrival_leave_time
        staypoint[['arrivaltime','leavetime']] = staypoint.apply(lambda x: get_arrival_leave_time(clusters[x.name],trajectory), axis=1)
    
        #defining algorithm name
        staypoint['trajectoryId'] = trajectory.iloc[0]['trajectoryId']

        #defining trajectoryId name
        staypoint['algorithm'] = 'dbscan'

        staypoint = staypoint[['trajectoryId','lat', 'lon', 'alt','arrivaltime','leavetime','algorithm']]

    return staypoint
