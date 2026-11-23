@echo off
title Push Flying shoes! to GitHub
cd /d "%~dp0"
echo ===================================================
echo   Flying shoes! - GitHub Push Tool
echo ===================================================
echo.

git branch -M main
git status
echo.
git add .
git commit -m "Update Flying shoes! source code" 2>nul
echo Pushing to GitHub...
git push -u origin main

if %errorlevel% neq 0 (
    echo.
    echo [NOTE] If remote is not set up, run:
    echo   git remote add origin https://github.com/ltxq/[REPO_NAME].git
    echo   git push -u origin main
) else (
    echo.
    echo SUCCESS: Uploaded to GitHub successfully!
)
echo.
pause
