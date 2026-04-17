import requests

service_url = "http://localhost:8082/api/v1/matchs"

def create_match(sender_id, partner_id, is_liked):
    
    request_body = {
        "senderId": sender_id,
        "partnerId": int(partner_id),
        "isLiked": is_liked
    }

    print(request_body)
    
    res = requests.post(url=service_url,
                json=request_body
                )
    print(res.text)