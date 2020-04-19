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

; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
; 	STRUCTURES
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\

	; ---------------------------------------------------------------------------------------------------------	
	; tMenu_Control_Text
	; ---------------------------------------------------------------------------------------------------------
	Type tMenu_Control_Text
		Field X
		Field Y
		Field Font
		Field Contents$
		Field RGB
	End Type
	
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	CONSTANTS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; Text control constants
	Const MENU_CONTROL_TEXT		= 1
	
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	METHODS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; ---- Creation/Destruction and management ----
	; =========================================================================================================
	; Menu_Control_Text_Create
	; =========================================================================================================
	Function Menu_Control_Text_Create.tMenu_Control(Name$, X, Y, Font, Contents$)
		ct.tMenu_Control_Text = New tMenu_Control_Text
			ct\X 		 = X
			ct\Y 		 = Y
			ct\Font		 = Font
			ct\Contents$ = Contents$
		
		c.tMenu_Control = New tMenu_Control
			c\Name$   = Name$
			c\Kind	  = MENU_CONTROL_TEXT
			c\Addr%	  = Handle(ct)
			c\Visible = True

		Return c
	End Function


	; =========================================================================================================
	; Menu_Control_Text_Destroy
	; =========================================================================================================
	Function Menu_Control_Text_Destroy(c.tMenu_Control)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_TEXT) Then RuntimeError("Menu_Control_Text_Destroy -> Wrong type of control passed to method.")

		; Cast control
		ct.tMenu_Control_Text = Object.tMenu_Control_Text(c\Addr%)

		; Destroy
		Delete ct
		Delete c
	End Function



	; =========================================================================================================
	; Menu_Control_Text_Render
	; =========================================================================================================
	Function Menu_Control_Text_Render(c.tMenu_Control)		
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_TEXT) Then RuntimeError("Menu_Control_Text_Destroy -> Wrong type of control passed to method.")

		; If hidden, exit
		If (c\Visible = False) Then Return
		
		; Cast control
		ct.tMenu_Control_Text = Object.tMenu_Control_Text(c\Addr%)

		; Render
		Text(ct\X, ct\Y, ct\Contents$)
	End Function
	

	; ---- Attributes get ----
	; =========================================================================================================
	; Menu_Control_Text_GetX
	; =========================================================================================================
	Function Menu_Control_Text_GetX(c.tMenu_Control)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_TEXT) Then RuntimeError("Menu_Control_Text_GetX -> Wrong type of control passed to method.")

		; Return
		ct.tMenu_Control_Text = Object.tMenu_Control_Text(c\Addr%)
		Return ct\X
	End Function


	; =========================================================================================================
	; Menu_Control_Text_GetY
	; =========================================================================================================
	Function Menu_Control_Text_GetY(c.tMenu_Control)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_TEXT) Then RuntimeError("Menu_Control_Text_GetY -> Wrong type of control passed to method.")

		; Return
		ct.tMenu_Control_Text = Object.tMenu_Control_Text(c\Addr%)
		Return ct\Y
	End Function


	; =========================================================================================================
	; Menu_Control_Text_GetFont
	; =========================================================================================================
	Function Menu_Control_Text_GetFont(c.tMenu_Control)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_TEXT) Then RuntimeError("Menu_Control_Text_GetFont -> Wrong type of control passed to method.")

		; Return
		ct.tMenu_Control_Text = Object.tMenu_Control_Text(c\Addr%)
		Return ct\Font
	End Function

	
	; =========================================================================================================
	; Menu_Control_Text_GetContents
	; =========================================================================================================
	Function Menu_Control_Text_GetContents(c.tMenu_Control)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_TEXT) Then RuntimeError("Menu_Control_Text_GetContents -> Wrong type of control passed to method.")

		; Return
		ct.tMenu_Control_Text = Object.tMenu_Control_Text(c\Addr%)
		Return ct\Contents
	End Function


	; ---- Attributes set ----
	; =========================================================================================================
	; Menu_Control_Text_SetX
	; =========================================================================================================
	Function Menu_Control_Text_SetX(c.tMenu_Control, X)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_TEXT) Then RuntimeError("Menu_Control_Text_SetX -> Wrong type of control passed to method.")

		; Return
		ct.tMenu_Control_Text = Object.tMenu_Control_Text(c\Addr%)
		ct\X = X
	End Function


	; =========================================================================================================
	; Menu_Control_Text_SetY
	; =========================================================================================================
	Function Menu_Control_Text_SetY(c.tMenu_Control, Y)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_TEXT) Then RuntimeError("Menu_Control_Text_SetY -> Wrong type of control passed to method.")

		; Return
		ct.tMenu_Control_Text = Object.tMenu_Control_Text(c\Addr%)
		ct\Y = Y
	End Function


	; =========================================================================================================
	; Menu_Control_Text_SetFont
	; =========================================================================================================
	Function Menu_Control_Text_SetFont(c.tMenu_Control, Font)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_TEXT) Then RuntimeError("Menu_Control_Text_SetFont -> Wrong type of control passed to method.")

		; Return
		ct.tMenu_Control_Text = Object.tMenu_Control_Text(c\Addr%)
		ct\Font = Font
	End Function


	; =========================================================================================================
	; Menu_Control_Text_SetContents
	; =========================================================================================================
	Function Menu_Control_Text_SetContents(c.tMenu_Control, Contents$)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_TEXT) Then RuntimeError("Menu_Control_Text_SetContents -> Wrong type of control passed to method.")

		; Return
		ct.tMenu_Control_Text = Object.tMenu_Control_Text(c\Addr%)
		ct\Contents = Contents
	End Function


	; =========================================================================================================
	; Menu_Control_Text_SetColor
	; =========================================================================================================
	Function Menu_Control_Text_SetColor(c.tMenu_Control, RGB)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_TEXT) Then RuntimeError("Menu_Control_Text_SetColor -> Wrong type of control passed to method.")

		; Return
		ct.tMenu_Control_Text = Object.tMenu_Control_Text(c\Addr%)
		ct\RGB = RGB
	End Function
