from flask import Flask
from flask.ext.sqlalchemy import SQLAlchemy
import os
from flask.ext.login import LoginManager

app = Flask(__name__)
app.config.from_object('config')

from app import views
