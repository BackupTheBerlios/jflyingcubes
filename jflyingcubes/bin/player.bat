@echo off

SET CP=../icons

echo %1%

java -cp %CP% -jar ../lib/jflyingcubes-player.jar %1%