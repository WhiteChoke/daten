from aiogram.types import InlineKeyboardMarkup, InlineKeyboardButton

match = InlineKeyboardMarkup(inline_keyboard=[
    [
        InlineKeyboardButton(text="❤️", callback_data="like"),
        InlineKeyboardButton(text="👎", callback_data="dislike")
    ]
])