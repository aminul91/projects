from django.urls import path
from app.views import *

app_name = 'app'

urlpatterns = [
    # url: '/', name = app:home
    path('', HomeView.as_view(), name="home"),
    path('tutor_topic/', TopicView.tutor_topic, name="tutor_topic"),
    path('homepage/', HomeView.as_view(), name="homepage"),
    # path('api_links/', views.AboutView.api_links, name="api_links"),
]
