from aiogram import Router, F
from aiogram.filters import CommandStart
from aiogram.types import Message

import app.bot_messages as messages
import app.keyboards.register_kb as register_kd
import app.keyboards.profile_kb as profile_kb
import app.keyboards.main_kb as main_kb
from app.api import profile_service

router = Router()

@router.message(CommandStart())
async def cmd_start(message: Message): 
    
    response = await profile_service.get_profile(message.from_user.id)
    
    match response.status:
        case 200:
            profile = await response.json()
            if not(profile["isActive"]):
                await message.answer(messages.profile_disactivated,
                                     parse_mode="HTML",
                                     reply_markup=profile_kb.activate)
            else:
                await message.answer(text="Выберите опцию",
                                     reply_markup=main_kb.main)
        case 404:
            await message.answer(messages.welcome,             
                                parse_mode="HTML",  
                                reply_markup=register_kd.start)

@router.message(F.text == "Активировать")
async def activate(message: Message):
    await profile_service.activate_profile(message.from_user.id)
    
    await message.answer(messages.welcome_back,
                         parse_mode="HTML",
                         reply_markup=profile_kb.activate)
    