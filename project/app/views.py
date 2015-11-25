from app import app
from flask import render_template

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
