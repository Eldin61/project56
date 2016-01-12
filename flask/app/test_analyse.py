from unittest import TestCase
from analyse import Analyse

__author__ = 'Orlando'

class TestAnalyse(TestCase):

    def test_allunitid_method(self):
        o = Analyse()

        methodid = o.allunitid_method()
        actualid = [14100071, 14120026, 14120031, 14120029, 15030001, 14100042, 999, 14100064, 14100015, 15030000]

        self.assertEquals(methodid, actualid)