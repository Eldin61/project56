from flask import Flask, render_template, redirect, url_for, request, session
from functools import wraps
from app import app
import analyse

def login_required(f):
    @wraps(f)
    def wrap(*args, **kwargs):
        if 'logged_in' in session:
            return f(*args, **kwargs)
        else:
            return redirect(url_for('login.html'))
    return wrap

@app.route('/logout')
@login_required
def logout():
    session.pop('logged_in', None)
    return redirect(url_for('login.html'))

@app.route('/login.html', methods=['POST', 'GET'])
def login():
    if request.method == 'POST':
        if request.form['email'] != 'admin' or request.form['password'] != 'admin':
            print 'invalid Credentials'
        else:
            session['logged_in'] = True
            return redirect(url_for('index'))
    return render_template('pages/login.html', title="Login")

@app.route('/')
@app.route('/index.html')
@login_required
def index():
    return render_template('pages/index.html', title="Home", header="Home")

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
    return render_template('pages/morris.html', title="Morris", header="Morris.js Charts", nav="Morris Page")

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
@login_required
def grid():
    return render_template('pages/grid.html', title="Grid", header="Grid", nav="Grid Page")
