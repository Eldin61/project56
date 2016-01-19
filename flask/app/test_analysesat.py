from unittest import TestCase
import analyse
from json import loads
from urllib import urlopen

__author__ = 'Orlando'


class ConnectedSatTest(TestCase):
    def test_connectedsatalites_method(self):

        a = analyse.Analyse()
        url = 'http://145.24.222.121/index.php/unitid'
        jsonlist = loads(urlopen(url).read())
        testList = 0
        for w in jsonlist['data']:
            url = 'http://145.24.222.121/index.php/' + str(w['unitid'])
            jsonlistnested = loads(urlopen(url).read())
            try:
                for item in jsonlistnested['data']:
                    satalites = int(item['numsatalites'])
                    if satalites == 0:
                        testList +=1
                    else:
                        pass
            except:
                pass

        self.assertEqual(a.connectedsatalites_method()[0], testList)

