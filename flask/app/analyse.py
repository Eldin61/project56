import urllib, json

class Analyse:

    def getUnique(f):
        url = "http://145.24.222.121/index.php/events//?where[0][col]=value&where[0][op]==&where[0][val]=true"
        response = urllib.urlopen(url)
        jsonObj = json.loads(response.read())

        i = 0
        unitid = []
        while i < len(jsonObj["data"]):
            unitid.append(jsonObj["data"][i]["unitid"])
            i = i + 1
        unitid2 = list(set(unitid))
        j = 0
        data = []
        totalDict = {}
        while j < len(unitid2):
            url2 = "http://145.24.222.121/index.php/events//?where[0][col]=value&where[0][op]==&where[0][val]=true&where[1][col]=unitid&where[1][op]==&where[1][val]=" + str(unitid2[j])
            response2 = urllib.urlopen(url2)
            data2 = json.loads(response2.read())

            total = data2["total"]
            totalDict = {"id": str(unitid2[j]), "ignitions": str(total)}
            data.append(totalDict.copy())
            j = j + 1
        return data
