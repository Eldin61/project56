# import the Flask class from the flask module
from flask import Flask, render_template, redirect, url_for, request, session
from functools import wraps
import urllib, json
# create the application object
app = Flask(__name__)

app.secret_key = 'gitrikt'

def login_required(f):
    @wraps(f)
    def wrap(*args, **kwargs):
        if 'logged_in' in session:
            return f(*args, **kwargs)
        else:
            return redirect(url_for('login'))
    return wrap
# use decorators to link the function to a url
@app.route('/')
@login_required
def home():
    return render_template("index.html")  # return a string

@app.route('/unique')
@login_required
def unique():
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
    return render_template('index.html', data=data)

@app.route('/welcome')
def welcome():
    return render_template('welcome.html')  # render a template

    # route for handling the login page logic
@app.route('/login', methods=['GET', 'POST'])
def login():
    error = None
    if request.method == 'POST':
        if request.form['username'] != 'admin' or request.form['password'] != 'admin':
            error = 'Invalid Credentials. Please try again.'
        else:
            session['logged_in'] = True
            return redirect(url_for('home'))
    return render_template('login.html', error=error)

@app.route('/logout')
@login_required
def logout():
    session.pop('logged_in', None)
    return redirect(url_for('welcome'))
# start the server with the 'run()' method
if __name__ == '__main__':
    app.run(debug=True)
