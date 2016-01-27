#Imports
from flask import Flask, render_template,send_file, redirect, url_for, request, session, g # Flask plugin
from functools import wraps
from app import app
import analyse
import psycopg2 # SQLplugin
from flask_googlemaps import GoogleMaps # Google maps plugin
from flask_googlemaps import Map # Google maps plugin
from flask import request # Flask plugin

# Method to force check login when you request a page
def login_required(f):
    @wraps(f)
    def wrap(*args, **kwargs):
        if 'logged_in' in session:
            return f(*args, **kwargs)
        else:
            return redirect(url_for('login'))
    return wrap

# Method to verify account details for the login system
def connect_db(): 
    return psycopg2.connect(database='users', user='userdb', password='root', host='127.0.0.1', port='5432')

@app.route('/logout')# App route makes this method run when www.***.**./logout is requested.
def logout():
    session.pop('logged_in', None)
    return redirect(url_for('login'))

# Login forced when a user requests a page
@app.route('/login', methods=['POST', 'GET']) # App route makes this method run when www.***.**./login is requested.
def login():
    if request.method == 'POST': # if login method is 
    	db = connect_db() # connect db
        cur = db.cursor() 
		# check credentials listed versus whats in the database 
        username = request.form['email'] 
        cur.execute("SELECT password from users where username=\'" + username + "\'")  # Get user data from database based on entry of username
        rows = cur.fetchall()  # Fetch all rows from the database 
        for row in rows:
        	password = row[0]
        db.commit()  # Done 
        db.close() # Close connection 
        if request.form['password'] != password: # if those credentials are not valid
            print 'invalid Credentials'
        else: # else they are
            session['logged_in'] = True
            return redirect(url_for('index')) # continue to index.html
    return render_template('pages/login.html', title="Login") # If its not good, return to loginpage

@app.route('/')
@app.route('/index.html') # App route makes this method run when www.***.**./index is requested.

def index():
    counterlist = list() # list hack to display unit ids
    counter = 0 
    analyseobject = analyse.Analyse() # Creating the analyse object 
    unitid = analyseobject.allunitid_method() # get unitids and save them
    status = analyseobject.carstatus_method() # get car status and save it 
    connected = analyseobject.connectedsatalites_method() # find connected unit ids 
    connectionscount = analyseobject.dataentry_method()  # count them
    truecount = analyseobject.connectioncount_method()  # how many of those are live
	
    for item in status: # for every item in car status list
		counterlist.append(counter) # save the number of cars
		counter = counter +1
		print counter
    unitidinformation = analyseobject.allcarstatus_method() # get all unit id information
	# Open index.html with the saved data from above so we can display it in index.html
    return render_template('pages/index.html', title="Home", header="Home", unitid = unitid,status = status, connected = connected, unitidinformation = unitidinformation,counterlist=counterlist,connectionscount=connectionscount,truecount=truecount)

@app.route('/blank.html') # App route makes.........
 
def blank():
    return render_template('pages/blank.html', title="Blank", header="Blank", nav="Blank Page")

@app.route('/flot.html') # App route makes.........
def flot():
    return render_template('pages/flot.html', title="Flot", header="Flot Charts", nav="Flot Page")
@app.route('/meta.html') # App route makes.........
 
def meta():
    
    return render_template('pages/meta.html', title="Meta Data", header="Meta", nav="Meta Page")
	
@app.route('/metaspecials.html') # App route makes.........
def metaspecial():
    return render_template('pages/metaspecials.html', title="Meta Data Specials", header="Meta Specials", nav="Meta Specials Page")

@app.route('/morris.html') # App route makes.........
 
def morris():
    analyseobject = analyse.Analyse() # Creating the analyse object 
    unitid = analyseobject.allunitid_method() # get unitid information
    satavr = analyseobject.sataliteavarage_method() # get satalite information
    return render_template('pages/morris.html', title="Morris", header="Morris.js Charts", nav="Morris Page",unitid = unitid,satavr=satavr)

@app.route('/graphs.html') # App route makes.........
 
def graphs():
    analyseobject = analyse.Analyse() # Creating the analyse object 
    unitid = analyseobject.allunitid_method()
    satavr = analyseobject.sataliteavarage_method()
    return render_template('pages/graphs.html', title="Graphs", header="Null", nav="Current Analyses",unitid = unitid,satavr=satavr)
	
@app.route('/csv.txt', methods=['GET', 'POST'])
def download():
    return send_file('csv.txt', as_attachment=True)

@app.route('/cars.html',methods=["POST", "GET"]) # App route makes.........
 
def cars():
    int_carinfo = 0
    analyseobject = analyse.Analyse() # Creating the analyse object 
    unitid = analyseobject.allunitid_method() # get unitid information
    status = analyseobject.carstatus_method() # get unitid status information
    int_listlength = len(unitid)

    if request.method == 'POST':  # if you pres the status button on that page 
		idnr = int(request.form['submit'])  # request the form based on button ID 
		int_carinfo = unitid[idnr]  # get that car number  
		gieflist = analyseobject.latestunitinfo_method(int_carinfo)  # return the data based on that car number
		trackinghistory_list = analyseobject.trackinghistory_method(int_carinfo)	  # ge the tracking history of that car
		
		return render_template('pages/carinfo.html', title="Unit Info", header="Unit Info", nav="Car Status",int_carinfo=int_carinfo,gieflist=gieflist,trackinghistory_list=trackinghistory_list)  # send all the data to the page 
			
    elif request.method == 'GET':
	    return render_template('pages/cars.html', title="Cars", header="Cars", nav="Car Status",unitid = unitid, status = status)
	    pass

@app.route('/carinfo.html') # App route makes.........
def carinfo():
    return render_template('pages/carinfo.html', title="Unit Info", header="Unit Info", nav="Car Status")
	
@app.route('/geolocationdata.html')
def geolocationdata():
    analyseobject = analyse.Analyse()  # Make anaylyse object
    unitid = analyseobject.allunitid_method()  # Get unit ids
    connectionlist = list()  # get connections
    for item in unitid:
		connectionlist.append(analyseobject.geolocationtracker(item))  # for every unit in unit id, get the data  and send it to the page 
    return render_template('pages/geolocationdata.html', title="geolocationdata", header="geolocationdata", nav="geolocationdata",connectionlist=connectionlist,unitid=unitid)

@app.route('/tables.html')
 
def tables():
    return render_template('pages/tables.html', title="Tables", header="Tables", nav="Tables Page")

@app.route('/forms.html')
 
def forms():
    return render_template('pages/forms.html', title="Forms", header="Forms", nav="Forms Page")

@app.route('/panels-wells.html')
 
def panels_wells():
    return render_template('pages/panels-wells.html', title="Panels and Wells", header="Panels and Wells", nav="Panels and Wells Page")

@app.route('/buttons.html')
 
def buttons():
    return render_template('pages/buttons.html', title="Buttons", header="Buttons", nav="Buttons Page")

@app.route('/notifications.html')
 
def notifications():
    return render_template('pages/notifications.html', title="Notifications", header="Notifications", nav="Notifications Page")

@app.route('/typography.html')
 
def typography():
    return render_template('pages/typography.html', title="Typography", header="Typography", nav="Typography Page")

@app.route('/icons.html')
 
def icons():
    return render_template('pages/icons.html', title="Icons", header="Icons", nav="Icons Page")

@app.route('/grid.html')
def grid():
    return render_template('pages/grid.html', title="Grid", header="Grid", nav="Grid Page")