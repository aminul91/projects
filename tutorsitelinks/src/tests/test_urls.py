from django.test import SimpleTestCase
from django.urls import resolve,reverse
from budget.views import *

class TestUrls(SimpleTestCase):
    def test_home_url_resolve(self):
        url = reverse('home')
        self.assertEqual(resolve(url).func,project_list)
        
    def test_ProjectCreate_url_resolve_second(self):
        url = reverse('tutor_topic')
        self.assertEqual(resolve(url).func.view_class,ProjectCreateView)
    

    