from aiogram.types import KeyboardButton, ReplyKeyboardMarkup

activate = ReplyKeyboardMarkup(keyboard=[
    [KeyboardButton(text="Активировать")]
], resize_keyboard=True)

profile_action = ReplyKeyboardMarkup(keyboard=[
    [KeyboardButton(text="Изменить профиль📝")],
    [KeyboardButton(text="Деативировать☠️")]
], resize_keyboard=True)

edit_profile = ReplyKeyboardMarkup(keyboard=[
    [KeyboardButton(text="Изменить фото"), KeyboardButton(text="Изменить био")],
    [KeyboardButton(text="Заполнить анкету занова")]
], resize_keyboard=True)