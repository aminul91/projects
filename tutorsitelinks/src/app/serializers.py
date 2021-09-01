from rest_framework import serializers
from app.models import *
# Register your models here.


class tutorialSerializer(serializers.ModelSerializer):
    class Meta:
        model = tutorial_types
        fields = ['type_name']

class linksSerializer(serializers.ModelSerializer):
    class Meta:
        model = tutorials_paths
        fields = ('links_name','links_path','type_value','language_value')
