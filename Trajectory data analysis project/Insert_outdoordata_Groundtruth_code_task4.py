import psycopg2,csv
from numpy import *
import pandas as pd


#Establishing the connection
conn = psycopg2.connect(
   database="Sose2020-mobi-mobility", user='student', password='mobi-project2020', host='141.13.162.166', port= '5432'
)

conn.autocommit = True
cursor = conn.cursor()

with open('Groundtruth_file_name.csv', newline='') as myFile:
    readerp = csv.reader(myFile)
    csv_data = list(readerp)[1:]
for row in csv_data:
   
    aa=int(row[0])
    bb=pd.to_datetime(row[1] + ' ' + row[2])
    cc=pd.to_datetime(row[1] + ' ' + row[3])
    dd=row[4]
    ee=double(row[5])
    ff = double(row[6])
    gg = double(row[7])
    cursor.execute("INSERT INTO public.outdoor_groundtruth(userid,starttime,endtime,label,latitude,longitude,altitude) VALUES (%s,%s,%s,%s,%s,%s,%s)", (aa,bb,cc,dd,ee,ff,gg))

conn.commit()
         




# Commit your changes in the database

print("Records inserted groundt truth........")

# Closing the connect
