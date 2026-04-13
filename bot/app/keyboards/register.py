from aiogram.types import InlineKeyboardMarkup, InlineKeyboardButton, ReplyKeyboardMarkup, KeyboardButton

start = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text="Заполнить анкету", callback_data="registration")]
], )

gender = ReplyKeyboardMarkup(keyboard=[
    [
        KeyboardButton(text="Мужчина"),
        KeyboardButton(text="Женщина")
    ]
], resize_keyboard=True, one_time_keyboard=True)

location_request = ReplyKeyboardMarkup(keyboard=[
    [KeyboardButton(text="location", request_location=True)],
], resize_keyboard=True, one_time_keyboard=True)