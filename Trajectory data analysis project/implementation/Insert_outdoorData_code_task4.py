import psycopg2,csv
from numpy import *
import pandas as pd


#Establishing the connection
conn = psycopg2.connect(
   database="Sose2020-mobi-mobility", user='student', password='mobi-project2020', host='141.13.162.166', port= '5432'
)
#Setting auto commit false
conn.autocommit = True
cursor = conn.cursor()
#Creating a cursor object using the cursor() method
with open('groundA.csv', newline='') as myFile:
    readerp = csv.reader(myFile)
    csv_data = list(readerp)[1:]
for row in csv_data:
   
    aa=int(row[0])
    bb=int(row[1])
    cc=double(row[2])
    dd=double(row[3])
    ee=double(row[4])
    ff = pd.to_datetime(row[5] + ' ' + row[6])
    cursor.execute("INSERT INTO public.gps_data(userid,trajectoryid,latitude,longitude,altitude,timestamp) VALUES (%s,%s,%s,%s,%s,%s)", (aa,bb,cc,dd,ee,ff))

conn.commit()
print("Record inserted")
