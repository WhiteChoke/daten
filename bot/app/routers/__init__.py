__all__ = ("router")

from aiogram import Router

from app.handlers.commands import router as commands_router
from app.handlers.registration import router as registration_router
from app.handlers.match import router as match_router

router = Router()

router.include_routers(
    commands_router,
    registration_router,
    match_router   
)