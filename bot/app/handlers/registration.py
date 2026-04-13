from datetime import datetime

from aiogram import Router, F
from aiogram.types import CallbackQuery, Message
from aiogram.fsm.state import StatesGroup, State
from aiogram.fsm.context import FSMContext

import app.bot_messages as messages
import app.keyboards.register as kb
from app.api.profile import create_profile

router = Router()

class Registration(StatesGroup):
    name = State()
    birthday = State()
    maxAge = State()
    minAge = State()
    gender = State()
    searchGender = State()
    coordinates = State()
    bio = State()
    searchRadius = State()
    photoLink = State()

@router.callback_query(F.data == "registration")
async def reg_step_one(callback: CallbackQuery, state: FSMContext):
    await callback.answer()
    await state.set_state(Registration.name)
    await callback.message.edit_text(messages.enter_name,
                                     parse_mode="HTML")
    
@router.message(Registration.name)
async def reg_step_two(message: Message, state: FSMContext):
    await state.update_data(name=message.text)
    await state.set_state(Registration.birthday)
    await message.answer(messages.enter_birthday,
                         parse_mode="HTML")
    
@router.message(Registration.birthday)
async def enter_birthday(message: Message, state: FSMContext):
    
    try:
       datetime.strptime(message.text, "%d.%m.%Y")
    except ValueError:
        await message.answer(messages.incorrect_date_format,
                             parse_mode="HTML")
        return
    
    await state.update_data(birthday=message.text)
    await state.set_state(Registration.gender)
    
    await message.answer(messages.select_gender,
                        parse_mode="HTML",
                        reply_markup=kb.gender)
        
@router.message(Registration.gender)
async def enter_birthday(message: Message, state: FSMContext):
    
    if message.text not in ["Мужчина", "Женщина"]:
        await message.answer(messages.incorrect_date_format,
                             parse_mode="HTML")
        return
    
    await state.update_data(gender=message.text)
    await state.set_state(Registration.photoLink)
    await message.answer(messages.enter_photo_link,
                        parse_mode="HTML")
    
@router.message(Registration.photoLink)
async def enter_birthday(message: Message, state: FSMContext):
    await state.update_data(photoLink=message.photo[-1].file_id)
    await state.set_state(Registration.bio)
    await message.answer(messages.enter_bio,
                        parse_mode="HTML")
    
@router.message(Registration.bio)
async def enter_birthday(message: Message, state: FSMContext):
    if len(message.text) > 500:
        await message.answer(messages.long_message_error,
                            parse_mode="HTML")
        return
    
    await state.update_data(bio=message.text)
    await state.set_state(Registration.coordinates)
    await message.answer(messages.enter_location,
                        parse_mode="HTML",
                        reply_markup=kb.location_request)
        
@router.message(Registration.coordinates)
async def enter_birthday(message: Message, state: FSMContext):
    loc = {
        "latitude": message.location.latitude,
        "longitude": message.location.longitude
    }
    await state.update_data(coordinates=loc)
    await state.set_state(Registration.searchRadius)
    await message.answer(messages.enter_search_radius,
                        parse_mode="HTML")
    
@router.message(Registration.searchRadius)
async def enter_birthday(message: Message, state: FSMContext):
    await state.update_data(searchRadius=message.text)
    await state.set_state(Registration.minAge)
    await message.answer(messages.enter_min_age,
                        parse_mode="HTML")
    
@router.message(Registration.minAge)
async def enter_birthday(message: Message, state: FSMContext):
    await state.update_data(minAge=message.text)
    await state.set_state(Registration.maxAge)
    await message.answer(messages.enter_max_age,
                        parse_mode="HTML")
    
@router.message(Registration.maxAge)
async def enter_birthday(message: Message, state: FSMContext):
    await state.update_data(maxAge=message.text)
    await state.set_state(Registration.searchGender)
    await message.answer(messages.select_search_gender,
                        parse_mode="HTML",
                        reply_markup=kb.gender)
    
@router.message(Registration.searchGender)
async def enter_birthday(message: Message, state: FSMContext):
    await state.update_data(searchGender=message.text)
    data = await state.get_data()
    await create_profile(message.from_user.id, data)
    await message.answer_photo(photo=data["photoLink"])
    await state.clear()

    