import requests

service_url = "http://localhost:8083/api/v1/decks"

def get_form(tgId):
    
    response = requests.get(url=f"{service_url}/{tgId}")
    
    print(response.text)
    
get_form(843)