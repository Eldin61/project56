from flask import Flask, send_from_directory
import os
from flask_googlemaps import GoogleMaps

app = Flask(__name__)
app.config.from_object('config')

from app import views
GoogleMaps(app)

@app.route('/bower_components/<path:path>')
def send_bower(path):
    return send_from_directory(os.path.join(app.root_path, 'bower_components'), path)

@app.route('/dist/<path:path>')
def send_dist(path):
    return send_from_directory(os.path.join(app.root_path, 'dist'), path)

@app.route('/js/<path:path>')
def send_js(path):
    return send_from_directory(os.path.join(app.root_path, 'js'), path)

from app import views

# showcase opdrachtD
