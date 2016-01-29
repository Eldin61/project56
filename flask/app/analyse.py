from json import loads
from urllib import urlopen

class Analyse:
	
	
	def allunitid_method(f):
	    unitidlist = list()
	    url = 'http://145.24.222.121/index.php/unitid' #url link to rest api data
	    jsonlist = loads(urlopen(url).read()) #load all the data from the url
	    for w in jsonlist['data']: #loop through data
		    unitidlist.append(w['unitid']) #add the unitid's to list
	    return unitidlist
	
	def dataentry_method(b):
		sataliteinfo = list()
		url = 'http://145.24.222.121/index.php/connectioncount'
		jsonlist = loads(urlopen(url).read()) 
		for w in jsonlist['data']:
			sataliteinfo.append(w['count'])
		return sataliteinfo
			
	def connectioncount_method(h):
		sataliteinfo = list()
		url = 'http://145.24.222.121/index.php/contruecount'
		jsonlist = loads(urlopen(url).read()) 
		for w in jsonlist['data']:
			sataliteinfo.append(w['count'])
		return sataliteinfo
		
	def sataliteavarage_method(d):
		sataliteinfo = list()
		url = 'http://145.24.222.121/index.php/unitid'
		jsonlist = loads(urlopen(url).read()) 
		for w in jsonlist['data']:
			url = 'http://145.24.222.121/index.php/' + str(w['unitid']) #creates a url by looping through the list of unitid's and tostrings them
			jsonlistnested = loads(urlopen(url).read()) 
			try :
				counter_int = 0
				for item in jsonlistnested['data']:
					counter_int += item['numsatalites'] #add numsatalites count to counter
				sataliteinfo.append(counter_int / len(jsonlistnested['data'])) #calculates average by dividing total by number of times looped through data    
			except: 
				sataliteinfo.append(0)
				pass				
		print sataliteinfo
		return sataliteinfo
		
	def connectedsatalites_method(ids):
		connected = list()
		url = 'http://145.24.222.121/index.php/unitid'
		jsonlist = loads(urlopen(url).read()) 
		blackList = 0
		redList = 0
		yellowList = 0 
		greenList = 0
		for w in jsonlist['data']:
			url = 'http://145.24.222.121/index.php/' + str(w['unitid'])
			jsonlistnested = loads(urlopen(url).read()) 	
			try:
				#checks every entry and checks the connected num of sattalites and adds a count to the corresponding list
				for item in jsonlistnested['data']:
					satalites = int(item['numsatalites'])
					if satalites == 0:
						blackList +=1
					elif satalites < 5:
						redList +=1
					elif satalites < 8:
						yellowList +=1
					elif satalites > 8:
						greenList +=1
			except:	
				pass
		connected = [blackList, redList, yellowList, greenList]
		return connected	
	
	def allcarstatus_method(x): 
		statusinfo_list = list()
		META = []
		url = 'http://145.24.222.121/index.php/unitid'
		jsonlist = loads(urlopen(url).read()) 
		count = 0
		for item in jsonlist['data']:
			url = 'http://145.24.222.121/index.php/' + str(item['unitid']) + 'status'
			jsonlistnested = loads(urlopen(url).read())

			try:
				#collects all the available data from the url
				for item in jsonlistnested['data']:
					temp = []
					statusinfo_list = list()
					statusinfo_list.append(item['unitid'])
					statusinfo_list.append(item['tim'])
					statusinfo_list.append(item['dat'])
					statusinfo_list.append(item['port'])
					statusinfo_list.append(item['value'])
					statusinfo_list.append(item['rdx'])
					statusinfo_list.append(item['rdy'])
					statusinfo_list.append(item['speed'])
					statusinfo_list.append(item['course'])
					statusinfo_list.append(item['numsatalites'])
					statusinfo_list.append(item['hdop'])
					statusinfo_list.append(item['quality'])
					temp.extend([statusinfo_list,count])
					META.append(temp)
					count = count+1
			except:
				statusinfo_list.append("Unknown")
				pass
		return META

	def carstatus_method(e):
		statusinfo_list = list()
		url = 'http://145.24.222.121/index.php/unitid'
		jsonlist = loads(urlopen(url).read()) 
		for item in jsonlist['data']:
			url = 'http://145.24.222.121/index.php/' + str(item['unitid']) + 'status'
			jsonlistnested = loads(urlopen(url).read())
			try:
				for item in jsonlistnested['data']:
					statusinfo_list.append(item['value'])							
			except:
				statusinfo_list.append("Unknown")
				pass
		return statusinfo_list
		
	
	@staticmethod	
	def geolocationtracker(unitid):
		trackinghistory_list = list()
		trackinghistoryX = list() 
		trackinghistoryY = list()
		trackingsatalites = list()	
		trackingunitid = list()
		csv = open("app\csv.txt", "wb")
		url = 'http://145.24.222.121/index.php/'+str(unitid)	#opens url of corresponding unitid
		jsonlist = loads(urlopen(url).read())
		loopcount = 1;
		try:
			for item in jsonlist['data']:
				intx = float(item['rdx'])	#gets x coordinate and turns it into a float
				inty = float(item['rdy']) 
				intsatalite = int(item['numsatalites'])
				
				
				convertlist = [intx,inty]	#add both coordinates to a list
				newcoords = Analyse.fromRdToWgs(convertlist)	#calls the converter method to convert coordinates
				trackingsatalites.append(intsatalite)
				trackinghistoryX.append(newcoords[0])
				trackinghistoryY.append(newcoords[1])
				trackingunitid.append(unitid)

			

		except:
			pass
		for item in trackingsatalites:  # sorts the lists and prints them in csv in order
			trackinghistory_list.append(trackingsatalites[item])			
			trackinghistory_list.append(trackinghistoryX[item])
			trackinghistory_list.append(trackinghistoryY[item])
			trackinghistory_list.append(trackingunitid[item])
		print>>csv, trackinghistory_list
		print trackinghistory_list
		return trackinghistory_list
	
	
	@staticmethod
	def fromRdToWgs(  coords ): #converts Rijksdriehoek coordinates to GPS
		X0      = 155000
		Y0      = 463000
		phi0    = 52.15517440
		lam0    = 5.38720621
		
		Kp = [0,2,0,2,0,2,1,4,2,4,1]
		Kq = [1,0,2,1,3,2,0,0,3,1,1]
		Kpq = [3235.65389,-32.58297,-0.24750,-0.84978,-0.06550,-0.01709,-0.00738,0.00530,-0.00039,0.00033,-0.00012]

		Lp = [1,1,1,3,1,3,0,3,1,0,2,5]
		Lq = [0,1,2,0,3,1,1,2,4,2,0,0]
		Lpq = [5260.52916,105.94684,2.45656,-0.81885,0.05594,-0.05607,0.01199,-0.00256,0.00128,0.00022,-0.00022,0.00026]

		dX = 1E-5 * ( coords[0] -X0 )
		dY = 1E-5 * ( coords[1] -Y0 )
					
		phi = 0
		lam = 0

		for k in range(len(Kpq)):
			phi = phi + ( Kpq[k] * dX**Kp[k] * dY**Kq[k] )
		phi = phi0 + phi / 3600

		for l in range(len(Lpq)):
			lam = lam + ( Lpq[l] * dX**Lp[l] * dY**Lq[l] )
		lam = lam0 + lam / 3600

		return [phi,lam]
		
	@staticmethod	
	def latestunitinfo_method(unitid):
		
		unitstatus_list = list()
		url = 'http://145.24.222.121/index.php/'+str(unitid)+'status'
		jsonlist = loads(urlopen(url).read()) 
		for item in jsonlist['data']:
			try:
				unitstatus_list.append(item['unitid'])	
				unitstatus_list.append(item['port'])							
				unitstatus_list.append(item['value'])							
				unitstatus_list.append(item['dat'])
				unitstatus_list.append(item['tim'])	
				intx = float(item['rdx'])
				inty = float(item['rdy'])
				

				convertlist = [intx,inty]
				newcoords = Analyse.fromRdToWgs(convertlist)
				unitstatus_list.append(item['rdx'])	
				unitstatus_list.append(item['rdy'])							
				unitstatus_list.append(item['speed'])
				unitstatus_list.append(item['course'])
				unitstatus_list.append(item['numsatalites'])	
				unitstatus_list.append(item['hdop'])					
				unitstatus_list.append(item['quality'])	
				unitstatus_list.append(newcoords[0])	
				unitstatus_list.append(newcoords[1])
				
			except:
				unitstatus_list.append("Unknown")
				pass
		return unitstatus_list
		
	@staticmethod		
	def trackinghistory_method(unitid):
		trackinghistory_dict = {}
		url = 'http://145.24.222.121/index.php/'+str(unitid)
		jsonlist = loads(urlopen(url).read())
		loopcount = 1;
		try:
			for item in jsonlist['data']:
				intx = float(item['rdx'])
				inty = float(item['rdy']) 
				intsatalite = int(item['numsatalites'])
				
			
				convertlist = [intx,inty]
				newcoords = Analyse.fromRdToWgs(convertlist)
				if intsatalite == 0:
					url = 'http://i849.photobucket.com/albums/ab58/reneheijnen/mark4/blackarea'+str(loopcount)+'.png'
					loopcount +=1
					trackinghistory_dict.update({url:[newcoords]})	#adds unique url as key with value = converted coordinates to a dictionary		
				elif intsatalite < 5:
					url = 'http://i849.photobucket.com/albums/ab58/reneheijnen/mark4/redarea'+str(loopcount)+'.png'
					loopcount +=1
					trackinghistory_dict.update({url:[newcoords]})					
				elif intsatalite < 8:
					url = 'http://i849.photobucket.com/albums/ab58/reneheijnen/mark4/yellowarea'+str(loopcount)+'.png'
					loopcount +=1
					trackinghistory_dict.update({url:[newcoords]})		
				elif intsatalite > 8:
					url = 'http://i849.photobucket.com/albums/ab58/reneheijnen/mark4/greenarea'+str(loopcount)+'.png'
					loopcount +=1
					trackinghistory_dict.update({url:[newcoords]})		
		except:
			pass
		return trackinghistory_dict