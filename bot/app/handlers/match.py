from aiogram import Router, F
from aiogram.types import Message

from app.api import deck

from datetime import datetime
from dateutil.relativedelta import relativedelta

router = Router()

@router.message(F.text == "Начать поиск")
async def start_match(message: Message):

    
    response = deck.get_form(message.from_user.id)
    
    birthday = datetime.strptime(response["birthday"], "%Y-%m-%d")
    age = relativedelta(datetime.today(), birthday).years
    
    print(response)
    
    # await message.answer_photo(
    #     photo=response["photoLink"],
    #     caption=f"{response["name"]}, {age}\n\n {response["bio"]}"
    # )
    
    await message.answer(f"{response["name"]}, {age}\n\n {response["bio"]}")