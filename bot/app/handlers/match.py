from aiogram import Router, F
from aiogram.types import Message, CallbackQuery

from app.api import deck_service
from app.keyboards import form_kb
from app.api import match_service

from datetime import datetime
from dateutil.relativedelta import relativedelta

router = Router()

@router.message(F.text == "Начать поиск")
async def send_match(message: Message):

    
    response = deck_service.get_form(message.from_user.id)
    
    birthday = datetime.fromisoformat(response["birthday"])
    age = relativedelta(datetime.today(), birthday).years
        
    await message.answer_photo(
        photo=response["photoLink"],
        caption=f"{response["name"]}, {age}\n {response["bio"]}",
        reply_markup=await form_kb.create_match_kb(response["telegramId"])
    )

@router.callback_query(lambda f: "create_match" in f.data)
async def match(callback: CallbackQuery):
    
    partner_id, answer = callback.data.split("/")[1:]
    print(callback.data)
    
    match_service.create_match(callback.from_user.id, partner_id, answer)
    await callback.answer()
    
    response = deck_service.get_form(callback.from_user.id)
    
    birthday = datetime.fromisoformat(response["birthday"])
    age = relativedelta(datetime.today(), birthday).years
        
    await callback.message.answer_photo(
        photo=response["photoLink"],
        caption=f"{response["name"]}, {age}\n {response["bio"]}",
        reply_markup=await form_kb.create_match_kb(response["telegramId"])
    )
    