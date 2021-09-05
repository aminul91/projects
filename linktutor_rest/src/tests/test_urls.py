from django.test import SimpleTestCase
from django.urls import resolve,reverse
from app.views import *

class TestUrls(SimpleTestCase):
    def test_get_url_resolve(self):
        url = reverse('tutorials')
        self.assertEqual(resolve(url).func.view_class, ApiView)
        print("four")
    
    def test_insert_update_url_resolve(self):
        url = reverse('tutorial_insert')
        self.assertEqual(resolve(url).func.view_class, ApiInsert)
        print("five")
        
    
    
    
