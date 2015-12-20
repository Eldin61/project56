from flask import Flask, render_template, redirect, url_for, request, session, g
from functools import wraps
from app import app
import analyse
import psycopg2
from flask_googlemaps import GoogleMaps
from flask_googlemaps import Map
from flask import request

def login_required(f):
    @wraps(f)
    def wrap(*args, **kwargs):
        if 'logged_in' in session:
            return f(*args, **kwargs)
        else:
            return redirect(url_for('login'))
    return wrap


def connect_db():
    return psycopg2.connect(database='users', user='userdb', password='root', host='127.0.0.1', port='5432')

@app.route('/logout')
@login_required
def logout():
    session.pop('logged_in', None)
    return redirect(url_for('login'))

@app.route('/login', methods=['POST', 'GET'])
def login():
    if request.method == 'POST':
    	db = connect_db()
        cur = db.cursor()

        username = request.form['email']
        cur.execute("SELECT password from users where username=\'" + username + "\'")
        rows = cur.fetchall()
        for row in rows:
        	password = row[0]
        db.commit()
        db.close()
        if request.form['password'] != password:
            print 'invalid Credentials'
        else:
            session['logged_in'] = True
            return redirect(url_for('index'))
        
    return render_template('pages/login.html', title="Login")

@app.route('/')
@app.route('/index.html')
@login_required
def index():
    analyseobject = analyse.Analyse()
    unitid = analyseobject.allunitid_method()
    return render_template('pages/index.html', title="Home", header="Home", unitid = unitid)

@app.route('/blank.html')
@login_required
def blank():
    return render_template('pages/blank.html', title="Blank", header="Blank", nav="Blank Page")

@app.route('/flot.html')
@login_required
def flot():
    return render_template('pages/flot.html', title="Flot", header="Flot Charts", nav="Flot Page")

@app.route('/morris.html')
@login_required
def morris():
    analyseobject = analyse.Analyse()
    unitid = analyseobject.allunitid_method()
    satavr = analyseobject.sataliteavarage_method()
    return render_template('pages/morris.html', title="Morris", header="Morris.js Charts", nav="Morris Page",unitid = unitid,satavr=satavr)

@app.route('/graphs.html')
@login_required
def graphs():
    analyseobject = analyse.Analyse()
    unitid = analyseobject.allunitid_method()
    satavr = analyseobject.sataliteavarage_method()
    return render_template('pages/graphs.html', title="Graphs", header="Null", nav="Current Analyses",unitid = unitid,satavr=satavr)

@app.route('/cars.html',methods=["POST", "GET"])
@login_required
def cars():
    int_carinfo = 0
    analyseobject = analyse.Analyse()
    unitid = analyseobject.allunitid_method()
    status = analyseobject.carstatus_method()
