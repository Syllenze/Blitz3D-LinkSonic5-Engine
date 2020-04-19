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
;               20/01/2008 - Started working on Menu engine.                                                   ;
;                                                                                                              ;
;==============================================================================================================;
;                                                                                                              ;
;   TODO: - Implement pretty much everything                                                                   ;
;                                                                                                              ;
;==============================================================================================================;
	
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	METHODS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; ---------------------------------------------------------------------------------------------------------
	; Game_Menu_Update
	; ---------------------------------------------------------------------------------------------------------
	Function Game_Menu_Update()
		Select Game\State
			Case GAME_STATE_START : Game_Menu_Start()
			Case GAME_STATE_STEP  : Game_Menu_Step()
			Case GAME_STATE_END   : Game_Menu_End()
		End Select
	End Function


	; ---------------------------------------------------------------------------------------------------------
	; Game_Menu_Start
	; ---------------------------------------------------------------------------------------------------------
	Function Game_Menu_Start()
		; Create menu machine
		Game\Menu = Menu_Machine_Create()
		
		; Create test interface
		MainMenu.tMenu_Interface = Menu_Interface_Create("Test01")

			; Create different test controls
			Menu_Interface_AttachControl(MainMenu, Menu_Control_Text_Create("Message01", 20, 20, 0, "OH LAWD"))

		; Attach interfaces to menu
		Menu_Machine_AttachInterface(Game\Menu, MainMenu)

		; Next phase
		Game\State = GAME_STATE_STEP
	End Function


	; ---------------------------------------------------------------------------------------------------------
	; Game_Menu_Step
	; ---------------------------------------------------------------------------------------------------------
	Function Game_Menu_Step()
		Cls
		Menu_Machine_Update(Game\Menu)
		Menu_Machine_Render(Game\Menu)
		PostEffect_UpdateAll(Game\DeltaTime)
		Flip
	End Function


	; ---------------------------------------------------------------------------------------------------------
	; Game_Menu_End
	; ---------------------------------------------------------------------------------------------------------
	Function Game_Menu_End()
		
	End Function


	; =========================================================================================================
	; Menu_Callback
	; =========================================================================================================
	Function Menu_Callback(m.tMenu_Machine, i.tMenu_Interface, Callback$)
		Select i\Name$
			Case "Test01"
				Select Callback$
					Case "PreStart"
						PostEffect_Create_FadeIn(0.01, 0, 0, 0)
					Case "Start"
						If ((First tPostEffect_Fade) = Null) Then Return True
						Return False
					Case "Step"

					Case "PreEnd"
						PostEffect_Create_FadeOut(0.01, 0, 0, 0)
					Case "End"
						If (First tPostEffect_Fade = Null) Then Return True
						Return False
					Case "Change"

					Case "Pressed"
					
					Case "Pre-Render"
					
					Case "Post-Render"
					
				End Select
		End Select
	End Function

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	DECLARATIONS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	