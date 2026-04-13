import requests

async def create_profile(tgId,request):
    body = {
        "telegramId": tgId,
        "name": request["name"],
        "birthday": request["birthday"],
        "maxAge": request["maxAge"],
        "minAge": request["minAge"],
        "gender": request["gender"],
        "searchGender": request["searchGender"],
        "latitude": request["coordinates"]["latitude"],
        "longitude": request["coordinates"]["longitude"],
        "bio": request["bio"],
        "searchRadius": request["searchRadius"],
        "photoLink": request["photoLink"],
    }
    