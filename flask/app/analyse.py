from json import loads
from urllib import urlopen
import converter
class Analyse:
	
	def allunitid_method(f):
	    unitidlist = list()
	    url = 'http://145.24.222.121/index.php/unitid'
	    jsonlist = loads(urlopen(url).read()) 
	    for w in jsonlist['data']:
		    unitidlist.append(w['unitid'])							
	    return unitidlist
	
	
	def sataliteavarage_method(d):
		sataliteinfo = list()
		url = 'http://145.24.222.121/index.php/unitid'
		jsonlist = loads(urlopen(url).read()) 
		for w in jsonlist['data']:
			url = 'http://145.24.222.121/index.php/' + str(w['unitid'])
			jsonlistnested = loads(urlopen(url).read()) 
			try :
				counter_int = 0
				for item in jsonlistnested['data']:
					counter_int += item['numsatalites']
				sataliteinfo.append(counter_int / len(jsonlistnested['data']))    
			except: 
				sataliteinfo.append(0)
				pass				
		return sataliteinfo

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
	def latestunitinfo_method(unitid):
		#converterObj = converter.Converter()

		unitstatus_list = list()
		coords = []
		url = 'http://145.24.222.121/index.php/'+str(unitid)+'status'
		jsonlist = loads(urlopen(url).read()) 
		for item in jsonlist['data']:
			try:
				unitstatus_list.append(item['unitid'])	
				unitstatus_list.append(item['port'])							
				unitstatus_list.append(item['value'])							
				unitstatus_list.append(item['dat'])							
				unitstatus_list.append(item['tim'])	
				unitstatus_list.append(item['rdx'])							
				unitstatus_list.append(item['rdy'])							
				unitstatus_list.append(item['speed'])
				unitstatus_list.append(item['course'])
				unitstatus_list.append(item['numsatalites'])	
				unitstatus_list.append(item['hdop'])					
				unitstatus_list.append(item['quality'])								
			except:
				unitstatus_list.append("Unknown")
				pass
		return unitstatus_list
	
	