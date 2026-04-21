from aiogram.types import KeyboardButton, ReplyKeyboardMarkup

main = ReplyKeyboardMarkup(keyboard=[
    [KeyboardButton(text="Профиль🖼️")],
    [KeyboardButton(text="Начать поиск🔭")]
], resize_keyboard=True)