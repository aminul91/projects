# Generated by Django 3.1.7 on 2021-08-31 01:03

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='language_types',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('language_name', models.CharField(max_length=50)),
            ],
        ),
        migrations.CreateModel(
            name='suggestions',
            fields=[
                ('suggestor_name', models.CharField(default='anonymous', max_length=50)),
                ('suggestion', models.CharField(default='', max_length=200)),
                ('suggestion_value', models.IntegerField(default=0, primary_key=True, serialize=False)),
            ],
        ),
        migrations.CreateModel(
            name='tutorial_types',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('type_name', models.CharField(max_length=50)),
            ],
        ),
        migrations.CreateModel(
            name='user_infos',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('user_name', models.CharField(default='', max_length=50)),
                ('pass_user', models.CharField(default='', max_length=50)),
                ('user_email', models.EmailField(max_length=254)),
            ],
        ),
        migrations.CreateModel(
            name='tutorials_paths',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('links_name', models.CharField(blank=True, max_length=70)),
                ('links_path', models.CharField(blank=True, max_length=300)),
                ('language_value', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='app.language_types')),
                ('type_value', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='app.tutorial_types')),
            ],
        ),
    ]