####################################DIT MOET BETER, DYNAMISCH ##########################################
    if request.method == 'POST':
		if request.form['submit'] == '0':
			int_carinfo = unitid[0]
			gieflist = analyseobject.latestunitinfo_method(int_carinfo)
			trackinghistory_list = analyseobject.trackinghistory_method(int_carinfo)		
			return render_template('pages/carinfo.html', title="Unit Info", header="Unit Info", nav="Car Status",int_carinfo=int_carinfo,gieflist=gieflist,trackinghistory_list=trackinghistory_list)
			pass
		elif request.form['submit'] == '1':
			int_carinfo = unitid[1]
			gieflist = analyseobject.latestunitinfo_method(int_carinfo)
			trackinghistory_list = analyseobject.trackinghistory_method(int_carinfo)
			return render_template('pages/carinfo.html', title="Unit Info", header="Unit Info", nav="Car Status",int_carinfo=int_carinfo,gieflist=gieflist,trackinghistory_list=trackinghistory_list)
			pass
		if request.form['submit'] == '2':
			int_carinfo = unitid[2]
			gieflist = analyseobject.latestunitinfo_method(int_carinfo)
			return render_template('pages/carinfo.html', title="Unit Info", header="Unit Info", nav="Car Status",int_carinfo=int_carinfo,gieflist=gieflist,trackinghistory_list=trackinghistory_list)
			pass
		if request.form['submit'] == '3':
			int_carinfo = unitid[3]
			gieflist = analyseobject.latestunitinfo_method(int_carinfo)
			trackinghistory_list = analyseobject.trackinghistory_method(int_carinfo)

			return render_template('pages/carinfo.html', title="Unit Info", header="Unit Info", nav="Car Status",int_carinfo=int_carinfo,gieflist=gieflist,trackinghistory_list=trackinghistory_list)
			pass
		if request.form['submit'] == '4':
			int_carinfo = unitid[4]
			gieflist = analyseobject.latestunitinfo_method(int_carinfo)
			trackinghistory_list = analyseobject.trackinghistory_method(int_carinfo)

			return render_template('pages/carinfo.html', title="Unit Info", header="Unit Info", nav="Car Status",int_carinfo=int_carinfo,gieflist=gieflist,trackinghistory_list=trackinghistory_list)
			pass
		elif request.form['submit'] == '5':
			int_carinfo = unitid[5]
			gieflist = analyseobject.latestunitinfo_method(int_carinfo)
			trackinghistory_list = analyseobject.trackinghistory_method(int_carinfo)

			return render_template('pages/carinfo.html', title="Unit Info", header="Unit Info", nav="Car Status",int_carinfo=int_carinfo,gieflist=gieflist,trackinghistory_list=trackinghistory_list)
			pass
		if request.form['submit'] == '6':
			int_carinfo = unitid[6]
			gieflist = analyseobject.latestunitinfo_method(int_carinfo)
			trackinghistory_list = analyseobject.trackinghistory_method(int_carinfo)

			return render_template('pages/carinfo.html', title="Unit Info", header="Unit Info", nav="Car Status",int_carinfo=int_carinfo,gieflist=gieflist,trackinghistory_list=trackinghistory_list)
			pass
		if request.form['submit'] == '7':
			int_carinfo = unitid[7]
			gieflist = analyseobject.latestunitinfo_method(int_carinfo)
			trackinghistory_list = analyseobject.trackinghistory_method(int_carinfo)

			return render_template('pages/carinfo.html', title="Unit Info", header="Unit Info", nav="Car Status",int_carinfo=int_carinfo,gieflist=gieflist,trackinghistory_list=trackinghistory_list)
			pass
		if request.form['submit'] == '8':
			int_carinfo = unitid[8]
			gieflist = analyseobject.latestunitinfo_method(int_carinfo)
			trackinghistory_list = analyseobject.trackinghistory_method(int_carinfo)

			return render_template('pages/carinfo.html', title="Unit Info", header="Unit Info", nav="Car Status",int_carinfo=int_carinfo,gieflist=gieflist,trackinghistory_list=trackinghistory_list)
			pass			
		elif request.form['submit'] == '9':
			int_carinfo = unitid[9]
			gieflist = analyseobject.latestunitinfo_method(int_carinfo)
			trackinghistory_list = analyseobject.trackinghistory_method(int_carinfo)

			return render_template('pages/carinfo.html', title="Unit Info", header="Unit Info", nav="Car Status",int_carinfo=int_carinfo,gieflist=gieflist,trackinghistory_list=trackinghistory_list)
			pass
		if request.form['submit'] == '10':
			int_carinfo = unitid[10]
			gieflist = analyseobject.latestunitinfo_method(int_carinfo)
			trackinghistory_list = analyseobject.trackinghistory_method(int_carinfo)

			return render_template('pages/carinfo.html', title="Unit Info", header="Unit Info", nav="Car Status",int_carinfo=int_carinfo,gieflist=gieflist,trackinghistory_list=trackinghistory_list)
			pass
		if request.form['submit'] == '11':
			int_carinfo = unitid[11]
			gieflist = analyseobject.latestunitinfo_method(int_carinfo)
			trackinghistory_list = analyseobject.trackinghistory_method(int_carinfo)

			return render_template('pages/carinfo.html', title="Unit Info", header="Unit Info", nav="Car Status",int_carinfo=int_carinfo,gieflist=gieflist,trackinghistory_list=trackinghistory_list)
			pass												
    elif request.method == 'GET':
	    return render_template('pages/cars.html', title="Cars", header="Cars", nav="Car Status",unitid = unitid, status = status)
	    pass
#########################################################################################################################################################################################		
    return render_template('pages/cars.html', title="Cars", header="Cars", nav="Car Status",unitid = unitid, status = status, unitcount = unitcount, statuscount = statuscount)

@app.route('/carinfo.html')
@login_required
def carinfo():
    return render_template('pages/carinfo.html', title="Unit Info", header="Unit Info", nav="Car Status")

@app.route('/tables.html')
@login_required
def tables():
    return render_template('pages/tables.html', title="Tables", header="Tables", nav="Tables Page")

@app.route('/forms.html')
@login_required
def forms():
    return render_template('pages/forms.html', title="Forms", header="Forms", nav="Forms Page")

@app.route('/panels-wells.html')
@login_required
def panels_wells():
    return render_template('pages/panels-wells.html', title="Panels and Wells", header="Panels and Wells", nav="Panels and Wells Page")

@app.route('/buttons.html')
@login_required
def buttons():
    return render_template('pages/buttons.html', title="Buttons", header="Buttons", nav="Buttons Page")

@app.route('/notifications.html')
@login_required
def notifications():
    return render_template('pages/notifications.html', title="Notifications", header="Notifications", nav="Notifications Page")

@app.route('/typography.html')
@login_required
def typography():
    return render_template('pages/typography.html', title="Typography", header="Typography", nav="Typography Page")

@app.route('/icons.html')
@login_required
def icons():
    return render_template('pages/icons.html', title="Icons", header="Icons", nav="Icons Page")

@app.route('/grid.html')
def grid():
    return render_template('pages/grid.html', title="Grid", header="Grid", nav="Grid Page")