from aiogram import Router, F
from aiogram.filters import CommandStart
from aiogram.types import Message
import app.bot_messages as messages

import app.keyboards.register_kb as register_kd

router = Router()

@router.message(CommandStart())
async def cmd_start(message: Message): 
    await message.answer(messages.welcome,
                         parse_mode="HTML", 
                         reply_markup=register_kd.start
                         )
    

    