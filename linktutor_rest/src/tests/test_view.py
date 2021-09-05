from django.test import TestCase, Client
from django.urls import reverse
from app.models import *
from app.views import *
from rest_framework import status

class TestViews(TestCase):
    def setUp(self):
        self.client = Client()
        self.tutorials_url = reverse('tutorials')
        self.tutorials_insert = reverse('tutorial_insert')
    
    def test_link_list_POST(self):
        tutorial1 = tutorial_types.objects.create(
            type_name = "Movie_hit",
            type_value = 15,
        )
        language1 = language_types.objects.create(
            language_name = "eng",
            language_value = 31,
        )
        response = self.client.post('/tutorial_insert/', {'links_name': 'Bongo Academy',
                                                          'links_path': 'bvhfggf',
                                                          'categ_name': 'Movie', 
                                                          'type_value': tutorial1.type_value, 
                                                          'language_value': language1.language_value, 
                                                          'language_type': 'english'})
        print(response.status_code)
        self.assertEqual(response.status_code,status.HTTP_201_CREATED)
        print("seven")
        
    def test_project_list_GET(self):
        response = self.client.get(self.tutorials_url)
        self.assertEqual(response.status_code,200)
        print("one")
    
   
    
    
        


        
    
    
    
