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
	; tMenu_Control_Image
	; ---------------------------------------------------------------------------------------------------------
	Type tMenu_Control_Image
		Field Texture
		Field SubFrame
		Field X
		Field Y
		Field Angle
		Field RGB
		Field Alpha#
	End Type
	
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	CONSTANTS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; Image control constants
	Const MENU_CONTROL_IMAGE		= 2
	
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
; 	METHODS
; /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

	; ---- Creation/Destruction and management ----
	; =========================================================================================================
	; Menu_Control_Image_Create
	; =========================================================================================================
	Function Menu_Control_Image_Create.tMenu_Control(Name$, Texture, SubFrame, X, Y, Angle=0, RGB=$FFFFFF, Alpha#=1.0)
		ci.tMenu_Control_Image = New tMenu_Control_Image
			ci\Texture	 = Texture
			ci\SubFrame	 = SubFrame
			ci\X 		 = X
			ci\Y 		 = Y
			ci\Angle	 = Angle
			ci\RGB		 = RGB
			ci\Alpha     = Alpha
		
		c.tMenu_Control = New tMenu_Control
			c\Name$   = Name$
			c\Kind	  = MENU_CONTROL_IMAGE
			c\Addr%	  = Handle(ci)
			c\Visible = True

		Return c
	End Function


	; =========================================================================================================
	; Menu_Control_Image_Destroy
	; =========================================================================================================
	Function Menu_Control_Image_Destroy(c.tMenu_Control)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_IMAGE) Then RuntimeError("Menu_Control_Image_Destroy -> Wrong type of control passed to method.")

		; Cast control
		ci.tMenu_Control_Image = Object.tMenu_Control_Image(c\Addr%)

		; Destroy
		Delete ci
		Delete c
	End Function

	; =========================================================================================================
	; Menu_Control_Image_Render
	; =========================================================================================================
	Function Menu_Control_Image_Render(c.tMenu_Control)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_IMAGE) Then RuntimeError("Menu_Control_Image_Render -> Wrong type of control passed to method.")

		; If hidden, exit
		If (c\Visible = False) Then Return
		
		; Cast control
		ci.tMenu_Control_Image = Object.tMenu_Control_Image(c\Addr%)

		; Render
		StartDraw()
			SetRotation(ci\Angle)
			SetColor((ci\RGB Shr 16) And $FF, (ci\RGB Shr 8) And $FF, ci\RGB And $FF)
			SetAlpha(ci\Alpha#)
			DrawImageEx(ci\Texture, ci\X, ci\Y, ci\SubFrame)
		EndDraw()
	End Function


	; ---- Attributes get ----
	; =========================================================================================================
	; Menu_Control_Image_GetTexture
	; =========================================================================================================
	Function Menu_Control_Image_GetTexture(c.tMenu_Control)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_IMAGE) Then RuntimeError("Menu_Control_Image_GetTexture -> Wrong type of control passed to method.")

		; Return
		ci.tMenu_Control_Image = Object.tMenu_Control_Image(c\Addr%)
		Return ci\Texture
	End Function


	; =========================================================================================================
	; Menu_Control_Image_GetSubframe
	; =========================================================================================================
	Function Menu_Control_Image_GetSubFrame(c.tMenu_Control)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_IMAGE) Then RuntimeError("Menu_Control_Image_GetSubFrame -> Wrong type of control passed to method.")

		; Return
		ci.tMenu_Control_Image = Object.tMenu_Control_Image(c\Addr%)
		Return ci\SubFrame
	End Function

	
	; =========================================================================================================
	; Menu_Control_Image_GetX
	; =========================================================================================================
	Function Menu_Control_Image_GetX(c.tMenu_Control)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_IMAGE) Then RuntimeError("Menu_Control_Image_GetX -> Wrong type of control passed to method.")

		; Return
		ci.tMenu_Control_Image = Object.tMenu_Control_Image(c\Addr%)
		Return ci\X
	End Function


	; =========================================================================================================
	; Menu_Control_Image_GetY
	; =========================================================================================================
	Function Menu_Control_Image_GetY(c.tMenu_Control)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_IMAGE) Then RuntimeError("Menu_Control_Image_GetY -> Wrong type of control passed to method.")

		; Return
		ci.tMenu_Control_Image = Object.tMenu_Control_Image(c\Addr%)
		Return ci\Y
	End Function


	; =========================================================================================================
	; Menu_Control_Image_GetAngle
	; =========================================================================================================
	Function Menu_Control_Image_GetAngle(c.tMenu_Control)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_IMAGE) Then RuntimeError("Menu_Control_Image_GetAngle -> Wrong type of control passed to method.")

		; Return
		ci.tMenu_Control_Image = Object.tMenu_Control_Image(c\Addr%)
		Return ci\Angle
	End Function


	; =========================================================================================================
	; Menu_Control_Image_GetColor
	; =========================================================================================================
	Function Menu_Control_Image_GetColor(c.tMenu_Control)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_IMAGE) Then RuntimeError("Menu_Control_Image_GetColor -> Wrong type of control passed to method.")

		; Return
		ci.tMenu_Control_Image = Object.tMenu_Control_Image(c\Addr%)
		Return ci\RGB
	End Function


	; =========================================================================================================
	; Menu_Control_Image_GetAlpha
	; =========================================================================================================
	Function Menu_Control_Image_GetAlpha#(c.tMenu_Control)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_IMAGE) Then RuntimeError("Menu_Control_Image_GetAlpha -> Wrong type of control passed to method.")

		; Return
		ci.tMenu_Control_Image = Object.tMenu_Control_Image(c\Addr%)
		Return ci\Alpha#
	End Function


	; ---- Attributes set ----
	; =========================================================================================================
	; Menu_Control_Image_SetTexture
	; =========================================================================================================
	Function Menu_Control_Image_SetTexture(c.tMenu_Control, Texture)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_IMAGE) Then RuntimeError("Menu_Control_Image_SetTexture -> Wrong type of control passed to method.")

		; Return
		ci.tMenu_Control_Image = Object.tMenu_Control_Image(c\Addr%)
		ci\Texture = Texture
	End Function

	; =========================================================================================================
	; Menu_Control_Image_SetSubFrame
	; =========================================================================================================
	Function Menu_Control_Image_SetSubFrame(c.tMenu_Control, SubFrame)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_IMAGE) Then RuntimeError("Menu_Control_Image_SetSubFrame -> Wrong type of control passed to method.")

		; Return
		ci.tMenu_Control_Image = Object.tMenu_Control_Image(c\Addr%)
		ci\SubFrame = SubFrame
	End Function
	
	; =========================================================================================================
	; Menu_Control_Image_SetX
	; =========================================================================================================
	Function Menu_Control_Image_SetX(c.tMenu_Control, X)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_IMAGE) Then RuntimeError("Menu_Control_Image_SetX -> Wrong type of control passed to method.")

		; Return
		ci.tMenu_Control_Image = Object.tMenu_Control_Image(c\Addr%)
		ci\X = X
	End Function


	; =========================================================================================================
	; Menu_Control_Image_SetY
	; =========================================================================================================
	Function Menu_Control_Image_SetY(c.tMenu_Control, Y)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_IMAGE) Then RuntimeError("Menu_Control_Image_SetY -> Wrong type of control passed to method.")

		; Return
		ci.tMenu_Control_Image = Object.tMenu_Control_Image(c\Addr%)
		ci\Y = Y
	End Function


	; =========================================================================================================
	; Menu_Control_Image_SetAngle
	; =========================================================================================================
	Function Menu_Control_Image_SetAngle(c.tMenu_Control, Angle)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_IMAGE) Then RuntimeError("Menu_Control_Image_SetAngle -> Wrong type of control passed to method.")

		; Return
		ci.tMenu_Control_Image = Object.tMenu_Control_Image(c\Addr%)
		ci\Angle = Angle
	End Function


	; =========================================================================================================
	; Menu_Control_Image_SetColor
	; =========================================================================================================
	Function Menu_Control_Image_SetColor(c.tMenu_Control, RGB)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_IMAGE) Then RuntimeError("Menu_Control_Image_SetColor -> Wrong type of control passed to method.")

		; Return
		ci.tMenu_Control_Image = Object.tMenu_Control_Image(c\Addr%)
		ci\RGB = RGB
	End Function

	; =========================================================================================================
	; Menu_Control_Image_SetAlpha
	; =========================================================================================================
	Function Menu_Control_Image_SetAlpha(c.tMenu_Control, Alpha#)
		; Check if the control kind is the correct
		If (c\Kind <> MENU_CONTROL_IMAGE) Then RuntimeError("Menu_Control_Image_SetAlpha -> Wrong type of control passed to method.")

		; Return
		ci.tMenu_Control_Image = Object.tMenu_Control_Image(c\Addr%)
		ci\Alpha# = Alpha#
	End Function