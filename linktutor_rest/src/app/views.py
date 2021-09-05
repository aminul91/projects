from django.shortcuts import render, HttpResponse, get_object_or_404, redirect
import requests
from rest_framework.views import APIView
from rest_framework.decorators import api_view
from rest_framework.response import Response
from app.serializers import *
from app.models import *
from django.views.generic.base import TemplateView
from django.urls import reverse
from rest_framework.permissions import IsAuthenticated
from rest_framework.authentication import SessionAuthentication, BasicAuthentication, TokenAuthentication

"""Here different function are implemented different way & techniques as it is personal and practice project. Production level project should be same way with consistency  """

class HomeView(TemplateView):
    template_name = "app/home.html"

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context['data_list'] = []
        domain = self.request.build_absolute_uri(reverse('tutorials'))
        
        domain_language=domain+"language"
        chart2_data = []
        context["data_list"].append({"path_url":domain,"path_url_language":domain_language})
        return context

class TopicView():
    def tutor_topic(request):
        context = { 
               "data_view" : []
               
        }
        data_r=""
        type_val_info=0
        if request.method == "POST":
            type_val_info = request.POST.get('type_val')
            type_val_info = int(type_val_info)
        print(type_val_info)
        data_r = tutorials_paths.objects.filter(type_value = type_val_info)
        context["data_view"].append({"type_val": type_val_info,"tutor_data":data_r})
        return render(request,"app/tutorials.html",context)

class ApiView(APIView):
    
    def get(self,request):
        data_r = tutorials_paths.objects.all()
        serial_data = linksSerializer(data_r,many = True)
        return Response(serial_data.data)

class ApiInsert(APIView):
    authentication_classes = [BasicAuthentication, TokenAuthentication]
    permission_classes = [IsAuthenticated]

    def get(self,request):
        data_r = tutorials_paths.objects.all()
        serial_data = linksSerializer(data_r,many = True)
        return Response(serial_data.data)

    def post(self,request):
        serializer = linksSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)

class ApiInfo():
    @api_view(['GET'])
    def values(request,language):
        num=0
        language=str(language)
        data_r = language_types.objects.filter(language_name=language)
        print("pp")
        for req in data_r:
            print(req.language_name)
            num=int(req.language_value)
        links_r = tutorials_paths.objects.filter(language_value=num)
        serial_data = linksSerializer(links_r,many = True)
        return Response(serial_data.data)



def host_for_endpoint(request):
    path_url =""
    path_url = request.build_absolute_uri
    return path_url