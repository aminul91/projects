from django.urls import path
from app.views import *

app_name = 'app'

urlpatterns = [
    path('articlelistapi/', articlelist, name='articlelist'),
    path('articledetails/<int:pk>/', articledetails, name='articledetails'),

]
