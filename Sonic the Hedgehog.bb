; ------------------------------------------------------------------------
; BlitzSonic Engine -- Classic Sonic the Hedgehog engine for Blitz 3D
; version 0.1, February 7th, 2008
;
; Copyright (C) 2008 - BlitzSonic Team.
; ------------------------------------------------------------------------
;
; This software is provided 'as-is', without any express or implied
; warranty.  In no event will the authors be held liable for any damages
; arising from the use of this software.
; 
; Permission is granted to anyone to use this software for any purpose
; (except for commercial applications) and to alter it and redistribute
; it freely subject to the following restrictions:
;
; 1. The origin of this software must not be misrepresented; you must not
;    claim that you wrote the original software. If you use this software
;    in a product, an acknowledgment in the product itself as a splash
;    screen is required.
; 2. Altered source versions must be plainly marked as such, and must not be
;    misrepresented as being the original software.
; 3. This notice may not be removed or altered from any source distribution.
;
; All characters and materials in relation to the Sonic the Hedgehog game series
; are copyrights/trademarks of SEGA of Japan (SEGA Co., LTD). This product
; has been developed without permission of SEGA, therefore it's prohibited
; to sell/make profit of it.
;
; The BlitzSonic Team:
; - Héctor "Damizean" (elgigantedeyeso at gmail dot com)
; - Mark "Coré" (mabc_bh at yahoo dot com dot br)
; - Streak Thunderstorm
; - Mista ED
;

;\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/;
;	Project Title : Sonic the Hedgehog                                                                         ;
; ============================================================================================================ ;
;	Author :                                                                                                   ;
;	Email :                                                                                                    ;
;	Version: 0.1                                                                                               ;
;	Date: --/--/2008                                                                                           ;
;                                                                                                              ;
;\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/;
;                                                                                                              ;
;   Changelog:  -(Damizean)------------------------------->                                                    ;
;               17/01/2008 - Code reorganization.                                                              ;
;                                                                                                              ;
;==============================================================================================================;
;                                                                                                              ;
;   TODO:                                                                                                      ;
;                                                                                                              ;
;==============================================================================================================;

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
;   STARTUP
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; --- Include critical libraries before starting ----

Include "_SourceCode\Systems\FastExt\FastExt.bb"
Include "_SourceCode\Systems\FastExt\ShadowsSimple.bb"
Include "_SourceCode\Systems\FastExt\RenderCubeMapFunctions.bb"

; Code code
Include "_SourceCode\Core\Core_GeneralConstants.bb"
Include "_SourceCode\Core\Core_InputManagement.bb"

; Libraries code
Include "_SourceCode\Libraries\Library_FastImage.bb"
Include "_SourceCode\Libraries\Library_XMLParser.bb"
Include "_SourceCode\Libraries\Library_Ocean.bb"
Include "_SourceCode\Systems\System_FxManager.bb"

; Game code
Include "_SourceCode\Game\Game_Settings.bb"
Include "_SourceCode\Systems\DevilParticleSystem.bb"

; --- Initializate 3D mode ---
Game_LoadConfig()

Graphics3D(GAME_WINDOW_W, GAME_WINDOW_H, GAME_WINDOW_DEPTH, GAME_WINDOW_MODE) : InitExt()
SetBuffer(BackBuffer())
Dither(True)
WBuffer(True)
AntiAlias(False)

HidePointer()

Dither(False)
WBuffer(True)
AntiAlias(False)

;Explosion = LoadSprite("Textures\Woosh.png")

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
;   INCLUDES
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; Core code
Include "_SourceCode\Core\Core_DeltaTime.bb"
Include "_SourceCode\Core\Core_Maths.bb"
Include "_SourceCode\Core\Core_Tools.bb"
;Include "_SourceCode\Core\Core_Verlets.bb"

; Online code
Include "_SourceCode\Online\CoreOnline.bb"
Include "_SourceCode\Online\ValueOnline.bb"

; Game code
Include "_SourceCode\Game\Game.bb"
Include "_SourceCode\Game\Game_Interface.bb"
Include "_SourceCode\Game\Game_Resources_Models.bb"
Include "_SourceCode\Game\Game_Resources_Textures.bb"
Include "_SourceCode\Game\Game_Resources_Sounds.bb"
Include "_SourceCode\Game\Game_Resources_Text.bb"
Include "_SourceCode\Game\Game_Resources_Menu.bb"
Include "_SourceCode\Game\Game_Character_Parser.bb"

