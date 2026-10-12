@echo off
set "PATH=%PATH%;C:\Users\dasan\AppData\Local\GitHubDesktop\app-3.6.7\resources\app\git\cmd"
echo =========================================================
echo  Pushing Backend to https://github.com/dheeraj669/vehicle-service-backend
echo =========================================================
echo.
git push -u origin main
echo.
if %ERRORLEVEL% EQU 0 (
    echo =========================================================
    echo  SUCCESS! All backend and database files are now on GitHub!
    echo =========================================================
) else (
    echo [NOTE] If you were prompted for credentials, use a GitHub Personal Access Token (PAT) as the password:
    echo https://github.com/settings/tokens
)
pause
