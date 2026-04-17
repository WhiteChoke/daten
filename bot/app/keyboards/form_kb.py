from aiogram.types import InlineKeyboardMarkup, InlineKeyboardButton
from aiogram.utils.keyboard import InlineKeyboardBuilder

async def create_match_kb(partner_id):
    kb = InlineKeyboardBuilder()
        
    kb.add(InlineKeyboardButton(text="❤️", callback_data=f"create_match/{partner_id}/true"))
    kb.add(InlineKeyboardButton(text="👎", callback_data=f"create_match/{partner_id}/false"))
    
    return kb.adjust(2).as_markup()

