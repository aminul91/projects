from app.models import Article
from rest_framework import serializers


class ArticleSerializer(serializers.ModelSerializer):
    # Model serializer
    class Meta:
        model = Article
        fields = ['id', 'title', 'authorname', 'email', 'date']