; Stage code
Include "_SourceCode\Game\Stage\Stage.bb"
;Include "_SourceCode\Game\Stage\Particles\Particle_Templates.bb"
Include "_SourceCode\Game\Stage\Camera\Camera.bb"
Include "_SourceCode\Game\Stage\Player\Player.bb"

Include "_SourceCode\Game\Stage\Player\Player_Management.bb"
Include "_SourceCode\Game\Stage\Particles\Particle_Management.bb"
Include "_SourceCode\Game\Stage\Player\Player_Management_Alternate.bb"

Include "_SourceCode\Game\Stage\Player\Action_Controllers.bb"
Include "_SourceCode\Game\Stage\Player\Player_Motion.bb"
Include "_SourceCode\Game\Stage\Player\Player_Animation.bb"

Include "_SourceCode\Game\Stage\Player\Tag Character\Player_2.bb"
Include "_SourceCode\Game\Stage\Player\Tag Character\Player_Management_2.bb"
Include "_SourceCode\Game\Stage\Player\Tag Character\Player_Motion_2.bb"
Include "_SourceCode\Game\Stage\Player\Tag Character\Player_Animation_2.bb"

Include "_SourceCode\Game\Stage\Objects\Objects.bb"
;Include "_SourceCode\Game\Stage\Objects\Segments.bb"

; Menu code
Include "_SourceCode\Game\Menu\Menu.bb"
Include "_SourceCode\Game\Menu\Menu_Machine.bb"
Include "_SourceCode\Game\Menu\Menu_Interface.bb"
Include "_SourceCode\Game\Menu\Menu_Control_Text.bb"
Include "_SourceCode\Game\Menu\Menu_Control_Image.bb"

; General systems code
Include "_SourceCode\Systems\RottNet.bb"
Include "_SourceCode\Systems\System_FxManager.bb"
Include "_SourceCode\Systems\System_AAGlow.bb"
Include "_SourceCode\Systems\System_PostFX.bb"
Include "_SourceCode\Systems\System_GameScript_VM.bb"
Include "_SourceCode\Systems\System_GameScript_Compiler.bb"
Include "_SourceCode\Systems\System_GameScript_Functions.bb"
Include "_SourceCode\Systems\Trail.bb"
;Include "_SourceCode\Systems\Rain.bb"
Include "_SourceCode\Systems\DevilGUI.bb"
Include "_SourceCode\Systems\TextWrap.bb"
Include "_SourceCode\Systems\Sounds_Array.bb"
Include "_SourceCode\Systems\System_Barliesque_Sun_Flares.bb"
Include "_SourceCode\Systems\System_SyntaxError_Sprite_Control.bb"
Include "_SourceCode\Systems\Transitions.bb"
;Include "_SourceCode\Systems\Bass.bb"
;Include "_SourceCode\Systems\System_Buttons.bb"
;Include "_SourceCode\Systems\ParticleWorks.bb"

Global Online=0

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
;   ENTRY POINT
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

If (KeyDown(Key_O)) Then
NewGame = StartNetGame()
Game\Online = 1
EndIf
If NewGame = 1 Then
Game_Startup()
PlayerID = CreateNetPlayer(2)
EndIf
If NewGame = 2 Then
Game_Startup()
PlayerID = CreateNetPlayer(1)
EndIf

; Data Loss FailSafe
If KeyDown(KEY_R) Then
fileout = WriteFile("Data\Defaults.dat")

WriteLine(fileout, "Sonic")
WriteLine(fileout, 0)
WriteLine(fileout, 0)
WriteLine(fileout, 0)
WriteLine(fileout, "")
WriteLine(fileout, 1)
WriteLine(fileout, 1)
WriteLine(fileout, 1)
WriteLine(fileout, 0)
WriteLine(fileout, 0)

CloseFile(fileout)
EndIf
; -------------------

If (Not KeyDown(Key_O)) Then
	
;	If (Game\Gameplay\TypingStage = 1) Then
;		GUI_UpdateGUI()
;	EndIf
	
	Game\Online = 0

	Game_Startup()
	While(1)
		
		If (KeyHit(KEY_SPACE) And Paused = 1) Then
			
			Game\PickStage = 1 : Game\StartGame = 0 : Game\State = GAME_STATE_END : Online = 0 : BP_EndSession(); : DeleteButton(2) : Cls; : Game\Gameplay\TypingStage = 1 : Game\Path$ = "Stages/" + Game\Stage\Hub$ + "/"; Exit
		EndIf
			
			Game_Update()
	Wend
	
	End
	
;	While (Game\StageSelecting = 1)
;		If (KeyHit(Key_ESC)) Then End
;	Wend
	
EndIf
;~IDEal Editor Parameters:
;~C#Blitz3D