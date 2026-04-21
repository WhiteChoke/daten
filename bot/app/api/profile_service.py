import aiohttp

genders = {
    "Мужчина": "MALE",
    "Женщина": "FEMALE"
}

service_url = "http://localhost:8080/api/v1/profiles"

async def create_profile(tg_id: int, request: dict) -> None:
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
    
    async with aiohttp.ClientSession() as session:
        await session.post(url=service_url, json=request_body)

async def get_profile(tg_id: int) -> aiohttp.ClientResponse:
    async with aiohttp.ClientSession() as session:
        response = await session.get(f"{service_url}/tg/{tg_id}")
        await response.json()
        
        return response
    
async def activate_profile(tg_id: int) -> int:
    async with aiohttp.ClientSession() as session:
        response = await session.patch(f"{service_url}/activate/{tg_id}")
        
        return response.status
    
async def deactivate_profile(tg_id: int) -> int:
    async with aiohttp.ClientSession() as session:
        response = await session.delete(f"{service_url}/{tg_id}")
        
        return response.status
    

