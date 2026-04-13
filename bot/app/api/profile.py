import requests

genders = {
    "Мужчина": "MALE",
    "Женщина": "FEMALE"
}

def create_profile(tgId,request):
    request_body = {
        "telegramId": tgId,
        "name": request["name"],
        "birthday": request["birthday"],
        "maxAge": request["maxAge"],
        "minAge": request["minAge"],
        "gender": genders[request["gender"]],
        "searchGender": genders[request["searchGender"]],
        "latitude": request["coordinates"]["latitude"],
        "longitude": request["coordinates"]["longitude"],
        "bio": request["bio"],
        "searchRadius": request["searchRadius"],
        "photoLink": request["photoLink"],
    }
    
    response = requests.post(url="http://localhost:8080/api/v1/profiles", 
                                    json=request_body
                                )
    print(response.text)