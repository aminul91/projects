# django Article app REST frame work [CLASS based View Implementation] 

A REST API developed with class based view

Implemented Django REST framework

This is Developed as practice project where I implement Class based View

Another version of project is available which I developed with Function based view

This API provides Article information and article's author's information.


# ENDPOINT

Go to the folder where manage.py located
RUn the command 'python manage.py makemigrations'
RUn the command 'python manage.py migrate'
RUn the command 'python manage.py runserver'
If you run the project 8000 port

http://localhost:8000/articlelist/


## Create Virtual Environment with Miniconda
If you do not have django Then

```sh
conda create --name djh python=3.7
conda activate djh
pip install -r requirements.txt
pip freeze > requirements.txt

pip install django
