import json

import requests
# This is the interface on which truck driver interacts
# Correct password is 2913
# Please subscribe to this channel
url = "http://192.168.0.109:5000/pm"
while True:
    password = input("Enter password: ")
    data = {'pswd': password}
    r = requests.post(url=url, data=data)
    json_load = json.loads(r.text)

    if json_load["result"] == "ok":
        print("Congratulation!! you enterd the correct password.\nNow unload the truck dickhead")
    else:
        print("Enter the correct password dumbo.\nMaybe if you get to school instead of fucking around niggas then you know the correct password.")
