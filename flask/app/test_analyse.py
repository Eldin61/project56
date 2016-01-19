from unittest import TestCase
from analyse import Analyse
from json import loads
from urllib import urlopen

__author__ = 'Orlando'

class TestAnalyse(TestCase):

    def test_allunitid_method(self):
        o = Analyse()

        methodid = o.allunitid_method()
        url = 'http://145.24.222.121/index.php/unitid'
        jsonlist = loads(urlopen(url).read())
        listlength = 0
        for w in jsonlist['data']:
            listlength +=1

        self.assertEquals(len(methodid), listlength)