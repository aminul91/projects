from django.urls import path
from app import views

app_name = 'app'

urlpatterns = [
    # url: '/', name = app:home
    path('', views.home_view, name="home"),
    path('tutor_topic/', views.tutor_topic, name="tutor_topic"),
    path('homepage/', views.home_view, name="homepage"),
    path('api_links/', views.api_links, name="api_links"),
]
