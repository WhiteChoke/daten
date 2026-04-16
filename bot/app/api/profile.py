import requests

genders = {
    "Мужчина": "MALE",
    "Женщина": "FEMALE"
}

servise_url = "http://localhost:8080/api/v1/profiles"

def create_profile(tg_id,request):
    request_body = {
        "telegramId": tg_id,
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
    
    requests.post(url=servise_url,   
                json=request_body
                )
