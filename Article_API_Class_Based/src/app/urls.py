from django.urls import path
from app.views import *

app_name = 'app'

urlpatterns = [
    #path('articlelistapi/', articlelist, name='articlelist'),
    #path('articledetails/<int:pk>/', articledetails, name='articledetails'),
    path('articlelist/', Articlelist.as_view(), name='articlelist'),
    path('articleinfo/<int:id>/', ArticleDetails.as_view(), name='articleinfo'),

]
