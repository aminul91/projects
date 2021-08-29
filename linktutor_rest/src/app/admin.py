from django.contrib import admin
from app.models import *
# Register your models here.

from .models import *
admin.site.register(user_infos)
admin.site.register(tutorial_types)
admin.site.register(language_types)
admin.site.register(tutorials_paths)
admin.site.register(suggestions)
