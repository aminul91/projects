from django.shortcuts import render, HttpResponse, get_object_or_404, redirect
import requests, json
from rest_framework.views import APIView
from rest_framework.decorators import api_view
from rest_framework.response import Response
from app.serializers import *
from app.models import *
from django.views.generic.base import TemplateView
from django.urls import reverse
from rest_framework.permissions import IsAuthenticated
from rest_framework.authentication import SessionAuthentication, BasicAuthentication, TokenAuthentication

class HomeView(TemplateView):
    template_name = "app/home.html"

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        domain,domain_auth_url = url_endpoint()
        response= requests.get(domain)  
        data_r = response.json()
        context['data_list'] = []
        domain_language=domain+"language"
        chart2_data = []
        context["data_list"].append({"tutorial":data_r,"path_url":domain,"auth_url":domain_auth_url,"path_url_language":domain_language,"Chart_t":chart2_data})
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
        data_r = tutorials_paths.objects.filter(type_value = type_val_info)
        context["data_view"].append({"type_val": type_val_info,"tutor_data":data_r})
        return render(request,"app/tutorials.html",context)

class ApiView(APIView):
    def get(self,request):
        data_r = tutorials_paths.objects.all()
        serial_data = linksSerializer(data_r,many = True)
        return Response(serial_data.data)



def api_links(request):
    context = { 
               "data_view" : []
               
        }
    
    domain = request.get_host()
    domain="http://"+domain+"/tutorials/"
    response = requests.get(domain).json()
    context["data_view"].append({"response": response})
    return render(request, "app/api_links.html",context)

class ApiInfo():
    @api_view(['GET'])
    def values(request,username):
        num=0
        username=str(username)
        data_r = language_types.objects.filter(language_name=username)
        for req in data_r:
            print(req.language_name)
            num=int(req.language_value)
        links_r = tutorials_paths.objects.filter(language_value=num)
        serial_data = linksSerializer(links_r,many = True)
        return Response(serial_data.data)

def values_double(request,language,tutorial):
    context = { 
               "data_view" : []
               
        }
    context["data_view"].append({"language": language,"tutorial": tutorial})
    return render(request, "app/message.html",context)

def host_for_endpoint(request):
    path_url =""
    path_url = request.build_absolute_uri
    return path_url

def url_endpoint():
    try:
        with open("config/config.json") as json_file:
                json_data = json.load(json_file)
                if json_data is not None:
                    domain_public = json_data["get_url"]
                    domain_protected = json_data["other_operation_url"]
                    json_file.close()
                    return domain_public,domain_protected
                else:
                    print("json file is empty")
    except IOError:
        print("file not found")

    except ValueError:
        print("No information from Config file")