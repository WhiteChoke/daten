import requests


service_url = "http://localhost:8083/api/v1/decks"

def get_form(tgId):
        
    response = requests.get(url=f"{service_url}/{tgId}")

    if response.status_code != 200:
        print(response.text)
        return


    return response.json()
