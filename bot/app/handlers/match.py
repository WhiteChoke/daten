from aiogram import Router, F
from aiogram.types import Message

router = Router()

@router.message(F.text == "Начать поиск")
async def start_match(message: Message):
    await message.answer()