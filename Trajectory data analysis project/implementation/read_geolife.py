import numpy as np
import pandas as pd
import glob
import os.path
import datetime
import os
from configparser import ConfigParser
import psycopg2
import psycopg2.extras as extras


 # Create an instance of ConfigParser class.
dbConfig = ConfigParser()
# read the configuration file.
dbConfig.read('database.ini')

countRow = 0

config_params = dbConfig._sections['postgresql']


def read_plt(plt_file,user):
    points = pd.read_csv(plt_file, skiprows=6, header=None,
                         parse_dates=[[5, 6]], infer_datetime_format=True)

    # for clarity rename columns
    points.rename(inplace=True, columns={'5_6': 'time', 0: 'lat', 1: 'lon', 3: 'alt'})

    # remove unused columns
    points.drop(inplace=True, columns=[2, 4])

    # generate trajectoryId
    points['trajectoryId'] = os.path.split(user)[1] + os.path.splitext(os.path.basename(plt_file))[0]
    #rearrange list
    points = points[['trajectoryId','time','lat','lon','alt']]

    return points 



def read_user_n_upload(user_folder):

    plt_files = glob.glob(os.path.join(user_folder, 'Trajectory', '*.plt'))
    #intern counter, counts rows per user
    countRow = 0

    try:
        connection = psycopg2.connect(user=config_params['user'],
                                      password=config_params['password'],
                                      host=config_params['host'],
                                      port=config_params['port'],
                                      database=config_params['database'])

        connection.autocommit=False
        cursor = connection.cursor()

        #cursor.execute("SELECT version(), format('Database: %s, User: %s',current_database(),user) db_details ")
        #record = cursor.fetchone()
        #print("You are connected to - ", record,"\n")


        for f in plt_files:

            df = read_plt(f,user_folder)
            #print('The file: ' + os.path.basename(f) + ', contains: ' + str(len(df)))
             # Create a list of tupples from the dataframe values
            tuples = [tuple(x) for x in df.to_numpy()]
            # SQL query to execute
            #insert data from one file
            query  = "INSERT INTO public.\"geolifeDataset\" (trajectoryId,sensor_time,latitude,longitude,altitude) VALUES %s" 
            extras.execute_values(cur=cursor, sql=query, argslist=tuples)    
            countRow += len(df)
            #print('Total rows: ' + str(countRow))
    
        connection.commit()
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
            #print("PostgreSQL connection is closed")
    
    return countRow

def read_all_users(folder):
    subfolders = os.listdir(folder)
    for i, sf in enumerate(subfolders):
        print('[%d/%d] processing user %s' % (i + 1, len(subfolders), sf))
        #using global counter in order to count all rows
        global countRow
        countRow += read_user_n_upload(os.path.join(folder,sf))

read_all_users('Data')
print ('Total rows: ' + str(countRow))
