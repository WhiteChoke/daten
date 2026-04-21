import aiohttp

service_url = "http://localhost:8082/api/v1/matchs"

async def create_match(sender_id, partner_id, is_liked):
    
    request_body = {
        "senderId": sender_id,
        "partnerId": int(partner_id),
        "isLiked": is_liked
    }

    async with aiohttp.ClientSession() as session:
         await session.post(url=service_url, json=request_body)
