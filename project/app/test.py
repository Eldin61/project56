url = "http://localhost/index.php/events//?where[0][col]=value&where[0][op]==&where[0][val]=true&limit=50"
response = urllib.urlopen(url)
data = json.loads(response.read())
i = 0

vehicles = []
mydict = {}
orderunit = []

while i < len(data["data"]):
    dataArray = data["data"][i]
    unitid = dataArray["unitid"]
    orderunit.append(unitid)


    url2 = "http://localhost/index.php/events//?where[0][col]=value&where[0][op]==&where[0][val]=true&where[1][col]=unitid&where[1][op]==&where[1][val]=" + str(unitid)
    response2 = urllib.urlopen(url2)
    data2 = json.loads(response2.read())

    total = data2["total"]

    mydict = {"id": str(unitid), "ignitions": str(total)}
    vehicles.append(mydict.copy())

    i = i + 1
