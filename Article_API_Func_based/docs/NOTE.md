# Note

## Install Miniconda in Linux

- Go to: [this link](https://docs.conda.io/en/latest/miniconda.html)
- Download the .sh file from the list for Linux.

- Make it executable:
`chmod +x filename.sh`

- Run the file:
`./filename.sh`

- Set the environment variable:
`export PATH=~/anaconda3/bin:$PATH`

- Check the installation:
`conda --version`

## Create Virtual Environment with Miniconda

```sh
conda create --name djh python=3.7
conda activate djh
pip install -r requirements.txt
pip freeze > requirements.txt

pip install django
```

## Configure Project for Heroku

- Create the following files in the root directory.
  - requirements.txt
  - Procfile
  - runtime.txt

- Procfile
  - `web: gunicorn --pythonpath src core.wsgi --log-file -`
  - Here project name is core. And core is situated inside src directory.

## Environmental Variables

- For PostgreSQL
  - DEV = False
  - DATABASE_URL = YOUR_DATABASE_URL

- For SQLite
  - Configuration not required.
  - When DEV is not set as environmental variable, DEV = True.
  - If DEV = True, SQLite is used.

## Deploying in Heroku

- Create an app in Heroku and attach the git repository with it.
- Set environment variable DEV = False
- In the heroku console, run the following commands.

```shell
python ./src/manage.py makemigrations
python ./src/manage.py migrate
python ./src/manage.py createsuperuser
```

## Connect the Heroku PostgresSQL DB to local pgAdmin

- Get the DATABASE_URL from Heroku
- DATABASE_URL = postgres://username:password@host:port/db_name
- In the pgAdmin, connect ot the PostgreSQL server using the credentials.
