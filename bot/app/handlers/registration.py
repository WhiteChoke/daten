from datetime import datetime

from aiogram import Router, F
from aiogram.types import CallbackQuery, Message
from aiogram.fsm.state import StatesGroup, State
from aiogram.fsm.context import FSMContext

import app.bot_messages as messages
import app.keyboards.register_kb as kb
from app.keyboards import main_kb
from app.api.profile_service import create_profile

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
async def registration_start(callback: CallbackQuery, state: FSMContext):
    await callback.answer()
    await state.set_state(Registration.name)
    await callback.message.edit_text(messages.enter_name,
                                     parse_mode="HTML")
    
@router.message(Registration.name)
async def enter_name(message: Message, state: FSMContext):
    await state.update_data(name=message.text)
    await state.set_state(Registration.birthday)
    await message.answer(messages.enter_birthday,
                         parse_mode="HTML")
    
@router.message(Registration.birthday)
async def enter_birthday(message: Message, state: FSMContext):
    
    try:
        formated = datetime.strptime(message.text, "%d.%m.%Y")
        date = formated.strftime("%Y-%m-%d")
        
        await state.update_data(birthday=date)
        
    except ValueError:
        await message.answer(messages.incorrect_date_format,
                             parse_mode="HTML")
        return
    
    await state.set_state(Registration.gender)
    await message.answer(messages.select_gender,
                        parse_mode="HTML",
                        reply_markup=kb.gender)
        
@router.message(Registration.gender)
async def enter_gender(message: Message, state: FSMContext):
    
    if message.text not in ["Мужчина", "Женщина"]:
        await message.answer(messages.incorrect_gender,
                             parse_mode="HTML")
        return
    
    await state.update_data(gender=message.text)
    await state.set_state(Registration.photoLink)
    await message.answer(messages.enter_photo_link,
                        parse_mode="HTML")
    
@router.message(Registration.photoLink)
async def enter_photo_link(message: Message, state: FSMContext):
    await state.update_data(photoLink=message.photo[-1].file_id)
    await state.set_state(Registration.bio)
    await message.answer(messages.enter_bio,
                        parse_mode="HTML")
    
@router.message(Registration.bio)
async def enter_bio(message: Message, state: FSMContext):
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
async def enter_coordinates(message: Message, state: FSMContext):
    loc = {
        "latitude": message.location.latitude,
        "longitude": message.location.longitude
    }
    await state.update_data(coordinates=loc)
    await state.set_state(Registration.searchRadius)
    await message.answer(messages.enter_search_radius,
                        parse_mode="HTML")
    
@router.message(Registration.searchRadius)
async def enter_search_radius(message: Message, state: FSMContext):
    await state.update_data(searchRadius=message.text)
    await state.set_state(Registration.minAge)
    await message.answer(messages.enter_min_age,
                        parse_mode="HTML")
    
@router.message(Registration.minAge)
async def enter_min_age(message: Message, state: FSMContext):
    
    try:
        min_age = int(message.text)
        await state.update_data(minAge=min_age)
    except Exception:
        await message.answer(messages.number_error)
        return
        
    await state.set_state(Registration.maxAge)
    await message.answer(messages.enter_max_age,
                        parse_mode="HTML")
    
@router.message(Registration.maxAge)
async def enter_max_age(message: Message, state: FSMContext):
    
    try:
        max_age = int(message.text)
        await state.update_data(maxAge=max_age)
    except Exception:
        await message.answer(messages.number_error)
        return
    
    await state.set_state(Registration.searchGender)
    await message.answer(messages.select_search_gender,
                        parse_mode="HTML",
                        reply_markup=kb.gender)
    
@router.message(Registration.searchGender)
async def enter_search_gender(message: Message, state: FSMContext):
    
    if message.text not in ["Мужчина", "Женщина"]:
        await message.answer(messages.incorrect_gender,
                             parse_mode="HTML")
        return
    
    await state.update_data(searchGender=message.text)
    data = await state.get_data()
    await create_profile(message.from_user.id, data)
    await message.answer_photo(
        photo=await state.get_data["photoLink"],
        caption=f"{await state.get_data["name"]}, {await state.get_data["age"]}\n {await state.get_data["bio"]}",
        reply_markup=main_kb.main)
    
    await state.clear()

    