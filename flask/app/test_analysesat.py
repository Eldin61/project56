from unittest import TestCase
import analyse

__author__ = 'Orlando'


class ConnectedSatTest(TestCase):
	def test_connectedsatalites_method(self):

 		a = analyse.Analyse()
 		self.assertEqual(a.connectedsatalites_method()[0], 150)
 		#test purpose
