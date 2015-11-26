from app import app
from flask import render_template
import urllib, json

@app.route("/")
@app.route("/index")

def index():
    user = {"nickname": "Eldin",
            "age": "22",
            "school": "hro",
            "company": "alternate"}
    return render_template("index.html",
                            title = "home",
                            user = user)

urlCon = "http://localhost/index.php/connections/value/false"
responseCon = urllib.urlopen(urlCon)
dataCon = json.loads(responseCon.read())
print dataCon
datetime = ""
i = 0
