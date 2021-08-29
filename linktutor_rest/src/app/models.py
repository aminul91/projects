from django.db import models

# Create your models here.
class user_infos(models.Model):
    user_name = models.CharField(max_length=50,default="")
    pass_user = models.CharField(max_length=50,default="")
    user_email = models.EmailField()
    def __str__(self):
        return self.user_name

class tutorial_types(models.Model):
    type_name = models.CharField(max_length=50)
    type_value = models.IntegerField(default=0,primary_key = True)
    def __str__(self):
        return self.type_name

class language_types(models.Model):
    language_name = models.CharField(max_length=50)
    language_value = models.IntegerField(default=0,primary_key = True)
    def __str__(self):
        return self.language_name   


class suggestions(models.Model):
    suggestor_name = models.CharField(max_length=50,default="anonymous")
    suggestion = models.CharField(max_length=200,default="")
    suggestion_value = models.IntegerField(default=0,primary_key = True)
    def __str__(self):
        return self.suggestion_value

class tutorials_paths(models.Model):
    links_name = models.CharField(max_length=70,blank=True)
    links_path = models.CharField(max_length=300,blank=True)
    links_val = models.IntegerField(default=0,primary_key = True)
    type_value = models.ForeignKey(tutorial_types, on_delete=models.CASCADE)
    categ_name = models.CharField(max_length=100,blank=True)
    language_value = models.ForeignKey(language_types, on_delete=models.CASCADE)
    language_type = models.CharField(max_length=100,blank=True)
    def __str__(self):
        return self.links_path
