from json import loads
from urllib import urlopen

unitidlist = list()

# w = for every item
# TEST SCRIPT FOR PARSING DATA FROM REST - NOT IN USE !!
# TEST SCRIPT FOR PARSING DATA FROM REST - NOT IN USE !!
# TEST SCRIPT FOR PARSING DATA FROM REST - NOT IN USE !!
# TEST SCRIPT FOR PARSING DATA FROM REST - NOT IN USE !!
# TEST SCRIPT FOR PARSING DATA FROM REST - NOT IN USE !!
# TEST SCRIPT FOR PARSING DATA FROM REST - NOT IN USE !!
# Test methods to get all data from 1 unit id
def allunitid_method():
	url = 'http://145.24.222.121/index.php/unitid'
	response = loads(urlopen(url).read()) 
	for w in response['data']:
		unitidlist.append(w['unitid'])
		#loadunitmonitor_method(w['unitid']) # laad de monitor info van bepaald unitid
		#loadunitinfo_method(w['unitid'])	# laad de event/position info van bepaald unitid
											# TODO connection info van bepaald unitid (data ziet er niet boeiend uit tbh)
def loadunitinfo_method(int):
	url = 'http://145.24.222.121/index.php/' + str(int)
	response = loads(urlopen(url).read()) 
	print '-------------' + str(int) + '-------------'
	try :
		for w in response['data']:
			print ' date time :' + str(w['datetime'])
			#print ' unitid :' + w['unitid']
			print ' port :' + str(w['port'])
			print ' value :' + str(w['value'])
			print ' rdx :' + str(w['rdx'])
			print ' rdy :' + str(w['rdy'])
			print ' speed :' + str(w['speed'])
			print ' course :' + str(w['course'])
			print ' numsatalites :' + str(w['numsatalites'])
			print ' hdop :' + str(w['hdop'])
			print ' quality :' + str(w['quality'])
			print '------------------------------'
	except: 
		pass
		print '14120032 does not exist in positions..'
	print '14120032 does not exist in positions! skipped entire untid'

def loadunitmonitor_method(int):
## try except omdat er te veel data doorheen gaat :(
	url = 'http://localhost/index.php/monitoring//?where[0][col]=unitid&where[0][op]==&where[0][val]='  + str(int) + '&limit=50000'
	response = loads(urlopen(url).read())
	print '-------------' + str(int) + '-------------'
	try :	
		for w in response['data']:
			print ' begintime :' + str(w['begintime'])
			#print ' endtime :' + w['endtime']
			print ' type :' + str(w['type'])
			print ' min :' + str(w['min'])
			print ' max :' + str(w['max'])
			print ' sum :' + str(w['sum'])
	except: 
		pass
		print '14120032 does not exist in positions..'

#### post entire rest report
def events_method():
	url = 'http://145.24.222.121/index.php/events'
	response = loads(urlopen(url).read()) 
	print 'limted on :'  ,response['limit']
	print 'count :'  ,response['count']
	print 'total results :'  ,response['total']

	for w in response['data']:
		print w['datetime'], w['unitid'], w['port'], w['value']
		

		
def connections_method():
	url = 'http://145.24.222.121/index.php/connections'
	response = loads(urlopen(url).read())
	print 'limted on :'  ,response['limit']
	print 'count :'  ,response['count']
	print 'total results :'  ,response['total']
	
	for w in response['data']:
		print w['datetime'], w['unitid'], w['port'], w['value']
		
		
		
def monitoring_method():
	url = 'http://145.24.222.121/index.php/monitoring?limit=50'#  50 voor testing
	response = loads(urlopen(url).read())

	for w in response['data']:
		print w['unitid'], w['begintime'], w['endtime'], w['type'], w['min'], w['max'], w['sum']
		
		
def positions_method():
	url = 'http://145.24.222.121/index.php/positions?limit=50' #  50 voor testing
	response = loads(urlopen(url).read())

	for w in response['data']:
		print w['datetime'], w['unitid'], w['rdx'], w['rdy'], w['speed'], w['course'], w['numsatalites'], w['hdop'], w['quality']
		
	
	
allunitid_method()
