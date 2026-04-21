import aiohttp

service_url = "http://localhost:8083/api/v1/decks"

async def get_form(tg_id):
    async with aiohttp.ClientSession() as session:
        response = await session.get(url=f"{service_url}/{tg_id}")
        return await response.json()
