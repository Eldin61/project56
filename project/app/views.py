from flask import render_template, flash, redirect
from app import app
from .forms import LoginForm
import urllib, json
from flask import request
from flask.ext.login import login_user, logout_user, current_user, login_required

@app.route("/login", methods=["POST", "GET"])
def index():
    return render_template("login.html")

@app.route("/profile", methods=["POST", "GET"])
def login():
    username = request.form["sendUser"]
    password = request.form["sendPass"]
    if username == "Eldin":
        if password == "root":
            return render_template("index.html")

@app.route("/test", methods=["POST", "GET"])
def test():

    url = "http://localhost/index.php/events//?where[0][col]=value&where[0][op]==&where[0][val]=true"
    response = urllib.urlopen(url)
    jsonObj = json.loads(response.read())

    i = 0
    unitid = []
    while i < len(jsonObj["data"]):
        unitid.append(jsonObj["data"][i]["unitid"])
        i = i + 1
    unitid2 = list(set(unitid))
    j = 0
    data = []
    totalDict = {}
    while j < len(unitid2):
        url2 = "http://localhost/index.php/events//?where[0][col]=value&where[0][op]==&where[0][val]=true&where[1][col]=unitid&where[1][op]==&where[1][val]=" + str(unitid2[j])
        response2 = urllib.urlopen(url2)
        data2 = json.loads(response2.read())

        total = data2["total"]
        totalDict = {"id": str(unitid2[j]), "ignitions": str(total)}
        data.append(totalDict.copy())
        j = j + 1

    form = LoginForm()
    return render_template("index.html", form = form, data = data)

@app.route("/test2", methods=["POST"])
def test2():
    print "clicked test2"
    print (request.form["testreq"])
